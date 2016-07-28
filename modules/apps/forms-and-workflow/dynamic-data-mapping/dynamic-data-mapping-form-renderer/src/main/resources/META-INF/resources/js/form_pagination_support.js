AUI.add(
	'liferay-ddm-form-renderer-pagination',
	function(A) {
		var Renderer = Liferay.DDM.Renderer;

		var FormPaginationSupport = function() {
		};

		FormPaginationSupport.prototype = {
			initializer: function() {
				var instance = this;

				instance.after('render', instance._afterPaginatedFormRender);
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

				instance.eachField(
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

				pagination.next();
			},

			prevPage: function() {
				var instance = this;

				var pagination = instance.getPagination();

				pagination.prev();
			},

			showPage: function(page) {
				var instance = this;

				var pagination = instance.getPagination();

				pagination.setState(
					{
						page: page
					}
				);
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

				var wizardNode = container.one('.lfr-ddm-form-wizard');

				if (container.inDoc() && wizardNode) {
					instance.wizard = new Renderer.Wizard(
						{
							boundingBox: wizardNode,
							srcNode: wizardNode.one('> ul')
						}
					).render();
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
					instance._syncWizardUI(event.prevVal, event.newVal);
				}

				var firstField = instance.getFirstPageField();

				if (firstField) {
					firstField.focus();
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

					var pages = instance._getPaginationNodes();

					instance.validatePage(
						pages.item(currentPage - 1),
						function(hasErrors) {
							if (!hasErrors) {
								pagination.set('page', nextPage);
							}
						}
					);
				}
				else {
					pagination.set('page', nextPage);
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
			}
		};

		Liferay.namespace('DDM.Renderer').FormPaginationSupport = FormPaginationSupport;
	},
	'',
	{
		requires: ['aui-pagination', 'liferay-ddm-form-renderer-wizard']
	}
);