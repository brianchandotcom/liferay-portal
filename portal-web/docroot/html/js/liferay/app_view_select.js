AUI.add(
	'liferay-app-view-select',
	function(A) {
		var AArray = A.Array;
		var Lang = A.Lang;
		var History = Liferay.HistoryManager;
		var Util = Liferay.Util;

		var WIN = A.config.win;

		var ATTR_CHECKED = 'checked';

		var CSS_RESULT_ROW = 'tr.selectable';

		var CSS_SELECTED = 'selected';

		var DATA_FOLDER_ID = 'data-folder-id';

		var DATA_REPOSITORY_ID = 'data-repository-id';

		var DISPLAY_STYLE_BUTTON_GROUP = 'displayStyleButtonGroup';

		var DISPLAY_STYLE_LIST = 'list';

		var DISPLAY_STYLE_TOOLBAR = 'displayStyleToolbar';

		var STR_ACTIVE = 'active';

		var STR_CLICK = 'click';

		var STR_DISPLAY_STYLE = 'displayStyle';

		var STR_DOT = '.';

		var STR_FOCUS = 'focus';

		var STR_TOGGLE_ACTIONS_BUTTON = 'toggleActionsButton';

		var AppViewSelect = A.Component.create(
			{
				ATTRS: {
					checkBoxesId: {
						validator: Lang.isArray
					},

					displayStyle: {
						validator: Lang.isString
					},

					displayStyleCSSClass: {
						validator: Lang.isString
					},

					displayStyleToolbar: {
						setter: A.one
					},

					displayViews: {
						validator: Lang.isObject
					},

					folderContainer: {
						setter: A.one
					},

					portletContainerId: {
						validator: Lang.isString
					},

					repositories: {
						validator: Lang.isArray
					},

					selectedFolder: {
						getter: '_getSelectedFolder',
						readOnly: true
					},

					selector: {
						validator: Lang.isString
					}
				},

				AUGMENTS: [Liferay.PortletBase],

				EXTENDS: A.Base,

				NAME: 'liferay-app-view-select',

				prototype: {
					initializer: function(config) {
						var instance = this;

						instance._portletContainer = instance.byId(instance.get('portletContainerId'));

						instance._displayStyle = instance.ns(STR_DISPLAY_STYLE);

						instance._displayStyleToolbar = instance.get(DISPLAY_STYLE_TOOLBAR);

						instance._entriesContainer = instance.byId('entriesContainer');

						instance._selectAllCheckbox = instance.byId('allRowIds');

						instance._folderContainer = instance.get('folderContainer');

						instance._selector = instance.get('selector');

						instance._checkBoxesId = instance.get('checkBoxesId');

						instance._displayStyleCSSClass = instance.get('displayStyleCSSClass');

						instance._eventHandles = [
							Liferay.on('liferay-app-view-folders:dataRequest', instance._onDataRequest, instance),
							Liferay.on(instance.ns('dataProcessed'), instance._updateSelectedEntriesStatus, instance),
							Liferay.on('liferay-app-view-move:dragStart', instance._onDragStart, instance)
						];

						instance._initHover();

						if (themeDisplay.isSignedIn()) {
							if (instance._selectAllCheckbox) {
								instance._initSelectAllCheckbox();
							}

							instance._initToggleSelect();
						}
					},

					destructor: function() {
						var instance = this;

						A.Array.invoke(instance._eventHandles, 'detach');
					},

					syncDisplayStyleToolbar: function() {
						var instance = this;

						var displayViews = instance.get('displayViews');

						var length = displayViews.length;

						if (length > 1) {
							var displayStyleButtonGroup = instance._displayStyleToolbar.getData(DISPLAY_STYLE_BUTTON_GROUP);

							if (displayStyleButtonGroup) {
								var displayStyle = instance._getDisplayStyle(instance._displayStyle);

								var selectedIndex = AArray.indexOf(displayViews, displayStyle);

								displayStyleButtonGroup.select(selectedIndex);
							}
						}
					},

					_getDisplayStyle: function(currentDisplayStyle, style) {
						var instance = this;

						var displayStyle = History.get(currentDisplayStyle) || instance.get(STR_DISPLAY_STYLE);

						if (style) {
							displayStyle = (displayStyle == style);
						}

						return displayStyle;
					},

					_getSelectedFolder: function() {
						var instance = this;

						var selectedFolderNode = instance._folderContainer.one('.active .browse-folder');

						var selectedFolderId = 0;
						var repositoryId = 0;

						if (selectedFolderNode) {
							selectedFolderId = selectedFolderNode.attr(DATA_FOLDER_ID);

							repositoryId = selectedFolderNode.attr(DATA_REPOSITORY_ID);

							if (!repositoryId) {
								var repositories = instance.get('repositories');

								if (repositories) {
									repositoryId = repositories[0].id;
								}
							}
						}

						return {
							id: selectedFolderId,
							repositoryId: repositoryId
						};
					},

					_initHover: function() {
						var instance = this;

						instance._eventHandles.push(
							instance._entriesContainer.on([STR_FOCUS, 'blur'], instance._toggleHovered, instance)
						);
					},

					_initSelectAllCheckbox: function() {
						var instance = this;

						instance._eventHandles.push(
							instance._selectAllCheckbox.on(STR_CLICK, instance._toggleEntriesSelection, instance)
						);
					},

					_initToggleSelect: function() {
						var instance = this;

						instance._eventHandles.push(
							instance._entriesContainer.delegate(
								'change',
								instance._onEntrySelectorChange,
								STR_DOT + instance._selector,
								instance
							)
						);
					},

					_onDataRequest: function(event) {
						var instance = this;

						var entriesSelector = STR_DOT + instance._displayStyleCSSClass + '.selected' + ' :checkbox';

						if (instance._getDisplayStyle(instance._displayStyle, DISPLAY_STYLE_LIST)) {
							entriesSelector = 'td > :checkbox:checked';
						}

						var selectedEntries = instance._entriesContainer.all(entriesSelector);

						if (selectedEntries.size()) {
							instance._selectedEntries = selectedEntries.val();
						}
					},

					_onDragStart: function(event) {
						var instance = this;

						var node = event.node;

						if (!node.hasClass(CSS_SELECTED)) {
							instance._unselectAllEntries();

							instance._toggleSelected(node);
						}
					},

					_onEntrySelectorChange: function(event) {
						var instance = this;

						instance._toggleSelected(event.currentTarget, true);

						WIN[instance.ns(STR_TOGGLE_ACTIONS_BUTTON)]();

						Util.checkAllBox(
							instance._entriesContainer,
							instance._checkBoxesId,
							instance._selectAllCheckbox
						);
					},

					_toggleEntriesSelection: function() {
						var instance = this;

						var selectAllCheckbox = instance._selectAllCheckbox;

						for (var i = 0; i < instance._checkBoxesId.length; i++) {
							Util.checkAll(instance._portletContainer, instance._checkBoxesId[i], selectAllCheckbox, CSS_RESULT_ROW);
						}

						WIN[instance.ns(STR_TOGGLE_ACTIONS_BUTTON)]();

						if (!instance._getDisplayStyle(instance._displayStyle, DISPLAY_STYLE_LIST)) {
							var articleDisplayStyle = A.all(STR_DOT + instance._displayStyleCSSClass + '.selectable');

							articleDisplayStyle.toggleClass(CSS_SELECTED, instance._selectAllCheckbox.attr(ATTR_CHECKED));
						}
					},

					_toggleHovered: function(event) {
						var instance = this;

						if (!instance._getDisplayStyle(instance._displayStyle, DISPLAY_STYLE_LIST)) {
							var articleDisplayStyle = event.target.ancestor(STR_DOT + instance._displayStyleCSSClass);

							if (articleDisplayStyle) {
								articleDisplayStyle.toggleClass('hover', (event.type == STR_FOCUS));
							}
						}
					},

					_toggleSelected: function(node, preventUpdate) {
						var instance = this;

						if (instance._getDisplayStyle(instance._displayStyle, DISPLAY_STYLE_LIST)) {
							if (!preventUpdate) {
								var input = node.one('input') || node;

								input.attr(ATTR_CHECKED, !node.attr(ATTR_CHECKED));
							}
						}
						else {
							node = node.ancestor(STR_DOT + instance._displayStyleCSSClass) || node;

							if (!preventUpdate) {
								var selectElement = node.one(STR_DOT + instance._selector);

								selectElement.attr(ATTR_CHECKED, !selectElement.attr(ATTR_CHECKED));
							}
						}

						node.toggleClass(CSS_SELECTED);
					},

					_unselectAllEntries: function() {
						var instance = this;

						instance._selectAllCheckbox.attr(CSS_SELECTED, false);

						instance._toggleEntriesSelection();
					},

					_updateSelectedEntriesStatus: function() {
						var instance = this;

						var selectedEntries = instance._selectedEntries;

						if (selectedEntries && selectedEntries.length) {
							var entriesContainer = instance._entriesContainer;

							A.each(
								selectedEntries,
								function(item, index) {
									var entry = entriesContainer.one('input[value="' + item + '"]');

									if (entry) {
										instance._toggleSelected(entry);
									}
								}
							);

							selectedEntries.length = 0;

							Util.checkAllBox(
								instance._entriesContainer,
								instance._checkBoxesId,
								instance._selectAllCheckbox
							);
						}
					}
				}
			}
		);

		Liferay.AppViewSelect = AppViewSelect;
	},
	'',
	{
		requires: ['liferay-app-view-move', 'liferay-history-manager', 'liferay-portlet-base', 'liferay-util-list-fields']
	}
);