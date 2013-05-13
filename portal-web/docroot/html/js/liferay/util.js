;(function(A, Liferay) {
	A.use('aui-base-lang');

	var Lang = A.Lang;

	var AArray = A.Array;
	var AObject = A.Object;
	var AString = A.Lang.String;
	var Browser = Liferay.Browser;

	var isArray = Lang.isArray;
	var arrayIndexOf = AArray.indexOf;

	var EVENT_CLICK = 'click';

	var htmlEscapedValues = [];
	var htmlUnescapedValues = [];

	var MAP_HTML_CHARS_ESCAPED = {
		'&': '&amp;',
		'<': '&lt;',
		'>': '&gt;',
		'"': '&#034;',
		'\'': '&#039;',
		'/': '&#047;',
		'`': '&#096;'
	};

	var MAP_HTML_CHARS_UNESCAPED = {};

	AObject.each(
		MAP_HTML_CHARS_ESCAPED,
		function(item, index) {
			MAP_HTML_CHARS_UNESCAPED[item] = index;

			htmlEscapedValues.push(item);
			htmlUnescapedValues.push(index);
		}
	);

	var REGEX_DASH = /-([a-z])/gi;

	var STR_LEFT_SQUARE_BRACKET = '[';

	var STR_RIGHT_SQUARE_BRACKET = ']';

	var REGEX_HTML_ESCAPE = new RegExp(STR_LEFT_SQUARE_BRACKET + htmlUnescapedValues.join('') + STR_RIGHT_SQUARE_BRACKET, 'g');

	var REGEX_HTML_UNESCAPE = new RegExp(htmlEscapedValues.join('|'), 'gi');

	var SRC_HIDE_LINK = {
		src: 'hideLink'
	};

	var Window = {
		_map: {}
	};

	var Util = {
		submitCountdown: 0,

		actsAsAspect: function(object) {
			object.yield = null;
			object.rv = {};

			object.before = function(method, f) {
				var original = eval('this.' + method);

				this[method] = function() {
					f.apply(this, arguments);

					return original.apply(this, arguments);
				};
			};

			object.after = function(method, f) {
				var original = eval('this.' + method);

				this[method] = function() {
					this.rv[method] = original.apply(this, arguments);

					return f.apply(this, arguments);
				};
			};

			object.around = function(method, f) {
				var original = eval('this.' + method);

				this[method] = function() {
					this.yield = original;

					return f.apply(this, arguments);
				};
			};
		},

		addInputFocus: function() {
			A.use(
				'aui-base',
				function(A) {
					var handleFocus = function(event) {
						var target = event.target;

						var tagName = target.get('tagName');

						if (tagName) {
							tagName = tagName.toLowerCase();
						}

						var nodeType = target.get('type');

						if (((tagName == 'input') && (/text|password/).test(nodeType)) ||
							(tagName == 'textarea')) {

							var action = 'addClass';

							if (/blur|focusout/.test(event.type)) {
								action = 'removeClass';
							}

							target[action]('focus');
						}
					};

					A.on('focus', handleFocus, document);
					A.on('blur', handleFocus, document);
				}
			);

			Util.addInputFocus = function(){};
		},

		addInputType: function(el) {
			Util.addInputType = Lang.emptyFn;

			if (Browser.isIe() && Browser.getMajorVersion() < 7) {
				Util.addInputType = function(el) {
					var item;

					if (el) {
						el = A.one(el);
					}
					else {
						el = A.one(document.body);
					}

					var defaultType = 'text';

					el.all('input').each(
						function(item, index, collection) {
							var type = item.get('type') || defaultType;

							item.addClass(type);
						}
					);
				};
			}

			return Util.addInputType(el);
		},

		addParams: function(params, url) {
			A.use('querystring-stringify-simple');

			if (Lang.isObject(params)) {
				params = A.QueryString.stringify(params);
			}
			else {
				params = Lang.trim(params);
			}

			if (params) {
				var loc = url || location.href;

				var anchorHash;
				var finalUrl;

				if (loc.indexOf('#') > -1) {
					var locationPieces = loc.split('#');
					loc = locationPieces[0];
					anchorHash = locationPieces[1];
				}

				if (loc.indexOf('?') == -1) {
					params = '?' + params;
				}
				else {
					params = '&' + params;
				}

				if (loc.indexOf(params) == -1) {
					finalUrl = loc + params;

					if (anchorHash) {
						finalUrl += '#' + anchorHash;
					}
					if (!url) {
						location.href = finalUrl;
					}

					return finalUrl;
				}
			}
		},

		camelize: function(value, separator) {
			var regex = REGEX_DASH;

			if (separator) {
				regex = new RegExp(separator + '([a-z])', 'gi');
			}

			value = value.replace(
				regex,
				function(match0, match1) {
					return match1.toUpperCase();
				}
			);

			return value;
		},

		checkTab: function(box) {
			if ((document.all) && (event.keyCode == 9)) {
				box.selection = document.selection.createRange();

				setTimeout(
					function() {
						Util.processTab(box.id);
					},
					0
				);
			}
		},

		clamp: function(value, min, max) {
			return Math.min(Math.max(value, min), max);
		},

		disableEsc: function() {
			if ((document.all) && (event.keyCode == 27)) {
				event.returnValue = false;
			}
		},

		disableFormButtons: function(inputs, form) {
			inputs.set('disabled', true);
			inputs.setStyle('opacity', 0.5);

			if (A.UA.gecko) {
				A.getWin().on(
					'unload',
					function(event) {
						inputs.set('disabled', false);
					}
				);
			}
		},

		enableFormButtons: function(inputs, form) {
			Util._submitLocked = null;

			document.body.style.cursor = 'auto';

			inputs.set('disabled', false);
			inputs.setStyle('opacity', 1);
		},

		endsWith: function(str, x) {
			return (str.lastIndexOf(x) === (str.length - x.length));
		},

		escapeCDATA: function(str) {
			return str.replace(
				/<!\[CDATA\[|\]\]>/gi,
				function(match) {
					var str = '';

					if (match == ']]>') {
						str = ']]&gt;';
					}
					else if (match == '<![CDATA[') {
						str = '&lt;![CDATA[';
					}

					return str;
				}
			);
		},

		escapeHTML: function(str, preventDoubleEscape, entities) {
			var result;

			var regex = REGEX_HTML_ESCAPE;

			var entitiesList = [];

			var entitiesValues;

			if (Lang.isObject(entities)) {
				entitiesValues = [];

				AObject.each(
					entities,
					function(item, index) {
						entitiesList.push(index);

						entitiesValues.push(item);
					}
				);

				regex = new RegExp(STR_LEFT_SQUARE_BRACKET + AString.escapeRegEx(entitiesList.join('')) + STR_RIGHT_SQUARE_BRACKET, 'g');
			}
			else {
				entities = MAP_HTML_CHARS_ESCAPED;

				entitiesValues = htmlEscapedValues;
			}

			return str.replace(regex, A.bind('_escapeHTML', Util, !!preventDoubleEscape, entities, entitiesValues));
		},

		getAttributes: function(el, attributeGetter) {
			var instance = this;

			var result = null;

			if (el) {
				if (Lang.isFunction(el.getDOM)) {
					el = el.getDOM();
				}

				result = {};

				var isGetterString = Lang.isString(attributeGetter);
				var isGetterFn = Lang.isFunction(attributeGetter);

				var attrs = el.attributes;
				var length = attrs.length;

				while (length--) {
					var attr = attrs[length];
					var name = attr.nodeName;
					var value = attr.nodeValue;

					if (isGetterString) {
						if (name.indexOf(attributeGetter) === 0) {
							name = name.substr(attributeGetter.length);
						}
						else {
							continue;
						}
					}
					else if (isGetterFn) {
						value = attributeGetter(value, name, attrs);

						if (value === false) {
							continue;
						}
					}

					result[name] = value;
				}
			}

			return result;
		},

		getColumnId: function(str) {
			var columnId = str.replace(/layout-column_/, '');

			return columnId;
		},

		getOpener: function() {
			var openingWindow = Window._opener;

			if (!openingWindow) {
				var topUtil = Liferay.Util.getTop().Liferay.Util;

				var windowName = Liferay.Util.getWindowName();

				var dialog = topUtil.Window._map[windowName];

				if (dialog) {
					openingWindow = topUtil.Window._map[windowName]._opener;

					Window._opener = openingWindow;
				}
			}

			return openingWindow || window.opener || window.parent;
		},

		getPortletId: function(portletId) {
			portletId = portletId.replace(/^p_p_id_/i, '');
			portletId = portletId.replace(/_$/, '');

			return portletId;
		},

		getPortletNamespace: function(portletId) {
			return '_' + portletId + '_';
		},

		getTop: function() {
			var topWindow = Util._topWindow;

			if (!topWindow) {
				var parentWindow = window.parent;

				var parentThemeDisplay;

				while (parentWindow != window) {
					try {
						if (typeof parentWindow.location.href == 'undefined') {
							break;
						}
					}
					catch (e) {
						break;
					}

					parentThemeDisplay = parentWindow.themeDisplay;

					if (!parentThemeDisplay) {
						break;
					}
					else if (!parentThemeDisplay.isStatePopUp() || (parentWindow == parentWindow.parent)) {
						topWindow = parentWindow;

						break;
					}

					parentWindow = parentWindow.parent;
				}

				if (!topWindow) {
					topWindow = window;
				}

				Util._topWindow = topWindow;
			}

			return topWindow;
		},

		getWindow: function(id) {
			if (!id) {
				id = Util.getWindowName();
			}

			return Util.getTop().Liferay.Util.Window.getById(id);
		},

		getWindowName: function() {
			return window.name || Window._name || '';
		},

		getURLWithSessionId: function(url) {
			if (!themeDisplay.isAddSessionIdToURL()) {
				return url;
			}

			// LEP-4787

			var x = url.indexOf(';');

			if (x > -1) {
				return url;
			}

			var sessionId = ';jsessionid=' + themeDisplay.getSessionId();

			x = url.indexOf('?');

			if (x > -1) {
				return url.substring(0, x) + sessionId + url.substring(x);
			}

			// In IE6, http://www.abc.com;jsessionid=XYZ does not work, but
			// http://www.abc.com/;jsessionid=XYZ does work.

			x = url.indexOf('//');

			if (x > -1) {
				var y = url.lastIndexOf('/');

				if (x + 1 == y) {
					return url + '/' + sessionId;
				}
			}

			return url + sessionId;
		},

		isArray: function(object) {
			return !!(window.Array && object.constructor == window.Array);
		},

		isEditorPresent: function(editorImpl) {
			return Liferay.EDITORS && Liferay.EDITORS[editorImpl];
		},

		openWindow: function(config, callback) {
			config.openingWindow = window;

			var top = Util.getTop();

			var topUtil = top.Liferay.Util;

			topUtil._openWindowProvider(config, callback);
		},

		processTab: function(id) {
			document.all[id].selection.text = String.fromCharCode(9);
			document.all[id].focus();
		},

		randomInt: function() {
			return (Math.ceil(Math.random() * (new Date).getTime()));
		},

		randomMinMax: function(min, max) {
			return (Math.round(Math.random() * (max - min))) + min;
		},

		selectAndCopy: function(el) {
			el.focus();
			el.select();

			if (document.all) {
				var textRange = el.createTextRange();

				textRange.execCommand('copy');
			}
		},

		setBox: function(oldBox, newBox) {
			for (var i = oldBox.length - 1; i > -1; i--) {
				oldBox.options[i] = null;
			}

			for (i = 0; i < newBox.length; i++) {
				oldBox.options[i] = new Option(newBox[i].value, i);
			}

			oldBox.options[0].selected = true;
		},

		setCursorPosition: function(el, position) {
			var instance = this;

			instance.setSelectionRange(el, position, position);
		},

		setSelectionRange: function(el, selectionStart, selectionEnd) {
			var instance = this;

			if (Lang.isFunction(el.getDOM)) {
				el = el.getDOM();
			}

			if (el.setSelectionRange) {
				el.focus();

				el.setSelectionRange(selectionStart, selectionEnd);
			}
			else if (el.createTextRange) {
				var textRange = el.createTextRange();

				textRange.collapse(true);

				textRange.moveEnd('character', selectionEnd);
				textRange.moveEnd('character', selectionStart);

				textRange.select();
			}
		},

		showCapsLock: function(event, span) {
			var keyCode = event.keyCode ? event.keyCode : event.which;
			var shiftKey = event.shiftKey ? event.shiftKey : ((keyCode == 16) ? true : false);

			if (((keyCode >= 65 && keyCode <= 90) && !shiftKey) ||
				((keyCode >= 97 && keyCode <= 122) && shiftKey)) {

				document.getElementById(span).style.display = '';
			}
			else {
				document.getElementById(span).style.display = 'none';
			}
		},

		sortByAscending: function(a, b) {
			a = a[1].toLowerCase();
			b = b[1].toLowerCase();

			if (a > b) {
				return 1;
			}

			if (a < b) {
				return -1;
			}

			return 0;
		},

		startsWith: function(str, x) {
			return (str.indexOf(x) === 0);
		},

		textareaTabs: function(event) {
			var el = event.currentTarget.getDOM();
			var pressedKey = event.keyCode;

			if (event.isKey('TAB')) {
				event.halt();

				var oldscroll = el.scrollTop;

				if (el.setSelectionRange) {
					var caretPos = el.selectionStart + 1;
					var elValue = el.value;

					el.value = elValue.substring(0, el.selectionStart) + '\t' + elValue.substring(el.selectionEnd, elValue.length);

					setTimeout(
						function() {
							el.focus();
							el.setSelectionRange(caretPos, caretPos);
						}, 0);

				}
				else {
					document.selection.createRange().text='\t';
				}

				el.scrollTop = oldscroll;

				return false;
			}
		},

		toCharCode: A.cached(
			function(name) {
				var buffer = [];

				for (var i = 0; i < name.length; i++) {
					buffer[i] = name.charCodeAt(i);
				}

				return buffer.join('');
			}
		),

		toNumber: function(value) {
			return parseInt(value, 10) || 0;
		},

		uncamelize: function(value, separator) {
			separator = separator || ' ';

			value = value.replace(/([a-zA-Z][a-zA-Z])([A-Z])([a-z])/g, '$1' + separator + '$2$3');
			value = value.replace(/([a-z])([A-Z])/g, '$1' + separator + '$2');

			return value;
		},

		unescapeHTML: function(str, entities) {
			var regex = REGEX_HTML_UNESCAPE;

			var entitiesMap = MAP_HTML_CHARS_UNESCAPED;

			if (entities) {
				var entitiesValues = [];

				entitiesMap = {};

				AObject.each(
					entities,
					function(item, index) {
						entitiesMap[item] = index;

						entitiesValues.push(item);
					}
				);

				regex = new RegExp(entitiesValues.join('|'), 'gi');
			}

			return str.replace(regex, A.bind('_unescapeHTML', Util, entitiesMap));
		},

		_defaultSubmitFormFn: function(event) {
			var form = event.form;

			var hasErrors = false;

			if (event.validate) {
				var liferayForm = Liferay.Form.get(form.attr('id'));

				if (liferayForm) {
					var validator = liferayForm.formValidator;

					if (A.instanceOf(validator, A.FormValidator)) {
						validator.validate();

						hasErrors = validator.hasErrors();
					}
				}
			}

			if (!hasErrors) {
				var action = event.action;
				var singleSubmit = event.singleSubmit;

				var inputs = form.all('input[type=button], input[type=reset], input[type=submit]');

				Util.disableFormButtons(inputs, form);

				if (singleSubmit === false) {
					Util._submitLocked = A.later(
						1000,
						Util,
						Util.enableFormButtons,
						[inputs, form]
					);
				}
				else {
					Util._submitLocked = true;
				}

				if (action !== null) {
					form.attr('action', action);
				}

				form.submit();

				form.attr('target', '');
			}
		},

		_escapeHTML: function(preventDoubleEscape, entities, entitiesValues, match) {
			var result;

			if (preventDoubleEscape) {
				var arrayArgs = AArray(arguments);

				var length = arrayArgs.length;

				var string = arrayArgs[length - 1];
				var offset = arrayArgs[length - 2];

				var nextSemicolonIndex = string.indexOf(';', offset);

				if (nextSemicolonIndex >= 0) {
					var entity = string.substring(offset, nextSemicolonIndex + 1);

					if (AArray.indexOf(entitiesValues, entity) >= 0) {
						result = match;
					}
				}
			}

			if (!result) {
				result = entities[match];
			}

			return result;
		},

		_getEditableInstance: function(title) {
			var editable = Util._EDITABLE;

			if (!editable) {
				editable = new A.Editable(
					{
						after: {
							contentTextChange: function(event) {
								var instance = this;

								if (!event.initial) {
									var title = instance.get('node');

									var portletTitleEditOptions = title.getData('portletTitleEditOptions');

									Util.savePortletTitle(
										{
											doAsUserId: portletTitleEditOptions.doAsUserId,
											plid: portletTitleEditOptions.plid,
											portletId: portletTitleEditOptions.portletId,
											title: event.newVal
										}
									);
								}
							},
							startEditing: function(event) {
								var instance = this;

								var Layout = Liferay.Layout;

								if (Layout) {
									instance._dragListener = Layout.getLayoutHandler().on(
										'drag:start',
										function(event) {
											instance.fire('save');
										}
									);
								}
							},
							stopEditing: function(event) {
								var instance = this;

								if (instance._dragListener) {
									instance._dragListener.detach();
								}
							}
						},
						cssClass: 'lfr-portlet-title-editable',
						node: title
					}
				);

				Util._EDITABLE = editable;
			}

			return editable;
		},

		_unescapeHTML: function(entities, match) {
			return entities[match];
		},

		MAP_HTML_CHARS_ESCAPED: MAP_HTML_CHARS_ESCAPED
	};

	Liferay.provide(
		Util,
		'afterIframeLoaded',
		function(event) {
			var nodeInstances = A.Node._instances;

			var docEl = event.doc;

			var docUID = docEl._yuid;

			if (docUID in nodeInstances) {
				delete nodeInstances[docUID];
			}

			var iframeDocument = A.one(docEl);

			var iframeBody = iframeDocument.one('body');

			var dialog = event.dialog;

			iframeBody.addClass('dialog-iframe-popup');

			var detachEventHandles = function() {
				AArray.invoke(eventHandles, 'detach');

				iframeDocument.purge(true);
			};

			var eventHandles = [
				iframeBody.delegate('submit', detachEventHandles, 'form'),

				iframeBody.delegate(
					EVENT_CLICK,
					function() {
						dialog.set('visible', false, SRC_HIDE_LINK);

						detachEventHandles();
					},
					'.lfr-hide-dialog'
				)
			];

			var cancelButton = iframeBody.one('.btn-cancel');

			if (cancelButton) {
				cancelButton.after(
					EVENT_CLICK,
					function() {
						detachEventHandles();

						dialog.hide();
					}
				);
			}

			var rolesSearchContainer = iframeBody.one('#rolesSearchContainerSearchContainer');

			if (rolesSearchContainer) {
				eventHandles.push(
					rolesSearchContainer.delegate(
						EVENT_CLICK,
						function(event) {
							event.preventDefault();

							detachEventHandles();

							submitForm(document.hrefFm, event.currentTarget.attr('href'));
						},
						'a'
					)
				);
			}
		},
		['aui-base']
	);

	Liferay.provide(
		Util,
		'check',
		function(form, name, checked) {
			var checkbox = A.one(form[name]);

			if (checkbox) {
				checkbox.set('checked', checked);
			}
		},
		['aui-base']
	);

	Liferay.provide(
		Util,
		'checkAll',
		function(form, name, allBox, selectClassName) {
			var selector;

			if (isArray(name)) {
				selector = 'input[name='+ name.join('], input[name=') + STR_RIGHT_SQUARE_BRACKET;
			}
			else {
				selector = 'input[name=' + name + STR_RIGHT_SQUARE_BRACKET;
			}

			form = A.one(form);

			form.all(selector).set('checked', A.one(allBox).get('checked'));

			if (selectClassName) {
				form.all(selectClassName).toggleClass('info', A.one(allBox).get('checked'));
			}
		},
		['aui-base']
	);

	Liferay.provide(
		Util,
		'checkAllBox',
		function(form, name, allBox) {
			var totalBoxes = 0;
			var totalOn = 0;
			var inputs = A.one(form).all('input[type=checkbox]');

			allBox = A.one(allBox) || A.one(form).one('input[name=' + allBox + STR_RIGHT_SQUARE_BRACKET);

			if (!isArray(name)) {
				name = [name];
			}

			inputs.each(
				function(item, index, collection) {
					if (!item.compareTo(allBox)) {
						if (arrayIndexOf(name, item.getAttribute('name')) > -1) {
							totalBoxes++;
						}

						if (item.get('checked')) {
							totalOn++;
						}
					}
				}
			);

			allBox.set('checked', (totalBoxes == totalOn));
		},
		['aui-base']
	);

	Liferay.provide(
		Util,
		'createFlyouts',
		function(options) {
			options = options || {};

			var flyout = A.one(options.container);
			var containers = [];

			if (flyout) {
				var lis = flyout.all('li');

				lis.each(
					function(item, index, collection) {
						var childUL = item.one('ul');

						if (childUL) {
							childUL.hide();

							item.addClass('lfr-flyout');
							item.addClass('has-children lfr-flyout-has-children');
						}
					}
				);

				var hideTask = A.debounce(
					function(event) {
						showTask.cancel();

						var li = event.currentTarget;

						if (li.hasClass('has-children')) {
							var childUL = event.currentTarget.one('> ul');

							if (childUL) {
								childUL.hide();

								if (options.mouseOut) {
									options.mouseOut.apply(event.currentTarget, [event]);
								}
							}
						}
					},
					300
				);

				var showTask = A.debounce(
					function(event) {
						hideTask.cancel();

						var li = event.currentTarget;

						if (li.hasClass('has-children')) {
							var childUL = event.currentTarget.one('> ul');

							if (childUL) {
								childUL.show();

								if (options.mouseOver) {
									options.mouseOver.apply(event.currentTarget, [event]);
								}
							}
						}
					},
					0
				);

				lis.on('mouseenter', showTask, 'li');
				lis.on('mouseleave', hideTask, 'li');
			}
		},
		['aui-base']
	);

	Liferay.provide(
		Util,
		'disableElements',
		function(obj) {
			var el = A.one(obj);

			if (el) {
				el = el.getDOM();

				var children = el.getElementsByTagName('*');

				var emptyFnFalse = Lang.emptyFnFalse;
				var Event = A.Event;

				for (var i = children.length - 1; i >= 0; i--) {
					var item = children[i];

					item.style.cursor = 'default';

					el.onclick = emptyFnFalse;
					el.onmouseover = emptyFnFalse;
					el.onmouseout = emptyFnFalse;
					el.onmouseenter = emptyFnFalse;
					el.onmouseleave = emptyFnFalse;

					Event.purgeElement(el, false);

					item.href = 'javascript:;';
					item.disabled = true;
					item.action = '';
					item.onsubmit = emptyFnFalse;
				}
			}
		},
		['aui-base']
	);

	Liferay.provide(
		Util,
		'disableSelectBoxes',
		function(toggleBoxId, value, selectBoxId) {
			var selectBox = A.one('#' + selectBoxId);
			var toggleBox = A.one('#' + toggleBoxId);

			if (selectBox && toggleBox) {
				var dynamicValue = Lang.isFunction(value);

				var disabled = function() {
					var currentValue = selectBox.val();

					var visible = (value == currentValue);

					if (dynamicValue) {
						visible = value(currentValue, value);
					}

					toggleBox.set('disabled', !visible);
				};

				disabled();

				selectBox.on('change', disabled);
			}
		},
		['aui-base']
	);

	Liferay.provide(
		Util,
		'disableTextareaTabs',
		function(textarea) {
			textarea = A.one(textarea);

			if (textarea && textarea.attr('textareatabs') != 'enabled') {
				textarea.attr('textareatabs', 'disabled');

				textarea.detach('keydown', Util.textareaTabs);
			}
		},
		['aui-base']
	);

	Liferay.provide(
		Util,
		'disableToggleBoxes',
		function(checkBoxId, toggleBoxId, checkDisabled) {
			var checkBox = A.one('#' + checkBoxId);
			var toggleBox = A.one('#' + toggleBoxId);

			if (checkBox && toggleBox) {
				if (checkBox.get('checked') && checkDisabled) {
					toggleBox.set('disabled', true);
				}
				else {
					toggleBox.set('disabled', false);
				}

				checkBox.on(
					EVENT_CLICK,
					function() {
						toggleBox.set('disabled', !toggleBox.get('disabled'));
					}
				);
			}
		},
		['aui-base']
	);

	Liferay.provide(
		Util,
		'enableTextareaTabs',
		function(textarea) {
			textarea = A.one(textarea);

			if (textarea && textarea.attr('textareatabs') != 'enabled') {
				textarea.attr('textareatabs', 'disabled');

				textarea.on('keydown', Util.textareaTabs);
			}
		},
		['aui-base']
	);

	Liferay.provide(
		Util,
		'focusFormField',
		function(el, caretPosition) {
			Util.addInputFocus();

			var interacting = false;

			var clickHandle = A.getDoc().on(
				EVENT_CLICK,
				function(event) {
					interacting = true;

					clickHandle.detach();
				}
			);

			if (!interacting) {
				el = A.one(el);

				try {
					el.focus();
				}
				catch (e) {
				}
			}
		},
		['aui-base']
	);

	Liferay.provide(
		Util,
		'forcePost',
		function(link) {
			link = A.one(link);

			if (link) {
				var url = link.attr('href');

				var newWindow = (link.attr('target') == '_blank');

				if (newWindow) {
					A.one(document.hrefFm).attr('target', '_blank');
				}

				submitForm(document.hrefFm, url, !newWindow);

				Util._submitLocked = null;
			}
		},
		['aui-base']
	);

	Liferay.provide(
		Util,
		'moveItem',
		function(fromBox, toBox, sort) {
			fromBox = A.one(fromBox);
			toBox = A.one(toBox);

			var selectedIndex = fromBox.get('selectedIndex');

			var selectedOption;

			if (selectedIndex >= 0) {
				var options = fromBox.all('option');

				selectedOption = options.item(selectedIndex);

				options.each(
					function(item, index, collection) {
						if (item.get('selected')) {
							toBox.append(item);
						}
					}
				);
			}

			if (selectedOption && selectedOption.text() != '' && sort == true) {
				Util.sortBox(toBox);
			}
		},
		['aui-base']
	);

	Liferay.provide(
		Util,
		'openDDMPortlet',
		function(config, callback) {
			var instance = this;

			var defaultValues = {
				eventName: 'selectStructure'
			};

			config = A.merge(defaultValues,	config);

			var ddmURL = Liferay.PortletURL.createRenderURL();

			ddmURL.setEscapeXML(false);

			ddmURL.setDoAsGroupId(config.doAsGroupId || themeDisplay.getScopeGroupId());

			ddmURL.setParameter('classNameId', config.classNameId);
			ddmURL.setParameter('classPK', config.classPK);
			ddmURL.setParameter('eventName', config.eventName);
			ddmURL.setParameter('groupId', config.groupId);

			if ('refererPortletName' in config) {
				ddmURL.setParameter('refererPortletName', config.refererPortletName);
			}

			if ('refererWebDAVToken' in config) {
				ddmURL.setParameter('refererWebDAVToken', config.refererWebDAVToken);
			}

			ddmURL.setParameter('scopeTemplateMode', config.templateMode);
			ddmURL.setParameter('scopeTitle', config.title);

			if ('showGlobalScope' in config) {
				ddmURL.setParameter('showGlobalScope', config.showGlobalScope);
			}

			if ('showManageTemplates' in config) {
				ddmURL.setParameter('showManageTemplates', config.showManageTemplates);
			}

			if ('showToolbar' in config) {
				ddmURL.setParameter('showToolbar', config.showToolbar);
			}

			if (config.struts_action) {
				ddmURL.setParameter('struts_action', config.struts_action);
			}
			else {
				ddmURL.setParameter('struts_action', '/dynamic_data_mapping/view');
			}

			ddmURL.setParameter('templateId', config.templateId);

			ddmURL.setPortletId(166);
			ddmURL.setWindowState('pop_up');

			config.uri = ddmURL.toString();

			var dialogConfig = config.dialog;

			if (!dialogConfig) {
				dialogConfig = {};

				config.dialog = dialogConfig;
			}

			Util.openWindow(config);

			Liferay.on(config.eventName, callback);
		},
		['liferay-portlet-url']
	);

	Liferay.provide(
		Util,
		'openDocument',
		function(webDavUrl, onSuccess, onError) {
			if (A.UA.ie) {
				try {
					var executor = new A.config.win.ActiveXObject('SharePoint.OpenDocuments');

					executor.EditDocument(webDavUrl);

					if (Lang.isFunction(onSuccess)) {
						onSuccess();
					}

				}
				catch (exception) {
					if (Lang.isFunction(onError)) {
						onError(exception);
					}
				}
			}
		},
		['aui-base']
	);

	Liferay.provide(
		Util,
		'portletTitleEdit',
		function(options) {
			var obj = options.obj;

			if (obj && !obj.hasClass('portlet-borderless')) {
				var title = obj.one('.portlet-title-text');

				if (title && !title.hasClass('not-editable')) {
					title.addClass('portlet-title-editable');

					title.on(
						EVENT_CLICK,
						function(event) {
							var editable = Util._getEditableInstance(title);

							var rendered = editable.get('rendered');

							if (rendered) {
								editable.fire('stopEditing');
							}

							editable.set('node', event.currentTarget);

							if (rendered) {
								editable.syncUI();
							}

							editable._startEditing(event);
						}
					);

					title.setData('portletTitleEditOptions', options);
				}
			}
		},
		['aui-editable-deprecated']
	);

	Liferay.provide(
		Util,
		'removeFolderSelection',
		function(folderIdString, folderNameString, namespace) {
			A.byIdNS(namespace, folderIdString).val(0);

			var nameEl = A.byIdNS(namespace, folderNameString);

			nameEl.attr('href', '');

			nameEl.empty();

			Liferay.Util.toggleDisabled(A.byIdNS(namespace, 'removeFolderButton'), true);
		},
		['aui-base', 'liferay-node']
	);

	Liferay.provide(
		Util,
		'removeItem',
		function(box, value) {
			box = A.one(box);

			var selectedIndex = box.get('selectedIndex');

			if (!value) {
				box.all('option').item(selectedIndex).remove(true);
			}
			else {
				box.all('option[value=' + value + STR_RIGHT_SQUARE_BRACKET).item(selectedIndex).remove(true);
			}
		},
		['aui-base']
	);

	Liferay.provide(
		Util,
		'reorder',
		function(box, down) {
			box = A.one(box);

			var selectedIndex = box.get('selectedIndex');

			if (selectedIndex == -1) {
				box.set('selectedIndex', 0);
			}
			else {
				var selectedItems = box.all(':selected');

				var lastIndex = box.get('options').size() - 1;

				var length = selectedItems.size();

				if (down) {
					while (length--) {
						var item = selectedItems.item(length);

						var itemIndex = item.get('index');

						var referenceNode = box.get('firstChild');

						if (itemIndex != lastIndex) {
							var nextSibling = item.next();

							if (nextSibling) {
								referenceNode = nextSibling.next();
							}
						}

						box.insertBefore(item, referenceNode);
					}
				}
				else {
					for (var i = 0; i < length; i++) {
						var item = selectedItems.item(i);

						var itemIndex = item.get('index');

						if (itemIndex == 0) {
							box.append(item);
						}
						else {
							box.insertBefore(item, item.previous());
						}
					}
				}
			}
		},
		['aui-base']
	);

	Liferay.provide(
		Util,
		'resizeTextarea',
		function(elString, usingRichEditor) {
			var el = A.one('#' + elString);

			if (!el) {
				el = A.one('textarea[name=' + elString + STR_RIGHT_SQUARE_BRACKET);
			}

			if (el) {
				var pageBody = A.getBody();

				var diff;

				var resize = function(event) {
					var pageBodyHeight = pageBody.get('winHeight');

					if (usingRichEditor) {
						try {
							if (el.get('nodeName').toLowerCase() != 'iframe') {
								el = window[elString];
							}
						}
						catch (e) {
						}
					}

					if (!diff) {
						var buttonRow = pageBody.one('.button-holder');
						var templateEditor = pageBody.one('.lfr-template-editor');

						if (buttonRow && templateEditor) {
							var region = templateEditor.getXY();

							diff = (buttonRow.outerHeight(true) + region[1]) + 25;
						}
						else {
							diff = 170;
						}
					}

					el = A.one(el);

					var styles = {
						width: '98%'
					};

					if (event) {
						styles.height = (pageBodyHeight - diff);
					}

					if (usingRichEditor) {
						if (!el || !A.DOM.inDoc(el)) {
							A.on(
								'available',
								function(event) {
									el = A.one(window[elString]);

									if (el) {
										el.setStyles(styles);
									}
								},
								'#' + elString + '_cp'
							);

							return;
						}
					}

					if (el) {
						el.setStyles(styles);
					}
				};

				resize();

				var dialog = Liferay.Util.getWindow();

				if (dialog) {
					var resizeEventHandle = dialog.iframe.after('resizeiframe:heightChange', resize);

					A.getWin().on('unload', resizeEventHandle.detach, resizeEventHandle);
				}
			}
		},
		['aui-base']
	);

	Liferay.provide(
		Util,
		'savePortletTitle',
		function(params) {
			A.mix(
				params,
				{
					doAsUserId: 0,
					plid: 0,
					portletId: 0,
					title: '',
					url: themeDisplay.getPathMain() + '/portlet_configuration/update_title'
				}
			);

			A.io.request(
				params.url,
				{
					data: {
						doAsUserId: params.doAsUserId,
						p_auth: Liferay.authToken,
						p_l_id: params.plid,
						portletId: params.portletId,
						title: params.title
					}
				}
			);
		},
		['aui-io']
	);

	Liferay.provide(
		Util,
		'selectEntity',
		function(config, callback) {
			var dialog = Util.getWindow(config.id);

			var eventName = config.eventName || config.id;

			var selectionEvent = Liferay.on(eventName, callback);

			if (dialog) {
				dialog.show();
			}
			else {
				Util.openWindow(
					config,
					function(dialogWindow) {
						dialogWindow.after('close', selectionEvent.detach, selectionEvent);
					}
				);
			}
		},
		['aui-base', 'liferay-util-window']
	);

	Liferay.provide(
		Util,
		'selectFolder',
		function(folderData, folderHref, namespace) {
			A.byIdNS(namespace, folderData['idString']).val(folderData['idValue']);

			var nameEl = A.byIdNS(namespace, folderData['nameString']);

			Liferay.Util.addParams(namespace + 'folderId=' + folderData['idValue'], folderHref);

			nameEl.attr('href', folderHref);

			nameEl.setContent(folderData['nameValue'] + '&nbsp;');

			var button = A.byIdNS(namespace, 'removeFolderButton');

			if (button) {
				button.set('disabled', false);

				button.removeClass('btn-disabled');
			}
		},
		['aui-base', 'liferay-node']
	);

	Liferay.provide(
		Util,
		'setSelectedValue',
		function(col, value) {
			var option = A.one(col).one('option[value=' + value + STR_RIGHT_SQUARE_BRACKET);

			if (option) {
				option.set('selected', true);
			}
		},
		['aui-base']
	);

	Liferay.provide(
		Util,
		'sortBox',
		function(box) {
			var newBox = [];

			var options = box.all('option');

			for (var i = 0; i < options.size(); i++) {
				newBox[i] = [options.item(i).val(), options.item(i).text()];
			}

			newBox.sort(Util.sortByAscending);

			var boxObj = A.one(box);

			boxObj.all('option').remove(true);

			A.each(
				newBox,
				function(item, index, collection) {
					boxObj.append('<option value="' + item[0] + '">' + item[1] + '</option>');
				}
			);

			if (Browser.isIe()) {
				var currentWidth = boxObj.getStyle('width');

				if (currentWidth == 'auto') {
					boxObj.setStyle('width', 'auto');
				}
			}
		},
		['aui-base']
	);

	/**
	 * OPTIONS
	 *
	 * Required
	 * uri {string}: The url to open that sets the editor.
	 */

	Liferay.provide(
		Util,
		'switchEditor',
		function(options) {
			var uri = options.uri;

			var windowName = Liferay.Util.getWindowName();

			var dialog = Liferay.Util.getWindow(windowName);

			if (dialog) {
				dialog.iframe.set('uri', uri);
			}
		},
		['aui-io']
	);

	Liferay.provide(
		Util,
		'toggleBoxes',
		function(checkBoxId, toggleBoxId, displayWhenUnchecked) {
			var checkBox = A.one('#' + checkBoxId);
			var toggleBox = A.one('#' + toggleBoxId);

			if (checkBox && toggleBox) {
				var checked = checkBox.get('checked');

				if (checked) {
					toggleBox.show();
				}
				else {
					toggleBox.hide();
				}

				if (displayWhenUnchecked) {
					toggleBox.toggle();
				}

				checkBox.on(
					EVENT_CLICK,
					function() {
						toggleBox.toggle();
					}
				);
			}
		},
		['aui-base']
	);

	Liferay.provide(
		Util,
		'toggleControls',
		function(node) {
			var docBody = A.getBody();

			node = node || docBody;

			var trigger = node.one('.toggle-controls');

			if (trigger) {
				var hiddenClass = 'controls-hidden';
				var iconHiddenClass = 'icon-remove';
				var iconVisibleClass = 'icon-ok';
				var visibleClass = 'controls-visible';
				var currentClass = visibleClass;
				var currentIconClass = iconVisibleClass;

				if (Liferay._editControlsState != 'visible') {
					currentClass = hiddenClass;
					currentIconClass = iconHiddenClass;
				}

				var icon = trigger.one('.controls-state-icon');

				if (icon) {
					icon.addClass(currentIconClass);
				}

				docBody.addClass(currentClass);

				trigger.on(
					EVENT_CLICK,
					function(event) {
						if (icon) {
							icon.toggleClass(iconVisibleClass).toggleClass(iconHiddenClass);
						}

						docBody.toggleClass(visibleClass).toggleClass(hiddenClass);

						Liferay._editControlsState = (docBody.hasClass(visibleClass) ? 'visible' : 'hidden');

						Liferay.Store('liferay_toggle_controls', Liferay._editControlsState);
					}
				);
			}
		},
		['liferay-store']
	);

	Liferay.provide(
		Util,
		'toggleDisabled',
		function(button, state) {
			if (!A.instanceOf(button, A.NodeList)) {
				button = A.all(button);
			}

			button.each(
				function(item, index, collection) {
					item.attr('disabled', state);

					item.toggleClass('disabled', state);
				}
			);
		},
		['aui-base']
	);

	Liferay.provide(
		Util,
		'toggleRadio',
		function(radioId, showBoxIds, hideBoxIds) {
			var radioButton = A.one('#' + radioId);

			if (radioButton) {
				var checked = radioButton.get('checked');

				var showBoxes;

				if (Lang.isValue(showBoxIds)) {
					if (Lang.isArray(showBoxIds)) {
						showBoxIds = showBoxIds.join(',#');
					}

					showBoxes = A.all('#' + showBoxIds);

					showBoxes.toggle(checked);
				}

				radioButton.on(
					'change',
					function() {
						if (showBoxes) {
							showBoxes.show();
						}

						if (Lang.isValue(hideBoxIds)) {
							if (Lang.isArray(hideBoxIds)) {
								hideBoxIds = hideBoxIds.join(',#');
							}

							A.all('#' + hideBoxIds).hide();
						}
					}
				);
			}
		},
		['aui-base']
	);

	Liferay.provide(
		Util,
		'toggleSelectBox',
		function(selectBoxId, value, toggleBoxId) {
			var selectBox = A.one('#' + selectBoxId);
			var toggleBox = A.one('#' + toggleBoxId);

			if (selectBox && toggleBox) {
				var dynamicValue = Lang.isFunction(value);

				var toggle = function() {
					var currentValue = selectBox.val();

					var visible = (value == currentValue);

					if (dynamicValue) {
						visible = value(currentValue, value);
					}

					toggleBox.toggle(visible);
				};

				toggle();

				selectBox.on('change', toggle);
			}
		},
		['aui-base']
	);

	Liferay.provide(
		Util,
		'updateCheckboxValue',
		function(checkbox) {
			checkbox = A.one(checkbox);

			if (checkbox) {
				var checked = checkbox.attr('checked');

				var value = 'false';

				if (checked) {
					value = checkbox.val();
				}

				checkbox.previous().val(value);
			}
		},
		['aui-base']
	);

	Liferay.provide(
		window,
		'submitForm',
		function(form, action, singleSubmit, validate) {
			if (!Util._submitLocked) {
				Liferay.fire(
					'submitForm',
					{
						form: A.one(form),
						action: action,
						singleSubmit: singleSubmit,
						validate: validate !== false
					}
				);
			}
		},
		['aui-base', 'aui-form-validator', 'liferay-form']
	);

	Liferay.publish(
		'submitForm',
		{
			defaultFn: Util._defaultSubmitFormFn
		}
	);

	Liferay.provide(
		Util,
		'_openWindowProvider',
		function(config, callback) {
			Window.getWindow(config, callback);
		},
		['liferay-util-window']
	);

	Liferay.after(
		'closeWindow',
		function(event) {
			var id = event.id;

			var dialog = Liferay.Util.getTop().Liferay.Util.Window.getById(id);

			if (dialog && dialog.iframe) {
				var dialogWindow = dialog.iframe.node.get('contentWindow').getDOM();

				var openingWindow = dialogWindow.Liferay.Util.getOpener();
				var redirect = event.redirect;

				if (redirect) {
					openingWindow.location = redirect;
				}
				else {
					var refresh = event.refresh;

					if (refresh && openingWindow) {
						var data;

						if (!event.portletAjaxable) {
							data = {
								portletAjaxable: false
							};
						}

						openingWindow.Liferay.Portlet.refresh('#p_p_id_' + refresh + '_', data);
					}
				}

				dialog.hide();
			}
		}
	);

	Util.Window = Window;

	Liferay.Util = Util;

	// 0-200: Theme Developer
	// 200-400: Portlet Developer
	// 400+: Liferay

	Liferay.zIndex = {
		DOCK: 10,
		DOCK_PARENT: 20,
		ALERT: 430,
		DROP_AREA: 440,
		DROP_POSITION: 450,
		DRAG_ITEM: 460,
		TOOLTIP: 10000,
		WINDOW: 1000,
		MENU: 5000
	};
})(AUI(), Liferay);