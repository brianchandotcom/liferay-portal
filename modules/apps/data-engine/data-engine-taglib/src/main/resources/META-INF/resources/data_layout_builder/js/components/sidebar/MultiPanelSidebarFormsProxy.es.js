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
import {getConnectedReactComponentAdapter} from 'dynamic-data-mapping-form-renderer/js/util/ReactComponentAdapter.es';
import React, {useState} from 'react';
import {DndProvider} from 'react-dnd';
import {HTML5Backend} from 'react-dnd-html5-backend';

import DragLayer from '../../drag-and-drop/DragLayer.es';
import MultiPanelSidebar from './MultiPanelSidebar.es';

const FormsMultiPanelMock = {
	panels: [['fields']],
	sidebarPanels: {
		fields: {
			icon: 'forms',
			isLink: false,
			label: 'Builder',
			pluginEntryPoint:
				'data-engine-taglib@3.0.0/data_layout_builder/js/plugins/forms-field-sidebar/index.es',
			sidebarPanelId: 'fields',
		},
	},
	sidebarVariant: 'light',
};

const MultiPanelSidebarFormsProxy = React.forwardRef(
	({
		activePage,
		dataProviderInstanceParameterSettingsURL,
		dataProviderInstancesURL,
		defaultLanguageId,
		editingLanguageId,
		fieldTypes,
		focusedField,
		functionsMetadata,
		functionsURL,
		instance,
		onChange,
		pages,
		rules,
		spritemap,
	}) => {
		const [{currentPanelId, open}, setStatus] = useState({
			currentPanelId: 'fields',
			open: true,
		});

		return (
			<DndProvider backend={HTML5Backend} context={window}>
				<ClayIconSpriteContext.Provider value={spritemap}>
					<FormsSidebarPluginContext.Provider
						value={{
							activePage,
							dataProviderInstanceParameterSettingsURL,
							dataProviderInstancesURL,
							defaultLanguageId,
							dispatch: (type, payload) => {
								instance.context.dispatch(type, payload);
							},
							editingLanguageId,
							fieldTypes,
							focusedCustomObjectField: {},
							focusedField,
							functionsMetadata,
							functionsURL,
							pages,
							rules,
						}}
					>
						<DragLayer></DragLayer>
						<MultiPanelSidebar
							createPlugin={({
								panel,
								sidebarOpen,
								sidebarPanelId,
							}) => ({
								panel,
								sidebarOpen,
								sidebarPanelId,
							})}
							currentPanelId={currentPanelId}
							onChange={({sidebarOpen, sidebarPanelId}) => {
								onChange(sidebarOpen);
								setStatus({
									currentPanelId: sidebarPanelId,
									open: sidebarOpen,
								});
							}}
							open={open}
							panels={FormsMultiPanelMock.panels}
							sidebarPanels={FormsMultiPanelMock.sidebarPanels}
							variant={FormsMultiPanelMock.sidebarVariant}
						/>
					</FormsSidebarPluginContext.Provider>
				</ClayIconSpriteContext.Provider>
			</DndProvider>
		);
	}
);

export const FormsSidebarPluginContext = React.createContext({});

MultiPanelSidebarFormsProxy.displayName = 'MultiPanelSidebarFormsProxy';

export const ReactMultiPanelSidebarAdapter = getConnectedReactComponentAdapter(
	MultiPanelSidebarFormsProxy
);
