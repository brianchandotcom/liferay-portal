(function() {
	var saveCommand = {
		canUndo: false,
		exec: function(editor) {
			editor.fire('saveContent');
		}
	};

	var pluginName = 'ajaxsave';

	CKEDITOR.plugins.add(
		pluginName,
		{
			init: function(editor) {
				editor.addCommand(pluginName, saveCommand);

				if (editor.ui.addButton) {
					editor.ui.addButton(
						'AjaxSave',
						{
							command: pluginName,
							icon: Liferay.ThemeDisplay.getPathJavaScript() + '/editor/ckeditor/plugins/ajaxsave/assets/save.png',
							label: editor.lang.save.toolbar
						}
					);
				}
			}
		}
	);
})();