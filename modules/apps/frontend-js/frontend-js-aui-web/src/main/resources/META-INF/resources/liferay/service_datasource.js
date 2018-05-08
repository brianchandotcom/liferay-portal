AUI.add(
	'liferay-service-datasource',
	function(A) {
		var ServiceDataSource = A.Component.create(
			{
				EXTENDS: A.DataSource.Local,
				NAME: 'servicedatasource',
				prototype: {
					_defRequestFn: function(event) {
						var instance = this;

						var source = instance.get('source');

						source(event.request, A.rbind('_serviceCallbackFn', instance, event));
					},

					_serviceCallbackFn: function(obj, xHR, event) {
						var instance = this;

						instance.fire(
							'data',
							A.mix(
								{
									data: obj
								},
								event
							)
						);
					}
				}
			}
		);

		Liferay.Service.DataSource = ServiceDataSource;
	},
	'',
	{
		requires: ['aui-base', 'datasource-local']
	}
);