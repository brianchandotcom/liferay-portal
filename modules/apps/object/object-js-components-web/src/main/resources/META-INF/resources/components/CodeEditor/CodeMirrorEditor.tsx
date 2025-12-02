/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {CodeMirror} from '@liferay/frontend-js-codemirror-web';
import classNames from 'classnames';
import {CodeMirrorKeyboardMessage} from 'frontend-js-components-web';
import React, {useEffect, useRef, useState} from 'react';

import './CodeMirrorEditor.scss';

const CodeMirrorEditor = React.forwardRef<CodeMirror.Editor, ICodeMirrorEditor>(
	({mode, onChange, readOnly, ...options}, ref) => {
		const editorWrapperRef = useRef<HTMLDivElement>(null);
		const codeMirrorRef = useRef<CodeMirror.Editor>();

		const [isEnabled, setIsEnabled] = useState(true);
		const [isFocused, setIsFocused] = useState(false);

		useEffect(() => {
			const hasEnabledTabKey = ({
				state: {keyMaps},
			}: CodeMirror.Editor) => {
				return keyMaps.every(
					(key: {name: string}) => key.name !== 'tabKey'
				);
			};

			const editor = CodeMirror(
				editorWrapperRef.current as HTMLDivElement,
				{
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
						'Ctrl-Space': readOnly ? '' : 'autocomplete',
					},
					foldGutter: true,
					gutters: [
						'CodeMirror-linenumbers',
						'CodeMirror-foldgutter',
					],
					inputStyle: 'contenteditable',
					lineNumbers: true,
					mode: mode ?? 'freemarker',
					readOnly: readOnly && 'nocursor',
					...options,
				}
			);

			codeMirrorRef.current = editor;

			if (ref instanceof Function) {
				ref(editor);
			}
			else if (ref) {
				(ref as React.MutableRefObject<CodeMirror.Editor>).current =
					editor;
			}

			const handleChange = (editor: CodeMirror.Editor) => {
				onChange(editor.getValue(), editor.lineCount());
			};

			editor.on('blur', () => setIsFocused(false));

			editor.on('change', handleChange);

			editor.on('focus', (cm) => {
				setIsFocused(true);

				if (hasEnabledTabKey(cm)) {
					cm.addKeyMap({
						'Shift-Tab': false,
						'Tab': false,
						'name': 'tabKey',
					});
				}
			});

			return () => editor.off('change', handleChange);

			// eslint-disable-next-line react-hooks/exhaustive-deps
		}, []);

		return (
			<>
				<div
					aria-label={Liferay.Language.get(
						'use-ctrl-m-to-enable-or-disable-the-tab-key'
					)}
					className={classNames('lfr-objects__editor', {
						'lfr-objects__editor--disabled': readOnly,
					})}
					ref={editorWrapperRef}
				>
					{isFocused ? (
						<CodeMirrorKeyboardMessage keyIsEnabled={isEnabled} />
					) : null}
				</div>
			</>
		);
	}
);

export default React.memo(CodeMirrorEditor);

export interface ICodeMirrorEditor extends CodeMirror.EditorConfiguration {
	onChange: (value?: string, lineCount?: number) => void;
}
