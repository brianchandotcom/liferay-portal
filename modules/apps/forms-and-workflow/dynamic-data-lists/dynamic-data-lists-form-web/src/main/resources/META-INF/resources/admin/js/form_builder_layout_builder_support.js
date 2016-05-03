AUI.add(
	'liferay-ddl-form-builder-layout-builder-support',
	function(A) {
		var FormBuilderLayoutBuilderSupport = function() {
		};

		FormBuilderLayoutBuilderSupport.ATTRS = {
		};

		FormBuilderLayoutBuilderSupport.prototype = {
			initializer: function() {
				var instance = this;

				var boundingBox = instance.get('boundingBox');

				instance._eventHandlers.push(
					instance.after('render', instance._afterMainClassRender, instance),
					boundingBox.delegate('mouseenter', A.bind(instance._showCutRowHelperMessage, instance), '.layout-builder-move-cut-row-button'),
					boundingBox.delegate('mouseleave', A.bind(instance._hideCutRowHelperMessage, instance), '.layout-builder-move-cut-row-button')
				);
			},

			_afterMainClassRender: function() {
				var instance = this;

				instance._createPopoverHelperMessage();
			},

			_createPopoverHelperMessage: function() {
				var instance = this;

				var popOver = new A.Popover(
					{
						constrain: true,
						position: 'top',
						visible: false,
						zIndex: 50
					}
				).render();

				instance._popoverHelperMessage = popOver;
			},

			_hideCutRowHelperMessage: function() {
				var instance = this;

				instance._popoverHelperMessage.hide();
			},

			_showCutRowHelperMessage: function(event) {
				var instance = this;

				var alignToNode = {
					node: event.currentTarget,
					points: [A.WidgetPositionAlign.RC, A.WidgetPositionAlign.TC]
				};

				instance._popoverHelperMessage.set('bodyContent', Liferay.Language.get('click-to-cut-row'));
				instance._popoverHelperMessage.set('align', alignToNode);
				instance._popoverHelperMessage.show();
			}
		};

		Liferay.namespace('DDL').FormBuilderLayoutBuilderSupport = FormBuilderLayoutBuilderSupport;
	},
	'',
	{
		requires: []
	}
);