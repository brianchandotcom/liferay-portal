CKEDITOR.plugins.add(
	'wikilink',
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

			editor.addCommand('link', new CKEDITOR.dialogCommand('link'));
			editor.addCommand('unlink', new CKEDITOR.unlinkCommand());

			editor.ui.addButton(
				'Link',
				{
					command: 'link',
					label: editor.lang.link.toolbar
				}
			);

			editor.ui.addButton(
				'Unlink',
				{
					command: 'unlink',
					label: editor.lang.unlink
				}
			);

			CKEDITOR.dialog.add('link', instance.path + 'dialogs/link.js');

			 editor.on(
				'selectionChange',
				function(event) {

					// document.queryCommandEnabled does not work for this in Firefox.
					// Use element paths to detect the state.

					var command = editor.getCommand('unlink');

					var commandState = CKEDITOR.TRISTATE_DISABLED;

					var lastElement = event.data.path.lastElement;

					if (lastElement) {
						var element = lastElement.getAscendant('a', true);

						if (element && element.getName() == 'a' && element.getAttribute('href')) {
							commandState = CKEDITOR.TRISTATE_OFF;
						}
					}

					command.setState(commandState);
				}
			);

			editor.on(
				'doubleclick',
				function(event) {
					var element = CKEDITOR.plugins.link.getSelectedLink(editor) || event.data.element;

					if (!element.isReadOnly() && element.is('a')) {
						event.data.dialog = 'link';
					}
				}
			);

			if (editor.addMenuItems) {
				editor.addMenuItems(
					{
						link: {
							command: 'link',
							group: 'link',
							label: editor.lang.link.menu,
							order: 1
						},
						unlink: {
							command: 'unlink',
							group: 'link',
							label: editor.lang.unlink,
							order: 5
						}
					}
				);
			}

			if (editor.contextMenu) {
				editor.contextMenu.addListener(
					function(element, selection) {
						var selectionObj = null;

						if (element && !element.isReadOnly()) {
							element = CKEDITOR.plugins.link.getSelectedLink(editor);

							if (element) {
								selectionObj = {
									link: CKEDITOR.TRISTATE_OFF,
									unlink: CKEDITOR.TRISTATE_OFF
								};
							}
						}

						return selectionObj;
					}
				);
			}
		}
	}
);

CKEDITOR.plugins.link = {
	getSelectedLink: function(editor) {
		var selectedLink = null;

		try {
			var selection = editor.getSelection();

			if (selection.getType() == CKEDITOR.SELECTION_ELEMENT) {
				var selectedElement = selection.getSelectedElement();

				if (selectedElement.is('a')) {
					selectedLink = selectedElement;
				}
			}
			else {
				var range = selection.getRanges(true)[ 0 ];

				range.shrink(CKEDITOR.SHRINK_TEXT);

				var root = range.getCommonAncestor();

				selectedLink = root.getAscendant('a', true);
			}
		}
		catch(e) {
		}

		return selectedLink;
	}
};

CKEDITOR.unlinkCommand = function() {};

CKEDITOR.unlinkCommand.prototype = {
	startDisabled: true,

	exec: function(editor) {
		var selection = editor.getSelection();
		var bookmarks = selection.createBookmarks();
		var ranges = selection.getRanges();
		var length = ranges.length;

		for (var i = 0 ; i < length; i++) {
			var rangeRoot = ranges[i].getCommonAncestor(true);
			var element = rangeRoot.getAscendant('a', true);

			if (!element) {
				continue;
			}

			ranges[i].selectNodeContents(element);
		}

		selection.selectRanges(ranges);
		editor.document.$.execCommand('unlink', false, null);
		selection.selectBookmarks(bookmarks);
	}
};