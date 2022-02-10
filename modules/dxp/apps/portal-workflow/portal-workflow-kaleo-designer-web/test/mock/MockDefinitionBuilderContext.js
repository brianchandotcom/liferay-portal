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

import {DefinitionBuilderContextProvider} from '../../src/main/resources/META-INF/resources/designer/js/definition-builder/DefinitionBuilderContext';

export default function MockDefinitionBuilderContext({children}) {
	const [blockingErrors, setBlockingErrors] = useState({});
	const [selectedLanguageId, setSelectedLanguageId] = useState('');
	const [translations, setTranslations] = useState({});

	const contextProps = {
		blockingErrors,
		defaultLanguageId: themeDisplay.getLanguageId(),
		selectedLanguageId,
		setBlockingErrors,
		setSelectedLanguageId,
		setTranslations,
		translations,
	};

	return (
		<DefinitionBuilderContextProvider {...contextProps}>
			<ReactFlowProvider>{children}</ReactFlowProvider>
		</DefinitionBuilderContextProvider>
	);
}
