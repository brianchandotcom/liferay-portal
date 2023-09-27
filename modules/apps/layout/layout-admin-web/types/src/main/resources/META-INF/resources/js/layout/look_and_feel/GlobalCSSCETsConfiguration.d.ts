/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

/// <reference types="react" />

export default function GlobalCSSCETsConfiguration({
	globalCSSCETSelectorURL,
	globalCSSCETs: initialGlobalCSSCETs,
	isReadOnly,
	portletNamespace,
	selectGlobalCSSCETsEventName,
}: IProps): JSX.Element;
interface IGlobalCSSCET {
	cetExternalReferenceCode: string;
	inherited: boolean;
	inheritedLabel: string;
	name: string;
}
interface IProps {
	globalCSSCETSelectorURL: string;
	globalCSSCETs: IGlobalCSSCET[];
	isReadOnly: boolean;
	portletNamespace: string;
	selectGlobalCSSCETsEventName: string;
}
export {};
