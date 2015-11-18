(function() {
	var pluginName = 'ajaxsave';

	CKEDITOR.plugins.add(
		pluginName,
		{
			init: function(editor) {
				editor.addCommand(
					pluginName,
					{
						canUndo: false,
						exec: function(editor) {
							editor.fire('saveContent');
						}
					}
				);

				if (editor.ui.addButton) {
					editor.ui.addButton(
						'AjaxSave',
						{
							command: pluginName,
							icon: themeDisplay.getPathJavaScript() + '/editor/ckeditor_latest/plugins/ajaxsave/assets/save.png',
							label: editor.lang.save.toolbar
						}
					);
				}
			}
		}
	);
})();