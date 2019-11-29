/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

AUI.add(
	'liferay-logo-editor',
	A => {
		var Lang = A.Lang;

		var LogoEditor = A.Component.create({
			ATTRS: {
				aspectRatio: {
					validator: Lang.isNumber,
					value: null
				},

				maxFileSize: {
					validator: Lang.isNumber,
					value:
						Liferay.PropsValues.UPLOAD_SERVLET_REQUEST_IMPL_MAX_SIZE
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

			AUGMENTS: [Liferay.PortletBase],

			EXTENDS: A.Base,

			NAME: 'logoeditor',

			prototype: {
				_defUploadCompleteFn(event) {
					var instance = this;

					var response = event.response;

					var portraitPreviewImg = instance._portraitPreviewImg;

					if (Lang.isObject(response)) {
						if (response.errorMessage) {
							instance._showError(response.errorMessage);

							instance._fileNameNode.set('value', '');
						}

						if (response.tempImageFileName) {
							var previewURL = instance.get('previewURL');

							var tempImageFileName = encodeURIComponent(
								response.tempImageFileName
							);

							previewURL = Liferay.Util.addParams(
								instance.get('namespace') +
									'tempImageFileName=' +
									tempImageFileName,
								previewURL
							);
							previewURL = Liferay.Util.addParams(
								't=' + Date.now(),
								previewURL
							);

							portraitPreviewImg.attr('src', previewURL);

							instance.one('#previewURL').val(previewURL);
							instance
								.one('#tempImageFileName')
								.val(response.tempImageFileName);
						}
					}

					portraitPreviewImg.removeClass('loading');

					if (instance._emptyResultMessage) {
						instance._emptyResultMessage.hide();
					}
				},

				_defUploadStartFn() {
					var instance = this;

					instance._getMessageNode().remove();

					Liferay.Util.toggleDisabled(instance._submitButton, true);
				},

				_getMessageNode(message, cssClass) {
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
						messageNode
							.removeClass('alert-danger')
							.removeClass('alert-success');

						messageNode.addClass(cssClass);
					}

					return messageNode;
				},

				_onFileNameChange() {
					var instance = this;

					var formValidator = Liferay.Form.get(
						instance._formNode.attr('id')
					).formValidator;

					formValidator.validateField(instance._fileNameNode);

					if (
						instance._fileNameNode.val() &&
						!formValidator.hasErrors()
					) {
						var imageCropper = instance._imageCropper;
						var portraitPreviewImg = instance._portraitPreviewImg;

						portraitPreviewImg.addClass('loading');

						portraitPreviewImg.attr(
							'src',
							themeDisplay.getPathThemeImages() + '/spacer.png'
						);

						if (imageCropper) {
							imageCropper.disable();
						}

						var form = document[instance.ns('fm')];

						instance.fire('uploadStart');

						Liferay.Util.fetch(instance.get('uploadURL'), {
							body: new FormData(form),
							method: 'POST'
						})
							.then(response => response.json())
							.then(response => {
								instance.fire('uploadComplete', {
									response
								});
							});
					}
				},

				_onImageLoad() {
					var instance = this;

					var imageCropper = instance._imageCropper;
					var portraitPreviewImg = instance._portraitPreviewImg;

					if (
						portraitPreviewImg.attr('src').indexOf('spacer.png') ==
						-1
					) {
						var aspectRatio = instance.get('aspectRatio');

						var portraitPreviewImgHeight = portraitPreviewImg.height();
						var portraitPreviewImgWidth = portraitPreviewImg.width();

						var cropHeight = portraitPreviewImgHeight;
						var cropWidth = portraitPreviewImgWidth;

						if (aspectRatio) {
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

							imageCropper.setAttrs({
								cropHeight,
								cropWidth,
								x: 0,
								y: 0
							});
						}
						else {
							imageCropper = new A.ImageCropper({
								cropHeight,
								cropWidth,
								preserveRatio: instance.get('preserveRatio'),
								srcNode: portraitPreviewImg
							}).render();

							instance._imageCrop = A.one('.image-cropper-crop');
							instance._imageCropper = imageCropper;
						}

						instance._setCropBackgroundSize(
							portraitPreviewImgWidth,
							portraitPreviewImgHeight
						);

						Liferay.Util.toggleDisabled(
							instance._submitButton,
							false
						);
					}
				},

				_onSubmit() {
					var instance = this;

					var imageCropper = instance._imageCropper;
					var portraitPreviewImg = document.getElementById(
						instance.get('namespace') + 'portraitPreviewImg'
					);

					if (imageCropper && portraitPreviewImg) {
						var region = imageCropper.get('region');

						var cropRegion = Liferay.Util.getCropRegion(
							portraitPreviewImg,
							region
						);

						instance._cropRegionNode.val(
							JSON.stringify(cropRegion)
						);
					}
				},

				_setCropBackgroundSize(width, height) {
					var instance = this;

					if (instance._imageCrop) {
						instance._imageCrop.setStyle(
							'backgroundSize',
							width + 'px ' + height + 'px'
						);
					}
				},

				_showError(message) {
					new Liferay.Alert({
						closeable: true,
						delay: {
							hide: 3000,
							show: 0
						},
						duration: 500,
						message,
						type: 'danger'
					}).render();
				},

				bindUI() {
					var instance = this;

					instance.publish('uploadComplete', {
						defaultFn: A.rbind('_defUploadCompleteFn', instance)
					});

					instance.publish('uploadStart', {
						defaultFn: A.rbind('_defUploadStartFn', instance)
					});

					instance._fileNameNode.on(
						'change',
						instance._onFileNameChange,
						instance
					);
					instance._formNode.on(
						'submit',
						instance._onSubmit,
						instance
					);
					instance._portraitPreviewImg.on(
						'load',
						instance._onImageLoad,
						instance
					);
				},

				destructor() {
					var instance = this;

					var imageCropper = instance._imageCropper;

					if (imageCropper) {
						imageCropper.destroy();
					}
				},

				initializer() {
					var instance = this;

					instance.renderUI();
					instance.bindUI();
				},

				renderUI() {
					var instance = this;

					instance._cropRegionNode = instance.one('#cropRegion');
					instance._emptyResultMessage = instance.one(
						'#emptyResultMessage'
					);
					instance._fileNameNode = instance.one('#fileName');
					instance._formNode = instance.one('#fm');
					instance._portraitPreviewImg = instance.one(
						'#portraitPreviewImg'
					);
					instance._submitButton = instance.one('#submitButton');
				},

				resize() {
					var instance = this;

					var portraitPreviewImg = instance._portraitPreviewImg;

					if (portraitPreviewImg) {
						instance._setCropBackgroundSize(
							portraitPreviewImg.width(),
							portraitPreviewImg.height()
						);
					}
				}
			}
		});

		Liferay.LogoEditor = LogoEditor;
	},
	'',
	{
		requires: ['aui-image-cropper', 'liferay-alert', 'liferay-portlet-base']
	}
);
