AUI.add(
	'liferay-blogs-uploader',
	function(A) {
		var Lang = A.Lang;

		var CSS_UPLOADING_IMAGE = 'uploading-image';

		var FAILURE_TIMEOUT = 10000;

		var STR_BLANK = '';

		var STR_UNDERSCORE = '_';

		var TMPL_IMAGE_CONTAINER = '<div class="uploading-image-container"></div>';

		var TMPL_PROGRESS_BAR = '<div class="progressbar"></div>';

		var UPLOAD_PROGRESS_ID = 'blogsEntryUploadImageProgress';

		var BlogsUploader = A.Component.create(
			{
				ATTRS: {
					editor: {},

					strings: {
						validator: Lang.isObject,
						value: {
							uploadingFileError: Liferay.Language.get('an-unexpected-error-occurred-while-uploading-your-file')
						}
					},

					uploadUrl: {
						validator: Lang.isString,
						value: STR_BLANK
					}
				},

				EXTENDS: A.Plugin.Base,

				NAME: 'bloguploader',

				NS: 'upload',

				prototype: {
					initializer: function() {
						var instance = this;

						instance._editor = instance.get('editor');

						var uploader = instance._getUploader();

						instance._eventHandles = [
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

					uploadImage: function(image, file) {
						var instance = this;

						var randomId = new Date().getTime() + STR_UNDERSCORE + Liferay.Util.randomInt();

						image.setAttribute('data-random-id', randomId);
						image.addClass(CSS_UPLOADING_IMAGE);
						instance._image = image;

						instance._createProgressBar();

						var uploader = instance._getUploader();

						uploader.set(
							'postVarsPerFile',
							{
								'randomId': randomId
							}
						);

						uploader.upload(new A.FileHTML5(file));
					},

					_createProgressBar: function(event) {
						var instance = this;

						var imageContainerNode = A.Node.create(TMPL_IMAGE_CONTAINER);
						A.one(instance._image.$).wrap(imageContainerNode);

						var progressBarNode = A.Node.create(TMPL_PROGRESS_BAR);
						imageContainerNode.appendChild(progressBarNode);

						var progressbar = new A.ProgressBar(
							{
								boundingBox: progressBarNode,
								id: UPLOAD_PROGRESS_ID
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

						if (instance._progressbar) {
							instance._progressbar.destroy();
						}

						var data = event.data;

						try {
							data = A.JSON.parse(data);
						}
						catch (err) {
						}

						if (data.success) {
							var image = A.one(instance._editor.element.$).one('[data-random-id="' + data.image.randomId + '"]');

							if (image) {
								image.removeAttribute('data-random-id');
								image.removeClass(CSS_UPLOADING_IMAGE);
								image.setAttribute(data.image.dataImageIdAttribute, data.image.fileEntryId);
								image.setAttribute('src', data.image.url);
							}
						}
						else {
							instance._removeTmpImage(data.image);
						}
					},

					_onUploadError: function(event) {
						var instance = this;

						event.target.cancelUpload();

						instance._removeTmpImage(event);
					},

					_onUploadProgress: function(event) {
						var instance = this;

						var percentLoaded = Math.round(event.percentLoaded);

						var progressbar = instance._progressbar;

						if (progressbar) {
							progressbar.set('label', percentLoaded + '%');

							progressbar.set('value', Math.ceil(percentLoaded));
						}
					},

					_removeTmpImage: function(imageData) {
						var instance = this;

						if (imageData && imageData.randomId) {
							var imageId = imageData.randomId;

							var image = A.one(instance._editor.element.$).one('[data-random-id="' + imageId + '"]');

							image.remove();
						}

						var strings = instance.get('strings');

						new Liferay.Notice(
							{
								closeText: false,
								content: strings.uploadingFileError,
								noticeClass: 'hide',
								timeout: FAILURE_TIMEOUT,
								toggleText: false,
								type: 'warning',
								useAnimation: true
							}
						).show();
					}
				}
			}
		);

		Liferay.BlogsUploader = BlogsUploader;
	},
	'',
	{
		requires: ['aui-base', 'aui-progressbar', 'liferay-notice', 'uploader']
	}
);