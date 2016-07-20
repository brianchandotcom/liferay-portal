AUI.add(
	'liferay-asset-taglib-tags-selector',
	function(A) {
		var Lang = A.Lang;

		var AArray = A.Array;

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

		/**
		 * OPTIONS
		 *
		 * Required
		 * hiddenInput {string}: The hidden input used to pass in the current tags.
		 * tagNames (string): The current tags.
		 *
		 */

		var AssetTaglibTagsSelector = A.Component.create(
			{
				ATTRS: {
					allowAddEntry: {
						value: true
					},

					allowAnyEntry: {
						value: true
					},

					dataSource: {
						valueFn: function() {
							var instance = this;

							return instance._getTagsDataSource();
						}
					},

					eventName: {
						validator: Lang.isString
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

					portletURL: {
						validator: Lang.isString
					},

					schema: {
						value: {
							resultFields: ['text', 'value']
						}
					},

					tagNames: {
						setter: function(value) {
							var instance = this;

							if (Lang.isString(value)) {
								value = value.split(',');
							}

							return value;
						},
						value: ''
					}
				},

				EXTENDS: A.TextboxList,

				NAME: 'tagselector',

				prototype: {
					renderUI: function() {
						var instance = this;

						AssetTaglibTagsSelector.superclass.renderUI.apply(instance, arguments);

						instance._renderIcons();

						instance.inputNode.addClass('lfr-tag-selector-input');

						instance._overlayAlign.node = instance.entryHolder;
					},

					bindUI: function() {
						var instance = this;

						AssetTaglibTagsSelector.superclass.bindUI.apply(instance, arguments);

						instance._bindTagsSelector();

						var entries = instance.entries;

						entries.after('add', instance._updateHiddenInput, instance);
						entries.after('remove', instance._updateHiddenInput, instance);
					},

					syncUI: function() {
						var instance = this;

						AssetTaglibTagsSelector.superclass.syncUI.apply(instance, arguments);

						var tagNames = instance.get('tagNames');

						tagNames.forEach(instance.add, instance);
					},

					addEntries: function() {
						var instance = this;

						instance._addEntries();
					},

					_addEntries: function() {
						var instance = this;

						var text = Lang.String.escapeHTML(instance.inputNode.val());

						if (text) {
							if (text.indexOf(',') > -1) {
								var items = text.split(',');

								items.forEach(
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

						var form = instance.inputNode.get('form');

						instance._submitFormListener = A.Do.before(instance._addEntries, form, 'submit', instance);

						instance.get('boundingBox').on('keypress', instance._onKeyPress, instance);
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
											term = '';
										}

										var serviceQueryObj = serviceQueryCache[key];

										if (!serviceQueryObj) {
											serviceQueryObj = {
												end: 20,
												groupIds: instance.get('groupIds'),
												name: '%' + term + '%',
												start: 0,
												tagProperties: ''
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

					_onAddEntryClick: function(event) {
						var instance = this;

						event.domEvent.preventDefault();

						instance._addEntries();
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

						var buttonGroup = [];

						if (instance.get('portletURL')) {
							buttonGroup.unshift(
								{
									label: Liferay.Language.get('select'),
									on: {
										click: A.bind('_showSelectPopup', instance)
									},
									title: Liferay.Language.get('select-tags')
								}
							);
						}

						if (instance.get('allowAddEntry')) {
							buttonGroup.unshift(
								{
									label: Liferay.Language.get('add'),
									on: {
										click: A.bind('_onAddEntryClick', instance)
									},
									title: Liferay.Language.get('add-tags')
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

					_setGroupIds: function(value) {
						return value.split(',');
					},

					_showSelectPopup: function(event) {
						var instance = this;

						event.domEvent.preventDefault();

						var uri = Lang.sub(
							decodeURIComponent(instance.get('portletURL')),
							{
								selectedTags: instance.entries.keys.join()
							}
						);

						var itemSelectorDialog = new A.LiferayItemSelectorDialog(
							{
								eventName: instance.get('eventName'),
								on: {
									selectedItemChange: function(event) {
										var selectedItem = event.newVal;

										if (selectedItem) {
											instance.entries.each(
												function(item) {
													instance.entries.remove(item);
												}
											);

											AArray.each(
												selectedItem.items.split(','),
												function(value) {
													instance.add(value);
												}
											);
										}
									}
								},
								'strings.add': Liferay.Language.get('done'),
								title: Liferay.Language.get('tags'),
								url: uri
							}
						);

						itemSelectorDialog.open();
					},

					_updateHiddenInput: function(event) {
						var instance = this;

						var hiddenInput = instance.get('hiddenInput');

						hiddenInput.val(instance.entries.keys.join());
					}
				}
			}
		);

		Liferay.AssetTaglibTagsSelector = AssetTaglibTagsSelector;
	},
	'',
	{
		requires: ['aui-io-plugin-deprecated', 'aui-live-search-deprecated', 'aui-template-deprecated', 'aui-textboxlist-deprecated', 'datasource-cache', 'liferay-item-selector-dialog', 'liferay-service-datasource']
	}
);