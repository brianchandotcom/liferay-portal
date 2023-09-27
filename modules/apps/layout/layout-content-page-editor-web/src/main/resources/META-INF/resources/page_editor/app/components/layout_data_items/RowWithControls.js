/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import classNames from 'classnames';
import PropTypes from 'prop-types';
import React, {useState} from 'react';

import useSetRef from '../../../common/hooks/useSetRef';
import {getLayoutDataItemPropTypes} from '../../../prop_types/index';
import {ResizeContextProvider} from '../../contexts/ResizeContext';
import {useSelector} from '../../contexts/StoreContext';
import selectCanUpdateItemConfiguration from '../../selectors/selectCanUpdateItemConfiguration';
import getLayoutDataItemTopperUniqueClassName from '../../utils/getLayoutDataItemTopperUniqueClassName';
import {getResponsiveColumnSize} from '../../utils/getResponsiveColumnSize';
import {getResponsiveConfig} from '../../utils/getResponsiveConfig';
import isItemEmpty from '../../utils/isItemEmpty';
import Topper from '../topper/Topper';
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

	const {height} = rowResponsiveConfig.styles;

	return (
		<Topper
			className={getLayoutDataItemTopperUniqueClassName(item.itemId)}
			item={item}
			itemElement={itemElement}
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
