/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayTabs from '@clayui/tabs';
import React, {useEffect, useState} from 'react';

let tabs = [];

export default function ({
	hasCPDefinitionSpecificationOptionValues,
	hasCPMedia,
	hasDescription,
	hasReplacements,
	namespace,
	navCPMediaId,
	navDescriptionId,
	navReplacementsId,
	navSpecificationsId,
}) {
	const [activeTabKeyValue, setActiveTabKeyValue] = useState(0);
	const [firstRender, setFirstRender] = useState(true);

	const display = (tabId) => {
		for (let i = 0; i < tabs.length; i++) {
			const currentTabId = tabs[i].id;
			const tabElt = document.getElementById(currentTabId);

			if (tabId === currentTabId) {
				tabElt.classList.add('active', 'show');
			}
			else {
				tabElt.classList.remove('active', 'show');
			}
			if (!firstRender && currentTabId === navReplacementsId) {
				tabElt.style.display = null;
				tabElt.style.visibility = null;
				tabElt.style.height = null;
			}
		}
	};

	useEffect(() => {
		tabs = document.getElementsByClassName(namespace + 'tab-element');
		if (hasDescription) {
			display(navDescriptionId);
			setActiveTabKeyValue(0);
		}
		else if (hasCPDefinitionSpecificationOptionValues) {
			display(navSpecificationsId);
			setActiveTabKeyValue(1);
		}
		else if (hasCPMedia) {
			display(navCPMediaId);
			setActiveTabKeyValue(2);
		}
		else if (hasReplacements) {
			display(navReplacementsId);
			setActiveTabKeyValue(3);
		}
		setFirstRender(false);
		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, [
		hasCPDefinitionSpecificationOptionValues,
		hasCPMedia,
		hasDescription,
		hasReplacements,
		navCPMediaId,
		navDescriptionId,
		navReplacementsId,
		navSpecificationsId,
		namespace,
	]);

	return (
		<>
			<ClayTabs className="nav-left" modern>
				{hasDescription ? (
					<ClayTabs.Item
						active={activeTabKeyValue === 0}
						innerProps={{
							'aria-controls': 'tabpanel-1',
						}}
						onClick={() => {
							display(navDescriptionId);
							setActiveTabKeyValue(0);
						}}
					>
						{Liferay.Language.get('full-description')}
					</ClayTabs.Item>
				) : (
					<></>
				)}

				{hasCPDefinitionSpecificationOptionValues ? (
					<ClayTabs.Item
						active={activeTabKeyValue === 1}
						innerProps={{
							'aria-controls': 'tabpanel-2',
						}}
						onClick={() => {
							display(navSpecificationsId);
							setActiveTabKeyValue(1);
						}}
					>
						{Liferay.Language.get('specifications')}
					</ClayTabs.Item>
				) : (
					<></>
				)}

				{hasCPMedia ? (
					<ClayTabs.Item
						active={activeTabKeyValue === 2}
						innerProps={{
							'aria-controls': 'tabpanel-3',
						}}
						onClick={() => {
							display(navCPMediaId);
							setActiveTabKeyValue(2);
						}}
					>
						{Liferay.Language.get('attachments')}
					</ClayTabs.Item>
				) : (
					<></>
				)}

				{hasReplacements ? (
					<ClayTabs.Item
						active={activeTabKeyValue === 3}
						innerProps={{
							'aria-controls': 'tabpanel-4',
						}}
						onClick={() => {
							display(navReplacementsId);
							setActiveTabKeyValue(3);
						}}
					>
						{Liferay.Language.get('replacements')}
					</ClayTabs.Item>
				) : (
					<></>
				)}
			</ClayTabs>
		</>
	);
}
