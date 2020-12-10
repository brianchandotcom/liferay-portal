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
import PropTypes from 'prop-types';
import React, {useEffect, useState} from 'react';

export default function MultiSelectCategory({
	assetCategories,
	fragmentEntryLinkId,
	selectedAssetCategoryIds: initialSelectedCategoryIds,
}) {
	const [selectedCategoryIds, setSelectedCategoryIds] = useState(() => {
		return assetCategories
			.filter((category) =>
				initialSelectedCategoryIds.includes(category.id)
			)
			.map((category) => category.id);
	});

	const [filteredCategories, setFilteredCategories] = useState(
		assetCategories
	);

	const [searchValue, setSearchValue] = useState('');

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
		if (selected) {
			setSelectedCategoryIds([...selectedCategoryIds, id]);
		}
		else {
			setSelectedCategoryIds(
				selectedCategoryIds.filter((category) => category !== id)
			);
		}
	};

	const items = filteredCategories.map((category) => ({
		...category,
		checked: initialSelectedCategoryIds.includes(category.id),
		onChange: (selected) => onSelectedClick(selected, category.id),
		type: 'checkbox',
	}));

	const onApply = () => {
		const queryParamName = `categoryId_${fragmentEntryLinkId}`;
		const search = new URLSearchParams(window.location.search);

		search.delete(queryParamName);

		selectedCategoryIds.forEach((id) => {
			search.append(queryParamName, id);
		});

		window.location.search = search;
	};

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
			footerContent={
				<ClayButton onClick={onApply} small>
					{Liferay.Language.get('apply')}
				</ClayButton>
			}
			items={items}
			onSearchValueChange={setSearchValue}
			searchValue={searchValue}
			searchable={true}
			trigger={
				<ClayButton
					className="form-control-select form-control-sm text-left w-100"
					displayType="secondary"
					small
				>
					{label}
				</ClayButton>
			}
		/>
	);
}

MultiSelectCategory.propTypes = {
	assetCategories: PropTypes.arrayOf(
		PropTypes.shape({
			id: PropTypes.string.isRequired,
			label: PropTypes.string.isRequired,
		}).isRequired
	),
	fragmentEntryLinkId: PropTypes.string.isRequired,
	selectedAssetCategoryIds: PropTypes.arrayOf(PropTypes.string).isRequired,
};
