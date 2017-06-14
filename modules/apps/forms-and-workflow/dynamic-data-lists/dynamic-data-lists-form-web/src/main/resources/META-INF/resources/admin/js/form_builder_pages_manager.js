AUI.add(
	'liferay-ddl-form-builder-pages-manager',
	function(A) {
		var CSS_FORM_BUILDER_CONTROLS_TRIGGER = A.getClassName('form', 'builder', 'controls', 'trigger');

		var CSS_FORM_BUILDER_PAGE_MANAGER_ADD_PAGE_LAST_POSITION = A.getClassName('form', 'builder', 'page', 'manager', 'add', 'last', 'position');

		var CSS_FORM_BUILDER_PAGE_MANAGER_ADD_SUCCESS_PAGE = A.getClassName('form', 'builder', 'page', 'manager', 'add', 'success', 'page');

		var CSS_FORM_BUILDER_PAGE_MANAGER_DELETE_PAGE = A.getClassName('form', 'builder', 'page', 'manager', 'delete', 'page');

		var CSS_FORM_BUILDER_PAGE_MANAGER_SWITCH_MODE = A.getClassName('form', 'builder', 'page', 'manager', 'switch', 'mode');

		var CSS_FORM_BUILDER_PAGE_POPOVER_CONTENT = A.getClassName('form', 'builder', 'pages', 'popover', 'content');

		var CSS_FORM_BUILDER_PAGES_CONTENT = A.getClassName('form', 'builder', 'page', 'manager', 'content');

		var CSS_FORM_BUILDER_PAGINATION = A.getClassName('form', 'builder', 'pagination');

		var CSS_FORM_BUILDER_SUCCESS_PAGE = A.getClassName('form', 'builder', 'success', 'page');

		var CSS_FORM_BUILDER_SUCCESS_PAGE_CONTENT = A.getClassName('form', 'builder', 'success', 'page', 'content');

		var CSS_FORM_BUILDER_SUCCESS_PAGE_TITLE = A.getClassName('form', 'builder', 'success', 'page', 'title');

		var CSS_FORM_BUILDER_TABVIEW = A.getClassName('form', 'builder', 'tabview');

		var CSS_LAYOUT = A.getClassName('form', 'builder', 'layout');

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

					defaultLanguageId: {
						value: themeDisplay.getDefaultLanguageId()
					},

					descriptions: {
						getter: '_getDescriptions'
					},

					editingLanguageId: {
						value: themeDisplay.getDefaultLanguageId()
					},

					localizedDescriptions: {
						value: []
					},

					localizedTitles: {
						value: []
					},

					mode: {
						validator: '_validateMode',
						value: 'wizard'
					},

					strings: {
						value: {
							addPageLastPosition: Liferay.Language.get('add-new-page'),
							addSuccessPage: Liferay.Language.get('add-success-page'),
							aditionalInfo: Liferay.Language.get('add-a-short-description-for-this-page'),
							content: Liferay.Language.get('content'),
							defaultContent: Liferay.Language.get('your-information-was-successfully-received-thanks-for-fill-out'),
							defaultTitle: Liferay.Language.get('done'),
							deleteCurrentPage: Liferay.Language.get('delete-current-page'),
							resetPage: Liferay.Language.get('reset-page'),
							switchMode: Liferay.Language.get('switch-pagination-mode'),
							title: Liferay.Language.get('title'),
							untitledPage: Liferay.Language.get('untitled-page-x-of-x')
						},
						writeOnce: true
					},

					successPageSettings: {
						value: {
							body: {},
							enabled: false,
							title: {}
						}
					},

					titles: {
						getter: '_getTitles'
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
							Liferay.Util.getLexiconIconTpl('ellipsis-v') +
						'</a>',

					TPL_PAGE_HEADER: '<div class="' + CSS_PAGE_HEADER + ' form-inline">' +
						'<textarea rows="1" placeholder="{untitledPage}" class="' + CSS_PAGE_HEADER_TITLE + ' ' +
						CSS_PAGE_HEADER_TITLE_HIDE_BORDER + ' form-control"></textarea>' +
						'<textarea rows="1" placeholder="{aditionalInfo}" class="' + CSS_PAGE_HEADER_DESCRIPTION + ' ' +
						CSS_PAGE_HEADER_DESCRIPTION_HIDE_BORDER + ' form-control"></textarea>' +
					'</div>',

					TPL_POPOVER_CONTENT: '<ul class="' + CSS_FORM_BUILDER_PAGE_POPOVER_CONTENT + '">' +
					'<li class="' + CSS_FORM_BUILDER_PAGE_MANAGER_ADD_PAGE_LAST_POSITION + '">{addPageLastPosition}</li>' +
					'<li class="' + CSS_FORM_BUILDER_PAGE_MANAGER_DELETE_PAGE + '">{deleteCurrentPage}</li>' +
					'<li class="' + CSS_FORM_BUILDER_PAGE_MANAGER_ADD_SUCCESS_PAGE + '">{addSuccessPage}</li>' +
					'<li class="' + CSS_FORM_BUILDER_PAGE_MANAGER_SWITCH_MODE + '">{switchMode}</li>' +
					'</ul>',

					TPL_SUCCESS_PAGE: '<div class="' + CSS_FORM_BUILDER_SUCCESS_PAGE + '">' +
					'<label class="control-label">{title}</label><input class="' + CSS_FORM_BUILDER_SUCCESS_PAGE_TITLE + ' form-control" type="text"><br>' +
					'<label class="control-label">{content}</label><input class="' + CSS_FORM_BUILDER_SUCCESS_PAGE_CONTENT + ' form-control" type="text">' +
					'</div>',

					initializer: function() {
						var instance = this;

						instance._eventHandlers = [
							A.on('windowresize', A.bind('_syncPageInformationHeight', instance)),
							instance.after('editingLanguageIdChange', A.bind('_afterEditingLanguageIdChange', instance)),
							instance.after('titlesChange', A.bind('_afterTitlesChange', instance))
						];

						var boundingBox = instance.get('builder').get('boundingBox');

						var content = boundingBox.one('.form-builder-content');

						var strings = instance.get('strings');

						var successPage = A.Node.create(
							A.Lang.sub(
								instance.TPL_SUCCESS_PAGE,
								{
									content: strings.content,
									title: strings.title
								}
							)
						);

						successPage.hide();

						content.append(successPage);

						instance._createTitleForEditingLanguageId();
					},

					destructor: function() {
						var instance = this;

						(new A.EventHandle(instance._eventHandlers)).detach();
					},

					disablePaginations: function() {
						var instance = this;

						FormBuilderPagesManager.superclass.disablePaginations.apply(instance, arguments);

						instance._toggleWizardDisabled(true);
					},

					enablePaginations: function() {
						var instance = this;

						FormBuilderPagesManager.superclass.enablePaginations.apply(instance, arguments);

						instance._toggleWizardDisabled(false);
					},

					getSuccessPageDefinition: function() {
						var instance = this;

						return instance._updateSuccessPageSettings();
					},

					setSuccessPage: function(successPageSettings) {
						var instance = this;

						var builder = instance.get('builder');

						var boundingBox = builder.get('boundingBox');

						var successPage = boundingBox.one('.' + CSS_FORM_BUILDER_SUCCESS_PAGE);

						var wizard = instance._getWizard();

						if (successPageSettings && successPageSettings.enabled) {
							wizard.set('successPage', successPageSettings.enabled);

							successPage.one('.' + CSS_FORM_BUILDER_SUCCESS_PAGE_TITLE).val(successPageSettings.title);

							successPage.one('.' + CSS_FORM_BUILDER_SUCCESS_PAGE_CONTENT).val(successPageSettings.body);

							instance._uiSetMode(instance.get('mode'));

							instance._syncControlTriggersUI();

							var popoverBoundingBox = instance._getPopover().get('boundingBox');

							popoverBoundingBox.one('.' + CSS_FORM_BUILDER_PAGE_MANAGER_ADD_SUCCESS_PAGE).hide();
						}

						instance._syncPopoverContent();
					},

					toggleControlsTriggerDisabled: function(disabled) {
						var instance = this;

						var builder = instance.get('builder');

						var boundingBox = builder.get('boundingBox');

						boundingBox.all('.' + CSS_FORM_BUILDER_CONTROLS_TRIGGER).toggleClass('disabled', disabled);
					},

					toggleDescriptionDisabled: function(disabled) {
						var instance = this;

						var descriptionNode = instance.get('pageHeader').one('.' + CSS_PAGE_HEADER_DESCRIPTION);

						instance._toggleNodeDisabled(descriptionNode, disabled);
					},

					toggleTitleDisabled: function(disabled) {
						var instance = this;

						var titleNode = instance.get('pageHeader').one('.' + CSS_PAGE_HEADER_TITLE);

						instance._toggleNodeDisabled(titleNode, disabled);
					},

					_addWizardPage: function() {
						var instance = this;

						var activePageNumber = instance.get('activePageNumber');

						var wizard = instance._getWizard();

						wizard.set('selected', activePageNumber - 1);
					},

					_afterEditingLanguageIdChange: function(event) {
						var instance = this;

						instance._updateSuccessPageSettings();

						instance.set('editingLanguageId', event.newVal);

						var wizard = instance._getWizard();

						var selectedWizard = wizard.get('selected');

						var pagesQuantity = wizard.get('items').length;

						if (wizard.get('successPage') && selectedWizard === pagesQuantity) {
							instance._syncSuccessPage();
						}
						else {
							instance._syncTitle();
						}

						instance._syncWizardItems();
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

						var wizard = instance._getWizard();

						var selectedWizard = wizard.get('selected');

						var pagesQuantity = wizard.get('items').length;

						if (wizard.get('successPage') && selectedWizard === pagesQuantity) {
							instance._showSuccessPage();
						}
						else if (selectedWizard > -1) {
							instance._showLayout();
							var pagination = instance._getPagination();

							pagination.set('page', selectedWizard + 1);

							instance.set('activePageNumber', selectedWizard + 1);

							instance._syncTitle();
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
										addSuccessPage: strings.addSuccessPage,
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
						popoverBoundingBox.one('.' + CSS_FORM_BUILDER_PAGE_MANAGER_ADD_SUCCESS_PAGE).on('click', A.bind('_onAddSuccessClick', instance));

						var switchModeNode = popoverBoundingBox.one('.' + CSS_FORM_BUILDER_PAGE_MANAGER_SWITCH_MODE);

						switchModeNode.on('click', A.bind('_onSwitchViewClick', instance));
						switchModeNode.toggle(instance.get('pagesQuantity') > 1);

						instance._renderControlTriggers(popover);
						instance._syncControlTriggersUI();

						return popover;
					},

					_createTitleForEditingLanguageId: function() {
						var instance = this;

						var activePageNumber = instance.get('activePageNumber');
						var editingLanguageId = instance.get('editingLanguageId');
						var defaultLanguageId = instance.get('defaultLanguageId');

						var localizedTitles = instance.get('localizedTitles');
						var localizedDescriptions = instance.get('localizedDescriptions');

						var activePageIndex = activePageNumber - 1;

						if (!localizedTitles[activePageIndex]) {
							localizedTitles[activePageIndex] = {};
						}

						if (!localizedDescriptions[activePageIndex]) {
							localizedDescriptions[activePageIndex] = {};
						}

						if (!localizedTitles[activePageIndex][editingLanguageId]) {
							localizedTitles[activePageIndex][editingLanguageId] = localizedTitles[activePageIndex][defaultLanguageId] || '';
						}

						if (!localizedDescriptions[activePageIndex][editingLanguageId]) {
							localizedDescriptions[activePageIndex][editingLanguageId] = localizedDescriptions[activePageIndex][defaultLanguageId] || '';
						}
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

						instance._createTitleForEditingLanguageId();

						var editingLanguageId = instance.get('editingLanguageId');
						var defaultLanguageId = instance.get('defaultLanguageId');

						var localizedTitles = instance.get('localizedTitles');

						var items = [];

						for (var i = 1; i <= pagesQuantity; i++) {
							var index = i - 1;

							if (!localizedTitles[index]) {
								localizedTitles[index] = {};
							}

							var title = localizedTitles[index][editingLanguageId] || localizedTitles[index][defaultLanguageId];

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

					_getDeleteButtonString: function() {
						var instance = this;

						var deleteButtonString;

						var wizard = instance._getWizard();

						if (instance.get('pagesQuantity') > 1 || wizard.get('successPage')) {
							deleteButtonString = instance.get('strings').deleteCurrentPage;
						}
						else {
							deleteButtonString = instance.get('strings').resetPage;
						}

						return deleteButtonString;
					},

					_getDescriptions: function() {
						var instance = this;

						var editingLanguageId = instance.get('editingLanguageId');

						return instance.get('localizedDescriptions').map(
							function(localizedDescription) {
								return localizedDescription[editingLanguageId];
							}
						);
					},

					_getTitles: function() {
						var instance = this;

						var editingLanguageId = instance.get('editingLanguageId');

						return instance.get('localizedTitles').map(
							function(localizedTitle) {
								return localizedTitle[editingLanguageId];
							}
						);
					},

					_getWizard: function() {
						var instance = this;

						if (!instance._wizard) {
							var builder = instance.get('builder');

							var wizardNode = builder.get('boundingBox').one('.' + CSS_FORM_BUILDER_TABVIEW);

							instance._wizard = new Liferay.DDL.FormBuilderWizard(
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

						instance._wizard.get('boundingBox').delegate('click', A.bind(instance._onClickItemWizard, instance), 'li');

						return instance._wizard;
					},

					_onAddLastPageClick: function() {
						var instance = this;

						instance._addPage();
						instance._addWizardPage();

						instance._getPopover().hide();
					},

					_onAddSuccessClick: function() {
						var instance = this;

						var wizard = instance._getWizard();

						wizard.set('successPage', true);

						instance._uiSetMode(instance.get('mode'));

						instance._showSuccessPage();

						instance._syncControlTriggersUI();

						var pagesQuantity = instance.get('pagesQuantity');

						wizard.clearAll();

						wizard.activate(pagesQuantity);

						instance._syncPopoverContent();

						var popoverBoundingBox = instance._getPopover().get('boundingBox');

						popoverBoundingBox.one('.' + CSS_FORM_BUILDER_PAGE_MANAGER_ADD_SUCCESS_PAGE).hide();

						instance._resetSuccessPage();

						instance._getPopover().hide();
					},

					_onClickItemWizard: function(event) {
						var instance = this;

						var currentTarget = event.currentTarget;

						var attrSuccessPage = currentTarget.getData('success-page');

						if (!attrSuccessPage) {
							instance._showLayout();
						}
					},

					_onDescriptionInputValueChange: function(event) {
						var instance = this;

						var activePageNumber = instance.get('activePageNumber');
						var editingLanguageId = instance.get('editingLanguageId');
						var descriptions = instance.get('descriptions');
						var localizedDescriptions = instance.get('localizedDescriptions');

						var description = event.newVal.trim();

						descriptions[activePageNumber - 1] = description;
						localizedDescriptions[activePageNumber - 1][editingLanguageId] = description;

						instance.set('descriptions', descriptions);
						instance.set('localizedDescriptions', localizedDescriptions);
					},

					_onPageControlOptionClick: function(event) {
						var popover = this._getPopover();

						event.stopPropagation();

						if (!event.currentTarget.hasClass('disabled')) {
							popover.set(
								'align',
								{
									node: event.currentTarget,
									points: [A.WidgetPositionAlign.RC, A.WidgetPositionAlign.TC]
								}
							);

							popover.set('position', event.currentTarget.getData('position'));

							popover.toggle();
						}
					},

					_onRemovePageClick: function() {
						var instance = this;

						var wizard = instance._getWizard();

						if (!wizard.isSuccessPageSelected()) {
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

							var descriptions = instance.get('descriptions');

							descriptions.splice(activePageNumber - 1, 1);

							instance.set('descriptions', descriptions);

							var localizedTitles = instance.get('localizedTitles');

							localizedTitles.splice(activePageNumber - 1, 1);

							instance.set('localizedTitles', localizedTitles);

							var localizedDescriptions = instance.get('localizedDescriptions');

							localizedDescriptions.splice(activePageNumber - 1, 1);

							instance.set('localizedDescriptions', localizedDescriptions);

							instance.set('activePageNumber', page);

							instance._removeWizardPage(activePageNumber - 1);

							if (!instance.get('pagesQuantity')) {
								instance._addPage();
								instance._addWizardPage();

								wizard.activate(0);
							}
						}
						else {
							instance._removeSuccessPage();
						}
					},

					_onRemoveSuccessPageClick: function() {
						var instance = this;

						instance._removeSuccessPage();
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
						var editingLanguageId = instance.get('editingLanguageId');
						var titles = instance.get('titles');
						var localizedTitles = instance.get('localizedTitles');

						var title = event.newVal.trim();

						if (!title) {
							var pagesQuantity = instance.get('pagesQuantity');

							title = instance._createUntitledPageLabel(activePageNumber, pagesQuantity);
						}

						titles[activePageNumber - 1] = title;
						localizedTitles[activePageNumber - 1][editingLanguageId] = title;

						instance.set('titles', titles);
						instance.set('localizedTitles', localizedTitles);
					},

					_plugAutoSize: function(node) {
						var instance = this;

						if (!node.autosize) {
							var height = node.get('scrollHeight');

							node.plug(A.Plugin.Autosize);
							node.height(height);
						}

						node.autosize._uiAutoSize();
					},

					_removeSuccessPage: function() {
						var instance = this;

						var wizard = instance._getWizard();

						wizard.set('successPage', false);

						instance._uiSetMode(instance.get('mode'));

						instance._syncControlTriggersUI();

						var popoverBoundingBox = instance._getPopover().get('boundingBox');

						instance._resetSuccessPage();

						popoverBoundingBox.one('.' + CSS_FORM_BUILDER_PAGE_MANAGER_ADD_SUCCESS_PAGE).show();

						instance._syncPopoverContent();

						instance._showLayout();
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

						instance._setCharacterLimitToPageTitle(100);
						instance._setCharacterLimitToPageDescription(120);
					},

					_renderTopPagination: function() {
						var instance = this;

						var wizard = instance._getWizard();

						wizard.render();
					},

					_resetSuccessPage: function() {
						var instance = this;

						var builder = instance.get('builder');

						var boundingBox = builder.get('boundingBox');

						var successPage = boundingBox.one('.' + CSS_FORM_BUILDER_SUCCESS_PAGE);

						var strings = instance.get('strings');

						var successPageSettings = {
							body: {},
							enabled: instance._getWizard().get('successPage'),
							title: {}
						};

						var defaultLanguageId = instance.get('defaultLanguageId');

						successPageSettings.body[defaultLanguageId] = strings.defaultContent;
						successPageSettings.title[defaultLanguageId] = strings.defaultTitle;

						instance.set('successPageSettings', successPageSettings);

						successPage.one('.' + CSS_FORM_BUILDER_SUCCESS_PAGE_TITLE).val(strings.defaultTitle);

						successPage.one('.' + CSS_FORM_BUILDER_SUCCESS_PAGE_CONTENT).val(strings.defaultContent);
					},

					_setCharacterLimitToPageDescription: function(maxLength) {
						var instance = this;

						if (instance._charCounterPageDescription) {
							instance._charCounterPageDescription.set('maxLength', maxLength);
						}
						else {
							var pageHeader = instance.get('pageHeader').one('.' + CSS_PAGE_HEADER);

							instance._charCounterPageDescription = new A.CharCounter(
								{
									input: pageHeader.one('.' + CSS_PAGE_HEADER_DESCRIPTION),
									maxLength: maxLength
								}
							);
						}
					},

					_setCharacterLimitToPageTitle: function(maxLength) {
						var instance = this;

						if (instance._charCounterPageTitle) {
							instance._charCounterPageTitle.set('maxLength', maxLength);
						}
						else {
							var pageHeader = instance.get('pageHeader').one('.' + CSS_PAGE_HEADER);

							instance._charCounterPageTitle = new A.CharCounter(
								{
									input: pageHeader.one('.' + CSS_PAGE_HEADER_TITLE),
									maxLength: maxLength
								}
							);
						}
					},

					_showLayout: function() {
						var instance = this;

						var boundingBox = instance.get('builder').get('boundingBox');

						boundingBox.one('.' + CSS_LAYOUT).show();
						boundingBox.one('.' + CSS_PAGE_HEADER).show();

						instance._syncSuccessPage();

						boundingBox.one('.' + CSS_FORM_BUILDER_SUCCESS_PAGE).hide();
					},

					_showSuccessPage: function() {
						var instance = this;

						var boundingBox = instance.get('builder').get('boundingBox');

						boundingBox.one('.' + CSS_LAYOUT).hide();
						boundingBox.one('.' + CSS_PAGE_HEADER).hide();

						boundingBox.one('.' + CSS_FORM_BUILDER_SUCCESS_PAGE).show();

						instance._syncSuccessPage();
					},

					_syncControlTriggersUI: function() {
						var instance = this;

						var builder = instance.get('builder');
						var pageHeader = instance.get('pageHeader');
						var pagesQuantity = instance.get('pagesQuantity');

						var boundingBox = builder.get('boundingBox');

						var wizard = instance._getWizard();

						boundingBox.all('.' + CSS_FORM_BUILDER_CONTROLS_TRIGGER).toggle(pagesQuantity > 1 || wizard.get('successPage'));
						pageHeader.one('.' + CSS_FORM_BUILDER_CONTROLS_TRIGGER).toggle(pagesQuantity <= 1 && !wizard.get('successPage'));
					},

					_syncPageInformationHeight: function() {
						var instance = this;

						var pageHeader = instance.get('pageHeader');

						var pageDescription = pageHeader.one('.' + CSS_PAGE_HEADER_DESCRIPTION);
						var pageTitle = pageHeader.one('.' + CSS_PAGE_HEADER_TITLE);

						instance._plugAutoSize(pageDescription);
						instance._plugAutoSize(pageTitle);
					},

					_syncPopoverContent: function() {
						var instance = this;

						var deletePageButton = instance._getPopover().get('boundingBox').one('.' + CSS_FORM_BUILDER_PAGE_MANAGER_DELETE_PAGE);

						deletePageButton.text(instance._getDeleteButtonString());
					},

					_syncSuccessPage: function() {
						var instance = this;

						var builder = instance.get('builder');

						var boundingBox = builder.get('boundingBox');

						var successPage = boundingBox.one('.' + CSS_FORM_BUILDER_SUCCESS_PAGE);

						var editingLanguageId = instance.get('editingLanguageId');
						var defaultLanguageId = instance.get('defaultLanguageId');

						var successPageSettings = instance.get('successPageSettings');

						if (!successPageSettings.body[editingLanguageId]) {
							var strings = instance.get('strings');

							if (successPageSettings.body[defaultLanguageId]) {
								successPageSettings.body[editingLanguageId] = A.clone(successPageSettings.body[defaultLanguageId]);
							}
							else {
								successPageSettings.body[editingLanguageId] = strings.defaultContent;
							}

							if (successPageSettings.title[defaultLanguageId]) {
								successPageSettings.title[editingLanguageId] = A.clone(successPageSettings.title[defaultLanguageId]);
							}
							else {
								successPageSettings.title[editingLanguageId] = strings.defaultTitle;
							}

							instance.set('successPageSettings', successPageSettings);
						}

						successPage.one('.' + CSS_FORM_BUILDER_SUCCESS_PAGE_TITLE).val(successPageSettings.title[editingLanguageId]);
						successPage.one('.' + CSS_FORM_BUILDER_SUCCESS_PAGE_CONTENT).val(successPageSettings.body[editingLanguageId]);
					},

					_syncTitle: function() {
						var instance = this;

						instance._createTitleForEditingLanguageId();

						var editingLanguageId = instance.get('editingLanguageId');
						var defaultLanguageId = instance.get('defaultLanguageId');

						var titles = instance.get('localizedTitles');
						var descriptions = instance.get('localizedDescriptions');

						var pageHeader = instance.get('pageHeader');

						var activePageNumber = instance.get('activePageNumber');

						var titleNode = pageHeader.one('.' + CSS_PAGE_HEADER_TITLE);

						titleNode.val(titles[activePageNumber - 1][editingLanguageId] || titles[activePageNumber - 1][defaultLanguageId] || '');

						var descriptionNode = pageHeader.one('.' + CSS_PAGE_HEADER_DESCRIPTION);

						descriptionNode.val(descriptions[activePageNumber - 1][editingLanguageId] || descriptions[activePageNumber - 1][defaultLanguageId] || '');
					},

					_syncWizardItems: function() {
						var instance = this;

						var wizard = instance._getWizard();

						wizard.set('selected', instance.get('activePageNumber') - 1);
						wizard.set('items', instance._createWizardItems());
					},

					_toggleNodeDisabled: function(node, disabled) {
						if (disabled) {
							node.setAttribute('disabled', '');
						}
						else {
							node.removeAttribute('disabled');
						}
					},

					_toggleWizardDisabled: function(disabled) {
						var instance = this;

						instance._getWizard().set('disabled', disabled);
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

						if (instance.get('pagesQuantity') > 1 || wizard.get('successPage')) {
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

					_updateSuccessPageSettings: function() {
						var instance = this;

						var builder = instance.get('builder');

						var boundingBox = builder.get('boundingBox');

						var successPage = boundingBox.one('.' + CSS_FORM_BUILDER_SUCCESS_PAGE);

						var wizard = instance._getWizard();

						var successPageSettings = instance.get('successPageSettings');
						var editingLanguageId = instance.get('editingLanguageId');

						successPageSettings.enabled = wizard.get('successPage');

						successPageSettings.body[editingLanguageId] = successPage.one('.' + CSS_FORM_BUILDER_SUCCESS_PAGE_CONTENT).val();
						successPageSettings.title[editingLanguageId] = successPage.one('.' + CSS_FORM_BUILDER_SUCCESS_PAGE_TITLE).val();

						instance.set('successPageSettings', successPageSettings);

						return successPageSettings;
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
		requires: ['aui-autosize-deprecated', 'aui-char-counter', 'aui-form-builder-page-manager', 'liferay-ddl-form-builder-wizard']
	}
);