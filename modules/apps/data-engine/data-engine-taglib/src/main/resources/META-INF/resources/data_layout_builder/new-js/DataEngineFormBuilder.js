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

import ClayLayout from '@clayui/layout';
import {Context as ModalContext} from '@clayui/modal';
import {
	Pages,
	useConfig,
	useForm,
	useFormState,
} from 'dynamic-data-mapping-form-renderer';
import {EVENT_TYPES as CORE_EVENT_TYPES} from 'dynamic-data-mapping-form-renderer/js/core/actions/eventTypes.es';
import fieldDelete from 'dynamic-data-mapping-form-renderer/js/core/thunks/fieldDelete.es';
import React, {useContext, useMemo, useState} from 'react';

import MultiPanelSidebar from '../js/components/sidebar/MultiPanelSidebar.es';
import initializeSidebarConfig from '../js/components/sidebar/initializeSidebarConfig.es';
import DragLayer from '../js/drag-and-drop/DragLayer.es';

const SIDEBAR_INITIAL_STATE = {
	sidebarOpen: true,
	sidebarPanelId: 'fields',
};

const DataEngineFormBuilder = () => {
	const dispatch = useForm();
	const [{onClose}, modalDispatch] = useContext(ModalContext);
	const {rules, sidebarPanels: initialSidebarPanels} = useFormState();

	const {portletNamespace} = useConfig();

	const [sidebarState, setSidebarState] = useState(SIDEBAR_INITIAL_STATE);

	const {panels, sidebarPanels, sidebarVariant} = useMemo(
		() =>
			initializeSidebarConfig({
				portletNamespace,
				sidebarPanels: initialSidebarPanels,
			}),
		[initialSidebarPanels, portletNamespace]
	);

	return (
		<div className="data-engine-form-builder ddm-form-builder pb-5">
			<ClayLayout.Sheet>
				<div className="ddm-form-builder-wrapper">
					<div className="container ddm-form-builder">
						<DragLayer />
						<Pages
							editable={true}
							fieldActions={[
								{
									action: (payload) =>
										dispatch({
											payload,
											type:
												CORE_EVENT_TYPES.FIELD
													.DUPLICATE,
										}),
									label: Liferay.Language.get('duplicate'),
								},
								{
									action: (payload) =>
										dispatch(
											fieldDelete({
												action: {
													payload,
													type:
														CORE_EVENT_TYPES.FIELD
															.DELETE,
												},
												modalDispatch,
												onClose,
												rules,
											})
										),
									label: Liferay.Language.get('delete'),
								},
							]}
						/>
						<MultiPanelSidebar
							createPlugin={({
								panel,
								sidebarOpen,
								sidebarPanelId,
							}) => ({
								dispatch,
								panel,
								sidebarOpen,
								sidebarPanelId,
							})}
							currentPanelId={sidebarState.sidebarPanelId}
							onChange={({sidebarOpen, sidebarPanelId}) =>
								setSidebarState({
									sidebarOpen,
									sidebarPanelId,
								})
							}
							open={sidebarState.sidebarOpen}
							panels={panels}
							sidebarPanels={sidebarPanels}
							variant={sidebarVariant}
						/>
					</div>
				</div>
			</ClayLayout.Sheet>
		</div>
	);
};

export default DataEngineFormBuilder;
