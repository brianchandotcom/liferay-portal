(function() {
	var pluginName = 'restore';

	CKEDITOR.plugins.add(
		pluginName,
		{
			init: function(editor) {
				editor.addCommand(
					pluginName,
					{
						canUndo: false,
						exec: function(editor) {
							editor.fire('restoreContent');
						}
					}
				);

				if (editor.ui.addButton) {
					editor.ui.addButton(
						'Restore',
						{
							command: pluginName,
							icon: themeDisplay.getPathJavaScript() + '/editor/ckeditor_latest/plugins/restore/assets/restore.png',
							label: Liferay.Language.get('restore-the-original-content')
						}
					);
				}
			}
		}
	);
})();