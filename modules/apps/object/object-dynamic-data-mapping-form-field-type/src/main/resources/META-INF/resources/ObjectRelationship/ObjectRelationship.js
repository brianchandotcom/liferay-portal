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
import ClayDropDown from '@clayui/drop-down';
import {useDebounce} from '@clayui/shared';
import {
	FORM_EVENT_TYPES,
	useForm,
	useFormState,
} from 'data-engine-js-components-web';
import {ReactFieldBase as FieldBase} from 'dynamic-data-mapping-form-field-type';
import {fetch} from 'frontend-js-web';
import React, {useEffect, useMemo, useRef, useState} from 'react';

const HEADERS = new Headers({
	'Accept': 'application/json',
	'Content-Type': 'application/json',
});

const defaultLanguageId = Liferay.ThemeDisplay.getDefaultLanguageId();

async function fetchOptions(url) {
	const response = await fetch(url, {
		headers: HEADERS,
		method: 'GET',
	});

	return await response.json();
}

function getLabel(item, labelKey) {
	const objectLabel = item?.[labelKey];

	const label =
		typeof objectLabel === 'object'
			? objectLabel[defaultLanguageId]
			: objectLabel;

	return label ? String(label) : '';
}

function LoadingWithDebounce({loading, render}) {
	const debouncedLoadingChange = useDebounce(loading, 500);

	return loading || debouncedLoadingChange ? (
		<ClayDropDown.Item className="disabled">
			{Liferay.Language.get('loading')}
		</ClayDropDown.Item>
	) : (
		render
	);
}

export function ObjectRelationship({
	apiURL,
	fieldName,
	id,
	inputName,
	labelKey = 'label',
	name,
	onBlur = () => {},
	onChange,
	onFocus = () => {},
	parameterObjectFieldName,
	placeholder = Liferay.Language.get('search'),
	readOnly,
	required,
	value,
	valueKey = 'value',
	...otherProps
}) {
	const [active, setActive] = useState(false);
	const [label, setLabel] = useState('');
	const [loading, setLoading] = useState(false);
	const [resource, setResource] = useState([]);
	const autocompleteRef = useRef();
	const dropdownRef = useRef();

	const dispatch = useForm();
	const {objectRelationships} = useFormState();

	const parameterObjectFieldId = useMemo(
		() => objectRelationships?.[parameterObjectFieldName],
		[objectRelationships, parameterObjectFieldName]
	);

	const url = useMemo(() => {
		if (parameterObjectFieldName && !parameterObjectFieldId) {
			return;
		}

		return parameterObjectFieldId
			? apiURL.replace(/{\w+}/, parameterObjectFieldId)
			: `${apiURL}?page=1&pageSize=10${label ? `&search=${label}` : ''}`;
	}, [apiURL, label, parameterObjectFieldId, parameterObjectFieldName]);

	/**
	 * Provides selected value for dependant relationships
	 */
	useEffect(() => {
		dispatch({
			payload: {
				[fieldName]: value,
			},
			type: FORM_EVENT_TYPES.OBJECT.RELATIONSHIPS_CHANGE,
		});
	}, [dispatch, fieldName, value]);

	/**
	 * Cleans up value and label case the relationship it relies on had its value reset
	 */
	useEffect(() => {
		if (parameterObjectFieldName && !parameterObjectFieldId && value) {
			onChange({target: {value: null}});
			setLabel('');
		}
	}, [onChange, parameterObjectFieldName, parameterObjectFieldId, value]);

	/**
	 * Retrieves the label from the apiURL and the relationship value
	 */
	useEffect(() => {
		if (!value) {
			return;
		}

		if (!parameterObjectFieldName) {
			fetchOptions(`${apiURL}/${value}`).then((item) =>
				setLabel(getLabel(item, labelKey))
			);
		}
		else if (resource?.items) {
			const selected = resource?.items.find(
				({id}) => id === Number(value)
			);

			setLabel(getLabel(selected, labelKey));
		}
	}, [apiURL, labelKey, parameterObjectFieldName, resource, value]);

	/**
	 * Fetches the data to populate the dropdown items
	 */
	useEffect(() => {
		if (!url) {
			return;
		}

		setLoading(true);
		fetchOptions(url)
			.then(setResource)
			.finally(() => setLoading(false));
	}, [url]);

	/**
	 * Deactivates the dropdown on outside click
	 */
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
					disabled={
						parameterObjectFieldName && !parameterObjectFieldId
					}
					name={inputName}
					onBlur={onBlur}
					onChange={({target: {value}}) => {
						if (value === '') {
							onChange({target: {value: null}});
						}
						else {
							const selected = resource?.items?.find(
								(item) => getLabel(item, labelKey) === value
							);

							onChange({
								target: {
									value: selected
										? String(selected[valueKey])
										: null,
								},
							});
						}

						setLabel(value);
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
					value={label}
				/>

				<ClayAutocomplete.DropDown
					active={active && !readOnly && resource}
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
												match={label}
												onClick={(event) => {
													onChange(
														event,
														String(item[valueKey])
													);
													setActive(false);
													setLabel(
														getLabel(item, labelKey)
													);
												}}
												value={getLabel(item, labelKey)}
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
