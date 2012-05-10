AUI.add(
	'liferay-category-admin',
	function(A) {
		var AObject = A.Object;
		var HistoryManager = Liferay.HistoryManager;
		var Lang = A.Lang;
		var Node = A.Node;
		var Widget = A.Widget;

		var owns = AObject.owns;

		var ACTION_ADD = 0;

		var ACTION_ADD_SUBCATEGORY = 4;

		var ACTION_EDIT = 1;

		var ACTION_MOVE = 2;

		var ACTION_VIEW = 3;

		var CATEGORY = 0;

		var CSS_ACTIVE_AREA = 'active-area';

		var CSS_ADMIN_DIALOG = 'portlet-asset-categories-admin-dialog';

		var CSS_COLUMN_WIDTH_CATEGORY = 'aui-w40';

		var CSS_COLUMN_WIDTH_CATEGORY_FULL = 'aui-w75';

		var CSS_MESSAGE_ERROR = 'portlet-msg-error';

		var CSS_MESSAGE_SUCCESS = 'portlet-msg-success';

		var CSS_VOCABULARY_ITEM_CHECK = 'vocabulary-item-check';

		var DATA_VOCABULARY_ID = 'data-vocabularyId';

		var DEFAULT_DEBOUNCE_TIMEOUT = 50;

		var EVENT_CLICK = 'click';

		var EVENT_SAVE_AUTO_FIELDS = 'saveAutoFields';

		var EVENT_SUBMIT = 'submit';

		var EVENT_VISIBLE_CHANGE = 'visibleChange';

		var EXCEPTION_NO_SUCH_VOCABULARY = 'NoSuchVocabularyException';

		var EXCEPTION_PRINCIPAL = 'auth.PrincipalException';

		var EXCEPTION_VOCABULARY_NAME = 'VocabularyNameException';

		var INVALID_VALUE = A.Attribute.INVALID_VALUE;

		var LIFECYCLE_RENDER = 0;

		var LIFECYCLE_PROCESS = 1;

		var MESSAGE_TYPE_ERROR = 'error';

		var MESSAGE_TYPE_SUCCESS = 'success';

		var SELECTOR_BUTTON_CANCEL = '.aui-button-input-cancel';

		var SELECTOR_CATEGORY_NAME_INPUT = '.category-name input';

		var SELECTOR_CSS_COLUMN = '.aui-column';

		var SELECTOR_FLOATING_TRIGGER = '.lfr-floating-trigger';

		var SELECTOR_UPDATE_CATEGORY_FORM = 'form.update-category-form';

		var SELECTOR_UPDATE_VOCABULARY_FORM = 'form.update-vocabulary-form';

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

		var STR_LABEL = 'label';

		var STR_NODE = 'node';

		var STR_PAGE = 'page';

		var STR_PANEL_INSTANCE = 'panelInstance';

		var STR_PARENT_NODE = 'parentNode';
		
		var STR_PARENT_CATEGORY_ID = 'parentCategoryId';

		var STR_QUERY = 'query';

		var STR_SELECTED = 'selected';

		var STR_SUCCESS = 'success';

		var STR_TITLE = 'title';

		var STR_VOCABULARY_ID = 'vocabularyId';

		var STR_VOCABULARIES = 'vocabularies';

		var STR_ZINDEX = 'zIndex';

		var STR_RESPONSE_DATA = 'responseData';

		var STR_ROWS_PER_PAGE = 'rowsPerPage';

		var TPL_MESSAGES_CATEGORY = '<div class="aui-helper-hidden lfr-message-response" id="vocabulary-category-messages" />';

		var TPL_MESSAGES_VOCABULARY = '<div class="aui-helper-hidden lfr-message-response" id="vocabulary-messages" />';

		var TPL_SEARCH_QUERY = '%{0}%';

		var TPL_VOCABULARY_LIST = '<li class="vocabulary-category results-row {cssClassSelected}" data-vocabulary="{titleCurrentValue}" data-vocabularyId="{vocabularyId}" tabIndex="0">' +
			'<div class="vocabulary-content-wrapper">' +
				'<input type="checkbox" class="vocabulary-item-check aui-field-input-choice" name="vocabulary-item-check" data-vocabularyId="{vocabularyId}" data-vocabularyName="{titleCurrentValue}">' +
				'<span class="vocabulary-item">' +
					'<a href="javascript:;" data-vocabularyId="{vocabularyId}" tabIndex="-1">{titleCurrentValue}</a>' +
				'</span>' +
				'<a href="javascript:;" class="vocabulary-item-actions-trigger" data-vocabularyId="{vocabularyId}"></a>' +
			'</div>' +
		'</li>';

		var TPL_CATEGORIES_TREE_CONTAINER = '<div class="categories-treeview-container" id="categoriesTreeContainer"></div>';

		var TYPE_VOCABULARY = 1;

		var AssetCategoryAdmin = A.Component.create(
			{
				EXTENDS: A.Base,

				NAME: 'assetcategoryadmin',

				prototype: {
					initializer: function(config) {
						var instance = this;

						instance._originalConfig = config;

						var childrenContainer = A.one(instance._categoryContainerSelector);

						instance.portletId = config.portletId;

						instance._prefixedPortletId = '_' + config.portletId + '_';

						instance._container = A.one('.categories-admin-container');
						instance._categoryViewContainer = A.one('.category-view');

						instance._portletMessageContainer = Node.create(TPL_MESSAGES_VOCABULARY);
						instance._categoryMessageContainer = Node.create(TPL_MESSAGES_CATEGORY);

						instance._container.placeBefore(instance._portletMessageContainer);
						childrenContainer.placeBefore(instance._categoryMessageContainer);

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

						var vocabularyList = A.one(instance._vocabularyContainerSelector);

						vocabularyList.on(EVENT_CLICK, instance._onVocabularyListClick, instance);

						vocabularyList.on('key', instance._onVocabularyListSelect, 'up:13', instance);

						var addCategoryButton = A.one(idPrefix + 'addCategoryButton');

						addCategoryButton.on(EVENT_CLICK, instance._onShowCategoryPanel, instance, ACTION_ADD);

						instance._addCategoryButton = addCategoryButton;

						instance._addCategoryButtonWrapper = addCategoryButton.ancestor('.aui-button');

						A.one(idPrefix + 'addVocabularyButton').on(EVENT_CLICK, instance._onShowVocabularyPanel, instance, ACTION_ADD);
						A.one(idPrefix + 'categoryPermissionsButton').on(EVENT_CLICK, instance._onChangePermissions, instance);
						A.one(idPrefix + 'deleteSelectedItems').on(EVENT_CLICK, instance._deleteSelected, instance);

						var checkAllVocabulariesCheckbox = A.one(idPrefix + 'checkAllVocabulariesCheckbox');

						checkAllVocabulariesCheckbox.on(EVENT_CLICK, instance._checkAllVocabularies, instance);

						instance._checkAllVocabulariesCheckbox = checkAllVocabulariesCheckbox;

						var checkAllCategoriesCheckbox = A.one(idPrefix + 'checkAllCategoriesCheckbox');

						checkAllCategoriesCheckbox.on(EVENT_CLICK, instance._checkAllCategories, instance);

						instance._checkAllCategoriesCheckbox = checkAllCategoriesCheckbox;

						instance._createLiveSearch();

						HistoryManager.on('stateChange', instance._onStateChange, instance);

						instance._loadData();

						instance.after('drop:enter', instance._afterDragEnter);
						instance.after('drop:exit', instance._afterDragExit);

						instance.on('drop:hit', instance._onDragDrop);
					},

					_addCategory: function(form) {
						var instance = this;

						var ioCategory = instance._getIOCategory();

						ioCategory.set('form', form.getDOM());
						ioCategory.set('uri', form.attr(STR_ACTION));

						ioCategory.start();
					},

					_addVocabulary: function(form) {
						var instance = this;

						var ioVocabulary = instance._getIOVocabulary();

						ioVocabulary.set('form', form.getDOM());
						ioVocabulary.set('uri', form.attr(STR_ACTION));

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

					_alignFloatingPanels: function(contextPanel) {
						var instance = this;

						var boundingBox = contextPanel.get(STR_BOUNDING_BOX);

						var autoFieldsTriggers = boundingBox.all(SELECTOR_FLOATING_TRIGGER);

						autoFieldsTriggers.each(instance._alignToTriggers, instance);
					},

					_alignToTriggers: function(item, index, collection) {
						var instance = this;

						var panelInstance = item.getData(STR_PANEL_INSTANCE);

						if (!panelInstance._positionHelper.test(':hidden')) {
							panelInstance.position(item);
						}
					},

					_bindAlignFloatingPanelsEvent: function(contextPanel) {
						var instance = this;

						var dragInstance = contextPanel.get('dragInstance');

						dragInstance.on(
							'end',
							function(event) {
								instance._alignFloatingPanels(contextPanel);
							}
						);
					},

					_bindCloseEvent: function(contextPanel) {
						var instance = this;

						contextPanel.get(STR_BOUNDING_BOX).on('key', contextPanel.hide, 'up:27', contextPanel);
					},

					_buildCategoryTreeview: function(categories, parentCategoryId) {
						var instance = this;

						var children = instance._filterCategory(categories, parentCategoryId);

						A.each(
							children,
							function(item, index, collection) {
								var categoryId = item.categoryId;
								var hasChild = instance._filterCategory(categories, categoryId).length;

								var node = new A.TreeNodeCheck(
									{
										alwaysShowHitArea: false,
										id: STR_CATEGORY_NODE + item.categoryId,
										label: Liferay.Util.escapeHTML(item.titleCurrentValue),
										leaf: false,
										on: {
											checkedChange: function(event) {
												if (event.newVal) {
													instance._toggleAllVocabularies(false);
												}
											},
											select: function(event) {
												var categoryId = instance._getCategoryId(event.target);

												var viewContainer = instance._categoryViewContainer;

												instance._selectCategory(categoryId);
												instance._showLoading(viewContainer);
												instance._showSection(viewContainer);

												var categoryURL = instance._createURL(CATEGORY, ACTION_VIEW, LIFECYCLE_RENDER);

												var ioCategoryDetails = instance._getIOCategoryDetails();

												ioCategoryDetails.set('uri', categoryURL.toString()).start();
											}
										}
									}
								);

								var parentId = STR_CATEGORY_NODE + parentCategoryId;
								var parentNode = instance._categoriesTreeView.getNodeById(parentId) || instance._categoriesTreeView;

								parentNode.appendChild(node);

								if (hasChild) {
									instance._buildCategoryTreeview(categories, categoryId);
								}
							}
						);

						return children.length;
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

						instance._hideSection(instance._categoryViewContainer);

						if (instance._selectedCategory) {
							instance._selectedCategory.unselect();
						}
					},

					_createCategoryPanelAdd: function() {
						var instance = this;

						instance._categoryPanelAdd = new A.Dialog(
							{
								align: instance._dialogAlignConfig,
								cssClass: CSS_ADMIN_DIALOG,
								title: Liferay.Language.get('add-category'),
								resizable: false,
								width: 550,
								zIndex: 1000
							}
						).render();

						instance._categoryPanelAdd.hide();

						instance._bindAlignFloatingPanelsEvent(instance._categoryPanelAdd);

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

					_createCategoryTreeView: function(categories) {
						var instance = this;

						var childrenList = A.one(instance._categoryContainerSelector);

						childrenList.empty();

						if (categories.length <= 0) {
							return;
						}

						var boundingBox = Node.create(TPL_CATEGORIES_TREE_CONTAINER);

						childrenList.append(boundingBox);

						instance._categoriesTreeView = new CategoriesTree(
							{
								boundingBox: boundingBox,
								on: {
									dropAppend: function(event) {
										var tree = event.tree;
										var fromCategoryId = instance._getCategoryId(tree.dragNode);
										var toCategoryId = instance._getCategoryId(tree.dropNode);
										var vocabularyId = instance._selectedVocabularyId;

										instance._merge(fromCategoryId, toCategoryId, vocabularyId);
									},
									dropInsert: function(event) {
										var tree = event.tree;
										var parentNode = tree.dropNode.get(STR_PARENT_NODE);
										var fromCategoryId = instance._getCategoryId(tree.dragNode);
										var toCategoryId = instance._getCategoryId(parentNode);
										var vocabularyId = instance._selectedVocabularyId;

										instance._merge(fromCategoryId, toCategoryId, vocabularyId);
									}
								},
								type: 'normal'
							}
						).render();

						instance._buildCategoryTreeview(categories, 0);
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

						instance._vocabularyPanelAdd = new A.Dialog(
							{
								align: instance._dialogAlignConfig,
								cssClass: CSS_ADMIN_DIALOG,
								title: Liferay.Language.get('add-vocabulary'),
								resizable: false,
								width: 550,
								zIndex: 1000
							}
						).render();

						instance._vocabularyPanelAdd.hide();

						instance._bindAlignFloatingPanelsEvent(instance._vocabularyPanelAdd);

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

						var defaultConfig = {
							align: instance._dialogAlignConfig,
							cssClass: CSS_ADMIN_DIALOG,
							resizable: false,
							width: 550,
							zIndex: 1000
						};

						if (Lang.isObject(config)) {
							config = A.merge(defaultConfig, config);
						}
						else {
							config = defaultConfig;
						}

						instance._panelEdit = new A.Dialog(config).render();

						instance._panelEdit.hide();

						instance._bindAlignFloatingPanelsEvent(instance._panelEdit);

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
							panelPermissionsChange = Liferay.Util._openWindow(
								{
									dialog: {
										align: instance._dialogAlignConfig,
										cssClass: 'portlet-asset-categories-admin-dialog permissions-change',
										width: 600
									},
									title: Liferay.Language.get('edit-permissions')
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

						Liferay.Service.Asset.AssetCategory.deleteCategory(
							{
								categoryId: categoryId
							},
							callback
						);
					},

					_deleteSelected: function(event) {
						var instance = this;

						var vocabulary = true;

						var ids = A.all('.vocabulary-item-check:checked').attr(DATA_VOCABULARY_ID);

						if (ids.length) {
							instance._deleteSelectedVocabularies(ids);
						}
						else {
							ids = instance._getSelectedCategoriesId();

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

						if (instance._categoriesTreeView &&
							categoryIds.length > 0 &&
							confirm(Liferay.Language.get('are-you-sure-you-want-to-delete-the-selected-categories'))) {

							Liferay.Service.Asset.AssetCategory.deleteCategories(
								{
									categoryIds: categoryIds
								},
								A.bind(instance._processCategoryDeletion, instance)
							);
						}
					},

					_deleteSelectedVocabularies: function(vocabularyIds) {
						var instance = this;

						if (vocabularyIds.length > 0 &&
							confirm(Liferay.Language.get('are-you-sure-you-want-to-delete-the-selected-vocabularies'))) {

							Liferay.Service.Asset.AssetVocabulary.deleteVocabularies(
								{
									vocabularyIds: vocabularyIds
								},
								A.bind(instance._processVocabularyDeletion, instance)
							);
						}
					},

					_deleteVocabulary: function(vocabularyId, callback) {
						var instance = this;

						Liferay.Service.Asset.AssetVocabulary.deleteVocabulary(
							{
								vocabularyId: vocabularyId
							},
							A.bind(callback, instance)
						);
					},

					_destroyFloatingPanels: function(autoFieldsInstance, panelInstance) {
						var instance = this;

						if (autoFieldsInstance) {
							autoFieldsInstance.destroy();
						}

						if (panelInstance) {
							panelInstance.destroy();
						}
					},

					_displayVocabularyCategoriesImpl: function(categories, callback) {
						var instance = this;

						if (instance._categoriesTreeView) {
							instance._categoriesTreeView.destroy();
							instance._categoriesTreeView = null;

							instance._selectedCategory = null;
						}

						instance._createCategoryTreeView(categories);

						if (categories.length <= 0) {
							var categoryMessages = A.one('#vocabulary-category-messages');

							categoryMessages.removeClass(CSS_MESSAGE_ERROR).removeClass(CSS_MESSAGE_SUCCESS);
							categoryMessages.addClass('portlet-msg-info');
							categoryMessages.html(Liferay.Language.get('there-are-no-categories'));

							categoryMessages.show();
						}

						var vocabularyContainer = A.one(instance._vocabularyContainerSelector);
						var listLinks = vocabularyContainer.all('li');

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

						if (callback) {
							callback();
						}
					},

					_displayList: function(callback) {
						var instance = this;

						var buffer = [];
						var list = A.one(instance._vocabularyContainerSelector);

						instance._showLoading('.vocabulary-categories, .vocabulary-list');

						buffer.push('<ul>');

						instance._getVocabularies(
							function(result) {
								var vocabularies = result.vocabularies;

								instance._vocabularies = vocabularies;

								A.each(
									vocabularies,
									function(item, index, collection) {
										if (index === 0) {
											item.cssClassSelected = STR_SELECTED;
										}
										else {
											item.cssClassSelected = '';
										}

										var auxItem = A.clone(item);

										auxItem.titleCurrentValue = Liferay.Util.escapeHTML(auxItem.titleCurrentValue);

										buffer.push(Lang.sub(TPL_VOCABULARY_LIST, auxItem));
									}
								);

								buffer.push('</ul>');

								list.html(buffer.join(''));

								var firstVocabulary = A.one(instance._vocabularyItemSelector);

								if (firstVocabulary) {
									instance._selectedVocabularyName = instance._getVocabularyName(firstVocabulary);
									instance._selectedVocabularyId = instance._getVocabularyId(firstVocabulary);
								}

								instance._addCategoryButton.attr('disabled', !firstVocabulary);
								instance._addCategoryButtonWrapper.toggleClass('aui-button-disabled', !firstVocabulary);

								if (callback) {
									callback();
								}
							}
						);
					},

					_displayVocabularyCategories: function(vocabularyId, callback) {
						var instance = this;

						var categoryMessages = A.one('#vocabulary-category-messages');

						if (categoryMessages) {
							categoryMessages.hide();
						}

						instance._checkAllCategoriesCheckbox.attr(STR_CHECKED, false);

						instance._getVocabularyCategories(
							vocabularyId,
							function(categories) {
								instance._displayVocabularyCategoriesImpl(categories, callback);
							}
						);
					},

					_getIOCategory: function() {
						var instance = this;

						var ioCategory = instance._ioCategory;

						if (!ioCategory) {
							ioCategory = A.io.request(
								null,
								{
									autoLoad: false,
									dataType: 'json',
									on: {
										success: function(event, id, obj) {
											var response = this.get(STR_RESPONSE_DATA);

											instance._onCategoryAddSuccess(response);
										},
										failure: function(event, id, obj) {
											instance._onCategoryAddFailure(obj);
										}
									}
								}
							);

							instance._ioCategory = ioCategory;
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

					_getSelectedCategoriesId: function() {
						var instance = this;

						var selectedCategoriesIds = [];

						var categoriesTreeView = instance._categoriesTreeView;

						categoriesTreeView.eachChildren(
							function(child) {
								if (child.isChecked()) {
									var categoryId = instance._getCategoryId(child);

									selectedCategoriesIds.push(categoryId);
								}
							},
							true
						);

						return selectedCategoriesIds;
					},

					_getVocabulariesPaginator: function() {
						var instance = this;

						var vocabulariesPaginator = instance._vocabulariesPaginator;

						if (!vocabulariesPaginator) {
							var originalConfig = instance._originalConfig;

							var config = {
								alwaysVisible: false,
								containers: '.vocabularies-paginator',
								firstPageLinkLabel: '<<',
								lastPageLinkLabel: '>>',
								nextPageLinkLabel: '>',
								prevPageLinkLabel: '<',
								rowsPerPageOptions: originalConfig.itemsPerPageOptions
							};

							var paginatorMap = instance._getVocabulariesPaginatorMap();

							AObject.each(
								paginatorMap,
								function(item, index, collection) {
									config[index] = Number(HistoryManager.get(item.historyEntry)) || item.defaultValue;
								}
							);

							vocabulariesPaginator = new A.Paginator(config).render();

							vocabulariesPaginator.on('changeRequest', instance._onVocabulariesPaginatorChangeRequest, instance);

							instance._vocabulariesPaginator = vocabulariesPaginator;
						}

						return vocabulariesPaginator;
					},

					_getVocabulariesPaginatorMap: function() {
						var instance = this;

						var paginatorMap = instance._paginatorMap;

						if (!paginatorMap) {
							paginatorMap = {
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

							instance._paginatorMap = paginatorMap;
						}

						return paginatorMap;
					},

					_feedVocabularySelect: function(vocabularies, selectedVocabularyId) {
						var instance = this;

						if (instance._categoryFormAdd) {
							var selectEl = instance._categoryFormAdd.one(SELECTOR_VOCABULARY_SELECT_LIST);

							if (selectEl) {
								selectedVocabularyId = parseInt(selectedVocabularyId, 10);

								selectEl.empty();

								A.each(
									vocabularies,
									function(item, index, collection) {
										var vocabularyEl = document.createElement('option');

										if (item.vocabularyId == selectedVocabularyId) {
											vocabularyEl.selected = true;
										}

										vocabularyEl.value = item.vocabularyId;

										var vocabularyTextEl = document.createTextNode(item.titleCurrentValue);

										vocabularyEl.appendChild(vocabularyTextEl);

										selectEl.appendChild(vocabularyEl);
									}
								);
							}
						}
					},

					_filterCategory: function(categories, parentCategoryId) {
						var instance = this;

						var filteredCategories;

						if (Lang.isArray(categories)) {
							filteredCategories = A.Array.filter(
								categories,
								function(item, index, collection) {
									return (item.parentCategoryId == parentCategoryId);
								}
							);
						}

						return filteredCategories || [];
					},

					_focusCategoryPanelAdd: function() {
						var instance = this;

						var inputCategoryAddNameNode = instance._inputCategoryNameNode || instance._categoryFormAdd.one(SELECTOR_CATEGORY_NAME_INPUT);

						Liferay.Util.focusFormField(inputCategoryAddNameNode);
					},

					_focusVocabularyPanelAdd: function() {
						var instance = this;

						var inputVocabularyAddNameNode = instance._inputVocabularyAddNameNode || instance._vocabularyFormAdd.one(SELECTOR_VOCABULARY_NAME_INPUT);

						Liferay.Util.focusFormField(inputVocabularyAddNameNode);
					},

					_getCategory: function(categoryId) {
						var instance = this;

						return A.Widget.getByNode('#' + STR_CATEGORY_NODE + categoryId);
					},

					_getCategoryId: function(node) {
						var instance = this;

						var nodeId = node.get('id') || '';
						var categoryId = nodeId.replace(STR_CATEGORY_NODE, '');

						if (Lang.isGuid(categoryId)) {
							categoryId = '';
						}

						return categoryId;
					},

					_getParentCategoryId: function(node) {
						var instance = this;

						var parentNode = node.get(STR_PARENT_NODE);

						return instance._getCategoryId(parentNode);
					},

					_getVocabularies: function(callback) {
						var instance = this;

						var paginator = instance._getVocabulariesPaginator();

						var currentPage = 0;

						var query = instance._liveSearch.get(STR_QUERY);

						if (!instance._restartSearch) {
							currentPage = paginator.get(STR_PAGE);

							if (!currentPage) {
								var paginatorMap = instance._getVocabulariesPaginatorMap();

								currentPage = paginatorMap[STR_PAGE].defaultValue;
							}

							currentPage -= 1;
						}

						var rowsPerPage = paginator.get(STR_ROWS_PER_PAGE);

						var start = currentPage * rowsPerPage;
						var end = start + rowsPerPage;

						Liferay.Service.Asset.AssetVocabulary.getJSONGroupVocabularies(
							{
								groupId: themeDisplay.getParentGroupId(),
								name: query,
								start: start,
								end: end,
								obc: null
							},
							function(result) {
								instance._restartSearch = false;

								paginator.setState(result);

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

					_getVocabularyCategories: function(vocabularyId, callback) {
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
									groupId: themeDisplay.getParentGroupId(),
									name: Lang.sub(TPL_SEARCH_QUERY, [query])
								},
								defaultParams
							);
						}

						Liferay.Service.Asset.AssetCategory.getVocabularyCategories(params, callback);
					},

					_getVocabularyId: function(exp) {
						var instance = this;

						return A.one(exp).attr(DATA_VOCABULARY_ID);
					},

					_getVocabularyName: function(exp) {
						var instance = this;

						return A.one(exp).attr('data-vocabulary');
					},

					_hideAllMessages: function() {
						var instance = this;

						instance._container.one('.lfr-message-response').hide();
					},

					_hideFloatingPanels: function(event) {
						var instance = this;

						instance._processAutoFieldsTriggers(event, instance._resetInputLocalized);
					},

					_hideSection: function(exp) {
						var instance = this;

						var node = A.one(exp);

						if (node) {
							var parentNode = node.ancestor(SELECTOR_CSS_COLUMN);

							if (parentNode) {
								parentNode.previous(SELECTOR_CSS_COLUMN).replaceClass(CSS_COLUMN_WIDTH_CATEGORY, CSS_COLUMN_WIDTH_CATEGORY_FULL);
								parentNode.hide();
							}
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

					_initializeCategoryPanelAdd: function(action) {
						var instance = this;

						var categoryFormAdd = instance._categoryPanelAdd.get(STR_CONTENT_BOX).one(SELECTOR_UPDATE_CATEGORY_FORM);

						categoryFormAdd.detach(EVENT_SUBMIT);

						categoryFormAdd.on(EVENT_SUBMIT, instance._onCategoryFormSubmit, instance, categoryFormAdd, action);

						var closeButton = categoryFormAdd.one(SELECTOR_BUTTON_CANCEL);

						closeButton.on(EVENT_CLICK, instance._onCategoryAddButtonClose, instance);

						instance._categoryFormAdd = categoryFormAdd;

						instance._feedVocabularySelect(instance._vocabularies, instance._selectedVocabularyId);

						instance._focusCategoryPanelAdd();
					},

					_initializeCategoryPanelEdit: function() {
						var instance = this;

						var categoryFormEdit = instance._panelEdit.get(STR_CONTENT_BOX).one(SELECTOR_UPDATE_CATEGORY_FORM);

						categoryFormEdit.detach(EVENT_SUBMIT);

						categoryFormEdit.on(EVENT_SUBMIT, instance._onCategoryFormSubmit, instance, categoryFormEdit);

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

						Liferay.Util.focusFormField(inputCategoryNameNode);
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

						Liferay.Util.focusFormField(inputVocabularyEditNameNode);
					},

					_loadData: function() {
						var instance = this;

						instance._closeEditSection();

						instance._checkAllVocabulariesCheckbox.attr(STR_CHECKED, false);

						instance._displayList(
							function() {
								instance._displayVocabularyCategories(instance._selectedVocabularyId);
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

						panelPermissionsChange.iframe.set('uri', url);

						panelPermissionsChange._syncUIPosAlign();

						if (instance._panelEdit) {
							var zIndex = parseInt(instance._panelEdit.get(STR_ZINDEX), 10) + 2;

							panelPermissionsChange.set(STR_ZINDEX, zIndex);
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

					_onCategoryAddSuccess: function(response) {
						var instance = this;

						var exception = response.exception;

						if (!exception && response.categoryId) {
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
							var errorKey = '';

							if (exception.indexOf('DuplicateCategoryException') > -1) {
								errorKey = Liferay.Language.get('that-category-already-exists');
							}
							else if ((exception.indexOf('CategoryNameException') > -1) ||
									(exception.indexOf('AssetCategoryException') > -1)) {

								errorKey = Liferay.Language.get('one-of-your-fields-contains-invalid-characters');
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
								A.bind(instance._processCategoryDeletion, instance)
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
							else  if (action == ACTION_ADD_SUBCATEGORY) {
								parentCategoryId = instance._selectedCategoryId;
							}

							form.one(parentCategoryElId).val(parentCategoryId);

							Liferay.fire(
								EVENT_SAVE_AUTO_FIELDS,
								{
									form: form
								}
							);

							instance._addCategory(form);
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

					_onCategoryViewContainerClick: function(event) {
						var instance = this;

						var targetId = event.target.get('id');

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

						var url = event.target.attr('data-url');

						instance._loadPermissions(url);
					},

					_onDragDrop: function(event) {
						var instance = this;

						var dragNode = event.drag.get(STR_NODE);
						var dropNode = event.drop.get(STR_NODE);

						var node = A.Widget.getByNode(dragNode);

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

						var paginatorState = {};

						var paginatorMap = instance._getVocabulariesPaginatorMap();

						AObject.each(
							paginatorMap,
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
									paginatorState[index] = value;
								}
							}
						);

						if (!AObject.isEmpty(paginatorState)) {
							instance._vocabulariesPaginator.setState(paginatorState);

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

										A.one(instance._vocabularyContainerSelector).set('scrollTop', scrollTop);
									}
								}
							);

							instance._hidePanels();
						}
						else {
							var errorKey = '';

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
							Liferay.Util.checkAllBox(event.currentTarget, CSS_VOCABULARY_ITEM_CHECK, instance._checkAllVocabulariesCheckbox);

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

					_onVocabulariesPaginatorChangeRequest: function(event) {
						var instance = this;

						var stateBefore = event.state.before;
						var state = event.state;

						var historyState = {};

						var paginatorMap = instance._getVocabulariesPaginatorMap();

						AObject.each(
							paginatorMap,
							function(item, index, collection) {
								if (owns(state, index)) {
									var historyEntry = item.historyEntry;

									var newItemValue = state[index];

									var value = INVALID_VALUE;

									if (newItemValue === item.defaultValue &&
										Lang.isValue(HistoryManager.get(historyEntry))) {

										value = null;
									}
									else if (newItemValue !== stateBefore[index]) {
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

					_processAutoFieldsTriggers: function(event, callback) {
						var instance = this;

						var contextPanel = event.currentTarget;

						var boundingBox = contextPanel.get(STR_BOUNDING_BOX);

						var autoFieldsTriggers = boundingBox.all(SELECTOR_FLOATING_TRIGGER);

						autoFieldsTriggers.each(
							function(item, index, collection) {
								var autoFieldsInstance = item.getData(STR_AUTO_FIELDS_INSTANCE);
								var panelInstance = item.getData(STR_PANEL_INSTANCE);

								callback.call(instance, autoFieldsInstance, panelInstance);
							}
						);
					},

					_processCategoryDeletion: function(result) {
						var instance = this;

						var exception = result.exception;

						if (!exception) {
							instance._closeEditSection();
							instance._hidePanels();
							instance._displayVocabularyCategories(instance._selectedVocabularyId);

							instance._sendMessage(MESSAGE_TYPE_SUCCESS, Liferay.Language.get('your-request-processed-successfully'));
						}
						else {
							var errorMessage = Liferay.Language.get('your-request-failed-to-complete');

							if (exception.indexOf(EXCEPTION_PRINCIPAL) > -1) {
								errorMessage = Liferay.Language.get('you-do-not-have-permission-to-access-the-requested-resource');
							}

							instance._sendMessage(MESSAGE_TYPE_ERROR, errorMessage);
						}
					},

					_processVocabularyDeletion: function(result) {
						var instance = this;

						var exception = result.exception;

						if (!exception) {
							instance._closeEditSection();
							instance._hidePanels();
							instance._loadData();
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

					_processSearch: function() {
						var instance = this;

						instance._restartSearch = true;

						if (!instance._liveSearch.get(STR_QUERY) || instance._searchType.val() == STR_VOCABULARIES) {
							instance._selectedVocabularyId = null;

							instance._loadData();
						}
						else if (instance._selectedVocabularyId) {
							instance._closeEditSection();

							instance._displayVocabularyCategories(instance._selectedVocabularyId);
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

					_resetInputLocalized: function(autoFieldsInstance, panelInstance) {
						var instance = this;

						if (autoFieldsInstance) {
							autoFieldsInstance.reset();
						}

						if (panelInstance) {
							panelInstance.hide();
						}
					},

					_selectCategory: function(categoryId) {
						var instance = this;

						var category = instance._getCategory(categoryId);
						var parentCategoryId = instance._getParentCategoryId(category);

						categoryId = instance._getCategoryId(category);

						instance._selectedCategoryId = categoryId;
						instance._selectedParentCategoryId = parentCategoryId || 0;

						if (!categoryId) {
							return category;
						}

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
						var typeClass = 'portlet-msg-' + type;

						output.removeClass(CSS_MESSAGE_ERROR).removeClass(CSS_MESSAGE_SUCCESS);
						output.addClass(typeClass);
						output.html(message);

						output.show();

						instance._hideMessageTask();
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

						if (!categoryPanelAdd) {
							categoryPanelAdd = instance._createCategoryPanelAdd();
						}
						else if (instance._currentCategoryPanelAddIOHandle) {
							instance._currentCategoryPanelAddIOHandle.detach();
						}

						var categoryURL = instance._createURL(CATEGORY, action, LIFECYCLE_RENDER);

						categoryPanelAdd.plug(
							A.Plugin.IO,
							{
								autoLoad: false,
								uri: categoryURL.toString()
							}
						);
						
						categoryPanelAdd.show();

						categoryPanelAdd._syncUIPosAlign();

						instance._currentCategoryPanelAddIOHandle = categoryPanelAdd.io.after(
							STR_SUCCESS,
							A.bind(instance._initializeCategoryPanelAdd, instance, action)
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

					_showLoading: function(container) {
						var instance = this;

						A.all(container).html('<div class="loading-animation" />');
					},

					_showSection: function(exp) {
						var instance = this;

						var element = A.one(exp);

						if (element) {
							var parentNode = element.ancestor(SELECTOR_CSS_COLUMN);

							if (parentNode) {
								parentNode.previous(SELECTOR_CSS_COLUMN).replaceClass(CSS_COLUMN_WIDTH_CATEGORY_FULL, CSS_COLUMN_WIDTH_CATEGORY);

								parentNode.show();

								var firstInput = element.one('input');

								if (firstInput) {
									firstInput.focus();
								}
							}
						}
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
								instance._initializeVocabularyPanelAdd,
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
					},

					_toggleAllVocabulariesFn: function(state) {
						var instance = this;

						if (state === true) {
							instance._toggleAllCategories(false);
						}

						instance._checkAllVocabulariesCheckbox.attr(STR_CHECKED, state);

						A.all('.vocabulary-item-check').attr(STR_CHECKED, state);
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
						ioCategoryUpdate.set('uri', moveURL.toString());

						ioCategoryUpdate.set('arguments.success', vocabularyId);

						ioCategoryUpdate.start();
					},

					_categoryItemSelector: '.vocabulary-categories .aui-tree-node',
					_categoryContainerSelector: '.vocabulary-categories',
					_selectedVocabulary: null,
					_selectedVocabularyId: null,
					_selectedVocabularyName: null,
					_vocabularies: null,
					_vocabularyItemSelector: '.vocabulary-list li',
					_vocabularyContainerSelector: '.vocabulary-list'
				}
			}
		);

		var CategoriesTree = A.Component.create(
			{
				NAME: 'CategoriesTree',

				EXTENDS: A.TreeViewDD,

				prototype: {
					_findCategoryByName: function(event) {
						var instance = this;

						var result = false;

						var dragNode = event.drag.get(STR_NODE).get(STR_PARENT_NODE);

						var dragTreeNode = Widget.getByNode(dragNode);

						if (dragTreeNode) {
							var categoryName = dragTreeNode.get(STR_LABEL);

							var dropAction = instance.dropAction;

							var dropNode = event.drop.get(STR_NODE).get(STR_PARENT_NODE);

							if (dropAction !== 'append') {
								dropNode = dropNode.get('parentNode.parentNode');
							}

							var dropTreeNode = Widget.getByNode(dropNode);

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

							if (instance._findCategoryByName(event)) {
								instance._resetState(instance.nodeContent);
							}
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
					initializer: function () {
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
		requires: ['aui-live-search', 'aui-dialog', 'aui-dialog-iframe', 'aui-paginator', 'autocomplete-base', 'aui-tree-view', 'dd', 'json', 'liferay-history-manager', 'liferay-portlet-url', 'liferay-util-window']
	}
);