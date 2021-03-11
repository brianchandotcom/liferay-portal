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

import ClayButton, {ClayButtonWithIcon} from '@clayui/button';
import ListView from 'data-engine-js-components-web/js/components/list-view/ListView.es';
import {confirmDelete} from 'data-engine-js-components-web/js/utils/client.es';
import {getLocalizedValue} from 'data-engine-js-components-web/js/utils/lang.es';
import React, {useContext} from 'react';

import {AppContext} from '../../AppContext.es';
import {fromNow} from '../../utils/time.es';

const queryFields = ['dateCreated', 'dateModified', 'id', 'name'].join(',');

export default ({
	defaultLanguageId,
	history,
	match: {
		params: {dataDefinitionId},
	},
}) => {
	const {basePortletURL} = useContext(AppContext);

	const getItemURL = (item) =>
		Liferay.Util.PortletURL.createRenderURL(basePortletURL, {
			dataDefinitionId,
			dataLayoutId: item.id,
			mvcRenderCommandName: '/app_builder/edit_form_view',
		});

	const handleEditItem = (item) => {
		const itemURL = getItemURL(item);

		Liferay.Util.navigate(itemURL);
	};

	const COLUMNS = [
		{
			key: 'name',
			sortable: true,
			value: Liferay.Language.get('name'),
		},
		{
			key: 'dateCreated',
			sortable: true,
			value: Liferay.Language.get('created-date'),
		},
		{
			asc: false,
			key: 'dateModified',
			sortable: true,
			value: Liferay.Language.get('modified-date'),
		},
		{
			key: 'id',
			value: Liferay.Language.get('id'),
		},
	];

	const addURL = Liferay.Util.PortletURL.createRenderURL(basePortletURL, {
		dataDefinitionId,
		mvcRenderCommandName: '/app_builder/edit_form_view',
	});

	return (
		<ListView
			actions={[
				{
					action: (item) => Promise.resolve(handleEditItem(item)),
					name: Liferay.Language.get('edit'),
				},
				{
					action: confirmDelete('/o/data-engine/v2.0/data-layouts/'),
					name: Liferay.Language.get('delete'),
				},
			]}
			addButton={() => (
				<ClayButtonWithIcon
					className="nav-btn nav-btn-monospaced"
					onClick={() => Liferay.Util.navigate(addURL)}
					symbol="plus"
					title={Liferay.Language.get('new-form-view')}
				/>
			)}
			columns={COLUMNS}
			emptyState={{
				button: () => (
					<ClayButton
						displayType="secondary"
						onClick={() => Liferay.Util.navigate(addURL)}
					>
						{Liferay.Language.get('new-form-view')}
					</ClayButton>
				),
				description: Liferay.Language.get(
					'create-one-or-more-forms-to-display-the-data-held-in-your-data-object'
				),
				title: Liferay.Language.get('there-are-no-form-views-yet'),
			}}
			endpoint={`/o/data-engine/v2.0/data-definitions/${dataDefinitionId}/data-layouts?fields=${queryFields}`}
			history={history}
		>
			{(item) => {
				const {dateCreated, dateModified, id, name} = item;

				return {
					dataDefinitionId,
					dateCreated: fromNow(dateCreated),
					dateModified: fromNow(dateModified),
					id,
					name: (
						<a href={getItemURL(item)}>
							{getLocalizedValue(defaultLanguageId, name)}
						</a>
					),
				};
			}}
		</ListView>
	);
};
