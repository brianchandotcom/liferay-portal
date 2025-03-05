/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import getTextFieldComponents from '../components/TextFieldComponents';
import {Field, FieldType} from './field';

type FieldComponents = {
	FirstSectionComponent: React.FC<{field: Field}>;
	SecondSectionComponent: React.FC<{field: Field}>;
};

const GETTERS: Record<FieldType, () => Partial<FieldComponents>> = {
	'boolean': () => ({}),
	'date': () => ({}),
	'datetime': () => ({}),
	'decimal': () => ({}),
	'integer': () => ({}),
	'long-text': () => ({}),
	'multiselect': () => ({}),
	'rich-text': () => ({}),
	'single-select': () => ({}),
	'text': getTextFieldComponents,
	'upload': () => ({}),
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
