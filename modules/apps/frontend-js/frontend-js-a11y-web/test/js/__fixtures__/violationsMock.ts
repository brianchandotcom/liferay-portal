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

import type {Result} from 'axe-core';

const violations = [
	{
		description:
			'Ensures elements with an ARIA role that require parent roles are contained by them',
		help: 'Certain ARIA roles must be contained by particular parents',
		helpUrl:
			'https://dequeuniversity.com/rules/axe/4.2/aria-required-parent?application=axeAPI',
		id: 'aria-required-parent-crit',
		impact: 'critical',
		nodes: [
			{
				all: [],
				any: [
					{
						data: ['tablist'],
						id: 'aria-required-parent',
						impact: 'critical',
						message:
							'Required ARIA parent role not present: tablist',
						relatedNodes: [],
					},
				],
				html:
					'<button class="nav-link btn btn-unstyled" type="button" aria-controls="_com_liferay_layout_content_page_editor_web_internal_portlet_ContentPageEditorPortlet__useId_10tabPanel0" id="_com_liferay_layout_content_page_editor_web_internal_portlet_ContentPageEditorPortlet__useId_10tab0" aria-disabled="true" aria-selected="false" role="tab">',
				impact: 'critical',
				none: [],
				target: [
					'html > #senna_surface1 > .page-editor__sidebar > .page-editor__sidebar__content > .page-editor__sidebar__fragments-widgets-panel > .nav > .nav-item:nth-child(1) > #_com_liferay_layout_content_page_editor_web_internal_portlet_ContentPageEditorPortlet__useId_10tab0',
				],
			},
			{
				all: [],
				any: [
					{
						data: ['tablist'],
						id: 'aria-required-parent',
						impact: 'critical',
						message:
							'Required ARIA parent role not present: tablist',
						relatedNodes: [],
					},
				],
				html:
					'<button class="nav-link btn btn-unstyled" type="button" aria-controls="_com_liferay_layout_content_page_editor_web_internal_portlet_ContentPageEditorPortlet__useId_10tabPanel0" id="_com_liferay_layout_content_page_editor_web_internal_portlet_ContentPageEditorPortlet__useId_10tab0" aria-disabled="true" aria-selected="false" role="tab">',
				impact: 'critical',
				none: [],
				target: [
					'html > #senna_surface1 > .page-editor__sidebar > .page-editor__sidebar__content > .page-editor__sidebar__fragments-widgets-panel > .nav > .nav-item:nth-child(1) > #_com_liferay_layout_content_page_editor_web_internal_portlet_ContentPageEditorPortlet__useId_10tab0',
				],
			},
			{
				all: [],
				any: [
					{
						data: ['tablist'],
						id: 'aria-required-parent',
						impact: 'critical',
						message:
							'Required ARIA parent role not present: tablist',
						relatedNodes: [],
					},
				],
				html:
					'<button class="nav-link btn btn-unstyled" type="button" aria-controls="_com_liferay_layout_content_page_editor_web_internal_portlet_ContentPageEditorPortlet__useId_10tabPanel0" id="_com_liferay_layout_content_page_editor_web_internal_portlet_ContentPageEditorPortlet__useId_10tab0" aria-disabled="true" aria-selected="false" role="tab">',
				impact: 'critical',
				none: [],
				target: [
					'html > #senna_surface1 > .page-editor__sidebar > .page-editor__sidebar__content > .page-editor__sidebar__fragments-widgets-panel > .nav > .nav-item:nth-child(1) > #_com_liferay_layout_content_page_editor_web_internal_portlet_ContentPageEditorPortlet__useId_10tab0',
				],
			},
		],
		tags: ['cat.aria', 'wcag2a', 'wcag131'],
	},
	{
		description:
			'Ensures elements with an ARIA role that require parent roles are contained by them',
		help: 'Certain ARIA roles must be contained by particular parents',
		helpUrl:
			'https://dequeuniversity.com/rules/axe/4.2/aria-required-parent?application=axeAPI',
		id: 'aria-required-parent-ser',
		impact: 'serious',
		nodes: [
			{
				all: [],
				any: [
					{
						data: ['tablist'],
						id: 'aria-required-parent',
						impact: 'critical',
						message:
							'Required ARIA parent role not present: tablist',
						relatedNodes: [],
					},
				],
				html:
					'<button class="nav-link btn btn-unstyled" type="button" aria-controls="_com_liferay_layout_content_page_editor_web_internal_portlet_ContentPageEditorPortlet__useId_10tabPanel0" id="_com_liferay_layout_content_page_editor_web_internal_portlet_ContentPageEditorPortlet__useId_10tab0" aria-disabled="true" aria-selected="false" role="tab">',
				impact: 'critical',
				none: [],
				target: [
					'html > #senna_surface1 > .page-editor__sidebar > .page-editor__sidebar__content > .page-editor__sidebar__fragments-widgets-panel > .nav > .nav-item:nth-child(1) > #_com_liferay_layout_content_page_editor_web_internal_portlet_ContentPageEditorPortlet__useId_10tab0',
				],
			},
			{
				all: [],
				any: [
					{
						data: ['tablist'],
						id: 'aria-required-parent',
						impact: 'critical',
						message:
							'Required ARIA parent role not present: tablist',
						relatedNodes: [],
					},
				],
				html:
					'<button class="nav-link btn btn-unstyled" type="button" aria-controls="_com_liferay_layout_content_page_editor_web_internal_portlet_ContentPageEditorPortlet__useId_10tabPanel0" id="_com_liferay_layout_content_page_editor_web_internal_portlet_ContentPageEditorPortlet__useId_10tab0" aria-disabled="true" aria-selected="false" role="tab">',
				impact: 'critical',
				none: [],
				target: [
					'html > #senna_surface1 > .page-editor__sidebar > .page-editor__sidebar__content > .page-editor__sidebar__fragments-widgets-panel > .nav > .nav-item:nth-child(1) > #_com_liferay_layout_content_page_editor_web_internal_portlet_ContentPageEditorPortlet__useId_10tab0',
				],
			},
		],
		tags: ['cat.aria', 'wcag2a', 'wcag131'],
	},
	{
		description:
			'Ensures elements with an ARIA role that require parent roles are contained by them',
		help: 'Certain ARIA roles must be contained by particular parents',
		helpUrl:
			'https://dequeuniversity.com/rules/axe/4.2/aria-required-parent?application=axeAPI',
		id: 'aria-required-parent-mod',
		impact: 'moderate',
		nodes: [
			{
				all: [],
				any: [
					{
						data: ['tablist'],
						id: 'aria-required-parent',
						impact: 'critical',
						message:
							'Required ARIA parent role not present: tablist',
						relatedNodes: [],
					},
				],
				html:
					'<button class="nav-link btn btn-unstyled" type="button" aria-controls="_com_liferay_layout_content_page_editor_web_internal_portlet_ContentPageEditorPortlet__useId_10tabPanel0" id="_com_liferay_layout_content_page_editor_web_internal_portlet_ContentPageEditorPortlet__useId_10tab0" aria-disabled="true" aria-selected="false" role="tab">',
				impact: 'critical',
				none: [],
				target: [
					'html > #senna_surface1 > .page-editor__sidebar > .page-editor__sidebar__content > .page-editor__sidebar__fragments-widgets-panel > .nav > .nav-item:nth-child(1) > #_com_liferay_layout_content_page_editor_web_internal_portlet_ContentPageEditorPortlet__useId_10tab0',
				],
			},
			{
				all: [],
				any: [
					{
						data: ['tablist'],
						id: 'aria-required-parent',
						impact: 'critical',
						message:
							'Required ARIA parent role not present: tablist',
						relatedNodes: [],
					},
				],
				html:
					'<button class="nav-link btn btn-unstyled" type="button" aria-controls="_com_liferay_layout_content_page_editor_web_internal_portlet_ContentPageEditorPortlet__useId_10tabPanel0" id="_com_liferay_layout_content_page_editor_web_internal_portlet_ContentPageEditorPortlet__useId_10tab0" aria-disabled="true" aria-selected="false" role="tab">',
				impact: 'critical',
				none: [],
				target: [
					'html > #senna_surface1 > .page-editor__sidebar > .page-editor__sidebar__content > .page-editor__sidebar__fragments-widgets-panel > .nav > .nav-item:nth-child(1) > #_com_liferay_layout_content_page_editor_web_internal_portlet_ContentPageEditorPortlet__useId_10tab0',
				],
			},
			{
				all: [],
				any: [
					{
						data: ['tablist'],
						id: 'aria-required-parent',
						impact: 'critical',
						message:
							'Required ARIA parent role not present: tablist',
						relatedNodes: [],
					},
				],
				html:
					'<button class="nav-link btn btn-unstyled" type="button" aria-controls="_com_liferay_layout_content_page_editor_web_internal_portlet_ContentPageEditorPortlet__useId_10tabPanel0" id="_com_liferay_layout_content_page_editor_web_internal_portlet_ContentPageEditorPortlet__useId_10tab0" aria-disabled="true" aria-selected="false" role="tab">',
				impact: 'critical',
				none: [],
				target: [
					'html > #senna_surface1 > .page-editor__sidebar > .page-editor__sidebar__content > .page-editor__sidebar__fragments-widgets-panel > .nav > .nav-item:nth-child(1) > #_com_liferay_layout_content_page_editor_web_internal_portlet_ContentPageEditorPortlet__useId_10tab0',
				],
			},
		],
		tags: ['cat.aria', 'wcag2aa', 'wcag131'],
	},
	{
		description:
			'Ensures elements with an ARIA role that require parent roles are contained by them',
		help: 'Certain ARIA roles must be contained by particular parents',
		helpUrl:
			'https://dequeuniversity.com/rules/axe/4.2/aria-required-parent?application=axeAPI',
		id: 'aria-required-parent-min',
		impact: 'minor',
		nodes: [
			{
				all: [],
				any: [
					{
						data: ['tablist'],
						id: 'aria-required-parent',
						impact: 'critical',
						message:
							'Required ARIA parent role not present: tablist',
						relatedNodes: [],
					},
				],
				html:
					'<button class="nav-link btn btn-unstyled" type="button" aria-controls="_com_liferay_layout_content_page_editor_web_internal_portlet_ContentPageEditorPortlet__useId_10tabPanel0" id="_com_liferay_layout_content_page_editor_web_internal_portlet_ContentPageEditorPortlet__useId_10tab0" aria-disabled="true" aria-selected="false" role="tab">',
				impact: 'critical',
				none: [],
				target: [
					'html > #senna_surface1 > .page-editor__sidebar > .page-editor__sidebar__content > .page-editor__sidebar__fragments-widgets-panel > .nav > .nav-item:nth-child(1) > #_com_liferay_layout_content_page_editor_web_internal_portlet_ContentPageEditorPortlet__useId_10tab0',
				],
			},
			{
				all: [],
				any: [
					{
						data: ['tablist'],
						id: 'aria-required-parent',
						impact: 'critical',
						message:
							'Required ARIA parent role not present: tablist',
						relatedNodes: [],
					},
				],
				html:
					'<button class="nav-link btn btn-unstyled" type="button" aria-controls="_com_liferay_layout_content_page_editor_web_internal_portlet_ContentPageEditorPortlet__useId_10tabPanel0" id="_com_liferay_layout_content_page_editor_web_internal_portlet_ContentPageEditorPortlet__useId_10tab0" aria-disabled="true" aria-selected="false" role="tab">',
				impact: 'critical',
				none: [],
				target: [
					'html > #senna_surface1 > .page-editor__sidebar > .page-editor__sidebar__content > .page-editor__sidebar__fragments-widgets-panel > .nav > .nav-item:nth-child(1) > #_com_liferay_layout_content_page_editor_web_internal_portlet_ContentPageEditorPortlet__useId_10tab0',
				],
			},
			{
				all: [],
				any: [
					{
						data: ['tablist'],
						id: 'aria-required-parent',
						impact: 'critical',
						message:
							'Required ARIA parent role not present: tablist',
						relatedNodes: [],
					},
				],
				html:
					'<button class="nav-link btn btn-unstyled" type="button" aria-controls="_com_liferay_layout_content_page_editor_web_internal_portlet_ContentPageEditorPortlet__useId_10tabPanel0" id="_com_liferay_layout_content_page_editor_web_internal_portlet_ContentPageEditorPortlet__useId_10tab0" aria-disabled="true" aria-selected="false" role="tab">',
				impact: 'critical',
				none: [],
				target: [
					'html > #senna_surface1 > .page-editor__sidebar > .page-editor__sidebar__content > .page-editor__sidebar__fragments-widgets-panel > .nav > .nav-item:nth-child(1) > #_com_liferay_layout_content_page_editor_web_internal_portlet_ContentPageEditorPortlet__useId_10tab0',
				],
			},
		],
		tags: ['cat.aria', 'wcag2a', 'wcag131'],
	},
	{
		description:
			'Ensures elements with an ARIA role that require parent roles are contained by them',
		help: 'Certain ARIA roles must be contained by particular parents',
		helpUrl:
			'https://dequeuniversity.com/rules/axe/4.2/aria-required-parent?application=axeAPI',
		id: 'aria-required-parent-crit-2',
		impact: 'critical',
		nodes: [
			{
				all: [],
				any: [
					{
						data: ['tablist'],
						id: 'aria-required-parent',
						impact: 'critical',
						message:
							'Required ARIA parent role not present: tablist',
						relatedNodes: [],
					},
				],
				html:
					'<button class="nav-link btn btn-unstyled" type="button" aria-controls="_com_liferay_layout_content_page_editor_web_internal_portlet_ContentPageEditorPortlet__useId_10tabPanel0" id="_com_liferay_layout_content_page_editor_web_internal_portlet_ContentPageEditorPortlet__useId_10tab0" aria-disabled="true" aria-selected="false" role="tab">',
				impact: 'critical',
				none: [],
				target: [
					'html > #senna_surface1 > .page-editor__sidebar > .page-editor__sidebar__content > .page-editor__sidebar__fragments-widgets-panel > .nav > .nav-item:nth-child(1) > #_com_liferay_layout_content_page_editor_web_internal_portlet_ContentPageEditorPortlet__useId_10tab0',
				],
			},
			{
				all: [],
				any: [
					{
						data: ['tablist'],
						id: 'aria-required-parent',
						impact: 'critical',
						message:
							'Required ARIA parent role not present: tablist',
						relatedNodes: [],
					},
				],
				html:
					'<button class="nav-link btn btn-unstyled" type="button" aria-controls="_com_liferay_layout_content_page_editor_web_internal_portlet_ContentPageEditorPortlet__useId_10tabPanel0" id="_com_liferay_layout_content_page_editor_web_internal_portlet_ContentPageEditorPortlet__useId_10tab0" aria-disabled="true" aria-selected="false" role="tab">',
				impact: 'critical',
				none: [],
				target: [
					'html > #senna_surface1 > .page-editor__sidebar > .page-editor__sidebar__content > .page-editor__sidebar__fragments-widgets-panel > .nav > .nav-item:nth-child(1) > #_com_liferay_layout_content_page_editor_web_internal_portlet_ContentPageEditorPortlet__useId_10tab0',
				],
			},
			{
				all: [],
				any: [
					{
						data: ['tablist'],
						id: 'aria-required-parent',
						impact: 'critical',
						message:
							'Required ARIA parent role not present: tablist',
						relatedNodes: [],
					},
				],
				html:
					'<button class="nav-link btn btn-unstyled" type="button" aria-controls="_com_liferay_layout_content_page_editor_web_internal_portlet_ContentPageEditorPortlet__useId_10tabPanel0" id="_com_liferay_layout_content_page_editor_web_internal_portlet_ContentPageEditorPortlet__useId_10tab0" aria-disabled="true" aria-selected="false" role="tab">',
				impact: 'critical',
				none: [],
				target: [
					'html > #senna_surface1 > .page-editor__sidebar > .page-editor__sidebar__content > .page-editor__sidebar__fragments-widgets-panel > .nav > .nav-item:nth-child(1) > #_com_liferay_layout_content_page_editor_web_internal_portlet_ContentPageEditorPortlet__useId_10tab0',
				],
			},
		],
		tags: ['cat.aria', 'wcag2a', 'wcag131'],
	},
] as Array<Result>;

export default violations;
