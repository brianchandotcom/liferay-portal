/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
	showCloseButton = true,
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

			<h2 className="flex-grow-1 mb-0 mr-1 text-3">{children}</h2>

			{iconRight}

			{showCloseButton && (
				<ClayButtonWithIcon
					aria-label={Liferay.Language.get('close')}
					displayType="unstyled"
					onClick={() => {
						dispatch(switchSidebarPanel({sidebarOpen: false}));

						document
							.querySelector(
								`[data-panel-id="${sidebarPanelId}"]`
							)
							?.focus();
					}}
					size="sm"
					symbol="times"
					title={Liferay.Language.get('close')}
				/>
			)}
		</header>
	);
}
