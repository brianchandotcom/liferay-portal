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

import CKEditor from 'ckeditor4-react';
import PropTypes from 'prop-types';
import React, {forwardRef, useCallback, useEffect, useRef} from 'react';

const BASEPATH = '/o/frontend-editor-ckeditor-web/ckeditor/';
const CONTEXT_URL = Liferay.ThemeDisplay.getPathContext();
const CURRENT_PATH = CONTEXT_URL ? CONTEXT_URL + BASEPATH : BASEPATH;

/**
 * This component contains shared code between
 * DXP implementations of CKEditor. Please don't import it directly.
 */
const BaseEditor = forwardRef(
	({contents, name, onChange, onChangeMethodName, ...props}, ref) => {
		const editorRef = useRef();

		useEffect(() => {
			Liferay.once('beforeScreenFlip', () => {
				if (
					window.CKEDITOR &&
					!Object.keys(window.CKEDITOR.instances).length
				) {
					delete window.CKEDITOR;
				}
			});
		}, []);

		const getHTML = useCallback(() => {
			let data = contents;

			const editor = editorRef.current.editor;

			if (editor && editor.instanceReady) {
				data = editor.getData();

				if (
					CKEDITOR.env.gecko &&
					CKEDITOR.tools.trim(data) === '<br />'
				) {
					data = '';
				}

				data = data.replace(/(\u200B){7}/, '');
			}

			return data;
		}, [contents]);

		const onChangeCallback = () => {
			if (!onChangeMethodName && !onChange) {
				return;
			}

			if (onChangeMethodName) {
				window[onChangeMethodName](getHTML());
			}
			else {
				onChange(getHTML());
			}
		};

		const editorRefsCallback = useCallback(
			(element) => {
				if (ref) {
					ref.current = element;
				}
				editorRef.current = element;
			},
			[ref, editorRef]
		);

		useEffect(() => {
			window[name] = {
				getHTML,
				getText() {
					return contents;
				},
			};
		}, [contents, getHTML, name]);

		return (
			<CKEditor
				name={name}
				onChange={onChangeCallback}
				onChangeMethodName={onChangeMethodName}
				ref={editorRefsCallback}
				{...props}
			/>
		);
	}
);

CKEditor.editorUrl = `${CURRENT_PATH}ckeditor.js`;
window.CKEDITOR_BASEPATH = CURRENT_PATH;

BaseEditor.displayName = 'BaseEditor';

BaseEditor.propTypes = {
	contents: PropTypes.string,
	name: PropTypes.string.isRequired,
	onChange: PropTypes.func,
	onChangeMethodName: PropTypes.string,
};

export default BaseEditor;
