/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import ClayIcon from '@clayui/icon';
import ClayLink from '@clayui/link';
import classNames from 'classnames';
import React, {useContext} from 'react';

import FeatureFlagContext from './FeatureFlagContext';

const LinkOrButton = ({
	ariaLabel,
	children,
	className,
	disabled,
	href,
	symbol,
	title,
	wide,
	...otherProps
}) => {
	const {showDesignImprovements} = useContext(FeatureFlagContext);
	const responsive = symbol && children;
	const Wrapper = href && !disabled ? ClayLink : ClayButton;

	return (
		<>
			<Wrapper
				aria-label={symbol && ariaLabel}
				block={otherProps.button?.block}
				className={classNames(className, {
					'd-md-none': showDesignImprovements && responsive,
					'nav-btn-monospaced': showDesignImprovements && responsive,
					'pl-4 pr-4': wide && !symbol,
				})}
				disabled={disabled}
				href={href}
				{...otherProps}
				title={symbol && title}
			>
				{symbol ? <ClayIcon symbol={symbol} /> : children}
			</Wrapper>

			{showDesignImprovements && responsive && (
				<Wrapper
					block={otherProps.button?.block}
					className={classNames(className, 'd-md-flex d-none', {
						'pl-4 pr-4': wide,
					})}
					disabled={disabled}
					href={href}
					{...otherProps}
				>
					{children}
				</Wrapper>
			)}
		</>
	);
};
export default LinkOrButton;
