/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import ClayIcon from '@clayui/icon';
import ClayLink from '@clayui/link';
import classNames from 'classnames';
import React from 'react';

const LinkOrButton = React.forwardRef(
	(
		{
			ariaLabel,
			children,
			className,
			disabled,
			href,
			symbol,
			title,
			wide,
			wideViewportTitleVisible = true,
			...otherProps
		},
		ref
	) => {
		const responsive = Boolean(symbol && children);

		const Wrapper = href && !disabled ? ClayLink : ClayButton;

		return (
			<div ref={ref}>
				<Wrapper
					aria-label={symbol && ariaLabel}
					block={otherProps.button?.block}
					className={classNames(className, {
						'd-md-none': responsive,
						'nav-btn-monospaced': responsive,
						'pl-4 pr-4': wide && !symbol,
					})}
					disabled={disabled}
					href={href}
					{...otherProps}
					title={symbol && title}
				>
					{symbol ? <ClayIcon symbol={symbol} /> : children}
				</Wrapper>

				{responsive && (
					<Wrapper
						block={otherProps.button?.block}
						className={classNames(className, 'd-md-flex d-none', {
							'pl-4 pr-4': wide,
						})}
						disabled={disabled}
						href={href}
						{...otherProps}
						title={wideViewportTitleVisible && title}
					>
						{children}
					</Wrapper>
				)}
			</div>
		);
	}
);

export default LinkOrButton;
