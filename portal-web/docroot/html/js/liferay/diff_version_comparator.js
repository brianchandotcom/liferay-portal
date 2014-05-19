AUI.add(
	'liferay-diff-version-comparator',
	function(A) {
		var BODY = A.getBody();

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

		var DiffVersionSearch = A.Component.create(
			{
				AUGMENTS: [A.AutoCompleteBase],

				EXTENDS: A.Base,

				NAME: 'diffversionsearch',

				prototype: {
					initializer: function() {
						this._bindUIACBase();
						this._syncUIACBase();
					}
				}
			}
		);

		var DiffVersionComparator = A.Component.create(
			{
				ATTRS: {
					diffContainerHtmlResultsSelector: {
						value: ''
					},

					diffFormSelector: {
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

					searchBoxSelector: {
						value: ''
					},

					sourceVersionSelector: {
						value: ''
					},

					targetVersionSelector: {
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
						var diffFormSelector = instance.get('diffFormSelector');
						var searchBoxSelector = instance.get('searchBoxSelector');
						var sourceVersionSelector = instance.get('sourceVersionSelector');
						var targetVersionSelector = instance.get('targetVersionSelector');
						var versionFilterSelector = instance.get('versionFilterSelector');
						var versionItemsSelector = instance.get('versionItemsSelector');

						instance._diffContainerHtmlResults = instance.byId(diffContainerHtmlResultsSelector);
						instance._diffForm = instance.byId(diffFormSelector);
						instance._searchBox = instance.byId(searchBoxSelector);
						instance._sourceVersionSelector = instance.byId(sourceVersionSelector);
						instance._targetVersionSelector = instance.byId(targetVersionSelector);
						instance._versionFilter = instance.byId(versionFilterSelector);
						instance._versionItems = instance.byId(versionItemsSelector);

						instance._diffVersionSearch = instance._createDiffVersionSearch();

						var eventHandles = [
							BODY.delegate('click', instance._onSourceVersionSelected, '.source-version', instance),
							BODY.delegate('click', instance._onTargetVersionSelected, '.target-version', instance),
							instance._versionFilter.delegate('click', instance._onCloseFilter, '.close-version-filter', instance),
							instance._versionItems.delegate('click', instance._onSelectVersionItem, '.version-item', instance),
							instance._diffVersionSearch.on('results', instance._onDiffVersionSearchResults, instance)
						];

						instance._languageSelector = instance.byId('languageId');

						if (instance._languageSelector) {
							eventHandles.push(
								instance._languageSelector.on('change', instance._onLanguageSelectorChange, instance)
							);
						}

						instance._eventHandles = eventHandles;
					},

					destructor: function() {
						var instance = this;

						(new A.EventHandle(instance._eventHandles)).detach();
					},

					_createDiffVersionSearch: function() {
						var instance = this;

						var results = [];

						instance._versionItems.all('.version-item').each(
							function(node) {
								results.push(
									{
										node: node,
										searchData: node.one('.version-title').text()
									}
								);
							}
						);

						var diffVersionSearch = new DiffVersionSearch(
							{
								inputNode: instance._searchBox,
								minQueryLength: 0,
								queryDelay: 0,
								source: results,
								resultTextLocator: 'searchData',
								resultFilters: 'phraseMatch'
							}
						);

						return diffVersionSearch;
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

					_onDiffVersionSearchResults: function(event) {
						var instance = this;

						instance._versionItems.all('.version-item').addClass('hide');

						A.Array.each(
							event.results,
							function(result) {
								result.raw.node.removeClass('hide');
							}
						);
					},

					_onCloseFilter: function(event) {
						var instance = this;

						instance._versionItems.all('.version-item').removeClass('selected');

						instance._versionFilter.hide();

						instance._loadDiffHTML(instance._initialSourceVersion, instance._initialTargetVersion);
					},

					_onLanguageSelectorChange: function(event) {
						var instance = this;

						submitForm(instance._diffForm);
					},

					_onSourceVersionSelected: function(event) {
						var instance = this;

						var sourceVersion = event.currentTarget.attr('data-version');

						instance.byId('sourceVersion').val(sourceVersion);

						submitForm(instance._diffForm);
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
					},

					_onTargetVersionSelected: function(event) {
						var instance = this;

						var targetVersion = event.currentTarget.attr('data-version');

						instance.byId('targetVersion').val(targetVersion);

						submitForm(instance._diffForm);
					}
				}
			}
		);

		Liferay.DiffVersionSearch = DiffVersionSearch;
		Liferay.DiffVersionComparator = DiffVersionComparator;
	},
	'',
	{
		requires: ['aui-io-request', 'aui-parse-content', 'autocomplete-base', 'autocomplete-filters', 'liferay-portlet-base']
	}
);