/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton, {ClayButtonWithIcon} from '@clayui/button';
import ClayLayout from '@clayui/layout';
import ClayLink from '@clayui/link';
import React, {useContext, useEffect, useState} from 'react';

import {DiagramBuilderContext} from '../../../../../DiagramBuilderContext';
import SidebarPanel from '../../../SidebarPanel';

const ScriptedReassignment = ({actionData, setContentName}) => {
	const {selectedItem, setSelectedItem} = useContext(DiagramBuilderContext);

	const [showScriptData, setShowScriptData] = useState(
		selectedItem?.data.taskTimers?.reassignments?.script
	);

	const addSourceButtonName = Liferay.Language.get('add-source-code');
	const panelTitle = `${Liferay.Language.get(
		'source-code'
	)} (${Liferay.Language.get('groovy')})`;

	const goToEditor = () => setContentName('scripted-reassignment');

	const deleteScript = () => {
		setSelectedItem((previous) => {
			return {
				...previous,
				data: {
					...previous.data,
					reassignments: null,
				},
			};
		});
	};

	useEffect(() => {
		setShowScriptData(actionData?.script);
	}, [actionData]);

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

export default ScriptedReassignment;
