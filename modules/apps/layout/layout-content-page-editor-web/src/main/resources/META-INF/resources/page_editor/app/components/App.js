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
import {GlobalContextProvider} from '../contexts/GlobalContext';
import {StoreContextProvider} from '../contexts/StoreContext';
import {StyleErrorsContextProvider} from '../contexts/StyleErrorsContext';
import {WidgetsContextProvider} from '../contexts/WidgetsContext';
import {reducer} from '../reducers/index';
import {DragAndDropContextProvider} from '../utils/drag-and-drop/useDragAndDrop';
import CommonStylesManager from './CommonStylesManager';
import {DisplayPagePreviewItemSelector} from './DisplayPagePreviewItemSelector';
import DragPreview from './DragPreview';
import ItemConfigurationSidebar from './ItemConfigurationSidebar';
import LayoutViewport from './LayoutViewport';
import ShortcutManager from './ShortcutManager';
import Sidebar from './Sidebar';
import Toolbar from './Toolbar';
import BackURL from './app-hooks/BackURL';
import ExtendSession from './app-hooks/ExtendSession';
import LanguageDirection from './app-hooks/LanguageDirection';
import PortletConfigurationListener from './app-hooks/PortletConfigurationListener';
import PreviewURL from './app-hooks/PreviewURL';
import URLParser from './app-hooks/URLParser';

export default function App({state}) {
	const initialState = reducer(state, {type: INIT});

	return (
		<StoreContextProvider initialState={initialState} reducer={reducer}>
			<BackURL />

			<LanguageDirection />

			<PortletConfigurationListener />

			<URLParser />

			<PreviewURL />

			<ExtendSession />

			<ControlsProvider>
				<CollectionActiveItemContextProvider>
					<DragAndDropContextProvider>
						<EditableProcessorContextProvider>
							<DisplayPagePreviewItemContextProvider>
								<WidgetsContextProvider>
									<DisplayPagePreviewItemSelector dark />

									<DragPreview />

									<StyleErrorsContextProvider>
										<Toolbar />

										<ShortcutManager />

										<GlobalContextProvider>
											<CommonStylesManager />

											<LayoutViewport />

											<StyleBookContextProvider>
												<Sidebar />

												{Liferay.FeatureFlags[
													'LPS-153452'
												] && (
													<ItemConfigurationSidebar />
												)}
											</StyleBookContextProvider>
										</GlobalContextProvider>
									</StyleErrorsContextProvider>
								</WidgetsContextProvider>
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
