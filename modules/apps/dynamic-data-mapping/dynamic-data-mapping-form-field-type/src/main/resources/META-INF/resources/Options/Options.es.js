/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayIcon from '@clayui/icon';
import {usePrevious} from '@liferay/frontend-js-react-web';
import classNames from 'classnames';
import {RulesSupport, useFormState} from 'data-engine-js-components-web';
import {openModal} from 'frontend-js-web';
import React, {useEffect, useRef, useState} from 'react';
import {DndProvider} from 'react-dnd';
import {HTML5Backend} from 'react-dnd-html5-backend';

import {FieldBase} from '../FieldBase/ReactFieldBase.es';
import KeyValue from '../KeyValue/KeyValue.es';
import DnD from './DnD.es';
import DragPreview from './DragPreview.es';
import {
	compose,
	dedupValue,
	getDefaultOptionValue,
	isOptionValueGenerated,
	normalizeFields,
	normalizeReference,
	random,
} from './util.es';

const Option = React.forwardRef(
	({children, className, disabled, style}, ref) => (
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

			<div className="ddm-option-entry">{children}</div>
		</div>
	)
);

const getInitialOption = (generateOptionValueUsingOptionLabel) => {
	const optionValue = getDefaultOptionValue(
		generateOptionValueUsingOptionLabel,
		''
	);

	const initalOption = {
		id: random(),
		label: '',
		reference: optionValue,
		value: '',
	};

	if (!generateOptionValueUsingOptionLabel) {
		initalOption.value = optionValue;
	}

	return initalOption;
};

const refreshFields = (
	allowSpecialCharacters,
	defaultLanguageId,
	editingLanguageId,
	generateOptionValueUsingOptionLabel,
	initialOption,
	options
) => {
	const refreshedFields = [
		...options.map((option) => ({
			generateKeyword: generateOptionValueUsingOptionLabel
				? isOptionValueGenerated(
						defaultLanguageId,
						editingLanguageId,
						options,
						option
				  )
				: false,
			...option,
			value: option.value
				? option.value
				: getDefaultOptionValue(
						generateOptionValueUsingOptionLabel,
						option.label
				  ),
		})),
		{
			generateKeyword: generateOptionValueUsingOptionLabel,
			...initialOption,
		},
	].filter((field) => field && !!Object.keys(field).length);

	return normalizeFields(
		allowSpecialCharacters,
		refreshedFields,
		generateOptionValueUsingOptionLabel
	);
};

