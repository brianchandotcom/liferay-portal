/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

declare class CollapseProvider {
	_transitioning?: boolean;
	_transitionEndEvent?: any;
	EVENT_HIDDEN: string;
	EVENT_HIDE: string;
	EVENT_SHOW: string;
	EVENT_SHOWN: string;
	constructor();
	hide: ({panel, trigger}: {panel?: any; trigger?: any}) => void;
	show: ({panel, trigger}: {panel?: any; trigger?: any}) => void;
	_getDimension(panel: any): string;
	_getPanel(trigger: any): any;
	_getTrigger(panel: any): Element | null;
	_onTriggerClick: (event: any) => void;
	_setTransitionEndEvent(): void;
}
export default CollapseProvider;
