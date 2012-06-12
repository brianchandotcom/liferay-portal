AUI.add(
	'liferay-logo-editor',
	function(A) {
		var Lang = A.Lang;

		var LogoEditor = A.Component.create(
			{
				ATTRS: {
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

						portraitPreviewImg.attr('src', '<%= themeDisplay.getPathThemeImages() %>/spacer.png');

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
									complete: function(event) {
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
							if (imageCropper) {
								imageCropper.enable();

								imageCropper.syncImageUI();

								imageCropper.setAttrs(
									{
										cropHeight: Math.max(portraitPreviewImg.height() * 0.3, 50),
										cropWidth: Math.max(portraitPreviewImg.width() * 0.3, 50),
										x: 0,
										y: 0
									}
								);
							}
							else {
								imageCropper = new A.ImageCropper(
									{
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