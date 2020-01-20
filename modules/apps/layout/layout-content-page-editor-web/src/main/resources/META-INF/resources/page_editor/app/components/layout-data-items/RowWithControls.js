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

import React from 'react';

import {LAYOUT_DATA_FLOATING_TOOLBAR_BUTTONS} from '../../config/constants/layoutDataFloatingToolbarButtons';
import {LAYOUT_DATA_ITEM_TYPES} from '../../config/constants/layoutDataItemTypes';
import FloatingToolbar from '../FloatingToolbar';
import Topper from '../Topper';
import Row from './Row';

const RowWithControls = React.forwardRef(
	({children, item, layoutData}, ref) => {
		return (
			<Topper
				acceptDrop={[LAYOUT_DATA_ITEM_TYPES.column]}
				item={item}
				layoutData={layoutData}
				name={Liferay.Language.get('row')}
			>
				{() => (
					<Row
						className="page-editor__row"
						item={item}
						layoutData={layoutData}
						ref={ref}
					>
						<FloatingToolbar
							buttons={[
								LAYOUT_DATA_FLOATING_TOOLBAR_BUTTONS.rowConfiguration
							]}
							item={item}
							itemRef={ref}
						/>

						{children}
					</Row>
				)}
			</Topper>
		);
	}
);

export default RowWithControls;
