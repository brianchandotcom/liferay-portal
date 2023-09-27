/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

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
