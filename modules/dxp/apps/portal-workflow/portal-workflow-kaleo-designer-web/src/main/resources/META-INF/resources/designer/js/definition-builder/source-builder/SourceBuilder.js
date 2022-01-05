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

import ClayAlert from '@clayui/alert';
import ClayIcon from '@clayui/icon';
import ClayLayout from '@clayui/layout';
import ClayLink from '@clayui/link';
import ClayToolbar from '@clayui/toolbar';
import {Editor} from 'frontend-editor-ckeditor-web';
import React, {useContext, useEffect, useRef, useState} from 'react';
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
		setShowInvalidContentMessage,
		showInvalidContentMessage,
	} = useContext(DefinitionBuilderContext);
	const editorRef = useRef();
	const [showImportSuccessMessage, setShowImportSuccessMessage] = useState(
		false
	);

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
		if (showInvalidContentMessage) {
			document.addEventListener('keydown', () => {
				setShowInvalidContentMessage(false);
			});

			return () => {
				document.removeEventListener('keydown', () => {
					setShowInvalidContentMessage(false);
				});
			};
		}
	}, [setShowInvalidContentMessage, showInvalidContentMessage]);

	const writeDefinitionMessage = Liferay.Language.get(
		'write-your-definition-or-x'
	).substring(0, 25);

	const importFileMessage = Liferay.Language.get(
		'import-a-file'
	).toLowerCase();

	function loadFile(event) {
		var files = event.target.files;

		if (files) {
			var reader = new FileReader();

			reader.onloadend = (event) => {
				if (event.target.readyState === FileReader.DONE) {
					currentEditor.setData(event.target.result);

					const fileInput = document.querySelector('#fileInput');

					fileInput.value = '';

					setShowImportSuccessMessage(true);
				}
			};

			reader.readAsText(files[0]);
		}
	}

	return (
		<>
			<ClayToolbar className="source-toolbar">
				<ClayLayout.ContainerFluid>
					<ClayToolbar.Nav>
						<ClayToolbar.Item>
							<span>{Liferay.Language.get('source')}</span>
						</ClayToolbar.Item>

						{showInvalidContentMessage && (
							<ClayToolbar.Item className="error ml-4">
								<span>
									{Liferay.Language.get(
										'please-enter-valid-content'
									)}
								</span>
							</ClayToolbar.Item>
						)}

						<ClayToolbar.Item>
							<div className="import-file">
								<ClayIcon symbol="document-code" />

								<span>{writeDefinitionMessage}</span>

								<label className="pt-1" htmlFor="fileInput">
									<ClayLink className="ml-1">
										{`${importFileMessage}.`}
									</ClayLink>
								</label>

								<input
									id="fileInput"
									onChange={(event) => loadFile(event)}
									type="file"
								/>
							</div>
						</ClayToolbar.Item>
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

			{showImportSuccessMessage && (
				<ClayAlert.ToastContainer>
					<ClayAlert
						autoClose={5000}
						displayType="success"
						onClose={() => setShowImportSuccessMessage(false)}
						title={`${Liferay.Language.get('success')}:`}
					>
						{Liferay.Language.get(
							'definition-imported-successfully'
						)}
					</ClayAlert>
				</ClayAlert.ToastContainer>
			)}
		</>
	);
}
