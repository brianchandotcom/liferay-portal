/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React from 'react';
export default function ObjectRelationship({
	apiURL,
	fieldName,
	inputName,
	labelKey,
	name,
	objectEntryId,
	objectFieldBusinessType,
	onBlur,
	onChange,
	onFocus,
	parameterObjectFieldName,
	placeholder,
	readOnly,
	required,
	value,
	valueKey,
	...otherProps
}: IProps): JSX.Element;
interface IProps {
	apiURL: string;
	fieldName: string;
	inputName: string;
	labelKey?: string;
	name: string;
	objectEntryId: string;
	objectFieldBusinessType: string;
	onBlur?: React.FocusEventHandler<HTMLInputElement>;
	onChange: (event: {
		target: {
			value: unknown;
		};
	}) => void;
	onFocus?: React.FocusEventHandler<HTMLInputElement>;
	parameterObjectFieldName?: string;
	placeholder?: string;
	readOnly?: boolean;
	required?: boolean;
	value?: string;
	valueKey?: string;
}
export {};
