/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 */

import ClayButton from '@clayui/button';
import ClayForm, {ClayInput} from '@clayui/form';
import ClayIcon from '@clayui/icon';
import ClayModal from '@clayui/modal';
import ClayMultiSelect from '@clayui/multi-select';
import classNames from 'classnames';
import {openToast} from 'frontend-js-web';
import React, {useContext, useState} from 'react';

import ChartContext from '../ChartContext';
import {createOrganizations} from '../data/organizations';

export default function AddOrganizationModal({
	closeModal,
	observer,
	parentData,
}) {
	const [query, setQuery] = useState('');
	const [items, setItems] = useState([]);
	const [errors, setErrors] = useState([]);
	const {chartInstanceRef} = useContext(ChartContext);

	function handleSave() {
		const newOrganizations = query
			? items.concat({label: query, value: query})
			: [...items];

		const organizationNames = newOrganizations.map((item) => item.label);

		if (!newOrganizations.length) {
			setErrors([Liferay.Language.get('a-name-is-required')]);

			return;
		}

		createOrganizations(organizationNames, parentData.id).then(
			(results) => {
				const newOrganizationsDetails = [];
				const newErrors = new Set();
				const failedOrganizations = [];

				results.forEach((result, fetchNumber) => {
					if (result.status === 'rejected') {
						failedOrganizations.push(newOrganizations[fetchNumber]);
						newErrors.add(result.reason.title);
					}
					else {
						newOrganizationsDetails.push(result.value);
					}
				});

				setItems(failedOrganizations);
				setErrors(Array.from(newErrors));
				setQuery('');

				if (newOrganizationsDetails.length) {
					const message =
						newOrganizationsDetails.length === 1
							? Liferay.Util.sub(
									Liferay.Language.get(
										'1-organization-was-added-to-x'
									),
									parentData.name
							  )
							: Liferay.Util.sub(
									Liferay.Language.get(
										'x-organizations-were-added-to-x'
									),
									newOrganizationsDetails.length,
									parentData.name
							  );

					openToast({
						message,
						type: 'success',
					});

					chartInstanceRef.current.addNodes(
						newOrganizationsDetails,
						'organization',
						parentData
					);

					chartInstanceRef.current.updateNodeContent({
						...parentData,
						numberOfOrganizations:
							parentData.numberOfOrganizations +
							newOrganizationsDetails.length,
					});
				}

				if (!failedOrganizations.length) {
					closeModal();
				}
			}
		);
	}

	return (
		<ClayModal center observer={observer} size="md">
			<ClayModal.Header>
				{Liferay.Language.get('add-organizations')}
			</ClayModal.Header>

			<ClayModal.Body>
				<ClayForm.Group
					className={classNames(!!errors.length && 'has-error')}
				>
					<label htmlFor="addNewOrganization">
						{Liferay.Language.get('name') + ' '}
						<ClayIcon
							className="ml-1 reference-mark"
							symbol="asterisk"
						/>
					</label>

					<ClayInput.Group>
						<ClayInput.GroupItem>
							<ClayMultiSelect
								id="addNewOrganization"
								inputValue={query}
								items={items}
								onChange={setQuery}
								onItemsChange={setItems}
								placeholder={Liferay.Language.get(
									'organization-name'
								)}
							/>

							{!!errors.length && (
								<ClayForm.FeedbackGroup>
									{errors.map((error, i) => (
										<ClayForm.FeedbackItem key={i}>
											<ClayForm.FeedbackIndicator symbol="info-circle" />
											{error}
										</ClayForm.FeedbackItem>
									))}
								</ClayForm.FeedbackGroup>
							)}
						</ClayInput.GroupItem>
					</ClayInput.Group>
				</ClayForm.Group>
			</ClayModal.Body>

			<ClayModal.Footer
				last={
					<ClayButton.Group spaced>
						<ClayButton
							displayType="secondary"
							onClick={closeModal}
						>
							{Liferay.Language.get('cancel')}
						</ClayButton>

						<ClayButton displayType="primary" onClick={handleSave}>
							{Liferay.Language.get('save')}
						</ClayButton>
					</ClayButton.Group>
				}
			/>
		</ClayModal>
	);
}
