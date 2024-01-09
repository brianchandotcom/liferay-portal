/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayTabs from '@clayui/tabs';
import React, {useEffect, useRef, useState} from 'react';

const Panel = ({children: tabPanel}) => {
	const ref = useRef();

	useEffect(() => {
		const fragment = document.createDocumentFragment();

		while (tabPanel.firstChild) {
			fragment.appendChild(tabPanel.firstChild);
		}

		ref.current.appendChild(fragment);
	}, [tabPanel]);

	return <div ref={ref}></div>;
};

export default function Tabs({
	activation,
	additionalProps: _additionalProps,
	children: panelsContent,
	componentId: _componentId,
	cssClass,
	displayType,
	fade,
	justified,
	locale: _locale,
	portletId: _portletId,
	portletNamespace: _portletNamespace,
	tabsItems,
	...otherProps
}) {
	const activeIndex = tabsItems.findIndex((item) => item.active);

	const [active, setActive] = useState(activeIndex === -1 ? 0 : activeIndex);

	return (
		<>
			<ClayTabs
				activation={activation}
				active={active}
				className={cssClass}
				displayType={displayType}
				fade={fade}
				justified={justified}
				onActiveChange={setActive}
				{...otherProps}
			>
				<ClayTabs.List>
					{tabsItems.map(({disabled, href, label}, i) => (
						<ClayTabs.Item
							active={i === active}
							disabled={disabled}
							href={href}
							key={i}
						>
							{label}
						</ClayTabs.Item>
					))}
				</ClayTabs.List>

				<ClayTabs.Panels>
					{Array.from(panelsContent).map((panelContent, i) => (
						<ClayTabs.TabPanel key={i}>
							<Panel>{panelContent}</Panel>
						</ClayTabs.TabPanel>
					))}
				</ClayTabs.Panels>
			</ClayTabs>
		</>
	);
}
