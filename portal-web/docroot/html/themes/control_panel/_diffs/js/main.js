Liferay.Util.portletTitleEdit = function() {
};

if (!themeDisplay.isStatePopUp()) {
	AUI().ready(
		'aui-live-search-deprecated',
		'aui-overlay-context-panel-deprecated',
		'event-mouseenter',
		'liferay-message',
		'liferay-panel-search',
		'liferay-store',
		'node-focusmanager',
		'transition',
		function(A) {
			var body = A.getBody();

			var portletId = '';
			var visible = false;

			var sessionKey = 'show-portlet-description-' + portletId;

			var trim = A.Lang.trim;

			var CSS_DISPLAY_PANEL_COLUMNS = 'display-panel-columns';

			var CSS_PANELS_MINIMIZED = 'panels-minimized';

			var CSS_SEARCH_PANEL_ACTIVE = 'search-panel-active';

			var EVENT_DATA_SIDEBAR = {
				persist: true
			};

			var SELECTOR_SEARCH_NODES = '#controlPanelMenuAddContentPanelContainer ul li a';

			var TPL_CANCEL_SEARCH_BUTTON = '<a class="icon-remove cancel-search" href="javascript:;"></a>';

			var TPL_TOGGLE_PANELS_BUTTON = '<div class="minimize-panels">' +
					'<a class="close-panel-button" href="javascript:;">' +
						'<span>' + Liferay.Language.get('minimize-panels') + '</span>' +
					'</a>' +
				'</div>';

			var ControlPanel = {
				init: function() {
					var instance = this;

					instance._renderUI();
					instance._bindUI();
				},

				_afterHiddenChange: function(event) {
					var instance = this;

					instance._uiSetHidden(event.newVal, event.persist);
				},

				_bindUI: function() {
					var instance = this;

					var panelHolder = instance._panelHolder;

					if (panelHolder) {
						Liferay.set('controlPanelSidebarHidden', body.hasClass(CSS_PANELS_MINIMIZED));

						Liferay.after('controlPanelSidebarHiddenChange', instance._afterHiddenChange, instance);

						if (body.hasClass(CSS_PANELS_MINIMIZED)) {
							instance._panelHolderAccordionHandle = instance._panelHolderAccordionDisable();

							panelHolder.delegate('click', instance._panelHolderHandleClick, '.toggler-header');

							instance._controlPanelTools.on('click', instance._toggleHidden, instance);
						}

						instance._panelToggleButton.on('click', instance._toggleHidden, instance);
					}

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

					new Liferay.PanelSearch(
						{
							categorySelector: '.panel-page-category',
							inputNode: instance._searchPanelInput,
							nodeList: '#controlPanelMenuAddContentPanelContainer',
							nodeSelector: 'li',
							togglerId: '_160_controlPanelMenuAddContentPanelContainer'
						}
					);
				},

				_focusSearchBar: function(event) {
					var instance = this;

					instance._searchPanelInput.selectText();
				},

				_panelHolderAccordionDisable: function() {
					var component = Liferay.component('_160_controlPanelMenuAddContentPanelContainer');

					var handler = A.on(
						function(e) {
							return new A.Do.Prevent();
						},
						component,
						'headerEventHandler'
					);

					return handler;
				},

				_panelHolderCollapse: function() {
					var instance = this;

					body.addClass(CSS_PANELS_MINIMIZED);
				},

				_panelHolderExpand: function() {
					var instance = this;

					body.removeClass(CSS_PANELS_MINIMIZED);
				},

				_panelHolderHandleClick: function(event) {
					var currentTarget = event.currentTarget;

					var accordionGroup = currentTarget.ancestor();

					accordionGroup.toggleClass('open');

					currentTarget.once(
						'clickoutside',
						function() {
							accordionGroup.removeClass('open');
						}
					);
				},

				_renderUI: function() {
					var instance = this;

					var controlPanelTools = A.one('.control-panel-tools');

					var togglePanels = A.Node.create(TPL_TOGGLE_PANELS_BUTTON);

					var panelHolder = A.one('.panel-page-menu');

					var panelToggleButton = togglePanels.one('a');

					var searchPanelHolder = A.one('.search-panels');

					if (panelHolder) {
						var panelPageBody = panelHolder.ancestor().one('.panel-page-body');

						panelHolder.append(togglePanels);

						instance._controlPanelTools = controlPanelTools;
						instance._panelHolderAccordionHandle = null;
						instance._panelHolder = panelHolder;
						instance._panelPageBody = panelPageBody;
						instance._panelToggleButton = panelToggleButton;
						instance._togglePanels = togglePanels;
					}

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

				_toggleHidden: function(event) {
					var instance = this;

					Liferay.set('controlPanelSidebarHidden', !Liferay.get('controlPanelSidebarHidden'), EVENT_DATA_SIDEBAR);
				},

				_uiSetHidden: function(newVal, persist) {
					var instance = this;

					if (persist) {
						Liferay.Store('control-panel-sidebar-minimized', newVal);
					}

					if (body.hasClass(CSS_PANELS_MINIMIZED)) {
						instance._panelHolder.detach();

						instance._panelHolderExpand();

						instance._controlPanelTools.detach('click');

						if (instance._panelHolderAccordionHandle) {
							instance._panelHolderAccordionHandle.detach();
						}
					}
					else {
						instance._panelHolderAccordionHandle = instance._panelHolderAccordionDisable();

						instance._panelHolderCollapse();

						instance._panelHolder.delegate('click', instance._panelHolderHandleClick, '.toggler-header');

						instance._controlPanelTools.on('click', instance._toggleHidden, instance);
					}
				},

				_searchActive: false,
				_searchValue: ''
			};

			ControlPanel.init();
		}
	);
}