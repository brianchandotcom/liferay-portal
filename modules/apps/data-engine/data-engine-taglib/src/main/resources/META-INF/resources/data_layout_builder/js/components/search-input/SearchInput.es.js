/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ClayButtonWithIcon} from '@clayui/button';
import {ClayInput} from '@clayui/form';
import ClayIcon from '@clayui/icon';
import React, {useEffect, useRef, useState} from 'react';

const SearchInput = React.forwardRef(
	({onChange = () => {}, setSearchClicked, searchText = ''}, ref) => {
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
						onBlur={() => setSearchClicked(false)}
						onChange={({target: {value}}) => {
							setValue(value);
							onChange(value);
						}}
						onFocus={() => setSearchClicked(true)}
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
