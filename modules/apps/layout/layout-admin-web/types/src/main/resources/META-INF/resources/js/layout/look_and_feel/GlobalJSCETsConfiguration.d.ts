/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

/// <reference types="react" />

export default function GlobalJSCETsConfiguration({
	globalJSCETSelectorURL,
	globalJSCETs: initialGlobalJSCETs,
	isReadOnly,
	portletNamespace,
	selectGlobalJSCETsEventName,
}: IProps): JSX.Element;
declare type ILoadTypeOptions = 'default' | 'async' | 'defer';
declare type IScriptLocationOptions = 'head' | 'bottom';
interface IGlobalJSCET {
	cetExternalReferenceCode: string;
	inherited: boolean;
	inheritedLabel: string;
	loadType?: ILoadTypeOptions;
	name: string;
	scriptLocation?: IScriptLocationOptions;
}
interface IProps {
	globalJSCETSelectorURL: string;
	globalJSCETs: IGlobalJSCET[];
	isReadOnly: boolean;
	portletNamespace: string;
	selectGlobalJSCETsEventName: string;
}
export {};
