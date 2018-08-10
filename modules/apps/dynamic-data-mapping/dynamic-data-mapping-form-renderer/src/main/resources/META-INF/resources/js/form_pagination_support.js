AUI.add(
	'liferay-ddm-form-renderer-pagination',
	function(A) {
		var Renderer = Liferay.DDM.Renderer;

		var FormPaginationSupport = function() {
		};

		FormPaginationSupport.ATTRS = {
			pagesState: {
				setter: '_setPagesSate',
				valueFn: '_valuePagesState'
			}
		};

		FormPaginationSupport.prototype = {
			initializer: function() {
				var instance = this;

				instance.after('render', instance._afterPaginatedFormRender);
			},

			destructor: function() {
				var instance = this;

				if (instance.pagination) {
					instance.pagination.destroy();
					instance.pagination = null;
				}
			},

			getCurrentPage: function() {
				var instance = this;

				var pagination = instance.getPagination();

				return pagination.get('page');
			},

			getCurrentPageNode: function() {
				var instance = this;

				return instance.getPageNode(instance.getCurrentPage());
			},

			getFirstPageField: function() {
				var instance = this;

				var firstField;

				var pageNode = instance.getCurrentPageNode();

				instance.eachNestedField(
					function(field) {
						var visible = field.get('visible');

						if (visible && pageNode.contains(field.get('container'))) {
							firstField = field;
						}

						return !!firstField;
					}
				);

				return firstField;
			},

			getPageNode: function(page) {
				var instance = this;

				var pages = instance._getPaginationNodes();

				return pages.item(page - 1);
			},

			getPagesTotal: function() {
				var instance = this;

				return instance._getPaginationNodes().size();
			},

			getPagination: function() {
				var instance = this;

				if (!instance.pagination) {
					instance.pagination = new A.Pagination(
						{
							circular: false,
							page: 1,
							total: instance.getPagesTotal()
						}
					);

					instance._eventHandlers.push(
						instance.pagination.after('pageChange', A.bind('_afterPaginationPageChange', instance)),
						instance.pagination.on('changeRequest', A.bind('_onPaginationChangeRequest', instance))
					);
				}

				return instance.pagination;
			},

			nextPage: function() {
				var instance = this;

				var pagination = instance.getPagination();

				var pages = instance.get('pagesState');

				var page;

				var pageIndex = pagination.get('page');

				instance.validatePage(
					instance.getPageNode(pageIndex),
					function(hasErrors) {
						if (!hasErrors) {
							do {
								page = pages[pageIndex];
								pageIndex++;
							} while (!page.enabled);

							pagination.set('page', pageIndex);
						}
					}
				);
			},

			prevPage: function() {
				var instance = this;

				var pagination = instance.getPagination();

				var pages = instance.get('pagesState');

				if (!pages.length) {
					pagination.prev();
					return;
				}

				var page;

				var prevPage = pagination.get('page') - 2;

				do {
					page = pages[prevPage];
					prevPage--;
				} while (!page.enabled);

				pagination.set('page', prevPage + 2);
			},

			showPage: function(page) {
				var instance = this;

				var pagination = instance.getPagination();

				pagination.setState(
					{
						page: page
					}
				);

				instance._firePageViewedEvent(page);
			},

			_afterPaginatedFormRender: function() {
				var instance = this;

				var controls = instance._getPaginationControlsNode();

				if (controls) {
					instance._eventHandlers.push(
						controls.delegate('click', A.bind('_onClickPaginationControls', instance), 'button')
					);

					instance._syncPaginationControlsUI();
				}

				var container = instance.get('container');

				var paginatedNode = container.one('.lfr-ddm-form-paginated');

				if (container.inDoc() && paginatedNode) {
					instance.paginated = new Renderer.Paginated(
						{
							boundingBox: paginatedNode,
							srcNode: paginatedNode.one('> ul')
						}
					).render();
				}

				var wizardNode = container.one('.lfr-ddm-form-wizard');

				if (container.inDoc() && wizardNode) {
					instance.wizard = new Renderer.Wizard(
						{
							boundingBox: wizardNode,
							srcNode: wizardNode.one('> ul')
						}
					).render();
				}

				var context = instance.get('context');

				var currentPage = context.currentPage;

				if (currentPage) {
					instance.showPage(currentPage);

					instance._syncPaginatedUI(currentPage - 1, currentPage);
					instance._syncWizardUI(currentPage - 1, currentPage);
				}
			},

			_afterPaginationPageChange: function(event) {
				var instance = this;

				var pages = instance._getPaginationNodes();

				pages.item(event.prevVal - 1).removeClass('active');
				pages.item(event.newVal - 1).addClass('active');

				var controls = instance._getPaginationControlsNode();

				if (controls) {
					instance._syncPaginationControlsUI();

					instance._syncPaginatedUI(event.prevVal, event.newVal);
					instance._syncWizardUI(event.prevVal, event.newVal);
				}

				var formId = instance.getFormId();

				if (formId > 0) {
					Liferay.fire(
						'ddmFormPageHide',
						{
							formId: formId,
							page: event.prevVal - 1
						}
					);

					instance._firePageViewedEvent(event.newVal);
				}

				var firstField = instance.getFirstPageField();

				if (firstField) {
					firstField.focus();
				}
			},

			_firePageViewedEvent: function(page) {
				var instance = this;

				var formId = instance.getFormId();

				if (formId > 0) {
					var context = instance.get('context');

					var pageContext = context.pages[page - 1];

					var pageTitle = pageContext.title;

					Liferay.fire(
						'ddmFormPageShow',
						{
							formId: formId,
							page: page - 1,
							title: pageTitle
						}
					);
				}
			},

			_getPaginationControlsNode: function() {
				var instance = this;

				var container = instance.get('container');

				return container.one('.lfr-ddm-form-pagination-controls');
			},

			_getPaginationNextButton: function() {
				var instance = this;

				var controls = instance._getPaginationControlsNode();

				return controls.one('.lfr-ddm-form-pagination-next');
			},

			_getPaginationNodes: function() {
				var instance = this;

				var container = instance.get('container');

				return container.all('.lfr-ddm-form-page');
			},

			_getPaginationPrevButton: function() {
				var instance = this;

				var controls = instance._getPaginationControlsNode();

				return controls.one('.lfr-ddm-form-pagination-prev');
			},

			_onClickPaginationControls: function(event) {
				var instance = this;

				var currentTarget = event.currentTarget;

				if (currentTarget.hasClass('lfr-ddm-form-pagination-prev')) {
					instance.prevPage();
				}
				else if (currentTarget.hasClass('lfr-ddm-form-pagination-next')) {
					instance.nextPage();
				}
			},

			_onPaginationChangeRequest: function(event) {
				var instance = this;

				var currentPage = instance.getCurrentPage();

				var nextPage = event.state.page;

				var pagination = instance.getPagination();

				if (nextPage > currentPage) {
					event.preventDefault();

					pagination.set('page', nextPage);
				}
				else {
					pagination.set('page', nextPage);
				}
			},

			_setPagesSate: function(pages) {
				var pagesState = [];

				for (var i = 0; i < pages.length; i++) {
					if (pages[i].enabled === undefined) {
						pagesState[i] = {
							enabled: true
						};
					}
					else {
						pagesState[i] = {
							enabled: pages[i].enabled
						};
					}
				}

				return pagesState;
			},

			_syncPaginatedUI: function(prevPage, currentPage) {
				var instance = this;

				var paginated = instance.paginated;

				if (paginated) {
					if (currentPage > prevPage) {
						paginated.complete(prevPage - 1);
					}
					else {
						paginated.clear(prevPage - 1);
					}

					paginated.activate(currentPage - 1);
				}
			},

			_syncPaginationControlsUI: function() {
				var instance = this;

				var currentPage = instance.getCurrentPage();
				var pagesTotal = instance.getPagesTotal();

				var nextButton = instance._getPaginationNextButton();
				var prevButton = instance._getPaginationPrevButton();

				if (pagesTotal == 1) {
					nextButton.hide();
					prevButton.hide();
				}
				else if (currentPage === 1) {
					nextButton.show();
					prevButton.hide();
				}
				else if (currentPage == pagesTotal) {
					nextButton.hide();
					prevButton.show();
				}
				else {
					nextButton.show();
					prevButton.show();
				}

				var submitButton = instance.getSubmitButton();

				if (submitButton) {
					var readOnly = instance.get('readOnly');

					submitButton.toggle(currentPage === pagesTotal && !readOnly);
				}
			},

			_syncWizardUI: function(prevPage, currentPage) {
				var instance = this;

				var wizard = instance.wizard;

				if (wizard) {
					if (currentPage > prevPage) {
						wizard.complete(prevPage - 1);
					}
					else {
						wizard.clear(prevPage - 1);
					}

					wizard.activate(currentPage - 1);
				}
			},

			_valuePagesState: function() {
				var instance = this;

				return instance.get('context').pages;
			}
		};

		Liferay.namespace('DDM.Renderer').FormPaginationSupport = FormPaginationSupport;
	},
	'',
	{
		requires: ['aui-pagination', 'liferay-ddm-form-renderer-paginated', 'liferay-ddm-form-renderer-wizard']
	}
);