(function() {
	var restoreCommand = {
		canUndo: false,
		exec: function(editor) {
			editor.fire('restoreContent');
		}
	};

	var pluginName = 'restore';

	CKEDITOR.plugins.add(
		pluginName,
		{
			init: function(editor) {
				editor.addCommand(pluginName, restoreCommand);

				if (editor.ui.addButton) {
					editor.ui.addButton(
						'Restore',
						{
							command: pluginName,
							icon: Liferay.ThemeDisplay.getPathJavaScript() + '/editor/ckeditor/plugins/restore/assets/restore.png',
							label: Liferay.Language.get('restore-the-original-content')
						}
					);
				}
			}
		}
	);
})();