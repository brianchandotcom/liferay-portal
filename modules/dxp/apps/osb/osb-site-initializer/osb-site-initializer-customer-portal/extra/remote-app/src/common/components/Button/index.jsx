/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Button as ClayButton} from '@clayui/core';
import ClayIcon from '@clayui/icon';
import classNames from 'classnames';
import {forwardRef, useMemo} from 'react';
import {navigationIcons} from '~/routes/customer-portal/containers/SideMenu/utils/navigationIcons';

const ButtonBase = (
	{
		appendIcon,
		appendIconClassName,
		children,
		iconKey,
		isImagePrependIcon,
		isLoading,
		prependIcon,
		prependIconClassName,
		...props
	},
	ref
) => {
	const Icon = useMemo(() => {
		try {
			if (iconKey) {
				const [activeIcon] = navigationIcons[iconKey];

				return activeIcon;
			}
		} catch (error) {
			console.error('Error:', error);
		}
	}, [iconKey]);

	return (
		<ClayButton
			aria-label={
				typeof props.children === 'string' ? props.children : ''
			}
			ref={ref}
			{...props}
		>
			{iconKey && <Icon className="mr-2" />}

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
