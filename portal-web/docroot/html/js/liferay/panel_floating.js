AUI.add(
	'liferay-panel-floating',
	function(A) {

		/**
		 * OPTIONS
		 *
		 * Also inherits all configuration options from Liferay.Panel
		 *
		 * Optional
		 * trigger {string|object}: A selector of the element that triggers the opening of the floating panel.
		 * paging {boolean}: Whether or not to add pagination to the panel.
		 * pagingElements {string}: A selector of the elements that make up each "page".
		 * resultsPerPage {number}: The number of results to show per page.
		 * width {number}: The width of the panel.
		 *
		 */

		var PanelFloating = A.Component.create(
			{
				EXTENDS: Liferay.Panel,

				NAME: 'liferaypanelfloating',

				constructor: function(options) {
					var instance = this;

					var defaults = {
						trigger: '.lfr-trigger',
						paging: false,
						pagingElements: 'ul',
						resultsPerPage: 1,
						width: 300
					};

					options = A.merge(defaults, options);

					instance._paging = options.paging;
					instance._pagingElements = options.pagingElements;
					instance._trigger = A.one(options.trigger);
					instance._containerWidth = options.width;

					PanelFloating.superclass.constructor.apply(instance, arguments);

					if (!instance._inContainer) {
						instance._container = A.Node.create('<div class="lfr-floating-container aui-helper-hidden"></div>');

						instance._panel.item(0).placeBefore(instance._container);
						instance._container.append(instance._panel);

						instance._inContainer = true;
					}

					instance._positionHelper = A.Node.create('<div class="lfr-position-helper"></div>');
					instance._positionHelper.append(instance._container);

					instance._positionHelper._hideClass = 'aui-helper-hidden-accessible';

					A.getBody().prepend(instance._positionHelper);

					instance._positionHelper.hide();

					instance.paginate(instance._container.all('.lfr-panel-content'));

					instance._trigger.addClass('lfr-floating-trigger');

					instance._hideAllPanels = function(event) {
						var target = event.target;

						if (!target.hasClass('lfr-panel') && !target.ancestor('.lfr-position-helper')) {
							instance.fire(
								'outerClick',
								{
									targetEl: target
								}
							);

							A.getDoc().detach('click', instance._hideAllPanels);
						}

						event.stopPropagation();
					};

					instance._trigger.on(
						'click',
						function(event) {
							instance.fire(
								'triggerClick',
								{
									trigger: event.target
								}
							);

							A.getDoc().on('click', instance._hideAllPanels);

							event.stopPropagation();
						}
					);

					instance.publish(
						'hide',
						{
							defaultFn: instance._defHideFn
						}
					);

					instance.publish(
						'outerClick',
						{
							defaultFn: instance._defOuterClickFn
						}
					);

					instance.publish(
						'show',
						{
							defaultFn: instance._defShowFn
						}
					);

					instance.publish(
						'triggerClick',
						{
							defaultFn: instance._defTriggerClickFn
						}
					);

					instance.set('trigger', instance._trigger);

					Liferay.on('submitForm', instance.hide, instance);
				},

				prototype: {
					hide: function() {
						var instance = this;

						instance.fire('hide');
					},

					onTitleClick: function(el) {
						var instance = this;

						PanelFloating.superclass.onTitleClick.apply(instance, arguments);

						var currentContainer = A.one(el).ancestor('.lfr-panel');
						var sets = currentContainer.all('ul');

						if (sets.size() && !sets.all('.current-set').size()) {
							sets.item(0).addClass('current-set');
						}
					},

					paginate: function(currentPanelContent) {
						var instance = this;

						if (instance._paging) {
							currentPanelContent.each(
								function(item, index, collection) {
									var pages = item.all('ul');
									var totalPages = pages.size();

									if (totalPages > 1) {
										var paginatorContainer = A.Node.create('<div class="paginator-container"></div>');

										item.append(paginatorContainer);

										var paginatorInstance = new A.Paginator(
											{
												TPL: {
													defaultOutput: '<span class="lfr-paginator-prev">{PrevPageLink}</span>{PageLinks}<span class="lfr-paginator-next">{NextPageLink}</span>'
												},
												containers: paginatorContainer,
												on: {
													changeRequest: function(newState) {
														var page = newState.state.page;
														var showPage = Math.max(0, page - 1);

														pages.hide();
														pages.item(showPage).show();

														this.setState(newState);
													}
												},
												total: totalPages
											}
										).render();

										instance._container.addClass('lfr-panel-paging');
									}
								}
							);
						}
					},

					position: function(trigger) {
						var instance = this;

						trigger = A.one(trigger);

						var container = instance._container;
						var positionHelper = instance._positionHelper;

						var triggerHeight = trigger.get('offsetHeight');
						var triggerWidth = trigger.get('offsetWidth');

						var triggerOffset = trigger.getXY();

						positionHelper.setStyles(
							{
								height: triggerHeight + 'px',
								width: triggerWidth + 'px'
							}
						);

						positionHelper.show();
						container.show();

						positionHelper.setStyles(
							{
								left: triggerOffset[0] + 'px',
								top: triggerOffset[1] + triggerHeight + 'px'
							}
						);
					},

					show: function() {
						var instance = this;

						instance.fire('show');
					},

					_defHideFn: function(event) {
						var instance = this;

						instance._positionHelper.hide();

						instance._trigger.removeClass('lfr-trigger-selected');
					},

					_defOuterClickFn: function(event) {
						var instance = this;

						instance.fire('hide', event);
					},

					_defShowFn: function(event) {
						var instance = this;

						var trigger = event.trigger || instance._trigger;

						instance._container.setStyle('width', instance._containerWidth + 'px');

						instance.position(trigger);

						trigger.addClass('lfr-trigger-selected');

						if (instance._paging) {
							instance._setMaxPageHeight();
						}
					},

					_defTriggerClickFn: function(event) {
						var instance = this;

						var eventType = 'hide';

						if (instance._positionHelper.test(':hidden')) {
							eventType = 'show';
						}

						instance.fire(eventType, event);
					},

					_setMaxPageHeight: function() {
						var instance = this;

						var sets = instance._container.all('.lfr-panel').filter(
							function(node) {
								return !node.hasClass('lfr-collapsed');
							}
						);

						var maxHeight = 0;

						var panelContent = sets.all('.lfr-panel-content');
						var pages = panelContent.all('>' + instance._pagingElements);

						pages.each(
							function(item, index, collection) {
								var setHeight = item.get('offsetHeight');

								if (setHeight > maxHeight) {
									maxHeight = setHeight;
								}
							}
						);

						pages.setStyle('height', maxHeight + 'px');
					}
				}
			}
		);

		Liferay.PanelFloating = PanelFloating;
	},
	'',
	{
		requires: ['aui-paginator', 'liferay-panel']
	}
);