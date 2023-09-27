/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

/// <reference types="react" />

interface ObjectFolderCardHeaderProps {
	externalReferenceCode?: string;
	items: IItem[];
	label?: LocalizedValue<string>;
	modelBuilderURL: string;
	name?: string;
}
export default function ObjectFolderCardHeader({
	externalReferenceCode,
	items,
	label,
	modelBuilderURL,
	name,
}: ObjectFolderCardHeaderProps): JSX.Element;
export {};
