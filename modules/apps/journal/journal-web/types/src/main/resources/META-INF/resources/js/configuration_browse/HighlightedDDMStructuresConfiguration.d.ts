/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

/// <reference types="react" />

interface DDMStructure {
	ddmStructureId: string;
	name: string;
}
interface Props {
	ddmStructures?: DDMStructure[];
	portletNamespace: string;
	selectDDMStructureURL: string;
}
export default function HighlightedDDMStructuresConfiguration({
	ddmStructures: initialDDMStructures,
	portletNamespace,
	selectDDMStructureURL,
}: Props): JSX.Element;
export {};
