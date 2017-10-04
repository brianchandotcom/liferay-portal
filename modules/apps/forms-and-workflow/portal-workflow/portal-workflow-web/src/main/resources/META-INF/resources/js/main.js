AUI.add(
	'liferay-workflow-web',
	function(A) {
		var WorkflowWeb = {
			showSuccessMessage: function() {
				var instance = this;

				var successMessage = Liferay.Language.get('definition-imported-sucessfully');

				var alert = instance._alert;

				if (alert) {
					alert.destroy();
				}

				alert = new Liferay.Alert(
					{
						closeable: true,
						delay: {
							hide: 3000,
							show: 0
						},
						icon: 'check',
						message: successMessage,
						type: 'success'
					}
				);

				if (!alert.get('rendered')) {
					alert.render('.portlet-column');
				}

				alert.show();

				instance._alert = alert;
			},

			_alert: null
		};
		Liferay.WorkflowWeb = WorkflowWeb;
	},
	'',
	{
		requires: ['liferay-alert']
	}
);