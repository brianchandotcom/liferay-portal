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

import ClayForm, {ClayInput, ClaySelect} from '@clayui/form';
import classNames from 'classnames';
import React, {useState} from 'react';

import TokenGroup from '../components/TokenGroup';
import TokenItem from '../components/TokenItem';

const FORM_STATES = ['', 'error', 'warning', 'success'];

const INPUT_VARIANTS = [
	{
		categoryLabel: Liferay.Language.get('inputs'),
		condensed: false,
		disabled: false,
		value: false,
	},
	{
		categoryLabel: Liferay.Language.get('inputs-condensed'),
		condensed: true,
		disabled: false,
		value: false,
	},
	{
		categoryLabel: Liferay.Language.get('inputs-with-value'),
		condensed: false,
		disabled: false,
		value: true,
	},
	{
		categoryLabel: Liferay.Language.get('inputs-condensed-with-value'),
		condensed: true,
		disabled: false,
		value: true,
	},
	{
		categoryLabel: Liferay.Language.get('disabled-inputs'),
		condensed: false,
		disabled: true,
		value: false,
	},
	{
		categoryLabel: Liferay.Language.get('disabled-inputs-condensed'),
		condensed: true,
		disabled: true,
		value: false,
	},
	{
		categoryLabel: Liferay.Language.get('disabled-inputs-with-value'),
		condensed: false,
		disabled: true,
		value: true,
	},
	{
		categoryLabel: Liferay.Language.get(
			'disabled-inputs-condensed-with-value'
		),
		condensed: true,
		disabled: true,
		value: true,
	},
];

const SELECT_OPTIONS = [
	{
		label: '--Please choose an option--',
		value: '',
	},
	{
		label: 'Option 1',
		value: '1',
	},
	{
		label: 'Option 2',
		value: '2',
	},
];

const STATE_CLASS_NAME_MAP = {
	error: 'has-error',
	success: 'has-success',
	warning: 'has-warning',
};

const Input = (props) => {
	const {
		component = 'input',
		condensed,
		disabled,
		formText,
		id,
		label,
		message,
		placeholder,
		state,
		value: initValue = '',
	} = props;

	const [value, setValue] = useState(initValue);

	return (
		<ClayForm.Group
			className={classNames({
				disabled,
				filled: value.length > 0,
				['form-condensed']: condensed,
				[STATE_CLASS_NAME_MAP[state]]: state,
			})}
		>
			<label className={classNames(disabled)} htmlFor={id}>
				{label}
			</label>

			<ClayInput
				component={component}
				disabled={disabled}
				id={id}
				onChange={(event) => {
					setValue(event.currentTarget.value);
				}}
				placeholder={placeholder}
				value={value}
			/>

			{(message || formText) && (
				<div className="form-feedback-group">
					{message && (
						<div className="form-feedback-item">
							<span className="form-feedback-indicator"></span>
							{message}
						</div>
					)}

					{formText && <div className="form-text">{formText}</div>}
				</div>
			)}
		</ClayForm.Group>
	);
};

const Select = (props) => {
	const {
		condensed,
		disabled,
		formText,
		id,
		label,
		message,
		state,
		value: initValue = 0,
	} = props;

	const [value, setValue] = useState(initValue);

	return (
		<ClayForm.Group
			className={classNames({
				disabled,
				filled: value.length > 0,
				['form-condensed']: condensed,
				[STATE_CLASS_NAME_MAP[state]]: state,
			})}
		>
			<label className={classNames(disabled)} htmlFor={id}>
				{label}
			</label>

			<ClaySelect
				aria-label={label}
				disabled={disabled}
				id={id}
				onChange={(event) => {
					setValue(event.currentTarget.value);
				}}
				value={value}
			>
				{SELECT_OPTIONS.map((item) => (
					<ClaySelect.Option
						key={item.value}
						label={item.label}
						value={item.value}
					/>
				))}
			</ClaySelect>

			{(message || formText) && (
				<div className="form-feedback-group">
					{message && (
						<div className="form-feedback-item">
							<span className="form-feedback-indicator"></span>
							{message}
						</div>
					)}

					{formText && <div className="form-text">{formText}</div>}
				</div>
			)}
		</ClayForm.Group>
	);
};

const FormGuide = () => {
	return (
		<>
			{INPUT_VARIANTS.map((variant, j) => (
				<TokenGroup group="form" key={j} title={variant.categoryLabel}>
					{FORM_STATES.map((state, i) => (
						<TokenItem key={state} size="small">
							<Input
								condensed={variant.condensed}
								disabled={variant.disabled}
								formText="Form sample text."
								id={i}
								label="Label"
								message="Form feedback message."
								placeholder="Placeholder Content"
								state={state}
								value={variant.value ? 'Hello World' : ''}
							/>
						</TokenItem>
					))}

					{FORM_STATES.map((item, i) => (
						<TokenItem key={item} size="small">
							<Input
								component="textarea"
								condensed={variant.condensed}
								disabled={variant.disabled}
								formText="Form sample text."
								id={i}
								label="Label"
								message="Form feedback message."
								placeholder="Placeholder Content"
								state={item}
								value={variant.value ? 'Hello World' : ''}
							/>
						</TokenItem>
					))}

					{FORM_STATES.map((item, i) => (
						<TokenItem key={item} size="small">
							<Select
								condensed={variant.condensed}
								disabled={variant.disabled}
								formText="Form sample text."
								id={i}
								label="Label"
								message="Form feedback message."
								state={item}
								value={variant.value ? '1' : ''}
							/>
						</TokenItem>
					))}
				</TokenGroup>
			))}
		</>
	);
};

export default FormGuide;
