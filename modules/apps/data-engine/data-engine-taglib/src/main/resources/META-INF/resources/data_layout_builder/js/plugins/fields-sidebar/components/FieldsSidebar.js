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
import {useFormState} from 'data-engine-js-components-web';
import React, {useState} from 'react';

import Sidebar from '../../../components/sidebar/Sidebar.es';
import FieldsSidebarBody from './FieldsSidebarBody';
import SidebarFieldSettings from './SidebarFieldSettings';

export const FieldsSidebar = ({title}) => {
	const {focusedField} = useFormState();

	return Object.keys(focusedField).length ? (
		<SidebarFieldSettings field={focusedField} />
	) : (
		<FieldListSidebar title={title} />
	);
};

const FieldListSidebar = ({title}) => {
	const [searchTerm, setSearchTerm] = useState('');

	return (
		<Sidebar>
			<Sidebar.Header>
				<Sidebar.Title className="mb-3" title={title} />
				<ClayForm onSubmit={(event) => event.preventDefault()}>
					<Sidebar.SearchInput
						onSearch={(keywords) => setSearchTerm(keywords)}
						searchText={searchTerm}
					/>
				</ClayForm>
			</Sidebar.Header>
			<Sidebar.Body>
				<FieldsSidebarBody
					keywords={searchTerm}
					setKeywords={setSearchTerm}
				/>
			</Sidebar.Body>
		</Sidebar>
	);
};
