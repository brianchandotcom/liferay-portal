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

import React, {useEffect, useRef} from 'react';

import Sidebar from './Sidebar';

const SidebarPanelMetricsView = ({html}) => {
	const elementRef = useRef();

	useEffect(() => {
		const fragment = document.createRange().createContextualFragment(html);

		elementRef.current.innerHTML = '';

		elementRef.current.appendChild(fragment);
	}, [html]);

	return (
		<>
			<Sidebar.Header
				title={Liferay.Language.get('content-performance')}
			/>

			<Sidebar.Body className="c-p-0">
				<div ref={elementRef}></div>
			</Sidebar.Body>
		</>
	);
};

export default SidebarPanelMetricsView;
