/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import getTextFieldComponents from '../components/TextFieldComponents';
import {Field, FieldType} from './field';

type GetFieldComponents = {
	FirstSectionComponent: React.FC<{field: Field}>;
	SecondSectionComponent: React.FC<{field: Field}>;
};

export default function getFieldComponents(
	fieldType: FieldType
): GetFieldComponents {
	if (fieldType === 'text') {
		const {FirstSectionComponent, SecondSectionComponent} =
			getTextFieldComponents();

		return {
			FirstSectionComponent: FirstSectionComponent ?? (() => null),
			SecondSectionComponent: SecondSectionComponent ?? (() => null),
		};
	}

	return {
		FirstSectionComponent: () => null,
		SecondSectionComponent: () => null,
	};
}
