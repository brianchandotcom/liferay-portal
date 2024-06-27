/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayLoadingIndicator from '@clayui/loading-indicator';
import {ClayModalProvider} from '@clayui/modal';
import {
	ConfigProvider,
	FormProvider,
	KeyboardDNDContextProvider,
	dragAndDropReducer,
	fieldEditableReducer,
	historyReducer,
	languageReducer,
	objectFieldsReducer,
	pageReducer,
	pagesStructureReducer,
	parseProps,
} from 'data-engine-js-components-web';
import React, {Suspense} from 'react';
import {DndProvider} from 'react-dnd';
import {HTML5Backend} from 'react-dnd-html5-backend';
import {HashRouter as Router, Route, Switch} from 'react-router-dom';

import {NavigationBar} from './components/NavigationBar.es';
import {INITIAL_CONFIG_STATE} from './config/initialConfigState.es';
import {BUILDER_INITIAL_STATE, initState} from './config/initialState.es';
import AutoSaveProvider from './hooks/useAutoSave.es';
import {ToastProvider} from './hooks/useToast.es';
import FormBuilder from './pages/FormBuilder.es';
import Report from './pages/Report';
import RuleBuilder from './pages/RuleBuilder.es';
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
export default function App({autosaveInterval, autosaveURL, ...otherProps}) {
	const {config, state} = parseProps(otherProps);
	const {defaultLanguageId} = state;

	return (
		<DndProvider backend={HTML5Backend} context={window}>
			<ConfigProvider
				config={config}
				initialConfig={INITIAL_CONFIG_STATE}
			>
				<ClayModalProvider>
					<FormProvider
						init={initState}
						initialState={{
							...BUILDER_INITIAL_STATE,
							defaultLanguageId,
							editingLanguageId: defaultLanguageId,
						}}
						reducers={[
							dragAndDropReducer,
							elementSetReducer,
							fieldEditableReducer,
							formInfoReducer,
							historyReducer,
							languageReducer,
							objectFieldsReducer,
							pageReducer,
							pagesStructureReducer,
							rulesReducer,
							sidebarReducer,
						]}
						value={state}
					>
						<KeyboardDNDContextProvider>
							<ToastProvider>
								<Router>
									<AutoSaveProvider
										interval={autosaveInterval}
										url={autosaveURL}
									>
										<Route
											component={NavigationBar}
											path="/"
										/>

										<Suspense
											fallback={<ClayLoadingIndicator />}
										>
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
										</Suspense>
									</AutoSaveProvider>
								</Router>
							</ToastProvider>
						</KeyboardDNDContextProvider>
					</FormProvider>
				</ClayModalProvider>
			</ConfigProvider>
		</DndProvider>
	);
}

App.displayName = 'App';
