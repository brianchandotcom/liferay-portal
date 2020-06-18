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

import ClayIcon from '@clayui/icon';
import classNames from 'classnames';
import {usePrevious} from 'frontend-js-react-web';
import React, {useEffect, useMemo, useRef, useState} from 'react';
import {DndProvider} from 'react-dnd';
import HTML5Backend from 'react-dnd-html5-backend';

import {FieldBase} from '../FieldBase/ReactFieldBase.es';
import KeyValue from '../KeyValue/KeyValue.es';
import DnD from './DnD.es';
import DragPreview from './DragPreview.es';
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

const refreshFields = (defaultLanguageId, editingLanguageId, options) =>
	[
		...options.map((option) => ({
			...option,
			generateKeyword: isOptionValueGenerated(
				defaultLanguageId,
				editingLanguageId,
				options,
				option
			),
			id: random(),
		})),
		{...defaultOption, generateKeyword: true, id: random()},
	].filter((field) => field && Object.keys(field).length > 0);

const Options = ({
	children,
	defaultLanguageId,
	disabled,
	editingLanguageId,
	onChange,
	value = {},
}) => {
	const prevEditingLanguageId = usePrevious(editingLanguageId);

	const normalizedValue = useMemo(() => {
		const formattedValue = {...value};

		Object.keys(value).forEach((languageId) => {
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

		return refreshFields(defaultLanguageId, editingLanguageId, options);
	});

	useEffect(() => {
		if (prevEditingLanguageId !== editingLanguageId) {
			const options =
				normalizedValue[editingLanguageId] ||
				normalizedValue[defaultLanguageId] ||
				[];

			setFields(
				refreshFields(defaultLanguageId, editingLanguageId, options)
			);
		}
	}, [
		defaultLanguageId,
		editingLanguageId,
		normalizedValue,
		prevEditingLanguageId,
	]);

	const defaultOptionRef = useRef(
		fields.length === 2 &&
			fields[0].label.toLowerCase() ===
				Liferay.Language.get('option').toLowerCase()
	);

	const fieldsFilter = (fields) => {
		const _fields = [...fields];

		_fields.splice(_fields.length - 1, 1);

		let _normalizedValue = {...normalizedValue};

		const availableLanguageIds = Object.getOwnPropertyNames(
			normalizedValue
		);

		availableLanguageIds.forEach((languageId) => {
			_normalizedValue = {
				..._normalizedValue,
				[languageId]: synchroniseValue(_fields, languageId),
			};
		});

		return _normalizedValue;
	};

	const synchroniseValue = (fields, languageId) => {
		if (editingLanguageId === languageId) {
			return [...fields];
		}

		const _values = [];

		fields.forEach((localizedValue, index) => {
			let newLocalizedValue = localizedValue;

			if (normalizedValue[languageId][index]) {
				newLocalizedValue = {
					...newLocalizedValue,
					label: normalizedValue[languageId][index].label,
				};
			}
			_values.push(
				normalizedValue[languageId].find(
					(_localizedValue) =>
						_localizedValue.value == localizedValue.value
				) || newLocalizedValue
			);
		});

		return _values;
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

	const set = (fields) => {
		setFields(fields);
		onChange(fieldsFilter(fields));
	};

	const add = (fields, index, property, value) => {
		fields[index][property] = value;

		fields.push({
			...defaultOption,
			generateKeyword: true,
			id: random(),
		});

		return [fields, index, property, value];
	};

	const change = (fields, index, property, value) => {
		const {edited, label} = fields[index];

		fields[index][property] = value;
		fields[index]['edited'] =
			edited || (value && value !== label && property === 'value');

		return [fields, index, property, value];
	};

	const normalize = (fields) => {
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
							defaultOptionRef,
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

const Main = ({
	defaultLanguageId = themeDisplay.getLanguageId(),
	editingLanguageId = themeDisplay.getLanguageId(),
	onChange,
	placeholder = Liferay.Language.get('enter-an-option'),
	readOnly,
	required,
	value = {},
	visible,
	...otherProps
}) => (
	<DndProvider backend={HTML5Backend} context={window}>
		<FieldBase {...otherProps} readOnly={readOnly} visible={visible}>
			<Options
				defaultLanguageId={defaultLanguageId}
				disabled={readOnly}
				editingLanguageId={editingLanguageId}
				onChange={(value) => onChange({}, value)}
				value={value}
			>
				{({
					defaultOptionRef,
					handleBlur,
					handleField,
					index,
					option,
				}) => (
					<KeyValue
						generateKeyword={option.generateKeyword}
						keyword={option.value}
						keywordReadOnly={false}
						name={`option${index}`}
						onBlur={handleBlur}
						onChange={(event) =>
							handleField('label', event.target.value)
						}
						onFocus={() => {
							if (defaultOptionRef.current) {
								handleField('label', '');
								defaultOptionRef.current = false;
							}
						}}
						onKeywordBlur={handleBlur}
						onKeywordChange={(event, value, generate) => {
							handleField('generateKeyword', generate);
							handleField('value', value);
						}}
						placeholder={placeholder}
						readOnly={option.disabled}
						required={required}
						showLabel={false}
						value={option.label}
						visible={visible}
					/>
				)}
			</Options>
		</FieldBase>
	</DndProvider>
);

Main.displayName = 'Options';

export default Main;
