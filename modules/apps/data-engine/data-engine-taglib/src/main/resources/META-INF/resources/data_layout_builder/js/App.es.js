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

import AppContext from './AppContext.es';
import AppContextProvider from './AppContextProvider.es';
import DataLayoutBuilder from './data-layout-builder/DataLayoutBuilder.es';
import DataLayoutBuilderContextProvider from './data-layout-builder/DataLayoutBuilderContextProvider.es';
import DataLayoutBuilderSidebar from './data-layout-builder/DataLayoutBuilderSidebar.es';
import DataLayoutBuilderDragAndDrop from './drag-and-drop/DataLayoutBuilderDragAndDrop.es';
import withDragAndDropContext from './drag-and-drop/withDragAndDropContext.es';

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
	groupId: Number(groupId)
});

const AppContent = ({dataLayoutBuilder, setDataLayoutBuilder, ...props}) => {
	const [state, dispatch] = useContext(AppContext);

	useEffect(() => {
		if (dataLayoutBuilder) {
			dataLayoutBuilder.emit('contextUpdated', state);
		}
	}, [dataLayoutBuilder, state]);

	return (
		<>
			<DataLayoutBuilder
				appContext={[state, dispatch]}
				defaultLanguageId={themeDisplay.getLanguageId()}
				editingLanguageId={themeDisplay.getLanguageId()}
				onLoad={setDataLayoutBuilder}
				{...parseProps(props)}
			/>

			{dataLayoutBuilder && (
				<DataLayoutBuilderContextProvider
					dataLayoutBuilder={dataLayoutBuilder}
				>
					<DataLayoutBuilderSidebar />

					<DataLayoutBuilderDragAndDrop
						dataLayoutBuilder={dataLayoutBuilder}
					/>
				</DataLayoutBuilderContextProvider>
			)}
		</>
	);
};

const App = withDragAndDropContext(props => {
	const {dataDefinitionId, dataLayoutId, fieldTypesModules} = parseProps(
		props
	);

	const [loaded, setLoaded] = useState(false);
	const [dataLayoutBuilder, setDataLayoutBuilder] = useState(null);

	useEffect(() => {
		Liferay.Loader.require(...fieldTypesModules, () => {
			setLoaded(true);
		});
	}, [fieldTypesModules]);

	return (
		<ClayModalProvider>
			{loaded && (
				<AppContextProvider
					dataDefinitionId={dataDefinitionId}
					dataLayoutBuilder={dataLayoutBuilder}
					dataLayoutId={dataLayoutId}
				>
					<AppContent
						dataLayoutBuilder={dataLayoutBuilder}
						setDataLayoutBuilder={setDataLayoutBuilder}
						{...props}
					/>
				</AppContextProvider>
			)}
		</ClayModalProvider>
	);
});

export default function(props) {
	return <App {...props} />;
}
