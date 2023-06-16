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

import {Field, FieldType} from './utils/dataConverter';

export {FieldFeedback} from './core/components/FieldFeedback';
export {EVENT_TYPES as FORM_EVENT_TYPES} from './custom/form/eventTypes';
export {
	getDDMFormFieldSettingsContext,
	FieldType,
	FieldTypeName,
} from './utils/dataConverter';

export function convertToFormData(body: unknown): unknown;

export function makeFetch({
	body,
	headers,
	method,
	url,
	...otherProps
}: {
	body: unknown;
	headers?:
		| {
				Accept: string;
		  }
		| undefined;
	method?: string | undefined;
	url: unknown;
	[x: string]: unknown;
}): unknown;

export function useConfig(): {
	dataEngineModule: string;
	displayChartAsTable: boolean;
	fieldTypes: FieldType[];
	formReportDataURL: string;
	portletNamespace: string;
};

export function useForm(): ({
	payload,
	type,
}: {
	payload?: unknown;
	type: string;
}) => void;

export function useFormState<T extends {[key: string]: unknown}>(): T;

export const FormReport: React.FC<{
	data?: string;
	dataEngineModule: string;
	displayChartAsTable: boolean;
	fields: unknown;
	formReportRecordsFieldValuesURL: string;
	portletNamespace: string;
}>;

export const FormView: React.FC;

export const PartialResults: React.FC<{
	dataEngineModule: string;
	displayChartAsTable: boolean;
	reportDataURL: string;
}>;

export class PagesVisitor {
	constructor(pages: unknown);
	mapFields: (
		mapper: (field: Field) => void,
		merge: boolean,
		includeNestedFields: boolean
	) => void;
}
