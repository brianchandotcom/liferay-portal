/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ClayInput} from '@clayui/form';
import ClayPanel from '@clayui/panel';
import {FormikErrors, FormikTouched} from 'formik';
import {FieldBase} from 'frontend-js-components-web';
import React, {FocusEventHandler} from 'react';

import {AgentDefinition} from './types/AgentDefinition';

interface IProps {
	errors: FormikErrors<AgentDefinition>;
	handleBlur: FocusEventHandler;
	readOnly: boolean;
	setField: <K extends keyof AgentDefinition>(
		field: K,
		value: AgentDefinition[K]
	) => void;
	touched: FormikTouched<AgentDefinition>;
	values: AgentDefinition;
}

const VariablesPanel: React.FC<IProps> = ({
	errors,
	handleBlur,
	readOnly,
	setField,
	touched,
	values,
}) => {
	return (
		<ClayPanel
			className="agent-definition-form-variables"
			collapsable={false}
		>
			<ClayPanel.Body>
				<div className="agent-definition-form-header">
					<h2>{Liferay.Language.get('variables')}</h2>
				</div>

				<FieldBase
					errorMessage={
						touched.inputVariables
							? errors.inputVariables
							: undefined
					}
					id="input-variables"
					label={Liferay.Language.get('input-variables')}
					required
				>
					<ClayInput
						className={
							touched.inputVariables && errors.inputVariables
								? 'is-invalid'
								: ''
						}
						disabled={readOnly}
						id="input-variables"
						name="inputVariables"
						onBlur={handleBlur}
						onChange={(event) =>
							setField('inputVariables', event.target.value)
						}
						placeholder="inputVariable1,inputVariable2"
						required
						type="text"
						value={values.inputVariables}
					/>
				</FieldBase>

				<FieldBase
					errorMessage={
						touched.outputVariable
							? errors.outputVariable
							: undefined
					}
					id="output-variable"
					label={Liferay.Language.get('output-variable')}
					required
				>
					<ClayInput
						className={
							touched.outputVariable && errors.outputVariable
								? 'is-invalid'
								: ''
						}
						disabled={readOnly}
						id="output-variable"
						name="outputVariable"
						onBlur={handleBlur}
						onChange={(event) =>
							setField('outputVariable', event.target.value)
						}
						placeholder="outputVariable"
						required
						type="text"
						value={values.outputVariable}
					/>
				</FieldBase>
			</ClayPanel.Body>
		</ClayPanel>
	);
};

export default VariablesPanel;
