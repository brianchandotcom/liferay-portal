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
import DEFAULT_BALLOON_EDITOR_CONFIG from './config/DefaultBalloonEditorConfiguration';

import '../css/main.scss';

const EMPTY_OBJECT = {};

const BalloonEditor = React.forwardRef(
	(
		{
			config = EMPTY_OBJECT,
			contents,
			name,
			onChange,
			onChangeMethodName,
			...otherProps
		},
		ref
	) => {
		const editorConfig = {
			...DEFAULT_BALLOON_EDITOR_CONFIG,
			...config,
		};

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

		const editorRefsCallback = useCallback(
			(element) => {
				if (ref) {
					ref.current = element;
				}
				editorRef.current = element;
			},
			[ref, editorRef]
		);

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

		useEffect(() => {
			window[name] = {
				getHTML,
				getText() {
					return contents;
				},
			};
		}, [name, contents, getHTML]);

		return (
			<Editor
				config={editorConfig}
				data={contents}
				name={name}
				onBeforeLoad={(CKEDITOR) => {
					CKEDITOR.ADDITIONAL_RESOURCE_PARAMS = {
						languageId: themeDisplay.getLanguageId(),
					};

					CKEDITOR.disableAutoInline = true;

					CKEDITOR.getNextZIndex = function () {
						return CKEDITOR.dialog._.currentZIndex
							? CKEDITOR.dialog._.currentZIndex + 10
							: Liferay.zIndex.WINDOW + 10;
					};
				}}
				onChange={onChangeCallback}
				onInstanceReady={(event) => {
					const editor = event.editor;

					const editable = editor.editable();

					// `floatPanel` plugin requires `id` to be `cke_${editor.name}`

					editable.setAttribute('id', `cke_${editor.name}`);

					editable.attachClass('liferay-editable');

					const balloonToolbars = editor.balloonToolbars;

					if (editorConfig.toolbarText) {
						balloonToolbars.create({
							buttons: editorConfig.toolbarText,
							cssSelector: '*',
						});
					}

					if (editorConfig.toolbarImage) {
						balloonToolbars.create({
							buttons: editorConfig.toolbarImage,
							priority:
								window.CKEDITOR.plugins.balloontoolbar.PRIORITY
									.HIGH,
							widgets: 'image,image2',
						});
					}

					if (editorConfig.toolbarTable) {
						balloonToolbars.create({
							buttons: editorConfig.toolbarTable,
							cssSelector: 'td',
							priority:
								window.CKEDITOR.plugins.balloontoolbar.PRIORITY
									.HIGH,
						});
					}

					if (editorConfig.toolbarVideo) {
						balloonToolbars.create({
							buttons: editorConfig.toolbarVideo,
							priority:
								window.CKEDITOR.plugins.balloontoolbar.PRIORITY
									.HIGH,
							widgets: 'videoembed',
						});
					}
				}}
				ref={editorRefsCallback}
				type="inline"
				{...otherProps}
			/>
		);
	}
);

BalloonEditor.propTypes = {
	config: PropTypes.object,
	contents: PropTypes.string,
	name: PropTypes.string,
	onChange: PropTypes.func,
	onChangeMethodName: PropTypes.string,
};

export default BalloonEditor;
