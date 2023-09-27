/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

CKEDITOR.plugins.add('wikilink', {
	init(editor) {
		const instance = this;

		editor.addCommand('link', new CKEDITOR.dialogCommand('link'));
		editor.addCommand('unlink', new CKEDITOR.unlinkCommand());

		editor.ui.addButton('Link', {
			command: 'link',
			label: editor.lang.link.toolbar,
		});

		editor.ui.addButton('Unlink', {
			command: 'unlink',
			label: editor.lang.link.unlink,
		});

		CKEDITOR.dialog.add('link', instance.path + 'dialogs/link.js');

		editor.on('selectionChange', (event) => {

			// document.queryCommandEnabled does not work for this in Firefox.
			// Use element paths to detect the state.

			const command = editor.getCommand('unlink');

			let commandState = CKEDITOR.TRISTATE_DISABLED;

			const lastElement = event.data.path.lastElement;

			if (lastElement) {
				const element = lastElement.getAscendant('a', true);

				if (
					element &&
					element.getName() === 'a' &&
					element.getAttribute('href')
				) {
					commandState = CKEDITOR.TRISTATE_OFF;
				}
			}

			command.setState(commandState);
		});

		editor.on('doubleclick', (event) => {
			const element =
				CKEDITOR.plugins.link.getSelectedLink(editor) ||
				event.data.element;

			if (!element.isReadOnly() && element.is('a')) {
				event.data.dialog = 'link';
			}
		});

		if (editor.addMenuItems) {
			editor.addMenuItems({
				link: {
					command: 'link',
					group: 'link',
					label: editor.lang.link.menu,
					order: 1,
				},
				unlink: {
					command: 'unlink',
					group: 'link',
					label: editor.lang.link.unlink,
					order: 5,
				},
			});
		}

		if (editor.contextMenu) {
			editor.contextMenu.addListener((element) => {
				let selectionObj = null;

				if (element && !element.isReadOnly()) {
					element = CKEDITOR.plugins.link.getSelectedLink(editor);

					if (element) {
						selectionObj = {
							link: CKEDITOR.TRISTATE_OFF,
							unlink: CKEDITOR.TRISTATE_OFF,
						};
					}
				}

				return selectionObj;
			});
		}
	},
});

CKEDITOR.plugins.link = {
	getSelectedLink(editor) {
		let selectedLink = null;

		try {
			const selection = editor.getSelection();

			if (selection.getType() === CKEDITOR.SELECTION_ELEMENT) {
				const selectedElement = selection.getSelectedElement();

				if (selectedElement.is('a')) {
					selectedLink = selectedElement;
				}
			}
			else {
				const range = selection.getRanges(true)[0];

				range.shrink(CKEDITOR.SHRINK_TEXT);

				const root = range.getCommonAncestor();

				selectedLink = root.getAscendant('a', true);
			}
		}
		catch (error) {}

		return selectedLink;
	},
};

CKEDITOR.unlinkCommand = function () {};

CKEDITOR.unlinkCommand.prototype = {
	exec(editor) {
		const selection = editor.getSelection();

		const bookmarks = selection.createBookmarks();
		const ranges = selection.getRanges();

		const length = ranges.length;

		for (let i = 0; i < length; i++) {
			const rangeRoot = ranges[i].getCommonAncestor(true);

			const element = rangeRoot.getAscendant('a', true);

			if (!element) {
				continue;
			}

			ranges[i].selectNodeContents(element);
		}

		selection.selectRanges(ranges);
		editor.document.$.execCommand('unlink', false, null);
		selection.selectBookmarks(bookmarks);
	},

	startDisabled: true,
};
