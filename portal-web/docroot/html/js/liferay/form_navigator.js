AUI().add(
	'liferay-form-navigator',
	function(A) {
		var CSS_HIDDEN = 'aui-helper-hidden-accessible';

		var CSS_SECTION_ERROR = 'section-error';

		var CSS_SELECTED = 'selected';

		var SELECTOR_FORM_SECTION = '.form-section';

		var SELECTOR_LIST_ITEM_SELECTED = 'li.selected';

		var FormNavigator = function(options) {
			var instance = this;

			instance._namespace = options.namespace || '';

			instance._container = A.one(options.container);

			instance._formName = options.formName;

			Liferay.after('form:registered', instance._afterFormRegistered, instance);

			instance._navigation = instance._container.one('.form-navigator');
			instance._sections = instance._container.all(SELECTOR_FORM_SECTION);

			if (instance._navigation) {
				instance._navigation.delegate('click', instance._onClick, 'li a', instance);
			}

			if (options.modifiedSections) {
				instance._modifiedSections = A.all('[name=' + options.modifiedSections+ ']');

				if (!instance._modifiedSections) {
					instance._modifiedSections = A.Node.create('<input name="' + options.modifiedSections + '" type="hidden" />');

					instance._container.append(instance._modifiedSections);
				}
			}
			else {
				instance._modifiedSections = null;
			}

			if (options.defaultModifiedSections) {
				instance._modifiedSectionsArray = options.defaultModifiedSections;
			}
			else {
				instance._modifiedSectionsArray = [];
			}

			instance._revealSection(location.href);

			A.on('formNavigator:trackChanges', instance._trackChanges, instance);

			var inputs = instance._container.all('input, select, textarea');

			if (inputs) {
				inputs.on(
					'change',
					function(event) {
						A.fire('formNavigator:trackChanges', event.target);
					}
				);
			}

			Liferay.on(
				'submitForm',
				function(event, data) {
					if (instance._modifiedSections) {
						instance._modifiedSections.val(instance._modifiedSectionsArray.join(','));
					}
				}
			);
		};

		FormNavigator.prototype = {
			_addModifiedSection: function (section) {
				var instance = this;

				if (A.Array.indexOf(section, instance._modifiedSectionsArray) == -1) {
					instance._modifiedSectionsArray.push(section);
				}
			},

			_afterFormRegistered: function(event) {
				var instance = this;

				if (event.formName === instance._formName) {
					var formValidator = event.form.formValidator;

					instance._formValidator = formValidator;

					formValidator.on(['errorField', 'validField'], instance._updateSectionStatus, instance);
				}
			},

			_getId: function(id) {
				var instance = this;

				var namespace = instance._namespace;

				id = id || '';

				if (id.indexOf('#') > -1) {
					id = id.split('#')[1] || '';

					id = id.replace(instance._hashKey, '');
				}
				else if (id.indexOf('historyKey=') > -1) {
					id = id.match(/historyKey=([^&#]+)/);
					id = id && id[1];
				}
				else {
					id = '';
				}

				if (id && namespace && (id.indexOf(namespace) == -1)) {
					id = namespace + id;
				}

				return id;
			},

			_onClick: function(event) {
				var instance = this;

				event.preventDefault();

				var target = event.currentTarget;

				var li = target.get('parentNode');

				if (li && !li.test('.selected')) {
					var href = target.attr('href');

					instance._revealSection(href, li);

					var hash = href.split('#');

					var hashValue = hash[1];

					if (hashValue) {
						A.later(0, instance, instance._updateHash, [hashValue]);
					}
				}
			},

			_revealSection: function(id, currentNavItem) {
				var instance = this;

				id = instance._getId(id);

				if (id) {
					id = id.charAt(0) != '#' ? '#' + id : id;

					var li = currentNavItem || instance._navigation.one('[href$=' + id + ']').get('parentNode');

					id = id.split('#');

					var namespacedId = id[1];

					if (namespacedId) {
						Liferay.fire('formNavigator:reveal' + namespacedId);

						var section = A.one('#' + namespacedId);
						var selected = instance._navigation.one(SELECTOR_LIST_ITEM_SELECTED);

						if (selected) {
							selected.removeClass(CSS_SELECTED);
						}

						li.addClass(CSS_SELECTED);

						instance._sections.removeClass(CSS_SELECTED).addClass(CSS_HIDDEN);

						if (section) {
							section.addClass(CSS_SELECTED).removeClass(CSS_HIDDEN);
						}
					}
				}
			},

			_trackChanges: function(el) {
				var instance = this;

				var currentSection = A.one(el).ancestor(SELECTOR_FORM_SECTION).attr('id');

				var currentSectionLink = A.one('#' + currentSection + 'Link');

				if (currentSectionLink) {
					currentSectionLink.get('parentNode').addClass('section-modified');
				}

				instance._addModifiedSection(currentSection);
			},

			_updateHash: function(section) {
				var instance = this;

				location.hash = instance._hashKey + section;
			},

			_updateSectionStatus: function() {
				var instance = this;

				var navigation = instance._navigation;

				var lis = navigation.all('li');

				lis.removeClass(CSS_SECTION_ERROR);

				var formValidator = instance._formValidator;

				if (formValidator.hasErrors()) {
					var selectors = A.Object.keys(formValidator.errors);

					A.all('#' + selectors.join(', #')).each(
						function(item, index, collection) {
							var section = item.ancestor(SELECTOR_FORM_SECTION);

							if (section) {
								var navItem = navigation.one('a[href="#' + section.attr('id') + '"]');

								if (navItem) {
									navItem.ancestor().addClass(CSS_SECTION_ERROR);
								}
							}
						}
					);
				}
			},

			_hashKey: '_LFR_FN_'
		};

		Liferay.FormNavigator = FormNavigator;
	},
	'',
	{
		requires: ['aui-base']
	}
);