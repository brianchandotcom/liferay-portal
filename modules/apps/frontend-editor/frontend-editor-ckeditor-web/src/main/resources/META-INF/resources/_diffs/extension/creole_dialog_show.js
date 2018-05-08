ckEditor.on(
	'dialogShow',
	function(event) {
		var A = AUI();

		var MODIFIED = 'modified';

		var SELECTOR_HBOX_FIRST = '.cke_dialog_ui_hbox_first';

		var dialog = event.data.definition.dialog;

		if (dialog.getName() == 'image') {
			var lockButton = A.one('.cke_btn_locked');

			if (lockButton) {
				var imageProperties = lockButton.ancestor(SELECTOR_HBOX_FIRST);

				if (imageProperties) {
					imageProperties.hide();
				}
			}

			var imagePreviewBox = A.one('.ImagePreviewBox');

			if (imagePreviewBox) {
				imagePreviewBox.setStyle('width', 410);
			}
		}
		else if (dialog.getName() == 'cellProperties') {
			var containerNode = A.one('#' + dialog.getElement('cellType').$.id);

			if (!containerNode.getData(MODIFIED)) {
				containerNode.one(SELECTOR_HBOX_FIRST).hide();

				containerNode.one('.cke_dialog_ui_hbox_child').hide();

				var cellTypeWrapper = containerNode.one('.cke_dialog_ui_hbox_last');

				cellTypeWrapper.replaceClass('cke_dialog_ui_hbox_last', 'cke_dialog_ui_hbox_first');

				cellTypeWrapper.setStyle('width', '100%');

				cellTypeWrapper.all('tr').each(
					function(item, index, collection) {
						if (index > 0) {
							item.hide();
						}
					}
				);

				containerNode.setData(MODIFIED, true);
			}
		}
	}
);