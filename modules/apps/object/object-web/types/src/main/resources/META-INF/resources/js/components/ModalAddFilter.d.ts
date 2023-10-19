/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

/// <reference types="react" />

import {Observer} from '@clayui/modal/lib/types';
import {MultiSelectItem} from '@liferay/object-js-components-web';
import './ModalAddFilter.scss';
interface ModalAddFilterProps {
	aggregationFilter?: boolean;
	creationLanguageId?: Liferay.Language.Locale;
	currentFilters: CurrentFilter[];
	disableAutoClose?: boolean;
	disableDateValues?: boolean;
	editingFilter: boolean;
	editingObjectFieldName: string;
	filterOperators: TFilterOperators;
	filterTypeRequired?: boolean;
	header: string;
	objectFields: ObjectField[];
	observer: Observer;
	onClose: () => void;
	onSave: (
		objectFieldName: string,
		filterBy?: string,
		fieldLabel?: LocalizedValue<string>,
		objectFieldBusinessType?: string,
		filterType?: string,
		valueList?: MultiSelectItem[],
		value?: string
	) => void;
	validate: ({
		checkedItems,
		disableDateValues,
		items,
		selectedFilterBy,
		selectedFilterTypeValue,
		setErrors,
		value,
	}: FilterValidation) => FilterErrors;
	workflowStatusJSONArray: LabelValueObject[];
}
export declare type FilterErrors = {
	endDate?: string;
	items?: string;
	selectedFilterBy?: string;
	selectedFilterType?: string;
	startDate?: string;
	value?: string;
};
export declare type FilterValidation = {
	checkedItems: MultiSelectItem[];
	disableDateValues?: boolean;
	items: MultiSelectItem[];
	selectedFilterBy?: ObjectField;
	selectedFilterTypeValue?: string;
	setErrors: (value: FilterErrors) => void;
	value?: string;
};
declare type CurrentFilter = {
	definition: {
		[key: string]: string[] | number[];
	} | null;
	fieldLabel?: string;
	filterBy?: string;
	filterType: string | null;
	label: LocalizedValue<string>;
	objectFieldBusinessType?: string;
	objectFieldName?: string;
	value?: string;
	valueList?: LabelValueObject[];
};
export declare function ModalAddFilter({
	aggregationFilter,
	creationLanguageId,
	currentFilters,
	disableAutoClose,
	disableDateValues,
	editingFilter,
	editingObjectFieldName,
	filterOperators,
	filterTypeRequired,
	header,
	objectFields,
	observer,
	onClose,
	onSave,
	validate,
	workflowStatusJSONArray,
}: ModalAddFilterProps): JSX.Element;
export {};
