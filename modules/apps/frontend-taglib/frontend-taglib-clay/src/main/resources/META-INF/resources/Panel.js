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
	displayTitle,
	displayType,
	expanded,
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
			displayTitle={displayTitle}
			displayType={displayType}
			expanded={expanded}
			showCollapseIcon={showCollapseIcon}
			{...otherProps}
		>
			{Array.from(children).map((item, i) => (
				<PanelItem key={i}>{item}</PanelItem>
			))}
		</ClayPanel>
	);
}
