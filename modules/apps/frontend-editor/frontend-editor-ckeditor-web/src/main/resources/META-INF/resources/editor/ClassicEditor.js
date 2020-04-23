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

import React, {useEffect, useMemo, useRef, useState} from 'react';

import {Editor} from './Editor';

const DEFAULT_CONFIG = {
	filebrowserBrowseUrl: '',
	filebrowserFlashBrowseUrl: '',
	filebrowserImageBrowseLinkUrl: '',
	filebrowserImageBrowseUrl: '',
	filebrowserUploadUrl: null,
};

const getToolbarSet = toolbarSet => {
	if (Liferay.Util.isPhone()) {
		toolbarSet = 'phone';
	}
	else if (Liferay.Util.isTablet()) {
		toolbarSet = 'tablet';
	}

	return toolbarSet;
};

const ClassicEditor = ({
	contents,
	cssClass,
	editorConfig,
	initialToolbarSet,
	name,
}) => {
	const editorRef = useRef();

	const [toolbarSet, setToolbarSet] = useState();

	const config = useMemo(() => {
		return {
			...DEFAULT_CONFIG,
			toolbar: toolbarSet,
			...editorConfig,
		};
	}, [editorConfig, toolbarSet]);

	useEffect(() => {
		setToolbarSet(getToolbarSet(initialToolbarSet));
	}, [initialToolbarSet]);

	return (
		<div className={cssClass} id={`${name}Container`}>
			<textarea
				id={name}
				name={name}
				style={{
					display: 'none',
				}}
			></textarea>

			<Editor
				className="lfr-editable"
				config={config}
				data={contents}
				id={name}
				onBeforeLoad={CKEDITOR => {
					CKEDITOR.disableAutoInline = true;
					CKEDITOR.dtd.$removeEmpty.i = 0;
					CKEDITOR.dtd.$removeEmpty.span = 0;
				}}
				ref={editorRef}
			/>
		</div>
	);
};

export default ClassicEditor;
