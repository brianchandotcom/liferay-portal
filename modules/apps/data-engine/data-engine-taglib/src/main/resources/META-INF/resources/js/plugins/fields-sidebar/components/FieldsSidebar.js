/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayForm from '@clayui/form';
import {useFormState} from 'data-engine-js-components-web';
import React, {useState} from 'react';

import Sidebar from '../../../components/sidebar/Sidebar.es';
import FieldsSidebarBody from './FieldsSidebarBody';
import SidebarFieldSettings from './SidebarFieldSettings';

export function FieldsSidebar({title}) {
	const {focusedField} = useFormState();

	return Object.keys(focusedField).length ? (
		<SidebarFieldSettings field={focusedField} />
	) : (
		<FieldListSidebar title={title} />
	);
}

const FieldListSidebar = ({title}) => {
	const [searchTerm, setSearchTerm] = useState('');
	const [searchClicked, setSearchClicked] = useState(null);

	return (
		<Sidebar>
			<Sidebar.Header>
				<Sidebar.Title className="mb-3" title={title} />

				<div
					aria-live="polite"
					className="sr-only"
					id="screenReaderSearchResult"
				></div>

				<ClayForm onSubmit={(event) => event.preventDefault()}>
					<Sidebar.SearchInput
						onSearch={(keywords) => setSearchTerm(keywords)}
						searchText={searchTerm}
						setSearchClicked={setSearchClicked}
					/>
				</ClayForm>
			</Sidebar.Header>

			<Sidebar.Body>
				<FieldsSidebarBody
					keywords={searchTerm}
					searchClicked={searchClicked}
					setKeywords={setSearchTerm}
					setSearchClicked={setSearchClicked}
				/>
			</Sidebar.Body>
		</Sidebar>
	);
};
