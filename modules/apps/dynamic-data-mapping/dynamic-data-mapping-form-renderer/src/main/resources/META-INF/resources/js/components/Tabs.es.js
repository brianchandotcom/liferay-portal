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
import React from 'react';

import {EVENT_TYPES, usePage} from '../hooks/usePage.es';

export const Tabs = ({activePage, pages}) => {
	const {dispatch} = usePage();

	return (
		<nav className="component-tbar ddm-form-tabs mb-3 tbar">
			<div className="container-fluid pr-0">
				<ClayTabs>
					{pages.map((page, index) => (
						<ClayTabs.Item
							active={index === activePage}
							disabled={!page.enabled}
							key={index}
							onClick={() =>
								dispatch({
									payload: {pageIndex: index},
									type: EVENT_TYPES.PAGINATION,
								})
							}
						>
							<span className="navbar-text-truncate">
								{page.title}
							</span>
						</ClayTabs.Item>
					))}
				</ClayTabs>
			</div>
		</nav>
	);
};
