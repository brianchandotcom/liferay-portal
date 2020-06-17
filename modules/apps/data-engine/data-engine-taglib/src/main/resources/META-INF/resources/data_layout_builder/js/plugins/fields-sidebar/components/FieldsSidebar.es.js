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

import ClayForm from '@clayui/form';
import classNames from 'classnames';
import React, {useContext, useState} from 'react';

import AppContext from '../../../AppContext.es';
import Sidebar from '../../../components/sidebar/Sidebar.es';
import FieldsSidebarBody from './FieldsSidebarBody.es';
import FieldsSidebarSettingsBody from './FieldsSidebarSettingsBody.es';
import FieldsSidebarSettingsHeader from './FieldsSidebarSettingsHeader.es';

export default function ({title}) {
	const [{focusedCustomObjectField, focusedField}] = useContext(AppContext);
	const [keywords, setKeywords] = useState('');

	const hasFocusedField = Object.keys(focusedField).length > 0;
	const hasFocusedCustomObjectField =
		Object.keys(focusedCustomObjectField).length > 0;

	const displaySettings = hasFocusedCustomObjectField || hasFocusedField;

	return (
		<Sidebar
			className={classNames({['display-settings']: displaySettings})}
		>
			<Sidebar.Header>
				<Sidebar.Title title={title} />

				{displaySettings ? (
					<FieldsSidebarSettingsHeader />
				) : (
					<ClayForm onSubmit={(event) => event.preventDefault()}>
						<Sidebar.SearchInput
							onSearch={(keywords) => setKeywords(keywords)}
							searchText={keywords}
						/>
					</ClayForm>
				)}
			</Sidebar.Header>

			<Sidebar.Body>
				{displaySettings ? (
					<FieldsSidebarSettingsBody />
				) : (
					<FieldsSidebarBody
						keywords={keywords}
						setKeywords={setKeywords}
					/>
				)}
			</Sidebar.Body>
		</Sidebar>
	);
}
