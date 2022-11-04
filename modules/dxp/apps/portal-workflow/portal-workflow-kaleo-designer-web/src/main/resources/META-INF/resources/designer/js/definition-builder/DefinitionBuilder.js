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

export default function DefinitionBuilder(props) {
	const [accountEntryId, setAccountEntryId] = useState(props.accountEntryId);
	const [active, setActive] = useState(true);
	const [alertMessage, setAlertMessage] = useState('');
	const [alertType, setAlertType] = useState(null);
	const [blockingErrors, setBlockingErrors] = useState({errorType: ''});
	const [currentEditor, setCurrentEditor] = useState(null);
	const [definitionDescription, setDefinitionDescription] = useState('');
	const [definitionId, setDefinitionId] = useState(props.definitionName);
	const [definitionInfo, setDefinitionInfo] = useState(null);
	const [definitionName, setDefinitionName] = useState(null);
	const [definitionTitle, setDefinitionTitle] = useState(props.title);
	const [
		definitionTitleTranslations,
		setDefinitionTitleTranslations,
	] = useState(props.translations);
	const [deserialize, setDeserialize] = useState(false);
	const [elements, setElements] = useState(defaultNodes);
	const [selectedLanguageId, setSelectedLanguageId] = useState('');
	const [showDefinitionInfo, setShowDefinitionInfo] = useState(false);
	const [showInvalidContentMessage, setShowInvalidContentMessage] = useState(
		false
	);
	const [sourceView, setSourceView] = useState(false);
	const [showAlert, setShowAlert] = useState(false);
	const [version, setVersion] = useState(parseInt(props.version, 10));

	const contextProps = {
		accountEntryId,
		active,
		alertMessage,
		alertType,
		blockingErrors,
		currentEditor,
		definitionDescription,
		definitionId,
		definitionInfo,
		definitionName,
		definitionTitle,
		definitionTitleTranslations,
		deserialize,
		elements,
		functionActionExecutors: props.functionActionExecutors,
		selectedLanguageId,
		setAccountEntryId,
		setActive,
		setAlertMessage,
		setAlertType,
		setBlockingErrors,
		setCurrentEditor,
		setDefinitionDescription,
		setDefinitionId,
		setDefinitionInfo,
		setDefinitionName,
		setDefinitionTitle,
		setDefinitionTitleTranslations,
		setDeserialize,
		setElements,
		setSelectedLanguageId,
		setShowAlert,
		setShowDefinitionInfo,
		setShowInvalidContentMessage,
		setSourceView,
		setVersion,
		showAlert,
		showDefinitionInfo,
		showInvalidContentMessage,
		sourceView,
		version,
	};

	return (
		<DefinitionBuilderContextProvider {...contextProps}>
			<div className="definition-builder-app">
				<ReactFlowProvider>
					<UpperToolbar {...props} />

					{sourceView ? <SourceBuilder /> : <DiagramBuilder />}
				</ReactFlowProvider>
			</div>
		</DefinitionBuilderContextProvider>
	);
}
