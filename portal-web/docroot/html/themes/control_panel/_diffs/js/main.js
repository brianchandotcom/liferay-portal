Liferay.Util.portletTitleEdit = function() {
};

if (!themeDisplay.isStatePopUp()) {
	AUI().ready('aui-live-search-deprecated', 'aui-overlay-context-panel-deprecated', 'event-mouseenter', 'liferay-message', 'liferay-store', 'node-focusmanager', 'transition',
		function(A) {
			var body = A.getBody();

			var portletId = '';
			var visible = false;

			var sessionKey = 'show-portlet-description-' + portletId;

			var trim = A.Lang.trim;

			var CSS_SEARCH_PANEL_ACTIVE = 'search-panel-active';

			var SELECTOR_SEARCH_NODES = '#controlPanelMenuAddContentPanelContainer ul li a';

			var TPL_CANCEL_SEARCH_BUTTON = '<a class="icon-remove cancel-search" href="javascript:;"></a>';

			var ControlPanel = {
				init: function() {
					var instance = this;

					instance._renderUI();
					instance._bindUI();
				},

				_bindUI: function() {
					var instance = this;

					if (instance._searchPanelHolder) {
						Liferay.publish(
							'focusSearchBar',
							{
								defaultFn: A.bind('_focusSearchBar', instance)
							}
						);

						A.getDoc().on(
							'keyup',
							function(event) {
								if (event.isKey('ESC')) {
									Liferay.fire('focusSearchBar');
								}
							}
						);
					}
				},

				_createCancelButton: function() {
					var instance = this;

					var cancelSearch = A.Node.create(TPL_CANCEL_SEARCH_BUTTON);

					instance._searchPanelHolder.append(cancelSearch);

					var searchNodes = A.all(SELECTOR_SEARCH_NODES);

					cancelSearch.on(
						'click',
						function(event) {
							body.removeClass(CSS_SEARCH_PANEL_ACTIVE);

							instance._searchPanelInput.val('').focus();

							searchNodes.show();

							instance._searchActive = false;
						}
					);
				},

				_createFocusManager: function() {
					var instance = this;

					var addContentPanelContainer = A.one('#controlPanelMenuAddContentPanelContainer');

					addContentPanelContainer.plug(
						A.Plugin.NodeFocusManager,
						{
							descendants: '.toggler-content ul li a:visible',
							keys: {
								next: 'down:40',
								previous: 'down:38'
							}
						}
					);

					var focusManager = addContentPanelContainer.focusManager;

					instance._refreshFocusManagerTask = A.debounce(focusManager.refresh, 50, focusManager);

					instance._searchPanelInput.on(
						'key',
						function(event) {
							focusManager.focus(0);
						},
						'down:40'
					);
				},

				_createLiveSearch: function() {
					var instance = this;

					var searchPanelInput = instance._searchPanelInput;

					var liveSearch = new A.LiveSearch(
						{
							input: searchPanelInput,
							nodes: SELECTOR_SEARCH_NODES,

							data: function(node) {
								return node.text();
							},

							on: {
								search: function(event) {
									if (trim(liveSearch.get('searchValue'))) {
										body.addClass(CSS_SEARCH_PANEL_ACTIVE);

										instance._searchActive = true;
									}
								}
							},

							after: {
								search: function(event) {
									instance._searchActive = true;

									instance._refreshFocusManagerTask();

									if (!trim(liveSearch.get('searchValue'))) {
										body.removeClass(CSS_SEARCH_PANEL_ACTIVE);

										instance._searchActive = false;
									}
								}
							}
						}
					);

					instance._liveSearch = liveSearch;
				},

				_focusSearchBar: function(event) {
					var instance = this;

					instance._searchPanelInput.selectText();
				},

				_renderUI: function() {
					var instance = this;

					var searchPanelHolder = A.one('.search-panels');

					if (searchPanelHolder) {
						var searchPanelInput = searchPanelHolder.one('#_160_searchPanel');

						searchPanelInput.attr('autocomplete', 'off');

						instance._searchPanelHolder = searchPanelHolder;
						instance._searchPanelInput = searchPanelInput;

						instance._createCancelButton();
						instance._createFocusManager();
						instance._createLiveSearch();
					}
				},

				_searchActive: false,
				_searchValue: ''
			};

			ControlPanel.init();
		}
	);
}