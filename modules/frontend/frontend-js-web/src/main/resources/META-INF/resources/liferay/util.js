;(function(A, $, _, Liferay) {
	A.use('aui-base-lang');

	var AArray = A.Array;

	var Lang = A.Lang;

	var EVENT_CLICK = 'click';

	var REGEX_PORTLET_ID = /^(?:p_p_id)?_(.*)_.*$/;

	var SRC_HIDE_LINK = {
		src: 'hideLink'
	};

	var STR_CHECKED = 'checked';

	var STR_RIGHT_SQUARE_BRACKET = ']';

	var Window = {
		_map: {}
	};

	var Util = {
		submitCountdown: 0,

		addInputCancel: function() {
			A.use(
				'aui-button-search-cancel',
				function(A) {
					new A.ButtonSearchCancel(
						{
							trigger: 'input[type=password], input[type=search], input.clearable, input.search-query'
						}
					);
				}
			);

			Util.addInputCancel = function() {
			};
		},

		addParams: function(params, url) {
			if (_.isObject(params)) {
				params = $.param(params, true);
			}
			else {
				params = String(params).trim();
			}

			var loc = url || location.href;

			var finalUrl = loc;

			if (params) {
				var anchorHash;

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

				}
			}

			return finalUrl;
		},

		checkAll: function(form, name, allBox, selectClassName) {
			if (form) {
				form = Util.getDOM(form);
				allBox = Util.getDOM(allBox);

				var selector;

				if (_.isArray(name)) {
					selector = 'input[name=' + name.join('], input[name=') + STR_RIGHT_SQUARE_BRACKET;
				}
				else {
					selector = 'input[name=' + name + STR_RIGHT_SQUARE_BRACKET;
				}

				form = $(form);

				var allBoxChecked = $(allBox).prop(STR_CHECKED);

				form.find(selector).each(
					function(index, item) {
						item = $(item);

						if (!item.prop('disabled')) {
							item.prop(STR_CHECKED, allBoxChecked);
						}
					}
				);

				if (selectClassName) {
					form.find(selectClassName).toggleClass('info', allBoxChecked);
				}
			}
		},

		checkAllBox: function(form, name, allBox) {
			var totalOn = 0;

			if (form) {
				form = Util.getDOM(form);
				allBox = Util.getDOM(allBox);

				form = $(form);

				var allBoxNodes = $(allBox);

				if (!allBoxNodes.length) {
					allBoxNodes = $('input[name="' + allBox + '"]');
				}

				var totalBoxes = 0;

				var inputs = form.find('input[type=checkbox]');

				if (!_.isArray(name)) {
					name = [name];
				}

				inputs.each(
					function(index, item) {
						item = $(item);

						if (!item.is(allBoxNodes) && _.indexOf(name, item.attr('name')) > -1) {
							totalBoxes++;

							if (item.prop(STR_CHECKED)) {
								totalOn++;
							}
						}
					}
				);

				allBoxNodes.prop(STR_CHECKED, totalBoxes == totalOn);
			}

			return totalOn;
		},

		checkTab: function(box) {
			if (document.all && window.event.keyCode == 9) {
				box.selection = document.selection.createRange();

				setTimeout(
					function() {
						Util.processTab(box.id);
					},
					0
				);
			}
		},

		disableElements: function(el) {
			var currentElement = $(el)[0];

			if (currentElement) {
				var children = currentElement.getElementsByTagName('*');

				var emptyFnFalse = _.constant(false);

				for (var i = children.length - 1; i >= 0; i--) {
					var item = children[i];

					item.style.cursor = 'default';

					item.onclick = emptyFnFalse;
					item.onmouseover = emptyFnFalse;
					item.onmouseout = emptyFnFalse;
					item.onmouseenter = emptyFnFalse;
					item.onmouseleave = emptyFnFalse;

					item.action = '';
					item.disabled = true;
					item.href = 'javascript:;';
					item.onsubmit = emptyFnFalse;

					$(item).off();
				}
			}
		},

		disableEsc: function() {
			if (document.all && window.event.keyCode == 27) {
				window.event.returnValue = false;
			}
		},

		disableFormButtons: function(inputs, form) {
			inputs.attr('disabled', true);
			inputs.setStyle('opacity', 0.5);

			if (A.UA.gecko) {
				A.getWin().on(
					'unload',
					function(event) {
						inputs.attr('disabled', false);
					}
				);
			}
		},

		disableToggleBoxes: function(checkBoxId, toggleBoxId, checkDisabled) {
			var checkBox = $('#' + checkBoxId);
			var toggleBox = $('#' + toggleBoxId);

			toggleBox.prop('disabled', checkDisabled && checkBox.prop(STR_CHECKED));

			checkBox.on(
				EVENT_CLICK,
				function() {
					toggleBox.prop('disabled', !toggleBox.prop('disabled'));
				}
			);
		},

		enableFormButtons: function(inputs) {
			Util._submitLocked = null;

			Util.toggleDisabled(inputs, false);
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

		focusFormField: function(el) {
			var interacting = false;

			var doc = $(document);

			doc.on(
				'click.focusFormField',
				function(event) {
					interacting = true;

					doc.off('click.focusFormField');
				}
			);

			el = Util.getDOM(el);

			el = $(el);

			if (!interacting && Util.inBrowserView(el)) {
				el.focus();
			}
		},

		forcePost: function(link) {
			link = Util.getDOM(link);

			link = $(link);

			if (link.length) {
				var url = link.attr('href');

				var newWindow = link.attr('target') == '_blank';

				var hrefFm = $(document.hrefFm);

				if (newWindow) {
					hrefFm.attr('target', '_blank');
				}

				submitForm(hrefFm, url, !newWindow);

				Util._submitLocked = null;
			}
		},

		getAttributes: function(el, attributeGetter) {
			var instance = this;

			var result = null;

			if (el) {
				el = Util.getDOM(el);

				if (el.jquery) {
					el = el[0];
				}

				result = {};

				var getterFn = _.isFunction(attributeGetter);
				var getterString = _.isString(attributeGetter);

				var attrs = el.attributes;
				var length = attrs.length;

				while (length--) {
					var attr = attrs[length];
					var name = attr.nodeName.toLowerCase();
					var value = attr.nodeValue;

					if (getterString) {
						if (name.indexOf(attributeGetter) === 0) {
							name = name.substr(attributeGetter.length);
						}
						else {
							continue;
						}
					}
					else if (getterFn) {
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

		getDOM: function(el) {
			if (el._node || el._nodes) {
				el = el.getDOM();
			}

			return el;
		},

		getGeolocation: function(success, fallback, options) {
			if (success && navigator.geolocation) {
				navigator.geolocation.getCurrentPosition(
					function(position) {
						success(
							position.coords.latitude,
							position.coords.longitude,
							position
						);
					},
					fallback,
					options
				);
			}
			else if (fallback) {
				fallback();
			}
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
			return String(portletId).replace(REGEX_PORTLET_ID, '$1');
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

					if (!parentThemeDisplay || window.name === 'devicePreviewIframe') {
						break;
					}
					else if (!parentThemeDisplay.isStatePopUp() || parentWindow == parentWindow.parent) {
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

		getWindow: function(id) {
			if (!id) {
				id = Util.getWindowName();
			}

			return Util.getTop().Liferay.Util.Window.getById(id);
		},

		getWindowName: function() {
			return window.name || Window._name || '';
		},

		getWindowWidth: function() {
			return window.innerWidth > 0 ? window.innerWidth : screen.width;
		},

		inBrowserView: function(node, win, nodeRegion) {
			var viewable = false;

			node = $(node);

			if (node.length) {
				if (!nodeRegion) {
					nodeRegion = node.offset();

					nodeRegion.bottom = nodeRegion.top + node.outerHeight();
					nodeRegion.right = nodeRegion.left + node.outerWidth();
				}

				if (!win) {
					win = window;
				}

				win = $(win);

				var winRegion = {};

				winRegion.left = win.scrollLeft();
				winRegion.right = winRegion.left + win.width();

				winRegion.top = win.scrollTop();
				winRegion.bottom = winRegion.top + win.height();

				viewable = nodeRegion.bottom <= winRegion.bottom &&
					nodeRegion.left >= winRegion.left &&
					nodeRegion.right <= winRegion.right &&
					nodeRegion.top >= winRegion.top;

				if (viewable) {
					var frameEl = $(win.prop('frameElement'));

					if (frameEl.length) {
						var frameOffset = frameEl.offset();

						var xOffset = frameOffset.left - winRegion.left;

						nodeRegion.left += xOffset;
						nodeRegion.right += xOffset;

						var yOffset = frameOffset.top - winRegion.top;

						nodeRegion.top += yOffset;
						nodeRegion.bottom += yOffset;

						viewable = Util.inBrowserView(node, win.prop('parent'), nodeRegion);
					}
				}
			}

			return viewable;
		},

		isPhone: function() {
			var instance = this;

			return instance.getWindowWidth() < Liferay.BREAKPOINTS.PHONE;
		},

		isTablet: function() {
			var instance = this;

			return instance.getWindowWidth() < Liferay.BREAKPOINTS.TABLET;
		},

		listCheckboxesExcept: function(form, except, name, checked) {
			form = Util.getDOM(form);

			var selector = 'input[type=checkbox]';

			if (name) {
				selector += '[name=' + name + ']';
			}

			return _.reduce(
				$(form).find(selector),
				function(prev, item, index) {
					item = $(item);

					var val = item.val();

					if (val && item.attr('name') != except && item.prop('checked') == checked && !item.prop('disabled')) {
						prev.push(val);
					}

					return prev;
				},
				[]
			).join();
		},

		listCheckedExcept: function(form, except, name) {
			return Util.listCheckboxesExcept(form, except, name, true);
		},

		listSelect: function(select, delimeter) {
			select = Util.getDOM(select);

			return _.reduce(
				$(select).find('option'),
				function(prev, item, index) {
					var val = $(item).val();

					if (val) {
						prev.push(val);
					}

					return prev;
				},
				[]
			).join(delimeter || ',');
		},

		listUncheckedExcept: function(form, except, name) {
			return Util.listCheckboxesExcept(form, except, name, false);
		},

		ns: function(namespace, obj) {
			var instance = this;

			var value;

			var ns = instance._ns;

			if (!_.isObject(obj)) {
				value = ns(namespace, obj);
			}
			else {
				value = {};

				_.forEach(
					obj,
					function(item, index) {
						index = ns(namespace, index);

						value[index] = item;
					}
				);
			}

			return value;
		},

		openInDialog: function(event) {
			event.preventDefault();

			var currentTarget = Util.getDOM(event.currentTarget);

			currentTarget = $(currentTarget);

			var config = currentTarget.data();

			if (!config.uri) {
				config.uri = currentTarget.data('href') || currentTarget.attr('href');
			}

			if (!config.title) {
				config.title = currentTarget.attr('title');
			}

			Liferay.Util.openWindow(config);
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
			return Math.ceil(Math.random() * (new Date()).getTime());
		},

		removeEntitySelection: function(entityIdString, entityNameString, removeEntityButton, namespace) {
			$('#' + namespace + entityIdString).val(0);

			$('#' + namespace + entityNameString).val('');

			Liferay.Util.toggleDisabled(removeEntityButton, true);

			Liferay.fire('entitySelectionRemoved');
		},

		reorder: function(box, down) {
			box = Util.getDOM(box);

			box = $(box);

			if (box.prop('selectedIndex') == -1) {
				box.prop('selectedIndex', 0);
			}
			else {
				var selectedItems = box.find('option:selected');

				if (down) {
					_.forEachRight(
						selectedItems,
						function(item, index) {
							item = $(item);

							var itemIndex = item.prop('index');

							var lastIndex = box.find('option').length - 1;

							if (itemIndex === lastIndex) {
								box.prepend(item);
							}
							else {
								item.insertAfter(item.next());
							}
						}
					);
				}
				else {
					_.forEach(
						selectedItems,
						function(item, index) {
							item = $(item);

							var itemIndex = item.prop('index');

							if (itemIndex === 0) {
								box.append(item);
							}
							else {
								item.insertBefore(item.prev());
							}
						}
					);
				}
			}
		},

		rowCheckerCheckAllBox: function(ancestorTable, ancestorRow, checkboxesIds, checkboxAllIds, cssClass) {
			Util.checkAllBox(ancestorTable, checkboxesIds, checkboxAllIds);

			if (ancestorRow) {
				ancestorRow.toggleClass(cssClass);
			}
		},

		savePortletTitle: function(params) {
			_.defaults(
				params,
				{
					doAsUserId: 0,
					plid: 0,
					portletId: 0,
					title: '',
					url: themeDisplay.getPathMain() + '/portal/update_portlet_title'
				}
			);

			$.ajax(
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

		selectEntityHandler: function(container, selectEventName, disableButton) {
			container = $(container);

			var openingLiferay = Util.getOpener().Liferay;

			var selectorButtons = container.find('.selector-button');

			container.on(
				'click',
				'.selector-button',
				function(event) {
					var currentTarget = $(event.currentTarget);

					if (disableButton !== false) {
						selectorButtons.prop('disabled', false);

						currentTarget.prop('disabled', true);
					}

					var result = Util.getAttributes(currentTarget, 'data-');

					openingLiferay.fire(selectEventName, result);

					Util.getWindow().hide();
				}
			);

			openingLiferay.on(
				'entitySelectionRemoved',
				function(event) {
					selectorButtons.prop('disabled', false);
				}
			);
		},

		selectFolder: function(folderData, namespace) {
			$('#' + namespace + folderData.idString).val(folderData.idValue);

			var name = _.unescape(folderData.nameValue);

			$('#' + namespace + folderData.nameString).val(name);

			var button = $('#' + namespace + 'removeFolderButton');

			Liferay.Util.toggleDisabled(button, false);
		},

		setCursorPosition: function(el, position) {
			var instance = this;

			instance.setSelectionRange(el, position, position);
		},

		setSelectionRange: function(el, selectionStart, selectionEnd) {
			var instance = this;

			el = Util.getDOM(el);

			if (el.jquery) {
				el = el[0];
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

			var shiftKeyCode = keyCode == 16 ? true : false;

			var shiftKey = event.shiftKey ? event.shiftKey : shiftKeyCode;

			var display = 'none';

			if (keyCode >= 65 && keyCode <= 90 && !shiftKey || keyCode >= 97 && keyCode <= 122 && shiftKey) {
				display = '';
			}

			$('#' + span).css('display', display);
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

		toCharCode: _.memoize(
			function(name) {
				return _.invoke(name, 'charCodeAt').join('');
			}
		),

		toggleBoxes: function(checkBoxId, toggleBoxId, displayWhenUnchecked, toggleChildCheckboxes) {
			var checkBox = $('#' + checkBoxId);
			var toggleBox = $('#' + toggleBoxId);

			var checked = checkBox.prop(STR_CHECKED);

			if (displayWhenUnchecked) {
				checked = !checked;
			}

			toggleBox.toggleClass('hide', !checked);

			checkBox.on(
				EVENT_CLICK,
				function() {
					toggleBox.toggleClass('hide');

					if (toggleChildCheckboxes) {
						var childCheckboxes = toggleBox.find('input[type=checkbox]');

						childCheckboxes.prop(STR_CHECKED, checkBox.prop(STR_CHECKED));
					}
				}
			);
		},

		toggleDisabled: function(button, state) {
			button = Util.getDOM(button);

			button = $(button);

			button.each(
				function(index, item) {
					item = $(item);

					item.prop('disabled', state);

					item.toggleClass('disabled', state);
				}
			);
		},

		toggleRadio: function(radioId, showBoxIds, hideBoxIds) {
			var radioButton = $('#' + radioId);

			var showBoxes;

			if (showBoxIds) {
				if (_.isArray(showBoxIds)) {
					showBoxIds = showBoxIds.join(',#');
				}

				showBoxes = $('#' + showBoxIds);

				showBoxes.toggleClass('hide', !radioButton.prop(STR_CHECKED));
			}

			radioButton.on(
				'change',
				function() {
					if (showBoxes) {
						showBoxes.removeClass('hide');
					}

					if (hideBoxIds) {
						if (_.isArray(hideBoxIds)) {
							hideBoxIds = hideBoxIds.join(',#');
						}

						$('#' + hideBoxIds).addClass('hide');
					}
				}
			);
		},

		toggleSearchContainerButton: function(buttonId, searchContainerId, form, ignoreFieldName) {
			$(searchContainerId).on(
				EVENT_CLICK,
				'input[type=checkbox]',
				function() {
					Util.toggleDisabled(buttonId, !Util.listCheckedExcept(form, ignoreFieldName));
				}
			);
		},

		toggleSelectBox: function(selectBoxId, value, toggleBoxId) {
			var selectBox = $('#' + selectBoxId);
			var toggleBox = $('#' + toggleBoxId);

			var dynamicValue = _.isFunction(value);

			var toggle = function() {
				var currentValue = selectBox.val();

				var visible = value == currentValue;

				if (dynamicValue) {
					visible = value(currentValue, value);
				}

				toggleBox.toggleClass('hide', !visible);
			};

			toggle();

			selectBox.on('change', toggle);
		},

		toNumber: function(value) {
			return parseInt(value, 10) || 0;
		},

		_defaultPreviewArticleFn: function(event) {
			var instance = this;

			event.preventDefault();

			Liferay.Util.openWindow(
				{
					cache: false,
					title: event.title,
					uri: event.uri
				}
			);
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

						if (hasErrors) {
							validator.focusInvalidField();
						}
					}
				}
			}

			if (!hasErrors) {
				var action = event.action || form.attr('action');

				var singleSubmit = event.singleSubmit;

				var inputs = form.all('input[type=button], input[type=image], input[type=reset], input[type=submit]');

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

				var actionURL = new A.Url(action);

				var authToken = actionURL.getParameter('p_auth');

				if (authToken) {
					form.append('<input name="p_auth" type="hidden" value="' + authToken + '" />');

					actionURL.removeParameter('p_auth');

					action = actionURL.toString();
				}

				form.attr('action', action);

				form.submit();

				form.attr('target', '');
			}
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

		_ns: _.cached(
			function(namespace, str) {
				if (!_.isUndefined(str) && !(str.lastIndexOf(namespace, 0) === 0)) {
					str = namespace + str;
				}

				return str;
			}
		)
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
			iframeBody.addClass(dialog.iframeConfig.bodyCssClass);

			var detachEventHandles = function() {
				AArray.invoke(eventHandles, 'detach');

				iframeDocument.purge(true);
			};

			var eventHandles = [
				iframeBody.delegate('submit', detachEventHandles, 'form'),

				iframeBody.delegate(
					EVENT_CLICK,
					function(event) {
						dialog.set(
							'visible',
							false,
							event.currentTarget.hasClass('lfr-hide-dialog') ? SRC_HIDE_LINK : null
						);

						detachEventHandles();
					},
					'.btn-cancel,.lfr-hide-dialog'
				)
			];
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

			var ddmURL;

			if (config.basePortletURL) {
				ddmURL = Liferay.PortletURL.createURL(config.basePortletURL);
			}
			else {
				ddmURL = Liferay.PortletURL.createRenderURL();
			}

			ddmURL.setEscapeXML(false);

			ddmURL.setDoAsGroupId(config.doAsGroupId || themeDisplay.getScopeGroupId());

			ddmURL.setParameter('classNameId', config.classNameId);
			ddmURL.setParameter('classPK', config.classPK);
			ddmURL.setParameter('resourceClassNameId', config.resourceClassNameId);
			ddmURL.setParameter('eventName', config.eventName);
			ddmURL.setParameter('groupId', config.groupId);
			ddmURL.setParameter('mode', config.mode);

			if (config.mvcPath) {
				ddmURL.setParameter('mvcPath', config.mvcPath);
			}
			else {
				ddmURL.setParameter('mvcPath', '/view.jsp');
			}

			ddmURL.setParameter('portletResourceNamespace', config.portletResourceNamespace);

			if ('redirect' in config) {
				ddmURL.setParameter('redirect', config.redirect);
			}

			if ('refererPortletName' in config) {
				ddmURL.setParameter('refererPortletName', config.refererPortletName);
			}

			if ('refererWebDAVToken' in config) {
				ddmURL.setParameter('refererWebDAVToken', config.refererWebDAVToken);
			}

			ddmURL.setParameter('scopeTitle', config.title);

			if ('showAncestorScopes' in config) {
				ddmURL.setParameter('showAncestorScopes', config.showAncestorScopes);
			}

			if ('showBackURL' in config) {
				ddmURL.setParameter('showBackURL', config.showBackURL);
			}

			if ('showHeader' in config) {
				ddmURL.setParameter('showHeader', config.showHeader);
			}

			if ('showManageTemplates' in config) {
				ddmURL.setParameter('showManageTemplates', config.showManageTemplates);
			}

			if ('showToolbar' in config) {
				ddmURL.setParameter('showToolbar', config.showToolbar);
			}

			ddmURL.setParameter('structureAvailableFields', config.structureAvailableFields);
			ddmURL.setParameter('templateId', config.templateId);

			ddmURL.setPortletId(Liferay.PortletKeys.DYNAMIC_DATA_MAPPING);
			ddmURL.setWindowState('pop_up');

			config.uri = ddmURL.toString();

			var dialogConfig = config.dialog;

			if (!dialogConfig) {
				dialogConfig = {};

				config.dialog = dialogConfig;
			}

			var eventHandles = [Liferay.once(config.eventName, callback)];

			var detachSelectionOnHideFn = function(event) {
				if (!event.newVal) {
					(new A.EventHandle(eventHandles)).detach();
				}
			};

			Util.openWindow(
				config,
				function(dialogWindow) {
					eventHandles.push(dialogWindow.after(['destroy', 'visibleChange'], detachSelectionOnHideFn));
				}
			);
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
				catch (e) {
					if (Lang.isFunction(onError)) {
						onError(e);
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

			if (obj) {
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
		'selectEntity',
		function(config, callback) {
			var dialog = Util.getWindow(config.id);

			var eventName = config.eventName || config.id;

			var eventHandles = [Liferay.on(eventName, callback)];

			var selectedData = config.selectedData;

			if (selectedData) {
				config.dialog.destroyOnHide = true;
			}

			var detachSelectionOnHideFn = function(event) {
				if (!event.newVal) {
					(new A.EventHandle(eventHandles)).detach();
				}
			};

			var disableSelectedAssets = function(event) {
				if (selectedData && selectedData.length) {
					var currentWindow = event.currentTarget.node.get('contentWindow.document');

					var selectorButtons = currentWindow.all('.lfr-search-container .selector-button');

					A.some(
						selectorButtons,
						function(item, index) {
							var assetEntryId = item.attr('data-assetentryid');

							var assetEntryIndex = selectedData.indexOf(assetEntryId);

							if (assetEntryIndex > -1) {
								item.attr('disabled', true);

								selectedData.splice(assetEntryIndex, 1);
							}

							return !selectedData.length;
						}
					);
				}
			};

			if (dialog) {
				eventHandles.push(dialog.after(['destroy', 'visibleChange'], detachSelectionOnHideFn));

				dialog.show();
			}
			else {
				var destroyDialog = function(event) {
					var dialogId = config.id;

					var dialogWindow = Util.getWindow(dialogId);

					if (dialogWindow && Util.getPortletId(dialogId) === event.portletId) {
						dialogWindow.destroy();

						Liferay.detach('destroyPortlet', destroyDialog);
					}
				};

				Util.openWindow(
					config,
					function(dialogWindow) {
						eventHandles.push(
							dialogWindow.after(['destroy', 'visibleChange'], detachSelectionOnHideFn),
							dialogWindow.iframe.after(['load'], disableSelectedAssets)
						);

						Liferay.on('destroyPortlet', destroyDialog);
					}
				);
			}

		},
		['aui-base', 'liferay-util-window']
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
				var iconHiddenClass = 'icon-eye-close';
				var iconVisibleClass = 'icon-eye-open';
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

				Liferay.fire(
					'toggleControls',
					{
						enabled: Liferay._editControlsState === 'visible'
					}
				);

				trigger.on(
					'tap',
					function(event) {
						if (icon) {
							icon.toggleClass(iconVisibleClass);
							icon.toggleClass(iconHiddenClass);
						}

						docBody.toggleClass(visibleClass);
						docBody.toggleClass(hiddenClass);

						Liferay._editControlsState = docBody.hasClass(visibleClass) ? 'visible' : 'hidden';

						Liferay.Store('com.liferay.frontend.js.web_toggleControls', Liferay._editControlsState);

						Liferay.fire(
							'toggleControls',
							{
								enabled: Liferay._editControlsState === 'visible',
								src: 'ui'
							}
						);
					}
				);
			}
		},
		['event-tap', 'liferay-store']
	);

	Liferay.provide(
		window,
		'submitForm',
		function(form, action, singleSubmit, validate) {
			if (!Util._submitLocked) {
				if (form.jquery) {
					form = form[0];
				}

				Liferay.fire(
					'submitForm',
					{
						action: action,
						form: A.one(form),
						singleSubmit: singleSubmit,
						validate: validate !== false
					}
				);
			}
		},
		['aui-base', 'aui-form-validator', 'aui-url', 'liferay-form']
	);

	Liferay.publish(
		'submitForm',
		{
			defaultFn: Util._defaultSubmitFormFn
		}
	);

	Liferay.publish(
		'previewArticle',
		{
			defaultFn: Util._defaultPreviewArticleFn
		}
	);

	Liferay.provide(
		Util,
		'_openWindowProvider',
		function(config, callback) {
			var dialog = Window.getWindow(config);

			if (Lang.isFunction(callback)) {
				callback(dialog);
			}
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

	Liferay.BREAKPOINTS = {
		PHONE: 768,
		TABLET: 980
	};

	Liferay.STATUS_CODE = {
		BAD_REQUEST: 400,
		INTERNAL_SERVER_ERROR: 500,
		OK: 200,
		SC_DUPLICATE_FILE_EXCEPTION: 490,
		SC_FILE_ANTIVIRUS_EXCEPTION: 494,
		SC_FILE_EXTENSION_EXCEPTION: 491,
		SC_FILE_NAME_EXCEPTION: 492,
		SC_FILE_SIZE_EXCEPTION: 493,
		SC_UPLOAD_REQUEST_SIZE_EXCEPTION: 495
	};

	// 0-200: Theme Developer
	// 200-400: Portlet Developer
	// 400+: Liferay

	Liferay.zIndex = {
		ALERT: 430,
		DOCK: 10,
		DOCK_PARENT: 20,
		DRAG_ITEM: 460,
		DROP_AREA: 440,
		DROP_POSITION: 450,
		MENU: 5000,
		OVERLAY: 1000,
		TOOLTIP: 10000,
		WINDOW: 1200
	};
})(AUI(), AUI.$, AUI._, Liferay);