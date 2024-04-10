/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayIcon from '@clayui/icon';
import ClayLink from '@clayui/link';
import classNames from 'classnames';
import {postForm, sub} from 'frontend-js-web';
import PropTypes from 'prop-types';
import React, {useContext, useEffect, useState} from 'react';

import FrontendDataSetContext from '../../FrontendDataSetContext';
import {OPEN_SIDE_PANEL} from '../../utils/eventsDefinitions';
import {getOpenedSidePanel} from '../../utils/sidePanels';

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
	selectAllItems,
	selectedItems,
	selectedItemsKey,
	selectedItemsValue,
	total,
}) {
	const {actionParameterName, onBulkActionItemClick} = useContext(
		FrontendDataSetContext
	);

	const [
		currentSidePanelActionPayload,
		setCurrentSidePanelActionPayload,
	] = useState(null);

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
				loadData,
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
						[`${
							actionParameterName || selectedItemsKey
						}`]: selectedItemsValue.join(','),
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

	return selectedItemsValue.length ? (
		<FrontendDataSetContext.Consumer>
			{({formId, formName, loadData, namespace, sidePanelId}) => (
				<nav className="management-bar management-bar-primary navbar navbar-expand-md pb-2 pt-2 subnav-tbar">
					<div
						className={classNames(
							'container-fluid container-fluid-max-xl py-1',
							!fluid && 'px-0'
						)}
					>
						<ul className="navbar-nav">
							<li className="nav-item">
								<span className="text-truncate">
									{sub(
										Liferay.Language.get(
											'x-of-x-items-selected'
										),
										selectedItemsValue.length,
										total
									)}
								</span>

								<ClayLink
									className="ml-3"
									href="#"
									onClick={(event) => {
										event.preventDefault();
										selectAllItems();
									}}
								>
									{Liferay.Language.get('select-all')}
								</ClayLink>
							</li>
						</ul>

						<div className="bulk-actions">
							{bulkActions.map((actionDefinition, i) => (
								<button
									className={classNames(
										'btn btn-monospaced btn-link',
										i > 0 && 'ml-1'
									)}
									key={actionDefinition.label}
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
									type="button"
								>
									<ClayIcon symbol={actionDefinition.icon} />
								</button>
							))}
						</div>
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
	selectedItemsKey: PropTypes.string.isRequired,
	selectedItemsValue: PropTypes.array.isRequired,
	total: PropTypes.number.isRequired,
};

export default BulkActions;
