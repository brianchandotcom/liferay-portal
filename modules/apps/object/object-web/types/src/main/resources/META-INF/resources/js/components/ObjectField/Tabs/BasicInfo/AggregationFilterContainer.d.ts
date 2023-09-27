/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
