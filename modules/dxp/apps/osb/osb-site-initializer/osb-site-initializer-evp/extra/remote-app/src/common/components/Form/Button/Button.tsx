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
