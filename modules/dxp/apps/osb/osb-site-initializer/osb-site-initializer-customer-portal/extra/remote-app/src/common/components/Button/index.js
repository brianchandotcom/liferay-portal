/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
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
		prependIcon,
		...props
	},
	ref
) => {
	return (
		<ClayButton ref={ref} {...props}>
			{prependIcon && (
				<span className="inline-item inline-item-before">
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
					<ClayIcon symbol={appendIcon} />
				</span>
			)}
		</ClayButton>
	);
};
const Button = forwardRef(ButtonBase);
export default Button;
