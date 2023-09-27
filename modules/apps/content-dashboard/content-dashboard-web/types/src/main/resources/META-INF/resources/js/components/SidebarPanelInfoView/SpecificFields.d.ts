/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React from 'react';
declare const SpecificFields: ({
	fields,
	languageTag,
}: IProps) => (string | JSX.Element)[] | null;
declare type SpecificItemTypes = 'Date' | 'String' | 'URL';
interface SpecificField {
	help?: string;
	title: string;
	type: SpecificItemTypes;
	value: string;
}
interface IProps {
	children?: React.ReactNode;
	fields: SpecificField[];
	languageTag: string;
}
export default SpecificFields;
