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
	dragAndDropReducer,
	fieldEditableReducer,
	languageReducer,
	pagesStructureReducer,
} from 'data-engine-js-components-web/js/core/reducers/index.es';
import {pageReducer} from 'data-engine-js-components-web/js/custom/form/reducers/index.es';
import React from 'react';
import {DndProvider} from 'react-dnd';
import {HTML5Backend} from 'react-dnd-html5-backend';
import {HashRouter as Router, Route, Switch} from 'react-router-dom';

import {NavigationBar} from './components/NavigationBar.es';
import {INITIAL_CONFIG_STATE} from './config/initialConfigState.es';
import {BUILDER_INITIAL_STATE, initState} from './config/initialState.es';
import {AutoSaveProvider} from './hooks/useAutoSave.es';
import {ToastProvider} from './hooks/useToast.es';
import {FormBuilder} from './pages/FormBuilder.es';
import {Report} from './pages/Report.es';
import {RuleBuilder} from './pages/RuleBuilder.es';
import {
	elementSetReducer,
	formInfoReducer,
	rulesReducer,
	sidebarReducer,
} from './reducers/index.es';

/**
 * Exporting default application to Forms Admin. Only Providers and
 * routing must be defined.
 */
export const App = ({autosaveInterval, autosaveURL, ...otherProps}) => {
	const {config, state} = parseProps(otherProps);

	return (
		<DndProvider backend={HTML5Backend} context={window}>
			<ConfigProvider
				config={config}
				initialConfig={INITIAL_CONFIG_STATE}
			>
				<ClayModalProvider>
					<FormProvider
						init={initState}
						initialState={BUILDER_INITIAL_STATE}
						reducers={[
							dragAndDropReducer,
							elementSetReducer,
							fieldEditableReducer,
							formInfoReducer,
							languageReducer,
							pageReducer,
							pagesStructureReducer,
							rulesReducer,
							sidebarReducer,
						]}
						value={state}
					>
						<AutoSaveProvider
							interval={autosaveInterval}
							published={config.published}
							url={autosaveURL}
						>
							<ToastProvider>
								<Router>
									<Switch>
										<Route
											component={NavigationBar}
											path="/"
										/>
									</Switch>
									<Switch>
										<Route
											component={FormBuilder}
											exact
											path="/"
										/>
										<Route
											component={RuleBuilder}
											path="/rules"
										/>
										<Route
											component={Report}
											path="/report"
										/>
									</Switch>
								</Router>
							</ToastProvider>
						</AutoSaveProvider>
					</FormProvider>
				</ClayModalProvider>
			</ConfigProvider>
		</DndProvider>
	);
};

App.displayName = 'App';

export default App;
