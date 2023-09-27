/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import ClayForm, {ClayInput} from '@clayui/form';
import ClayLayout from '@clayui/layout';
import classnames from 'classnames';
import {fetch, navigate} from 'frontend-js-web';
import React, {useRef, useState} from 'react';

import {API_URL} from '../Constants';
import {IFDSViewSectionProps} from '../FDSView';
import RequiredMark from '../components/RequiredMark';
import openDefaultFailureToast from '../utils/openDefaultFailureToast';
import openDefaultSuccessToast from '../utils/openDefaultSuccessToast';

function Pagination({
	fdsView,
	fdsViewsURL,
	namespace,
	onFDSViewUpdate,
}: IFDSViewSectionProps) {
	const [listOfItemsPerPage, setListOfItemsPerPage] = useState(
		fdsView.listOfItemsPerPage
	);
	const [defaultItemsPerPage, setDefaultItemsPerPage] = useState(
		fdsView.defaultItemsPerPage.toString()
	);
	const [
		incompatibleDefaultItemsPerPageValidationError,
		setIncompatibleDefaultItemsPerPageValidationError,
	] = useState(false);
	const [
		invalidNumberInListOfItemsPerPageValidationError,
		setInvalidNumberInListOfItemsPerPageValidationError,
	] = useState(false);
	const [
		requiredDefaultItemsPerPageValidationError,
		setRequiredDefaultItemsPerPageValidationError,
	] = useState(false);
	const [
		requiredListOfItemsPerPageValidationError,
		setRequiredListOfItemsPerPageValidationError,
	] = useState(false);
	const [
		invalidListOfItemsPerPageLengthValidationError,
		setInvalidListOfItemsPerPageLengthValidationError,
	] = useState(false);

	const fdsViewListOfItemsPerPageRef = useRef<HTMLInputElement>(null);
	const fdsViewDefaultItemsPerPageRef = useRef<HTMLInputElement>(null);

	const getItemsPerPageArray = (): string[] => {
		return listOfItemsPerPage.split(',').map((size) => size.trim());
	};

	const listOfItemsPerPageFieldValidation = (itemsPerPageArray: string[]) => {
		if (itemsPerPageArray.length > 25) {
			setInvalidListOfItemsPerPageLengthValidationError(true);
		}
		else {
			setInvalidListOfItemsPerPageLengthValidationError(false);
		}

		const invalidNumber = itemsPerPageArray.some((element) => {
			const isPositiveInteger = /^\d+$/.test(element);
			const item: number = parseInt(element, 10);

			return !isPositiveInteger || item < 1 || item > 1000;
		});

		setInvalidNumberInListOfItemsPerPageValidationError(invalidNumber);
	};

	const compatibilityValidation = (itemsPerPageArray: string[]) => {
		if (!itemsPerPageArray.includes(defaultItemsPerPage)) {
			setIncompatibleDefaultItemsPerPageValidationError(true);
		}
		else {
			setIncompatibleDefaultItemsPerPageValidationError(false);
		}
	};

	const handleSaveClick = async () => {
		const getItemsPerPageString = () => {
			const uniqueItemsPerPageArray = [
				...new Set(getItemsPerPageArray()),
			];

			const sortedItemsPerPageArray = uniqueItemsPerPageArray
				.map((element) => parseInt(element, 10))
				.sort((a, b) => a - b);

			return sortedItemsPerPageArray.join(', ');
		};

		const itemsPerPage = getItemsPerPageString();

		const body = {
			defaultItemsPerPage,
			label: fdsView.label,
			listOfItemsPerPage: itemsPerPage,
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

			onFDSViewUpdate(responseJSON);
		}
		else {
			openDefaultFailureToast();
		}
	};

	const listOfItemsPerPageValidationError =
		requiredListOfItemsPerPageValidationError ||
		invalidNumberInListOfItemsPerPageValidationError ||
		invalidListOfItemsPerPageLengthValidationError;

	const defaultItemsPerPageValidationError =
		incompatibleDefaultItemsPerPageValidationError ||
		requiredDefaultItemsPerPageValidationError;

	return (
		<ClayLayout.Sheet className="mt-3" size="lg">
			<ClayLayout.SheetHeader>
				<h2 className="sheet-title">
					{Liferay.Language.get('pagination')}
				</h2>

				<div className="sheet-text">
					{Liferay.Language.get(
						'data-set-view-pagination-description'
					)}
				</div>
			</ClayLayout.SheetHeader>

			<ClayLayout.SheetSection>
				<ClayForm.Group
					className={classnames(
						listOfItemsPerPageValidationError && 'has-error'
					)}
				>
					<label
						htmlFor={`${namespace}fdsViewListOfItemsPerPageTextarea`}
					>
						{Liferay.Language.get('list-of-items-per-page')}

						<RequiredMark />
					</label>

					<ClayInput
						component="textarea"
						id={`${namespace}fdsViewListOfItemsPerPageTextarea`}
						onBlur={() => {
							const itemsPerPageArray = getItemsPerPageArray();

							listOfItemsPerPageFieldValidation(
								itemsPerPageArray
							);

							compatibilityValidation(itemsPerPageArray);

							setRequiredListOfItemsPerPageValidationError(
								!fdsViewListOfItemsPerPageRef.current?.value
							);
						}}
						onChange={(event) =>
							setListOfItemsPerPage(event.target.value)
						}
						ref={fdsViewListOfItemsPerPageRef}
						required
						type="text"
						value={listOfItemsPerPage}
					/>

					{listOfItemsPerPageValidationError && (
						<ClayForm.FeedbackGroup>
							<ClayForm.FeedbackItem>
								<ClayForm.FeedbackIndicator symbol="exclamation-full" />

								{requiredListOfItemsPerPageValidationError
									? Liferay.Language.get(
											'this-field-is-required'
									  )
									: invalidNumberInListOfItemsPerPageValidationError
									? Liferay.Language.get(
											'this-field-contains-an-invalid-number'
									  )
									: Liferay.Language.get(
											'this-field-contains-more-than-25-elements'
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
						defaultItemsPerPageValidationError && 'has-error'
					)}
				>
					<label
						htmlFor={`${namespace}fdsViewDefaultItemsPerPageInput`}
					>
						{Liferay.Language.get('default-items-per-page')}

						<RequiredMark />
					</label>

					<ClayInput
						id={`${namespace}fdsViewDefaultItemsPerPageInput`}
						onBlur={() => {
							compatibilityValidation(getItemsPerPageArray());

							setRequiredDefaultItemsPerPageValidationError(
								!fdsViewDefaultItemsPerPageRef.current?.value
							);
						}}
						onChange={(event) =>
							setDefaultItemsPerPage(event.target.value)
						}
						ref={fdsViewDefaultItemsPerPageRef}
						type="number"
						value={defaultItemsPerPage}
					/>

					{defaultItemsPerPageValidationError && (
						<ClayForm.FeedbackGroup>
							<ClayForm.FeedbackItem>
								<ClayForm.FeedbackIndicator symbol="exclamation-full" />

								{requiredDefaultItemsPerPageValidationError
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
					<ClayButton
						disabled={
							listOfItemsPerPageValidationError ||
							defaultItemsPerPageValidationError
						}
						onClick={handleSaveClick}
					>
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
