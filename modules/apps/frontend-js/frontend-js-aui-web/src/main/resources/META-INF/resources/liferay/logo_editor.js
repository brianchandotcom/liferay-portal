/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

AUI.add(
	'liferay-logo-editor',
	(A) => {
		const Lang = A.Lang;

		const LogoEditor = A.Component.create({
			ATTRS: {
				aspectRatio: {
					validator: Lang.isNumber,
					value: null,
				},

				maxFileSize: {
					validator: Lang.isNumber,
					value:
						Liferay.PropsValues
							.UPLOAD_SERVLET_REQUEST_IMPL_MAX_SIZE,
				},

				preserveRatio: {
					value: false,
				},

				previewURL: {
					validator: Lang.isString,
					value: null,
				},

				uploadURL: {
					validator: Lang.isString,
					value: null,
				},
			},

			AUGMENTS: [Liferay.PortletBase],

			EXTENDS: A.Base,

			NAME: 'logoeditor',

			prototype: {
				_defUploadCompleteFn(event) {
					const instance = this;

					const response = event.response;

					const portraitPreviewImg = instance._portraitPreviewImg;

					if (Lang.isObject(response)) {
						if (response.errorMessage) {
							instance._showError(response.errorMessage);

							instance._fileNameNode.set('value', '');
						}

						if (response.tempImageFileName) {
							let previewURL = instance.get('previewURL');

							const tempImageFileName = encodeURIComponent(
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
					const instance = this;

					instance._getMessageNode().remove();

					Liferay.Util.toggleDisabled(instance._submitButton, true);
				},

				_getMessageNode(message, cssClass) {
					const instance = this;

					let messageNode = instance._messageNode;

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
					const instance = this;

					const formValidator = Liferay.Form.get(
						instance._formNode.attr('id')
					).formValidator;

					formValidator.validateField(instance._fileNameNode);

					if (
						instance._fileNameNode.val() &&
						!formValidator.hasErrors()
					) {
						const imageCropper = instance._imageCropper;
						const portraitPreviewImg = instance._portraitPreviewImg;

						portraitPreviewImg.addClass('loading');

						portraitPreviewImg.attr(
							'src',
							themeDisplay.getPathThemeImages() + '/spacer.png'
						);

						if (imageCropper) {
							imageCropper.disable();
						}

						const form = document[instance.ns('fm')];

						instance.fire('uploadStart');

						Liferay.Util.fetch(instance.get('uploadURL'), {
							body: new FormData(form),
							method: 'POST',
						})
							.then((response) => response.json())
							.then((response) => {
								instance.fire('uploadComplete', {
									response,
								});
							});
					}
				},

				_onImageLoad() {
					const instance = this;

					let imageCropper = instance._imageCropper;
					const portraitPreviewImg = instance._portraitPreviewImg;

					if (
						portraitPreviewImg.attr('src').indexOf('spacer.png') ===
						-1
					) {
						const aspectRatio = instance.get('aspectRatio');

						const portraitPreviewImgHeight = portraitPreviewImg.height();
						const portraitPreviewImgWidth = portraitPreviewImg.width();

						let cropHeight = portraitPreviewImgHeight;
						let cropWidth = portraitPreviewImgWidth;

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
								y: 0,
							});
						}
						else {
							imageCropper = new A.ImageCropper({
								cropHeight,
								cropWidth,
								preserveRatio: instance.get('preserveRatio'),
								srcNode: portraitPreviewImg,
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
					const instance = this;

					const imageCropper = instance._imageCropper;
					const portraitPreviewImg = document.getElementById(
						instance.get('namespace') + 'portraitPreviewImg'
					);

					if (imageCropper && portraitPreviewImg) {
						const region = imageCropper.get('region');

						const cropRegion = Liferay.Util.getCropRegion(
							portraitPreviewImg,
							region
						);

						instance._cropRegionNode.val(
							JSON.stringify(cropRegion)
						);
					}
				},

				_setCropBackgroundSize(width, height) {
					const instance = this;

					if (instance._imageCrop) {
						instance._imageCrop.setStyle(
							'backgroundSize',
							width + 'px ' + height + 'px'
						);
					}
				},

				_showError(message) {
					Liferay.Util.openToast({
						message,
						type: 'danger',
					});
				},

				bindUI() {
					const instance = this;

					instance.publish('uploadComplete', {
						defaultFn: A.rbind('_defUploadCompleteFn', instance),
					});

					instance.publish('uploadStart', {
						defaultFn: A.rbind('_defUploadStartFn', instance),
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
					const instance = this;

					const imageCropper = instance._imageCropper;

					if (imageCropper) {
						imageCropper.destroy();
					}
				},

				initializer() {
					const instance = this;

					instance.renderUI();
					instance.bindUI();
				},

				renderUI() {
					const instance = this;

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
					const instance = this;

					const portraitPreviewImg = instance._portraitPreviewImg;

					if (portraitPreviewImg) {
						instance._setCropBackgroundSize(
							portraitPreviewImg.width(),
							portraitPreviewImg.height()
						);
					}
				},
			},
		});

		Liferay.LogoEditor = LogoEditor;
	},
	'',
	{
		requires: ['aui-image-cropper', 'liferay-portlet-base'],
	}
);
