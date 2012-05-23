AUI.add(
	'liferay-upload-progress',
	function(A) {
		var Lang = A.Lang;

		var STR_VALUE = 'value';

		var STR_UPDATE_PERIOD = 'updatePeriod';

		var TPL_FRAME = '<iframe frameborder="0" height="0" id="{0}-poller" src="javascript:;" style="display:none" tabindex="-1" title="empty" width="0"></iframe>';

		var TPL_URL_UPDATE = themeDisplay.getPathMain() + '/portal/upload_progress_poller?uploadProgressId={0}&updatePeriod={1}';

		var WIN = A.config.win;

		var UploadProgressBar = A.Component.create(
			{
				ATTRS: {
					updatePeriod: {
						validator: Lang.isNumber,
						value: 1000
					}
				},

				EXTENDS: A.ProgressBar,

				NAME: 'uploadprogress',

				prototype: {
					renderUI: function() {
						var instance = this;

						UploadProgressBar.superclass.renderUI.call(instance, arguments);

						var tplFrame = Lang.sub(TPL_FRAME, [instance.get('id')]);

						var frame = A.Node.create(tplFrame);

						instance.get('boundingBox').placeBefore(frame);

						instance._frame = frame;
					},

					bindUI: function() {
						var instance = this;

						UploadProgressBar.superclass.bindUI.call(instance, arguments);

						instance.after('complete', instance._afterComplete);
						instance.after('valueChange', instance._afterValueChange);
					},

					startProgress: function() {
						var instance = this;

						if (!instance.get('rendered')) {
							instance.render();
						}

						instance.set(STR_VALUE, 0);

						setTimeout(
							function() {
								instance.updateProgress();
							},
							instance.get(STR_UPDATE_PERIOD)
						);
					},

					updateProgress: function() {
						var instance = this;

						var url = Lang.sub(TPL_URL_UPDATE, [instance.get('id'), instance.get(STR_UPDATE_PERIOD)]);

						instance._frame.set('src', url);
					},

					_afterComplete: function(event) {
						var instance = this;

						instance.set('label', instance.get('strings.complete'));
					},

					_afterValueChange: function(event) {
						var instance = this;

						instance.set('label', event.newVal + '%');
					}
				}
			}
		);

		Liferay.UploadProgressBar = UploadProgressBar;
	},
	'',
	{
		requires: ['aui-progressbar']
	}
);