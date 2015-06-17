AUI.add(
	'liferay-file-uploader',
	function(A) {
		var Lang = A.Lang;

		var NAME = 'itemselectoruploader';

		var STR_BLANK = '';

		var STR_UNDERSCORE = '_';

		var STR_UPLOADABLE_FILE_RETURN_TYPE = 'com.liferay.item.selector.criteria.UploadableFileReturnType';

		var STR_UPLOAD_COMPLETE = 'uploadcomplete';

		var STR_UPLOAD_ERROR = 'uploaderror';

		var STR_UPLOAD_PROGRESS = 'uploadprogress';

		var FileUploader = A.Component.create(
			{
				EXTENDS: A.Plugin.Base,

				NAME: NAME,

				NS: NAME,

				prototype: {
					initializer: function() {
						var instance = this;

						var uploader = instance._getUploader();

						var host = instance.get('host');

						instance._eventHandles = [
							host.on('selectedItemChange', instance._onSelectedItemChange, instance),
							uploader.on(STR_UPLOAD_COMPLETE, instance._onUploadComplete, instance),
							uploader.on(STR_UPLOAD_ERROR, instance._onUploadError, instance),
							uploader.on(STR_UPLOAD_PROGRESS, instance._onUploadProgress, instance)
						];
					},

					destructor: function() {
						var instance = this;

						if (instance._uploader) {
							instance._uploader.destroy();
						}

						(new A.EventHandle(instance._eventHandles)).detach();
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

					_onSelectedItemChange: function(event) {
						var instance = this;

						var item = event.newVal;

						if (item.returnType === STR_UPLOADABLE_FILE_RETURN_TYPE) {
							event.preventDefault();

							var randomId = Date.now() + STR_UNDERSCORE + Liferay.Util.randomInt();

							item.randomId = randomId;

							item.uploader = instance._getUploader();

							var host = instance.get('host');

							host.fire('selectedItemUploadStart', item);

							instance.startUpload(item.value.file, item.value.uploadURL, randomId);
						}
					},

					_onUploadComplete: function(event) {
						var instance = this;

						instance.fire(STR_UPLOAD_COMPLETE, event.details[0]);
					},

					_onUploadError: function(event) {
						var instance = this;

						event.target.cancelUpload();

						instance.fire(STR_UPLOAD_ERROR, event.details[0]);
					},

					_onUploadProgress: function(event) {
						var instance = this;

						instance.fire(STR_UPLOAD_PROGRESS, event.details[0]);
					},

					startUpload: function(file, url, randomId) {
						var instance = this;

						Object.setPrototypeOf(file, File.prototype);

						file = new A.FileHTML5(file);

						var uploader = instance._getUploader();

						uploader.set(
							'postVarsPerFile',
							{
								randomId: randomId
							}
						);

						uploader.upload(file, url);
					}
				}
			}
		);

		A.Plugin.LiferayFileUploader = FileUploader;
	},
	'',
	{
		requires: ['aui-base', 'uploader']
	}
);