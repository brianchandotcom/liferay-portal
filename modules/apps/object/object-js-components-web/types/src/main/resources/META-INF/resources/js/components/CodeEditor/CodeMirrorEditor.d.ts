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
declare const _default: React.MemoExoticComponent<React.ForwardRefExoticComponent<
	ICodeMirrorEditor & React.RefAttributes<CodeMirror.Editor>
>>;
export default _default;
export interface ICodeMirrorEditor extends CodeMirror.EditorConfiguration {
	className?: string;
	fixed?: boolean;
	onChange: (value?: string) => void;
}
