/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ElementType} from 'react';
import '../../EditObjectFieldContent.scss';
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
	containerWrapper: ElementType;
	creationLanguageId2?: Liferay.Language.Locale;
	filterOperators: TFilterOperators;
	modelBuilder: boolean;
	objectDefinitionExternalReferenceCode2?: string;
	onSubmit?: (editedObjectField?: Partial<ObjectField>) => void;
	setAggregationFilters: (values: AggregationFilters[]) => void;
	setCreationLanguageId2: (values: Liferay.Language.Locale) => void;
	setValues: (values: Partial<ObjectField>) => void;
	values: Partial<ObjectField>;
	workflowStatuses: LabelValueObject[];
}
export declare function AggregationFilterContainer({
	aggregationFilters,
	containerWrapper: ContainerWrapper,
	creationLanguageId2,
	filterOperators,
	modelBuilder,
	objectDefinitionExternalReferenceCode2,
	onSubmit,
	setAggregationFilters,
	setCreationLanguageId2,
	setValues,
	values,
	workflowStatuses,
}: AggregationFilterProps): JSX.Element;
export {};
