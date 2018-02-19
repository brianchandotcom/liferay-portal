AUI.add(
	'liferay-ddm-form-builder-sidebar',
	function(A) {
		var AObject = A.Object;

		var CSS_PREFIX = 'form-builder-sidebar';

		var CSS_CLASS_CLOSE = A.getClassName(CSS_PREFIX, 'close');

		var CSS_CLASS_TITLE = A.getClassName(CSS_PREFIX, 'title');

		var CSS_CLASS_DESCRIPTION = A.getClassName(CSS_PREFIX, 'description');

		var FormBuilderSidebar = A.Component.create(
			{
				ATTRS: {
					bodyContent: {
						value: ''
					},

					cssClass: {
						value: 'sidebar'
					},

					description: {
						value: ''
					},

					fieldTypeOptions: {
						value: []
					},

					open: {
						value: false
					},

					skin: {
						value: 'sidebar-light'
					},

					title: {
						value: ''
					},

					toolbar: {
						value: null
					}
				},

				CSS_PREFIX: CSS_PREFIX,

				NAME: 'liferay-ddm-form-builder-sidebar',

				prototype: {
					initializer: function() {
						var instance = this;

						var eventHandlers;

						eventHandlers = [
							A.getDoc().on('click', A.bind(instance._onClickDocument, instance)),
							instance.after('descriptionChange', instance._syncHeaderInfo),
							instance.after('render', instance._afterRender),
							instance.after('titleChange', instance._syncHeaderInfo)
						];

						instance._eventHandlers = eventHandlers;
					},

					renderUI: function() {
						var instance = this;

						instance.get('boundingBox').addClass('sidenav-fixed sidenav-menu-slider sidenav-right sidenav-transition closed ' + instance.get('skin'));
						instance._renderTemplate();
					},

					bindUI: function() {
						var instance = this;

						var boundingBox = instance.get('boundingBox');

						var closeButton = boundingBox.one('.' + CSS_CLASS_CLOSE);

						instance._eventHandlers.push(
							A.one('doc').on('key', A.bind('_afterPressEscapeKey', instance), 'esc'),
							boundingBox.on('transitionend', A.bind('_onTransitionEnd', instance)),
							closeButton.on('click', A.bind('_afterClickCloseButton', instance))
						);
					},

					destructor: function() {
						var instance = this;

						(new A.EventHandle(instance._eventHandlers)).detach();
					},

					close: function() {
						var instance = this;

						if (!instance._isClose) {
							instance.fire('close:start');
						}
						else {
							instance.fire('close');
						}

						instance.get('boundingBox').removeClass('open');
						instance.get('boundingBox').addClass('closed');

						instance.set('field', null);
					},

					getTemplate: function() {
						var instance = this;

						var renderer = instance.getTemplateRenderer();

						var container = document.createDocumentFragment();

						new renderer(instance.getTemplateContext(), container);

						return container.firstChild.outerHTML;
					},

					getTemplateContext: function() {
						var instance = this;

						var toolbar = instance.get('toolbar');

						var context = {
							bodyContent: instance.get('bodyContent'),
							closeButtonIcon: Liferay.Util.getLexiconIconTpl('times'),
							description: instance.get('description'),
							fieldTypeOptions: instance.get('fieldTypeOptions'),
							title: instance.get('title'),
							toolbarButtonIcon: Liferay.Util.getLexiconIconTpl('ellipsis-v')
						};

						if (toolbar) {
							context.toolbarTemplateContext = toolbar.get('context');
						}

						return context;
					},

					getTemplateRenderer: function() {
						return AObject.getValue(window, ['DDMSidebar', 'render']);
					},

					isOpen: function() {
						var instance = this;

						return !!instance._isOpen;
					},

					open: function() {
						var instance = this;

						if (!instance.isOpen()) {
							instance.fire('open:start');
						}
						else {
							instance.fire('open');
						}

						instance.get('boundingBox').removeClass('closed');
						instance.get('boundingBox').addClass('open');
					},

					_afterClickCloseButton: function(event) {
						var instance = this;

						event.preventDefault();
						instance.close();
					},

					_afterPressEscapeKey: function() {
						var instance = this;

						if (instance.isOpen()) {
							instance.close();
						}
					},

					_afterRender: function() {
						var instance = this;

						var boundingBox = instance.get('boundingBox');

						var toolbar = instance.get('toolbar');

						if (toolbar) {
							toolbar.set('element', boundingBox.one('.dropdown'));
						}
					},

					_getFieldTypes: function(fieldTypes) {
						var instance = this;

						var types = [];

						fieldTypes.forEach(
							function(fieldType) {
								types.push(
									{
										description: fieldType.get('description'),
										group: fieldType.get('group') || 'customized',
										icon: window.DDMFieldTypesSidebar.render.Soy.toIncDom(Liferay.Util.getLexiconIconTpl(fieldType.get('icon'))),
										label: fieldType.get('label'),
										name: fieldType.get('name')
									}
								);
							}
						);

						return _.groupBy(types, 'group');
					},

					_onClickDocument: function(event) {
						var instance = this;

						if (instance.get('open') && !instance.hasFocus(event.target)) {
							instance.close();
						}
					},

					_onTransitionEnd: function(event) {
						var instance = this;

						var boundingBox = instance.get('boundingBox');

						var propertyName = event._event.propertyName;

						var target = event.target;

						if (propertyName === 'width' && target === boundingBox) {
							if (boundingBox.hasClass('open')) {
								instance.fire('open');

								instance._isOpen = true;
								instance._isClose = false;
								instance.set('open', true);
							}
							else {
								instance.fire('close');

								instance._isClose = true;
								instance._isOpen = false;
								instance.set('open', false);
							}
						}
					},

					_renderTemplate: function() {
						var instance = this;

						var headerTemplate = instance.getTemplate();

						instance.get('contentBox').append(headerTemplate);
					},

					_syncHeaderInfo: function() {
						var instance = this;

						var descriptionNode = instance.get('contentBox').one('.' + CSS_CLASS_DESCRIPTION);

						var titleNode = instance.get('contentBox').one('.' + CSS_CLASS_TITLE);

						if (titleNode) {
							titleNode.text(instance.get('title'));
						}

						if (descriptionNode) {
							descriptionNode.text(instance.get('description'));
						}
					}
				}
			}
		);

		Liferay.namespace('DDM').FormBuilderSidebar = FormBuilderSidebar;
	},
	'',
	{
		requires: ['aui-tabview', 'liferay-ddm-form-builder-field-options-toolbar']
	}
);