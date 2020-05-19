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

import {useEventListener} from 'frontend-js-react-web';
import {isPhone, isTablet} from 'frontend-js-web';
import React, {useCallback, useEffect, useMemo, useRef, useState} from 'react';

import {Editor} from './Editor';

const getToolbarSet = (toolbarSet) => {
	if (isPhone()) {
		toolbarSet = 'phone';
	}
	else if (isTablet()) {
		toolbarSet = 'tablet';
	}

	return toolbarSet;
};

const ClassicEditor = ({
	contents = '',
	cssClass,
	editorConfig = {},
	initialToolbarSet,
	name,
	onChangeMethodName,
}) => {
	const editorRef = useRef();

	const [toolbarSet, setToolbarSet] = useState(initialToolbarSet);

	const config = useMemo(() => {
		return {
			toolbar: toolbarSet,
			...editorConfig,
		};
	}, [editorConfig, toolbarSet]);

	const getHTML = useCallback(() => {
		let data = contents;

		const editor = editorRef.current.editor;

		if (editor && editor.instanceReady) {
			data = editor.getData();

			if (CKEDITOR.env.gecko && CKEDITOR.tools.trim(data) === '<br />') {
				data = '';
			}
		}

		return data;
	}, [contents]);

	const onChangeCallback = () => {
		const editor = editorRef.current.editor;

		if (editor.checkDirty()) {
			window[onChangeMethodName](getHTML());

			editor.resetDirty();
		}
	};

	useEffect(() => {
		setToolbarSet(getToolbarSet(initialToolbarSet));
	}, [initialToolbarSet]);

	useEffect(() => {
		window[name] = {
			getHTML,
			getText() {
				return contents;
			},
		};
	}, [contents, getHTML, name]);

	useEventListener(
		'resize',
		() => setToolbarSet(getToolbarSet(initialToolbarSet)),
		true,
		window
	);

	return (
		<div className={cssClass} id={`${name}Container`}>
			<Editor
				className="lfr-editable"
				config={config}
				data={contents}
				key={toolbarSet}
				onBeforeLoad={(CKEDITOR) => {
					CKEDITOR.disableAutoInline = true;
					CKEDITOR.dtd.$removeEmpty.i = 0;
					CKEDITOR.dtd.$removeEmpty.span = 0;

					CKEDITOR.on('instanceCreated', ({editor}) => {
						editor.name = name;
					});
				}}
				onChange={onChangeCallback}
				ref={editorRef}
			/>
		</div>
	);
};

export default ClassicEditor;
