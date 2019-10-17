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
	'liferay-progress',
	A => {
		var Lang = A.Lang;

		var STR_EMPTY = '';

		var STR_UPDATE_PERIOD = 'updatePeriod';

		var STR_VALUE = 'value';

		var TPL_FRAME =
			'<iframe frameborder="0" height="0" id="{0}-poller" src="javascript:;" style="display:none" tabindex="-1" title="empty" width="0"></iframe>';

		var TPL_URL_UPDATE =
			themeDisplay.getPathMain() +
			'/portal/progress_poller?progressId={0}&sessionKey={1}&updatePeriod={2}';

		var Progress = A.Component.create({
			ATTRS: {
				message: {
					validator: Lang.isString,
					value: STR_EMPTY
				},

				sessionKey: {
					validator: Lang.isString,
					value: STR_EMPTY
				},

				updatePeriod: {
					validator: Lang.isNumber,
					value: 1000
				}
			},

			EXTENDS: A.ProgressBar,

			NAME: 'progress',

			prototype: {
				_afterComplete() {
					var instance = this;

					instance
						.get('boundingBox')
						.removeClass('lfr-progress-active');

					instance.set('label', instance.get('strings.complete'));

					instance._iframeLoadHandle.detach();
				},

				_afterValueChange(event) {
					var instance = this;

					var label = instance.get('message');

					if (!label) {
						label = event.newVal + '%';
					}

					instance.set('label', label);
				},

				_onIframeLoad() {
					var instance = this;

					setTimeout(() => {
						instance._frame.get('contentWindow.location').reload();
					}, instance.get(STR_UPDATE_PERIOD));
				},

				bindUI() {
					var instance = this;

					Progress.superclass.bindUI.call(instance, arguments);

					instance.after('complete', instance._afterComplete);
					instance.after('valueChange', instance._afterValueChange);

					instance._iframeLoadHandle = instance._frame.on(
						'load',
						instance._onIframeLoad,
						instance
					);
				},

				renderUI() {
					var instance = this;

					Progress.superclass.renderUI.call(instance, arguments);

					var tplFrame = Lang.sub(TPL_FRAME, [instance.get('id')]);

					var frame = A.Node.create(tplFrame);

					instance.get('boundingBox').placeBefore(frame);

					instance._frame = frame;
				},

				startProgress() {
					var instance = this;

					if (!instance.get('rendered')) {
						instance.render();
					}

					instance.set(STR_VALUE, 0);

					instance.get('boundingBox').addClass('lfr-progress-active');

					setTimeout(() => {
						instance.updateProgress();
					}, instance.get(STR_UPDATE_PERIOD));
				},

				updateProgress() {
					var instance = this;

					var url = Lang.sub(TPL_URL_UPDATE, [
						instance.get('id'),
						instance.get('sessionKey'),
						instance.get(STR_UPDATE_PERIOD)
					]);

					instance._frame.attr('src', url);
				}
			}
		});

		Liferay.Progress = Progress;
	},
	'',
	{
		requires: ['aui-progressbar']
	}
);
