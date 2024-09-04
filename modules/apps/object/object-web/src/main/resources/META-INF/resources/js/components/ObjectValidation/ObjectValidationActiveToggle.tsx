/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Toggle} from '@liferay/object-js-components-web';
import React from 'react';

export interface ObjectValidationActiveToggleProps {
	disabled: boolean;
	disabledGroovyValidation: boolean;
	setValues: (values: Partial<ObjectValidation>) => void;
	values: Partial<ObjectValidation>;
}

export function getDisabledValidationActionToggleState(
	disabled: boolean,
	disabledGroovyValidation: boolean,
	objectValidationRuleSettings: ObjectValidationRuleSetting[]
) {
	const allowActiveStatusUpdate = objectValidationRuleSettings.find(
		({name}) => name === 'allowActiveStatusUpdate'
	);

	if (allowActiveStatusUpdate) {
		return allowActiveStatusUpdate.value === 'false';
	}

	return disabled || disabledGroovyValidation;
}

export function ObjectValidationActiveToggle({
	disabled,
	disabledGroovyValidation,
	setValues,
	values,
}: ObjectValidationActiveToggleProps) {
	return (
		<Toggle
			disabled={getDisabledValidationActionToggleState(
				disabled,
				disabledGroovyValidation,
				values.objectValidationRuleSettings ?? []
			)}
			label={Liferay.Language.get('active-validation')}
			onToggle={(active) => setValues({active})}
			toggled={values.active}
		/>
	);
}
