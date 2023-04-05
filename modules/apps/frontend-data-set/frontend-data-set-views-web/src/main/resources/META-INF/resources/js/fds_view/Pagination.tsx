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
import {fetch, openToast} from 'frontend-js-web';
import React, {useEffect, useState} from 'react';

import {TFDSView} from '../FDSViews';
import RequiredMark from '../RequiredMark';

const FDS_PAGE_SIZES = '4, 8, 20, 40, 60';
const FDS_DEFAULT_PAGE_SIZE = '20';

interface IPaginationProps {
	fdsView: TFDSView;
	fdsViewsAPIURL: string;
	namespace: string;
}

function Pagination({fdsView, fdsViewsAPIURL, namespace}: IPaginationProps) {
	const [pageSizes, setPageSizes] = useState(FDS_PAGE_SIZES);
	const [defaultPageSize, setDefaultPageSize] = useState(
		FDS_DEFAULT_PAGE_SIZE
	);
	const [defaultValueError, setDefaultValueError] = useState(false);

	useEffect(() => {
		const paginationArray = pageSizes.split(', ');

		if (!paginationArray.includes(defaultPageSize)) {
			setDefaultValueError(true);
		}
		else {
			setDefaultValueError(false);
		}
	}, [defaultPageSize, pageSizes]);

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

		if (responseJSON?.id) {
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

				<p>
					Use values separated by commas to generate different
					pagination items in the Dataset view.{' '}
				</p>
			</ClayLayout.SheetHeader>

			<ClayLayout.SheetSection>
				<ClayForm.Group>
					<label htmlFor={`${namespace}fdsViewPageSizesTextarea`}>
						{Liferay.Language.get('page-sizes')}

						<RequiredMark />
					</label>

					<ClayInput
						component="textarea"
						id={`${namespace}fdsViewPaginationItemsTextarea`}
						onChange={(event) => setPageSizes(event.target.value)}
						required
						type="text"
						value={pageSizes}
					/>
				</ClayForm.Group>

				<ClayForm.Group
					className={classnames(defaultValueError && 'has-error')}
				>
					<label
						htmlFor={`${namespace}fdsViewPaginationDefaultPageSizeInput`}
					>
						{Liferay.Language.get('default-page-size')}

						<RequiredMark />
					</label>

					<ClayInput
						id={`${namespace}fdsViewPaginationDefaultValueInput`}
						onChange={(event) =>
							setDefaultPageSize(event.target.value)
						}
						type="text"
						value={defaultPageSize}
					/>

					{defaultValueError && (
						<ClayForm.FeedbackGroup>
							<ClayForm.FeedbackItem>
								<ClayForm.FeedbackIndicator symbol="exclamation-full" />

								{Liferay.Language.get(
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
					<ClayButton
						disabled={defaultValueError}
						onClick={handleSaveClick}
					>
						{Liferay.Language.get('save')}
					</ClayButton>

					<ClayButton
						displayType="secondary"
						onClick={() => {
							setDefaultPageSize(FDS_DEFAULT_PAGE_SIZE);

							setPageSizes(FDS_PAGE_SIZES);
						}}
					>
						{Liferay.Language.get('cancel')}
					</ClayButton>
				</ClayButton.Group>
			</ClayLayout.SheetFooter>
		</ClayLayout.Sheet>
	);
}

export default Pagination;
