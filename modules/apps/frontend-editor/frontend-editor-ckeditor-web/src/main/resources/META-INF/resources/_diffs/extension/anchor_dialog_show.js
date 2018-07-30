ckEditor.on(
	'dialogShow',
	function(event) {
		var dialog = event.data.definition.dialog;

		if (dialog.getName() === 'anchor') {
			var originalGetValueOfFn = dialog.getValueOf.bind(dialog);

			dialog.getValueOf = function(a, b) {
				var originalValue = originalGetValueOfFn(a, b);

				var modifiedValue = originalValue.split(' ').join('_');

				return modifiedValue;
			};
		}
	}
);