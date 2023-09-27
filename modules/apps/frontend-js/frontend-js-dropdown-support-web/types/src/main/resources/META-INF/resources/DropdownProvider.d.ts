/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

declare class DropdownProvider {
	EVENT_HIDDEN: string;
	EVENT_HIDE: string;
	EVENT_SHOW: string;
	EVENT_SHOWN: string;
	constructor();
	hide: ({menu, trigger}: {menu?: any; trigger?: any}) => void;
	show: ({menu, trigger}: {menu?: any; trigger?: any}) => void;
	_getMenu(trigger: any): any;
	_getTrigger(menu: any): any;
	_onKeyDown: (event: any) => void;
	_onTriggerClick: (event: any) => void;
	_warnNotButtonTrigger(): void;
}
export default DropdownProvider;
