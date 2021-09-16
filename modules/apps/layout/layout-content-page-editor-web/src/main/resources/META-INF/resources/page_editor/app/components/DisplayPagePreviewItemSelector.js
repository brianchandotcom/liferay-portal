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

import ClayDropDown, {Align} from '@clayui/drop-down';
import ClayIcon from '@clayui/icon';
import classNames from 'classnames';
import PropTypes from 'prop-types';
import React, {useState} from 'react';

import {openItemSelector} from '../../core/openItemSelector';
import {config} from '../config/index';
import {
	useDisplayPagePreviewItem,
	useDisplayPageRecentPreviewItemList,
	useSelectDisplayPagePreviewItem,
} from '../contexts/DisplayPagePreviewItemContext';
import itemSelectorValueToInfoItem from '../utils/item-selector-value/itemSelectorValueToInfoItem';
import {useId} from '../utils/useId';

const NO_ITEM_LABEL = `-- ${Liferay.Language.get('none')} --`;

export const DisplayPagePreviewItemSelector = ({dark = false}) => {
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
};

DisplayPagePreviewItemSelector.propTypes = {
	dark: PropTypes.bool,
};
