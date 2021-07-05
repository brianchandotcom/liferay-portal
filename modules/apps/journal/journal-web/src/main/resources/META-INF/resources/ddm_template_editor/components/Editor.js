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
import React, {useContext, useEffect, useRef, useState} from 'react';

import {AppContext} from './AppContext';
import {CodeMirrorEditor} from './CodeMirrorEditor';

export const Editor = ({autocompleteData, initialScript}) => {
	const {editorMode, inputChannel, portletNamespace} = useContext(AppContext);

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

	return (
		<>
			<CodeMirrorEditor
				autocompleteData={autocompleteData}
				content={script}
				inputChannel={inputChannel}
				mode={editorMode}
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
