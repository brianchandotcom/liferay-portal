/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ClayButtonWithIcon} from '@clayui/button';
import ClayDropDown from '@clayui/drop-down';
import classnames from 'classnames';
import {sub} from 'frontend-js-web';
import PropTypes from 'prop-types';
import React, {useState} from 'react';

import DropDownItem from './DropDownItem';

const SortableListItemMoreActions = ({
	index,
	itemIsDeleteable,
	onDeleteVariation,
	onReorder,
	sortableListItemName,
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
					aria-label={sub(
						Liferay.Language.get('actions-for-x'),
						sortableListItemName
					)}
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
	sortableListItemName: PropTypes.string.isRequired,
	totalItems: PropTypes.number.isRequired,
};

export default SortableListItemMoreActions;
