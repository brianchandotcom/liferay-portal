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

import {
	EditorSideBarCategory,
	EditorSidebar,
	FieldBase,
} from '@liferay/object-js-components-web';
import classNames from 'classnames';
import React, {useRef} from 'react';

import CodeMirrorEditor, {ICodeMirrorEditor} from './CodeMirrorEditor';

import './index.scss';

export default function CodeEditor({
	className,
	error,
	sidebarElements,
	...options
}: IProps) {
	const editorRef = useRef<CodeMirror.Editor>();

	return (
		<div className={classNames('lfr-objects__code-editor', className)}>
			<FieldBase
				className="lfr-objects__code-editor-source"
				errorMessage={error}
			>
				<CodeMirrorEditor
					editorRef={editorRef}
					lineWrapping={true}
					{...options}
				/>
			</FieldBase>

			{sidebarElements && (
				<EditorSidebar
					editorRef={editorRef}
					elements={sidebarElements}
				/>
			)}
		</div>
	);
}

interface IProps extends ICodeMirrorEditor {
	error?: string;
	sidebarElements?: EditorSideBarCategory[];
}
