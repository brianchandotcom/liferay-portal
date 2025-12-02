/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {CodeMirrorEditor} from '@liferay/object-js-components-web';
import React, {useRef} from 'react';

const BaseSourceCode = ({scriptSourceCode, updateSelectedItem}) => {
	const editorRef = useRef();

	return (
		<CodeMirrorEditor
			lineWrapping={true}
			mode="xml"
			onChange={(value) => {
				updateSelectedItem(value);
			}}
			readOnly={false}
			ref={editorRef}
			value={scriptSourceCode ? scriptSourceCode[0] : ''}
		/>
	);
};

export default BaseSourceCode;
