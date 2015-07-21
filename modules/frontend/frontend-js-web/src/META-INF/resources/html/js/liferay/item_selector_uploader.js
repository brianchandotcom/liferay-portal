AUI.add(
	'liferay-item-selector-uploader',
	function(A) {
		var CSS_UPLOADING = 'uploading';

		var NAME = 'itemselectoruploader';

		var PROGRESS_HEIGHT = '6';

		var TPL_PROGRESS_BAR = '<div class="col-md-10 progressbar"></div>';

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

						instance._eventHandles = [
							uploader.on('uploadcomplete', instance._onUploadComplete, instance),
							uploader.on('uploaderror', instance._onUploadError, instance),
							uploader.on('uploadprogress', instance._onUploadProgress, instance)
						];

						instance._createProgressBar();
					},

					destructor: function() {
						var instance = this;

						if (instance._uploader) {
							instance._uploader.destroy();
						}

						(new A.EventHandle(instance._eventHandles)).detach();
					},

					startUpload: function(file, url) {
						var instance = this;

						file = new A.FileHTML5(file);

						var uploader = instance._getUploader();

						uploader.upload(file, url);

						instance.rootNode.addClass(CSS_UPLOADING);
					},

					_createProgressBar: function() {
						var instance = this;

						var rootNode = instance.rootNode;

						var progressBarNode = A.Node.create(TPL_PROGRESS_BAR);

						rootNode.append(progressBarNode);

						var progressbar = new A.ProgressBar(
							{
								boundingBox: progressBarNode,
								height: PROGRESS_HEIGHT
							}
						).render();

						instance._progressbar = progressbar;
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

					_onUploadComplete: function(event) {
						var instance = this;

						instance.rootNode.removeClass(CSS_UPLOADING);

						var data = JSON.parse(event.data);

						var eventName = data.success ? 'itemUploadComplete' : 'itemUploadError';

						instance.fire(eventName, data);
					},

					_onUploadError: function(event) {
						var instance = this;

						event.target.cancelUpload();

						instance.rootNode.removeClass(CSS_UPLOADING);

						instance.fire('itemUploadError', event.details[0]);
					},

					_onUploadProgress: function(event) {
						var instance = this;

						var percentLoaded = Math.round(event.percentLoaded);

						var progressbar = instance._progressbar;

						if (progressbar) {
							progressbar.set('value', Math.ceil(percentLoaded));
						}
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