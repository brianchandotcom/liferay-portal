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
import ReactDOM from 'react-dom';
import SegmentEdit from './components/segment_edit/SegmentEdit.es';
import ThemeContext from './ThemeContext.es';
import {ClayIconSpriteContext} from '@clayui/icon';

export default function(id, props, context) {
	ReactDOM.render(
		<ClayIconSpriteContext.Provider value={context.spritemap}>
			<ThemeContext.Provider value={context}>
				<div className='segments-root'>
					<SegmentEdit {...props} />
				</div>
			</ThemeContext.Provider>
		</ClayIconSpriteContext.Provider>,
		document.getElementById(id)
	);
}
