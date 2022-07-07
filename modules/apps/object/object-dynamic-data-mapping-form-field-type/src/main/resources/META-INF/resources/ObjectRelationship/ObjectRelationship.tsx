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

const defaultLanguageId = Liferay.ThemeDisplay.getDefaultLanguageId();

async function fetchOptions<T>(url: string) {
	const response = await fetch(url, {
		headers: {
			'Accept': 'application/json',
			'Content-Type': 'application/json',
		},
		method: 'GET',
	});

	return (await response.json()) as T;
}

function getLabel<T extends {[key: string]: any}>(
	item: T | undefined,
	key: keyof T
) {
	const value = item?.[key];

	const label = typeof value === 'object' ? value[defaultLanguageId] : value;

	return label ? String(label) : '';
}

function LoadingWithDebounce({
	labelKey,
	loading,
	onSelect,
	resource,
	searchTerm,
}: {
	labelKey: string;
	loading: boolean;
	onSelect: (item: Item) => void;
	resource?: Resource;
	searchTerm?: string;
}) {
	const debouncedLoadingChange = useDebounce(loading, 500);

	if (loading || debouncedLoadingChange) {
		return (
			<ClayDropDown.Item className="disabled">
				{Liferay.Language.get('loading')}
			</ClayDropDown.Item>
		);
	}

	if (!resource || !resource.items.length) {
		return (
			<ClayDropDown.Item className="disabled">
				{Liferay.Language.get('no-results-found')}
			</ClayDropDown.Item>
		);
	}

	return (
		<>
			{resource.items.map((item) => (
				<ClayAutocomplete.Item
					key={item.id}
					match={searchTerm}
					onClick={() => onSelect(item)}
					value={getLabel(item, labelKey)}
				/>
			))}
		</>
	);
}

export default function ObjectRelationship({
	apiURL,
	fieldName,
	inputName,
	labelKey = 'label',
	name,
	onBlur,
	onChange,
	onFocus,
	parameterObjectFieldName,
	placeholder = Liferay.Language.get('search'),
	readOnly,
	required,
	value,
	valueKey = 'value',
	...otherProps
}: IProps) {
	const [active, setActive] = useState(false);
	const [label, setLabel] = useState('');
	const [loading, setLoading] = useState(false);
	const [resource, setResource] = useState<Resource>();
	const autocompleteRef = useRef<HTMLDivElement>(null);
	const dropdownRef = useRef<HTMLDivElement>(null);

	const dispatch = useForm();
	const {
		objectRelationships,
	}: {objectRelationships?: {[key: string]: number}} = useFormState();

	const parameterObjectFieldId = useMemo(
		() =>
			parameterObjectFieldName &&
			objectRelationships?.[parameterObjectFieldName],
		[objectRelationships, parameterObjectFieldName]
	);

	const url = useMemo(() => {
		if (parameterObjectFieldName && !parameterObjectFieldId) {
			return;
		}

		return parameterObjectFieldId
			? apiURL.replace(/{\w+}/, parameterObjectFieldId.toString())
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
			fetchOptions<{[key: string]: unknown}>(
				`${apiURL}/${value}`
			).then((item) => setLabel(getLabel(item, labelKey)));
		}
		else if (resource) {
			const item = resource.items.find(({id}) => id === Number(value));

			setLabel(getLabel(item, labelKey));
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
		fetchOptions<Resource>(url)
			.then(setResource)
			.finally(() => setLoading(false));
	}, [url]);

	/**
	 * Deactivates the dropdown on outside click
	 */
	useEffect(() => {
		const handleClick = ({target}: MouseEvent) => {
			if (
				target === dropdownRef.current?.parentElement ||
				autocompleteRef.current?.contains(target as Node | null) ||
				dropdownRef.current?.contains(target as Node | null)
			) {
				return;
			}
			setActive(false);
		};

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
				<ClayAutocomplete.Input
					disabled={
						!!parameterObjectFieldName && !parameterObjectFieldId
					}
					name={inputName}
					onBlur={onBlur}
					onChange={({target: {value}}) => {
						if (value === '') {
							onChange({target: {value: null}});
						}
						else {
							const selected = resource?.items.find(
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
						onFocus?.(event);
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
					active={!readOnly && resource && active}
				>
					<div ref={dropdownRef}>
						<ClayDropDown.ItemList>
							<LoadingWithDebounce
								labelKey={labelKey}
								loading={loading}
								onSelect={(item) => {
									onChange({
										target: {value: String(item[valueKey])},
									});
									setActive(false);
									setLabel(getLabel(item, labelKey));
								}}
								resource={resource}
								searchTerm={label}
							/>
						</ClayDropDown.ItemList>
					</div>
				</ClayAutocomplete.DropDown>

				{loading && <ClayAutocomplete.LoadingIndicator />}
			</ClayAutocomplete>

			<input name={name} type="hidden" value={value} />
		</FieldBase>
	);
}

interface IProps {
	apiURL: string;
	fieldName: string;
	inputName: string;
	labelKey?: string;
	name: string;
	onBlur?: React.FocusEventHandler<HTMLInputElement>;
	onChange: (event: {target: {value: unknown}}) => void;
	onFocus?: React.FocusEventHandler<HTMLInputElement>;
	parameterObjectFieldName?: string;
	placeholder?: string;
	readOnly?: boolean;
	required?: boolean;
	value?: string;
	valueKey?: string;
}

interface Item {
	id: number;
	[key: string]: unknown;
}

interface Resource {
	items: Item[];
}
