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
		const Lang = A.Lang;

		const LString = Lang.String;

		// eslint-disable-next-line @liferay/aui/no-object
		const AObject = A.Object;

		const BOUNDING_BOX = 'boundingBox';

		const CSS_LOADING_ANIMATION = 'loading-animation';

		const CSS_TAGS_LIST = 'lfr-categories-selector-list';

		const EMPTY_FN = Lang.emptyFn;

		const ID = 'id';

		const NAME = 'categoriesselector';

		const STR_MAX_ENTRIES = 'maxEntries';

		const STR_MORE_RESULTS_LABEL = 'moreResultsLabel';

		const STR_START = 'start';

		const TPL_CHECKED = ' checked="checked" ';

		const TPL_INPUT =
			'<label class="d-flex" title="{titleCurrentValue}">' +
			'<span class="flex-fill lfr-categories-selector-category-name text-truncate" title="{titleCurrentValue}">' +
			'<input data-categoryId="{categoryId}" data-vocabularyid="{vocabularyId}" name="{inputName}" type="{type}" value="{titleCurrentValue}" {checked} />' +
			'{titleCurrentValue}' +
			'</span>' +
			'<span class="flex-fill lfr-categories-selector-search-results-path small text-right text-secondary text-truncate" title="{path}">{path}</span>' +
			'</label>';

		const TPL_MESSAGE = '<div class="lfr-categories-message">{0}</div>';

		const TPL_SEARCH_RESULTS =
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

		const AssetCategoriesSelector = A.Component.create({
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
					const instance = this;

					const boundingBox = instance.get(BOUNDING_BOX);
					const labelNode = instance.get('labelNode');

					if (labelNode) {
						boundingBox.attr('aria-labelledby', labelNode.attr(ID));

						labelNode.attr('for', boundingBox.attr(ID));
					}
				},

				_bindTagsSelector: EMPTY_FN,

				_clearEntries() {
					const instance = this;

					const entries = instance.entries;

					entries.each(A.fn('removeAt', entries, 0));
				},

				_formatJSONResult(json) {
					const instance = this;

					const output = [];

					let type = 'check';

					if (instance.get('singleSelect')) {
						type = 'radio';
					}

					json.forEach((item) => {
						let checked = false;
						const treeId = 'category' + item.categoryId;

						if (
							instance.entries.findIndexBy(
								'categoryId',
								item.categoryId
							) > -1
						) {
							checked = true;
						}

						const newTreeNode = {
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
					const instance = this;

					const data = {};

					data.p_auth = Liferay.authToken;
					data.scopeGroupId = groupId;

					const assetId = instance._getTreeNodeAssetId(treeNode);
					const assetType = instance._getTreeNodeAssetType(treeNode);

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
					const instance = this;

					const portalModelResource = instance.get(
						'portalModelResource'
					);

					const groupIds = [];

					const vocabularyIds = instance.get('vocabularyIds');

					if (vocabularyIds.length) {
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
					const instance = this;

					const paginatorConfig = {
						offsetParam: STR_START,
					};

					const maxEntries = instance.get(STR_MAX_ENTRIES);

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
					const treeId = treeNode.get(ID);

					const match = treeId.match(/(\d+)$/);

					return match ? match[1] : null;
				},

				_getTreeNodeAssetType(treeNode) {
					const treeId = treeNode.get(ID);

					const match = treeId.match(/^(vocabulary|category)/);

					return match ? match[1] : null;
				},

				_initSearch: EMPTY_FN,

				_initSearchFocus() {
					const instance = this;

					const popup = instance._popup;

					const vocabularyGroupIds = instance.get(
						'vocabularyGroupIds'
					);
					const vocabularyIds = instance.get('vocabularyIds');

					let searchResults = instance._searchResultsNode;

					if (!searchResults) {
						searchResults = A.Node.create(TPL_SEARCH_RESULTS);

						instance._searchResultsNode = searchResults;

						const processSearchResults = A.bind(
							'_processSearchResults',
							instance,
							searchResults
						);

						const searchCategoriesTask = A.debounce(
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
							const onSelectChange = A.bind(
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
					const instance = this;

					const currentTarget = event.currentTarget;

					let assetId;
					let entryMatchKey;

					if (A.instanceOf(currentTarget, A.Node)) {
						assetId = currentTarget.attr('data-categoryId');

						entryMatchKey = currentTarget.val();
					}
					else {
						assetId = instance._getTreeNodeAssetId(currentTarget);

						entryMatchKey = currentTarget.get('label');
					}

					const matchKey = instance.get('matchKey');

					const entry = {
						categoryId: assetId,
					};

					entry[matchKey] = entryMatchKey;

					entry.value = LString.unescapeHTML(entry.value);

					instance.entries.add(entry);
				},

				_onCheckboxClick(event) {
					const instance = this;

					let method = '_onCheckboxUncheck';

					if (event.currentTarget.attr('checked')) {
						method = '_onCheckboxCheck';
					}

					instance[method](event);
				},

				_onCheckboxUncheck(event) {
					const instance = this;

					const currentTarget = event.currentTarget;

					let assetId;

					if (A.instanceOf(currentTarget, A.Node)) {
						assetId = currentTarget.attr('data-categoryId');
					}
					else {
						assetId = instance._getTreeNodeAssetId(currentTarget);
					}

					instance.entries.removeKey(assetId);
				},

				_onCheckedChange(event) {
					const instance = this;

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
					const instance = this;

					instance._clearEntries();

					instance._onCheckboxCheck(event);
				},

				_processSearchResults(searchResults, results) {
					const instance = this;

					const buffer = instance._searchBuffer;

					buffer.length = 0;

					const categories = results.categories;

					if (categories.length) {
						let inputType = 'checkbox';

						if (instance.get('singleSelect')) {
							inputType = 'radio';
						}

						const inputName = A.guid();

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
						const message = Lang.sub(TPL_MESSAGE, [
							Liferay.Language.get('no-categories-were-found'),
						]);

						buffer.push(message);
					}

					searchResults.removeClass(CSS_LOADING_ANIMATION);

					searchResults.html(buffer.join(''));
				},

				_renderIcons() {
					const instance = this;

					const contentBox = instance.get('contentBox');

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

					const iconsBoundingBox = instance.icons.get(BOUNDING_BOX);

					instance.entryHolder.placeAfter(iconsBoundingBox);
				},

				_searchCategories(
					event,
					searchResults,
					vocabularyIds,
					vocabularyGroupIds,
					callback
				) {
					const instance = this;

					const searchValue = event.currentTarget.val().trim();

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

					const treeViews = instance.TREEVIEWS;

					AObject.each(treeViews, (item) => {
						item.toggle(!searchValue);
					});
				},

				_showPopup() {
					const instance = this;

					Liferay.Util.getTop().AUI().use('aui-tree');

					AssetCategoriesSelector.superclass._showPopup.apply(
						instance,
						arguments
					);
				},

				_showSelectPopup(event) {
					const instance = this;

					instance._showPopup(event);

					const popup = instance._popup;

					popup.titleNode.html(Liferay.Language.get('categories'));

					popup.entriesNode.addClass(CSS_TAGS_LIST);

					const className = instance.get('className');

					instance._getEntries(className, (entries) => {
						const searchResults = instance._searchResultsNode;
						const searchValue = instance._searchValue;

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
					const instance = this;

					const popup = instance._popup;
					const vocabularyId = item.vocabularyId;
					let vocabularyTitle = LString.escapeHTML(
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

					const treeId = 'vocabulary' + vocabularyId;

					const vocabularyRootNode = {
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
										const treeViews = instance.TREEVIEWS;

										const tree = treeViews[vocabularyId];

										const children = tree.get('children');

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
					const instance = this;

					AssetCategoriesSelector.superclass.bindUI.apply(
						instance,
						arguments
					);
				},

				renderUI() {
					const instance = this;

					AssetCategoriesSelector.superclass.constructor.superclass.renderUI.apply(
						instance,
						arguments
					);

					instance._renderIcons();

					instance.inputContainer.addClass('hide-accessible sr-only');

					instance._applyARIARoles();
				},

				syncUI() {
					const instance = this;

					AssetCategoriesSelector.superclass.constructor.superclass.syncUI.apply(
						instance,
						arguments
					);

					const matchKey = instance.get('matchKey');

					instance.entries.getKey = function (object) {
						return object.categoryId;
					};

					const curEntries = instance.get('curEntries');
					const curEntryIds = instance.get('curEntryIds');

					curEntryIds.forEach((item, index) => {
						const entry = {
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
