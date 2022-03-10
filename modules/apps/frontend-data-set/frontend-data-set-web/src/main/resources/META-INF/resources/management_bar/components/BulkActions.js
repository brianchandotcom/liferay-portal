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

import ClayIcon from '@clayui/icon';
import ClayLink from '@clayui/link';
import classNames from 'classnames';
import PropTypes from 'prop-types';
import React, {useContext, useEffect, useState} from 'react';

import DataSetContext from '../../DataSetContext';
import {OPEN_SIDE_PANEL} from '../../utils/eventsDefinitions';
import {logError} from '../../utils/logError';
import {getOpenedSidePanel} from '../../utils/sidePanels';

function submit({action, data, formId, formName, formRef, namespace}) {
	let form = formRef.current;

	if (!form && (formId || (formName && namespace))) {
		const namespacedId = formId || `${namespace}${formName}`;
		form = document.getElementById(namespacedId);
	}

	if (form) {
		Liferay.Util.postForm(form, {data, url: action || form.action});
	}
	else {
		logError(`Form not found.`);
	}
}

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
	selectedItemsKey,
	selectedItemsValue,
	total,
}) {
	const {actionParameterName} = useContext(DataSetContext);
	const [
		currentSidePanelActionPayload,
		setCurrentSidePanelActionPayload,
	] = useState(null);

	function handleActionClick(
		actionDefinition,
		formId,
		formName,
		formRef,
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
		else {
			submit({
				action: href,
				data: {
					...data,
					[`${
						actionParameterName || selectedItemsKey
					}`]: selectedItemsValue.join(','),
				},
				formId,
				formName,
				formRef,
				namespace,
			});
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
		<DataSetContext.Consumer>
			{({
				formId,
				formName,
				formRef,
				loadData,
				namespace,
				sidePanelId,
			}) => (
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
									{Liferay.Util.sub(
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
											formRef,
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
		</DataSetContext.Consumer>
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
