/**
 * SPDX-FileCopyrightText: © 2017 Liferay, Inc. <https://liferay.com>
 * SPDX-License-Identifier: MIT
 */

const DESCRIPTION =
	'Direct use of ReactDOM.render is discouraged; instead, use ' +
	'the <react:component /> JSP taglib, or do: ' +
	`import {render} from 'frontend-js-react-web';`;

module.exports = {
	create(context) {
		const isReactDOMImport = (node) => {
			const ancestors = context.getAncestors(node);

			const parent = ancestors[ancestors.length - 1];

			return (
				parent.type === 'ImportDeclaration' &&
				parent.source.type === 'Literal' &&
				parent.source.value === 'react-dom'
			);
		};

		// Returns true if `identifier` actually refers to
		// `variable`, by walking up the scope stack.

		const isSame = (identifier, variable) => {
			const name = variable.name;
			let scope = context.getScope(identifier);

			while (scope) {
				for (let i = 0; i < scope.variables.length; i++) {
					if (scope.variables[i] === variable) {
						return true;
					}
					else if (scope.variables[i].name === name) {

						// Variable is shadowed, so it is not the same.

						return false;
					}
				}
				scope = scope.upper;
			}
		};

		const report = (node) =>
			context.report({
				messageId: 'noReactDOMRender',
				node,
			});

		const foundBindings = new Set();
		const foundNamespaces = new Set();

		const add = (set, iterable) => {
			for (const item of iterable) {
				set.add(item);
			}
		};

		return {
			CallExpression(node) {
				if (
					node.callee.type === 'MemberExpression' &&
					node.callee.object.type === 'Identifier'
				) {

					// eg. Foo.bar()

					for (const namespace of foundNamespaces) {
						if (
							node.callee.object.name === namespace.name &&
							node.callee.property.type === 'Identifier' &&
							node.callee.property.name === 'render'
						) {

							// eg. Foo.render()

							if (isSame(node.callee.object, namespace)) {

								// "Foo" came from react-dom.

								report(node);
							}
						}
					}
				}
				else if (node.callee.type === 'Identifier') {

					// eg. something()

					for (const binding of foundBindings) {
						if (node.callee.name === binding.name) {

							// "something" came from react-dom.

							if (isSame(node, binding)) {
								report(node);
							}
						}
					}
				}
			},

			/**
			 * Check for:
			 *
			 *     import X from 'react-dom'
			 */
			ImportDefaultSpecifier(node) {
				if (isReactDOMImport(node)) {
					add(foundNamespaces, context.getDeclaredVariables(node));
				}
			},

			/**
			 * Check for:
			 *
			 *     import * as X from 'react-dom'
			 */
			ImportNamespaceSpecifier(node) {
				if (isReactDOMImport(node)) {
					add(foundNamespaces, context.getDeclaredVariables(node));
				}
			},

			/**
			 * Check for:
			 *
			 *     import {x} from 'react-dom';
			 *     import {x as y} from 'react-dom';
			 */
			ImportSpecifier(node) {
				if (isReactDOMImport(node)) {
					add(
						foundBindings,
						context
							.getDeclaredVariables(node)
							.filter((variable) => {
								return (
									variable.defs[0] &&
									variable.defs[0].node &&
									variable.defs[0].node.imported &&
									variable.defs[0].node.imported.name ===
										'render'
								);
							})
					);
				}
			},

			VariableDeclarator(node) {
				if (
					node.init &&
					node.init.type === 'CallExpression' &&
					node.init.callee.type === 'Identifier' &&
					node.init.callee.name === 'require' &&
					!!node.init.arguments.length &&
					node.init.arguments[0].type === 'Literal' &&
					node.init.arguments[0].value === 'react-dom'
				) {
					const variables = context.getDeclaredVariables(node);

					if (node.id.type === 'Identifier') {

						// eg. const x = require('react-dom');

						add(foundNamespaces, variables);
					}
					else if (node.id.type === 'ObjectPattern') {

						// eg. const {render} = require('react-dom');
						// eg. const {render: x} = require('react-dom');

						add(
							foundBindings,
							context
								.getDeclaredVariables(node)
								.filter((variable) => {
									return (
										variable.references[0] &&
										variable.references[0].identifier &&
										variable.references[0].identifier
											.parent &&
										variable.references[0].identifier.parent
											.key &&
										variable.references[0].identifier.parent
											.key.name === 'render'
									);
								})
						);
					}
				}
			},
		};
	},

	meta: {
		docs: {
			category: 'Best Practices',
			description: DESCRIPTION,
			recommended: false,
			url: 'https://issues.liferay.com/browse/LPS-99399',
		},
		fixable: null,
		messages: {
			noReactDOMRender: DESCRIPTION,
		},
		schema: [],
		type: 'problem',
	},
};
