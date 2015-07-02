CKEDITOR.dialog.add(
	'link',
	function(editor) {
		var LANG_COMMON = editor.lang.common;

		var LANG_LINK = editor.lang.link;

		var PLUGIN = CKEDITOR.plugins.link;

		var handleAddress = function(val) {
			var address = val.toString().trim();

			address = address.replace(/[\u200B-\u200D\uFEFF]/g, '');

			var prefix = '';

			if (address.indexOf('/') !== 0 && address.indexOf('://') === -1) {
				prefix = 'http://';
			}

			return prefix + address;
		};

		var parseLink = function(editor, element) {
			var instance = this;

			var data = {
				address: ''
			};

			if (element) {
				var href = element.getAttribute('href');

				if (editor.config.decodeLinks) {
					data.address = decodeURIComponent(href);
				}
				else {
					data.address = href;
				}
			}
			else {
				var selection = editor.getSelection();

				data.address = selection.getSelectedText();
			}

			data.address = handleAddress(data.address);

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
									commit: function(data) {
										var instance = this;

										if (!data) {
											data = {};
										}

										var val = instance.getValue();

										var address = val;

										address = handleAddress(address);

										data.address = address;
										data.text = val;
									},
									id: 'linkAddress',
									label: LANG_COMMON.url,
									required: true,
									setup: function(data) {
										var instance = this;

										if (data) {
											instance.setValue(data.address);
										}

										var linkType = instance.getDialog().getContentElement('info', 'linkType');

										if (linkType && linkType.getValue()) {
											instance.select();
										}
									},
									type: 'text',
									validate: function() {
										var instance = this;

										var func = CKEDITOR.dialog.validate.notEmpty(LANG_LINK.noUrl);

										return func.apply(instance);
									}
								}
							],
							id: 'linkOptions',
							padding: 1,
							type: 'vbox'
						}
					],
					id: 'info',
					label: LANG_LINK.info,
					title: LANG_LINK.info
				}
			],
			minHeight: 100,
			minWidth: 250,
			title: LANG_LINK.title,

			onFocus: function() {
				var instance = this;

				var urlField = instance.getContentElement('info', 'linkAddress');

				urlField.select();
			},

			onLoad: function() {
			},

			onOk: function() {
				var instance = this;

				var attributes = {};
				var data = {};

				var editor = instance.getParentEditor();

				instance.commitContent(data);

				attributes['data-cke-saved-href'] = data.address;

				attributes.href = data.address;

				if (!instance._.selectedElement) {
					var selection = editor.getSelection();
					var ranges = selection.getRanges(true);

					if (ranges.length == 1 && ranges[0].collapsed) {
						var text = new CKEDITOR.dom.text(data.text, editor.document);

						ranges[0].insertNode(text);
						ranges[0].selectNodeContents(text);

						selection.selectRanges(ranges);
					}

					var style = new CKEDITOR.style(
						{
							attributes: attributes,
							element: 'a'
						}
					);

					style.type = CKEDITOR.STYLE_INLINE;

					editor.applyStyle(style);
				}
				else {
					var selectedElement = instance._.selectedElement;

					var currentText = selectedElement.getText(data.text);

					selectedElement.setAttributes(attributes);

					if (CKEDITOR.env.ie) {
						selectedElement.setText(currentText);
					}
				}
			},

			onShow: function() {
				var instance = this;

				instance.fakeObj = false;

				var editor = instance.getParentEditor();
				var selection = editor.getSelection();
				var element = PLUGIN.getSelectedLink(editor) || null;

				if (element) {
					selection.selectElement(element);
				}

				instance.setupContent(parseLink.apply(instance, [editor, element]));
			}
		};
	}
);