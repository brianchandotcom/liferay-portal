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
				<button className="btn btn-primary">
					{label}

					<ClayIcon className="ml-2" symbol="caret-bottom" />
				</button>
			}
			{...props}
		>
			<ClayDropDown.ItemList>
				{items.map(({icon, label, onClick}) => (
					<ClayDropDown.Item
						className="common-drop-down-item font-weight-semi-bold px-3 rounded-xs text-neutral-8"
						key={label}
						onClick={onClick}
					>
						<div className="d-flex">
							{icon && <div className="mr-1">{icon}</div>}

							{label}
						</div>
					</ClayDropDown.Item>
				))}
			</ClayDropDown.ItemList>
		</ClayDropDown>
	);
};

export default ButtonDropDown;
