/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

/// <reference types="react" />

import './PredefinedValuesTable.scss';
export default function PredefinedValuesTable({
	creationLanguageId,
	currentObjectDefinitionFields,
	disableRequiredChecked,
	errors,
	objectFieldsMap,
	setValues,
	title,
	validateExpressionURL,
	values,
}: IProps): JSX.Element;
interface IProps {
	creationLanguageId: Liferay.Language.Locale;
	currentObjectDefinitionFields: ObjectField[];
	disableRequiredChecked?: boolean;
	errors: {
		[key: string]: string;
	};
	objectFieldsMap: Map<string, ObjectField>;
	predefinedValues?: PredefinedValue[];
	setValues: (params: Partial<ObjectAction>) => void;
	title?: string;
	validateExpressionURL: string;
	values: Partial<ObjectAction>;
}
export {};
