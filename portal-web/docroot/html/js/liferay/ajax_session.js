AUI.add(
	'liferay-ajax-session',
	function(A) {
		var Lang = A.Lang;

		var IORequest = A.Component.create(
			{
				EXTENDS: A.IORequest,

				prototype: {
					initializer: function() {
						var instance = this;

						if (Liferay.Session) {
							instance.after('complete', instance._resetSessionInterval, instance);
						}
					},

					_resetSessionInterval: function(event, id, obj, args) {
						var instance = this;

						if (!args || (args && (args.sessionExtend || !Lang.isBoolean(args.sessionExtend)))) {
							Liferay.Session.resetInterval();
						}
					}
				}
			}
		);

		A._IORequest = A.IORequest;
		A.IORequest = IORequest;
	},
	'',
	{
		requires: ['aui-io-request', 'liferay-session']
	}
);