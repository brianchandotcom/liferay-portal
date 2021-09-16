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
import React, {useContext} from 'react';

import PanelContextProvider, {PanelContext, TYPES} from './context';

import './Panel.scss';

const Panel: React.FC<React.HTMLAttributes<HTMLElement>> & {
	Body: React.FC<IPanelBodyProps>;
	Header: React.FC<IPanelHeaderProps>;
	SimpleBody: React.FC<IPanelSimpleBodyProps>;
} = ({children, className, ...otherProps}) => {
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
};

interface IPanelBodyProps extends React.HTMLAttributes<HTMLElement> {}

const PanelBody: React.FC<IPanelBodyProps> = ({children, className}) => {
	const [{expanded}] = useContext(PanelContext);

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
};

interface IPanelHeaderProps extends React.HTMLAttributes<HTMLElement> {
	title: string;
	contentLeft?: React.ReactNode;
	contentRight?: React.ReactNode;
}

const PanelHeader: React.FC<IPanelHeaderProps> = ({
	contentLeft,
	contentRight,
	title,
}) => {
	const [{expanded}, dispatch] = useContext(PanelContext);

	return (
		<div
			className={classNames('object-admin-panel__header', {
				'object-admin-panel__header--expanded': expanded,
			})}
		>
			<div className="object-admin-panel__header__content-left">
				<ClayButtonWithIcon displayType="unstyled" symbol="drag" />

				<h3 className="object-admin-panel__title">{title}</h3>

				{contentLeft && (
					<span className="align-items-center d-flex ml-2">
						{contentLeft}
					</span>
				)}
			</div>
			<div className="object-admin-panel__header__content-right">
				{contentRight && (
					<span className="align-items-center d-flex ml-2">
						{contentRight}
					</span>
				)}

				<ClayButtonWithIcon
					displayType="unstyled"
					onClick={() =>
						dispatch({
							payload: {expanded: !expanded},
							type: TYPES.CHANGE_PANEL_EXPANDED,
						})
					}
					symbol={expanded ? 'angle-down' : 'angle-right'}
				/>
			</div>
		</div>
	);
};

interface IPanelSimpleBodyProps extends React.HTMLAttributes<HTMLElement> {
	title: string;
	contentRight?: React.ReactNode;
}

const PanelSimpleBody: React.FC<IPanelSimpleBodyProps> = ({
	children,
	contentRight,
	title,
}) => {
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
};

Panel.Body = PanelBody;
Panel.Header = PanelHeader;
Panel.SimpleBody = PanelSimpleBody;

export default Panel;
