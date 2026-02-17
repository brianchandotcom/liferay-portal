/**
 * SPDX-FileCopyrightText: © 2017 Liferay, Inc. <https://liferay.com>
 * SPDX-License-Identifier: MIT
 */

const DEPRECATED_MODULES = [
	'metal-affix',
	'metal-ajax',
	'metal-anim',
	'metal-aop',
	'metal-assertions',
	'metal-clipboard',
	'metal-debounce',
	'metal-dom',
	'metal-isomorphic',
	'metal-key',
	'metal-keyboard-focus',
	'metal-multimap',
	'metal-pagination',
	'metal-path-parser',
	'metal-position',
	'metal-promise',
	'metal-router',
	'metal-scrollspy',
	'metal-storage',
	'metal-structs',
	'metal-throttle',
	'metal-toggler',
	'metal-uri',
	'metal-useragent',
	'metal-web-component',
];

module.exports = {
	create(context) {
		return {
			ImportDeclaration(node) {
				if (
					node.source &&
					node.source.type === 'Literal' &&
					DEPRECATED_MODULES.includes(node.source.value)
				) {
					context.report({
						messageId: `no-${node.source.value}`,
						node,
					});
				}
			},
		};
	},

	meta: {
		docs: {
			category: 'Deprecated APIs',
			description:
				'metal-plugins are deprecated; use local utilities instead',
			recommended: false,
			url: 'https://issues.liferay.com/browse/LPS-96715',
		},
		fixable: null,
		messages: {
			'no-metal-affix': 'metal-affix is deprecated; use offset instead',
			'no-metal-ajax':
				"metal-ajax is deprecated; use `import {fetch} from 'frontend-js-web';` instead",
			'no-metal-anim':
				'metal-anim is deprecated; use `requestAnimationFrame()` instead',
			'no-metal-aop':
				"metal-aop is deprecated; use `import {AOP} from 'frontend-js-web';` instead",
			'no-metal-assertions':
				'metal-assertions is deprecated; use `typeof` instead',
			'no-metal-clipboard':
				'metal-clipboard is deprecated; use ClipboardJS instead',
			'no-metal-debounce':
				"metal-debounce is deprecated; use `import {debounce} from 'frontend-js-web';` instead",
			'no-metal-dom':
				'metal-dom is deprecated; see https://issues.liferay.com/browse/LPS-122383',
			'no-metal-isomorphic':
				'metal-isomorphic is deprecated; see https://github.com/liferay/liferay-frontend-projects/pull/456',
			'no-metal-key':
				'metal-key is deprecated; use key event handling instead',
			'no-metal-keyboard-focus':
				'metal-keyboard-focus is deprecated; use focus behaviour instead',
			'no-metal-multimap':
				'metal-multimap is deprecated; use Map instead',
			'no-metal-pagination':
				'metal-pagination is deprecated; use https://clayui.com/docs/components/pagination.html',
			'no-metal-path-parser':
				'metal-path-parser is deprecated; use string manipulation instead',
			'no-metal-position':
				"metal-position is deprecated; use `import {ALIGN_POSITIONS, align} from 'frontend-js-web';` instead",
			'no-metal-promise':
				'metal-promise is deprecated; use Promise instead',
			'no-metal-router':
				'metal-router is deprecated; see https://github.com/liferay/liferay-frontend-projects/pull/456',
			'no-metal-scrollspy':
				'metal-scrollspy is deprecated; use scroll instead',
			'no-metal-storage':
				'metal-storage is deprecated; use a local implementation instead',
			'no-metal-structs':
				'metal-structs is deprecated; use plain objects instead',
			'no-metal-throttle':
				"metal-throttle is deprecated; `import {throttle} from 'frontend-js-web';` instead",
			'no-metal-toggler':
				'metal-toggler is deprecated; use https://clayui.com/docs/components/toggle-switch.html',
			'no-metal-uri': 'metal-uri is deprecated; use URL instead',
			'no-metal-useragent':
				'metal-useragent is deprecated; use `Liferay.Browser` instead',
			'no-metal-web-component':
				'metal-web-component is deprecated; see https://github.com/liferay/liferay-frontend-projects/pull/456',
		},
		schema: [],
		type: 'problem',
	},
};
