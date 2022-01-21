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
import React, {forwardRef, useEffect} from 'react';

import '../css/editor.scss';

const BASEPATH = '/o/frontend-editor-ckeditor-web/ckeditor/';

/**
 * @deprecated As of Cavanaugh (7.4.x), replaced by ClassicEditor
 */
const Editor = forwardRef(({contents = '', name, ...props}, ref) => {
	useEffect(() => {
		Liferay.once('beforeScreenFlip', () => {
			if (
				window.CKEDITOR &&
				Object.keys(window.CKEDITOR.instances).length === 0
			) {
				delete window.CKEDITOR;
			}
		});
	}, []);

	return <CKEditor contents={contents} name={name} ref={ref} {...props} />;
});

CKEditor.editorUrl = `${BASEPATH}ckeditor.js`;
window.CKEDITOR_BASEPATH = BASEPATH;

Editor.propTypes = {
	contents: PropTypes.string,
	name: PropTypes.string,
};

export {Editor};
