AUI.add(
	'liferay-workflow-web',
	function(A) {
		var WorkflowWeb = {
			previewBeforeRevert: function(event, renderUrl, namespace, title) {
				var instance = this;

				Liferay.Util.Window.getWindow(
					{
						dialog: {
							destroyOnHide: true,
							modal: true
						},
						dialogIframe: {
							bodyCssClass: 'dialog-with-footer'
						},
						id: namespace + 'previewBeforeRevert',
						title: title,
						uri: renderUrl
					}
				);
			},

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
		requires: ['liferay-alert', 'liferay-util-window']
	}
);