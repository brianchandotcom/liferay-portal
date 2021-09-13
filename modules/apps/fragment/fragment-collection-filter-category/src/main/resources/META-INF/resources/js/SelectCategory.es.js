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
import {ClayDropDownWithItems} from '@clayui/drop-down';
import {
	getCollectionFilterValue,
	setCollectionFilterValue,
} from '@liferay/fragment-renderer-collection-filter-impl';
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
					checked: selectedCategoryIds?.[0],
					items: filteredCategories.map((category) => ({
						label: category.label,
						type: 'radio',
						value: category.id,
					})),
					name: 'categoryId',
					onChange: (categoryId) => onSelectedClick(true, categoryId),
					type: 'radiogroup',
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
		label = Liferay.Util.sub(
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
						small
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
				<ClayButton
					className="bg-light font-weight-normal form-control-select form-control-sm text-left w-100"
					displayType="secondary"
					small
				>
					{label}
				</ClayButton>
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
