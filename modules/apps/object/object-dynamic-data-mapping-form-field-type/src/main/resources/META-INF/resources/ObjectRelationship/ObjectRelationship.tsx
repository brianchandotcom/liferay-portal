/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayAutocomplete from '@clayui/autocomplete';
import ClayDropDown from '@clayui/drop-down';
import {useDebounce} from '@clayui/shared';
import {DateTimeRenderer} from '@liferay/frontend-data-set-web';
import {
	FORM_EVENT_TYPES,
	useForm,
	useFormState,
} from 'data-engine-js-components-web';
import {ReactFieldBase as FieldBase} from 'dynamic-data-mapping-form-field-type';
import {fetch} from 'frontend-js-web';
import React, {useEffect, useRef, useState} from 'react';

type LocalizedValue<T> = Liferay.Language.LocalizedValue<T>;

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

function getLabel<T extends ObjectMap<any>>(
	item: T,
	key: keyof T,
	objectFieldBusinessType: string
) {
	const value = item[key];

	if (typeof value !== 'object') {
		if (objectFieldBusinessType === 'Date') {
			return DateTimeRenderer({
				options: {
					format: {
						day: 'numeric',
						month: 'short',
						timeZone: 'UTC',
						year: 'numeric',
					},
				},
				value: String(value),
			});
		}

		return value ? String(value) : '';
	}
	const label =
		(value as LocalizedValue<string>)[defaultLanguageId] ??
		(value as {[key: string]: string})['name'] ??
		(value as {[key: string]: string})['label_i18n'];

	return label ? String(label) : '';
}

