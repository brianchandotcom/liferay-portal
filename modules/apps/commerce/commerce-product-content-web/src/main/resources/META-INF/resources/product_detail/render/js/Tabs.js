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

import ClayTabs from '@clayui/tabs';
import React, {useEffect, useState} from 'react';

let tabs = [];

function display(tabId) {
	for (let i = 0; i < tabs.length; i++) {
		const currentTabId = tabs[i].id;

		if (tabId === currentTabId) {
			document.getElementById(tabId).classList.add('active', 'show');
		}
		else {
			document
				.getElementById(currentTabId)
				.classList.remove('active', 'show');
		}
	}
}

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
				{hasDescription && (
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
				)}

				{hasCPDefinitionSpecificationOptionValues && (
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
				)}

				{hasCPMedia && (
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
				)}

				{hasReplacements && (
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
				)}
			</ClayTabs>
		</>
	);
}
