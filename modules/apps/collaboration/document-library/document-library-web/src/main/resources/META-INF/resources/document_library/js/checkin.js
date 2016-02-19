AUI.add(
	'document-library-checkin',
	function(A) {
		var DocumentLibraryCheckin = {
			showDialog: function(contentId, title, onSave) {
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

				saveButton.on('click', onSave);

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