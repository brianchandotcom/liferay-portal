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
import PropTypes from 'prop-types';
import React, {useState} from 'react';

import DropDownItem from './DropDownItem';

const SortableListItemMoreActions = ({
	index,
	itemIsDeleteable,
	onDeleteVariation,
	onReorder,
	totalItems,
}) => {
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
		{
			cssClasses: 'border-top mt-4',
			deleteAction: true,
			disabled: !itemIsDeleteable,
			icon: 'trash',
			text: 'Delete',
		},
	];

	const handleClick = ({deleteAction, direction, index}) => {
		if (deleteAction) {
			onDeleteVariation();

			return;
		}

		onReorder({direction, index});
	};

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
				{dropDownItems.map(
					({
						cssClasses,
						deleteAction,
						direction,
						disabled,
						icon,
						text,
					}) => (
						<DropDownItem
							cssClasses={cssClasses}
							deleteAction={deleteAction}
							direction={direction}
							disabled={disabled}
							icon={icon}
							index={index}
							key={text}
							onClick={handleClick}
							text={text}
						/>
					)
				)}
			</ClayDropDown.ItemList>
		</ClayDropDown>
	);
};

SortableListItemMoreActions.propTypes = {
	index: PropTypes.number.isRequired,
	itemIsDeleteable: PropTypes.bool.isRequired,
	onDeleteVariation: PropTypes.func.isRequired,
	onReorder: PropTypes.func.isRequired,
	totalItems: PropTypes.number.isRequired,
};

export default SortableListItemMoreActions;
