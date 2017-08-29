AUI.add(
	'liferay-diff-version-comparator',
	function(A) {
		var Lang = A.Lang;

		var STR_ACTIVE = 'active';

		var SELECTOR_VERSION_ITEM = '.version-item';

		var STR_CLICK = 'click';

		var STR_DATA_VERSION = 'data-version';

		var STR_DIFF_FORM = 'diffForm';

		var STR_VERSION_FILTER = 'versionFilter';

		var STR_VERSION_ITEMS = 'versionItems';

		var TPL_VERSION = '<div class="pull-right close-version-filter">' +
				'<a href="javascript:;">' +
					'<svg class="lexicon-icon" focusable="false"><use data-href="' + Liferay.ThemeDisplay.getPathThemeImages() + '/lexicon/icons.svg#times" /><title>Icon</title></svg>' +
				'</a>' +
			'</div>' +
			'<div>' +
				'<span class="text-default">{version}</span>' +
			'</div>' +
			'<div>' +
				'<small class="text-primary user-name">{userName}</small>' +
				'<small class="text-default">{displayDate}</small>' +
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
					diffContainerHtmlResults: {
						setter: A.one
					},

					diffForm: {
						setter: A.one
					},

					initialSourceVersion: {
						validator: Lang.isString
					},

					initialTargetVersion: {
						validator: Lang.isString
					},

					resourceURL: {
						validator: Lang.isString
					},

					searchBox: {
						setter: A.one
					},

					versionFilter: {
						setter: A.one
					},

					versionItems: {
						setter: A.one
					}
				},

				AUGMENTS: [Liferay.PortletBase],

				EXTENDS: A.Base,

				NAME: 'diffversioncomparator',

				prototype: {
					initializer: function() {
						var instance = this;

						instance._createDiffVersionSearch();

						instance._bindUI();
					},

					destructor: function() {
						var instance = this;

						(new A.EventHandle(instance._eventHandles)).detach();
					},

					_bindUI: function() {
						var instance = this;

						var eventHandles = [
							instance.get(STR_VERSION_FILTER).delegate(STR_CLICK, instance._onCloseFilter, '.close-version-filter', instance),
							instance.get(STR_VERSION_ITEMS).delegate(STR_CLICK, instance._onSelectVersionItem, SELECTOR_VERSION_ITEM, instance)
						];

						if (instance._diffVersionSearch) {
							eventHandles.push(
								instance._diffVersionSearch.on('results', instance._onDiffVersionSearchResults, instance)
							);
						}

						var languageSelector = instance.byId('languageId');

						if (languageSelector) {
							eventHandles.push(
								languageSelector.on('change', instance._onLanguageSelectorChange, instance)
							);
						}

						instance._eventHandles = eventHandles;
					},

					_createDiffVersionSearch: function() {
						var instance = this;

						var results = [];

						instance.get(STR_VERSION_ITEMS).all(SELECTOR_VERSION_ITEM).each(
							function(item, index, collection) {
								results.push(
									{
										node: item,
										title: item.one('.version-title').text(),
										user: item.attr('data-user-name')
									}
								);
							}
						);

						var searchBox = instance.get('searchBox');

						if (searchBox) {
							instance._diffVersionSearch = new DiffVersionSearch(
								{
									inputNode: searchBox,
									minQueryLength: 0,
									queryDelay: 0,
									resultFilters: function(query, results) {
										query = query.toLowerCase();

										return results.filter(
											function(item) {
												var result = item.raw;

												var title = result.title.toLowerCase();

												var user = result.user.toLowerCase();

												return ((title.indexOf(query) !== -1) || (user.indexOf(query) !== -1));
											}
										);
									},
									source: results
								}
							);
						}
					},

					_loadDiffHTML: function(sourceVersion, targetVersion) {
						var instance = this;

						A.io.request(
							instance.get('resourceURL'),
							{
								after: {
									failure: function(event, id, obj) {
										instance.get('diffContainerHtmlResults').html(Liferay.Language.get('an-error-occurred-while-processing-the-requested-resource'));
									},
									success: function(event, id, obj) {
										var responseData = this.get('responseData');

										instance.get('diffContainerHtmlResults').html(responseData);
									}
								},
								data: instance.ns(
									{
										filterSourceVersion: sourceVersion,
										filterTargetVersion: targetVersion
									}
								)
							}
						);
					},

					_onCloseFilter: function(event) {
						var instance = this;

						instance.get(STR_VERSION_ITEMS).all(SELECTOR_VERSION_ITEM).removeClass(STR_ACTIVE);

						instance.get(STR_VERSION_FILTER).hide();

						instance._loadDiffHTML(instance.get('initialSourceVersion'), instance.get('initialTargetVersion'));
					},

					_onDiffVersionSearchResults: function(event) {
						var instance = this;

						instance.get(STR_VERSION_ITEMS).all(SELECTOR_VERSION_ITEM).hide();

						instance.get(STR_VERSION_ITEMS).one('.message-info').toggle(!event.results.length);

						event.results.forEach(
							function(item, index, collection) {
								item.raw.node.show();
							}
						);
					},

					_onLanguageSelectorChange: function(event) {
						var instance = this;

						submitForm(instance.get(STR_DIFF_FORM));
					},

					_onSelectVersionItem: function(event) {
						var instance = this;

						var currentTarget = event.currentTarget;

						instance.get(STR_VERSION_ITEMS).all(SELECTOR_VERSION_ITEM).removeClass(STR_ACTIVE);

						currentTarget.addClass(STR_ACTIVE);

						var versionFilter = instance.get(STR_VERSION_FILTER);

						versionFilter.html(
							Lang.sub(
								TPL_VERSION,
								{
									displayDate: currentTarget.attr('data-display-date'),
									userName: currentTarget.attr('data-user-name'),
									version: currentTarget.attr('data-version-name')
								}
							)
						);

						versionFilter.show();

						instance._loadDiffHTML(currentTarget.attr('data-source-version'), currentTarget.attr(STR_DATA_VERSION));
					}
				}
			}
		);

		Liferay.DiffVersionSearch = DiffVersionSearch;
		Liferay.DiffVersionComparator = DiffVersionComparator;
	},
	'',
	{
		requires: ['aui-io-request', 'autocomplete-base', 'autocomplete-filters', 'liferay-portlet-base']
	}
);