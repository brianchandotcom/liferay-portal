/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
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

import 'codemirror/addon/hint/show-hint.css';

import 'codemirror/addon/hint/show-hint';

import 'codemirror/lib/codemirror.css';

import 'codemirror/mode/javascript/javascript';
import CodeMirror from 'codemirror';
import React, {useContext, useEffect, useRef} from 'react';

import ThemeContext from './ThemeContext';

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
	'Control',
	'Enter',
	'Escape',
	'Delete',
	'Meta',
	'Return',
	'Shift',
]);

const CSS_CLASS_HINT_TYPE = 'hint-type';

const MODES = {
	json: {
		name: 'JSON',
		type: 'application/json',
	},
};

function getCodeMirrorHints(cm, autocompleteSchema) {
	const cursor = cm.getCursor();
	const token = cm.getTokenAt(cursor);

	const start = token.start - 1;
	const end = token.end;

	if (token.type !== 'string property') {
		return;
	}

	// Build a path list of parent properties.

	// Get brackets ('{', '}', '[', ']') and properties.

	// Example `propertyBracketList` value:
	// ['{', 'description_i18n', '{', 'en_US']

	let propertyBracketList = [];

	for (let currentLine = 0; currentLine <= cursor.line; currentLine++) {
		const linePropertyBracketList = cm
			.getLineTokens(currentLine)
			.filter((token) => {

				// Get tokens only before the cursor.

				if (currentLine === cursor.line && token.end > cursor.ch) {
					return false;
				}

				return (
					token.string === '{' ||
					token.string === '}' ||
					token.string === '[' ||
					token.string === ']' ||
					isObjectProperty(token)
				);
			})
			.map((token) => removeQuotes(token.string));

		propertyBracketList = [
			...propertyBracketList,
			...linePropertyBracketList,
		];
	}

	// Filter the `propertyBracketList` to get only the parent properties.

	// Example value of `propertyPathList`:
	// [
	//   {"name": "elementDefinition", "type": "object"},
	//   {"name": "uiConfiguration", "type": "object"}
	// ]

	const propertyPathList = [];

	while (propertyBracketList.length > 0) {
		const lastItem = propertyBracketList.pop();

		if (lastItem === '}' || lastItem === ']') {

			// If a closing bracket `}` or `]` is found, trim the list to the
			// opening bracket.

			trimToOpeningBracket(propertyBracketList, lastItem);
		}
		else if (
			(lastItem === '{' || lastItem === '[') &&
			propertyBracketList.length > 1
		) {
			const nextItem = propertyBracketList.pop();

			if (nextItem === '}' || nextItem === ']') {

				// Continue to trim if the next item is a closing bracket.

				continue;
			}
			else if (nextItem === '[') {

				// '[' can be before '{' so remove the '[' and assume the next
				// item is the property name.

				propertyPathList.push({
					name: propertyBracketList.pop(),
					type: 'array',
				});
			}
			else {
				propertyPathList.push({name: nextItem, type: 'object'});
			}
		}
	}

	// Reverse so parent-most property is first.

	propertyPathList.reverse();

	// Get property autocomplete items.

	let list = getSchemaProperties(autocompleteSchema, propertyPathList);

	// Filter matched strings.

	const search = token.string.match(/[@]?\w+/);

	if (search !== null) {
		list = list.filter((item) => {
			return item.name.indexOf(search) > -1;
		});
	}

	return {
		from: CodeMirror.Pos(cursor.line, start + 2),
		list: list.map((item) => {
			return {

				// The `#` character is used to pass the `type` to the `render`
				// method.

				displayText: `${item.name}#${item.type}`,
				render: (element, cm, data) => {
					const [propertyName, propertyType] = data.displayText.split(
						'#'
					);

					const name = document.createElement('span');
					name.innerText = propertyName;

					const type = document.createElement('span');
					type.className = CSS_CLASS_HINT_TYPE;
					type.innerText = propertyType;

					element.appendChild(name);
					element.appendChild(type);
				},
				text: `${item.name}"`,
				...getCustomHintProperties(
					item,
					cm.getTokenAt(CodeMirror.Pos(cursor.line, end + 1))
				),
			};
		}),
		to: CodeMirror.Pos(cursor.line, end),
	};
}

/**
 * Additional properties to override the default CodeMirror hint properties.
 * Customizes behavior of the picked hint according to the `type` of the item.
 * @param {object} item An hint item with a `name` and `type` property.
 * @returns
 */
