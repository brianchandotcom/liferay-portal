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

import {ClayIconSpriteContext} from '@clayui/icon';
import {ClayModalProvider} from '@clayui/modal';
import React from 'react';
import {DndProvider} from 'react-dnd';
import {HTML5Backend} from 'react-dnd-html5-backend';
import {HashRouter as Router, Route, Switch} from 'react-router-dom';

import {ConfigProvider} from '../../core/hooks/useConfig.es';
import {FormProvider} from '../../core/hooks/useForm.es';
import dragAndDropReducer from '../../core/reducers/dragAndDropReducer.es';
import fieldEditableReducer from '../../core/reducers/fieldEditableReducer.es';
import languageReducer from '../../core/reducers/languageReducer.es';
import pagesStructureReducer from '../../core/reducers/pagesStructureReducer.es';
import {getConnectedReactComponentAdapter} from '../../util/ReactComponentAdapter.es';
import {FormBuilder} from './FormBuilder.es';
import {Report} from './Report.es';
import {RuleBuilder} from './RuleBuilder.es';
import {NavigationBar} from './components/NavigationBar.es';
import {BUILDER_INITIAL_CONFIG_STATE} from './config/initialConfigState.es';
import {BUILDER_INITIAL_STATE, initState} from './config/initialState.es';
import {AutoSaveProvider} from './hooks/useAutoSave.es';
import {ToastProvider} from './hooks/useToast.es';
import {
	elementSetReducer,
	formInfoReducer,
	pageReducer,
	rulesReducer,
	sidebarReducer,
} from './reducers/index.es';
import {parseProps} from './util/parseProps.es';

/**
 * Exporting default application to Forms Admin. Only Providers and
 * routing must be defined.
 */
export const App = React.forwardRef(
	({autosaveInterval, autosaveURL, spritemap, ...otherProps}) => {
		const {config, state} = parseProps({spritemap, ...otherProps});

		return (
			<ClayIconSpriteContext.Provider value={spritemap}>
				<DndProvider backend={HTML5Backend} context={window}>
					<ConfigProvider
						config={config}
						initialConfig={BUILDER_INITIAL_CONFIG_STATE}
					>
						<ClayModalProvider spritemap={spritemap}>
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
			</ClayIconSpriteContext.Provider>
		);
	}
);

App.displayName = 'App';

export default getConnectedReactComponentAdapter(App);
