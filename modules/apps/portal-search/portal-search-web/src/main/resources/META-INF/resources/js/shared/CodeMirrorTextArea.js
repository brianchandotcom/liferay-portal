/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {CodeMirror} from '@liferay/frontend-js-codemirror-web';

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
