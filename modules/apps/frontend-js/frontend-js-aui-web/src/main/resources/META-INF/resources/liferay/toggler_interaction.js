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
 * @deprecated As of Athanasius (7.3.x), with no direct replacement
 */
AUI.add(
	'liferay-toggler-interaction',
	(A) => {
		const Lang = A.Lang;

		const NAME = 'togglerinteraction';

		const STR_CHILDREN = 'children';

		const STR_CONTAINER = 'container';

		const STR_DESCENDANTS = 'descendants';

		const STR_HEADER = 'header';

		const STR_HOST = 'host';

		const STR_KEYS = 'keys';

		const STR_TOGGLER = 'toggler';

		const TogglerInteraction = A.Component.create({
			ATTRS: {
				children: {
					validator: Lang.isString,
				},

				descendants: {
					getter: '_getDescendants',
				},

				keys: {
					validator: Lang.isObject,
					value: {
						collapse: 'down:37',
						next: 'down:40',
						previous: 'down:38',
					},
				},

				parents: {
					validator: Lang.isString,
				},
			},

			EXTENDS: Liferay.TogglerKeyFilter,

			NAME,

			NS: NAME,

			prototype: {
				_childrenEventHandler(event) {
					const instance = this;

					const host = instance.get(STR_HOST);

					const target = event.currentTarget;

					const header = target
						.ancestor(instance.get('parents'))
						.one(host.get(STR_HEADER));

					let toggler = header.getData(STR_TOGGLER);

					if (!toggler) {
						host.createAll();

						toggler = header.getData(STR_TOGGLER);
					}

					toggler.collapse();

					header.focus();

					instance._focusManager.set('activeDescendant', header);
				},

				_getDescendants() {
					const instance = this;

					let result =
						instance.get(STR_HOST).get(STR_HEADER) + ':visible';

					const children = instance.get(STR_CHILDREN);

					if (children) {
						result += ', ' + children + ':visible';
					}

					return result;
				},

				_headerEventHandler(event) {
					const instance = this;

					instance._focusManager.refresh();

					return TogglerInteraction.superclass._headerEventHandler.call(
						instance,
						event
					);
				},

				_onExpandedChange(event) {
					const instance = this;

					if (event.silent) {
						const host = instance.get(STR_HOST);

						const container = host.get(STR_CONTAINER);

						const headerCssClass =
							host.get(STR_HEADER) + ':visible';

						instance._focusManager.refresh();

						instance._focusManager.set(
							'activeDescendant',
							container.one(headerCssClass)
						);
					}
				},

				destructor() {
					const instance = this;

					new A.EventHandle(instance._eventHandles).detach();
				},

				initializer() {
					const instance = this;

					const host = instance.get(STR_HOST);

					const container = host.get(STR_CONTAINER);

					container.plug(A.Plugin.NodeFocusManager, {
						descendants: instance.get(STR_DESCENDANTS),
						keys: instance.get(STR_KEYS),
					});

					instance._eventHandles = [
						container.delegate(
							'key',
							instance._childrenEventHandler,
							instance.get(STR_KEYS).collapse,
							instance.get(STR_CHILDREN),
							instance
						),
						host.on(
							'toggler:expandedChange',
							instance._onExpandedChange,
							instance
						),
					];

					instance._focusManager = container.focusManager;
				},
			},
		});

		Liferay.TogglerInteraction = TogglerInteraction;
	},
	'',
	{
		requires: [
			'event-key',
			'liferay-toggler-key-filter',
			'node-focusmanager',
		],
	}
);
