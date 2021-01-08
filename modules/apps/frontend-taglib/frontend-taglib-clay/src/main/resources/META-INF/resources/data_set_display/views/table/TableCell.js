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
import CommentRenderer from '../../data_renderers/CommentRenderer';
import DefaultRenderer from '../../data_renderers/DefaultRenderer';
import InputCheckboxRenderer from '../../data_renderers/InputCheckboxRenderer';
import InputDateRenderer from '../../data_renderers/InputDateTimeRenderer';
import InputTextRenderer from '../../data_renderers/InputTextRenderer';
import {
	getDataRendererById,
	getDataRendererByURL,
} from '../../utilities/dataRenderers';

function InlineEditInputRenderer({type, value, ...otherProps}) {
	const {modifiedItems, updateItem} = useContext(DataSetDisplayContext);
	let InputRenderer;

	switch (type) {
		case 'datetime':
			InputRenderer = InputDateRenderer;
			break;
		case 'checkbox':
			InputRenderer = InputCheckboxRenderer;
			break;
		default:
			InputRenderer = InputTextRenderer;
	}

	let inputValue = value;

	const formattedFieldName = Array.isArray(otherProps.options.fieldName)
		? otherProps.options.fieldName[0]
		: otherProps.options.fieldName;

	if (
		modifiedItems[otherProps.itemId] &&
		modifiedItems[otherProps.itemId][formattedFieldName]
	) {
		inputValue = modifiedItems[otherProps.itemId][formattedFieldName];
	}

	return (
		<InputRenderer
			{...otherProps}
			updateItem={(newValue) =>
				updateItem(
					otherProps.itemId,
					otherProps.options.fieldName,
					newValue
				)
			}
			value={inputValue}
		/>
	);
}

function TableCell({
	actions,
	comment,
	inlineEditSettings,
	itemData,
	itemId,
	itemInlineChanges,
	options,
	value,
	view,
}) {
	let dataRenderer = DefaultRenderer;

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
		if (loading) {
			return;
		}
		if (currentView.contentRendererModuleURL) {
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

	if (itemInlineChanges && inlineEditSettings) {
		return (
			<ClayTable.Cell>
				<InlineEditInputRenderer
					actions={actions}
					itemData={itemData}
					itemId={itemId}
					options={options}
					type={inlineEditSettings.type}
					value={value}
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
					value={value}
				/>
			) : (
				<span
					aria-hidden="true"
					className="loading-animation loading-animation-sm"
				/>
			)}
			{comment && <CommentRenderer>{comment}</CommentRenderer>}
		</ClayTable.Cell>
	);
}

export default TableCell;
