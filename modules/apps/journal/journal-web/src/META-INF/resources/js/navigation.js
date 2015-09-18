AUI.add(
	'liferay-journal-navigation',
	function(A) {
		var STR_ROW_IDS_JOURNAL_ARTICLE_CHECKBOX = 'rowIdsJournalArticle';

		var STR_ROW_IDS_JOURNAL_FOLDER_CHECKBOX = 'rowIdsJournalFolder';

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

						var displayStyleCSSClass = 'entry-display-style';

						var namespace = instance.NS;

						var portletContainerId = instance.ns('journalContainer');

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

						instance._journalContainer.purge(true);
					}
				}
			}
		);

		Liferay.Portlet.JournalNavigation = JournalNavigation;
	},
	'',
	{
		requires: ['liferay-app-view-move', 'liferay-portlet-base']
	}
);