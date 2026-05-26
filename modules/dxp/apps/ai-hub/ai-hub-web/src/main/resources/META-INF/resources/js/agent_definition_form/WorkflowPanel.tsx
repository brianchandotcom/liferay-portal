/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import Button from '@clayui/button';
import {Option, Picker} from '@clayui/core';
import Icon from '@clayui/icon';
import ClayPanel from '@clayui/panel';
import {Provider} from '@clayui/provider';
import {FormikErrors, FormikTouched} from 'formik';
import {FieldBase} from 'frontend-js-components-web';
import React from 'react';

import {AgentDefinition} from './types/AgentDefinition';
import {WorkflowDefinition} from './types/WorkflowDefinition';

interface IProps {
	errors: FormikErrors<AgentDefinition>;
	readOnly: boolean;
	setField: <K extends keyof AgentDefinition>(
		field: K,
		value: AgentDefinition[K]
	) => void;
	setFieldTouched: (field: string, isTouched?: boolean) => void;
	touched: FormikTouched<AgentDefinition>;
	values: AgentDefinition;
	workflowDefinitionURL: string;
	workflowDefinitions: WorkflowDefinition[];
}

const WorkflowPanel: React.FC<IProps> = ({
	errors,
	readOnly,
	setField,
	setFieldTouched,
	touched,
	values,
	workflowDefinitionURL,
	workflowDefinitions,
}) => {
	const handleViewWorkflow = () => {
		window.location.href = workflowDefinitionURL;
	};

	return (
		<ClayPanel
			className="agent-definition-form-workflow"
			collapsable={false}
		>
			<ClayPanel.Body>
				<div className="agent-definition-form-header">
					<h2>{Liferay.Language.get('workflow')}</h2>

					<Provider spritemap={Liferay.Icons.spritemap}>
						<Button
							displayType="secondary"
							onClick={handleViewWorkflow}
						>
							<span className="inline-item inline-item-before">
								<Icon symbol="icon-rule-builder" />
							</span>

							{Liferay.Language.get('view-workflow')}
						</Button>
					</Provider>
				</div>

				<FieldBase
					errorMessage={
						touched.workflowDefinitionName
							? errors.workflowDefinitionName
							: undefined
					}
					id="workflowDefinitionName"
					label={Liferay.Language.get('workflow-definition')}
					required
				>
					<Picker
						className="agent-definition-form-picker"
						disabled={readOnly}
						id="workflowDefinitionName"
						items={workflowDefinitions.map(
							(workflowDefinition) => ({
								label: workflowDefinition.title,
								value: workflowDefinition.name,
							})
						)}
						onSelectionChange={(value) => {
							setFieldTouched('workflowDefinitionName', true);
							setField('workflowDefinitionName', value as string);
						}}
						placeholder={Liferay.Language.get(
							'workflow-definition'
						)}
						selectedKey={values.workflowDefinitionName}
					>
						{({label, value}) => (
							<Option key={value}>{label}</Option>
						)}
					</Picker>
				</FieldBase>
			</ClayPanel.Body>
		</ClayPanel>
	);
};

export default WorkflowPanel;
