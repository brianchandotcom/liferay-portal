AUI.add(
	'liferay-panel',
	function(A) {

		/**
		 * OPTIONS
		 *
		 * Optional
		 * container {string|object}: A selector of the panel container if there are multiple panels handled by this one.
		 * panel {string|object}: A selector of the panel.
		 * panelContent {string|object}: A selector of the content section of the panel.
		 * header {string|object}: A selector of the panel's header area.
		 * titles {string|object}: A selector of the titles in the panel.
		 * footer {string|object}: A selector of the panel's footer area.
		 * accordion {boolean}: Whether or not the panels have accordion behavior (meaning only one panel can be open at a time).
		 * collapsible {boolean}: Whether or not the panel can be collapsed by clicking the title.
		 *
		 */

		var Panel = A.Component.create(
			{
				EXTENDS: A.Base,

				get: function(id) {
					var instance = this;

					return instance[instance._prefix + id];
				},

				NAME: 'liferaypanel',

				prototype: {
					initializer: function(config) {
						var instance = this;

						var defaults = {
							accordion: false,
							collapsible: true,
							container: null,
							footer: '.lfr-panel-footer',
							header: '.lfr-panel-header',
							panel: '.lfr-panel',
							panelContent: '.lfr-panel-content',
							persistState: false,
							titles: '.lfr-panel-titlebar'
						};

						instance.addTarget(Liferay);

						config = A.merge(defaults, config);

						instance._inContainer = false;
						instance._container = A.getBody();

						if (config.container) {
							instance._container = A.one(config.container);
							instance._inContainer = true;
						}

						instance._panel = instance._container.all(config.panel);

						instance._panelContent = instance._panel.all(config.panelContent);
						instance._header = instance._panel.all(config.header);
						instance._footer = instance._panel.all(config.footer);
						instance._panelTitles = instance._panel.all(config.titles);
						instance._accordion = config.accordion;

						instance._collapsible = config.collapsible;
						instance._persistState = config.persistState;

						if (instance._collapsible) {
							instance.makeCollapsible();

							instance._panelTitles.unselectable();

							instance._panelTitles.setStyle(
								{
									cursor: 'pointer'
								}
							);

							var collapsedPanels = instance._panel.all('.lfr-collapsed');

							if (instance._accordion && !collapsedPanels.size()) {
								instance._panel.item(0).addClass('lfr-collapsed');
							}
						}

						instance.set('container', instance._container);
						instance.set('panel', instance._panel);
						instance.set('panelContent', instance._panelContent);
						instance.set('panelTitles', instance._panelTitles);
					},

					makeCollapsible: function() {
						var instance = this;

						instance._panelTitles.each(
							function(item, index, collection) {
								var panel = item.ancestor('.lfr-panel');

								if (panel.hasClass('lfr-extended')) {
									var toggler = item.all('.lfr-panel-button');

									if (!toggler.size()) {
										item.append('<a class="lfr-panel-button" href="javascript:;"></a>');
									}
								}
							}
						);

						instance.publish(
							'collapse',
							{
								defaultFn: instance._defCollapseFn
							}
						);

						instance._panelTitles.attr('tabIndex', 0);

						instance._panelTitles.on('click', instance._onTitleClick, instance);
						instance._panelTitles.on('key', instance._onTitleClick, 'down:13', instance);
					},

					_defCollapseFn: function(event) {
						var instance = this;

						var panel = event.panel;

						panel.toggleClass('lfr-collapsed');

						if (instance._accordion) {
							var siblings = panel.siblings('.lfr-panel');

							siblings.each(
								function(item, index, collection) {
									var id = item.attr('id');

									if (id) {
										instance._saveState(id, 'closed');
									}

									item.addClass('lfr-collapsed');
								}
							);
						}

						var panelId = event.panelId;
						var state = 'open';

						if (panel.hasClass('lfr-collapsed')) {
							state = 'closed';
						}

						var toggler = event.panelTitle.all('.lfr-panel-button');

						if (toggler.size()) {
							var title = Liferay.Language.get('expand');

							if (state == 'open') {
								title = Liferay.Language.get('collapse');
							}

							toggler.attr('title', title);
						}

						instance._saveState(panelId, state);

						instance.fire(
							'titleClick',
							{
								panel: panel,
								panelId: panelId,
								panelTitle: event.panelTitle
							}
						);
					},

					_onTitleClick: function(event) {
						var instance = this;

						var currentTarget = event.currentTarget;

						var panel = currentTarget.ancestor('.lfr-panel');

						instance.fire(
							'collapse',
							{
								panel: panel,
								panelTitle: currentTarget,
								panelId: panel.attr('id')
							}
						);
					},

					_saveState: function(id, state) {
						var instance = this;

						if (instance._persistState) {
							var data = {};

							data[id] = state;

							Liferay.Store(data);
						}
					}
				},

				register: function(id, panel) {
					var instance = this;

					instance[instance._prefix + id] = panel;
				},

				_prefix: '__'
			}
		);

		Liferay.Panel = Panel;
	},
	'',
	{
		requires: ['aui-base', 'liferay-store']
	}
);