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

import {openToast} from 'frontend-js-web';
import PropTypes from 'prop-types';
import React, {useContext, useEffect, useRef, useState} from 'react';

import {AppContext} from './AppContext';
import {CodeMirrorEditor} from './CodeMirrorEditor';

export const Editor = ({autocompleteData, initialScript}) => {
	const {inputChannel, portletNamespace} = useContext(AppContext);

	const [script, setScript] = useState(initialScript);

	const scriptRef = useRef(script);
	scriptRef.current = script;

	useEffect(() => {
		const refreshHandler = Liferay.on(
			`${portletNamespace}refreshEditor`,
			() => {
				const formElement = document.getElementById(
					`${portletNamespace}fm`
				);

				if (!formElement) {
					return;
				}

				if (scriptRef.current === initialScript) {
					setScript('');
				}

				Liferay.fire(`${portletNamespace}saveTemplate`);

				requestAnimationFrame(() => {
					formElement.action = window.location.href;
					formElement.submit();
				});
			}
		);

		return () => {
			refreshHandler.detach();
		};
	}, [initialScript, portletNamespace]);

	useEffect(() => {
		const scriptImportedHandler = Liferay.on(
			`${portletNamespace}scriptImported`,
			(event) => {
				setScript(event.script);

				openToast({
					message: Liferay.Util.sub(
						Liferay.Language.get('x-imported'),
						event.fileName
					),
					title: Liferay.Language.get('success'),
					type: 'success',
				});
			}
		);

		return () => {
			scriptImportedHandler.detach();
		};
	}, [initialScript, portletNamespace]);

	useEffect(() => {
		const exportScriptHandler = Liferay.on(
			`${portletNamespace}exportScript`,
			() => {
				exportScript(scriptRef.current, 'ftl');
			}
		);

		return () => {
			exportScriptHandler.detach();
		};
	}, [initialScript, portletNamespace]);

	return (
		<>
			<CodeMirrorEditor
				autocompleteData={autocompleteData}
				content={script}
				inputChannel={inputChannel}
				onChange={setScript}
			/>

			<input
				id={`${portletNamespace}scriptContent`}
				name={`${portletNamespace}scriptContent`}
				type="hidden"
				value={script}
			/>
		</>
	);
};

Editor.propTypes = {
	autocompleteData: PropTypes.object.isRequired,
	initialScript: PropTypes.string.isRequired,
};

const exportScript = (script) => {
	const link = document.createElement('a');
	const blob = new Blob([script]);

	const fileURL = URL.createObjectURL(blob);

	link.href = fileURL;
	link.download = 'script.ftl';

	link.click();

	URL.revokeObjectURL(fileURL);
};
