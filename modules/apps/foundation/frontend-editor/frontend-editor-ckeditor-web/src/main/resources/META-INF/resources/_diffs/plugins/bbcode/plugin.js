(function() {
	var commandObject = {
		exec: function(editor) {
			editor.focus();
			editor.fire('saveSnapshot');

			var elementPath = new CKEDITOR.dom.elementPath(editor.getSelection().getStartElement());

			var elementAction = 'apply';

			var preElement = new CKEDITOR.style(
				{
					element: 'pre'
				}
			);

			preElement._.enterMode = editor.config.enterMode;

			if (preElement.checkActive(elementPath)) {
				elementAction = 'remove';
			}

			preElement[elementAction](editor.document);

			var preBlock = editor.document.findOne('pre');

			if (preBlock && preBlock.getChildCount() === 0) {
				preBlock.appendBogus();
			}

			setTimeout(
				function() {
					editor.fire('saveSnapshot');
				},
				0
			);
		},

		refresh: function(editor, path) {
			var firstBlock = path.block || path.blockLimit;

			var buttonState = CKEDITOR.TRISTATE_OFF;

			var element = editor.elementPath(firstBlock);

			if (element.contains('pre', 1)) {
				buttonState = CKEDITOR.TRISTATE_ON;
			}

			this.setState(buttonState);
		},

		context: 'pre'
	};

	CKEDITOR.plugins.add(
	'bbcode',
		{
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

				editor.addCommand('bbcode', commandObject);

				editor.ui.addButton(
					'Code',
					{
						command: 'bbcode',
						icon: editor.config.imagesPath + 'code.png',
						label: editor.config.lang.code
					}
				);
			}
		}
	);
})();