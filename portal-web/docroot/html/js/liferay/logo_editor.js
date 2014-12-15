AUI.add(
	'liferay-logo-editor',
	function(A) {
		var Lang = A.Lang;

		var LogoEditor = A.Component.create(
			{
				ATTRS: {
					maxFileSize: {
						value: null
					},

					previewURL: {
						value: null
					},

					uploadURL: {
						value: null
					}
				},

				AUGMENTS: [Liferay.PortletBase],

				EXTENDS: A.Base,

				NAME: 'logoeditor',

				prototype: {
					initializer: function() {
						var instance = this;

						instance.renderUI();
						instance.bindUI();
					},

					renderUI: function() {
						var instance = this;

						instance._cropRegionNode = instance.one('#cropRegion');
						instance._fileNameNode = instance.one('#fileName');
						instance._formNode = instance.one('#fm');
						instance._portraitPreviewImg = instance.one('#portraitPreviewImg');
						instance._submitButton = instance.one('#submitButton');
					},

					bindUI: function() {
						var instance = this;

						instance._fileNameNode.on('change', instance._onFileNameChange, instance);
						instance._formNode.on('submit', instance._onSubmit, instance);
						instance._portraitPreviewImg.on('load', instance._onImageLoad, instance);
					},

					destructor: function() {
						var instance = this;

						var imageCropper = instance._imageCropper;

						if (imageCropper) {
							imageCropper.destroy();
						}
					},

					resize: function() {
						var instance = this;

						var portraitPreviewImg = instance._portraitPreviewImg;

						if (portraitPreviewImg) {
							instance._setCropBackgroundSize(portraitPreviewImg.width(), portraitPreviewImg.height());
						}
					},

					_getImgNaturalSize: function(img) {
						var imageHeight = img.get('naturalHeight');
						var imageWidth = img.get('naturalWidth');

						if (Lang.isUndefined(imageHeight) || Lang.isUndefined(imageWidth)) {
							var tmp = new Image();

							tmp.src = img.attr('src');

							imageHeight = tmp.height;
							imageWidth = tmp.width;
						}

						return {
							height: imageHeight,
							width: imageWidth
						};
					},

					_getMessageNode: function(message, cssClass) {
						var instance = this;

						var messageNode = instance._messageNode;

						if (!messageNode) {
							messageNode = A.Node.create('<div></div>');

							instance._messageNode = messageNode;
						}

						if (message) {
							messageNode.html(message);
						}

						if (cssClass) {
							messageNode.removeClass('alert-error').removeClass('alert-success');

							messageNode.addClass(cssClass);
						}

						return messageNode;
					},

					_onFileNameChange: function(event) {
						var instance = this;

						var uploadURL = instance.get('uploadURL');

						var imageCropper = instance._imageCropper;
						var portraitPreviewImg = instance._portraitPreviewImg;

						portraitPreviewImg.addClass('loading');

						portraitPreviewImg.attr('src', themeDisplay.getPathThemeImages() + '/spacer.png');

						if (imageCropper) {
							imageCropper.disable();
						}

						A.io.request(
							uploadURL,
							{
								form: {
									id: instance.ns('fm'),
									upload: true
								},
								on: {
									complete: A.bind('_onUploadComplete', instance),
									start: A.bind('_onUploadStart', instance)
								}
							}
						);
					},

					_onImageLoad: function(event) {
						var instance = this;

						var imageCropper = instance._imageCropper;
						var portraitPreviewImg = instance._portraitPreviewImg;

						if (portraitPreviewImg.attr('src').indexOf('spacer.png') == -1) {
							var cropHeight = portraitPreviewImg.height();
							var cropWidth = portraitPreviewImg.width();

							if (imageCropper) {
								imageCropper.enable();

								imageCropper.syncImageUI();

								imageCropper.setAttrs(
									{
										cropHeight: cropHeight,
										cropWidth: cropWidth,
										x: 0,
										y: 0
									}
								);
							}
							else {
								imageCropper = new A.ImageCropper(
									{
										cropHeight: cropHeight,
										cropWidth: cropWidth,
										srcNode: portraitPreviewImg
									}
								).render();

								instance._imageCrop = A.one('.image-cropper-crop');
								instance._imageCropper = imageCropper;
							}

							instance._setCropBackgroundSize(cropWidth, cropHeight);

							Liferay.Util.toggleDisabled(instance._submitButton, false);
						}
					},

					_onSubmit: function(event) {
						var instance = this;

						var imageCropper = instance._imageCropper;
						var portraitPreviewImg = instance._portraitPreviewImg;

						if (imageCropper && portraitPreviewImg) {
							var region = imageCropper.get('region');

							var naturalSize = instance._getImgNaturalSize(portraitPreviewImg);

							var scaleX = naturalSize.width / portraitPreviewImg.width();
							var scaleY = naturalSize.height / portraitPreviewImg.height();

							var cropRegion = {
								height: region.height * scaleY,
								x: region.x * scaleX,
								y: region.y * scaleY,
								width: region.width * scaleX
							};

							instance._cropRegionNode.val(A.JSON.stringify(cropRegion));
						}
					},

					_onUploadComplete: function(event, id, obj) {
						var instance = this;

						var responseText = obj.responseText;

						var exception;

						if (responseText.indexOf('FileSizeException') > -1) {
							exception = 'FileSizeException';
						}
						else if (responseText.indexOf('TypeException') > -1) {
							exception = 'TypeException';
						}

						if (exception) {
							if (exception == 'FileSizeException') {
								message = Lang.sub(
									Liferay.Language.get('upload-images-no-larger-than-x-k'),
									[instance.get('maxFileSize')]
								);
							}
							else {
								message = Liferay.Language.get('please-enter-a-file-with-a-valid-file-type');
							}

							var messageNode = instance._getMessageNode(message, 'alert alert-error');

							instance._formNode.prepend(messageNode);
						}

						var previewURL = instance.get('previewURL');

						previewURL = Liferay.Util.addParams('t=' + Lang.now(), previewURL);

						var portraitPreviewImg = instance._portraitPreviewImg;

						portraitPreviewImg.attr('src', previewURL);

						portraitPreviewImg.removeClass('loading');
					},

					_onUploadStart: function(event, id, obj) {
						var instance = this;

						instance._getMessageNode().remove();

						Liferay.Util.toggleDisabled(instance._submitButton, true);
					},

					_setCropBackgroundSize: function(width, height) {
						var instance = this;

						if (instance._imageCrop) {
							instance._imageCrop.setStyle('backgroundSize', width + 'px ' + height + 'px');
						}
					}
				}
			}
		);

		Liferay.LogoEditor = LogoEditor;
	},
	'',
	{
		requires: ['aui-image-cropper', 'aui-io-request', 'liferay-portlet-base']
	}
);