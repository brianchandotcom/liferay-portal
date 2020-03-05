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

import './OptionsRegister.soy';

import ClayIcon from '@clayui/icon';
import classNames from 'classnames';
import {normalizeFieldName} from 'dynamic-data-mapping-form-renderer';
import React, {useMemo, useState} from 'react';

import {FieldBaseProxy} from '../FieldBase/ReactFieldBase.es';
import {KeyValueWithFieldBase} from '../KeyValue/KeyValue.es';
import getConnectedReactComponentAdapter from '../util/ReactComponentAdapter.es';
import {connectStore} from '../util/connectStore.es';
import templates from './OptionsAdapter.soy';

const defaultOption = {label: '', value: ''};

const random = a => {
	return a
		? (a ^ ((Math.random() * 16) >> (a / 4))).toString(16)
		: ([1e7] + -1e3 + -4e3 + -8e3 + -1e11).replace(/[018]/g, random);
};

const normalizeFields = fields => {
	const _fields = fields.map((field, index) => {
		if (fields.length - 1 === index) {
			return field;
		}

		const {label, value: prevValue} = field;

		let value = prevValue ? prevValue : label;

		if (!value) {
			value = Liferay.Language.get('option') + random();
		}

		return {
			...field,
			value: normalizeFieldName(value),
		};
	});

	return _fields;
};

const isOptionValueGenerated = (
	defaultLanguageId,
	editingLanguageId,
	option
) => {
	if (defaultLanguageId !== editingLanguageId) {
		return false;
	}

	if (option.edited) {
		return false;
	}

	if (
		new RegExp(`^${Liferay.Language.get('option')}\\d*$`).test(option.value)
	) {
		return true;
	}

	if (
		new RegExp(`^${option.value.replace(/\d+$/, '')}\\d*`).test(
			normalizeFieldName(option.label)
		)
	) {
		return true;
	}

	return true;
};

const Options = ({
	children,
	defaultLanguageId,
	disabled,
	editingLanguageId,
	onChange,
	spritemap,
	value = {},
}) => {
	const normalizedValue = useMemo(() => {
		const formattedValue = {...value};

		Object.keys(value).forEach(languageId => {
			if (defaultLanguageId !== languageId) {
				formattedValue[languageId] = formattedValue[languageId].filter(
					({value}) => !!value
				);
			}
		});

		return formattedValue;
	}, [defaultLanguageId, value]);

	const [fields, setFields] = useState(() => {
		const options = normalizedValue[editingLanguageId];

		return [
			...options.map(option => ({
				...option,
				generateKeyword: isOptionValueGenerated(
					defaultLanguageId,
					editingLanguageId,
					option
				),
				id: random(),
			})),
			defaultLanguageId === editingLanguageId
				? {...defaultOption}
				: false,
		].filter(Boolean);
	});

	const fieldsFilter = fields => {
		const _fields = [...fields];

		_fields.splice(_fields.length - 1, 1);

		return {...normalizedValue, [editingLanguageId]: _fields};
	};

	const handleAdd = (index, property, value) => {
		const _fields = [...fields];

		_fields[index][property] = value;
		_fields.push({...defaultOption, generateKeyword: true, id: random()});

		setFields(_fields);
		onChange(fieldsFilter(_fields));
	};

	const handleChange = (index, property, value) => {
		const _fields = [...fields];

		const {edited, value: prevValue} = _fields[index];

		_fields[index][property] = value;
		_fields[index]['edited'] =
			edited || (value && value !== prevValue && property === 'value');

		setFields(_fields);
		onChange(fieldsFilter(_fields));
	};

	const handleBlur = () => {
		const _fields = [...fields];

		const normalizedFields = normalizeFields(_fields);

		setFields(normalizedFields);
		onChange(fieldsFilter(normalizedFields));
	};

	const deleteOption = index => {
		const _fields = [...fields];

		_fields.splice(index, 1);

		setFields(_fields);
		onChange(fieldsFilter(_fields));
	};

	return (
		<div className="ddm-field-options-container">
			{fields.map((option, index) => (
				<>
					<div
						className="ddm-options-target"
						key={`target_${option.id}`}
					></div>

					<div className="ddm-field-options" key={option.id}>
						<span
							className={classNames('ddm-options-drag', {
								disabled,
							})}
						>
							<ClayIcon spritemap={spritemap} symbol="drag" />
						</span>

						<div className="ddm-option-entry">
							{children({
								handleBlur,
								handleField: !(fields.length - 1 === index)
									? handleChange.bind(this, index)
									: handleAdd.bind(this, index),
								index,
								option,
							})}

							{!(fields.length - 1 === index) && !disabled && (
								<button
									className="close close-modal"
									onClick={() => deleteOption(index)}
									type="button"
								>
									<ClayIcon
										spritemap={spritemap}
										symbol="times"
									/>
								</button>
							)}
						</div>
					</div>
				</>
			))}
		</div>
	);
};

const OptionsProxy = connectStore(
	({
		defaultLanguageId = themeDisplay.getLanguageId(),
		editingLanguageId = themeDisplay.getLanguageId(),
		emit,
		placeholder = Liferay.Language.get('enter-an-option'),
		readOnly,
		required,
		spritemap,
		store,
		value = {},
		visible,
		...otherProps
	}) => (
		<FieldBaseProxy
			{...otherProps}
			readOnly={readOnly}
			spritemap={spritemap}
			store={store}
			visible={visible}
		>
			<Options
				defaultLanguageId={defaultLanguageId}
				disabled={readOnly}
				editingLanguageId={editingLanguageId}
				onChange={value => emit('fieldEdited', {}, value)}
				spritemap={spritemap}
				value={value}
			>
				{({handleBlur, handleField, index, option}) => (
					<KeyValueWithFieldBase
						generateKeyword={option.generateKeyword}
						keyword={option.value}
						keywordReadOnly={
							defaultLanguageId !== editingLanguageId
						}
						name={`option${index}`}
						onKeywordBlur={handleBlur}
						onKeywordChange={(event, value) =>
							handleField('value', value)
						}
						onTextBlur={handleBlur}
						onTextChange={event =>
							handleField('label', event.target.value)
						}
						placeholder={placeholder}
						readOnly={option.disabled}
						required={required}
						showLabel={false}
						spritemap={spritemap}
						store={store}
						value={option.label}
						visible={visible}
					/>
				)}
			</Options>
		</FieldBaseProxy>
	)
);

const ReactOptionsAdapter = getConnectedReactComponentAdapter(
	OptionsProxy,
	templates
);

export {ReactOptionsAdapter};
export default ReactOptionsAdapter;
