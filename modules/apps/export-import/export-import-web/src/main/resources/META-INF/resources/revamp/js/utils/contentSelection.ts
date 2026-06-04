/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {
	PreviewPortletDataHandlerControl,
	PreviewPortletDataHandlerSection,
} from '../types/portletDataHandler';

import type {ContentSelection} from '../components/forms/content_selector/ContentSelector';

export type HandlerSelection =
	| {
			[key: string]: HandlerSelection | boolean | number[];
	  }
	| string
	| true;

export const LAYOUT_SET_LAYOUTS_PORTLET_DATA_KEY =
	'PORTLET_DATA_com_liferay_layout_admin_web_portlet_LayoutSetLayoutsPortlet';

export const CONTENT_SECTION_KEY = 'category.site_administration.content';

export const SITE_BUILDER_SECTION_KEY = 'category.site_administration.build';

export function isAllLayoutsSelected(
	value: HandlerSelection | undefined
): boolean {
	return typeof value === 'object' && !value.layoutIds;
}

export function isSelected(
	value: HandlerSelection | undefined,
	entry: PreviewPortletDataHandlerControl
): boolean {
	if (!value) {
		return false;
	}

	if (entry.name === LAYOUT_SET_LAYOUTS_PORTLET_DATA_KEY) {
		return isAllLayoutsSelected(value);
	}

	if (entry.type === 'Choice') {
		return true;
	}

	if (
		!entry.previewPortletDataHandlerControls?.length ||
		typeof value !== 'object'
	) {
		return true;
	}

	return entry.previewPortletDataHandlerControls.every((control) =>
		isSelected(value[control.name] as HandlerSelection, control)
	);
}

export function getInitialSelection(
	entry: PreviewPortletDataHandlerControl
): HandlerSelection {
	if (entry.name === LAYOUT_SET_LAYOUTS_PORTLET_DATA_KEY) {
		return {privateLayout: false};
	}

	if (entry.type === 'Choice') {
		return entry.choices[0].name;
	}

	if (!entry.previewPortletDataHandlerControls?.length) {
		return true;
	}

	return getInitialSelections(entry.previewPortletDataHandlerControls);
}

export function getInitialSelections(
	controls: PreviewPortletDataHandlerControl[]
): Record<string, HandlerSelection> {
	return Object.fromEntries(
		controls.map((control) => [control.name, getInitialSelection(control)])
	);
}

export function updateSelection<V>(
	current: Record<string, V>,
	key: string,
	value: V | undefined
): Record<string, V> | undefined {
	const {[key]: _, ...rest} = current;
	const next: Record<string, V> = value ? {...rest, [key]: value} : rest;

	return Object.keys(next).length ? next : undefined;
}

export function getSelectionSummary(
	controls: {label: string; name: string}[],
	selection: Record<string, HandlerSelection>
): string {
	return controls
		.filter((control) => selection[control.name] !== undefined)
		.map((control) => control.label)
		.join(', ');
}

export function withSiteBuilderSection(
	sections: PreviewPortletDataHandlerSection[],
	label = ''
): PreviewPortletDataHandlerSection[] {
	if (sections.some((section) => section.name === SITE_BUILDER_SECTION_KEY)) {
		return sections;
	}

	return [
		...sections,
		{
			label,
			name: SITE_BUILDER_SECTION_KEY,
			previewPortletDataHandlers: [],
		},
	];
}

export function toProcessRequestFlags(
	contentSelection: ContentSelection | undefined
) {
	const commentsAndRatings = (contentSelection?.[CONTENT_SECTION_KEY]
		?.commentsAndRatings ?? {}) as Record<string, boolean>;
	const lookAndFeel = (contentSelection?.[SITE_BUILDER_SECTION_KEY]
		?.lookAndFeel ?? {}) as Record<string, boolean>;

	return {
		comments: !!commentsAndRatings.comments,
		logo: !!lookAndFeel.logo,
		ratings: !!commentsAndRatings.ratings,
		sitePagesSettings: !!lookAndFeel.sitePagesSettings,
		siteTemplateSettings: !!lookAndFeel.siteTemplateSettings,
		themeSettings: !!lookAndFeel.themeSettings,
	};
}
