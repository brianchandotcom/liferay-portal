/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton, {ClayButtonWithIcon} from '@clayui/button';
import {ClaySelect} from '@clayui/form';
import ClayLayout from '@clayui/layout';
import ClayLink from '@clayui/link';
import React, {useContext, useEffect, useState} from 'react';

import {DiagramBuilderContext} from '../../../../../DiagramBuilderContext';
import SidebarPanel from '../../../SidebarPanel';

const scriptLanguageOptions = [
	{
		label: Liferay.Language.get('groovy'),
		value: 'groovy',
	},
	{
		label: Liferay.Language.get('java'),
		value: 'java',
	},
];

const ScriptedAssignment = ({setContentName}) => {
	const {selectedItem, setSelectedItem} = useContext(DiagramBuilderContext);

	const [showScriptData, setShowScriptData] = useState(
		selectedItem?.data.assignments?.script
	);

	const [scriptLanguage, setScriptLanguage] = useState(
		selectedItem?.data.assignments?.scriptLanguage
	);

	const addSourceButtonName = Liferay.Language.get('add-source-code');

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
		<SidebarPanel panelTitle={Liferay.Language.get('script')}>
			<label htmlFor="script-language">
				{Liferay.Language.get('script-language')}
			</label>

			<ClaySelect
				aria-label="Select"
				defaultValue={scriptLanguage}
				id="script-language"
				onChange={({target}) => {
					setScriptLanguage(target.value);

					setSelectedItem((previous) => {
						return {
							...previous,
							data: {
								...previous.data,
								assignments: {
									...previous.data.assignments,
									scriptLanguage: [target.value],
								},
							},
						};
					});
				}}
			>
				{scriptLanguageOptions &&
					scriptLanguageOptions.map((item) => (
						<ClaySelect.Option
							key={item.value}
							label={item.label}
							value={item.value}
						/>
					))}
			</ClaySelect>

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
