AUI.add(
	'liferay-control-menu',
	function(A) {
		var STR_ADD_PANEL = 'addPanel';

		var ControlMenu = {
			init: function(containerId) {
				var instance = this;

				var controlMenu = A.one(containerId);

				if (controlMenu) {
					var namespace = controlMenu.attr('data-namespace');

					instance._namespace = namespace;

					Liferay.Util.toggleControls(controlMenu);

					var eventHandle = controlMenu.on(
						['focus', 'mousemove', 'touchstart'],
						function(event) {
							Liferay.fire('initLayout');

							eventHandle.detach();
						}
					);
				}
			}
		};

		Liferay.ControlMenu = ControlMenu;

		Liferay.ControlMenu.ADD_PANEL = STR_ADD_PANEL;
	},
	'',
	{
		requires: ['aui-node', 'aui-overlay-mask-deprecated', 'event-move', 'event-touch', 'liferay-menu-toggle']
	}
);