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

import React, {useContext, useEffect, useState} from 'react';

// @ts-ignore

import FrontendDataSetContext from '../../FrontendDataSetContext';

// @ts-ignore

import DefaultRenderer from '../../data_renderers/DefaultRenderer';
import {
	AnyDataRenderer,
	getDataRendererById,
	getDataRendererByURL,
	getInputRendererById,
} from '../../utils/dataRenderers';

// @ts-ignore

import DndTableCell from './dnd_table/Cell';

function InlineEditInputRenderer({
	itemId,
	options,
	rootPropertyName,
	type,
	value,
	valuePath,
	...otherProps
}: any) {
	const {itemsChanges, updateItem} = useContext(
		FrontendDataSetContext as React.Context<any>
	);

	const [InputRenderer, setInputRenderer] = useState(() =>
		getInputRendererById(type)
	);

	useEffect(() => {
		setInputRenderer(() => getInputRendererById(type));
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
			updateItem={(newValue: any) =>
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
}: any) {
	const {
		customDataRenderers,
		inlineEditingSettings,
		loadData,
		openSidePanel,
	} = useContext(FrontendDataSetContext as React.Context<any>);

	const [loading, setLoading] = useState(false);

	const contentRenderer = view.contentRenderer;

	let SyncDataRenderer = DefaultRenderer;

	if (contentRenderer) {
		if (customDataRenderers && customDataRenderers[contentRenderer]) {
			SyncDataRenderer = customDataRenderers[contentRenderer];
		}
		else {
			SyncDataRenderer = getDataRendererById(contentRenderer);
		}
	}

	if (view.contentRendererModuleURL) {
		SyncDataRenderer = null;
	}

	const [dataRenderer, setDataRenderer] = useState<AnyDataRenderer | null>(
		() => SyncDataRenderer
	);

	useEffect(() => {
		if (!loading && view.contentRendererModuleURL && !dataRenderer) {
			setLoading(true);

			getDataRendererByURL(view.contentRendererModuleURL)
				.then((dataRenderer) => {
					setDataRenderer(() => dataRenderer);

					setLoading(false);
				})
				.catch(() => {
					setDataRenderer(() => null);

					setLoading(false);
				});
		}
	}, [view, loading, dataRenderer]);

	if (
		inlineEditSettings &&
		(itemInlineChanges || inlineEditingSettings?.alwaysOn)
	) {
		return (
			<DndTableCell columnName={String(options.fieldName)}>
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
			</DndTableCell>
		);
	}

	if (!dataRenderer || loading) {
		return (
			<DndTableCell columnName={String(options.fieldName)}>
				<span
					aria-hidden="true"
					className="loading-animation loading-animation-sm"
				/>
			</DndTableCell>
		);
	}

	if (dataRenderer.type === 'internal') {
		return (
			<DndTableCell columnName={String(options.fieldName)}>
				<dataRenderer.Component
					actions={actions}
					itemData={itemData}
					itemId={itemId}
					loadData={loadData}
					openSidePanel={openSidePanel}
					options={options}
					rootPropertyName={rootPropertyName}
					value={value}
					valuePath={valuePath}
				/>
			</DndTableCell>
		);
	}

	return <></>;
}

export default TableCell;
