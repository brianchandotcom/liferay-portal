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

import ClayButton from '@clayui/button';
import ClayDropDown, {Align} from '@clayui/drop-down';
import ClayIcon from '@clayui/icon';
import PropTypes from 'prop-types';
import React, {useMemo, useState} from 'react';

import {getLayoutDataItemPropTypes} from '../../prop-types/index';
import {useSelectItem} from '../contexts/ControlsContext';
import {useDispatch, useSelector} from '../contexts/StoreContext';
import deleteItem from '../thunks/deleteItem';
import duplicateItem from '../thunks/duplicateItem';
import canBeDuplicated from '../utils/canBeDuplicated';
import canBeRemoved from '../utils/canBeRemoved';
import canBeSaved from '../utils/canBeSaved';
import SaveFragmentCompositionModal from './SaveFragmentCompositionModal';

export default function ItemActions({item}) {
	const [active, setActive] = useState(false);
	const dispatch = useDispatch();
	const selectItem = useSelectItem();

	const state = useSelector((state) => state);
	const {
		fragmentEntryLinks,
		layoutData,
		segmentsExperienceId,
		widgets,
	} = state;

	const [openSaveModal, setOpenSaveModal] = useState(false);

	const itemActions = useMemo(() => {
		const actions = [];

		if (canBeDuplicated(fragmentEntryLinks, item, layoutData, widgets)) {
			actions.push({
				action: () =>
					dispatch(
						duplicateItem({
							itemId: item.itemId,
							segmentsExperienceId,
							selectItem,
						})
					),
				icon: 'paste',
				label: Liferay.Language.get('duplicate'),
			});
		}

		if (canBeSaved(item, layoutData)) {
			actions.push({
				action: () => setOpenSaveModal(true),
				icon: 'disk',
				label: Liferay.Language.get('save-composition'),
			});
		}

		if (canBeRemoved(item, layoutData)) {
			actions.push({
				action: () =>
					dispatch(
						deleteItem({
							itemId: item.itemId,
							selectItem,
							store: state,
						})
					),
				icon: 'times-circle',
				label: Liferay.Language.get('delete'),
			});
		}

		return actions;
	}, [
		dispatch,
		fragmentEntryLinks,
		item,
		layoutData,
		segmentsExperienceId,
		selectItem,
		state,
		widgets,
	]);

	return itemActions?.length ? (
		<>
			<ClayDropDown
				active={active}
				alignmentPosition={Align.BottomRight}
				onActiveChange={setActive}
				trigger={
					<ClayButton
						displayType="unstyled"
						small
						title={Liferay.Language.get('options')}
					>
						<ClayIcon
							className="page-editor__topper__icon"
							symbol="ellipsis-v"
						/>
					</ClayButton>
				}
			>
				<ClayDropDown.ItemList>
					{itemActions.map((itemAction) => (
						<ClayDropDown.Item
							key={itemAction.label}
							onClick={() => {
								setActive(false);

								itemAction.action();
							}}
							symbolLeft={itemAction.icon}
						>
							<p className="d-inline-block m-0 ml-4">
								{itemAction.label}
							</p>
						</ClayDropDown.Item>
					))}
				</ClayDropDown.ItemList>
			</ClayDropDown>

			{openSaveModal && (
				<SaveFragmentCompositionModal
					onCloseModal={() => setOpenSaveModal(false)}
				/>
			)}
		</>
	) : null;
}

ItemActions.propTypes = {
	item: PropTypes.oneOfType([getLayoutDataItemPropTypes()]),
};
