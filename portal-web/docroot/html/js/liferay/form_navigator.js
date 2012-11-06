AUI.add(
	'liferay-form-navigator',
	function(A) {
		var CSS_HIDDEN = 'aui-helper-hidden-accessible';

		var CSS_SECTION_ERROR = 'section-error';

		var CSS_SELECTED = 'selected';

		var SELECTOR_FORM_SECTION = '.form-section';

		var SELECTOR_LIST_ITEM_SELECTED = 'li.selected';

		var SELECTOR_SECTION_ERROR = '.' + CSS_SECTION_ERROR;

		var STR_HREF = 'href';

		var UI_SRC = A.Widget.UI_SRC;

		var FormNavigator = function(options) {
			var instance = this;

			var modifiedSections = options.modifiedSections;

			instance._container = A.one(options.container);

			instance._hash = null;
			instance._modifiedSections = null;

			instance._formName = options.formName;

			instance._modifiedSectionsArray = options.defaultModifiedSections || [];
			instance._namespace = options.namespace || '';

			Liferay.after('form:registered', instance._afterFormRegistered, instance);

			instance._navigation = instance._container.one('.form-navigator');
			instance._sections = instance._container.all(SELECTOR_FORM_SECTION);

			if (instance._navigation) {
				instance._navigation.delegate('click', instance._onClick, 'li a', instance);
			}

			if (modifiedSections) {
				instance._modifiedSections = A.all('[name=' + modifiedSections + ']');

				if (!instance._modifiedSections) {
					instance._modifiedSections = A.Node.create('<input name="' + modifiedSections + '" type="hidden" />');

					instance._container.append(instance._modifiedSections);
				}
			}

			A.setInterval(instance._pollHash, 100, instance);

			A.on('formNavigator:revealSection', instance._revealSection, instance);
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
			_addModifiedSection: function(section) {
				var instance = this;

				var modifiedSectionsArray = instance._modifiedSectionsArray;

				if (A.Array.indexOf(modifiedSectionsArray, section) == -1) {
					modifiedSectionsArray.push(section);
				}
			},

			_afterFormRegistered: function(event) {
				var instance = this;

				if (event.formName === instance._formName) {
					var formValidator = event.form.formValidator;

					instance._formValidator = formValidator;

					formValidator.on(['errorField', 'validField'], instance._updateSectionStatus, instance);
					formValidator.on('submitError', instance._revealSectionError, instance);
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

				event.src = UI_SRC;

				var target = event.currentTarget;

				var li = target.get('parentNode');

				if (li && !li.test('.selected')) {
					var href = target.attr(STR_HREF);

					instance._revealSection(href, li);

					var hash = href.split('#');

					var hashValue = hash[1];

					if (hashValue) {
						event.hashValue = hashValue;

						A.later(0, instance, instance._updateHash, event);
					}
				}
			},

			_pollHash: function() {
				var instance = this;

				var hash = location.hash;

				if (hash != instance._hash) {
					A.fire('formNavigator:revealSection', hash);

					Liferay.Util.getTop().Liferay.fire(
						'hashChange',
						{
							newVal: hash,
							prevVal: instance._hash,
							uri: location.href
						}
					);

					instance._hash = hash;
				}
			},

			_revealSection: function(id, currentNavItem) {
				var instance = this;

				id = instance._getId(id);

				if (id) {
					id = id.charAt(0) != '#' ? '#' + id : id;

					if (!currentNavItem) {
						var link = instance._navigation.one('[href$=' + id + ']');

						if (link) {
							currentNavItem = link.get('parentNode');
						}
					}

					id = id.split('#');

					var namespacedId = id[1];

					if (currentNavItem && namespacedId) {
						Liferay.fire('formNavigator:reveal' + namespacedId);

						var section = A.one('#' + namespacedId);
						var selected = instance._navigation.one(SELECTOR_LIST_ITEM_SELECTED);

						if (selected) {
							selected.removeClass(CSS_SELECTED);
						}

						currentNavItem.addClass(CSS_SELECTED);

						instance._sections.removeClass(CSS_SELECTED).addClass(CSS_HIDDEN);

						if (section) {
							section.addClass(CSS_SELECTED).removeClass(CSS_HIDDEN);
						}
					}
				}
			},

			_revealSectionError: function() {
				var instance = this;

				var sectionError = instance._navigation.one(SELECTOR_SECTION_ERROR);

				var sectionErrorLink = sectionError.one('a').attr(STR_HREF);

				instance._revealSection(sectionErrorLink, sectionError);
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

			_updateHash: function(event) {
				var instance = this;

				if (event.src && event.src == UI_SRC) {
					location.hash = instance._hashKey + event.hashValue;
				}
			},

			_updateSectionStatus: function() {
				var instance = this;

				var formValidator = instance._formValidator;
				var navigation = instance._navigation;

				var lis = navigation.all('li');

				lis.removeClass(CSS_SECTION_ERROR);

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
		requires: ['aui-base', 'aui-task-manager']
	}
);