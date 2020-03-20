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

import {ClayButtonWithIcon} from '@clayui/button';
import ClayDropDown from '@clayui/drop-down';
import React, {useState} from 'react';

const STAR_SCORES = [1, 2, 3, 4, 5];

const RatingsStars = () => {
	const [isDropdownOpen, setIsDropdownOpen] = useState(false);
	const [score, setScore] = useState(0);

	return (
		<ClayDropDown
			active={isDropdownOpen}
			onActiveChange={isActive => setIsDropdownOpen(isActive)}
			trigger={
				<ClayButtonWithIcon
					borderless
					displayType="secondary"
					small
					symbol="star-o"
				/>
			}
		>
			<ClayDropDown.ItemList>
				{STAR_SCORES.map((starScore, index) => (
					<ClayDropDown.Item
						active={starScore === score}
						key={index}
						onClick={() => {
							setScore(starScore);
							setIsDropdownOpen(false);
						}}
					>
						{starScore}
					</ClayDropDown.Item>
				))}
			</ClayDropDown.ItemList>
		</ClayDropDown>
	);
};

export default RatingsStars;
