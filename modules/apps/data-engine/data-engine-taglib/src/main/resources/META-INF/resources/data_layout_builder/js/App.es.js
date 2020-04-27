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
import HTML5Backend from 'react-dnd-html5-backend';

import AppContext from './AppContext.es';
import AppContextProvider from './AppContextProvider.es';
import MultiPanelSidebar from './components/sidebar/MultiPanelSidebar.es';
import initializeSidebarConfig from './components/sidebar/initializeSidebarConfig.es';
import DataLayoutBuilder from './data-layout-builder/DataLayoutBuilder.es';
import DataLayoutBuilderContextProvider from './data-layout-builder/DataLayoutBuilderContextProvider.es';
import DataLayoutBuilderDragAndDrop from './drag-and-drop/DataLayoutBuilderDragAndDrop.es';

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

const AppSidebar = ({panels, sidebarPanels, toolbarId}) => (
	<MultiPanelSidebar
		panels={panels}
		sidebarPanels={sidebarPanels}
		toolbarId={toolbarId}
	/>
);

const AppContent = ({
	dataLayoutBuilder,
	setDataLayoutBuilder,
	sidebarConfig,
	...props
}) => {
	const [state, dispatch] = useContext(AppContext);

	const {panels, sidebarPanels} = sidebarConfig;

	useEffect(() => {
		if (dataLayoutBuilder) {
			dataLayoutBuilder.emit('contextUpdated', state);
		}
	}, [dataLayoutBuilder, state]);

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
					<AppSidebar panels={panels} sidebarPanels={sidebarPanels} />

					<DataLayoutBuilderDragAndDrop
						dataLayoutBuilder={dataLayoutBuilder}
					/>
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
		fieldTypesModules,
		groupId,
	} = parseProps(props);

	const sidebarConfig = initializeSidebarConfig(props);

	const [loaded, setLoaded] = useState(false);
	const [dataLayoutBuilder, setDataLayoutBuilder] = useState(null);

	useEffect(() => {
		Liferay.Loader.require(...fieldTypesModules, () => {
			setLoaded(true);
		});
	}, [fieldTypesModules]);

	return (
		<DndProvider backend={HTML5Backend}>
			<ClayModalProvider>
				{loaded && (
					<AppContextProvider
						config={config}
						contentType={contentType}
						dataDefinitionId={dataDefinitionId}
						dataLayoutBuilder={dataLayoutBuilder}
						dataLayoutId={dataLayoutId}
						groupId={groupId}
					>
						<AppContent
							dataLayoutBuilder={dataLayoutBuilder}
							setDataLayoutBuilder={setDataLayoutBuilder}
							sidebarConfig={sidebarConfig}
							{...props}
						/>
					</AppContextProvider>
				)}
			</ClayModalProvider>
		</DndProvider>
	);
};

export default App;
