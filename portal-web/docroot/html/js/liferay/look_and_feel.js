AUI.add(
	'liferay-look-and-feel',
	function(A) {
		var Browser = Liferay.Browser;
		var Lang = A.Lang;

		var REGEX_IGNORED_CLASSES = /boundary|draggable/;

		var REGEX_IGNORED_CLASSES_PORTLET = /(?:^|\s)portlet(?=\s|$)/g;

		var REGEX_VALID_CLASSES = /(?:([\w\d-]+)-)?portlet(?:-?([\w\d-]+-?))?/g;

		var PortletCSS = {
			init: function(portletId) {
				var instance = this;

				var curPortletBoundaryId = 'p_p_id_' + portletId + '_';
				var obj = A.one('#' + curPortletBoundaryId);

				if (obj) {
					instance._portletId = portletId;
					instance._curPortlet = obj.one('.portlet');

					instance._portletBoundary = obj;

					if (!instance._curPortlet) {
						instance._curPortlet = obj;
						instance._curPortletWrapperId = curPortletBoundaryId;
					}
					else {
						instance._curPortletWrapperId = instance._curPortlet.attr('id');
					}

					instance._portletBoundaryId = curPortletBoundaryId;
					instance._newPanel = A.one('#portlet-set-properties');
					instance._currentLanguage = themeDisplay.getLanguageId();

					if (instance._curPortlet) {
						var content = instance._newPanel;

						if (!content) {
							content = A.Node.create('<div class="loading-animation" />');
						}

						if (!instance._currentPopup) {
							instance._currentPopup = new A.Dialog(
								{
									on: {
										close: function() {
											if (Browser.isIe() && Browser.getMajorVersion() == 6) {
												window.location.reload(true);
											}
										}
									},
									title: Liferay.Language.get('look-and-feel'),
									width: 820
								}
							).render();

							instance._currentPopup.plug(
								[
									{
										fn: A.Plugin.IO,
										cfg: {
											after: {
												success: function(event) {
													var host = this.get('host');
													var boundingBox = host.get('boundingBox');

													var properties = boundingBox.one('#portlet-set-properties');

													if (properties) {
														instance._newPanel = properties;
														instance._loadContent();
													}
												}
											},
											autoLoad: false,
											showLoading: false,
											data: {
												p_l_id: themeDisplay.getPlid(),
												p_p_id: 113,
												p_p_state: 'exclusive',
												doAsUserId: themeDisplay.getDoAsUserIdEncoded()
											},
											uri: themeDisplay.getPathMain() + '/portal/render_portlet'
										}
									},
									{
										fn: A.LoadingMask
									}
								]
							);
						}

						instance._currentPopup.show();
						instance._currentPopup.alignToViewport(20, 20);
						instance._currentPopup.loadingmask.show();
						instance._currentPopup.io.start();
					}
				}

			},

			_backgroundStyles: function() {
				var instance = this;

				var bgData = instance._objData.bgData;

				var portlet = instance._curPortlet;

				// Background color

				var backgroundColor = instance._backgroundColor;

				var setColor = function(obj) {
					var color = obj.val();

					var cssColor = color;

					if ((color == '') || (color == '#')) {
						cssColor = 'transparent';
						color = '';
					}

					portlet.setStyle('backgroundColor', cssColor);
					bgData.backgroundColor = color;
				};

				var hexValue = backgroundColor.val().replace('#', '');

				if (!instance._backgroundColorPicker) {
					instance._backgroundColorPicker = new A.ColorPicker(
						{
							triggerParent: backgroundColor.get('parentNode'),
							zIndex: 9999
						}
					).render(instance._currentPopup.get('boundingBox'));
				}

				var backgroundColorPicker = instance._backgroundColorPicker;

				var afterColorChange = function() {
					backgroundColor.val('#' + this.get('hex'));

					setColor(backgroundColor);
				};

				if (instance._afterBackgroundColorChangeHandler) {
					instance._afterBackgroundColorChangeHandler.detach();
				}

				instance._afterBackgroundColorChangeHandler = backgroundColorPicker.after('colorChange', afterColorChange);

				backgroundColorPicker.set('hex', hexValue);

				backgroundColor.detach('blur');

				backgroundColor.on(
					'blur',
					function(event) {
						setColor(event.currentTarget);
					}
				);
			},

			_borderStyles: function() {
				var instance = this;

				var portlet = instance._curPortlet;

				var ufaWidth = instance._ufaBorderWidth;
				var ufaStyle = instance._ufaBorderStyle;
				var ufaColor = instance._ufaBorderColor;

				var borderData = instance._objData.borderData;

				// Border width

				var wTopInt = instance._borderTopInt;
				var wTopUnit = instance._borderTopUnit;
				var wRightInt = instance._borderRightInt;
				var wRightUnit = instance._borderRightUnit;
				var wBottomInt = instance._borderBottomInt;
				var wBottomUnit = instance._borderBottomUnit;
				var wLeftInt = instance._borderLeftInt;
				var wLeftUnit = instance._borderLeftUnit;

				var changeWidth = function() {
					var styling = {};
					var borderWidth = {};

					borderWidth = instance._getCombo(wTopInt, wTopUnit);
					styling = {borderWidth: borderWidth.both};

					var ufa = ufaWidth.get('checked');

					borderData.borderWidth.top.value = borderWidth.input;
					borderData.borderWidth.top.unit = borderWidth.selectBox;
					borderData.borderWidth.sameForAll = ufa;

					if (!ufa) {
						var extStyling = {};

						extStyling.borderTopWidth = styling.borderWidth;

						var right = instance._getCombo(wRightInt, wRightUnit);
						var bottom = instance._getCombo(wBottomInt, wBottomUnit);
						var left = instance._getCombo(wLeftInt, wLeftUnit);

						extStyling.borderRightWidth = right.both;
						extStyling.borderBottomWidth = bottom.both;
						extStyling.borderLeftWidth = left.both;

						styling = extStyling;

						borderData.borderWidth.right.value = right.input;
						borderData.borderWidth.right.unit = right.selectBox;

						borderData.borderWidth.bottom.value = bottom.input;
						borderData.borderWidth.bottom.unit = bottom.selectBox;

						borderData.borderWidth.left.value = left.input;
						borderData.borderWidth.left.unit = left.selectBox;
					}

					portlet.setStyles(styling);

					changeStyle();
					changeColor();
				};

				wTopInt.detach('blur');
				wTopInt.on('blur', changeWidth);

				wTopInt.detach('keyup');
				wTopInt.on('keyup', changeWidth);

				wRightInt.detach('blur');
				wRightInt.on('blur', changeWidth);

				wRightInt.detach('keyup');
				wRightInt.on('keyup', changeWidth);

				wBottomInt.detach('blur');
				wBottomInt.on('blur', changeWidth);

				wBottomInt.detach('keyup');
				wBottomInt.on('keyup', changeWidth);

				wLeftInt.detach('blur');
				wLeftInt.on('blur', changeWidth);

				wLeftInt.detach('keyup');
				wLeftInt.on('keyup', changeWidth);

				wTopUnit.detach('change');
				wTopUnit.on('change', changeWidth);

				wRightUnit.detach('change');
				wRightUnit.on('change', changeWidth);

				wBottomUnit.detach('change');
				wBottomUnit.on('change', changeWidth);

				wLeftUnit.detach('change');
				wLeftUnit.on('change', changeWidth);

				ufaWidth.detach('change');
				ufaWidth.on('change', changeWidth);

				// Border style

				var sTopStyle = instance._borderTopStyle;
				var sRightStyle = instance._borderRightStyle;
				var sBottomStyle = instance._borderBottomStyle;
				var sLeftStyle = instance._borderLeftStyle;

				var changeStyle = function() {
					var styling = {};
					var borderStyle = {};

					borderStyle = sTopStyle.val();
					styling = {borderStyle: borderStyle};

					var ufa = ufaStyle.get('checked');

					borderData.borderStyle.top = borderStyle;
					borderData.borderStyle.sameForAll = ufa;

					if (!ufa) {
						var extStyling = {};

						extStyling.borderTopStyle = styling.borderStyle;

						var right = sRightStyle.val();
						var bottom = sBottomStyle.val();
						var left = sLeftStyle.val();

						extStyling.borderRightStyle = right;
						extStyling.borderBottomStyle = bottom;
						extStyling.borderLeftStyle = left;

						styling = extStyling;

						borderData.borderStyle.right = right;

						borderData.borderStyle.bottom = bottom;

						borderData.borderStyle.left = left;
					}

					portlet.setStyles(styling);
				};

				sTopStyle.detach('change');
				sTopStyle.on('change', changeStyle);

				sRightStyle.detach('change');
				sRightStyle.on('change', changeStyle);

				sBottomStyle.detach('change');
				sBottomStyle.on('change', changeStyle);

				sLeftStyle.detach('change');
				sLeftStyle.on('change', changeStyle);

				ufaStyle.detach('change');
				ufaStyle.on('change', changeStyle);

				// Border color

				var cTopColor = instance._borderTopColor;
				var cRightColor = instance._borderRightColor;
				var cBottomColor = instance._borderBottomColor;
				var cLeftColor = instance._borderLeftColor;

				var changeColor = function() {
					var styling = {};
					var borderColor = {};

					borderColor = cTopColor.val();
					styling = {borderColor: borderColor};

					var ufa = ufaColor.get('checked');

					borderData.borderColor.top = borderColor;
					borderData.borderColor.sameForAll = ufa;

					if (!ufa) {
						var extStyling = {};

						extStyling.borderTopColor = styling.borderColor;

						var right = cRightColor.val();
						var bottom = cBottomColor.val();
						var left = cLeftColor.val();

						extStyling.borderRightColor = right;
						extStyling.borderBottomColor = bottom;
						extStyling.borderLeftColor = left;

						styling = extStyling;

						borderData.borderColor.right = right;

						borderData.borderColor.bottom = bottom;

						borderData.borderColor.left = left;
					}

					portlet.setStyles(styling);
				};

				var popupBoundingBox = instance._currentPopup.get('boundingBox');

				A.each(
					[cTopColor, cRightColor, cBottomColor, cLeftColor],
					function(item, index, collection) {
						var hexValue = item.val().replace('#', '');

						var borderLocation = '_borderColorPicker' + index;

						if (!instance[borderLocation]) {
							instance[borderLocation] = new A.ColorPicker(
								{
									triggerParent: item.get('parentNode'),
									zIndex: 9999
								}
							).render(popupBoundingBox);
						}

						var borderColorPicker = instance[borderLocation];

						var afterColorChange = function() {
							item.val('#' + this.get('hex'));

							changeColor();
						};

						var borderColorChangeHandler = '_afterBorderColorChangeHandler' + index;

						if (instance[borderColorChangeHandler]) {
							instance[borderColorChangeHandler].detach();
						}

						instance[borderColorChangeHandler] = borderColorPicker.after('colorChange', afterColorChange);

						borderColorPicker.set('hex', hexValue);
					}
				);

				cTopColor.detach('blur');
				cTopColor.on('blur', changeColor);

				cRightColor.detach('blur');
				cRightColor.on('blur', changeColor);

				cBottomColor.detach('blur');
				cBottomColor.on('blur', changeColor);

				cLeftColor.detach('blur');
				cLeftColor.on('blur', changeColor);

				cTopColor.detach('keyup');
				cTopColor.on('keyup', changeColor);

				cRightColor.detach('keyup');
				cRightColor.on('keyup', changeColor);

				cBottomColor.detach('keyup');
				cBottomColor.on('keyup', changeColor);

				cLeftColor.detach('keyup');
				cLeftColor.on('keyup', changeColor);

				ufaColor.detach('change');
				ufaColor.on('change', changeColor);
			},

			_cssStyles: function() {
				var instance = this;

				var portlet = instance._curPortlet;
				var portletBoundary = instance._portletBoundary;

				var customCSS = instance._getNodeById('lfr-custom-css');
				var customCSSClassName = instance._getNodeById('lfr-custom-css-class-name');
				var customCSSContainer = customCSS.ancestor('.aui-field');
				var customCSSClassNameContainer = customCSSClassName.ancestor('.aui-field');
				var customPortletNoteHTML = '<p class="portlet-msg-info form-hint"></p>';
				var customPortletNote = A.one('#lfr-portlet-info');
				var refreshText = '';

				var portletId = instance._curPortletWrapperId;

				var portletClasses = instance._getCSSClasses(portletBoundary, portlet);

				var portletInfoText =
					Liferay.Language.get('your-current-portlet-information-is-as-follows') + '<br />' +
						Liferay.Language.get('portlet-id') + ': <strong>#' + portletId + '</strong><br />' +
							Liferay.Language.get('portlet-classes') + ': <strong>' + portletClasses + '</strong>';

				var customNote = A.one('#lfr-refresh-styles');

				if (!customNote) {
					customNote = A.Node.create(customPortletNoteHTML);

					customNote.setAttrs(
						{
							'className': '',
							id: 'lfr-refresh-styles'
						}
					);
				}

				if (!customPortletNote) {
					customPortletNote = A.Node.create(customPortletNoteHTML);
					customCSSClassNameContainer.placeBefore(customPortletNote);

					customPortletNote.attr('id', 'lfr-portlet-info');
				}

				customPortletNote.html(portletInfoText);

				Liferay.Util.enableTextareaTabs(customCSS.getDOM());

				if (!Browser.isSafari()) {
					refreshText = Liferay.Language.get('update-the-styles-on-this-page');

					var refreshLink = A.Node.create('<a href="javascript:;">' + refreshText + '</a>');

					var customStyleBlock = A.one('#lfr-custom-css-block-' + portletId);

					if (!customStyleBlock) {

						// Do not modify. This is a workaround for an IE bug.

						var styleEl = document.createElement('style');

						styleEl.id = 'lfr-custom-css-block-' + portletId;
						styleEl.className = 'lfr-custom-css-block';
						styleEl.setAttribute('type', 'text/css');

						document.getElementsByTagName('head')[0].appendChild(styleEl);
					}
					else {
						styleEl = customStyleBlock.getDOM();
					}

					var refreshStyles = function() {
						var customStyles = customCSS.val();

						customStyles = customStyles.replace(/<script[^>]*>([\u0001-\uFFFF]*?)<\/script>/gim, '');
						customStyles = customStyles.replace(/<\/?[^>]+>/gi, '');

						if (styleEl.styleSheet) { // for IE only
							if (customStyles == '') {

								// Do not modify. This is a workaround for an IE bug.

								customStyles = '<!---->';
							}

							styleEl.styleSheet.cssText = customStyles;
						}
						else {
							A.one(styleEl).html(customStyles);
						}
					};

					refreshLink.detach('click');
					refreshLink.on('click', refreshStyles);

					customNote.empty().append(refreshLink);
				}
				else {
					refreshText = Liferay.Language.get('please-press-the-save-button-to-view-your-changes');

					customNote.empty().text(refreshText);
				}

				var insertContainer = A.one('#lfr-add-rule-container');
				var addIdLink = A.one('#lfr-add-id');
				var addClassLink = A.one('#lfr-add-class');
				var updateOnType = A.one('#lfr-update-on-type');

				if (!insertContainer) {
					insertContainer = A.Node.create('<div id="lfr-add-rule-container"></div>');
					addIdLink = A.Node.create('<a href="javascript:;" id="lfr-add-id">' + Liferay.Language.get('add-a-css-rule-for-just-this-portlet') + '</a>');
					addClassLink = A.Node.create('<a href="javascript:;" id="lfr-add-class">' + Liferay.Language.get('add-a-css-rule-for-all-portlets-like-this-one') + '</a>');

					var updateOnTypeHolder = A.Node.create('<span class="aui-field"><span class="aui-field-content"></span></span>');
					var updateOnTypeLabel = A.Node.create('<label>' + Liferay.Language.get('update-my-styles-as-i-type') + ' </label>');

					updateOnType = A.Node.create('<input id="lfr-update-on-type" type="checkbox" />');

					updateOnTypeLabel.appendChild(updateOnType);
					updateOnTypeHolder.get('firstChild').appendChild(updateOnTypeLabel);

					customCSSContainer.placeAfter(insertContainer);

					insertContainer.appendChild(addIdLink);

					insertContainer.append('<br />');

					insertContainer.appendChild(addClassLink);
					insertContainer.appendChild(updateOnTypeHolder);

					insertContainer.after(customNote);
				}

				updateOnType.on(
					'click',
					function(event) {
						if (event.currentTarget.get('checked')) {
							customNote.hide();
							customCSS.on('keyup', refreshStyles);
						}
						else {
							customNote.show();
							customCSS.detach('keyup', refreshStyles);
						}
					}
				);

				addIdLink.detach('click');

				addIdLink.on(
					'click',
					function() {
						instance._insertCustomCSSValue(customCSS, '#' + portletId);
					}
				);

				addClassLink.detach('click');

				addClassLink.on(
					'click',
					function() {
						instance._insertCustomCSSValue(customCSS, instance._getCSSClasses(portletBoundary, portlet));
					}
				);
			},

			_getCombo: function(input, selectBox) {
				var instance = this;

				var inputVal = input.val();
				var selectVal = selectBox.val();

				inputVal = instance._getSafeInteger(inputVal);

				return {input: inputVal, selectBox: selectVal, both: inputVal + selectVal};
			},

			_getCSSClasses: function(portletBoundary, portlet) {
				var instance = this;

				var portletClasses = '';

				if (portlet && portlet != portletBoundary) {
					portletClasses = portlet.attr('className').replace(REGEX_IGNORED_CLASSES_PORTLET, '');

					portletClasses = Lang.trim(portletClasses).replace(/\s+/g, '.');

					if (portletClasses) {
						portletClasses = ' .' + portletClasses;
					}
				}

				var boundaryClasses = [];

				portletBoundary.attr('className').replace(
					REGEX_VALID_CLASSES,
					function(match, subMatch1, subMatch2) {
						if (!REGEX_IGNORED_CLASSES.test(subMatch2)) {
							boundaryClasses.push(match);
						}
					}
				);

				return '.' + boundaryClasses.join('.') + portletClasses;
			},

			_getNodeById: function(id) {
				var instance = this;

				return A.one('#_113_' + id);
			},

			_getSafeInteger: function(input) {
				var instance = this;

				var output = parseInt(input);

				if (output == '' || isNaN(output)) {
					output = 0;
				}

				return output;
			},

			_insertCustomCSSValue: function(textarea, value) {
				var instance = this;

				var currentVal = Lang.trim(textarea.val());

				if (currentVal.length) {
					currentVal += '\n\n';
				}

				var newVal = currentVal + value + ' {\n\t\n}\n';

				textarea.val(newVal);

				Liferay.Util.setCursorPosition(textarea, newVal.length - 3);
			},

			_languageClasses: function(key, value, removeClass) {
				var instance = this;

				var option = instance._portletLanguage.one('option[value=' + key + ']');

				if (option) {
					if (removeClass) {
						option.removeClass('focused');
					}
					else {
						option.addClass('focused');
					}
				}
			},

			_loadContent: function(instantiated) {
				var instance = this;

				var newPanel = instance._newPanel;

				if (!instantiated) {
					newPanel.addClass('instantiated');
					instance._portletBoundaryIdVar = A.one('#portlet-boundary-id');

					// Portlet config

					var portletTitle = instance._curPortlet.one('.portlet-title');

					instance._defaultPortletTitle = Lang.trim(portletTitle ? portletTitle.text() : '');

					instance._customTitleInput = instance._getNodeById('custom-title');
					instance._customTitleCheckbox = instance._getNodeById('use-custom-titleCheckbox');
					instance._showBorders = instance._getNodeById('show-borders');
					instance._borderNote = A.one('#border-note');
					instance._portletLanguage = instance._getNodeById('lfr-portlet-language');
					instance._portletLinksTarget = instance._getNodeById('lfr-point-links');

					// Text

					instance._fontFamily = instance._getNodeById('lfr-font-family');
					instance._fontWeight = instance._getNodeById('lfr-font-boldCheckbox');
					instance._fontStyle = instance._getNodeById('lfr-font-italicCheckbox');
					instance._fontSize = instance._getNodeById('lfr-font-size');
					instance._fontColor = instance._getNodeById('lfr-font-color');
					instance._textAlign = instance._getNodeById('lfr-font-align');
					instance._textDecoration = instance._getNodeById('lfr-font-decoration');
					instance._wordSpacing = instance._getNodeById('lfr-font-space');
					instance._leading = instance._getNodeById('lfr-font-leading');
					instance._tracking = instance._getNodeById('lfr-font-tracking');

					// Background

					instance._backgroundColor = instance._getNodeById('lfr-bg-color');

					// Border

					instance._ufaBorderWidth = instance._getNodeById('lfr-use-for-all-widthCheckbox');
					instance._ufaBorderStyle = instance._getNodeById('lfr-use-for-all-styleCheckbox');
					instance._ufaBorderColor = instance._getNodeById('lfr-use-for-all-colorCheckbox');

					instance._borderTopInt = instance._getNodeById('lfr-border-width-top');
					instance._borderTopUnit = instance._getNodeById('lfr-border-width-top-unit');
					instance._borderRightInt = instance._getNodeById('lfr-border-width-right');
					instance._borderRightUnit = instance._getNodeById('lfr-border-width-right-unit');
					instance._borderBottomInt = instance._getNodeById('lfr-border-width-bottom');
					instance._borderBottomUnit = instance._getNodeById('lfr-border-width-bottom-unit');
					instance._borderLeftInt = instance._getNodeById('lfr-border-width-left');
					instance._borderLeftUnit = instance._getNodeById('lfr-border-width-left-unit');

					instance._borderTopStyle = instance._getNodeById('lfr-border-style-top');
					instance._borderRightStyle = instance._getNodeById('lfr-border-style-right');
					instance._borderBottomStyle = instance._getNodeById('lfr-border-style-bottom');
					instance._borderLeftStyle = instance._getNodeById('lfr-border-style-left');

					instance._borderTopColor = instance._getNodeById('lfr-border-color-top');
					instance._borderRightColor = instance._getNodeById('lfr-border-color-right');
					instance._borderBottomColor = instance._getNodeById('lfr-border-color-bottom');
					instance._borderLeftColor = instance._getNodeById('lfr-border-color-left');

					// Spacing

					instance._ufaPadding = instance._getNodeById('lfr-use-for-all-paddingCheckbox');
					instance._ufaMargin = instance._getNodeById('lfr-use-for-all-marginCheckbox');

					instance._paddingTopInt = instance._getNodeById('lfr-padding-top');
					instance._paddingTopUnit = instance._getNodeById('lfr-padding-top-unit');
					instance._paddingRightInt = instance._getNodeById('lfr-padding-right');
					instance._paddingRightUnit = instance._getNodeById('lfr-padding-right-unit');
					instance._paddingBottomInt = instance._getNodeById('lfr-padding-bottom');
					instance._paddingBottomUnit = instance._getNodeById('lfr-padding-bottom-unit');
					instance._paddingLeftInt = instance._getNodeById('lfr-padding-left');
					instance._paddingLeftUnit = instance._getNodeById('lfr-padding-left-unit');

					instance._marginTopInt = instance._getNodeById('lfr-margin-top');
					instance._marginTopUnit = instance._getNodeById('lfr-margin-top-unit');
					instance._marginRightInt = instance._getNodeById('lfr-margin-right');
					instance._marginRightUnit = instance._getNodeById('lfr-margin-right-unit');
					instance._marginBottomInt = instance._getNodeById('lfr-margin-bottom');
					instance._marginBottomUnit = instance._getNodeById('lfr-margin-bottom-unit');
					instance._marginLeftInt = instance._getNodeById('lfr-margin-left');
					instance._marginLeftUnit = instance._getNodeById('lfr-margin-left-unit');

					// Advanced CSS

					instance._customCSS = instance._getNodeById('lfr-custom-css');
					instance._customCSSClassName = instance._getNodeById('lfr-custom-css-class-name');

					instance._saveButton = instance._getNodeById('lfr-lookfeel-save');
					instance._resetButton = instance._getNodeById('lfr-lookfeel-reset');

					// WAP styling

					instance._wapTitleInput = instance._getNodeById('lfr-wap-title');
					instance._wapInitialWindowStateSelect = instance._getNodeById('lfr-wap-initial-window-state');

				}

				instance._tabs = new A.TabView(
					{
						listNode: newPanel.one('.aui-tabview-list'),
						contentNode: newPanel.one('.aui-tabview-content')
					}
				).render(newPanel.one('form'));

				newPanel.show();

				instance._currentPopup.loadingmask.refreshMask();

				newPanel.all('.lfr-colorpicker-img').remove(true);

				instance._portletMsgResponse = A.one('#lfr-portlet-css-response');

				if (instance._portletMsgResponse) {
					instance._portletMsgResponse.hide();
				}

				var defaultData = {
					advancedData: {
						customCSS: ''
					},

					bgData: {
						backgroundColor: '',
						backgroundImage: '',
						backgroundPosition: {
							left: {
								unit: 'px',
								value: ''
							},
							top: {
								unit: 'px',
								value: ''
							}
						},
						backgroundRepeat: '',
						useBgImage: false
					},

					borderData: {
						borderColor: {
							bottom: '',
							left: '',
							right: '',
							sameForAll: true,
							top: ''
						},

						borderStyle: {
							bottom: '',
							left: '',
							right: '',
							sameForAll: true,
							top: ''
						},

						borderWidth: {
							bottom: {
								unit: 'px',
								value: ''
							},
							left: {
								unit: 'px',
								value: ''
							},
							right: {
								unit: 'px',
								value: ''
							},
							sameForAll: true,
							top: {
								unit: 'px',
								value: ''
							}
						}
					},

					portletData: {
						language: 'en_US',
						portletLinksTarget: '',
						title: '',
						titles: {},
						useCustomTitle: false
					},

					spacingData: {
						margin: {
							bottom: {
								unit: 'px',
								value: ''
							},
							left: {
								unit: 'px',
								value: ''
							},
							right: {
								unit: 'px',
								value: ''
							},
							sameForAll: true,
							top: {
								unit: 'px',
								value: ''
							}
						},
						padding: {
							bottom: {
								unit: 'px',
								value: ''
							},
							left: {
								unit: 'px',
								value: ''
							},
							right: {
								unit: 'px',
								value: ''
							},
							sameForAll: true,
							top: {
								unit: 'px',
								value: ''
							}
						}

					},

					textData: {
						color: '',
						fontFamily: '',
						fontSize: '',
						fontStyle: '',
						fontWeight: '',
						letterSpacing: '',
						lineHeight: '',
						textAlign: '',
						textDecoration: '',
						wordSpacing: ''
					},

					wapData: {
						initialWindowState: 'NORMAL',
						title: ''
					}
				};

				var onLookAndFeelComplete = function() {
					instance._portletBoundaryIdVar.val(instance._curPortletWrapperId);

					instance._setDefaults();

					instance._portletConfig();
					instance._textStyles();
					instance._backgroundStyles();
					instance._borderStyles();
					instance._spacingStyles();
					instance._cssStyles();

					var useForAll = newPanel.all('.lfr-use-for-all input[type=checkbox]');

					var handleForms = function(item, index, collection) {
						var checkBox = item;

						var fieldset = checkBox.ancestor('fieldset');

						var otherHolders = fieldset.all('.aui-field-row');
						var firstIndex = 0;

						if (!otherHolders.size()) {
							otherHolders = fieldset.all('.aui-field-content');
							firstIndex = 1;
						}

						var checked = item.get('checked');

						otherHolders.each(
							function(holderItem, holderIndex, holderCollection) {
								if (holderIndex > firstIndex) {
									var fields = holderItem.all('input, select');
									var colorPickerImages = holderItem.all('.aui-buttonitem');

									var action = 'show';
									var disabled = false;
									var opacity = 1;

									if (checked) {
										action = 'hide';
										disabled = true;
										opacity = 0.3;
									}

									holderItem.setStyle('opacity', opacity);
									fields.set('disabled', disabled);
									colorPickerImages[action]();
								}
							}
						);
					};

					useForAll.detach('click');

					useForAll.on(
						'click',
						function(event) {
							handleForms(event.currentTarget);
						}
					);

					useForAll.each(handleForms);

					var updatePortletCSSClassName = function(previousCSSClass, newCSSClass) {
						var portlet = instance._portletBoundary;

						portlet.removeClass(previousCSSClass);
						portlet.addClass(newCSSClass);
					};

					var saveHandler = function(event, id, obj) {
						var ajaxResponseMsg = instance._portletMsgResponse;
						var ajaxResponseHTML = '<div id="lfr-portlet-css-response"></div>';
						var message = '';
						var messageClass = '';
						var type = 'success';

						if (obj.statusText.toLowerCase() != 'ok') {
							type = 'error';
						}

						if (type == 'success') {
							message = Liferay.Language.get('your-request-processed-successfully');
							messageClass = 'portlet-msg-success';
						}
						else {
							message = Liferay.Language.get('your-settings-could-not-be-saved');
							messageClass = 'portlet-msg-error';
						}

						if (!ajaxResponseMsg) {
							ajaxResponse = A.Node.create(ajaxResponseHTML);
							newPanel.one('form').prepend(ajaxResponse);

							instance._portletMsgResponse = ajaxResponse;
						}

						ajaxResponse.addClass(messageClass);
						ajaxResponse.html(message);
						ajaxResponse.show();
					};

					instance._saveButton.detach('click');

					instance._saveButton.on(
						'click',
						function() {
							instance._objData.advancedData.customCSS = instance._customCSS.val();

							var previousCSSClass = instance._objData.advancedData.customCSSClassName;
							var newCSSClass = instance._customCSSClassName.val();

							instance._objData.advancedData.customCSSClassName = newCSSClass;

							updatePortletCSSClassName(previousCSSClass, newCSSClass);

							instance._objData.wapData.title = instance._wapTitleInput.val();
							instance._objData.wapData.initialWindowState = instance._wapInitialWindowStateSelect.val();

							A.io.request(
								themeDisplay.getPathMain() + '/portlet_configuration/update_look_and_feel',
								{
									data: {
										css: A.JSON.stringify(instance._objData),
										doAsUserId: themeDisplay.getDoAsUserIdEncoded(),
										p_auth: Liferay.authToken,
										p_l_id: themeDisplay.getPlid(),
										portletId: instance._portletId
									},
									on: {
										complete: saveHandler
									}
								}
							);
						}
					);

					instance._resetButton.detach('click');

					instance._resetButton.on(
						'click',
						function() {
							try {
								instance._curPortlet.set('style', '');
							}
							catch (e) {
								instance._curPortlet.set('style.cssText', '');
							}

							var customStyle = A.one('#lfr-custom-css-block-' + instance._curPortletWrapperId);

							if (customStyle) {
								customStyle.remove(true);
							}

							instance._objData = defaultData;
							instance._setDefaults();
						}
					);

					instance._currentPopup.loadingmask.hide();
				};

				instance._objData = defaultData;

				A.io.request(
					themeDisplay.getPathMain() + '/portlet_configuration/get_look_and_feel',
					{
						data: {
							doAsUserId: themeDisplay.getDoAsUserIdEncoded(),
							p_auth: Liferay.authToken,
							p_l_id: themeDisplay.getPlid(),
							portletId: instance._portletId
						},
						dataType: 'json',
						on: {
							success: function(event, id, obj) {
								var objectData = this.get('responseData');

								if (objectData.hasCssValue) {
									instance._objData = objectData;
								}
								else {
									instance._objData.portletData = objectData.portletData;
								}

								onLookAndFeelComplete();
							}
						}
					}
				);
			},

			_portletConfig: function() {
				var instance = this;

				var portletData = instance._objData.portletData;
				var customTitleInput = instance._customTitleInput;
				var customTitleCheckbox = instance._customTitleCheckbox;
				var showBorders = instance._showBorders;
				var language = instance._portletLanguage;
				var borderNote = instance._borderNote;
				var portletLinksTarget = instance._portletLinksTarget;

				// Use custom title

				customTitleCheckbox.detach('click');

				customTitleCheckbox.on(
					'click',
					function(event) {
						var title;
						var portletTitle = instance._curPortlet.one('.portlet-title');

						var checked = event.currentTarget.get('checked');

						portletData.useCustomTitle = checked;

						if (checked) {
							customTitleInput.set('disabled', false);
							language.set('disabled', false);

							title = Lang.trim(customTitleInput.val());

							if (title == '') {
								title = (portletTitle && portletTitle.text()) || '';
								title = Lang.trim(title);

								customTitleInput.val(title);
							}

							portletData.title = title;

							instance._portletTitles(false, title);
						}
						else {
							customTitleInput.attr('disabled', true);
							language.attr('disabled', true);
							title = instance._defaultPortletTitle;
						}

						if (portletTitle) {
							portletTitle.text(title);
						}
					}
				);

				customTitleInput.detach('keyup');

				customTitleInput.on(
					'keyup',
					function(event) {
						if (!portletData.useCustomTitle) {
							return;
						}

						var portletTitle = instance._curPortlet.one('.portlet-title, .portlet-title-default');

						if (portletTitle) {
							var cruft = portletTitle.html().match(/<\/?[^>]+>|\n|\r|\t/gim);

							if (cruft) {
								cruft = cruft.join('');
							}
							else {
								cruft = '';
							}

							var value = event.currentTarget.val();

							var portletLanguage = instance._portletLanguage.val();

							if (portletLanguage == instance._currentLanguage) {
								portletTitle.html(cruft + value);
							}

							portletData.title = value;
							instance._portletTitles(portletLanguage, value);
						}
					}
				);

				// Show borders

				showBorders.on(
					'change',
					function(event) {
						borderNote.show();

						portletData.showBorders = event.currentTarget.val();
					}
				);

				language.on(
					'change',
					function(event) {
						portletData.language = event.currentTarget.val();

						var title = instance._portletTitles(portletData.language);

						if (portletData.useCustomTitle) {
							customTitleInput.val(title);
						}
					}
				);

				// Point target links to

				portletLinksTarget.on(
					'change',
					function(event) {
						portletData.portletLinksTarget = event.currentTarget.val();
					}
				);
			},

			_portletTitles: function(key, value) {
				var instance = this;

				if (!instance._objData.portletData.titles) {
					instance._objData.portletData.titles = {};
				}

				var portletTitles = instance._objData.portletData.titles;

				if (!key) {
					key = instance._portletLanguage.val();
				}

				if (value == null) {
					var portletTitle = portletTitles[key];

					if (portletTitle) {
						return portletTitle;
					}

					return '';
				}
				else {
					portletTitles[key] = value;

					if (value == '') {
						instance._languageClasses(key, null, true);
					}
					else {
						instance._languageClasses(key);
					}
				}
			},

			_setCheckbox: function(obj, value) {
				var instance = this;

				if (obj) {
					obj.set('checked', value);
				}
			},

			_setDefaults: function() {
				var instance = this;

				var objData = instance._objData;

				var portletData = objData.portletData;
				var textData = objData.textData;
				var bgData = objData.bgData;
				var borderData = objData.borderData;
				var spacingData = objData.spacingData;
				var wapData = objData.wapData;

				if (wapData == null) {
					wapData = {
						initialWindowState: 'NORMAL',
						title: ''
					};

					objData.wapData = wapData;
				}

				var fontStyle = false;
				var fontWeight = false;

				if (textData.fontStyle && textData.fontStyle != 'normal') {
					fontStyle = true;
				}

				if (textData.fontWeight && textData.fontWeight != 'normal') {
					fontWeight = true;
				}

				// Portlet config

				instance._setCheckbox(instance._customTitleCheckbox, portletData.useCustomTitle);
				instance._setSelect(instance._showBorders, portletData.showBorders);
				instance._setSelect(instance._portletLanguage, instance._currentLanguage);
				instance._setSelect(instance._portletLinksTarget, portletData.portletLinksTarget);

				var portletTitles = portletData.titles;
				var portletTitle = instance._portletTitles(portletData.language);

				if (!portletTitle) {
					portletTitle = instance._defaultPortletTitle;
				}

				instance._setInput(instance._customTitleInput, portletTitle);

				if (!portletData.useCustomTitle) {
					instance._customTitleInput.set('disabled', true);
					instance._portletLanguage.set('disabled', true);
				}

				if (portletData.titles) {
					A.each(
						portletData.titles,
						function(item, index, collection) {
							instance._languageClasses(item);
						}
					);
				}

				// Text

				instance._setSelect(instance._fontFamily, textData.fontFamily);
				instance._setCheckbox(instance._fontWeight, fontWeight);
				instance._setCheckbox(instance._fontStyle, fontStyle);
				instance._setSelect(instance._fontSize, textData.fontSize);
				instance._setInput(instance._fontColor, textData.color);
				instance._setSelect(instance._textAlign, textData.textAlign);
				instance._setSelect(instance._textDecoration, textData.textDecoration);
				instance._setSelect(instance._wordSpacing, textData.wordSpacing);
				instance._setSelect(instance._leading, textData.lineHeight);
				instance._setSelect(instance._tracking, textData.letterSpacing);

				// Background

				instance._setInput(instance._backgroundColor, bgData.backgroundColor);

				// Border

				instance._setCheckbox(instance._ufaBorderWidth, borderData.borderWidth.sameForAll);
				instance._setCheckbox(instance._ufaBorderStyle, borderData.borderStyle.sameForAll);
				instance._setCheckbox(instance._ufaBorderColor, borderData.borderColor.sameForAll);

				instance._setInput(instance._borderTopInt, borderData.borderWidth.top.value);
				instance._setSelect(instance._borderTopUnit, borderData.borderWidth.top.unit);
				instance._setInput(instance._borderRightInt, borderData.borderWidth.right.value);
				instance._setSelect(instance._borderRightUnit, borderData.borderWidth.right.unit);
				instance._setInput(instance._borderBottomInt, borderData.borderWidth.bottom.value);
				instance._setSelect(instance._borderBottomUnit, borderData.borderWidth.bottom.unit);
				instance._setInput(instance._borderLeftInt, borderData.borderWidth.left.value);
				instance._setSelect(instance._borderLeftUnit, borderData.borderWidth.left.unit);

				instance._setSelect(instance._borderTopStyle, borderData.borderStyle.top);
				instance._setSelect(instance._borderRightStyle, borderData.borderStyle.right);
				instance._setSelect(instance._borderBottomStyle, borderData.borderStyle.bottom);
				instance._setSelect(instance._borderLeftStyle, borderData.borderStyle.left);

				instance._setInput(instance._borderTopColor, borderData.borderColor.top);
				instance._setInput(instance._borderRightColor, borderData.borderColor.right);
				instance._setInput(instance._borderBottomColor, borderData.borderColor.bottom);
				instance._setInput(instance._borderLeftColor, borderData.borderColor.left);

				// Spacing

				instance._setCheckbox(instance._ufaPadding, spacingData.padding.sameForAll);
				instance._setCheckbox(instance._ufaMargin, spacingData.margin.sameForAll);

				instance._setInput(instance._paddingTopInt, spacingData.padding.top.value);
				instance._setSelect(instance._paddingTopUnit, spacingData.padding.top.unit);
				instance._setInput(instance._paddingRightInt, spacingData.padding.right.value);
				instance._setSelect(instance._paddingRightUnit, spacingData.padding.right.unit);
				instance._setInput(instance._paddingBottomInt, spacingData.padding.bottom.value);
				instance._setSelect(instance._paddingBottomUnit, spacingData.padding.bottom.unit);
				instance._setInput(instance._paddingLeftInt, spacingData.padding.left.value);
				instance._setSelect(instance._paddingLeftUnit, spacingData.padding.left.unit);

				instance._setInput(instance._marginTopInt, spacingData.margin.top.value);
				instance._setSelect(instance._marginTopUnit, spacingData.margin.top.unit);
				instance._setInput(instance._marginRightInt, spacingData.margin.right.value);
				instance._setSelect(instance._marginRightUnit, spacingData.margin.right.unit);
				instance._setInput(instance._marginBottomInt, spacingData.margin.bottom.value);
				instance._setSelect(instance._marginBottomUnit, spacingData.margin.bottom.unit);
				instance._setInput(instance._marginLeftInt, spacingData.margin.left.value);
				instance._setSelect(instance._marginLeftUnit, spacingData.margin.left.unit);

				// Advanced CSS

				var customStyleBlock = A.one('#lfr-custom-css-block-' + instance._curPortletWrapperId);

				var customStyles = customStyleBlock && customStyleBlock.html();

				if (customStyles == '' || customStyles == null) {
					customStyles = objData.advancedData.customCSS;
				}

				instance._setTextarea(instance._customCSS, customStyles);

				instance._setTextarea(instance._customCSSClassName, objData.advancedData.customCSSClassName);

				// WAP styling

				instance._setInput(instance._wapTitleInput, wapData.title);
				instance._setSelect(instance._wapInitialWindowStateSelect, wapData.initialWindowState);
			},

			_setInput: function(obj, value) {
				var instance = this;

				if (obj) {
					obj.val(value);
				}
			},

			_setSelect: function(obj, value) {
				var instance = this;

				if (obj) {
					var option = obj.one('option[value=' + value + ']');

					if (option) {
						option.attr('selected', 'selected');
					}
				}
			},

			_setTextarea: function(obj, value) {
				var instance = this;

				instance._setInput(obj, value);
			},

			_spacingStyles: function() {
				var instance = this;

				var portlet = instance._curPortlet;

				var ufaPadding = instance._ufaPadding;
				var ufaMargin = instance._ufaMargin;

				var spacingData = instance._objData.spacingData;

				// Padding

				var pTop = instance._paddingTopInt;
				var pTopUnit = instance._paddingTopUnit;
				var pRight = instance._paddingRightInt;
				var pRightUnit = instance._paddingRightUnit;
				var pBottom = instance._paddingBottomInt;
				var pBottomUnit = instance._paddingBottomUnit;
				var pLeft = instance._paddingLeftInt;
				var pLeftUnit = instance._paddingLeftUnit;

				var changePadding = function() {
					var styling = {};

					var padding = instance._getCombo(pTop, pTopUnit);

					styling = {padding: padding.both};

					var ufa = ufaPadding.get('checked');

					spacingData.padding.top.value = padding.input;
					spacingData.padding.top.unit = padding.selectBox;

					spacingData.padding.sameForAll = ufa;

					if (!ufa) {
						var extStyling = {};

						extStyling.paddingTop = styling.padding;

						var right = instance._getCombo(pRight, pRightUnit);
						var bottom = instance._getCombo(pBottom, pBottomUnit);
						var left = instance._getCombo(pLeft, pLeftUnit);

						extStyling.paddingRight = right.both;
						extStyling.paddingBottom = bottom.both;
						extStyling.paddingLeft = left.both;

						styling = extStyling;

						spacingData.padding.right.value = right.input;
						spacingData.padding.right.unit = right.selectBox;

						spacingData.padding.bottom.value = bottom.input;
						spacingData.padding.bottom.unit = bottom.selectBox;

						spacingData.padding.left.value = left.input;
						spacingData.padding.left.unit = left.selectBox;
					}

					portlet.setStyles(styling);
				};

				pTop.detach('blur');
				pTop.on('blur', changePadding);

				pRight.detach('blur');
				pRight.on('blur', changePadding);

				pBottom.detach('blur');
				pBottom.on('blur', changePadding);

				pLeft.detach('blur');
				pLeft.on('blur', changePadding);

				pTop.detach('keyup');
				pTop.on('keyup', changePadding);

				pRight.detach('keyup');
				pRight.on('keyup', changePadding);

				pBottom.detach('keyup');
				pBottom.on('keyup', changePadding);

				pLeft.detach('keyup');
				pLeft.on('keyup', changePadding);

				pTopUnit.detach('change');
				pTopUnit.on('change', changePadding);

				pRightUnit.detach('change');
				pRightUnit.on('change', changePadding);

				pBottomUnit.detach('change');
				pBottomUnit.on('change', changePadding);

				pLeftUnit.detach('change');
				pLeftUnit.on('change', changePadding);

				ufaPadding.detach('change');
				ufaPadding.on('change', changePadding);

				// Margin

				var mTop = instance._marginTopInt;
				var mTopUnit = instance._marginTopUnit;
				var mRight = instance._marginRightInt;
				var mRightUnit = instance._marginRightUnit;
				var mBottom = instance._marginBottomInt;
				var mBottomUnit = instance._marginBottomUnit;
				var mLeft = instance._marginLeftInt;
				var mLeftUnit = instance._marginLeftUnit;

				var changeMargin = function() {
					var styling = {};

					var margin = instance._getCombo(mTop, mTopUnit);

					styling = {margin: margin.both};

					var ufa = ufaMargin.get('checked');

					spacingData.margin.top.value = margin.input;
					spacingData.margin.top.unit = margin.selectBox;

					spacingData.margin.sameForAll = ufa;

					if (!ufa) {
						var extStyling = {};

						extStyling.marginTop = styling.margin;

						var right = instance._getCombo(mRight, mRightUnit);
						var bottom = instance._getCombo(mBottom, mBottomUnit);
						var left = instance._getCombo(mLeft, mLeftUnit);

						extStyling.marginRight = right.both;
						extStyling.marginBottom = bottom.both;
						extStyling.marginLeft = left.both;

						styling = extStyling;

						spacingData.margin.right.value = right.input;
						spacingData.margin.right.unit = right.selectBox;

						spacingData.margin.bottom.value = bottom.input;
						spacingData.margin.bottom.unit = bottom.selectBox;

						spacingData.margin.left.value = left.input;
						spacingData.margin.left.unit = left.selectBox;
					}

					portlet.setStyles(styling);
				};

				mTop.detach('blur');
				mTop.on('blur', changeMargin);

				mRight.detach('blur');
				mRight.on('blur', changeMargin);

				mBottom.detach('blur');
				mBottom.on('blur', changeMargin);

				mLeft.detach('blur');
				mLeft.on('blur', changeMargin);

				mTop.detach('keyup');
				mTop.on('keyup', changeMargin);

				mRight.detach('keyup');
				mRight.on('keyup', changeMargin);

				mBottom.detach('keyup');
				mBottom.on('keyup', changeMargin);

				mLeft.detach('keyup');
				mLeft.on('keyup', changeMargin);

				mTopUnit.detach('change');
				mTopUnit.on('change', changeMargin);

				mRightUnit.detach('change');
				mRightUnit.on('change', changeMargin);

				mBottomUnit.detach('change');
				mBottomUnit.on('change', changeMargin);

				mLeftUnit.detach('change');
				mLeftUnit.on('change', changeMargin);

				ufaMargin.detach('change');
				ufaMargin.on('change', changeMargin);
			},

			_textStyles: function() {
				var instance = this;

				var portlet = instance._curPortlet;
				var fontFamily = instance._fontFamily;
				var fontBold = instance._fontWeight;
				var fontItalic = instance._fontStyle;
				var fontSize = instance._fontSize;
				var fontColor = instance._fontColor;
				var textAlign = instance._textAlign;
				var textDecoration = instance._textDecoration;
				var wordSpacing = instance._wordSpacing;
				var leading = instance._leading;
				var tracking = instance._tracking;

				var textData = instance._objData.textData;

				// Font family

				fontFamily.detach('change');

				fontFamily.on(
					'change',
					function(event) {
						var fontFamily = event.currentTarget.val();

						portlet.setStyle('fontFamily', fontFamily);

						textData.fontFamily = fontFamily;
					}
				);

				// Font style

				fontBold.detach('click');

				fontBold.on(
					'click',
					function(event) {
						var style = 'normal';

						if (event.currentTarget.get('checked')) {
							style = 'bold';
						}

						portlet.setStyle('fontWeight', style);

						textData.fontWeight = style;
					}
				);

				fontItalic.detach('click');

				fontItalic.on(
					'click',
					function(event) {
						var style = 'normal';

						if (event.currentTarget.get('checked')) {
							style = 'italic';
						}

						portlet.setStyle('fontStyle', style);

						textData.fontStyle = style;
					}
				);

				// Font size

				fontSize.detach('change');

				fontSize.on(
					'change',
					function(event) {
						var fontSize = event.currentTarget.val();

						portlet.setStyle('fontSize', fontSize);

						textData.fontSize = fontSize;
					}
				);

				// Font color

				var changeColor = function(obj) {
					var color = obj.val();

					if (color) {
						portlet.setStyle('color', color);

						textData.color = color;
					}
				};

				var hexValue = fontColor.val().replace('#', '');

				if (!instance._fontColorPicker) {
					instance._fontColorPicker = new A.ColorPicker(
						{
							triggerParent: fontColor.get('parentNode'),
							zIndex: 9999
						}
					).render(instance._currentPopup.get('boundingBox'));
				}

				var fontColorPicker = instance._fontColorPicker;

				var afterColorChange = function() {
					fontColor.val('#' + this.get('hex'));

					changeColor(fontColor);
				};

				if (instance._afterFontColorChangeHandler) {
					instance._afterFontColorChangeHandler.detach();
				}

				instance._afterFontColorChangeHandler = fontColorPicker.after('colorChange', afterColorChange);

				fontColorPicker.set('hex', hexValue);

				fontColor.detach('blur');

				fontColor.on(
					'blur',
					function(event) {
						changeColor(event.currentTarget);
					}
				);

				// Text alignment

				textAlign.detach('change');

				textAlign.on(
					'change',
					function(event) {
						var textAlign = event.currentTarget.val();

						portlet.setStyle('textAlign', textAlign);

						textData.textAlign = textAlign;
					}
				);

				// Text decoration

				textDecoration.detach('change');

				textDecoration.on(
					'change',
					function(event) {
						var decoration = event.currentTarget.val();

						portlet.setStyle('textDecoration', decoration);

						textData.textDecoration = decoration;
					}
				);

				// Word spacing

				wordSpacing.detach('change');

				wordSpacing.on(
					'change',
					function(event) {
						var spacing = event.currentTarget.val();

						portlet.setStyle('wordSpacing', spacing);

						textData.wordSpacing = spacing;
					}
				);

				// Line height

				leading.detach('change');

				leading.on(
					'change',
					function(event) {
						var leading = event.currentTarget.val();

						portlet.setStyle('lineHeight', leading);

						textData.lineHeight = leading;
					}
				);

				// Letter spacing

				tracking.detach('change');

				tracking.on(
					'change',
					function(event) {
						var tracking = event.currentTarget.val();

						portlet.setStyle('letterSpacing', tracking);

						textData.letterSpacing = tracking;
					}
				);
			}
		};

		Liferay.PortletCSS = PortletCSS;
	},
	'',
	{
		requires: ['aui-color-picker', 'aui-dialog', 'aui-io-request', 'aui-tabs-base']
	}
);