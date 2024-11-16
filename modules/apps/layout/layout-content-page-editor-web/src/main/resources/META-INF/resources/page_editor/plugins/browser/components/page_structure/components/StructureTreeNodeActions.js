/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import ClayDropDown from '@clayui/drop-down';
import ClayIcon from '@clayui/icon';
import {FocusScope} from '@clayui/shared';
import classNames from 'classnames';
import {FeatureIndicator} from 'frontend-js-components-web';
import {openToast} from 'frontend-js-web';
import React, {useCallback, useMemo, useRef, useState} from 'react';
import {flushSync} from 'react-dom';

import SaveFragmentCompositionModal from '../../../../../app/components/SaveFragmentCompositionModal';
import hasDropZoneChild from '../../../../../app/components/layout_data_items/hasDropZoneChild';
import {ITEM_ACTIVATION_ORIGINS} from '../../../../../app/config/constants/itemActivationOrigins';
import {LAYOUT_DATA_ITEM_TYPES} from '../../../../../app/config/constants/layoutDataItemTypes';
import {
	useClipboard,
	useSetClipboard,
} from '../../../../../app/contexts/ClipboardContext';
import {
	useSelectItem,
	useSelectMultipleItems,
} from '../../../../../app/contexts/ControlsContext';
import {useSetMovementText} from '../../../../../app/contexts/KeyboardMovementContext';
import {useSetEditedNodeId} from '../../../../../app/contexts/ShortcutContext';
import {
	useDispatch,
	useSelector,
} from '../../../../../app/contexts/StoreContext';
import {useGetWidgets} from '../../../../../app/contexts/WidgetsContext';
import deleteItem from '../../../../../app/thunks/deleteItem';
import duplicateItem from '../../../../../app/thunks/duplicateItem';
import pasteItems from '../../../../../app/thunks/pasteItems';
import canBeDuplicated from '../../../../../app/utils/canBeDuplicated';
import canBeRemoved from '../../../../../app/utils/canBeRemoved';
import canBeRenamed from '../../../../../app/utils/canBeRenamed';
import canBeSaved from '../../../../../app/utils/canBeSaved';
import {
	FORM_ERROR_TYPES,
	getFormErrorDescription,
} from '../../../../../app/utils/getFormErrorDescription';
import isCuttable from '../../../../../app/utils/isCuttable';
import isInputFragment from '../../../../../app/utils/isInputFragment';
import {isMovementValid} from '../../../../../app/utils/isMovementValid';
import isStepper from '../../../../../app/utils/isStepper';
import toMovementItem from '../../../../../app/utils/toMovementItem';
import updateItemStyle from '../../../../../app/utils/updateItemStyle';
import useHasRequiredChild from '../../../../../app/utils/useHasRequiredChild';

export default function StructureTreeNodeActions({disabled, item, visible}) {
	const [active, setActive] = useState(false);

	const [openSaveModal, setOpenSaveModal] = useState(false);

	const alignElementRef = useRef();
	const dropdownRef = useRef();

	const updateActive = useCallback((nextActive) => {
		flushSync(() => {
			setActive(nextActive);
		});

		if (nextActive) {
			dropdownRef.current?.querySelector('button')?.focus();
		}
		else {
			alignElementRef.current?.focus();
		}
	}, []);

	return (
		<>
			<ClayButton
				aria-expanded={active}
				aria-haspopup="true"
				aria-label={Liferay.Language.get('options')}
				className={classNames(
					'ml-0 page-editor__page-structure__tree-node__actions-button position-relative',
					{
						'page-editor__page-structure__tree-node__actions-button--visible':
							visible,
					}
				)}
				disabled={disabled}
				displayType="unstyled"
				onClick={(event) => {
					event.stopPropagation();
					updateActive(!active);
				}}
				ref={alignElementRef}
				size="sm"
				tabIndex={
					document.activeElement.dataset.id?.includes(item.id)
						? '0'
						: '-1'
				}
				title={Liferay.Language.get('options')}
			>
				{active ? (
					<div
						className="position-absolute"
						style={{
							height: '50px',
							transform: 'translateX(-10px, -10px)',
							width: '50px',
						}}
					/>
				) : null}

				<ClayIcon symbol="ellipsis-v" />
			</ClayButton>

			<ClayDropDown.Menu
				active={active}
				alignElementRef={alignElementRef}
				containerProps={{
					className: 'cadmin',
				}}
				hasLeftSymbols
				onActiveChange={updateActive}
				ref={dropdownRef}
			>
				{active && (
					<ActionList
						item={item}
						setActive={updateActive}
						setOpenSaveModal={setOpenSaveModal}
					/>
				)}
			</ClayDropDown.Menu>

			{openSaveModal && (
				<SaveFragmentCompositionModal
					itemId={item.id}
					onCloseModal={() => setOpenSaveModal(false)}
				/>
			)}
		</>
	);
}

