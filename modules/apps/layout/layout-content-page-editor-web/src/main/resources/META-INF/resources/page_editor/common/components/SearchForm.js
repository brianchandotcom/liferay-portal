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
import ClayForm, {ClayInput} from '@clayui/form';
import ClayIcon from '@clayui/icon';
import {debounce} from 'frontend-js-web';
import PropTypes from 'prop-types';
import React, {useRef, useState} from 'react';

let nextInputId = 0;

export default function SearchForm({className, onChange}) {
	const id = `pageEditorSearchFormInput${nextInputId++}`;
	const onChangeDebounce = useRef(debounce((value) => onChange(value), 100));
	const [searchValue, setSearchValue] = useState('');

	return (
		<ClayForm.Group className={className} role="search">
			<label className="sr-only" htmlFor={id}>
				{Liferay.Language.get('search-form')}
			</label>
			<ClayInput.Group>
				<ClayInput.GroupItem>
					<ClayInput
						id={id}
						insetAfter
						onChange={(event) => {
							setSearchValue(event.target.value);
							onChangeDebounce.current(event.target.value);
						}}
						placeholder={`${Liferay.Language.get('search')}...`}
						sizing="sm"
						value={searchValue}
					/>
					<ClayInput.GroupInsetItem after tag="span">
						{searchValue ? (
							<ClayButtonWithIcon
								borderless
								displayType="secondary"
								monospaced={false}
								onClick={() => {
									setSearchValue('');
									onChangeDebounce.current('');
								}}
								small
								symbol={searchValue ? 'times' : 'search'}
								title={Liferay.Language.get('clear')}
							/>
						) : (
							<ClayIcon
								className="mt-0 search-icon"
								symbol="search"
							/>
						)}
					</ClayInput.GroupInsetItem>
				</ClayInput.GroupItem>
			</ClayInput.Group>
		</ClayForm.Group>
	);
}

SearchForm.propTypes = {
	onChange: PropTypes.func.isRequired,
};
