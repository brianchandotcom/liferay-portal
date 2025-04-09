/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayLayout from '@clayui/layout';
import {ClayVerticalNav} from '@clayui/nav';
import React, {ReactElement, useState} from 'react';

interface Props {
	mainContentMap: Map<string, ReactElement>;
}

interface VerticalNavItem {
	active: boolean;
	label: string;
	onClick: () => void;
}

/**
 * Container component for the main content of the Categorization section's
 * individual entity-related pages with a navigation sidebar, such as creating
 * or editing an entity.
 *
 * @param mainContentMap Map of <key: string, value: ReactElement> pairs where
 * the keys are the language key for a given view and the values are the
 * corresponding React component. Maps retain insertion order - the vertical
 * nav bar's items will be in the order that entries were inserted into the map.
 *
 * @constructor
 */
const CategorizationContentContainer = ({mainContentMap}: Props) => {
	const [activeVerticalNavKey, setActiveVerticalNavKey] = useState(
		mainContentMap.keys().next().value
	);

	const verticalNavItems: VerticalNavItem[] = [];

	mainContentMap.forEach((value: ReactElement, key: string) => {
		verticalNavItems.push({
			active: activeVerticalNavKey === key,
			label: Liferay.Language.get(key),
			onClick: () => {
				setActiveVerticalNavKey(key);
			},
		});
	});

	return (
		<>
			<ClayLayout.ContainerFluid
				className="cms-parent-container m-0"
				size={false}
			>
				<ClayLayout.Row className="cms-container-child">
					<ClayLayout.Col
						className="categorization-vertical-nav p-0"
						md={3}
						sm={12}
					>
						<div className="p-4">
							<ClayVerticalNav items={verticalNavItems} />
						</div>
					</ClayLayout.Col>

					<ClayLayout.Col md={9} sm={12}>
						{mainContentMap.has(activeVerticalNavKey) &&
							mainContentMap.get(activeVerticalNavKey)}
					</ClayLayout.Col>
				</ClayLayout.Row>
			</ClayLayout.ContainerFluid>
		</>
	);
};

export default CategorizationContentContainer;
