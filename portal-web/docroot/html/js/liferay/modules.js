var LiferayAUI = Liferay.AUI;

var COMBINE = LiferayAUI.getCombine();

var PATH_JAVASCRIPT = LiferayAUI.getJavaScriptRootPath();

window.YUI_config = {
	base: PATH_JAVASCRIPT + '/aui/',
	combine: COMBINE,
	comboBase: LiferayAUI.getComboPath(),
	filter: Liferay.AUI.getFilter(),
	groups: {
		liferay: {
			base: PATH_JAVASCRIPT + '/liferay/',
			combine: COMBINE,
			modules: {
				'liferay-app-view-folders': {
					path: 'app_view_folders.js',
					requires: [
						'aui-base',
						'aui-parse-content',
						'liferay-app-view-move',
						'liferay-history-manager',
						'liferay-list-view',
						'liferay-node',
						'liferay-portlet-base'
					]
				},
				'liferay-app-view-move': {
					path: 'app_view_move.js',
					requires: [
						'aui-base',
						'dd-constrain',
						'dd-delegate',
						'dd-drag',
						'dd-drop',
						'dd-proxy',
						'liferay-history-manager',
						'liferay-portlet-base',
						'liferay-util-list-fields'
					]
				},
				'liferay-app-view-paginator': {
					path: 'app_view_paginator.js',
					requires: [
						'aui-pagination',
						'aui-parse-content',
						'liferay-history-manager',
						'liferay-portlet-base'
					]
				},
				'liferay-app-view-select': {
					path: 'app_view_select.js',
					requires: [
						'liferay-app-view-move',
						'liferay-history-manager',
						'liferay-portlet-base',
						'liferay-util-list-fields'
					]
				},
				'liferay-asset-categories-selector': {
					path: 'asset_categories_selector.js',
					requires: [
						'aui-tree',
						'liferay-asset-tags-selector'
					]
				},
				'liferay-asset-tags-selector': {
					path: 'asset_tags_selector.js',
					requires: [
						'array-extras',
						'async-queue',
						'aui-autocomplete-deprecated',
						'aui-form-textfield-deprecated',
						'aui-io-plugin-deprecated',
						'aui-io-request',
						'aui-live-search-deprecated',
						'aui-modal',
						'aui-template-deprecated',
						'aui-textboxlist-deprecated',
						'datasource-cache',
						'liferay-service-datasource'
					]
				},
				'liferay-auto-fields': {
					path: 'auto_fields.js',
					requires: [
						'aui-base',
						'aui-data-set-deprecated',
						'aui-io-request',
						'aui-parse-content',
						'sortable',
						'base',
						'liferay-undo-manager'
					]
				},
				'liferay-browser-selectors': {
					path: 'browser_selectors.js',
					requires: ['yui-base']
				},
				'liferay-ddm-repeatable-fields': {
					path: 'ddm_repeatable_fields.js',
					requires: [
						'aui-base',
						'aui-io-request',
						'aui-parse-content'
					]
				},
				'liferay-dockbar': {
					path: 'dockbar.js',
					requires: [
						'aui-node',
						'event-touch'
					]
				},
				'liferay-dockbar-add-application': {
					path: 'dockbar_add_application.js',
					requires: [
						'aui-io-request',
						'event-mouseenter',
						'liferay-dockbar',
						'liferay-dockbar-add-base',
						'liferay-dockbar-add-application-search',
						'liferay-portlet-base'
					]
				},
				'liferay-dockbar-add-application-search': {
					path: 'dockbar_add_application_search.js',
					requires: [
						'aui-base',
						'liferay-dockbar',
						'liferay-dockbar-add-search'
					]
				},
				'liferay-dockbar-add-base': {
					path: 'dockbar_add_base.js',
					requires: [
						'aui-base',
						'liferay-dockbar'
					]
				},
				'liferay-dockbar-add-content': {
					path: 'dockbar_add_content.js',
					requires: [
						'aui-io-request',
						'event-mouseenter',
						'liferay-dockbar',
						'liferay-dockbar-add-content-content-preview',
						'liferay-dockbar-add-content-search',
						'liferay-portlet-base'
					]
				},
				'liferay-dockbar-add-content-preview': {
					path: 'dockbar_add_content_preview.js',
					requires: [
						'aui-debounce',
						'aui-io-request',
						'event-mouseenter'
					]
				},
				'liferay-dockbar-portlet-dd': {
					path: 'dockbar_portlet_dd.js',
					requires: [
						'aui-base',
						'dd',
						'liferay-dockbar',
						'liferay-layout',
						'liferay-layout-column',
						'liferay-layout-freeform',
						'liferay-portlet-base'
					]
				},
				'liferay-dockbar-add-content-search': {
					path: 'dockbar_add_content_search.js',
					requires: [
						'aui-base',
						'liferay-dockbar',
						'liferay-dockbar-add-search'
					]
				},
				'liferay-dockbar-add-page': {
					path: 'dockbar_add_page.js',
					requires: [
						'aui-parse-content',
						'aui-toggler-delegate',
						'liferay-dockbar',
						'liferay-dockbar-add-base',
						'liferay-dockbar-add-page-search',
						'liferay-toggler-key-filter'
					]
				},
				'liferay-dockbar-add-page-search': {
					path: 'dockbar_add_page_search.js',
					requires: [
						'aui-base',
						'liferay-dockbar',
						'liferay-dockbar-add-search'
					]
				},
				'liferay-dockbar-add-search': {
					path: 'dockbar_add_search.js',
					requires: [
						'aui-base',
						'autocomplete-base',
						'autocomplete-filters',
						'liferay-dockbar'
					]
				},
				'liferay-dockbar-underlay': {
					path: 'dockbar_underlay.js',
					requires: [
						'aui-button',
						'aui-io-plugin-deprecated',
						'aui-overlay-manager-deprecated'
					]
				},
				'liferay-dynamic-select': {
					path: 'dynamic_select.js',
					requires: [
						'aui-base'
					]
				},
				'liferay-form': {
					path: 'form.js',
					requires: [
						'aui-base',
						'aui-form-validator'
					]
				},
				'liferay-form-placeholders': {
					condition: {
						name: 'liferay-form-placeholders',
						test: function(A) {
							return !('placeholder' in document.createElement('input'));
						},
						trigger: 'liferay-form'
					},
					path: 'form_placeholders.js',
					requires: [
						'liferay-form',
						'plugin'
					]
				},
				'liferay-history': {
					path: 'history.js',
					requires: [ 'history-hash', 'liferay-history-html5', 'querystring-parse-simple' ]
				},
				'liferay-history-html5': {
					path: 'history_html5.js',
					condition: {
						name: 'liferay-history-html5',
						test: function(A) {
							var WIN = A.config.win;

							var HISTORY = WIN.history;

							return (HISTORY &&
									HISTORY.pushState &&
									HISTORY.replaceState &&
									('onpopstate' in WIN || A.UA.gecko >= 2));
						},
						trigger: 'liferay-history'
					},
					requires: [
						'liferay-history',
						'history-html5',
						'querystring-stringify-simple'
					]
				},
				'liferay-history-manager': {
					path: 'history_manager.js',
					requires: [
						'liferay-history'
					]
				},
				'liferay-hudcrumbs': {
					path: 'hudcrumbs.js',
					requires: [
						'aui-base',
						'plugin'
					]
				},
				'liferay-icon': {
					path: 'icon.js',
					requires: [
						'aui-base'
					]
				},
				'liferay-inline-editor-base': {
					path: 'inline_editor_base.js',
					requires: [
						'aui-base',
						'aui-overlay-base-deprecated'
					]
				},
				'liferay-input-localized': {
					path: 'input_localized.js',
					requires: [
						'aui-base',
						'aui-component',
						'aui-event-input',
						'aui-palette',
						'aui-set',
						'portal-available-languages'
					]
				},
				'liferay-input-move-boxes': {
					path: 'input_move_boxes.js',
					requires: [
						'aui-base',
						'aui-toolbar'
					]
				},
				'liferay-toggler-key-filter': {
					path: 'toggler_key_filter.js',
					requires: [
						'aui-event-base'
					]
				},
				'liferay-layout': {
					path: 'layout.js'
				},
				'liferay-layout-column': {
					path: 'layout_column.js',
					requires: [
						'aui-sortable-layout',
						'dd'
					]
				},
				'liferay-layout-freeform': {
					path: 'layout_freeform.js',
					requires: [
						'aui-resize-deprecated',
						'liferay-layout-column'
					]
				},
				'liferay-list-view': {
					path: 'list_view.js',
					requires: [
						'aui-base',
						'transition'
					]
				},
				'liferay-logo-editor': {
					path: 'logo_editor.js',
					requires: [
						'aui-image-cropper',
						'aui-io-request',
						'liferay-portlet-base'
					]
				},
				'liferay-logo-selector': {
					path: 'logo_selector.js',
					requires: [
						'aui-base'
					]
				},
				'liferay-look-and-feel': {
					path: 'look_and_feel.js',
					requires: [
						'aui-color-picker-popover',
						'aui-io-plugin-deprecated',
						'aui-io-request',
						'aui-modal',
						'aui-tabview'
					]
				},
				'liferay-menu': {
					path: 'menu.js',
					requires: [
						'aui-debounce',
						'aui-node'
					]
				},
				'liferay-message': {
					path: 'message.js',
					requires: [
						'aui-base',
						'liferay-store'
					]
				},
				'liferay-navigation': {
					path: 'navigation.js',
					plugins: {
						'liferay-navigation-touch': {
							condition: {
								name: 'liferay-navigation-touch',
								test: function(A) {
									return A.UA.touch;
								},
								trigger: 'liferay-navigation'
							}
						}
					}
				},
				'liferay-navigation-interaction': {
					path: 'navigation_interaction.js',
					plugins: {
						'liferay-navigation-interaction-touch': {
							condition: {
								name: 'liferay-navigation-interaction-touch',
								test: function(A) {
									return A.UA.touch;
								},
								trigger: 'liferay-navigation-interaction'
							}
						}
					},
					requires: [
						'node-focusmanager',
						'plugin'
					]
				},
				'liferay-navigation-interaction-touch': {
					path: 'navigation_interaction_touch.js',
					requires: [
						'event-touch',
						'liferay-navigation-interaction'
					]
				},
				'liferay-navigation-touch': {
					path: 'navigation_touch.js',
					requires: [
						'event-touch',
						'liferay-navigation'
					]
				},
				'liferay-notice': {
					path: 'notice.js',
					requires: [
						'aui-base',
						'transition'
					]
				},
				'liferay-node': {
					path: 'node.js',
					requires: [
						'dom-base'
					]
				},
				'liferay-poller': {
					path: 'poller.js',
					requires: [
						'aui-base',
						'io',
						'json'
					]
				},
				'liferay-portlet-base': {
					path: 'portlet_base.js',
					requires: [
						'aui-base',
						'liferay-node'
					]
				},
				'liferay-portlet-url': {
					path: 'portlet_url.js',
					requires: [
						'aui-base',
						'aui-io-request',
						'querystring-stringify-simple'
					]
				},
				'liferay-preview': {
					path: 'preview.js',
					requires: [
						'aui-base',
						'aui-overlay-mask-deprecated',
						'aui-toolbar'
					]
				},
				'liferay-progress': {
					path: 'progress.js',
					requires: [
						'aui-progressbar'
					]
				},
				'liferay-ratings': {
					path: 'ratings.js',
					requires: [
						'aui-io-request',
						'aui-rating'
					]
				},
				'liferay-restore-entry': {
					path: 'restore_entry.js',
					requires: [
						'aui-io-plugin-deprecated',
						'aui-io-request',
						'aui-modal',
						'liferay-portlet-base'
					]
				},
				'liferay-search-container': {
					path: 'search_container.js',
					requires: [
						'aui-base',
						'aui-datatable-core',
						'event-mouseenter'
					]
				},
				'liferay-service-datasource': {
					path: 'service_datasource.js',
					requires: [
						'aui-base',
						'datasource-local'
					]
				},
				'liferay-session': {
					path: 'session.js',
					requires: [
						'aui-io-request',
						'aui-task-manager',
						'cookie',
						'liferay-notice'
					]
				},
				'liferay-staging': {
					path: 'staging.js',
					requires: [
						'aui-io-plugin-deprecated',
						'aui-modal'
					]
				},
				'liferay-staging-branch': {
					path: 'staging_branch.js',
					requires: [
						'liferay-staging'
					]
				},
				'liferay-staging-version': {
					path: 'staging_version.js',
					requires: [
						'aui-button',
						'liferay-staging'
					]
				},
				'liferay-store': {
					path: 'store.js',
					requires: [
						'aui-io-request'
					]
				},
				'liferay-token-list': {
					path: 'token_list.js',
					requires: [
						'aui-base',
						'aui-template'
					]
				},
				'liferay-translation-manager': {
					path: 'translation_manager.js',
					requires: [
						'aui-base'
					]
				},
				'liferay-undo-manager': {
					path: 'undo_manager.js',
					requires: [
						'aui-data-set-deprecated',
						'base'
					]
				},
				'liferay-upload': {
					path: 'upload.js',
					requires: [
						'aui-io-request',
						'aui-template-deprecated',
						'collection',
						'liferay-portlet-base',
						'uploader'
					]
				},
				'liferay-util-list-fields': {
					path: 'util_list_fields.js',
					requires: [
						'aui-base'
					]
				},
				'liferay-util-window': {
					path: 'util_window.js',
					requires: [
						'aui-dialog-iframe-deprecated',
						'aui-modal'
					]
				},
				'liferay-xml-formatter': {
					path: 'xml_formatter.js',
					requires: [
						'aui-base'
					]
				}
			},
			root: PATH_JAVASCRIPT + '/liferay/'
		},

		misc: {
			base: PATH_JAVASCRIPT + '/misc/',
			combine: COMBINE,
			modules: {
				'swfupload': {
					path: '/swfupload/swfupload.js'
				},
				'swfobject': {
					path: '/swfobject.js'
				}
			},
			root: PATH_JAVASCRIPT + '/misc/'
		},

		portal: {
			base: PATH_JAVASCRIPT + '/liferay/',
			combine: false,
			modules: {
				'portal-aui-lang': {
					path: LiferayAUI.getLangPath(),
					requires: [
						'aui-calendar'
					]
				},
				'portal-available-languages': {
					path: LiferayAUI.getAvailableLangPath(),
					requires: [
						'liferay-language'
					]
				}
			},
			root: PATH_JAVASCRIPT + '/liferay/'
		}
	},
	root: PATH_JAVASCRIPT + '/aui/',
	useBrowserConsole: false
};

YUI.Env.core.push('liferay-browser-selectors');