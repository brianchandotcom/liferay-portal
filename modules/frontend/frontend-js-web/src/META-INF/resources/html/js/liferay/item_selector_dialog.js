AUI.add(
	'liferay-item-selector-dialog',
	function(A) {
		var Lang = A.Lang;

		var Util = Liferay.Util;

		var LiferayItemSelectorDialog = A.Component.create(
			{
				ATTRS: {
					eventName: {
						validator: Lang.isString
					},

					strings: {
						value: {
							add: Liferay.Language.get('add'),
							cancel: Liferay.Language.get('cancel')
						}
					},

					title: {
						validator: Lang.isString,
						value: Liferay.Language.get('select-image')
					},

					url: {
						validator: Lang.isString
					}
				},

				NAME: 'item-selector-dialog',

				NS: 'item-selector-dialog',

				prototype: {
					initializer: function() {
						var instance = this;

						var strings = instance.get('strings');

						var eventName = instance.get('eventName');

						Util.selectEntity(
							{
								dialog: {
									constrain: true,
									destroyOnHide: true,
									modal: true,
									'toolbars.footer': [
										{
											cssClass: 'btn-primary',
											disabled: true,
											id: 'addButton',
											label: strings.add,
											on: {
												click: function() {
													instance.fire(
														'itemSelected',
														instance._selectedItem
													);

													instance._closeDialog(eventName);
												}
											}
										},
										{
											id: 'cancelButton',
											label: strings.cancel,
											on: {
												click: function() {
													instance._closeDialog(eventName);
												}
											}
										}
									]
								},
								eventName: eventName,
								id: eventName,
								title: Liferay.Language.get('select-image'),
								uri: instance.get('url')
							},
							A.bind(instance._onDocumentPicked, instance)
						);
					},

					_closeDialog: function(dialogId) {
						var instance = this;

						var dialog = Util.getWindow(dialogId);

						dialog.hide();

						instance._selectedItem = null;
					},

					_onDocumentPicked: function(event) {
						var instance = this;

						instance._selectedItem = event;

						var eventName = instance.get('eventName');

						var dialog = Util.getWindow(eventName);

						var addButton = dialog.getToolbar('footer').get('boundingBox').one('#addButton');

						Util.toggleDisabled(addButton, !event.value);
					}
				}
			}
		);

		A.LiferayItemSelectorDialog = LiferayItemSelectorDialog;
	},
	'',
	{}
);