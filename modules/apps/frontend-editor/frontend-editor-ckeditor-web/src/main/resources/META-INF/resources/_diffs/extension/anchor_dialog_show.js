ckEditor.on(
	'dialogShow',
	function(event) {
		var dialog = event.data.definition.dialog;

		if (dialog.getName() === 'anchor') {
			var originalFn = dialog.getValueOf.bind(dialog);

			dialog.getValueOf = function(pageId, elementId) {
				var value = originalFn(pageId, elementId);

				if (pageId === 'info' && elementId === 'txtName') {
					value = value.replace(/ /g, '_');
				}

				return value;
			};
		}
	}
);