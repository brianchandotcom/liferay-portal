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

import ClayButton, {ClayButtonWithIcon} from '@clayui/button';
import ClayDropDown from '@clayui/drop-down';
import ClayIcon from '@clayui/icon';
import PropTypes from 'prop-types';
import React, {useState} from 'react';

const DropDownItem = ({direction, disabled, icon, index, onClick, text}) => {
	const handleClick = () => {
		onClick({direction, index});
	};

	return (
		<ClayDropDown.Item>
			<ClayButton
				block
				className="align-items-center d-flex justify-content-between"
				disabled={disabled}
				displayType={null}
				onClick={handleClick}
				small
			>
				{text}
				<ClayIcon symbol={icon} />
			</ClayButton>
		</ClayDropDown.Item>
	);
};

const SortableListItemMoreActions = ({index, onReorder, totalItems}) => {
	const [show, setShow] = useState(false);

	const dropDownItems = [
		{
			direction: -1,
			disabled: !index,
			icon: 'angle-up',
			text: 'Prioritize',
		},
		{
			direction: 1,
			disabled: index + 1 === totalItems,
			icon: 'angle-down',
			text: 'Deprioritize',
		},
	];

	return (
		<ClayDropDown
			active={show}
			onActiveChange={setShow}
			trigger={
				<ClayButtonWithIcon
					displayType="unstyled"
					symbol={show ? 'times' : 'ellipsis-v'}
				/>
			}
		>
			<ClayDropDown.ItemList>
				{dropDownItems.map(({direction, disabled, icon, text}) => (
					<DropDownItem
						direction={direction}
						disabled={disabled}
						icon={icon}
						index={index}
						key={text}
						onClick={onReorder}
						text={text}
					/>
				))}
			</ClayDropDown.ItemList>
		</ClayDropDown>
	);
};

SortableListItemMoreActions.propTypes = {
	index: PropTypes.number.isRequired,
	onReorder: PropTypes.func.isRequired,
};

export default SortableListItemMoreActions;