function LoadingWithDebounce({
	labelKey,
	list,
	loading,
	objectFieldBusinessType,
	onSelect,
	searchTerm,
}: {
	labelKey: string;
	list?: Item[];
	loading?: boolean;
	objectFieldBusinessType: string;
	onSelect: (item: Item) => void;
	searchTerm?: string;
}) {
	const debouncedLoadingChange = useDebounce(loading, 500);

	if (loading || debouncedLoadingChange) {
		return (
			<ClayDropDown.Item disabled>
				{Liferay.Language.get('loading')}
			</ClayDropDown.Item>
		);
	}

	if (!list?.length) {
		return (
			<ClayDropDown.Item disabled>
				{Liferay.Language.get('no-results-found')}
			</ClayDropDown.Item>
		);
	}

	return (
		<>
			{list.map((item) => (
				<ClayAutocomplete.Item
					key={item.id}
					match={searchTerm}
					onClick={() => onSelect(item)}
					value={getLabel(item, labelKey, objectFieldBusinessType)}
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
	objectEntryId,
	objectFieldBusinessType,
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
	const autocompleteRef = useRef<HTMLInputElement>(null);
	const dropdownRef = useRef<HTMLDivElement>(null);
	const [
		{active, list, loading, searchTerm, selected, url},
		setState,
	] = useState<State>({url: null});

	const dispatch = useForm();

	const {objectRelationships} = useFormState<{
		objectRelationships?: ObjectMap<number>;
	}>();

	const parameterObjectFieldId = parameterObjectFieldName
		? objectRelationships?.[parameterObjectFieldName]
		: null;

	/**
	 * Provides selected value for dependant relationships
	 */
	useEffect(() => {
		dispatch({
			payload: {
				[fieldName]: Number(value),
			},
			type: FORM_EVENT_TYPES.OBJECT.RELATIONSHIPS_CHANGE,
		});
	}, [dispatch, fieldName, value]);

	/**
	 * Fetches the data to populate the dropdown items
	 */
	useEffect(() => {
		const fetchData = async () => {
			let newURL: string | null = null;

			if (!parameterObjectFieldName || parameterObjectFieldId) {
				newURL = parameterObjectFieldId
					? apiURL.replace(/{\w+}/, String(parameterObjectFieldId))
					: `${apiURL}?page=1&pageSize=10${
							searchTerm ? `&search=${searchTerm}` : ''
					  }`;
			}

			if (!newURL || newURL === url) {
				return;
			}

			setState((prevState) => ({...prevState, loading: true}));

			try {
				const {items} = await fetchOptions<Resource>(newURL);

				const state: State = {
					list:
						objectEntryId !== '0'
							? items.filter(
									(item) => item.id !== Number(objectEntryId)
							  )
							: items,
					loading: false,
					url: newURL,
				};

				if (value) {
					let selected: Item | void = items.find(
						({id}) => id === Number(value)
					);

					if (!selected && !parameterObjectFieldName) {
						selected = await fetchOptions<Item>(
							`${apiURL}/${value}`
						);
					}

					if (selected) {
						state.selected = selected;
					}
					else {
						onChange({target: {value: null}});
					}
				}
				setState(({active, searchTerm}) => ({
					active,
					searchTerm,
					...state,
				}));
			}
			catch (error) {
				setState(({active, searchTerm}) => ({
					active,
					loading: false,
					searchTerm,
					url,
				}));
				console.error(error);
			}
		};

		fetchData();
	}, [
		apiURL,
		objectEntryId,
		onChange,
		parameterObjectFieldId,
		parameterObjectFieldName,
		searchTerm,
		value,
		url,
	]);

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
			setState((prevState) => ({...prevState, active: false}));
		};

		if (active) {
			document.addEventListener('mousedown', handleClick);
		}

		return () => {
			document.removeEventListener('mousedown', handleClick);
		};
	}, [active]);

	const label =
		(selected && getLabel(selected, labelKey, objectFieldBusinessType)) ??
		searchTerm;

	return (
		<FieldBase
			name={name}
			readOnly={readOnly}
			required={required}
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
						let selected: Item | undefined;

						if (value) {
							selected = list?.find(
								(item) =>
									getLabel(
										item,
										labelKey,
										objectFieldBusinessType
									) === value
							);
						}

						setState((prevState) => {
							const state = {...prevState};

							if (selected) {
								state.selected = selected;
							}
							else {
								delete state.selected;
							}

							state.searchTerm = value;

							return state;
						});

						const getValue = () => {
							if (!value) {
								return value;
							}

							if (selected) {
								return String(selected[valueKey]);
							}

							return null;
						};

						onChange({
							target: {
								value: getValue(),
							},
						});
					}}
					onFocus={(event) => {
						onFocus?.(event);
						setState((prevState) => ({...prevState, active: true}));
					}}
					onKeyPress={(event) => {
						if (event.key === 'Escape') {
							setState((prevState) => ({
								...prevState,
								active: false,
							}));
						}
					}}
					placeholder={placeholder}
					readOnly={readOnly}
					required={required}
					value={label}
				/>

				<ClayAutocomplete.DropDown active={!readOnly && list && active}>
					<div ref={dropdownRef}>
						<ClayDropDown.ItemList>
							<LoadingWithDebounce
								labelKey={labelKey}
								list={list}
								loading={loading}
								objectFieldBusinessType={
									objectFieldBusinessType
								}
								onSelect={(selected) => {
									onChange({
										target: {
											value: String(selected[valueKey]),
										},
									});
									setState((prevState) => ({
										...prevState,
										active: false,
										selected,
									}));
								}}
								searchTerm={label}
							/>
						</ClayDropDown.ItemList>
					</div>
				</ClayAutocomplete.DropDown>

				{loading && <ClayAutocomplete.LoadingIndicator />}
			</ClayAutocomplete>

			<input name={name} type="hidden" value={selected?.id} />
		</FieldBase>
	);
}

interface IProps {
	apiURL: string;
	fieldName: string;
	inputName: string;
	labelKey?: string;
	name: string;
	objectEntryId: string;
	objectFieldBusinessType: string;
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

interface Item extends ObjectMap {
	id: number;
}

interface ObjectMap<T = unknown> {
	[key: string]: T;
}

interface Resource {
	items: Item[];
}

interface State {
	active?: boolean;
	list?: Item[];
	loading?: boolean;
	searchTerm?: string;
	selected?: Item;
	url: string | null;
}
