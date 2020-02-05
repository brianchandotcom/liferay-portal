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
	'liferay-widget-size-animation-plugin',
	A => {
		var Lang = A.Lang;

		var NAME = 'sizeanim';

		var STR_END = 'end';

		var STR_HOST = 'host';

		var STR_SIZE = 'size';

		var STR_START = 'start';

		var SizeAnim = A.Component.create({
			ATTRS: {
				align: {
					validator: Lang.isBoolean
				},
				duration: {
					validator: Lang.isNumber,
					value: 0.3
				},
				easing: {
					validator: Lang.isString,
					value: 'easeBoth'
				},
				preventTransition: {
					validator: Lang.isBoolean
				}
			},

			EXTENDS: A.Plugin.Base,

			NAME,

			NS: NAME,

			prototype: {
				_alignWidget() {
					var instance = this;

					if (instance.get('align')) {
						instance.get(STR_HOST).align();
					}
				},

				_animWidgetSize(size) {
					var instance = this;

					var host = instance.get(STR_HOST);

					instance._anim.stop();

					var attrs = {
						height: size.height,
						width: size.width
					};

					if (!instance.get('preventTransition')) {
						instance._anim.set('to', attrs);

						instance._anim.run();
					}
					else {
						instance.fire(STR_START);

						host.setAttrs(attrs);

						instance._alignWidget();

						instance.fire(STR_END);
					}
				},

				destructor() {
					var instance = this;

					instance.get(STR_HOST).removeAttr(STR_SIZE);

					new A.EventHandle(instance._eventHandles).detach();
				},

				initializer() {
					var instance = this;

					var host = instance.get(STR_HOST);

					host.addAttr(STR_SIZE, {
						setter: A.bind('_animWidgetSize', instance)
					});

					instance._anim = new A.Anim({
						duration: instance.get('duration'),
						easing: instance.get('easing'),
						node: host
					});

					var eventHandles = [
						instance._anim.after(
							STR_END,
							A.bind('fire', instance, STR_END)
						),
						instance._anim.after(
							STR_START,
							A.bind('fire', instance, STR_START)
						),
						instance._anim.after(
							'tween',
							instance._alignWidget,
							instance
						)
					];

					instance._eventHandles = eventHandles;
				}
			}
		});

		A.Plugin.SizeAnim = SizeAnim;
	},
	'',
	{
		requires: ['anim-easing', 'plugin', 'widget']
	}
);
