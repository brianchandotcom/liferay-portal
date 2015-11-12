AUI.add(
	'document-library-checkin',
	function(A) {
		var DocumentLibraryCheckin = {
			showDialog: function(contentId, title, fieldIds, onSave) {
				var instance = this;

				var versionDetailsDialog = Liferay.Util.Window.getWindow(
					{
						dialog: {
							bodyContent: A.one('#' + contentId).html(),
							destroyOnHide: true
						},
						title: title
					}
				);

				var versionDetailsDialogBoundingBox = versionDetailsDialog.get('boundingBox');

				var saveButton = versionDetailsDialogBoundingBox.one('.btn-primary');

				saveButton.on(
					'click',
					function(event) {
						onSave(
							event,
							A.Array.map(
								fieldIds,
								function(fieldId) {
									return versionDetailsDialogBoundingBox.one('#' + fieldId);
								}
							)
						);
					}
				);

				var cancelButton = versionDetailsDialogBoundingBox.one('.btn-cancel');

				cancelButton.on(
					'click',
					function(event) {
						versionDetailsDialog.destroy();
					}
				);

				versionDetailsDialog.render();
			}
		};

		Liferay.Portlet.DocumentLibrary.Checkin = DocumentLibraryCheckin;
	},
	'',
	{
		requires: ['liferay-document-library', 'liferay-util-window']
	}
);