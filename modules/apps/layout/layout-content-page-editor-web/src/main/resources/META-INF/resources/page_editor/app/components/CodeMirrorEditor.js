/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {CodeMirror} from '@liferay/frontend-js-codemirror-web';
import classNames from 'classnames';
import {CodeMirrorKeyboardMessage} from 'frontend-js-components-web';
import React, {forwardRef, useEffect, useRef, useState} from 'react';

const noop = () => {};

const CodeMirrorEditor = forwardRef(
	(
		{className, initialContent = '', mode = 'text/html', onChange = noop},
		ref
	) => {
		const [isEnabled, setIsEnabled] = useState(true);
		const [isFocused, setIsFocused] = useState(false);
		const containerRef = useRef();

		useEffect(() => {
			if (!containerRef.current) {
				return;
			}

			const hasEnabledTabKey = ({state: {keyMaps}}) =>
				keyMaps.every((key) => key.name !== 'tabKey');

			const codeMirror = CodeMirror(containerRef.current, {
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

			if (typeof ref === 'function') {
				ref(codeMirror);
			}
			else if (ref) {
				ref.current = codeMirror;
			}

			return () => {
				if (typeof ref === 'function') {
					ref(null);
				}
				else if (ref) {
					ref.current = null;
				}
			};

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
					ref={containerRef}
				/>
			</div>
		);
	}
);

export default CodeMirrorEditor;
