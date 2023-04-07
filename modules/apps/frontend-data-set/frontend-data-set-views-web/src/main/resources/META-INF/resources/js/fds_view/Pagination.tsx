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
import classnames from 'classnames';
import {fetch, navigate, openToast} from 'frontend-js-web';
import React, {useRef, useState} from 'react';

import {TFDSView} from '../FDSViews';
import RequiredMark from '../RequiredMark';

interface IPaginationProps {
	fdsView: TFDSView;
	fdsViewsAPIURL: string;
	fdsViewsURL: string;
	namespace: string;
}

function Pagination({
	fdsView,
	fdsViewsAPIURL,
	fdsViewsURL,
	namespace,
}: IPaginationProps) {
	const [pageSizes, setPageSizes] = useState(fdsView.pageSizes);
	const [defaultPageSize, setDefaultPageSize] = useState(
		fdsView.defaultPageSize.toString()
	);
	const [
		incompatibleDefaultPageSizeValidationError,
		setIncompatibleDefaultPageSizeValidationError,
	] = useState(false);
	const [
		requiredDefaultPageSizeValidationError,
		setRequiredDefaultPageSizeValidationError,
	] = useState(false);
	const [
		requiredPageSizesValidationError,
		setRequiredPageSizesValidationError,
	] = useState(false);

	const fdsViewPageSizesRef = useRef<HTMLInputElement>(null);
	const fdsViewDefaultPageSizeRef = useRef<HTMLInputElement>(null);

	const compatibilityValidation = () => {
		const paginationArray = pageSizes.split(', ');

		if (!paginationArray.includes(defaultPageSize)) {
			setIncompatibleDefaultPageSizeValidationError(true);
		}
		else {
			setIncompatibleDefaultPageSizeValidationError(false);
		}
	};

	const handleSaveClick = async () => {
		const body = {
			defaultPageSize,
			label: fdsView.label,
			pageSizes,
		};

		const response = await fetch(
			`${fdsViewsAPIURL}/by-external-reference-code/${fdsView.externalReferenceCode}`,
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

		if (response.ok && responseJSON?.id) {
			openToast({
				message: Liferay.Language.get(
					'your-request-completed-successfully'
				),
				type: 'success',
			});
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
					{Liferay.Language.get('pagination')}
				</h2>

				<div className="sheet-text">
					{Liferay.Language.get(
						'use-values-separated-by-commas-to-generate-different-pagination-items-in-the-dataset-view'
					)}
				</div>
			</ClayLayout.SheetHeader>

			<ClayLayout.SheetSection>
				<ClayForm.Group
					className={classnames(
						requiredPageSizesValidationError && 'has-error'
					)}
				>
					<label htmlFor={`${namespace}fdsViewPageSizesTextarea`}>
						{Liferay.Language.get('page-sizes')}

						<RequiredMark />
					</label>

					<ClayInput
						component="textarea"
						id={`${namespace}fdsViewPageSizesTextarea`}
						onBlur={() => {
							compatibilityValidation();

							setRequiredPageSizesValidationError(
								!fdsViewPageSizesRef.current?.value
							);
						}}
						onChange={(event) => setPageSizes(event.target.value)}
						ref={fdsViewPageSizesRef}
						required
						type="text"
						value={pageSizes}
					/>

					{requiredPageSizesValidationError && (
						<ClayForm.FeedbackGroup>
							<ClayForm.FeedbackItem>
								<ClayForm.FeedbackIndicator symbol="exclamation-full" />

								{Liferay.Language.get('this-field-is-required')}
							</ClayForm.FeedbackItem>
						</ClayForm.FeedbackGroup>
					)}
				</ClayForm.Group>

				<ClayForm.Group
					className={classnames(
						(incompatibleDefaultPageSizeValidationError ||
							requiredDefaultPageSizeValidationError) &&
							'has-error'
					)}
				>
					<label
						htmlFor={`${namespace}fdsViewPaginationDefaultPageSizeInput`}
					>
						{Liferay.Language.get('default-page-size')}

						<RequiredMark />
					</label>

					<ClayInput
						id={`${namespace}fdsViewPaginationDefaultPageSizeInput`}
						onBlur={() => {
							compatibilityValidation();

							setRequiredDefaultPageSizeValidationError(
								!fdsViewDefaultPageSizeRef.current?.value
							);
						}}
						onChange={(event) =>
							setDefaultPageSize(event.target.value)
						}
						ref={fdsViewDefaultPageSizeRef}
						type="number"
						value={defaultPageSize}
					/>

					{(incompatibleDefaultPageSizeValidationError ||
						requiredDefaultPageSizeValidationError) && (
						<ClayForm.FeedbackGroup>
							<ClayForm.FeedbackItem>
								<ClayForm.FeedbackIndicator symbol="exclamation-full" />

								{requiredDefaultPageSizeValidationError
									? Liferay.Language.get(
											'this-field-is-required'
									  )
									: Liferay.Language.get(
											'the-default-value-must-exist-in-the-pagination-items-input'
									  )}
							</ClayForm.FeedbackItem>
						</ClayForm.FeedbackGroup>
					)}

					<ClayForm.Text>
						{Liferay.Language.get(
							'value-to-be-displayed-by-default-in-the-dataset-view-pagination'
						)}
					</ClayForm.Text>
				</ClayForm.Group>
			</ClayLayout.SheetSection>

			<ClayLayout.SheetFooter>
				<ClayButton.Group spaced>
					<ClayButton onClick={handleSaveClick}>
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
}

export default Pagination;
