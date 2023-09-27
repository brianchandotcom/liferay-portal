/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

CKEDITOR.dialog.add('link', (editor) => {
	const LANG_COMMON = editor.lang.common;

	const LANG_LINK = editor.lang.link;

	const PLUGIN = CKEDITOR.plugins.link;

	const parseLink = function (editor, element) {
		const instance = this;

		const data = {
			address: '',
		};

		if (element) {
			const href = element.getAttribute('href');

			if (editor.config.decodeLinks) {
				data.address = decodeURIComponent(href);
			}
			else {
				data.address = href;
			}
		}
		else {
			const selection = editor.getSelection();

			data.address = selection.getSelectedText();
		}

		instance._.selectedElement = element;

		return data;
	};

	return {
		contents: [
			{
				elements: [
					{
						children: [
							{
								commit(data) {
									const instance = this;

									if (!data) {
										data = {};
									}

									const val = instance.getValue();

									data.address = val;
									data.text = val;
								},
								id: 'linkAddress',
								label: LANG_COMMON.url,
								required: true,
								setup(data) {
									const instance = this;

									if (data) {
										instance.setValue(data.address);
									}

									const linkType = instance
										.getDialog()
										.getContentElement('info', 'linkType');

									if (linkType && linkType.getValue()) {
										instance.select();
									}
								},
								type: 'text',
								validate() {
									const instance = this;

									const func = CKEDITOR.dialog.validate.notEmpty(
										LANG_LINK.noUrl
									);

									return func.apply(instance);
								},
							},
							{
								id: 'linkBrowse',
								label: LANG_COMMON.browseServer,
								onClick(event) {
									const dialog = event.data.dialog;

									const editor = dialog.getParentEditor();

									const urlField = dialog.getContentElement(
										'info',
										'linkAddress'
									);

									editor.execCommand(
										'linkselector',
										(newVal, selectedItem) => {
											if (
												selectedItem &&
												selectedItem.title
											) {
												urlField.setValue(
													selectedItem.title
												);
											}
											else {
												urlField.setValue(
													location.origin + newVal
												);
											}
										}
									);
								},
								required: true,
								type: 'button',
							},
						],
						id: 'linkOptions',
						padding: 1,
						type: 'vbox',
					},
				],
				id: 'info',
				label: LANG_LINK.info,
				title: LANG_LINK.info,
			},
		],

		minHeight: 100,
		minWidth: 250,

		onFocus() {
			const instance = this;

			const urlField = instance.getContentElement('info', 'linkAddress');

			urlField.select();
		},

		onLoad() {},

		onOk() {
			const instance = this;

			const attributes = {};
			const data = {};

			const editor = instance.getParentEditor();

			instance.commitContent(data);

			attributes['data-cke-saved-href'] = data.address;

			attributes.href = data.address;

			if (!instance._.selectedElement) {
				const selection = editor.getSelection();

				const ranges = selection.getRanges(true);

				if (ranges.length === 1 && ranges[0].collapsed) {
					const text = new CKEDITOR.dom.text(
						data.text,
						editor.document
					);

					ranges[0].insertNode(text);
					ranges[0].selectNodeContents(text);

					selection.selectRanges(ranges);
				}

				const style = new CKEDITOR.style({
					attributes,
					element: 'a',
				});

				style.type = CKEDITOR.STYLE_INLINE;

				editor.applyStyle(style);
			}
			else {
				const selectedElement = instance._.selectedElement;

				const currentText = selectedElement.getText(data.text);

				selectedElement.setAttributes(attributes);

				if (CKEDITOR.env.ie) {
					selectedElement.setText(currentText);
				}
			}
		},

		onShow() {
			const instance = this;

			instance.fakeObj = false;

			const editor = instance.getParentEditor();

			const element = PLUGIN.getSelectedLink(editor) || null;

			if (element) {
				const selection = editor.getSelection();

				selection.selectElement(element);
			}

			instance.setupContent(parseLink.apply(instance, [editor, element]));
		},

		title: LANG_LINK.title,
	};
});
