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

import ClayButton, {ClayButtonWithIcon} from '@clayui/button';
import ClayLayout from '@clayui/layout';
import ClayLink from '@clayui/link';
import React, {useContext, useEffect, useState} from 'react';

import {DiagramBuilderContext} from '../../../../../DiagramBuilderContext';
import SidebarPanel from '../../../SidebarPanel';

const ScriptedAssignment = ({setContentName}) => {
	const {selectedItem, setSelectedItem} = useContext(DiagramBuilderContext);

	const [showScriptData, setShowScriptData] = useState(
		selectedItem?.data.assignments?.script
	);

	const addSourceButtonName = Liferay.Language.get('add-source-code');
	const panelTitle = `${Liferay.Language.get(
		'source-code'
	)} (${Liferay.Language.get('groovy')})`;

	const goToEditor = () => setContentName('scripted-assignment');

	const deleteScript = () => {
		setSelectedItem((previous) => {
			return {
				...previous,
				data: {...previous.data, assignments: null},
			};
		});
	};

	useEffect(() => {
		setShowScriptData(selectedItem?.data.assignments?.script);
	}, [selectedItem]);

	return (
		<SidebarPanel panelTitle={panelTitle}>
			{showScriptData ? (
				<ClayLayout.ContentCol className="current-node-data-area" float>
					<ClayLayout.Row
						className="current-node-data-row"
						justify="between"
					>
						<ClayLink
							button={false}
							className="truncate-container"
							displayType="secondary"
							href="#"
							onClick={goToEditor}
						>
							<span>{Liferay.Language.get('script')}</span>
						</ClayLink>

						<ClayButtonWithIcon
							className="delete-button text-secondary trash-button"
							displayType="unstyled"
							onClick={deleteScript}
							symbol="trash"
						/>
					</ClayLayout.Row>
				</ClayLayout.ContentCol>
			) : (
				<ClayButton displayType="secondary" onClick={goToEditor}>
					{addSourceButtonName.toUpperCase()}
				</ClayButton>
			)}
		</SidebarPanel>
	);
};

export default ScriptedAssignment;
