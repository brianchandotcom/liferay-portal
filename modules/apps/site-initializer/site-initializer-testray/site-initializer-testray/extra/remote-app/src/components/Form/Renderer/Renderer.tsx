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

import Form from '..';
import React, {useCallback, useEffect, useState} from 'react';
import {useParams} from 'react-router-dom';

import i18n from '../../../i18n';
import fetcher from '../../../services/fetcher';
import {Operators} from '../../../util/search';
import {AutoCompleteProps} from '../AutoComplete';

type RenderedFieldOptions = string[] | {label: string; value: string}[];

export type RendererFields = {
	disabled?: boolean;
	label: string;
	name: string;
	operator?: Operators;
	options?: RenderedFieldOptions;
	type:
		| 'autocomplete'
		| 'checkbox'
		| 'date'
		| 'text'
		| 'textarea'
		| 'select'
		| 'multiselect';
} & Partial<AutoCompleteProps>;

type RendererProps = {
	fields: RendererFields[];
	filter?: string;
	form: any;
	onChange: (event: any) => void;
};

const Renderer: React.FC<RendererProps> = ({
	fields,
	filter,
	form,
	onChange,
}) => {
	const params = useParams();
	const [disabledFields, setDisableFields] = useState({});
	const [gqlOptions, setGqlOptions] = useState<{[key: string]: []}>({});

	const fieldsFiltered = fields.filter(({label}) =>
		filter ? label.toLowerCase().includes(filter.toLowerCase()) : true
	);

	const fetchQueries = useCallback(
		async (gqlQueries: (RendererFields | (() => Promise<any>))[][]) => {
			const results = await Promise.allSettled(
				gqlQueries.map(([, query]) => (query as any)())
			);

			let i = 0;
			const _gqlOptions: any = {};

			for (const result of results) {
				if (result.status === 'fulfilled') {
					const queries: any[][] = [...(gqlQueries as any)];
					const field: RendererFields = queries[i][0];
					const fieldIndex = fields.findIndex(
						(value) => value.name === field.name
					);

					if (field.transformData) {
						const parsedValue = field.transformData(result.value);

						if (fields[fieldIndex]) {
							fields[fieldIndex].options = parsedValue;
						}

						_gqlOptions[field.name] = parsedValue;
					}
				}
				i++;
			}

			setGqlOptions(_gqlOptions);
		},
		[fields]
	);

	useEffect(() => {
		const gqlQueries = fields
			.filter(({resource}) => resource)
			.map(({resource, ...field}) => [
				field,
				() =>
					fetcher(
						(typeof resource === 'function'
							? resource(params)
							: resource) as string
					),
			]);

		fetchQueries(gqlQueries as any);
	}, [fetchQueries, fields, params]);

	return (
		<div className="form-renderer">
			{fieldsFiltered.map((field, index) => {
				const {
					label,
					disabled,
					name,
					type,
					options = [],
					resource,
				} = field;

				const currentValue = form[name];

				const getOptions = () => {
					const _options =
						gqlOptions[name] ||
						(options || []).map((option) =>
							typeof option === 'object'
								? option
								: {
										label: option,
										value: option,
								  }
						);

					return _options;
				};

				if (['text', 'textarea'].includes(type)) {
					return (
						<div key={index}>
							<Form.Input
								disabled={
									(disabled ?? (disabledFields as any))[name]
								}
								onChange={onChange}
								value={currentValue}
								{...(field as any)}
							/>

							{type === 'textarea' && (
								<Form.Checkbox
									disabled={disabled}
									label={i18n.sub('no-x', field.label)}
									onClick={() => {
										onChange({target: {name, value: null}});

										setDisableFields({
											...disabledFields,
											[name]: !(disabledFields as any)[
												name
											],
										});
									}}
								/>
							)}
						</div>
					);
				}

				if (type === 'date') {
					return (
						<Form.DatePicker
							key={index}
							name={name}
							onChange={onChange}
							value={currentValue}
							{...(field as any)}
						/>
					);
				}

				if (type === 'select') {
					return (
						<Form.Select
							disabled={disabled}
							key={index}
							label={label}
							name={name}
							onChange={onChange}
							options={getOptions()}
							value={currentValue}
						/>
					);
				}

				if (type === 'checkbox') {
					const onCheckboxChange = (event: any) => {
						const inputValue = event.target.value;
						const formValue: unknown[] = form[name];

						onChange({
							target: {
								name,
								value: formValue.includes(inputValue)
									? formValue.filter(
											(value) => value !== inputValue
									  )
									: [...formValue, inputValue],
							},
						});
					};

					return (
						<div key={index}>
							<label>{label}</label>

							{options.map((option, index) => (
								<Form.Checkbox
									checked={form[name]?.includes(option)}
									disabled={disabled}
									key={index}
									label={
										typeof option === 'string'
											? option
											: option.label
									}
									name={name}
									onChange={onCheckboxChange}
									value={
										typeof option === 'string'
											? option
											: option.value
									}
								/>
							))}
						</div>
					);
				}

				if (type === 'autocomplete') {
					return (
						<Form.AutoComplete
							onSearch={() => null}
							resource={resource as string}
							transformData={field.transformData}
						/>
					);
				}

				if (type === 'multiselect') {
					return (
						<div className="mb-2" key={index}>
							<Form.MultiSelect
								disabled={disabled}
								label={label}
								name={name}
								onChange={onChange}
								options={getOptions()}
								value={currentValue}
							/>
						</div>
					);
				}

				return null;
			})}

			{!fieldsFiltered.length && (
				<p>{i18n.translate('there-are-no-matching-results')}</p>
			)}
		</div>
	);
};

export default Renderer;
