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
import React from 'react';

import {Editor} from './Editor';

import '../css/main.scss';

const BalloonEditor = ({config = {}, contents, name, ...otherProps}) => {
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

	let initialCssClass;

	return (
		<Editor
			config={getConfig()}
			name={name}
			onBeforeLoad={(CKEDITOR) => {
				CKEDITOR.disableAutoInline = true;

				initialCssClass = CKEDITOR.env.cssClass;

				CKEDITOR.env.cssClass = 'lfr_balloon_editor';
			}}
			onDestroy={() => {
				CKEDITOR.env.cssClass = initialCssClass;
			}}
			onInstanceReady={(event) => {
				const editor = event.editor;

				const balloonToolbars = editor.balloonToolbars;

				balloonToolbars.create({
					buttons:
						'Bold,Italic,Underline,RemoveFormat,Link,NumberedList,BulletedList,JustifyLeft,JustifyCenter,JustifyRight,JustifyBlock,Anchor',
					refresh(editor, path) {
						const tags = new Set([
							'h1',
							'h2',
							'h3',
							'p',
							'span',
							'strong',
							'li',
						]);

						let result = false;

						tags.forEach((tag) => {
							if (path.contains(tag)) {
								result = true;
							}
						});

						return result;
					},
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
