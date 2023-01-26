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
import React, {useState} from 'react';

export default function SearchField() {
	const [searchValue, setSearchValue] = useState('');

	const handleChange = (event) => {
		setSearchValue(event.target.value);
	};

	return (
		<>
			<ClayInput.Group small>
				<ClayInput.GroupItem>
					<ClayInput
						aria-label={Liferay.Language.get('search-for')}
						className="form-control input-group-inset input-group-inset-after"
						onChange={handleChange}
						placeholder={Liferay.Language.get('search-for')}
						type="text"
						value={searchValue}
					/>

					<ClayInput.GroupInsetItem after tag="span">
						<ClayButtonWithIcon
							displayType="unstyled"
							small
							symbol={searchValue ? 'times' : 'search'}
							title={Liferay.Language.get('clear')}
						/>
					</ClayInput.GroupInsetItem>
				</ClayInput.GroupItem>
			</ClayInput.Group>
			<hr className="separator" />
		</>
	);
}
