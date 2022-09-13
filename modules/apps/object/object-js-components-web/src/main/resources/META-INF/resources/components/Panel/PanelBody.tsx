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

import {usePanelContext} from './objectPanelContext';

import './Panel.scss';

interface IPanelSimpleBodyProps extends React.HTMLAttributes<HTMLElement> {
	contentRight?: React.ReactNode;
	title: string;
}

export function PanelBody({
	children,
	className,
}: React.HTMLAttributes<HTMLElement>) {
	const [{expanded}] = usePanelContext();

	return (
		<>
			{expanded && (
				<div
					className={classNames(
						className,
						'object-admin-panel__body'
					)}
				>
					{children}
				</div>
			)}
		</>
	);
}

export function PanelSimpleBody({
	children,
	contentRight,
	title,
}: IPanelSimpleBodyProps) {
	return (
		<div className="object-admin-panel__simple-body">
			<div className="object-admin-panel__simple-body__content-left">
				<ClayButtonWithIcon displayType="unstyled" symbol="drag" />

				<div>
					<h5 className="object-admin-panel__title">{title}</h5>

					<div>{children}</div>
				</div>
			</div>

			<div className="object-admin-panel__simple-body__content-right">
				{contentRight}
			</div>
		</div>
	);
}
