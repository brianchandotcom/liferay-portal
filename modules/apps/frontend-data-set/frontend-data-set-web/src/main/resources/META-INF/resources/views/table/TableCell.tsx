/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {FDSTableCellHTMLElementBuilderArgs} from '@liferay/js-api/data-set';
import {ClientExtension} from 'frontend-js-components-web';
import React, {ComponentType, useContext, useEffect, useState} from 'react';

import FrontendDataSetContext, {
	IFrontendDataSetContext,
	TRenderer,
} from '../../FrontendDataSetContext';
import {getInternalCellRenderer} from '../../cell_renderers/getInternalCellRenderer';
import {getInputRendererById} from '../../utils/renderer';
import ViewsContext from '../ViewsContext';
import DndTableCell from './dnd_table/Cell';

export function InlineEditInputRenderer({
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
	const [{modifiedFields}] = useContext(ViewsContext) as any;

	const [cellRenderer, setCellRenderer] = useState<TRenderer | null>(null);

	useEffect(() => {
		if (field.contentRendererClientExtension) {
			const mergedField = {...field, ...modifiedFields[field.fieldName]};

			setCellRenderer({
				htmlElementBuilder: mergedField.htmlElementBuilder,
				type: 'clientExtension',
			});

			return;
		}

		const contentRenderer = field.contentRenderer || 'default';

		const customTableCellRenderer = customRenderers?.tableCell?.find(
			(renderer: TRenderer) => renderer.name === contentRenderer
		);

		if (customTableCellRenderer) {
			setCellRenderer(customTableCellRenderer);

			return;
		}

		if (customDataRenderers && customDataRenderers[contentRenderer]) {
			setCellRenderer({
				component: customDataRenderers[contentRenderer],
				type: 'internal',
			});

			return;
		}

		setCellRenderer(getInternalCellRenderer(contentRenderer));
	}, [customDataRenderers, customRenderers, field, modifiedFields]);

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

	if (cellRenderer?.type === 'clientExtension') {
		return (
			<DndTableCell columnName={String(field.fieldName)}>
				<ClientExtension<FDSTableCellHTMLElementBuilderArgs>
					args={{value}}
					htmlElementBuilder={cellRenderer.htmlElementBuilder}
				/>
			</DndTableCell>
		);
	}

	if (cellRenderer?.type === 'internal' && cellRenderer.component) {
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

	return null;
}

export default TableCell;
