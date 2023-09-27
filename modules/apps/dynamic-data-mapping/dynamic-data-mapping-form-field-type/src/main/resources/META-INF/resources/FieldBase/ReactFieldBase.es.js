/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import ClayForm from '@clayui/form';
import ClayIcon from '@clayui/icon';
import ClayLabel from '@clayui/label';
import ClayPopover from '@clayui/popover';
import classNames from 'classnames';
import {
	EVENT_TYPES as CORE_EVENT_TYPES,
	FieldFeedback,
	Layout,
	PagesVisitor,
	useForm,
	useFormState,
} from 'data-engine-js-components-web';
import {sub} from 'frontend-js-web';
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
		return !Object.keys(value).length ? '' : JSON.stringify(value);
	}

	return value;
}

const getFieldDetails = ({
	errorMessage,
	hasError,
	label,
	required,
	text,
	tip,
	warningMessage,
}) => {
	const fieldDetails = [];

	if (label) {
		fieldDetails.push(Liferay.Util.escape(label));
	}

	if (tip) {
		fieldDetails.push(Liferay.Util.escape(tip));
	}

	if (text) {
		fieldDetails.push(Liferay.Util.escape(text));
	}

	if (hasError) {
		fieldDetails.push(Liferay.Util.escape(errorMessage));
	}
	else {
		if (warningMessage) {
			fieldDetails.push(Liferay.Util.escape(warningMessage));
		}
		if (required) {
			fieldDetails.push(Liferay.Language.get('required'));
		}
	}

	return fieldDetails.length ? fieldDetails.join('<br>') : false;
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
			onShowChange={setPopoverVisible}
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
	fieldReference,
	hideField,
	hideEditedFlag,
	id,
	itemPath,
	label,
	localizedValue = {},
	name,
	nestedFields,
	onClick,
	overMaximumRepetitionsLimit,
	parentInstanceId,
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
	warningMessage,
}) {
	const {editingLanguageId, pages} = useFormState();
	const dispatch = useForm();

	const hasError = displayErrors && errorMessage && !valid;

	const fieldDetails = getFieldDetails({
		errorMessage,
		hasError,
		label,
		required,
		text,
		tip,
		warningMessage,
	});

	const fieldDetailsId = `${id ?? name}_fieldDetails`;

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
	const showLegend =
		type === 'checkbox_multiple' ||
		type === 'grid' ||
		type === 'paragraph' ||
		type === 'radio';
	const showPopover = fieldName === 'inputMaskFormat';
	const showFor =
		type === 'text' ||
		type === 'numeric' ||
		type === 'image' ||
		type === 'rich_text' ||
		type === 'search_location' ||
		type === 'select';
	const readFieldDetails = !showFor || type === 'select';
	const hasFieldDetails = accessible && fieldDetails && readFieldDetails;

	const accessiblePropsGroup = {
		...(!renderLabel && {'aria-labelledby': fieldDetailsId}),
		...(type === 'fieldset' && {role: 'group'}),
	};

	const accessiblePropsFields = {
		...(hasFieldDetails && {'aria-labelledby': fieldDetailsId}),
		...(showFor && {htmlFor: id ?? name}),
		...(readFieldDetails && {tabIndex: 0}),
	};

	const defaultRows = nestedFields?.map((field) => ({
		columns: [{fields: [field], size: 12}],
	}));

	const checkRepetitions = () => {
		let repetitionsCounter = 0;

		const visitor = new PagesVisitor(pages);

		const newParentInstanceId = parentInstanceId;

		visitor.mapFields(
			(field) => {
				if (
					fieldReference === field.fieldReference &&
					newParentInstanceId === field.parentInstanceId
				) {
					repetitionsCounter++;
				}
			},
			true,
			true
		);

		return repetitionsCounter;
	};

	return (
		<ClayForm.Group
			{...accessiblePropsGroup}
			className={classNames({
				'has-error': hasError,
				'has-warning': warningMessage && !hasError,
				'hide': !visible,
			})}
			data-field-name={name}
			data-field-reference={fieldReference}
			onClick={onClick}
			style={style}
		>
			{repeatable && (
				<div className="lfr-ddm-form-field-repeatable-toolbar">
					{checkRepetitions() > 1 && (
						<ClayButton
							aria-label={sub(
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
						aria-label={sub(
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
								{...accessiblePropsFields}
								className="lfr-ddm-legend"
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
								{...accessiblePropsFields}
								className={classNames({
									'ddm-empty': !showLabel && !required,
									'ddm-label': showLabel || required,
									'ddm-repeatable': repeatable,
								})}
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

			<FieldFeedback
				aria-hidden={readFieldDetails}
				errorMessage={hasError ? errorMessage : undefined}
				helpMessage={typeof tip === 'string' ? tip : undefined}
				name={id ?? name}
				warningMessage={warningMessage}
			/>

			{hasFieldDetails && (
				<span
					className="sr-only"
					dangerouslySetInnerHTML={{
						__html: fieldDetails,
					}}
					id={fieldDetailsId}
				/>
			)}

			{defaultRows && <Layout itemPath={itemPath} rows={defaultRows} />}
		</ClayForm.Group>
	);
}
