/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {FDSCellRendererArgs} from '@liferay/js-api/data-set';
import {ClientExtension} from 'frontend-js-components-web';
import {TRenderer, getRenderer} from 'frontend-js-web';
import React, {ComponentType, useContext, useEffect, useState} from 'react';

// @ts-ignore

import FrontendDataSetContext, {
	IFrontendDataSetContext,
} from '../../FrontendDataSetContext';
import {getInternalCellRenderer} from '../../cell_renderers/getInternalCellRenderer';
import {getInputRendererById} from '../../utils/renderer';

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
	const {itemsChanges, updateItem}: IFrontendDataSetContext = useContext(
		FrontendDataSetContext
	);

	const [InputRenderer, setInputRenderer] = useState<ComponentType>(() =>
		getInputRendererById(type)
	);

	useEffect(() => {
		setInputRenderer(() => getInputRendererById(type));
	}, [type]);

	let inputValue = value;

	if (
		itemsChanges?.[itemId] &&
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
				updateItem?.(itemId, rootPropertyName, valuePath, newValue)
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
		customRenderers,
		inlineEditingSettings,
		loadData,
		openSidePanel,
	}: IFrontendDataSetContext = useContext(FrontendDataSetContext);

	const [loading, setLoading] = useState(false);

	const contentRenderer = view.contentRenderer || 'default';

	const [cellRenderer, setCellRenderer] = useState<TRenderer | null>(() => {
		if (view.contentRendererModuleURL) {
			return null;
		}

		const customTableCellRenderer = customRenderers?.tableCell?.find(
			(renderer: TRenderer) => renderer.name === contentRenderer
		);

		if (customTableCellRenderer) {
			return customTableCellRenderer;
		}

		if (customDataRenderers && customDataRenderers[contentRenderer]) {
			return {
				component: customDataRenderers[contentRenderer],
				type: 'internal',
			};
		}

		return getInternalCellRenderer(contentRenderer);
	});

	useEffect(() => {
		if (!loading && view.contentRendererModuleURL && !cellRenderer) {
			setLoading(true);

			getRenderer({
				type: view.contentRendererClientExtension
					? 'clientExtension'
					: 'internal',
				url: view.contentRendererModuleURL,
			})
				.then((renderer: TRenderer) => {
					setCellRenderer(() => renderer);

					setLoading(false);
				})
				.catch((error: string) => {
					console.error(
						`Unable to load FDS cell renderer at ${view.contentRendererModuleURL}:`,
						error
					);

					setCellRenderer(() => getInternalCellRenderer('default'));

					setLoading(false);
				});
		}
	}, [view, loading, cellRenderer]);

	if (
		inlineEditingSettings &&
		(itemInlineChanges || inlineEditingSettings.alwaysOn)
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

	if (!cellRenderer || loading) {
		return (
			<DndTableCell columnName={String(options.fieldName)}>
				<span
					aria-hidden="true"
					className="loading-animation loading-animation-sm"
				/>
			</DndTableCell>
		);
	}

	if (cellRenderer.type === 'clientExtension' && cellRenderer.htmlBuilder) {
		return (
			<DndTableCell columnName={String(options.fieldName)}>
				<ClientExtension<FDSCellRendererArgs>
					args={{value}}
					htmlBuilder={cellRenderer.htmlBuilder}
				/>
			</DndTableCell>
		);
	}

	if (cellRenderer.type === 'internal' && cellRenderer.component) {
		const CellRendererComponent = cellRenderer.component;

		return (
			<DndTableCell columnName={String(options.fieldName)}>
				{CellRendererComponent && (
					<CellRendererComponent
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
				)}
			</DndTableCell>
		);
	}
}

export default TableCell;
