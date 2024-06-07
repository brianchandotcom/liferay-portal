/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {CodeMirror} from '@liferay/frontend-js-codemirror-web';
import classNames from 'classnames';
import React, {useEffect, useRef} from 'react';

import './CodeMirrorEditor.scss';

const CodeMirrorEditor = React.forwardRef<CodeMirror.Editor, ICodeMirrorEditor>(
	({mode, onChange, readOnly, ...options}, ref) => {
		const editorWrapperRef = useRef<HTMLDivElement>(null);
		const codeMirrorRef = useRef<CodeMirror.Editor>();

		useEffect(() => {
			const editor = CodeMirror(
				editorWrapperRef.current as HTMLDivElement,
				{
					autoRefresh: true,
					foldGutter: true,
					gutters: [
						'CodeMirror-linenumbers',
						'CodeMirror-foldgutter',
					],
					inputStyle: 'contenteditable',
					lineNumbers: true,
					mode: mode ?? 'freemarker',
					readOnly: readOnly && 'nocursor',
					...options,
				}
			);

			codeMirrorRef.current = editor;

			if (ref instanceof Function) {
				ref(editor);
			}
			else if (ref) {
				(ref as React.MutableRefObject<CodeMirror.Editor>).current =
					editor;
			}

			const handleChange = (editor: CodeMirror.Editor) => {
				onChange(editor.getValue(), editor.lineCount());
			};

			editor.on('change', handleChange);

			return () => editor.off('change', handleChange);

			// eslint-disable-next-line react-hooks/exhaustive-deps
		}, []);

		return (
			<div
				className={classNames('lfr-objects__editor', {
					'lfr-objects__editor--disabled': readOnly,
				})}
				ref={editorWrapperRef}
			/>
		);
	}
);

export default React.memo(CodeMirrorEditor);

export interface ICodeMirrorEditor extends CodeMirror.EditorConfiguration {
	onChange: (value?: string, lineCount?: number) => void;
}
