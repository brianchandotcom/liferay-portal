AUI.add(
	'liferay-layouts-tree-check-content-display-page',
	function(A) {
		var CSS_LAYOUT_INVALID = 'layout-page-invalid';

		var CSS_TREE_HITAREA = A.getClassName('tree', 'hitarea');

		var STR_HOST = 'host';

		var LayoutsTreeCheckContentDisplayPage = A.Component.create(
			{
				EXTENDS: A.Plugin.Base,

				NAME: 'layoutstreecheckcontentdisplaypage',

				NS: 'checkContentDisplayPage',

				prototype: {
					initializer: function() {
						var instance = this;

						var host = instance.get(STR_HOST);

						instance._eventHandles = [
							instance.afterHostEvent('append', instance._onTreeAppend, instance),
							instance.doAfter('_formatRootNode', instance._formatRootNode, instance),
							instance.doBefore('_formatNodeLabel', instance._beforeFormatNodeLabel, instance),
							instance.doBefore('_onClickNodeEl', instance._beforeClickNodeEl, instance)
						];

						host.get('boundingBox').addClass('lfr-tree-display-page');
					},

					destructor: function() {
						var instance = this;

						(new A.EventHandle(instance._eventHandles)).detach();
					},

					_beforeClickNodeEl: function(event) {
						var instance = this;

						if (!event.target.test('.' + CSS_TREE_HITAREA)) {
							var link = event.currentTarget.one('a');

							if (!link || link.hasClass(CSS_LAYOUT_INVALID)) {
								return new A.Do.Halt();
							}
						}
					},

					_beforeFormatNodeLabel: function(node, cssClass, label, title) {
						if (!node.contentDisplayPage) {
							cssClass = cssClass + ' ' + CSS_LAYOUT_INVALID;

							return new A.Do.AlterArgs(
								'Added layout-page-invalid CSS class',
								[
									node,
									cssClass,
									label,
									Liferay.Language.get('this-page-is-not-a-content-display-page')
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