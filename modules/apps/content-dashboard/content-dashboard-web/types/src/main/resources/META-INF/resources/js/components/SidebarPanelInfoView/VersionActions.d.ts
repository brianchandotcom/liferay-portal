/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

/// <reference types="react" />

declare const VersionActions: ({actions}: IProps) => JSX.Element;
interface IProps {
	actions: IAction[];
}
export interface IAction {
	icon?: string;
	label: string;
	name: string;
	type: string;
	url: string;
}
declare global {
	interface Window {
		submitForm: (form: HTMLElement, url: string) => void;
	}
	interface IDocument extends Document {
		hrefFm: HTMLElement;
	}
}
export default VersionActions;
