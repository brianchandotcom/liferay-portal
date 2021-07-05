/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

import classNames from 'classnames';
import PropTypes from 'prop-types';
import React, {useEffect, useState} from 'react';

import {useChannel} from '../hooks/useChannel';
import {AppContextProvider} from './AppContext';
import {ClosableAlert} from './ClosableAlert';
import {Editor} from './Editor';
import Sidebar from './Sidebar';

export default function App({
	editorAutocompleteData,
	editorMode: initialEditorMode,
	portletNamespace,
	propertiesViewURL,
	script: initialScript,
	showCacheableWarning,
	showLanguageChangeWarning,
	templateVariableGroups,
}) {
	const [editorMode, setEditorMode] = useState(initialEditorMode);
	const inputChannel = useChannel();

	useEffect(() => {
		const modeSelect = document.getElementById(
			`${portletNamespace}language`
		);

		const onModeChange = (event) => {
			setEditorMode(
				{
					ftl: 'ftl',
					vm: 'velocity',
				}[event.target.value]
			);
		};

		if (modeSelect) {
			modeSelect.addEventListener('change', onModeChange);

			return () => {
				modeSelect.removeEventListener('change', onModeChange);
			};
		}
	}, [portletNamespace]);

	const [selectedSidebarPanelId, setSelectedSidebarPanelId] = useState(null);

	return (
		<AppContextProvider
			inputChannel={inputChannel}
			propertiesViewURL={propertiesViewURL}
			templateVariableGroups={templateVariableGroups}
		>
			<div className="ddm_template_editor__App">
				<div
					className={classNames('ddm_template_editor__App-content', {
						'ddm_template_editor__App-content--sidebar-open': selectedSidebarPanelId,
					})}
				>
					<ClosableAlert
						message={Liferay.Language.get(
							'changing-the-language-does-not-automatically-translate-the-existing-template-script'
						)}
						visible={showLanguageChangeWarning}
					/>

					<ClosableAlert
						id={`${portletNamespace}-cacheableWarningMessage`}
						linkedCheckboxId={`${portletNamespace}cacheable`}
						message={Liferay.Language.get(
							'this-template-is-marked-as-cacheable.-avoid-using-code-that-uses-request-handling,-the-cms-query-api,-taglibs,-or-other-dynamic-features.-uncheck-the-cacheable-property-if-dynamic-behavior-is-needed'
						)}
						visible={showCacheableWarning}
					/>

					<Editor
						autocompleteData={editorAutocompleteData}
						editorMode={editorMode}
						initialScript={initialScript}
						inputChannel={inputChannel}
						portletNamespace={portletNamespace}
					/>
				</div>

				<Sidebar
					selectedSidebarPanelId={selectedSidebarPanelId}
					setSelectedSidebarPanelId={setSelectedSidebarPanelId}
				/>
			</div>
		</AppContextProvider>
	);
}

App.propTypes = {
	editorAutocompleteData: PropTypes.object.isRequired,
	editorMode: PropTypes.string.isRequired,
	portletNamespace: PropTypes.string.isRequired,
	script: PropTypes.string.isRequired,
	showCacheableWarning: PropTypes.bool.isRequired,
	showLanguageChangeWarning: PropTypes.bool.isRequired,
	templateVariableGroups: PropTypes.any.isRequired,
};
