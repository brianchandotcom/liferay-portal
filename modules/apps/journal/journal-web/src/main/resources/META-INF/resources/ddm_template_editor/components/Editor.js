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
import React, {useEffect, useRef, useState} from 'react';

import {CodeMirrorEditor} from './CodeMirrorEditor';
import {ScriptInput} from './ScriptInput';

export const Editor = ({
	autocompleteData,
	editorMode,
	initialScript,
	inputChannel,
	portletNamespace,
}) => {
	const [script, setScript] = useState(initialScript);

	const scriptRef = useRef(script);
	scriptRef.current = script;

	useEffect(() => {
		const eventHandler = Liferay.on(
			`${portletNamespace}saveTemplate`,
			() => {
				const scriptInputElement = document.getElementById(
					`${portletNamespace}scriptContent`
				);

				if (scriptInputElement) {
					scriptInputElement.value = scriptRef.current;
				}
			}
		);

		return () => {
			eventHandler.detach();
		};
	}, [portletNamespace]);

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

	return (
		<>
			<CodeMirrorEditor
				autocompleteData={autocompleteData}
				content={script}
				inputChannel={inputChannel}
				mode={editorMode}
				onChange={setScript}
			/>

			<ScriptInput onSelectScript={setScript} />
		</>
	);
};

Editor.propTypes = {
	autocompleteData: PropTypes.object.isRequired,
	editorMode: PropTypes.oneOf(['ftl', 'xml', 'velocity']).isRequired,
	initialScript: PropTypes.string.isRequired,
	inputChannel: PropTypes.object.isRequired,
	portletNamespace: PropTypes.string.isRequired,
};
