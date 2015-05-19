(function() {
	var pluginName = 'imageselector';

	CKEDITOR.plugins.add(
		pluginName,
		{
			init: function(editor) {
				var instance = this;

				var imageSelectorUrl = editor.config.filebrowserImageBrowseUrl;

				editor.addCommand(
					pluginName,
					{
						canUndo: false,
						exec: function(editor) {
							AUI().use(
								'liferay-item-selector-dialog',
								function(A) {
									var eventName = editor.name + 'selectItem';

									var dialog = new A.LiferayItemSelectorDialog(
										{
											eventName: eventName,
											url: imageSelectorUrl
										}
									);

									dialog.on(
										'itemSelected',
										function(event) {
											var el = CKEDITOR.dom.element.createFromHtml('<img src="' + event.value + '">');

											editor.insertElement(el);
										}
									);
								}
							);
						}
					}
				);

				if (editor.ui.addButton) {
					editor.ui.addButton(
						'ImageSelector',
						{
							command: pluginName,
							icon: instance.path + 'assets/image.png',
							label: editor.lang.common.image
						}
					);

					editor.config.filebrowserImageBrowseUrl = '';

					editor.config.filebrowserImageBrowseLinkUrl = '';
				}
			}
		}
	);
})();