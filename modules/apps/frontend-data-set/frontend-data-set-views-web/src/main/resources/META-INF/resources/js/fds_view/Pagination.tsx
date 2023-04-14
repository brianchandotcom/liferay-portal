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
		invalidNumberInPageSizesValidationError,
		setInvalidNumberInPageSizesValidationError,
	] = useState(false);
	const [
		requiredDefaultPageSizeValidationError,
		setRequiredDefaultPageSizeValidationError,
	] = useState(false);
	const [
		requiredPageSizesValidationError,
		setRequiredPageSizesValidationError,
	] = useState(false);
	const [
		invalidPageSizesLengthValidationError,
		setInvalidPageSizesLengthValidationError,
	] = useState(false);

	const fdsViewPageSizesRef = useRef<HTMLInputElement>(null);
	const fdsViewDefaultPageSizeRef = useRef<HTMLInputElement>(null);

	const getPageSizesArray = (): string[] => {
		return pageSizes.trim().split(/\s*(?:,|$)\s*/);
	};

	const pageSizesFieldValidation = (pageSizesArray: string[]) => {
		if (pageSizesArray.length > 25) {
			setInvalidPageSizesLengthValidationError(true);
		}
		else {
			setInvalidPageSizesLengthValidationError(false);
		}

		const invalidNumber = pageSizesArray.some((element) => {
			const isNumber = /^\d+$/.test(element);
			const pageSize: number = parseInt(element, 10);

			return !isNumber || pageSize < 1 || pageSize > 1000;
		});

		if (invalidNumber) {
			setInvalidNumberInPageSizesValidationError(true);
		}
		else {
			setInvalidNumberInPageSizesValidationError(false);
		}
	};

	const compatibilityValidation = (pageSizesArray: string[]) => {
		if (!pageSizesArray.includes(defaultPageSize)) {
			setIncompatibleDefaultPageSizeValidationError(true);
		}
		else {
			setIncompatibleDefaultPageSizeValidationError(false);
		}
	};

	const handleSaveClick = async () => {
		const getPageSizesString = () => {
			const uniquePageSizesArray = [...new Set(getPageSizesArray())];

			const sortedPageSizesArray = uniquePageSizesArray
				.map((element) => parseInt(element, 10))
				.sort((a, b) => a - b);

			return sortedPageSizesArray.join(', ');
		};

		const pageSizesString = getPageSizesString();

		const body = {
			defaultPageSize,
			label: fdsView.label,
			pageSizes: pageSizesString,
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
			setPageSizes(pageSizesString);
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

	const pageSizesValidationError =
		requiredPageSizesValidationError ||
		invalidNumberInPageSizesValidationError ||
		invalidPageSizesLengthValidationError;

	const defaultPageSizeValidationError =
		incompatibleDefaultPageSizeValidationError ||
		requiredDefaultPageSizeValidationError;

	return (
		<ClayLayout.Sheet className="mt-3" size="lg">
			<ClayLayout.SheetHeader>
				<h2 className="sheet-title">
					{Liferay.Language.get('pagination')}
				</h2>

				<div className="sheet-text">
					{Liferay.Language.get(
						'dataset-view-pagination-description'
					)}
				</div>
			</ClayLayout.SheetHeader>

			<ClayLayout.SheetSection>
				<ClayForm.Group
					className={classnames(
						pageSizesValidationError && 'has-error'
					)}
				>
					<label htmlFor={`${namespace}fdsViewPageSizesTextarea`}>
						{Liferay.Language.get('list-of-items-per-page')}

						<RequiredMark />
					</label>

					<ClayInput
						component="textarea"
						id={`${namespace}fdsViewPageSizesTextarea`}
						onBlur={() => {
							const pageSizesArray = getPageSizesArray();

							pageSizesFieldValidation(pageSizesArray);

							compatibilityValidation(pageSizesArray);

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

					{pageSizesValidationError && (
						<ClayForm.FeedbackGroup>
							<ClayForm.FeedbackItem>
								<ClayForm.FeedbackIndicator symbol="exclamation-full" />

								{requiredPageSizesValidationError &&
									Liferay.Language.get(
										'this-field-is-required'
									)}

								{invalidNumberInPageSizesValidationError &&
									Liferay.Language.get(
										'this-field-contains-an-invalid-number-error'
									)}

								{invalidPageSizesLengthValidationError &&
									Liferay.Language.get(
										'this-field-contains-a-very-long-list-error'
									)}
							</ClayForm.FeedbackItem>
						</ClayForm.FeedbackGroup>
					)}

					<ClayForm.Text>
						{Liferay.Language.get('list-of-items-per-page-help')}
					</ClayForm.Text>
				</ClayForm.Group>

				<ClayForm.Group
					className={classnames(
						defaultPageSizeValidationError && 'has-error'
					)}
				>
					<label
						htmlFor={`${namespace}fdsViewPaginationDefaultPageSizeInput`}
					>
						{Liferay.Language.get('default-items-per-page')}

						<RequiredMark />
					</label>

					<ClayInput
						id={`${namespace}fdsViewPaginationDefaultPageSizeInput`}
						onBlur={() => {
							compatibilityValidation(getPageSizesArray());

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

					{defaultPageSizeValidationError && (
						<ClayForm.FeedbackGroup>
							<ClayForm.FeedbackItem>
								<ClayForm.FeedbackIndicator symbol="exclamation-full" />

								{requiredDefaultPageSizeValidationError
									? Liferay.Language.get(
											'this-field-is-required'
									  )
									: Liferay.Language.get(
											'the-default-value-must-exist-in-the-list-of-items-per-page'
									  )}
							</ClayForm.FeedbackItem>
						</ClayForm.FeedbackGroup>
					)}

					<ClayForm.Text>
						{Liferay.Language.get('default-items-per-page-help')}
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
