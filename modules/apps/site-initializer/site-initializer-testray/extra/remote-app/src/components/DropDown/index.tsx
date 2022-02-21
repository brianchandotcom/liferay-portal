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

import ClayDropDown, {Align} from '@clayui/drop-down';
import ClayIcon from '@clayui/icon';
import {ReactElement, useState} from 'react';
import {Link} from 'react-router-dom';

import {Dropdown} from '../../context/HeaderContext';

type DropDownProps = {
	items: Dropdown;
	position?: any;
	trigger: ReactElement;
};

const DropDown: React.FC<DropDownProps> = ({
	items,
	position = Align.BottomCenter,
	trigger,
}) => {
	const [active, setActive] = useState(false);

	return (
		<ClayDropDown
			active={active}
			alignmentPosition={position}
			onActiveChange={setActive}
			trigger={trigger}
		>
			<ClayDropDown.ItemList>
				{items.map((section, index) => (
					<div key={index}>
						<ClayDropDown.Group>
							{section.title && (
								<ClayDropDown.Caption>
									{section.title}
								</ClayDropDown.Caption>
							)}

							{section.items.map((item, itemIndex) => (
								<Link key={itemIndex} to={`${item.path}`}>
									<ClayDropDown.Item>
										<div className="align-items-center d-flex testray-sidebar-item text-dark">
											{item.icon && (
												<ClayIcon
													fontSize={16}
													symbol={item.icon}
												/>
											)}

											<span className="ml-1 testray-sidebar-text">
												{item.label}
											</span>
										</div>
									</ClayDropDown.Item>

									{item.divider && <ClayDropDown.Divider />}
								</Link>
							))}
						</ClayDropDown.Group>

						{items.length - 1 !== index && <ClayDropDown.Divider />}
					</div>
				))}
			</ClayDropDown.ItemList>
		</ClayDropDown>
	);
};

export default DropDown;
