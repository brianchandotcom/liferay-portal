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

Liferay.on = function() {};

Liferay.fire = function() {};

Liferay.detach = function() {};

(function(A, Liferay) {
	var CLICK_EVENTS = {};

	var DOC = A.config.doc;

	Liferay.provide(
		Liferay,
		'delegateClick',
		(id, fn) => {
			var el = DOC.getElementById(id);

			if (!el || el.id != id) {
				return;
			}

			var guid = A.one(el)
				.addClass('lfr-delegate-click')
				.guid();

			CLICK_EVENTS[guid] = fn;

			if (!Liferay._baseDelegateHandle) {
				Liferay._baseDelegateHandle = A.getBody().delegate(
					'click',
					Liferay._baseDelegate,
					'.lfr-delegate-click'
				);
			}
		},
		['aui-base']
	);

	Liferay._baseDelegate = function(event) {
		var id = event.currentTarget.attr('id');

		var fn = CLICK_EVENTS[id];

		if (fn) {
			fn.apply(this, arguments);
		}
	};

	Liferay._CLICK_EVENTS = CLICK_EVENTS;

	A.use('attribute', 'oop', A => {
		A.augment(Liferay, A.Attribute, true);
	});
})(AUI(), Liferay);
