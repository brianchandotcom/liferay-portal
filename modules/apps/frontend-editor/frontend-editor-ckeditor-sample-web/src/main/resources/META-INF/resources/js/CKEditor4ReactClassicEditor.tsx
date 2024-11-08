/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

// @ts-ignore

import {ClassicEditor} from 'frontend-editor-ckeditor-web';
import React from 'react';

const CKEditor4ReactClassicEditor = ({
	editorTransformerURLs,
	name,
	title,
}: {
	editorTransformerURLs?: Array<string>;
	name: string;
	title?: string;
}) => {
	const config: any = {
		toolbar_simple: [
			['Undo', 'Redo'],
			['Bold', 'Italic', 'Underline'],
		],
	};

	if (editorTransformerURLs?.length) {
		config.editorTransformerURLs = editorTransformerURLs;
	}

	const beforeConfig = JSON.stringify(config);

	return (
		<ClassicEditor
			editorConfig={config}
			name={name}
			onReady={({editor}: {editor: any}) => {
				const afterConfig = JSON.stringify(config);

				editor.setData(
					beforeConfig === afterConfig
						? 'Editor configuration object was not mutated.'
						: 'Editor configuration object must not be mutated!'
				);
			}}
			title={title}
		/>
	);
};

export default CKEditor4ReactClassicEditor;
