/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

/// <reference types="react" />

interface IProps {
	error?: string;
	objectDefinitionExternalReferenceCode: string;
	onChange: (objectFieldName: string) => void;
	value?: string;
}
export default function SelectRelationship({
	error,
	objectDefinitionExternalReferenceCode,
	onChange,
	value,
	...otherProps
}: IProps): JSX.Element;
export {};
