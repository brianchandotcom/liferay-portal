/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 */

import {Editor} from 'frontend-editor-ckeditor-web';
import React, {useContext, useRef} from 'react';

import {editorConfig} from '../../../../../constants';
import {DEFAULT_LANGUAGE} from '../../../../../source-builder/constants';
import {DiagramBuilderContext} from '../../../../DiagramBuilderContext';

const SourceCode = () => {
	const editorRef = useRef();
	const {selectedItem, setSelectedItem} = useContext(DiagramBuilderContext);

	const getEditorContent = (editor) => {
		setSelectedItem((previousValue) => ({
			...previousValue,
			data: {
				...previousValue.data,
				assignments: {
					assignmentType: ['scriptedAssignment'],
					script: [editor.getData()],
					scriptLanguage: [DEFAULT_LANGUAGE],
				},
			},
		}));
	};

	return (
		<Editor
			config={editorConfig}
			onInstanceReady={({editor}) => {
				editor.setMode('source');

				if (selectedItem.data?.assignments?.scriptedAssignment) {
					editor.setData(
						selectedItem.data.assignments.scriptedAssignment
					);
				}

				document
					.querySelector('div.sidebar-body')
					.addEventListener('keyup', () => {
						getEditorContent(editor);
					});

				return () => {
					document
						.querySelector('div.sidebar-body')
						.removeEventListener('keyup', getEditorContent(editor));
				};
			}}
			ref={editorRef}
		/>
	);
};

export default SourceCode;
