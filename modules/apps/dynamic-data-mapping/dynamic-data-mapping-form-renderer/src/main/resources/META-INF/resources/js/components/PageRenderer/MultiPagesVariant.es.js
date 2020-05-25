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

import ClayButton, {ClayButtonWithIcon} from '@clayui/button';
import {ClayDropDownWithItems} from '@clayui/drop-down';

import {PAGE_TYPES, usePage} from '../../hooks/usePage.es';

export const Container = ({children, page, pageIndex, pages}) => {
	const {dispatch, store} = usePage();
	const {editingLanguageId} = store;

	const pageSettingsItems = [
		{
			label: Liferay.Language.get('reset-page'),
			onClick: () =>
				dispatch({payload: {pageIndex}, type: PAGE_TYPES.PAGE_RESET}),
		},
		pageIndex > 0
			? {
					label: Liferay.Language.get('remove-page'),
					onClick: () =>
						dispatch({
							payload: pageIndex,
							type: PAGE_TYPES.PAGE_DELETED,
						}),
			  }
			: false,
	].filter(Boolean);

	const onAddSuccessPage = () => {
		const successPageSettings = {
			body: {
				[editingLanguageId]: Liferay.Language.get(
					'your-information-was-successfully-received-thank-you-for-filling-out-the-form'
				),
			},
			enabled: true,
			title: {
				[editingLanguageId]: Liferay.Language.get('thank-you'),
			},
		};

		dispatch({
			payload: successPageSettings,
			type: PAGE_TYPES.SUCCESS_CHANGED,
		});
		dispatch({payload: pages.length, type: PAGE_TYPES.CHANGE_ACTIVE_PAGE});
	};

	return (
		<div className="page">
			<div className="fade sheet show tab-pane" role="tabpanel">
				<div className="form-builder-layout">
					<h5 className="pagination">{page.pagination}</h5>

					{children}
				</div>
			</div>

			<div className="ddm-paginated-builder-dropdown">
				<ClayDropDownWithItems
					items={pageSettingsItems}
					trigger={<ClayButtonWithIcon symbol="ellipsis-v" />}
				/>
			</div>

			<div className="add-page-button-container">
				<div className="horizontal-line" />
				<ClayButton
					className="add-page-button"
					displayType="secondary"
					onClick={() =>
						dispatch({
							payload: {pageIndex},
							type: PAGE_TYPES.PAGE_ADDED,
						})
					}
					small
				>
					{Liferay.Language.get('new-page')}
				</ClayButton>
				<div className="horizontal-line" />
			</div>

			{pages.length - 1 === pageIndex && (
				<div className="add-page-button-container">
					<div className="horizontal-line" />
					<ClayButton
						className="add-page-button"
						displayType="secondary"
						onClick={onAddSuccessPage}
						small
					>
						{Liferay.Language.get('add-success-page')}
					</ClayButton>
					<div className="horizontal-line" />
				</div>
			)}
		</div>
	);
};
