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

import 'codemirror/addon/fold/foldgutter';

import 'codemirror/addon/fold/foldgutter.css';

import 'codemirror/addon/display/placeholder';

import 'codemirror/lib/codemirror.css';
import classNames from 'classnames';
import CodeMirror from 'codemirror';
import React, {useEffect, useRef} from 'react';

import './CodeMirrorEditor.scss';

export function CodeMirrorEditor({
	className,
	editorRef,
	fixed,
	onChange,
	...options
}: ICodeMirrorEditor) {
	const editorWrapperRef = useRef<HTMLDivElement>(null);
	const codeMirrorRef = useRef<CodeMirror.Editor>();

	useEffect(() => {
		const editor = CodeMirror(editorWrapperRef.current as HTMLDivElement, {
			autoRefresh: true,
			foldGutter: true,
			gutters: ['CodeMirror-linenumbers', 'CodeMirror-foldgutter'],
			inputStyle: 'contenteditable',
			lineNumbers: true,
			...options,
		});

		codeMirrorRef.current = editor;

		if (editorRef) {
			editorRef.current = editor;
		}

		const handleChange = (editor: CodeMirror.Editor) =>
			onChange(editor.getValue());

		editor.on('change', handleChange);

		return () => editor.off('change', handleChange);
		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, []);

	return (
		<div
			className={classNames(
				'form-control lfr-objects__editor',
				{'lfr-objects__editor--fixed': fixed},
				className
			)}
			ref={editorWrapperRef}
		/>
	);
}

export interface ICodeMirrorEditor extends CodeMirror.EditorConfiguration {
	className?: string;
	editorRef?: React.MutableRefObject<CodeMirror.Editor | undefined>;
	fixed?: boolean;
	onChange: (value?: string) => void;
}
