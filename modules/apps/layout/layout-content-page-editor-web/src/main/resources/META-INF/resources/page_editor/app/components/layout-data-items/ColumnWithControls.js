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

import {useEventListener} from '@liferay/frontend-js-react-web';
import classNames from 'classnames';
import React, {useMemo, useState} from 'react';

import {getLayoutDataItemPropTypes} from '../../../prop-types/index';
import {useIsActive} from '../../contexts/ControlsContext';
import {useGlobalContext} from '../../contexts/GlobalContext';
import {useResizing, useSetResizing} from '../../contexts/ResizeContext';
import {useSelector} from '../../contexts/StoreContext';
import selectCanUpdateItemConfiguration from '../../selectors/selectCanUpdateItemConfiguration';
import selectCanUpdatePageStructure from '../../selectors/selectCanUpdatePageStructure';
import {NotDraggableArea} from '../../utils/drag-and-drop/useDragAndDrop';
import {getResponsiveColumnSize} from '../../utils/getResponsiveColumnSize';
import {getResponsiveConfig} from '../../utils/getResponsiveConfig';
import isItemEmpty from '../../utils/isItemEmpty';
import TopperEmpty from '../TopperEmpty';
import Column from './Column';

const ROW_SIZE = 12;

const ColumnWithControls = React.forwardRef(({children, item}, ref) => {
	const canUpdateItemConfiguration = useSelector(
		selectCanUpdateItemConfiguration
	);
	const layoutData = useSelector((state) => state.layoutData);
	const parentItem = layoutData.items[item.parentId];
	const canUpdatePageStructure = useSelector(selectCanUpdatePageStructure);
	const [selectedColumn, setColumnSelected] = useState(null);
	const selectedViewportSize = useSelector(
		(state) => state.selectedViewportSize
	);

	const globalContext = useGlobalContext();
	const isActive = useIsActive();
	const resizing = useResizing();
	const setResizing = useSetResizing();

	const columnIndex = parentItem.children.indexOf(item.itemId);

	const parentItemIsActive = useMemo(
		() =>
			layoutData.items[item.parentId] ? isActive(item.parentId) : false,
		[isActive, item.parentId, layoutData.items]
	);

	const responsiveRowConfig = getResponsiveConfig(
		parentItem.config,
		selectedViewportSize
	);

	const isReverseOrder =
		responsiveRowConfig.reverseOrder &&
		parentItem.config.numberOfColumns === 2 &&
		responsiveRowConfig.modulesPerRow === 1;

	const isFirstColumnOfRow = columnRangeIsComplete(
		parentItem.children.slice(0, columnIndex),
		layoutData,
		selectedViewportSize
	);

	const handleMouseDown = () => {
		setColumnSelected(item);
		setResizing(true);
	};

	useEventListener(
		'mouseup',
		() => {
			setColumnSelected(null);
			setResizing(false);
		},
		false,
		globalContext.document.body
	);

	return (
		<TopperEmpty item={item}>
			<Column
				className={classNames('page-editor__col', {
					empty: isItemEmpty(item, layoutData, selectedViewportSize),
					'page-editor__row-overlay-grid__border':
						selectedColumn?.itemId === item.itemId &&
						!isFirstColumnOfRow &&
						resizing,
				})}
				item={item}
				ref={ref}
			>
				{(canUpdatePageStructure || canUpdateItemConfiguration) &&
					parentItemIsActive &&
					columnIndex !== 0 &&
					!isReverseOrder && (
						<NotDraggableArea>
							<button
								className={classNames(
									'btn-primary page-editor__col__resizer',
									{
										'page-editor__col__resizer-first': isFirstColumnOfRow,
									}
								)}
								onMouseDown={handleMouseDown}
								title={Liferay.Language.get('resize-column')}
								type="button"
							/>
						</NotDraggableArea>
					)}

				{children}
			</Column>
		</TopperEmpty>
	);
});

ColumnWithControls.propTypes = {
	item: getLayoutDataItemPropTypes().isRequired,
};

export default ColumnWithControls;

function columnRangeIsComplete(columnRange, layoutData, selectedViewportSize) {
	const sum = columnRange
		.map((columnId) =>
			getResponsiveColumnSize(
				layoutData.items[columnId].config,
				selectedViewportSize
			)
		)
		.reduce((acc, value) => {
			return acc + value;
		}, 0);

	return sum % ROW_SIZE === 0;
}
