AUI.add(
	'liferay-category-admin',
	function(A) {
		var AArray = A.Array;
		var AObject = A.Object;
		var HistoryManager = Liferay.HistoryManager;
		var JSON = A.JSON;
		var Lang = A.Lang;
		var Node = A.Node;
		var Util = Liferay.Util;

		var owns = AObject.owns;
		var toInt = Lang.toInt;

		var ACTION_ADD = 0;

		var ACTION_ADD_SUBCATEGORY = 4;

		var ACTION_EDIT = 1;

		var ACTION_MOVE = 2;

		var ACTION_VIEW = 3;

		var CATEGORY = 0;

		var CSS_ACTIVE_AREA = 'active-area';

		var CSS_ADMIN_DIALOG = 'portlet-asset-categories-admin-dialog';

		var CSS_CATEGORY_ITEM_CHECK = 'category-item-check';

		var CSS_COLUMN_WIDTH_CATEGORY = 'span5';

		var CSS_COLUMN_WIDTH_CATEGORY_FULL = 'span9';

		var CSS_MESSAGE_RESPONSE = 'lfr-message-response';

		var CSS_MESSAGE_ERROR = 'alert alert-error';

		var CSS_MESSAGE_SUCCESS = 'alert alert-success';

		var CSS_VOCABULARY_ITEM_CHECK = 'vocabulary-item-check';

		var DATA_CATEGORY_ID = 'data-categoryId';

		var DATA_CATEGORY_URL = 'data-url';

		var DATA_VOCABULARY = 'data-vocabulary';

		var DATA_VOCABULARY_ID = 'data-vocabularyId';

		var DEFAULT_DEBOUNCE_TIMEOUT = 50;

		var EVENT_CLICK = 'click';

		var EVENT_SAVE_AUTO_FIELDS = 'saveAutoFields';

		var EVENT_SUBMIT = 'submit';

		var EVENT_VISIBLE_CHANGE = 'visibleChange';

		var EXCEPTION_NO_SUCH_VOCABULARY = 'NoSuchVocabularyException';

		var EXCEPTION_PRINCIPAL = 'auth.PrincipalException';

		var EXCEPTION_VOCABULARY_NAME = 'VocabularyNameException';

		var ID = 'id';

		var INVALID_VALUE = A.Attribute.INVALID_VALUE;

		var LIFECYCLE_RENDER = 0;

		var LIFECYCLE_PROCESS = 1;

		var MESSAGE_TYPE_ALERT = 'alert';

		var MESSAGE_TYPE_ERROR = 'error';

		var MESSAGE_TYPE_SUCCESS = 'success';

		var MODE_RENDER_FLAT = 0;

		var REGEX_TREE_NODE_ID = /(\d+)$/;

		var REGEX_TREE_NODE_TYPE = /^(vocabulary|category)/;

		var SELECTOR_BUTTON_CANCEL = '.close-panel';

		var SELECTOR_CATEGORY_ITEM = '.category-item';

		var SELECTOR_CATEGORY_NAME_INPUT = '.category-name input';

		var SELECTOR_FLOATING_TRIGGER = '.lfr-floating-trigger';

		var SELECTOR_UPDATE_CATEGORY_FORM = 'form.update-category-form';

		var SELECTOR_UPDATE_VOCABULARY_FORM = 'form.update-vocabulary-form';

		var SELECTOR_VOCABULARY_MESSAGES = '#vocabulary-messages';

		var SELECTOR_VOCABULARY_CATEGORY_MESSAGES = '#vocabulary-category-messages';

		var SELECTOR_VOCABULARY_NAME_INPUT = '.vocabulary-name input';

		var SELECTOR_VOCABULARY_SELECT_LIST = '.vocabulary-select-list';

		var STR_ACTION = 'action';

		var STR_AUTO_FIELDS_INSTANCE = 'autoFieldsInstance';

		var STR_BOUNDING_BOX = 'boundingBox';

		var STR_CATEGORY_ID = 'categoryId';

		var STR_CATEGORY_NODE = 'categoryNode';

		var STR_CHECKED = 'checked';

		var STR_CONTENT_BOX = 'contentBox';

		var STR_EDIT_CATEGORY = 'edit_category';

		var STR_EMPTY = '';

		var STR_LABEL = 'label';

		var STR_NODE = 'node';

		var STR_PAGE = 'page';

		var STR_PARENT_NODE = 'parentNode';

		var STR_PARENT_CATEGORY_ID = 'parentCategoryId';

		var STR_QUERY = 'query';

		var STR_SELECTED = 'active';

		var STR_SPACE = ' ';

		var STR_SUCCESS = 'success';

		var STR_TITLE = 'title';

		var STR_TREE_NODE = 'tree-node';

		var STR_TREE_VIEW = 'tree-view';

		var STR_URI = 'uri';

		var STR_VOCABULARY_ID = 'vocabularyId';

		var STR_VOCABULARIES = 'vocabularies';

		var STR_ZINDEX = 'zIndex';

		var STR_RESPONSE_DATA = 'responseData';

		var STR_ROWS_PER_PAGE = 'rowsPerPage';

		var TPL_CATEGORY_ITEM =
			'<label class="category-item" id="categoryNode{categoryId}" title="{name}">' +
				'<span class="category-name" title="{name}">' +
					'<input class="category-item-check" data-categoryId="{categoryId}" name="category-item-check" type="checkbox" value="{name}" {checked} />' +
					'{name}' +
				'</span>' +
				'<span class="category-path" title="{path}">{path}</span>' +
			'</label>';

		var TPL_MESSAGES_CATEGORY = '<div class="hide lfr-message-response" id="vocabulary-category-messages" />';

		var TPL_MESSAGES_PORTLET = '<div class="hide lfr-message-response" id="porlet-messages" />';

		var TPL_MESSAGES_VOCABULARY = '<div class="hide lfr-message-response" id="vocabulary-messages" />';

		var TPL_SEARCH_QUERY = '%{0}%';

		var TPL_VOCABULARY_LIST_CONTAINER = '<ul class="nav nav-pills nav-stacked">';

		var TPL_VOCABULARY_LIST =
			'<li class="vocabulary-category results-row {cssClassSelected}" data-vocabulary="{titleCurrentValue}" data-vocabularyId="{vocabularyId}" tabIndex="0">' +
				'<a href="javascript:;" data-vocabularyId="{vocabularyId}" tabIndex="-1">' +
					'<input type="checkbox" class="vocabulary-item-check" name="vocabulary-item-check" data-vocabularyId="{vocabularyId}" data-vocabularyName="{titleCurrentValue}">' +
					'<span class="vocabulary-item-name" data-vocabularyId="{vocabularyId}">{titleCurrentValue}</span>' +
					'<span tabindex="0" class="vocabulary-item-actions-trigger" data-vocabularyId="{vocabularyId}"></span>' +
				'</a>' +
			'</li>';

		var TPL_VOCABULARY_OPTION = '<option value="{vocabularyId}">{titleCurrentValue}</option>';

		var TPL_CATEGORIES_TREE_CONTAINER = '<div class="categories-treeview-container" id="categoriesTreeContainer"></div>';

		var TYPE_VOCABULARY = 1;

		var AssetCategoryAdmin = A.Component.create(
			{
				EXTENDS: A.Base,

				NAME: 'assetcategoryadmin',

				prototype: {
					initializer: function(config) {
						var instance = this;

						instance._ioCategory = {};

						instance._originalConfig = config;

						var categoriesContainer = A.one(instance._categoryContainerSelector);
						var vocabularyList = A.one(instance._vocabularyListSelector);

						instance.portletId = config.portletId;

						instance._prefixedPortletId = '_' + config.portletId + '_';

						instance._container = A.one('.categories-admin-content');
						instance._categoryViewSection = instance._container.one('.vocabulary-edit-category');
						instance._categoryViewContainer = instance._categoryViewSection.one('.category-view');

						instance._categoryMessageContainer = Node.create(TPL_MESSAGES_CATEGORY);
						instance._portletMessageContainer = Node.create(TPL_MESSAGES_PORTLET);
						instance._vocabularyMessageContainer = Node.create(TPL_MESSAGES_VOCABULARY);

						instance._container.placeBefore(instance._portletMessageContainer);
						categoriesContainer.placeBefore(instance._categoryMessageContainer);
						vocabularyList.placeBefore(instance._vocabularyMessageContainer);

						instance._dialogAlignConfig = {
							node: '.vocabulary-list-container',
							points: ['tl', 'tc']
						};

						var namespace = instance._prefixedPortletId;

						var idPrefix = '#' + namespace;

						instance._toggleAllCategories = A.debounce(instance._toggleAllCategoriesFn, DEFAULT_DEBOUNCE_TIMEOUT);
						instance._toggleAllVocabularies = A.debounce(instance._toggleAllVocabulariesFn, DEFAULT_DEBOUNCE_TIMEOUT);

						instance._searchInput = A.one(idPrefix + 'categoriesAdminSearchInput');
						instance._searchType = A.one(idPrefix + 'categoriesAdminSelectSearch');

						A.one('.category-view-close').on(EVENT_CLICK, instance._closeEditSection, instance);

						instance._searchType.on('change', A.debounce(instance._onSearchTypeChange, DEFAULT_DEBOUNCE_TIMEOUT), instance);

						instance._categoryViewContainer.on(EVENT_CLICK, instance._onCategoryViewContainerClick, instance);

						var portletMessageContainer = instance._portletMessageContainer;

						instance._hideMessageTask = A.debounce('hide', 7000, portletMessageContainer);

						vocabularyList.on(EVENT_CLICK, instance._onVocabularyListClick, instance);

						vocabularyList.on('key', instance._onVocabularyListSelect, 'up:13', instance);

						var addCategoryButton = A.one(idPrefix + 'addCategoryButton');

						if (addCategoryButton) {
							addCategoryButton.on(EVENT_CLICK, instance._onShowCategoryPanel, instance, ACTION_ADD);
						}

						instance._addCategoryButton = addCategoryButton;

						var addVocabularyButton = A.one(idPrefix + 'addVocabularyButton');

						if (addVocabularyButton) {
							addVocabularyButton.on(EVENT_CLICK, instance._onShowVocabularyPanel, instance, ACTION_ADD);
						}

						var permissionButton = A.one(idPrefix + 'categoryPermissionsButton');

						if (permissionButton) {
							permissionButton.on(EVENT_CLICK, instance._onChangePermissions, instance);
						}

						A.one(idPrefix + 'deleteSelectedItems').on(EVENT_CLICK, instance._deleteSelected, instance);

						var checkAllVocabulariesCheckbox = A.one(idPrefix + 'checkAllVocabulariesCheckbox');

						checkAllVocabulariesCheckbox.on(EVENT_CLICK, instance._checkAllVocabularies, instance);

						instance._checkAllVocabulariesCheckbox = checkAllVocabulariesCheckbox;

						var checkAllCategoriesCheckbox = A.one(idPrefix + 'checkAllCategoriesCheckbox');

						checkAllCategoriesCheckbox.on(EVENT_CLICK, instance._checkAllCategories, instance);

						instance._checkAllCategoriesCheckbox = checkAllCategoriesCheckbox;

						instance._categoriesContainer = categoriesContainer;

						instance._createLiveSearch();

						HistoryManager.on('stateChange', instance._onStateChange, instance);

						instance._loadData();

						instance.after('drop:enter', instance._afterDragEnter);
						instance.after('drop:exit', instance._afterDragExit);

						instance.on('drop:hit', instance._onDragDrop);
					},

					_addCategory: function(form, action) {
						var instance = this;

						var ioCategory = instance._getIOCategory(action);

						ioCategory.set('form', form.getDOM());
						ioCategory.set(STR_URI, form.attr(STR_ACTION));

						ioCategory.start();
					},

					_addVocabulary: function(form) {
						var instance = this;

						var ioVocabulary = instance._getIOVocabulary();

						ioVocabulary.set('form', form.getDOM());
						ioVocabulary.set(STR_URI, form.attr(STR_ACTION));

						ioVocabulary.start();
					},

					_afterDragEnter: function(event) {
						var instance = this;

						var dropNode = event.drop.get(STR_NODE);

						dropNode.addClass(CSS_ACTIVE_AREA);
					},

					_afterDragExit: function(event) {
						var instance = this;

						var dropNode = event.target.get(STR_NODE);

						dropNode.removeClass(CSS_ACTIVE_AREA);
					},

					_afterVocabulariesPaginationChangeRequest: function(event) {
						var instance = this;

						var lastState = event.lastState;
						var state = event.state;

						var historyState = {};

						var paginationMap = instance._getVocabulariesPaginationMap();

						AObject.each(
							paginationMap,
							function(item, index, collection) {
								if (owns(state, index)) {
									var historyEntry = item.historyEntry;

									var newItemValue = state[index];

									var value = INVALID_VALUE;

									if (newItemValue === item.defaultValue &&
										Lang.isValue(HistoryManager.get(historyEntry))) {

										value = null;
									}
									else if (lastState && (newItemValue !== lastState[index])) {
										value = newItemValue;
									}

									if (value !== INVALID_VALUE) {
										historyState[historyEntry] = value;
									}
								}
							}
						);

						if (!AObject.isEmpty(historyState)) {
							HistoryManager.add(historyState);
						}

						instance._loadData();
					},

					_bindCloseEvent: function(contextPanel) {
						var instance = this;

						contextPanel.get(STR_BOUNDING_BOX).on('key', contextPanel.hide, 'up:27', contextPanel);
					},

					_clearCategoriesContainer: function() {
						var instance = this;

						if (instance._categoriesTreeView) {
							instance._categoriesTreeView.destroy();

							instance._categoriesTreeView = null;
						}

						instance._categoriesContainer.empty();
					},

					_createAlertMessage: function(items, selectedNames, keyId) {
						var instance = this;

						var itemNames = AArray.map(
							items,
							function(item, index, collection) {
								var itemId = item[keyId];

								return selectedNames[itemId];
							}
						);

						return Liferay.Language.get('the-following-items-could-not-be-deleted') + ' ' + itemNames.join(', ');
					},

					_createCategoryFlatView: function(categories) {
						var instance = this;

						instance._clearCategoriesContainer();

						if (categories.length) {
							var buffer = AArray.map(
								categories,
								function(item, index, collection) {
									if (item.parentCategoryId == 0) {
										instance._vocabularyRootCategories[item.categoryId] = 1;
									}

									return Lang.sub(TPL_CATEGORY_ITEM, item);
								}
							);

							instance._categoriesContainer.html(buffer.join(STR_EMPTY));

							if (!instance._categoresSearchHandle) {
								instance._categoresSearchHandle = instance._categoriesContainer.delegate(
									EVENT_CLICK,
									instance._onCategorySearchClick,
									'input[type=checkbox]',
									instance
								);
							}
						}
						else {
							instance._showCateroryMessage();
						}
					},

					_createCategoryTreeView: function(categories) {
						var instance = this;

						instance._clearCategoriesContainer();

						var boundingBox = Node.create(TPL_CATEGORIES_TREE_CONTAINER);

						instance._categoriesContainer.append(boundingBox);

						var paginatorConfig = {
							limit: 10,
							moreResultsLabel: Liferay.Language.get('load-more-results'),
							offsetParam: 'start',
							total: instance._getVocabularyCategoriesCount(instance._vocabularies, instance._selectedVocabularyId)
						};

						instance._categoriesTreeView = new CategoriesTree(
							{
								boundingBox: boundingBox,
								children: categories,
								on: {
									dropAppend: function(event) {
										var tree = event.tree;

										var fromCategoryId = instance._getCategoryId(tree.dragNode);
										var toCategoryId = instance._getCategoryId(tree.dropNode);
										var vocabularyId = instance._selectedVocabularyId;

										instance._merge(fromCategoryId, toCategoryId, vocabularyId);

										instance._categoriesTreeView.reinsertChild(tree.dragNode, tree.dropNode);
									},
									dropFailed: function(event) {
										var tree = event.tree;

										var parentNode = tree.dropNode.get(STR_PARENT_NODE);
										var dragParentNode = tree.dragNode.get(STR_PARENT_NODE);

										var toCategoryId = instance._getCategoryId(tree.dropNode);
										var fromCategoryId = instance._getCategoryId(dragParentNode);

										if (tree.instance.dropAction != 'append') {
											toCategoryId = instance._getCategoryId(parentNode);
										}

										var errorKey = STR_EMPTY;

										if (toCategoryId == fromCategoryId) {
											errorKey = Liferay.Language.get('changing-the-order-of-the-categories-is-not-supported');
										}
										else {
											errorKey = Liferay.Language.get('there-is-another-category-with-the-same-name-and-the-same-parent');
										}

										instance._sendMessage(MESSAGE_TYPE_ERROR, errorKey);
									},
									dropInsert: function(event) {
										var tree = event.tree;

										var parentNode = tree.dropNode.get(STR_PARENT_NODE);
										var fromCategoryId = instance._getCategoryId(tree.dragNode);
										var toCategoryId = instance._getCategoryId(parentNode);
										var vocabularyId = instance._selectedVocabularyId;

										instance._merge(fromCategoryId, toCategoryId, vocabularyId);

										instance._categoriesTreeView.reinsertChild(tree.dragNode, parentNode);
									}
								},
								io: {
									cfg: {
										data: A.bind('_formatRequestData', instance)
									},
									formatter: A.bind('_formatJSONResult', instance),
									url: themeDisplay.getPathMain() + '/asset/get_categories'
								},
								paginator: paginatorConfig,
								type: 'normal'
							}
						).render();
					},

					_createItemNameMap: function(itemIds, itemLookupFn, attrLookup, attr) {
						var instance = this;

						var itemNameMap = {};

						var length = itemIds.length;

						for (var i = 0; i < length; i++) {
							var itemId = itemIds[i];

							var item = itemLookupFn(itemId);

							var attrLookupFn = item[attrLookup];

							itemNameMap[itemId] = attrLookupFn.call(item, attr);
						}

						return itemNameMap;
					},

					_checkAllCategories: function(event) {
						var instance = this;

						instance._toggleAllCategories(event.currentTarget.attr(STR_CHECKED));
					},

					_checkAllVocabularies: function(event) {
						var instance = this;

						instance._toggleAllVocabularies(event.currentTarget.attr(STR_CHECKED));
					},

					_closeEditSection: function() {
						var instance = this;

						instance._hideSection(instance._categoryViewSection);

						var selectedCategory = instance._selectedCategory;

						if (selectedCategory) {
							if (Lang.isFunction(selectedCategory.unselect)) {
								selectedCategory.unselect();
							}
							else {
								selectedCategory.removeClass(STR_SELECTED);
							}
						}
					},

					_createCategoryPanelAdd: function() {
						var instance = this;

						instance._categoryPanelAdd = Liferay.Util.Window.getWindow(
							{
								dialog: {
									cssClass: CSS_ADMIN_DIALOG
								},
								title: Liferay.Language.get('add-category')
							}
						);

						instance._categoryPanelAdd.hide();

						instance._bindCloseEvent(instance._categoryPanelAdd);

						instance._categoryPanelAdd.on(
							EVENT_VISIBLE_CHANGE,
							function(event) {
								if (!event.newVal) {
									if (instance._categoryFormAdd) {
										instance._categoryFormAdd.reset();
									}

									instance._hideFloatingPanels(event);
									instance._resetCategoriesProperties(event);
								}
							}
						);

						return instance._categoryPanelAdd;
					},

					_createLiveSearch: function() {
						var instance = this;

						var liveSearch = new LiveSearch(
							{
								inputNode: instance._searchInput,
								minQueryLength: 0,
								queryDelay: 300
							}
						);

						liveSearch.after(STR_QUERY, instance._processSearch, instance);

						instance._searchInput.on('keydown', instance._onSearchInputKeyDown, instance);

						instance._liveSearch = liveSearch;
					},

					_createVocabularyPanelAdd: function() {
						var instance = this;

						instance._vocabularyPanelAdd = Liferay.Util.Window.getWindow(
							{
								dialog: {
									cssClass: CSS_ADMIN_DIALOG
								},
								title: Liferay.Language.get('add-vocabulary')
							}
						);

						instance._vocabularyPanelAdd.hide();

						instance._bindCloseEvent(instance._vocabularyPanelAdd);

						instance._vocabularyPanelAdd.on(
							EVENT_VISIBLE_CHANGE,
							function(event) {
								if (!event.newVal) {
									if (instance._vocabularyFormAdd) {
										instance._vocabularyFormAdd.reset();
									}

									var autoFields = A.one('#' + instance._prefixedPortletId + 'extraFields').getData(STR_AUTO_FIELDS_INSTANCE);

									if (autoFields) {
										autoFields.reset();
									}

									instance._hideFloatingPanels(event);
								}
							}
						);

						return instance._vocabularyPanelAdd;
					},

					_createPanelEdit: function(config) {
						var instance = this;

						instance._panelEdit = Liferay.Util.Window.getWindow(
							{
								dialog: {
									align: instance._dialogAlignConfig,
									cssClass: CSS_ADMIN_DIALOG
								},
								title: Liferay.Language.get('edit-category')
							}
						);

						instance._panelEdit.hide();

						instance._bindCloseEvent(instance._panelEdit);

						instance._panelEdit.on(
							EVENT_VISIBLE_CHANGE,
							function(event) {
								if (!event.newVal) {
									instance._processAutoFieldsTriggers(event, instance._destroyFloatingPanels);

									var body = instance._panelEdit.getStdModNode(A.WidgetStdMod.BODY);

									body.empty();
								}
							}
						);

						return instance._panelEdit;
					},

					_createPanelPermissions: function() {
						var instance = this;

						var panelPermissionsChange = instance._panelPermissionsChange;

						if (!panelPermissionsChange) {
							panelPermissionsChange = Util.Window.getWindow(
								{
									dialog: {
										align: instance._dialogAlignConfig,
										cssClass: 'portlet-asset-categories-admin-dialog permissions-change',
										width: 600
									},
									title: Liferay.Language.get('edit-permissions'),
									uri: 'about:blank'
								}
							);

							instance._panelPermissionsChange = panelPermissionsChange;
						}

						return panelPermissionsChange;
					},

					_createURL: function(type, action, lifecycle, params) {
						var instance = this;

						var path = '/asset_category_admin/';

						var url;

						if (lifecycle == LIFECYCLE_RENDER) {
							url = Liferay.PortletURL.createRenderURL();
						}
						else if (lifecycle == LIFECYCLE_PROCESS) {
							url = Liferay.PortletURL.createActionURL();
						}
						else {
							throw 'Internal error. Unimplemented lifecycle.';
						}

						url.setPortletId(instance.portletId);
						url.setWindowState('exclusive');

						if (type == TYPE_VOCABULARY) {
							path += 'edit_vocabulary';

							if (action == ACTION_EDIT) {
								url.setParameter(STR_VOCABULARY_ID, instance._selectedVocabularyId);
							}
						}
						else if (type == CATEGORY) {
							if (action == ACTION_ADD) {
								path += STR_EDIT_CATEGORY;

								url.setParameter(STR_VOCABULARY_ID, instance._selectedVocabularyId);
							}
							else if (action == ACTION_ADD_SUBCATEGORY) {
								path += STR_EDIT_CATEGORY;

								url.setParameter(STR_PARENT_CATEGORY_ID, instance._selectedCategoryId);
								url.setParameter(STR_VOCABULARY_ID, instance._selectedVocabularyId);
							}
							else if (action == ACTION_EDIT) {
								path += STR_EDIT_CATEGORY;

								url.setParameter(STR_CATEGORY_ID, instance._selectedCategoryId);
								url.setParameter(STR_VOCABULARY_ID, instance._selectedVocabularyId);
							}
							else if (action == ACTION_MOVE) {
								path += STR_EDIT_CATEGORY;

								url.setParameter(STR_CATEGORY_ID, instance._selectedCategoryId);
								url.setParameter('cmd', 'move');
							}
							else if (action == ACTION_VIEW) {
								path += 'view_category';

								url.setParameter(STR_CATEGORY_ID, instance._selectedCategoryId);
								url.setParameter(STR_VOCABULARY_ID, instance._selectedVocabularyId);
							}
						}

						url.setParameter('struts_action', path);

						if (params) {
							for (var key in params) {
								if (owns(params, key)) {
									url.setParameter(key, params[key]);
								}
							}
						}

						url.setDoAsGroupId(themeDisplay.getScopeGroupId());

						return url;
					},

					_deleteCategory: function(categoryId, callback) {
						var instance = this;

						Liferay.Service(
							'/assetcategory/delete-category',
							{
								categoryId: categoryId
							},
							callback
						);
					},

					_deleteSelected: function(event) {
						var instance = this;

						instance._selectedVocabularies = instance._getSelectedVocabularies();

						var ids = AObject.keys(instance._selectedVocabularies);

						if (ids.length) {
							instance._deleteSelectedVocabularies(ids);
						}
						else {
							instance._selectedCategories = instance._getSelectedCategories();

							ids = AObject.keys(instance._selectedCategories);

							if (ids.length) {
								instance._deleteSelectedCategories(ids);
							}
						}

						if (!ids.length) {
							alert(Liferay.Language.get('there-are-no-selected-vocabularies-or-categories'));
						}
					},

					_deleteSelectedCategories: function(categoryIds) {
						var instance = this;

						if (Lang.isArray(categoryIds) && categoryIds.length > 0 &&
							confirm(Liferay.Language.get('are-you-sure-you-want-to-delete-the-selected-categories'))) {

							Liferay.Service(
								'/assetcategory/delete-categories',
								{
									categoryIds: categoryIds,
									serviceContext: JSON.stringify(
										{
											failOnPortalException: false
										}
									)
								},
								A.bind('_processCategoryDeletion', instance, instance._selectedVocabularyId, categoryIds)
							);
						}
					},

					_deleteSelectedVocabularies: function(vocabularyIds) {
						var instance = this;

						if (vocabularyIds.length > 0 &&
							confirm(Liferay.Language.get('are-you-sure-you-want-to-delete-the-selected-vocabularies'))) {

							Liferay.Service(
								'/assetvocabulary/delete-vocabularies',
								{
									vocabularyIds: vocabularyIds,
									serviceContext: JSON.stringify(
										{
											failOnPortalException: false
										}
									)
								},
								A.bind('_processVocabularyDeletion', instance)
							);
						}
					},

					_deleteVocabulary: function(vocabularyId, callback) {
						var instance = this;

						Liferay.Service(
							'/assetvocabulary/delete-vocabulary',
							{
								vocabularyId: vocabularyId
							},
							A.bind(callback, instance)
						);
					},

					_destroyFloatingPanels: function(autoFieldsInstance) {
						var instance = this;

						if (autoFieldsInstance) {
							autoFieldsInstance.destroy();
						}
					},

					_displayCategoriesTreeView: function(categories) {
						var instance = this;

						if (categories.length) {
							instance._createCategoryTreeView(categories);
						}
						else {
							instance._categoriesContainer.empty();

							instance._showCateroryMessage();
						}

						var vocabularyList = A.one(instance._vocabularyListSelector);

						var listLinks = vocabularyList.all('li');

						listLinks.unplug(A.Plugin.Drop);

						var bubbleTargets = [instance];

						if (instance._categoriesTreeView) {
							bubbleTargets.push(instance._categoriesTreeView);
						}

						listLinks.plug(
							A.Plugin.Drop,
							{
								bubbleTargets: bubbleTargets
							}
						);
					},

					_displayList: function(callback) {
						var instance = this;

						var vocabularyList = A.one(instance._vocabularyListSelector);

						instance._showLoading('.vocabulary-categories, .vocabulary-list');

						instance._getVocabularies(
							function(result) {
								var vocabularies = result.vocabularies;

								instance._vocabularies = vocabularies;

								if (vocabularies && vocabularies.length) {
									instance._hideVocabularyMessage();

									var buffer = [TPL_VOCABULARY_LIST_CONTAINER];

									A.each(
										vocabularies,
										function(item, index, collection) {
											if (index === 0) {
												item.cssClassSelected = STR_SELECTED;
											}
											else {
												item.cssClassSelected = STR_EMPTY;
											}

											var auxItem = A.clone(item);

											auxItem.titleCurrentValue = Util.escapeHTML(auxItem.titleCurrentValue);

											buffer.push(Lang.sub(TPL_VOCABULARY_LIST, auxItem));
										}
									);

									buffer.push('</ul>');

									vocabularyList.html(buffer.join(STR_EMPTY));

									var firstVocabulary = A.one(instance._vocabularyItemSelector);

									if (firstVocabulary) {
										instance._selectedVocabularyName = instance._getVocabularyName(firstVocabulary);
										instance._selectedVocabularyId = instance._getVocabularyId(firstVocabulary);
									}

									Util.toggleDisabled(instance._addCategoryButton, !firstVocabulary);
								}
								else {
									vocabularyList.html(STR_EMPTY);

									instance._showVocabularyMessage();
								}

								if (callback) {
									callback();
								}
							}
						);
					},

					_displayVocabularyCategories: function(vocabularyId, callback, renderMode) {
						var instance = this;

						var categoryMessages = A.one(SELECTOR_VOCABULARY_CATEGORY_MESSAGES);

						if (categoryMessages) {
							categoryMessages.hide();
						}

						instance._checkAllCategoriesCheckbox.attr(STR_CHECKED, false);

						instance._vocabularyRootCategories = {};

						if (renderMode == MODE_RENDER_FLAT) {
							instance._getVocabularyCategoriesFlat(
								vocabularyId,
								function(response) {
									instance._displayCategoriesFlatView(response.categories, callback);
								}
							);
						}
						else {
							instance._getVocabularyCategoriesTree(
								vocabularyId,
								function(response) {
									var categories = instance._formatJSONResult(response);

									instance._displayCategoriesTreeView(categories);
								}
							);
						}
					},

					_displayCategoriesFlatView: function(categories, callback) {
						var instance = this;

						instance._createCategoryFlatView(categories);

						if (callback) {
							callback();
						}
					},

					_filterCategory: function(categories, parentCategoryId) {
						var instance = this;

						var filteredCategories = [];

						if (Lang.isArray(categories)) {
							filteredCategories = AArray.filter(
								categories,
								function(item, index, collection) {
									return (item.parentCategoryId == parentCategoryId);
								}
							);
						}

						return filteredCategories;
					},

					_focusCategoryPanelAdd: function() {
						var instance = this;

						var inputCategoryAddNameNode = instance._inputCategoryNameNode || instance._categoryFormAdd.one(SELECTOR_CATEGORY_NAME_INPUT);

						Util.focusFormField(inputCategoryAddNameNode);
					},

					_focusVocabularyPanelAdd: function() {
						var instance = this;

						var inputVocabularyAddNameNode = instance._inputVocabularyAddNameNode || instance._vocabularyFormAdd.one(SELECTOR_VOCABULARY_NAME_INPUT);

						Util.focusFormField(inputVocabularyAddNameNode);
					},

					_formatRequestData: function(treeNode) {
						var instance = this;

						var data = {
							p_auth: Liferay.authToken
						};

						var assetId = instance._getTreeNodeId(treeNode, REGEX_TREE_NODE_ID);

						var assetType = instance._getTreeNodeId(treeNode, REGEX_TREE_NODE_TYPE);

						if (Lang.isValue(assetId)) {
							if (assetType == 'category') {
								data.categoryId = assetId;
							}
							else {
								data.vocabularyId = instance._selectedVocabularyId;
							}
						}

						return data;
					},

					_formatJSONResult: function(json) {
						var instance = this;

						return AArray.map(
							json,
							function(item, index, collection) {
								var checked = false;

								if (item.parentCategoryId == 0) {
									instance._vocabularyRootCategories[item.categoryId] = 1;
								}

								var paginatorConfig = {
									limit: 10,
									moreResultsLabel: Liferay.Language.get('load-more-results'),
									offsetParam: 'start',
									total: item.childrenCount
								};

								return {
									alwaysShowHitArea: item.hasChildren,
									id: STR_CATEGORY_NODE + item.categoryId,
									label: Liferay.Util.escapeHTML(item.titleCurrentValue),
									paginator: paginatorConfig,
									type: 'check',
									on: {
										checkedChange: function(event) {
											if (event.newVal) {
												instance._toggleAllVocabularies(false);
											}
										},
										select: function(event) {
											var categoryId = instance._getCategoryId(event.target);

											instance._showCategoryViewContainer(categoryId);
										}
									}
								};
							}
						);
					},

					_getCategory: function(categoryId) {
						var instance = this;

						var categoryNode = instance._categoriesContainer.one('#' + STR_CATEGORY_NODE + categoryId);

						var category = categoryNode.getData('tree-node');

						if (!A.instanceOf(category, A.TreeNode)) {
							category = categoryNode;
						}

						return category;
					},

					_getCategoryId: function(node) {
						var instance = this;

						var categoryId = STR_EMPTY;

						if (node) {
							var nodeId = node.get(ID) || STR_EMPTY;

							categoryId = nodeId.replace(STR_CATEGORY_NODE, STR_EMPTY);

							if (Lang.isGuid(categoryId)) {
								categoryId = STR_EMPTY;
							}
						}

						return categoryId;
					},

					_getIOCategory: function(action) {
						var instance = this;

						var ioCategory = instance._ioCategory[action];

						if (!ioCategory) {
							ioCategory = A.io.request(
								null,
								{
									autoLoad: false,
									dataType: 'json',
									on: {
										success: function(event, id, obj) {
											var response = this.get(STR_RESPONSE_DATA);

											instance._onCategoryAddSuccess(response, action);
										},
										failure: function(event, id, obj) {
											instance._onCategoryAddFailure(obj);
										}
									}
								}
							);

							instance._ioCategory[action] = ioCategory;
						}

						return ioCategory;
					},

					_getIOCategoryDetails: function() {
						var instance = this;

						var ioCategoryDetails = instance._ioCategoryDetails;

						if (!ioCategoryDetails) {
							ioCategoryDetails = A.io.request(
								null,
								{
									autoLoad: false,
									dataType: 'html',
									on: {
										success: function(event, id, obj) {
											var response = this.get(STR_RESPONSE_DATA);

											instance._onCategoryViewSuccess(response);
										},
										failure: function(event, id, obj) {
											instance._onCategoryViewFailure(obj);
										}
									}
								}
							);

							instance._ioCategoryDetails = ioCategoryDetails;
						}

						return ioCategoryDetails;
					},

					_getIOCategoryUpdate: function() {
						var instance = this;

						var ioCategoryUpdate = instance._ioCategoryUpdate;

						if (!ioCategoryUpdate) {
							ioCategoryUpdate = A.io.request(
								null,
								{
									arguments: {},
									autoLoad: false,
									dataType: 'json',
									on: {
										success: function(event, id, obj, args) {
											var response = this.get(STR_RESPONSE_DATA);

											instance._onCategoryMoveSuccess(response, args.success);
										},
										failure: function(event, id, obj) {
											instance._onCategoryMoveFailure(obj);
										}
									}
								}
							);

							instance._ioCategoryUpdate = ioCategoryUpdate;
						}

						return ioCategoryUpdate;
					},

					_getIOVocabulary: function() {
						var instance = this;

						var ioVocabulary = instance._ioVocabulary;

						if (!ioVocabulary) {
							ioVocabulary = A.io.request(
								null,
								{
									autoLoad: false,
									dataType: 'json',
									on: {
										success: function(event, id, obj) {
											var response = this.get(STR_RESPONSE_DATA);

											instance._onVocabularyAddSuccess(response);
										},
										failure: function(event, id, obj) {
											instance._onVocabularyAddFailure(obj);
										}
									}
								}
							);

							instance._ioVocabulary = ioVocabulary;
						}

						return ioVocabulary;
					},

					_getParentCategoryId: function(node) {
						var instance = this;

						var categoryId = STR_EMPTY;

						var parentNode = node.get(STR_PARENT_NODE);

						if (parentNode) {
							categoryId = instance._getCategoryId(parentNode);
						}

						return categoryId;
					},

					_getSelectedCategories: function() {
						var instance = this;

						var selectedCategories = {};

						var categoriesTreeView = instance._categoriesTreeView;

						if (categoriesTreeView) {
							categoriesTreeView.eachChildren(
								function(child) {
									if (child.isChecked()) {
										var categoryId = instance._getCategoryId(child);

										selectedCategories[categoryId] = child.get(STR_LABEL);
									}
								},
								true
							);
						}
						else {
							var categoryIds = instance._categoriesContainer.all('.category-item-check:checked').attr(DATA_CATEGORY_ID);

							selectedCategories = instance._createItemNameMap(categoryIds, A.bind('_getCategory', instance), 'get', STR_TITLE);
						}

						return selectedCategories;
					},

					_getSelectedVocabularies: function() {
						var instance = this;

						var vocabularyIds = A.all('.vocabulary-item-check:checked').attr(DATA_VOCABULARY_ID);

						return instance._createItemNameMap(vocabularyIds, instance._getVocabulary, 'attr', DATA_VOCABULARY);
					},

					_getTreeNodeId: function(treeNode, regex) {
						var instance = this;

						var treeId = treeNode.get(ID);

						var match = treeId.match(regex);

						return match && match[1];
					},

					_getVocabularyById: function(vocabularyId) {
						var instance = this;

						return AArray.find(
							instance._vocabularies,
							function(item, index, collection) {
								return (vocabularyId == item.vocabularyId);
							}
						);
					},

					_getVocabularyCategoriesCount: function(vocabularies, vocabularyId) {
						var instance = this;

						var categoriesCount;

						vocabularyId = toInt(vocabularyId);

						A.some(
							vocabularies,
							function(item, index, collection) {
								if (item.vocabularyId === vocabularyId) {
									categoriesCount = item.categoriesCount;

									return true;
								}
							}
						);

						return categoriesCount;
					},

					_getVocabulariesPagination: function() {
						var instance = this;

						var vocabulariesPagination = instance._vocabulariesPagination;

						if (!vocabulariesPagination) {
							var originalConfig = instance._originalConfig;

							var config = {
								boundingBox: '.vocabularies-pagination',
								circular: false,
								visible: false
							};

							var paginationMap = instance._getVocabulariesPaginationMap();

							AObject.each(
								paginationMap,
								function(item, index, collection) {
									config[index] = Number(HistoryManager.get(item.historyEntry)) || item.defaultValue;
								}
							);

							vocabulariesPagination = new A.Pagination(config).render();

							vocabulariesPagination.after('changeRequest', instance._afterVocabulariesPaginationChangeRequest, instance);

							instance._vocabulariesPagination = vocabulariesPagination;
						}

						return vocabulariesPagination;
					},

					_getVocabulariesPaginationMap: function() {
						var instance = this;

						var paginationMap = instance._paginationMap;

						if (!paginationMap) {
							paginationMap = {
								page: {
									historyEntry: instance._prefixedPortletId + STR_PAGE,
									defaultValue: 1,
									formatter: Number
								},
								rowsPerPage: {
									historyEntry: instance._prefixedPortletId + STR_ROWS_PER_PAGE,
									defaultValue: instance._originalConfig.itemsPerPage,
									formatter: Number
								}
							};

							instance._paginationMap = paginationMap;
						}

						return paginationMap;
					},

					_getVocabularies: function(callback) {
						var instance = this;

						var pagination = instance._getVocabulariesPagination();

						var config = instance._originalConfig;

						var itemsPerPage = config.itemsPerPage;

						var currentPage = 0;

						var query = instance._liveSearch.get(STR_QUERY);

						if (!instance._restartSearch) {
							currentPage = pagination.get(STR_PAGE);

							if (!currentPage) {
								var paginationMap = instance._getVocabulariesPaginationMap();

								currentPage = paginationMap[STR_PAGE].defaultValue;
							}

							currentPage -= 1;
						}

						var start = currentPage * itemsPerPage;
						var end = start + itemsPerPage;

						var parentGroupId = themeDisplay.getParentGroupId();

						Liferay.Service(
							{
								'$display = /assetvocabulary/get-group-vocabularies-display': {
									groupId: parentGroupId,
									name: query,
									start: start,
									end: end,
									addDefaultVocabulary: true,
									obc: null,
									'vocabularies.$categoriesCount = /assetcategory/get-vocabulary-root-categories-count': {
										groupId: parentGroupId,
										'@vocabularyId': '$display.vocabularies.vocabularyId'
									}
								}
							},
							function(result) {
								var total = result.total;

								instance._restartSearch = false;

								pagination.set('total', Math.ceil(total / itemsPerPage));
								pagination.set('visible', (total > itemsPerPage));

								pagination.setState(result);

								if (callback) {
									callback.apply(instance, arguments);
								}
							}
						);
					},

					_getVocabulary: function(vocabularyId) {
						var instance = this;

						return A.one('li[data-vocabularyId="' + vocabularyId + '"]');
					},

					_getVocabularyCategoriesFlat: function(vocabularyId, callback) {
						var instance = this;

						instance._showLoading(instance._categoryContainerSelector);

						var defaultParams = {
							vocabularyId: vocabularyId,
							start: -1,
							end: -1,
							obc: null
						};

						var query = instance._liveSearch.get(STR_QUERY);

						var params = defaultParams;

						if (query && instance._searchType.val() != STR_VOCABULARIES) {
							params = A.mix(
								{
									groupId: themeDisplay.getSiteGroupId(),
									name: Lang.sub(TPL_SEARCH_QUERY, [query])
								},
								defaultParams
							);
						}

						Liferay.Service('/assetcategory/get-json-vocabulary-categories', params, callback);
					},

					_getVocabularyCategoriesTree: function(vocabularyId, callback) {
						var instance = this;

						A.io.request(
							themeDisplay.getPathMain() + '/asset/get_categories',
							{
								data: {
									end: 10,
									p_auth: Liferay.authToken,
									start: 0,
									vocabularyId: vocabularyId
								},
								dataType: 'json',
								on: {
									success: function(event) {
										callback(this.get(STR_RESPONSE_DATA));
									}
								}
							}
						);
					},

					_getVocabularyId: function(exp) {
						var instance = this;

						return A.one(exp).attr(DATA_VOCABULARY_ID);
					},

					_getVocabularyName: function(exp) {
						var instance = this;

						return A.one(exp).attr(DATA_VOCABULARY);
					},

					_hideAllMessages: function() {
						var instance = this;

						instance._container.one('.lfr-message-response').hide();
					},

					_hideFloatingPanels: function(event) {
						var instance = this;

						instance._processAutoFieldsTriggers(event, instance._resetInputLocalized);
					},

					_hideSection: function(node) {
						var instance = this;

						if (node) {
							node.previous().replaceClass(CSS_COLUMN_WIDTH_CATEGORY, CSS_COLUMN_WIDTH_CATEGORY_FULL);
							node.hide();
						}
					},

					_hidePanels: function() {
						var instance = this;

						if (instance._categoryPanelAdd) {
							instance._categoryPanelAdd.hide();
						}

						if (instance._vocabularyPanelAdd) {
							instance._vocabularyPanelAdd.hide();
						}

						if (instance._panelEdit) {
							instance._panelEdit.hide();
						}

						if (instance._panelPermissionsChange) {
							instance._panelPermissionsChange.hide();
						}
					},

					_hideVocabularyMessage: function() {
						A.one(SELECTOR_VOCABULARY_MESSAGES).hide();
					},

					_initializeCategoryPanelAdd: function(action) {
						var instance = this;

						var categoryFormAdd = instance._categoryPanelAdd.get(STR_CONTENT_BOX).one(SELECTOR_UPDATE_CATEGORY_FORM);

						categoryFormAdd.detach(EVENT_SUBMIT);

						categoryFormAdd.on(EVENT_SUBMIT, instance._onCategoryFormSubmit, instance, categoryFormAdd, action);

						var closeButton = categoryFormAdd.one(SELECTOR_BUTTON_CANCEL);

						closeButton.on(EVENT_CLICK, instance._onCategoryAddButtonClose, instance);

						instance._categoryFormAdd = categoryFormAdd;

						instance._loadVocabularySelect(instance._vocabularies, instance._selectedVocabularyId);

						instance._focusCategoryPanelAdd();
					},

					_initializeCategoryPanelEdit: function() {
						var instance = this;

						var categoryFormEdit = instance._panelEdit.get(STR_CONTENT_BOX).one(SELECTOR_UPDATE_CATEGORY_FORM);

						categoryFormEdit.detach(EVENT_SUBMIT);

						categoryFormEdit.on(EVENT_SUBMIT, instance._onCategoryFormSubmit, instance, categoryFormEdit, ACTION_EDIT);

						var closeButton = categoryFormEdit.one(SELECTOR_BUTTON_CANCEL);

						closeButton.on(
							EVENT_CLICK,
							function(event, panel) {
								panel.hide();
							},
							instance,
							instance._panelEdit
						);

						var buttonDeleteCategory = categoryFormEdit.one('#deleteCategoryButton');

						if (buttonDeleteCategory) {
							buttonDeleteCategory.on(EVENT_CLICK, instance._onCategoryDelete, instance);
						}

						var buttonChangeCategoryPermissions = categoryFormEdit.one('#updateCategoryPermissions');

						if (buttonChangeCategoryPermissions) {
							buttonChangeCategoryPermissions.on(EVENT_CLICK, instance._onChangePermissions, instance);
						}

						var inputCategoryNameNode = categoryFormEdit.one(SELECTOR_CATEGORY_NAME_INPUT);

						Util.focusFormField(inputCategoryNameNode);
					},

					_initializeVocabularyPanelAdd: function(callback) {
						var instance = this;

						var vocabularyFormAdd = instance._vocabularyPanelAdd.get(STR_CONTENT_BOX).one(SELECTOR_UPDATE_VOCABULARY_FORM);

						vocabularyFormAdd.detach(EVENT_SUBMIT);

						vocabularyFormAdd.on(EVENT_SUBMIT, instance._onVocabularyFormSubmit, instance, vocabularyFormAdd);

						var closeButton = vocabularyFormAdd.one(SELECTOR_BUTTON_CANCEL);

						closeButton.on(
							EVENT_CLICK,
							function(event, panel) {
								panel.hide();
							},
							instance,
							instance._vocabularyPanelAdd
						);

						instance._vocabularyFormAdd = vocabularyFormAdd;

						if (callback) {
							callback.call(instance);
						}
					},

					_initializeVocabularyPanelEdit: function() {
						var instance = this;

						var vocabularyFormEdit = instance._panelEdit.get(STR_CONTENT_BOX).one(SELECTOR_UPDATE_VOCABULARY_FORM);

						vocabularyFormEdit.detach(EVENT_SUBMIT);

						vocabularyFormEdit.on(EVENT_SUBMIT, instance._onVocabularyFormSubmit, instance, vocabularyFormEdit);

						var closeButton = vocabularyFormEdit.one(SELECTOR_BUTTON_CANCEL);

						closeButton.on(
							EVENT_CLICK,
							function(event, panel) {
								panel.hide();
							},
							instance,
							instance._panelEdit
						);

						var buttonDeleteVocabulary = vocabularyFormEdit.one('#deleteVocabularyButton');

						if (buttonDeleteVocabulary) {
							buttonDeleteVocabulary.on(EVENT_CLICK, instance._onVocabularyDelete, instance);
						}

						var buttonChangeVocabularyPermissions = vocabularyFormEdit.one('#vocabulary-change-permissions');

						if (buttonChangeVocabularyPermissions) {
							buttonChangeVocabularyPermissions.on(EVENT_CLICK, instance._onChangePermissions, instance);
						}

						var inputVocabularyEditNameNode = vocabularyFormEdit.one(SELECTOR_VOCABULARY_NAME_INPUT);

						Util.focusFormField(inputVocabularyEditNameNode);
					},

					_loadData: function() {
						var instance = this;

						instance._closeEditSection();

						instance._checkAllVocabulariesCheckbox.attr(STR_CHECKED, false);

						instance._displayList(
							function() {
								var selectedVocabularyId = instance._selectedVocabularyId;

								if (selectedVocabularyId) {
									instance._displayVocabularyCategories(instance._selectedVocabularyId);
								}
								else {
									instance._categoriesContainer.empty();

									instance._showCateroryMessage();
								}
							}
						);
					},

					_loadPermissions: function(url) {
						var instance = this;

						var panelPermissionsChange = instance._panelPermissionsChange;

						if (!instance._panelPermissionsChange) {
							panelPermissionsChange = instance._createPanelPermissions();
						}

						panelPermissionsChange.show();

						panelPermissionsChange.iframe.set(STR_URI, url);

						panelPermissionsChange._syncUIPosAlign();

						if (instance._panelEdit) {
							var zIndex = toInt(instance._panelEdit.get(STR_ZINDEX)) + 2;

							panelPermissionsChange.set(STR_ZINDEX, zIndex);
						}
					},

					_loadVocabularySelect: function(vocabularies, selectedVocabularyId) {
						var instance = this;

						if (instance._categoryFormAdd) {
							var selectNode = instance._categoryFormAdd.one(SELECTOR_VOCABULARY_SELECT_LIST);

							if (selectNode) {
								selectedVocabularyId = toInt(selectedVocabularyId);

								selectNode.empty();

								var buffer = [];

								var selectedVocabularyIndex;

								A.each(
									vocabularies,
									function(item, index, collection) {
										if (item.vocabularyId === selectedVocabularyId) {
											selectedVocabularyIndex = index;
										}

										buffer.push(
											Lang.sub(
												TPL_VOCABULARY_OPTION,
												{
													selected: item.selected,
													vocabularyId: item.vocabularyId,
													titleCurrentValue: A.Escape.html(item.titleCurrentValue)
												}
											)
										);
									}
								);

								selectNode.append(buffer.join(STR_EMPTY));

								selectNode.set('selectedIndex', selectedVocabularyIndex);
							}
						}
					},

					_merge: function(fromCategoryId, toCategoryId, vocabularyId) {
						var instance = this;

						vocabularyId = vocabularyId || instance._selectedVocabularyId;

						instance._updateCategory(fromCategoryId, toCategoryId, vocabularyId);
					},

					_onCategoryAddFailure: function(response) {
						var instance = this;

						instance._sendMessage(MESSAGE_TYPE_ERROR, Liferay.Language.get('your-request-failed-to-complete'));
					},

					_onCategoryAddSuccess: function(response, action) {
						var instance = this;

						var exception = response.exception;

						if (!exception && response.categoryId) {
							var vocabulary = instance._getVocabularyById(instance._selectedVocabularyId);

							if (action === ACTION_ADD && response.parentCategoryId === 0) {
								vocabulary.categoriesCount++;
							}

							instance._sendMessage(MESSAGE_TYPE_SUCCESS, Liferay.Language.get('your-request-processed-successfully'));

							instance._selectVocabulary(instance._selectedVocabularyId);

							instance._displayVocabularyCategories(
								instance._selectedVocabularyId,
								function() {
									instance._hideSection(instance._categoryViewContainer);
								}
							);

							instance._hidePanels();
						}
						else {
							var errorKey = STR_EMPTY;

							if	(exception.indexOf('AssetCategoryException') > -1) {
								errorKey = Liferay.Language.get('one-of-your-fields-contains-invalid-characters');
							}
							else if (exception.indexOf('CategoryNameException') > -1) {
								errorKey = Liferay.Language.get('please-enter-a-valid-category-name');
							}
							else if ((exception.indexOf('CategoryPropertyKeyException') > -1) ||
									(exception.indexOf('CategoryPropertyValueException') > -1)) {

								errorKey = Liferay.Language.get('one-of-your-property-fields-contains-invalid-characters');
							}
							else if (exception.indexOf('DuplicateCategoryException') > -1) {
								errorKey = Liferay.Language.get('that-category-already-exists');
							}
							else if (exception.indexOf(EXCEPTION_NO_SUCH_VOCABULARY) > -1) {
								errorKey = Liferay.Language.get('that-vocabulary-does-not-exist');
							}
							else if (exception.indexOf(EXCEPTION_PRINCIPAL) > -1) {
								errorKey = Liferay.Language.get('you-do-not-have-permission-to-access-the-requested-resource');
							}
							else {
								errorKey = Liferay.Language.get('your-request-failed-to-complete');
							}

							instance._sendMessage(MESSAGE_TYPE_ERROR, errorKey);
						}
					},

					_onCategoryAddButtonClose: function(event) {
						var instance = this;

						instance._categoryPanelAdd.hide();
					},

					_onCategoryDelete: function(event) {
						var instance = this;

						if (confirm(Liferay.Language.get('are-you-sure-you-want-to-delete-this-category'))) {
							instance._deleteCategory(
								instance._selectedCategoryId,
								A.bind('_processCategoryDeletion', instance, instance._selectedVocabularyId, [instance._selectedCategoryId])
							);
						}
					},

					_onCategoryFormSubmit: function(event, form, action) {
						var instance = this;

						event.halt();

						var vocabularySelectNode = A.one(SELECTOR_VOCABULARY_SELECT_LIST);

						var vocabularyId = (vocabularySelectNode && vocabularySelectNode.val()) || instance._selectedVocabularyId;

						if (vocabularyId) {
							var vocabularyElId = '#' + instance._prefixedPortletId + STR_VOCABULARY_ID;

							form.one(vocabularyElId).val(vocabularyId);

							var parentCategoryElId = '#' + instance._prefixedPortletId + 'parentCategoryId';

							var parentCategoryId = instance._selectedParentCategoryId;

							if (action == ACTION_ADD) {
								parentCategoryId = 0;
							}
							else if (action == ACTION_ADD_SUBCATEGORY) {
								parentCategoryId = instance._selectedCategoryId;
							}

							form.one(parentCategoryElId).val(parentCategoryId);

							Liferay.fire(
								EVENT_SAVE_AUTO_FIELDS,
								{
									form: form
								}
							);

							instance._addCategory(form, action);
						}
					},

					_onCategoryMoveFailure: function(event) {
						var instance = this;

						instance._sendMessage(MESSAGE_TYPE_ERROR, Liferay.Language.get('your-request-failed-to-complete'));
					},

					_onCategoryMoveSuccess: function(response, vocabularyId) {
						var instance = this;

						var exception = response.exception;

						if (!exception) {
							instance._closeEditSection();
							instance._sendMessage(MESSAGE_TYPE_SUCCESS, Liferay.Language.get('your-request-processed-successfully'));

							instance._selectVocabulary(vocabularyId);
						}
						else {
							var errorKey;

							if (exception.indexOf('AssetCategoryNameException') > -1) {
								errorKey = Liferay.Language.get('please-enter-a-valid-category-name');
							}
							else if (exception.indexOf('DuplicateCategoryException') > -1) {
								errorKey = Liferay.Language.get('there-is-another-category-with-the-same-name-and-the-same-parent');
							}
							else if (exception.indexOf(EXCEPTION_NO_SUCH_VOCABULARY) > -1) {
								errorKey = Liferay.Language.get('that-vocabulary-does-not-exist');
							}
							else if (exception.indexOf('NoSuchCategoryException') > -1) {
								errorKey = Liferay.Language.get('that-parent-category-does-not-exist');
							}
							else if (exception.indexOf(EXCEPTION_PRINCIPAL) > -1) {
								errorKey = Liferay.Language.get('you-do-not-have-permission-to-access-the-requested-resource');
							}
							else if (exception.indexOf('Exception') > -1) {
								errorKey = Liferay.Language.get('one-of-your-fields-contains-invalid-characters');
							}
							else {
								errorKey = Liferay.Language.get('your-request-failed-to-complete');
							}

							instance._sendMessage(MESSAGE_TYPE_ERROR, errorKey);
						}
					},

					_onCategorySearchClick: function(event) {
						var instance = this;

						var categoryItem = event.target.ancestor(SELECTOR_CATEGORY_ITEM);

						instance._unselectAllCategories();

						categoryItem.addClass(STR_SELECTED);

						Util.checkAllBox(event.container, CSS_CATEGORY_ITEM_CHECK, instance._checkAllCategoriesCheckbox);

						instance._toggleAllVocabularies(false);

						var categoryId = instance._getCategoryId(categoryItem);

						if (categoryId) {
							instance._showCategoryViewContainer(categoryId);
						}
					},

					_onCategoryViewContainerClick: function(event) {
						var instance = this;

						var targetId = event.target.get(ID);

						if (targetId == 'editCategoryButton') {
							event.halt();

							instance._hidePanels();
							instance._showCategoryPanel(ACTION_EDIT);
						}
						else if (targetId == 'deleteCategoryButton') {
							event.halt();

							instance._onCategoryDelete();
						}
						else if (targetId == 'updateCategoryPermissions') {
							event.halt();

							instance._onChangePermissions(event);
						}
						else if (targetId == 'addSubCategoryButton') {
							event.halt();

							instance._hidePanels();
							instance._showCategoryPanel(ACTION_ADD_SUBCATEGORY);
						}
					},

					_onCategoryViewFailure: function(response) {
						var instance = this;

						instance._sendMessage(MESSAGE_TYPE_ERROR, Liferay.Language.get('your-request-failed-to-complete'));
					},

					_onCategoryViewSuccess: function(response) {
						var instance = this;

						instance._categoryViewContainer.html(response);
					},

					_onChangePermissions: function(event) {
						var instance = this;

						var url = event.target.attr(DATA_CATEGORY_URL) || event.currentTarget.attr(DATA_CATEGORY_URL);

						instance._loadPermissions(url);
					},

					_onDragDrop: function(event) {
						var instance = this;

						var dragNode = event.drag.get(STR_NODE);
						var dropNode = event.drop.get(STR_NODE);

						var node = dragNode.getData('tree-node');

						var vocabularyId = dropNode.attr('data-vocabularyid');
						var fromCategoryId = instance._getCategoryId(node);

						instance._merge(fromCategoryId, 0, vocabularyId);

						dropNode.removeClass(CSS_ACTIVE_AREA);
					},

					_onSearchInputKeyDown: function(event) {
						if (event.isKey('ENTER')) {
							event.halt();
						}
					},

					_onSearchTypeChange: function(event) {
						var instance = this;

						var searchInput = instance._searchInput;

						if (searchInput.val()) {
							instance._processSearch();
						}
						else {
							searchInput.focus();
						}
					},

					_onShowCategoryPanel: function(event, action) {
						var instance = this;

						instance._hidePanels();

						instance._showCategoryPanel(action);
					},

					_onShowVocabularyPanel: function(event, action) {
						var instance = this;

						instance._hidePanels();

						instance._showVocabularyPanel(action);
					},

					_onStateChange: function(event) {
						var instance = this;

						var changed = event.changed;
						var removed = event.removed;

						var paginationState = {};

						var paginationMap = instance._getVocabulariesPaginationMap();

						AObject.each(
							paginationMap,
							function(item, index, collection) {
								var historyEntry = item.historyEntry;

								var value;

								if (owns(changed, historyEntry)) {
									value = item.formatter(changed[historyEntry].newVal);
								}
								else if (owns(removed, historyEntry)) {
									value = item.defaultValue;
								}

								if (value) {
									paginationState[index] = value;
								}
							}
						);

						if (!AObject.isEmpty(paginationState)) {
							instance._vocabulariesPagination.setState(paginationState);

							instance._loadData();
						}
					},

					_onVocabularyAddFailure: function(response) {
						var instance = this;

						instance._sendMessage(MESSAGE_TYPE_ERROR, Liferay.Language.get('your-request-failed-to-complete'));
					},

					_onVocabularyAddSuccess: function(response) {
						var instance = this;

						instance._hideAllMessages();

						var exception = response.exception;

						if (!response.exception) {
							instance._sendMessage(MESSAGE_TYPE_SUCCESS, Liferay.Language.get('your-request-processed-successfully'));

							instance._displayList(
								function() {
									var vocabulary = instance._selectVocabulary(response.vocabularyId);

									instance._displayVocabularyCategories(instance._selectedVocabularyId);

									if (vocabulary) {
										var scrollTop = vocabulary.get('region').top;

										A.one(instance._vocabularyListSelector).set('scrollTop', scrollTop);
									}
								}
							);

							instance._hidePanels();
						}
						else {
							var errorKey = STR_EMPTY;

							if (exception.indexOf('DuplicateVocabularyException') > -1) {
								errorKey = Liferay.Language.get('that-vocabulary-already-exists');
							}
							else if (exception.indexOf(EXCEPTION_VOCABULARY_NAME) > -1) {
								errorKey = Liferay.Language.get('one-of-your-fields-contains-invalid-characters');
							}
							else if (exception.indexOf(EXCEPTION_NO_SUCH_VOCABULARY) > -1) {
								errorKey = Liferay.Language.get('that-parent-vocabulary-does-not-exist');
							}
							else if (exception.indexOf(EXCEPTION_PRINCIPAL) > -1) {
								errorKey = Liferay.Language.get('you-do-not-have-permission-to-access-the-requested-resource');
							}
							else {
								errorKey = Liferay.Language.get('your-request-failed-to-complete');
							}

							instance._sendMessage(MESSAGE_TYPE_ERROR, errorKey);
						}
					},

					_onVocabularyDelete: function() {
						var instance = this;

						if (confirm(Liferay.Language.get('are-you-sure-you-want-to-delete-this-vocabulary'))) {
							instance._deleteVocabulary(instance._selectedVocabularyId, instance._processVocabularyDeletion);
						}
					},

					_onVocabularyFormSubmit: function(event, form) {
						var instance = this;

						event.halt();

						Liferay.fire(
							EVENT_SAVE_AUTO_FIELDS,
							{
								form: form
							}
						);

						instance._addVocabulary(form);
					},

					_onVocabularyListClick: function(event) {
						var instance = this;

						instance._onVocabularyListSelect(event);

						var target = event.target;

						if (target.hasClass(CSS_VOCABULARY_ITEM_CHECK)) {
							Util.checkAllBox(event.currentTarget, CSS_VOCABULARY_ITEM_CHECK, instance._checkAllVocabulariesCheckbox);

							instance._toggleAllCategories(false);
						}
						else if (event.target.hasClass('vocabulary-item-actions-trigger')) {
							instance._showVocabularyPanel(ACTION_EDIT);
						}
					},

					_onVocabularyListSelect: function(event) {
						var instance = this;

						var vocabularyId = instance._getVocabularyId(event.target);

						instance._selectVocabulary(vocabularyId);
					},

					_processAutoFieldsTriggers: function(event, callback) {
						var instance = this;

						var contextPanel = event.currentTarget;

						var boundingBox = contextPanel.get(STR_BOUNDING_BOX);

						var autoFieldsTriggers = boundingBox.all(SELECTOR_FLOATING_TRIGGER);

						autoFieldsTriggers.each(
							function(item, index, collection) {
								var autoFieldsInstance = item.getData(STR_AUTO_FIELDS_INSTANCE);

								callback.call(instance, autoFieldsInstance);
							}
						);
					},

					_processCategoryDeletion: function() {
						var instance = this;

						var categoryIds = arguments[1];
						var vocabularyId = arguments[0];

						var exception;
						var result;

						var argsLength = arguments.length;

						if (argsLength > 2) {
							result = arguments[2];

							if (argsLength > 3) {
								exception = arguments[2];
								result = arguments[3];
							}
						}

						var vocabulary = instance._getVocabularyById(vocabularyId);

						if (!exception) {
							var errorVocabularies = {};

							AObject.each(
								result,
								function(item, index, collection) {
									errorVocabularies[toInt(item.categoryId)] = true;
								}
							);

							var deletedRootCategories = AArray.filter(
								categoryIds,
								function(item, index, collection) {
									var categoryId = item;

									var rootVocabulary = (instance._vocabularyRootCategories[categoryId] === 1);

									var deletedVocabulary = !errorVocabularies[categoryId];

									var deletedRootCategory = (rootVocabulary && deletedVocabulary);

									if (deletedRootCategory) {
										instance._vocabularyRootCategories[categoryId] = null;
									}

									return deletedRootCategory;
								}
							);

							vocabulary.categoriesCount -= deletedRootCategories.length;

							instance._closeEditSection();
							instance._hidePanels();
							instance._displayVocabularyCategories(instance._selectedVocabularyId);

							if (Lang.isArray(result) && result.length > 0) {
								var alertMessage = instance._createAlertMessage(result, instance._selectedCategories, STR_CATEGORY_ID);

								instance._sendMessage(MESSAGE_TYPE_ALERT, alertMessage);
							}
							else {
								instance._sendMessage(MESSAGE_TYPE_SUCCESS, Liferay.Language.get('your-request-processed-successfully'));
							}
						}
						else {
							var errorMessage = Liferay.Language.get('your-request-failed-to-complete');

							if (exception.indexOf(EXCEPTION_PRINCIPAL) > -1) {
								errorMessage = Liferay.Language.get('you-do-not-have-permission-to-access-the-requested-resource');
							}

							instance._sendMessage(MESSAGE_TYPE_ERROR, errorMessage);
						}
					},

					_processSearch: function() {
						var instance = this;

						instance._restartSearch = true;

						if (!instance._liveSearch.get(STR_QUERY) || instance._searchType.val() == STR_VOCABULARIES) {
							instance._selectedVocabularyId = null;

							instance._loadData();
						}
						else if (instance._selectedVocabularyId) {
							instance._closeEditSection();

							instance._displayVocabularyCategories(instance._selectedVocabularyId, null, MODE_RENDER_FLAT);
						}
					},

					_processVocabularyDeletion: function() {
						var instance = this;

						var exception;

						var result = arguments[0];

						if (arguments.length > 1) {
							exception = arguments[0];
							result = arguments[1];
						}

						if (!exception) {
							instance._closeEditSection();
							instance._hidePanels();
							instance._loadData();

							if (Lang.isArray(result) && result.length > 0) {
								var alertMessage = instance._createAlertMessage(result, instance._selectedVocabularies, STR_VOCABULARY_ID);

								instance._sendMessage(MESSAGE_TYPE_ALERT, alertMessage);
							}
							else {
								instance._sendMessage(MESSAGE_TYPE_SUCCESS, Liferay.Language.get('your-request-processed-successfully'));
							}
						}
						else {
							var errorKey;

							if (exception.indexOf(EXCEPTION_PRINCIPAL) > -1) {
								errorKey = Liferay.Language.get('you-do-not-have-permission-to-access-the-requested-resource');
							}
							else {
								errorKey = Liferay.Language.get('your-request-failed-to-complete');
							}

							instance._sendMessage(MESSAGE_TYPE_ERROR, errorKey);
						}
					},

					_resetCategoriesProperties: function(event) {
						var instance = this;

						var contextPanel = event.currentTarget;
						var boundingBox = contextPanel.get(STR_BOUNDING_BOX);

						var namespace = instance._prefixedPortletId;

						var propertiesTrigger = boundingBox.one('fieldset#' + namespace + 'categoryProperties');

						var autoFieldsInstance = propertiesTrigger.getData(STR_AUTO_FIELDS_INSTANCE);

						autoFieldsInstance.reset();
					},

					_resetInputLocalized: function(autoFieldsInstance) {
						var instance = this;

						if (autoFieldsInstance) {
							autoFieldsInstance.reset();
						}
					},

					_selectCategory: function(categoryId) {
						var instance = this;

						var category = instance._getCategory(categoryId);
						var parentCategoryId = instance._getParentCategoryId(category);

						instance._selectedCategoryId = categoryId;
						instance._selectedParentCategoryId = parentCategoryId || 0;

						instance._selectedCategory = category;

						return category;
					},

					_selectCurrentVocabulary: function(value) {
						var instance = this;

						var option = A.one('select.vocabulary-select-list option[value="' + value + '"]');

						if (option) {
							option.set(STR_SELECTED, true);
						}
					},

					_selectVocabulary: function(vocabularyId) {
						var instance = this;

						var vocabulary = instance._getVocabulary(vocabularyId);

						if (vocabulary) {
							var vocabularyName = instance._getVocabularyName(vocabulary);

							if (vocabulary.hasClass(STR_SELECTED)) {
								return vocabulary;
							}

							instance._hideAllMessages();
							instance._selectedVocabularyName = vocabularyName;
							instance._selectedVocabularyId = vocabularyId;
							instance._selectCurrentVocabulary(vocabularyId);

							instance._unselectAllVocabularies();
							instance._closeEditSection();

							vocabulary.addClass(STR_SELECTED);

							instance._displayVocabularyCategories(instance._selectedVocabularyId);
						}

						return vocabulary;
					},

					_sendMessage: function(type, message) {
						var instance = this;

						var output = instance._portletMessageContainer;
						var typeClass = 'alert alert-' + type;

						output.removeClass(CSS_MESSAGE_ERROR).removeClass(CSS_MESSAGE_SUCCESS);
						output.addClass(typeClass);
						output.html(message);

						output.show();

						instance._hideMessageTask();
					},

					_showCateroryMessage: function(message, className) {
						var categoryMessages = A.one(SELECTOR_VOCABULARY_CATEGORY_MESSAGES);

						className = className || 'alert alert-info';

						message = message || Liferay.Language.get('there-are-no-categories');

						categoryMessages.set('className', CSS_MESSAGE_RESPONSE + STR_SPACE + className);

						categoryMessages.html(message);

						categoryMessages.show();
					},

					_showCategoryPanel: function(action) {
						var instance = this;

						if (action == ACTION_ADD || action == ACTION_ADD_SUBCATEGORY) {
							instance._showCategoryPanelAdd(action);
						}
						else if (action == ACTION_EDIT) {
							instance._showCategoryPanelEdit();
						}
						else {
							throw 'Internal error. No default action specified.';
						}
					},

					_showCategoryPanelAdd: function(action) {
						var instance = this;

						var categoryPanelAdd = instance._categoryPanelAdd;

						var categoryURL = instance._createURL(CATEGORY, action, LIFECYCLE_RENDER);

						if (!categoryPanelAdd) {
							categoryPanelAdd = instance._createCategoryPanelAdd();

							categoryPanelAdd.plug(
								A.Plugin.IO,
								{
									autoLoad: false,
									uri: categoryURL.toString()
								}
							);
						}
						else if (instance._currentCategoryPanelAddIOHandle) {
							instance._currentCategoryPanelAddIOHandle.detach();

							categoryPanelAdd.io.set(STR_URI, categoryURL.toString());
						}

						categoryPanelAdd.show();

						categoryPanelAdd._syncUIPosAlign();

						instance._currentCategoryPanelAddIOHandle = categoryPanelAdd.io.after(
							STR_SUCCESS,
							A.bind('_initializeCategoryPanelAdd', instance, action)
						);

						categoryPanelAdd.io.start();
					},

					_showCategoryPanelEdit: function() {
						var instance = this;

						var forceStart = false;
						var categoryPanelEdit = instance._panelEdit;

						if (!categoryPanelEdit) {
							categoryPanelEdit = instance._createPanelEdit();
						}
						else {
							forceStart = true;

							instance._currentPanelEditIOHandle.detach();
						}

						categoryPanelEdit.set(STR_TITLE, Liferay.Language.get('edit-category'));

						var categoryEditURL = instance._createURL(CATEGORY, ACTION_EDIT, LIFECYCLE_RENDER);

						categoryPanelEdit.show();

						categoryPanelEdit._syncUIPosAlign();

						categoryPanelEdit.plug(
							A.Plugin.IO,
							{
								uri: categoryEditURL.toString(),
								after: {
									success: instance._currentPanelEditInitListener
								}
							}
						);

						instance._currentPanelEditIOHandle = categoryPanelEdit.io.after(STR_SUCCESS, instance._initializeCategoryPanelEdit, instance);

						if (forceStart) {
							categoryPanelEdit.io.start();
						}
					},

					_showCategoryViewContainer: function(categoryId) {
						var instance = this;

						var viewContainer = instance._categoryViewContainer;

						instance._selectCategory(categoryId);

						instance._showLoading(viewContainer);
						instance._showSection(instance._categoryViewSection);

						var categoryURL = instance._createURL(CATEGORY, ACTION_VIEW, LIFECYCLE_RENDER);

						var ioCategoryDetails = instance._getIOCategoryDetails();

						ioCategoryDetails.set(STR_URI, categoryURL.toString()).start();
					},

					_showLoading: function(container) {
						var instance = this;

						A.all(container).html('<div class="loading-animation" />');
					},

					_showSection: function(node) {
						var instance = this;

						node.previous().replaceClass(CSS_COLUMN_WIDTH_CATEGORY_FULL, CSS_COLUMN_WIDTH_CATEGORY);

						node.show();

						var firstInput = node.one('input');

						if (firstInput) {
							firstInput.focus();
						}
					},

					_showVocabularyMessage: function(message, className) {
						var vocabularyMessages = A.one(SELECTOR_VOCABULARY_MESSAGES);

						className = className || 'alert alert-info';

						message = message || Liferay.Language.get('there-are-no-vocabularies');

						vocabularyMessages.set('className', CSS_MESSAGE_RESPONSE + STR_SPACE + className);

						vocabularyMessages.html(message);

						vocabularyMessages.show();
					},

					_showVocabularyPanel: function(action) {
						var instance = this;

						if (action == ACTION_ADD) {
							instance._showVocabularyPanelAdd();
						}
						else if (action == ACTION_EDIT) {
							instance._showVocabularyPanelEdit();
						}
						else {
							throw 'Internal error. No default action specified.';
						}
					},

					_showVocabularyPanelAdd: function() {
						var instance = this;

						var vocabularyPanelAdd = instance._vocabularyPanelAdd;

						if (!vocabularyPanelAdd) {
							vocabularyPanelAdd = instance._createVocabularyPanelAdd();

							var vocabularyURL = instance._createURL(TYPE_VOCABULARY, ACTION_ADD, LIFECYCLE_RENDER);

							vocabularyPanelAdd.show();

							vocabularyPanelAdd._syncUIPosAlign();

							var afterSuccess = A.bind(
								'_initializeVocabularyPanelAdd',
								instance,
								function() {
									instance._focusVocabularyPanelAdd();
								}
							);

							vocabularyPanelAdd.plug(
								A.Plugin.IO,
								{
									uri: vocabularyURL.toString(),
									after: {
										success: afterSuccess
									}
								}
							);
						}
						else {
							vocabularyPanelAdd.show();

							vocabularyPanelAdd._syncUIPosAlign();

							instance._focusVocabularyPanelAdd();
						}
					},

					_showVocabularyPanelEdit: function() {
						var instance = this;

						var forceStart = false;
						var vocabularyPanelEdit = instance._panelEdit;

						if (!vocabularyPanelEdit) {
							vocabularyPanelEdit = instance._createPanelEdit();
						}
						else {
							forceStart = true;

							instance._currentPanelEditIOHandle.detach();
						}

						vocabularyPanelEdit.set(STR_TITLE, Liferay.Language.get('edit-vocabulary'));

						var vocabularyEditURL = instance._createURL(TYPE_VOCABULARY, ACTION_EDIT, LIFECYCLE_RENDER);

						vocabularyPanelEdit.show();

						vocabularyPanelEdit._syncUIPosAlign();

						vocabularyPanelEdit.plug(
							A.Plugin.IO,
							{
								uri: vocabularyEditURL.toString()
							}
						);

						instance._currentPanelEditIOHandle = vocabularyPanelEdit.io.after(STR_SUCCESS, instance._initializeVocabularyPanelEdit, instance);

						if (forceStart) {
							vocabularyPanelEdit.io.start();
						}
					},

					_toggleAllCategoriesFn: function(state) {
						var instance = this;

						var categoriesTreeView = instance._categoriesTreeView;

						instance._checkAllCategoriesCheckbox.attr(STR_CHECKED, state);

						if (categoriesTreeView) {
							categoriesTreeView.eachChildren(
								function(child) {
									if (state) {
										child.check();
									}
									else {
										child.uncheck();
									}
								},
								true
							);
						}
						else {
							instance._categoriesContainer.all('.category-item-check').attr(STR_CHECKED, state);
						}
					},

					_toggleAllVocabulariesFn: function(state) {
						var instance = this;

						if (state === true) {
							instance._toggleAllCategories(false);
						}

						instance._checkAllVocabulariesCheckbox.attr(STR_CHECKED, state);

						A.all('.vocabulary-item-check').attr(STR_CHECKED, state);
					},

					_unselectAllCategories: function() {
						var instance = this;

						A.all(instance._categoryItemSelectorFlat).removeClass(STR_SELECTED);
					},

					_unselectAllVocabularies: function() {
						var instance = this;

						A.all(instance._vocabularyItemSelector).removeClass(STR_SELECTED);
					},

					_updateCategory: function(categoryId, parentCategoryId, vocabularyId) {
						var instance = this;

						var moveURL = instance._createURL(CATEGORY, ACTION_MOVE, LIFECYCLE_PROCESS);

						var prefix = instance._prefixedPortletId;

						var data = prefix + 'categoryId=' + categoryId + '&' +
									prefix + 'parentCategoryId=' + parentCategoryId + '&' +
									prefix + 'vocabularyId=' + vocabularyId;

						var ioCategoryUpdate = instance._getIOCategoryUpdate();

						ioCategoryUpdate.set('data', data);
						ioCategoryUpdate.set(STR_URI, moveURL.toString());

						ioCategoryUpdate.set('arguments.success', vocabularyId);

						ioCategoryUpdate.start();
					},

					_categoryItemSelectorFlat: '.category-item',
					_categoryContainerSelector: '.vocabulary-categories',
					_selectedCategories: null,
					_selectedVocabularies: null,
					_selectedVocabulary: null,
					_selectedVocabularyId: null,
					_selectedVocabularyName: null,
					_vocabularies: null,
					_vocabularyItemSelector: '.vocabulary-list li',
					_vocabularyListSelector: '.vocabulary-list'
				}
			}
		);

		var CategoriesTree = A.Component.create(
			{
				NAME: 'CategoriesTree',

				EXTENDS: A.TreeViewDD,

				prototype: {
					reinsertChild: function(dragNode, dropNode) {
						var instance = this;

						var categoryName = dragNode.get(STR_LABEL);

						dropNode.removeChild(dragNode);

						var children = dropNode.get('children');

						if (children.length) {
							var result;

							var method = 'insertBefore';

							AArray.some(
								children,
								function(item, index, collection) {
									if (item.get(STR_LABEL) > categoryName) {
										result = item;
									}
									else {
										var nextItem = collection[index + 1];

										if (!nextItem) {
											result = item;

											method = 'insertAfter';
										}
										else if (nextItem.get(STR_LABEL) > categoryName) {
											result = nextItem;
										}
									}

									return result;
								}
							);

							if (result) {
								result[method](dragNode);
							}
						}
						else {
							dropNode.append(dragNode);
						}
					},

					_findCategoryByName: function(event) {
						var instance = this;

						var result = false;

						var dragNode = event.drag.get(STR_NODE).get(STR_PARENT_NODE);

						var dragTreeNode = dragNode.getData(STR_TREE_NODE);

						if (dragTreeNode) {
							var categoryName = dragTreeNode.get(STR_LABEL);

							var dropAction = instance.dropAction;

							var dropNode = event.drop.get(STR_NODE).get(STR_PARENT_NODE);

							if (dropAction !== 'append') {
								dropNode = dropNode.get('parentNode.parentNode');
							}

							var dropTreeNode = dropNode.getData(STR_TREE_NODE);

							if (!A.instanceOf(dropTreeNode, A.TreeNode)) {
								dropTreeNode = dropNode.getData(STR_TREE_VIEW);
							}

							if (dropTreeNode) {
								var children = dropTreeNode.get('children');

								result = A.some(
									children,
									function(item, index, collection) {
										return (item.get(STR_LABEL) === categoryName);
									}
								);
							}
						}

						return result;
					},

					_onDropHit: function(event) {
						var instance = this;

						if (instance._findCategoryByName(event)) {
							var dragNode = event.drag.get(STR_NODE).get(STR_PARENT_NODE);
							var dropNode = event.drop.get(STR_NODE).get(STR_PARENT_NODE);

							var dropTreeNode = dropNode.getData(STR_TREE_NODE);
							var dragTreeNode = dragNode.getData(STR_TREE_NODE);

							var output = instance.getEventOutputMap(instance);

							output.tree.dropNode = dropTreeNode;
							output.tree.dragNode = dragTreeNode;

							instance.bubbleEvent('dropFailed', output);

							event.halt();

							instance._resetState(instance.nodeContent);
						}
						else {
							CategoriesTree.superclass._onDropHit.apply(instance, arguments);
						}
					},

					_updateNodeState: function(event) {
						var instance = this;

						var dropNode = event.drop.get(STR_NODE);

						if (dropNode && dropNode.hasClass('vocabulary-category')) {
							instance._appendState(dropNode);
						}
						else {
							CategoriesTree.superclass._updateNodeState.apply(instance, arguments);
						}
					}
				}
			}
		);

		var LiveSearch = A.Component.create(
			{
				AUGMENTS: [A.AutoCompleteBase],
				EXTENDS: A.Base,
				NAME: 'livesearch',
				prototype: {
					initializer: function() {
						this._bindUIACBase();
						this._syncUIACBase();
					}
				}
			}
		);

		Liferay.Portlet.AssetCategoryAdmin = AssetCategoryAdmin;
	},
	'',
	{
		requires: ['aui-dialog-iframe-deprecated', 'aui-io-plugin-deprecated', 'aui-live-search-deprecated', 'aui-modal', 'aui-pagination', 'autocomplete-base', 'aui-tree-view', 'dd', 'escape', 'json', 'liferay-history-manager', 'liferay-portlet-url', 'liferay-util-window']
	}
);