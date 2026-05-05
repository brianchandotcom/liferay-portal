/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

export interface PortletDataHandlerBoolean {
	label: string;
	name: string;
	portletDataHandlerControls?: PortletDataHandlerControl[];
	type: 'Boolean';
}

export interface PortletDataHandlerChoice {
	choices: {label: string; name: string}[];
	label: string;
	name: string;
	type: 'Choice';
}

export interface PortletDataHandlerSetting {
	label: string;
	name: string;
	portletDataHandlerControls?: PortletDataHandlerControl[];
	type: 'Setting';
}

export type PortletDataHandlerControl =
	| PortletDataHandlerBoolean
	| PortletDataHandlerChoice
	| PortletDataHandlerSetting;

export type PortletDataHandler = Omit<PortletDataHandlerBoolean, 'type'>;

export interface PortletDataHandlerSection {
	label: string;
	name: string;
	portletDataHandlers: PortletDataHandler[];
}

export interface ExportPreview {
	additionCount: number;
	portletDataHandlerSections: PortletDataHandlerSection[];
}
