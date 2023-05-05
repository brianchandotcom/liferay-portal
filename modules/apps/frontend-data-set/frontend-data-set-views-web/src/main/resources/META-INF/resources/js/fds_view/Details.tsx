/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

import ClayButton from '@clayui/button';
import ClayForm, {ClayInput} from '@clayui/form';
import ClayLayout from '@clayui/layout';
import classNames from 'classnames';
import {fetch, navigate, openToast} from 'frontend-js-web';
import React, {useRef, useState} from 'react';

import {API_URL} from '../Constants';
import {IFDSViewSectionInterface} from '../FDSView';
import RequiredMark from '../components/RequiredMark';

const Details = ({
	fdsView,
	fdsViewsURL,
	namespace,
}: IFDSViewSectionInterface) => {
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

		const responseJSON = await response.json();

		if (responseJSON?.id) {
			openToast({
				message: Liferay.Language.get(
					'your-request-completed-successfully'
				),
				type: 'success',
			});

			const controlMenuHeaderTitles = document.getElementsByClassName(
				'control-menu-level-1-heading'
			);

			if (controlMenuHeaderTitles.length === 1) {
				controlMenuHeaderTitles[0].innerHTML = Liferay.Util.escapeHTML(
					fdsViewLabelRef.current?.value ?? ''
				);
			}
		}
		else {
			openToast({
				message: Liferay.Language.get(
					'your-request-failed-to-complete'
				),
				type: 'danger',
			});
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
