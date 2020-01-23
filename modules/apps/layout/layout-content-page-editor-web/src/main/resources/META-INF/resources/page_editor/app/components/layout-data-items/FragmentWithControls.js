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

import React, {useContext} from 'react';

import {LAYOUT_DATA_FLOATING_TOOLBAR_BUTTONS} from '../../config/constants/layoutDataFloatingToolbarButtons';
import {LAYOUT_DATA_ITEM_TYPES} from '../../config/constants/layoutDataItemTypes';
import {ConfigContext} from '../../config/index';
import {useSelector, useDispatch} from '../../store/index';
import duplicateFragment from '../../thunks/duplicateFragment';
import FloatingToolbar from '../FloatingToolbar';
import Topper from '../Topper';
import FragmentContent from './FragmentContent';

const FragmentWithControls = React.forwardRef(({item, layoutData}, ref) => {
	const config = useContext(ConfigContext);
	const dispatch = useDispatch();
	const state = useSelector(state => state);

	const {fragmentEntryLinks} = state;

	const fragmentEntryLink =
		fragmentEntryLinks[item.config.fragmentEntryLinkId];

	const handleButtonClick = id => {
		if (id === LAYOUT_DATA_FLOATING_TOOLBAR_BUTTONS.duplicateFragment.id) {
			dispatch(
				duplicateFragment({
					config,
					fragmentEntryLinkId: item.config.fragmentEntryLinkId,
					itemId: item.itemId,
					store: state
				})
			);
		}
	};

	const portletId = fragmentEntryLink.portletId;

	const floatingToolbarButtons = [
		LAYOUT_DATA_FLOATING_TOOLBAR_BUTTONS.duplicateFragment
	];

	if (!portletId) {
		floatingToolbarButtons.push(
			LAYOUT_DATA_FLOATING_TOOLBAR_BUTTONS.fragmentConfiguration
		);
	}

	return (
		<Topper
			acceptDrop={[LAYOUT_DATA_ITEM_TYPES.fragment]}
			active
			item={item}
			layoutData={layoutData}
			name={fragmentEntryLink.name}
		>
			{() => (
				<>
					<FloatingToolbar
						buttons={floatingToolbarButtons}
						item={item}
						itemRef={ref}
						onButtonClick={handleButtonClick}
					/>

					<FragmentContent
						fragmentEntryLink={fragmentEntryLink}
						ref={ref}
					/>
				</>
			)}
		</Topper>
	);
});

export default FragmentWithControls;
