AUI.add(
	'liferay-item-selector-uploader',
	function(A) {
		var CSS_PROGRESS = 'progress';

		var CSS_UPLOADING = 'uploading';

		var NAME = 'itemselectoruploader';

		var PROGRESS_HEIGHT = '6';

		var STR_VALUE = 'value';

		var TPL_PROGRESS_BAR = '<div class="progress-container">' +
				'<div class="upload-details">' +
					'<strong id="{0}itemName"></strong>' +
					'<a href="javascript:;" id="{0}cancel">' + Liferay.Language.get('cancel') + '</a>' +
				'</div>' +
				'<div class="' + CSS_PROGRESS + '"></div>' +
			'</div>';

		var ItemUploader = A.Component.create(
			{
				AUGMENTS: [Liferay.PortletBase],

				EXTENDS: A.Base,

				NAME: NAME,

				NS: NAME,

				prototype: {
					initializer: function() {
						var instance = this;

						var uploader = instance._getUploader();

						instance._createProgressBar();

						var cancelBtn = instance._progressBarNode.oneNS(instance.NS, '#cancel');

						instance._eventHandles = [
							uploader.on('uploadcomplete', instance._onUploadComplete, instance),
							uploader.on('uploaderror', instance._onUploadError, instance),
							uploader.on('uploadprogress', instance._onUploadProgress, instance),
							cancelBtn.on('click', instance._onCancel, instance)
						];
					},

					destructor: function() {
						var instance = this;

						if (instance._uploader) {
							instance._uploader.destroy();
						}

						if (instance._progressBar) {
							instance._progressBar.destroy();
						}

						(new A.EventHandle(instance._eventHandles)).detach();
					},

					startUpload: function(file, url) {
						var instance = this;

						file = new A.FileHTML5(file);

						var uploader = instance._getUploader();

						uploader.upload(file, url);

						instance._currentFile = file;

						instance._progressBarNode.oneNS(instance.NS, '#itemName').html(file.get('name'));

						instance.rootNode.addClass(CSS_UPLOADING);
					},

					_createProgressBar: function() {
						var instance = this;

						var rootNode = instance.rootNode;

						var progressbarNode = A.Node.create(A.Lang.sub(TPL_PROGRESS_BAR, [instance.NS]));

						rootNode.append(progressbarNode);

						instance._progressBarNode = progressbarNode;

						var progressbar = new A.ProgressBar(
							{
								boundingBox: progressbarNode.one('.' + CSS_PROGRESS),
								height: PROGRESS_HEIGHT
							}
						).render();

						instance._progressBar = progressbar;
					},

					_getUploader: function() {
						var instance = this;

						var uploader = instance._uploader;

						if (!uploader) {
							uploader = new A.Uploader(
								{
									fileFieldName: 'imageSelectorFileName'
								}
							);

							instance._uploader = uploader;
						}

						return uploader;
					},

					_onCancel: function() {
						var instance = this;

						instance._currentFile.cancelUpload();

						instance._stopProgress();

						instance.fire('itemUploadCancel');
					},

					_onUploadComplete: function(event) {
						var instance = this;

						instance._stopProgress();

						var data = JSON.parse(event.data);

						var eventName = data.success ? 'itemUploadComplete' : 'itemUploadError';

						instance.fire(eventName, data);
					},

					_onUploadError: function(event) {
						var instance = this;

						event.target.cancelUpload();

						instance._stopProgress();

						instance.fire('itemUploadError', event.details[0]);
					},

					_onUploadProgress: function(event) {
						var instance = this;

						var percentLoaded = Math.round(event.percentLoaded);

						instance._progressBar.set(STR_VALUE, Math.ceil(percentLoaded));
					},

					_stopProgress: function() {
						var instance = this;

						instance._progressBar.set(STR_VALUE, 0);

						instance.rootNode.removeClass(CSS_UPLOADING);
					}
				}
			}
		);

		A.LiferayItemSelectorUploader = ItemUploader;
	},
	'',
	{
		requires: ['aui-base', 'aui-progressbar', 'liferay-portlet-base', 'uploader']
	}
);