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
import ClayIcon from '@clayui/icon';
import {ClayTooltipProvider} from '@clayui/tooltip';
import classNames from 'classnames';
import React from 'react';

import {BoxType} from '../Layout/types';
import PanelContextProvider, {
	TYPES,
	usePanelContext,
} from './objectPanelContext';

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
};

interface IPanelHeaderProps extends React.HTMLAttributes<HTMLElement> {
	contentLeft?: React.ReactNode;
	contentRight?: React.ReactNode;
	disabled?: boolean;
	title: string;
	type: BoxType;
}

const PanelHeader: React.FC<IPanelHeaderProps> = ({
	contentLeft,
	contentRight,
	disabled = false,
	title,
	type,
}) => {
	const [{expanded}, dispatch] = usePanelContext();

	return (
		<div
			className={classNames('object-admin-panel__header', {
				'object-admin-panel__header--expanded':
					expanded && type === 'regular',
			})}
		>
			<div
				className={classNames(
					'object-admin-panel__header__content-left',
					{
						'object-admin-panel__header__content-left--disabled': disabled,
					}
				)}
			>
				{type === 'regular' && (
					<ClayButtonWithIcon displayType="unstyled" symbol="drag" />
				)}

				<h3
					className={classNames('object-admin-panel__title', {
						'ml-3': type !== 'regular',
					})}
				>
					{title}
				</h3>

				{(type === 'categorization' || type === 'comments') && (
					<ClayTooltipProvider>
						<span
							className="ml-2"
							title={
								type === 'categorization'
									? Liferay.Language.get(
											'visibility-and-permissions-can-affect-how-the-categorization-block-will-be-displayed'
									  )
									: Liferay.Language.get(
											'visibility-and-permissions-can-affect-how-the-comments-block-is-displayed'
									  )
							}
						>
							<ClayIcon
								className="object-admin-panel__tooltip-icon"
								symbol="info-panel-open"
							/>
						</span>
					</ClayTooltipProvider>
				)}

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

				{type === 'regular' && (
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
				)}
			</div>
		</div>
	);
};

interface IPanelSimpleBodyProps extends React.HTMLAttributes<HTMLElement> {
	contentRight?: React.ReactNode;
	title: string;
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
