/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {FDSTableCellHTMLElementBuilderArgs} from '@liferay/js-api/data-set';
import {ClientExtension} from 'frontend-js-components-web';
import {TRenderer, getRenderer} from 'frontend-js-web';
import React, {ComponentType, useContext, useEffect, useState} from 'react';

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
	field,
	itemData,
	itemId,
	itemInlineChanges,
	rootPropertyName,
	value,
	valuePath,
}: any) {
	const {
		customDataRenderers,
		customRenderers,
		inlineEditingSettings,
		loadData,
		openSidePanel,
	}: IFrontendDataSetContext = useContext(FrontendDataSetContext);

	const [loading, setLoading] = useState(false);

	const contentRenderer = field.contentRenderer || 'default';

	const [cellRenderer, setCellRenderer] = useState<TRenderer | null>(() => {
		if (field.contentRendererModuleURL) {
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
		if (!loading && field.contentRendererModuleURL && !cellRenderer) {
			setLoading(true);

			getRenderer({
				type: field.contentRendererClientExtension
					? 'clientExtension'
					: 'internal',
				url: field.contentRendererModuleURL,
			})
				.then((renderer: TRenderer) => {
					setCellRenderer(() => renderer);

					setLoading(false);
				})
				.catch((error: string) => {
					console.error(
						`Unable to load FDS cell renderer at ${field.contentRendererModuleURL}:`,
						error
					);

					setCellRenderer(() => getInternalCellRenderer('default'));

					setLoading(false);
				});
		}
	}, [field, loading, cellRenderer]);

	if (
		inlineEditingSettings &&
		(itemInlineChanges || inlineEditingSettings.alwaysOn)
	) {
		return (
			<DndTableCell columnName={String(field.fieldName)}>
				<InlineEditInputRenderer
					actions={actions}
					itemData={itemData}
					itemId={itemId}
					options={field}
					rootPropertyName={rootPropertyName}
					type={field.inlineEditSettings.type}
					value={value}
					valuePath={valuePath}
				/>
			</DndTableCell>
		);
	}

	if (!cellRenderer || loading) {
		return (
			<DndTableCell columnName={String(field.fieldName)}>
				<span
					aria-hidden="true"
					className="loading-animation loading-animation-sm"
				/>
			</DndTableCell>
		);
	}

	if (
		cellRenderer.type === 'clientExtension' &&
		cellRenderer.htmlElementBuilder
	) {
		return (
			<DndTableCell columnName={String(field.fieldName)}>
				<ClientExtension<FDSTableCellHTMLElementBuilderArgs>
					args={{value}}
					htmlElementBuilder={cellRenderer.htmlElementBuilder}
				/>
			</DndTableCell>
		);
	}

	if (cellRenderer.type === 'internal' && cellRenderer.component) {
		const CellRendererComponent = cellRenderer.component;

		return (
			<DndTableCell columnName={String(field.fieldName)}>
				{CellRendererComponent && (
					<CellRendererComponent
						actions={actions}
						itemData={itemData}
						itemId={itemId}
						loadData={loadData}
						openSidePanel={openSidePanel}
						options={field}
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
