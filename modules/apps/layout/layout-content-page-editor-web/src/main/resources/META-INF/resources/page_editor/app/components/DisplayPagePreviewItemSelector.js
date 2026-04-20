/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayDropDown, {Align} from '@clayui/drop-down';
import {ReactPortal} from '@liferay/frontend-js-react-web';
import classNames from 'classnames';
import {useId} from 'frontend-js-components-web';
import React, {useMemo, useState} from 'react';

import {openItemSelector} from '../../common/openItemSelector';
import {LAYOUT_TYPES} from '../config/constants/layoutTypes';
import {config} from '../config/index';
import {
	useDisplayPagePreviewItem,
	useDisplayPageRecentPreviewItemList,
	useSelectDisplayPagePreviewItem,
} from '../contexts/DisplayPagePreviewItemContext';
import {deepEqual} from '../utils/checkDeepEqual';
import itemSelectorValueToInfoItem from '../utils/item_selector_value/itemSelectorValueToInfoItem';

const NO_ITEM_LABEL = `${Liferay.Language.get('none')}`;

export function DisplayPagePreviewItemSelector() {
	const displayPagePreviewItemSelectorWrapper = useMemo(
		() =>
			config.layoutType === LAYOUT_TYPES.display &&
			document.getElementById('infoItemSelectorContainer'),
		[]
	);

	return displayPagePreviewItemSelectorWrapper ? (
		<ReactPortal container={displayPagePreviewItemSelectorWrapper}>
			<DisplayPagePreviewItemSelectorContent />
		</ReactPortal>
	) : null;
}

export function DisplayPagePreviewItemSelectorContent() {
	const [active, setActive] = useState(false);
	const previewItem = useDisplayPagePreviewItem();
	const recentPreviewItemList = useDisplayPageRecentPreviewItemList();
	const selectLabelId = useId();
	const selectPreviewItem = useSelectDisplayPagePreviewItem();

	const selectItem = (item) => {
		setActive(false);
		selectPreviewItem(item);
	};

	const selectOtherItem = () =>
		openItemSelector({
			callback: (data) => selectItem({data, label: data.title}),
			eventName: `${config.portletNamespace}selectInfoItem`,
			itemSelectorURL: config.infoItemPreviewSelectorURL,
			transformValueCallback: itemSelectorValueToInfoItem,
		});

	return (
		<div className="align-items-center d-flex mb-0 page-editor__display-page-preview-item-selector-label-wrapper">
			<label className="mb-0 pr-2 text-secondary" id={selectLabelId}>
				<strong
					className={classNames(
						'd-block page-editor__display-page-preview-item-selector-label'
					)}
				>
					{Liferay.Language.get('preview-with')}:
				</strong>
			</label>

			<ClayDropDown
				active={active}
				alignmentPosition={Align.BottomRight}
				aria-labelledby={selectLabelId}
				hasLeftSymbols
				menuElementAttrs={{
					className: 'dropdown-menu-select',
					containerProps: {
						className: 'cadmin',
					},
				}}
				onActiveChange={setActive}
				role="listbox"
				trigger={
					<button
						className={classNames(
							'form-control form-control-sm form-control-select form-control-select-secondary page-editor__display-page-preview-item-selector-button'
						)}
						data-qa-id="previewItemSelectorButton"
						type="button"
					>
						{previewItem ? previewItem.label : NO_ITEM_LABEL}
					</button>
				}
			>
				<ClayDropDown.ItemList>
					<ClayDropDown.Item
						aria-selected={!previewItem}
						onClick={() => selectItem(null)}
						role="option"
						symbolLeft={!previewItem ? 'check-small' : undefined}
					>
						{NO_ITEM_LABEL}
					</ClayDropDown.Item>

					{recentPreviewItemList.map((recentPreviewItem) => (
						<ClayDropDown.Item
							aria-selected={deepEqual(
								previewItem,
								recentPreviewItem
							)}
							className="page-editor__display-page-preview-item-selector-dropdown-item"
							key={recentPreviewItem.label}
							onClick={() => selectItem(recentPreviewItem)}
							symbolLeft={
								deepEqual(previewItem, recentPreviewItem)
									? 'check-small'
									: ''
							}
						>
							{recentPreviewItem.label}
						</ClayDropDown.Item>
					))}
				</ClayDropDown.ItemList>

				<ClayDropDown.Divider />

				<ClayDropDown.ItemList>
					<ClayDropDown.Item
						data-qa-id="selectOtherItemDropdownItem"
						onClick={selectOtherItem}
					>
						{Liferay.Language.get('select-other-item')}...
					</ClayDropDown.Item>
				</ClayDropDown.ItemList>
			</ClayDropDown>
		</div>
	);
}
