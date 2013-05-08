AUI.add(
	'liferay-look-and-feel',
	function(A) {
		var Browser = Liferay.Browser;
		var Lang = A.Lang;

		var REGEX_IGNORED_CLASSES = /boundary|draggable/;

		var REGEX_IGNORED_CLASSES_PORTLET = /(?:^|\s)portlet(?=\s|$)/g;

		var REGEX_VALID_CLASSES = /(?:([\w\d-]+)-)?portlet(?:-?([\w\d-]+-?))?/g;

		var BACKGROUND_COLOR = 'backgroundColor';

		var BLUR = 'blur';

		var BOLD = 'bold';

		var BOUNDING_BOX = 'boundingBox';

		var CHANGE = 'change';

		var CHECKBOX = 'checkbox';

		var CHECKED = 'checked';

		var CLASS_NAME = 'className';

		var CLICK = 'click';

		var COLOR = 'color';

		var DISABLED = 'disabled';

		var EMPTY = '';

		var ERROR = 'error';

		var EXCLUSIVE = 'exclusive';

		var FIELDSET = 'fieldset';

		var FIRST_CHILD = 'firstChild';

		var FOCUSED = 'focused';

		var FONT_FAMILY = 'fontFamily';

		var FONT_SIZE = 'fontSize';

		var FONT_STYLE = 'fontStyle';

		var FONT_WEIGHT = 'fontWeight';

		var FORM = 'form';

		var HEAD = 'head';

		var HEX = 'hex';

		var HIDE = 'hide';

		var HOST = 'host';

		var ID = 'id';

		var INSTANTIATED = 'instantiated';

		var ITALIC = 'italic';

		var JSON = 'json';

		var KEYUP = 'keyup';

		var LETTER_SPACING = 'letterSpacing';

		var LINE_HEIGHT = 'lineHeight';

		var OK = 'ok';

		var OPACITY = 'opacity';

		var PX = 'px';

		var RESPONSE_DATA = 'responseData';

		var SELECT = 'select';

		var SELECTED = 'selected';

		var SHOW = 'show';

		var STYLE = 'style';

		var SUCCESS = 'success';

		var TEXT_ALIGN = 'textAlign';

		var TEXT_DECORATION = 'textDecoration';

		var TRANSPARENT = 'transparent';

		var TYPE = 'type';

		var WORD_SPACING = 'wordSpacing';

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
						instance._curPortletWrapperId = instance._curPortlet.attr(ID);
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
							instance._currentPopup = Liferay.Util.Window.getWindow(
								{
									dialog: {
										on: {
											visibleChange: function(event) {
												if (!event.newVal && Browser.isIe() && Browser.getMajorVersion() == 6) {
													window.location.reload(true);
												}

												instance._destroyColorPickers();
											}
										}
									},
									title: Liferay.Language.get('look-and-feel')
								}
							);

							instance._currentPopup.plug(
								[
									{
										fn: A.Plugin.IO,
										cfg: {
											after: {
												success: function(event) {
													var host = this.get(HOST);
													var boundingBox = host.get(BOUNDING_BOX);

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
												p_p_state: EXCLUSIVE,
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

				var setColor = function(color) {
					var cssColor = color;

					if ((color === EMPTY) || (color === '#')) {
						cssColor = TRANSPARENT;

						color = EMPTY;
					}

					portlet.setStyle(BACKGROUND_COLOR, cssColor);

					bgData.backgroundColor = color;
				};

				var hexValue = backgroundColor.val().replace('#', EMPTY);

				if (!instance._backgroundColorPicker) {
					instance._backgroundColorPicker = new A.ColorPickerPopover(
						{
							trigger: backgroundColor,
							zIndex: 1000
						}
					).render(instance._currentPopup.get(BOUNDING_BOX));
				}

				var backgroundColorPicker = instance._backgroundColorPicker;

				var afterColorChange = function(event) {
					var color = event.color;

					backgroundColor.val(event.color);

					backgroundColor.setStyle(BACKGROUND_COLOR, color);

					setColor(color);
				};

				if (instance._afterBackgroundColorChangeHandler) {
					instance._afterBackgroundColorChangeHandler.detach();
				}

				instance._afterBackgroundColorChangeHandler = backgroundColorPicker.after(SELECT, afterColorChange);
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

					var ufa = ufaWidth.get(CHECKED);

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

				wTopInt.detach(BLUR);
				wTopInt.on(BLUR, changeWidth);

				wTopInt.detach(KEYUP);
				wTopInt.on(KEYUP, changeWidth);

				wRightInt.detach(BLUR);
				wRightInt.on(BLUR, changeWidth);

				wRightInt.detach(KEYUP);
				wRightInt.on(KEYUP, changeWidth);

				wBottomInt.detach(BLUR);
				wBottomInt.on(BLUR, changeWidth);

				wBottomInt.detach(KEYUP);
				wBottomInt.on(KEYUP, changeWidth);

				wLeftInt.detach(BLUR);
				wLeftInt.on(BLUR, changeWidth);

				wLeftInt.detach(KEYUP);
				wLeftInt.on(KEYUP, changeWidth);

				wTopUnit.detach(CHANGE);
				wTopUnit.on(CHANGE, changeWidth);

				wRightUnit.detach(CHANGE);
				wRightUnit.on(CHANGE, changeWidth);

				wBottomUnit.detach(CHANGE);
				wBottomUnit.on(CHANGE, changeWidth);

				wLeftUnit.detach(CHANGE);
				wLeftUnit.on(CHANGE, changeWidth);

				ufaWidth.detach(CHANGE);
				ufaWidth.on(CHANGE, changeWidth);

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

					var ufa = ufaStyle.get(CHECKED);

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

				sTopStyle.detach(CHANGE);
				sTopStyle.on(CHANGE, changeStyle);

				sRightStyle.detach(CHANGE);
				sRightStyle.on(CHANGE, changeStyle);

				sBottomStyle.detach(CHANGE);
				sBottomStyle.on(CHANGE, changeStyle);

				sLeftStyle.detach(CHANGE);
				sLeftStyle.on(CHANGE, changeStyle);

				ufaStyle.detach(CHANGE);
				ufaStyle.on(CHANGE, changeStyle);

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

					var ufa = ufaColor.get(CHECKED);

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

				var popupBoundingBox = instance._currentPopup.get(BOUNDING_BOX);

				A.each(
					[cTopColor, cRightColor, cBottomColor, cLeftColor],
					function(item, index, collection) {
						var hexValue = item.val().replace('#', EMPTY);

						var borderLocation = '_borderColorPicker' + index;

						if (!instance[borderLocation]) {
							instance[borderLocation] = new A.ColorPickerPopover(
								{
									trigger: item,
									zIndex: 1000
								}
							).render(popupBoundingBox);
						}

						var borderColorPicker = instance[borderLocation];

						var afterColorChange = function(event) {
							var color = event.color;

							item.val(color);

							item.setStyle(BACKGROUND_COLOR, color);

							changeColor(color);
						};

						var borderColorChangeHandler = '_afterBorderColorChangeHandler' + index;

						if (instance[borderColorChangeHandler]) {
							instance[borderColorChangeHandler].detach();
						}

						instance[borderColorChangeHandler] = borderColorPicker.after(SELECT, afterColorChange);

						borderColorPicker.set(HEX, hexValue);
					}
				);

				cTopColor.detach(BLUR);
				cTopColor.on(BLUR, changeColor);

				cRightColor.detach(BLUR);
				cRightColor.on(BLUR, changeColor);

				cBottomColor.detach(BLUR);
				cBottomColor.on(BLUR, changeColor);

				cLeftColor.detach(BLUR);
				cLeftColor.on(BLUR, changeColor);

				cTopColor.detach(KEYUP);
				cTopColor.on(KEYUP, changeColor);

				cRightColor.detach(KEYUP);
				cRightColor.on(KEYUP, changeColor);

				cBottomColor.detach(KEYUP);
				cBottomColor.on(KEYUP, changeColor);

				cLeftColor.detach(KEYUP);
				cLeftColor.on(KEYUP, changeColor);

				ufaColor.detach(CHANGE);
				ufaColor.on(CHANGE, changeColor);
			},

			_cssStyles: function() {
				var instance = this;

				var portlet = instance._curPortlet;
				var portletBoundary = instance._portletBoundary;

				var customCSS = instance._getNodeById('lfr-custom-css');
				var customCSSClassName = instance._getNodeById('lfr-custom-css-class-name');
				var customCSSContainer = customCSS;
				var customCSSClassNameContainer = customCSSClassName;
				var customPortletNoteHTML = '<p class="alert alert-info form-hint"></p>';
				var customPortletNote = A.one('#lfr-portlet-info');
				var refreshText = EMPTY;

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
							CLASS_NAME: EMPTY,
							id: 'lfr-refresh-styles'
						}
					);
				}

				if (!customPortletNote) {
					customPortletNote = A.Node.create(customPortletNoteHTML);
					customCSSClassNameContainer.placeBefore(customPortletNote);

					customPortletNote.attr(ID, 'lfr-portlet-info');
				}

				customPortletNote.html(portletInfoText);

				Liferay.Util.enableTextareaTabs(customCSS.getDOM());

				if (!Browser.isSafari()) {
					refreshText = Liferay.Language.get('update-the-styles-on-this-page');

					var refreshLink = A.Node.create('<a href="javascript:;">' + refreshText + '</a>');

					var customStyleBlock = A.one('#lfr-custom-css-block-' + portletId);

					if (!customStyleBlock) {

						// Do not modify. This is a workaround for an IE bug.

						var styleEl = document.createElement(STYLE);

						styleEl.id = 'lfr-custom-css-block-' + portletId;
						styleEl.className = 'lfr-custom-css-block';
						styleEl.setAttribute(TYPE, 'text/css');

						document.getElementsByTagName(HEAD)[0].appendChild(styleEl);
					}
					else {
						styleEl = customStyleBlock.getDOM();
					}

					var refreshStyles = function() {
						var customStyles = customCSS.val();

						customStyles = customStyles.replace(/<script[^>]*>([\u0001-\uFFFF]*?)<\/script>/gim, EMPTY);
						customStyles = customStyles.replace(/<\/?[^>]+>/gi, EMPTY);

						if (styleEl.styleSheet) { // for IE only
							if (customStyles == EMPTY) {

								// Do not modify. This is a workaround for an IE bug.

								customStyles = '<!---->';
							}

							styleEl.styleSheet.cssText = customStyles;
						}
						else {
							A.one(styleEl).html(customStyles);
						}
					};

					refreshLink.detach(CLICK);
					refreshLink.on(CLICK, refreshStyles);

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

					var updateOnTypeHolder = A.Node.create('<span class="field"><span class="field-content"></span></span>');
					var updateOnTypeLabel = A.Node.create('<label>' + Liferay.Language.get('update-my-styles-as-i-type') + ' </label>');

					updateOnType = A.Node.create('<input id="lfr-update-on-type" type=CHECKBOX />');

					updateOnTypeLabel.appendChild(updateOnType);
					updateOnTypeHolder.get(FIRST_CHILD).appendChild(updateOnTypeLabel);

					customCSSContainer.placeAfter(insertContainer);

					insertContainer.appendChild(addIdLink);

					insertContainer.append('<br />');

					insertContainer.appendChild(addClassLink);
					insertContainer.appendChild(updateOnTypeHolder);

					insertContainer.after(customNote);
				}

				updateOnType.on(
					CLICK,
					function(event) {
						if (event.currentTarget.get(CHECKED)) {
							customNote.hide();
							customCSS.on(KEYUP, refreshStyles);
						}
						else {
							customNote.show();
							customCSS.detach(KEYUP, refreshStyles);
						}
					}
				);

				addIdLink.detach(CLICK);

				addIdLink.on(
					CLICK,
					function() {
						instance._insertCustomCSSValue(customCSS, '#' + portletId);
					}
				);

				addClassLink.detach(CLICK);

				addClassLink.on(
					CLICK,
					function() {
						instance._insertCustomCSSValue(customCSS, instance._getCSSClasses(portletBoundary, portlet));
					}
				);
			},

			_destroyColorPickers: function() {
				var instance = this;

				for (var i = 0; i < 4; i++) {
					var borderLocation = '_borderColorPicker' + i;

					if (instance[borderLocation]) {
						instance[borderLocation].destroy();

						instance[borderLocation] = null;
					}
				}

				if (instance._backgroundColorPicker) {
					instance._backgroundColorPicker.destroy();

					instance._backgroundColorPicker = null;
				}

				if (instance._fontColorPicker) {
					instance._fontColorPicker.destroy();

					instance._fontColorPicker = null;
				}
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

				var portletClasses = EMPTY;

				if (portlet && portlet != portletBoundary) {
					portletClasses = portlet.attr(CLASS_NAME).replace(REGEX_IGNORED_CLASSES_PORTLET, EMPTY);

					portletClasses = Lang.trim(portletClasses).replace(/\s+/g, '.');

					if (portletClasses) {
						portletClasses = ' .' + portletClasses;
					}
				}

				var boundaryClasses = [];

				portletBoundary.attr(CLASS_NAME).replace(
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

				if (output == EMPTY || isNaN(output)) {
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
						option.removeClass(FOCUSED);
					}
					else {
						option.addClass(FOCUSED);
					}
				}
			},

			_loadContent: function(instantiated) {
				var instance = this;

				var newPanel = instance._newPanel;

				if (!instantiated) {
					newPanel.addClass(INSTANTIATED);
					instance._portletBoundaryIdVar = A.one('#portlet-boundary-id');

					// Portlet config

					var portletTitle = instance._curPortlet.one('.portlet-title');

					instance._defaultPortletTitle = Lang.trim(portletTitle ? portletTitle.text() : EMPTY);

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
						srcNode: newPanel,
						panelNode: newPanel.one('.tab-pane')
					}
				).render();

				newPanel.show();

				instance._currentPopup.loadingmask.refreshMask();

				newPanel.all('.lfr-colorpicker-img').remove(true);

				instance._portletMsgResponse = A.one('#lfr-portlet-css-response');

				if (instance._portletMsgResponse) {
					instance._portletMsgResponse.hide();
				}

				var defaultData = {
					advancedData: {
						customCSS: EMPTY
					},

					bgData: {
						backgroundColor: EMPTY,
						backgroundImage: EMPTY,
						backgroundPosition: {
							left: {
								unit: PX,
								value: EMPTY
							},
							top: {
								unit: PX,
								value: EMPTY
							}
						},
						backgroundRepeat: EMPTY,
						useBgImage: false
					},

					borderData: {
						borderColor: {
							bottom: EMPTY,
							left: EMPTY,
							right: EMPTY,
							sameForAll: true,
							top: EMPTY
						},

						borderStyle: {
							bottom: EMPTY,
							left: EMPTY,
							right: EMPTY,
							sameForAll: true,
							top: EMPTY
						},

						borderWidth: {
							bottom: {
								unit: PX,
								value: EMPTY
							},
							left: {
								unit: PX,
								value: EMPTY
							},
							right: {
								unit: PX,
								value: EMPTY
							},
							sameForAll: true,
							top: {
								unit: PX,
								value: EMPTY
							}
						}
					},

					portletData: {
						language: 'en_US',
						portletLinksTarget: EMPTY,
						title: EMPTY,
						titles: {},
						useCustomTitle: false
					},

					spacingData: {
						margin: {
							bottom: {
								unit: PX,
								value: EMPTY
							},
							left: {
								unit: PX,
								value: EMPTY
							},
							right: {
								unit: PX,
								value: EMPTY
							},
							sameForAll: true,
							top: {
								unit: PX,
								value: EMPTY
							}
						},
						padding: {
							bottom: {
								unit: PX,
								value: EMPTY
							},
							left: {
								unit: PX,
								value: EMPTY
							},
							right: {
								unit: PX,
								value: EMPTY
							},
							sameForAll: true,
							top: {
								unit: PX,
								value: EMPTY
							}
						}

					},

					textData: {
						color: EMPTY,
						fontFamily: EMPTY,
						fontSize: EMPTY,
						fontStyle: EMPTY,
						fontWeight: EMPTY,
						letterSpacing: EMPTY,
						lineHeight: EMPTY,
						textAlign: EMPTY,
						textDecoration: EMPTY,
						wordSpacing: EMPTY
					},

					wapData: {
						initialWindowState: 'NORMAL',
						title: EMPTY
					}
				};

				var onLookAndFeelComplete = function() {
					instance._portletBoundaryIdVar.val(instance._curPortletWrapperId);

					instance._destroyColorPickers();

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

						var fieldset = checkBox.ancestor(FIELDSET);

						var otherHolders = fieldset.all('.field-row');
						var firstIndex = 0;

						if (!otherHolders.size()) {
							otherHolders = fieldset.all('.field-content');
							firstIndex = 1;
						}

						var checked = item.get(CHECKED);

						otherHolders.each(
							function(holderItem, holderIndex, holderCollection) {
								if (holderIndex > firstIndex) {
									var fields = holderItem.all('input, select');
									var colorPickerImages = holderItem.all('.buttonitem');

									var action = SHOW;
									var disabled = false;
									var opacity = 1;

									if (checked) {
										action = HIDE;
										disabled = true;
										opacity = 0.3;
									}

									holderItem.setStyle(OPACITY, opacity);
									fields.set(DISABLED, disabled);
									colorPickerImages[action]();
								}
							}
						);
					};

					useForAll.detach(CLICK);

					useForAll.on(
						CLICK,
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
						var message = EMPTY;
						var messageClass = EMPTY;
						var type = SUCCESS;

						if (obj.statusText.toLowerCase() != OK) {
							type = ERROR;
						}

						if (type == SUCCESS) {
							message = Liferay.Language.get('your-request-processed-successfully');
							messageClass = 'alert alert-success';
						}
						else {
							message = Liferay.Language.get('your-settings-could-not-be-saved');
							messageClass = 'alert alert-error';
						}

						if (!ajaxResponseMsg) {
							ajaxResponse = A.Node.create(ajaxResponseHTML);
							newPanel.one(FORM).prepend(ajaxResponse);

							instance._portletMsgResponse = ajaxResponse;
						}

						ajaxResponse.addClass(messageClass);
						ajaxResponse.html(message);
						ajaxResponse.show();
					};

					instance._saveButton.detach(CLICK);

					instance._saveButton.on(
						CLICK,
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

					instance._resetButton.detach(CLICK);

					instance._resetButton.on(
						CLICK,
						function() {
							try {
								instance._curPortlet.set(STYLE, EMPTY);
							}
							catch (e) {
								instance._curPortlet.set('style.cssText', EMPTY);
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
						dataType: JSON,
						on: {
							success: function(event, id, obj) {
								var objectData = this.get(RESPONSE_DATA);

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

				customTitleCheckbox.detach(CLICK);

				customTitleCheckbox.on(
					CLICK,
					function(event) {
						var title;
						var portletTitle = instance._curPortlet.one('.portlet-title');

						var checked = event.currentTarget.get(CHECKED);

						portletData.useCustomTitle = checked;

						if (checked) {
							customTitleInput.set(DISABLED, false);
							language.set(DISABLED, false);

							title = Lang.trim(customTitleInput.val());

							if (title == EMPTY) {
								title = (portletTitle && portletTitle.text()) || EMPTY;
								title = Lang.trim(title);

								customTitleInput.val(title);
							}

							portletData.title = title;

							instance._portletTitles(false, title);
						}
						else {
							customTitleInput.attr(DISABLED, true);
							language.attr(DISABLED, true);
							title = instance._defaultPortletTitle;
						}

						if (portletTitle) {
							portletTitle.text(title);
						}
					}
				);

				customTitleInput.detach(KEYUP);

				customTitleInput.on(
					KEYUP,
					function(event) {
						if (!portletData.useCustomTitle) {
							return;
						}

						var portletTitle = instance._curPortlet.one('.portlet-title, .portlet-title-default');

						if (portletTitle) {
							var cruft = portletTitle.html().match(/<\/?[^>]+>|\n|\r|\t/gim);

							if (cruft) {
								cruft = cruft.join(EMPTY);
							}
							else {
								cruft = EMPTY;
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
					CHANGE,
					function(event) {
						borderNote.show();

						portletData.showBorders = event.currentTarget.val();
					}
				);

				language.on(
					CHANGE,
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
					CHANGE,
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

					return EMPTY;
				}
				else {
					portletTitles[key] = value;

					if (value == EMPTY) {
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
					obj.set(CHECKED, value);
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
						title: EMPTY
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
					instance._customTitleInput.set(DISABLED, true);
					instance._portletLanguage.set(DISABLED, true);
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
				instance._setInputColor(instance._fontColor, textData.color);
				instance._setSelect(instance._textAlign, textData.textAlign);
				instance._setSelect(instance._textDecoration, textData.textDecoration);
				instance._setSelect(instance._wordSpacing, textData.wordSpacing);
				instance._setSelect(instance._leading, textData.lineHeight);
				instance._setSelect(instance._tracking, textData.letterSpacing);

				// Background

				instance._setInput(instance._backgroundColor, bgData.backgroundColor);
				instance._setInputColor(instance._backgroundColor, bgData.backgroundColor);

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
				instance._setInputColor(instance._borderTopColor, borderData.borderColor.top);

				instance._setInput(instance._borderRightColor, borderData.borderColor.right);
				instance._setInputColor(instance._borderRightColor, borderData.borderColor.right);

				instance._setInput(instance._borderBottomColor, borderData.borderColor.bottom);
				instance._setInputColor(instance._borderBottomColor, borderData.borderColor.bottom);

				instance._setInput(instance._borderLeftColor, borderData.borderColor.left);
				instance._setInputColor(instance._borderLeftColor, borderData.borderColor.left);

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

				if (customStyles == EMPTY || customStyles == null) {
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

			_setInputColor: function(obj, value) {
				if (obj) {
					obj.setStyle(BACKGROUND_COLOR, value);
				}
			},

			_setSelect: function(obj, value) {
				var instance = this;

				if (obj) {
					var option = obj.one('option[value=' + value + ']');

					if (option) {
						option.attr(SELECTED, SELECTED);
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

					var ufa = ufaPadding.get(CHECKED);

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

				pTop.detach(BLUR);
				pTop.on(BLUR, changePadding);

				pRight.detach(BLUR);
				pRight.on(BLUR, changePadding);

				pBottom.detach(BLUR);
				pBottom.on(BLUR, changePadding);

				pLeft.detach(BLUR);
				pLeft.on(BLUR, changePadding);

				pTop.detach(KEYUP);
				pTop.on(KEYUP, changePadding);

				pRight.detach(KEYUP);
				pRight.on(KEYUP, changePadding);

				pBottom.detach(KEYUP);
				pBottom.on(KEYUP, changePadding);

				pLeft.detach(KEYUP);
				pLeft.on(KEYUP, changePadding);

				pTopUnit.detach(CHANGE);
				pTopUnit.on(CHANGE, changePadding);

				pRightUnit.detach(CHANGE);
				pRightUnit.on(CHANGE, changePadding);

				pBottomUnit.detach(CHANGE);
				pBottomUnit.on(CHANGE, changePadding);

				pLeftUnit.detach(CHANGE);
				pLeftUnit.on(CHANGE, changePadding);

				ufaPadding.detach(CHANGE);
				ufaPadding.on(CHANGE, changePadding);

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

					var ufa = ufaMargin.get(CHECKED);

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

				mTop.detach(BLUR);
				mTop.on(BLUR, changeMargin);

				mRight.detach(BLUR);
				mRight.on(BLUR, changeMargin);

				mBottom.detach(BLUR);
				mBottom.on(BLUR, changeMargin);

				mLeft.detach(BLUR);
				mLeft.on(BLUR, changeMargin);

				mTop.detach(KEYUP);
				mTop.on(KEYUP, changeMargin);

				mRight.detach(KEYUP);
				mRight.on(KEYUP, changeMargin);

				mBottom.detach(KEYUP);
				mBottom.on(KEYUP, changeMargin);

				mLeft.detach(KEYUP);
				mLeft.on(KEYUP, changeMargin);

				mTopUnit.detach(CHANGE);
				mTopUnit.on(CHANGE, changeMargin);

				mRightUnit.detach(CHANGE);
				mRightUnit.on(CHANGE, changeMargin);

				mBottomUnit.detach(CHANGE);
				mBottomUnit.on(CHANGE, changeMargin);

				mLeftUnit.detach(CHANGE);
				mLeftUnit.on(CHANGE, changeMargin);

				ufaMargin.detach(CHANGE);
				ufaMargin.on(CHANGE, changeMargin);
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

				fontFamily.detach(CHANGE);

				fontFamily.on(
					CHANGE,
					function(event) {
						var fontFamily = event.currentTarget.val();

						portlet.setStyle(FONT_FAMILY, fontFamily);

						textData.fontFamily = fontFamily;
					}
				);

				// Font style

				fontBold.detach(CLICK);

				fontBold.on(
					CLICK,
					function(event) {
						var style = 'normal';

						if (event.currentTarget.get(CHECKED)) {
							style = BOLD;
						}

						portlet.setStyle(FONT_WEIGHT, style);

						textData.fontWeight = style;
					}
				);

				fontItalic.detach(CLICK);

				fontItalic.on(
					CLICK,
					function(event) {
						var style = NORMAL;

						if (event.currentTarget.get(CHECKED)) {
							style = ITALIC;
						}

						portlet.setStyle(FONT_STYLE, style);

						textData.fontStyle = style;
					}
				);

				// Font size

				fontSize.detach(CHANGE);

				fontSize.on(
					CHANGE,
					function(event) {
						var fontSize = event.currentTarget.val();

						portlet.setStyle(FONT_SIZE, fontSize);

						textData.fontSize = fontSize;
					}
				);

				// Font color

				var changeColor = function(color) {
					if (color) {
						portlet.setStyle(COLOR, color);

						textData.color = color;
					}
				};

				var hexValue = fontColor.val().replace('#', EMPTY);

				if (!instance._fontColorPicker) {
					instance._fontColorPicker = new A.ColorPickerPopover(
						{
							trigger: fontColor,
							zIndex: 1000
						}
					).render(instance._currentPopup.get(BOUNDING_BOX));
				}

				var fontColorPicker = instance._fontColorPicker;

				var afterColorChange = function(event) {
					var color = event.color;

					fontColor.val(color);

					fontColor.setStyle(BACKGROUND_COLOR, color);

					changeColor(color);
				};

				if (instance._afterFontColorChangeHandler) {
					instance._afterFontColorChangeHandler.detach();
				}

				instance._afterFontColorChangeHandler = fontColorPicker.after(SELECT, afterColorChange);

				// Text alignment

				textAlign.detach(CHANGE);

				textAlign.on(
					CHANGE,
					function(event) {
						var textAlign = event.currentTarget.val();

						portlet.setStyle(TEXT_ALIGN, textAlign);

						textData.textAlign = textAlign;
					}
				);

				// Text decoration

				textDecoration.detach(CHANGE);

				textDecoration.on(
					CHANGE,
					function(event) {
						var decoration = event.currentTarget.val();

						portlet.setStyle(TEXT_DECORATION, decoration);

						textData.textDecoration = decoration;
					}
				);

				// Word spacing

				wordSpacing.detach(CHANGE);

				wordSpacing.on(
					CHANGE,
					function(event) {
						var spacing = event.currentTarget.val();

						portlet.setStyle(WORD_SPACING, spacing);

						textData.wordSpacing = spacing;
					}
				);

				// Line height

				leading.detach(CHANGE);

				leading.on(
					CHANGE,
					function(event) {
						var leading = event.currentTarget.val();

						portlet.setStyle(LINE_HEIGHT, leading);

						textData.lineHeight = leading;
					}
				);

				// Letter spacing

				tracking.detach(CHANGE);

				tracking.on(
					CHANGE,
					function(event) {
						var tracking = event.currentTarget.val();

						portlet.setStyle(LETTER_SPACING, tracking);

						textData.letterSpacing = tracking;
					}
				);
			}
		};

		Liferay.PortletCSS = PortletCSS;
	},
	'',
	{
		requires: ['aui-color-picker-popover', 'aui-io-plugin-deprecated', 'aui-io-request', 'aui-tabview', 'liferay-util-window']
	}
);