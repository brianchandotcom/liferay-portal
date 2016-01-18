AUI.add(
	'liferay-layouts-tree-node-task',
	function(A) {
		var LayoutsTreeNodeTask = A.Component.create(
			{
				EXTENDS: A.TreeNodeTask,

				NAME: 'layoutstreenodetask',

				prototype: {
					renderUI: function() {
						var instance = this;

						LayoutsTreeNodeTask.superclass.renderUI.apply(instance, arguments);

						var checkEl = instance.get('checkEl');

						if (checkEl) {
							checkEl.remove();
						}
					},

					toggleCheck: function() {
						var instance = this;

						var checked = instance.get('checked');

						if (checked) {
							instance.uncheck();
						}
						else {
							instance.check();
						}
					},

					_uiSetChecked: function(val) {
						var instance = this;

						instance._syncIconCheckUI();

						instance.get('contentBox').toggleClass(A.getClassName('tree', 'node', 'checked'), val);
					}
				}
			}
		);

		A.LayoutsTreeNodeTask = LayoutsTreeNodeTask;

		A.TreeNode.nodeTypes['liferay-task'] = LayoutsTreeNodeTask;
	},
	'',
	{
		requires: ['aui-tree-node']
	}
);