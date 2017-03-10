AUI.add(
	'liferay-portlet-look-and-feel',
	function(A) {
		var LookAndFeel = A.Component.create(
			{
				ATTRS: {
				},

				AUGMENTS: [Liferay.PortletBase],

				EXTENDS: A.Base,

				NAME: 'look-and-feel',

				prototype: {
					initializer: function() {
						var instance = this;

						instance._backgroundColorPicker = instance._createColorPickerPopover(instance.byId('backgroundColor'));
						instance._borderColorBottomPicker = instance._createColorPickerPopover(instance.byId('borderColorBottom'));
						instance._borderColorLeftPicker = instance._createColorPickerPopover(instance.byId('borderColorLeft'));
						instance._borderColorTopPicker = instance._createColorPickerPopover(instance.byId('borderColorTop'));
						instance._borderColorRightPicker = instance._createColorPickerPopover(instance.byId('borderColorRight'));
						instance._fontColor = instance._createColorPickerPopover(instance.byId('fontColor'));

						instance._bindUI();
					},

					destructor: function() {
						var instance = this;

						(new A.EventHandle(instance._eventHandles)).detach();
					},

					_afterColorChange: function(event) {
						var instance = this;

						instance._setColor(event.color, event.trigger);
					},

					_bindUI: function() {
						var instance = this;

						var eventHandles = [
							instance._backgroundColorPicker.after('select', instance._afterColorChange, instance),
							instance._borderColorBottomPicker.after('select', instance._afterColorChange, instance),
							instance._borderColorLeftPicker.after('select', instance._afterColorChange, instance),
							instance._borderColorRightPicker.after('select', instance._afterColorChange, instance),
							instance._borderColorTopPicker.after('select', instance._afterColorChange, instance),
							instance._fontColor.after('select', instance._afterColorChange, instance)
						];

						instance._eventHandles = eventHandles;
					},

					_createColorPickerPopover: function(trigger) {
						var instance = this;

						var color = trigger.val();

						instance._setColor(color, trigger);

						return new A.ColorPickerPopover(
							{
								constrain: true,
								color: color,
								trigger: trigger,
								zIndex: Liferay.zIndex.POPOVER
							}
						).render();
					},

					_setColor: function(color, containerColor) {
						if (color && containerColor) {
							containerColor.val(color);

							containerColor.setStyle('backgroundColor', color);
						}
					}
				}
			}
		);

		Liferay.Portlet.LookAndFeel = LookAndFeel;
	},
	'',
	{
		requires: ['aui-base', 'aui-color-picker-popover', 'liferay-portlet-base']
	}
);