/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

AUI.add(
	'liferay-progress',
	(A) => {
		const Lang = A.Lang;

		const STR_EMPTY = '';

		const STR_UPDATE_PERIOD = 'updatePeriod';

		const STR_VALUE = 'value';

		const TPL_FRAME =
			'<iframe frameborder="0" height="0" id="{0}-poller" src="javascript:void(0);" style="display:none" tabindex="-1" title="empty" width="0"></iframe>';

		const TPL_URL_UPDATE =
			themeDisplay.getPathMain() +
			'/portal/progress_poller?progressId={0}&sessionKey={1}&updatePeriod={2}';

		const Progress = A.Component.create({
			ATTRS: {
				message: {
					validator: Lang.isString,
					value: STR_EMPTY,
				},

				sessionKey: {
					validator: Lang.isString,
					value: STR_EMPTY,
				},

				updatePeriod: {
					validator: Lang.isNumber,
					value: 1000,
				},
			},

			EXTENDS: A.ProgressBar,

			NAME: 'progress',

			prototype: {
				_afterComplete() {
					const instance = this;

					instance
						.get('boundingBox')
						.removeClass('lfr-progress-active');

					instance.set('label', instance.get('strings.complete'));

					instance._iframeLoadHandle.detach();
				},

				_afterValueChange(event) {
					const instance = this;

					let label = instance.get('message');

					if (!label) {
						label = event.newVal + '%';
					}

					instance.set('label', label);
				},

				_onIframeLoad() {
					const instance = this;

					setTimeout(() => {
						instance._frame
							.get('contentWindow')
							?.location?.reload();
					}, instance.get(STR_UPDATE_PERIOD));
				},

				bindUI() {
					const instance = this;

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
					const instance = this;

					Progress.superclass.renderUI.call(instance, arguments);

					const tplFrame = Lang.sub(TPL_FRAME, [instance.get('id')]);

					const frame = A.Node.create(tplFrame);

					instance.get('boundingBox').placeBefore(frame);

					instance._frame = frame;
				},

				startProgress() {
					const instance = this;

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
					const instance = this;

					const url = Lang.sub(TPL_URL_UPDATE, [
						instance.get('id'),
						instance.get('sessionKey'),
						instance.get(STR_UPDATE_PERIOD),
					]);

					instance._frame.attr('src', url);
				},
			},
		});

		Liferay.Progress = Progress;
	},
	'',
	{
		requires: ['aui-progressbar'],
	}
);
