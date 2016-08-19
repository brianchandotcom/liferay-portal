AUI.add(
	'liferay-ddl-form-builder-modal-support',
	function(A) {
		var FormBuilderModalSupport = function() {
		};

		FormBuilderModalSupport.ATTRS = {
			centered: {
				valueFn: '_valueCentered'
			},

			portletNamespace: {
			},

			zIndex: {
				value: Liferay.zIndex.OVERLAY
			}
		};

		FormBuilderModalSupport.prototype = {
			initializer: function() {
				var instance = this;

				instance._eventHandles.push(
					instance.after(instance._bindModalUI, instance, 'bindUI')
				);
			},

			_afterWindowResize: function() {
				var instance = this;

				if (instance.get('visible')) {
					if (instance.get('centered')) {
						instance.align();
					}
				}
			},

			_bindModalUI: function() {
				var instance = this;

				instance._eventHandles.push(
					instance.on('xyChange', instance._onModalXYChange)
				);
			},

			_centerYAxis: function(xy) {
				var instance = this;

				var contentBox = instance.get('contentBox');

				xy[1] = (A.config.win.pageYOffset - contentBox.outerHeight(true) / 2) + (A.config.win.innerHeight / 2);

				return xy;
			},

			_onModalXYChange: function(event) {
				var instance = this;

				if (instance.get('centered')) {
					event.newVal = instance._centerYAxis(event.newVal);
				}
			},

			_valueCentered: function() {
				var instance = this;

				var portletNode = A.one('#p_p_id' + instance.get('portletNamespace'));

				instance.set('centered', portletNode);
			}
		};

		Liferay.namespace('DDL').FormBuilderModalSupport = FormBuilderModalSupport;
	},
	'',
	{
		requires: ['aui-modal']
	}
);