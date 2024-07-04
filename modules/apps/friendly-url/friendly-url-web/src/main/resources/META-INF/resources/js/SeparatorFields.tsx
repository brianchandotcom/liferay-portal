/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ClayButtonWithIcon} from '@clayui/button';
import ClayForm, {ClayInput} from '@clayui/form';
import classNames from 'classnames';
import {useId} from 'frontend-js-components-web';
import {sub} from 'frontend-js-web';
import React, {useRef, useState} from 'react';

type Errors = {
	errorMessage?: string;
	fields?: Record<string, string>;
};

type Field = {
	defaultValue: string;
	label: string;
	name: string;
	value: string;
};

type FieldsProps = {
	errors: Errors;
	fields: Field[];
	url: string;
};

export default function SeparatorFields({errors, fields, url}: FieldsProps) {
	return (
		<>
			{fields.map((field) => (
				<Field
					errors={errors}
					field={field}
					key={field.name}
					url={url}
				/>
			))}
		</>
	);
}

type FieldProps = {
	errors: Errors;
	field: Field;
	url: string;
};

function Field({errors, field, url}: FieldProps) {
	const descriptionId = useId();
	const ref = useRef<HTMLInputElement>(null);

	const {defaultValue, label, name} = field;
	const error = errors.fields?.[name];

	const [value, setValue] = useState(field.value);

	return (
		<ClayForm.Group
			className={classNames({
				'has-error': error,
			})}
			key={name}
		>
			<label className="mb-0" htmlFor={name}>
				{label}
			</label>

			<p className="mb-1 small text-secondary">{url}</p>

			<p className="sr-only" id={descriptionId}>
				{sub(
					Liferay.Language.get('this-will-work-as-a-suffix-for-x'),
					url
				)}
			</p>

			<ClayInput.Group>
				<ClayInput.GroupItem prepend shrink>
					<ClayInput.GroupText aria-hidden="true">
						/
					</ClayInput.GroupText>
				</ClayInput.GroupItem>

				<ClayInput.GroupItem append>
					<ClayInput
						aria-describedby={descriptionId}
						id={name}
						name={name}
						onChange={(event) => setValue(event.target.value)}
						ref={ref}
						value={value}
					/>
				</ClayInput.GroupItem>

				{value !== defaultValue ? (
					<ClayInput.GroupItem shrink>
						<ClayButtonWithIcon
							aria-label={Liferay.Language.get(
								'reset-to-default-value'
							)}
							displayType="secondary"
							onClick={() => {
								setValue(defaultValue);

								ref.current?.focus();
							}}
							symbol="restore"
							title={Liferay.Language.get(
								'reset-to-default-value'
							)}
						/>
					</ClayInput.GroupItem>
				) : null}
			</ClayInput.Group>

			{error ? (
				<ClayForm.FeedbackGroup>
					<ClayForm.FeedbackItem>
						<ClayForm.FeedbackIndicator symbol="exclamation-full" />

						{error}
					</ClayForm.FeedbackItem>
				</ClayForm.FeedbackGroup>
			) : null}
		</ClayForm.Group>
	);
}
