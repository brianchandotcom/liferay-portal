AUI.add(
	'liferay-asset-tags-selector',
	function(A) {
		var Lang = A.Lang;

		var AArray = A.Array;

		var LString = Lang.String;

		var CSS_INPUT_NODE = 'lfr-tag-selector-input';

		var CSS_NO_MATCHES = 'no-matches';

		var CSS_POPUP = 'lfr-tag-selector-popup';

		var CSS_TAGS_LIST = 'lfr-tags-selector-list';

		var MAP_INVALID_CHARACTERS = AArray.hash(
			[
				'"',
				'#',
				'%',
				'&',
				'*',
				'+',
				',',
				'/',
				':',
				';',
				'<',
				'=',
				'>',
				'?',
				'@',
				'[',
				'\'',
				'\\',
				'\n',
				'\r',
				']',
				'`',
				'{',
				'|',
				'}',
				'~'
			]
		);

		var NAME = 'tagselector';

		var STR_BLANK = '';

		var TPL_CHECKED = ' checked="checked" ';

		var TPL_LOADING = '<div class="loading-animation" />';

		var TPL_SEARCH_FORM = '<form action="javascript:;" class="form-search lfr-tag-selector-search row">' +
				'<input class="col-md-12 lfr-tag-selector-input search-query" placeholder="{0}" type="text" />' +
			'</form>';

		var TPL_SUGGESTIONS_QUERY = 'select * from search.termextract where context="{0}"';

		var TPL_TAG = new A.Template(
			'<fieldset class="{[(!values.tags || !values.tags.length) ? "', CSS_NO_MATCHES, '" : "', STR_BLANK, '" ]}">',
				'<tpl for="tags">',
					'<label class="checkbox" title="{name}"><input {checked} type="checkbox" value="{name}" />{name}</label>',
				'</tpl>',

				'<div class="lfr-tag-message">{message}</div>',
			'</fieldset>'
		);

		var TPL_TAGS_CONTAINER = '<div class="' + CSS_TAGS_LIST + '"></div>';

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
					allowAddEntry: {
						value: true
					},

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

					groupIds: {
						setter: '_setGroupIds',
						validator: Lang.isString
					},

					guid: {
						value: ''
					},

					hiddenInput: {
						setter: function(value) {
							var instance = this;

							return A.one(value + instance.get('guid'));
						}
					},

					instanceVar: {
						value: ''
					},

					matchKey: {
						value: 'value'
					},

					portalModelResource: {
						value: false
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

						var entries = instance.entries;

						entries.after('add', instance._updateHiddenInput, instance);
						entries.after('remove', instance._updateHiddenInput, instance);
					},

					syncUI: function() {
						var instance = this;

						AssetTagsSelector.superclass.syncUI.apply(instance, arguments);

						var curEntries = instance.get('curEntries');

						A.each(curEntries, instance.add, instance);
					},

					addEntries: function() {
						var instance = this;

						instance._addEntries();
					},

					_addEntries: function() {
						var instance = this;

						var text = LString.escapeHTML(instance.inputNode.val());

						if (text) {
							if (text.indexOf(',') > -1) {
								var items = text.split(',');

								A.each(
									items,
									function(item, index) {
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

					_bindTagsSelector: function() {
						var instance = this;

						instance._submitFormListener = A.Do.before(instance._addEntries, window, 'submitForm', instance);

						instance.get('boundingBox').on('keypress', instance._onKeyPress, instance);
					},

					_getEntries: function(callback) {
						var instance = this;

						Liferay.Service(
							'/assettag/get-groups-tags',
							{
								groupIds: instance.get('groupIds')
							},
							callback
						);
					},

					_getPopup: function() {
						var instance = this;

						if (!instance._popup) {
							var popup = Liferay.Util.Window.getWindow(
								{
									dialog: {
										cssClass: CSS_POPUP,
										hideClass: 'hide-accessible',
										width: 600
									}
								}
							);

							var bodyNode = popup.bodyNode;

							bodyNode.html(STR_BLANK);

							var searchForm = A.Node.create(Lang.sub(TPL_SEARCH_FORM, [Liferay.Language.get('search')]));

							bodyNode.append(searchForm);

							var searchField = searchForm.one('input');

							var entriesNode = A.Node.create(TPL_TAGS_CONTAINER);

							bodyNode.append(entriesNode);

							popup.searchField = searchField;
							popup.entriesNode = entriesNode;

							instance._popup = popup;

							instance._initSearch();

							var onCheckboxClick = A.bind('_onCheckboxClick', instance);

							entriesNode.delegate('click', onCheckboxClick, 'input[type=checkbox]');
						}

						return instance._popup;
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
												end: 20,
												groupIds: instance.get('groupIds'),
												name: '%' + term + '%',
												start: 0,
												tagProperties: STR_BLANK
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
											function(item, index) {
												var visibleEntries = item.one('label:not(.hide)');

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
								input: popup.searchField,
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

						event.domEvent.preventDefault();

						instance._addEntries();
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

						if (!A.UA.gecko || event._event.charCode) {
							if (charCode == '44') {
								event.preventDefault();

								instance._addEntries();
							}
							else if (MAP_INVALID_CHARACTERS[String.fromCharCode(charCode)]) {
								event.halt();
							}
						}
					},

					_renderIcons: function() {
						var instance = this;

						var contentBox = instance.get('contentBox');

						var buttonGroup = [
							{
								icon: 'icon-search',
								label: Liferay.Language.get('select'),
								on: {
									click: A.bind('_showSelectPopup', instance)
								},
								title: Liferay.Language.get('select-tags')
							}
						];

						if (instance.get('allowAddEntry')) {
							buttonGroup.unshift(
								{
									icon: 'icon-plus',
									label: Liferay.Language.get('add'),
									on: {
										click: A.bind('_onAddEntryClick', instance)
									},
									title: Liferay.Language.get('add-tags')
								}
							);
						}

						if (instance.get('contentCallback')) {
							buttonGroup.push(
								{
									icon: 'icon-comment',
									label: Liferay.Language.get('suggestions'),
									on: {
										click: A.bind('_showSuggestionsPopup', instance)
									},
									title: Liferay.Language.get('suggestions')
								}
							);
						}

						instance.icons = new A.Toolbar(
							{
								children: [buttonGroup]
							}
						).render(contentBox);

						var iconsBoundingBox = instance.icons.get('boundingBox');

						instance.entryHolder.placeAfter(iconsBoundingBox);
					},

					_renderTemplate: function(data) {
						var instance = this;

						var popup = instance._popup;

						TPL_TAG.render(
							{
								checked: data.checked,
								message: Liferay.Language.get('no-tags-found'),
								name: data.name,
								tags: data
							},
							popup.entriesNode
						);

						popup.searchField.val('');

						popup.liveSearch.get('nodes').refresh();

						popup.liveSearch.refreshIndex();
					},

					_setGroupIds: function(value) {
						return value.split(',');
					},

					_showPopup: function(event) {
						var instance = this;

						event.domEvent.preventDefault();

						var popup = instance._getPopup();

						popup.entriesNode.html(TPL_LOADING);

						popup.show();
					},

					_showSelectPopup: function(event) {
						var instance = this;

						instance._showPopup(event);

						instance._popup.titleNode.html(Liferay.Language.get('tags'));

						instance._getEntries(
							function(entries) {
								instance._updateSelectList(entries);
							}
						);
					},

					_showSuggestionsPopup: function(event) {
						var instance = this;

						instance._showPopup(event);

						instance._popup.titleNode.html(Liferay.Language.get('suggestions'));

						var contentCallback = instance.get('contentCallback');

						var context = STR_BLANK;

						if (contentCallback) {
							context = contentCallback();

							context = String(context);
						}

						context = LString.stripTags(context);
						context = LString.escapeHTML(context);

						var query = Lang.sub(TPL_SUGGESTIONS_QUERY, [context]);

						var protocol = 'http';

						if (A.UA.secure) {
							protocol = 'https';
						}

						A.YQL(
							query,
							function(response) {
								var results = response.query && response.query.results;

								var data = [];

								if (results) {
									data = AArray.map(
										AArray(results.Result),
										function(item, index) {
											return {
												name: item
											};
										}
									);
								}

								instance._updateSelectList(AArray.unique(data));
							},
							null,
							{
								proto: protocol
							}
						);
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

								checkbox.attr('checked', checked);
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
		requires: ['array-extras', 'async-queue', 'aui-autocomplete-deprecated', 'aui-io-plugin-deprecated', 'aui-io-request', 'aui-live-search-deprecated', 'aui-template-deprecated', 'aui-textboxlist', 'datasource-cache', 'liferay-service-datasource', 'liferay-util-window', 'yql']
	}
);