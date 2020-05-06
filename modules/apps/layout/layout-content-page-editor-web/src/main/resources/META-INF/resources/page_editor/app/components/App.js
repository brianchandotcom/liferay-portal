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

import React, {useEffect} from 'react';
import {createPortal} from 'react-dom';

import {config} from '../config/index';
import {useSelector} from '../store/index';
import {DragAndDropContextProvider} from '../utils/useDragAndDrop';
import DisabledArea from './DisabledArea';
import DragPreview from './DragPreview';
import LayoutViewport from './LayoutViewport';
import Sidebar from './Sidebar';
import Toolbar from './Toolbar';

export default function App() {
	const mainItemId = useSelector((state) => state.layoutData.rootItems.main);
	const masterLayoutData = useSelector((state) => state.masterLayoutData);
	const languageId = useSelector((state) => state.languageId);
	const layoutData = useSelector((state) => state.layoutData);

	useEffect(() => {
		const currentLanguageDirection = config.languageDirection[languageId];
		const wrapper = document.getElementById('wrapper');

		if (wrapper) {
			wrapper.dir = currentLanguageDirection;
			wrapper.lang = languageId;
		}
	}, [languageId]);

	return (
		<DragAndDropContextProvider layoutData={layoutData}>
			<DisabledArea />
			<DragPreview />
			<Toolbar />
			<LayoutViewport
				mainItemId={mainItemId}
				useMasterLayout={masterLayoutData.items}
			/>
			{createPortal(<Sidebar />, document.body)}
		</DragAndDropContextProvider>
	);
}
