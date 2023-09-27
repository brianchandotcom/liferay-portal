/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayTabs from '@clayui/tabs';
import React, {useEffect, useRef} from 'react';

const Panel = ({children}) => {
	const ref = useRef();

	useEffect(() => {
		ref.current.appendChild(children);
	}, [children]);

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
	return (
		<>
			<ClayTabs
				activation={activation}
				className={cssClass}
				displayType={displayType}
				fade={fade}
				justified={justified}
				{...otherProps}
			>
				<ClayTabs.List>
					{tabsItems.map(({active, disabled, href, label}, i) => (
						<ClayTabs.Item
							active={active}
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
