/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 */

import ClayLayout from '@clayui/layout';
import ClayToolbar from '@clayui/toolbar';
import {Editor} from 'frontend-editor-ckeditor-web';
import React, {useRef} from 'react';

const config = {
	tabSpaces: 4,
	toolbar: [['Source']],
};

export default function SourceBuilder() {
	const editorRef = useRef();

	return (
		<>
			<ClayToolbar className="source-toolbar">
				<ClayLayout.ContainerFluid>
					<ClayToolbar.Nav>
						<ClayToolbar.Item>
							<span>{Liferay.Language.get('source')}</span>
						</ClayToolbar.Item>
					</ClayToolbar.Nav>
				</ClayLayout.ContainerFluid>
			</ClayToolbar>

			<Editor
				config={config}
				onInstanceReady={({editor}) => editor.setMode('source')}
				ref={editorRef}
			/>
		</>
	);
}
