/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Option, Picker} from '@clayui/core';
import React, {useContext, useEffect, useState} from 'react';

import TagService from '../../../common/services/TagService';
import {ViewDashboardContext} from '../ViewDashboardContext';
import {Item} from './FilterDropdown';
import {
	IAllFiltersDropdown,
	filterBySpaces,
	initialFilters,
} from './InventoryAnalysisCard';
import PickerTrigger from './PickerTrigger';

const AllTagsDropdown: React.FC<IAllFiltersDropdown> = ({
	className,
	item,
	onSelectItem,
}) => {
	const {
		constants: {cmsGroupId},
		filters: {space},
	} = useContext(ViewDashboardContext);

	const [tags, setTags] = useState<Item[]>([initialFilters.tag]);

	useEffect(() => {
		const fetchTags = async () => {
			const {data, error} = await TagService.getTags(cmsGroupId);

			if (error) {
				console.error(error);

				return;
			}

			if (data) {
				setTags([
					initialFilters.tag,
					...data.items
						.filter(
							({assetLibraries}) =>
								space.value === 'all' ||
								filterBySpaces(assetLibraries, space.value)
						)
						.map(({id, name}) => ({
							label: name,
							value: String(id),
						})),
				]);
			}
		};

		fetchTags();
	}, [cmsGroupId, space.value]);

	return (
		<Picker
			aria-label={Liferay.Language.get('filter-by-tag')}
			as={PickerTrigger}
			borderless
			filterKey="label"
			items={tags}
			messages={{
				noResultsFound: Liferay.Language.get('no-results-were-found'),
				searchPlaceholder: Liferay.Language.get('search'),
			}}
			onSelectionChange={(key) => {
				const selectedTag = tags.find(
					({value}) => value === String(key)
				);

				if (selectedTag) {
					onSelectItem(selectedTag);
				}
			}}
			searchable
			selectedKey={item.value}
			triggerClassName={className}
			triggerIcon="tag"
		>
			{(tag: Item) => <Option key={tag.value}>{tag.label}</Option>}
		</Picker>
	);
};

export {AllTagsDropdown};
