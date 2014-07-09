AUI.add(
	'liferay-layouts-tree-selectable',
	function(A) {
		var Lang = A.Lang;

		var LABEL_TPL = '<span class="{cssClass}" title="{title}">{label}</span>';

		var STR_DEFAULT_STATE = 'defaultState';

		var STR_HOST = 'host';

		var LayoutsTreeSelectable = A.Component.create(
			{
				ATTRS: {
					defaultState: {
						validator: Lang.isBoolean,
						value: false
					}
				},

				EXTENDS: A.Plugin.Base,

				NAME: 'layoutstreeselectable',

				NS: 'selectable',

				prototype: {
					initializer: function(config) {
						var instance = this;

						instance._eventHandles = [
							instance.afterHostEvent('*:checkedChange', instance._onNodeCheckedChange, instance),
							instance.afterHostEvent('*:childrenChange', instance._onNodeChildrenChange, instance),
							instance.afterHostEvent('append', instance._onTreeAppend, instance),
							instance.afterHostEvent('render', instance._onTreeRender, instance),
							instance.doAfter('_formatNode', instance._formatNode, instance),
							instance.doAfter('_formatNodeLabel', instance._formatNodeLabel, instance),
							instance.doAfter('_formatRootNode', instance._formatRootNode, instance)
						];
					},

					destructor: function() {
						var instance = this;

						(new A.EventHandle(instance._eventHandles)).detach();
					},

					_formatNode: function(node) {
						var instance = this;

						var currentRetVal = A.Do.currentRetVal;

						return new A.Do.AlterReturn(
							'Modified checked and type attributes',
							A.merge(
								currentRetVal,
								{
									checked: instance.get(STR_DEFAULT_STATE),
									type: 'task'
								}
							)
						);
					},

					_formatNodeLabel: function(node, cssClass, label, title) {
						return new A.Do.AlterReturn(
							'Modified node label',
							Lang.sub(
								LABEL_TPL,
								{
									cssClass: cssClass,
									label: label,
									title: title
								}
							)
						);
					},

					_formatRootNode: function(rootConfig, children) {
						var instance = this;

						return new A.Do.AlterReturn(
							'Modified checked, label and type attributes',
							A.merge(
								A.Do.currentRetVal,
								{
									checked: instance.get(STR_DEFAULT_STATE),
									label: rootConfig.label,
									type: 'task'
								}
							)
						);
					},

					_onNodeCheckedChange: function(event) {
						var instance = this;

						var host = instance.get(STR_HOST);

						if (event.target === event.originalTarget) {
							host.fire(
								'selectableNodeCheckedChange',
								{
									checked: event.newVal,
									node: event.target
								}
							);
						}
					},

					_onNodeChildrenChange: function(event) {
						var instance = this;

						var host = instance.get(STR_HOST);

						host.fire(
							'selectableNodeChildrenChange',
							{
								node: event.target
							}
						);
					},

					_onTreeAppend: function(event) {
						var instance = this;

						var host = instance.get(STR_HOST);

						host.fire(
							'selectableTreeAppend',
							{
								node: event.tree.node
							}
						);
					},

					_onTreeRender: function(event) {
						var instance = this;

						var host = instance.get(STR_HOST);

						host.fire('selectableTreeRender');
					}
				}
			}
		);

		A.Plugin.LayoutsTreeSelectable = LayoutsTreeSelectable;
	},
	'',
	{
		requires: ['aui-base']
	}
);