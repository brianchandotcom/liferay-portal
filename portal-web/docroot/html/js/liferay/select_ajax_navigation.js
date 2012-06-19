AUI.add(
	'liferay-select-ajax-navigation',
	function(A) {
		var History = Liferay.HistoryManager;

		var WIN = A.config.win;

		var ATTR_CHECKED = 'checked';

		var CSS_RESULT_ROW = '.results-row';

		var CSS_SELECTED = 'selected';

		var DATA_FOLDER_ID = 'data-folder-id';

		var DATA_REPOSITORY_ID = 'data-repository-id';

		var DISPLAY_STYLE_LIST = 'list';

		var STR_CLICK = 'click';

		var STR_FOCUS = 'focus';

		var STR_TOGGLE_ACTIONS_BUTTON = 'toggleActionsButton';

		var SelectAjaxNavigation = A.Component.create(
			{
				AUGMENTS: [Liferay.PortletBase],

				EXTENDS: A.Base,

				NAME: 'liferayselectajaxnavigation',

				prototype: {
					initializer: function(config) {
						var instance = this;

						var portletContainer = instance.byId(config.portletContainerId);

						instance._config = config;

						instance._portletContainer = portletContainer;

						instance._displayStyle = instance.ns('displayStyle');

						instance._entriesContainer = instance.byId('entriesContainer');

						instance._selectAllCheckbox = instance.byId('allRowIdsCheckbox');

						instance._folderContainer = config.folderContainer;

						instance._selector = config.selector;

						instance._checkBoxesId = config.checkBoxesId;

						instance._displayStyleCssClass = config.displayStyleCssClass;

						instance._initHover();

						if (themeDisplay.isSignedIn()) {
							instance._initSelectAllCheckbox();

							instance._initToggleSelect();
						}
					},

					destructor: function() {
						var instance = this;

						instance._ddHandler.destroy();

						A.Array.invoke(instance._eventHandles, 'detach');
					},

					_getDisplayStyle: function(currentDisplayStyle, style) {
						var instance = this;

						var displayStyle = History.get(currentDisplayStyle) || instance._config.displayStyle;

						if (style) {
							displayStyle = (displayStyle == style);
						}

						return displayStyle;
					},

					_getSelectedFolder: function() {
						var instance = this;

						var selectedFolderNode = instance._folderContainer.one('.selected .browse-folder');

						var selectedFolderId = 0;

						if (selectedFolderNode) {
							selectedFolderId = selectedFolderNode.attr(DATA_FOLDER_ID);
						}

						var repositoryId = selectedFolderNode.attr(DATA_REPOSITORY_ID);

						if (!repositoryId) {
							repositoryId = instance._config.repositories[0].id;
						}

						return {
							id: selectedFolderId,
							repositoryId: repositoryId
						};
					},

					_initHover: function() {
						var instance = this;

						instance._entriesContainer.on([STR_FOCUS, 'blur'], instance._toggleHovered, instance);
					},

					_initSelectAllCheckbox: function() {
						var instance = this;

						instance._selectAllCheckbox.on(STR_CLICK, instance._onSelectAllCheckboxChange, instance);
					},

					_initToggleSelect: function() {
						var instance = this;

						instance._entriesContainer.delegate(
							'change',
							instance._onEntrySelectorChange,
							'.' + instance._selector,
							instance
						);
					},

					_onEntrySelectorChange: function(event) {
						var instance = this;

						instance._toggleSelected(event.currentTarget, true);

						WIN[instance.ns(STR_TOGGLE_ACTIONS_BUTTON)]();

						Liferay.Util.checkAllBox(
							instance._entriesContainer,
							instance._checkBoxesId,
							instance._selectAllCheckbox
						);
					},

					_onSelectAllCheckboxChange: function() {
						var instance = this;

						instance._toggleEntriesSelection();
					},

					_setSelectedEntries: function(selectedEntries) {
						var instance = this;

						instance._selectedEntries = selectedEntries;
					},

					_toggleEntriesSelection: function() {
						var instance = this;

						var selectAllCheckbox = instance._selectAllCheckbox;

						for (var i = 0; i < instance._checkBoxesId.length; i++) {
							Liferay.Util.checkAll(instance._portletContainer, instance._checkBoxesId[i], selectAllCheckbox, CSS_RESULT_ROW);
						}

						WIN[instance.ns(STR_TOGGLE_ACTIONS_BUTTON)]();

						if (!instance._getDisplayStyle(instance._displayStyle, DISPLAY_STYLE_LIST)) {
							var articleDisplayStyle = A.all('.' + instance._displayStyleCssClass + '.selectable');

							articleDisplayStyle.toggleClass(CSS_SELECTED, instance._selectAllCheckbox.attr(ATTR_CHECKED));
						}
					},

					_toggleHovered: function(event) {
						var instance = this;

						if (!instance._getDisplayStyle(instance._displayStyle, DISPLAY_STYLE_LIST)) {
							var articleDisplayStyle = event.target.ancestor('.' + instance._displayStyleCssClass);

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
							node = node.ancestor('.' + instance._displayStyleCssClass) || node;

							if (!preventUpdate) {
								var selectElement = node.one('.' + instance._selector + ' :checkbox');

								selectElement.attr(ATTR_CHECKED, !selectElement.attr(ATTR_CHECKED));

								Liferay.Util.updateCheckboxValue(selectElement);
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
								function(item, index, collection) {
									var entry = entriesContainer.one('input[value="' + item + '"]');

									if (entry) {
										instance._toggleSelected(entry);
									}
								}
							);

							selectedEntries.length = 0;
						}
					}
				}
			}
		);

		Liferay.SelectAjaxNavigation = SelectAjaxNavigation;
	},
	'',
	{
		requires: ['aui-base', 'liferay-drag-and-drop-navigation', 'liferay-history-manager', 'liferay-portlet-base', 'liferay-util-list-fields']
	}
);