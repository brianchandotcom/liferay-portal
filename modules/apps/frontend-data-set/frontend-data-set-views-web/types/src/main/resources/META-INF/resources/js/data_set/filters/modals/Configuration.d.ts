/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React from 'react';
import {IField, IFilter} from '../../../utils/types';
interface IConfigurationProps {
	fieldInUseValidationError: boolean;
	fieldNames?: string[];
	fieldValidationError: boolean;
	fields: IField[];
	filter?: IFilter;
	labelValidationError?: boolean;
	namespace: string;
	onBlur: (event: React.FocusEvent<HTMLInputElement>) => void;
	onChangeField: (selectedField: IField | undefined) => void;
	onChangeLabel: (
		i18nFilterLabels: Partial<Liferay.Language.FullyLocalizedValue<string>>
	) => void;
}
declare function Configuration({
	fieldInUseValidationError,
	fieldNames,
	fieldValidationError,
	fields,
	filter,
	labelValidationError,
	namespace,
	onBlur,
	onChangeField,
	onChangeLabel,
}: IConfigurationProps): JSX.Element;
export default Configuration;
