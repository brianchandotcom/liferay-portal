/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import ClayDropDown, {Align} from '@clayui/drop-down';
import ClayIcon from '@clayui/icon';
import {openToast} from 'frontend-js-web';
import PropTypes from 'prop-types';
import React, {useMemo, useState} from 'react';

import {getLayoutDataItemPropTypes} from '../../../prop_types/index';
import {LAYOUT_DATA_ITEM_TYPES} from '../../config/constants/layoutDataItemTypes';
import {
	useCopiedNodeIds,
	useSetCopiedNodeIds,
} from '../../contexts/ClipboardContext';
import {
	useSelectItem,
	useSelectMultipleItems,
} from '../../contexts/ControlsContext';
import {useDispatch, useSelector} from '../../contexts/StoreContext';
import deleteItem from '../../thunks/deleteItem';
import duplicateItem from '../../thunks/duplicateItem';
import pasteItem from '../../thunks/pasteItem';
import canBeDuplicated from '../../utils/canBeDuplicated';
import canBeRemoved from '../../utils/canBeRemoved';
import canBeSaved from '../../utils/canBeSaved';
import {
	FORM_ERROR_TYPES,
	getFormErrorDescription,
} from '../../utils/getFormErrorDescription';
import hideFragment from '../../utils/hideFragment';
import isInputFragment from '../../utils/isInputFragment';
import useHasRequiredChild from '../../utils/useHasRequiredChild';
import SaveFragmentCompositionModal from '../SaveFragmentCompositionModal';
import hasDropZoneChild from '../layout_data_items/hasDropZoneChild';

export default function TopperItemActions({disabled, item}) {
	const [active, setActive] = useState(false);
	const dispatch = useDispatch();
	const hasRequiredChild = useHasRequiredChild(item.itemId);
	const copiedNodeIds = useCopiedNodeIds();
	const selectItem = useSelectItem();
	const selectMultipleItems = useSelectMultipleItems();
	const setCopiedNodeIds = useSetCopiedNodeIds();
	const widgets = useSelector((state) => state.widgets);

	const selectItems = Liferay.FeatureFlags['LPD-18221']
		? selectMultipleItems
		: selectItem;

	const {fragmentEntryLinks, layoutData, selectedViewportSize} = useSelector(
		(state) => state
	);

	const [openSaveModal, setOpenSaveModal] = useState(false);

	const dropdownItems = useMemo(() => {
		const items = [];

		if (
			item.type !== LAYOUT_DATA_ITEM_TYPES.dropZone &&
			item.type !== LAYOUT_DATA_ITEM_TYPES.formStepContainer &&
			!hasDropZoneChild(item, layoutData) &&
			!isInputFragment(item, fragmentEntryLinks)
		) {
			items.push({
				action: () => {
					hideFragment({
						dispatch,
						itemId: item.itemId,
						selectedViewportSize,
					});

					if (hasRequiredChild()) {
						const {message} = getFormErrorDescription({
							type: FORM_ERROR_TYPES.hiddenFragment,
						});

						openToast({
							message,
							type: 'warning',
						});
					}
				},
				icon: 'hidden',
				label: Liferay.Language.get('hide-fragment'),
			});
		}

		if (canBeSaved(item, layoutData)) {
			items.push({
				action: () => setOpenSaveModal(true),
				icon: 'disk',
				label: Liferay.Language.get('save-composition'),
			});
		}

		if (items.length) {
			items.push({
				type: 'separator',
			});
		}

		if (
			Liferay.FeatureFlags['LPD-18221'] &&
			canBeRemoved(item, layoutData)
		) {
			items.push({
				action: () => {
					setCopiedNodeIds([item.itemId]);
					dispatch(
						deleteItem({
							itemIds: [item.itemId],
							selectItems,
						})
					);
				},
				icon: 'cut',
				label: Liferay.Language.get('cut'),
			});

			if (
				canBeDuplicated(fragmentEntryLinks, item, layoutData, widgets)
			) {
				items.push({
					action: () => setCopiedNodeIds([item.itemId]),
					icon: 'copy',
					label: Liferay.Language.get('copy'),
				});
			}
		}

		if (canBeDuplicated(fragmentEntryLinks, item, layoutData, widgets)) {
			items.push({
				action: () =>
					dispatch(
						duplicateItem({
							itemIds: [item.itemId],
							selectItems,
						})
					),
				icon: 'copy',
				label: Liferay.Language.get('duplicate'),
			});

			if (!Liferay.FeatureFlags['LPD-18221']) {
				items.push({
					type: 'separator',
				});
			}
		}

		if (
			Liferay.FeatureFlags['LPD-18221'] &&
			canBeDuplicated(fragmentEntryLinks, item, layoutData, widgets)
		) {
			items.push({
				action: () =>
					dispatch(
						pasteItem({
							copyItemIds: copiedNodeIds,
							itemIds: [item.itemId],
							selectItems,
						})
					),
				disabled: !copiedNodeIds?.length,
				icon: 'paste',
				label: Liferay.Language.get('paste'),
			});

			items.push({
				type: 'separator',
			});
		}

		if (canBeRemoved(item, layoutData)) {
			items.push({
				action: () =>
					dispatch(
						deleteItem({
							itemIds: [item.itemId],
							selectItems,
						})
					),
				icon: 'trash',
				label: Liferay.Language.get('delete'),
			});
		}

		return items;
	}, [
		copiedNodeIds,
		dispatch,
		fragmentEntryLinks,
		hasRequiredChild,
		item,
		layoutData,
		selectedViewportSize,
		setCopiedNodeIds,
		selectItems,
		widgets,
	]);

	if (!dropdownItems.length) {
		return null;
	}

	return (
		<>
			<ClayDropDown
				active={active}
				alignmentPosition={Align.BottomRight}
				menuElementAttrs={{
					containerProps: {
						className: 'cadmin',
					},
				}}
				onActiveChange={setActive}
				trigger={
					<ClayButton
						aria-label={Liferay.Language.get('options')}
						disabled={disabled}
						displayType="unstyled"
						onClick={(event) => event.stopPropagation()}
						size="sm"
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
					{dropdownItems.map((dropdownItem, index, array) =>
						dropdownItem.type === 'separator' ? (
							index !== array.length - 1 && (
								<ClayDropDown.Divider key={index} />
							)
						) : (
							<React.Fragment key={index}>
								<ClayDropDown.Item
									disabled={dropdownItem.disabled}
									onClick={(event) => {
										event.stopPropagation();

										setActive(false);

										dropdownItem.action();
									}}
									symbolLeft={dropdownItem.icon}
								>
									<p className="d-inline-block m-0 ml-4">
										{dropdownItem.label}
									</p>
								</ClayDropDown.Item>
							</React.Fragment>
						)
					)}
				</ClayDropDown.ItemList>
			</ClayDropDown>

			{openSaveModal && (
				<SaveFragmentCompositionModal
					onCloseModal={() => setOpenSaveModal(false)}
				/>
			)}
		</>
	);
}

TopperItemActions.propTypes = {
	item: PropTypes.oneOfType([getLayoutDataItemPropTypes()]),
};
