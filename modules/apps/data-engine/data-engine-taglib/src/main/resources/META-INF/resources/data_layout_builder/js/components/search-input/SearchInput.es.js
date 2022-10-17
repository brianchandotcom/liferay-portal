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
import ClayIcon from '@clayui/icon';
import React, {useEffect, useRef, useState} from 'react';

const SearchInput = React.forwardRef(
	({onChange = () => {}, searchText = ''}, ref) => {
		const [value, setValue] = useState(searchText);
		const fallbackRef = useRef(null);
		const searchInputRef = ref ? ref : fallbackRef;

		useEffect(() => {
			setValue(searchText);
		}, [searchText]);

		const onClear = () => {
			setValue('');
			onChange('');
			searchInputRef.current.focus();
		};

		let SearchButton = <ClayIcon className="mr-2 mt-0" symbol="search" />;

		if (value) {
			SearchButton = (
				<ClayButtonWithIcon
					aria-label={Liferay.Language.get('clear')}
					displayType="unstyled"
					key="clearButton"
					onClick={onClear}
					symbol="times"
				/>
			);
		}

		return (
			<ClayInput.Group>
				<ClayInput.GroupItem>
					<ClayInput
						aria-label={Liferay.Language.get('search')}
						className="input-group-inset input-group-inset-after"
						onChange={({target: {value}}) => {
							setValue(value);
							onChange(value);
						}}
						placeholder={`${Liferay.Language.get('search')}...`}
						ref={searchInputRef}
						type="text"
						value={value}
					/>

					<ClayInput.GroupInsetItem after>
						{SearchButton}
					</ClayInput.GroupInsetItem>
				</ClayInput.GroupItem>
			</ClayInput.Group>
		);
	}
);

export default SearchInput;
