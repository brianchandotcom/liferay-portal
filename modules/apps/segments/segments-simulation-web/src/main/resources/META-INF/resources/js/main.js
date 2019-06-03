AUI.add(
	'liferay-portlet-segments-simulation',
	function(A) {
		var Lang = A.Lang;

		var SegmentsSimulation = A.Component.create({
			ATTRS: {
				deactivateSimulationUrl: {
					validator: Lang.isString
				},

				form: {
					validator: Lang.isObject
				},

				simulateSegmentsEntriesUrl: {
					validator: Lang.isString
				}
			},

			AUGMENTS: [Liferay.PortletBase],

			EXTENDS: A.Base,

			NAME: 'segmentsSimulation',

			prototype: {
				initializer: function() {
					var instance = this;

					instance._bindUI();
				},

				destructor: function() {
					var instance = this;

					new A.EventHandle(instance._eventHandles).detach();
				},

				_bindUI: function() {
					var instance = this;

					instance._eventHandles = [];

					instance._eventHandles.push(
						Liferay.on(
							'SimulationMenu:closeSimulationPanel',
							A.bind('_deactivateSimulation', instance)
						),
						Liferay.on(
							'SimulationMenu:openSimulationPanel',
							A.bind('_simulateSegmentsEntries', instance)
						),
						A.on('beforeunload', function() {
							instance._deactivateSimulation();
						})
					);

					var form = instance.get('form');

					A.one('#' + form.id).delegate(
						'click',
						instance._simulateSegmentsEntries,
						'input',
						instance
					);
				},

				_deactivateSimulation: function() {
					var instance = this;

					var form = instance.get('form');

					A.io.request(instance.get('deactivateSimulationUrl'), {
						form: form,
						method: 'post',
						after: {
							success: function(event, id, obj) {
								A.all('#' + form.id + ' input').set(
									'checked',
									false
								);
							}
						}
					});
				},

				_simulateSegmentsEntries: function() {
					var instance = this;

					A.io.request(instance.get('simulateSegmentsEntriesUrl'), {
						form: {
							id: instance.get('form')
						},
						method: 'POST',
						after: {
							success: function(event, id, obj) {
								var iframe = A.one('#simulationDeviceIframe');

								if (iframe) {
									var iframeWindow = A.Node.getDOMNode(
										iframe.get('contentWindow')
									);

									if (iframeWindow) {
										iframeWindow.location.reload();
									}
								}
							}
						}
					});
				}
			}
		});

		Liferay.Portlet.SegmentsSimulation = SegmentsSimulation;
	},
	'',
	{
		requires: ['aui-base', 'aui-io-request', 'liferay-portlet-base']
	}
);
