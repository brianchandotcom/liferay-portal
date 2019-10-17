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

import moment from 'moment';
import {Link} from 'react-router-dom';
import React, {useContext, useEffect, useRef, useState} from 'react';

import {AppContext} from '../../AppContext.es';
import Button from '../../components/button/Button.es';
import ControlMenu from '../../components/control-menu/ControlMenu.es';
import ListView from '../../components/list-view/ListView.es';
import {useKeyDown} from '../../hooks/index.es';
import isClickOutside from '../../utils/clickOutside.es';
import {addItem} from '../../utils/client.es';
import {confirmDelete} from '../../utils/client.es';
import CustomObjectPopover from './CustomObjectPopover.es';

const COLUMNS = [
	{
		key: 'name',
		sortable: true,
		value: Liferay.Language.get('name')
	},
	{
		key: 'dateCreated',
		sortable: true,
		value: Liferay.Language.get('create-date')
	},
	{
		asc: false,
		key: 'dateModified',
		sortable: true,
		value: Liferay.Language.get('modified-date')
	}
];

export default ({history}) => {
	const {basePortletURL, siteId} = useContext(AppContext);
	const addButtonRef = useRef();
	const emptyStateButtonRef = useRef();
	const popoverRef = useRef();

	const [alignElement, setAlignElement] = useState(addButtonRef.current);
	const [isPopoverVisible, setPopoverVisible] = useState(false);

	const onClickAddButton = ({currentTarget}) => {
		setAlignElement(currentTarget);

		if (isPopoverVisible && alignElement !== currentTarget) {
			return;
		}

		setPopoverVisible(!isPopoverVisible);
	};

	const onCancel = () => setPopoverVisible(false);

	const onSubmit = ({isAddFormView, name}) => {
		const addURL = `/o/data-engine/v1.0/sites/${siteId}/data-definitions`;

		addItem(addURL, {
			dataDefinitionFields: [],
			name: {
				value: name
			}
		}).then(({id}) => {
			if (isAddFormView) {
				Liferay.Util.navigate(
					Liferay.Util.PortletURL.createRenderURL(basePortletURL, {
						dataDefinitionId: id,
						mvcRenderCommandName: '/edit_form_view',
						newCustomObject: true
					})
				);
			} else {
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

	return (
		<>
			<ControlMenu
				title={Liferay.Language.get(
					'javax.portlet.title.com_liferay_app_builder_web_internal_portlet_CustomObjectsPortlet'
				)}
				tooltip={Liferay.Language.get(
					'javax.portlet.description.com_liferay_app_builder_web_internal_portlet_CustomObjectsPortlet'
				)}
			/>

			<ListView
				actions={[
					{
						action: item =>
							Promise.resolve(
								history.push(
									`/custom-object/${item.id}/form-views`
								)
							),
						name: Liferay.Language.get('form-views')
					},
					{
						action: item =>
							Promise.resolve(
								history.push(
									`/custom-object/${item.id}/table-views`
								)
							),
						name: Liferay.Language.get('table-views')
					},
					{
						action: item =>
							Promise.resolve(
								history.push(`/custom-object/${item.id}/apps`)
							),
						name: Liferay.Language.get('apps')
					},
					{
						name: 'divider'
					},
					{
						action: confirmDelete(
							'/o/data-engine/v1.0/data-definitions/'
						),
						name: Liferay.Language.get('delete')
					}
				]}
				addButton={() => (
					<div ref={addButtonRef}>
						<Button
							className="nav-btn nav-btn-monospaced navbar-breakpoint-down-d-none"
							onClick={onClickAddButton}
							symbol="plus"
							tooltip={Liferay.Language.get('new-custom-object')}
						/>
					</div>
				)}
				columns={COLUMNS}
				emptyState={{
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
					title: Liferay.Language.get(
						'there-are-no-custom-objects-yet'
					)
				}}
				endpoint={`/o/data-engine/v1.0/sites/${siteId}/data-definitions`}
			>
				{item => ({
					dateCreated: moment(item.dateCreated).fromNow(),
					dateModified: moment(item.dateModified).fromNow(),
					id: item.id,
					name: (
						<Link to={`/custom-object/${item.id}/form-views`}>
							{item.name.en_US}
						</Link>
					)
				})}
			</ListView>

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
