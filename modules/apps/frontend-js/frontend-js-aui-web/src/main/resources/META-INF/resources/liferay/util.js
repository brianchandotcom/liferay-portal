/* eslint-disable @liferay/aui/no-one */
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

(function (A) {
	A.use('aui-base-lang');

	const Lang = A.Lang;

	const EVENT_CLICK = 'click';

	const SRC_HIDE_LINK = {
		src: 'hideLink',
	};

	const STR_RIGHT_SQUARE_BRACKET = ']';

	const Window = {
		_map: {},

		getById(id) {
			const instance = this;

			return instance._map[id];
		},
	};

	const Util = {
		_getEditableInstance(title) {
			let editable = Util._EDITABLE;

			if (!editable) {
				editable = new A.Editable({
					after: {
						contentTextChange(event) {
							const instance = this;

							if (!event.initial) {
								const title = instance.get('node');

								const portletTitleEditOptions = title.getData(
									'portletTitleEditOptions'
								);

								Util.savePortletTitle({
									doAsUserId:
										portletTitleEditOptions.doAsUserId,
									plid: portletTitleEditOptions.plid,
									portletId:
										portletTitleEditOptions.portletId,
									title: event.newVal,
								});
							}
						},
						startEditing() {
							const instance = this;

							const Layout = Liferay.Layout;

							if (Layout) {
								instance._dragListener = Layout.getLayoutHandler().on(
									'drag:start',
									() => {
										instance.fire('save');
									}
								);
							}

							const title = instance.get('node');

							instance._titleListener = title.on(
								'mouseupoutside',
								(event) => {
									const editable = Util._getEditableInstance(
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
							const instance = this;

							if (instance._dragListener) {
								instance._dragListener.detach();
							}

							if (instance._titleListener) {
								instance._titleListener.detach();
							}
						},
					},
					cssClass: 'lfr-portlet-title-editable',
					node: title,
				});

				editable.get('cancelButton').icon = 'times';
				editable.get('saveButton').icon = 'check';

				Util._EDITABLE = editable;
			}

			return editable;
		},

		/**
		 * @deprecated As of Cavanaugh (7.4.x), with no direct replacement
		 */
		addInputCancel() {
			A.use('aui-button-search-cancel', (A) => {
				new A.ButtonSearchCancel({
					trigger:
						'input[type=password], input[type=search], input.clearable, input.search-query',
				});
			});

			Util.addInputCancel = function () {};
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
				}
				else {
					selector = 'input[name=' + name + STR_RIGHT_SQUARE_BRACKET;
				}

				const allBoxChecked = allBox.checked;

				const uploadedItems = Array.from(
					form.querySelectorAll(selector)
				);

				uploadedItems.forEach((item) => {
					if (!item.disabled) {
						item.checked = allBoxChecked;
					}
				});

				if (selectClassName) {
					const selectItem = form.querySelector(selectClassName);

					if (allBoxChecked) {
						selectItem.classList.add('info');
					}
					else {
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

				inputs.forEach((input) => {
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

		/**
		 * @deprecated As of Cavanaugh (7.4.x), with no direct replacement
		 */
		checkTab(box) {
			if (document.all && Number(window.event.keyCode) === 9) {
				box.selection = document.selection.createRange();

				setTimeout(() => {
					Util.processTab(box.id);
				}, 0);
			}
		},

		/**
		 * @deprecated As of Cavanaugh (7.4.x), with no direct replacement
		 */
		disableElements(element) {
			const currentElement = Util.getElement(element);

			if (currentElement) {
				const children = currentElement.getElementsByTagName('*');

				const emptyFnFalse = function () {
					return false;
				};

				for (let i = children.length - 1; i >= 0; i--) {
					const item = children[i];

					item.style.cursor = 'default';

					item.onclick = emptyFnFalse;
					item.onmouseover = emptyFnFalse;
					item.onmouseout = emptyFnFalse;
					item.onmouseenter = emptyFnFalse;
					item.onmouseleave = emptyFnFalse;

					item.action = '';
					item.disabled = true;
					item.href = 'javascript:void(0);';
					item.onsubmit = emptyFnFalse;
				}
			}
		},

		/**
		 * @deprecated As of Cavanaugh (7.4.x), with no direct replacement
		 */
		disableFormButtons(inputs, form) {
			inputs.attr('disabled', true);
			inputs.setStyle('opacity', 0.5);

			if (A.UA.gecko) {
				A.getWin().on('unload', () => {
					inputs.attr('disabled', false);
				});
			}
			else if (A.UA.safari) {
				A.use('node-event-html5', (A) => {
					A.getWin().on('pagehide', () => {
						Util.enableFormButtons(inputs, form);
					});
				});
			}
		},

		/**
		 * @deprecated As of Cavanaugh (7.4.x), replaced by `toggleDisabled`
		 */
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

		/**
		 * @deprecated As of Cavanaugh (7.4.x), with no direct replacement
		 */
		enableFormButtons(inputs) {
			Util._submitLocked = null;

			Util.toggleDisabled(inputs, false);
		},

		/**
		 * @deprecated As of Athanasius (7.3.x), with no direct replacement
		 */
		escapeCDATA(str) {
			return str.replace(/<!\[CDATA\[|\]\]>/gi, (match) => {
				let str = '';

				if (match === ']]>') {
					str = ']]&gt;';
				}
				else if (match === '<![CDATA[') {
					str = '&lt;![CDATA[';
				}

				return str;
			});
		},

		/**
		 * @deprecated As of Cavanaugh (7.4.x), with no direct replacement
		 */
		forcePost(link) {
			const currentElement = Util.getElement(link);

			if (currentElement) {
				const url = currentElement.getAttribute('href');

				// LPS-127302

				if (url === 'javascript:void(0);') {
					return;
				}

				const newWindow =
					currentElement.getAttribute('target') === '_blank';

				const hrefFm = document.hrefFm;

				if (newWindow) {
					hrefFm.setAttribute('target', '_blank');
				}

				submitForm(hrefFm, url, !newWindow);

				Util._submitLocked = null;
			}
		},

		/**
		 * @deprecated As of Cavanaugh (7.4.x), with no direct replacement
		 */
		getAttributes(element, attributeGetter) {
			let result = null;

			if (element) {
				element = Util.getDOM(element);

				if (element.jquery) {
					element = element[0];
				}

				result = {};

				const getterFn = typeof attributeGetter === 'function';
				const getterString = typeof attributeGetter === 'string';

				const attrs = element.attributes;
				let length = attrs.length;

				while (length--) {
					const attr = attrs[length];
					let name = attr.nodeName.toLowerCase();
					let value = attr.nodeValue;

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

		/**
		 * @deprecated As of Cavanaugh (7.4.x), with no direct replacement
		 */
		getColumnId(str) {
			const columnId = str.replace(/layout-column_/, '');

			return columnId;
		},

		/**
		 * @deprecated As of Cavanaugh (7.4.x), replaced by `window.name`
		 */
		getWindowName() {
			return window.name || Window._name || '';
		},

		/**
		 * @deprecated As of Athanasius (7.3.x), replaced by `window.innerWidth`
		 */
		getWindowWidth() {
			return window.innerWidth;
		},

		/**
		 * @deprecated As of Athanasius (7.3.x), replaced by `typeof val === 'function'`
		 */
		isFunction(val) {
			return typeof val === 'function';
		},

		/**
		 * @deprecated As of Cavanaugh (7.4.x), replaced by `get_checkboxes.js`
		 */
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

		/**
		 * @deprecated As of Cavanaugh (7.4.x), replaced by `import {getCheckedCheckboxes} from 'frontend-js-web';`
		 */
		listCheckedExcept(form, except, name) {
			return Util.listCheckboxesExcept(form, except, name, true);
		},

		/**
		 * @deprecated As of Cavanaugh (7.4.x), replaced by `import {getSelectedOptionValues} from 'frontend-js-web';`
		 */
		listSelect(select, delimeter) {
			select = Util.getElement(select);

			return Array.from(select.querySelectorAll('option'))
				.reduce((prev, item) => {
					const val = item.value;

					if (val) {
						prev.push(val);
					}

					return prev;
				}, [])
				.join(delimeter || ',');
		},

		/**
		 * @deprecated As of Cavanaugh (7.4.x), replaced by `import {getUncheckedCheckboxes} from 'frontend-js-web';`
		 */
		listUncheckedExcept(form, except, name) {
			return Util.listCheckboxesExcept(form, except, name, false);
		},

		/**
		 * @deprecated As of Cavanaugh (7.4.x), replaced by `Liferay.Util.openWindow()`
		 */
		openInDialog(event, config) {
			event.preventDefault();

			const currentTarget = Util.getElement(event.currentTarget);

			// eslint-disable-next-line prefer-object-spread
			config = Object.assign(
				{},
				// eslint-disable-next-line prefer-object-spread
				Object.assign({}, currentTarget.dataset),
				config
			);

			if (!config.uri) {
				config.uri =
					currentTarget.dataset.href ||
					currentTarget.getAttribute('href');
			}

			if (!config.title) {
				config.title = currentTarget.getAttribute('title');
			}

			Liferay.Util.openWindow(config);
		},

		/**
		 * @deprecated As of Cavanaugh (7.4.x), with no direct replacement
		 */
		processTab(id) {
			document.all[id].selection.text = String.fromCharCode(9);
			document.all[id].focus();
		},

		/**
		 * @deprecated As of Athanasius (7.3.x), with no direct replacement
		 */
		randomInt() {
			return Math.ceil(Math.random() * new Date().getTime());
		},

		/**
		 * @deprecated As of Cavanaugh (7.4.x), with no direct replacement
		 */
		reorder(box, down) {
			box = Util.getElement(box);

			if (box) {
				if (box.getAttribute('selectedIndex') === -1) {
					box.setAttribute('selectedIndex', 0);
				}
				else {
					const selectedItems = Array.from(
						box.querySelectorAll('option:checked')
					);

					const items = Array.from(box.querySelectorAll('option'));

					if (down) {
						selectedItems.reverse().forEach((item) => {
							const itemIndex = items.indexOf(item);

							const lastIndex = items.length - 1;

							if (itemIndex === lastIndex) {
								box.insertBefore(item, box.firstChild);
							}
							else {
								const nextItem =
									item.nextElementSibling.nextElementSibling;

								box.insertBefore(item, nextItem);
							}
						});
					}
					else {
						selectedItems.forEach((item) => {
							const itemIndex = items.indexOf(item);

							if (itemIndex === 0) {
								box.appendChild(item);
							}
							else {
								box.insertBefore(
									item,
									item.previousElementSibling
								);
							}
						});
					}
				}
			}
		},

		/**
		 * @deprecated As of Cavanaugh (7.4.x), with no direct replacement
		 */
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

		/**
		 * @deprecated As of Cavanaugh (7.4.x), with no direct replacement
		 */
		savePortletTitle(params) {
			params = {
				doAsUserId: 0,
				plid: 0,
				portletId: 0,
				title: '',
				url:
					themeDisplay.getPathMain() + '/portal/update_portlet_title',
				...params,
			};

			const data = {
				doAsUserId: params.doAsUserId,
				p_auth: Liferay.authToken,
				p_l_id: params.plid,
				portletId: params.portletId,
				title: params.title,
			};

			Liferay.Util.fetch(params.url, {
				body: Liferay.Util.objectToFormData(data),
				method: 'POST',
			});
		},

		/**
		 * @deprecated As of Cavanaugh (7.4.x), replaced by {@link https://developer.mozilla.org/en-US/docs/Web/API/HTMLInputElement/setSelectionRange}
		 */
		setCursorPosition(element, position) {
			const instance = this;

			instance.setSelectionRange(element, position, position);
		},

		/**
		 * @deprecated As of Cavanaugh (7.4.x), replaced by {@link https://developer.mozilla.org/en-US/docs/Web/API/HTMLInputElement/setSelectionRange}
		 */
		setSelectionRange(element, selectionStart, selectionEnd) {
			element = Util.getDOM(element);

			if (element.jquery) {
				element = element[0];
			}

			if (element.setSelectionRange) {
				element.focus();

				element.setSelectionRange(selectionStart, selectionEnd);
			}
			else if (element.createTextRange) {
				const textRange = element.createTextRange();

				textRange.collapse(true);

				textRange.moveEnd('character', selectionEnd);
				textRange.moveEnd('character', selectionStart);

				textRange.select();
			}
		},

		/**
		 * @deprecated As of Athanasius (7.3.x), with no direct replacement
		 */
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

		submitCountdown: 0,

		/**
		 * @deprecated As of Cavanaugh (7.4.x), replaced by `form.submit()`
		 */
		submitForm(form) {
			form.submit();
		},

		/**
		 * @deprecated As of Athanasius (7.3.x), replaced by `parseInt()`
		 */
		toNumber(value) {
			return parseInt(value, 10) || 0;
		},

		/**
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
						!Util.getCheckedCheckboxes(form, ignoreFieldName)
					);
				},
				'input[type=checkbox]'
			);
		},
	};

	Liferay.provide(
		Util,
		'afterIframeLoaded',
		(event) => {
			// eslint-disable-next-line @liferay/aui/no-node
			const nodeInstances = A.Node._instances;

			const docEl = event.doc;

			const docUID = docEl._yuid;

			if (docUID in nodeInstances) {
				delete nodeInstances[docUID];
			}

			const iframeDocument = A.one(docEl);

			const iframeBody = iframeDocument.one('body');

			const dialog = event.dialog;

			const lfrFormContent = iframeBody.one('.lfr-form-content');

			iframeBody.addClass('dialog-iframe-popup');

			if (
				lfrFormContent &&
				iframeBody.one('.button-holder.dialog-footer')
			) {
				iframeBody.addClass('dialog-with-footer');

				const stagingAlert = iframeBody.one(
					'.portlet-body > .lfr-portlet-message-staging-alert'
				);

				if (stagingAlert) {
					stagingAlert.remove();

					lfrFormContent.prepend(stagingAlert);
				}
			}

			iframeBody.addClass(dialog.iframeConfig.bodyCssClass);

			event.win.focus();

			const iframeWindow = event.win;

			if (iframeWindow.Liferay.SPA) {
				const beforeScreenFlipHandler = iframeWindow.Liferay.on(
					'beforeScreenFlip',
					() => {
						iframeWindow.document.body.classList.add(
							'dialog-iframe-popup'
						);
					}
				);

				iframeWindow.onunload = () => {
					if (beforeScreenFlipHandler) {
						iframeWindow.Liferay.detach(beforeScreenFlipHandler);
					}
				};
			}

			const cancelEventHandler = iframeBody.delegate(
				EVENT_CLICK,
				(event) => {
					dialog.set(
						'visible',
						false,
						event.currentTarget.hasClass('lfr-hide-dialog')
							? SRC_HIDE_LINK
							: null
					);

					cancelEventHandler.detach();

					iframeDocument.purge(true);
				},
				'.btn-cancel,.lfr-hide-dialog'
			);

			Liferay.fire('modalIframeLoaded', {
				src: event.dialog.iframe.node.getAttribute('src'),
			});
		},
		['aui-base']
	);

	/**
	 * @deprecated As of Cavanaugh (7.4.x), replaced by `openSelectionModal`
	 */
	Liferay.provide(
		Util,
		'openDDMPortlet',
		(config, callback) => {
			const defaultValues = {
				eventName: 'selectStructure',
			};
			// eslint-disable-next-line @liferay/aui/no-merge
			config = A.merge(defaultValues, config);

			const params = {
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
				templateId: config.templateId,
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

			const url = Liferay.Util.PortletURL.createRenderURL(
				config.basePortletURL,
				params
			);

			config.uri = url.toString();

			let dialogConfig = config.dialog;

			if (!dialogConfig) {
				dialogConfig = {};

				config.dialog = dialogConfig;
			}

			const eventHandles = [];

			if (callback) {
				eventHandles.push(Liferay.once(config.eventName, callback));
			}

			const detachSelectionOnHideFn = function (event) {
				Liferay.fire(config.eventName);

				if (!event.newVal) {
					new A.EventHandle(eventHandles).detach();
				}
			};

			Util.openWindow(config, (dialogWindow) => {
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
					const executor = new A.config.win.ActiveXObject(
						'SharePoint.OpenDocuments'
					);

					executor.EditDocument(webDavUrl);

					if (Lang.isFunction(onSuccess)) {
						onSuccess();
					}
				}
				catch (error) {
					if (Lang.isFunction(onError)) {
						onError(error);
					}
				}
			}
		},
		['aui-base']
	);

	/**
	 * @deprecated As of Cavanaugh (7.4.x), with no direct replacement
	 */
	Liferay.provide(
		Util,
		'selectEntityHandler',
		(containerSelector, selectEventName, disableButton) => {
			const container = A.one(containerSelector);

			if (!container) {
				return;
			}

			const openingLiferay = Util.getOpener().Liferay;

			const selectorButtons = container
				.getDOM()
				.querySelectorAll('.selector-button');

			container.delegate(
				EVENT_CLICK,
				(event) => {
					const currentTarget = event.currentTarget.getDOM();

					if (
						currentTarget.disabled ||
						currentTarget.dataset['preventSelection']
					) {
						return;
					}

					const doSelect = () => {
						if (disableButton) {
							selectorButtons.forEach((selectorButton) => {
								selectorButton.disabled = false;
							});

							currentTarget.disabled = true;
						}

						const result = Util.getAttributes(
							currentTarget,
							'data-'
						);

						openingLiferay.fire(selectEventName, result);

						const window = Util.getWindow();

						if (window) {
							window.hide();
						}
					};

					const confirmSelection =
						currentTarget.dataset['confirmSelection'] === 'true';

					if (!confirmSelection) {
						doSelect();
					}
					else {
						Liferay.Util.openConfirmModal({
							message:
								currentTarget.dataset[
									'confirmSelectionMessage'
								],
							onConfirm: (isConfirmed) => {
								if (isConfirmed) {
									if (disableButton) {
										selectorButtons.forEach(
											(selectorButton) => {
												selectorButton.disabled = false;
											}
										);

										currentTarget.disabled = true;
									}

									const result = Util.getAttributes(
										currentTarget,
										'data-'
									);

									openingLiferay.fire(
										selectEventName,
										result
									);

									const window = Util.getWindow();

									if (window) {
										window.hide();
									}
								}
							},
						});
					}
				},
				'.selector-button'
			);

			openingLiferay.on('entitySelectionRemoved', () => {
				selectorButtons.forEach((selectorButton) => {
					selectorButton.disabled = false;
				});
			});
		},
		['aui-base']
	);

	Liferay.provide(
		Util,
		'portletTitleEdit',
		(options) => {
			const object = options.obj;

			A.Event.defineOutside('mouseup');

			if (object) {
				const title = object.one('.portlet-title-text');

				if (title && !title.hasClass('not-editable')) {
					title.addClass('portlet-title-editable');

					title.on(EVENT_CLICK, (event) => {
						const editable = Util._getEditableInstance(title);

						const rendered = editable.get('rendered');

						if (rendered) {
							editable.fire('stopEditing');
						}

						editable.set('node', event.currentTarget);

						if (rendered) {
							editable.syncUI();
						}

						editable._startEditing(event);

						if (!rendered) {
							const defaultIconsTpl =
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
			const dialog = Util.getWindow(config.id);

			const eventName = config.eventName || config.id;

			const eventHandles = [Liferay.on(eventName, callback)];

			const detachSelectionOnHideFn = function (event) {
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
			}
			else {
				const destroyDialog = function (event) {
					const dialogId = config.id;

					const dialogWindow = Util.getWindow(dialogId);

					if (
						dialogWindow &&
						Util.getPortletId(dialogId) === event.portletId
					) {
						dialogWindow.destroy();

						Liferay.detach('destroyPortlet', destroyDialog);
					}
				};

				const editURL = new Liferay.Util.PortletURL.createPortletURL(
					config.uri,
					// eslint-disable-next-line @liferay/aui/no-merge
					A.merge(
						{
							eventName,
						},
						config.urlParams
					)
				);

				config.uri = editURL.toString();

				// eslint-disable-next-line @liferay/aui/no-merge
				config.dialogIframe = A.merge(
					{
						bodyCssClass: 'dialog-with-footer',
					},
					config.dialogIframe || {}
				);

				Util.openWindow(config, (dialogWindow) => {
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

	/**
	 * @deprecated As of Cavanaugh (7.4.x), replaced by `openSelectionModal`
	 */
	Liferay.provide(
		Util,
		'selectEntity',
		(config, callback) => {
			const dialog = Util.getWindow(config.id);

			const eventName = config.eventName || config.id;

			const eventHandles = [Liferay.on(eventName, callback)];

			const selectedData = config.selectedData;

			if (selectedData) {
				config.dialog.destroyOnHide = true;
			}

			const detachSelectionOnHideFn = function (event) {
				if (!event.newVal) {
					new A.EventHandle(eventHandles).detach();
				}
			};

			const syncAssets = function (event) {
				const currentWindow = event.currentTarget.node.get(
					'contentWindow.document'
				);

				const selectorButtons = currentWindow.all(
					'.lfr-search-container-wrapper .selector-button'
				);

				if (selectedData) {
					// eslint-disable-next-line @liferay/aui/no-each
					A.each(selectorButtons, (item) => {
						let assetEntryId =
							item.attr('data-entityid') ||
							item.attr('data-entityname');

						const assetGroupId = item.attr('data-groupid');

						if (assetGroupId) {
							assetEntryId = assetGroupId + '-' + assetEntryId;
						}

						const disabled = selectedData.includes(assetEntryId);

						if (disabled) {
							item.attr('data-prevent-selection', true);
						}
						else {
							item.removeAttribute('data-prevent-selection');
						}

						Util.toggleDisabled(item, disabled);
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
			}
			else {
				const destroyDialog = function (event) {
					const dialogId = config.id;

					const dialogWindow = Util.getWindow(dialogId);

					if (
						dialogWindow &&
						Util.getPortletId(dialogId) === event.portletId
					) {
						dialogWindow.destroy();

						Liferay.detach('destroyPortlet', destroyDialog);
					}
				};

				Util.openWindow(config, (dialogWindow) => {
					eventHandles.push(
						dialogWindow.after(
							['destroy', 'visibleChange'],
							detachSelectionOnHideFn
						),
						dialogWindow.iframe.after(['load'], syncAssets)
					);

					Liferay.on('destroyPortlet', destroyDialog);
				});
			}
		},
		['aui-base', 'liferay-util-window']
	);

	/**
	 * Used in `modules/apps/frontend-js/frontend-js-web/src/main/resources/META-INF/resources/liferay/util/open_window.js`
	 * which will need to be migrated over to `openModal`.
	 */
	Liferay.provide(
		Util,
		'_openWindowProvider',
		(config, callback) => {
			const dialog = Window.getWindow(config);

			if (Lang.isFunction(callback)) {
				callback(dialog);
			}
		},
		['liferay-util-window']
	);

	Util.Window = Window;

	Liferay.Util = Util;

	// 0-200: Theme Developer
	// 200-400: Portlet Developer
	// 400+: Liferay

})(AUI());
