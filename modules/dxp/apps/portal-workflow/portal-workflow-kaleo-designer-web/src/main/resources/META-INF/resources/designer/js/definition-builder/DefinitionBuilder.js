/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 */

import React, {useState} from 'react';
import {ReactFlowProvider} from 'react-flow-renderer';

import '../../css/definition-builder/main.scss';
import {DefinitionBuilderContextProvider} from './DefinitionBuilderContext';
import DiagramBuilder from './diagram-builder/DiagramBuilder';
import {defaultNodes} from './diagram-builder/components/nodes/utils';
import UpperToolbar from './shared/components/toolbar/UpperToolbar';
import SourceBuilder from './source-builder/SourceBuilder';

export default function (props) {
	const [currentEditor, setCurrentEditor] = useState(null);
	const [deserialize, setDeserialize] = useState(false);
	const [elements, setElements] = useState(defaultNodes);
	const [selectedLanguageId, setSelectedLanguageId] = useState('');
	const [showInvalidContentError, setShowInvalidContentError] = useState(
		false
	);
	const [sourceView, setSourceView] = useState(false);
	const [definitionTitle, setDefinitionTitle] = useState(props.title);

	const contextProps = {
		currentEditor,
		definitionTitle,
		deserialize,
		elements,
		selectedLanguageId,
		setCurrentEditor,
		setDefinitionTitle,
		setDeserialize,
		setElements,
		setSelectedLanguageId,
		setShowInvalidContentError,
		setSourceView,
		showInvalidContentError,
		sourceView,
	};

	return (
		<DefinitionBuilderContextProvider {...contextProps}>
			<div className="definition-builder-app">
				<UpperToolbar {...props} />

				<ReactFlowProvider>
					{sourceView ? (
						<SourceBuilder version={props.version} />
					) : (
						<DiagramBuilder version={props.version} />
					)}
				</ReactFlowProvider>
			</div>
		</DefinitionBuilderContextProvider>
	);
}
