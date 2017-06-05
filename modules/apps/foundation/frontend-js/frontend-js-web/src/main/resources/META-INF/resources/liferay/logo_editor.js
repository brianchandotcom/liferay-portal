AUI.add(
	'liferay-logo-editor',
	function(A) {
		var Lang = A.Lang;

		var LogoEditor = A.Component.create(
			{
				ATTRS: {
					aspectRatio: {
						validator: Lang.isNumber,
						value: null
					},

					maxFileSize: {
						validator: Lang.isNumber,
						value: Liferay.PropsValues.UPLOAD_SERVLET_REQUEST_IMPL_MAX_SIZE
					},

					preserveRatio: {
						value: false
					},

					previewURL: {
						validator: Lang.isString,
						value: null
					},

					uploadURL: {
						validator: Lang.isString,
						value: null
					}
				},

				AUGMENTS: [Liferay.CropRegion, Liferay.PortletBase, Liferay.StorageFormatter],

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
						instance._emptyResultMessage = instance.one('#emptyResultMessage');
						instance._fileNameNode = instance.one('#fileName');
						instance._formNode = instance.one('#fm');
						instance._portraitPreviewImg = instance.one('#portraitPreviewImg');
						instance._submitButton = instance.one('#submitButton');
					},

					bindUI: function() {
						var instance = this;

						instance.publish(
							{
								uploadComplete: {
									defaultFn: A.rbind('_defUploadCompleteFn', instance)
								},
								uploadStart: {
									defaultFn: A.rbind('_defUploadStartFn', instance)
								}
							}
						);

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

					_defUploadCompleteFn: function(event, id, obj) {
						var instance = this;

						var responseText = obj.responseText;

						try {
							responseText = JSON.parse(responseText);
						}
						catch (e) {
						}

						var portraitPreviewImg = instance._portraitPreviewImg;

						if (Lang.isObject(responseText)) {
							if (responseText.errorMessage) {
								var messageNode = instance._getMessageNode(responseText.errorMessage, 'alert alert-danger');

								instance._formNode.prepend(messageNode);
							}

							if (responseText.tempImageFileName) {
								var previewURL = instance.get('previewURL');

								previewURL = Liferay.Util.addParams(instance.get('namespace') + 'tempImageFileName=' + responseText.tempImageFileName, previewURL);
								previewURL = Liferay.Util.addParams('t=' + Date.now(), previewURL);

								portraitPreviewImg.attr('src', previewURL);

								instance.one('#previewURL').val(previewURL);
								instance.one('#tempImageFileName').val(responseText.tempImageFileName);
							}
						}

						portraitPreviewImg.removeClass('loading');

						if (instance._emptyResultMessage) {
							instance._emptyResultMessage.hide();
						}
					},

					_defUploadStartFn: function(event, id, obj) {
						var instance = this;

						instance._getMessageNode().remove();

						Liferay.Util.toggleDisabled(instance._submitButton, true);
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
							messageNode.removeClass('alert-danger').removeClass('alert-success');

							messageNode.addClass(cssClass);
						}

						return messageNode;
					},

					_onFileNameChange: function(event) {
						var instance = this;

						var formValidator = Liferay.Form.get(instance._formNode.attr('id')).formValidator;

						formValidator.validateField(instance._fileNameNode);

						if (instance._fileNameNode.val() === '') {
							return;
						}

						if (!formValidator.hasErrors()) {
							var imageCropper = instance._imageCropper;
							var portraitPreviewImg = instance._portraitPreviewImg;

							portraitPreviewImg.addClass('loading');

							portraitPreviewImg.attr('src', themeDisplay.getPathThemeImages() + '/spacer.png');

							if (imageCropper) {
								imageCropper.disable();
							}

							A.io.request(
								instance.get('uploadURL'),
								{
									form: {
										id: instance.ns('fm'),
										upload: true
									},
									on: {
										complete: A.bind('fire', instance, 'uploadComplete'),
										start: A.bind('fire', instance, 'uploadStart')
									}
								}
							);
						}
					},

					_onImageLoad: function(event) {
						var instance = this;

						var imageCropper = instance._imageCropper;
						var portraitPreviewImg = instance._portraitPreviewImg;

						if (portraitPreviewImg.attr('src').indexOf('spacer.png') == -1) {
							var aspectRatio = instance.get('aspectRatio');

							var portraitPreviewImgHeight = portraitPreviewImg.height();
							var portraitPreviewImgWidth = portraitPreviewImg.width();

							var cropHeight = portraitPreviewImgHeight;
							var cropWidth = portraitPreviewImgWidth;

							if (aspectRatio !== null) {
								if (cropHeight < cropWidth) {
									cropWidth = cropHeight;
								}
								else {
									cropHeight = cropWidth;
								}

								if (aspectRatio > 1) {
									cropHeight = cropWidth / aspectRatio;
								}
								else {
									cropWidth = cropHeight * aspectRatio;
								}
							}

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
										preserveRatio: instance.get('preserveRatio'),
										srcNode: portraitPreviewImg
									}
								).render();

								instance._imageCrop = A.one('.image-cropper-crop');
								instance._imageCropper = imageCropper;
							}

							instance._setCropBackgroundSize(portraitPreviewImgWidth, portraitPreviewImgHeight);

							Liferay.Util.toggleDisabled(instance._submitButton, false);
						}
					},

					_onSubmit: function(event) {
						var instance = this;

						var imageCropper = instance._imageCropper;
						var portraitPreviewImg = instance._portraitPreviewImg;

						if (imageCropper && portraitPreviewImg) {
							var region = imageCropper.get('region');

							var cropRegion = instance._getCropRegion(portraitPreviewImg, region);

							instance._cropRegionNode.val(JSON.stringify(cropRegion));
						}
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
		requires: ['aui-image-cropper', 'aui-io-request', 'liferay-crop-region', 'liferay-portlet-base', 'liferay-storage-formatter']
	}
);