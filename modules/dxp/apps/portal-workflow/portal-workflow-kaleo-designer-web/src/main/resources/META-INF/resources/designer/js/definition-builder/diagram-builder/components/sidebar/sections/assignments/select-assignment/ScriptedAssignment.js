/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 */

import ClayButton from '@clayui/button';
import React from 'react';

import SidebarPanel from '../../../SidebarPanel';

const ScriptedAssignment = ({setContentName}) => {
	const addSourceButtonName = Liferay.Language.get('add-source-code');
	const panelTitle = `${Liferay.Language.get(
		'source-code'
	)} (${Liferay.Language.get('groovy')})`;

	return (
		<SidebarPanel panelTitle={panelTitle}>
			<ClayButton
				displayType="secondary"
				onClick={() => setContentName('scripted-assignment')}
			>
				{addSourceButtonName.toUpperCase()}
			</ClayButton>
		</SidebarPanel>
	);
};

export default ScriptedAssignment;
