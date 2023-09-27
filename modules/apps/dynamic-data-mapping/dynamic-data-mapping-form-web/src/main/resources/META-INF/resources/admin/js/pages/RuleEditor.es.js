/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import './RuleEditor.scss';

import ClayButton from '@clayui/button';
import ClayForm from '@clayui/form';
import {RuleEditor as DataEngineRuleEditor} from 'data-engine-taglib';
import React, {useState} from 'react';

// A simple implementation of local storage outside
// the React lifecycle to maintain the state of the
// saved rule while the user can browse other pages.
// The status is reset when Save or Cancel is invoked.

const localDataStorage = {
	rule: undefined,
};

export function RuleEditor({onCancel, onSave, rule, ...otherProps}) {
	const [disabled, setDisabled] = useState(true);

	return (
		<ClayForm
			className="form-rule-builder"
			onSubmit={(event) => event.preventDefault()}
		>
			<div className="form-rule-builder-header">
				<h2 className="text-default">{Liferay.Language.get('rule')}</h2>

				<h4 className="text-default">
					{Liferay.Language.get(
						'define-condition-and-action-to-change-fields-and-elements-on-the-form'
					)}
				</h4>
			</div>

			<DataEngineRuleEditor
				onChange={({logicalOperator, ...otherProps}) => {
					localDataStorage.rule = {
						...otherProps,
						['logical-operator']: logicalOperator,
					};
				}}
				onValidator={(value) => setDisabled(!value)}
				rule={localDataStorage.rule ?? rule}
				{...otherProps}
			/>

			<div className="form-rule-builder-footer">
				<ClayButton.Group spaced>
					<ClayButton
						disabled={disabled}
						displayType="primary"
						onClick={() => {
							onSave(localDataStorage.rule);
							localDataStorage.rule = undefined;
						}}
					>
						{Liferay.Language.get('save')}
					</ClayButton>

					<ClayButton
						displayType="secondary"
						onClick={() => {
							localDataStorage.rule = undefined;
							onCancel();
						}}
					>
						{Liferay.Language.get('cancel')}
					</ClayButton>
				</ClayButton.Group>
			</div>
		</ClayForm>
	);
}
