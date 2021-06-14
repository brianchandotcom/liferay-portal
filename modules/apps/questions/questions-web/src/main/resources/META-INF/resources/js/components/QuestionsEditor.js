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

import {useEventListener} from '@liferay/frontend-js-react-web';
import {Editor} from 'frontend-editor-ckeditor-web';
import {isPhone, isTablet} from 'frontend-js-web';
import React, {useContext, useEffect, useMemo, useRef, useState} from 'react';

import {AppContext} from '../AppContext.es';

const getToolbarSet = (toolbarSet) => {
	if (isPhone()) {
		toolbarSet = 'phone';
	}
	else if (isTablet()) {
		toolbarSet = 'tablet';
	}

	return toolbarSet;
};

export function getCKEditorConfig() {
	const config = {
		allowedContent: true,
		codeSnippet_languages: {
			html: 'HTML',
			java: 'Java',
			javascript: 'JavaScript',
		},
		codeSnippet_theme: 'monokai_sublime',
		extraPlugins: 'codesnippet,itemselector',
		height: 216,
		removePlugins: 'elementspath',
		tabSpaces: 4,
	};

	config.toolbar = [
		['Bold', 'Italic', 'Underline', 'Strike'],
		['NumberedList', 'BulletedList'],
		['Outdent', 'Indent'],
		['Blockquote'],
		['CodeSnippet', 'ImageSelector'],
		['Link', 'Unlink'],
		['Undo', 'Redo'],
		['Source'],
	];

	return config;
}

const QuestionsEditor = ({
	contents = '',
	cssClass,
	editorConfig = {},
	initialToolbarSet,
	...props
}) => {
	const editorRef = useRef();

	const context = useContext(AppContext);

	const [toolbarSet, setToolbarSet] = useState(initialToolbarSet);

	const config = useMemo(() => {
		const CKEditorConfig = getCKEditorConfig();
		if (editorConfig.readOnly) {
			CKEditorConfig.toolbar.pop('Source');
		}

		return {
			toolbar: toolbarSet,
			...CKEditorConfig,
			...editorConfig,
		};
	}, [editorConfig, toolbarSet]);

	useEffect(() => {
		setToolbarSet(getToolbarSet(initialToolbarSet));
	}, [initialToolbarSet]);

	useEventListener(
		'resize',
		() => setToolbarSet(getToolbarSet(initialToolbarSet)),
		true,
		window
	);

	const insertTextAtCursor = (element, text) => {
		const val = element.value;
		let endIndex;
		let range;
		if (
			typeof element.selectionStart != 'undefined' &&
			typeof element.selectionEnd != 'undefined'
		) {
			endIndex = element.selectionEnd;
			element.value =
				val.slice(0, element.selectionStart) +
				text +
				val.slice(endIndex);
			element.selectionStart = element.selectionEnd =
				endIndex + text.length;
		}
		else if (
			typeof document.selection != 'undefined' &&
			typeof document.selection.createRange != 'undefined'
		) {
			element.focus();
			range = document.selection.createRange();
			range.collapse(false);
			range.text = text;
			range.select();
		}
	};

	return (
		<div className={cssClass} id={`${name}Container`}>
			<Editor
				className="lfr-editable"
				config={config}
				data={contents}
				key={toolbarSet}
				onBeforeLoad={(CKEDITOR) => {
					if (CKEDITOR) {
						CKEDITOR.disableAutoInline = true;

						if (!CKEDITOR.plugins.externals) {
							CKEDITOR.plugins.addExternal(
								'tab',
								'/plugins/tab/plugin.js'
							);
						}

						CKEDITOR.getNextZIndex = () => 1000;
						CKEDITOR.dtd.$removeEmpty.i = 0;
						CKEDITOR.dtd.$removeEmpty.span = 0;

						CKEDITOR.on('instanceCreated', ({editor}) => {
							if (context.imageBrowseURL) {
								editor.config.filebrowserImageBrowseUrl = context.imageBrowseURL.replace(
									'EDITOR_NAME_',
									editor.name
								);
							}

							editor.on('dialogShow', ({data}) => {
								if (data._.name === 'codeSnippet') {
									const {code, lang} = data._.contents.info;

									document.getElementById(
										lang._.inputId
									).value = 'java';

									const textarea = document.getElementById(
										code._.inputId
									);
									textarea.onkeydown = (ev) => {
										if (ev.keyCode === 9) {
											insertTextAtCursor(
												textarea,
												'    '
											);
											ev.preventDefault();
											ev.stopPropagation();

											return false;
										}
									};
								}
							});
						});
					}
				}}
				ref={editorRef}
				{...props}
			/>
		</div>
	);
};

export default QuestionsEditor;
