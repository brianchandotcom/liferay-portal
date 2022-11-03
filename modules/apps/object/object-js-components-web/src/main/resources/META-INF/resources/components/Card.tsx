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

import ClayIcon from '@clayui/icon';
import classNames from 'classnames';
import React from 'react';

import './Card.scss';

interface CardProps extends React.HTMLAttributes<HTMLDivElement> {
	disabled?: boolean;
	title: string;
	tooltip?: ITooltip | null;
	viewMode?: 'inline' | 'no-children' | 'no-margin' | 'no-padding';
}

interface ITooltip {
	content: string;
	symbol: string;
}

export function Card({
	children,
	className,
	disabled,
	title,
	tooltip,
	viewMode,
	...otherProps
}: CardProps) {
	const inline = viewMode === 'inline';
	const noChildren = viewMode === 'no-children';
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
						<div className="lfr-objects__card-header">
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
