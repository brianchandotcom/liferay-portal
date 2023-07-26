/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import 'codemirror/addon/display/autorefresh';

import 'codemirror/addon/edit/closebrackets';

import 'codemirror/addon/edit/closetag';

import 'codemirror/addon/edit/matchbrackets';

import 'codemirror/addon/fold/brace-fold';

import 'codemirror/addon/fold/comment-fold';

import 'codemirror/addon/fold/foldcode';

import 'codemirror/addon/fold/foldgutter.css';

import 'codemirror/addon/fold/foldgutter';

import 'codemirror/addon/fold/indent-fold';

import 'codemirror/addon/fold/xml-fold';

import 'codemirror/addon/hint/css-hint';

import 'codemirror/addon/hint/html-hint';

import 'codemirror/addon/hint/show-hint.css';

import 'codemirror/addon/hint/show-hint';

import 'codemirror/addon/hint/xml-hint';

import 'codemirror/lib/codemirror.css';

import 'codemirror/mode/css/css';

import 'codemirror/mode/htmlmixed/htmlmixed';

import 'codemirror/mode/xml/xml';
import classNames from 'classnames';
import CodeMirror from 'codemirror';
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
