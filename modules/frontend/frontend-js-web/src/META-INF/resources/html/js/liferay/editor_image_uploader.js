AUI.add(
	'liferay-editor-image-uploader',
	function(A) {
		var Lang = A.Lang;

		var CSS_UPLOADING_IMAGE = 'uploading-image';

		var NAME = 'editorimageupload';

		var STR_BLANK = '';

		var STR_HOST = 'host';

		var STR_UNDERSCORE = '_';

		var TPL_IMAGE_CONTAINER = '<div class="uploading-image-container"></div>';

		var TPL_PROGRESS_BAR = '<div class="progressbar"></div>';

		var BlogsUploader = A.Component.create(
			{
				ATTRS: {
					strings: {
						validator: Lang.isObject,
						value: {
							uploadingFileError: Liferay.Language.get('an-unexpected-error-occurred-while-uploading-your-file')
						}
					},

					timeout: {
						validator: Lang.isNumber,
						value: 10000
					},

					uploadUrl: {
						validator: Lang.isString,
						value: STR_BLANK
					}
				},

				EXTENDS: A.Plugin.Base,

				NAME: NAME,

				NS: NAME,

				prototype: {
					initializer: function() {
						var instance = this;

						var host = instance.get(STR_HOST);

						var editor = host.getNativeEditor();

						var uploader = instance._getUploader();

						instance._eventHandles = [
							editor.on('imagedrop', instance._uploadImage, instance),
							uploader.on('uploadcomplete', instance._onUploadComplete, instance),
							uploader.on('uploaderror', instance._onUploadError, instance),
							uploader.on('uploadprogress', instance._onUploadProgress, instance)
						];

						instance._editor = editor;
					},

					destructor: function() {
						var instance = this;

						if (instance._uploader) {
							instance._uploader.destroy();
						}

						if (instance._alert) {
							instance._alert.destroy();
						}

						(new A.EventHandle(instance._eventHandles)).detach();
					},

					_createProgressBar: function(image) {
						var instance = this;

						var imageContainerNode = A.Node.create(TPL_IMAGE_CONTAINER);
						var progressBarNode = A.Node.create(TPL_PROGRESS_BAR);

						image.wrap(imageContainerNode);

						imageContainerNode.appendChild(progressBarNode);

						var progressbar = new A.ProgressBar(
							{
								boundingBox: progressBarNode
							}
						).render();

						return progressbar;
					},

					_getAlert: function() {
						var instance = this;

						if (!instance._alert) {
							instance._alert = new A.Alert(
								{
									animated: true,
									closeable: true,
									cssClass: null,
									duration: instance.get('timeout'),
									render: true
								}
							);
						}

						return instance._alert;
					},

					_getUploader: function() {
						var instance = this;

						var uploader = instance._uploader;

						if (!uploader) {
							uploader = new A.Uploader(
								{
									fileFieldName: 'imageSelectorFileName',
									uploadURL: instance.get('uploadUrl')
								}
							);

							instance._uploader = uploader;
						}

						return uploader;
					},

					_onUploadComplete: function(event) {
						var instance = this;

						var target = event.details[0].target;

						var progressbar = target.progressbar;

						if (progressbar) {
							progressbar.destroy();
						}

						var data = A.JSON.parse(event.data);

						if (data.success) {
							var image = A.one(instance._editor.element.$).one('[data-random-id="' + data.image.randomId + '"]');

							if (image) {
								image.removeAttribute('data-random-id');
								image.removeClass(CSS_UPLOADING_IMAGE);

								image.attr(data.image.attributeDataImageId, data.image.fileEntryId);
								image.attr('src', data.image.url);

								var imageContainer = image.ancestor();

								image.unwrap(imageContainer);

								imageContainer.remove();
							}
						}
						else {
							instance._removeTempImage(data.image);
						}
					},

					_onUploadError: function(event) {
						var instance = this;

						event.target.cancelUpload();

						instance._removeTempImage(event);
					},

					_onUploadProgress: function(event) {
						var instance = this;

						var percentLoaded = Math.round(event.percentLoaded);

						var target = event.details[0].target;

						var progressbar = target.progressbar;

						if (progressbar) {
							progressbar.set('label', percentLoaded + ' %');

							progressbar.set('value', Math.ceil(percentLoaded));
						}
					},

					_removeTempImage: function(imageData) {
						var instance = this;

						if (imageData && imageData.randomId) {
							var imageId = imageData.randomId;

							var image = A.one(instance._editor.element.$).one('[data-random-id="' + imageId + '"]');

							image.ancestor().remove();
						}

						var strings = instance.get('strings');

						var alert = instance._getAlert();

						alert.set('bodyContent', strings.uploadingFileError).show();
					},

					_uploadImage: function(event) {
						var instance = this;

						var image = event.data.el.$;
						var file = event.data.file;

						image = A.one(image);

						var randomId = Lang.now() + STR_UNDERSCORE + Liferay.Util.randomInt();

						image.attr('data-random-id', randomId);

						image.addClass(CSS_UPLOADING_IMAGE);

						file = new A.FileHTML5(file);

						file.progressbar = instance._createProgressBar(image);

						var uploader = instance._getUploader();

						uploader.set(
							'postVarsPerFile',
							{
								randomId: randomId
							}
						);

						uploader.upload(file);
					}
				}
			}
		);

		A.Plugin.LiferayBlogsUploader = BlogsUploader;
	},
	'',
	{
		requires: ['aui-base', 'aui-progressbar', 'liferay-notice', 'uploader']
	}
);