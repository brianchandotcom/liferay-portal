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
import React, {useEffect} from 'react';

const BASEPATH = '/o/frontend-editor-ckeditor-web/ckeditor/';

const Editor = React.forwardRef((props, ref) => {
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

	return <CKEditor ref={ref} {...props} />;
});

CKEditor.editorUrl = `${BASEPATH}ckeditor.js`;
window.CKEDITOR_BASEPATH = BASEPATH;

export {Editor};
