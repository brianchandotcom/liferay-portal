AUI.add(
	'liferay-layouts-tree-check-content-display-page',
	function(A) {
		var STR_HOST = 'host';

		var LayoutsTreeCheckContentDisplayPage = A.Component.create(
			{
				EXTENDS: A.Plugin.Base,

				NAME: 'layoutstreecheckcontentdisplaypage',

				NS: 'checkContentDisplayPage',

				prototype: {
					initializer: function() {
						var instance = this;

						instance._eventHandles = [
							instance.afterHostEvent('append', instance._onTreeAppend, instance),
							instance.doAfter('_formatRootNode', instance._formatRootNode, instance),
							instance.doBefore('_formatNodeLabel', instance._beforeFormatNodeLabel, instance)
						];
					},

					destructor: function() {
						var instance = this;

						(new A.EventHandle(instance._eventHandles)).detach();
					},

					_beforeFormatNodeLabel: function(node, cssClass, label, title) {
						if (!node.contentDisplayPage) {
							cssClass += ' layout-page-invalid';

							return new A.Do.AlterArgs(
								'Added layout-page-invalid CSS class',
								[
									node,
									cssClass,
									label,
									title
								]
							);
						}
					},

					_formatRootNode: function(rootConfig, children) {
						var instance = this;

						return new A.Do.AlterReturn(
							'Modified label attribute',
							A.merge(
								A.Do.currentRetVal,
								{
									label: instance.get(STR_HOST).get('root').label
								}
							)
						);
					},

					_onTreeAppend: function(event) {
						var instance = this;

						var host = instance.get(STR_HOST);

						host.fire(
							'checkContentDisplayTreeAppend',
							{
								node: event.tree.node
							}
						);
					}
				}
			}
		);

		A.Plugin.LayoutsTreeCheckContentDisplayPage = LayoutsTreeCheckContentDisplayPage;
	},
	'',
	{
		requires: ['aui-base']
	}
);