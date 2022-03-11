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
import React, {ReactElement, useState} from 'react';
import {useNavigate} from 'react-router-dom';

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
	const navigate = useNavigate();
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
						<ClayDropDown.Group header={section.title}>
							{section.items.map(
								(
									{divider, icon, label, onClick, path},
									itemIndex
								) => (
									<React.Fragment key={itemIndex}>
										<ClayDropDown.Item
											onClick={() => {
												if (onClick) {
													setActive(false);

													return onClick();
												}

												if (!path) {
													return;
												}

												const isHttpUrl = path.startsWith(
													'http'
												);

												if (isHttpUrl) {
													window.location.href = path;

													return;
												}

												setActive(false);

												navigate(path);
											}}
										>
											<div className="align-items-center d-flex testray-sidebar-item text-dark">
												{icon && (
													<ClayIcon
														fontSize={16}
														symbol={icon}
													/>
												)}

												<span className="ml-1 testray-sidebar-text">
													{label}
												</span>
											</div>
										</ClayDropDown.Item>

										{divider && <ClayDropDown.Divider />}
									</React.Fragment>
								)
							)}
						</ClayDropDown.Group>

						{items.length - 1 !== index && <ClayDropDown.Divider />}
					</div>
				))}
			</ClayDropDown.ItemList>
		</ClayDropDown>
	);
};

export default DropDown;
