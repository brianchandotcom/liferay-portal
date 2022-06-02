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

import classNames from 'classnames';
import CodeMirror from 'codemirror';
import React, {useRef} from 'react';

import {FieldBase} from '../FieldBase';
import CodeMirrorEditor, {ICodeMirrorEditor} from './CodeMirrorEditor';
import {SideBarCategory, Sidebar} from './SideBar';

import './index.scss';

export {default as CodeMirrorEditor} from './CodeMirrorEditor';
export {SideBarCategory} from './SideBar';

const CodeEditor = React.forwardRef<CodeMirror.Editor, IProps>(
	({className, error, sidebarElements, ...options}, ref) => {
		const editorRef = useRef<CodeMirror.Editor>(
			null
		) as React.MutableRefObject<CodeMirror.Editor>;

		const handleDomNodeChange = (editor: CodeMirror.Editor) => {
			editorRef.current = editor;

			if (!ref) {
				return;
			}
			else if (ref instanceof Function) {
				ref(editor);
			}
			else {
				(ref as React.MutableRefObject<
					CodeMirror.Editor
				>).current = editor;
			}
		};

		return (
			<div className={classNames('lfr-objects__code-editor', className)}>
				<FieldBase
					className="lfr-objects__code-editor-source"
					errorMessage={error}
				>
					<CodeMirrorEditor
						lineWrapping={true}
						ref={handleDomNodeChange}
						{...options}
					/>
				</FieldBase>

				{sidebarElements && (
					<Sidebar editorRef={editorRef} elements={sidebarElements} />
				)}
			</div>
		);
	}
);

export default CodeEditor;

interface IProps extends ICodeMirrorEditor {
	error?: string;
	sidebarElements?: SideBarCategory[];
}
