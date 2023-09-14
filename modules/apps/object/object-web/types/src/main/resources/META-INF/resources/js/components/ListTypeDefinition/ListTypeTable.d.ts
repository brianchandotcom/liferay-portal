/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

/// <reference types="react" />

interface IProps {
	pickListId: number;
	readOnly: boolean;
	setValues: (values: Partial<ListTypeDefinition>) => void;
	values: Partial<ListTypeDefinition>;
}
export default function ListTypeTable({
	pickListId,
	readOnly,
	setValues,
	values,
}: IProps): JSX.Element | null;
export {};
