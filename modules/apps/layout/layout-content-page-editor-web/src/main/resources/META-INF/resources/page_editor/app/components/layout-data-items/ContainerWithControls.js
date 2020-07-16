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
import {LAYOUT_DATA_FLOATING_TOOLBAR_BUTTONS} from '../../config/constants/layoutDataFloatingToolbarButtons';
import selectCanUpdateItemConfiguration from '../../selectors/selectCanUpdateItemConfiguration';
import selectCanUpdatePageStructure from '../../selectors/selectCanUpdatePageStructure';
import selectShowFloatingToolbar from '../../selectors/selectShowFloatingToolbar';
import {useSelector} from '../../store/index';
import {useIsActive} from '../Controls';
import Topper from '../Topper';
import FloatingToolbar from '../floating-toolbar/FloatingToolbar';
import Container from './Container';

const ContainerWithControls = React.forwardRef(
	({children, item, layoutData}, ref) => {
		const canUpdateItemConfiguration = useSelector(
			selectCanUpdateItemConfiguration
		);
		const canUpdatePageStructure = useSelector(
			selectCanUpdatePageStructure
		);
		const isActive = useIsActive();
		const showFloatingToolbar = useSelector(selectShowFloatingToolbar);

		const [setRef, itemElement] = useSetRef(ref);

		const buttons = [];

		const {marginLeft, marginRight, widthType} = item.config;

		if (canUpdateItemConfiguration) {
			buttons.push(LAYOUT_DATA_FLOATING_TOOLBAR_BUTTONS.containerLink);
			buttons.push(LAYOUT_DATA_FLOATING_TOOLBAR_BUTTONS.containerStyles);
		}

		return (
			<>
				{isActive(item.itemId) && showFloatingToolbar && (
					<FloatingToolbar
						buttons={buttons}
						item={item}
						itemElement={itemElement}
					/>
				)}

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
			</>
		);
	}
);

ContainerWithControls.propTypes = {
	item: getLayoutDataItemPropTypes().isRequired,
	layoutData: LayoutDataPropTypes.isRequired,
};

export default ContainerWithControls;
