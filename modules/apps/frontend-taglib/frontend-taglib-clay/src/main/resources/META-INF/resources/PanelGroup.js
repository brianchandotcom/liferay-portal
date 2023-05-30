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

const PanelGroupItem = ({children}) => {
	const ref = useRef();

	useEffect(() => {
		ref.current.appendChild(children);
	}, [children]);

	return <div ref={ref}></div>;
};

export default function PanelGroup({
	additionalProps: _additionalProps,
	children,
	componentId: _componentId,
	cssClass,
	fluid,
	fluidFirst,
	fluidLast,
	flush,
	locale: _locale,
	portletId: _portletId,
	portletNamespace: _portletNamespace,
	small,
	...otherProps
}) {
	return (
		<ClayPanel.Group
			className={cssClass}
			fluid={fluid}
			fluidFirst={fluidFirst}
			fluidLast={fluidLast}
			flush={flush}
			small={small}
			{...otherProps}
		>
			{Array.from(children).map((item, i) => (
				<PanelGroupItem key={i}>{item}</PanelGroupItem>
			))}
		</ClayPanel.Group>
	);
}
