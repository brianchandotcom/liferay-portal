/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import ClayDropDown from '@clayui/drop-down';
import ClayForm, {ClayCheckbox} from '@clayui/form';
import classNames from 'classnames';
import {useId} from 'frontend-js-components-web';
import React, {useState} from 'react';

import {LAYOUT_DATA_ITEM_TYPES} from '../../config/constants/layoutDataItemTypes';
import {useHoverItem} from '../../contexts/ControlsContext';
import {useSelectorCallback} from '../../contexts/StoreContext';
import isEmptyArray from '../../utils/isEmptyArray';
import {isLayoutDataItemDeleted} from '../../utils/isLayoutDataItemDeleted';

export function selectConfiguredCollectionDisplays(state) {
	return Object.values(state.layoutData.items).filter(
		(item) =>
			item.type === LAYOUT_DATA_ITEM_TYPES.collection &&
			item.config?.collection &&
			!!Object.keys(item.config.collection).length &&
			!isLayoutDataItemDeleted(state.layoutData, item.itemId)
	);
}

export function TargetCollectionsField({
	enableCompatibleCollections = false,
	filterableCollections,
	onValueSelect,
	value,
}) {
	const [active, setActive] = useState(false);
	const inputId = useId();
	const [nextValue, setNextValue] = useState(value || []);
	const hoverItem = useHoverItem();

	const inputValue = useSelectorCallback(
		(state) => {
			if (!nextValue.length) {
				return '';
			}
			else if (nextValue.length === 1) {
				return state.layoutData.items[nextValue[0]]?.config?.collection
					?.title;
			}

			return Liferay.Language.get('multiple');
		},
		[nextValue]
	);

	const handleChange = (layoutItemId, checked) => {
		const included = nextValue.includes(layoutItemId);
		let selectedItems = nextValue;

		if (checked && !included) {
			selectedItems = [...nextValue, layoutItemId];

			setNextValue(selectedItems);
			onValueSelect('targetCollections', selectedItems);
		}
		else if (included) {
			selectedItems = nextValue.filter(
				(itemId) => itemId !== layoutItemId
			);

			setNextValue(selectedItems);
			onValueSelect('targetCollections', selectedItems);
		}
	};

	const items = Object.values(filterableCollections).map((item) => {
		const isSelected = nextValue.includes(item.itemId);

		return {
			checked: isSelected,
			disabled:
				enableCompatibleCollections &&
				!isSelected &&
				isItemDisabled({
					filterableCollections,
					itemId: item.itemId,
					targetCollections: nextValue,
				}),
			label: item.config.collection.title,
			onChange: (checked) => handleChange(item.itemId, checked),
			type: 'checkbox',
			value: item.itemId,
		};
	});

	return (
		<ClayForm.Group className="mt-1">
			<label htmlFor={inputId}>
				{Liferay.Language.get('target-collection')}
			</label>

			<ClayDropDown
				active={active}
				id={inputId}
				menuElementAttrs={{
					containerProps: {
						className:
							'cadmin page-editor__target-collections-field',
					},
				}}
				onActiveChange={setActive}
				trigger={
					<ClayButton
						aria-label={Liferay.Language.get('select')}
						className="bg-light font-weight-normal form-control-select text-left w-100"
						displayType="secondary"
						size="sm"
					>
						{inputValue ? (
							<span className="text-dark">{inputValue}</span>
						) : (
							Liferay.Language.get('select')
						)}
					</ClayButton>
				}
			>
				{enableCompatibleCollections &&
					Object.keys(filterableCollections).length > 1 && (
						<ClayDropDown.Help className="pt-3 px-3">
							{Liferay.Language.get(
								'multiple-selection-must-have-at-least-one-filter-in-common'
							)}
						</ClayDropDown.Help>
					)}

				{items.map((item) => (
					<label
						className={classNames('d-flex dropdown-item', {
							disabled: item.disabled,
						})}
						key={item.value}
						onMouseLeave={() => hoverItem(null)}
						onMouseOver={() => hoverItem(item.value)}
					>
						<ClayCheckbox
							checked={item.checked}
							disabled={item.disabled}
							onChange={item.onChange}
						/>

						<span className="font-weight-normal ml-2">
							{item.label}
						</span>
					</label>
				))}
			</ClayDropDown>
		</ClayForm.Group>
	);
}

function isItemDisabled({filterableCollections, itemId, targetCollections}) {
	if (isEmptyArray(targetCollections)) {
		return false;
	}

	const itemSupportedFilters =
		filterableCollections[itemId]?.supportedFilters || [];

	const targetCollectionsSupportedFilters = targetCollections.map(
		(targetCollection) =>
			filterableCollections[targetCollection].supportedFilters
	);

	return !itemSupportedFilters.some((supportedFilter) =>
		targetCollectionsSupportedFilters.every(
			(targetCollectionsSupportedFilter) =>
				targetCollectionsSupportedFilter.includes(supportedFilter)
		)
	);
}
