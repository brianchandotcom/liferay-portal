/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

/// <reference types="react" />

export default function GlobalJSCETsConfiguration({
	globalJSCETSelectorURL,
	globalJSCETs: initialGlobalJSCETs,
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
	portletNamespace: string;
	selectGlobalJSCETsEventName: string;
}
export {};
