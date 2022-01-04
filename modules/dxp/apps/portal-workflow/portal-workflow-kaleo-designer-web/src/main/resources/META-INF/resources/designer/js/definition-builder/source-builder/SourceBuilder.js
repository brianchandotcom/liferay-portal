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
import React, {useContext, useEffect, useRef} from 'react';
import {isEdge, isNode} from 'react-flow-renderer';

import {DefinitionBuilderContext} from '../DefinitionBuilderContext';
import {xmlNamespace} from './constants';
import {serializeDefinition} from './serializeUtil';

const config = {
	tabSpaces: 4,
	toolbar: [['Source']],
};

export default function SourceBuilder({version}) {
	const {
		currentEditor,
		definitionTitle,
		elements,
		setCurrentEditor,
		setShowInvalidContentError,
		showInvalidContentError,
	} = useContext(DefinitionBuilderContext);
	const editorRef = useRef();

	useEffect(() => {
		if (elements) {
			const metada = {
				description: '',
				name: definitionTitle,
				version,
			};

			const xmlContent = serializeDefinition(
				xmlNamespace,
				metada,
				elements.filter(isNode),
				elements.filter(isEdge)
			);

			if (xmlContent && currentEditor) {
				currentEditor.setData(xmlContent);
			}
		}

		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, [currentEditor, definitionTitle, elements, version]);

	useEffect(() => {
		if (showInvalidContentError) {
			document.addEventListener('keydown', () => {
				setShowInvalidContentError(false);
			});

			return () => {
				document.removeEventListener('keydown', () => {
					setShowInvalidContentError(false);
				});
			};
		}
	}, [setShowInvalidContentError, showInvalidContentError]);

	return (
		<>
			<ClayToolbar className="source-toolbar">
				<ClayLayout.ContainerFluid>
					<ClayToolbar.Nav>
						<ClayToolbar.Item>
							<span>{Liferay.Language.get('source')}</span>
						</ClayToolbar.Item>

						{showInvalidContentError && (
							<ClayToolbar.Item className="error ml-4">
								<span>
									{Liferay.Language.get(
										'please-enter-valid-content'
									)}
								</span>
							</ClayToolbar.Item>
						)}
					</ClayToolbar.Nav>
				</ClayLayout.ContainerFluid>
			</ClayToolbar>

			<Editor
				config={config}
				onInstanceReady={({editor}) => {
					setCurrentEditor(editor);

					editor.setMode('source');
				}}
				ref={editorRef}
			/>
		</>
	);
}
