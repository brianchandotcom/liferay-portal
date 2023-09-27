/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Editor} from 'frontend-editor-ckeditor-web';
import React, {useRef} from 'react';

import {editorConfig} from '../../../../../constants';

const BaseSourceCode = ({scriptSourceCode, updateSelectedItem}) => {
	const editorRef = useRef();

	return (
		<Editor
			config={editorConfig}
			onInstanceReady={({editor}) => {
				editor.setMode('source');

				if (scriptSourceCode) {
					editor.setData(scriptSourceCode[0]);
				}

				document
					.querySelector('div.sidebar-body')
					.addEventListener('keyup', () => {
						updateSelectedItem(editor);
					});

				return () => {
					document
						.querySelector('div.sidebar-body')
						.removeEventListener(
							'keyup',
							updateSelectedItem(editor)
						);
				};
			}}
			ref={editorRef}
		/>
	);
};

export default BaseSourceCode;
