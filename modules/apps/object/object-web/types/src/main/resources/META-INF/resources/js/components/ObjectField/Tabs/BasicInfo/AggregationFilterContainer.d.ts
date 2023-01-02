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

/// <reference types="react" />

interface AggregationFilters {
	defaultSort?: boolean;
	fieldLabel?: string;
	filterBy?: string;
	filterType?: string;
	label: LocalizedValue<string>;
	objectFieldBusinessType?: string;
	objectFieldName: string;
	priority?: number;
	sortOrder?: string;
	type?: string;
	value?: string;
	valueList?: LabelValueObject[];
}
interface AggregationFilterProps {
	aggregationFilters: AggregationFilters[];
	creationLanguageId2?: Liferay.Language.Locale;
	filterOperators: TFilterOperators;
	objectDefinitionExternalReferenceCode2?: string;
	setAggregationFilters: (values: AggregationFilters[]) => void;
	setCreationLanguageId2: (values: Liferay.Language.Locale) => void;
	setValues: (values: Partial<ObjectField>) => void;
	values: Partial<ObjectField>;
	workflowStatusJSONArray: LabelValueObject[];
}
export declare function AggregationFilterContainer({
	aggregationFilters,
	creationLanguageId2,
	filterOperators,
	objectDefinitionExternalReferenceCode2,
	setAggregationFilters,
	setCreationLanguageId2,
	setValues,
	values,
	workflowStatusJSONArray,
}: AggregationFilterProps): JSX.Element;
export {};
