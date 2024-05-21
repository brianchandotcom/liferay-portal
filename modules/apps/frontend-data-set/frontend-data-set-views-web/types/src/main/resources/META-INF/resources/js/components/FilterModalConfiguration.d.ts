/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

/// <reference types="react" />

import {IField, IFilter} from '../utils/types';
interface IFilterModalConfigurationProps {
	fieldNames?: string[];
	fields: IField[];
	filter?: IFilter;
	namespace: string;
	onChange: ({
		i18nFilterLabels,
		selectedField,
	}: {
		i18nFilterLabels: any;
		selectedField: IField | undefined;
	}) => void;
}
declare function FilterModalConfiguration({
	fieldNames,
	fields,
	filter,
	namespace,
	onChange,
}: IFilterModalConfigurationProps): JSX.Element;
export default FilterModalConfiguration;
