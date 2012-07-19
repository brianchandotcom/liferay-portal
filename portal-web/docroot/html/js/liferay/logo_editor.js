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

						instance._imageCropper.destroy();
					},

					_onFileNameChange: function(event) {
						var instance = this;

						var previewURL = instance.get('previewURL');
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
									complete: function(event, id, obj) {
										var exception = null;

										if (obj.responseText.indexOf('FileSizeException') != -1) {
											exception = 'FileSizeException';
										}
										else if (obj.responseText.indexOf('TypeException') != -1) {
											exception = 'TypeException';
										}

										if (exception != null) {
											messageClass = 'portlet-msg-error';

											if (exception == 'FileSizeException') {
												message =  Lang.sub(
													Liferay.Language.get('upload-images-no-larger-than-x-k'),
													[instance.get('maxFileSize')]
												);
											}
											else {
												message = Liferay.Language.get('please-enter-a-file-with-a-valid-file-type');
											}

											TPL_MESSAGE = '<div class="{messageClass}">{message}</div>';
											var ErrorMessage = Lang.sub(
												TPL_MESSAGE,
												{
													message: message,
													messageClass: messageClass
												}
											);

											instance._formNode.prepend(ErrorMessage);
										}

										previewURL = Liferay.Util.addParams('t=' + Lang.now(), previewURL);
										portraitPreviewImg.attr('src', previewURL);
										portraitPreviewImg.removeClass('loading');
									},
									start: function() {
										Liferay.Util.toggleDisabled(instance._submitButton, true);
									}
								}
							}
						);
					},

					_onImageLoad: function(event) {
						var instance = this;

						var imageCropper = instance._imageCropper;
						var portraitPreviewImg = instance._portraitPreviewImg;

						if (portraitPreviewImg.attr('src').indexOf('spacer.png') == -1) {

							var cropHeight;
							var cropWidth;

							var portraitHeight = portraitPreviewImg.height();
							var portraitWidth = portraitPreviewImg.width();

							if (portraitHeight <= 50) {
								cropHeight = portraitHeight;
							}
							else{
								cropHeight = portraitHeight * 0.3;
							}
							if (portraitPreviewImg.width() <= 50) {
								cropWidth = portraitWidth;
							}
							else {
								cropWidth = portraitWidth * 0.3;
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
										srcNode: portraitPreviewImg
									}
								).render();

								instance._imageCropper = imageCropper;
							}

							Liferay.Util.toggleDisabled(instance._submitButton, false);
						}
					},

					_onSubmit: function(event) {
						var instance = this;

						var imageCropper = instance._imageCropper;

						if (imageCropper) {
							instance._cropRegionNode.val(A.JSON.stringify(imageCropper.get('region')));
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