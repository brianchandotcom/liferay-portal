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

import React from 'react';

import useSetRef from '../../../core/hooks/useSetRef';
import {
	LayoutDataPropTypes,
	getLayoutDataItemPropTypes,
} from '../../../prop-types/index';
import {LAYOUT_DATA_FLOATING_TOOLBAR_BUTTONS} from '../../config/constants/layoutDataFloatingToolbarButtons';
import selectCanUpdateItemConfiguration from '../../selectors/selectCanUpdateItemConfiguration';
import selectShowFloatingToolbar from '../../selectors/selectShowFloatingToolbar';
import {useSelector} from '../../store/index';
import {useIsActive} from '../Controls';
import Topper from '../Topper';
import FloatingToolbar from '../floating-toolbar/FloatingToolbar';
import Collection from './Collection';

const CollectionWithControls = React.forwardRef(
	({children, item, layoutData}, ref) => {
		const canUpdateItemConfiguration = useSelector(
			selectCanUpdateItemConfiguration
		);
		const isActive = useIsActive();

		const showFloatingToolbar = useSelector(selectShowFloatingToolbar);

		const [setRef, itemElement] = useSetRef(ref);

		const buttons = [];

		if (canUpdateItemConfiguration) {
			buttons.push(
				LAYOUT_DATA_FLOATING_TOOLBAR_BUTTONS.collectionConfiguration
			);
		}

		return (
			<Topper
				item={item}
				itemElement={itemElement}
				layoutData={layoutData}
			>
				<>
					<Collection item={item} ref={setRef}>
						{children}
					</Collection>

					{isActive(item.itemId) && showFloatingToolbar && (
						<FloatingToolbar
							buttons={buttons}
							item={item}
							itemElement={itemElement}
						/>
					)}
				</>
			</Topper>
		);
	}
);

CollectionWithControls.propTypes = {
	item: getLayoutDataItemPropTypes().isRequired,
	layoutData: LayoutDataPropTypes.isRequired,
};

export default CollectionWithControls;
