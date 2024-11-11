/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {CKEditor} from '@ckeditor/ckeditor5-react';
import {
	Bold,
	ClassicEditor as BaseClassicEditor,
	EditorConfig,
	Essentials,
	GeneralHtmlSupport,
	Italic,
	Paragraph,
	Underline,
} from 'ckeditor5';
import React from 'react';

const ClassicEditor = ({config}: {config?: EditorConfig}) => {
	const defaultConfig: EditorConfig = {
		plugins: [
			Bold,
			Essentials,
			GeneralHtmlSupport,
			Italic,
			Underline,
			Paragraph,
		],
		toolbar: ['undo', 'redo', '|', 'bold', 'italic', 'underline'],
	};

	if (!Liferay.FeatureFlags['LPD-11235']) {
		return <></>;
	}

	return (
		<CKEditor
			config={{
				...defaultConfig,
				...config,
			}}
			editor={BaseClassicEditor}
		/>
	);
};

export default ClassicEditor;
