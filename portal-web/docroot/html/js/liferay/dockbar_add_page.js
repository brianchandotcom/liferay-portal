AUI.add(
	'liferay-dockbar-add-page',
	function(A) {
		var Dockbar = Liferay.Dockbar;

		var AddPage = A.Component.create(
			{
				AUGMENTS: [Dockbar.AddPageTemplateSearch],

				EXTENDS: A.Base,

				NAME: 'addpage',

				prototype: {
					initializer: function(config) {
						var instance = this;
					}
				}
			}
		);

		Dockbar.AddPage = AddPage;
	},
	'',
	{
		requires: ['liferay-dockbar', 'liferay-dockbar-add-page-template-search']
	}
);