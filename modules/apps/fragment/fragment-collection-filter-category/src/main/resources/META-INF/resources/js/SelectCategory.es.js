/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import {ClayDropDownWithItems} from '@clayui/drop-down';
import {
	getCollectionFilterValue,
	setCollectionFilterValue,
} from '@liferay/fragment-renderer-collection-filter-impl';
import {sub} from 'frontend-js-web';
import PropTypes from 'prop-types';
import React, {useEffect, useState} from 'react';

export default function SelectCategory({
	assetCategories,
	enableDropdown,
	fragmentEntryLinkId,
	showSearch,
	singleSelection = false,
}) {
	const [selectedCategoryIds, setSelectedCategoryIds] = useState(() => {
		const value = getCollectionFilterValue('category', fragmentEntryLinkId);

		if (Array.isArray(value)) {
			return value;
		}
		else if (value) {
			return [value];
		}

		return [];
	});

	const [filteredCategories, setFilteredCategories] = useState(
		assetCategories
	);

	const [searchValue, setSearchValue] = useState('');

	const [active, setActive] = useState(false);

	useEffect(() => {
		setFilteredCategories(
			searchValue
				? assetCategories.filter(
						(category) =>
							category.label
								.toLowerCase()
								.indexOf(searchValue.toLowerCase()) !== -1
				  )
				: assetCategories
		);
	}, [searchValue, assetCategories]);

	const onSelectedClick = (selected, id) => {
		if (selected && singleSelection) {
			setCollectionFilterValue('category', fragmentEntryLinkId, [id]);
			setSelectedCategoryIds([id]);
		}
		else if (selected) {
			setSelectedCategoryIds([...selectedCategoryIds, id]);
		}
		else {
			setSelectedCategoryIds(
				selectedCategoryIds.filter((category) => category !== id)
			);
		}
	};

	const items = singleSelection
		? [
				{
					items: filteredCategories.map((category) => ({
						label: category.label,
						type: 'radio',
						value: category.id,
					})),
					name: 'categoryId',
					onChange: (categoryId) => onSelectedClick(true, categoryId),
					type: 'radiogroup',
					value: selectedCategoryIds?.[0],
				},
		  ]
		: filteredCategories.map((category) => ({
				checked: selectedCategoryIds.includes(category.id),
				label: category.label,
				onChange: (selected) => onSelectedClick(selected, category.id),
				type: 'checkbox',
		  }));

	let label = Liferay.Language.get('select');

	if (selectedCategoryIds.length === 1) {
		label =
			assetCategories.find(
				(category) => selectedCategoryIds[0] === category.id
			)?.label || label;
	}
	else if (selectedCategoryIds.length > 1) {
		label = sub(
			Liferay.Language.get('x-selected'),
			selectedCategoryIds.length
		);
	}

	return (
		<ClayDropDownWithItems
			active={active}
			footerContent={
				singleSelection ? null : (
					<ClayButton
						onClick={() =>
							setCollectionFilterValue(
								'category',
								fragmentEntryLinkId,
								selectedCategoryIds
							)
						}
						size="sm"
					>
						{Liferay.Language.get('apply')}
					</ClayButton>
				)
			}
			items={items}
			onActiveChange={(nextActive) => {
				if (enableDropdown) {
					setActive(nextActive);
				}
				else {
					setActive(false);
				}
			}}
			onSearchValueChange={setSearchValue}
			searchValue={searchValue}
			searchable={showSearch}
			trigger={
				<button className="form-control form-control-select form-control-sm text-left w-100">
					{label}
				</button>
			}
		/>
	);
}

SelectCategory.propTypes = {
	assetCategories: PropTypes.arrayOf(
		PropTypes.shape({
			id: PropTypes.string.isRequired,
			label: PropTypes.string.isRequired,
		}).isRequired
	),
	fragmentEntryLinkId: PropTypes.string.isRequired,
	showSearch: PropTypes.bool.isRequired,
	singleSelection: PropTypes.bool,
};
