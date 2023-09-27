/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import classNames from 'classnames';
import CodeMirror from 'codemirror';
import {FieldBase} from 'frontend-js-components-web';
import React, {ReactNode, useRef} from 'react';

import CodeMirrorEditor, {ICodeMirrorEditor} from './CodeMirrorEditor';
import {Sidebar, SidebarCategory} from './Sidebar';

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

const CodeEditor = React.forwardRef<CodeMirror.Editor, CodeEditorProps>(
	(
		{
			CustomSidebarContent,
			className,
			error,
			mode,
			sidebarElements,
			...options
		},
		ref
	) => {
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
			<FieldBase
				className={classNames('lfr-objects__code-editor', className)}
				errorMessage={error}
			>
				<div className="form-control lfr-objects__code-editor-source">
					<CodeMirrorEditor
						lineWrapping={true}
						mode={mode}
						ref={handleDomNodeChange}
						{...options}
					/>

					{sidebarElements && (
						<Sidebar
							CustomSidebarContent={CustomSidebarContent}
							editorRef={editorRef}
							elements={sidebarElements}
						/>
					)}
				</div>
			</FieldBase>
		);
	}
);

export default CodeEditor;
