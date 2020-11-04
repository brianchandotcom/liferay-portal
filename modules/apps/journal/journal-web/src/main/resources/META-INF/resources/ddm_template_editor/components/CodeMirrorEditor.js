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

import 'codemirror/addon/display/autorefresh';

import 'codemirror/addon/edit/closebrackets';

import 'codemirror/addon/edit/closetag';

import 'codemirror/addon/edit/matchbrackets';

import 'codemirror/addon/fold/brace-fold';

import 'codemirror/addon/fold/comment-fold';

import 'codemirror/addon/fold/foldcode';

import 'codemirror/addon/fold/foldgutter.css';

import 'codemirror/addon/fold/foldgutter';

import 'codemirror/addon/fold/indent-fold';

import 'codemirror/addon/fold/xml-fold';

import 'codemirror/addon/hint/show-hint.css';

import 'codemirror/addon/hint/show-hint';

import 'codemirror/addon/hint/xml-hint';

import 'codemirror/lib/codemirror.css';

import 'codemirror/mode/velocity/velocity';

import 'codemirror/mode/xml/xml';
import CodeMirror from 'codemirror';
import PropTypes from 'prop-types';
import React, {useEffect, useRef, useState} from 'react';

export const CodeMirrorEditor = ({content, mode, onChange}) => {
	const [editor, setEditor] = useState();
	const [editorWrapper, setEditorWrapper] = useState();
	const initialContentRef = useRef(content);

	useEffect(() => {
		if (!editorWrapper) {
			return;
		}

		const codeMirror = CodeMirror(editorWrapper, {
			autoCloseTags: true,
			autoRefresh: true,
			extraKeys: {
				'Ctrl-Space': 'autocomplete',
			},
			foldGutter: true,
			gutters: ['CodeMirror-linenumbers', 'CodeMirror-foldgutter'],
			hintOptions: {
				completeSingle: false,
			},
			indentWithTabs: true,
			inputStyle: 'contenteditable',
			lineNumbers: true,
			matchBrackets: true,
			mode: {
				globalVars: true,
				name: mode,
			},
			showHint: true,
			tabSize: 2,
			value: initialContentRef.current,
			viewportMargin: Infinity,
		});

		setEditor(codeMirror);
	}, [editorWrapper, mode]);

	useEffect(() => {
		if (!editor) {
			return;
		}

		const handleChange = () => {
			onChange(editor.getValue());
		};

		editor.on('change', handleChange);

		return () => {
			editor.off('change', handleChange);
		};
	}, [editor, onChange]);

	useEffect(() => {
		if (editor && editor.getValue() !== content) {
			editor.setValue(content);
		}
	}, [content, editor]);

	return (
		<div
			className="ddm_template_editor__CodeMirrorEditor"
			ref={setEditorWrapper}
		/>
	);
};

CodeMirrorEditor.propTypes = {
	content: PropTypes.string.isRequired,
	mode: PropTypes.oneOf(['ftl', 'xml', 'velocity']),
	onChange: PropTypes.func.isRequired,
};