const Options = ({
	allowSpecialCharacters,
	children,
	defaultLanguageId,
	disabled,
	editingLanguageId,
	generateOptionValueUsingOptionLabel,
	onChange,
	tabPressed,
	value = {},
}) => {
	const {builderRules} = useFormState();

	const initialOptionRef = useRef(
		getInitialOption(generateOptionValueUsingOptionLabel)
	);

	const [normalizedValue] = useState(() => {
		const formattedValue = {...value};

		Object.keys(value).forEach((languageId) => {
			if (defaultLanguageId !== languageId) {
				formattedValue[languageId] = formattedValue[languageId].filter(
					({value}) => !!value
				);
			}

			formattedValue[languageId] = normalizeFields(
				allowSpecialCharacters,
				formattedValue[languageId].map((option) => {
					let newOption = {
						id: random(),
						...option,
					};

					if (
						!option.value &&
						option.label.toLowerCase() ===
							Liferay.Language.get('option').toLowerCase()
					) {
						const optionValue = getDefaultOptionValue(
							generateOptionValueUsingOptionLabel,
							option.label
						);

						newOption = {
							...newOption,
							reference: optionValue,
							value: optionValue,
						};
					}

					return newOption;
				}),
				generateOptionValueUsingOptionLabel
			);
		});

		return formattedValue;
	});

	const [fieldError, setFieldError] = useState(null);

	const [fields, setFields] = useState(() => {
		const options =
			normalizedValue[editingLanguageId] ||
			normalizedValue[defaultLanguageId] ||
			[];

		return refreshFields(
			allowSpecialCharacters,
			defaultLanguageId,
			editingLanguageId,
			generateOptionValueUsingOptionLabel,
			initialOptionRef.current,
			options
		);
	});

	const prevEditingLanguageId = usePrevious(editingLanguageId);

	useEffect(() => {
		const hasOwnProperty = Object.prototype.hasOwnProperty;

		if (
			prevEditingLanguageId !== editingLanguageId ||
			(!hasOwnProperty.call(normalizedValue, editingLanguageId) &&
				hasOwnProperty.call(value, editingLanguageId))
		) {
			const availableLanguageIds = Object.getOwnPropertyNames(value);

			availableLanguageIds.forEach((languageId) => {
				normalizedValue[languageId] = normalizeFields(
					allowSpecialCharacters,
					value[languageId].map((option) => {
						if (option.edited) {
							return {
								id: random(),
								...option,
							};
						}

						const {label} = value[languageId].find(
							(defaultOption) =>
								defaultOption.value === option.value
						);

						return {
							id: random(),
							...option,
							label,
						};
					}),
					generateOptionValueUsingOptionLabel
				);
			});

			const options = normalizedValue[editingLanguageId] || [];

			setFields(
				refreshFields(
					allowSpecialCharacters,
					defaultLanguageId,
					editingLanguageId,
					generateOptionValueUsingOptionLabel,
					initialOptionRef.current,
					options
				)
			);
		}
	}, [
		allowSpecialCharacters,
		defaultLanguageId,
		editingLanguageId,
		generateOptionValueUsingOptionLabel,
		normalizedValue,
		prevEditingLanguageId,
		value,
	]);

	const defaultOptionRef = useRef(
		fields.length === 2 &&
			fields[0].label.toLowerCase() ===
				Liferay.Language.get('option').toLowerCase()
	);

	const synchronizeValue = (fields, languageId) => {
		if (editingLanguageId === languageId) {
			return [...fields];
		}

		return [...fields].map((field) => {
			const existingValue = normalizedValue[languageId].find(
				({value}) => value === field.value
			);

			if (existingValue) {
				const {copyFrom} = existingValue;

				if (
					copyFrom &&
					copyFrom === editingLanguageId &&
					!existingValue.edited
				) {
					return {
						...existingValue,
						label: field.label,
						reference: field.reference,
					};
				}

				return {
					...existingValue,
					reference: field.reference,
				};
			}

			let copyFrom = editingLanguageId;

			if (languageId !== defaultLanguageId) {
				copyFrom = defaultLanguageId;
			}

			return {
				...field,
				copyFrom,
				edited: false,
				label: field.label,
			};
		});
	};

	const getSynchronizedValue = (fields) => {
		const _fields = [...fields];

		_fields.pop();

		const availableLanguageIds = Object.getOwnPropertyNames(
			normalizedValue
		);

		return availableLanguageIds.reduce(
			(value, languageId) => ({
				...value,
				[languageId]: synchronizeValue(_fields, languageId),
			}),
			{[editingLanguageId]: [..._fields]}
		);
	};

	const clone = (...args) => {
		return [[...fields], ...args];
	};

	const clearError = () => {
		setFieldError(null);
	};

	const checkValidReference = (fields, value, fieldName) => {
		const field = fields
			.filter(({value}) => value !== fieldName)
			.find(
				({reference}) =>
					reference?.toLowerCase() === value?.toLowerCase()
			);

		return field ? fieldName : null;
	};

	const dedup = (fields, index, property, value) => {
		const {generateKeyword, id} = fields[index];

		if (index === fields.length - 1 && tabPressed) {
			return [fields];
		}

		if (property === 'value' && generateKeyword) {
			value = dedupValue(
				fields,
				value ? value : Liferay.Language.get('option'),
				id,
				generateOptionValueUsingOptionLabel
			);
		}
		else if (property === 'reference') {
			setFieldError(
				checkValidReference(fields, value, fields[index].value)
			);
		}

		return [fields, index, property, value];
	};

	const set = (fields) => {
		const set = new Set();
		const normalizedField = fields.map((option) => {
			if (set.has(option.reference)) {
				return {
					...option,
					reference: option.value,
				};
			}
			else {
				set.add(option.reference);

				return option;
			}
		});

		setFields(fields);

		const synchronizedNormalizedValue = getSynchronizedValue(
			normalizedField
		);

		onChange(synchronizedNormalizedValue);
	};

	const add = (fields, index, property, value) => {
		fields[index][property] = value;

		if (defaultLanguageId !== editingLanguageId) {
			fields[index]['edited'] = true;
		}

		const initialOption = getInitialOption(
			generateOptionValueUsingOptionLabel
		);

		fields.push({
			generateKeyword: generateOptionValueUsingOptionLabel,
			...initialOption,
		});

		initialOptionRef.current = initialOption;

		return [fields, index, property, value];
	};

	const change = (fields, index, property, value) => {
		const {edited, label} = fields[index];

		fields[index][property] = value;
		fields[index]['edited'] =
			edited ||
			(value && value !== label && property === 'value') ||
			property === 'label';

		if (property === 'label') {
			fields[index]['copyFrom'] = undefined;
		}

		return [fields, index, property, value];
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

	const normalize = (fields, index) => {
		clearError();

		fields[index]['reference'] = normalizeReference(
			fields,
			fields[index],
			index
		);

		return [
			normalizeFields(
				allowSpecialCharacters,
				fields,
				generateOptionValueUsingOptionLabel
			),
		];
	};

	const composedAdd = compose(clone, dedup, add, set);
	const composedBlur = compose(clone, normalize, set);
	const composedChange = compose(clone, dedup, change, set);
	const composedDelete = compose(clone, handleDelete, set);
	const composedMove = compose(clone, move, set);

	const handleConfirmDelete = (index, option) => {
		if (
			builderRules &&
			RulesSupport.findRuleByFieldName(option, null, builderRules)
		) {
			openModal({
				bodyHTML: Liferay.Language.get(
					'a-rule-is-applied-to-this-field'
				),
				buttons: [
					{
						displayType: 'secondary',
						label: Liferay.Language.get('cancel'),
						type: 'cancel',
					},
					{
						displayType: 'danger',
						label: Liferay.Language.get('confirm'),
						onClick: () => {
							composedDelete(index);
						},
						type: 'cancel',
					},
				],
				size: 'md',
				title: Liferay.Language.get('delete-field-with-rule-applied'),
			});
		}
		else {
			composedDelete(index);
		}
	};

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
					<Option disabled={disabled}>
						{children({
							defaultOptionRef,
							fieldError,
							handleBlur: composedBlur.bind(this, index),
							handleField: !(fields.length - 1 === index)
								? composedChange.bind(this, index)
								: composedAdd.bind(this, index),
							index,
							onClick: () =>
								handleConfirmDelete(index, option.value),
							option,
							showCloseButton:
								!(fields.length - 1 === index) && !disabled,
						})}
					</Option>
				</DnD>
			))}
		</div>
	);
};

