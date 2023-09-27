/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayForm, {ClayInput, ClaySelect, ClaySelectBox} from '@clayui/form';
import ClayIcon from '@clayui/icon';
import {ClayTooltipProvider} from '@clayui/tooltip';
import getCN from 'classnames';
import React from 'react';

import ModelAutocomplete from './ModelAutocomplete';

const SELECT_BOX_SHOW_ITEMS_COUNT = 6;

function Input({
	error,
	children,
	disabled,
	helpText,
	label,
	name,
	onBlur,
	onChange,
	items,
	options = {},
	required = false,
	touched = false,
	type,
	value,
}) {
	const _handleEventChange = (event) => {
		onChange(event.target.value);
	};

	const _renderInput = () => {
		switch (type) {
			case 'model':
				return (
					<ModelAutocomplete
						disabled={disabled}
						label={label}
						name={name}
						onBlur={onBlur}
						onChange={onChange}
						required={required}
						value={value}
					/>
				);
			case 'multiple':
				return (
					<ClaySelectBox
						aria-label={label}
						className="mb-0" // Suppress extra margin from ClaySelectBox
						disabled={disabled}
						items={items}
						multiple
						name={name}
						onBlur={onBlur}
						onSelectChange={onChange}
						required={required}
						size={SELECT_BOX_SHOW_ITEMS_COUNT}
						value={value}
					/>
				);
			case 'number':
				return (
					<ClayInput
						aria-label={label}
						disabled={disabled}
						id={name}
						max={options.max}
						min={options.min}
						name={name}
						onBlur={onBlur}
						onChange={_handleEventChange}
						required={required}
						type="number"
						value={value}
					/>
				);
			case 'select':
				return (
					<ClaySelect
						aria-label={label}
						disabled={disabled}
						id={name}
						name={name}
						onBlur={onBlur}
						onChange={_handleEventChange}
						required={required}
						value={value}
					>
						{items.map((item) => (
							<ClaySelect.Option
								key={item.value}
								label={item.label}
								value={item.value}
							/>
						))}
					</ClaySelect>
				);
			default:
				return (
					<ClayInput
						aria-label={label}
						disabled={disabled}
						id={name}
						name={name}
						onBlur={onBlur}
						onChange={_handleEventChange}
						required={required}
						type={type || 'text'}
						value={value || ''}
					/>
				);
		}
	};

	return (
		<ClayForm.Group
			className={getCN({
				'has-error': error && touched,
			})}
		>
			<label htmlFor={name}>
				{label}

				{required && (
					<span className="reference-mark">
						<ClayIcon symbol="asterisk" />
					</span>
				)}

				{helpText && (
					<ClayTooltipProvider>
						<span className="ml-2" title={helpText}>
							<ClayIcon symbol="question-circle-full" />
						</span>
					</ClayTooltipProvider>
				)}
			</label>

			{_renderInput()}

			{error && touched && (
				<ClayForm.FeedbackGroup>
					<ClayForm.FeedbackItem>{error}</ClayForm.FeedbackItem>
				</ClayForm.FeedbackGroup>
			)}

			{children}
		</ClayForm.Group>
	);
}

export default Input;
