/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import {DisplayType} from '@clayui/button/lib/Button';
import ClayIcon from '@clayui/icon';
import ClayManagementToolbar from '@clayui/management-toolbar';
import {Fragment, ReactNode} from 'react';

type ButtonProps = {
	children: ReactNode;
	displayType?: DisplayType;
	symbol?: string;
	toolbar?: boolean;
} & React.HTMLAttributes<HTMLButtonElement>;

const Button: React.FC<ButtonProps> = ({
	children,
	displayType,
	symbol,
	toolbar,
	...otherProps
}) => {
	const Wrapper = toolbar ? ClayManagementToolbar.Item : Fragment;

	return (
		<Wrapper>
			<ClayButton displayType={displayType} {...otherProps}>
				{symbol && <ClayIcon className="mr-2" symbol={symbol} />}

				{children}
			</ClayButton>
		</Wrapper>
	);
};

export default Button;
