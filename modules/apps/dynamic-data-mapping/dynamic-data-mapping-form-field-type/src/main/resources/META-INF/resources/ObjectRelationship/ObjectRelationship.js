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
import {FieldBase} from 'dynamic-data-mapping-form-field-type/FieldBase/ReactFieldBase.es';
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
	inputName,
	labelKey = 'label',
	initialLabel,
	initialValue,
	name,
	onBlur = () => {},
	onChange,
	onFocus = () => {},
	placeholder = Liferay.Language.get('search'),
	predefinedValue,
	readOnly,
	required,
	value,
	valueKey = 'value',
	...otherProps
}) {
	const [active, setActive] = useState(false);
	const [networkStatus, setNetworkStatus] = useState(NETWORK_STATUS_LOADING);
	const autocompleteRef = useRef();
	const dropdownRef = useRef();
	const mutatedRef = useRef(false);

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
			search: value,
		},
	});

	const loading = networkStatus < NETWORK_STATUS_UNUSED;

	value = mutatedRef.current ? value : initialValue || predefinedValue;

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
				<ClayAutocomplete.Input
					name={inputName}
					onBlur={onBlur}
					onChange={(event) => {
						mutatedRef.current = true;

						onChange(event, event.target.value);
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
					value={mutatedRef.current ? value : initialLabel}
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
												match={String(value)}
												onClick={(event) => {
													mutatedRef.current = true;

													onChange(
														event,
														String(item[valueKey])
													);
													setActive(false);
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
