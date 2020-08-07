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
import {ClayTooltipProvider} from '@clayui/tooltip';
import classNames from 'classnames';
import {
	EVENT_TYPES,
	Layout,
	getRepeatedIndex,
	useForm,
	usePage,
} from 'dynamic-data-mapping-form-renderer';
import React, {useMemo} from 'react';

const convertValueToString = (value) => {
	if (value && typeof value === 'object') {
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

function FieldBase({
	children,
	displayErrors,
	errorMessage,
	label,
	localizedValue = {},
	name,
	nestedFields,
	onClick,
	readOnly,
	repeatable,
	required,
	showLabel = true,
	tip,
	tooltip,
	valid,
	visible,
}) {
	const {editingLanguageId = themeDisplay.getLanguageId()} = usePage();
	const dispatch = useForm();

	const localizedValueArray = useMemo(() => {
		const languageValues = [];

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
	const repeatedIndex = useMemo(() => getRepeatedIndex(name), [name]);

	return (
		<ClayTooltipProvider>
			<div
				className={classNames('form-group', {
					'has-error': displayErrors && errorMessage && !valid,
					hide: !visible,
				})}
				data-field-name={name}
				onClick={onClick}
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
										type: EVENT_TYPES.FIELD_REMOVED,
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
							className="ddm-form-field-repeatable-add-button p-0"
							disabled={readOnly}
							onClick={() =>
								dispatch({
									payload: name,
									type: EVENT_TYPES.FIELD_REPEATED,
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

				{((label && showLabel) ||
					required ||
					tooltip ||
					repeatable) && (
					<p
						className={classNames({
							'ddm-empty': !showLabel && !required,
							'ddm-label': showLabel || required,
						})}
					>
						{label && showLabel && label}

						{required && (
							<span className="reference-mark">
								<ClayIcon symbol="asterisk" />
							</span>
						)}

						{tooltip && (
							<span className="ddm-tooltip">
								<ClayIcon
									data-tooltip-align="right"
									symbol="question-circle-full"
									title={tooltip}
								/>
							</span>
						)}
					</p>
				)}

				{children}

				{localizedValueArray.length > 0 &&
					localizedValueArray.map((language) => (
						<input
							key={language.name}
							name={language.name}
							type="hidden"
							value={convertValueToString(language.value)}
						/>
					))}

				{tip && <span className="form-text">{tip}</span>}

				{displayErrors && errorMessage && !valid && (
					<span className="form-feedback-group">
						<div className="form-feedback-item">{errorMessage}</div>
					</span>
				)}

				{nestedFields && <Layout rows={getDefaultRows(nestedFields)} />}
			</div>
		</ClayTooltipProvider>
	);
}

export {FieldBase};
