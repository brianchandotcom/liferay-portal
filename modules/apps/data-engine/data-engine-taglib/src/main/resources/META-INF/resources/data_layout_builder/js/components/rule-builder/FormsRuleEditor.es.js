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

import './FormsRuleEditor.scss';

import ClayButton from '@clayui/button';
import React, {useRef, useState} from 'react';

import {Editor} from './editor/Editor.es';

export const FormsRuleEditor = ({onCancel, onSave, ...otherProps}) => {
	const [disabled, setDisabled] = useState(true);
	const ruleRef = useRef(null);

	return (
		<div className="form-rule-builder">
			<div className="form-rule-builder-header">
				<h2 className="text-default">{Liferay.Language.get('rule')}</h2>

				<h4 className="text-default">
					{Liferay.Language.get(
						'define-condition-and-action-to-change-fields-and-elements-on-the-form'
					)}
				</h4>
			</div>
			<Editor
				onChange={({logicalOperator, ...otherProps}) => {
					ruleRef.current = {
						...otherProps,
						['logical-operator']: logicalOperator,
					};
				}}
				onValidator={setDisabled}
				{...otherProps}
			/>
			<div className="form-rule-builder-footer">
				<ClayButton.Group spaced>
					<ClayButton
						disabled={disabled}
						displayType="primary"
						onClick={() => onSave(ruleRef.current)}
					>
						{Liferay.Language.get('save')}
					</ClayButton>
					<ClayButton displayType="secondary" onClick={onCancel}>
						{Liferay.Language.get('cancel')}
					</ClayButton>
				</ClayButton.Group>
			</div>
		</div>
	);
};
