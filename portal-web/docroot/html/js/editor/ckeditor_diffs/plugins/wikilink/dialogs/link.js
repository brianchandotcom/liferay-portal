CKEDITOR.dialog.add(
	'link',
	function(editor) {
		var LANG_COMMON = editor.lang.common;

		var LANG_LINK = editor.lang.link;

		var PLUGIN = CKEDITOR.plugins.link;

		var parseLink = function(editor, element) {
			var instance = this;

			var data = {
				address: ''
			};

			if (element) {
				var href = element.data('cke-saved-href') || element.getAttribute('href');

				if (editor.config.decodeLinks) {
					data.address = decodeURIComponent(href);
				}
				else {
					data.address = href;
				}
			}
			else {
				var selection = editor.getSelection();

				if (CKEDITOR.env.ie) {
					selection.unlock(true);

					data.address = selection.getNative().createRange().text;

					selection.lock();
				}
				else {
					data.address = selection.getNative().toString();
				}
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
									commit: function(data) {
										var instance = this;

										if (!data) {
											data = {};
										}

										data.address = instance.getValue();
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
							type:  'vbox'
						}
					],
					id: 'info',
					label: LANG_LINK.info,
					title: LANG_LINK.info
				}
			],
			minWidth: 250,
			minHeight: 100,
			title: LANG_LINK.title,

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
						var text = new CKEDITOR.dom.text(attributes['data-cke-saved-href'], editor.document);

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
					style.apply(editor.document);
				}
				else {
					instance._.selectedElement.setAttributes(attributes);
				}
			},

			onFocus: function() {
				var instance = this;

				var urlField = instance.getContentElement('info', 'linkAddress');

				urlField.select();
			},

			onLoad: function() {
			}
		};
	}
);