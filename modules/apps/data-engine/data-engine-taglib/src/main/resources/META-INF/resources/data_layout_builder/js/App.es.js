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

import {ClayModalProvider} from '@clayui/modal';
import React, {useContext, useEffect, useState} from 'react';
import {DndProvider} from 'react-dnd';
import {HTML5Backend} from 'react-dnd-html5-backend';

import AppContext from './AppContext.es';
import AppContextProvider from './AppContextProvider.es';
import {UPDATE_APP_PROPS} from './actions.es';
import MultiPanelSidebar from './components/sidebar/MultiPanelSidebar.es';
import initializeSidebarConfig from './components/sidebar/initializeSidebarConfig.es';
import DataLayoutBuilder from './data-layout-builder/DataLayoutBuilder.es';
import DataLayoutBuilderContextProvider from './data-layout-builder/DataLayoutBuilderContextProvider.es';
import DragLayer from './drag-and-drop/DragLayer.es';

const parseProps = ({
	dataDefinitionId,
	dataLayoutId,
	fieldTypesModules,
	groupId,
	...props
}) => ({
	...props,
	dataDefinitionId: Number(dataDefinitionId),
	dataLayoutId: Number(dataLayoutId),
	fieldTypesModules: fieldTypesModules.split(','),
	groupId: Number(groupId),
});

const AppContent = ({
	dataLayoutBuilder,
	setChildrenContext,
	setDataLayoutBuilder,
	sidebarConfig,
	...props
}) => {
	const [state, dispatch] = useContext(AppContext);
	const {panels, sidebarPanels, sidebarVariant} = sidebarConfig;

	useEffect(() => {
		if (dataLayoutBuilder) {
			dataLayoutBuilder.emit('contextUpdated', state);
		}
	}, [dataLayoutBuilder, state]);

	useEffect(() => {
		if (setChildrenContext) {
			setChildrenContext({dataLayoutBuilder, dispatch, state});
		}
	}, [dataLayoutBuilder, dispatch, setChildrenContext, state]);

	useEffect(() => {
		if (!setChildrenContext) {
			dispatch({payload: props, type: UPDATE_APP_PROPS});
		}
		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, [dispatch, setChildrenContext]);

	return (
		<>
			<DataLayoutBuilder
				appContext={[state, dispatch]}
				config={state.config}
				onLoad={setDataLayoutBuilder}
				{...parseProps(props)}
			/>

			{dataLayoutBuilder && (
				<DataLayoutBuilderContextProvider
					dataLayoutBuilder={dataLayoutBuilder}
				>
					<MultiPanelSidebar
						panels={panels}
						sidebarPanels={sidebarPanels}
						variant={sidebarVariant}
					/>

					<DragLayer />
				</DataLayoutBuilderContextProvider>
			)}
		</>
	);
};

const App = (props) => {
	const {
		config,
		contentType,
		dataDefinitionId,
		dataLayoutId,
		fieldSetContentType,
		groupId,
	} = parseProps(props);

	const sidebarConfig = initializeSidebarConfig(props);
	const [dataLayoutBuilder, setDataLayoutBuilder] = useState(null);

	return (
		<DndProvider backend={HTML5Backend}>
			<ClayModalProvider>
				<AppContextProvider
					config={config}
					contentType={contentType}
					dataDefinitionId={dataDefinitionId}
					dataLayoutBuilder={dataLayoutBuilder}
					dataLayoutId={dataLayoutId}
					fieldSetContentType={fieldSetContentType}
					groupId={groupId}
				>
					<AppContent
						dataLayoutBuilder={dataLayoutBuilder}
						setDataLayoutBuilder={setDataLayoutBuilder}
						sidebarConfig={sidebarConfig}
						{...props}
					/>
				</AppContextProvider>
			</ClayModalProvider>
		</DndProvider>
	);
};

export default App;
