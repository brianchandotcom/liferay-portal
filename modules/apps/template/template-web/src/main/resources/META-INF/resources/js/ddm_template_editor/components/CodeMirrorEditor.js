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

import 'codemirror/addon/hint/show-hint.css';

import 'codemirror/addon/hint/show-hint';

import 'codemirror/addon/hint/xml-hint';

import 'codemirror/lib/codemirror.css';

import 'codemirror/mode/htmlmixed/htmlmixed';

import 'codemirror/mode/javascript/javascript';
import CodeMirror from 'codemirror';
import PropTypes from 'prop-types';
import React, {useEffect, useRef, useState} from 'react';

export function CodeMirrorEditor({
	autocompleteData,
	content,
	inputChannel,
	mode,
	onChange,
}) {
	const [editor, setEditor] = useState();
	const [editorWrapper, setEditorWrapper] = useState();
	const initialContentRef = useRef(content);

	useEffect(() => {
		if (!editorWrapper) {
			return;
		}

		const hasEnabledTabKey = ({state: {keyMaps}}) =>
			keyMaps.every((key) => key.name !== 'tabKey');

		const codeMirror = CodeMirror(editorWrapper, {
			autoCloseTags: true,
			autoRefresh: true,
			extraKeys: {
				'Ctrl-M'(cm) {
					if (hasEnabledTabKey(cm)) {
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
			foldGutter: true,
			gutters: ['CodeMirror-linenumbers', 'CodeMirror-foldgutter'],
			indentWithTabs: true,
			inputStyle: 'contenteditable',
			lineNumbers: true,
			matchBrackets: true,
			showHint: true,
			tabSize: 2,
			value: initialContentRef.current,
			viewportMargin: Infinity,
		});

		setEditor(codeMirror);

		codeMirror.on('focus', (cm) => {
			if (hasEnabledTabKey(cm)) {
				cm.addKeyMap({
					'Shift-Tab': false,
					'Tab': false,
					'name': 'tabKey',
				});
			}
		});
	}, [editorWrapper]);

	useEffect(() => {
		if (editor) {
			const variableStart = '${';
			const variableEnd = '}';

			let wordList = [];

			try {
				wordList = Object.keys(autocompleteData.variables)
					.sort()
					.map((word) => ({lowerCaseWord: word.toLowerCase(), word}));
			}
			catch (error) {
				if (process.env.NODE_ENV === 'development') {
					console.error(
						'Error loading editor autocomplete data',
						error
					);
				}
			}

			const getWordContext = (cm) => {
				const currentRange = cm.findWordAt({
					...cm.getCursor(),
					sticky: 'before',
					xRel: 0,
				});

				const getRange = (range) => {
					return cm.getRange(range.anchor, range.head);
				};

				return {
					current: getRange(currentRange),
					next: getRange(
						cm.findWordAt(cm.findPosH(currentRange.head, 1, 'char'))
					),
					previous: getRange(
						cm.findWordAt(
							cm.findPosH(currentRange.anchor, -1, 'char')
						)
					),
				};
			};

			const hint = (cm) => {
				const {current, next, previous} = getWordContext(cm);
				const currentLowerCase = current.toLowerCase();
				const cursorPosition = cm.getCursor();

				const closeVariable = next !== variableEnd;
				const openVariable =
					current !== variableStart && previous !== variableStart;

				if (current === variableStart) {
					return {
						from: cursorPosition,
						list: wordList.map(({word}) => ({
							displayText: word,
							text: `${word}${closeVariable ? variableEnd : ''}`,
						})),
					};
				}

				return {
					from: {
						...cursorPosition,
						ch: cursorPosition.ch - current.length,
					},
					list: wordList
						.map(({lowerCaseWord, word}) => ({
							index: lowerCaseWord.indexOf(currentLowerCase),
							lowerCaseWord,
							word,
						}))
						.filter(({index}) => index >= 0)
						.sort(
							({index: indexA}, {index: indexB}) =>
								indexA - indexB
						)
						.map(({word}) => ({
							displayText: word,
							text: `${openVariable ? variableStart : ''}${word}${
								closeVariable ? variableEnd : ''
							}`,
						})),
					to: cursorPosition,
				};
			};

			editor.setOption('hintOptions', {
				completeSingle: false,
				hint: variableStart || variableEnd ? hint : null,
			});

			const handleEditorChange = (cm) => {
				const {current} = getWordContext(cm);

				if (current === variableStart) {
					cm.showHint();
				}
			};

			editor.on('change', handleEditorChange);

			return () => {
				editor.off('change', handleEditorChange);
			};
		}
	}, [autocompleteData, editor]);

	useEffect(() => {
		if (editor) {
			editor.setOption('mode', mode);
		}
	}, [editor, mode]);

	useEffect(() => {
		if (!editor) {
			return;
		}

		const handleChange = () => {
			onChange(editor.getValue());
		};

		editor.on('change', handleChange);

		return () => {
			editor.off('change', handleChange);
		};
	}, [editor, onChange]);

	useEffect(() => {
		if (editor && editor.getValue() !== content) {
			editor.setValue(content);
		}
	}, [content, editor]);

	useEffect(() => {
		if (inputChannel) {
			const removeListener = inputChannel.onData((data) => {
				editor?.replaceSelection(data);
			});

			return removeListener;
		}
	}, [editor, inputChannel]);

	return (
		<div
			className="ddm_template_editor__CodeMirrorEditor"
			ref={setEditorWrapper}
		/>
	);
}

CodeMirrorEditor.propTypes = {
	autocompleteData: PropTypes.object.isRequired,
	content: PropTypes.string.isRequired,
	inputChannel: PropTypes.shape({
		onData: PropTypes.func.isRequired,
	}),
	mode: PropTypes.object,
	onChange: PropTypes.func.isRequired,
};
