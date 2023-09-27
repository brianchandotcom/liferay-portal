/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayPanel from '@clayui/panel';
import React, {useEffect, useRef} from 'react';

const PanelItem = ({children}) => {
	const ref = useRef();

	useEffect(() => {
		ref.current.appendChild(children);
	}, [children]);

	return <div ref={ref}></div>;
};

export default function Panel({
	additionalProps: _additionalProps,
	children,
	collapsable,
	collapseClassNames,
	componentId: _componentId,
	cssClass,
	defaultExpanded,
	displayTitle,
	displayType,
	locale: _locale,
	portletId: _portletId,
	portletNamespace: _portletNamespace,
	showCollapseIcon,
	...otherProps
}) {
	return (
		<ClayPanel
			className={cssClass}
			collapsable={collapsable}
			collapseClassNames={collapseClassNames}
			defaultExpanded={defaultExpanded}
			displayTitle={displayTitle}
			displayType={displayType}
			showCollapseIcon={showCollapseIcon}
			{...otherProps}
		>
			{Array.from(children).map((item, i) => (
				<PanelItem key={i}>{item}</PanelItem>
			))}
		</ClayPanel>
	);
}
