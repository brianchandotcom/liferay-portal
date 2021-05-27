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

/* eslint-disable */

export default violations = [
	{
		id: 'aria-required-parent-crit',
		impact: 'critical',
		tags: ['cat.aria', 'wcag2a', 'wcag131'],
		description:
			'Ensures elements with an ARIA role that require parent roles are contained by them',
		help: 'Certain ARIA roles must be contained by particular parents',
		helpUrl:
			'https://dequeuniversity.com/rules/axe/4.2/aria-required-parent?application=axeAPI',
		nodes: [
			{
				any: [
					{
						id: 'aria-required-parent',
						data: ['tablist'],
						relatedNodes: [],
						impact: 'critical',
						message:
							'Required ARIA parent role not present: tablist',
					},
				],
				all: [],
				none: [],
				impact: 'critical',
				html:
					'<button class="nav-link btn btn-unstyled" type="button" aria-controls="_com_liferay_layout_content_page_editor_web_internal_portlet_ContentPageEditorPortlet__useId_10tabPanel0" id="_com_liferay_layout_content_page_editor_web_internal_portlet_ContentPageEditorPortlet__useId_10tab0" aria-disabled="true" aria-selected="false" role="tab">',
				target: [
					'html > #senna_surface1 > .page-editor__sidebar > .page-editor__sidebar__content > .page-editor__sidebar__fragments-widgets-panel > .nav > .nav-item:nth-child(1) > #_com_liferay_layout_content_page_editor_web_internal_portlet_ContentPageEditorPortlet__useId_10tab0',
				],
			},
			{
				any: [
					{
						id: 'aria-required-parent',
						data: ['tablist'],
						relatedNodes: [],
						impact: 'critical',
						message:
							'Required ARIA parent role not present: tablist',
					},
				],
				all: [],
				none: [],
				impact: 'critical',
				html:
					'<button class="nav-link btn btn-unstyled" type="button" aria-controls="_com_liferay_layout_content_page_editor_web_internal_portlet_ContentPageEditorPortlet__useId_10tabPanel0" id="_com_liferay_layout_content_page_editor_web_internal_portlet_ContentPageEditorPortlet__useId_10tab0" aria-disabled="true" aria-selected="false" role="tab">',
				target: [
					'html > #senna_surface1 > .page-editor__sidebar > .page-editor__sidebar__content > .page-editor__sidebar__fragments-widgets-panel > .nav > .nav-item:nth-child(1) > #_com_liferay_layout_content_page_editor_web_internal_portlet_ContentPageEditorPortlet__useId_10tab0',
				],
			},
			{
				any: [
					{
						id: 'aria-required-parent',
						data: ['tablist'],
						relatedNodes: [],
						impact: 'critical',
						message:
							'Required ARIA parent role not present: tablist',
					},
				],
				all: [],
				none: [],
				impact: 'critical',
				html:
					'<button class="nav-link btn btn-unstyled" type="button" aria-controls="_com_liferay_layout_content_page_editor_web_internal_portlet_ContentPageEditorPortlet__useId_10tabPanel0" id="_com_liferay_layout_content_page_editor_web_internal_portlet_ContentPageEditorPortlet__useId_10tab0" aria-disabled="true" aria-selected="false" role="tab">',
				target: [
					'html > #senna_surface1 > .page-editor__sidebar > .page-editor__sidebar__content > .page-editor__sidebar__fragments-widgets-panel > .nav > .nav-item:nth-child(1) > #_com_liferay_layout_content_page_editor_web_internal_portlet_ContentPageEditorPortlet__useId_10tab0',
				],
			},
		],
	},
	{
		id: 'aria-required-parent-ser',
		impact: 'serious',
		tags: ['cat.aria', 'wcag2a', 'wcag131'],
		description:
			'Ensures elements with an ARIA role that require parent roles are contained by them',
		help: 'Certain ARIA roles must be contained by particular parents',
		helpUrl:
			'https://dequeuniversity.com/rules/axe/4.2/aria-required-parent?application=axeAPI',
		nodes: [
			{
				any: [
					{
						id: 'aria-required-parent',
						data: ['tablist'],
						relatedNodes: [],
						impact: 'critical',
						message:
							'Required ARIA parent role not present: tablist',
					},
				],
				all: [],
				none: [],
				impact: 'critical',
				html:
					'<button class="nav-link btn btn-unstyled" type="button" aria-controls="_com_liferay_layout_content_page_editor_web_internal_portlet_ContentPageEditorPortlet__useId_10tabPanel0" id="_com_liferay_layout_content_page_editor_web_internal_portlet_ContentPageEditorPortlet__useId_10tab0" aria-disabled="true" aria-selected="false" role="tab">',
				target: [
					'html > #senna_surface1 > .page-editor__sidebar > .page-editor__sidebar__content > .page-editor__sidebar__fragments-widgets-panel > .nav > .nav-item:nth-child(1) > #_com_liferay_layout_content_page_editor_web_internal_portlet_ContentPageEditorPortlet__useId_10tab0',
				],
			},
			{
				any: [
					{
						id: 'aria-required-parent',
						data: ['tablist'],
						relatedNodes: [],
						impact: 'critical',
						message:
							'Required ARIA parent role not present: tablist',
					},
				],
				all: [],
				none: [],
				impact: 'critical',
				html:
					'<button class="nav-link btn btn-unstyled" type="button" aria-controls="_com_liferay_layout_content_page_editor_web_internal_portlet_ContentPageEditorPortlet__useId_10tabPanel0" id="_com_liferay_layout_content_page_editor_web_internal_portlet_ContentPageEditorPortlet__useId_10tab0" aria-disabled="true" aria-selected="false" role="tab">',
				target: [
					'html > #senna_surface1 > .page-editor__sidebar > .page-editor__sidebar__content > .page-editor__sidebar__fragments-widgets-panel > .nav > .nav-item:nth-child(1) > #_com_liferay_layout_content_page_editor_web_internal_portlet_ContentPageEditorPortlet__useId_10tab0',
				],
			},
		],
	},
	{
		id: 'aria-required-parent-mod',
		impact: 'moderate',
		tags: ['cat.aria', 'wcag2aa', 'wcag131'],
		description:
			'Ensures elements with an ARIA role that require parent roles are contained by them',
		help: 'Certain ARIA roles must be contained by particular parents',
		helpUrl:
			'https://dequeuniversity.com/rules/axe/4.2/aria-required-parent?application=axeAPI',
		nodes: [
			{
				any: [
					{
						id: 'aria-required-parent',
						data: ['tablist'],
						relatedNodes: [],
						impact: 'critical',
						message:
							'Required ARIA parent role not present: tablist',
					},
				],
				all: [],
				none: [],
				impact: 'critical',
				html:
					'<button class="nav-link btn btn-unstyled" type="button" aria-controls="_com_liferay_layout_content_page_editor_web_internal_portlet_ContentPageEditorPortlet__useId_10tabPanel0" id="_com_liferay_layout_content_page_editor_web_internal_portlet_ContentPageEditorPortlet__useId_10tab0" aria-disabled="true" aria-selected="false" role="tab">',
				target: [
					'html > #senna_surface1 > .page-editor__sidebar > .page-editor__sidebar__content > .page-editor__sidebar__fragments-widgets-panel > .nav > .nav-item:nth-child(1) > #_com_liferay_layout_content_page_editor_web_internal_portlet_ContentPageEditorPortlet__useId_10tab0',
				],
			},
			{
				any: [
					{
						id: 'aria-required-parent',
						data: ['tablist'],
						relatedNodes: [],
						impact: 'critical',
						message:
							'Required ARIA parent role not present: tablist',
					},
				],
				all: [],
				none: [],
				impact: 'critical',
				html:
					'<button class="nav-link btn btn-unstyled" type="button" aria-controls="_com_liferay_layout_content_page_editor_web_internal_portlet_ContentPageEditorPortlet__useId_10tabPanel0" id="_com_liferay_layout_content_page_editor_web_internal_portlet_ContentPageEditorPortlet__useId_10tab0" aria-disabled="true" aria-selected="false" role="tab">',
				target: [
					'html > #senna_surface1 > .page-editor__sidebar > .page-editor__sidebar__content > .page-editor__sidebar__fragments-widgets-panel > .nav > .nav-item:nth-child(1) > #_com_liferay_layout_content_page_editor_web_internal_portlet_ContentPageEditorPortlet__useId_10tab0',
				],
			},
			{
				any: [
					{
						id: 'aria-required-parent',
						data: ['tablist'],
						relatedNodes: [],
						impact: 'critical',
						message:
							'Required ARIA parent role not present: tablist',
					},
				],
				all: [],
				none: [],
				impact: 'critical',
				html:
					'<button class="nav-link btn btn-unstyled" type="button" aria-controls="_com_liferay_layout_content_page_editor_web_internal_portlet_ContentPageEditorPortlet__useId_10tabPanel0" id="_com_liferay_layout_content_page_editor_web_internal_portlet_ContentPageEditorPortlet__useId_10tab0" aria-disabled="true" aria-selected="false" role="tab">',
				target: [
					'html > #senna_surface1 > .page-editor__sidebar > .page-editor__sidebar__content > .page-editor__sidebar__fragments-widgets-panel > .nav > .nav-item:nth-child(1) > #_com_liferay_layout_content_page_editor_web_internal_portlet_ContentPageEditorPortlet__useId_10tab0',
				],
			},
		],
	},
	{
		id: 'aria-required-parent-min',
		impact: 'minor',
		tags: ['cat.aria', 'wcag2a', 'wcag131'],
		description:
			'Ensures elements with an ARIA role that require parent roles are contained by them',
		help: 'Certain ARIA roles must be contained by particular parents',
		helpUrl:
			'https://dequeuniversity.com/rules/axe/4.2/aria-required-parent?application=axeAPI',
		nodes: [
			{
				any: [
					{
						id: 'aria-required-parent',
						data: ['tablist'],
						relatedNodes: [],
						impact: 'critical',
						message:
							'Required ARIA parent role not present: tablist',
					},
				],
				all: [],
				none: [],
				impact: 'critical',
				html:
					'<button class="nav-link btn btn-unstyled" type="button" aria-controls="_com_liferay_layout_content_page_editor_web_internal_portlet_ContentPageEditorPortlet__useId_10tabPanel0" id="_com_liferay_layout_content_page_editor_web_internal_portlet_ContentPageEditorPortlet__useId_10tab0" aria-disabled="true" aria-selected="false" role="tab">',
				target: [
					'html > #senna_surface1 > .page-editor__sidebar > .page-editor__sidebar__content > .page-editor__sidebar__fragments-widgets-panel > .nav > .nav-item:nth-child(1) > #_com_liferay_layout_content_page_editor_web_internal_portlet_ContentPageEditorPortlet__useId_10tab0',
				],
			},
			{
				any: [
					{
						id: 'aria-required-parent',
						data: ['tablist'],
						relatedNodes: [],
						impact: 'critical',
						message:
							'Required ARIA parent role not present: tablist',
					},
				],
				all: [],
				none: [],
				impact: 'critical',
				html:
					'<button class="nav-link btn btn-unstyled" type="button" aria-controls="_com_liferay_layout_content_page_editor_web_internal_portlet_ContentPageEditorPortlet__useId_10tabPanel0" id="_com_liferay_layout_content_page_editor_web_internal_portlet_ContentPageEditorPortlet__useId_10tab0" aria-disabled="true" aria-selected="false" role="tab">',
				target: [
					'html > #senna_surface1 > .page-editor__sidebar > .page-editor__sidebar__content > .page-editor__sidebar__fragments-widgets-panel > .nav > .nav-item:nth-child(1) > #_com_liferay_layout_content_page_editor_web_internal_portlet_ContentPageEditorPortlet__useId_10tab0',
				],
			},
			{
				any: [
					{
						id: 'aria-required-parent',
						data: ['tablist'],
						relatedNodes: [],
						impact: 'critical',
						message:
							'Required ARIA parent role not present: tablist',
					},
				],
				all: [],
				none: [],
				impact: 'critical',
				html:
					'<button class="nav-link btn btn-unstyled" type="button" aria-controls="_com_liferay_layout_content_page_editor_web_internal_portlet_ContentPageEditorPortlet__useId_10tabPanel0" id="_com_liferay_layout_content_page_editor_web_internal_portlet_ContentPageEditorPortlet__useId_10tab0" aria-disabled="true" aria-selected="false" role="tab">',
				target: [
					'html > #senna_surface1 > .page-editor__sidebar > .page-editor__sidebar__content > .page-editor__sidebar__fragments-widgets-panel > .nav > .nav-item:nth-child(1) > #_com_liferay_layout_content_page_editor_web_internal_portlet_ContentPageEditorPortlet__useId_10tab0',
				],
			},
		],
	},
	{
		id: 'aria-required-parent-crit-2',
		impact: 'critical',
		tags: ['cat.aria', 'wcag2a', 'wcag131'],
		description:
			'Ensures elements with an ARIA role that require parent roles are contained by them',
		help: 'Certain ARIA roles must be contained by particular parents',
		helpUrl:
			'https://dequeuniversity.com/rules/axe/4.2/aria-required-parent?application=axeAPI',
		nodes: [
			{
				any: [
					{
						id: 'aria-required-parent',
						data: ['tablist'],
						relatedNodes: [],
						impact: 'critical',
						message:
							'Required ARIA parent role not present: tablist',
					},
				],
				all: [],
				none: [],
				impact: 'critical',
				html:
					'<button class="nav-link btn btn-unstyled" type="button" aria-controls="_com_liferay_layout_content_page_editor_web_internal_portlet_ContentPageEditorPortlet__useId_10tabPanel0" id="_com_liferay_layout_content_page_editor_web_internal_portlet_ContentPageEditorPortlet__useId_10tab0" aria-disabled="true" aria-selected="false" role="tab">',
				target: [
					'html > #senna_surface1 > .page-editor__sidebar > .page-editor__sidebar__content > .page-editor__sidebar__fragments-widgets-panel > .nav > .nav-item:nth-child(1) > #_com_liferay_layout_content_page_editor_web_internal_portlet_ContentPageEditorPortlet__useId_10tab0',
				],
			},
			{
				any: [
					{
						id: 'aria-required-parent',
						data: ['tablist'],
						relatedNodes: [],
						impact: 'critical',
						message:
							'Required ARIA parent role not present: tablist',
					},
				],
				all: [],
				none: [],
				impact: 'critical',
				html:
					'<button class="nav-link btn btn-unstyled" type="button" aria-controls="_com_liferay_layout_content_page_editor_web_internal_portlet_ContentPageEditorPortlet__useId_10tabPanel0" id="_com_liferay_layout_content_page_editor_web_internal_portlet_ContentPageEditorPortlet__useId_10tab0" aria-disabled="true" aria-selected="false" role="tab">',
				target: [
					'html > #senna_surface1 > .page-editor__sidebar > .page-editor__sidebar__content > .page-editor__sidebar__fragments-widgets-panel > .nav > .nav-item:nth-child(1) > #_com_liferay_layout_content_page_editor_web_internal_portlet_ContentPageEditorPortlet__useId_10tab0',
				],
			},
			{
				any: [
					{
						id: 'aria-required-parent',
						data: ['tablist'],
						relatedNodes: [],
						impact: 'critical',
						message:
							'Required ARIA parent role not present: tablist',
					},
				],
				all: [],
				none: [],
				impact: 'critical',
				html:
					'<button class="nav-link btn btn-unstyled" type="button" aria-controls="_com_liferay_layout_content_page_editor_web_internal_portlet_ContentPageEditorPortlet__useId_10tabPanel0" id="_com_liferay_layout_content_page_editor_web_internal_portlet_ContentPageEditorPortlet__useId_10tab0" aria-disabled="true" aria-selected="false" role="tab">',
				target: [
					'html > #senna_surface1 > .page-editor__sidebar > .page-editor__sidebar__content > .page-editor__sidebar__fragments-widgets-panel > .nav > .nav-item:nth-child(1) > #_com_liferay_layout_content_page_editor_web_internal_portlet_ContentPageEditorPortlet__useId_10tab0',
				],
			},
		],
	},
];
