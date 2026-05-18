/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {PreviewPortletDataHandlerControl} from '../types/portletDataHandler';

export type HandlerSelection =
	| {
			[key: string]: HandlerSelection;
	  }
	| string
	| true;

export function isSelected(
	value: HandlerSelection | undefined,
	entry: PreviewPortletDataHandlerControl
): boolean {
	if (!value) {
		return false;
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
		isSelected(value[control.name], control)
	);
}

export function getInitialSelection(
	entry: PreviewPortletDataHandlerControl
): HandlerSelection {
	if (entry.type === 'Choice') {
		return entry.choices[0].name;
	}

	if (!entry.previewPortletDataHandlerControls?.length) {
		return true;
	}

	const selection: Record<string, HandlerSelection> = {};

	entry.previewPortletDataHandlerControls.forEach((control) => {
		selection[control.name] = getInitialSelection(control);
	});

	return selection;
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
