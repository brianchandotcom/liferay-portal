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

import React from 'react';
import {BoxType} from '../Layout/types';
import './Panel.scss';
declare const Panel: React.FC<React.HTMLAttributes<HTMLElement>> & {
	Body: React.FC<IPanelBodyProps>;
	Header: React.FC<IPanelHeaderProps>;
	SimpleBody: React.FC<IPanelSimpleBodyProps>;
};
interface IPanelBodyProps extends React.HTMLAttributes<HTMLElement> {}
interface IPanelHeaderProps extends React.HTMLAttributes<HTMLElement> {
	contentLeft?: React.ReactNode;
	contentRight?: React.ReactNode;
	disabled?: boolean;
	title: string;
	type: BoxType;
}
interface IPanelSimpleBodyProps extends React.HTMLAttributes<HTMLElement> {
	contentRight?: React.ReactNode;
	title: string;
}
export default Panel;
