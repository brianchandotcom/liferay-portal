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
import CodeMirror from 'codemirror';
import React from 'react';
import './CodeMirrorEditor.scss';
export default function CodeMirrorEditor({
	className,
	editorRef,
	error,
	fixed,
	onChange,
	options,
	placeholder,
}: IProps): JSX.Element;
interface IProps {
	className?: string;
	editorRef?: React.MutableRefObject<CodeMirror.Editor | undefined>;
	error?: string;
	fixed?: boolean;
	onChange: (value?: string) => void;
	options?: CodeMirror.EditorConfiguration;
	placeholder?: string;
}
export {};
