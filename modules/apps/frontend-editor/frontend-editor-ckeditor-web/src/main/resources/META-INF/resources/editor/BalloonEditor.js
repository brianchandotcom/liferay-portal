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
import React, {useState} from 'react';

import {Editor} from './Editor';

import '../css/main.scss';

const BalloonEditor = ({config = {}, contents, name, ...otherProps}) => {
	const [cssClass, setCssClass] = useState('');

	const defaultExtraPlugins = 'balloontoolbar,floatingspace';

	const getConfig = () => {
		const extraPlugins = config.extraPlugins
			? `${config.extraPlugins}`
			: '';

		return {
			...config,
			extraAllowedContent: '*',
			extraPlugins: `${extraPlugins}${defaultExtraPlugins}`,
		};
	};

	return (
		<Editor
			config={getConfig()}
			name={name}
			onBeforeLoad={(CKEDITOR) => {
				CKEDITOR.disableAutoInline = true;

				setCssClass(CKEDITOR.env.cssClass);

				CKEDITOR.env.cssClass = `${CKEDITOR.env.cssClass} lfr-balloon-editor`;
			}}
			onDestroy={() => {
				CKEDITOR.env.cssClass = cssClass;
			}}
			onInstanceReady={(event) => {
				const editor = event.editor;

				const balloonToolbars = editor.balloonToolbars;

				balloonToolbars.create({
					buttons:
						'Bold,Italic,Underline,RemoveFormat,Link,NumberedList,BulletedList,JustifyLeft,JustifyCenter,JustifyRight,JustifyBlock,Anchor',
					cssSelector: '*',
				});

				balloonToolbars.create({
					buttons: 'Link,Unlink',
					priority:
						window.CKEDITOR.plugins.balloontoolbar.PRIORITY.HIGH,
					refresh(editor, path) {
						return path.contains('a');
					},
				});

				balloonToolbars.create({
					buttons:
						'JustifyLeft,JustifyCenter,JustifyRight,Link,Unlink',
					priority:
						window.CKEDITOR.plugins.balloontoolbar.PRIORITY.HIGH,
					widgets: 'image,image2',
				});

				if (contents) {
					editor.setData(contents);
				}
			}}
			type="inline"
			{...otherProps}
		/>
	);
};

BalloonEditor.propTypes = {
	config: PropTypes.object,
	name: PropTypes.string,
};

export default BalloonEditor;
