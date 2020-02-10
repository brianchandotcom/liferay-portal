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
import classNames from 'classnames';
import {DragTypes} from 'data-engine-taglib';
import React, {useContext, useLayoutEffect, useRef, useState} from 'react';
import {useDrop} from 'react-dnd';

import Table from '../../components/table/Table.es';
import ColumnOverlay from './ColumnOverlay.es';
import DropZonePlaceholder from './DropZonePlaceholder.es';
import EditTableViewContext from './EditTableViewContext.es';

const generateItems = (columns, rows = 10) => {
	const items = [];

	for (let i = 0; i < rows; i++) {
		items.push(generateItem(columns, i));
	}

	return items;
};

const generateItem = columns =>
	columns.reduce(
		(acc, column) => ({
			...acc,
			[column]: `-`
		}),
		{}
	);

const DropZone = ({fields, onAddFieldName, onRemoveFieldName}) => {
	const [{canDrop, overTarget}, drop] = useDrop({
		accept: DragTypes.DRAG_FIELD_TYPE,
		collect: monitor => ({
			canDrop: monitor.canDrop(),
			overTarget: monitor.isOver()
		}),
		drop: ({data: {name}}) => {
			onAddFieldName(name);
		}
	});

	const [container, setContainer] = useState();
	const containerRef = useRef();
	const empty = fields.length === 0;

	useLayoutEffect(() => {
		if (containerRef.current) {
			setContainer(containerRef.current);
		}
	}, [empty]);

	const [
		{
			dataListView: {appliedFilters}
		}
	] = useContext(EditTableViewContext);

	if (empty) {
		return (
			<div className="p-4 sheet">
				<div className="empty-drop-zone-header"></div>
				<div
					className={classNames('empty-drop-zone', {
						'target-droppable': canDrop,
						'target-over': overTarget
					})}
					ref={drop}
				>
					<p className="m-0">
						{Liferay.Language.get(
							'drag-columns-from-the-sidebar-and-drop-here'
						)}
					</p>
				</div>
			</div>
		);
	}

	return (
		<div ref={containerRef}>
			<Table
				actions={[]}
				checkable={true}
				columns={fields.map(({label, name}) => ({
					key: label,
					value: (
						<div className="container p-0">
							<div className="align-items-center row">
								<div className="autofit-col-expand col">
									{label ? label.en_US : ''}
								</div>
								{Object.prototype.hasOwnProperty.call(
									appliedFilters,
									name
								) && (
									<div className="col text-right">
										<ClayIcon symbol="filter" />
									</div>
								)}
							</div>
						</div>
					)
				}))}
				items={generateItems(
					fields.map(({label}) => (label ? label.en_US : ''))
				)}
				ref={drop}
			/>

			{container && (
				<>
					<DropZonePlaceholder
						container={container}
						fields={fields}
						onAddFieldName={onAddFieldName}
					/>

					<ColumnOverlay
						container={container}
						fields={fields}
						onRemoveFieldName={onRemoveFieldName}
					/>
				</>
			)}
		</div>
	);
};

export default DropZone;
