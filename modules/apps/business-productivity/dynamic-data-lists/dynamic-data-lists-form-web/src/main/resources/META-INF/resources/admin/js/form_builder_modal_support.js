AUI.add(
	'liferay-ddl-form-builder-modal-support',
	function(A) {

		var CSS_MODAL_BD = A.getClassName('modal-body');

		var FormBuilderModalSupport = function() {
		};

		FormBuilderModalSupport.ATTRS = {
			dynamicContentHeight: {
				value: false,
				writeOnce: true
			},

			topFixed: {
				value: false,
				validator: function() {
					return (this.get('centered'));
				}
			}
		};

		FormBuilderModalSupport.prototype = {
			initializer: function() {
				var instance = this;

				instance._eventHandles.push(instance.after(instance._bindModalUI, instance, 'bindUI'));
				instance._eventHandles.push(instance.after(instance._afterModalRender, instance, '_afterRender'));
			},

			syncHeight: function() {
				var instance = this;
				var modalBody = A.one('.' + CSS_MODAL_BD);

				modalBody.setStyle('max-height', '100%');

				modalBody.setStyle('max-height', A.DOM.winHeight(document) - instance._getModalOffset());
			},

			_afterModalRender: function() {
				var instance = this;

				if (this.get('dynamicContentHeight')) {
					this._configModalDynamicHeight();
					this.syncHeight();
				}
			},

			_afterTopFixedChange: function() {
				var instance = this;

				instance.align();
			},

			_afterWindowResize: function() {
				var instance = this;

				if (this.get('dynamicContentHeight')) {
					instance.syncHeight();
				}

				if (instance.get('centered')) {
					instance.align();
				}
			},

			_bindModalUI: function() {
				var instance = this;

				instance._eventHandles.push(instance.after('topFixedChange', instance._afterTopFixedChange));
				instance._eventHandles.push(instance.on('xyChange', instance._onModalXYChange));
			},

			_configModalDynamicHeight: function() {
				var instance = this;

				instance.get('boundingBox').addClass('dynamic-content-height');
			},

			_fixAtTheTop: function(xy) {
				var instance = this;

				xy[1] = A.config.win.scrollY + window.parseInt(instance.get('boundingBox').getComputedStyle('margin-top'));

				return xy;
			},

			_getModalOffset: function() {
				var instance = this;
				var modalBodyHeight = A.one('.' + CSS_MODAL_BD).get('offsetHeight');

				var boundingBox = instance.get('boundingBox');

				var boundingOuterHeight = boundingBox.get('offsetHeight') +
					window.parseInt(boundingBox.getComputedStyle('marginTop')) +
					window.parseInt(boundingBox.getComputedStyle('marginBottom'));

				return Math.max(modalBodyHeight, boundingOuterHeight) - Math.min(modalBodyHeight, boundingOuterHeight);
			},

			_onModalXYChange: function(event) {
				var instance = this;

				if (instance.get('topFixed') && instance.get('centered')) {
					event.newVal = instance._fixAtTheTop(event.newVal);
				}
			}
		};

		Liferay.namespace('DDL').FormBuilderModalSupport = FormBuilderModalSupport;
	},
	'',
	{
		requires: ['aui-modal']
	}
);