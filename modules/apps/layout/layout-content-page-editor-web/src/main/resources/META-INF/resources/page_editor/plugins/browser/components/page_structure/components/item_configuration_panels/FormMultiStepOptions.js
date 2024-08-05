/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useControlledState} from '@liferay/layout-js-components-web';
import React from 'react';

import {SelectField} from '../../../../../../app/components/fragment_configuration_fields/SelectField';
import {TextField} from '../../../../../../app/components/fragment_configuration_fields/TextField';

const FORM_TYPE_OPTIONS = [
	{label: Liferay.Language.get('simple'), value: 'simple'},
	{label: Liferay.Language.get('multi-step'), value: 'multi-step'},
];

export default function FormMultiStepOptions({item, onValueSelect}) {
	const [isMultiStep, setIsMultiStep] = useControlledState(
		item.config.isMultiStep
	);

	const [numberOfSteps, setNumberOfSteps] = useControlledState(
		item.config.numberOfSteps
	);

	return (
		<>
			<SelectField
				className="mb-2"
				field={{
					label: Liferay.Language.get('form-type'),
					name: 'formType',
					typeOptions: {
						validValues: FORM_TYPE_OPTIONS,
					},
				}}
				onValueSelect={(_name, formType) => {
					setIsMultiStep(formType === 'multi-step');
					onValueSelect({
						isMultiStep: formType === 'multi-step',
						numberOfSteps: formType === 'multi-step' ? 2 : 1,
					});
				}}
				value={isMultiStep ? 'multi-step' : 'simple'}
			/>

			{isMultiStep ? (
				<TextField
					field={{
						label: Liferay.Language.get('number-of-steps'),
						typeOptions: {
							validation: {
								min: 2,
								type: 'number',
							},
						},
					}}
					onValueSelect={(_, numberOfSteps) => {
						setNumberOfSteps(numberOfSteps);
						onValueSelect({numberOfSteps});
					}}
					value={numberOfSteps || 2}
				/>
			) : null}
		</>
	);
}
