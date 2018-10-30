AUI.add(
	'liferay-ddm-form-builder-confirmation-dialog',
	function(A) {
		var CSS_BTN_LG = A.getClassName('btn', 'lg');

		var CSS_BTN_LINK = A.getClassName('btn', 'link');

		var CSS_BTN_PRIMARY = A.getClassName('btn', 'primary');

		var Lang = A.Lang;

		var FormBuilderConfirmationDialog = {
			open: function(config) {
				var instance = this;

				if (!config.width) {
					config.width = false;
				}

				Liferay.Util.openWindow(
					{
						dialog: {
							bodyContent: config.body,
							destroyOnHide: true,
							draggable: false,
							height: 210,
							resizable: false,
							toolbars: {
								footer: [
									{
										cssClass: [CSS_BTN_LG, CSS_BTN_LINK].join(' '),
										labelHTML: Liferay.Language.get('dismiss'),
										on: {
											click: function() {
												if (Lang.isFunction(config.dismissFn)) {
													config.dismissFn(arguments);
												}

												Liferay.Util.getWindow(config.id).hide();
											}
										}
									},
									{
										cssClass: [CSS_BTN_LG, CSS_BTN_PRIMARY].join(' '),
										labelHTML: config.labelHTML,
										on: {
											click: function(event) {
												if (Lang.isFunction(config.confirmFn)) {
													config.confirmFn(arguments);
												}

												Liferay.Util.getWindow(config.id).hide();
											}
										}
									}
								]
							},
							width: config.width
						},
						id: config.id,
						title: config.title
					}
				);
			}
		};

		Liferay.namespace('DDM').FormBuilderConfirmationDialog = FormBuilderConfirmationDialog;
	},
	'',
	{
		requires: []
	}
);