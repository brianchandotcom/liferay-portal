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
import pathToRegexp from 'path-to-regexp';
import React, {useState, useEffect} from 'react';

import {useRouter} from '../../hooks/useRouter.es';
import {parse, stringify} from '../router/queryString.es';

const SearchField = props => {
	const {
		history,
		location,
		match: {params, path}
	} = useRouter();
	const query = parse(location.search);
	const {search = ''} = query;

	const {disabled, placeholder = Liferay.Language.get('search-for')} = props;

	const spritemap = `${Liferay.ThemeDisplay.getPathThemeImages()}/lexicon/icons.svg`;

	const [searchValue, setSearchValue] = useState('');
	const [redirect, setRedirect] = useState(false);

	useEffect(() => {
		setSearchValue(search);
	}, [search]);

	const handleChange = event => {
		setSearchValue(event.target.value);
	};

	const handleSubmit = event => {
		event.preventDefault();
		setRedirect(true);
	};

	if (redirect) {
		setRedirect(false);

		const pathname = pathToRegexp.compile(path)({
			...params,
			page: 1
		});

		history.push({
			pathname,
			search: stringify({...query, search: searchValue})
		});
	}

	return (
		<ClayManagementToolbar.Search
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
							className="navbar-breakpoint-d-none"
							displayType="unstyled"
							spritemap={spritemap}
							symbol="times"
						/>

						<ClayButtonWithIcon
							displayType="unstyled"
							spritemap={spritemap}
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
