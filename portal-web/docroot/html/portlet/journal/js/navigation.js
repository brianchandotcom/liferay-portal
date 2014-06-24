AUI.add(
	'liferay-journal-navigation',
	function(A) {
		var DISPLAY_STYLE_TOOLBAR = 'displayStyleToolbar';

		var STR_ROW_IDS_JOURNAL_FOLDER_CHECKBOX = 'rowIdsJournalFolder';

		var STR_ROW_IDS_JOURNAL_ARTICLE_CHECKBOX = 'rowIdsJournalArticle';

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
							instance.ns(STR_ROW_IDS_JOURNAL_ARTICLE_CHECKBOX),
							instance.ns(STR_ROW_IDS_JOURNAL_FOLDER_CHECKBOX)
						];

						var displayStyle = config.displayStyle;

						var displayStyleCSSClass = 'entry-display-style';

						var displayStyleToolbar = instance.byId(DISPLAY_STYLE_TOOLBAR);

						var namespace = instance.NS;

						var portletContainerId = instance.ns('journalContainer');

						var selectConfig = config.select;

						selectConfig.checkBoxesId = checkBoxesId;
						selectConfig.displayStyle = displayStyle;
						selectConfig.displayStyleCSSClass = displayStyleCSSClass;
						selectConfig.displayStyleToolbar = displayStyleToolbar;
						selectConfig.folderContainer = instance.byId('folderContainer');
						selectConfig.namespace = namespace;
						selectConfig.portletContainerId = portletContainerId;
						selectConfig.selector = 'entry-selector';

						instance._appViewSelect = new Liferay.AppViewSelect(selectConfig);

						var moveConfig = config.move;

						moveConfig.processEntryIds = {
							checkBoxesIds: checkBoxesId,
							entryIds: [
								instance.ns('articleIds'),
								instance.ns('folderIds')
							]
						};

						moveConfig.displayStyleCSSClass = displayStyleCSSClass;
						moveConfig.draggableCSSClass = '.entry-link';
						moveConfig.namespace = namespace;
						moveConfig.portletContainerId = portletContainerId;
						moveConfig.portletGroup = 'journal';

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