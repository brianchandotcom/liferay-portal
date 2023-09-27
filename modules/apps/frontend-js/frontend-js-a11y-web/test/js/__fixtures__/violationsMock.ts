/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import type {ImpactValue} from 'axe-core';

import type {Violations} from '../../../src/main/resources/META-INF/resources/hooks/useA11y';

const nodeRule = (id: string, impact: ImpactValue, target: string) => ({
	all: [],
	any: [
		{
			data: ['tablist'],
			id,
			impact,
			message: 'Required ARIA parent role not present: tablist',
			relatedNodes: [],
		},
	],
	html:
		'<button class="nav-link btn btn-unstyled" type="button" aria-controls="_com_liferay_layout_content_page_editor_web_internal_portlet_ContentPageEditorPortlet__useId_10tabPanel0" id="_com_liferay_layout_content_page_editor_web_internal_portlet_ContentPageEditorPortlet__useId_10tab0" aria-disabled="true" aria-selected="false" role="tab">',
	impact,
	none: [],
	target: [target],
});

const violations = {
	iframes: {},
	nodes: {
		'html > node01': {
			'aria-required-parent-crit': nodeRule(
				'aria-required-parent-crit',
				'critical',
				'html > node01'
			),
		},
		'html > node02': {
			'aria-required-parent-crit': nodeRule(
				'aria-required-parent-crit',
				'critical',
				'html > node02'
			),
			'aria-required-parent-min': nodeRule(
				'aria-required-parent-min',
				'minor',
				'html > node02'
			),
		},
		'html > node03': {
			'aria-required-parent-crit': nodeRule(
				'aria-required-parent-crit',
				'critical',
				'html > node03'
			),
			'aria-required-parent-mod': nodeRule(
				'aria-required-parent-mod',
				'moderate',
				'html > node03'
			),
		},
		'html > node04': {
			'aria-required-parent-ser': nodeRule(
				'aria-required-parent-ser',
				'serious',
				'html > node04'
			),
		},
		'html > node05': {
			'aria-required-parent-ser': nodeRule(
				'aria-required-parent-ser',
				'serious',
				'html > node05'
			),
		},
		'html > node06': {
			'aria-required-parent-min': nodeRule(
				'aria-required-parent-min',
				'minor',
				'html > node06'
			),
			'aria-required-parent-mod': nodeRule(
				'aria-required-parent-mod',
				'moderate',
				'html > node06'
			),
		},
		'html > node07': {
			'aria-required-parent-min': nodeRule(
				'aria-required-parent-min',
				'minor',
				'html > node07'
			),
		},
	},
	rules: {
		'aria-required-parent-crit': {
			description:
				'Ensures elements with an ARIA role that require parent roles are contained by them',
			help: 'Certain ARIA roles must be contained by particular parents',
			helpUrl:
				'https://dequeuniversity.com/rules/axe/4.2/aria-required-parent?application=axeAPI',
			id: 'aria-required-parent-crit',
			impact: 'critical',
			nodes: ['html > node01', 'html > node02', 'html > node03'],
			tags: ['cat.aria', 'wcag2a', 'wcag131'],
		},
		'aria-required-parent-min': {
			description:
				'Ensures elements with an ARIA role that require parent roles are contained by them',
			help: 'Certain ARIA roles must be contained by particular parents',
			helpUrl:
				'https://dequeuniversity.com/rules/axe/4.2/aria-required-parent?application=axeAPI',
			id: 'aria-required-parent-min',
			impact: 'minor',
			nodes: ['html > node02', 'html > node06', 'html > node07'],
			tags: ['cat.aria', 'wcag2a', 'wcag131'],
		},
		'aria-required-parent-mod': {
			description:
				'Ensures elements with an ARIA role that require parent roles are contained by them',
			help: 'Certain ARIA roles must be contained by particular parents',
			helpUrl:
				'https://dequeuniversity.com/rules/axe/4.2/aria-required-parent?application=axeAPI',
			id: 'aria-required-parent-mod',
			impact: 'moderate',
			nodes: ['html > node03', 'html > node06'],
			tags: ['cat.aria', 'wcag2aa', 'wcag131'],
		},
		'aria-required-parent-ser': {
			description:
				'Ensures elements with an ARIA role that require parent roles are contained by them',
			help: 'Certain ARIA roles must be contained by particular parents',
			helpUrl:
				'https://dequeuniversity.com/rules/axe/4.2/aria-required-parent?application=axeAPI',
			id: 'aria-required-parent-ser',
			impact: 'serious',
			nodes: ['html > node04', 'html > node05'],
			tags: ['cat.aria', 'wcag2a', 'wcag131'],
		},
	},
} as Violations;

export default violations;
