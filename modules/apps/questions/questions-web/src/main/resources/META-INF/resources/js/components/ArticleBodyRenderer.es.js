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

import parser from 'bbcode-to-react';
import {Editor} from 'frontend-editor-ckeditor-web';
import React from 'react';

export function getCKEditorConfig(bodyClass) {
	const config = {
		allowedContent: true,
		autoGrow_minHeight: 200,
		autoGrow_onStartup: true,
		bodyClass: 'cke_' + bodyClass,
		codeSnippet_theme: 'monokai_sublime',
		contentsCss: '/o/questions-web/css/main.css',
		extraPlugins: 'autogrow,codesnippet',
		readOnly: true,
		removePlugins: 'elementspath',
	};

	config.toolbar = [];

	return config;
}

export default ({
	articleBody,
	bodyClass = 'question', 
	compactMode = false,
	encodingFormat,
	id,
	signature,
}) => (
	<>
		{encodingFormat === 'bbcode' && <p>{parser.toReact(articleBody)}</p>}
		{encodingFormat !== 'bbcode' && compactMode && (
			<div
				className={`questions-article-body-${id}`}
				dangerouslySetInnerHTML={{__html: articleBody}}
			/>
		)}
		{encodingFormat !== 'bbcode' && !compactMode && (
			<div className={`cke_readonly questions-article-body-${id}`}>
				<Editor
					config={getCKEditorConfig(bodyClass)}
					data={articleBody}
					required
				/>
			</div>
		)}

		{signature && (
			<style
				dangerouslySetInnerHTML={{
					__html: `.questions-article-body-${id} p:last-child:after {content: " - ${signature}"; font-weight: bold;}`,
				}}
			/>
		)}
	</>
);
