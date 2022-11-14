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
import ClayDropDown from '@clayui/drop-down';
import ClayIcon from '@clayui/icon';

interface DropdownOption {
	icon: string;
	key: string;
	label: string;
}

interface Props {
	onClick?: () => void;
	options: DropdownOption[];
}

const DropDown = ({onClick, options}: Props) => (
	<ClayDropDown
		trigger={
			<ClayButton displayType="unstyled">
				<ClayIcon symbol="ellipsis-v"></ClayIcon>
			</ClayButton>
		}
	>
		<ClayDropDown.ItemList>
			<ClayDropDown.Group>
				{options.map((item, index) => (
					<ClayDropDown.Item key={index} onClick={onClick}>
						<ClayIcon symbol={item.icon}></ClayIcon>

						{item.label}
					</ClayDropDown.Item>
				))}
			</ClayDropDown.Group>
		</ClayDropDown.ItemList>
	</ClayDropDown>
);

export default DropDown;
