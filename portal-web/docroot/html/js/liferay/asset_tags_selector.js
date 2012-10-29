AUI.add(
	'liferay-asset-tags-selector',
	function(A) {
		var Lang = A.Lang;

		var AArray = A.Array;

		var NAME = 'tagselector';

		var CSS_INPUT_NODE = 'lfr-tag-selector-input';

		var CSS_NO_MATCHES = 'no-matches';

		var CSS_POPUP = 'lfr-tag-selector-popup';

		var CSS_TAGS_LIST = 'lfr-tags-selector-list';

		var MAP_INVALID_CHARACTERS = AArray.hash(
			[
				'&',
				'\'',
				'@',
				'\\',
				']',
				'}',
				':',
				',',
				'=',
				'>',
				'/',
				'<',
				'\n',
				'[',
				'{',
				'%',
				'|',
				'+',
				'#',
				'?',
				'"',
				'\r',
				';',
				'*',
				'~'
			]
		);

		var TPL_CHECKED = ' checked="checked" ';

		var TPL_LOADING = '<div class="loading-animation" />';

		var TPL_TAG = new A.Template(
			'<fieldset class="{[(!values.tags || !values.tags.length) ? "', CSS_NO_MATCHES, '" : "', STR_BLANK ,'" ]}">',
				'<tpl for="tags">',
					'<label title="{name}"><input {checked} type="checkbox" value="{name}" />{name}</label>',
				'</tpl>',
				'<div class="lfr-tag-message">{message}</div>',
			'</fieldset>'
		);

		var TPL_URL_SUGGESTIONS = 'http://search.yahooapis.com/ContentAnalysisService/V1/termExtraction?appid=YahooDemo&output=json&context={context}';

		var TPL_TAGS_CONTAINER = '<div class="' + CSS_TAGS_LIST + '"></div>';

		var STR_BLANK = '';

		/**
		 * OPTIONS
		 *
		 * Required
		 * className (string): The class name of the current asset.
		 * curEntries (string): The current tags.
		 * instanceVar {string}: The instance variable for this class.
		 * hiddenInput {string}: The hidden input used to pass in the current tags.
		 * textInput {string}: The text input for users to add tags.
		 * summarySpan {string}: The summary span to show the current tags.
		 *
		 * Optional
		 * focus {boolean}: Whether the text input should be focused.
		 * portalModelResource {boolean}: Whether the asset model is on the portal level.
		 *
		 * Callbacks
		 * contentCallback {function}: Called to get suggested tags.
		 */

		var AssetTagsSelector = A.Component.create(
			{
				ATTRS: {
					allowAnyEntry: {
						value: true
					},
					allowSuggestions: {
						value: false
					},
					className: {
						value: null
					},
					contentCallback: {
						value: null
					},
					curEntries: {
						setter: function(value) {
							var instance = this;

							if (Lang.isString(value)) {
								value = value.split(',');
							}

							return value;
						},
						value: ''
					},
					dataSource: {
						valueFn: function() {
							var instance = this;

							return instance._getTagsDataSource();
						}
					},
					guid: {
						value: ''
					},
					instanceVar: {
						value: ''
					},
					portalModelResource: {
						value: false
					},
					hiddenInput: {
						setter: function(value) {
							var instance = this;

							return A.one(value + instance.get('guid'));
						}
					},
					matchKey: {
						value: 'value'
					},
					schema: {
						value: {
							resultFields: ['text', 'value']
						}
					}
				},

				EXTENDS: A.TextboxList,

				NAME: NAME,

				prototype: {
					renderUI: function() {
						var instance = this;

						AssetTagsSelector.superclass.renderUI.apply(instance, arguments);

						instance._renderIcons();

						instance.inputNode.addClass(CSS_INPUT_NODE);

						instance._overlayAlign.node = instance.entryHolder;
					},

					bindUI: function() {
						var instance = this;

						AssetTagsSelector.superclass.bindUI.apply(instance, arguments);

						instance._bindTagsSelector();

						instance.entries.after('add', instance._updateHiddenInput, instance);
						instance.entries.after('remove', instance._updateHiddenInput, instance);
					},

					addEntries: function() {
						var instance = this;

						instance._onAddEntryClick();
					},

					syncUI: function() {
						var instance = this;

						AssetTagsSelector.superclass.syncUI.apply(instance, arguments);

						var curEntries = instance.get('curEntries');

						A.each(curEntries, instance.add, instance);
					},

					_bindTagsSelector: function() {
						var instance = this;

						instance._submitFormListener = A.Do.before(instance._onAddEntryClick, window, 'submitForm', instance);

						instance.get('boundingBox').on('keypress', instance._onKeyPress, instance);
					},

					_getPopup: function() {
						var instance = this;

						if (!instance._popup) {
							var popup = new A.Dialog(
								{
									bodyContent: TPL_LOADING,
									constrain: true,
									draggable: true,
									hideClass: 'aui-helper-hidden-accessible',
									preventOverlap: true,
									stack: true,
									title: '',
									width: 320,
									zIndex: 1000
								}
							).render();

							popup.get('boundingBox').addClass(CSS_POPUP);

							var bodyNode = popup.bodyNode;

							bodyNode.html(STR_BLANK);

							var searchField = new A.Textfield(
								{
									defaultValue: Liferay.Language.get('search'),
									labelText: false
								}
							).render(bodyNode);

							var entriesNode = A.Node.create(TPL_TAGS_CONTAINER);

							bodyNode.appendChild(entriesNode);

							popup.searchField = searchField;
							popup.entriesNode = entriesNode;

							instance._popup = popup;

							instance._initSearch();

							var onCheckboxClick = A.bind(instance._onCheckboxClick, instance);

							entriesNode.delegate('click', onCheckboxClick, 'input[type=checkbox]');
						}

						return instance._popup;
					},

					_getProxyData: function(context) {
						var instance = this;

						var suggestionsURL = Lang.sub(
							TPL_URL_SUGGESTIONS,
							{
								context: encodeURIComponent(context)
							}
						);

						var proxyData = {
							url: suggestionsURL
						};

						return proxyData;
					},

					_getEntries: function(callback) {
						var instance = this;

						var portalModelResource = instance.get('portalModelResource');

						var groupIds = [];

						if (!portalModelResource && (themeDisplay.getParentGroupId() != themeDisplay.getCompanyGroupId())) {
							groupIds.push(themeDisplay.getParentGroupId());
						}

						groupIds.push(themeDisplay.getCompanyGroupId());

						Liferay.Service(
							'/assettag/get-groups-tags',
							{
								groupIds: groupIds
							},
							callback
						);
					},

					_getTagsDataSource: function() {
						var instance = this;

						var AssetTagSearch = Liferay.Service.bind('/assettag/search');

						AssetTagSearch._serviceQueryCache = {};

						var serviceQueryCache = AssetTagSearch._serviceQueryCache;

						var dataSource = new Liferay.Service.DataSource(
							{
								on: {
									request: function(event) {
										var term = decodeURIComponent(event.request);

										var key = term;

										if (term == '*') {
											term = STR_BLANK;
										}

										var serviceQueryObj = serviceQueryCache[key];

										if (!serviceQueryObj) {
											serviceQueryObj = {
												groupId: themeDisplay.getParentGroupId(),
												name: '%' + term + '%',
												tagProperties: STR_BLANK,
												start: 0,
												end: 20
											};

											serviceQueryCache[key] = serviceQueryObj;
										}

										event.request = serviceQueryObj;
									}
								},
								source: AssetTagSearch
							}
						).plug(
							A.Plugin.DataSourceCache,
							{
								max: 500
							}
						);

						return dataSource;
					},

					_initSearch: function() {
						var instance = this;

						var popup = instance._popup;

						popup.liveSearch = new A.LiveSearch(
							{
								after: {
									search: function() {
										var fieldsets = popup.entriesNode.all('fieldset');

										fieldsets.each(
											function(item, index, collection) {
												var visibleEntries = item.one('label:not(.aui-helper-hidden)');

												var action = 'addClass';

												if (visibleEntries) {
													action = 'removeClass';
												}

												item[action](CSS_NO_MATCHES);
											}
										);
									}
								},
								data: function(node) {
									var value = node.attr('title');

									return value.toLowerCase();
								},
								input: popup.searchField.get('node'),
								nodes: '.' + CSS_TAGS_LIST + ' label'
							}
						);
					},

					_namespace: function(name) {
						var instance = this;

						return instance.get('instanceVar') + name + instance.get('guid');
					},

					_onAddEntryClick: function(event) {
						var instance = this;

						var text = Liferay.Util.escapeHTML(instance.inputNode.val());

						if (text) {
							if (text.indexOf(',') > -1) {
								var items = text.split(',');

								A.each(
									items,
									function(item, index, collection) {
										instance.entries.add(item, {});
									}
								);
							}
							else {
								instance.entries.add(text, {});
							}
						}

						Liferay.Util.focusFormField(instance.inputNode);
					},

					_onCheckboxClick: function(event) {
						var instance = this;

						var checkbox = event.currentTarget;
						var checked = checkbox.get('checked');
						var value = checkbox.val();

						var action = 'remove';

						if (checked) {
							action = 'add';
						}

						instance[action](value);
					},

					_onKeyPress: function(event) {
						var instance = this;

						var charCode = event.charCode;

						if (charCode == '44') {
							instance._onAddEntryClick();

							event.preventDefault();
						}
						else if (MAP_INVALID_CHARACTERS[String.fromCharCode(charCode)]) {
							event.halt();
						}
					},

					_renderIcons: function() {
						var instance = this;

						var contentBox = instance.get('contentBox');

						var toolbar = [
							{
								handler: {
									context: instance,
									fn: instance._onAddEntryClick
								},
								icon: 'plus',
								id: 'add',
								label: Liferay.Language.get('add')
							},
							{
								handler: {
									context: instance,
									fn: instance._showSelectPopup
								},
								icon: 'search',
								id: 'select',
								label: Liferay.Language.get('select')
							}
						];

						if (instance.get('contentCallback')) {
							toolbar.push(
								{
									handler: {
										context: instance,
										fn: instance._showSuggestionsPopup
									},
									icon: 'comment',
									id: 'suggest',
									label: Liferay.Language.get('suggestions')
								}
							);
						}

						instance.icons = new A.Toolbar(
							{
								children: toolbar
							}
						).render(contentBox);

						var iconsBoundingBox = instance.icons.get('boundingBox');

						instance.entryHolder.placeAfter(iconsBoundingBox);
					},

					_renderTemplate: function(data) {
						var instance = this;

						var popup = instance._popup;

						var tplTag = TPL_TAG.render(
							{
								checked: data.checked,
								message: Liferay.Language.get('no-tags-found'),
								name: data.name,
								tags: data
							},
							popup.entriesNode
						);

						popup.searchField.resetValue();

						popup.liveSearch.get('nodes').refresh();

						popup.liveSearch.refreshIndex();
					},

					_showPopup: function(event) {
						var instance = this;

						var popup = instance._getPopup();

						if (event && event.currentTarget) {
							var toolItem = event.currentTarget.get('boundingBox');

							popup.align(toolItem, ['bl', 'tl']);
						}

						popup.entriesNode.html(TPL_LOADING);

						popup.show();

						if (popup.get('stack')) {
							setTimeout(
								function() {
									A.DialogManager.bringToTop(popup);
								},
								0
							);
						}
					},

					_showSelectPopup: function(event) {
						var instance = this;

						instance._showPopup(event);

						instance._popup.set('title', Liferay.Language.get('tags'));

						instance._getEntries(
							function(entries) {
								instance._updateSelectList(entries);
							}
						);
					},

					_showSuggestionsPopup: function(event) {
						var instance = this;

						instance._showPopup(event);

						instance._popup.set('title', Liferay.Language.get('suggestions'));

						var contentCallback = instance.get('contentCallback');

						var context = STR_BLANK;

						var data = [];

						if (contentCallback) {
							context = contentCallback();

							context = String(context);
						}

						var length = context.length;

						var urlSizeLimit = 4096;

						var end = urlSizeLimit;
						var lastSpaceIndex = 0;
						var start = 0;

						var suggestionsIO = A.io.request(
							themeDisplay.getPathMain() + '/portal/rest_proxy',
							{
								autoLoad: false,
								dataType: 'json',
								on: {
									success: function(event, id, obj) {
										var results = this.get('responseData');

											var resultData = results && results.ResultSet && results.ResultSet.Result;

										if (resultData) {
											for (var i = 0; i < resultData.length; i++) {
												data.push(
													{
														name: resultData[i]
													}
												);
											}
										}

										queue.run();
									}
								}
							}
						);

						var queue = new A.AsyncQueue(
							{
								fn: function() {
									queue.pause();

									var phrase = context.substr(start, end);

									lastSpaceIndex = urlSizeLimit;

									if (end < length) {
										lastSpaceIndex = phrase.lastIndexOf(' ');

										phrase = phrase.substr(0, lastSpaceIndex);

										end = start + lastSpaceIndex;
									}

									start += lastSpaceIndex;
									end = start + urlSizeLimit;

									suggestionsIO.set('data', instance._getProxyData(phrase));

									suggestionsIO.start();
								},
								until: function() {
									return length <= start;
								}
							}
						);

						queue.after(
							'complete',
							function(event) {
								instance._updateSelectList(AArray.unique(data));
							}
						);

						queue.run();
					},

					_updateHiddenInput: function(event) {
						var instance = this;

						var hiddenInput = instance.get('hiddenInput');

						hiddenInput.val(instance.entries.keys.join());

						var popup = instance._popup;

						if (popup && popup.get('visible')) {
							var checkbox = popup.bodyNode.one('input[value=' + event.attrName + ']');

							if (checkbox) {
								var checked = false;

								if (event.type == 'dataset:add') {
									checked = true;
								}

								checkbox.set('checked', checked);
							}
						}
					},

					_updateSelectList: function(data) {
						var instance = this;

						for (var i = 0; i < data.length; i++) {
							var tag = data[i];

							tag.checked = instance.entries.indexOfKey(tag.name) > -1 ? TPL_CHECKED : STR_BLANK;
						}

						instance._renderTemplate(data);
					}
				}
			}
		);

		Liferay.AssetTagsSelector = AssetTagsSelector;
	},
	'',
	{
		requires: ['array-extras', 'async-queue', 'aui-autocomplete', 'aui-dialog', 'aui-form-textfield', 'aui-io-request', 'aui-live-search', 'aui-template', 'aui-textboxlist', 'datasource-cache', 'liferay-service-datasource']
	}
);