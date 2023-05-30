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

import {
	Card,
	Input,
	InputLocalized,
	Toggle,
} from '@liferay/object-js-components-web';
import React from 'react';

import {TriggerEventContainer} from './TriggerEventContainer';
import {TabProps} from './useObjectValidationForm';

interface BasicInfoProps extends TabProps {
	componentLabel: string;
}

export function BasicInfo({
	componentLabel,
	disabled,
	errors,
	setValues,
	values,
}: BasicInfoProps) {
	return (
		<>
			<Card title={componentLabel}>
				<InputLocalized
					disabled={disabled}
					error={errors.name}
					label={Liferay.Language.get('label')}
					onChange={(name) => setValues({name})}
					placeholder={Liferay.Language.get('add-a-label')}
					required
					translations={values.name!}
				/>

				<Input
					disabled
					label={Liferay.Language.get('type')}
					value={values.engineLabel}
				/>

				<Toggle
					disabled={disabled}
					label={Liferay.Language.get('active-validation')}
					onToggle={(active) => setValues({active})}
					toggled={values.active}
				/>
			</Card>

			<TriggerEventContainer
				disabled={disabled}
				eventTypes={[{label: Liferay.Language.get('on-submission')}]}
			/>
		</>
	);
}
