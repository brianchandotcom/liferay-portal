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
import React from 'react';

import {getLayoutDataItemPropTypes} from '../../../prop-types/index';
import selectCanUpdateItemConfiguration from '../../selectors/selectCanUpdateItemConfiguration';
import selectCanUpdatePageStructure from '../../selectors/selectCanUpdatePageStructure';
import {useSelector} from '../../store/index';
import {getResponsiveColumnSize} from '../../utils/getResponsiveColumnSize';
import {useUpdatedLayoutDataContext} from '../ResizeContext';

const Column = React.forwardRef(
	({children, className, item, ...props}, ref) => {
		const canUpdateItemConfiguration = useSelector(
			selectCanUpdateItemConfiguration
		);
		const canUpdatePageStructure = useSelector(
			selectCanUpdatePageStructure
		);
		const selectedViewportSize = useSelector(
			(state) => state.selectedViewportSize
		);
		const updatedLayoutData = useUpdatedLayoutDataContext();

		const itemConfig = updatedLayoutData
			? updatedLayoutData.items[item.itemId].config
			: item.config;

		const columnSize = getResponsiveColumnSize(
			itemConfig,
			selectedViewportSize
		);

		const columnContent =
			canUpdatePageStructure || canUpdateItemConfiguration ? (
				<div className="page-editor__col__border">{children}</div>
			) : (
				children
			);

		return (
			<div
				{...props}
				className={classNames(className, 'col', {
					[`col-${columnSize}`]: columnSize,
					empty: !item.children.length,
				})}
				ref={ref}
			>
				{columnContent}
			</div>
		);
	}
);

Column.propTypes = {
	item: getLayoutDataItemPropTypes({
		config: PropTypes.shape({size: PropTypes.number}),
	}).isRequired,
};

export default Column;
