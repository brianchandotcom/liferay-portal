AUI.add(
	'liferay-bbcode-editor',
	function(A) {
		var bbCode = function(options) {
			var instance = this;
			options = options || {};

			instance._textarea = A.one(options.textarea);
			instance._location = A.one(options.location);

			instance._createEmoticons(
				function() {
					instance._createToolbar();

					if (options.onLoad) {
						options.onLoad();
					}
				}
			);
		};

		bbCode.prototype = {
			getHTML: function(content) {
				var instance = this;

				return instance._textarea.val();
			},

			insertTag: function(tag, param, content) {
				var instance = this;

				var begTag;

				if (param) {
					begTag = '[' + tag + '=' + param + ']';
				}
				else {
					begTag = '[' + tag + ']';
				}

				var endTag = '[/' + tag + ']';

				var textarea = instance._textarea;
				var field = textarea.getDOM();
				var value = textarea.val();

				if (Liferay.Browser.isIe()) {
					instance._setSelectionRange();

					if (content != null) {
						instance._selectionRange.text = begTag + content + endTag;
					}
					else {
						instance._selectionRange.text = begTag + instance._selectionRange.text + endTag;
					}

					instance._selectionRange.moveEnd('character', -endTag.length);
					instance._selectionRange.select();

					instance._selectionRange = null;
				}
				else if (field.selectionStart || field.selectionStart == 0) {
					var startPos = field.selectionStart;
					var endPos = field.selectionEnd;

					var preSel = value.substring(0, startPos);
					var sel = value.substring(startPos, endPos);
					var postSel = value.substring(endPos, field.value.length);

					var caretPos = startPos + begTag.length;

					if (content != null) {
						field.value = preSel + begTag + content + endTag + postSel;
					}
					else {
						field.value = preSel + begTag + sel + endTag + postSel;
						field.setSelectionRange(caretPos, caretPos);
					}
				}
				else {
					field.value += begTag + content + endTag;
				}

				textarea.focus();
			},

			setHTML: function(content) {
				var instance = this;

				instance._textarea.val(content);
			},

			_createEmoticons: function(callback) {
				var instance = this;

				A.io.request(
					themeDisplay.getPathMain() + '/portal/emoticons',
					{
						on: {
							success: function(event, id, obj) {
								var responseData = this.get('responseData');
								var emoticonsContainer = A.Node.create('<div class="lfr-emoticon-container"></div>');

								instance._emoticons = emoticonsContainer.html(responseData);

								var emoticons = instance._emoticons.all('.emoticon');

								if (emoticons) {
									emoticons.on(
										'click',
										function(event) {
											var emoticonCode = event.currentTarget.getAttribute('emoticonCode');

											if (emoticonCode) {
												instance._insertEmoticon(emoticonCode);
											}
										}
									);
								}

								if (callback) {
									callback.apply(instance, []);
								}
							}
						}
					}
				);
			},

			_createToolbar: function() {
				var instance = this;

				var html = '';

				instance._buttons = {
					fontType: {
						onChange: function(event) {
							var target = event.target;
							var value = target.val();

							if (value != Liferay.Language.get('font')) {
								instance.insertTag('font', value);

								target.set('selectedIndex', 0);
							}
						},
						options: [Liferay.Language.get('font'), 'Arial', 'Comic Sans', 'Courier New', 'Tahoma', 'Times New Roman', 'Verdana', 'Wingdings']
					},

					fontSize: {
						groupEnd: true,
						onChange: function(event) {
							var target = event.target;
							var value = target.val();

							if (value != Liferay.Language.get('size')) {
								instance.insertTag('size', value);

								target.set('selectedIndex', 0);
							}
						},
						options: [Liferay.Language.get('size'), 1, 2, 3, 4, 5, 6, 7]
					},

					b: {
						image: 'message_boards/bold.png',
						onClick: function(event) {
							instance.insertTag('b');
						},
						text: Liferay.Language.get('bold')
					},

					i: {
						image: 'message_boards/italic.png',
						onClick: function(event) {
							instance.insertTag('i');
						},
						text: Liferay.Language.get('italic')
					},

					u: {
						image: 'message_boards/underline.png',
						onClick: function(event) {
							instance.insertTag('u');
						},
						text: Liferay.Language.get('underline')
					},

					s: {
						image: 'message_boards/strike.png',
						onClick: function(event) {
							instance.insertTag('s');
						},
						text: Liferay.Language.get('strikethrough')
					},

					fontColor: {
						className: 'use-colorpicker',
						groupEnd: true,
						image: 'message_boards/color.png',
						text: Liferay.Language.get('font-color')
					},

					url: {
						image: 'message_boards/hyperlink.png',
						onClick: function(event) {
							instance._insertURL();
						},
						text: Liferay.Language.get('url')
					},

					email: {
						image: 'message_boards/email.png',
						onClick: function(event) {
							instance._insertEmail();
						},
						text: Liferay.Language.get('email-address')
					},

					image: {
						image: 'message_boards/image.png',
						onClick: function(event) {
							instance._insertImage();
						},
						text: Liferay.Language.get('image')
					},

					ol: {
						image: 'message_boards/ordered_list.png',
						onClick: function(event) {
							instance._insertList('1');
						},
						text: Liferay.Language.get('ordered-list')
					},

					ul: {
						image: 'message_boards/unordered_list.png',
						onClick: function(event) {
							instance._insertList('');
						},
						text: Liferay.Language.get('unordered-list')
					},

					left: {
						image: 'message_boards/justify_left.png',
						onClick: function(event) {
							instance.insertTag('left');
						},
						text: Liferay.Language.get('left')
					},

					center: {
						image: 'message_boards/justify_center.png',
						onClick: function(event) {
							instance.insertTag('center');
						},
						text: Liferay.Language.get('center')
					},

					right: {
						image: 'message_boards/justify_right.png',
						onClick: function(event) {
							instance.insertTag('right');
						},
						text: Liferay.Language.get('right')
					},

					indent: {
						text: Liferay.Language.get('indent'),
						image: 'message_boards/indent.png',
						onClick: function(event) {
							instance.insertTag('indent');
						}
					},

					quote: {
						image: 'message_boards/quote.png',
						onClick: function(event) {
							instance.insertTag('quote');
						},
						text: Liferay.Language.get('quote')
					},

					code: {
						image: 'message_boards/code.png',
						onClick: function(event) {
							instance.insertTag('code');
						},
						text: Liferay.Language.get('code')
					},

					emoticons: {
						image: 'emoticons/smile.gif',
						text: Liferay.Language.get('emoticons')
					}
				};

				A.each(
					instance._buttons,
					function(n, i) {
						var buttonClass = ' ' + (n.className || '');
						var buttonText = n.text || '';

						if (i != 'insert' && !n.options) {
							var imagePath = themeDisplay.getPathThemeImages() + '/' + n.image;

							html +=
								'<a buttonId="' + i + '" class="lfr-button ' + buttonClass + '" href="javascript:;" title="' + buttonText + '">' +
								'<img alt="' + buttonText + '" buttonId="' + i + '" src="' + imagePath + '" >' +
								'</a>';
						}
						else if (n.options && n.options.length) {
							html += '<select class="' + buttonClass + '" selectId="' + i + '" title="' + buttonText + '">';

							A.each(
								n.options,
								function(v, i) {
									html += '<option value="' + v + '">' + v + '</option>';
								}
							);

							html += '</select>';
						}

						if (n.groupEnd) {
							html += '<span class="lfr-separator"></span>';
						}
					}
				);

				if (!instance._location) {
					instance._location = A.Node.create('<div class="lfr-toolbar">' + html + '</div>');
					instance._textarea.placeBefore(instance._location);
				}
				else {
					instance._location.html(html);
				}

				var emoticonButton = instance._location.all('.lfr-button[buttonId=emoticons]');
				var hoveringOver = false;
				var offsetHeight = 0;
				var offsetWidth = 0;
				var boxWidth = 0;

				var emoticonOverlay = new A.OverlayContext(
					{
						align: {
							 points: ['tr', 'br']
						},
						contentBox: instance._emoticons,
						hideDelay: 500,
						trigger: emoticonButton.item(0)
					}
				);

				emoticonOverlay.render();

				instance._location.on(
					'click',
					function(event) {
						instance._setSelectionRange();

						var target = event.target;
						var buttonId = event.target.getAttribute('buttonId');

						if (buttonId && instance._buttons[buttonId].onClick) {
							instance._buttons[buttonId].onClick.apply(target, [event]);
						}
					}
				);

				var selects = instance._location.all('select');

				if (selects) {
					selects.on(
						'change',
						function(event) {
							var selectId = event.target.getAttribute('selectId');

							if (selectId && instance._buttons[selectId].onChange) {
								instance._buttons[selectId].onChange.apply(this, [event]);
							}
						}
					);
				}

				instance._fontColorInput = A.Node.create('<input type="hidden" val="" />');

				var colorpicker = instance._location.one('.use-colorpicker');

				var colorpicker = instance._location.one('.use-colorpicker');

				if (colorpicker) {
					instance._fontColorInput.placeBefore(colorpicker);

					new A.ColorPicker(
						{
							after: {
								colorChange: function() {
									instance._fontColorInput.val('#' + this.get('hex'));
								},
								visibleChange: function(event) {
									if (!event.newVal) {
										instance._insertColor();
									}
								}
							},
							trigger: colorpicker,
							zIndex: 9999
						}
					).render();
				}
			},

			_insertColor: function() {
				var instance = this;

				var color = instance._fontColorInput.val();
				instance.insertTag('color', color);
			},

			_insertEmail: function() {
				var instance = this;

				var addy = prompt(Liferay.Language.get('enter-an-email-address'), '');

				if (addy) {
					var name = prompt(Liferay.Language.get('enter-a-name-for-the-email-address'), '');

					instance._resetSelection();

					if (!name) {
						name = addy;
						addy = null;
					}

					instance.insertTag('email', addy, name);
				}
			},

			_getSelection: function() {
				var instance = this;

				var textarea = instance._textarea;
				var field = textarea.getDOM();
				var value = textarea.val();
				var selection;

				if (Liferay.Browser.isIe()) {
					instance._setSelectionRange();

					selection = instance._selectionRange.text;
				}
				else if (field.selectionStart || field.selectionStart == 0) {
					selection = value.substring(field.selectionStart, field.selectionEnd);
				}

				return selection;
			},

			_insertEmoticon: function(emoticon) {
				var instance = this;

				var textarea = instance._textarea;
				var field = textarea.getDOM();

				textarea.focus();

				if (Liferay.Browser.isIe()) {
					field.focus();

					var sel = document.selection.createRange();

					sel.text = emoticon;
				}
				else if (field.selectionStart || field.selectionStart == "0") {
					var startPos = field.selectionStart;
					var endPos = field.selectionEnd;

					var preSel = field.value.substring(0, startPos);
					var postSel = field.value.substring(endPos, field.value.length);

					field.value = preSel + emoticon + postSel;
				}
				else {
					field.value += emoticon;
				}
			},

			_insertImage: function() {
				var instance = this;

				var url = prompt(Liferay.Language.get('enter-an-address-for-the-image'), 'http://');

				if (url) {
					instance._resetSelection();
					instance.insertTag('img', null, url);
				}
			},

			_insertList: function(ordered) {
				var instance = this;

				var list = "\n";
				var entry;

				while (entry = prompt(Liferay.Language.get('enter-a-list-item-click-cancel-or-leave-blank-to-end-the-list'), '')) {
					if (!entry) {
						break;
					}

					list += '[*]' + entry + '\n';
				}

				if (list != '\n') {
					instance._resetSelection();
					instance.insertTag('list', ordered, list);
				}
			},

			_insertURL: function() {
				var instance = this;

				var url = prompt(Liferay.Language.get('enter-an-address'), 'http://');

				if (url != null) {
					var title;
					var selection = instance._getSelection();

					if (selection) {
						title = selection;
					}
					else {
						title = prompt(Liferay.Language.get('enter-a-title-for-the-address'), '');
					}

					if (title) {
						instance.insertTag('url', url, title);
					}
					else {
						instance.insertTag('url', url);
					}
				}
			},

			_resetSelection: function() {
				var instance = this;

				var textarea = instance._textarea;
				var field = textarea.getDOM();

				if (Liferay.Browser.isIe()) {
					field.focus();

					var selection = document.selection.createRange();

					selection.collapse(false);
					selection.select();
				}
				else if (field.setSelectionRange) {
					var selectionStart = field.selectionStart;

					field.setSelectionRange(selectionStart, selectionStart);
				}
			},

			_setSelectionRange: function() {
				var instance = this;

				if (Liferay.Browser.isIe() && (instance._selectionRange == null)) {
					instance._textarea.focus();

					instance._selectionRange = document.selection.createRange();
				}
			}
		};

		Liferay.namespace('Editor');

		Liferay.Editor.bbCode = bbCode;
	},
	'',
	{
		requires: ['aui-color-picker-deprecated', 'aui-io-request', 'aui-overlay-context-deprecated']
	}
);