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
import React from 'react';

import useSetRef from '../../../core/hooks/useSetRef';
import {
	LayoutDataPropTypes,
	getLayoutDataItemPropTypes,
} from '../../../prop-types/index';
import selectCanUpdateItemConfiguration from '../../selectors/selectCanUpdateItemConfiguration';
import selectCanUpdatePageStructure from '../../selectors/selectCanUpdatePageStructure';
import {useSelector} from '../../store/index';
import Topper from '../Topper';
import Container from './Container';

const ContainerWithControls = React.forwardRef(
	({children, item, layoutData}, ref) => {
		const {marginLeft, marginRight, widthType} = item.config;
		const [setRef, itemElement] = useSetRef(ref);

		const canUpdateItemConfiguration = useSelector(
			selectCanUpdateItemConfiguration
		);
		const canUpdatePageStructure = useSelector(
			selectCanUpdatePageStructure
		);

		return (
			<Topper
				className={classNames({
					[`ml-${marginLeft || 0}`]: widthType !== 'fixed',
					[`mr-${marginRight || 0}`]: widthType !== 'fixed',
					container: widthType === 'fixed',
					'p-0': widthType === 'fixed',
				})}
				item={item}
				itemElement={itemElement}
				layoutData={layoutData}
			>
				<Container
					className={classNames({
						empty: !item.children.length,
						'page-editor__container':
							canUpdatePageStructure ||
							canUpdateItemConfiguration,
					})}
					item={item}
					ref={setRef}
				>
					{children}
				</Container>
			</Topper>
		);
	}
);

ContainerWithControls.propTypes = {
	item: getLayoutDataItemPropTypes().isRequired,
	layoutData: LayoutDataPropTypes.isRequired,
};

export default ContainerWithControls;
