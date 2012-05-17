AUI.add(
	'liferay-upload-progress',
	function(A) {
		var Lang = A.Lang;

		var STR_VALUE = 'value';

		var STR_UPDATE_PERIOD = 'updatePeriod';

		var NAME = 'uploadprogress';

		var UploadProgressBar = A.Component.create(
			{
				ATTRS: {
					redirect: {
						validator: Lang.isString,
						value: null
					},

					updatePeriod: {
						validator: Lang.isNumber,
						value: 1000
					}
				},

				EXTENDS: A.ProgressBar,

				NAME: NAME,

				prototype: {
					animateBar: function(percent) {
						var instance = this;

						instance.set(STR_VALUE, percent);
					},

					hideProgress: function() {
						var instance = this;

						instance.hide();
					},

					sendRedirect: function() {
						var instance = this;

						var redirect = instance.get('redirect');

						window.location = decodeURIComponent(redirect);
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

					updateBar: function(percent) {
						var instance = this;

						instance.set(STR_VALUE, percent);
					},

					updateProgress: function() {
						var instance = this;

						var uploadProgressId = instance.get('id');

						var uploadProgressPoller = document.getElementById(uploadProgressId + '-poller');

						var updatePeriod = instance.get(STR_UPDATE_PERIOD);

						uploadProgressPoller.src = themeDisplay.getPathMain() + '/portal/upload_progress_poller?uploadProgressId=' + uploadProgressId + '&updatePeriod=' + updatePeriod;
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