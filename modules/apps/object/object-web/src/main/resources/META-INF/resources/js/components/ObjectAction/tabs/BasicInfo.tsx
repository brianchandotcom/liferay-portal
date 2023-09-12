/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayForm from '@clayui/form';
import {
	Card,
	FormError,
	Input,
	Toggle,
} from '@liferay/object-js-components-web';
import {InputLocalized} from 'frontend-js-components-web';
import React from 'react';

import {defaultLanguageId} from '../../../utils/constants';
import {toCamelCase} from '../../../utils/string';

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
				<Toggle
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
