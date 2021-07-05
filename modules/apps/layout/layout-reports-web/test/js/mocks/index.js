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

export const pageURLs = [
	{
		languageId: 'en-US',
		languageLabel: 'English (United States)',
		layoutReportsIssuesURL:
			'http://localhost:8080/web/guest/home?p_p_id=com_liferay_layout_reports_web_internal_portlet_LayoutReportsPortlet&p_p_lifecycle=2&p_p_state=normal&p_p_mode=view&p_p_resource_id=%2Flayout_reports%2Fget_layout_reports_issues&p_p_cacheability=cacheLevelPage&_com_liferay_layout_reports_web_internal_portlet_LayoutReportsPortlet_groupId=20125&_com_liferay_layout_reports_web_internal_portlet_LayoutReportsPortlet_url=http%3A%2F%2Flocalhost%3A8080&p_p_auth=2TuMGSyj',
		title: 'Home',
		url: 'http://localhost:8080',
	},
	{
		languageId: 'ar-SA',
		languageLabel: 'Arabic (Saudi Arabia)',
		layoutReportsIssuesURL:
			'http://localhost:8080/web/guest/home?p_p_id=com_liferay_layout_reports_web_internal_portlet_LayoutReportsPortlet&p_p_lifecycle=2&p_p_state=normal&p_p_mode=view&p_p_resource_id=%2Flayout_reports%2Fget_layout_reports_issues&p_p_cacheability=cacheLevelPage&_com_liferay_layout_reports_web_internal_portlet_LayoutReportsPortlet_groupId=20125&_com_liferay_layout_reports_web_internal_portlet_LayoutReportsPortlet_url=http%3A%2F%2Flocalhost%3A8080%2Far%2F&p_p_auth=2TuMGSyj',
		title: 'Home',
		url: 'http://localhost:8080/ar/',
	},
];

export const selectedIssue = {
	description:
		'Background and foreground colors do not have a sufficient contrast ratio. Low contrast ratio is difficult or impossible for many users to read. <a href="https://web.dev/color-contrast" target="_blank">Learn more about Low Contrast Ratio.</a>',
	failingElements: [
		{
			node: {
				boundingRect: {
					bottom: 3762,
					height: 12,
					left: 118,
					right: 185,
					top: 3750,
					width: 67,
				},
				explanation:
					'Fix any of the following:\n  Element has insufficient color contrast of 1.35 (foreground color: #dddddd, background color: #ffffff, font size: 8.3pt (11px), font weight: normal). Expected contrast ratio of 4.5:1',
				lhId: '6-10-SPAN',
				nodeLabel: 'color-contrast',
				path: '1,HTML,2,BODY,8,SPAN',
				selector: 'body > span',
				snippet: '<span style="color: #dddddd">',
				type: 'node',
			},
		},
	],
	key: 'low-contrast-ratio',
	tips:
		'Text that is 18pt, or 14pt and bold, needs a contrast ratio of 3:1. All other text needs a contrast ratio of 4.5:1. Contrast between colors may be defined in the style book, the page/fragment template or the fragment itself.',
	title: 'Low Contrast Ratio',
	total: '1',
};