const ActionList = ({item, setActive, setOpenSaveModal}) => {
	const dispatch = useDispatch();
	const hasRequiredChild = useHasRequiredChild(item.id);
	const selectItem = useSelectItem();
	const selectMultipleItems = useSelectMultipleItems();
	const setEditedNodeId = useSetEditedNodeId();
	const setText = useSetMovementText();
	const getWidgets = useGetWidgets();

	const clipboard = useClipboard();
	const setClipboard = useSetClipboard();

	const selectItems = Liferay.FeatureFlags['LPD-18221']
		? selectMultipleItems
		: selectItem;

	const {fragmentEntryLinks, layoutData, selectedViewportSize} = useSelector(
		(state) => state
	);

	const layoutDataItem = useSelector(
		(state) => state.layoutData.items[item.id]
	);

	const isHidden = item.config?.styles?.display === 'none';

	const dropdownItems = useMemo(() => {
		const items = [];

		if (
			item.type !== LAYOUT_DATA_ITEM_TYPES.column &&
			item.type !== LAYOUT_DATA_ITEM_TYPES.formStep &&
			item.type !== LAYOUT_DATA_ITEM_TYPES.fragmentDropZone &&
			item.type !== LAYOUT_DATA_ITEM_TYPES.dropZone &&
			!hasDropZoneChild(layoutDataItem, layoutData) &&
			!isInputFragment(layoutDataItem, fragmentEntryLinks)
		) {
			items.push({
				action: () => {
					updateItemStyle({
						dispatch,
						itemIds: [item.id],
						selectedViewportSize,
						styleName: 'display',
						styleValue: isHidden ? 'block' : 'none',
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

					selectItem(item.id, {
						origin: ITEM_ACTIVATION_ORIGINS.itemActions,
					});

					setText(
						isHidden
							? Liferay.Language.get('item-shown')
							: Liferay.Language.get('hidden-item')
					);
				},
				icon: isHidden ? 'view' : 'hidden',
				label: isHidden
					? Liferay.Language.get('show-fragment')
					: Liferay.Language.get('hide-fragment'),
			});
		}

		if (canBeSaved(layoutDataItem, layoutData)) {
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

		if (isCuttable(item.id, fragmentEntryLinks, layoutData)) {
			items.push({
				action: () => {
					setClipboard([item.id]);
					dispatch(
						deleteItem({
							itemIds: [item.id],
							selectItems,
						})
					);
					setText(Liferay.Language.get('item-was-cut'));
				},
				icon: 'cut',
				isBetaFeature: true,
				label: Liferay.Language.get('cut'),
			});
		}

		if (
			Liferay.FeatureFlags['LPD-18221'] &&
			canBeDuplicated(
				fragmentEntryLinks,
				layoutDataItem,
				layoutData,
				getWidgets
			)
		) {
			items.push({
				action: () => {
					setClipboard([item.id]);

					setText(Liferay.Language.get('item-copied'));
				},
				icon: 'copy',
				isBetaFeature: true,
				label: Liferay.Language.get('copy'),
			});
		}

		if (
			canBeDuplicated(
				fragmentEntryLinks,
				layoutDataItem,
				layoutData,
				getWidgets
			)
		) {
			items.push({
				action: () => {
					dispatch(
						duplicateItem({
							itemIds: [item.id],
							selectItems,
						})
					);

					setText(Liferay.Language.get('item-duplicated'));
				},
				icon: 'copy',
				label: Liferay.Language.get('duplicate'),
			});
		}

		if (
			Liferay.FeatureFlags['LPD-18221'] &&
			(!isStepper(fragmentEntryLinks[item.config.fragmentEntryLinkId]) ||
				item.type === LAYOUT_DATA_ITEM_TYPES.column ||
				item.type === LAYOUT_DATA_ITEM_TYPES.fragmentDropZone ||
				item.type === LAYOUT_DATA_ITEM_TYPES.formStep)
		) {
			items.push({
				action: () => {
					if (
						isMovementValid({
							fragmentEntryLinks,
							getWidgets,
							layoutData,
							sources: clipboard.map((id) =>
								toMovementItem(
									id,
									layoutData,
									fragmentEntryLinks
								)
							),
							targetId: item.id,
						})
					) {
						dispatch(
							pasteItems({
								clipboard,
								parentItemId: item.id,
								selectItems,
							})
						);

						setText(Liferay.Language.get('item-pasted'));
					}
				},
				disabled: !clipboard?.length,
				icon: 'paste',
				isBetaFeature: true,
				label: Liferay.Language.get('paste'),
			});
		}

		if (canBeRenamed(layoutDataItem)) {
			items.push({
				action: () => {
					setEditedNodeId(item.id);
				},
				label: Liferay.Language.get('rename'),
			});
		}

		if (canBeRemoved(layoutDataItem, layoutData)) {
			items.push({
				type: 'divider',
			});

			items.push({
				action: () => {
					dispatch(
						deleteItem({
							itemIds: [item.id],
							selectItems,
						})
					);

					setText(Liferay.Language.get('item-removed'));
				},
				icon: 'trash',
				label: Liferay.Language.get('delete'),
			});
		}

		return items;
	}, [
		clipboard,
		dispatch,
		fragmentEntryLinks,
		getWidgets,
		hasRequiredChild,
		item,
		layoutData,
		layoutDataItem,
		selectedViewportSize,
		selectItem,
		setClipboard,
		setEditedNodeId,
		setOpenSaveModal,
		setText,
		isHidden,
		selectItems,
	]);

	return (
		<FocusScope>
			<div>
				<ClayDropDown.ItemList items={dropdownItems}>
					{(item) =>
						item.type === 'divider' ? (
							<ClayDropDown.Divider />
						) : (
							<ClayDropDown.Item
								aria-label={item.label}
								disabled={item.disabled}
								onClick={() => {
									setActive(false);

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
			</div>
		</FocusScope>
	);
};
