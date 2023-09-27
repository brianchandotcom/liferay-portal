/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import classNames from 'classnames';
import React, {ReactNode} from 'react';

import {PanelContextProvider} from './objectPanelContext';

import './Panel.scss';

interface PanelProps extends React.HTMLAttributes<HTMLElement> {
	children: ReactNode;
}

export function Panel({children, className, ...otherProps}: PanelProps) {
	return (
		<PanelContextProvider>
			<div
				{...otherProps}
				className={classNames(className, 'object-admin-panel')}
			>
				{children}
			</div>
		</PanelContextProvider>
	);
}
