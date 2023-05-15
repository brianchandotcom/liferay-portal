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

import {ClayButtonWithIcon} from '@clayui/button';
import {ClayInput} from '@clayui/form';
import React from 'react';

interface ISearchInterface {
	onSearch: Function;
	query: string;
}

const Search = ({onSearch, query}: ISearchInterface) => (
	<ClayInput.Group>
		<ClayInput.GroupItem>
			<ClayInput
				insetAfter
				onChange={(event) => onSearch(event.target.value)}
				placeholder={Liferay.Language.get('search')}
				type="text"
				value={query}
			/>

			<ClayInput.GroupInsetItem after tag="span">
				<ClayButtonWithIcon
					aria-label={Liferay.Language.get('search')}
					displayType="unstyled"
					symbol="search"
				/>
			</ClayInput.GroupInsetItem>
		</ClayInput.GroupItem>
	</ClayInput.Group>
);

export default Search;
