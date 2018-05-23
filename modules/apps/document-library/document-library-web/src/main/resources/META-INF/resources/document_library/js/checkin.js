AUI.add(
	'document-library-checkin',
	function(A) {
		var DocumentLibraryCheckin = {
			showDialog: function(contentId, title, saveButton, cancelLabel) {
				var versionDetailsDialog = Liferay.Util.Window.getWindow(
					{
						dialog: {
							bodyContent: A.one('#' + contentId).html(),
							destroyOnHide: true,
							height: 400,
							'toolbars.footer': [
								{
									cssClass: 'btn-link',
									label: cancelLabel,
									on: {
										click: function() {
											Liferay.Util.getWindow(contentId + 'Dialog').destroy();
										}
									}
								},
								{
									cssClass: 'btn-primary',
									label: saveButton.label,
									on: {
										click: saveButton.callback
									}
								}
							],
							width: 700
						},
						dialogIframe: {
							bodyCssClass: 'dialog-with-footer'
						},
						id: contentId + 'Dialog',
						title: title
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