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

import {FDSCellRenderer, FDSCellRendererArgs} from '@liferay/js-api/data-set';
import React, {
	ComponentType,
	RefObject,
	useContext,
	useEffect,
	useState,
} from 'react';

// @ts-ignore

import FrontendDataSetContext from '../../FrontendDataSetContext';
import {getInternalCellRenderer} from '../../cell_renderers/getInternalCellRenderer';
import {
	CellRenderer,
	getCellRendererByURL,
	getInputRendererById,
} from '../../utils/renderer';

// @ts-ignore

import DndTableCell from './dnd_table/Cell';

interface ClientExtensionRendererComponentProps {
	args: FDSCellRendererArgs;
	renderer: FDSCellRenderer;
}

class ClientExtensionRendererComponent extends React.Component<
	ClientExtensionRendererComponentProps
> {
	private readonly ref: RefObject<HTMLDivElement>;

	constructor(props: ClientExtensionRendererComponentProps) {
		super(props);

		this.ref = React.createRef();
	}

	componentDidMount() {
		if (!this.ref.current) {
			return;
		}

		this.ref.current.appendChild(this.props.renderer(this.props.args));
	}

	render() {
		return <div ref={this.ref}></div>;
	}
}

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

	const [InputRenderer, setInputRenderer] = useState<ComponentType>(() =>
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

	const contentRenderer = view.contentRenderer || 'default';

	const [cellRenderer, setCellRenderer] = useState<CellRenderer | null>(
		() => {
			if (view.contentRendererModuleURL) {
				return null;
			}

			if (customDataRenderers && customDataRenderers[contentRenderer]) {
				return {
					component: customDataRenderers[contentRenderer],
					type: 'internal',
				};
			}

			return getInternalCellRenderer(contentRenderer);
		}
	);

	useEffect(() => {
		if (!loading && view.contentRendererModuleURL && !cellRenderer) {
			setLoading(true);

			getCellRendererByURL(
				view.contentRendererModuleURL,
				view.contentRendererClientExtension
					? 'clientExtension'
					: 'internal'
			)
				.then((cellRenderer) => {
					setCellRenderer(() => cellRenderer);

					setLoading(false);
				})
				.catch((error) => {
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

	if (cellRenderer.type === 'clientExtension') {
		return (
			<DndTableCell columnName={String(options.fieldName)}>
				<ClientExtensionRendererComponent
					args={{value}}
					renderer={cellRenderer.renderer}
				/>
			</DndTableCell>
		);
	}

	const CellRendererComponent = cellRenderer.component;

	return (
		<DndTableCell columnName={String(options.fieldName)}>
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
		</DndTableCell>
	);
}

export default TableCell;
