AUI.add(
	'liferay-item-selector-browser',
	function(A) {
		var AArray = A.Array;
		var Lang = A.Lang;

		var CSS_DROP_ACTIVE = 'drop-active';

		var STR_DRAG_LEAVE = 'dragleave';

		var STR_DRAG_OVER = 'dragover';

		var STR_DROP = 'drop';

		var STR_ITEM_SELECTED = '_onItemSelected';

		var STR_LINKS = 'links';

		var STR_SELECTED_ITEM = 'selectedItem';

		var STR_UPLOADABLE_FILE_RETURN_TYPE = 'com.liferay.item.selector.criteria.UploadableFileReturnType';

		var STR_VISIBLE_CHANGE = 'visibleChange';

		var UPLOAD_ITEM_LINK_TPL = '<a data-returnType="{returnType}" data-value="{value}" href="{preview}" title="{title}"></a>';

		var ItemSelectorBrowser = A.Component.create(
			{
				ATTRS: {
					closeCaption: {
						validator: Lang.isString,
						value: ''
					}
				},

				AUGMENTS: [Liferay.PortletBase],

				EXTENDS: A.Base,

				NAME: 'itemselectorbrowser',

				prototype: {
					initializer: function() {
						var instance = this;

						instance._itemViewer = new A.LiferayItemViewer(
							{
								btnCloseCaption: instance.get('closeCaption'),
								links: instance.all('.item-preview')
							}
						);

						instance._uploadItemViewer = new A.LiferayItemViewer(
							{
								btnCloseCaption: instance.get('closeCaption'),
								links: '',
								renderControls: false
							}
						);

						instance._itemSelectorUploader = new A.LiferayItemSelectorUploader(
							{
								rootNode: instance.rootNode
							}
						);

						instance._dropArea = A.one('.drop-zone');

						instance._bindUI();
						instance._renderUI();
					},

					destructor: function() {
						var instance = this;

						instance._itemViewer.destroy();
						instance._uploadItemViewer.destroy();
						instance._itemSelectorUploader.destroy();

						(new A.EventHandle(instance._eventHandles)).detach();
					},

					_afterVisibleChange: function(event) {
						var instance = this;

						if (!event.newVal) {
							instance.fire(STR_SELECTED_ITEM);
						}
					},

					_bindUI: function() {
						var instance = this;

						var itemViewer = instance._itemViewer;

						var uploadItemViewer = instance._uploadItemViewer;

						var itemSelectorUploader = instance._itemSelectorUploader;

						var rootNode = instance.rootNode;

						instance._eventHandles = [
							itemViewer.get(STR_LINKS).on('click', A.bind(STR_ITEM_SELECTED, instance, itemViewer)),
							itemViewer.after('currentIndexChange', A.bind(STR_ITEM_SELECTED, instance, itemViewer)),
							itemViewer.after(STR_VISIBLE_CHANGE, instance._afterVisibleChange, instance),
							uploadItemViewer.after(STR_VISIBLE_CHANGE, instance._afterVisibleChange, instance),
							itemSelectorUploader.after('itemUploadCancel', instance._onItemUploadCancel, instance),
							itemSelectorUploader.after('itemUploadComplete', instance._onItemUploadComplete, instance),
							itemSelectorUploader.after('itemUploadError', instance._onItemUploadError, instance),
							rootNode.on(STR_DRAG_OVER, instance._ddEventHandler, instance),
							rootNode.on(STR_DRAG_LEAVE, instance._ddEventHandler, instance),
							rootNode.on(STR_DROP, instance._ddEventHandler, instance)
						];

						var inputFileNode = instance.one('input[type="file"]');

						if (inputFileNode) {
							instance._eventHandles.push(
								inputFileNode.on('change', A.bind(instance._onInputFileChanged, instance))
							);
						}
					},

					_ddEventHandler: function(event) {
						var instance = this;

						var dataTransfer = event._event.dataTransfer;

						if (dataTransfer && dataTransfer.types) {
							var dataTransferTypes = dataTransfer.types || [];

							if (AArray.indexOf(dataTransferTypes, 'Files') > -1 && AArray.indexOf(dataTransferTypes, 'text/html') === -1) {
								event.halt();

								var type = event.type;

								var eventDrop = type === STR_DROP;

								var rootNode = instance.rootNode;

								if (type === STR_DRAG_OVER) {
									rootNode.addClass(CSS_DROP_ACTIVE);
								}
								else if (type === STR_DRAG_LEAVE || eventDrop) {
									rootNode.removeClass(CSS_DROP_ACTIVE);

									if (eventDrop) {
										instance._previewFile(dataTransfer.files[0]);
									}
								}
							}
						}
					},

					_onInputFileChanged: function(event) {
						var instance = this;

						instance._previewFile(event.currentTarget.getDOMNode().files[0]);
					},

					_onItemSelected: function(itemViewer) {
						var instance = this;

						var link = itemViewer.get(STR_LINKS).item(itemViewer.get('currentIndex'));

						instance.fire(
							STR_SELECTED_ITEM,
							{
								data: {
									returnType: link.getData('returntype'),
									value: link.getData('value')
								}
							}
						);
					},

					_onItemUploadCancel: function(event) {
						var instance = this;

						var uploadItemViewer = instance._uploadItemViewer;

						if (uploadItemViewer) {
							uploadItemViewer.hide();
						}
					},

					_onItemUploadComplete: function(itemData) {
						var instance = this;

						var uploadItemViewer = instance._uploadItemViewer;

						if (uploadItemViewer) {
							uploadItemViewer.updateCurrentImage(itemData);
						}

						instance._onItemSelected(uploadItemViewer);
					},

					_onItemUploadError: function() {
						var instance = this;

						var uploadItemViewer = instance._uploadItemViewer;

						if (uploadItemViewer) {
							uploadItemViewer.hide();
						}

						var message = Liferay.Language.get('an-unexpected-error-occurred-while-uploading-your-file');

						new Liferay.Notice(
							{
								closeText: false,
								content: message + '<button class="close" type="button">&times;</button>',
								noticeClass: 'hide',
								toggleText: false,
								type: 'warning',
								useAnimation: false
							}
						).show();
					},

					_previewFile: function(file) {
						var instance = this;

						if (A.config.win.FileReader) {
							var reader = new FileReader();

							reader.addEventListener(
								'loadend',
								function(event) {
									instance._showFile(file, event.target.result);
								}
							);

							reader.readAsDataURL(file);
						}
					},

					_renderUI: function() {
						var instance = this;

						var rootNode = instance.rootNode;

						instance._itemViewer.render(rootNode);
						instance._uploadItemViewer.render(rootNode);
					},

					_showFile: function(file, preview) {
						var instance = this;

						var dropArea = instance._dropArea;

						var returnType = dropArea.getData('returntype');

						var value = preview;

						if (returnType === STR_UPLOADABLE_FILE_RETURN_TYPE) {
							value = {
								base64: preview,
								file: file,
								id: A.guid(),
								uploadURL: dropArea.getData('uploadurl')
							};
						}

						var linkNode = A.Node.create(
							Lang.sub(
								UPLOAD_ITEM_LINK_TPL,
								{
									preview: preview,
									returnType: returnType,
									title: file.name
								}
							)
						);

						linkNode.setData('value', value);

						instance._uploadItemViewer.set(STR_LINKS, new A.NodeList(linkNode));
						instance._uploadItemViewer.show();

						instance._itemSelectorUploader.startUpload(file, dropArea.getData('uploadurl'));
					}
				}
			}
		);

		Liferay.ItemSelectorBrowser = ItemSelectorBrowser;
	},
	'',
	{
		requires: ['liferay-item-selector-uploader', 'liferay-item-viewer', 'liferay-notice', 'liferay-portlet-base']
	}
);