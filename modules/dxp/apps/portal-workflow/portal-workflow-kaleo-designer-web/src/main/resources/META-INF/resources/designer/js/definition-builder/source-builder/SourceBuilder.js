/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayAlert from '@clayui/alert';
import ClayIcon from '@clayui/icon';
import ClayLayout from '@clayui/layout';
import ClayLink from '@clayui/link';
import ClayLoadingIndicator from '@clayui/loading-indicator';
import ClayToolbar from '@clayui/toolbar';
import {Editor} from 'frontend-editor-ckeditor-web';
import React, {useContext, useEffect, useRef, useState} from 'react';
import {isEdge, isNode} from 'react-flow-renderer';

import {DefinitionBuilderContext} from '../DefinitionBuilderContext';
import {editorConfig} from '../constants';
import {xmlNamespace} from './constants';
import {serializeDefinition} from './serializeUtil';

export default function SourceBuilder() {
	const {
		currentEditor,
		definitionDescription,
		definitionName,
		elements,
		setCurrentEditor,
		version,
	} = useContext(DefinitionBuilderContext);
	const editorRef = useRef();
	const [loading, setLoading] = useState(true);
	const [showImportSuccessMessage, setShowImportSuccessMessage] = useState(
		false
	);
	const [showInvalidContentMessage, setShowInvalidContentMessage] = useState(
		false
	);

	useEffect(() => {
		if (currentEditor?.mode === 'source' && elements) {
			const metadata = {
				description: definitionDescription,
				name: definitionName,
				version,
			};

			const xmlContent = serializeDefinition(
				xmlNamespace,
				metadata,
				elements.filter(isNode),
				elements.filter(isEdge)
			);

			if (xmlContent) {
				currentEditor.setData(xmlContent);

				setLoading(false);
			}
		}

		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, [currentEditor, definitionName, elements, version]);

	useEffect(() => {
		if (currentEditor && currentEditor.mode !== 'source') {
			currentEditor.setMode('source');
		}
	}, [currentEditor]);

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
		setShowInvalidContentMessage(false);

		const files = event.target.files;

		if (files[0].type === 'text/xml') {
			const reader = new FileReader();

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
		else if (files[0].type !== 'text/xml') {
			setShowInvalidContentMessage(true);
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

			{loading && (
				<ClayLoadingIndicator
					displayType="primary"
					shape="squares"
					size="md"
				/>
			)}

			<Editor
				config={editorConfig}
				onInstanceReady={({editor}) => {
					editor.setMode('source');

					setCurrentEditor(editor);
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

			{showInvalidContentMessage && (
				<ClayAlert.ToastContainer>
					<ClayAlert
						autoClose={5000}
						displayType="danger"
						onClose={() => showInvalidContentMessage(false)}
						title={`${Liferay.Language.get('error')}:`}
					>
						{Liferay.Language.get('please-select-a-valid-xml-file')}
					</ClayAlert>
				</ClayAlert.ToastContainer>
			)}
		</>
	);
}
