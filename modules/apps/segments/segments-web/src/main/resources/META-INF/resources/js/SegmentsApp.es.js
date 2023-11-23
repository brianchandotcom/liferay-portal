/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {DragPreview} from '@liferay/layout-js-components-web';
import React from 'react';
import {DndProvider} from 'react-dnd';
import {HTML5Backend} from 'react-dnd-html5-backend';

import ThemeContext from './ThemeContext.es';
import SegmentEdit from './components/segment_edit/SegmentEdit';
import {KeyboardMovementContextProvider} from './contexts/KeyboardMovementContext';

export default function ({context, props}) {
	return (
		<DndProvider backend={HTML5Backend}>
			<KeyboardMovementContextProvider>
				<ThemeContext.Provider value={context}>
					<div className="segments-root">
						<DragPreview />

						<SegmentEdit {...props} />
					</div>
				</ThemeContext.Provider>
			</KeyboardMovementContextProvider>
		</DndProvider>
	);
}
