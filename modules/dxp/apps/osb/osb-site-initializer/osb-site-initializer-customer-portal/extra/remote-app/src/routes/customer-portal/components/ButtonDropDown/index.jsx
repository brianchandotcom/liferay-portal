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
import ClayDropDown, {Align} from '@clayui/drop-down';
import ClayIcon from '@clayui/icon';

const ButtonDropDown = ({
	label,
	align = Align.BottomRight,
	active,
	setActive,
	items,
	...props
}) => {
	return (
		<ClayDropDown
			active={active}
			alignmentPosition={align}
			onActiveChange={setActive}
			trigger={
				<ClayButton className="btn btn-primary px-3 py-2">
					{label}

					<ClayIcon className="ml-2" symbol="caret-bottom" />
				</ClayButton>
			}
			{...props}
		>
			<ClayDropDown.ItemList>
				{items?.map(({icon, label, onClick}) => (
					<ClayDropDown.Item
						className="cp-activation-keys-drop-down-item font-weight-semi-bold px-3 rounded-xs text-neutral-8"
						key={label}
						onClick={onClick}
					>
						{icon}

						{label}
					</ClayDropDown.Item>
				))}
			</ClayDropDown.ItemList>
		</ClayDropDown>
	);
};

export default ButtonDropDown;
