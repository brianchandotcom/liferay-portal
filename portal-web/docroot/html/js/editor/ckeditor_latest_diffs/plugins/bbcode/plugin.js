(function() {
	CKEDITOR.plugins.add(
	'bbcode',
		{
			beforeInit: function( editor ) {
				CKEDITOR.tools.extend(
					editor.config, {
						listIndentOnly: true
					},
					true
				);
			},

			init: function(editor) {
				var instance = this;

				var path = instance.path;

				var dependencies = [
					CKEDITOR.getUrl(path + 'bbcode_data_processor.js'),
					CKEDITOR.getUrl(path + 'bbcode_parser.js')
				];

				CKEDITOR.scriptLoader.load(
					dependencies,
					function() {
						var bbcodeDataProcessor = CKEDITOR.plugins.get('bbcode_data_processor');

						bbcodeDataProcessor.init(editor);
					}
				);

				var preElement = new CKEDITOR.style(
					{
						element: 'pre'
					}
				);

				preElement._.enterMode = editor.config.enterMode;

				editor.ui.addButton(
					'Code',
					{
						click : function() {
							editor.focus();
							editor.fire('saveSnapshot');

							var elementPath = new CKEDITOR.dom.elementPath(editor.getSelection().getStartElement());

							var elementAction = 'apply';

							if (preElement.checkActive(elementPath)) {
								elementAction = 'remove';
							}

							preElement[elementAction](editor.document);

							setTimeout(
								function() {
									editor.fire('saveSnapshot');
								},
								0
							);
						},
						icon: editor.config.imagesPath + 'code.png',
						label: Liferay.Language.get('code')
					}
				);
			}
		}
	);
})();
