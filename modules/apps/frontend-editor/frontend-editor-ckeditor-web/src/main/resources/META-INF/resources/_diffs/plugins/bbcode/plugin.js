/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

(function () {
	const commandObject = {
		context: 'pre',

		exec(editor) {
			editor.focus();
			editor.fire('saveSnapshot');

			const elementPath = new CKEDITOR.dom.elementPath(
				editor.getSelection().getStartElement()
			);

			let elementAction = 'apply';

			const preElement = new CKEDITOR.style({
				element: 'pre',
			});

			preElement._.enterMode = editor.config.enterMode;

			if (preElement.checkActive(elementPath)) {
				elementAction = 'remove';
			}

			preElement[elementAction](editor.document);

			const preBlock = editor.document.findOne('pre');

			if (preBlock && preBlock.getChildCount() === 0) {
				preBlock.appendBogus();
			}

			setTimeout(() => {
				editor.fire('saveSnapshot');
			}, 0);
		},

		refresh(editor, path) {
			const firstBlock = path.block || path.blockLimit;

			let buttonState = CKEDITOR.TRISTATE_OFF;

			const element = editor.elementPath(firstBlock);

			if (element.contains('pre', 1)) {
				buttonState = CKEDITOR.TRISTATE_ON;
			}

			this.setState(buttonState);
		},
	};

	CKEDITOR.plugins.add('bbcode', {
		init(editor) {
			const instance = this;

			const path = instance.path;

			const dependencies = [
				CKEDITOR.getUrl(path + 'bbcode_data_processor.js'),
				CKEDITOR.getUrl(path + 'bbcode_parser.js'),
			];

			CKEDITOR.scriptLoader.load(dependencies, () => {
				const bbcodeDataProcessor = CKEDITOR.plugins.get(
					'bbcode_data_processor'
				);

				bbcodeDataProcessor.init(editor);
			});

			editor.addCommand('bbcode', commandObject);
		},
	});
})();
