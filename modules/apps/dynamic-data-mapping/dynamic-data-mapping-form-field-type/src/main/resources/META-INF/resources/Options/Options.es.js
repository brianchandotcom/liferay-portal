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
import React, {useMemo, useState} from 'react';
import {DndProvider} from 'react-dnd';
import HTML5Backend from 'react-dnd-html5-backend';

import {FieldBaseProxy} from '../FieldBase/ReactFieldBase.es';
import {Main as KeyValue} from '../KeyValue/KeyValue.es';
import getConnectedReactComponentAdapter from '../util/ReactComponentAdapter.es';
import {connectStore} from '../util/connectStore.es';
import DnD from './DnD.es';
import DragPreview from './DragPreview.es';
import templates from './OptionsAdapter.soy';
import {
	compose,
	dedupValue,
	isOptionValueGenerated,
	normalizeFields,
	random,
} from './util.es';

const defaultOption = {label: '', value: ''};

const Option = React.forwardRef(
	({children, className, disabled, onClick, showCloseButton, style}, ref) => (
		<div
			className={classNames('ddm-field-options', className)}
			style={style}
		>
			<span
				className={classNames('ddm-options-drag', {
					disabled,
				})}
				ref={disabled ? null : ref}
			>
				<ClayIcon symbol="drag" />
			</span>

			<div className="ddm-option-entry">
				{children}

				{showCloseButton && (
					<button
						className="close close-modal"
						onClick={onClick}
						type="button"
					>
						<ClayIcon symbol="times" />
					</button>
				)}
			</div>
		</div>
	)
);

const Options = ({
	children,
	defaultLanguageId,
	disabled,
	editingLanguageId,
	onChange,
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
		const options =
			normalizedValue[editingLanguageId] ||
			normalizedValue[defaultLanguageId] ||
			[];

		return [
			...options.map(option => ({
				...option,
				generateKeyword: isOptionValueGenerated(
					defaultLanguageId,
					editingLanguageId,
					options,
					option
				),
				id: random(),
			})),
			defaultLanguageId === editingLanguageId
				? {...defaultOption, generateKeyword: true, id: random()}
				: false,
		].filter(Boolean);
	});

	const fieldsFilter = fields => {
		const _fields = [...fields];

		_fields.splice(_fields.length - 1, 1);

		return {...normalizedValue, [editingLanguageId]: _fields};
	};

	const clone = (...args) => {
		return [[...fields], ...args];
	};

	const dedup = (fields, index, property, value) => {
		const {generateKeyword, id} = fields[index];

		if (property === 'value' && generateKeyword) {
			value = dedupValue(
				fields,
				value ? value : Liferay.Language.get('option'),
				id
			);
		}

		return [fields, index, property, value];
	};

	const set = fields => {
		setFields(fields);
		onChange(fieldsFilter(fields));
	};

	const add = (fields, index, property, value) => {
		fields[index][property] = value;

		if (defaultLanguageId === editingLanguageId) {
			fields.push({
				...defaultOption,
				generateKeyword: true,
				id: random(),
			});
		}

		return [fields, index, property, value];
	};

	const change = (fields, index, property, value) => {
		const {edited, label} = fields[index];

		fields[index][property] = value;
		fields[index]['edited'] =
			edited || (value && value !== label && property === 'value');

		return [fields, index, property, value];
	};

	const normalize = fields => {
		return [normalizeFields(fields)];
	};

	const handleDelete = (fields, index) => {
		fields.splice(index, 1);

		return [fields];
	};

	const move = (fields, data) => {
		const {itemPosition, targetPosition} = data;

		if (itemPosition === fields.length - 1) {
			return [fields];
		}

		const item = {...fields[itemPosition]};
		const newTargetPosition =
			targetPosition > itemPosition ? targetPosition - 1 : targetPosition;

		fields.splice(itemPosition, 1);
		fields.splice(newTargetPosition, 0, item);

		return [fields];
	};

	const composedAdd = compose(clone, dedup, add, set);
	const composedBlur = compose(clone, normalize, set);
	const composedChange = compose(clone, dedup, change, set);
	const composedDelete = compose(clone, handleDelete, set);
	const composedMove = compose(clone, move, set);

	return (
		<div className="ddm-field-options-container">
			<DragPreview component={Option}>{children}</DragPreview>
			{fields.map((option, index) => (
				<DnD
					index={index}
					key={option.id}
					onDragEnd={composedMove}
					option={option}
				>
					<Option
						disabled={disabled}
						onClick={() => composedDelete(index)}
						showCloseButton={
							!(fields.length - 1 === index) && !disabled
						}
					>
						{children({
							handleBlur: composedBlur,
							handleField: !(fields.length - 1 === index)
								? composedChange.bind(this, index)
								: composedAdd.bind(this, index),
							index,
							option,
						})}
					</Option>
				</DnD>
			))}
		</div>
	);
};

const OptionsProxy = connectStore(
	({
		defaultLanguageId = themeDisplay.getLanguageId(),
		dispatch,
		editingLanguageId = themeDisplay.getLanguageId(),
		emit,
		placeholder = Liferay.Language.get('enter-an-option'),
		readOnly,
		required,
		store,
		value = {},
		visible,
		...otherProps
	}) => (
		<DndProvider backend={HTML5Backend}>
			<FieldBaseProxy
				{...otherProps}
				dispatch={dispatch}
				readOnly={readOnly}
				store={store}
				visible={visible}
			>
				<Options
					defaultLanguageId={defaultLanguageId}
					disabled={readOnly}
					editingLanguageId={editingLanguageId}
					onChange={value => emit('fieldEdited', {}, value)}
					value={value}
				>
					{({handleBlur, handleField, index, option}) => (
						<KeyValue
							dispatch={dispatch}
							generateKeyword={option.generateKeyword}
							keyword={option.value}
							keywordReadOnly={
								defaultLanguageId !== editingLanguageId
							}
							name={`option${index}`}
							onKeywordBlur={handleBlur}
							onKeywordChange={(event, value, generate) => {
								handleField('generateKeyword', generate);
								handleField('value', value);
							}}
							onTextBlur={handleBlur}
							onTextChange={event =>
								handleField('label', event.target.value)
							}
							placeholder={placeholder}
							readOnly={option.disabled}
							required={required}
							showLabel={false}
							store={store}
							value={option.label}
							visible={visible}
						/>
					)}
				</Options>
			</FieldBaseProxy>
		</DndProvider>
	)
);

const ReactOptionsAdapter = getConnectedReactComponentAdapter(
	OptionsProxy,
	templates
);

export {ReactOptionsAdapter};
export default ReactOptionsAdapter;
