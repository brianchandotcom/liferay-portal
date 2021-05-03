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

declare const STATES: {
	IDLE: {
		show: boolean;
	};
	SHOW: {
		show: boolean;
	};
	WAIT_HIDE: {
		show: boolean;
	};
	WAIT_RESHOW: {
		show: boolean;
	};
	WAIT_SHOW: {
		show: boolean;
	};
};
export {STATES};
interface State {
	current: {
		show: boolean;
	};
	nextTarget?: HTMLElement;
	target?: HTMLElement;
	timestamp?: number;
}
declare type Action =
	| {
			type: 'hide';
	  }
	| {
			type: 'hideDelayCompleted';
	  }
	| {
			type: 'showDelayCompleted';
	  }
	| {
			type: 'show';
			target?: HTMLElement;
	  };
export default function reducer(state: State, action: Action): State;
