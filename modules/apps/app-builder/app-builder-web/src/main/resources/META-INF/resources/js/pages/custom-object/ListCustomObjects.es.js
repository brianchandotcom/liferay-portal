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

import React, {useContext, useEffect, useRef, useState} from 'react';

import {AppNavigationBar} from '../../App.es';
import {AppContext} from '../../AppContext.es';
import Button from '../../components/button/Button.es';
import ControlMenu from '../../components/control-menu/ControlMenu.es';
import ListObjects from '../../components/list-objects/ListObjects.es';
import {useKeyDown} from '../../hooks/index.es';
import isClickOutside from '../../utils/clickOutside.es';
import {addItem, confirmDelete} from '../../utils/client.es';
import CustomObjectPopover from './CustomObjectPopover.es';

export default ({history}) => {
	const {basePortletURL} = useContext(AppContext);
	const addButtonRef = useRef();
	const defaultLanguageId = Liferay.ThemeDisplay.getDefaultLanguageId();
	const emptyStateButtonRef = useRef();
	const popoverRef = useRef();

	const [alignElement, setAlignElement] = useState(addButtonRef.current);
	const [isPopoverVisible, setPopoverVisible] = useState(false);

	const [
		customObjectPermissionsModalState,
		setCustomObjectPermissionsModalState,
	] = useState({
		dataDefinitionId: null,
		endpoint: null,
	});

	const onClickAddButton = ({currentTarget}) => {
		setAlignElement(currentTarget);

		if (isPopoverVisible && alignElement !== currentTarget) {
			return;
		}

		setPopoverVisible(!isPopoverVisible);
	};

	const onCancel = () => setPopoverVisible(false);

	const onSubmit = ({isAddFormView, name}) => {
		const addURL = `/o/data-engine/v2.0/data-definitions/by-content-type/app-builder`;

		addItem(addURL, {
			availableLanguageIds: [defaultLanguageId],
			dataDefinitionFields: [],
			defaultLanguageId,
			name: {
				[defaultLanguageId]: name,
			},
		}).then(({id}) => {
			if (isAddFormView) {
				Liferay.Util.navigate(
					Liferay.Util.PortletURL.createRenderURL(basePortletURL, {
						dataDefinitionId: id,
						mvcRenderCommandName: '/edit_form_view',
						newCustomObject: true,
					})
				);
			}
			else {
				history.push(`/custom-object/${id}/form-views/`);
			}
		});
	};

	useEffect(() => {
		const handler = ({target}) => {
			const isOutside = isClickOutside(
				target,
				addButtonRef.current,
				emptyStateButtonRef.current,
				popoverRef.current
			);

			if (isOutside) {
				setPopoverVisible(false);
			}
		};

		window.addEventListener('click', handler);

		return () => window.removeEventListener('click', handler);
	}, [addButtonRef, emptyStateButtonRef, popoverRef]);

	useKeyDown(() => {
		if (isPopoverVisible) {
			setPopoverVisible(false);
		}
	}, 27);

	const listViewProps = {
		actions: [
			{
				action: ({id}) =>
					Promise.resolve(
						history.push(`/custom-object/${id}/form-views`)
					),
				name: Liferay.Language.get('form-views'),
			},
			{
				action: ({id}) =>
					Promise.resolve(
						history.push(`/custom-object/${id}/table-views`)
					),
				name: Liferay.Language.get('table-views'),
			},
			{
				action: ({id}) =>
					Promise.resolve(history.push(`/custom-object/${id}/apps`)),
				name: Liferay.Language.get('apps'),
			},
			{
				name: 'divider',
			},
			{
				action: ({id}) =>
					Promise.resolve(
						setCustomObjectPermissionsModalState((prevState) => ({
							...prevState,
							dataDefinitionId: id,
						}))
					),
				name: Liferay.Language.get('app-permissions'),
			},
			{
				name: 'divider',
			},
			{
				action: confirmDelete('/o/data-engine/v2.0/data-definitions/'),
				name: Liferay.Language.get('delete'),
			},
		],
		addButton: () => (
			<div ref={addButtonRef}>
				<Button
					className="nav-btn nav-btn-monospaced"
					onClick={onClickAddButton}
					symbol="plus"
					tooltip={Liferay.Language.get('new-custom-object')}
				/>
			</div>
		),
		emptyState: {
			button: () => (
				<Button
					displayType="secondary"
					onClick={onClickAddButton}
					ref={emptyStateButtonRef}
				>
					{Liferay.Language.get('new-custom-object')}
				</Button>
			),
			description: Liferay.Language.get(
				'custom-objects-define-the-types-of-data-your-business-application-needs'
			),
			title: Liferay.Language.get('there-are-no-custom-objects-yet'),
		},
		endpoint: `/o/data-engine/v2.0/data-definitions/by-content-type/app-builder`,
	};

	return (
		<>
			<ControlMenu
				title={Liferay.Language.get(
					'javax.portlet.title.com_liferay_app_builder_web_internal_portlet_ObjectsPortlet'
				)}
			/>

			<AppNavigationBar />

			<ListObjects
				customObjectPermissionsModalState={
					customObjectPermissionsModalState
				}
				listViewProps={listViewProps}
				objectType="custom-object"
				setCustomObjectPermissionsModalState={
					setCustomObjectPermissionsModalState
				}
			/>

			<CustomObjectPopover
				alignElement={alignElement}
				onCancel={onCancel}
				onSubmit={onSubmit}
				ref={popoverRef}
				visible={isPopoverVisible}
			/>
		</>
	);
};
