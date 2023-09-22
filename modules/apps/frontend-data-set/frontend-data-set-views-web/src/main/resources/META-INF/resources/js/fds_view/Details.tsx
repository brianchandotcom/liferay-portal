/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import ClayForm, {ClayInput} from '@clayui/form';
import ClayIcon from '@clayui/icon';
import ClayLayout from '@clayui/layout';
import ClayList from '@clayui/list';
import classNames from 'classnames';
import {fetch, navigate} from 'frontend-js-web';
import React, {useRef, useState} from 'react';

import {API_URL} from '../Constants';
import {IFDSViewSectionProps} from '../FDSView';
import RequiredMark from '../components/RequiredMark';
import openDefaultFailureToast from '../utils/openDefaultFailureToast';
import openDefaultSuccessToast from '../utils/openDefaultSuccessToast';

const Details = ({
	fdsView,
	fdsViewsURL,
	namespace,
	onFDSViewUpdate,
}: IFDSViewSectionProps) => {
	const [labelValidationError, setLabelValidationError] = useState(false);

	const fdsViewDescriptionRef = useRef<HTMLInputElement>(null);
	const fdsViewLabelRef = useRef<HTMLInputElement>(null);

	const updateFDSView = async () => {
		const body = {
			description: fdsViewDescriptionRef.current?.value,
			label: fdsViewLabelRef.current?.value,
		};

		const response = await fetch(
			`${API_URL.FDS_VIEWS}/by-external-reference-code/${fdsView.externalReferenceCode}`,
			{
				body: JSON.stringify(body),
				headers: {
					'Accept': 'application/json',
					'Content-Type': 'application/json',
				},
				method: 'PATCH',
			}
		);

		if (!response.ok) {
			openDefaultFailureToast();

			return;
		}

		const responseJSON = await response.json();

		if (responseJSON?.id) {
			openDefaultSuccessToast();

			const controlMenuHeaderTitles = document.getElementsByClassName(
				'control-menu-level-1-heading'
			);

			if (controlMenuHeaderTitles.length === 1) {
				controlMenuHeaderTitles[0].innerHTML = Liferay.Util.escapeHTML(
					fdsViewLabelRef.current?.value ?? ''
				);
			}
			onFDSViewUpdate(responseJSON);
		}
		else {
			openDefaultFailureToast();
		}
	};

	return (
		<ClayLayout.Sheet className="mt-3" size="lg">
			<ClayLayout.SheetHeader>
				<h2 className="sheet-title">
					{Liferay.Language.get('details')}
				</h2>
			</ClayLayout.SheetHeader>

			<ClayLayout.SheetSection>
				<ClayForm.Group
					className={classNames({
						'has-error': labelValidationError,
					})}
				>
					<label htmlFor={`${namespace}fdsViewLabelInput`}>
						{Liferay.Language.get('name')}

						<RequiredMark />
					</label>

					<ClayInput
						defaultValue={fdsView.label}
						id={`${namespace}fdsViewLabelInput`}
						onBlur={() =>
							setLabelValidationError(
								!fdsViewLabelRef.current?.value
							)
						}
						ref={fdsViewLabelRef}
						type="text"
					/>

					{labelValidationError && (
						<ClayForm.FeedbackGroup>
							<ClayForm.FeedbackItem>
								<ClayForm.FeedbackIndicator symbol="exclamation-full" />

								{Liferay.Language.get('this-field-is-required')}
							</ClayForm.FeedbackItem>
						</ClayForm.FeedbackGroup>
					)}
				</ClayForm.Group>

				<ClayForm.Group>
					<label htmlFor={`${namespace}fdsViewDesctiptionInput`}>
						{Liferay.Language.get('description')}
					</label>

					<ClayInput
						defaultValue={fdsView.description}
						id={`${namespace}fdsViewDesctiptionInput`}
						ref={fdsViewDescriptionRef}
						type="text"
					/>
				</ClayForm.Group>
			</ClayLayout.SheetSection>

			<ClayLayout.SheetSection className="mb-4">
				<h3 className="sheet-subtitle">
					{Liferay.Language.get('rest-information')}
				</h3>

				<ClayList className="flex-row flex-wrap">
					<ClayList.Item className="border-0 col-12 col-sm-6" flex>
						<ClayList.ItemField className="justify-content-center">
							<ClayIcon symbol="api-web" />
						</ClayList.ItemField>

						<ClayList.ItemField expand>
							<ClayList.ItemTitle>
								{Liferay.Language.get('application')}
							</ClayList.ItemTitle>

							<ClayList.ItemText>
								{
									fdsView.fdsEntryFDSViewRelationship
										.restApplication
								}
							</ClayList.ItemText>
						</ClayList.ItemField>
					</ClayList.Item>

					<ClayList.Item className="border-0 col-12 col-sm-6" flex>
						<ClayList.ItemField className="justify-content-center">
							<ClayIcon symbol="diagram" />
						</ClayList.ItemField>

						<ClayList.ItemField>
							<ClayList.ItemTitle>
								{Liferay.Language.get('schema')}
							</ClayList.ItemTitle>

							<ClayList.ItemText>
								{fdsView.fdsEntryFDSViewRelationship.restSchema}
							</ClayList.ItemText>
						</ClayList.ItemField>
					</ClayList.Item>

					<ClayList.Item className="border-0 col-12" flex>
						<ClayList.ItemField className="justify-content-center">
							<ClayIcon symbol="nodes" />
						</ClayList.ItemField>

						<ClayList.ItemField>
							<ClayList.ItemTitle>
								{Liferay.Language.get('endpoint')}
							</ClayList.ItemTitle>

							<ClayList.ItemText>
								{
									fdsView.fdsEntryFDSViewRelationship
										.restEndpoint
								}
							</ClayList.ItemText>
						</ClayList.ItemField>
					</ClayList.Item>
				</ClayList>
			</ClayLayout.SheetSection>

			<ClayLayout.SheetFooter>
				<ClayButton.Group spaced>
					<ClayButton onClick={updateFDSView}>
						{Liferay.Language.get('save')}
					</ClayButton>

					<ClayButton
						displayType="secondary"
						onClick={() => navigate(fdsViewsURL)}
					>
						{Liferay.Language.get('cancel')}
					</ClayButton>
				</ClayButton.Group>
			</ClayLayout.SheetFooter>
		</ClayLayout.Sheet>
	);
};

export default Details;