function getCustomHintProperties(item, endToken) {
	return {
		hint: (cm, data, completion) => {
			let text = `${item.name}"`;

			// Check characters after if any property value is defined already.

			if (endToken.string?.startsWith('"') || endToken.string === '') {
				switch (item.type) {
					case 'array':
						text = `${item.name}": []`;
						break;
					case 'object':
						text = `${item.name}": {}`;
						break;
					case 'string':
						text = `${item.name}": ""`;
						break;
					default:
						text = `${item.name}": `;
						break;
				}
			}

			// Similar to default hint behavior.
			// @see https://codemirror.net/addon/hint/show-hint.js

			cm.replaceRange(
				text,
				completion.from || data.from,
				completion.to || data.to,
				'complete'
			);

			// Position cursor between brackets or quotes.

			const cursor = cm.getCursor();

			cm.setCursor(cursor.line, cursor.ch - 1);
		},
	};
}

/**
 * Traverses an object to find a specific value by a property path.
 * @see getSchemaProperties
 * @param {object} object The object to traverse.
 * @param {string} path A slash-delimited path to traverse.
 * @param {string} [delimiter] The delimiter to use for splitting the path.
 * @returns
 */
function getDeepValue(object, path, delimiter = '/') {
	const pathList = path.split(delimiter);

	for (let i = 0; i < pathList.length; i++) {
		object = object[pathList[i]];
	}

	return object;
}

/**
 * Gets the properties for a specific schema definition. Uses a propertyPathList
 * (i.e. ['elementDefinition', configuration']) to recursively traverse the
 * schema. `propertyPathList` should be sorted with the parent-most property
 * first, like reading a breadcrumb.
 * @param {object} schema The current evaluated JSON schema object.
 * @param {Array} propertyPathList A list of parent properties to traverse.
 * @param {object} [fullSchema] The original JSON schema object needed for
 * 	parsing $refs. Only used in recursion.
 * @returns {Array} List of objects with `name` and `type` properties.
 */
function getSchemaProperties(schema, propertyPathList, fullSchema) {

	// Fallback to empty array to avoid undefined errors.

	if (!schema || !propertyPathList) {
		return [];
	}

	// If the schema links to a reference ($ref), forward to the referenced
	// schema.

	if (schema.$ref) {

		// Check if reference is in the same schema. Only same schema references
		// are supported (i.e. "#/definitions/test").

		if (schema.$ref.substring(0, 1) === '#') {
			const refSchema = getDeepValue(
				fullSchema,
				schema.$ref.substring(2)
			);

			return getSchemaProperties(refSchema, propertyPathList, fullSchema);
		}

		// Throw warning for unsupported reference and return empty array.

		if (process.env.NODE_ENV === 'development') {
			console.warn('Unable to parse $ref', schema.$ref);
		}

		return [];
	}

	// If `propertyPathList` is empty, return the schema properties.

	if (propertyPathList.length === 0) {
		const propertyNames = Object.keys(schema.properties || {});

		return propertyNames.map((name) => {

			// Get `type` value, forward $ref reference if defined.

			let type = schema.properties[name].type || '';

			if (
				schema.properties[name].$ref &&
				schema.properties[name].$ref.substring(0, 1) === '#'
			) {

				// Fallback to schema, assuming it's the full schema since
				// it never reached the recursion below where it would have
				// been defined.

				const refSchema = getDeepValue(
					fullSchema || schema,
					schema.properties[name].$ref.substring(2)
				);

				type = refSchema.type || '';
			}

			return {
				name,
				type,
			};
		});
	}

	// If `propertyPathList` is not empty, traverse the schema.

	const property = propertyPathList[0];

	if (schema.properties && schema.properties[property.name]) {

		// Set fullSchema for recursive calls that might need to reference it.
		// This will only be called on the first `getSchemaProperties` call.

		if (!fullSchema) {
			fullSchema = schema;
		}

		if (property.type === 'array') {
			return getSchemaProperties(
				schema.properties[property.name].items,
				propertyPathList.slice(1),
				fullSchema
			);
		}
		else {
			return getSchemaProperties(
				schema.properties[property.name],
				propertyPathList.slice(1),
				fullSchema
			);
		}
	}

	return [];
}

/**
 * Checks if a Code Mirror token is a object property. For example "name" in
 * {"name": "test"}.
 * @param {Token} token Code Mirror token
 * @returns {boolean}
 */
