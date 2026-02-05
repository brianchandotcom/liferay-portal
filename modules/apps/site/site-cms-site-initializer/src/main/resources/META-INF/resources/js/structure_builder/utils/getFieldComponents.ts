/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import getDateTimeFieldComponents from '../components/field_components/DateTimeFieldComponents';
import getLongTextFieldComponents from '../components/field_components/LongTextFieldComponents';
import getNumericFieldComponents from '../components/field_components/NumericFieldComponents';
import getSelectFromListFieldComponents from '../components/field_components/SelectFromListFieldComponents';
import getTextFieldComponents from '../components/field_components/TextFieldComponents';
import getUploadFieldComponents from '../components/field_components/UploadFieldComponents';
import {Field, FieldType} from './field';

type FieldComponents = {
	FirstSectionComponent: React.FC<{disabled?: boolean; field: Field}>;
	SecondSectionComponent: React.FC<{disabled?: boolean; field: Field}>;
};

const GETTERS: Record<FieldType, () => Partial<FieldComponents>> = {
	'boolean': () => ({}),
	'date': () => ({}),
	'datetime': getDateTimeFieldComponents,
	'decimal': () => ({}),
	'integer': getNumericFieldComponents,
	'long-text': getLongTextFieldComponents,
	'rich-text': () => ({}),
	'select-from-list': getSelectFromListFieldComponents,
	'text': getTextFieldComponents,
	'upload': getUploadFieldComponents,
};

export default function getFieldComponents(
	fieldType: FieldType
): FieldComponents {
	const getter = GETTERS[fieldType];

	const {FirstSectionComponent, SecondSectionComponent} = getter?.() ?? {};

	return {
		FirstSectionComponent: FirstSectionComponent ?? (() => null),
		SecondSectionComponent: SecondSectionComponent ?? (() => null),
	};
}
