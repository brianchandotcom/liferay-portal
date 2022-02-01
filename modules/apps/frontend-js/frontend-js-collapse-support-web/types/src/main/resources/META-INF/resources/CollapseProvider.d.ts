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
	_prefersReducedMotion(): boolean;
	_setTransitionEndEvent(): void;
}
export default CollapseProvider;
