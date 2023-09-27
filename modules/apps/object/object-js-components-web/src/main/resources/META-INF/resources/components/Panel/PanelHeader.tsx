/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
						aria-label={Liferay.Language.get('collapse')}
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