export const layoutReportsIssues = {
	'en-US': {
		date: 'July 5, 2021 12:09 PM',
		issues: [
			{
				details: [
					{
						description:
							'When an image is being used as an <code>&lt;input&gt;</code> button, providing alternative text helps users of screen readers and other assistive technologies understand the purpose of the button. <a href="https://web.dev/input-image-alt" target="_blank">Learn more about Missing Input ALT Attributes.</a>',
						failingElements: [
							{
								node: {
									boundingRect: {
										bottom: 3759,
										height: 0,
										left: 115,
										right: 115,
										top: 3759,
										width: 0,
									},
									explanation:
										'Fix any of the following:\n  Element has no alt attribute\n  aria-label attribute does not exist or is empty\n  aria-labelledby attribute does not exist, references elements that do not exist or references elements that are empty\n  Element has no title attribute',
									lhId: '6-13-INPUT',
									nodeLabel: 'input',
									path: '1,HTML,2,BODY,6,INPUT',
									selector: 'body > input',
									snippet: '<input type="image">',
									type: 'node',
								},
							},
						],
						key: 'missing-input-alt-attributes',

						tips:
							'Input ALT attributes can be added in the fragment editor.',

						title: 'Missing Input ALT Attributes',
						total: '1',
					},
				],
				key: 'accessibility',
				title: 'Accessibility',
				total: '3',
			},
			{
				details: [
					{
						description:
							'When multiple pages have similar content, search engines consider them duplicate versions of the same page. Valid canonical links let you tell search engines which version of a page to crawl and display to users in search results. <a href="https://web.dev/canonical" target="_blank">Learn more about Invalid Canonical URL.</a>',

						failingElements: [
							{
								content:
									'If the problem is that the canonical URL does not match the URL of the page, try changing the configuration for using localized canonical URLs from <a href="http://localhost:8080/group/control_panel/manage?p_p_id=com_liferay_configuration_admin_web_portlet_InstanceSettingsPortlet&p_p_lifecycle=0&p_p_state=maximized&_com_liferay_configuration_admin_web_portlet_InstanceSettingsPortlet_mvcRenderCommandName=%2Fconfiguration_admin%2Fedit_configuration&_com_liferay_configuration_admin_web_portlet_InstanceSettingsPortlet_redirect=http%3A%2F%2Flocalhost%3A8080%2Fweb%2Fguest%2Fhome&_com_liferay_configuration_admin_web_portlet_InstanceSettingsPortlet_factoryPid=com.liferay.layout.seo.internal.configuration.LayoutSEOCompanyConfiguration&_com_liferay_configuration_admin_web_portlet_InstanceSettingsPortlet_pid=com.liferay.layout.seo.internal.configuration.LayoutSEOCompanyConfiguration">Instance Settings > Pages > SEO</a>.',
							},
						],
						key: 'invalid-canonical-url',
						tips:
							'In a Liferay site, canonical URLs are automatically generated. If it is missing, the issue could be originated by the theme. If the problem is that it does not match the URL of the page, you can change the configuration for using localized canonical URLs at Instance Settings > Pages > SEO.',
						title: 'Invalid Canonical URL',
						total: '1',
					},
					{
						description:
							'The meta description provides a summary of a page\'s content that search engines include in search results. A high-quality, unique meta description makes your page appear more relevant and can increase your search traffic. <a href="https://web.dev/meta-description" target="_blank">Learn more about Missing Meta Description.</a>',
						failingElements: [
							{
								content:
									'<a href="http://localhost:8080/group/guest/~/control_panel/manage?p_p_id=com_liferay_layout_admin_web_portlet_GroupPagesPortlet&p_p_lifecycle=0&p_p_state=maximized&_com_liferay_layout_admin_web_portlet_GroupPagesPortlet_mvcRenderCommandName=%2Flayout_admin%2Fedit_layout&_com_liferay_layout_admin_web_portlet_GroupPagesPortlet_redirect=http%3A%2F%2Flocalhost%3A8080%2Fweb%2Fguest%2Fhome&_com_liferay_layout_admin_web_portlet_GroupPagesPortlet_backURL=http%3A%2F%2Flocalhost%3A8080%2Fweb%2Fguest%2Fhome&_com_liferay_layout_admin_web_portlet_GroupPagesPortlet_portletResource=com_liferay_layout_reports_web_internal_portlet_LayoutReportsPortlet&_com_liferay_layout_admin_web_portlet_GroupPagesPortlet_groupId=20125&_com_liferay_layout_admin_web_portlet_GroupPagesPortlet_privateLayout=false&_com_liferay_layout_admin_web_portlet_GroupPagesPortlet_screenNavigationEntryKey=seo&p_r_p_selPlid=8&p_p_auth=VHvDznkm">Add a description</a> from the configuration section of this page.',
							},
						],
						key: 'missing-meta-description',
						tips:
							'Meta description can be added in the configuration section of this page.',
						title: 'Missing Meta Description',
						total: '1',
					},
					{
						description:
							'Tap targets are the areas of a web page that users on touch devices can interact with. Buttons, links, and form elements all have tap targets. Making sure tap targets are big enough and far enough apart from each other makes your page more mobile friendly and accessible. <a href="https://web.dev/tap-targets" target="_blank">Learn more about Small Tap Targets.</a>',
						failingElements: [
							{
								height: 4,
								overlapScoreRatio: 14.203125,
								overlappingTarget: {
									boundingRect: {
										bottom: 2540,
										height: 12,
										left: 280,
										right: 329,
										top: 2528,
										width: 49,
									},
									lhId: '6-1-A',
									nodeLabel: 'Click here',
									overlappingTargetScore: 227.25,
									path: '1,HTML,2,BODY,12,A',
									selector: 'body > a',
									snippet:
										'<a href="https://www.google.com">',
									type: 'node',
								},
								size: '4x4',
								tapTarget: {
									boundingRect: {
										bottom: 2527,
										height: 4,
										left: 332,
										right: 336,
										top: 2523,
										width: 4,
									},
									lhId: '6-9-BUTTON',
									nodeLabel: 'a',
									path: '1,HTML,2,BODY,14,BUTTON',
									selector: 'body > button',
									snippet:
										'<button onclick="alert(\'hola\')" style="padding: 0; width: 1px; height: 1px">',
									type: 'node',
								},
								tapTargetScore: 16,
								width: 4,
							},
						],
						key: 'small-tap-targets',
						tips:
							'Targets that are smaller than 48px by 48px or closer than 8px apart fail the audit, so make sure your layout respects these rules.',
						title: 'Small Tap Targets',
						total: '1',
					},
					{
						description:
							'Search engine users rely on the title to determine whether a page is relevant to their search. It also gives users of screen readers and other assistive technologies an overview of the page. <a href="https://web.dev/document-title" target="_blank">Learn more about Missing &lt;title&gt; Element.</a>',
						failingElements: [
							{
								content:
									'<a href="http://localhost:8080/group/guest/~/control_panel/manage?p_p_id=com_liferay_layout_admin_web_portlet_GroupPagesPortlet&p_p_lifecycle=0&p_p_state=maximized&_com_liferay_layout_admin_web_portlet_GroupPagesPortlet_mvcRenderCommandName=%2Flayout_admin%2Fedit_layout&_com_liferay_layout_admin_web_portlet_GroupPagesPortlet_redirect=http%3A%2F%2Flocalhost%3A8080%2Fweb%2Fguest%2Fhome&_com_liferay_layout_admin_web_portlet_GroupPagesPortlet_backURL=http%3A%2F%2Flocalhost%3A8080%2Fweb%2Fguest%2Fhome&_com_liferay_layout_admin_web_portlet_GroupPagesPortlet_portletResource=com_liferay_layout_reports_web_internal_portlet_LayoutReportsPortlet&_com_liferay_layout_admin_web_portlet_GroupPagesPortlet_groupId=20125&_com_liferay_layout_admin_web_portlet_GroupPagesPortlet_privateLayout=false&_com_liferay_layout_admin_web_portlet_GroupPagesPortlet_screenNavigationEntryKey=seo&p_r_p_selPlid=8&p_p_auth=VHvDznkm">Add a title</a> from the configuration section of this page.',
							},
						],
						key: 'missing-title-element',
						tips:
							'<code>&lt;title&gt;</code> element is automatically generated from the asset title, but you can change it in the configuration section of this page.',
						title: 'Missing <title> Element',

						total: '1',
					},
				],
				key: 'seo',
				title: 'SEO',
				total: '12',
			},
		],
	},
};
