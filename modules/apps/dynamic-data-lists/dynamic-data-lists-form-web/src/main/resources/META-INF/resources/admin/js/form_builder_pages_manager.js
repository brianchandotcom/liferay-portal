AUI.add(
	'liferay-ddl-form-builder-pages-manager',
	function(A) {
		var Renderer = Liferay.DDM.Renderer;

		var CSS_FORM_BUILDER_CONTROLS_TRIGGER = A.getClassName('form', 'builder', 'controls', 'trigger');

		var CSS_FORM_BUILDER_PAGE_MANAGER_ADD_PAGE_LAST_POSITION = A.getClassName('form', 'builder', 'page', 'manager', 'add', 'last', 'position');

		var CSS_FORM_BUILDER_PAGE_MANAGER_DELETE_PAGE = A.getClassName('form', 'builder', 'page', 'manager', 'delete', 'page');

		var CSS_FORM_BUILDER_PAGE_MANAGER_SWITCH_MODE = A.getClassName('form', 'builder', 'page', 'manager', 'switch', 'mode');

		var CSS_FORM_BUILDER_PAGES_CONTENT = A.getClassName('form', 'builder', 'page', 'manager', 'content');

		var CSS_FORM_BUILDER_PAGINATION = A.getClassName('form', 'builder', 'pagination');

		var CSS_FORM_BUILDER_TABVIEW = A.getClassName('form', 'builder', 'tabview');

		var CSS_PAGE_HEADER = A.getClassName('form', 'builder', 'page', 'header');

		var CSS_PAGE_HEADER_DESCRIPTION = A.getClassName('form', 'builder', 'page', 'header', 'description');

		var CSS_PAGE_HEADER_DESCRIPTION_HIDE_BORDER = A.getClassName('form', 'builder', 'page', 'header', 'description', 'hide', 'border');

		var CSS_PAGE_HEADER_TITLE = A.getClassName('form', 'builder', 'page', 'header', 'title');

		var CSS_PAGE_HEADER_TITLE_HIDE_BORDER = A.getClassName('form', 'builder', 'page', 'header', 'title', 'hide', 'border');

		var FormBuilderPagesManager = A.Component.create(
			{
				ATTRS: {
					builder: {
					},

					mode: {
						validator: '_validateMode',
						value: 'pagination'
					},

					strings: {
						value: {
							addPageLastPosition: Liferay.Language.get('add-new-page'),
							aditionalInfo: Liferay.Language.get('add-a-short-description-for-this-page'),
							deleteCurrentPage: Liferay.Language.get('delete-current-page'),
							resetPage: Liferay.Language.get('reset-page'),
							switchMode: Liferay.Language.get('switch-pagination-mode'),
							untitledPage: Liferay.Language.get('untitled-page-x-of-x')
						},
						writeOnce: true
					}
				},

				CSS_PREFIX: 'form-builder-page-manager',

				NAME: 'liferay-ddl-form-builder-pages-manager',

				EXTENDS: A.FormBuilderPageManager,

				prototype: {
					TPL_PAGES: '<div class="' + CSS_FORM_BUILDER_PAGES_CONTENT + '">' +
						'<div class="' + CSS_FORM_BUILDER_PAGINATION + '"></div>' +
					'</div>',

					TPL_PAGE_CONTROL_TRIGGER:
						'<a class="' + CSS_FORM_BUILDER_CONTROLS_TRIGGER + '" data-position="{position}" href="javascript:;">' +
							'<span class="icon-ellipsis-vertical icon-monospaced"></span>' +
						'</a>',

					TPL_PAGE_HEADER: '<div class="' + CSS_PAGE_HEADER + ' form-inline">' +
						'<textarea rows="1" placeholder="{untitledPage}" tabindex="1" class="' + CSS_PAGE_HEADER_TITLE + ' ' +
						CSS_PAGE_HEADER_TITLE_HIDE_BORDER + ' form-control"></textarea>' +
						'<textarea rows="1" placeholder="{aditionalInfo}" tabindex="2" class="' + CSS_PAGE_HEADER_DESCRIPTION + ' ' +
						CSS_PAGE_HEADER_DESCRIPTION_HIDE_BORDER + ' form-control"></textarea>' +
					'</div>',

					initializer: function() {
						var instance = this;

						instance.after('titlesChange', A.bind('_afterTitlesChange', instance));

						A.on('windowresize', A.bind('_syncPageInformationHeight', instance));
					},

					_addWizardPage: function() {
						var instance = this;

						var activePageNumber = instance.get('activePageNumber');

						var wizard = instance._getWizard();

						wizard.set('selected', activePageNumber - 1);
					},

					_afterPagesQuantityChange: function(event) {
						var instance = this;

						FormBuilderPagesManager.superclass._afterPagesQuantityChange.apply(instance, arguments);

						instance._syncControlTriggersUI();

						instance._uiSetMode(instance.get('mode'));

						var popover = instance._getPopover();

						var popoverBoundingBox = popover.get('boundingBox');

						var switchModeNode = popoverBoundingBox.one('.' + CSS_FORM_BUILDER_PAGE_MANAGER_SWITCH_MODE);

						switchModeNode.toggle(event.newVal > 1);
					},

					_afterTitlesChange: function(event) {
						var instance = this;

						instance._syncWizardItems();
					},

					_afterWizardSelectionChange: function() {
						var instance = this;

						var selectedWizard = instance._getWizard().get('selected');

						if (selectedWizard > -1) {
							var pagination = instance._getPagination();

							pagination.set('page', selectedWizard + 1);

							instance.set('activePageNumber', selectedWizard + 1);
						}
					},

					_createPopover: function() {
						var instance = this;

						var strings = instance.get('strings');

						var popover = new A.Popover(
							{
								bodyContent: A.Lang.sub(
									instance.TPL_POPOVER_CONTENT,
									{
										addPageLastPosition: strings.addPageLastPosition,
										addPageNextPosition: strings.addPageNextPosition,
										deleteCurrentPage: this._getDeleteButtonString(),
										switchMode: strings.switchMode
									}
								),
								constrain: true,
								cssClass: 'form-builder-page-manager-popover-header',
								visible: false,
								zIndex: 50
							}
						).render();

						var popoverBoundingBox = popover.get('boundingBox');

						popoverBoundingBox.one('.' + CSS_FORM_BUILDER_PAGE_MANAGER_ADD_PAGE_LAST_POSITION).on('click', A.bind('_onAddLastPageClick', instance));
						popoverBoundingBox.one('.' + CSS_FORM_BUILDER_PAGE_MANAGER_DELETE_PAGE).on('click', A.bind('_onRemovePageClick', instance));

						var switchModeNode = popoverBoundingBox.one('.' + CSS_FORM_BUILDER_PAGE_MANAGER_SWITCH_MODE);

						switchModeNode.on('click', A.bind('_onSwitchViewClick', instance));
						switchModeNode.toggle(instance.get('pagesQuantity') > 1);

						instance._renderControlTriggers(popover);
						instance._syncControlTriggersUI();

						return popover;
					},

					_createUntitledPageLabel: function(activePageNumber, pagesQuantity) {
						var instance = this;
						var title;

						var strings = instance.get('strings');

						title = A.Lang.sub(
							strings.untitledPage,
							[
								activePageNumber,
								pagesQuantity
							]
						);

						return title;
					},

					_createWizardItems: function() {
						var instance = this;

						var activePageNumber = instance.get('activePageNumber');
						var pagesQuantity = instance.get('pagesQuantity');
						var titles = instance.get('titles');

						var items = [];

						for (var i = 1; i <= pagesQuantity; i++) {
							var title = titles[i - 1];

							if (!title) {
								title = instance._createUntitledPageLabel(i, pagesQuantity);
							}

							items.push(
								{
									state: (activePageNumber === i) ? 'active' : '',
									title: title
								}
							);
						}

						return items;
					},

					_getWizard: function() {
						var instance = this;

						if (!instance.wizard) {
							var builder = instance.get('builder');

							var wizardNode = builder.get('boundingBox').one('.' + CSS_FORM_BUILDER_TABVIEW);

							instance.wizard = new Renderer.Wizard(
								{
									after: {
										selectedChange: A.bind(instance._afterWizardSelectionChange, instance)
									},
									allowNavigation: true,
									boundingBox: wizardNode,
									items: instance._createWizardItems(),
									srcNode: wizardNode.one('> ul')
								}
							);
						}

						return instance.wizard;
					},

					_onAddLastPageClick: function() {
						var instance = this;

						instance._addPage();
						instance._addWizardPage();

						instance._getPopover().hide();
					},

					_onAddPageClick: function() {
						var instance = this;

						instance._addPage();
						instance._addWizardPage();
					},

					_onPageControlOptionClick: function(event) {
						var popover = this._getPopover();

						event.stopPropagation();

						popover.set(
							'align',
							{
								node: event.currentTarget,
								points: [A.WidgetPositionAlign.RC, A.WidgetPositionAlign.TC]
							}
						);

						popover.set('position', event.currentTarget.getData('position'));

						popover.toggle();
					},

					_onRemovePageClick: function() {
						var instance = this;

						var activePageNumber = instance.get('activePageNumber');

						var pagination = instance._getPagination();

						pagination.prev();

						instance.set('pagesQuantity', instance.get('pagesQuantity') - 1);

						instance.fire(
							'remove',
							{
								removedIndex: activePageNumber - 1
							}
						);

						var page = Math.max(1, activePageNumber - 1);

						pagination.getItem(page).addClass('active');

						var titles = instance.get('titles');

						titles.splice(activePageNumber - 1, 1);

						instance.set('titles', titles);
						instance.set('activePageNumber', page);

						instance._removeWizardPage(activePageNumber - 1);

						if (!instance.get('pagesQuantity')) {
							instance._addPage();
							instance._addWizardPage();

							instance._getWizard().activate(0);
						}
					},

					_onSwitchViewClick: function() {
						var instance = this;

						instance._getPopover().hide();

						if (instance.get('mode') === 'pagination') {
							instance.set('mode', 'wizard');
						}
						else {
							instance.set('mode', 'pagination');
						}
					},

					_onTitleInputValueChange: function(event) {
						var instance = this;

						var activePageNumber = instance.get('activePageNumber');
						var titles = instance.get('titles');

						var title = event.newVal.trim();

						titles[activePageNumber - 1] = title;

						if (!title) {
							var pagesQuantity = instance.get('pagesQuantity');

							title = instance._createUntitledPageLabel(activePageNumber, pagesQuantity);
						}

						instance.set('titles', titles);
					},

					_plugAutoSizeOnPageHeaderInformation: function(node) {
						var instance = this;

						var height = node.get('scrollHeight');

						node.plug(
							A.Plugin.Autosize
						);

						node.autosize._uiAutoSize();

						node.autosize._uiAutoSize();

						node.height(height);
					},

					_removeWizardPage: function(index) {
						var instance = this;

						var wizard = instance._getWizard();

						wizard._removeItem(index);

						instance._syncWizardItems();
					},

					_renderControlTriggers: function(popover) {
						var instance = this;

						var builder = instance.get('builder');

						var boundingBox = builder.get('boundingBox');

						var topControlTrigger = A.Lang.sub(
							instance.TPL_PAGE_CONTROL_TRIGGER,
							{
								position: 'top'
							}
						);

						var leftControlTrigger = A.Lang.sub(
							instance.TPL_PAGE_CONTROL_TRIGGER,
							{
								position: 'left'
							}
						);

						instance.get('pageHeader').one('.' + CSS_PAGE_HEADER).append(leftControlTrigger);

						boundingBox.one('.' + CSS_FORM_BUILDER_TABVIEW).append(topControlTrigger);
						boundingBox.one('.' + CSS_FORM_BUILDER_PAGINATION).append(leftControlTrigger);

						boundingBox.delegate('click', A.bind(instance._onPageControlOptionClick, instance), '.' + CSS_FORM_BUILDER_CONTROLS_TRIGGER);

						var controlsTriggerNodeList = boundingBox.all('.' + CSS_FORM_BUILDER_CONTROLS_TRIGGER);

						controlsTriggerNodeList.on('clickoutside', popover.hide, popover);
					},

					_renderTopPagination: function() {
						var instance = this;

						var wizard = instance._getWizard();

						wizard.render();
					},

					_syncControlTriggersUI: function() {
						var instance = this;

						var builder = instance.get('builder');
						var pageHeader = instance.get('pageHeader');
						var pagesQuantity = instance.get('pagesQuantity');

						var boundingBox = builder.get('boundingBox');

						boundingBox.all('.' + CSS_FORM_BUILDER_CONTROLS_TRIGGER).toggle(pagesQuantity > 1);
						pageHeader.one('.' + CSS_FORM_BUILDER_CONTROLS_TRIGGER).toggle(pagesQuantity <= 1);
					},

					_syncPageInformationHeight: function() {
						var instance = this;

						var pageTitle = instance.get('pageHeader').one('.' + CSS_PAGE_HEADER_TITLE);

						var pageDescription = instance.get('pageHeader').one('.' + CSS_PAGE_HEADER_DESCRIPTION);

						if (!pageTitle.autosize) {
							instance._plugAutoSizeOnPageHeaderInformation(pageTitle);
							instance._plugAutoSizeOnPageHeaderInformation(pageDescription);
						}

						pageTitle.autosize._uiAutoSize();

						pageDescription.autosize._uiAutoSize();
					},

					_syncWizardItems: function() {
						var instance = this;

						var wizard = instance._getWizard();

						wizard.set('selected', instance.get('activePageNumber') - 1);
						wizard.set('items', instance._createWizardItems());
					},

					_uiSetActivePageNumber: function(event) {
						var instance = this;

						FormBuilderPagesManager.superclass._uiSetActivePageNumber.apply(instance, arguments);

						instance._syncPageInformationHeight();
					},

					_uiSetMode: function(type) {
						var instance = this;

						var pagination = instance._getPagination();
						var wizard = instance._getWizard();

						var paginationBoundingBox = pagination.get('boundingBox').get('parentNode');
						var wizardBoundingBox = wizard.get('boundingBox');

						if (instance.get('pagesQuantity') > 1) {
							if (type === 'wizard') {
								paginationBoundingBox.hide();
								wizardBoundingBox.show();

								instance._syncWizardItems();
							}
							else if (type === 'pagination') {
								paginationBoundingBox.show();
								wizardBoundingBox.hide();

								pagination.set('page', instance.get('activePageNumber'));
							}
						}
						else {
							paginationBoundingBox.hide();
							wizardBoundingBox.hide();
						}
					},

					_validateMode: function(mode) {
						return (mode === 'pagination' || mode === 'wizard');
					}
				}
			}
		);

		Liferay.namespace('DDL').FormBuilderPagesManager = FormBuilderPagesManager;
	},
	'',
	{
		requires: ['aui-autosize-deprecated', 'aui-form-builder-page-manager', 'liferay-ddm-form-renderer-wizard']
	}
);