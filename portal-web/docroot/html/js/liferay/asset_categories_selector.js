AUI.add(
	'liferay-asset-categories-selector',
	function(A) {
		var Lang = A.Lang;

		var LString = Lang.String;

		var AObject = A.Object;

		var BOUNDING_BOX = 'boundingBox';

		var CSS_TAGS_LIST = 'lfr-categories-selector-list';

		var EMPTY_FN = Lang.emptyFn;

		var ID = 'id';

		var NAME = 'categoriesselector';

		var STR_EXPANDED = 'expanded';

		var STR_MORE_RESULTS_LABEL = 'moreResultsLabel';

		var STR_PREV_EXPANDED = '_LFR_prevExpanded';

		var STR_MAX_ENTRIES = 'maxEntries';

		var STR_START = 'start';

		var TPL_CHECKED = ' checked="checked" ';

		var TPL_INPUT =
			'<label title="{titleCurrentValue}">' +
				'<span class="lfr-categories-selector-category-name" title="{titleCurrentValue}">' +
					'<input data-categoryId="{categoryId}" data-vocabularyid="{vocabularyId}" name="{inputName}" type="{type}" value="{titleCurrentValue}" {checked} />' +
					'{titleCurrentValue}' +
				'</span>' +
				'<span class="lfr-categories-selector-search-results-path" title="{path}">{path}</span>' +
			'</label>';

		var TPL_MESSAGE = '<div class="lfr-categories-message">{0}</div>';

		var TPL_RADIO_CLASS = ' class="{0}" ';

		var TPL_RADIO_ID = ' id="{0}" ';

		var TPL_RADIO_IMAGE = '<div class="lfr-categories-selector-radio-image category{0}"></div>';

		var TPL_SEARCH_RESULTS = '<div class="lfr-categories-selector-search-results"></div>';

		/**
		 * OPTIONS
		 *
		 * Required
		 * className {String}: The class name of the current asset.
		 * curEntryIds (string): The ids of the current categories.
		 * curEntries (string): The names of the current categories.
		 * hiddenInput {string}: The hidden input used to pass in the current categories.
		 * instanceVar {string}: The instance variable for this class.
		 * labelNode {String|A.Node}: The node of the label element for this selector.
		 * title {String}: The title of the button element for this selector.
		 * vocabularyIds (string): The ids of the vocabularies.
		 * vocabularyGroupIds (string): The groupIds of the vocabularies.
		 *
		 * Optional
		 * maxEntries {Number}: The maximum number of entries that will be loaded. The default value is -1, which will load all categories.
		 * moreResultsLabel {String}: The localized label for link "Load more results".
		 * portalModelResource {boolean}: Whether the asset model is on the portal level.
		 */

		var AssetCategoriesSelector = A.Component.create(
			{
				ATTRS: {
					curEntries: {
						setter: function(value) {
							var instance = this;

							if (Lang.isString(value)) {
								value = value.split('_CATEGORY_');
							}

							return value;
						},
						value: []
					},

					curEntryIds: {
						setter: function(value) {
							var instance = this;

							if (Lang.isString(value)) {
								value = value.split(',');
							}

							return value;
						},
						value: []
					},

					label: {
						validator: '_isValidString',
						value: Liferay.Language.get('select')
					},

					labelNode: {
						setter: function(value) {
							return A.one(value) || A.Attribute.INVALID_VALUE;
						},
						value: null
					},

					maxEntries: {
						validator: Lang.isNumber,
						value: -1
					},

					moreResultsLabel: {
						validator: '_isValidString',
						value: Liferay.Language.get('load-more-results')
					},

					singleSelect: {
						validator: Lang.isBoolean,
						value: false
					},

					title: {
						validator: '_isValidString',
						value: Liferay.Language.get('select-categories')
					},

					vocabularyGroupIds: {
						setter: function(value) {
							var instance = this;

							if (Lang.isString(value) && value) {
								value = value.split(',');
							}

							return value;
						},
						value: []
					},

					vocabularyIds: {
						setter: function(value) {
							var instance = this;

							if (Lang.isString(value) && value) {
								value = value.split(',');
							}

							return value;
						},
						value: []
					}
				},

				EXTENDS: Liferay.AssetTagsSelector,

				NAME: NAME,

				prototype: {
					TREEVIEWS: {},
					UI_EVENTS: {},

					renderUI: function() {
						var instance = this;

						AssetCategoriesSelector.superclass.constructor.superclass.renderUI.apply(instance, arguments);

						instance._renderIcons();

						instance.inputContainer.addClass('hide-accessible');

						instance._applyARIARoles();
					},

					bindUI: function() {
						var instance = this;

						AssetCategoriesSelector.superclass.bindUI.apply(instance, arguments);
					},

					syncUI: function() {
						var instance = this;

						AssetCategoriesSelector.superclass.constructor.superclass.syncUI.apply(instance, arguments);

						var matchKey = instance.get('matchKey');

						instance.entries.getKey = function(obj) {
							return obj.categoryId;
						};

						var curEntries = instance.get('curEntries');
						var curEntryIds = instance.get('curEntryIds');

						A.each(
							curEntryIds,
							function(item, index) {
								var entry = {
									categoryId: item
								};

								entry[matchKey] = curEntries[index];

								entry.value = LString.unescapeHTML(entry.value);

								instance.entries.add(entry);
							}
						);
					},

					_afterTBLFocusedChange: EMPTY_FN,

					_applyARIARoles: function() {
						var instance = this;

						var boundingBox = instance.get(BOUNDING_BOX);
						var labelNode = instance.get('labelNode');

						if (labelNode) {
							boundingBox.attr('aria-labelledby', labelNode.attr(ID));

							labelNode.attr('for', boundingBox.attr(ID));
						}
					},

					_bindTagsSelector: EMPTY_FN,

					_clearEntries: function() {
						var instance = this;

						var entries = instance.entries;

						entries.each(A.fn('removeAt', entries, 0));
					},

					_formatJSONResult: function(json) {
						var instance = this;

						var output = [];

						var type = 'check';

						if (instance.get('singleSelect')) {
							type = 'radio';
						}

						A.each(
							json,
							function(item, index) {
								var checked = false;
								var treeId = 'category' + item.categoryId;

								if (instance.entries.findIndexBy('categoryId', item.categoryId) > -1) {
									checked = true;
								}

								var newTreeNode = {
									after: {
										checkedChange: A.bind('_onCheckedChange', instance)
									},
									checked: checked,
									id: treeId,
									label: LString.escapeHTML(item.titleCurrentValue),
									leaf: !item.hasChildren,
									paginator: instance._getPaginatorConfig(item),
									type: type
								};

								output.push(newTreeNode);
							}
						);

						return output;
					},

					_formatRequestData: function(treeNode) {
						var instance = this;

						var data = {};

						data.p_auth = Liferay.authToken;

						var assetId = instance._getTreeNodeAssetId(treeNode);
						var assetType = instance._getTreeNodeAssetType(treeNode);

						if (Lang.isValue(assetId)) {
							if (assetType == 'category') {
								data.categoryId = assetId;
							}
							else {
								data.vocabularyId = assetId;
							}
						}

						return data;
					},

					_getEntries: function(className, callback) {
						var instance = this;

						var portalModelResource = instance.get('portalModelResource');

						var groupIds = [];

						var vocabularyIds = instance.get('vocabularyIds');

						if (vocabularyIds.length > 0) {
							Liferay.Service(
								{
									'$vocabularies = /assetvocabulary/get-vocabularies': {
										vocabularyIds: vocabularyIds,
										'$childrenCount = /assetcategory/get-vocabulary-root-categories-count': {
											'@groupId': '$vocabularies.groupId',
											'@vocabularyId': '$vocabularies.vocabularyId'
										}
									}
								},
								callback
							);
						}
						else {
							if (!portalModelResource && (themeDisplay.getSiteGroupId() != themeDisplay.getCompanyGroupId())) {
								groupIds.push(themeDisplay.getSiteGroupId());
							}

							groupIds.push(themeDisplay.getCompanyGroupId());

							Liferay.Service(
								{
									'$vocabularies = /assetvocabulary/get-groups-vocabularies': {
										className: className,
										groupIds: groupIds,
										'$childrenCount = /assetcategory/get-vocabulary-root-categories-count': {
											'groupId': '$vocabularies.groupId',
											'@vocabularyId': '$vocabularies.vocabularyId'
										}
									}
								},
								callback
							);
						}
					},

					_getPaginatorConfig: function(item) {
						var instance = this;

						var paginatorConfig = {
							offsetParam: STR_START
						};

						var maxEntries = instance.get(STR_MAX_ENTRIES);

						if (maxEntries > 0) {
							paginatorConfig.limit = maxEntries;
							paginatorConfig.moreResultsLabel = instance.get(STR_MORE_RESULTS_LABEL);
							paginatorConfig.total = item.childrenCount;
						}
						else {
							paginatorConfig.end = -1;
							paginatorConfig.start = -1;
						}

						return paginatorConfig;
					},

					_getTreeNodeAssetId: function(treeNode) {
						var treeId = treeNode.get(ID);
						var match = treeId.match(/(\d+)$/);

						return (match ? match[1] : null);
					},

					_getTreeNodeAssetType: function(treeNode) {
						var treeId = treeNode.get(ID);
						var match = treeId.match(/^(vocabulary|category)/);

						return (match ? match[1] : null);
					},

					_initSearch: function() {
						var instance = this;

						var popup = instance._popup;

						var vocabularyIds = instance.get('vocabularyIds');
						var vocabularyGroupIds = instance.get('vocabularyGroupIds');

						var searchResults = instance._searchResultsNode;

						if (!searchResults) {
							searchResults = A.Node.create(TPL_SEARCH_RESULTS);

							instance._searchResultsNode = searchResults;

							var processSearchResults = A.bind(
								'_processSearchResults',
								instance,
								searchResults
							);

							var searchCategoriesTask = A.debounce(
								instance._searchCategories,
								350,
								instance,
								searchResults,
								vocabularyIds,
								vocabularyGroupIds,
								processSearchResults
							);

							var input = popup.searchField;

							input.on('keyup', searchCategoriesTask);

							if (instance.get('singleSelect')) {
								var onSelectChange = A.bind('_onSelectChange', instance);

								popup.entriesNode.delegate('change', onSelectChange, 'input[type=radio]');
							}
						}

						popup.entriesNode.append(searchResults);

						instance._searchBuffer = [];
					},

					_isValidString: function(value) {
						var instance = this;

						return Lang.isString(value) && value.length;
					},

					_onBoundingBoxClick: EMPTY_FN,

					_onCheckboxCheck: function(event) {
						var instance = this;

						var currentTarget = event.currentTarget;

						var assetId;
						var entryMatchKey;

						if (A.instanceOf(currentTarget, A.Node)) {
							assetId = currentTarget.attr('data-categoryId');

							entryMatchKey = currentTarget.val();
						}
						else {
							assetId = instance._getTreeNodeAssetId(currentTarget);

							entryMatchKey = currentTarget.get('label');
						}

						var matchKey = instance.get('matchKey');

						var entry = {
							categoryId: assetId
						};

						entry[matchKey] = entryMatchKey;

						entry.value = LString.unescapeHTML(entry.value);

						instance.entries.add(entry);
					},

					_onCheckboxClick: function(event) {
						var instance = this;

						var method = '_onCheckboxUncheck';

						if (event.currentTarget.attr('checked')) {
							method = '_onCheckboxCheck';
						}

						instance[method](event);
					},

					_onCheckboxUncheck: function(event) {
						var instance = this;

						var currentTarget = event.currentTarget;

						var assetId;

						if (A.instanceOf(currentTarget, A.Node)) {
							assetId = currentTarget.attr('data-categoryId');
						}
						else {
							assetId = instance._getTreeNodeAssetId(currentTarget);
						}

						instance.entries.removeKey(assetId);
					},

					_onCheckedChange: function(event) {
						var instance = this;

						if (event.newVal) {
							if (instance.get('singleSelect')) {
								instance._clearEntries();
							}

							instance._onCheckboxCheck(event);
						}
						else {
							instance._onCheckboxUncheck(event);
						}
					},

					_onSelectChange: function(event) {
						var instance = this;

						instance._clearEntries();

						instance._onCheckboxCheck(event);
					},

					_processSearchResults: function(searchResults, results) {
						var instance = this;

						var buffer = instance._searchBuffer;

						buffer.length = 0;

						var categories = results.categories;

						if (categories.length > 0) {
							var inputType = 'checkbox';

							if (instance.get('singleSelect')) {
								inputType = 'radio';
							}

							var inputName = A.guid();

							A.each(
								categories,
								function(item, index) {
									item.checked = instance.entries.findIndexBy('categoryId', item.categoryId) > -1 ? TPL_CHECKED : '';

									item.inputName = inputName;
									item.type = inputType;

									buffer.push(Lang.sub(TPL_INPUT, item));
								}
							);
						}
						else {
							var message = Lang.sub(TPL_MESSAGE, [Liferay.Language.get('no-categories-found')]);

							buffer.push(message);
						}

						searchResults.removeClass('loading-animation');

						searchResults.html(buffer.join(''));
					},

					_renderIcons: function() {
						var instance = this;

						var contentBox = instance.get('contentBox');

						instance.icons = new A.Toolbar(
							{
								children: [
									{
										icon: 'icon-search',
										label: instance.get('label'),
										on: {
											click: A.bind('_showSelectPopup', instance)
										},
										title: instance.get('title')
									}
								]
							}
						).render(contentBox);

						var iconsBoundingBox = instance.icons.get(BOUNDING_BOX);

						instance.entryHolder.placeAfter(iconsBoundingBox);
					},

					_searchCategories: function(event, searchResults, vocabularyIds, vocabularyGroupIds, callback) {
						var instance = this;

						var searchValue = Lang.trim(event.currentTarget.val());

						if (searchValue && !event.isNavKey()) {
							searchResults.empty();

							searchResults.addClass('loading-animation');

							Liferay.Service(
								{
									'$display = /assetcategory/search-categories-display': {
										end: -1,
										groupIds: vocabularyGroupIds,
										start: -1,
										title: searchValue,
										vocabularyIds: vocabularyIds,
										'categories.$path = /assetcategory/get-category-path': {
											'@categoryId': '$display.categories.categoryId'
										}
									}
								},
								callback
							);
						}

						searchResults.toggle(!!searchValue);

						var treeViews = instance.TREEVIEWS;

						AObject.each(
							treeViews,
							function(item, index) {
								item.toggle(!searchValue);
							}
						);
					},

					_showSelectPopup: function(event) {
						var instance = this;

						instance._showPopup(event);

						var popup = instance._popup;

						popup.titleNode.html(Liferay.Language.get('categories'));

						popup.entriesNode.addClass(CSS_TAGS_LIST);

						var className = instance.get('className');

						instance._getEntries(
							className,
							function(entries) {
								popup.entriesNode.empty();

								A.each(entries, instance._vocabulariesIterator, instance);

								A.each(
									instance.TREEVIEWS,
									function(item, index) {
										item.expandAll();
									}
								);
							}
						);

						if (instance._bindSearchHandle) {
							instance._bindSearchHandle.detach();
						}

						instance._bindSearchHandle = popup.searchField.once('focus', instance._initSearch, instance);
					},

					_vocabulariesIterator: function(item, index) {
						var instance = this;

						var popup = instance._popup;
						var vocabularyTitle = LString.escapeHTML(item.titleCurrentValue);
						var vocabularyId = item.vocabularyId;

						if (item.groupId == themeDisplay.getCompanyGroupId()) {
							vocabularyTitle += ' (' + Liferay.Language.get('global') + ')';
						}

						var treeId = 'vocabulary' + vocabularyId;

						var vocabularyRootNode = {
							alwaysShowHitArea: true,
							id: treeId,
							label: vocabularyTitle,
							leaf: false,
							paginator: instance._getPaginatorConfig(item),
							type: 'io'
						};

						instance.TREEVIEWS[vocabularyId] = new A.TreeView(
							{
								children: [vocabularyRootNode],
								io: {
									cfg: {
										data: A.bind('_formatRequestData', instance),
										on: {
											success: function(event) {
												var treeViews = instance.TREEVIEWS;

												var tree = treeViews[vocabularyId];

												var children = tree.get('children');

												if (!children || !children.length || !children[0].hasChildNodes()) {
													tree.destroy();

													delete treeViews[vocabularyId];
												}
											}
										}
									},
									formatter: A.bind('_formatJSONResult', instance),
									url: themeDisplay.getPathMain() + '/asset/get_categories'
								}
							}
						).render(popup.entriesNode);
					}
				}
			}
		);

		Liferay.AssetCategoriesSelector = AssetCategoriesSelector;
	},
	'',
	{
		requires: ['aui-tree', 'liferay-asset-tags-selector']
	}
);