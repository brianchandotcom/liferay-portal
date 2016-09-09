AUI.add(
	'liferay-ddl-form-builder-confirmation-dialog',
	function(A) {
		var CSS_BTN_LG = A.getClassName('btn', 'lg');

		var CSS_BTN_LINK = A.getClassName('btn', 'link');

		var CSS_BTN_PRIMARY = A.getClassName('btn', 'primary');

		var Lang = A.Lang;

		var FormBuilderConfirmationDialog = {
			open: function(config) {
				var instance = this;

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
										cssClass: [CSS_BTN_LG, CSS_BTN_PRIMARY].join(' '),
										labelHTML: Liferay.Language.get('yes-cancel'),
										on: {
											click: function(event) {
												if (Lang.isFunction(config.confirmFn)) {
													config.confirmFn(arguments);
												}

												Liferay.Util.getWindow(config.id).hide();
											}
										}
									},
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
									}
								]
							},
							width: false
						},
						id: config.id,
						title: Liferay.Language.get('cancel-field-changes')
					}
				);
			}
		};

		Liferay.namespace('DDL').FormBuilderConfirmationDialog = FormBuilderConfirmationDialog;
	},
	'',
	{
		requires: []
	}
);