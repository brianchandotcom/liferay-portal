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
import {useFormState} from 'data-engine-js-components-web';
import React, {useState} from 'react';

import Sidebar from '../../../../js/components/sidebar/Sidebar.es';
import FieldsSidebarBody from './FieldsSidebarBody';
import FieldsSidebarSettingsBody from './FieldsSidebarSettingsBody';
import FieldsSidebarSettingsHeader from './FieldsSidebarSettingsHeader';

export const FieldsSidebar = ({title}) => {
	const [keywords, setKeywords] = useState('');
	const {focusedField} = useFormState();

	const hasFocusedField = Object.keys(focusedField).length > 0;

	return (
		<Sidebar
			className={classNames({['display-settings']: hasFocusedField})}
		>
			<Sidebar.Header>
				<Sidebar.Title title={title} />

				{hasFocusedField ? (
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
				{hasFocusedField ? (
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
};
