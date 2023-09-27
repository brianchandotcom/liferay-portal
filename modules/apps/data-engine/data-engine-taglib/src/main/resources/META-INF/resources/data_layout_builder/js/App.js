/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ClayModalProvider} from '@clayui/modal';
import {
	ConfigProvider,
	FormProvider,
	KeyboardDNDContextProvider,
	parseProps,
} from 'data-engine-js-components-web';
import {
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
import KeyboardDNDText from './KeyboardDNDText';
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
						<KeyboardDNDContextProvider>
							<DataEngineTaglibCompatibilityLayer />

							<FormBuilder />

							<KeyboardDNDText />
						</KeyboardDNDContextProvider>
					</FormProvider>
				</ConfigProvider>
			</ClayModalProvider>
		</DndProvider>
	);
};

export default App;
