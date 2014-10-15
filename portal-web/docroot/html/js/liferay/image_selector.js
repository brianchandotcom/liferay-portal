AUI.add(
	'liferay-image-selector',
	function(A) {
		var Lang = A.Lang;

		var CSS_DROP_ACTIVE = 'drop-active';

		var STR_CLICK = 'click';

		var STR_IMAGE_DATA = 'imageData';

		var ImageSelector = A.Component.create(
			{
				ATTRS: {
					documentSelectorURL: {
						validator: Lang.isString
					},

					paramName: {
						validator: Lang.isString
					},

					uploadURL: {
						validator: Lang.isString
					}
				},

				AUGMENTS: [Liferay.PortletBase],

				EXTENDS: A.Base,

				NAME: 'imageselector',

				prototype: {
					initializer: function(config) {
						var instance = this;

						instance._bindUI();

						instance._renderUploader();
					},

					destructor: function() {
						var instance = this;

						instance._uploader.destroy();

						(new A.EventHandle(instance._eventHandles)).detach();
					},

					_bindUI: function() {
						var instance = this;

						instance._updateImageDataFn = A.bind('_updateImageData', instance);

						instance.publish(
							STR_IMAGE_DATA,
							{
								defaultFn: A.bind('_defImageDataFn', instance)
							}
						);

						instance._eventHandles = [
							instance.rootNode.delegate(STR_CLICK, instance._onBrowseClick, '.browse-image', instance),
							instance.one('#removeImage').on(STR_CLICK, instance._updateImageData, instance)
						];
					},

					_defImageDataFn: function(event) {
						var instance = this;

						var fileEntryIdNode = instance.rootNode.one('#' + instance.get('paramName') + 'Id');

						var fileEntryImage = instance.one('#image');

						var browseImageControls = instance.one('.browse-image-controls');
						var changeImageControls = instance.one('.change-image-controls');

						var fileEntryId = event.imageData.fileEntryId;
						var fileEntryUrl = event.imageData.url;

						fileEntryIdNode.val(fileEntryId);

						fileEntryImage.attr('src', fileEntryUrl);

						var showImageControls = (fileEntryId !== 0 && fileEntryUrl !== '');

						fileEntryImage.toggle(showImageControls);

						changeImageControls.toggle(showImageControls);
						browseImageControls.toggle(!showImageControls);

						instance.rootNode.toggleClass('drop-enabled', !showImageControls);
					},

					_onBrowseClick: function(event) {
						var instance = this;

						Liferay.Util.selectEntity(
							{
								dialog: {
									constrain: true,
									destroyOnHide: true,
									modal: true
								},
								eventName: instance.ns('selectImage'),
								id: instance.ns('selectImage'),
								title: Liferay.Language.get('select-image'),
								uri: instance.get('documentSelectorURL')
							},
							instance._updateImageDataFn
						);
					},

					_onFileSelect: function(event) {
						var instance = this;

						instance.rootNode.removeClass(CSS_DROP_ACTIVE);

						instance._uploader.uploadAll();
					},

					_onUploadComplete: function(event) {
						var instance = this;

						var data = event.data;

						try {
							data = A.JSON.parse(data);
						}
						catch (err) {
						}

						if (data.success) {
							instance.fire(
								STR_IMAGE_DATA,
								{
									imageData: data.image
								}
							);
						}
					},

					_renderUploader: function() {
						var instance = this;

						instance._uploader = new A.Uploader(
							{
								boundingBox: instance.rootNode,
								dragAndDropArea: instance.rootNode,
								fileFieldName: 'imageSelectorFileName',
								on: {
									dragleave: A.bind('removeClass', instance.rootNode, CSS_DROP_ACTIVE),
									dragover: A.bind('addClass', instance.rootNode, CSS_DROP_ACTIVE),
									fileselect: A.bind('_onFileSelect', instance),
									uploadcomplete: A.bind('_onUploadComplete', instance)
								},
								uploadURL: instance.get('uploadURL')
							}
						).render();
					},

					_updateImageData: function(event) {
						var instance = this;

						instance.fire(
							STR_IMAGE_DATA,
							{
								imageData: {
									fileEntryId: event.fileentryid || 0,
									url: event.url || ''
								}
							}
						);
					}
				}
			}
		);

		Liferay.ImageSelector = ImageSelector;
	},
	'',
	{
		requires: ['aui-base', 'liferay-portlet-base', 'uploader']
	}
);