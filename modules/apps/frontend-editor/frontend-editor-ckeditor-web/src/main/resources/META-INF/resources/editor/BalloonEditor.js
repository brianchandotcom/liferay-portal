/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import PropTypes from 'prop-types';
import React from 'react';

import DEFAULT_BALLOON_EDITOR_CONFIG from './config/DefaultBalloonEditorConfiguration';

import '../css/main.scss';
import BaseEditor from './BaseEditor';

const EMPTY_OBJECT = {};

const BalloonEditor = ({
	config = EMPTY_OBJECT,
	contents,
	name,
	...otherProps
}) => {
	const editorConfig = {
		...DEFAULT_BALLOON_EDITOR_CONFIG,
		...config,
	};

	if (!editorConfig.balloonEditorEnabled) {
		return null;
	}

	return (
		<BaseEditor
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
			type="inline"
			{...otherProps}
		/>
	);
};

BalloonEditor.propTypes = {
	config: PropTypes.object,
	contents: PropTypes.string,
	name: PropTypes.string,
};

export default BalloonEditor;
