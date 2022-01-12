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

import ClayButton from '@clayui/button';
import ClayIcon from '@clayui/icon';
import ClayLink from '@clayui/link';
import classNames from 'classnames';
import React, {useContext} from 'react';

import FeatureFlagContext from './FeatureFlagContext';

const LinkOrButton = ({
	children,
	className,
	disabled,
	href,
	symbol,
	...otherProps
}) => {
	const {showDesignImprovements} = useContext(FeatureFlagContext);
	const responsive = symbol && children;
	const Wrapper = href && !disabled ? ClayLink : ClayButton;

	return (
		<>
			<Wrapper
				block={otherProps.button?.block}
				className={classNames(className, {
					'btn-content-spaced': !symbol,
					'd-md-none': showDesignImprovements && responsive,
				})}
				href={href}
				{...otherProps}
			>
				{symbol ? <ClayIcon symbol={symbol} /> : children}
			</Wrapper>

			{showDesignImprovements && responsive && (
				<Wrapper
					block={otherProps.button?.block}
					className={classNames(className, 'btn-content-spaced d-md-flex d-none')}
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
