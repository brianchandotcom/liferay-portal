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
import selectShowLayoutItemTopper from '../../selectors/selectShowLayoutItemTopper';
import {useDispatch, useSelector} from '../../store/index';
import duplicateItem from '../../thunks/duplicateItem';
import FloatingToolbar from '../FloatingToolbar';
import Topper from '../Topper';
import Row from './Row';

const RowWithControls = React.forwardRef(
	({children, item, layoutData}, ref) => {
		const config = useContext(ConfigContext);
		const dispatch = useDispatch();
		const state = useSelector(state => state);
		const showLayoutItemTopper = useSelector(selectShowLayoutItemTopper);

		const handleButtonClick = id => {
			if (id === LAYOUT_DATA_FLOATING_TOOLBAR_BUTTONS.duplicateItem.id) {
				dispatch(
					duplicateItem({
						config,
						fragmentEntryLinkId: item.config.fragmentEntryLinkId,
						itemId: item.itemId,
						store: state
					})
				);
			}
		};

		const content = (
			<Row
				className="page-editor__row"
				item={item}
				layoutData={layoutData}
				ref={ref}
			>
				<FloatingToolbar
					buttons={[
						LAYOUT_DATA_FLOATING_TOOLBAR_BUTTONS.duplicateItem,
						LAYOUT_DATA_FLOATING_TOOLBAR_BUTTONS.rowConfiguration
					]}
					item={item}
					itemRef={ref}
					onButtonClick={handleButtonClick}
				/>

				{children}
			</Row>
		);

		return showLayoutItemTopper ? (
			<Topper
				acceptDrop={[LAYOUT_DATA_ITEM_TYPES.column]}
				item={item}
				layoutData={layoutData}
				name={Liferay.Language.get('row')}
			>
				{() => content}
			</Topper>
		) : (
			content
		);
	}
);

export default RowWithControls;
