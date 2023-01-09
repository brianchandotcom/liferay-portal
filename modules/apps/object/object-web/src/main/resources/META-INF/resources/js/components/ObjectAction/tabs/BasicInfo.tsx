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

import ClayForm, {ClayToggle} from '@clayui/form';
import {
	Card,
	FormError,
	Input,
	InputLocalized,
} from '@liferay/object-js-components-web';
import React from 'react';

import {toCamelCase} from '../../../utils/string';

const defaultLanguageId = Liferay.ThemeDisplay.getDefaultLanguageId();

interface BasicInfoProps {
	errors: FormError<ObjectAction & ObjectActionParameters>;
	handleChange: React.ChangeEventHandler<HTMLInputElement>;
	isApproved: boolean;
	readOnly?: boolean;
	setValues: (values: Partial<ObjectAction>) => void;
	values: Partial<ObjectAction>;
}

export default function BasicInfo({
	errors,
	handleChange,
	isApproved,
	readOnly,
	setValues,
	values,
}: BasicInfoProps) {
	return (
		<Card title={Liferay.Language.get('basic-info')}>
			<InputLocalized
				error={errors.label}
				label={Liferay.Language.get('action-label')}
				name="label"
				onChange={(label) =>
					setValues({
						...values,
						...(!isApproved && {
							name: toCamelCase(label[defaultLanguageId] ?? ''),
						}),
						label,
					})
				}
				required
				translations={values.label ?? {[defaultLanguageId]: ''}}
			/>

			<Input
				disabled={isApproved}
				error={errors.name}
				label={Liferay.Language.get('action-name')}
				name="name"
				onChange={handleChange}
				required
				value={values.name}
			/>

			<Input
				component="textarea"
				error={errors.description}
				label={Liferay.Language.get('description')}
				name="description"
				onChange={handleChange}
				value={values.description}
			/>

			<ClayForm.Group>
				<ClayToggle
					disabled={readOnly}
					label={Liferay.Language.get('active')}
					name="indexed"
					onToggle={(active) => setValues({active})}
					toggled={values.active}
				/>
			</ClayForm.Group>
		</Card>
	);
}
