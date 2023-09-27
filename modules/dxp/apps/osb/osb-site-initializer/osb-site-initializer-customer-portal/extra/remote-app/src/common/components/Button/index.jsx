/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Button as ClayButton} from '@clayui/core';
import ClayIcon from '@clayui/icon';
import classNames from 'classnames';
import {forwardRef} from 'react';

const ButtonBase = (
	{
		appendIcon,
		appendIconClassName,
		children,
		isImagePrependIcon,
		isLoading,
		prependIcon,
		prependIconClassName,
		...props
	},
	ref
) => {
	return (
		<ClayButton
			aria-label={
				typeof props.children === 'string' ? props.children : ''
			}
			ref={ref}
			{...props}
		>
			{prependIcon && (
				<span
					className={classNames(
						'inline-item inline-item-before',
						prependIconClassName
					)}
				>
					{isImagePrependIcon ? (
						<img className="mr-2" src={prependIcon} width="16" />
					) : (
						<ClayIcon symbol={prependIcon} />
					)}
				</span>
			)}

			{children}

			{appendIcon && (
				<span
					className={classNames(
						'inline-item inline-item-after',
						appendIconClassName
					)}
				>
					<ClayIcon
						aria-label={`Icon ${appendIcon}}`}
						symbol={appendIcon}
					/>
				</span>
			)}

			{isLoading && (
				<span className="cp-spinner ml-2 spinner-border spinner-border-sm"></span>
			)}
		</ClayButton>
	);
};
const Button = forwardRef(ButtonBase);
export default Button;
