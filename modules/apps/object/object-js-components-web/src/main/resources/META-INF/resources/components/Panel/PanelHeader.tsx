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

import {TYPES, usePanelContext} from './objectPanelContext';

import './Panel.scss';

interface IPanelHeaderProps extends React.HTMLAttributes<HTMLElement> {
	contentLeft?: React.ReactNode;
	contentRight?: React.ReactNode;
	disabled?: boolean;
	title: string;
	type: string;
}

export function PanelHeader({
	contentLeft,
	contentRight,
	disabled = false,
	title,
	type,
}: IPanelHeaderProps) {
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

				{type === 'categorization' && (
					<ClayTooltipProvider>
						<span
							className="ml-2"
							title={Liferay.Language.get(
								'visibility-and-permissions-can-affect-how-the-categorization-block-will-be-displayed'
							)}
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
}
