AUI.add(
	'liferay-kaleo-designer-dialogs',
	function(A) {
		var KaleoDesignerDialogs = {};

		var openConfirmDeleteDialog = function(title, message, actionUrl) {
			var dialog = Liferay.Util.Window.getWindow(
				{
					dialog: {
						bodyContent: message,
						destroyOnHide: true,
						height: 200,
						resizable: false,
						toolbars: {
							footer: [
								{
									cssClass: 'btn btn-primary',
									discardDefaultButtonCssClasses: true,
									label: Liferay.Language.get('delete'),
									on: {
										click: function() {
											window.location.assign(actionUrl);
										}
									}
								},
								{
									cssClass: 'btn btn-secondary',
									discardDefaultButtonCssClasses: true,
									label: Liferay.Language.get('cancel'),
									on: {
										click: function() {
											dialog.destroy();
										}
									}
								}
							],
							header: [
								{
									cssClass: 'close',
									discardDefaultButtonCssClasses: true,
									labelHTML: '<svg class="lexicon-icon" focusable="false"><use data-href="' + Liferay.ThemeDisplay.getPathThemeImages() + '/lexicon/icons.svg#times" /><title>' + Liferay.Language.get('close') + '</title></svg>',
									on: {
										click: function(event) {
											dialog.destroy();

											event.domEvent.stopPropagation();
										}
									}
								}
							]
						},
						width: 600
					},
					title: title
				}
			);
		};

		KaleoDesignerDialogs.openConfirmDeleteDialog = openConfirmDeleteDialog;

		Liferay.KaleoDesignerDialogs = KaleoDesignerDialogs;
	},
	'',
	{
		requires: ['liferay-util-window']
	}
);