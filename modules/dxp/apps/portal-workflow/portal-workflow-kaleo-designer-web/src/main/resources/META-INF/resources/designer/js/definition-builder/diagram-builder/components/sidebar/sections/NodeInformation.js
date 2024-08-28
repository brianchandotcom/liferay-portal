/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import PropTypes from 'prop-types';
import React, {useContext} from 'react';

import {DefinitionBuilderContext} from '../../../../DefinitionBuilderContext';
import {DiagramBuilderContext} from '../../../DiagramBuilderContext';
import {DisabledGroovyScriptAlert} from '../../shared-components/DisabledGroovyScriptAlert';
import ScriptInput from '../../shared-components/ScriptInput';
import SidebarPanel from '../SidebarPanel';
import {NodeInformationBaseSection} from './NodeInformationBaseSection';

export default function NodeInformation({errors, setErrors}) {
	const {
		allowScriptContentToBeExecutedOrIncluded,
		elements,
		hasGroovyOrJavaScript,
		scriptManagementConfigurationPortletURL,
		selectedLanguageId,
	} = useContext(DefinitionBuilderContext);
	const {
		selectedItem,
		selectedItemNewId,
		setSelectedItem,
		setSelectedItemNewId,
	} = useContext(DiagramBuilderContext);

	return (
		<>
			{!allowScriptContentToBeExecutedOrIncluded &&
				hasGroovyOrJavaScript &&
				selectedItem &&
				selectedItem.type === 'condition' && (
					<DisabledGroovyScriptAlert
						scriptManagementConfigurationPortletURL={
							scriptManagementConfigurationPortletURL
						}
					/>
				)}

			<SidebarPanel panelTitle={Liferay.Language.get('information')}>
				<NodeInformationBaseSection
					elements={elements}
					errors={errors}
					selectedItem={selectedItem}
					selectedItemNewId={selectedItemNewId}
					selectedLanguageId={selectedLanguageId}
					setErrors={setErrors}
					setSelectedItem={setSelectedItem}
					setSelectedItemNewId={setSelectedItemNewId}
				/>

				{selectedItem?.type === 'condition' && (
					<ScriptInput
						defaultScriptLanguage={
							selectedItem?.data.scriptLanguage
						}
						handleClickCapture={(scriptLanguage) =>
							setSelectedItem({
								...selectedItem,
								data: {
									...selectedItem.data,
									scriptLanguage,
								},
							})
						}
						inputValue={selectedItem?.data.script || ''}
						updateSelectedItem={({target}) =>
							setSelectedItem({
								...selectedItem,
								data: {
									...selectedItem.data,
									script: target.value,
								},
							})
						}
					/>
				)}
			</SidebarPanel>
		</>
	);
}

NodeInformation.propTypes = {
	errors: PropTypes.object.isRequired,
	setErrors: PropTypes.func.isRequired,
};
