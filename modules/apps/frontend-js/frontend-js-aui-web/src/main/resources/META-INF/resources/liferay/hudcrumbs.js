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
 * The Hudcrumbs Component.
 *
 * @deprecated As of Mueller (7.2.x), with no direct replacement
 * @module liferay-hudcrumbs
 */

AUI.add(
	'liferay-hudcrumbs',
	(A) => {
		const Lang = A.Lang;

		const NAME = 'hudcrumbs';

		const Hudcrumbs = A.Component.create({
			ATTRS: {
				clone: {
					value: null,
				},
				hostMidpoint: {
					validator: Lang.isNumber,
					value: 0,
				},
				scrollDelay: {
					validator: Lang.isNumber,
					value: 50,
				},
				width: {
					validtor: Lang.isNumber,
					value: 0,
				},
			},

			EXTENDS: A.Plugin.Base,

			NAME,

			NS: NAME,

			prototype: {
				_calculateDimensions() {
					const instance = this;

					const region = instance.get('host').get('region');

					instance.get('clone').setStyles({
						left: region.left + 'px',
						width: region.width + 'px',
					});

					instance.set(
						'hostMidpoint',
						region.top + region.height / 2
					);
				},

				_onScroll(event) {
					const instance = this;

					const scrollTop = event.currentTarget.get('scrollTop');

					const hudcrumbs = instance.get('clone');

					let action = 'hide';

					if (scrollTop >= instance.get('hostMidpoint')) {
						action = 'show';
					}

					if (instance.lastAction !== action) {
						hudcrumbs[action]();
					}

					instance.lastAction = action;
				},

				_onStartNavigate() {
					const instance = this;

					instance.get('clone').hide();
				},

				destructor() {
					const instance = this;

					Liferay.detach('startNavigate', instance._onStartNavigate);

					const win = instance._win;

					win.detach('scroll', instance._onScrollTask);
					win.detach('windowresize', instance._calculateDimensions);
				},

				initializer() {
					const instance = this;

					const breadcrumbs = instance.get('host');
					const hudcrumbs = breadcrumbs.clone();

					hudcrumbs.resetId();

					// eslint-disable-next-line @liferay/aui/no-get-body
					const body = A.getBody();
					const win = A.getWin();

					instance._body = body;
					instance._win = win;

					hudcrumbs.hide();

					hudcrumbs.addClass('lfr-hudcrumbs');

					instance.set('clone', hudcrumbs);

					instance._calculateDimensions();

					instance._onScrollTask = A.debounce(
						instance._onScroll,
						instance.get('scrollDelay'),
						instance
					);

					win.on('scroll', instance._onScrollTask);
					win.on(
						'windowresize',
						instance._calculateDimensions,
						instance
					);

					body.append(hudcrumbs);

					Liferay.on(
						'startNavigate',
						instance._onStartNavigate,
						instance
					);
				},
			},
		});

		A.Hudcrumbs = Hudcrumbs;
	},
	'',
	{
		requires: ['aui-base', 'aui-debounce', 'event-resize'],
	}
);
