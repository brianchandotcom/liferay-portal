/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
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
	formText,
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
	const _renderInput = () => {
		switch (type) {
			case 'model':
				return (
					<ModelAutocomplete
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
						id={name}
						max={options.max}
						min={options.min}
						name={name}
						onBlur={onBlur}
						onChange={(event) => onChange(event.target.value)}
						required={required}
						type="number"
						value={value}
					/>
				);
			case 'select':
				return (
					<ClaySelect
						aria-label={label}
						id={name}
						name={name}
						onBlur={onBlur}
						onChange={(event) => onChange(event.target.value)}
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
						id={name}
						name={name}
						onBlur={onBlur}
						onChange={(event) => onChange(event.target.value)}
						required={required}
						type="text"
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

			{formText && (
				<ClayForm.FeedbackGroup>
					<ClayForm.Text>{formText}</ClayForm.Text>
				</ClayForm.FeedbackGroup>
			)}
		</ClayForm.Group>
	);
}

export default Input;
