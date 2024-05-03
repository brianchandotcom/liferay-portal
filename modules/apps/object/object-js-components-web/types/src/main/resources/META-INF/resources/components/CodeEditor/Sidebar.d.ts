/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {CodeMirror} from '@liferay/frontend-js-codemirror-web';
import {ReactNode, RefObject} from 'react';
import './Sidebar.scss';
interface SidebarElement {
	content: string;
	helpText?: string;
	label: string;
	tooltip?: string;
}
export interface SidebarCategory {
	items: SidebarElement[];
	label: string;
}
interface SidebarProps {
	CustomSidebarContent?: ReactNode;
	disabled?: boolean;
	editorRef: RefObject<CodeMirror.Editor>;
	elements: SidebarCategory[];
	elementsDisabled?: boolean;
	otherProps?: unknown;
}
export declare function Sidebar({
	CustomSidebarContent,
	disabled,
	editorRef,
	elements,
	elementsDisabled,
}: SidebarProps): JSX.Element;
export {};
