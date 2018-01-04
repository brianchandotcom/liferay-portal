AUI.add(
	'liferay-ddm-form-builder-pagination',
	function(A) {
		var FormBuilderPagination = A.Component.create(
			{
				ATTRS: {
					successPage: {
						value: false
					}
				},

				EXTENDS: A.Pagination,

				NAME: 'liferay-ddm-form-builder-pagination',

				prototype: {
					CONTENT_TEMPLATE: '<ul class="pagination"></ul>',
					ITEM_TEMPLATE: '<li class="{cssClass}"><a href="#">{content}</a></li>',
					SUCCESS_PAGE_ITEM_TEMPLATE: '<li class="{cssClass}" data-success-page="true"><a href="#">{content}</a></li>',

					initializer: function() {
						var instance = this;

						instance.after('successPageChange', A.bind(instance._afterSuccessPageChange, instance));
					},

					next: function() {
						var instance = this;

						var	total = instance.get('total');

						if (instance.get('successPage')) {
							total++;
						}

						if (total === 0) {
							return;
						}

						var page = instance.get('page');

						instance._dispatchRequest(
							{
								page: (instance.get('circular') && (page === total)) ? 1 : Math.min(total, ++page)
							}
						);
					},

					prev: function() {
						var instance = this;

						var	total = instance.get('total');

						if (instance.get('successPage')) {
							total++;
						}

						if (total === 0) {
							return;
						}

						var page = instance.get('page');

						instance._dispatchRequest(
							{
								page: (instance.get('circular') && (page === 1)) ? total : Math.max(1, --page)
							}
						);
					},

					_afterSuccessPageChange: function() {
						var instance = this;

						instance._renderItemsUI(instance.get('total'));
					},

					_renderItemsUI: function(total) {
						var instance = this;

						var	offset = instance.get('offset');

						var buffer = A.Lang.sub(
							instance.ITEM_TEMPLATE,
							{
								content: instance.getString('prev'),
								cssClass: 'pagination-control'
							}
						);

						for (var i = offset; i <= (offset + total - 1); i++) {
							buffer += instance.get('formatter').apply(instance, [i]);
						}

						if (instance.get('successPage')) {
							buffer += A.Lang.sub(
								instance.SUCCESS_PAGE_ITEM_TEMPLATE,
								{
									content: Liferay.Language.get('success-page'),
									cssClass: 'pagination-success-page'
								}
							);
						}

						buffer += A.Lang.sub(
							instance.ITEM_TEMPLATE,
							{
								content: instance.getString('next'),
								cssClass: 'pagination-control'
							}
						);

						var items = A.NodeList.create(buffer);

						instance.set('items', items);
						instance.get('contentBox').setContent(items);

						if (!instance.get('showControls')) {
							items.first().remove();
							items.last().remove();
						}
					}
				}
			}
		);

		Liferay.namespace('DDM').FormBuilderPagination = FormBuilderPagination;
	},
	'',
	{
		requires: ['liferay-ddm-form-renderer-pagination']
	}
);