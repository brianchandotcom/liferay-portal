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

AUI.add(
	'liferay-asset-categories-selector',
	(A) => {
		var Lang = A.Lang;

		var LString = Lang.String;

		// eslint-disable-next-line @liferay/aui/no-object
		var AObject = A.Object;

		var BOUNDING_BOX = 'boundingBox';

		var CSS_LOADING_ANIMATION = 'loading-animation';

		var CSS_TAGS_LIST = 'lfr-categories-selector-list';

		var EMPTY_FN = Lang.emptyFn;

		var ID = 'id';

		var NAME = 'categoriesselector';

		var STR_MAX_ENTRIES = 'maxEntries';

		var STR_MORE_RESULTS_LABEL = 'moreResultsLabel';

		var STR_START = 'start';

		var TPL_CHECKED = ' checked="checked" ';

		var TPL_INPUT =
			'<label title="{titleCurrentValue}">' +
			'<span class="lfr-categories-selector-category-name text-truncate" title="{titleCurrentValue}">' +
			'<input data-categoryId="{categoryId}" data-vocabularyid="{vocabularyId}" name="{inputName}" type="{type}" value="{titleCurrentValue}" {checked} />' +
			'{titleCurrentValue}' +
			'</span>' +
			'<span class="lfr-categories-selector-search-results-path text-truncate" title="{path}">{path}</span>' +
			'</label>';

		var TPL_MESSAGE = '<div class="lfr-categories-message">{0}</div>';

		var TPL_SEARCH_RESULTS =
			'<div class="lfr-categories-selector-search-results"></div>';

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

		var AssetCategoriesSelector = A.Component.create({
			ATTRS: {
				curEntries: {
					setter(value) {
						if (Lang.isString(value)) {
							value = value.split('_CATEGORY_');
						}

						return value;
					},
					value: [],
				},

				curEntryIds: {
					setter(value) {
						if (Lang.isString(value)) {
							value = value.split(',');
						}

						return value;
					},
					value: [],
				},

				label: {
					validator: '_isValidString',
					value: Liferay.Language.get('select'),
				},

				labelNode: {
					setter(value) {
						return A.one(value) || A.Attribute.INVALID_VALUE;
					},
					value: null,
				},

				maxEntries: {
					validator: Lang.isNumber,
					value: -1,
				},

				moreResultsLabel: {
					validator: '_isValidString',
					value: Liferay.Language.get('load-more-results'),
				},

				singleSelect: {
					validator: Lang.isBoolean,
					value: false,
				},

				title: {
					validator: '_isValidString',
					value: Liferay.Language.get('select-categories'),
				},

				vocabularyGroupIds: {
					setter(value) {
						if (Lang.isString(value) && value) {
							value = value.split(',');
						}

						return value;
					},
					value: [],
				},

				vocabularyIds: {
					setter(value) {
						if (Lang.isString(value) && value) {
							value = value.split(',');
						}

						return value;
					},
					value: [],
				},
			},

			EXTENDS: Liferay.AssetTagsSelector,

			NAME,

			prototype: {
				_afterTBLFocusedChange: EMPTY_FN,

				_applyARIARoles() {
					var instance = this;

					var boundingBox = instance.get(BOUNDING_BOX);
					var labelNode = instance.get('labelNode');

					if (labelNode) {
						boundingBox.attr('aria-labelledby', labelNode.attr(ID));

						labelNode.attr('for', boundingBox.attr(ID));
					}
				},

				_bindTagsSelector: EMPTY_FN,

				_clearEntries() {
					var instance = this;

					var entries = instance.entries;

					entries.each(A.fn('removeAt', entries, 0));
				},

				_formatJSONResult(json) {
					var instance = this;

					var output = [];

					var type = 'check';

					if (instance.get('singleSelect')) {
						type = 'radio';
					}

					json.forEach((item) => {
						var checked = false;
						var treeId = 'category' + item.categoryId;

						if (
							instance.entries.findIndexBy(
								'categoryId',
								item.categoryId
							) > -1
						) {
							checked = true;
						}

						var newTreeNode = {
							after: {
								checkedChange: A.bind(
									'_onCheckedChange',
									instance
								),
							},
							checked,
							id: treeId,
							label: LString.escapeHTML(item.titleCurrentValue),
							leaf: !item.hasChildren,
							paginator: instance._getPaginatorConfig(item),
							type,
						};

						output.push(newTreeNode);
					});

					return output;
				},

				_formatRequestData(groupId, parentVocabularyId, treeNode) {
					var instance = this;

					var data = {};

					data.p_auth = Liferay.authToken;
					data.scopeGroupId = groupId;

					var assetId = instance._getTreeNodeAssetId(treeNode);
					var assetType = instance._getTreeNodeAssetType(treeNode);

					if (Lang.isValue(assetId)) {
						if (assetType === 'category') {
							data.categoryId = assetId;

							if (parentVocabularyId) {
								data.vocabularyId = parentVocabularyId;
							}
						}
						else {
							data.vocabularyId = assetId;
						}
					}

					return data;
				},

				_getEntries(className, callback) {
					var instance = this;

					var portalModelResource = instance.get(
						'portalModelResource'
					);

					var groupIds = [];

					var vocabularyIds = instance.get('vocabularyIds');

					if (vocabularyIds.length > 0) {
						Liferay.Service(
							{
								'$vocabularies = /assetvocabulary/get-vocabularies': {
									'$childrenCount = /assetcategory/get-vocabulary-root-categories-count': {
										'@groupId': '$vocabularies.groupId',
										'@vocabularyId':
											'$vocabularies.vocabularyId',
									},
									'$group[descriptiveName] = /group/get-group': {
										'@groupId': '$vocabularies.groupId',
									},
									vocabularyIds,
								},
							},
							callback
						);
					}
					else {
						if (
							!portalModelResource &&
							themeDisplay.getSiteGroupId() !==
								themeDisplay.getCompanyGroupId()
						) {
							groupIds.push(themeDisplay.getSiteGroupId());
						}

						groupIds.push(themeDisplay.getCompanyGroupId());

						Liferay.Service(
							{
								'$vocabularies = /assetvocabulary/get-groups-vocabularies': {
									'$childrenCount = /assetcategory/get-vocabulary-root-categories-count': {
										'@vocabularyId':
											'$vocabularies.vocabularyId',
										'groupId': '$vocabularies.groupId',
									},
									'$group[descriptiveName] = /group/get-group': {
										'@groupId': '$vocabularies.groupId',
									},
									className,
									groupIds,
								},
							},
							callback
						);
					}
				},

				_getPaginatorConfig(item) {
					var instance = this;

					var paginatorConfig = {
						offsetParam: STR_START,
					};

					var maxEntries = instance.get(STR_MAX_ENTRIES);

					if (maxEntries > 0) {
						paginatorConfig.limit = maxEntries;
						paginatorConfig.moreResultsLabel = instance.get(
							STR_MORE_RESULTS_LABEL
						);
						paginatorConfig.total = item.childrenCount;
					}
					else {
						paginatorConfig.end = -1;
						paginatorConfig.start = -1;
					}

					return paginatorConfig;
				},

				_getTreeNodeAssetId(treeNode) {
					var treeId = treeNode.get(ID);

					var match = treeId.match(/(\d+)$/);

					return match ? match[1] : null;
				},

				_getTreeNodeAssetType(treeNode) {
					var treeId = treeNode.get(ID);

					var match = treeId.match(/^(vocabulary|category)/);

					return match ? match[1] : null;
				},

				_initSearch: EMPTY_FN,

				_initSearchFocus() {
					var instance = this;

					var popup = instance._popup;

					var vocabularyGroupIds = instance.get('vocabularyGroupIds');
					var vocabularyIds = instance.get('vocabularyIds');

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
							instance
						);

						popup.searchField.on('keyup', (event) => {
							if (!event.isNavKey()) {
								searchCategoriesTask(
									event,
									searchResults,
									vocabularyIds,
									vocabularyGroupIds,
									processSearchResults
								);
							}
						});

						if (instance.get('singleSelect')) {
							var onSelectChange = A.bind(
								'_onSelectChange',
								instance
							);

							popup.entriesNode.delegate(
								'change',
								onSelectChange,
								'input[type=radio]'
							);
						}
					}

					popup.entriesNode.append(searchResults);

					instance._searchBuffer = [];
				},

				_isValidString(value) {
					return Lang.isString(value) && value.length;
				},

				_onBoundingBoxClick: EMPTY_FN,

				_onCheckboxCheck(event) {
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
						categoryId: assetId,
					};

					entry[matchKey] = entryMatchKey;

					entry.value = LString.unescapeHTML(entry.value);

					instance.entries.add(entry);
				},

				_onCheckboxClick(event) {
					var instance = this;

					var method = '_onCheckboxUncheck';

					if (event.currentTarget.attr('checked')) {
						method = '_onCheckboxCheck';
					}

					instance[method](event);
				},

				_onCheckboxUncheck(event) {
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

				_onCheckedChange(event) {
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

				_onSelectChange(event) {
					var instance = this;

					instance._clearEntries();

					instance._onCheckboxCheck(event);
				},

				_processSearchResults(searchResults, results) {
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

						categories.forEach((item) => {
							item.checked =
								instance.entries.findIndexBy(
									'categoryId',
									item.categoryId
								) > -1
									? TPL_CHECKED
									: '';

							item.inputName = inputName;
							item.type = inputType;

							buffer.push(Lang.sub(TPL_INPUT, item));
						});
					}
					else {
						var message = Lang.sub(TPL_MESSAGE, [
							Liferay.Language.get('no-categories-were-found'),
						]);

						buffer.push(message);
					}

					searchResults.removeClass(CSS_LOADING_ANIMATION);

					searchResults.html(buffer.join(''));
				},

				_renderIcons() {
					var instance = this;

					var contentBox = instance.get('contentBox');

					instance.icons = new A.Toolbar({
						children: [
							{
								label: instance.get('label'),
								on: {
									click: A.bind('_showSelectPopup', instance),
								},
								title: instance.get('title'),
							},
						],
					}).render(contentBox);

					var iconsBoundingBox = instance.icons.get(BOUNDING_BOX);

					instance.entryHolder.placeAfter(iconsBoundingBox);
				},

				_searchCategories(
					event,
					searchResults,
					vocabularyIds,
					vocabularyGroupIds,
					callback
				) {
					var instance = this;

					var searchValue = event.currentTarget.val().trim();

					instance._searchValue = searchValue;

					if (searchValue) {
						searchResults.empty();

						searchResults.addClass(CSS_LOADING_ANIMATION);

						Liferay.Service(
							{
								'$display = /assetcategory/search-categories-display': {
									'categories.$path = /assetcategory/get-category-path': {
										'@categoryId':
											'$display.categories.categoryId',
									},
									'end': -1,
									'groupIds': vocabularyGroupIds,
									'start': -1,
									'title': searchValue,
									vocabularyIds,
								},
							},
							callback
						);
					}

					searchResults.toggle(!!searchValue);

					var treeViews = instance.TREEVIEWS;

					AObject.each(treeViews, (item) => {
						item.toggle(!searchValue);
					});
				},

				_showPopup() {
					var instance = this;

					Liferay.Util.getTop().AUI().use('aui-tree');

					AssetCategoriesSelector.superclass._showPopup.apply(
						instance,
						arguments
					);
				},

				_showSelectPopup(event) {
					var instance = this;

					instance._showPopup(event);

					var popup = instance._popup;

					popup.titleNode.html(Liferay.Language.get('categories'));

					popup.entriesNode.addClass(CSS_TAGS_LIST);

					var className = instance.get('className');

					instance._getEntries(className, (entries) => {
						var searchResults = instance._searchResultsNode;
						var searchValue = instance._searchValue;

						if (searchResults) {
							searchResults.removeClass(CSS_LOADING_ANIMATION);

							searchResults.toggle(!!searchValue);
						}

						popup.entriesNode
							.all('.tree-view, .loading-animation')
							.remove(true);

						entries.forEach(
							instance._vocabulariesIterator,
							instance
						);

						A.each(instance.TREEVIEWS, (item) => {
							item.toggle(!searchValue);

							item.expandAll();
						});
					});

					if (instance._bindSearchHandle) {
						instance._bindSearchHandle.detach();
					}

					instance._bindSearchHandle = popup.searchField.once(
						'focus',
						instance._initSearchFocus,
						instance
					);
				},

				_vocabulariesIterator(item) {
					var instance = this;

					var popup = instance._popup;
					var vocabularyId = item.vocabularyId;
					var vocabularyTitle = LString.escapeHTML(
						item.titleCurrentValue
					);

					if (item.groupId === themeDisplay.getCompanyGroupId()) {
						vocabularyTitle +=
							' (' + Liferay.Language.get('global') + ')';
					}
					else {
						vocabularyTitle +=
							' (' + item.group.descriptiveName + ')';
					}

					var treeId = 'vocabulary' + vocabularyId;

					var vocabularyRootNode = {
						alwaysShowHitArea: true,
						id: treeId,
						label: vocabularyTitle,
						leaf: false,
						paginator: instance._getPaginatorConfig(item),
						type: 'io',
					};

					instance.TREEVIEWS[vocabularyId] = new A.TreeView({
						children: [vocabularyRootNode],
						io: {
							cfg: {
								data: A.bind(
									'_formatRequestData',
									instance,
									item.groupId,
									vocabularyId
								),
								on: {
									success() {
										var treeViews = instance.TREEVIEWS;

										var tree = treeViews[vocabularyId];

										var children = tree.get('children');

										if (
											!children ||
											!children.length ||
											!children[0].hasChildNodes()
										) {
											tree.destroy();

											delete treeViews[vocabularyId];
										}
									},
								},
							},
							formatter: A.bind('_formatJSONResult', instance),
							url:
								themeDisplay.getPathMain() +
								'/asset/get_categories',
						},
					}).render(popup.entriesNode);
				},

				TREEVIEWS: {},
				UI_EVENTS: {},

				bindUI() {
					var instance = this;

					AssetCategoriesSelector.superclass.bindUI.apply(
						instance,
						arguments
					);
				},

				renderUI() {
					var instance = this;

					AssetCategoriesSelector.superclass.constructor.superclass.renderUI.apply(
						instance,
						arguments
					);

					instance._renderIcons();

					instance.inputContainer.addClass('hide-accessible');

					instance._applyARIARoles();
				},

				syncUI() {
					var instance = this;

					AssetCategoriesSelector.superclass.constructor.superclass.syncUI.apply(
						instance,
						arguments
					);

					var matchKey = instance.get('matchKey');

					instance.entries.getKey = function (object) {
						return object.categoryId;
					};

					var curEntries = instance.get('curEntries');
					var curEntryIds = instance.get('curEntryIds');

					curEntryIds.forEach((item, index) => {
						var entry = {
							categoryId: item,
						};

						entry[matchKey] = curEntries[index];

						entry.value = LString.unescapeHTML(entry.value);

						instance.entries.add(entry);
					});
				},
			},
		});

		Liferay.AssetCategoriesSelector = AssetCategoriesSelector;
	},
	'',
	{
		requires: ['aui-tree', 'liferay-asset-tags-selector'],
	}
);
