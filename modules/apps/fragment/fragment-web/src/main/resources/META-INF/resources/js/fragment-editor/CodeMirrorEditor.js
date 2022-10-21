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

import 'codemirror/addon/hint/css-hint';

import 'codemirror/addon/hint/html-hint';

import 'codemirror/addon/hint/javascript-hint';

import 'codemirror/addon/hint/show-hint.css';

import 'codemirror/addon/hint/show-hint';

import 'codemirror/addon/hint/xml-hint';

import 'codemirror/lib/codemirror.css';

import 'codemirror/mode/css/css';

import 'codemirror/mode/htmlmixed/htmlmixed';

import 'codemirror/mode/javascript/javascript';

import 'codemirror/mode/xml/xml';
import ClayIcon from '@clayui/icon';
import {CodeMirrorKeyboardMessage} from '@liferay/layout-content-page-editor-web';
import CodeMirror from 'codemirror';
import React, {useEffect, useMemo, useRef, useState} from 'react';

const AUTOCOMPLETE_EXCLUDED_KEYS = new Set([
	' ',
	',',
	';',
	'Alt',
	'AltGraph',
	'AltRight',
	'ArrowDown',
	'ArrowLeft',
	'ArrowRight',
	'ArrowUp',
	'Backspace',
	'Control',
	'Enter',
	'Escape',
	'Delete',
	'Meta',
	'Return',
	'Shift',
]);

const MODES = {
	css: {
		name: 'CSS',
		type: 'text/css',
	},
	html: {
		hint: (cm, options) => {
			const {
				customDataAttributes,
				customEntities,
				customEntitiesSymbolsRegex,
				customTags,
			} = options;

			const cursor = cm.getCursor();
			const token = cm.getTokenAt(cursor);

			if (token.type && token.type !== 'string') {
				const content = token.string;

				const htmlCompletion = CodeMirror.hint.html(cm, options);

				if (!htmlCompletion) {
					return;
				}

				const resultSet = new Set(htmlCompletion.list);

				if (
					token.type === 'attribute' &&
					token.string.startsWith('data')
				) {
					customDataAttributes.forEach((item) => {
						let attributeName = `data-${item}`;
						let attributeValue = '';

						if (attributeName.indexOf(':') !== -1) {
							attributeValue = attributeName.substring(
								attributeName.indexOf(':') + 1
							);

							attributeName = attributeName.substring(
								0,
								attributeName.indexOf(':')
							);
						}

						if (
							attributeName.startsWith(content) &&
							!resultSet.has(attributeName)
						) {
							resultSet.add({
								displayText: `${attributeName}${
									attributeValue ? ':' + attributeValue : ''
								}`,
								text: `${attributeName}="${attributeValue}"`,
							});
						}
					});
				}
				else {
					customTags.forEach((item) => {
						if (
							item.name.startsWith(content) &&
							!resultSet.has(item.content)
						) {
							resultSet.add({
								displayText: item.name,
								text: item.content,
							});
						}
					});
				}

				return {
					...htmlCompletion,
					list: Array.from(resultSet),
				};
			}
			else if (customEntities && customEntitiesSymbolsRegex) {
				const line = cm.getLine(cursor.line).slice(0, cursor.ch);

				const match = (
					line.match(new RegExp(customEntitiesSymbolsRegex, 'g')) ||
					[]
				).pop();

				if (!match) {
					return;
				}

				const customEntity = customEntities.find((entity) =>
					match.startsWith(entity.start)
				);

				const content = match.slice(customEntity.start.length);

				const results = customEntity.content
					.filter((entityContent) =>
						entityContent.startsWith(content)
					)
					.map(
						(entityContent) =>
							`${customEntity.start}${entityContent}`
					);

				return {
					from: CodeMirror.Pos(cursor.line, cursor.ch - match.length),
					list: results,
					to: CodeMirror.Pos(cursor.line, cursor.ch),
				};
			}
		},
		name: 'HTML',
		type: 'text/html',
	},
	javascript: {
		name: 'JavaScript',
		type: 'text/javascript',
	},
	json: {
		name: 'JSON',
		type: 'application/json',
	},
};

const escapeChars = (string) => string.replace(/[.*+\-?^${}()|[\]\\]/g, '\\$&');

