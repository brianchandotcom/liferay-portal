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

import {ClayButtonWithIcon} from '@clayui/button';
import classNames from 'classnames';
import React from 'react';

import {switchSidebarPanel} from '../../app/actions/index';
import {useDispatch, useSelector} from '../../app/contexts/StoreContext';

export default function SidebarPanelHeader({
	children,
	iconLeft = null,
	iconRight = null,
}) {
	const dispatch = useDispatch();

	const sidebarPanelId = useSelector((state) => state.sidebar?.panelId);

	return (
		<header
			className={classNames(
				'align-items-center d-flex justify-content-between my-3 pl-3 pr-2 page-editor__sidebar__panel-header'
			)}
		>
			{iconLeft}

			<h1 className="flex-grow-1 mb-0 mr-1 text-3">{children}</h1>

			{iconRight}

			<ClayButtonWithIcon
				displayType="unstyled"
				onClick={() => {
					dispatch(switchSidebarPanel({sidebarOpen: false}));

					document
						.querySelector(`[data-panel-id="${sidebarPanelId}"]`)
						?.focus();
				}}
				small
				symbol="times"
				title={Liferay.Language.get('close')}
			/>
		</header>
	);
}
