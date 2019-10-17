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

AUI.add(
	'liferay-toggler-key-filter',
	A => {
		var KeyMap = A.Event.KeyMap;

		var NAME = 'togglerkeyfilter';

		var TogglerKeyFilter = A.Component.create({
			ATTRS: {
				filter: {
					validator: Array.isArray,
					value: [
						KeyMap.ESC,
						KeyMap.LEFT,
						KeyMap.NUM_MINUS,
						KeyMap.NUM_PLUS,
						KeyMap.RIGHT,
						KeyMap.SPACE
					]
				}
			},

			EXTENDS: A.Plugin.Base,

			NAME,

			NS: NAME,

			prototype: {
				_headerEventHandler(event) {
					var instance = this;

					var validAction = event.type === instance._toggleEvent;

					if (!validAction) {
						validAction =
							instance.get('filter').indexOf(event.keyCode) > -1;
					}

					var retVal;

					if (!validAction) {
						retVal = new A.Do.Prevent();
					}

					return retVal;
				},

				initializer() {
					var instance = this;

					instance._toggleEvent = instance
						.get('host')
						.get('toggleEvent');

					instance.beforeHostMethod(
						'headerEventHandler',
						instance._headerEventHandler,
						instance
					);
				}
			}
		});

		Liferay.TogglerKeyFilter = TogglerKeyFilter;
	},
	'',
	{
		requires: ['aui-event-base']
	}
);
