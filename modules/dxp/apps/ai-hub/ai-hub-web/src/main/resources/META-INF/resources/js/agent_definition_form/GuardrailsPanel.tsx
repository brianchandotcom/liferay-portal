/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayMultiSelect from '@clayui/multi-select';
import ClayPanel from '@clayui/panel';
import React from 'react';

import {RelationshipPicker} from './hooks/useRelationshipPicker';
import {Guardrail} from './types/Guardrail';

interface IProps {
	guardrails: RelationshipPicker<Guardrail>;
	readOnly: boolean;
}

const GuardrailsPanel: React.FC<IProps> = ({guardrails, readOnly}) => {
	return (
		<ClayPanel
			className="agent-definition-form-guardrails"
			collapsable={false}
		>
			<ClayPanel.Body>
				<div className="agent-definition-form-header">
					<h2>{Liferay.Language.get('guardrails')}</h2>
				</div>

				<label htmlFor="assignedGuardrails">
					{Liferay.Language.get('assigned-guardrails')}
				</label>

				<ClayMultiSelect
					allowDuplicateValues={false}
					allowsCustomLabel={false}
					disabled={readOnly}
					inputName="assignedGuardrails"
					items={guardrails.selected}
					loadingState={4}
					locator={{
						label: 'title',
						value: 'externalReferenceCode',
					}}
					onChange={guardrails.setInputValue}
					onItemsChange={(items) =>
						guardrails.setSelected(items as Guardrail[])
					}
					sourceItems={guardrails.sourceList}
					value={guardrails.inputValue}
				/>
			</ClayPanel.Body>
		</ClayPanel>
	);
};

export default GuardrailsPanel;