function isObjectProperty(token) {
	return (
		token.type === 'string property' &&
		token.string.length > 1 &&
		token.string.startsWith('"') &&
		token.string.endsWith('"')
	);
}

/**
 * Removes quotes from a string. For example "test" -> test.
 * @param {string} value
 * @returns {string}
 */
function removeQuotes(value) {
	return value.replace(/^"(.*)"$/, '$1');
}

/**
 * Removes the items up to it's matching opening bracket. This function mutates
 * `propertyBracketList`.
 *
 * Given a `propertyBracketList` (notice the last '}' is not in the list):
 * ['{', 'description_i18n', '{', '}', 'title_i18n', '{']
 *
 * The mutated `propertyBracketList` will be:
 * ['{', 'description_i18n', '{', '}', 'title_i18n'].
 *
 * @param {Array} propertyBracketList
 * @param {string} closingBracketCharacter
 */
function trimToOpeningBracket(propertyBracketList, closingBracketCharacter) {
	const lastItem = propertyBracketList.pop();

	if (lastItem === '}') {
		trimToOpeningBracket(propertyBracketList, '}');
	}
	else if (lastItem === ']') {
		trimToOpeningBracket(propertyBracketList, ']');
	}

	let openBracketMatch = '{';

	if (closingBracketCharacter === ']') {
		openBracketMatch = '[';
	}

	if (lastItem !== openBracketMatch && propertyBracketList.length > 1) {
		trimToOpeningBracket(propertyBracketList, closingBracketCharacter);
	}
}

/**
 * Reusing the `ref` from `forwardRef` with React hooks
 * https://itnext.io/reusing-the-ref-from-forwardref-with-react-hooks-4ce9df693dd
 */

function useCombinedRefs(...refs) {
	const targetRef = React.useRef();

	React.useEffect(() => {
		refs.forEach((ref) => {
			if (!ref) {
				return;
			}

			if (typeof ref === 'function') {
				ref(targetRef.current);
			}
			else {
				ref.current = targetRef.current;
			}
		});
	}, [refs]);

	return targetRef;
}

const CodeMirrorEditor = React.forwardRef(
	(
		{
			autocompleteSchema,
			folded = false,
			lineWrapping = true,
			onChange = () => {},
			mode = 'json',
			value = '',
			readOnly = false,
		},
		ref
	) => {
		const innerRef = useRef(ref);
		const editorWrapperRef = useRef();
		const editorRef = useCombinedRefs(ref, innerRef);
		const {jsonAutocompleteEnabled} = useContext(ThemeContext);

		useEffect(() => {
			if (editorWrapperRef.current) {
				const codeMirror = CodeMirror(editorWrapperRef.current, {
					autoCloseBrackets: true,
					autoCloseTags: true,
					autoRefresh: true,
					extraKeys: {
						'Ctrl-Space': 'autocomplete',
					},
					foldGutter: true,
					gutters: [
						'CodeMirror-linenumbers',
						'CodeMirror-foldgutter',
					],
					hintOptions: {
						completeSingle: false,
					},
					indentWithTabs: true,
					inputStyle: 'contenteditable',
					lineNumbers: true,
					lineWrapping,
					matchBrackets: true,
					mode: {globalVars: true, name: MODES[mode].type},
					readOnly,
					tabSize: 2,
					value,
				});

				codeMirror.on('change', (cm) => {
					onChange(cm.getValue());
				});

				// Enable autocomplete if `autocompleteSchema` is defined.

				if (autocompleteSchema && jsonAutocompleteEnabled) {
					CodeMirror.registerHelper('hint', 'json', (cm) =>
						getCodeMirrorHints(cm, autocompleteSchema)
					);

					codeMirror.on('keyup', (cm, event) => {
						if (
							!cm.state.completionActive &&
							!AUTOCOMPLETE_EXCLUDED_KEYS.has(event.key)
						) {
							codeMirror.showHint();
						}
					});
				}

				if (folded) {
					codeMirror.operation(() => {
						for (
							let line = codeMirror.firstLine() + 1;
							line <= codeMirror.lastLine() - 1;
							++line
						) {
							codeMirror.foldCode({ch: 0, line}, null, 'fold');
						}
					});
				}

				editorRef.current = codeMirror;
			}
		}, [editorWrapperRef]); // eslint-disable-line

		return (
			<div
				className="codemirror-editor-wrapper"
				ref={editorWrapperRef}
			></div>
		);
	}
);

export default CodeMirrorEditor;
