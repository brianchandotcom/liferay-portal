/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ClayInput} from '@clayui/form';
import ClayIcon from '@clayui/icon';
import {ManagementToolbar} from 'frontend-js-components-web';
import React from 'react';

import './ManagementToolbarSearch.scss';

interface IProps {
	query: string;
	setQuery: (value: string) => void;
}

export function ManagementToolbarSearch({query, setQuery}: IProps) {
	return (
		<ManagementToolbar.Search onSubmit={(event) => event.preventDefault()}>
			<ClayInput.Group>
				<ClayInput.GroupItem>
					<ClayInput
						aria-label={Liferay.Language.get('search')}
						className="form-control input-group-inset input-group-inset-after"
						onChange={({target}) => {
							setQuery(target.value);
						}}
						placeholder={Liferay.Language.get('search')}
						type="text"
						value={query}
					/>

					<ClayInput.GroupInsetItem after tag="span">
						<ClayIcon
							className="lfr-object__object-view-management-toolbar-search-icon"
							symbol="search"
						/>
					</ClayInput.GroupInsetItem>
				</ClayInput.GroupItem>
			</ClayInput.Group>
		</ManagementToolbar.Search>
	);
}
