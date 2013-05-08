AUI.add(
	'liferay-app-view-folders',
	function(A) {
		var AObject = A.Object;
		var History = Liferay.HistoryManager;
		var Lang = A.Lang;

		var formatSelectorNS = A.Node.formatSelectorNS;

		var owns = AObject.owns;

		var CSS_SELECTED = 'selected';

		var DATA_DIRECTION_RIGHT = 'data-direction-right';

		var DATA_FOLDER_ID = 'data-folder-id';

		var DATA_VIEW_ENTRIES = 'data-view-entries';

		var DATA_VIEW_FOLDERS = 'data-view-folders';

		var EXPAND_FOLDER = 'expandFolder';

		var MESSAGE_TYPE_ERROR = 'error';

		var PARAM_DISPLAY_STYLE = 'displayStyle';

		var PARAM_STRUTS_ACTION = 'struts_action';

		var SEARCH_REPOSITORY_ID = 'searchRepositoryId';

		var SRC_HISTORY = 2;

		var SRC_RESTORE_STATE = 4;

		var SRC_SEARCH = 3;

		var STR_AJAX_REQUEST = 'ajax';

		var STR_CLICK = 'click';

		var STR_DATA = 'data';

		var STR_DISPLAY_STYLE = 'displayStyle';

		var STR_FOLDER_CONTAINER = 'folderContainer';

		var STR_FOLDER_ID = 'folderId';

		var STR_RIGHT = 'right';

		var STR_STRUTS_ACTION = 'strutsAction';

		var STR_SUCCESS = 'success';

		var STR_TOGGLE_ACTIONS_BUTTON = 'toggleActionsButton';

		var TPL_MESSAGE_RESPONSE = '<div class="lfr-message-response" />';

		var VIEW_ENTRIES = 'viewEntries';

		var VIEW_FOLDERS = 'viewFolders';

		var WIN = A.config.win;

		var AppViewFolders = A.Component.create(
			{
				ATTRS: {
					defaultParams: {
						validator: Lang.isObject
					},

					defaultParentFolderId: {
						validator: Lang.isString
					},

					displayStyleToolbar: {
						setter: A.one,
						value: null
					},

					entry: {
						validator: '_validateEntry',
						value: {
							paramName: 'fileEntryTypeId',
							typeId: 'data-file-entry-type-id'
						}
					},

					folderContainer: {
						validator: '_validateFolderContainer',
						valueFn: '_valueFolderContainer'
					},

					listViewConfig: {
						validator: Lang.isObject,
						valueFn: '_valueListView'
					},

					mainUrl: {
						validator: Lang.isString
					},

					portletContainerId: {
						validator: Lang.isString
					},

					strutsAction: {
						validator: Lang.isString
					}
				},

				AUGMENTS: [Liferay.PortletBase],

				EXTENDS: A.Base,

				NAME: 'liferay-app-view-folders',

				prototype: {
					initializer: function(config) {
						var instance = this;

						var portletContainer = instance.byId(instance.get('portletContainerId'));

						instance._config = config;

						instance._dataRetrieveFailure = instance.ns('dataRetrieveFailure');
						instance._eventDataRequest = instance.ns('dataRequest');
						instance._eventDataRetrieveSuccess = instance.ns('dataRetrieveSuccess');

						var listViewConfig = instance.get('listViewConfig');

						instance._listView = new Liferay.ListView(listViewConfig).render();

						instance._portletContainer = portletContainer;

						instance._displayStyle = instance.ns(PARAM_DISPLAY_STYLE);
						instance._folderId = instance.ns(STR_FOLDER_ID);

						instance._displayStyleToolbar = instance.get('displayStyleToolbar');

						instance._portletMessageContainer = A.Node.create(TPL_MESSAGE_RESPONSE);

						instance._entriesContainer = instance.byId('entriesContainer');

						instance._eventPageLoaded = instance.ns('pageLoaded');

						instance._repositoriesData = {};

						var eventHandles = [
							Liferay.after(instance._eventDataRequest, A.bind('_afterDataRequest', instance)),
							Liferay.on(instance._dataRetrieveFailure, A.bind('_onDataRetrieveFailure', instance)),
							Liferay.on(instance._eventDataRequest, A.bind('_onDataRequest', instance))
						];

						instance._eventHandles = eventHandles;

						portletContainer.delegate(
							STR_CLICK,
							A.bind('_onPortletContainerClick', instance),
							formatSelectorNS(instance.NS, '#entriesContainer a[data-folder=true], #breadcrumbContainer a')
						);

						portletContainer.plug(A.LoadingMask);

						instance._listView.after('itemChange', instance._afterListViewItemChange, instance);

						instance._restoreState();
					},

					destructor: function() {
						var instance = this;

						A.Array.invoke(instance._eventHandles, 'detach');
					},

					displayMessage: function(type, message) {
						var instance = this;

						var output = instance._portletMessageContainer;

						output.removeClass('alert-error').removeClass('alert-success');

						output.addClass('alert alert-' + type);

						output.html(message);

						output.show();

						instance._entriesContainer.setContent(output);
					},

					processData: function(data) {
						var instance = this;

						instance._setBreadcrumb(data);
						instance._setButtons(data);
						instance._setEntries(data);
						instance._setFolders(data);
						instance._setParentTitle(data);

						instance._parseContent(data);

						WIN[instance.ns(STR_TOGGLE_ACTIONS_BUTTON)]();
					},

					_addHistoryState: function(data) {
						var instance = this;

						var historyState = A.clone(data);

						var currentHistoryState = History.get();

						var defaultParams = instance.get('defaultParams');

						AObject.each(
							currentHistoryState,
							function(index, item, collection) {
								if (!owns(historyState, item) && !owns(defaultParams, item)) {
									historyState[item] = null;
								}
							}
						);

						if (!AObject.isEmpty(historyState)) {
							History.add(historyState);
						}
					},

					_afterDataRequest: function(event) {
						var instance = this;

						var requestParams = event.requestParams;

						var data = {};

						var displayStyle = instance._displayStyle;

						data[instance._folderId] = instance.get('defaultParentFolderId');

						data[displayStyle] = History.get(displayStyle) || instance.get(STR_DISPLAY_STYLE);

						data[instance.ns(VIEW_ENTRIES)] = true;

						data[instance.ns(VIEW_FOLDERS)] = true;

						A.mix(data, requestParams, true);

						var src = event.src;

						if (src !== SRC_HISTORY) {
							instance._addHistoryState(data);
						}

						if (src !== SRC_RESTORE_STATE) {
							data[STR_AJAX_REQUEST] = true;
						}

						var ioRequest = A.io.request(
							instance.get('mainUrl'),
							{
								autoLoad: false,
								method: 'get'
							}
						);

						var sendIOResponse = A.bind('_sendIOResponse', instance, ioRequest);

						ioRequest.after(['failure', STR_SUCCESS], sendIOResponse);

						ioRequest.set(STR_DATA, data);

						if (src === SRC_SEARCH) {
							var repositoryId = event.requestParams[instance.NS + SEARCH_REPOSITORY_ID];

							var repositoriesData = instance._repositoriesData;

							var repositoryData = repositoriesData[repositoryId];

							if (!repositoryData) {
								repositoryData = {};

								repositoriesData[repositoryId] = repositoryData;
							}

							repositoryData.dataRequest = data;
						}
						else {
							instance._portletContainer.loadingmask.show();
						}

						ioRequest.start();

						delete data[STR_AJAX_REQUEST];

						instance._lastDataRequest = data;

						Liferay.fire(
							'liferay-app-view-folders:afterDataRequest',
							{
								data: data
							}
						);
					},

					_afterListViewItemChange: function(event) {
						var instance = this;

						var selFolder = A.one('.folder.selected');

						if (selFolder) {
							selFolder.removeClass(CSS_SELECTED);
						}

						var item = event.newVal;

						item.ancestor('.folder').addClass(CSS_SELECTED);

						var entryConfig = instance.get('entry');

						var dataExpandFolder = item.attr('data-expand-folder');
						var dataStructureId = item.attr(entryConfig.typeId);
						var dataFolderId = item.attr(DATA_FOLDER_ID);
						var dataNavigation = item.attr('data-navigation');
						var dataViewEntries = item.attr(DATA_VIEW_ENTRIES);
						var dataViewFolders = item.attr(DATA_VIEW_FOLDERS);

						var direction = 'left';

						if (item.attr(DATA_DIRECTION_RIGHT)) {
							direction = STR_RIGHT;
						}

						instance._listView.set('direction', direction);

						var requestParams = {};

						requestParams[instance.ns(PARAM_STRUTS_ACTION)] = instance.get(STR_STRUTS_ACTION);

						if (dataExpandFolder) {
							requestParams[instance.ns(EXPAND_FOLDER)] = dataExpandFolder;
						}

						if (dataFolderId) {
							requestParams[instance._folderId] = dataFolderId;
						}

						if (dataNavigation) {
							requestParams[instance.ns('navigation')] = dataNavigation;
						}

						if (dataViewEntries) {
							requestParams[instance.ns(VIEW_ENTRIES)] = dataViewEntries;
						}

						if (dataStructureId) {
							requestParams[instance.ns(entryConfig.paramName)] = dataStructureId;
						}

						if (dataViewFolders) {
							requestParams[instance.ns(VIEW_FOLDERS)] = dataViewFolders;
						}

						Liferay.fire(
							instance._eventDataRequest,
							{
								requestParams: requestParams,
								resetPagination: true
							}
						);
					},

					_onDataRequest: function(event) {
						var instance = this;

						instance._processDefaultParams(event);

						Liferay.fire(
							'liferay-app-view-folders:dataRequest',
							{
								requestParams: event.requestParams,
								resetPagination: event.resetPagination,
								src: event.src
							}
						);
					},

					_onDataRetrieveFailure: function(event) {
						var instance = this;

						instance._portletContainer.loadingmask.hide();

						instance.displayMessage(MESSAGE_TYPE_ERROR, Liferay.Language.get('your-request-failed-to-complete'));
					},

					_onPortletContainerClick: function(event) {
						var instance = this;

						event.preventDefault();

						var requestParams = {};

						requestParams[instance.ns(PARAM_STRUTS_ACTION)] = instance.get(STR_STRUTS_ACTION);
						requestParams[instance.ns('action')] = 'browseFolder';
						requestParams[instance._folderId] = event.currentTarget.attr(DATA_FOLDER_ID);
						requestParams[instance.ns(EXPAND_FOLDER)] = false;

						var viewEntries = event.currentTarget.attr(DATA_VIEW_ENTRIES);

						if (viewEntries) {
							requestParams[instance.ns(VIEW_ENTRIES)] = viewEntries;
						}

						var viewFolders = event.currentTarget.attr(DATA_VIEW_FOLDERS);

						if (viewFolders) {
							requestParams[instance.ns(VIEW_FOLDERS)] = viewFolders;
						}

						var direction = 'left';

						if (event.currentTarget.attr(DATA_DIRECTION_RIGHT)) {
							direction = STR_RIGHT;
						}

						instance._listView.set('direction', direction);

						Liferay.fire(
							instance._eventDataRequest,
							{
								requestParams: requestParams,
								resetPagination: true
							}
						);
					},

					_parseContent: function(data) {
						var instance = this;

						var tmpNode = A.Node.create('<div></div>');

						tmpNode.plug(A.Plugin.ParseContent);

						tmpNode.ParseContent.parseContent(data);
					},

					_processDefaultParams: function(event) {
						var instance = this;

						var requestParams = event.requestParams;

						AObject.each(
							instance.get('defaultParams'),
							function(item, index, collection) {
								if (!Lang.isValue(History.get(index))) {
									requestParams[index] = item;
								}
							}
						);
					},

					_restoreState: function() {
						var instance = this;

						if (!History.HTML5) {
							var initialState = History.get();

							if (!AObject.isEmpty(initialState)) {
								var namespace = instance.NS;

								var requestParams = {};

								AObject.each(
									initialState,
									function(item, index, collection) {
										if (index.indexOf(namespace) === 0) {
											requestParams[index] = item;
										}
									}
								);

								Liferay.fire(
									instance._eventDataRequest,
									{
										requestParams: requestParams,
										src: SRC_RESTORE_STATE
									}
								);
							}
						}
					},

					_sendIOResponse: function(ioRequest, event) {
						var instance = this;

						var data = ioRequest.get(STR_DATA);

						var responseData = ioRequest.get('responseData');

						var eventType = instance._eventDataRetrieveSuccess;

						if (event.type.indexOf(STR_SUCCESS) == -1) {
							eventType = instance._dataRetrieveFailure;
						}

						Liferay.fire(
							eventType,
							{
								data: data,
								responseData: responseData
							}
						);
					},

					_setBreadcrumb: function(content) {
						var instance = this;

						var breadcrumb = instance.one('#breadcrumb', content);

						if (breadcrumb) {
							var breadcrumbContainer;

							var journalBreadcrumb = breadcrumb.one('.portlet-breadcrumb ul');

							if (journalBreadcrumb) {
								breadcrumbContainer = instance.byId('breadcrumbContainer');

								breadcrumbContainer.setContent(journalBreadcrumb);
							}

							var portalBreadcrumb = breadcrumb.one('.portal-breadcrumb ul');

							if (portalBreadcrumb) {
								breadcrumbContainer = A.one('#breadcrumbs ul');

								if (breadcrumbContainer) {
									breadcrumbContainer.setContent(portalBreadcrumb.html());
								}
							}
						}
					},

					_setButtons: function(content) {
						var instance = this;

						var addButton = instance.one('#addButton', content);

						if (addButton) {
							var addButtonContainer = instance.byId('addButtonContainer');

							addButtonContainer.setContent(addButton);
						}

						var displayStyleButtons = instance.one('#displayStyleButtons', content);

						if (displayStyleButtons) {
							instance._displayStyleToolbar.empty();

							var displayStyleButtonsContainer = instance.byId('displayStyleButtonsContainer');

							displayStyleButtonsContainer.setContent(displayStyleButtons);
						}

						var sortButton = instance.one('#sortButton', content);

						if (sortButton) {
							var sortButtonContainer = instance.byId('sortButtonContainer');

							sortButtonContainer.setContent(sortButton);
						}
					},

					_setEntries: function(content) {
						var instance = this;

						var entries = instance.one('#entries', content);

						if (entries) {
							var entriesContainer = instance._entriesContainer;

							entriesContainer.empty();

							entriesContainer.setContent(entries);

							Liferay.fire('liferay-app-view-folders:setEntries');
						}

						var addButtonContainer = instance.byId('addButtonContainer');

						addButtonContainer.show();

						var sortButtonContainer = instance.byId('sortButtonContainer');

						sortButtonContainer.show();
					},

					_setFolders: function(content) {
						var instance = this;

						var folders = instance.one('#folderContainer', content);

						if (folders) {
							var listViewDataContainer = A.one('.lfr-list-view-data-container');

							instance._listView.set(STR_DATA, folders.html());
						}
					},

					_setParentTitle: function(content) {
						var instance = this;

						var parentTitle = instance.one('#parentTitle', content);

						if (parentTitle) {
							var parentTitleContainer = instance.byId('parentTitleContainer');

							parentTitleContainer.setContent(parentTitle);
						}
					},

					_validateEntry: function(value) {
						return Lang.isObject(value) && Lang.isString(value.paramName) && Lang.isString(value.typeId);
					},

					_validateFolderContainer: function(value) {
						return (Lang.isString(value) || value instanceof A.Node);
					},

					_valueFolderContainer: function() {
						var instance = this;

						return instance.byId(STR_FOLDER_CONTAINER);
					},

					_valueListView: function() {
						var instance = this;

						var folderContainer = instance.get(STR_FOLDER_CONTAINER);

						return {
							boundingBox: formatSelectorNS(instance.NS, '#listViewContainer'),
							contentBox: folderContainer,
							cssClass: 'folder-display-style lfr-list-view-content',
							itemSelector: '.folder a.browse-folder, .folder a.expand-folder',
							srcNode: folderContainer
						};
					}
				}
			}
		);

		Liferay.AppViewFolders = AppViewFolders;
	},
	'',
	{
		requires: ['aui-base', 'aui-parse-content', 'liferay-app-view-move', 'liferay-history-manager', 'liferay-list-view', 'liferay-node', 'liferay-portlet-base']
	}
);