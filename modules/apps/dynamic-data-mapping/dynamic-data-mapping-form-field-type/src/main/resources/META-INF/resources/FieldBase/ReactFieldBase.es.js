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
import ClayPopover from '@clayui/popover';
import classNames from 'classnames';
import {
	Layout,
	getRepeatedIndex,
	useForm,
	useFormState,
} from 'data-engine-js-components-web';
import {EVENT_TYPES as CORE_EVENT_TYPES} from 'data-engine-js-components-web/js/core/actions/eventTypes.es';
import moment from 'moment/min/moment-with-locales';
import React, {useMemo, useState} from 'react';

const convertInputValue = (fieldType, locale, value) => {
	if (fieldType === 'date') {
		const momentLocale = moment().locale(locale);

		const date = moment(value, [
			momentLocale.localeData().longDateFormat('L'),
			'YYYY-MM-DD',
		]).toDate();

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

const Popover = ({tooltip}) => {
	const [isPopoverVisible, setPopoverVisible] = useState(false);

	const POPOVER_IMAGE_HEIGHT = 170;
	const POPOVER_IMAGE_WIDTH = 232;
	const POPOVER_MAX_WIDTH = 256;

	return (
		<ClayPopover
			alignPosition="right-bottom"
			data-testid="clayPopover"
			disableScroll
			header={Liferay.Language.get('input-mask-format')}
			show={isPopoverVisible}
			style={{maxWidth: POPOVER_MAX_WIDTH}}
			trigger={
				<span
					className="ddm-tooltip"
					onMouseOut={() => setPopoverVisible(false)}
					onMouseOver={() => setPopoverVisible(true)}
				>
					<ClayIcon symbol="question-circle-full" />
				</span>
			}
		>
			<p>{tooltip}</p>

			<img
				alt={Liferay.Language.get('input-mask-format')}
				height={POPOVER_IMAGE_HEIGHT}
				src={`${themeDisplay.getPathThemeImages()}/forms/input_mask_format.png`}
				width={POPOVER_IMAGE_WIDTH}
			/>
		</ClayPopover>
	);
};

const FieldProperties = ({required, showPopover, tooltip}) => {
	return (
		<>
			{required && (
				<span className="ddm-label-required reference-mark">
					<ClayIcon symbol="asterisk" />
				</span>
			)}

			{tooltip && (
				<>
					{showPopover ? (
						<Popover tooltip={tooltip} />
					) : (
						<span className="ddm-tooltip" title={tooltip}>
							<ClayIcon symbol="question-circle-full" />
						</span>
					)}
				</>
			)}
		</>
	);
};

function FieldBase({
	children,
	displayErrors,
	errorMessage,
	fieldName,
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
	const inputEditedName = name + '_edited';

	const hiddenTranslations = useMemo(() => {
		const array = [];

		if (!localizedValue) {
			return array;
		}

		Object.keys(localizedValue).forEach((key) => {
			if (key !== editingLanguageId) {
				array.push({
					inputName: name.replace(editingLanguageId, key),
					locale: key,
					value: localizedValue[key],
				});
			}
		});

		return array;
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

	const showPopover = fieldName === 'inputMaskFormat';

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
									showPopover={showPopover}
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
									showPopover={showPopover}
									tooltip={tooltip}
								/>
							</label>
							{children}
						</>
					)}
				</>
			)}

			{!renderLabel && children}

			{hiddenTranslations.length > 0 &&
				hiddenTranslations.map((translation) => (
					<input
						key={translation.inputName}
						name={translation.inputName}
						type="hidden"
						value={
							translation.value
								? convertInputValue(
										type,
										translation.locale,
										translation.value
								  )
								: ''
						}
					/>
				))}

			<input
				key={inputEditedName}
				name={inputEditedName}
				type="hidden"
				value={localizedValue[editingLanguageId] !== undefined}
			/>

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