const Main = ({
	allowSpecialCharacters,
	defaultLanguageId = themeDisplay.getDefaultLanguageId(),
	editingLanguageId = themeDisplay.getDefaultLanguageId(),
	generateOptionValueUsingOptionLabel = false,
	onChange,
	keywordReadOnly,
	placeholder = Liferay.Language.get('enter-an-option'),
	readOnly,
	required,
	showKeyword,
	value = {},
	visible,
	...otherProps
}) => {
	const [tabPressed, setTabPressed] = useState(false);

	return (
		<DndProvider backend={HTML5Backend} context={window}>
			<FieldBase {...otherProps} readOnly={readOnly} visible={visible}>
				<Options
					allowSpecialCharacters={allowSpecialCharacters}
					defaultLanguageId={defaultLanguageId}
					disabled={readOnly}
					editingLanguageId={editingLanguageId}
					generateOptionValueUsingOptionLabel={
						generateOptionValueUsingOptionLabel
					}
					onChange={(value) => onChange({}, value)}
					tabPressed={tabPressed}
					value={value}
				>
					{({
						defaultOptionRef,
						fieldError,
						handleBlur,
						handleField,
						index,
						onClick,
						option,
						showCloseButton,
					}) =>
						option && (
							<KeyValue
								allowSpecialCharacters={allowSpecialCharacters}
								displayErrors={
									fieldError && fieldError === option.value
								}
								editingLanguageId={editingLanguageId}
								errorMessage={Liferay.Language.get(
									'this-reference-is-already-being-used'
								)}
								generateKeyword={option.generateKeyword}
								keyword={option.value}
								keywordReadOnly={keywordReadOnly}
								name={`option${index}`}
								onBlur={handleBlur}
								onChange={(value) =>
									handleField('label', value)
								}
								onClick={onClick}
								onFocus={() => {
									if (defaultOptionRef.current) {
										handleField('label', '');
										defaultOptionRef.current = false;
									}
								}}
								onKeyDown={(event) => {
									if (event.key === 'Tab') {
										setTabPressed(true);
									}
									else {
										setTabPressed(false);
									}
								}}
								onKeywordBlur={handleBlur}
								onKeywordChange={(value, generate) => {
									handleField('generateKeyword', generate);
									handleField('value', value);
								}}
								onReferenceBlur={handleBlur}
								onReferenceChange={(value) =>
									handleField('reference', value)
								}
								placeholder={placeholder}
								readOnly={option.disabled}
								reference={option.reference}
								required={required}
								showCloseButton={showCloseButton}
								showKeyword={showKeyword}
								showLabel={false}
								value={option.label}
								visible={visible}
							/>
						)
					}
				</Options>
			</FieldBase>
		</DndProvider>
	);
};

Main.displayName = 'Options';

export default Main;
