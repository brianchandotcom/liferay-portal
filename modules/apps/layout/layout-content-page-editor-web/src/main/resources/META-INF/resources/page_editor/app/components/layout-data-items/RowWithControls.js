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

import classNames from 'classnames';
import PropTypes from 'prop-types';
import React, {useState} from 'react';

import useSetRef from '../../../core/hooks/useSetRef';
import {getLayoutDataItemPropTypes} from '../../../prop-types/index';
import {ResizeContextProvider} from '../../contexts/ResizeContext';
import {useSelector} from '../../contexts/StoreContext';
import selectCanUpdateItemConfiguration from '../../selectors/selectCanUpdateItemConfiguration';
import {getResponsiveColumnSize} from '../../utils/getResponsiveColumnSize';
import {getResponsiveConfig} from '../../utils/getResponsiveConfig';
import isItemEmpty from '../../utils/isItemEmpty';
import {isValidSpacingOption} from '../../utils/isValidSpacingOption';
import Topper from '../Topper';
import Row from './Row';

const ROW_SIZE = 12;

const RowWithControls = React.forwardRef(({children, item}, ref) => {
	const [resizing, setResizing] = useState(false);
	const [nextColumnSizes, setNextColumnSizes] = useState(null);

	const canUpdateItemConfiguration = useSelector(
		selectCanUpdateItemConfiguration
	);

	const layoutData = useSelector((state) => state.layoutData);

	const selectedViewportSize = useSelector(
		(state) => state.selectedViewportSize
	);

	const rowResponsiveConfig = getResponsiveConfig(
		item.config,
		selectedViewportSize
	);

	const [setRef, itemElement] = useSetRef(ref);
	const {verticalAlignment} = rowResponsiveConfig;

	const {
		display,
		height,
		marginBottom,
		marginLeft,
		marginRight,
		marginTop,
		maxWidth,
		minWidth,
		width,
	} = rowResponsiveConfig.styles;

	return (
		<Topper
			className={classNames({
				[`mb-${marginBottom}`]: isValidSpacingOption(marginBottom),
				[`ml-${marginLeft}`]: isValidSpacingOption(marginLeft),
				[`mr-${marginRight}`]: isValidSpacingOption(marginRight),
				[`mt-${marginTop}`]: isValidSpacingOption(marginTop),
			})}
			item={item}
			itemElement={itemElement}
			style={{
				display,
				maxWidth,
				minWidth,
				width,
			}}
		>
			<Row
				className={classNames({
					'align-bottom': verticalAlignment === 'bottom',
					'align-middle': verticalAlignment === 'middle',
					'empty':
						isSomeRowEmpty(
							item,
							layoutData,
							selectedViewportSize
						) && !height,
					'page-editor__row': canUpdateItemConfiguration,
					'page-editor__row-overlay-grid': resizing,
				})}
				item={item}
				ref={setRef}
			>
				<ResizeContextProvider
					value={{
						nextColumnSizes,
						resizing,
						setNextColumnSizes,
						setResizing,
					}}
				>
					{children}
				</ResizeContextProvider>
			</Row>
		</Topper>
	);
});

/**
 * Group children item by row and then check that if some row is empty
 */
function isSomeRowEmpty(item, layoutData, selectedViewportSize) {
	const rows = groupItemsByRow(item, layoutData, selectedViewportSize);

	return rows.some((row) =>
		row.every((column) =>
			isItemEmpty(column, layoutData, selectedViewportSize)
		)
	);
}

function groupItemsByRow(item, layoutData, selectedViewportSize) {
	const rows = [];
	let row = [];
	let columnSum = 0;

	item.children.forEach((childId) => {
		const child = layoutData.items[childId];

		const columnSize = getResponsiveColumnSize(
			child.config,
			selectedViewportSize
		);

		columnSum = columnSum + columnSize;

		row.push(child);

		if (columnSum === ROW_SIZE) {
			rows.push(row);
			row = [];
			columnSum = 0;
		}
	});

	return rows;
}

RowWithControls.propTypes = {
	item: getLayoutDataItemPropTypes({
		config: PropTypes.shape({gutters: PropTypes.bool}),
	}).isRequired,
};

export default RowWithControls;
