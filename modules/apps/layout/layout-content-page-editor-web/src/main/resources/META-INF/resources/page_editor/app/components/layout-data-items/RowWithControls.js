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
import selectCanUpdateItemConfiguration from '../../selectors/selectCanUpdateItemConfiguration';
import {useSelector} from '../../store/index';
import {getResponsiveConfig} from '../../utils/getResponsiveConfig';
import {ResizeContextProvider} from '../ResizeContext';
import Topper from '../Topper';
import Row from './Row';

const RowWithControls = React.forwardRef(({children, item}, ref) => {
	const [resizing, setResizing] = useState(false);
	const [updatedLayoutData, setUpdatedLayoutData] = useState(null);
	const [customRow, setCustomRow] = useState(false);

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
	const {modulesPerRow, verticalAlignment} = rowResponsiveConfig;

	const {height, maxWidth, minWidth, width} = item.config.styles;

	return (
		<Topper
			item={item}
			itemElement={itemElement}
			style={{
				maxWidth,
				minWidth,
				width,
			}}
		>
			<Row
				className={classNames({
					'align-bottom': verticalAlignment === 'bottom',
					'align-middle': verticalAlignment === 'middle',
					empty:
						item.config.numberOfColumns === modulesPerRow &&
						!item.children.some(
							(childId) =>
								layoutData.items[childId].children.length
						) &&
						!height,
					'page-editor__row': canUpdateItemConfiguration,
					'page-editor__row-overlay-grid': resizing,
				})}
				item={item}
				ref={setRef}
			>
				<ResizeContextProvider
					value={{
						customRow,
						resizing,
						setCustomRow,
						setResizing,
						setUpdatedLayoutData,
						updatedLayoutData,
					}}
				>
					{children}
				</ResizeContextProvider>
			</Row>
		</Topper>
	);
});

RowWithControls.propTypes = {
	item: getLayoutDataItemPropTypes({
		config: PropTypes.shape({gutters: PropTypes.bool}),
	}).isRequired,
};

export default RowWithControls;
