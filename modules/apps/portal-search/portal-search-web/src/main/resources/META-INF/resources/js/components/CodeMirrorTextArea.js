/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import 'codemirror/addon/edit/closebrackets';

import 'codemirror/addon/edit/closetag';

import 'codemirror/addon/edit/matchbrackets';

import 'codemirror/addon/fold/brace-fold';

import 'codemirror/addon/fold/comment-fold';

import 'codemirror/addon/fold/foldcode';

import 'codemirror/addon/fold/foldgutter.css';

import 'codemirror/addon/fold/foldgutter';

import 'codemirror/addon/fold/indent-fold';

import 'codemirror/lib/codemirror.css';

import 'codemirror/mode/javascript/javascript';
import CodeMirror from 'codemirror';

export default function CodeMirrorTextArea({id}) {
	CodeMirror.fromTextArea(document.getElementById(id), {
		foldGutter: true,
		gutters: ['CodeMirror-linenumbers', 'CodeMirror-foldgutter'],
		indentWithTabs: true,
		inputStyle: 'contenteditable',
		lineNumbers: true,
		matchBrackets: true,
		mode: {globalVars: true, name: 'application/json'},
		readOnly: true,
		tabSize: 2,
	});
}
