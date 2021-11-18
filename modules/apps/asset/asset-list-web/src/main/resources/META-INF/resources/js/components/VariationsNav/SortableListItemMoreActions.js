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
import classnames from 'classnames';
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
			cssClasses: 'pl-3 py-1',
			direction: -1,
			disabled: !index,
			icon: 'angle-up',
			text: Liferay.Language.get('prioritize'),
		},
		{
			cssClasses: 'pl-3 py-1',
			direction: 1,
			disabled: index + 1 === totalItems,
			icon: 'angle-down',
			text: Liferay.Language.get('deprioritize'),
		},
		{
			cssClasses: 'border-top pl-3 py-1',
			deleteAction: true,
			disabled: !itemIsDeleteable,
			icon: 'trash',
			text: Liferay.Language.get('delete'),
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
			className="more-actions"
			onActiveChange={setShow}
			trigger={
				<ClayButtonWithIcon
					className={classnames('more-actions__button', {
						'more-actions__button--active': show,
					})}
					displayType="unstyled"
					symbol="ellipsis-v"
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
