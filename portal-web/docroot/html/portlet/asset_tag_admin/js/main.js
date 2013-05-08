AUI.add(
	'liferay-tags-admin',
	function(A) {
		var AObject = A.Object;
		var HistoryManager = Liferay.HistoryManager;
		var Lang = A.Lang;
		var Node = A.Node;

		var owns = AObject.owns;

		var ACTION_ADD = 0;

		var ACTION_EDIT = 1;

		var ACTION_VIEW = 2;

		var CSS_ACTIVE_AREA = 'active-area';

		var CSS_TAG_DIALOG = 'portlet-asset-tag-admin-dialog';

		var DRAG_NODE = 'dragNode';

		var EVENT_CLICK = 'click';

		var EVENT_SUBMIT = 'submit';

		var INVALID_VALUE = A.Attribute.INVALID_VALUE;

		var LIFECYCLE_RENDER = 0;

		var LIFECYCLE_PROCESS = 1;

		var MAX_DISPLAY_ITEMS = 15;

		var MESSAGE_TYPE_ERROR = 'error';

		var MESSAGE_TYPE_SUCCESS = 'success';

		var NODE = 'node';

		var TPL_PORTLET_MESSAGES = '<div class="hide lfr-message-response" id="portletMessages" />';

		var TPL_TAG_LIST_CONTAINER = '<ul class="nav nav-pills nav-stacked">';

		var TPL_TAG_LIST = '<li class="tag-item-container results-row {cssClassSelected}" data-tag="{name}" data-tagId="{tagId}" tabIndex="0">' +
			'<a href="javascript:;" data-tagId="{tagId}" tabIndex="-1">' +
				'<input type="checkbox" class="tag-item-check" name="tag-item-check" data-tagId="{tagId}" data-tagName="{name}">' +
				'<span class="tag-item-name" data-tagId="{tagId}">{name}</span>' +
				'<span tabindex="0" class="tag-item-actions-trigger" data-tagId="{tagId}"></span>' +
			'</a>' +
		'</li>';

		var TPL_TAG_MERGE_BODY = '<div class="container-fluid tags-admin-merge-tag">' +
			'<div class="row">' +
				'<div class="span6">' +
					'<div class="selected-tags-container">' +
						'<label for="{namespace}selectedTagsList">' + Liferay.Language.get('tags-to-be-merged') + ':</label>' +
						'<select id="{namespace}selectedTagsList" class="selected-tags-list" multiple>' +
						'</select>' +
					'</div>' +
					'<div class="btn-group" id="{namespace}sortSelect">' +
						'<button class="btn btn-mini tag-move-up" id="{namespace}tagMoveUp"><i class="icon-chevron-up"></i></button>' +
						'<button class="btn btn-mini tag-move-down" id="{namespace}tagMoveDown"><i class="icon-chevron-down"></i></button>' +
					'</div>' +
				'</div>' +
				'<div class="span6">' +
					'<div class="target-tags-container">' +
						'<label class="tags-label" for="{namespace}targetTagsList">' + Liferay.Language.get('target-tag') + ':</label>' +
						'<select id="{namespace}targetTagsList" class="target-tags-list">' +
						'</select>' +
					'</div>' +
				'</div>' +
			'</div>' +
		'</div>';

		var TPL_TAG_MERGE_FOOTER =
			'<div class="container-fluid tags-admin-merge-tag">' +
				'<div class="row-fluid">' +
					'<div class="span7">' +
						'<div class="tag-options pull-left">' +
							'<label class="checkbox">' +
								'<input id="{namespace}mergeOnlySelectedTags" type="checkbox">' + Liferay.Language.get('merge-only-selected-tags') +
							'</label>' +
							'<label class="checkbox">' +
								'<input checked id="{namespace}overrideProperties" type="checkbox">' + Liferay.Language.get('override-tags-properties') +
							'</label>' +
						'</div>' +
					'</div>' +
					'<div class="span5">' +
						'<div id="{namespace}buttonsContainer"></div>' +
					'</div>' +
				'</div>' +
			'</div>';

		var TPL_TAG_MERGE_ITEM = '<option value="{value}" title="{name}" active>{name}</option>';

		var TPL_TAGS_MESSAGES = '<div class="alert alert-info hide lfr-message-response" id="tagsMessages" />';

		var AssetTagsAdmin = A.Component.create(
			{
				NAME: 'assettagsadmin',

				EXTENDS: A.Base,

				prototype: {
					initializer: function(config) {
						var instance = this;

						instance._config = config;

						instance.portletId = config.portletId;

						instance._prefixedPortletId = '_' + config.portletId + '_';

						instance._container = A.one('.tags-admin-container');
						instance._tagViewContainer = A.one('.tag-view-container');
						instance._tagsList = A.one('.tags-admin-list');

						instance._tagsMessageContainer = Node.create(TPL_TAGS_MESSAGES);
						instance._portletMessageContainer = Node.create(TPL_PORTLET_MESSAGES);

						instance._container.placeBefore(instance._portletMessageContainer);

						var tagDataContainer = A.one('.tags-admin-edit-tag');

						instance._dialogAlignConfig = {
							node: tagDataContainer,
							points: ['tc', 'tl']
						};

						var portletMessageContainer = instance._portletMessageContainer;

						instance._hideMessageTask = A.debounce('hide', 7000, portletMessageContainer);

						instance._tagsList.on(EVENT_CLICK, instance._onTagsListClick, instance);
						instance._tagsList.on('key', instance._onTagsListClick, 'up:13', instance);

						instance._tagViewContainer.on(EVENT_CLICK, instance._onTagViewContainerClick, instance);

						instance._listContainer = instance._container.one('.tags-admin-list-container');

						instance._listContainer.plug(A.LoadingMask);

						var namespace = instance._prefixedPortletId;

						var addTagButton = A.one('#' + namespace + 'addTagButton');

						if (addTagButton) {
							addTagButton.on(EVENT_CLICK, instance._onShowTagPanel, instance, ACTION_ADD);
						}

						var tagsPermissionsButton = A.one('#' + namespace + 'tagsPermissionsButton');

						if (tagsPermissionsButton) {
							tagsPermissionsButton.on(EVENT_CLICK, instance._onTagChangePermissions, instance);
						}

						A.one('#' + namespace + 'deleteSelectedTags').on(EVENT_CLICK, instance._deleteSelectedTags, instance);
						A.one('#' + namespace + 'mergeSelectedTags').on(EVENT_CLICK, instance._mergeSelectedTags, instance);

						var checkAllTagsCheckbox = A.one('#' + namespace + 'checkAllTagsCheckbox');

						checkAllTagsCheckbox.on(EVENT_CLICK, instance._checkAllTags, instance);

						instance._checkAllTagsCheckbox = checkAllTagsCheckbox;

						instance._createTagSearch();

						HistoryManager.on('stateChange', instance._onStateChange, instance);

						instance._loadData();

						instance.after('drag:drag', instance._afterDrag);
						instance.after('drag:drophit', instance._afterDragDrop);
						instance.after('drag:enter', instance._afterDragEnter);
						instance.after('drag:exit', instance._afterDragExit);
						instance.after('drag:start', instance._afterDragStart);
					},

					_afterDrag: function(event) {
						var instance = this;

						A.DD.DDM.syncActiveShims(true);
					},

					_afterDragDrop: function(event) {
						var instance = this;

						var dropNode = event.drop.get(NODE);
						var node = event.target.get(NODE);

						dropNode.removeClass(CSS_ACTIVE_AREA);

						instance._merge(node, dropNode);
					},

					_afterDragEnter: function(event) {
						var instance = this;

						var target = event.target;
						var proxyNode = target.get(DRAG_NODE);
						var node = target.get(NODE);
						var dropNode = event.drop.get(NODE);

						var textDestNode = dropNode.one('a').html();
						var textSrcNode = node.one('a').html();

						proxyNode.one('a').html(textDestNode + ' &larr; ' + textSrcNode);

						dropNode.addClass(CSS_ACTIVE_AREA);
					},

					_afterDragExit: function(event) {
						var instance = this;

						var dropNode = event.drop.get(NODE);

						dropNode.removeClass(CSS_ACTIVE_AREA);
					},

					_afterDragStart: function(event) {
						var instance = this;

						var drag = event.target;

						var proxyNode = drag.get(DRAG_NODE);
						var node = drag.get(NODE);

						var clone = proxyNode.get('firstChild');

						if (!clone) {
							clone = node.clone().empty();

							clone.addClass('tag-item-merge');

							proxyNode.attr('data-tagId', clone.attr('data-tagId'));
							proxyNode.appendChild(clone);
						}

						clone.html(node.html());
					},

					_afterTagsPaginationChangeRequest: function(event) {
						var instance = this;

						var lastState = event.state.lastState;
						var state = event.state;

						var historyState = {};

						var paginationMap = instance._getTagsPaginationMap();

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

						instance._reloadData();
					},

					_bindCloseEvent: function(contextPanel) {
						var instance = this;

						contextPanel.get('boundingBox').on('key', contextPanel.hide, 'up:27', contextPanel);
					},

					_checkAllTags: function(event) {
						var currentCheckedStatus = event.currentTarget.attr('checked');

						A.all('.tag-item-check').attr('checked', currentCheckedStatus);
					},

					_createTagPanelAdd: function() {
						var instance = this;

						var tagPanelAdd = Liferay.Util.Window.getWindow(
							{
								dialog: {
									align: instance._dialogAlignConfig,
									cssClass: CSS_TAG_DIALOG
								},
								title: Liferay.Language.get('add-tag')
							}
						);

						tagPanelAdd.hide();

						instance._bindCloseEvent(tagPanelAdd);

						instance._tagPanelAdd = tagPanelAdd;

						return tagPanelAdd;
					},

					_createTagPanelEdit: function() {
						var instance = this;

						instance._tagPanelEdit = Liferay.Util.Window.getWindow(
							{
								dialog: {
									align: instance._dialogAlignConfig,
									cssClass: CSS_TAG_DIALOG
								},
								title: Liferay.Language.get('edit-tag')
							}
						);

						instance._tagPanelEdit.hide();

						instance._bindCloseEvent(instance._tagPanelEdit);

						instance._tagPanelEdit.after(
							'visibleChange',
							function(event) {
								if (!event.newVal) {
									var body = instance._tagPanelEdit.getStdModNode(A.WidgetStdMod.BODY);

									body.empty();
								}
							}
						);

						return instance._tagPanelEdit;
					},

					_createTagPanelPermissions: function() {
						var instance = this;

						var panelPermissionsChange = instance._panelPermissionsChange;

						if (!panelPermissionsChange) {
							panelPermissionsChange = Liferay.Util.Window.getWindow(
								{
									dialog: {
										align: instance._dialogAlignConfig,
										cssClass: CSS_TAG_DIALOG + ' permissions-change',
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

					_createTagSearch: function() {
						var instance = this;

						var searchInput = A.one('#' + instance._prefixedPortletId + 'tagsAdminSearchInput');

						var tagsSearch = new TagsSearch(
							{
								inputNode: searchInput,
								minQueryLength: 0,
								queryDelay: 300
							}
						);

						tagsSearch.after(
							'query',
							function(event) {
								instance._restartSearch = true;

								instance._loadData();
							}
						);

						searchInput.on('keydown', instance._onSearchInputKeyDown, instance);

						instance._tagsSearch = tagsSearch;
					},

					_createURL: function(action, lifecycle, params) {
						var instance = this;

						var path = '/asset_tag_admin/';

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

						if (action == ACTION_ADD) {
							path += 'edit_tag';
						}
						else if (action == ACTION_EDIT) {
							path += 'edit_tag';

							url.setParameter('tagId', instance._selectedTagId);
						}
						else if (action == ACTION_VIEW) {
							path += 'view_tag';

							url.setParameter('tagId', instance._selectedTagId);
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

					_deleteSelectedTags: function(event) {
						var instance = this;

						var tagsNodes = A.all('.tag-item-check:checked');

						if (tagsNodes.size() > 0) {
							if (confirm(Liferay.Language.get('are-you-sure-you-want-to-delete-the-selected-tags'))) {
								var checkedItemsIds = tagsNodes.attr('data-tagId');

								if (checkedItemsIds.length > 0) {
									Liferay.Service(
										'/assettag/delete-tags',
										{
											tagIds: checkedItemsIds
										},
										A.bind('_processActionResult', instance)
									);
								}
							}
						}
						else {
							alert(Liferay.Language.get('there-are-no-selected-tags'));
						}
					},

					_deleteTag: function(tagId, callback) {
						var instance = this;

						Liferay.Service(
							'/assettag/delete-tag',
							{
								tagId: tagId
							},
							callback
						);
					},

					_displayTagData: function(tagId) {
						var instance = this;

						tagId = tagId || instance._selectedTagId;

						if (tagId) {
							var tagURL = instance._createURL(ACTION_VIEW, LIFECYCLE_RENDER);

							var ioDetails = instance._getIOTagDetails();

							ioDetails.set('uri', tagURL.toString()).start();
						}
						else {
							instance._tagViewContainer.empty();
						}
					},

					_displayTags: function(callback) {
						var instance = this;

						var loadingMask = instance._listContainer.loadingmask;

						loadingMask.show();

						instance._getTags(
							function(result) {
								loadingMask.hide();

								instance._prepareTags(result.tags, callback);
							}
						);
					},

					_focusTagPanelAdd: function() {
						var instance = this;

						var inputTagAddNameNode = instance._tagFormAdd.one('.tag-name input');

						Liferay.Util.focusFormField(inputTagAddNameNode);
					},

					_focusTagPanelEdit: function() {
						var instance = this;

						var inputTagEditNameNode = instance._tagFormEdit.one('.tag-name input');

						Liferay.Util.focusFormField(inputTagEditNameNode);
					},

					_getDDHandler: function() {
						var instance = this;

						var ddHandler = instance._ddHandler;

						if (!ddHandler) {
							ddHandler = new A.DD.Delegate(
								{
									container: '.tags-admin-list',
									nodes: 'li',
									target: true
								}
							);

							var dd = ddHandler.dd;

							dd.addTarget(instance);

							dd.plug(
								A.Plugin.DDProxy,
								{
									borderStyle: '0',
									moveOnEnd: false
								}
							);

							dd.plug(
								A.Plugin.DDConstrained,
								{
									constrain2node: instance._tagsList
								}
							);

							dd.plug(
								A.Plugin.DDNodeScroll,
								{
									node: instance._tagsList,
									scrollDelay: 100
								}
							);

							dd.removeInvalid('a');

							instance._ddHandler = ddHandler;
						}

						return ddHandler;
					},

					_getIOTagUpdate: function() {
						var instance = this;

						var ioTag = instance._ioTag;

						if (!ioTag) {
							ioTag = A.io.request(
								null,
								{
									autoLoad: false,
									dataType: 'json',
									on: {
										success: function(event, id, obj) {
											var response = this.get('responseData');

											instance._onTagUpdateSuccess(response);
										},
										failure: function(event, id, obj) {
											instance._onTagUpdateFailure(obj);
										}
									}
								}
							);

							instance._ioTag = ioTag;
						}

						return ioTag;
					},

					_getIOTagDetails: function() {
						var instance = this;

						var ioTagDetails = instance._ioTagDetails;

						if (!ioTagDetails) {
							ioTagDetails = A.io.request(
								null,
								{
									autoLoad: false,
									dataType: 'html',
									on: {
										success: function(event, id, obj) {
											var response = this.get('responseData');

											instance._onTagViewSuccess(response);
										},
										failure: function(event, id, obj) {
											instance._onTagViewFailure(obj);
										}
									}
								}
							);

							instance._ioTagDetails = ioTagDetails;
						}

						return ioTagDetails;
					},

					_getTag: function(tagId) {
						var instance = this;

						return instance._tagsList.one('li[data-tagId="' + tagId + '"]');
					},

					_getTagId: function(expr) {
						var instance = this;

						var elem = expr;
						var attr;

						if (!expr instanceof Node) {
							elem = instance._tagsList.one(expr);
						}

						if (elem) {
							attr = elem.attr('data-tagId');
						}

						return attr;
					},

					_getTagName: function(expr) {
						var instance = this;

						var elem = expr;
						var attr;

						if (!expr instanceof Node) {
							elem = instance._tagsList.one(expr);
						}

						if (elem) {
							attr = elem.attr('data-tag');
						}

						return attr;
					},

					_getTagsPagination: function() {
						var instance = this;

						var tagsPagination = instance._tagsPagination;

						if (!tagsPagination) {
							var instanceConfig = instance._config;

							var config = {
								boundingBox: '.tags-pagination',
								circular: false,
								visible: false
							};

							var paginationMap = instance._getTagsPaginationMap();

							AObject.each(
								paginationMap,
								function(item, index, collection) {
									config[index] = Number(HistoryManager.get(item.historyEntry)) || item.defaultValue;
								}
							);

							tagsPagination = new A.Pagination(config).render();

							tagsPagination.after('changeRequest', instance._afterTagsPaginationChangeRequest, instance);

							instance._tagsPagination = tagsPagination;
						}

						return tagsPagination;
					},

					_getTagsPaginationMap: function() {
						var instance = this;

						var paginationMap = instance._paginationMap;

						if (!paginationMap) {
							paginationMap = {
								page: {
									historyEntry: instance._prefixedPortletId + 'page',
									defaultValue: 1,
									formatter: Number
								},
								tagsPerPage: {
									historyEntry: instance._prefixedPortletId + 'tagsPerPage',
									defaultValue: instance._config.tagsPerPage,
									formatter: Number
								}
							};

							instance._paginationMap = paginationMap;
						}

						return paginationMap;
					},

					_getTagPanelMerge: function() {
						var instance = this;

						var tagPanelMerge = instance._tagPanelMerge;

						if (!tagPanelMerge) {
							var namespace = instance._prefixedPortletId;

							var tplValues = {
								namespace: namespace
							};

							var panelBodyContent = Lang.sub(TPL_TAG_MERGE_BODY, tplValues);
							var panelFooterContent = Lang.sub(TPL_TAG_MERGE_FOOTER, tplValues);

							tagPanelMerge = Liferay.Util.Window.getWindow(
								{
									dialog: {
										align: instance._dialogAlignConfig,
										bodyContent: panelBodyContent,
										cssClass: CSS_TAG_DIALOG,
										footerContent: panelFooterContent
									},
									title: Liferay.Language.get('merge-tags')
								}
							);

							var okButton = new A.Button(
								{
									label: Liferay.Language.get('ok'),
									on: {
										click: A.bind('_onTagMergeClick', instance)
									}
								}
							);

							var cancelButton = new A.Button(
								{
									label: Liferay.Language.get('cancel'),
									on: {
										click: function(event) {
											tagPanelMerge.hide();
										}
									}
								}
							);

							var buttonsContainer = A.one('#' + namespace + 'buttonsContainer');

							okButton.render(buttonsContainer);
							cancelButton.render(buttonsContainer);

							tagPanelMerge.hide();

							tagPanelMerge.after(
								'visibleChange',
								function(event) {
									if (!event.newVal) {
										instance._previousTagData = null;
									}
								}
							);

							A.one('#' + namespace + 'sortSelect').delegate(
								'click',
								function(event) {
									var down = event.currentTarget.hasClass('tag-move-down');

									Liferay.Util.reorder(instance._selectedTagsList, down);
								},
								'button'
							);

							instance._bindCloseEvent(tagPanelMerge);

							var contentBox = tagPanelMerge.get('contentBox');

							var targetTagsList = contentBox.one('#' + namespace + 'targetTagsList');

							targetTagsList.on('change', instance._updateMergeItemsTarget, instance);

							instance._selectedTagsList = contentBox.one('#' + namespace + 'selectedTagsList');
							instance._targetTagsList = contentBox.one('#' + namespace + 'targetTagsList');

							instance._tagPanelMerge = tagPanelMerge;
						}

						return tagPanelMerge;
					},

					_getTags: function(callback) {
						var instance = this;

						var pagination = instance._getTagsPagination();

						var config = instance._config;

						var tagsPerPage = config.tagsPerPage;

						var currentPage = 0;

						var query = instance._tagsSearch.get('query');

						if (!instance._restartSearch) {
							currentPage = pagination.get('page');

							if (!currentPage) {
								var paginationMap = instance._getTagsPaginationMap();

								currentPage = paginationMap['page'].defaultValue;
							}

							currentPage -= 1;
						}

						var start = currentPage * tagsPerPage;
						var end = start + tagsPerPage;

						Liferay.Service(
							'/assettag/get-json-group-tags',
							{
								groupId: themeDisplay.getSiteGroupId(),
								name: query,
								start: start,
								end: end
							},
							function(result) {
								var total = result.total;

								instance._restartSearch = false;

								pagination.set('total', Math.ceil(total / tagsPerPage));
								pagination.set('visible', (total > tagsPerPage));

								pagination.setState(result);

								if (callback) {
									callback.apply(instance, arguments);
								}
							}
						);
					},

					_initializeTagPanelAdd: function(callback) {
						var instance = this;

						var tagPanelAdd = instance._tagPanelAdd;

						var tagFormAdd = tagPanelAdd.get('contentBox').one('form.update-tag-form');

						tagFormAdd.detach(EVENT_SUBMIT);

						tagFormAdd.on(EVENT_SUBMIT, instance._onTagFormSubmit, instance, tagFormAdd);

						instance._tagFormAdd = tagFormAdd;

						var closeButton = tagFormAdd.one('.close-panel');

						closeButton.on(
							EVENT_CLICK,
							function(event) {
								tagPanelAdd.hide();
							}
						);

						tagPanelAdd.on(
							'visibleChange',
							function(event) {
								if (!event.newVal) {
									if (instance._tagFormAdd) {
										instance._tagFormAdd.reset();
									}

									instance._resetTagsProperties(event);
								}
							}
						);

						if (callback) {
							callback.call(instance);
						}

						return tagPanelAdd;
					},

					_initializeTagPanelEdit: function(callback) {
						var instance = this;

						var tagPanelEdit = instance._tagPanelEdit;

						var tagFormEdit = tagPanelEdit.get('contentBox').one('form.update-tag-form');

						tagFormEdit.detach(EVENT_SUBMIT);

						tagFormEdit.on(EVENT_SUBMIT, instance._onTagFormSubmit, instance, tagFormEdit);

						var closeButton = tagFormEdit.one('.close-panel');

						closeButton.on(
							EVENT_CLICK,
							function(event) {
								tagPanelEdit.hide();
							}
						);

						var buttonDeleteTag = tagFormEdit.one('#deleteTagButton');

						if (buttonDeleteTag) {
							buttonDeleteTag.on(EVENT_CLICK, instance._onDeleteTag, instance);
						}

						var buttonChangeTagPermissions = tagFormEdit.one('#updateTagPermissions');

						if (buttonChangeTagPermissions) {
							buttonChangeTagPermissions.on(EVENT_CLICK, instance._onTagChangePermissions, instance);
						}

						var inputTagNameNode = tagFormEdit.one('.tag-name input');

						Liferay.Util.focusFormField(inputTagNameNode);
					},

					_hideAllMessages: function() {
						var instance = this;

						instance._container.all('.lfr-message-response').hide();
					},

					_hidePanels: function() {
						var instance = this;

						if (instance._tagPanelAdd) {
							instance._tagPanelAdd.hide();
						}

						if (instance._tagPanelEdit) {
							instance._tagPanelEdit.hide();
						}

						if (instance._tagPanelMerge) {
							instance._tagPanelMerge.hide();
						}
					},

					_loadData: function() {
						var instance = this;

						instance._displayTags(
							function() {
								instance._displayTagData();
							}
						);
					},

					_loadPermissions: function(url) {
						var instance = this;

						var panelPermissionsChange = instance._panelPermissionsChange;

						if (!instance._panelPermissionsChange) {
							panelPermissionsChange = instance._createTagPanelPermissions();
						}

						panelPermissionsChange.show();

						panelPermissionsChange.iframe.set('uri', url);

						panelPermissionsChange._syncUIPosAlign();

						if (instance._tagPanelEdit) {
							var zIndex = parseInt(instance._tagPanelEdit.get('zIndex'), 10) + 2;

							panelPermissionsChange.set('zIndex', zIndex);
						}
					},

					_merge: function(node, dropNode) {
						var instance = this;

						var fromTagId = instance._getTagId(node);
						var fromTagName = instance._getTagName(node);
						var toTagId = instance._getTagId(dropNode);
						var toTagName = instance._getTagName(dropNode);

						var mergeText = Liferay.Language.get('are-you-sure-you-want-to-merge-x-into-x');

						mergeText = Lang.sub(mergeText, [fromTagName, toTagName]);

						if (confirm(mergeText)) {
							instance._mergeTag(
								fromTagId,
								toTagId,
								function() {
									node.remove();

									instance._selectTag(toTagId);
								}
							);
						}
					},

					_mergeSelectedTags: function(event) {
						var instance = this;

						var selectedTagsNodes = A.all('.tag-item-check:checked');

						if (selectedTagsNodes.size() > 1) {
							var checkedItemsIds = selectedTagsNodes.attr('data-tagId');
							var checkedItemsName = selectedTagsNodes.attr('data-tagName');

							var tagPanelMerge = instance._getTagPanelMerge();

							var selectedTagsList = instance._selectedTagsList;
							var targetTagsList = instance._targetTagsList;

							selectedTagsList.empty();
							targetTagsList.empty();

							selectedTagsNodes.each(
								function(item, index, collection) {
									var name = checkedItemsName[index];
									var value = checkedItemsIds[index];

									var listItem = Lang.sub(
										TPL_TAG_MERGE_ITEM,
										{
											name: name,
											title: name,
											value: value
										}
									);

									selectedTagsList.append(listItem);
									targetTagsList.append(listItem);
								}
							);

							targetTagsList.attr('selectedIndex', 0);

							instance._updateMergeItemsTarget();

							if (selectedTagsNodes.size() > MAX_DISPLAY_ITEMS) {
								selectedTagsList.attr('size', MAX_DISPLAY_ITEMS);
							}
							else {
								selectedTagsList.removeAttribute('size');
							}

							tagPanelMerge.show();
						}
						else {
							var errorMessage = Lang.sub(Liferay.Language.get('please-choose-at-least-x-tags'), [2]);

							alert(errorMessage);
						}
					},

					_mergeTags: function(fromIds, toId, overrideProperties, callback) {
						Liferay.Service(
							'/assettag/merge-tags',
							{
								fromTagIds: fromIds,
								toTagId: toId,
								overrideProperties: overrideProperties
							},
							callback
						);
					},

					_mergeTag: function(fromId, toId, callback) {
						Liferay.Service(
							'/assettag/merge-tags',
							{
								fromTagId: fromId,
								toTagId: toId,
								overrideProperties: true
							},
							callback
						);
					},

					_onDeleteTag: function(event) {
						var instance = this;

						if (confirm(Liferay.Language.get('are-you-sure-you-want-to-delete-this-tag'))) {
							instance._deleteTag(
								instance._selectedTagId,
								A.bind('_processActionResult', instance)
							);
						}
					},

					_onSearchInputKeyDown: function(event) {
						if (event.isKey('ENTER')) {
							event.halt();
						}
					},

					_onShowTagPanel: function(event, action) {
						var instance = this;

						instance._hidePanels();

						instance._showTagPanel(action);
					},

					_onStateChange: function(event) {
						var instance = this;

						var changed = event.changed;
						var removed = event.removed;

						var paginationState = {};

						var paginationMap = instance._getTagsPaginationMap();

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

						if (AObject.size(paginationState)) {
							instance._tagsPagination.setState(paginationState);

							instance._reloadData();
						}
					},

					_onTagChangePermissions: function(event) {
						var instance = this;

						var url = event.target.attr('data-url') || event.currentTarget.attr('data-url');

						instance._loadPermissions(url);
					},

					_onTagFormSubmit: function(event, form) {
						var instance = this;

						event.halt();

						Liferay.fire(
							'saveAutoFields',
							{
								form: form
							}
						);

						instance._updateTag(form);
					},

					_onTagsListClick: function(event) {
						var instance = this;

						instance._onTagsListSelect(event);

						var target = event.target;

						if (target.hasClass('tag-item-check')) {
							Liferay.Util.checkAllBox(event.currentTarget, 'tag-item-check', '#' + instance._prefixedPortletId + 'checkAllTagsCheckbox');
						}
						else if (target.hasClass('tag-item-actions-trigger')) {
							instance._onShowTagPanel(event, ACTION_EDIT);
						}
					},

					_onTagsListSelect: function(event) {
						var instance = this;

						var tagId = instance._getTagId(event.target);

						instance._selectTag(tagId);
					},

					_onTagMergeClick: function(event) {
						var instance = this;

						var selectedList = instance._selectedTagsList;

						var namespace = instance._prefixedPortletId;

						var mergeOnlySelected = A.one('#' + namespace + 'mergeOnlySelectedTags').get('checked');

						var tags = selectedList.all(mergeOnlySelected ? ':selected' : 'option');

						if (tags.size() > 0) {
							var targetTag = instance._targetTagsList.one(':selected');

							var targetTagName = targetTag.text();

							var mergeText = Liferay.Language.get('are-you-sure-you-want-to-merge-the-chosen-tags-into-x');

							mergeText = Lang.sub(mergeText, [targetTagName]);

							if (confirm(mergeText)) {
								var tagsIds = tags.val();
								var targetTagId = targetTag.val();

								var overrideProperties = A.one('#' + namespace + 'overrideProperties').attr('checked');

								instance._mergeTags(
									tagsIds,
									targetTagId,
									overrideProperties,
									A.bind('_processActionResult', instance)
								);
							}
						}
						else {
							alert(Liferay.Language.get('there-are-no-selected-tags'));
						}
					},

					_onTagUpdateFailure: function(response) {
						var instance = this;

						instance._sendMessage(MESSAGE_TYPE_ERROR, Liferay.Language.get('your-request-failed-to-complete'));
					},

					_onTagUpdateSuccess: function(response) {
						var instance = this;

						instance._hideAllMessages();

						var exception = response.exception;

						if (!response.exception) {
							instance._sendMessage(MESSAGE_TYPE_SUCCESS, Liferay.Language.get('your-request-processed-successfully'));

							instance._displayTags(
								function() {
									instance._unselectAllTags();
									instance._selectTag(response.tagId);
								}
							);

							instance._hidePanels();
						}
						else {
							var errorText;

							var autoHide = true;

							if (exception.indexOf('DuplicateTagException') > -1) {
								errorText = Liferay.Language.get('that-tag-already-exists');
							}
							else if ((exception.indexOf('AssetTagException') > -1)) {
								errorText = Lang.sub(
									Liferay.Language.get('tag-names-cannot-be-empty-string-or-contain-characters-such-as-x'),
									['<br />' + exception.substr(exception.lastIndexOf(':') + 1)]
								);

								autoHide = false;
							}
							else if (exception.indexOf('auth.PrincipalException') > -1) {
								errorText = Liferay.Language.get('you-do-not-have-permission-to-access-the-requested-resource');
							}
							else {
								errorText = Liferay.Language.get('your-request-failed-to-complete');
							}

							instance._sendMessage(MESSAGE_TYPE_ERROR, errorText, autoHide);
						}
					},

					_onTagViewContainerClick: function(event) {
						var instance = this;

						var targetId = event.target.get('id');

						if (targetId == 'editTagButton') {
							instance._onShowTagPanel(event, ACTION_EDIT);
						}
						else if (targetId == 'deleteTagButton') {
							instance._onDeleteTag(event);
						}
						else if (targetId == 'updateTagPermissions') {
							instance._onTagChangePermissions(event);
						}
					},

					_onTagViewFailure: function() {
						var instance = this;

						instance._sendMessage(MESSAGE_TYPE_ERROR, Liferay.Language.get('your-request-failed-to-complete'));
					},

					_onTagViewSuccess: function(response) {
						var instance = this;

						instance._tagViewContainer.html(response);
					},

					_prepareTags: function(tags, callback) {
						var instance = this;

						var selectedTagId;
						var selectedTagName;

						if (tags.length > 0) {
							var buffer = [TPL_TAG_LIST_CONTAINER];

							instance._tags = tags;

							A.each(
								tags,
								function(item, index, collection) {
									if (index === 0) {
										item.cssClassSelected = 'active';
									}
									else {
										item.cssClassSelected = '';
									}

									buffer.push(Lang.sub(TPL_TAG_LIST, item));
								}
							);

							buffer.push('</ul>');

							instance._tagsList.html(buffer.join(''));

							var firstTag = A.one(instance._tagsItemsSelector);
							var tagName = instance._getTagName(firstTag);
							var tagId = instance._getTagId(firstTag);

							selectedTagId = tagId;
							selectedTagName = tagName;
						}
						else {
							var tagsMessageContainer = instance._tagsMessageContainer;

							tagsMessageContainer.html(Liferay.Language.get('there-are-no-tags'));

							instance._tagsList.setContent(tagsMessageContainer);

							tagsMessageContainer.show();

							selectedTagId = null;
							selectedTagName = null;
						}

						instance._selectedTagId = selectedTagId;
						instance._selectedTagName = selectedTagName;

						instance._getDDHandler().syncTargets();

						if (callback) {
							callback();
						}
					},

					_processActionResult: function(result) {
						var instance = this;

						var exception = result.exception;

						if (!exception) {
							instance._sendMessage(MESSAGE_TYPE_SUCCESS, Liferay.Language.get('your-request-processed-successfully'));

							instance._hidePanels();
							instance._loadData();
						}
						else {
							var errorText;

							if (exception.indexOf('auth.PrincipalException') > -1) {
								errorText = Liferay.Language.get('you-do-not-have-permission-to-access-the-requested-resource');
							}
							else {
								errorText = Liferay.Language.get('your-request-failed-to-complete');
							}

							instance._sendMessage(MESSAGE_TYPE_ERROR, errorText);
						}
					},

					_reloadData: function() {
						var instance = this;

						instance._checkAllTagsCheckbox.attr('checked', false);

						instance._displayTags();
					},

					_resetTagsProperties: function(event) {
						var instance = this;

						var contextPanel = event.currentTarget;
						var boundingBox = contextPanel.get('boundingBox');

						var namespace = instance._prefixedPortletId;

						var propertiesTrigger = boundingBox.one('fieldset#' + namespace + 'tagProperties');

						var autoFieldsInstance = propertiesTrigger.getData('autoFieldsInstance');

						autoFieldsInstance.reset();
					},

					_selectTag: function(tagId) {
						var instance = this;

						var tag = instance._getTag(tagId);

						if (tag) {
							var tagName = instance._getTagName(tag);

							if (tag.hasClass('active')) {
								return tag;
							}

							instance._hideAllMessages();
							instance._selectedTagName = tagName;
							instance._selectedTagId = tagId;

							instance._unselectAllTags();

							tag.addClass('active');

							instance._displayTagData();
						}

						return tag;
					},

					_sendMessage: function(type, message, autoHide) {
						var instance = this;

						var output = instance._portletMessageContainer;

						output.removeClass('alert-error').removeClass('alert-success');
						output.addClass('alert alert-' + type);
						output.html(message);

						output.show();

						if (autoHide !== false) {
							instance._hideMessageTask();
						}
					},

					_showTagPanel: function(action) {
						var instance = this;

						if (action == ACTION_ADD) {
							instance._showTagPanelAdd();
						}
						else if (action == ACTION_EDIT) {
							instance._showTagPanelEdit();
						}
						else {
							throw 'Internal error. No action specified.';
						}
					},

					_showTagPanelAdd: function() {
						var instance = this;

						var tagPanelAdd = instance._tagPanelAdd;

						if (!tagPanelAdd) {
							tagPanelAdd = instance._createTagPanelAdd();

							var tagURL = instance._createURL(ACTION_ADD, LIFECYCLE_RENDER);

							tagPanelAdd.show();

							tagPanelAdd._syncUIPosAlign();

							var afterSuccess = A.bind(
								'_initializeTagPanelAdd',
								instance,
								function() {
									instance._focusTagPanelAdd();
								}
							);

							tagPanelAdd.plug(
								A.Plugin.IO,
								{
									uri: tagURL.toString(),
									after: {
										success: afterSuccess
									}
								}
							);
						}
						else {
							tagPanelAdd.show();

							tagPanelAdd._syncUIPosAlign();

							instance._focusTagPanelAdd();
						}
					},

					_showTagPanelEdit: function() {
						var instance = this;

						var forceStart = false;
						var tagPanelEdit = instance._tagPanelEdit;

						if (!tagPanelEdit) {
							tagPanelEdit = instance._createTagPanelEdit();
						}
						else {
							forceStart = true;

							instance._currentPanelEditIOHandle.detach();
						}

						var tagEditURL = instance._createURL(ACTION_EDIT, LIFECYCLE_RENDER);

						tagPanelEdit.show();

						tagPanelEdit._syncUIPosAlign();

						tagPanelEdit.plug(
							A.Plugin.IO,
							{
								uri: tagEditURL.toString()
							}
						);

						instance._currentPanelEditIOHandle = tagPanelEdit.io.after('success', instance._initializeTagPanelEdit, instance);

						if (forceStart) {
							tagPanelEdit.io.start();
						}
					},

					_updateMergeItemsTarget: function() {
						var instance = this;

						var selectedTagsList = instance._selectedTagsList;
						var targetTagsList = instance._targetTagsList;

						var selectetTargeTagIndex = targetTagsList.get('selectedIndex');

						var targetTag = targetTagsList.get('options').item(selectetTargeTagIndex);

						var targetTagId = targetTag.val();

						var previousTagData = instance._previousTagData;

						if (previousTagData) {
							var previousTag = previousTagData.tagNode;
							var previousTagNextSibling = previousTagData.nextSibling;
							var previousTagPrevSibling = previousTagData.previousSibling;

							if (previousTagNextSibling) {
								previousTagNextSibling.placeBefore(previousTag);
							}
							else if (previousTagPrevSibling) {
								previousTagPrevSibling.placeAfter(previousTag);
							}
							else {
								selectedTagsList.append(previousTag);
							}

							previousTagData = null;
						}

						var selectedTag = selectedTagsList.one('[value=' + targetTagId + ']');

						if (selectedTag) {
							previousTagData = {
								tagNode: selectedTag,
								nextSibling: selectedTag.next(),
								previousSibling: selectedTag.previous()
							};

							selectedTag.remove();

							instance._previousTagData = previousTagData;
						}
					},

					_unselectAllTags: function() {
						var instance = this;

						A.all(instance._tagsItemsSelector).removeClass('active');
					},

					_updateTag: function(form) {
						var instance = this;

						var ioTag = instance._getIOTagUpdate();

						ioTag.set('form', form.getDOM());
						ioTag.set('uri', form.attr('action'));

						ioTag.start();
					},

					_tagsItemsSelector: '.tags-admin-list li'
				}
			}
		);

		var TagsSearch = A.Component.create(
			{
				AUGMENTS: [A.AutoCompleteBase],
				EXTENDS: A.Base,
				NAME: 'tagssearch',
				prototype: {
					initializer: function() {
						this._bindUIACBase();
						this._syncUIACBase();
					}
				}
			}
		);

		Liferay.Portlet.AssetTagsAdmin = AssetTagsAdmin;
	},
	'',
	{
		requires: ['aui-button', 'aui-dialog-iframe-deprecated', 'aui-io-plugin-deprecated', 'aui-loading-mask-deprecated', 'aui-pagination', 'autocomplete-base', 'aui-tree-view', 'dd', 'json', 'liferay-history-manager', 'liferay-portlet-url', 'liferay-util-window']
	}
);