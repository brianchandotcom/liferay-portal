/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import CodeMirror from 'codemirror';
import React, {ReactNode} from 'react';
import {ICodeMirrorEditor} from './CodeMirrorEditor';
import {SidebarCategory} from './Sidebar';
import './index.scss';
export {default as CodeMirrorEditor} from './CodeMirrorEditor';
export {Collapsible} from './Collapsible';
export {Element} from './Element';
export {SidebarCategory} from './Sidebar';
interface CodeEditorProps extends ICodeMirrorEditor {
	CustomSidebarContent?: ReactNode;
	className?: string;
	error?: string;
	sidebarElements?: SidebarCategory[];
}
declare const CodeEditor: React.ForwardRefExoticComponent<
	CodeEditorProps & React.RefAttributes<CodeMirror.Editor>
>;
export default CodeEditor;
