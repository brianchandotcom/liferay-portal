/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayLayout from '@clayui/layout';
import PropTypes from 'prop-types';
import React from 'react';

import {getLayoutDataItemPropTypes} from '../../../prop_types/index';
import {useNextColumnSizes} from '../../contexts/ResizeContext';
import {useSelector} from '../../contexts/StoreContext';
import selectCanUpdateItemConfiguration from '../../selectors/selectCanUpdateItemConfiguration';
import selectCanUpdatePageStructure from '../../selectors/selectCanUpdatePageStructure';
import {getResponsiveColumnSize} from '../../utils/getResponsiveColumnSize';

const Column = React.forwardRef(({children, className, item}, ref) => {
	const canUpdateItemConfiguration = useSelector(
		selectCanUpdateItemConfiguration
	);
	const canUpdatePageStructure = useSelector(selectCanUpdatePageStructure);
	const selectedViewportSize = useSelector(
		(state) => state.selectedViewportSize
	);
	const nextColumnSizes = useNextColumnSizes();

	const columnSize =
		nextColumnSizes?.[item.itemId] ||
		getResponsiveColumnSize(item.config, selectedViewportSize);

	const columnContent =
		canUpdatePageStructure || canUpdateItemConfiguration ? (
			<div className="page-editor__col__border">{children}</div>
		) : (
			children
		);

	return (
		<ClayLayout.Col className={className} ref={ref} size={columnSize}>
			{columnContent}
		</ClayLayout.Col>
	);
});

Column.propTypes = {
	item: getLayoutDataItemPropTypes({
		config: PropTypes.shape({size: PropTypes.number}),
	}).isRequired,
};

export default Column;
