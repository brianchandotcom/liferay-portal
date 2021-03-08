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

import './FieldBase.scss';

import ClayButton from '@clayui/button';
import ClayIcon from '@clayui/icon';
import classNames from 'classnames';
import {
	Layout,
	getRepeatedIndex,
	useForm,
	useFormState,
} from 'dynamic-data-mapping-form-renderer';
import {EVENT_TYPES as CORE_EVENT_TYPES} from 'dynamic-data-mapping-form-renderer/js/core/actions/eventTypes.es';
import moment from 'moment';
import React, {useMemo} from 'react';

const convertInputValue = (fieldType, value) => {
	if (fieldType === 'date') {
		const date = moment(value).toDate();

		if (moment(date).isValid()) {
			return moment(date).format('YYYY-MM-DD');
		}
	}
	else if (
		fieldType === 'document_library' ||
		fieldType === 'geolocation' ||
		fieldType === 'grid' ||
		fieldType === 'image'
	) {
		if (Object.keys(value).length === 0) {
			return '';
		}

		return JSON.stringify(value);
	}

	return value;
};

const getDefaultRows = (nestedFields) => {
	return nestedFields.map((nestedField) => {
		return {
			columns: [
				{
					fields: [nestedField],
					size: 12,
				},
			],
		};
	});
};

const FieldProperties = ({required, tooltip}) => {
	return (
		<>
			{required && (
				<span className="ddm-label-required reference-mark">
					<ClayIcon symbol="asterisk" />
				</span>
			)}

			{tooltip && (
				<span className="ddm-tooltip">
					<ClayIcon symbol="question-circle-full" title={tooltip} />
				</span>
			)}
		</>
	);
};

function FieldBase({
	children,
	displayErrors,
	errorMessage,
	label,
	localizedValue = {},
	name,
	nestedFields,
	onClick,
	overMaximumRepetitionsLimit = false,
	readOnly,
	repeatable,
	required,
	showLabel = true,
	style,
	text,
	tip,
	tooltip,
	type,
	valid,
	visible,
}) {
	const {editingLanguageId} = useFormState();
	const dispatch = useForm();

	const localizedValueArray = useMemo(() => {
		const languageValues = [];

		if (!localizedValue) {
			return languageValues;
		}

		Object.keys(localizedValue).forEach((key) => {
			if (key !== editingLanguageId && localizedValue[key] !== '') {
				languageValues.push({
					name: name.replace(editingLanguageId, key),
					value: localizedValue[key],
				});
			}
		});

		return languageValues;
	}, [localizedValue, editingLanguageId, name]);

	const renderLabel =
		(label && showLabel) || required || tooltip || repeatable;

	const repeatedIndex = useMemo(() => getRepeatedIndex(name), [name]);

	const showLegend =
		type &&
		(type === 'checkbox_multiple' ||
			type === 'grid' ||
			type === 'paragraph' ||
			type === 'radio');

	const fieldDetailsId = name + '_fieldDetails';
	const hasError = displayErrors && errorMessage && !valid;

	let fieldDetails = '';

	if (renderLabel) {
		fieldDetails += label + '<br>';
	}

	if (tip) {
		fieldDetails += tip + '<br>';
	}

	if (text) {
		fieldDetails += text + '<br>';
	}

	if (hasError) {
		fieldDetails += errorMessage;
	}
	else if (required) {
		fieldDetails += Liferay.Language.get('required');
	}

	return (
		<div
			aria-labelledby={!renderLabel ? fieldDetailsId : null}
			className={classNames('form-group', {
				'has-error': hasError,
				hide: !visible,
			})}
			data-field-name={name}
			onClick={onClick}
			style={style}
			tabIndex={!renderLabel ? 0 : null}
		>
			{repeatable && (
				<div className="lfr-ddm-form-field-repeatable-toolbar">
					{repeatable && repeatedIndex > 0 && (
						<ClayButton
							className="ddm-form-field-repeatable-delete-button p-0"
							disabled={readOnly}
							onClick={() =>
								dispatch({
									payload: name,
									type: CORE_EVENT_TYPES.FIELD.REMOVED,
								})
							}
							small
							title={Liferay.Language.get('remove')}
							type="button"
						>
							<ClayIcon symbol="hr" />
						</ClayButton>
					)}

					<ClayButton
						className={classNames(
							'ddm-form-field-repeatable-add-button p-0',
							{
								hide: overMaximumRepetitionsLimit,
							}
						)}
						disabled={readOnly}
						onClick={() =>
							dispatch({
								payload: name,
								type: CORE_EVENT_TYPES.FIELD.REPEATED,
							})
						}
						small
						title={Liferay.Language.get('duplicate')}
						type="button"
					>
						<ClayIcon symbol="plus" />
					</ClayButton>
				</div>
			)}

			{renderLabel && (
				<>
					{showLegend ? (
						<fieldset>
							<legend
								aria-labelledby={fieldDetailsId}
								className="lfr-ddm-legend"
								tabIndex="0"
							>
								{label && showLabel && label}

								<FieldProperties
									required={required}
									tooltip={tooltip}
								/>
							</legend>
							{children}
						</fieldset>
					) : (
						<>
							<label
								aria-describedby={fieldDetailsId}
								className={classNames({
									'ddm-empty': !showLabel && !required,
									'ddm-label': showLabel || required,
								})}
								tabIndex="0"
							>
								{label && showLabel && label}

								<FieldProperties
									required={required}
									tooltip={tooltip}
								/>
							</label>
							{children}
						</>
					)}
				</>
			)}

			{!renderLabel && children}

			{localizedValueArray.length > 0 &&
				localizedValueArray.map((language) => (
					<input
						key={language.name}
						name={language.name}
						type="hidden"
						value={
							language.value
								? convertInputValue(type, language.value)
								: ''
						}
					/>
				))}

			{typeof tip === 'string' && (
				<span aria-hidden="true" className="form-text">
					{tip}
				</span>
			)}

			{hasError && (
				<span className="form-feedback-group">
					<div aria-hidden="true" className="form-feedback-item">
						{errorMessage}
					</div>
				</span>
			)}

			{fieldDetails && (
				<span
					className="sr-only"
					dangerouslySetInnerHTML={{
						__html: fieldDetails,
					}}
					id={fieldDetailsId}
				/>
			)}

			{nestedFields && <Layout rows={getDefaultRows(nestedFields)} />}
		</div>
	);
}

export {FieldBase};
