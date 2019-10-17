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
import React, {useContext} from 'react';

import {AppContext} from '../../AppContext.es';
import Button from '../../components/button/Button.es';
import ListView from '../../components/list-view/ListView.es';
import {confirmDelete} from '../../utils/client.es';

export default ({
	match: {
		params: {dataDefinitionId}
	}
}) => {
	const {basePortletURL} = useContext(AppContext);

	const getItemURL = item =>
		Liferay.Util.PortletURL.createRenderURL(basePortletURL, {
			dataDefinitionId,
			dataLayoutId: item.id,
			mvcRenderCommandName: '/edit_form_view'
		});

	const handleEditItem = item => {
		const itemURL = getItemURL(item);

		Liferay.Util.navigate(itemURL);
	};

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

	const addURL = Liferay.Util.PortletURL.createRenderURL(basePortletURL, {
		dataDefinitionId,
		mvcRenderCommandName: '/edit_form_view'
	});

	return (
		<ListView
			actions={[
				{
					action: item => Promise.resolve(handleEditItem(item)),
					name: Liferay.Language.get('edit')
				},
				{
					action: confirmDelete('/o/data-engine/v1.0/data-layouts/'),
					name: Liferay.Language.get('delete')
				}
			]}
			addButton={() => (
				<Button
					className="nav-btn nav-btn-monospaced navbar-breakpoint-down-d-none"
					onClick={() => Liferay.Util.navigate(addURL)}
					symbol="plus"
					tooltip={Liferay.Language.get('new-form-view')}
				/>
			)}
			columns={COLUMNS}
			emptyState={{
				button: () => (
					<Button
						displayType="secondary"
						onClick={() => Liferay.Util.navigate(addURL)}
					>
						{Liferay.Language.get('new-form-view')}
					</Button>
				),
				description: Liferay.Language.get(
					'create-one-or-more-forms-to-display-the-data-held-in-your-data-object'
				),
				title: Liferay.Language.get('there-are-no-form-views-yet')
			}}
			endpoint={`/o/data-engine/v1.0/data-definitions/${dataDefinitionId}/data-layouts`}
		>
			{item => ({
				dataDefinitionId,
				dateCreated: moment(item.dateCreated).fromNow(),
				dateModified: moment(item.dateModified).fromNow(),
				id: item.id,
				name: <a href={getItemURL(item)}>{item.name.en_US}</a>
			})}
		</ListView>
	);
};
