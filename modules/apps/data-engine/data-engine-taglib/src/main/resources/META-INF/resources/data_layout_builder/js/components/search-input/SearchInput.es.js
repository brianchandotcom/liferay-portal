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
import React, {useEffect, useRef, useState} from 'react';

const SearchInput = React.forwardRef(
	(
		{
			clearButton = true,
			onChange = () => {},
			onSubmit = () => {},
			searchText = '',
			...restProps
		},
		ref
	) => {
		const [value, setValue] = useState(searchText);
		const searchInputRef = ref ? ref : useRef(null);

		useEffect(() => {
			setValue(searchText);
		}, [searchText]);

		const onClear = () => {
			setValue('');
			onChange('');
			searchInputRef.current.focus();
		};

		let SearchButton = (
			<ClayButtonWithIcon
				displayType="unstyled"
				key="searcgButton"
				onClick={_ => onSubmit(value)}
				symbol="search"
				{...restProps}
			/>
		);

		if (clearButton && value) {
			SearchButton = (
				<ClayButtonWithIcon
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
						{...restProps}
					/>

					<ClayInput.GroupInsetItem after>
						{SearchButton}
					</ClayInput.GroupInsetItem>
				</ClayInput.GroupItem>
			</ClayInput.Group>
		);
	}
);

const SearchInputWithForm = ({onSubmit = () => {}, ...restProps}) => {
	const [searchText, setSearchText] = useState('');

	const handleSubmit = value => {
		onSubmit(value.trim());
	};

	return (
		<ClayForm
			onSubmit={event => {
				event.preventDefault();
				handleSubmit(searchText);
			}}
		>
			<SearchInput
				clearButton={false}
				onChange={searchText => setSearchText(searchText)}
				onSubmit={handleSubmit}
				{...restProps}
			/>
		</ClayForm>
	);
};

export default SearchInput;

export {SearchInputWithForm};
