AUI.add(
	'liferay-item-selector-browser',
	function(A) {
		var AArray = A.Array;
		var Lang = A.Lang;

		var CSS_DROP_ACTIVE = 'drop-active';

		var UPLOAD_ITEM_LINK_TPL = '<a href="{preview}" title="{title}"></a>';

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
								links: instance.all('a.item-preview')
							}
						);

						instance._uploadItemViewer = new A.LiferayItemViewer(
							{
								btnCloseCaption: instance.get('closeCaption'),
								links: '',
								renderControls: false
							}
						);

						instance._bindUI();
						instance._renderUI();
					},

					destructor: function() {
						var instance = this;

						instance._itemViewer.destroy();
						instance._uploadItemViewer.destroy();

						(new A.EventHandle(instance._eventHandles)).detach();
					},

					_bindUI: function() {
						var instance = this;

						var inputFileNode = instance.one('input[type="file"]');

						var rootNode = instance.rootNode;

						instance._eventHandles = [
							rootNode.on('dragover', instance._ddEventHandler, instance),
							rootNode.on('dragleave', instance._ddEventHandler, instance),
							rootNode.on('drop', instance._ddEventHandler, instance)
						];

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

								var eventDrop = type === 'drop';

								var rootNode = instance.rootNode;

								if (type === 'dragover') {
									rootNode.addClass(CSS_DROP_ACTIVE);
								}
								else if (type === 'dragleave' || eventDrop) {
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

						var linkNode = A.Node.create(
							Lang.sub(
								UPLOAD_ITEM_LINK_TPL,
								{
									preview: preview,
									title: file.name
								}
							)
						);

						instance._uploadItemViewer.set('links', new A.NodeList(linkNode));
						instance._uploadItemViewer.show();
					}
				}
			}
		);

		Liferay.ItemSelectorBrowser = ItemSelectorBrowser;
	},
	'',
	{
		requires: ['liferay-item-viewer', 'liferay-portlet-base']
	}
);