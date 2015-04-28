AUI.add(
	'liferay-item-browser',
	function(A) {
		var Lang = A.Lang;

		var CSS_DROP_ACTIVE = 'drop-active';

		var EVENT_FILE_SELECT = 'fileSelect';

		var ItemBrowser = A.Component.create(
			{
				ATTRS: {
					browseImageId: {
						validator: Lang.isString
					},

					inputFileId: {
						validator: Lang.isString
					},

					itemViewerCloseCaption: {
						validator: Lang.isString,
						value: ''
					},

					itemViewerContainer: {
						validator: Lang.isString
					},

					linkId: {
						validator: Lang.isString
					}
				},

				AUGMENTS: [Liferay.PortletBase, Liferay.StorageFormatter],

				EXTENDS: A.Base,

				NAME: 'itembrowser',

				prototype: {
					initializer: function() {
						var instance = this;

						instance._bindUI();

						instance._itemViewer = new A.LiferayItemViewer(
							{
								btnCloseCaption: instance.get('itemViewerCloseCaption'),
								infoTemplate: '',
								links: '#' + instance.ns(instance.get('linkId')),
								renderControls: false
							}
						).render('#' + instance.ns(instance.get('itemViewerContainer')));
					},

					destructor: function() {
						var instance = this;

						instance._itemViewer.destroy();

						(new A.EventHandle(instance._eventHandles)).detach();
					},

					_bindUI: function() {
						var instance = this;

						var browseImageNode = instance._getNodeById(instance.get('browseImageId'));
						var inputFileNode = instance._getNodeById(instance.get('inputFileId'));

						instance._eventHandles = [
							browseImageNode.on('click', A.bind(instance._onBrowseClick, instance)),
							inputFileNode.on('change', A.bind(instance._onInputFileChanged, instance)),
							instance.on(EVENT_FILE_SELECT, instance._onFileSelect, instance),
							instance.rootNode.on('dragover', instance._ddEventHandler, instance),
							instance.rootNode.on('dragleave', instance._ddEventHandler, instance),
							instance.rootNode.on('drop', instance._ddEventHandler, instance)
						];
					},

					_ddEventHandler: function(event) {
						var instance = this;

						event.stopPropagation();
						event.preventDefault();

						switch (event.type) {
							case 'dragover':
								instance.rootNode.addClass(CSS_DROP_ACTIVE);

								break;
							case 'dragleave':
								instance.rootNode.removeClass(CSS_DROP_ACTIVE);

								break;
							case 'drop':
								instance.rootNode.removeClass(CSS_DROP_ACTIVE);

								var file = event._event.dataTransfer.files[0];

								if (file) {
									instance.fire(
										EVENT_FILE_SELECT,
										{
											file: file
										}
									);
								}

								break;
						}
					},

					_getNodeById: function(id) {
						var instance = this;

						return AUI.$('#' + instance.ns(id));
					},

					_onBrowseClick: function() {
						var instance = this;

						var inputFileNode = instance._getNodeById(instance.get('inputFileId'));

						inputFileNode.click();
					},

					_onFileSelect: function(evt) {
						var instance = this;

						var file = evt.file;

						var reader = new FileReader();

						reader.onload = function(event) {
							var imageSrc = event.target.result;
							var fileName = file.name;

							var imageLinkNode = instance._getNodeById(instance.get('linkId'));

							imageLinkNode.attr('href', imageSrc);
							imageLinkNode.attr('title', fileName);

							var imageInfoNode = imageLinkNode.siblings('.image-info');

							imageInfoNode.find('#imageName').html(fileName);
							imageInfoNode.find('#imageSize').html(instance.formatStorage(file.size));
							imageInfoNode.find('#imageExtension').html(fileName.substring(fileName.lastIndexOf('.') + 1));

							var itemViewer = instance._itemViewer;

							itemViewer.set('links', '#' + instance.ns(instance.get('linkId')));

							itemViewer.show();
						};

						reader.readAsDataURL(file);
					},

					_onInputFileChanged: function(event) {
						var instance = this;

						var file = event.target.files[0];

						instance.fire(
							EVENT_FILE_SELECT,
							{
								file: file
							}
						);
					}
				}
			}
		);

		Liferay.ItemBrowser = ItemBrowser;
	},
	'',
	{
		requires: ['liferay-item-viewer', 'liferay-portlet-base', 'liferay-storage-formatter']
	}
);