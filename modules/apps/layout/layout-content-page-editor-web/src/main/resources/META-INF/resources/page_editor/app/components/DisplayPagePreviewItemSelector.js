/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayDropDown, {Align} from '@clayui/drop-down';
import ClayIcon from '@clayui/icon';
import {ReactPortal} from '@liferay/frontend-js-react-web';
import classNames from 'classnames';
import {useId} from 'frontend-js-components-web';
import PropTypes from 'prop-types';
import React, {useMemo, useState} from 'react';

import {openItemSelector} from '../../common/openItemSelector';
import {LAYOUT_TYPES} from '../config/constants/layoutTypes';
import {config} from '../config/index';
import {
	useDisplayPagePreviewItem,
	useDisplayPageRecentPreviewItemList,
	useSelectDisplayPagePreviewItem,
} from '../contexts/DisplayPagePreviewItemContext';
import itemSelectorValueToInfoItem from '../utils/item_selector_value/itemSelectorValueToInfoItem';

const NO_ITEM_LABEL = `-- ${Liferay.Language.get('none')} --`;

export function DisplayPagePreviewItemSelector({dark = false}) {
	const displayPagePreviewItemSelectorWrapper = useMemo(
		() =>
			config.layoutType === LAYOUT_TYPES.display &&
			document.getElementById('infoItemSelectorContainer'),
		[]
	);

	return displayPagePreviewItemSelectorWrapper ? (
		<ReactPortal container={displayPagePreviewItemSelectorWrapper}>
			<DisplayPagePreviewItemSelectorContent dark={dark} />
		</ReactPortal>
	) : null;
}

DisplayPagePreviewItemSelector.propTypes = {
	dark: PropTypes.bool,
};

export function DisplayPagePreviewItemSelectorContent({dark = false}) {
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
		<ClayDropDown
			active={active}
			alignmentPosition={Align.BottomRight}
			aria-labelledby={selectLabelId}
			menuElementAttrs={{
				containerProps: {
					className: 'cadmin',
				},
			}}
			onActiveChange={setActive}
			role="listbox"
			trigger={
				<p
					className={classNames(
						'align-items-center d-flex flex-row mb-0 page-editor__display-page-preview-item-selector-label-wrapper w-100',
						{
							'page-editor__display-page-preview-item-selector-label-wrapper-dark': dark,
						}
					)}
					id={selectLabelId}
					role="label"
				>
					<strong
						className={classNames(
							'd-block page-editor__display-page-preview-item-selector-label',
							{'text-secondary': !dark}
						)}
					>
						{Liferay.Language.get('preview-with')}:
					</strong>

					<button
						className={classNames(
							'align-items-center btn btn-sm d-flex page-editor__display-page-preview-item-selector-button',
							dark ? 'btn-dark' : 'btn-secondary'
						)}
						type="button"
					>
						<span className="flex-grow-1 overflow-hidden text-left text-truncate">
							{previewItem ? previewItem.label : NO_ITEM_LABEL}
						</span>

						<ClayIcon
							className="flex-shrink-0 text-secondary"
							symbol="caret-bottom"
						/>
					</button>
				</p>
			}
		>
			<ClayDropDown.ItemList>
				<ClayDropDown.Item
					aria-selected={!previewItem}
					onClick={() => selectItem(null)}
					role="option"
					symbolRight={!previewItem ? 'check' : undefined}
				>
					{NO_ITEM_LABEL}
				</ClayDropDown.Item>

				{recentPreviewItemList.map((recentPreviewItem) => (
					<ClayDropDown.Item
						aria-selected={previewItem === recentPreviewItem}
						className="page-editor__display-page-preview-item-selector-dropdown-item"
						key={recentPreviewItem.label}
						onClick={() => selectItem(recentPreviewItem)}
						symbolRight={
							previewItem === recentPreviewItem ? 'check' : ''
						}
					>
						<span className="page-editor__display-page-preview-item-selector-dropdown-item-label">
							{recentPreviewItem.label}
						</span>
					</ClayDropDown.Item>
				))}
			</ClayDropDown.ItemList>

			<ClayDropDown.Divider />

			<ClayDropDown.ItemList>
				<ClayDropDown.Item onClick={selectOtherItem}>
					{Liferay.Language.get('select-other-item')}...
				</ClayDropDown.Item>
			</ClayDropDown.ItemList>
		</ClayDropDown>
	);
}

DisplayPagePreviewItemSelectorContent.propTypes = {
	dark: PropTypes.bool,
};
