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
import React, {useCallback} from 'react';

import useSetRef from '../../../core/hooks/useSetRef';
import {getLayoutDataItemPropTypes} from '../../../prop-types/index';
import {useSelector} from '../../store/index';
import {getFrontendTokenValue} from '../../utils/getFrontendTokenValue';
import {getResponsiveConfig} from '../../utils/getResponsiveConfig';
import Topper from '../Topper';
import FragmentContent from '../fragment-content/FragmentContent';
import FragmentContentInteractionsFilter from '../fragment-content/FragmentContentInteractionsFilter';
import FragmentContentProcessor from '../fragment-content/FragmentContentProcessor';
import getAllPortals from './getAllPortals';

const FragmentWithControls = React.forwardRef(({item}, ref) => {
	const selectedViewportSize = useSelector(
		(state) => state.selectedViewportSize
	);

	const getPortals = useCallback((element) => getAllPortals(element), []);
	const itemConfig = getResponsiveConfig(item.config, selectedViewportSize);
	const [setRef, itemElement] = useSetRef(ref);

	const {
		marginLeft,
		marginRight,
		maxWidth,
		minWidth,
		shadow,
		width,
	} = itemConfig.styles;

	const style = {};

	style.boxShadow = getFrontendTokenValue(shadow);
	style.maxWidth = maxWidth;
	style.minWidth = minWidth;
	style.width = width;

	return (
		<Topper
			className={classNames({
				[`ml-${marginLeft}`]: marginLeft != null,
				[`mr-${marginRight}`]: marginRight != null,
			})}
			item={item}
			itemElement={itemElement}
			style={style}
		>
			<FragmentContentInteractionsFilter
				fragmentEntryLinkId={item.config.fragmentEntryLinkId}
				itemId={item.itemId}
			>
				<FragmentContent
					elementRef={setRef}
					fragmentEntryLinkId={item.config.fragmentEntryLinkId}
					getPortals={getPortals}
					item={item}
					withinTopper
				/>

				<FragmentContentProcessor
					fragmentEntryLinkId={item.config.fragmentEntryLinkId}
					itemId={item.itemId}
				/>
			</FragmentContentInteractionsFilter>
		</Topper>
	);
});

FragmentWithControls.displayName = 'FragmentWithControls';

FragmentWithControls.propTypes = {
	item: getLayoutDataItemPropTypes().isRequired,
};

export default FragmentWithControls;
