AUI.add(
	'liferay-journal-content',
	function(A) {
		var Lang = A.Lang;

		var STR_CLICK = 'click';

		var STR_STRINGS = 'strings';

		var STR_URLS = 'urls';

		var WIN = A.config.win;

		var JournalContent = A.Component.create(
			{
				ATTRS: {
					editStructure: {
						setter: A.one
					},

					editTemplate: {
						setter: A.one
					},

					strings: {
						validator: Lang.isObject,
						value: {
							editStructure: Liferay.Language.get('editing-the-current-structure-deletes-all-unsaved-content'),
							editTemplate: Liferay.Language.get('editing-the-current-template-deletes-all-unsaved-content'),
							structures: Liferay.Language.get('structures'),
							templates: Liferay.Language.get('templates')
						}
					},

					urls: {
						validator: Lang.isObject,
						value: {}
					}
				},

				AUGMENTS: [Liferay.PortletBase],

				EXTENDS: A.Base,

				NAME: 'journalcontent',

				prototype: {
					initializer: function(config) {
						var instance = this;

						instance._bindUI();
					},

					destructor: function() {
						var instance = this;

						(new A.EventHandle(instance._eventHandles)).detach();
					},

					_bindUI: function() {
						var instance = this;

						var editTemplate = instance.get('editTemplate');

						var eventHandles = [];

						if (editTemplate) {
							eventHandles.push(
								editTemplate.on(STR_CLICK, instance._editTemplate, instance)
							);
						}

						var editStructure = instance.get('editStructure');

						if (editStructure) {
							eventHandles.push(
								editStructure.on(STR_CLICK, instance._editStructure, instance)
							);
						}

						instance._eventHandles = eventHandles;
					},

					_editStructure: function(event) {
						var instance = this;

						var strings = instance.get(STR_STRINGS);

						if (confirm(strings.editStructure)) {
							var urls = instance.get(STR_URLS);

							Liferay.Util.openWindow(
								{
									id: A.guid(),
									refreshWindow: WIN,
									title: strings.structures,
									uri: urls.editStructure
								}
							);
						}
					},

					_editTemplate: function(event) {
						var instance = this;

						var strings = instance.get(STR_STRINGS);

						if (confirm(strings.editTemplate)) {
							var urls = instance.get(STR_URLS);

							Liferay.Util.openWindow(
								{
									id: A.guid(),
									refreshWindow: WIN,
									title: strings.templates,
									uri: urls.editTemplate
								}
							);
						}
					}
				}
			}
		);

		Liferay.Portlet.JournalContent = JournalContent;
	},
	'',
	{
		requires: ['aui-base', 'liferay-portlet-base']
	}
);