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

(function(A, $, Liferay) {
	A.use('aui-base-lang');

	var AArray = A.Array;

	var Lang = A.Lang;

	var EVENT_CLICK = 'click';

	var MAP_TOGGLE_STATE = {
		false: {
			cssClass: 'controls-hidden',
			iconCssClass: 'hidden',
			state: 'hidden'
		},
		true: {
			cssClass: 'controls-visible',
			iconCssClass: 'view',
			state: 'visible'
		}
	};

	var REGEX_PORTLET_ID = /^(?:p_p_id)?_(.*)_.*$/;

	var REGEX_SUB = /\{\s*([^|}]+?)\s*(?:\|([^}]*))?\s*\}/g;

	var SRC_HIDE_LINK = {
		src: 'hideLink'
	};

	var STR_CHECKED = 'checked';

	var STR_RIGHT_SQUARE_BRACKET = ']';

	var TPL_LEXICON_ICON =
		'<svg class="lexicon-icon lexicon-icon-{0} {1}" focusable="false" role="image">' +
		'<use data-href="' +
		themeDisplay.getPathThemeImages() +
		'/lexicon/icons.svg#{0}" />' +
		'</svg>';

	var Window = {
		_map: {},

		getById(id) {
			var instance = this;

			return instance._map[id];
		}
	};

	var Util = {
		_defaultSubmitFormFn(event) {
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

				var inputs = form.all(
					'button[type=submit], input[type=button], input[type=image], input[type=reset], input[type=submit]'
				);

				Util.disableFormButtons(inputs, form);

				if (singleSubmit === false) {
					Util._submitLocked = A.later(
						1000,
						Util,
						Util.enableFormButtons,
						[inputs, form]
					);
				} else {
					Util._submitLocked = true;
				}

				var baseURL;
				var queryString;
				var searchParamsIndex = action.indexOf('?');

				if (searchParamsIndex === -1) {
					baseURL = action;
					queryString = '';
				} else {
					baseURL = action.slice(0, searchParamsIndex);
					queryString = action.slice(searchParamsIndex + 1);
				}

				var searchParams = new URLSearchParams(queryString);

				var authToken = searchParams.get('p_auth') || '';

				form.append(
					'<input name="p_auth" type="hidden" value="' +
						authToken +
						'" />'
				);

				if (authToken) {
					searchParams.delete('p_auth');

					action = baseURL + '?' + searchParams.toString();
				}

				form.attr('action', action);

				Util.submitForm(form);

				form.attr('target', '');

				Util._submitLocked = null;
			}
		},

		_getEditableInstance(title) {
			var editable = Util._EDITABLE;

			if (!editable) {
				editable = new A.Editable({
					after: {
						contentTextChange(event) {
							var instance = this;

							if (!event.initial) {
								var title = instance.get('node');

								var portletTitleEditOptions = title.getData(
									'portletTitleEditOptions'
								);

								Util.savePortletTitle({
									doAsUserId:
										portletTitleEditOptions.doAsUserId,
									plid: portletTitleEditOptions.plid,
									portletId:
										portletTitleEditOptions.portletId,
									title: event.newVal
								});
							}
						},
						startEditing() {
							var instance = this;

							var Layout = Liferay.Layout;

							if (Layout) {
								instance._dragListener = Layout.getLayoutHandler().on(
									'drag:start',
									() => {
										instance.fire('save');
									}
								);
							}

							var title = instance.get('node');

							instance._titleListener = title.on(
								'mouseupoutside',
								event => {
									var editable = Util._getEditableInstance(
										title
									);

									if (
										!editable
											.get('boundingBox')
											.contains(event.target)
									) {
										editable.save();
									}
								}
							);
						},
						stopEditing() {
							var instance = this;

							if (instance._dragListener) {
								instance._dragListener.detach();
							}

							if (instance._titleListener) {
								instance._titleListener.detach();
							}
						}
					},
					cssClass: 'lfr-portlet-title-editable',
					node: title
				});

				editable.get('cancelButton').icon = 'times';
				editable.get('saveButton').icon = 'check';

				Util._EDITABLE = editable;
			}

			return editable;
		},

		addInputCancel() {
			A.use('aui-button-search-cancel', A => {
				new A.ButtonSearchCancel({
					trigger:
						'input[type=password], input[type=search], input.clearable, input.search-query'
				});
			});

			Util.addInputCancel = function() {};
		},

		addParams(params, url) {
			if (typeof params === 'object') {
				var paramKeys = Object.keys(params);

				params = paramKeys
					.map(key => {
						return (
							encodeURIComponent(key) +
							'=' +
							encodeURIComponent(params[key])
						);
					})
					.join('&');
			} else {
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
				} else {
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

		checkAll(form, name, allBox, selectClassName) {
			if (form) {
				form = Util.getDOM(form);

				if (typeof form === 'string') {
					form = document.querySelector(form);
				}

				allBox = Util.getDOM(allBox);

				if (typeof allBox === 'string') {
					allBox = document.querySelector(allBox);
				}

				let selector;

				if (Array.isArray(name)) {
					selector =
						'input[name=' +
						name.join('], input[name=') +
						STR_RIGHT_SQUARE_BRACKET;
				} else {
					selector = 'input[name=' + name + STR_RIGHT_SQUARE_BRACKET;
				}

				const allBoxChecked = allBox.checked;

				const uploadedItems = Array.from(
					form.querySelectorAll(selector)
				);

				uploadedItems.forEach(item => {
					if (!item.disabled) {
						item.checked = allBoxChecked;
					}
				});

				if (selectClassName) {
					const selectItem = form.querySelector(selectClassName);

					if (allBoxChecked) {
						selectItem.classList.add('info');
					} else {
						selectItem.classList.remove('info');
					}
				}
			}
		},

		checkAllBox(form, name, allBox) {
			let totalOn = 0;

			if (form) {
				form = Util.getDOM(form);

				if (typeof form === 'string') {
					form = document.querySelector(form);
				}

				allBox = Util.getDOM(allBox);

				if (typeof allBox === 'string') {
					allBox =
						document.querySelector(allBox) ||
						form.querySelector(`input[name="${allBox}"]`);
				}

				const inputs = Array.from(
					form.querySelectorAll('input[type=checkbox]')
				);

				if (!Array.isArray(name)) {
					name = [name];
				}

				let totalBoxes = 0;

				inputs.forEach(input => {
					if (
						input.id !== allBox.id ||
						(input.id !== allBox.name &&
							name.indexOf(input.name) > -1)
					) {
						totalBoxes++;

						if (input.checked) {
							totalOn++;
						}
					}
				});

				allBox.checked = totalBoxes === totalOn;
			}

			return totalOn;
		},

		checkTab(box) {
			if (document.all && window.event.keyCode == 9) {
				box.selection = document.selection.createRange();

				setTimeout(() => {
					Util.processTab(box.id);
				}, 0);
			}
		},

		disableElements(el) {
			const currentElement = Util.getElement(el);

			if (currentElement) {
				var children = currentElement.getElementsByTagName('*');

				var emptyFnFalse = function() {
					return false;
				};

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
				}
			}
		},

		disableEsc() {
			if (document.all && window.event.keyCode == 27) {
				window.event.returnValue = false;
			}
		},

		disableFormButtons(inputs, form) {
			inputs.attr('disabled', true);
			inputs.setStyle('opacity', 0.5);

			if (A.UA.gecko) {
				A.getWin().on('unload', () => {
					inputs.attr('disabled', false);
				});
			} else if (A.UA.safari) {
				A.use('node-event-html5', A => {
					A.getWin().on('pagehide', () => {
						Util.enableFormButtons(inputs, form);
					});
				});
			}
		},

		disableToggleBoxes(checkBoxId, toggleBoxId, checkDisabled) {
			const checkBox = document.getElementById(checkBoxId);
			const toggleBox = document.getElementById(toggleBoxId);

			if (checkBox && toggleBox) {
				toggleBox.disabled = checkDisabled && checkBox.checked;

				checkBox.addEventListener(EVENT_CLICK, () => {
					toggleBox.disabled = !toggleBox.disabled;
				});
			}
		},

		enableFormButtons(inputs) {
			Util._submitLocked = null;

			Util.toggleDisabled(inputs, false);
		},

		escapeCDATA(str) {
			return str.replace(/<!\[CDATA\[|\]\]>/gi, match => {
				var str = '';

				if (match == ']]>') {
					str = ']]&gt;';
				} else if (match == '<![CDATA[') {
					str = '&lt;![CDATA[';
				}

				return str;
			});
		},

		focusFormField(el) {
			let interacting = false;

			el = Util.getElement(el);

			const handler = () => {
				interacting = true;

				document.body.removeEventListener('click', handler);
			};

			document.body.addEventListener('click', handler);

			if (!interacting && Util.inBrowserView(el)) {
				const getDisabledParents = function(el) {
					let result = [];

					if (el.parentElement) {
						if (el.parentElement.getAttribute('disabled')) {
							result = [el.parentElement];
						}

						result = [
							...result,
							...getDisabledParents(el.parentElement)
						];
					}

					return result;
				};

				const disabledParents = getDisabledParents(el);

				const focusable =
					!el.getAttribute('disabled') &&
					el.offsetWidth > 0 &&
					el.offsetHeight > 0 &&
					!disabledParents.length;

				const form = el.closest('form');

				if (!form || focusable) {
					el.focus();
				} else if (form) {
					const portletName = form.getAttribute('data-fm-namespace');

					const formReadyEventName = portletName + 'formReady';

					const formReadyHandler = event => {
						const elFormName = form.getAttribute('name');

						const formName = event.formName;

						if (elFormName === formName) {
							el.focus();

							Liferay.detach(
								formReadyEventName,
								formReadyHandler
							);
						}
					};

					Liferay.on(formReadyEventName, formReadyHandler);
				}
			}
		},

		forcePost(link) {
			const currentElement = Util.getElement(link);

			if (currentElement) {
				const url = currentElement.getAttribute('href');

				const newWindow =
					currentElement.getAttribute('target') == '_blank';

				const hrefFm = document.hrefFm;

				if (newWindow) {
					hrefFm.setAttribute('target', '_blank');
				}

				submitForm(hrefFm, url, !newWindow);

				Util._submitLocked = null;
			}
		},

		getAttributes(el, attributeGetter) {
			var result = null;

			if (el) {
				el = Util.getDOM(el);

				if (el.jquery) {
					el = el[0];
				}

				result = {};

				var getterFn = this.isFunction(attributeGetter);
				var getterString = typeof attributeGetter === 'string';

				var attrs = el.attributes;
				var length = attrs.length;

				while (length--) {
					var attr = attrs[length];
					var name = attr.nodeName.toLowerCase();
					var value = attr.nodeValue;

					if (getterString) {
						if (name.indexOf(attributeGetter) === 0) {
							name = name.substr(attributeGetter.length);
						} else {
							continue;
						}
					} else if (getterFn) {
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

		getColumnId(str) {
			var columnId = str.replace(/layout-column_/, '');

			return columnId;
		},

		getDOM(el) {
			if (el._node || el._nodes) {
				el = el.getDOM();
			}

			return el;
		},

		getElement(el) {
			const currentElement = Util.getDOM(el);

			return typeof currentElement === 'string'
				? document.querySelector(currentElement)
				: currentElement.length
				? currentElement[0]
				: currentElement;
		},

		getGeolocation(success, fallback, options) {
			if (success && navigator.geolocation) {
				navigator.geolocation.getCurrentPosition(
					position => {
						success(
							position.coords.latitude,
							position.coords.longitude,
							position
						);
					},
					fallback,
					options
				);
			} else if (fallback) {
				fallback();
			}
		},

		getLexiconIcon(icon, cssClass) {
			var instance = this;

			const tempElement = document.createElement('div');

			tempElement.innerHTML = instance.getLexiconIconTpl(icon, cssClass);

			return tempElement.firstChild;
		},

		getLexiconIconTpl(icon, cssClass) {
			return Liferay.Util.sub(TPL_LEXICON_ICON, icon, cssClass || '');
		},

		getOpener() {
			var openingWindow = Window._opener;

			if (!openingWindow) {
				var topUtil = Liferay.Util.getTop().Liferay.Util;

				var windowName = Liferay.Util.getWindowName();

				var dialog = topUtil.Window.getById(windowName);

				if (dialog) {
					openingWindow = dialog._opener;

					Window._opener = openingWindow;
				}
			}

			return openingWindow || window.opener || window.parent;
		},

		getPortletId(portletId) {
			return String(portletId).replace(REGEX_PORTLET_ID, '$1');
		},

		getTop() {
			var topWindow = Util._topWindow;

			if (!topWindow) {
				var parentWindow = window.parent;

				var parentThemeDisplay;

				while (parentWindow != window) {
					try {
						if (typeof parentWindow.location.href == 'undefined') {
							break;
						}

						parentThemeDisplay = parentWindow.themeDisplay;
					} catch (e) {
						break;
					}

					if (
						!parentThemeDisplay ||
						window.name === 'simulationDeviceIframe'
					) {
						break;
					} else if (
						!parentThemeDisplay.isStatePopUp() ||
						parentWindow == parentWindow.parent
					) {
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

		getURLWithSessionId(url) {
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

		getWindow(id) {
			if (!id) {
				id = Util.getWindowName();
			}

			return Util.getTop().Liferay.Util.Window.getById(id);
		},

		getWindowName() {
			return window.name || Window._name || '';
		},

		getWindowWidth() {
			return window.innerWidth > 0 ? window.innerWidth : screen.width;
		},

		inBrowserView(node, win, nodeRegion) {
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

				viewable =
					nodeRegion.bottom <= winRegion.bottom &&
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

						viewable = Util.inBrowserView(
							node,
							win.prop('parent'),
							nodeRegion
						);
					}
				}
			}

			return viewable;
		},

		isFunction(val) {
			return typeof val === 'function';
		},

		isPhone() {
			var instance = this;

			return instance.getWindowWidth() < Liferay.BREAKPOINTS.PHONE;
		},

		isTablet() {
			var instance = this;

			return instance.getWindowWidth() < Liferay.BREAKPOINTS.TABLET;
		},

		listCheckboxesExcept(form, except, name, checked) {
			form = Util.getDOM(form);

			if (typeof form === 'string') {
				form = document.querySelector(form);
			}

			let selector = 'input[type=checkbox]';

			if (name) {
				selector += '[name=' + name + ']';
			}

			const checkboxes = Array.from(form.querySelectorAll(selector));

			return checkboxes
				.reduce((prev, item) => {
					const value = item.value;

					if (
						value &&
						item.name !== except &&
						item.checked === checked &&
						!item.disabled
					) {
						prev.push(value);
					}

					return prev;
				}, [])
				.join();
		},

		listCheckedExcept(form, except, name) {
			return Util.listCheckboxesExcept(form, except, name, true);
		},

		listSelect(select, delimeter) {
			select = Util.getDOM(select);

			return $(select)
				.find('option')
				.toArray()
				.reduce((prev, item) => {
					var val = $(item).val();

					if (val) {
						prev.push(val);
					}

					return prev;
				}, [])
				.join(delimeter || ',');
		},

		listUncheckedExcept(form, except, name) {
			return Util.listCheckboxesExcept(form, except, name, false);
		},

		normalizeFriendlyURL(text) {
			var newText = text.replace(/[^a-zA-Z0-9_-]/g, '-');

			if (newText[0] === '-') {
				newText = newText.replace(/^-+/, '');
			}

			newText = newText.replace(/--+/g, '-');

			return newText.toLowerCase();
		},

		openInDialog(event, config) {
			event.preventDefault();

			var currentTarget = Util.getDOM(event.currentTarget);

			currentTarget = $(currentTarget);

			config = A.mix(A.merge({}, currentTarget.data()), config);

			if (!config.uri) {
				config.uri =
					currentTarget.data('href') || currentTarget.attr('href');
			}

			if (!config.title) {
				config.title = currentTarget.attr('title');
			}

			Liferay.Util.openWindow(config);
		},

		openWindow(config, callback) {
			config.openingWindow = window;

			var top = Util.getTop();

			var topUtil = top.Liferay.Util;

			topUtil._openWindowProvider(config, callback);
		},

		processTab(id) {
			document.all[id].selection.text = String.fromCharCode(9);
			document.all[id].focus();
		},

		randomInt() {
			return Math.ceil(Math.random() * new Date().getTime());
		},

		removeEntitySelection(
			entityIdString,
			entityNameString,
			removeEntityButton,
			namespace
		) {
			$('#' + namespace + entityIdString).val(0);

			$('#' + namespace + entityNameString).val('');

			Liferay.Util.toggleDisabled(removeEntityButton, true);

			Liferay.fire('entitySelectionRemoved');
		},

		reorder(box, down) {
			box = Util.getDOM(box);

			box = $(box);

			if (box.prop('selectedIndex') == -1) {
				box.prop('selectedIndex', 0);
			} else {
				var selectedItems = box.find('option:selected');

				if (down) {
					selectedItems
						.get()
						.reverse()
						.forEach(item => {
							item = $(item);

							var itemIndex = item.prop('index');

							var lastIndex = box.find('option').length - 1;

							if (itemIndex === lastIndex) {
								box.prepend(item);
							} else {
								item.insertAfter(item.next());
							}
						});
				} else {
					selectedItems.get().forEach(item => {
						item = $(item);

						var itemIndex = item.prop('index');

						if (itemIndex === 0) {
							box.append(item);
						} else {
							item.insertBefore(item.prev());
						}
					});
				}
			}
		},

		rowCheckerCheckAllBox(
			ancestorTable,
			ancestorRow,
			checkboxesIds,
			checkboxAllIds,
			cssClass
		) {
			Util.checkAllBox(ancestorTable, checkboxesIds, checkboxAllIds);

			if (ancestorRow) {
				ancestorRow.toggleClass(cssClass);
			}
		},

		savePortletTitle(params) {
			params = {
				doAsUserId: 0,
				plid: 0,
				portletId: 0,
				title: '',
				url:
					themeDisplay.getPathMain() + '/portal/update_portlet_title',
				...params
			};

			var data = {
				doAsUserId: params.doAsUserId,
				p_auth: Liferay.authToken,
				p_l_id: params.plid,
				portletId: params.portletId,
				title: params.title
			};

			Liferay.Util.fetch(params.url, {
				body: Liferay.Util.objectToFormData(data),
				method: 'POST'
			});
		},

		selectEntityHandler(container, selectEventName, disableButton) {
			container = $(container);

			var openingLiferay = Util.getOpener().Liferay;

			var selectorButtons = container.find('.selector-button');

			container.on('click', '.selector-button', event => {
				var target = $(event.target);

				if (!target.attr('data-prevent-selection')) {
					var currentTarget = $(event.currentTarget);

					var confirmSelection =
						currentTarget.attr('data-confirm-selection') === 'true';
					var confirmSelectionMessage = currentTarget.attr(
						'data-confirm-selection-message'
					);

					if (!confirmSelection || confirm(confirmSelectionMessage)) {
						if (disableButton !== false) {
							selectorButtons.prop('disabled', false);

							currentTarget.prop('disabled', true);
						}

						var result = Util.getAttributes(currentTarget, 'data-');

						openingLiferay.fire(selectEventName, result);

						Util.getWindow().hide();
					}
				}
			});

			openingLiferay.on('entitySelectionRemoved', () => {
				selectorButtons.prop('disabled', false);
			});
		},

		selectFolder(folderData, namespace) {
			$('#' + namespace + folderData.idString).val(folderData.idValue);

			var name = Liferay.Util.unescape(folderData.nameValue);

			$('#' + namespace + folderData.nameString).val(name);

			var button = $('#' + namespace + 'removeFolderButton');

			Liferay.Util.toggleDisabled(button, false);
		},

		setCursorPosition(el, position) {
			var instance = this;

			instance.setSelectionRange(el, position, position);
		},

		setSelectionRange(el, selectionStart, selectionEnd) {
			el = Util.getDOM(el);

			if (el.jquery) {
				el = el[0];
			}

			if (el.setSelectionRange) {
				el.focus();

				el.setSelectionRange(selectionStart, selectionEnd);
			} else if (el.createTextRange) {
				var textRange = el.createTextRange();

				textRange.collapse(true);

				textRange.moveEnd('character', selectionEnd);
				textRange.moveEnd('character', selectionStart);

				textRange.select();
			}
		},

		showCapsLock(event, span) {
			var keyCode = event.keyCode ? event.keyCode : event.which;

			var shiftKeyCode = keyCode === 16;

			var shiftKey = event.shiftKey ? event.shiftKey : shiftKeyCode;

			var display = 'none';

			if (
				(keyCode >= 65 && keyCode <= 90 && !shiftKey) ||
				(keyCode >= 97 && keyCode <= 122 && shiftKey)
			) {
				display = '';
			}

			$('#' + span).css('display', display);
		},

		sortByAscending(a, b) {
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

		sub(string, data) {
			if (
				arguments.length > 2 ||
				(typeof data !== 'object' && typeof data !== 'function')
			) {
				data = Array.prototype.slice.call(arguments, 1);
			}

			return string.replace
				? string.replace(REGEX_SUB, (match, key) => {
						return data[key] === undefined ? match : data[key];
				  })
				: string;
		},

		submitCountdown: 0,

		submitForm(form) {
			form.submit();
		},

		toNumber(value) {
			return parseInt(value, 10) || 0;
		},

		toggleBoxes(
			checkBoxId,
			toggleBoxId,
			displayWhenUnchecked,
			toggleChildCheckboxes
		) {
			var checkBox = $('#' + checkBoxId);
			var toggleBox = $('#' + toggleBoxId);

			var checked = checkBox.prop(STR_CHECKED);

			if (displayWhenUnchecked) {
				checked = !checked;
			}

			toggleBox.toggleClass('hide', !checked);

			checkBox.on(EVENT_CLICK, () => {
				toggleBox.toggleClass('hide');

				if (toggleChildCheckboxes) {
					var childCheckboxes = toggleBox.find(
						'input[type=checkbox]'
					);

					childCheckboxes.prop(
						STR_CHECKED,
						checkBox.prop(STR_CHECKED)
					);
				}
			});
		},

		toggleDisabled(button, state) {
			button = Util.getDOM(button);

			button = $(button);

			button.each((index, item) => {
				item = $(item);

				item.prop('disabled', state);

				item.toggleClass('disabled', state);
			});
		},

		toggleRadio(radioId, showBoxIds, hideBoxIds) {
			const radioButton = document.getElementById(radioId);

			if (radioButton) {
				let showBoxes;

				if (showBoxIds) {
					if (Array.isArray(showBoxIds)) {
						showBoxIds = showBoxIds.join(',#');
					}

					showBoxes = document.querySelectorAll('#' + showBoxIds);

					showBoxes.forEach(showBox => {
						if (radioButton.checked) {
							showBox.classList.remove('hide');
						} else {
							showBox.classList.add('hide');
						}
					});
				}

				radioButton.addEventListener('change', () => {
					if (showBoxes) {
						showBoxes.forEach(showBox => {
							showBox.classList.remove('hide');
						});
					}

					if (hideBoxIds) {
						if (Array.isArray(hideBoxIds)) {
							hideBoxIds = hideBoxIds.join(',#');
						}

						const hideBoxes = document.querySelectorAll(
							'#' + hideBoxIds
						);

						hideBoxes.forEach(hideBox => {
							hideBox.classList.add('hide');
						});
					}
				});
			}
		},

		/*
		 * @deprecated As of Athanasius (7.3.x), with no direct replacement
		 */
		toggleSearchContainerButton(
			buttonId,
			searchContainerId,
			form,
			ignoreFieldName
		) {
			A.one(searchContainerId).delegate(
				EVENT_CLICK,
				() => {
					Util.toggleDisabled(
						buttonId,
						!Util.listCheckedExcept(form, ignoreFieldName)
					);
				},
				'input[type=checkbox]'
			);
		},

		toggleSelectBox(selectBoxId, value, toggleBoxId) {
			var selectBox = document.getElementById(selectBoxId);
			var toggleBox = document.getElementById(toggleBoxId);

			if (selectBox && toggleBox) {
				var dynamicValue = this.isFunction(value);

				var toggle = function() {
					var currentValue = selectBox.value;

					var visible = value == currentValue;

					if (dynamicValue) {
						visible = value(currentValue, value);
					}

					if (visible) {
						toggleBox.classList.remove('hide');
					} else {
						toggleBox.classList.add('hide');
					}
				};

				toggle();

				selectBox.addEventListener('change', toggle);
			}
		}
	};

	Liferay.provide(
		Util,
		'afterIframeLoaded',
		event => {
			var nodeInstances = A.Node._instances;

			var docEl = event.doc;

			var docUID = docEl._yuid;

			if (docUID in nodeInstances) {
				delete nodeInstances[docUID];
			}

			var iframeDocument = A.one(docEl);

			var iframeBody = iframeDocument.one('body');

			var dialog = event.dialog;

			var lfrFormContent = iframeBody.one('.lfr-form-content');

			iframeBody.addClass('dialog-iframe-popup');

			if (
				lfrFormContent &&
				iframeBody.one('.button-holder.dialog-footer')
			) {
				iframeBody.addClass('dialog-with-footer');

				var stagingAlert = iframeBody.one(
					'.portlet-body > .lfr-portlet-message-staging-alert'
				);

				if (stagingAlert) {
					stagingAlert.remove();

					lfrFormContent.prepend(stagingAlert);
				}
			}

			iframeBody.addClass(dialog.iframeConfig.bodyCssClass);

			event.win.focus();

			var detachEventHandles = function() {
				AArray.invoke(eventHandles, 'detach');

				iframeDocument.purge(true);
			};

			var eventHandles = [
				iframeBody.delegate('submit', detachEventHandles, 'form'),

				iframeBody.delegate(
					EVENT_CLICK,
					event => {
						dialog.set(
							'visible',
							false,
							event.currentTarget.hasClass('lfr-hide-dialog')
								? SRC_HIDE_LINK
								: null
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
		(config, callback) => {
			var defaultValues = {
				eventName: 'selectStructure'
			};

			config = A.merge(defaultValues, config);

			var params = {
				classNameId: config.classNameId,
				classPK: config.classPK,
				doAsGroupId:
					config.doAsGroupId || themeDisplay.getScopeGroupId(),
				eventName: config.eventName,
				groupId: config.groupId,
				mvcPath: config.mvcPath || '/view.jsp',
				p_p_state: 'pop_up',
				portletResourceNamespace: config.portletResourceNamespace,
				resourceClassNameId: config.resourceClassNameId,
				scopeTitle: config.title,
				structureAvailableFields: config.structureAvailableFields,
				templateId: config.templateId
			};

			if ('mode' in config) {
				params.mode = config.mode;
			}

			if ('navigationStartsOn' in config) {
				params.navigationStartsOn = config.navigationStartsOn;
			}

			if ('redirect' in config) {
				params.redirect = config.redirect;
			}

			if ('refererPortletName' in config) {
				params.refererPortletName = config.refererPortletName;
			}

			if ('refererWebDAVToken' in config) {
				params.refererWebDAVToken = config.refererWebDAVToken;
			}

			if ('searchRestriction' in config) {
				params.searchRestriction = config.searchRestriction;
				params.searchRestrictionClassNameId =
					config.searchRestrictionClassNameId;
				params.searchRestrictionClassPK =
					config.searchRestrictionClassPK;
			}

			if ('showAncestorScopes' in config) {
				params.showAncestorScopes = config.showAncestorScopes;
			}

			if ('showBackURL' in config) {
				params.showBackURL = config.showBackURL;
			}

			if ('showCacheableInput' in config) {
				params.showCacheableInput = config.showCacheableInput;
			}

			if ('showHeader' in config) {
				params.showHeader = config.showHeader;
			}

			if ('showManageTemplates' in config) {
				params.showManageTemplates = config.showManageTemplates;
			}

			var url = Liferay.Util.PortletURL.createRenderURL(
				config.basePortletURL,
				params
			);

			config.uri = url.toString();

			var dialogConfig = config.dialog;

			if (!dialogConfig) {
				dialogConfig = {};

				config.dialog = dialogConfig;
			}

			var eventHandles = [];

			if (callback) {
				eventHandles.push(Liferay.once(config.eventName, callback));
			}

			var detachSelectionOnHideFn = function(event) {
				Liferay.fire(config.eventName);

				if (!event.newVal) {
					new A.EventHandle(eventHandles).detach();
				}
			};

			Util.openWindow(config, dialogWindow => {
				eventHandles.push(
					dialogWindow.after(
						['destroy', 'visibleChange'],
						detachSelectionOnHideFn
					)
				);
			});
		},
		['aui-base']
	);

	Liferay.provide(
		Util,
		'openDocument',
		(webDavUrl, onSuccess, onError) => {
			if (A.UA.ie) {
				try {
					var executor = new A.config.win.ActiveXObject(
						'SharePoint.OpenDocuments'
					);

					executor.EditDocument(webDavUrl);

					if (Lang.isFunction(onSuccess)) {
						onSuccess();
					}
				} catch (e) {
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
		options => {
			var obj = options.obj;

			A.Event.defineOutside('mouseup');

			if (obj) {
				var title = obj.one('.portlet-title-text');

				if (title && !title.hasClass('not-editable')) {
					title.addClass('portlet-title-editable');

					title.on(EVENT_CLICK, event => {
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

						if (!rendered) {
							var defaultIconsTpl =
								A.ToolbarRenderer.prototype.TEMPLATES.icon;

							A.ToolbarRenderer.prototype.TEMPLATES.icon = Liferay.Util.getLexiconIconTpl(
								'{cssClass}'
							);

							editable._comboBox.icons.destroy();
							editable._comboBox._renderIcons();

							A.ToolbarRenderer.prototype.TEMPLATES.icon = defaultIconsTpl;
						}
					});

					title.setData('portletTitleEditOptions', options);
				}
			}
		},
		['aui-editable-deprecated', 'event-outside']
	);

	Liferay.provide(
		Util,
		'editEntity',
		(config, callback) => {
			var dialog = Util.getWindow(config.id);

			var eventName = config.eventName || config.id;

			var eventHandles = [Liferay.on(eventName, callback)];

			var detachSelectionOnHideFn = function(event) {
				if (!event.newVal) {
					new A.EventHandle(eventHandles).detach();
				}
			};

			if (dialog) {
				eventHandles.push(
					dialog.after(
						['destroy', 'visibleChange'],
						detachSelectionOnHideFn
					)
				);

				dialog.show();
			} else {
				var destroyDialog = function(event) {
					var dialogId = config.id;

					var dialogWindow = Util.getWindow(dialogId);

					if (
						dialogWindow &&
						Util.getPortletId(dialogId) === event.portletId
					) {
						dialogWindow.destroy();

						Liferay.detach('destroyPortlet', destroyDialog);
					}
				};

				var editURL = new Liferay.Util.PortletURL.createPortletURL(
					config.uri,
					A.merge(
						{
							eventName
						},
						config.urlParams
					)
				);

				config.uri = editURL.toString();

				config.dialogIframe = A.merge(
					{
						bodyCssClass: 'dialog-with-footer'
					},
					config.dialogIframe || {}
				);

				Util.openWindow(config, dialogWindow => {
					eventHandles.push(
						dialogWindow.after(
							['destroy', 'visibleChange'],
							detachSelectionOnHideFn
						)
					);

					Liferay.on('destroyPortlet', destroyDialog);
				});
			}
		},
		['aui-base', 'liferay-util-window']
	);

	Liferay.provide(
		Util,
		'selectEntity',
		(config, callback) => {
			var dialog = Util.getWindow(config.id);

			var eventName = config.eventName || config.id;

			var eventHandles = [Liferay.on(eventName, callback)];

			var selectedData = config.selectedData;

			if (selectedData) {
				config.dialog.destroyOnHide = true;
			}

			var detachSelectionOnHideFn = function(event) {
				if (!event.newVal) {
					new A.EventHandle(eventHandles).detach();
				}
			};

			var disableSelectedAssets = function(event) {
				if (selectedData && selectedData.length) {
					var currentWindow = event.currentTarget.node.get(
						'contentWindow.document'
					);

					var selectorButtons = currentWindow.all(
						'.lfr-search-container-wrapper .selector-button'
					);

					A.some(selectorButtons, item => {
						var assetEntryId =
							item.attr('data-entityid') ||
							item.attr('data-entityname');

						var assetEntryIndex = selectedData.indexOf(
							assetEntryId
						);

						if (assetEntryIndex > -1) {
							item.attr('data-prevent-selection', true);
							item.attr('disabled', true);

							selectedData.splice(assetEntryIndex, 1);
						}

						return !selectedData.length;
					});
				}
			};

			if (dialog) {
				eventHandles.push(
					dialog.after(
						['destroy', 'visibleChange'],
						detachSelectionOnHideFn
					)
				);

				dialog.show();
			} else {
				var destroyDialog = function(event) {
					var dialogId = config.id;

					var dialogWindow = Util.getWindow(dialogId);

					if (
						dialogWindow &&
						Util.getPortletId(dialogId) === event.portletId
					) {
						dialogWindow.destroy();

						Liferay.detach('destroyPortlet', destroyDialog);
					}
				};

				Util.openWindow(config, dialogWindow => {
					eventHandles.push(
						dialogWindow.after(
							['destroy', 'visibleChange'],
							detachSelectionOnHideFn
						),
						dialogWindow.iframe.after(
							['load'],
							disableSelectedAssets
						)
					);

					Liferay.on('destroyPortlet', destroyDialog);
				});
			}
		},
		['aui-base', 'liferay-util-window']
	);

	Liferay.provide(
		Util,
		'toggleControls',
		node => {
			var docBody = A.getBody();

			node = node || docBody;

			var trigger = node.one('.toggle-controls');

			if (trigger) {
				var controlsVisible = Liferay._editControlsState === 'visible';

				var currentState = MAP_TOGGLE_STATE[controlsVisible];

				var icon = trigger.one('.lexicon-icon');

				if (icon) {
					currentState.icon = icon;
				}

				docBody.addClass(currentState.cssClass);

				Liferay.fire('toggleControls', {
					enabled: controlsVisible
				});

				trigger.on('tap', () => {
					controlsVisible = !controlsVisible;

					var prevState = currentState;

					currentState = MAP_TOGGLE_STATE[controlsVisible];

					docBody.toggleClass(prevState.cssClass);
					docBody.toggleClass(currentState.cssClass);

					var editControlsIconClass = currentState.iconCssClass;
					var editControlsState = currentState.state;

					if (icon) {
						var newIcon = currentState.icon;

						if (!newIcon) {
							newIcon = Util.getLexiconIcon(
								editControlsIconClass
							);

							newIcon = A.one(newIcon);

							currentState.icon = newIcon;
						}

						icon.replace(newIcon);

						icon = newIcon;
					}

					Liferay._editControlsState = editControlsState;

					Liferay.Util.Session.set(
						'com.liferay.frontend.js.web_toggleControls',
						editControlsState
					);

					Liferay.fire('toggleControls', {
						enabled: controlsVisible,
						src: 'ui'
					});
				});
			}
		},
		['event-tap']
	);

	Liferay.provide(
		window,
		'submitForm',
		(form, action, singleSubmit, validate) => {
			if (!Util._submitLocked) {
				if (form.jquery) {
					form = form[0];
				}

				Liferay.fire('submitForm', {
					action,
					form: A.one(form),
					singleSubmit,
					validate: validate !== false
				});
			}
		},
		['aui-base', 'aui-form-validator', 'aui-url', 'liferay-form']
	);

	Liferay.publish('submitForm', {
		defaultFn: Util._defaultSubmitFormFn
	});

	Liferay.provide(
		Util,
		'_openWindowProvider',
		(config, callback) => {
			var dialog = Window.getWindow(config);

			if (Lang.isFunction(callback)) {
				callback(dialog);
			}
		},
		['liferay-util-window']
	);

	Liferay.after('closeWindow', event => {
		var id = event.id;

		var dialog = Liferay.Util.getTop().Liferay.Util.Window.getById(id);

		if (dialog && dialog.iframe) {
			var dialogWindow = dialog.iframe.node.get('contentWindow').getDOM();

			var openingWindow = dialogWindow.Liferay.Util.getOpener();
			var redirect = event.redirect;

			if (redirect) {
				openingWindow.Liferay.Util.navigate(redirect);
			} else {
				var refresh = event.refresh;

				if (refresh && openingWindow) {
					var data;

					if (!event.portletAjaxable) {
						data = {
							portletAjaxable: false
						};
					}

					openingWindow.Liferay.Portlet.refresh(
						'#p_p_id_' + refresh + '_',
						data
					);
				}
			}

			dialog.hide();
		}
	});

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
		SC_FILE_CUSTOM_EXCEPTION: 499,
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
		POPOVER: 1600,
		TOOLTIP: 10000,
		WINDOW: 1200
	};
})(AUI(), AUI.$, Liferay);
