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
import React, {useState} from 'react';

import {AppContextProvider} from './AppContext';
import {ClosableAlert} from './ClosableAlert';
import {Editor} from './Editor';
import Sidebar from './Sidebar';
import {PANEL_IDS} from './panelIds';

import './App.scss';

export default function App({
	editorAutocompleteData = {variables: {}},
	portletNamespace,
	propertiesViewURL,
	script: initialScript = '',
	showCacheableWarning = false,
	showPropertiesPanel = false,
	templateVariableGroups = [],
}) {
	const [selectedSidebarPanelId, setSelectedSidebarPanelId] = useState(
		showPropertiesPanel ? PANEL_IDS.properties : PANEL_IDS.elements
	);

	return (
		<AppContextProvider
			portletNamespace={portletNamespace}
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
						id={`${portletNamespace}-cacheableWarningMessage`}
						message={Liferay.Language.get(
							'this-template-is-marked-as-cacheable.-avoid-using-code-that-uses-request-handling,-the-cms-query-api,-taglibs,-or-other-dynamic-features.-uncheck-the-cacheable-property-if-dynamic-behavior-is-needed'
						)}
						visible={showCacheableWarning}
					/>

					<Editor
						autocompleteData={editorAutocompleteData}
						initialScript={initialScript}
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
	portletNamespace: PropTypes.string.isRequired,
	script: PropTypes.string.isRequired,
	showCacheableWarning: PropTypes.bool.isRequired,
	templateVariableGroups: PropTypes.any.isRequired,
};
