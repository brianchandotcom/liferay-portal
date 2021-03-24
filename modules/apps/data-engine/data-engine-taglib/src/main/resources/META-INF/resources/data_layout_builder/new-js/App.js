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
} from 'dynamic-data-mapping-form-renderer';
import {
	dragAndDropReducer,
	fieldEditableReducer,
	languageReducer,
	pagesStructureReducer,
} from 'dynamic-data-mapping-form-renderer/js/core/reducers/index.es';
import {pageReducer} from 'dynamic-data-mapping-form-renderer/js/custom/form/reducers/index.es';
import React from 'react';
import {DndProvider} from 'react-dnd';
import {HTML5Backend} from 'react-dnd-html5-backend';

import DataEngineFormBuilder from './DataEngineFormBuilder';
import INITIAL_CONFIG from './config/initialConfig';
import INITIAL_STATE from './config/initialState';

const App = (props) => {
	const {config, state} = parseProps(props);

	return (
		<DndProvider backend={HTML5Backend}>
			<ClayModalProvider>
				<ConfigProvider config={config} initialConfig={INITIAL_CONFIG}>
					<FormProvider
						initialState={INITIAL_STATE}
						reducers={[
							dragAndDropReducer,
							fieldEditableReducer,
							languageReducer,
							pageReducer,
							pagesStructureReducer,
						]}
						value={state}
					>
						<DataEngineFormBuilder />
					</FormProvider>
				</ConfigProvider>
			</ClayModalProvider>
		</DndProvider>
	);
};

export default App;
