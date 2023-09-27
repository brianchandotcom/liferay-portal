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

import 'codemirror/addon/hint/show-hint.css';

import 'codemirror/addon/hint/show-hint';

import 'codemirror/lib/codemirror.css';

import 'codemirror/mode/javascript/javascript';
import ClayLoadingIndicator from '@clayui/loading-indicator';
import getCN from 'classnames';
import CodeMirror from 'codemirror';
import React, {useContext, useEffect, useRef} from 'react';

import useAsyncCall from '../hooks/useAsyncCall';
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

const CSS_CLASS_HINT_NAME = 'hint-name';
const CSS_CLASS_HINT_TYPE = 'hint-type';

const LANGUAGE_ID = 'LanguageId';

const MODES = {
	json: {
		name: 'JSON',
		type: 'application/json',
	},
};

export function getCodeMirrorHints(cm, autocompleteSchema, availableLanguages) {
	const cursor = cm.getCursor();
	const token = cm.getTokenAt(cursor);

	const start = token.start - 1;
	const end = token.end;

	if (token.type !== 'string property' && token.type !== 'string') {
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

	// Get the property on current line to check for an enum list later.

	const currentProperty = cm
		.getLineTokens(cursor.line)
		.filter((token) => {
			if (token.end > cursor.ch) {
				return false;
			}

			return (
				token.string.length > 1 &&
				token.string.startsWith('"') &&
				token.string.endsWith('"')
			);
		})
		.map((token) => removeQuotes(token.string));

	// Filter the `propertyBracketList` to get only the parent properties.

	// Example value of `propertyPathList`:
	// [
	//   {"name": "elementDefinition", "type": "object"},
	//   {"name": "uiConfiguration", "type": "object"}
	// ]

	const propertyPathList = [];

	while (propertyBracketList.length) {
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

	let list = getSchemaProperties(
		autocompleteSchema,
		propertyPathList,
		availableLanguages
	);

	const search = token.string.match(/[@]?\w+/);

	// Return a filtered enum list if the property on the current line
	// matches a property inside schemaProperties with an enum.

	if (token.type === 'string') {
		const property = list.find(
			(item) => item.name === currentProperty[currentProperty.length - 1]
		);

		if (property?.enum) {
			let enumList = property.enum;

			if (search !== null) {
				enumList = property.enum.filter(
					(item) =>
						item.toLowerCase().indexOf(search[0].toLowerCase()) > -1
				);
			}

			return {
				from: CodeMirror.Pos(cursor.line, start + 2),
				list: enumList.map((item) => {
					return {
						displayText: `${item}`,
						text: `${item}"`,
					};
				}),

				to: CodeMirror.Pos(cursor.line, end),
			};
		}

		return;
	}

	// Filter matched strings.

	if (search !== null) {
		list = list.filter(
			(item) =>
				item.name.toLowerCase().indexOf(search[0].toLowerCase()) > -1
		);
	}

	return {
		from: CodeMirror.Pos(cursor.line, start + 2),
		list: list.map((item) => {
			return {

				// The `#` character is used to pass the `type` to the `render`
				// method.

				displayText: `${item.name}#${
					Array.isArray(item.type) ? item.type.join('|') : item.type
				}`,
				render: (element, cm, data) => {
					const [propertyName, propertyType] = data.displayText.split(
						'#'
					);

					const name = document.createElement('span');
					name.className = CSS_CLASS_HINT_NAME;
					name.textContent = propertyName;

					const type = document.createElement('span');
					type.className = CSS_CLASS_HINT_TYPE;
					type.textContent = propertyType;

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

				// For special case "aggs" within aggregation configuration, autofill
				// with this snippet to show how aggregation types are structured.

				if (item.name === 'aggs') {
					const indentedTabs =
						endToken.state.indented / cm.getOption('indentUnit');

					const aggsText = JSON.stringify(
						{NAME: {AGG_TYPE: {}}},
						null,
						'\t'
					).replace(/\n/g, '\n' + '\t'.repeat(indentedTabs));

					text = `aggs": ${aggsText}`;
				}
				else {
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
 * @param {object} availableLanguages The available languages object to use
 * for i18n properties.
 * @param {object} [fullSchema] The original JSON schema object needed for
 * 	parsing $refs. Only used in recursion.
 * @returns {Array} List of objects with `name` and `type` properties.
 */
function getSchemaProperties(
	schema,
	propertyPathList,
	availableLanguages,
	fullSchema
) {

	// Fallback to empty array to avoid undefined errors.

	if (!schema || !propertyPathList) {
		return [];
	}

	// Set fullSchema for recursive calls that might need to reference it.
	// This will only be called on the first `getSchemaProperties` call.

	if (!fullSchema) {
		fullSchema = schema;
	}

	// If an `allOf` or `anyOf` property is available, separate the schema
	// and getSchemaProperties from each one.

	if (schema.allOf || schema.anyOf) {
		const {allOf, anyOf, ...restOfSchema} = schema;

		const ofProperties = (allOf || anyOf).map((item) =>
			getSchemaProperties(
				item,
				propertyPathList,
				availableLanguages,
				fullSchema
			)
		);

		return [
			...getSchemaProperties(
				restOfSchema,
				propertyPathList,
				availableLanguages,
				fullSchema
			),
			...removeDuplicateProperties(ofProperties.flat()),
		];
	}

	// If the schema links to a reference ($ref), forward to the referenced
	// schema.

	if (schema.$ref) {

		// Check if the reference is for Language IDs, which is dynamically set

		if (schema.$ref === LANGUAGE_ID) {
			return Object.keys(availableLanguages).map((language) => ({
				name: language,
				type: 'string',
			}));
		}

		// Check if reference is in the same schema. Only same schema references
		// are supported (i.e. "#/definitions/test").

		if (schema.$ref.substring(0, 1) === '#') {
			const refSchema = getDeepValue(
				fullSchema,
				schema.$ref.substring(2)
			);

			return getSchemaProperties(
				refSchema,
				propertyPathList,
				availableLanguages,
				fullSchema
			);
		}

		// Throw warning for unsupported reference and return empty array.

		if (process.env.NODE_ENV === 'development') {
			console.warn('Unable to parse $ref', schema.$ref);
		}

		return [];
	}

	// If `propertyPathList` is empty, return the schema properties.

	if (!propertyPathList.length) {
		const propertyNames = Object.keys(schema.properties || {});

		return propertyNames.map((name) => {

			// Get `type` value, forward $ref reference if defined.

			let enumList = schema.properties[name].enum;
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

				enumList = refSchema.enum;
				type = refSchema.type || '';
			}

			if (type === 'string' && enumList) {
				return {enum: enumList, name, type};
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
		if (property.type === 'array') {
			return getSchemaProperties(
				schema.properties[property.name].items,
				propertyPathList.slice(1),
				availableLanguages,
				fullSchema
			);
		}
		else {
			return getSchemaProperties(
				schema.properties[property.name],
				propertyPathList.slice(1),
				availableLanguages,
				fullSchema
			);
		}
	}

	// If property is not available in schema's properties, persist with
	// schema's additionalProperties instead.

	if (schema.additionalProperties) {
		return getSchemaProperties(
			schema.additionalProperties,
			propertyPathList.slice(1),
			availableLanguages,
			fullSchema
		);
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
 * Removes any duplicate properties in an array with the same type and name.
 * This could happen in some cases like when flattening properties from `anyOf`
 * in a schema.
 * @param {Array} properties An array of object properties. Each property should
 * have a name and type.
 * @returns {Array}
 */
function removeDuplicateProperties(properties) {
	const uniqueProperties = [];

	properties.forEach((property) => {
		if (
			uniqueProperties.findIndex(
				({name, type}) =>
					name === property.name &&
					type.toString() === property.type.toString()
			) === -1
		) {
			uniqueProperties.push(property);
		}
	});

	return uniqueProperties;
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
			foldInitializationDelay = 0,
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
		const {availableLanguages} = useContext(ThemeContext);

		const [foldLoading] = useAsyncCall(() => {
			if (folded && editorRef.current) {
				editorRef.current.operation(() => {
					for (
						let line = editorRef.current.firstLine() + 1;
						line <= editorRef.current.lastLine() - 1;
						++line
					) {
						editorRef.current.foldCode({ch: 0, line}, null, 'fold');
					}
				});
			}
		}, foldInitializationDelay);

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

				if (autocompleteSchema) {
					codeMirror.on('keyup', (cm, event) => {
						const hint = () =>
							getCodeMirrorHints(
								cm,
								autocompleteSchema,
								availableLanguages
							);

						if (
							!cm.state.completionActive &&
							!AUTOCOMPLETE_EXCLUDED_KEYS.has(event.key)
						) {
							codeMirror.showHint({hint});
						}
					});
				}

				editorRef.current = codeMirror;
			}
		}, [editorWrapperRef]); // eslint-disable-line

		return (
			<>
				{foldLoading && (
					<div className="codemirror-loading-state">
						<ClayLoadingIndicator />
					</div>
				)}

				<div
					className={getCN('codemirror-editor-wrapper', {
						hide: foldLoading,
					})}
					ref={editorWrapperRef}
				></div>
			</>
		);
	}
);

export default CodeMirrorEditor;
