/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton, {ClayButtonWithIcon} from '@clayui/button';
import DropDown from '@clayui/drop-down';
import ClayIcon from '@clayui/icon';
import ClayLink from '@clayui/link';
import classNames from 'classnames';
import {postForm, sub} from 'frontend-js-web';
import PropTypes from 'prop-types';
import React, {useContext, useEffect, useState} from 'react';

import FrontendDataSetContext from '../../FrontendDataSetContext';
import {OPEN_SIDE_PANEL} from '../../utils/eventsDefinitions';
import {getOpenedSidePanel} from '../../utils/sidePanels';
import SelectionCheckbox from './SelectionCheckbox';

function getQueryString(key, values = []) {
	return `?${key}=${values.join(',')}`;
}

function getRichPayload(payload, key, values = []) {
	const richPayload = {
		...payload,
		url: payload.baseURL + getQueryString(key, values),
	};

	return richPayload;
}

function BulkActions({
	bulkActions,
	fluid,
	handleCheckboxClick,
	items,
	onClear,
	pageSelectedItemsValue,
	selectItems,
	selectedItems,
	selectedItemsKey,
	selectedItemsValue,
	total,
}) {
	const {
		actionParameterName,
		onBulkActionItemClick,
		showBulkActionsManagementBar,
		showBulkActionsManagementBarActions,
	} = useContext(FrontendDataSetContext);

	const [currentSidePanelActionPayload, setCurrentSidePanelActionPayload] =
		useState(null);

	const highlightedBulkActions = bulkActions.filter(
		(bulkAction) => bulkAction.data.highlighted
	);

	function handleActionClick(
		actionDefinition,
		formId,
		formName,
		loadData,
		namespace,
		sidePanelId
	) {
		const {data, href, slug, target} = actionDefinition;

		if (target === 'sidePanel') {
			const sidePanelActionPayload = {
				baseURL: href,
				id: sidePanelId,
				onAfterSubmit: () => loadData(),
				slug: slug ?? null,
			};

			Liferay.fire(
				OPEN_SIDE_PANEL,
				getRichPayload(
					sidePanelActionPayload,
					selectedItemsKey,
					selectedItemsValue
				)
			);

			setCurrentSidePanelActionPayload(sidePanelActionPayload);
		}
		else if (onBulkActionItemClick) {
			onBulkActionItemClick({
				action: actionDefinition,
				formId,
				formName,
				loadData,
				namespace,
				selectedData: {
					items: selectedItems,
					keyValues: selectedItemsValue,
				},
			});
		}
		else if (formId || (formName && namespace)) {
			const namespacedId = formId || `${namespace}${formName}`;

			const form = document.getElementById(namespacedId);

			if (form) {
				postForm(form, {
					data: {
						...data,
						[`${actionParameterName || selectedItemsKey}`]:
							selectedItemsValue.join(','),
					},
					url: href || form.action,
				});
			}
		}
	}

	useEffect(
		() => {
			if (!currentSidePanelActionPayload) {
				return;
			}

			const currentOpenedSidePanel = getOpenedSidePanel();

			if (
				currentOpenedSidePanel?.id ===
					currentSidePanelActionPayload.id &&
				currentOpenedSidePanel.url.indexOf(
					currentSidePanelActionPayload.baseURL
				) > -1
			) {
				Liferay.fire(
					OPEN_SIDE_PANEL,
					getRichPayload(
						currentSidePanelActionPayload,
						selectedItemsValue
					)
				);
			}
		},

		// eslint-disable-next-line react-hooks/exhaustive-deps
		[selectedItemsValue]
	);

	return showBulkActionsManagementBar && selectedItemsValue.length ? (
		<FrontendDataSetContext.Consumer>
			{({
				formId,
				formName,
				loadData,
				namespace,
				selectable,
				sidePanelId,
			}) => (
				<nav className="management-bar management-bar-primary navbar navbar-expand-md">
					<div
						className={classNames(
							'container-fluid',
							!fluid && 'px-0'
						)}
					>
						<ul className="navbar-nav">
							{!!total && selectable && (
								<li className="nav-item">
									<SelectionCheckbox
										handleCheckboxClick={
											handleCheckboxClick
										}
										items={items}
										selectedItemsValue={
											pageSelectedItemsValue
										}
									/>
								</li>
							)}

							<li className="nav-item">
								<span className="text-truncate">
									{selectedItemsValue.length === total
										? sub(
												Liferay.Language.get(
													'all-selected-x-of-x-items'
												),
												selectedItemsValue.length,
												total
											)
										: sub(
												Liferay.Language.get(
													'x-of-x-items-selected'
												),
												selectedItemsValue.length,
												total
											)}
								</span>

								{Liferay.FeatureFlags['LPD-42570'] && (
									<ClayLink
										className="ml-3"
										href="#"
										onClick={(event) => {
											event.preventDefault();
											onClear();
										}}
									>
										{Liferay.Language.get('clear')}
									</ClayLink>
								)}

								<ClayLink
									className="ml-3"
									href="#"
									onClick={(event) => {
										event.preventDefault();
										selectItems(
											items.map(
												(item) => item[selectedItemsKey]
											)
										);
									}}
								>
									{Liferay.Language.get('select-all')}
								</ClayLink>
							</li>
						</ul>

						{showBulkActionsManagementBarActions && (
							<ul className="bulk-actions navbar-nav">
								{highlightedBulkActions.map(
									(highlightedBulkAction) => {
										return (
											<li
												className="nav-item"
												key={
													highlightedBulkAction.data
														.id
												}
											>
												<ClayButton
													className="nav-link"
													displayType="unstyled"
													onClick={() =>
														handleActionClick(
															highlightedBulkAction,
															formId,
															formName,
															loadData,
															namespace,
															sidePanelId
														)
													}
												>
													<span className="inline-item inline-item-before">
														<ClayIcon
															symbol={
																highlightedBulkAction.icon
															}
														/>
													</span>

													{
														highlightedBulkAction.label
													}
												</ClayButton>
											</li>
										);
									}
								)}

								{!!bulkActions.length && (
									<li className="nav-item">
										<DropDown
											hasLeftSymbols
											trigger={
												<ClayButtonWithIcon
													aria-label={Liferay.Language.get(
														'actions'
													)}
													className="nav-link nav-link-monospaced"
													displayType="unstyled"
													symbol="ellipsis-v"
													title={Liferay.Language.get(
														'actions'
													)}
												/>
											}
										>
											<DropDown.ItemList>
												{bulkActions.map(
													(actionDefinition) => (
														<DropDown.Item
															key={
																actionDefinition.label
															}
															onClick={() =>
																handleActionClick(
																	actionDefinition,
																	formId,
																	formName,
																	loadData,
																	namespace,
																	sidePanelId
																)
															}
															symbolLeft={
																actionDefinition.icon
															}
														>
															{
																actionDefinition.label
															}
														</DropDown.Item>
													)
												)}
											</DropDown.ItemList>
										</DropDown>
									</li>
								)}
							</ul>
						)}
					</div>
				</nav>
			)}
		</FrontendDataSetContext.Consumer>
	) : null;
}

BulkActions.propTypes = {
	bulkActions: PropTypes.arrayOf(
		PropTypes.shape({
			href: PropTypes.string.isRequired,
			icon: PropTypes.string.isRequired,
			label: PropTypes.string.isRequired,
			method: PropTypes.string,
			target: PropTypes.oneOf(['sidePanel', 'modal']),
		})
	),
	handleCheckboxClick: PropTypes.func.isRequired,
	items: PropTypes.array.isRequired,
	onClear: PropTypes.func.isRequired,
	selectedItemsKey: PropTypes.string.isRequired,
	selectedItemsValue: PropTypes.array.isRequired,
	total: PropTypes.number,
};

export default BulkActions;
