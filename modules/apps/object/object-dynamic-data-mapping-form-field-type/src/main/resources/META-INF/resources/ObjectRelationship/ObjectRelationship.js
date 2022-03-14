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

import ClayAutocomplete from '@clayui/autocomplete';
import {useResource} from '@clayui/data-provider';
import ClayDropDown from '@clayui/drop-down';
import {useDebounce} from '@clayui/shared';
import {ReactFieldBase as FieldBase} from 'dynamic-data-mapping-form-field-type';
import {fetch} from 'frontend-js-web';
import React, {useEffect, useRef, useState} from 'react';

const NETWORK_STATUS_LOADING = 1;
const NETWORK_STATUS_UNUSED = 4;

const LoadingWithDebounce = ({loading, render}) => {
	const debouncedLoadingChange = useDebounce(loading, 500);

	if (loading || debouncedLoadingChange) {
		return (
			<ClayDropDown.Item className="disabled">
				{Liferay.Language.get('loading')}
			</ClayDropDown.Item>
		);
	}

	return render;
};

export function ObjectRelationship({
	apiURL,
	id,
	initialLabel,
	inputName,
	labelKey = 'label',
	name,
	onBlur = () => {},
	onChange,
	onFocus = () => {},
	placeholder = Liferay.Language.get('search'),
	readOnly,
	required,
	value,
	valueKey = 'value',
	...otherProps
}) {
	const [active, setActive] = useState(false);
	const [networkStatus, setNetworkStatus] = useState(NETWORK_STATUS_LOADING);
	const [search, setSearch] = useState(initialLabel || '');
	const autocompleteRef = useRef();
	const dropdownRef = useRef();

	useEffect(() => {
		function handleClick(event) {
			if (
				autocompleteRef.current.contains(event.target) ||
				event.target === dropdownRef.current.parentElement ||
				(dropdownRef.current &&
					dropdownRef.current.contains(event.target))
			) {
				return;
			}

			setActive(false);
		}
		if (active) {
			document.addEventListener('mousedown', handleClick);
		}

		return () => {
			document.removeEventListener('mousedown', handleClick);
		};
	}, [active]);

	const {resource} = useResource({
		fetch,
		fetchPolicy: 'cache-first',
		link: apiURL,
		onNetworkStatusChange: setNetworkStatus,
		variables: {
			page: 1,
			pageSize: 10,
			search,
		},
	});

	const loading = networkStatus < NETWORK_STATUS_UNUSED;

	return (
		<FieldBase
			name={name}
			onChange={onChange}
			readOnly={readOnly}
			required={required}
			value={value}
			{...otherProps}
		>
			<ClayAutocomplete ref={autocompleteRef}>
				<input id={id} name={name} type="hidden" value={value || ''} />

				<ClayAutocomplete.Input
					name={inputName}
					onBlur={onBlur}
					onChange={(event) => {
						const currentSearch = event.target.value;

						if (currentSearch === '') {
							onChange({
								target: {
									value: '',
								},
							});
						}
						else {
							const searchedItem = resource?.items?.find(
								(item) =>
									String(item[labelKey]) === currentSearch
							);

							onChange({
								target: {
									value: searchedItem
										? String(searchedItem[valueKey])
										: null,
								},
							});
						}

						setSearch(currentSearch);
					}}
					onFocus={(event) => {
						onFocus(event);
						setActive(true);
					}}
					onKeyUp={(event) => {
						setActive(event.keyCode !== 27);
					}}
					placeholder={placeholder}
					readOnly={readOnly}
					required={required}
					value={search}
				/>

				<ClayAutocomplete.DropDown
					active={active && (readOnly ? false : !!resource)}
				>
					<div ref={dropdownRef}>
						<ClayDropDown.ItemList>
							<LoadingWithDebounce
								loading={loading}
								render={
									<>
										{resource?.items?.length === 0 && (
											<ClayDropDown.Item className="disabled">
												{Liferay.Language.get(
													'no-results-found'
												)}
											</ClayDropDown.Item>
										)}
										{resource?.items?.map((item) => (
											<ClayAutocomplete.Item
												key={item.id}
												match={String(search)}
												onClick={(event) => {
													onChange(
														event,
														String(item[valueKey])
													);
													setActive(false);
													setSearch(
														String(item[labelKey])
													);
												}}
												value={String(item[labelKey])}
											/>
										))}
									</>
								}
							/>
						</ClayDropDown.ItemList>
					</div>
				</ClayAutocomplete.DropDown>

				{loading && <ClayAutocomplete.LoadingIndicator />}
			</ClayAutocomplete>
		</FieldBase>
	);
}

export default ObjectRelationship;
