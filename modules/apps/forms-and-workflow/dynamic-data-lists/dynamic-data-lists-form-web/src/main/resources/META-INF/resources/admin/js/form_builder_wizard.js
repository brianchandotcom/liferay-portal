AUI.add(
	'liferay-ddl-form-builder-wizard',
	function(A) {
		var TPL_WIZARD_SUCCESSPAGE = '<li class="{state}">' +
				'<div class="progress-bar-title">{title}</div>' +
				'<div class="progress-bar-success-item divider"></div>' +
			'</li>';

		var FormBuilderWizard = A.Component.create(
			{
				ATTRS: {
					successPage: {
						value: false
					}
				},

				EXTENDS: Liferay.DDM.Renderer.Wizard,

				NAME: 'liferay-ddl-form-builder-wizard',

				prototype: {
					initializer: function() {
						var instance = this;

						instance._activeSuccessPage = false;

						instance.after('successPageChange', A.bind(instance._afterSuccessPageChange, instance));
					},

					_afterSuccessPageChange: function() {
						var instance = this;

						var contentBox = instance.get('contentBox');

						contentBox.empty();

						contentBox.append(instance._getItemsNodeList(instance.get('items')));
					},

					_getItemsNodeList: function(items) {
						var instance = this;

						var itemsNodeList = FormBuilderWizard.superclass._getItemsNodeList.apply(instance, arguments);

						if (instance.get('successPage')) {
							itemsNodeList.push(
								A.Node.create(
									A.Lang.sub(
										TPL_WIZARD_SUCCESSPAGE,
										{
											state: instance._activeSuccessPage ? 'active' : '',
											title: 'Success Page'
										}
									)
								)
							);
						}

						return itemsNodeList;
					},

					_setState: function(index, state) {
						var instance = this;

						var items = instance.get('items');

						if (items[index]) {
							items[index].state = state;

							instance._activeSuccessPage = false;

							instance.set('items', items);
						}
						else {
							instance._activeSuccessPage = true;

							instance.set('items', items);
						}
					}
				}
			}
		);

		Liferay.namespace('DDL').FormBuilderWizard = FormBuilderWizard;
	},
	'',
	{
		requires: ['liferay-ddm-form-renderer-wizard']
	}
);