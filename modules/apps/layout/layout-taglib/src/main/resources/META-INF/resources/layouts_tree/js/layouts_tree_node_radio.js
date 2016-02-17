AUI.add(
	'liferay-layouts-tree-node-radio',
	function(A) {
		var TPL_RADIO = '<label><input type="radio"></label>';

		var LayoutsTreeNodeRadio = A.Component.create(
			{
				ATTRS: {
					checkEl: {
						setter: A.one,
						valueFn: '_valueCheckEl'
					}
				},

				EXTENDS: A.TreeNodeRadio,

				NAME: 'layoutstreenoderadio',

				prototype: {
					renderUI: function() {
						var instance = this;

						LayoutsTreeNodeRadio.superclass.renderUI.apply(instance, arguments);

						var checkEl = instance.get('checkEl');

						checkEl.append(instance.get('label'));
						checkEl.show();

						var labelEl = instance.get('labelEl');

						labelEl.empty();
						labelEl.addClass('radio');

						var checkContainerEl = instance.get('checkContainerEl');

						checkContainerEl.removeClass('tree-node-checkbox-container');

						labelEl.append(checkContainerEl);

						instance.get('hitAreaEl').remove();
						instance.get('iconEl').remove();
					},

					_uiSetChecked: function(val) {
						var instance = this;

						instance.get('checkEl').one('input').attr('checked', val ? 'checked' : '');
					},

					_valueCheckEl: function() {
						var instance = this;

						var checkName = instance.get('checkName');

						return A.Node.create(TPL_RADIO).attr('name', checkName);
					}
				}
			}
		);

		A.LayoutsTreeNodeRadio = LayoutsTreeNodeRadio;

		A.TreeNode.nodeTypes['liferay-radio'] = LayoutsTreeNodeRadio;
	},
	'',
	{
		requires: ['aui-tree-node']
	}
);