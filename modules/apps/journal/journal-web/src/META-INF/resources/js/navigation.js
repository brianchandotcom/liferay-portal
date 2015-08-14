AUI.add(
	'liferay-journal-navigation',
	function(A) {
		var DISPLAY_STYLE_TOOLBAR = 'displayStyleToolbar';

		var JournalNavigation = A.Component.create(
			{
				AUGMENTS: [Liferay.PortletBase],

				EXTENDS: A.Base,

				NAME: 'journalnavigation',

				prototype: {
					initializer: function(config) {
						var instance = this;

						var journalContainer = instance.byId('journalContainer');

						instance._journalContainer = journalContainer;

						var checkBoxesId = [
							config.rowIds
						];

						var displayStyle = A.clone(config.displayStyle);

						var displayStyleCSSClass = 'entry-display-style';

						var displayStyleToolbar = instance.byId(DISPLAY_STYLE_TOOLBAR);

						var namespace = instance.NS;

						var portletContainerId = instance.ns('journalContainer');

						var selectConfig = A.merge(
							{
								checkBoxesId: checkBoxesId,
								displayStyle: displayStyle,
								displayStyleCSSClass: displayStyleCSSClass,
								displayStyleToolbar: displayStyleToolbar,
								namespace: namespace,
								portletContainerId: portletContainerId,
								selector: 'entry-selector',
								toggleSelector: 'click-selector'
							},
							config.select
						);

						instance._appViewSelect = new Liferay.AppViewSelect(selectConfig);

						var moveConfig = A.merge(
							{
								displayStyleCSSClass: displayStyleCSSClass,
								draggableCSSClass: '.entry-link',
								moveToTrashActionName: 'moveToTrash',
								namespace: namespace,
								portletContainerId: portletContainerId,
								portletGroup: 'journal',
								processEntryIds: {
									checkBoxesIds: checkBoxesId,
									entryIds: [
										instance.ns('articleIds'),
										instance.ns('folderIds')
									]
								}
							},
							config.move
						);

						instance._appViewMove = new Liferay.AppViewMove(moveConfig);
					},

					destructor: function() {
						var instance = this;

						instance._appViewMove.destroy();
						instance._appViewSelect.destroy();

						instance._journalContainer.purge(true);
					}
				}
			}
		);

		Liferay.Portlet.JournalNavigation = JournalNavigation;
	},
	'',
	{
		requires: ['liferay-app-view-move', 'liferay-app-view-select', 'liferay-portlet-base']
	}
);