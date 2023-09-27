/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ClayButtonWithIcon} from '@clayui/button';
import {ClayInput} from '@clayui/form';
import {ManagementToolbar} from 'frontend-js-components-web';
import React, {useEffect, useState} from 'react';

import {useRouter} from '../../hooks/useRouter.es';
import {replaceHistory} from '../filter/util/filterUtil.es';
import {parse, stringify} from '../router/queryString.es';

const SearchField = ({
	disabled,
	placeholder = Liferay.Language.get('search-for'),
}) => {
	const routerProps = useRouter();

	const query = parse(routerProps.location.search);
	const {search = ''} = query;

	const [searchValue, setSearchValue] = useState('');

	useEffect(() => {
		setSearchValue(search);
	}, [search]);

	const handleChange = (event) => {
		setSearchValue(event.target.value);
	};

	const handleSubmit = (event) => {
		event.preventDefault();

		query.search = searchValue;

		replaceHistory(stringify(query), routerProps);
	};

	return (
		<ManagementToolbar.Search
			method="GET"
			onSubmit={handleSubmit}
			showMobile={true}
		>
			<ClayInput.Group>
				<ClayInput.GroupItem>
					<ClayInput
						aria-label="Search"
						className="form-control input-group-inset input-group-inset-after"
						disabled={disabled}
						onChange={handleChange}
						placeholder={placeholder}
						type="text"
						value={searchValue}
					/>

					<ClayInput.GroupInsetItem after tag="span">
						<ClayButtonWithIcon
							displayType="unstyled"
							symbol="search"
							type="submit"
						/>
					</ClayInput.GroupInsetItem>
				</ClayInput.GroupItem>
			</ClayInput.Group>
		</ManagementToolbar.Search>
	);
};

export default SearchField;
