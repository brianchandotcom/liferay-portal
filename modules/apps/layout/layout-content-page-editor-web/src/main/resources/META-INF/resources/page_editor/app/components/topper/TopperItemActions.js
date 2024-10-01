/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import ClayDropDown, {Align} from '@clayui/drop-down';
import ClayIcon from '@clayui/icon';
import {FeatureIndicator} from 'frontend-js-components-web';
import {openToast} from 'frontend-js-web';
import PropTypes from 'prop-types';
import React, {useMemo, useState} from 'react';

import {getLayoutDataItemPropTypes} from '../../../prop_types/index';
import {LAYOUT_DATA_ITEM_TYPES} from '../../config/constants/layoutDataItemTypes';
import {
	useCopiedItemIds,
	useSetCopiedItemIds,
} from '../../contexts/ClipboardContext';
import {
	useSelectItem,
	useSelectMultipleItems,
} from '../../contexts/ControlsContext';
import {useDispatch, useSelector} from '../../contexts/StoreContext';
import deleteItem from '../../thunks/deleteItem';
import duplicateItem from '../../thunks/duplicateItem';
import pasteItem from '../../thunks/pasteItem';
import canBeCopied from '../../utils/canBeCopied';
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
	const copiedItemIds = useCopiedItemIds();
	const dispatch = useDispatch();
	const hasRequiredChild = useHasRequiredChild(item.itemId);
	const selectItem = useSelectItem();
	const selectMultipleItems = useSelectMultipleItems();
	const setCopiedItemIds = useSetCopiedItemIds();
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
				type: 'divider',
			});
		}

		if (
			Liferay.FeatureFlags['LPD-18221'] &&
			canBeRemoved(item, layoutData)
		) {
			items.push({
				action: () => {
					setCopiedItemIds([item.itemId]);
					dispatch(
						deleteItem({
							itemIds: [item.itemId],
							selectItems,
						})
					);
				},
				icon: 'cut',
				isBetaFeature: true,
				label: Liferay.Language.get('cut'),
			});

			if (
				canBeDuplicated(fragmentEntryLinks, item, layoutData, widgets)
			) {
				items.push({
					action: () => setCopiedItemIds([item.itemId]),
					icon: 'copy',
					isBetaFeature: true,
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
					type: 'divider',
				});
			}
		}

		if (
			Liferay.FeatureFlags['LPD-18221'] &&
			canBeDuplicated(fragmentEntryLinks, item, layoutData, widgets)
		) {
			items.push({
				action: () => {
					if (
						copiedItemIds.every(
							(copiedItemId) =>
								!!layoutData.items[copiedItemId] &&
								!!item &&
								canBeCopied(
									copiedItemId,
									fragmentEntryLinks,
									item.itemId,
									layoutData
								)
						)
					) {
						dispatch(
							pasteItem({
								copiedItemIds,
								parentItemId: item.itemId,
								selectItems,
							})
						);
					}
				},
				disabled: !copiedItemIds?.length,
				icon: 'paste',
				isBetaFeature: true,
				label: Liferay.Language.get('paste'),
			});

			items.push({
				type: 'divider',
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
		copiedItemIds,
		dispatch,
		fragmentEntryLinks,
		hasRequiredChild,
		item,
		layoutData,
		selectedViewportSize,
		setCopiedItemIds,
		selectItems,
		widgets,
	]);

	if (!dropdownItems.length) {
		return null;
	}

	return (
		<>
			<ClayDropDown
				alignmentPosition={Align.BottomRight}
				closeOnClick
				hasLeftSymbols
				menuElementAttrs={{
					containerProps: {
						className: 'cadmin',
					},
				}}
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
				<ClayDropDown.ItemList items={dropdownItems}>
					{(item) =>
						item.type === 'divider' ? (
							<ClayDropDown.Divider />
						) : (
							<ClayDropDown.Item
								disabled={item.disabled}
								onClick={(event) => {
									event.stopPropagation();

									item.action();
								}}
								symbolLeft={item.icon}
							>
								{item.label}

								{item.isBetaFeature ? (
									<span className="ml-2">
										<FeatureIndicator type="beta" />
									</span>
								) : null}
							</ClayDropDown.Item>
						)
					}
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
