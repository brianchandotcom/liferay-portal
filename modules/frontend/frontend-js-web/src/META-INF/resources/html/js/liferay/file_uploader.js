AUI.add(
	'liferay-file-uploader',
	function(A) {
		var Lang = A.Lang;

		var NAME = 'itemselectoruploader';

		var STR_BLANK = '';

		var STR_HOST = 'host';

		var STR_UNDERSCORE = '_';

		var STR_UPLOADABLE_FILE_RETURN_TYPE = 'com.liferay.item.selector.criteria.UploadableFileReturnType';

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
							uploader.on('uploadcomplete', instance._onUploadComplete, instance),
							uploader.on('uploaderror', instance._onUploadError, instance),
							uploader.on('uploadprogress', instance._onUploadProgress, instance)
						];
					},

					destructor: function() {
						var instance = this;

						if (instance._uploader) {
							instance._uploader.destroy();
						}

						(new A.EventHandle(instance._eventHandles)).detach();
					},

					_getUploader: function(uploadableItem) {
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

						if (uploadableItem) {
							instance._uploader.set('uploadURL', uploadableItem.value.uploadURL);
							instance._uploader.set('postVarsPerFile', { randomId: uploadableItem.value.id });
						}

						return uploader;
					},

					_onSelectedItemChange: function(event) {
						var instance = this;

						var item = event.newVal;

						if (item.returnType === STR_UPLOADABLE_FILE_RETURN_TYPE) {
							event.preventDefault();

							instance.startUpload(item);
						}
					},

					_onUploadComplete: function(event) {
						var instance = this;

						var host = instance.get(STR_HOST);

						var eventData = {
							uploadEvent: event
						};

						host.fire('selectedItemUploadComplete', event.details[0]);
					},

					_onUploadError: function(event) {
						var instance = this;

						var host = instance.get(STR_HOST);

						event.target.cancelUpload();

						host.fire('selectedItemUploadError', event.details[0]);
					},

					_onUploadProgress: function(event) {
						var instance = this;

						var host = instance.get(STR_HOST);

						host.fire('selectedItemUploadProgress', event.details[0]);
					},

					startUpload: function(uploadableItem) {
						var instance = this;

						var host = instance.get(STR_HOST);

						var uploader = instance._getUploader(uploadableItem);

						uploadableItem.uploader = uploader;

						var originalFile = uploadableItem.value.file;

						Object.setPrototypeOf(originalFile, File.prototype);

						uploadableItem.value.file = new A.FileHTML5(originalFile);

						uploader.upload(uploadableItem.value.file);

						host.fire(
							'selectedItemUploadStart',
							{
								data: uploadableItem
							}
						);
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