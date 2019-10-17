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

/**
 * The History Manager, a utility for SPA.
 *
 * @deprecated As of Mueller (7.2.x), replaced by senna.js
 * @module liferay-history-manager
 */

AUI.add(
	'liferay-history-manager',
	A => {
		var HistoryBase = A.HistoryBase;
		var HistoryManager = new Liferay.History();

		var EVENT_STATE_CHANGE = 'stateChange';

		HistoryManager.SRC_ADD = HistoryBase.SRC_ADD;
		HistoryManager.SRC_REPLACE = HistoryBase.SRC_REPLACE;

		HistoryManager.SRC_HASH = A.HistoryHash
			? A.HistoryHash.SRC_HASH
			: 'hash';
		HistoryManager.SRC_POPSTATE = A.HistoryHTML5
			? A.HistoryHTML5.SRC_POPSTATE
			: 'popstate';

		HistoryManager.HTML5 = HistoryBase.html5;

		HistoryManager.PAIR_SEPARATOR = Liferay.History.PAIR_SEPARATOR;
		HistoryManager.VALUE_SEPARATOR = Liferay.History.VALUE_SEPARATOR;

		HistoryManager.publish(EVENT_STATE_CHANGE, {
			broadcast: 2
		});

		HistoryManager.after('change', event => {
			if (
				event.newVal.liferay &&
				(event.src === HistoryManager.SRC_HASH ||
					event.src === HistoryManager.SRC_POPSTATE)
			) {
				HistoryManager.fire(EVENT_STATE_CHANGE, event);
			}
		});

		Liferay.HistoryManager = HistoryManager;
	},
	'',
	{
		requires: ['liferay-history']
	}
);
