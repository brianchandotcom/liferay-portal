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

import PropTypes from 'prop-types';
import React, {useState} from 'react';

import {useChannel} from '../hooks/useChannel';
import {ClosableAlert} from './ClosableAlert';
import {CodeMirrorEditor} from './CodeMirrorEditor';
import {ScriptInput} from './ScriptInput';
import {Sidebar} from './Sidebar';

export default function App({
	editorMode,
	portletNamespace,
	script: initialScript,
	showCacheableWarning,
	showLanguageChangeWarning,
	templateVariableGroups,
}) {
	const inputChannel = useChannel();
	const [script, setScript] = useState(initialScript);

	return (
		<div className="ddm_template_editor__App">
			<div className="ddm_template_editor__App-sidebar">
				<Sidebar
					onButtonClick={(item) =>
						inputChannel.sendData(item.content)
					}
					templateVariableGroups={templateVariableGroups}
				/>
			</div>

			<div className="ddm_template_editor__App-content">
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

				<CodeMirrorEditor
					content={script}
					inputChannel={inputChannel}
					mode={editorMode}
					onChange={setScript}
				/>

				<ScriptInput onSelectScript={setScript} />
			</div>
		</div>
	);
}

App.propTypes = {
	editorMode: PropTypes.oneOf(['ftl', 'xml', 'velocity']).isRequired,
	portletNamespace: PropTypes.string.isRequired,
	script: PropTypes.string.isRequired,
	showCacheableWarning: PropTypes.bool.isRequired,
	showLanguageChangeWarning: PropTypes.bool.isRequired,
	templateVariableGroups: PropTypes.any.isRequired,
};
