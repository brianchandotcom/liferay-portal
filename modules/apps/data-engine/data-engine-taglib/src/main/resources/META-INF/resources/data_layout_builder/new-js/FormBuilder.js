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
import classNames from 'classnames';
import {
	Pages,
	useConfig,
	useForm,
	useFormState,
} from 'data-engine-js-components-web';
import {EVENT_TYPES as CORE_EVENT_TYPES} from 'data-engine-js-components-web/js/core/actions/eventTypes.es';
import fieldDelete from 'data-engine-js-components-web/js/core/thunks/fieldDelete.es';
import React, {useContext, useEffect, useMemo, useState} from 'react';

import MultiPanelSidebar from '../js/components/sidebar/MultiPanelSidebar.es';
import initializeSidebarConfig from '../js/components/sidebar/initializeSidebarConfig.es';
import DragLayer from '../js/drag-and-drop/DragLayer.es';
import {getItem} from '../js/utils/client.es';
import {EVENT_TYPES} from './eventTypes';

export const FormBuilder = () => {
	const dispatch = useForm();
	const [{onClose}, modalDispatch] = useContext(ModalContext);
	const {
		focusedField,
		rules,
		sidebarPanels: initialSidebarPanels,
	} = useFormState();

	const {
		allowFieldSets,
		contentType,
		dataDefinitionId,
		groupId,
		portletNamespace,
	} = useConfig();

	const [{sidebarOpen, sidebarPanelId}, setSidebarState] = useState({
		sidebarOpen: false,
		sidebarPanelId: 'fields',
	});

	const {panels, sidebarPanels, sidebarVariant} = useMemo(
		() =>
			initializeSidebarConfig({
				portletNamespace,
				sidebarPanels: initialSidebarPanels,
			}),
		[initialSidebarPanels, portletNamespace]
	);

	/**
	 * Load fieldSets
	 */
	useEffect(() => {
		if (allowFieldSets && contentType) {
			let globalFieldSetsPromise = [];

			if (groupId) {
				globalFieldSetsPromise = getItem(
					`/o/data-engine/v2.0/sites/${groupId}/data-definitions/by-content-type/${contentType}`
				);
			}

			const groupFieldSetsPromise = getItem(
				`/o/data-engine/v2.0/data-definitions/by-content-type/${contentType}`
			);

			const fetchFieldSets = async () => {
				try {
					const [
						{items: globalFieldSets = []},
						{items: groupFieldSets = []},
					] = await Promise.all([
						globalFieldSetsPromise,
						groupFieldSetsPromise,
					]);
					const fieldSets = [
						...globalFieldSets,
						...groupFieldSets,
					].filter(({id}) => id !== parseInt(dataDefinitionId, 10));

					dispatch({
						payload: {fieldSets},
						type: EVENT_TYPES.FIELD_SET.UPDATE_LIST,
					});
				}
				catch (error) {
					if (process.env.NODE_ENV === 'development') {
						console.warn(
							`[DataEngineFormBuilder] fetchFieldSets promise rejected: ${error}`
						);
					}
				}
			};

			fetchFieldSets();
		}
	}, [allowFieldSets, contentType, dataDefinitionId, dispatch, groupId]);

	/**
	 * Adjusts alert messages size according to sidebarOpen state
	 */
	useEffect(() => {
		const alerts = document.querySelector(
			'.data-engine-form-builder-messages'
		);

		if (alerts) {
			alerts.className = classNames('data-engine-form-builder-messages', {
				'data-engine-form-builder-messages--collapsed': sidebarOpen,
			});
		}
	}, [sidebarOpen]);

	/**
	 * Opens the sidebar whenever a field is focused
	 */
	useEffect(() => {
		const hasFocusedField = Object.keys(focusedField).length > 0;

		if (hasFocusedField) {
			setSidebarState(({sidebarPanelId}) => ({
				sidebarOpen: true,
				sidebarPanelId,
			}));
		}
	}, [focusedField]);

	return (
		<div
			className={classNames(
				'data-engine-form-builder ddm-form-builder pb-5',
				{
					'ddm-form-builder--sidebar-open': sidebarOpen,
				}
			)}
		>
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
							currentPanelId={sidebarPanelId}
							onChange={setSidebarState}
							open={sidebarOpen}
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
