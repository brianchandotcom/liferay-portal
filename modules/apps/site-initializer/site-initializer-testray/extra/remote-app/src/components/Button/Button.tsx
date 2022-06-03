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
import {DisplayType} from '@clayui/button/lib/Button';
import ClayIcon from '@clayui/icon';
import {ReactNode} from 'react';

type ButtonProps = {
	children: ReactNode;
	displayType?: DisplayType;
	symbol?: string;
} & React.HTMLAttributes<HTMLButtonElement>;

const Button: React.FC<ButtonProps> = ({
	children,
	displayType,
	symbol,
	...props
}) => {
	return (
		<ClayButton displayType={displayType} {...props}>
			{symbol && <ClayIcon className="mr-2" symbol={symbol} />}

			{children}
		</ClayButton>
	);
};

export default Button;
