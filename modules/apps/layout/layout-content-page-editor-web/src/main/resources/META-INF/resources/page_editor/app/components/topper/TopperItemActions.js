/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import ClayDropDown, {Align} from '@clayui/drop-down';
import ClayIcon from '@clayui/icon';
import {FeatureIndicator} from 'frontend-js-components-web';
import {openModal, openToast} from 'frontend-js-web';
import PropTypes from 'prop-types';
import React, {useMemo, useState} from 'react';

import {getLayoutDataItemPropTypes} from '../../../prop_types/index';
import {FRAGMENT_ENTRY_TYPES} from '../../config/constants/fragmentEntryTypes';
import {LAYOUT_DATA_ITEM_TYPES} from '../../config/constants/layoutDataItemTypes';
import {PORTLET_DEFAULT_ACTIONS} from '../../config/constants/portletDefaultActions';
import {useClipboard, useSetClipboard} from '../../contexts/ClipboardContext';
import {
	useSelectItem,
	useSelectMultipleItems,
} from '../../contexts/ControlsContext';
import {
	useDispatch,
	useSelector,
	useSelectorCallback,
} from '../../contexts/StoreContext';
import {useGetWidgets} from '../../contexts/WidgetsContext';
import deleteItem from '../../thunks/deleteItem';
import duplicateItem from '../../thunks/duplicateItem';
import pasteItems from '../../thunks/pasteItems';
import canBeDuplicated from '../../utils/canBeDuplicated';
import canBeRemoved from '../../utils/canBeRemoved';
import canBeSaved from '../../utils/canBeSaved';
import {
	FORM_ERROR_TYPES,
	getFormErrorDescription,
} from '../../utils/getFormErrorDescription';
import getPortletCustomActions from '../../utils/getPortletCustomActions';
import getPortletId from '../../utils/getPortletId';
import hideFragment from '../../utils/hideFragment';
import isCuttable from '../../utils/isCuttable';
import isInputFragment from '../../utils/isInputFragment';
import {isMovementValid} from '../../utils/isMovementValid';
import isStepper from '../../utils/isStepper';
import toMovementItem from '../../utils/toMovementItem';
import useHasRequiredChild from '../../utils/useHasRequiredChild';
import SaveFragmentCompositionModal from '../SaveFragmentCompositionModal';
import hasDropZoneChild from '../layout_data_items/hasDropZoneChild';

export default function TopperItemActions({disabled, item}) {
	const dispatch = useDispatch();
	const hasRequiredChild = useHasRequiredChild(item.itemId);
	const selectItem = useSelectItem();
	const selectMultipleItems = useSelectMultipleItems();
	const getWidgets = useGetWidgets();

	const clipboard = useClipboard();
	const setClipboard = useSetClipboard();

	const selectItems = Liferay.FeatureFlags['LPD-18221']
		? selectMultipleItems
		: selectItem;

	const {fragmentEntryLinks, layoutData, selectedViewportSize} = useSelector(
		(state) => state
	);

	const [openSaveModal, setOpenSaveModal] = useState(false);

	const fragmentEntryLink = useSelectorCallback(
		(state) => state.fragmentEntryLinks[item.config.fragmentEntryLinkId],
		[item.config.fragmentEntryLinkId]
	);

	const {portletActions, portletId} = useMemo(() => {
		if (
			fragmentEntryLink?.fragmentEntryType !== FRAGMENT_ENTRY_TYPES.widget
		) {
			return {};
		}

		return {
			portletActions: fragmentEntryLink.actions,
			portletId: getPortletId(fragmentEntryLink.editableValues),
		};
	}, [fragmentEntryLink]);

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

		addDivider(items);

		if (isCuttable(item.itemId, fragmentEntryLinks, layoutData)) {
			items.push({
				action: () => {
					setClipboard([item.itemId]);
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
				canBeDuplicated(
					fragmentEntryLinks,
					item,
					layoutData,
					getWidgets
				)
			) {
				items.push({
					action: () => setClipboard([item.itemId]),
					icon: 'copy',
					isBetaFeature: true,
					label: Liferay.Language.get('copy'),
				});
			}
		}

		if (canBeDuplicated(fragmentEntryLinks, item, layoutData, getWidgets)) {
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
		}

		if (portletId && Liferay.FeatureFlags['LPD-32075']) {
			addPortletAction(
				items,
				portletActions[PORTLET_DEFAULT_ACTIONS.exportImport],
				portletId
			);
		}

		if (
			Liferay.FeatureFlags['LPD-18221'] &&
			!isStepper(fragmentEntryLinks[item.config.fragmentEntryLinkId])
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
							targetId: item.itemId,
						})
					) {
						dispatch(
							pasteItems({
								clipboard,
								parentItemId: item.itemId,
								selectItems,
							})
						);
					}
				},
				disabled: !clipboard?.length,
				icon: 'paste',
				isBetaFeature: true,
				label: Liferay.Language.get('paste'),
			});
		}

		addDivider(items);

		if (portletId && Liferay.FeatureFlags['LPD-32075']) {
			addPortletAction(
				items,
				portletActions[PORTLET_DEFAULT_ACTIONS.configuration],
				portletId
			);

			addPortletAction(
				items,
				portletActions[PORTLET_DEFAULT_ACTIONS.configurationTemplates],
				portletId
			);

			addPortletAction(
				items,
				portletActions[PORTLET_DEFAULT_ACTIONS.permissions],
				portletId
			);

			const customActions = getPortletCustomActions(fragmentEntryLink);

			if (customActions.length) {
				addDivider(items);

				for (const action of customActions) {
					addPortletAction(items, action, portletId);
				}
			}
		}

		if (canBeRemoved(item, layoutData)) {
			addDivider(items);

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
		clipboard,
		dispatch,
		fragmentEntryLink,
		fragmentEntryLinks,
		getWidgets,
		hasRequiredChild,
		item,
		layoutData,
		portletActions,
		portletId,
		selectedViewportSize,
		setClipboard,
		selectItems,
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

function addDivider(items) {
	const lastItem = items.at(-1);

	if (!items.length || lastItem.type === 'divider') {
		return;
	}

	items.push({
		type: 'divider',
	});
}

function addPortletAction(items, action, portletId) {
	if (!action) {
		return;
	}

	items.push({
		action: () => {
			openModal({
				onClose: () => Liferay.Portlet.refresh(`#p_p_id_${portletId}_`),
				title: action.title,
				url: action.url,
			});
		},
		icon: action.icon,
		label: action.title,
	});
}

TopperItemActions.propTypes = {
	item: PropTypes.oneOfType([getLayoutDataItemPropTypes()]),
};
