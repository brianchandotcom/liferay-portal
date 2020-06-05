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

import React from 'react';

import AutocompleteFilter from '../management_bar/components/filters/AutocompleteFilter';
import CheckboxesFilter from '../management_bar/components/filters/CheckboxesFilter';
import DateFilter from '../management_bar/components/filters/DateFilter';
import DateRangeFilter from '../management_bar/components/filters/DateRangeFilter';
import NumberFilter from '../management_bar/components/filters/NumberFilter';
import RadioFilter from '../management_bar/components/filters/RadioFilter';
import SelectFilter from '../management_bar/components/filters/SelectFilter';
import TextFilter from '../management_bar/components/filters/TextFilter';

export const filterIdToComponentMap = {
	autocomplete: AutocompleteFilter,
	checkbox: CheckboxesFilter,
	date: DateFilter,
	dateRange: DateRangeFilter,
	number: NumberFilter,
	radio: RadioFilter,
	select: SelectFilter,
	text: TextFilter,
};

export const renderFilter = (item, panelType) => {
	const Filter = filterIdToComponentMap[item.type];

	if (!Filter) {
		throw new Error(`Filter type '${item.type}' not found.`);
	}

	return <Filter {...item} panelType={panelType} />;
};
