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

import PropTypes from 'prop-types';
import React from 'react';

import {StyleBookContextProvider} from '../../plugins/page-design-options/hooks/useStyleBook';
import {INIT} from '../actions/types';
import {CollectionActiveItemContextProvider} from '../contexts/CollectionActiveItemContext';
import {ControlsProvider} from '../contexts/ControlsContext';
import {DisplayPagePreviewItemContextProvider} from '../contexts/DisplayPagePreviewItemContext';
import {EditableProcessorContextProvider} from '../contexts/EditableProcessorContext';
import {FormValidationContextProvider} from '../contexts/FormValidationContext';
import {GlobalContextProvider} from '../contexts/GlobalContext';
import {StoreContextProvider} from '../contexts/StoreContext';
import {reducer} from '../reducers/index';
import {DragAndDropContextProvider} from '../utils/drag-and-drop/useDragAndDrop';
import CommonStylesManager from './CommonStylesManager';
import {DisplayPagePreviewItemSelector} from './DisplayPagePreviewItemSelector';
import DragPreview from './DragPreview';
import ItemConfigurationSidebar from './ItemConfigurationSidebar';
import {LayoutBreadcrumbs} from './LayoutBreadcrumbs';
import LayoutViewport from './LayoutViewport';
import ShortcutManager from './ShortcutManager';
import Sidebar from './Sidebar';
import Toolbar from './Toolbar';
import WidgetsManager from './WidgetsManager';
import AppHooks from './app-hooks/index';

export default function App({state}) {
	const initialState = reducer(state, {type: INIT});

	return (
		<StoreContextProvider initialState={initialState} reducer={reducer}>
			<ControlsProvider>
				<CollectionActiveItemContextProvider>
					<DragAndDropContextProvider>
						<EditableProcessorContextProvider>
							<DisplayPagePreviewItemContextProvider>
								<AppHooks />

								<DisplayPagePreviewItemSelector dark />

								<DragPreview />

								<WidgetsManager />

								<FormValidationContextProvider>
									<Toolbar />

									<ShortcutManager />

									<GlobalContextProvider>
										<CommonStylesManager />

										<LayoutViewport />

										<LayoutBreadcrumbs />

										<StyleBookContextProvider>
											<Sidebar />

											<ItemConfigurationSidebar />
										</StyleBookContextProvider>
									</GlobalContextProvider>
								</FormValidationContextProvider>
							</DisplayPagePreviewItemContextProvider>
						</EditableProcessorContextProvider>
					</DragAndDropContextProvider>
				</CollectionActiveItemContextProvider>
			</ControlsProvider>
		</StoreContextProvider>
	);
}

App.propTypes = {
	state: PropTypes.object.isRequired,
};
