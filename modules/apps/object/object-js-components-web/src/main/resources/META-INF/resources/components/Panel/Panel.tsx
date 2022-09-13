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