const noop = () => {};

const FixedText = ({helpText, text = ''}) => {
	return (
		<div className="source-editor__fixed-text">
			<code className="source-editor__fixed-text__content">{text}</code>

			{helpText && (
				<span
					className="float-right source-editor__fixed-text__help"
					data-title={helpText}
				>
					<ClayIcon
						className="icon-monospaced"
						symbol="question-circle-full"
					/>
				</span>
			)}
		</div>
	);
};

const CodeMirrorEditor = ({
	customDataAttributes,
	customEntities,
	customTags,
	onChange = noop,
	mode = 'html',
	codeFooterText,
	codeHeaderText,
	codeHeaderHelpText,
	content = '',
	readOnly,
	showHeader = true,
}) => {
	const editorRef = useRef();
	const ref = useRef();
	const [isEnabled, setIsEnabled] = useState(true);
	const [isFocused, setIsFocused] = useState(false);

	const customEntitiesSymbolsRegex = useMemo(() => {
		if (!customEntities) {
			return;
		}

		return `${customEntities
			.map((entity) => {
				const start = escapeChars(entity.start);
				const end = escapeChars(entity.end);

				return `${start}((?!\\s|${end}).)*(?:${end})?`;
			})
			.join('|')}$`;
	}, [customEntities]);

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
					'Ctrl-Space': readOnly ? '' : 'autocomplete',
				},
				foldGutter: true,
				gutters: ['CodeMirror-linenumbers', 'CodeMirror-foldgutter'],
				hintOptions: {
					completeSingle: false,
					customDataAttributes,
					customEntities,
					customEntitiesSymbolsRegex,
					customTags,
					hint: MODES[mode].hint,
				},
				indentWithTabs: true,
				inputStyle: 'contenteditable',
				lineNumbers: true,
				matchBrackets: true,
				mode: {globalVars: true, name: MODES[mode].type},
				readOnly,
				showHint: !readOnly,
				tabSize: 2,
				value: content,
				viewportMargin: Infinity,
			});

			codeMirror.on('change', (cm) => {
				onChange(cm.getValue());
			});

			codeMirror.on('keyup', (cm, event) => {
				if (
					!readOnly &&
					!cm.state.completionActive &&
					!AUTOCOMPLETE_EXCLUDED_KEYS.has(event.key)
				) {
					codeMirror.showHint();
				}
			});

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

			editorRef.current = codeMirror;
		}
	}, [ref]); // eslint-disable-line

	useEffect(() => {
		if (editorRef.current) {
			editorRef.current.setOption('mode', {
				globalVars: true,
				name: MODES[mode].type,
			});

			editorRef.current.setOption('readOnly', readOnly);

			editorRef.current.setOption('hintOptions', {
				...editorRef.current.getOption('hintOptions'),
				customEntities,
				customEntitiesSymbolsRegex,
				customTags,
			});
		}
	}, [
		customEntities,
		customEntitiesSymbolsRegex,
		customTags,
		mode,
		readOnly,
	]);

	useEffect(() => {
		if (editorRef.current) {
			editorRef.current.setValue(content);
		}
	}, [content]);

	return (
		<>
			{showHeader && (
				<nav className="source-editor-toolbar tbar">
					<ul className="tbar-nav">
						<li className="source-editor-toolbar__syntax tbar-item tbar-item-expand text-center">
							{MODES[mode].name}
						</li>
					</ul>
				</nav>
			)}

			{(codeHeaderHelpText || codeHeaderText) && (
				<FixedText
					helpText={codeHeaderHelpText}
					text={codeHeaderText}
				/>
			)}

			<div className="d-flex flex-column flex-grow-1 position-relative">
				{isFocused && !readOnly ? (
					<CodeMirrorKeyboardMessage keyIsEnabled={isEnabled} />
				) : null}

				<div
					aria-label={
						readOnly
							? null
							: Liferay.Language.get(
									'use-ctrl-m-to-enable-or-disable-the-tab-key'
							  )
					}
					className="codemirror-editor-wrapper h-100"
					ref={ref}
				></div>
			</div>

			{codeFooterText && <FixedText text={codeFooterText} />}
		</>
	);
};

export default CodeMirrorEditor;
