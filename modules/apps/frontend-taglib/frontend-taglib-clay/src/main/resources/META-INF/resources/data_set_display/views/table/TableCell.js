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

import ClayTable from '@clayui/table';
import React, {useContext, useEffect, useState} from 'react';

import DataSetDisplayContext from '../../DataSetDisplayContext';
import DefaultRenderer from '../../data_renderers/DefaultRenderer';
import {
	getDataRendererById,
	getDataRendererByURL,
	getInputRendererById,
} from '../../utils/dataRenderers';

function InlineEditInputRenderer({
	itemId,
	options,
	rootPropertyName,
	type,
	value,
	valuePath,
	...otherProps
}) {
	const [InputRenderer, updateInputRenderer] = useState(() =>
		getInputRendererById(type)
	);
	const {itemsChanges, updateItem} = useContext(DataSetDisplayContext);

	useEffect(() => {
		updateInputRenderer(() => getInputRendererById(type));
	}, [type]);

	let inputValue = value;

	if (
		itemsChanges[itemId] &&
		typeof itemsChanges[itemId][rootPropertyName] !== 'undefined'
	) {
		inputValue = itemsChanges[itemId][rootPropertyName].value;
	}

	return (
		<InputRenderer
			{...otherProps}
			itemId={itemId}
			options={options}
			updateItem={(newValue) =>
				updateItem(itemId, rootPropertyName, valuePath, newValue)
			}
			value={inputValue}
		/>
	);
}

function TableCell({
	actions,
	inlineEditSettings,
	itemData,
	itemId,
	itemInlineChanges,
	options,
	rootPropertyName,
	value,
	valuePath,
	view,
}) {
	let dataRenderer = DefaultRenderer;

	const {inlineEditingSettings} = useContext(DataSetDisplayContext);

	if (view.contentRenderer) {
		dataRenderer = getDataRendererById(view.contentRenderer);
	}

	if (view.contentRendererModuleURL) {
		dataRenderer = null;
	}

	const [currentView, updateCurrentView] = useState({
		...view,
		Component: dataRenderer,
	});

	const [loading, setLoading] = useState(false);

	useEffect(() => {
		if (!loading && currentView.contentRendererModuleURL) {
			setLoading(true);
			getDataRendererByURL(currentView.contentRendererModuleURL).then(
				(Component) => {
					updateCurrentView({
						...currentView,
						Component,
					});
					setLoading(false);
				}
			);
		}
	}, [currentView, loading]);

	if (
		inlineEditSettings &&
		(itemInlineChanges || inlineEditingSettings?.alwaysOn)
	) {
		return (
			<ClayTable.Cell>
				<InlineEditInputRenderer
					actions={actions}
					itemData={itemData}
					itemId={itemId}
					options={options}
					rootPropertyName={rootPropertyName}
					type={inlineEditSettings.type}
					value={value}
					valuePath={valuePath}
				/>
			</ClayTable.Cell>
		);
	}

	return (
		<ClayTable.Cell>
			{currentView.Component && !loading ? (
				<currentView.Component
					actions={actions}
					itemData={itemData}
					itemId={itemId}
					options={options}
					rootPropertyName={rootPropertyName}
					value={value}
					valuePath={valuePath}
				/>
			) : (
				<span
					aria-hidden="true"
					className="loading-animation loading-animation-sm"
				/>
			)}
		</ClayTable.Cell>
	);
}

export default TableCell;
