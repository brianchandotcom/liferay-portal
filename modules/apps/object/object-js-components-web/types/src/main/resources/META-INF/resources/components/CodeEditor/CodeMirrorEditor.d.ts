/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import 'codemirror/addon/display/autorefresh';
import 'codemirror/addon/fold/foldgutter';
import 'codemirror/addon/fold/foldgutter.css';
import 'codemirror/addon/display/placeholder';
import 'codemirror/lib/codemirror.css';
import CodeMirror from 'codemirror';
import React from 'react';
import './CodeMirrorEditor.scss';
declare const _default: React.MemoExoticComponent<React.ForwardRefExoticComponent<
	ICodeMirrorEditor & React.RefAttributes<CodeMirror.Editor>
>>;
export default _default;
export interface ICodeMirrorEditor extends CodeMirror.EditorConfiguration {
	onChange: (value?: string, lineCount?: number) => void;
}
