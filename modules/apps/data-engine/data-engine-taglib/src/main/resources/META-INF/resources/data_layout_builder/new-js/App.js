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
import {
	ConfigProvider,
	FormProvider,
	parseProps,
} from 'data-engine-js-components-web';
import {
	dataDefinitionReducer,
	dataLayoutReducer,
	dragAndDropReducer,
	fieldEditableReducer,
	languageReducer,
	pagesStructureReducer,
} from 'data-engine-js-components-web/js/core/reducers/index.es';
import {pageReducer} from 'data-engine-js-components-web/js/custom/form/reducers/index.es';
import React from 'react';
import {DndProvider} from 'react-dnd';
import {HTML5Backend} from 'react-dnd-html5-backend';

import {DataEngineTaglibCompatibilityLayer} from './DataEngineTaglibCompatibilityLayer';
import {FormBuilder} from './FormBuilder';
import INITIAL_CONFIG from './config/initialConfig';
import INITIAL_STATE from './config/initialState';
import {useData} from './hooks/useData';
import fieldSetReducer from './reducers/fieldSetReducer';
import rulesReducer from './reducers/rulesReducer';
import sidebarReducer from './reducers/sidebarReducer';

const App = (props) => {
	const {config, state} = parseProps(props);

	const {dataDefinition, dataLayout} = useData({
		dataDefinitionId: config.dataDefinitionId,
		dataLayoutId: config.dataLayoutId,
	});

	// We block the rendering of the application when the data is not ready, this
	// can be replaced in the future by using `React.Suspense` when `useResource`
	// is compatible.

	if (!dataDefinition || !dataLayout) {
		return null;
	}

	return (
		<DndProvider backend={HTML5Backend}>
			<ClayModalProvider>
				<ConfigProvider config={config} initialConfig={INITIAL_CONFIG}>
					<FormProvider
						initialState={INITIAL_STATE}
						reducers={[
							dataDefinitionReducer,
							dataLayoutReducer,
							dragAndDropReducer,
							fieldEditableReducer,
							fieldSetReducer,
							languageReducer,
							pageReducer,
							pagesStructureReducer,
							rulesReducer,
							sidebarReducer,
						]}
						value={{...state, ...dataDefinition, dataLayout}}
					>
						<DataEngineTaglibCompatibilityLayer />
						<FormBuilder />
					</FormProvider>
				</ConfigProvider>
			</ClayModalProvider>
		</DndProvider>
	);
};

export default App;
