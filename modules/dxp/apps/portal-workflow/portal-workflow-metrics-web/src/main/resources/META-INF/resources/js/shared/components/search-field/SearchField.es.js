/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 */

import {ClayButtonWithIcon} from '@clayui/button';
import {ClayInput} from '@clayui/form';
import ClayManagementToolbar from '@clayui/management-toolbar';
import React, {useEffect, useState} from 'react';

import {useRouter} from '../../hooks/useRouter.es';
import {replaceHistory} from '../filter/util/filterUtil.es';
import {parse, stringify} from '../router/queryString.es';

const SearchField = ({
	disabled,
	placeholder = Liferay.Language.get('search-for')
}) => {
	const routerProps = useRouter();

	const query = parse(routerProps.location.search);
	const {search = null} = query;

	const [searchValue, setSearchValue] = useState(null);

	useEffect(() => {
		setSearchValue(search);
	}, [search]);

	const handleChange = event => {
		setSearchValue(event.target.value);
	};

	const handleSubmit = event => {
		event.preventDefault();

		query.search = searchValue;

		replaceHistory(stringify(query), routerProps);
	};

	return (
		<ClayManagementToolbar.Search
			data-testid="searchFieldForm"
			method="GET"
			onSubmit={handleSubmit}
			showMobile={true}
		>
			<ClayInput.Group>
				<ClayInput.GroupItem>
					<ClayInput
						aria-label="Search"
						className="form-control input-group-inset input-group-inset-after"
						data-testid="searchField"
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
		</ClayManagementToolbar.Search>
	);
};

export default SearchField;
