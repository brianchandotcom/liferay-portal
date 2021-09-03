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

import PropTypes from 'prop-types';
import React, {useCallback, useEffect, useRef} from 'react';

import {Editor} from './Editor';

const ClassicEditor = React.forwardRef(
	(
		{
			contents = '',
			editorConfig,
			initialToolbarSet = 'simple',
			name,
			onChange,
			onChangeMethodName,
			title,
			...otherProps
		},
		ref
	) => {
		const editorRef = useRef();

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

			const editor = editorRef.current.editor;

			if (editor.checkDirty()) {
				if (onChangeMethodName) {
					window[onChangeMethodName](getHTML());
				}
				else {
					onChange(getHTML());
				}

				editor.resetDirty();
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
			<div id={`${name}Container`}>
				{title && (
					<label className="control-label" htmlFor={name}>
						{title}
					</label>
				)}
				<Editor
					className="lfr-editable"
					config={{
						toolbar: initialToolbarSet,
						...editorConfig,
					}}
					data={contents}
					name={name}
					onBeforeLoad={(CKEDITOR) => {
						CKEDITOR.disableAutoInline = true;
						CKEDITOR.dtd.$removeEmpty.i = 0;
						CKEDITOR.dtd.$removeEmpty.span = 0;

						CKEDITOR.getNextZIndex = function () {
							return CKEDITOR.dialog._.currentZIndex
								? CKEDITOR.dialog._.currentZIndex + 10
								: Liferay.zIndex.WINDOW + 10;
						};
					}}
					onChange={onChangeCallback}
					onDrop={(event) => {
						const data = event.data.dataTransfer.getData(
							'text/html'
						);
						const editor = event.editor;

						if (data) {
							const fragment = CKEDITOR.htmlParser.fragment.fromHtml(
								data
							);

							const name = fragment.children[0].name;

							if (name) {
								return editor.pasteFilter.check(name);
							}
						}
					}}
					ref={editorRefsCallback}
					{...otherProps}
				/>
			</div>
		);
	}
);

ClassicEditor.propTypes = {
	contents: PropTypes.string,
	editorConfig: PropTypes.object,
	initialToolbarSet: PropTypes.string,
	name: PropTypes.string,
	onChange: PropTypes.func,
	onChangeMethodName: PropTypes.string,
	title: PropTypes.string,
};

export {ClassicEditor};
export default ClassicEditor;
