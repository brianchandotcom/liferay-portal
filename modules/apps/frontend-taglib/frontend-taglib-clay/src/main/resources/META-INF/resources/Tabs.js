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
