/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayIcon from '@clayui/icon';
import classNames from 'classnames';
import React from 'react';

import './Card.scss';

interface CardProps extends React.HTMLAttributes<HTMLDivElement> {
	customHeader?: JSX.Element;
	disabled?: boolean;
	title?: string;
	tooltip?: ITooltip | null;
	viewMode?:
		| 'inline'
		| 'no-children'
		| 'no-header-border'
		| 'no-margin'
		| 'no-padding';
}

interface ITooltip {
	content: string;
	symbol: string;
}
export function Card({
	children,
	className,
	customHeader,
	disabled,
	title,
	tooltip,
	viewMode,
	...otherProps
}: CardProps) {
	const inline = viewMode === 'inline';
	const noChildren = viewMode === 'no-children';
	const noHeaderBorder = viewMode === 'no-header-border';
	const noMargin = viewMode === 'no-margin';
	const noPadding = viewMode === 'no-padding';

	return (
		<div
			{...otherProps}
			className={classNames(className, 'lfr-objects__card', {
				'lfr-objects__card--inline': inline,
				'lfr-objects__card--no-margin': noChildren || noMargin,
			})}
		>
			{noChildren ? (
				<div className="lfr-objects__card-title--with-padding">
					{title}
				</div>
			) : (
				<>
					{inline ? (
						title
					) : (
						<div
							className={classNames('lfr-objects__card-header', {
								'lfr-objects__card-header--no-border': noHeaderBorder,
							})}
						>
							{customHeader ? (
								customHeader
							) : (
								<>
									<h3
										className={classNames(
											'lfr-objects__card-title',
											{
												'lfr-objects__card-title--disabled': disabled,
											}
										)}
									>
										{title}
									</h3>

									{tooltip && (
										<span
											className="ml-2"
											data-tooltip-align="top"
											title={tooltip.content}
										>
											<ClayIcon
												className="lfr-objects__card-header-tooltip-icon"
												symbol={tooltip.symbol}
											/>
										</span>
									)}
								</>
							)}
						</div>
					)}
					<div
						className={classNames('lfr-objects__card-body', {
							'lfr-objects__card-body--inline': inline,
							'lfr-objects__card-body--no-padding': noPadding,
						})}
					>
						{children}
					</div>
				</>
			)}
		</div>
	);
}
