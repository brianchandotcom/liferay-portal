/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import ClayLink from '@clayui/link';
import ClayNavigationBar from '@clayui/navigation-bar';
import React from 'react';

export default function NavigationBar({
	activeItemAriaCurrent,
	cssClass,
	inverted,
	navigationItems,
}) {
	return (
		<ClayNavigationBar
			aria-current={activeItemAriaCurrent}
			className={cssClass}
			fluidSize={Liferay?.FeatureFlags?.['LPS-184404'] ? false : 'xl'}
			inverted={inverted}
			triggerLabel={navigationItems.find(({active}) => active)?.label}
		>
			{navigationItems.map(({active, href, label}, index) => {
				return (
					<ClayNavigationBar.Item
						active={active}
						data-nav-item-index={index}
						key={label}
					>
						{href ? (
							<ClayLink href={href}>{label}</ClayLink>
						) : (
							<ClayButton>{label}</ClayButton>
						)}
					</ClayNavigationBar.Item>
				);
			})}
		</ClayNavigationBar>
	);
}
