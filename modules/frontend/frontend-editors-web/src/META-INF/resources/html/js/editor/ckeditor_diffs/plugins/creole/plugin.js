;(function() {
	CKEDITOR.plugins.add(
	'creole',
		{
			init: function(editor) {
				var instance = this;

				var path = instance.path;

				var dependencies = [
					CKEDITOR.getUrl(path + 'creole_data_processor.js'),
					CKEDITOR.getUrl(path + 'creole_parser.js')
				];

				CKEDITOR.scriptLoader.loadScripts(
					dependencies,
					function() {
						var creoleDataProcessor = CKEDITOR.plugins.get('creole_data_processor');

						creoleDataProcessor.init(editor);
					}
				);
			}
		}
	);
})();