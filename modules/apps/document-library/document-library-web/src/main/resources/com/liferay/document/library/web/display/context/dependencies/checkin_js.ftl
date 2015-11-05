Liferay.provide(
	window,
	'${namespace}showVersionDetailsDialog',
	function(saveURL) {
		var versionDetailsDialog = Liferay.Util.Window.getWindow(
			{
				dialog: {
					bodyContent: $('#${namespace}versionDetails').html(),
					destroyOnHide: true
				},
				title: '${dialogTitle}'
			}
		);

		var versionDetailsDialogBoundingBox = versionDetailsDialog.get('boundingBox');

		var majorVersion = versionDetailsDialogBoundingBox.one('#${namespace}${randomNamespace}majorVersion');
		var saveButton = versionDetailsDialogBoundingBox.one('#${namespace}${randomNamespace}save');

		saveButton.on(
			'click',
			function(event) {
				var changeLog = versionDetailsDialogBoundingBox.one('#${namespace}${randomNamespace}changeLog');

				var portletURL = new Liferay.PortletURL(null, null, saveURL);

				portletURL.setParameter('majorVersion', majorVersion.attr('checked'));
				portletURL.setParameter('changeLog', changeLog.val());

				window.location.href = portletURL.toString();
			}
		);

		var cancelButton = versionDetailsDialogBoundingBox.one('#${namespace}${randomNamespace}cancel');

		cancelButton.on(
			'click',
			function(event) {
				versionDetailsDialog.destroy();
			}
		);

		versionDetailsDialog.after(
			'render',
			function(event) {
				majorVersion.focus();
			}
		);

		versionDetailsDialog.render();
	},
	['liferay-util-window']
);