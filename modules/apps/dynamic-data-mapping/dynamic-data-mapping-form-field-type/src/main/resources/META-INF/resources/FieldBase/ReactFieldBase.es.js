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

import ClayButton from '@clayui/button';
import ClayIcon from '@clayui/icon';
import ClayLabel from '@clayui/label';
import ClayPopover from '@clayui/popover';
import classNames from 'classnames';
import {
	EVENT_TYPES as CORE_EVENT_TYPES,
	Layout,
	getRepeatedIndex,
	useForm,
	useFormState,
} from 'data-engine-js-components-web';
import moment from 'moment/min/moment-with-locales';
import React, {useMemo, useState} from 'react';

import './FieldBase.scss';

function normalizeInputValue(fieldType, locale, value) {
	if (!value) {
		return '';
	}
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
		return Object.keys(value).length === 0 ? '' : JSON.stringify(value);
	}

	return value;
}

const getFieldDetails = ({errorMessage, hasError, required, text, tip}) => {
	let fieldDetails = '';

	if (tip) {
		fieldDetails += Liferay.Util.escape(tip) + '<br>';
	}

	if (text) {
		fieldDetails += Liferay.Util.escape(text) + '<br>';
	}

	if (hasError) {
		fieldDetails += Liferay.Util.escape(errorMessage);
	}
	else if (required) {
		fieldDetails += Liferay.Language.get('required');
	}

	return fieldDetails;
};

const HideFieldProperty = () => {
	return (
		<ClayLabel className="ml-1" displayType="secondary">
			{Liferay.Language.get('hidden')}
		</ClayLabel>
	);
};

const LabelProperty = ({hideField, label}) => {
	return hideField ? <span className="text-secondary">{label}</span> : label;
};

const RequiredProperty = () => {
	return (
		<span className="ddm-label-required reference-mark">
			<ClayIcon symbol="asterisk" />
		</span>
	);
};

const TooltipProperty = ({showPopover, tooltip}) => {
	return showPopover ? (
		<Popover tooltip={tooltip} />
	) : (
		<span className="ddm-tooltip" title={tooltip}>
			<ClayIcon symbol="question-circle-full" />
		</span>
	);
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

export function FieldBase({
	accessible = true,
	children,
	displayErrors,
	errorMessage,
	fieldName,
	hideField,
	hideEditedFlag,
	id,
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

	const hasError = displayErrors && errorMessage && !valid;

	const fieldDetails = getFieldDetails({
		errorMessage,
		hasError,
		required,
		text,
		tip,
	});

	let fieldDetailsId = id ?? name;

	fieldDetailsId = fieldDetailsId + '_fieldDetails';

	const accessibleProps =
		accessible && fieldDetails ? {'aria-labelledby': fieldDetailsId} : null;

	const hiddenTranslations = useMemo(() => {
		if (!localizedValue) {
			return;
		}

		return Object.entries(localizedValue).map(([locale, value]) => {
			if (locale === editingLanguageId) {
				return null;
			}

			return (
				<input
					key={locale}
					name={name.replace(editingLanguageId, locale)}
					type="hidden"
					value={normalizeInputValue(type, locale, value)}
				/>
			);
		});
	}, [localizedValue, editingLanguageId, name, type]);

	const renderLabel =
		(label && showLabel) || hideField || repeatable || required || tooltip;
	const repeatedIndex = useMemo(() => getRepeatedIndex(name), [name]);
	const showLegend =
		type === 'checkbox_multiple' ||
		type === 'grid' ||
		type === 'paragraph' ||
		type === 'radio';
	const showPopover = fieldName === 'inputMaskFormat';

	const defaultRows = nestedFields?.map((field) => ({
		columns: [{fields: [field], size: 12}],
	}));

	return (
		<div
			aria-labelledby={!renderLabel ? fieldDetailsId : null}
			className={classNames('form-group', {
				'has-error': hasError,
				'hide': !visible,
			})}
			data-field-name={name}
			onClick={onClick}
			style={style}
			tabIndex={!renderLabel ? 0 : undefined}
		>
			{repeatable && (
				<div className="lfr-ddm-form-field-repeatable-toolbar">
					{repeatedIndex > 0 && (
						<ClayButton
							aria-label={Liferay.Util.sub(
								Liferay.Language.get('remove-duplicate-field'),
								label ? label : type
							)}
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
						aria-label={Liferay.Util.sub(
							Liferay.Language.get('add-duplicate-field'),
							label ? label : type
						)}
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
								{...accessibleProps}
								className="lfr-ddm-legend"
								tabIndex={0}
							>
								{showLabel && label}

								{required && <RequiredProperty />}

								{tooltip && (
									<TooltipProperty
										showPopover={showPopover}
										tooltip={tooltip}
									/>
								)}
							</legend>

							{children}
						</fieldset>
					) : (
						<>
							<label
								{...accessibleProps}
								className={classNames({
									'ddm-empty': !showLabel && !required,
									'ddm-label': showLabel || required,
								})}
								htmlFor={id ?? name}
								tabIndex={0}
							>
								{showLabel && label && (
									<LabelProperty
										hideField={hideField}
										label={label}
									/>
								)}

								{required && <RequiredProperty />}

								{hideField && <HideFieldProperty />}

								{showLabel && tooltip && (
									<TooltipProperty
										showPopover={showPopover}
										tooltip={tooltip}
									/>
								)}
							</label>

							{children}

							{!showLabel && tooltip && (
								<TooltipProperty
									showPopover={showPopover}
									tooltip={tooltip}
								/>
							)}
						</>
					)}
				</>
			)}

			{!renderLabel && children}

			{hiddenTranslations}

			{!hideEditedFlag && (
				<input
					name={`${name}_edited`}
					type="hidden"
					value={localizedValue[editingLanguageId] !== undefined}
				/>
			)}

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

			{accessible && fieldDetails && (
				<span
					className="sr-only"
					dangerouslySetInnerHTML={{
						__html: fieldDetails,
					}}
					id={fieldDetailsId}
				/>
			)}

			{defaultRows && <Layout rows={defaultRows} />}
		</div>
	);
}
