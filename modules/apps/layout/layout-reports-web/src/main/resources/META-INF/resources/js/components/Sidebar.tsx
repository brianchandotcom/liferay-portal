/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ClayButtonWithIcon} from '@clayui/button';
import React, {MouseEventHandler} from 'react';

interface Props {
	children: React.ReactNode;
}

export function SidebarBody({children}: Props) {
	return <div className="sidebar-body">{children}</div>;
}

interface HeaderProps {
	onBackButtonClick?: MouseEventHandler;
	title: string;
}

export function SidebarHeader({onBackButtonClick, title}: HeaderProps) {
	return (
		<div className="align-items-center c-gap-2 d-flex sidebar-header">
			{onBackButtonClick ? (
				<ClayButtonWithIcon
					aria-label={Liferay.Language.get('go-back')}
					className="component-action text-secondary"
					displayType="unstyled"
					monospaced
					onClick={onBackButtonClick}
					symbol="angle-left"
					title={Liferay.Language.get('go-back')}
				/>
			) : null}

			<span className="font-weight-bold">{title}</span>

			<ClayButtonWithIcon
				aria-label={title}
				className="component-action ml-auto sidenav-close text-secondary"
				displayType="unstyled"
				monospaced
				symbol="times"
				title={Liferay.Language.get('close')}
			/>
		</div>
	);
}
