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

import './KeyValueRegister.soy';

import {normalizeFieldName} from 'dynamic-data-mapping-form-renderer';
import React, {useRef} from 'react';

import {FieldBaseProxy} from '../FieldBase/ReactFieldBase.es';
import {TextWithFieldBase, useSyncValue} from '../Text/Text.es';
import getConnectedReactComponentAdapter from '../util/ReactComponentAdapter.es';
import {connectStore} from '../util/connectStore.es';
import templates from './KeyValueAdapter.soy';

const KeyValue = ({disabled, onChange, value, ...otherProps}) => (
	<div className="active form-text key-value-editor">
		<label className="control-label key-value-label">
			{Liferay.Language.get('field-name')}:
		</label>

		<input
			{...otherProps}
			className="key-value-input"
			onChange={event => {
				const value = normalizeFieldName(event.target.value);
				onChange({target: {value}});
			}}
			readOnly={disabled}
			type="text"
			value={value}
		/>
	</div>
);

const KeyValueWithFieldBase = ({
	dispatch,
	generateKeyword: initialGenerateKeyword = true,
	keyword,
	keywordReadOnly,
	name,
	onKeywordBlur,
	onKeywordChange,
	onTextBlur,
	onTextChange,
	onTextFocus,
	placeholder,
	readOnly,
	required,
	showLabel,
	spritemap,
	store,
	value,
	visible,
	...otherProps
}) => {
	const generateKeywordRef = useRef(initialGenerateKeyword);

	return (
		<FieldBaseProxy
			{...otherProps}
			dispatch={dispatch}
			name={name}
			readOnly={readOnly}
			required={required}
			showLabel={showLabel}
			spritemap={spritemap}
			store={store}
			visible={visible}
		>
			<TextWithFieldBase
				dispatch={dispatch}
				name={`keyValueLabel${name}`}
				onBlur={onTextBlur}
				onChange={event => {
					const {value} = event.target;

					if (generateKeywordRef.current) {
						const newKeyword = normalizeFieldName(value);
						onKeywordChange(event, newKeyword);
					}

					onTextChange(event);
				}}
				onFocus={onTextFocus}
				placeholder={placeholder}
				readOnly={readOnly}
				required={required}
				showLabel={showLabel}
				spritemap={spritemap}
				store={store}
				value={value}
				visible={visible}
			/>
			<KeyValue
				disabled={keywordReadOnly}
				onBlur={onKeywordBlur}
				onChange={event => {
					const {value} = event.target;

					generateKeywordRef.current = false;
					onKeywordChange(event, value);
				}}
				value={keyword}
			/>
		</FieldBaseProxy>
	);
};

const KeyValueProxy = connectStore(
	({emit, keyword: initialKeyword, ...otherProps}) => {
		const [keyword, setKeyword] = useSyncValue(initialKeyword);

		return (
			<KeyValueWithFieldBase
				{...otherProps}
				keyword={keyword}
				onKeywordBlur={event =>
					emit('fieldKeywordBlurred', event, event.target.value)
				}
				onKeywordChange={(event, value) => {
					setKeyword(value);
					emit('fieldKeywordEdited', event, value);
				}}
				onTextBlur={event =>
					emit('fieldBlurred', event, event.target.value)
				}
				onTextChange={event =>
					emit('fieldEdited', event, event.target.value)
				}
				onTextFocus={event =>
					emit('fieldFocused', event, event.target.value)
				}
			/>
		);
	}
);

const ReactKeyValueAdapter = getConnectedReactComponentAdapter(
	KeyValueProxy,
	templates
);

export {ReactKeyValueAdapter, KeyValueWithFieldBase};
export default ReactKeyValueAdapter;
