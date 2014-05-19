AUI.add(
	'liferay-diff-version-comparator',
	function(A) {
		var Lang = A.Lang;

		var TPL_VERSION =
			'<div class="close-version-filter">' +
				'<i class="icon-remove"></i>' +
			'</div>' +
			'<div>' +
				'<span class="version"><liferay-ui:message key="version" /> {0}</span>' +
			'</div>' +
			'<div>' +
				'<span class="user-name">{1}</span>' +
				'<span class="display-date">{2}</span>' +
			'</div>';

		var DiffVersionComparator = A.Component.create(
			{
				ATTRS: {
					diffContainerHtmlResultsSelector: {
						value: ''
					},

					initialSourceVersion: {
						value: ''
					},

					initialTargetVersion: {
						value: ''
					},

					namespace: {
						value: ''
					},

					resourceURL: {
						value: ''
					},

					versionFilterSelector: {
						value: ''
					},

					versionItemsSelector: {
						value: ''
					}
				},

				AUGMENTS: [Liferay.PortletBase],

				EXTENDS: A.Base,

				NAME: 'diffversioncomparator',

				prototype: {
					initializer: function() {
						var instance = this;

						instance._initialSourceVersion = instance.get('initialSourceVersion');
						instance._initialTargetVersion = instance.get('initialTargetVersion');
						instance._resourceURL = instance.get('resourceURL');

						var diffContainerHtmlResultsSelector = instance.get('diffContainerHtmlResultsSelector');
						var versionFilterSelector = instance.get('versionFilterSelector');
						var versionItemsSelector = instance.get('versionItemsSelector');

						instance._diffContainerHtmlResults = instance.byId(diffContainerHtmlResultsSelector);
						instance._versionFilter = instance.byId(versionFilterSelector);
						instance._versionItems = instance.byId(versionItemsSelector);

						var eventHandles = [
							instance._versionFilter.delegate('click', instance._onCloseFilter, '.close-version-filter', instance),
							instance._versionItems.delegate('click', instance._onSelectVersionItem, '.version-item', instance)
						];

						instance._eventHandles = eventHandles;
					},

					destructor: function() {
						var instance = this;

						(new A.EventHandle(instance._eventHandles)).detach();
					},

					_loadDiffHTML: function(sourceVersion, targetVersion) {
						var instance = this;

						A.io.request(
							instance._resourceURL,
							{
								after: {
									success: function(event, id, obj) {
										var responseData = this.get('responseData');

										instance._diffContainerHtmlResults.html(responseData);
									}
								},
								data: instance.ns(
									{
										sourceVersion: sourceVersion,
										targetVersion: targetVersion
									}
								)
							}
						);
					},

					_onCloseFilter: function(event) {
						var instance = this;

						instance._versionItems.all('.version-item').removeClass('selected');

						instance._versionFilter.hide();

						instance._loadDiffHTML(instance._initialSourceVersion, instance._initialTargetVersion);
					},


					_onSelectVersionItem: function(event) {
						var instance = this;

						var currentTarget = event.currentTarget;

						instance._versionItems.all('.version-item').removeClass('selected');

						currentTarget.addClass('selected');

						var version = currentTarget.attr('data-version');
						var userName = currentTarget.attr('data-user-name');
						var displayDate = currentTarget.attr('data-display-date');

						instance._versionFilter.html(Lang.sub(TPL_VERSION, [version, userName, displayDate]));

						instance._versionFilter.show();

						instance._loadDiffHTML(currentTarget.attr('data-source-version'), currentTarget.attr('data-version'));
					}
				}
			}
		);

		Liferay.DiffVersionComparator = DiffVersionComparator;
	},
	'',
	{
		requires: ['aui-io-request', 'liferay-portlet-base']
	}
);