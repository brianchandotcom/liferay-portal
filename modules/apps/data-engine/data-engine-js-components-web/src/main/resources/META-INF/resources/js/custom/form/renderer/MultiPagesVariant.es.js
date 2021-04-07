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
import ClayLayout from '@clayui/layout';
import {sub} from 'dynamic-data-mapping-form-field-type/util/strings.es';
import React from 'react';

import {EVENT_TYPES as CORE_EVENT_TYPES} from '../../../core/actions/eventTypes.es';
import {useForm, useFormState} from '../../../core/hooks/useForm.es';
import {usePage} from '../../../core/hooks/usePage.es';
import {EVENT_TYPES} from '../eventTypes.es';

export const Container = ({children, empty, pageIndex, pages}) => {
	const {editingLanguageId, successPageSettings} = useFormState();
	const dispatch = useForm();

	const pageSettingsItems = [
		empty
			? {
					className: 'ddm-btn-disabled',
					disabled: true,
					label: Liferay.Language.get('reset-page'),
			  }
			: {
					label: Liferay.Language.get('reset-page'),
					onClick: () =>
						dispatch({
							payload: {pageIndex},
							type: EVENT_TYPES.PAGE.RESET,
						}),
			  },
		pageIndex > 0
			? {
					label: Liferay.Language.get('remove-page'),
					onClick: () =>
						dispatch({
							payload: {pageIndex},
							type: EVENT_TYPES.PAGE.DELETE,
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
			type: EVENT_TYPES.SUCCESS_PAGE,
		});
		dispatch({
			payload: {activePage: pages.length},
			type: CORE_EVENT_TYPES.PAGE.CHANGE,
		});
	};

	return (
		<>
			{pageIndex === 0 && <div className="horizontal-line" />}

			<div className="page">
				<ClayLayout.Sheet
					className="fade show tab-pane"
					role="tabpanel"
				>
					<div className="form-builder-layout">
						<h5 className="pagination">
							{sub(Liferay.Language.get('page-x-of-x'), [
								pageIndex + 1,
								pages[pages.length - 1].contentRenderer ===
								'success'
									? pages.length - 1
									: pages.length,
							])}
						</h5>

						{children}
					</div>
				</ClayLayout.Sheet>

				<div className="ddm-paginated-builder-reorder">
					<ClayButtonWithIcon
						className="reorder-page-button"
						disabled={pageIndex === 0}
						displayType="secondary"
						onClick={() =>
							dispatch({
								payload: {
									firstIndex: pageIndex,
									secondIndex: pageIndex - 1,
								},
								type: EVENT_TYPES.PAGE.SWAP,
							})
						}
						small
						symbol="angle-up"
						title={Liferay.Language.get('move-page-up')}
					/>

					<ClayButtonWithIcon
						className="reorder-page-button"
						disabled={
							pageIndex ===
							pages.length -
								(successPageSettings?.enabled ? 2 : 1)
						}
						displayType="secondary"
						onClick={() =>
							dispatch({
								payload: {
									firstIndex: pageIndex,
									secondIndex: pageIndex + 1,
								},
								type: EVENT_TYPES.PAGE.SWAP,
							})
						}
						small
						symbol="angle-down"
						title={Liferay.Language.get('move-page-down')}
					/>
				</div>

				<div className="ddm-paginated-builder-dropdown">
					<ClayDropDownWithItems
						className="dropdown-action"
						items={pageSettingsItems}
						trigger={
							<ClayButtonWithIcon
								displayType="unstyled"
								symbol="ellipsis-v"
								title={Liferay.Language.get('page-options')}
							/>
						}
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
								type: EVENT_TYPES.PAGE.ADD,
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
		</>
	);
};

Container.displayName = 'MultiPagesVariant.Container';

export const PageHeader = ({localizedDescription, localizedTitle}) => {
	const {defaultLanguageId, editingLanguageId} = useFormState();
	const {pageIndex} = usePage();

	const dispatch = useForm();

	return (
		<div>
			<input
				className="form-builder-page-header-title form-control p-0"
				maxLength="120"
				onChange={(event) =>
					dispatch({
						payload: {pageIndex, value: event.target.value},
						type: EVENT_TYPES.PAGE.TITLE_CHANGE,
					})
				}
				placeholder={Liferay.Language.get('page-title')}
				value={
					localizedTitle[editingLanguageId] ??
					localizedTitle[defaultLanguageId]
				}
			/>
			<input
				className="form-builder-page-header-description form-control p-0"
				maxLength="120"
				onChange={(event) =>
					dispatch({
						payload: {pageIndex, value: event.target.value},
						type: EVENT_TYPES.PAGE.DESCRIPTION_CHANGE,
					})
				}
				placeholder={Liferay.Language.get(
					'add-a-short-description-for-this-page'
				)}
				value={
					localizedDescription[editingLanguageId] ??
					localizedDescription[defaultLanguageId]
				}
			/>
		</div>
	);
};

PageHeader.displayName = 'MultiPagesVariant.PageHeader';
