/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React, {useState} from 'react';
import {ReactFlowProvider} from 'react-flow-renderer';

import {DefinitionBuilderContextProvider} from '../../src/main/resources/META-INF/resources/designer/js/definition-builder/DefinitionBuilderContext';

export default function MockDefinitionBuilderContext({children}) {
	const [blockingErrors, setBlockingErrors] = useState({});
	const [selectedLanguageId, setSelectedLanguageId] = useState('');
	const [translations, setTranslations] = useState({});
	const [showDefinitionInfo, setShowDefinitionInfo] = useState(false);
	const [
		definitionTitleTranslations,
		setDefinitionTitleTranslations,
	] = useState('');

	const contextProps = {
		blockingErrors,
		defaultLanguageId: themeDisplay.getLanguageId(),
		definitionTitleTranslations,
		selectedLanguageId,
		setBlockingErrors,
		setDefinitionTitleTranslations,
		setSelectedLanguageId,
		setShowDefinitionInfo,
		setTranslations,
		showDefinitionInfo,
		translations,
	};

	return (
		<DefinitionBuilderContextProvider {...contextProps}>
			<ReactFlowProvider>{children}</ReactFlowProvider>
		</DefinitionBuilderContextProvider>
	);
}
