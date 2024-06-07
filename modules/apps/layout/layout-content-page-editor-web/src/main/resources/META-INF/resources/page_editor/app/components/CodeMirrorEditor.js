/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {CodeMirror} from '@liferay/frontend-js-codemirror-web';
import classNames from 'classnames';
import {CodeMirrorKeyboardMessage} from 'frontend-js-components-web';
import React, {useEffect, useRef, useState} from 'react';

const noop = () => {};

const CodeMirrorEditor = ({
	className,
	initialContent = '',
	mode = 'text/html',
	onChange = noop,
}) => {
	const [isEnabled, setIsEnabled] = useState(true);
	const [isFocused, setIsFocused] = useState(false);
	const ref = useRef();

	useEffect(() => {
		if (ref.current) {
			const hasEnabledTabKey = ({state: {keyMaps}}) =>
				keyMaps.every((key) => key.name !== 'tabKey');

			const codeMirror = CodeMirror(ref.current, {
				autoCloseTags: true,
				autoRefresh: true,
				extraKeys: {
					'Ctrl-M'(cm) {
						const tabKeyIsEnabled = hasEnabledTabKey(cm);

						setIsEnabled(tabKeyIsEnabled);

						if (tabKeyIsEnabled) {
							cm.addKeyMap({
								'Shift-Tab': false,
								'Tab': false,
								'name': 'tabKey',
							});
						}
						else {
							cm.removeKeyMap('tabKey');
						}
					},
					'Ctrl-Space': 'autocomplete',
				},
				globalVars: true,
				gutters: ['CodeMirror-linenumbers', 'CodeMirror-foldgutter'],
				hintOptions: {
					completeSingle: false,
				},
				lineNumbers: true,
				mode,
				showHint: true,
				tabSize: 2,
				value: initialContent,
			});

			codeMirror.on('change', (cm) => {
				onChange(cm.getValue());
			});

			codeMirror.setSize(null, '100%');

			codeMirror.on('focus', (cm) => {
				setIsFocused(true);

				if (hasEnabledTabKey(cm)) {
					cm.addKeyMap({
						'Shift-Tab': false,
						'Tab': false,
						'name': 'tabKey',
					});
				}
			});

			codeMirror.on('blur', () => setIsFocused(false));
		}

		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, []);

	return (
		<div className="h-100 position-relative">
			{isFocused ? (
				<CodeMirrorKeyboardMessage keyIsEnabled={isEnabled} />
			) : null}

			<div
				aria-label={Liferay.Language.get(
					'use-ctrl-m-to-enable-or-disable-the-tab-key'
				)}
				className={classNames(className, 'h-100')}
				ref={ref}
			/>
		</div>
	);
};

export default CodeMirrorEditor;
