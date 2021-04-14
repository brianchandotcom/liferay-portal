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

import ClayForm, {ClayInput} from '@clayui/form';
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
			<ClayInput
				id={id}
				onChange={(event) => {
					setSearchValue(event.target.value);
					onChangeDebounce.current(event.target.value);
				}}
				placeholder={`${Liferay.Language.get('search')}...`}
				sizing="sm"
				type="search"
				value={searchValue}
			/>
		</ClayForm.Group>
	);
}

SearchForm.propTypes = {
	onChange: PropTypes.func.isRequired,
};
