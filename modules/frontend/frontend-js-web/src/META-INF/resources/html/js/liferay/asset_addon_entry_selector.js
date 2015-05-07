AUI.add(
	'liferay-asset-addon-entry-selector',
	function(A) {
		var AArray = A.Array;

		var Lang = A.Lang;

		var NAME = 'assetaddonentryselector';

		var STR_BLANK = '';

		var STR_CHECKED = 'checked';

		var STR_CLICK = 'click';

		var STR_DATA_KEY = 'data-key';

		var STR_ENTRIES = 'entries';

		var STR_INPUT = 'input';

		var STR_SELECTED_ENTRIES = 'selectedEntries';

		var TPL_SELECT_ENTRY = '<li>' +
				'<label>' +
					'<input {checked} class="toggle-card" data-key={key} data-label={label} type="checkbox">' +
					'<div class="toggle-card-container">' +
						'<div class="toggle-card-cell">' +
							'<div class="toggle-card-icon">' +
								'<span class="toggle-card-off icon-{icon}"></span>' +
								'<span class="toggle-card-on icon-ok"></span>' +
							'</div>' +
							'<div class="toggle-card-label">' +
								'<span>{label}</span>' +
							'</div>' +
						'</div>' +
					'</div>' +
				'</label>' +
			'</li>';

		var TPL_SELECT_LIST = '<ul class="list-inline list-unstyled">{entries}</ul>';

		var TPL_SUMMARY_ENTRY = '<li class="list-entry" data-key="{key}" data-label="{label}">' +
				'<span class="label label-entry label-circle">' +
					'{label}' +
					'<button class="remove-button" type="button">' +
						'<i class="icon-remove"></i>' +
					'</button>' +
				'</span>' +
			'</li>';

		var AssetAddonEntrySelector = A.Component.create(
			{
				ATTRS: {
					dialogTitle: {
						validator: Lang.isString,
						value: Liferay.Language.get('select-entries')
					},

					entries: {
						setter: '_setEntries',
						validator: Lang.isArray
					},

					selectedEntries: {
						validator: Lang.isArray
					}
				},

				AUGMENTS: [Liferay.PortletBase],

				EXTENDS: A.Base,

				NAME: NAME,

				prototype: {
					initializer: function() {
						var instance = this;

						instance._dialogId = A.guid();
						instance._selectDialogContent = instance._getSelectDialogContent();

						instance._bindUI();
					},

					destructor: function() {
						var instance = this;

						(new A.EventHandle(instance._eventHandles)).detach();
					},

					_bindUI: function() {
						var instance = this;

						instance._eventHandles = [
							instance.after('selectedEntriesChange', instance._syncUI, instance),
							instance.one('.select-button').on(STR_CLICK, instance._onSelectClick, instance),
							instance.one('.selected-entries').delegate(STR_CLICK, instance._onSummaryItemRemove, '.remove-button', instance)
						];
					},

					_getSelectDialog: function() {
						var instance = this;

						var dialog = instance._dialog;

						if (!dialog) {
							var dialogConfig = {
								'toolbars.footer': instance._getSelectDialogFooterToolbar(),
								width: 540
							};

							dialog = Liferay.Util.Window.getWindow(
								{
									dialog: dialogConfig,
									id: instance._dialogId,
									title: instance.get('dialogTitle')
								}
							);

							dialog.setStdModContent('body', instance._selectDialogContent);

							instance._dialog = dialog;
						}

						return dialog;
					},

					_getSelectDialogContent: function() {
						var instance = this;

						var selectedEntries = instance.get(STR_SELECTED_ENTRIES);

						var entriesContent = AArray.reduce(
							instance.get(STR_ENTRIES),
							STR_BLANK,
							function(previousValue, currentValue) {
								currentValue.checked = (selectedEntries.indexOf(currentValue.key) !== -1) ? STR_CHECKED : STR_BLANK;

								return previousValue + Lang.sub(TPL_SELECT_ENTRY, currentValue);
							}
						);

						var content = Lang.sub(
							TPL_SELECT_LIST,
							{
								entries: entriesContent
							}
						);

						return A.Node.create(content);
					},

					_getSelectDialogFooterToolbar: function() {
						var instance = this;

						var footerToolbar = [
							{
								label: Liferay.Language.get('done'),
								on: {
									click: A.bind('_updateSelectedEntries', instance)
								}
							},
							{
								label: Liferay.Language.get('cancel'),
								on: {
									click: A.bind('_hideSelectDialog', instance)
								}
							}
						];

						return footerToolbar;
					},

					_hideSelectDialog: function() {
						var instance = this;

						instance._getSelectDialog().hide();
					},

					_onSelectClick: function(event) {
						var instance = this;

						instance._showSelectDialog();
					},

					_onSummaryItemRemove: function(event) {
						var instance = this;

						var selectedEntries = instance.get(STR_SELECTED_ENTRIES);

						var removedItem = event.currentTarget.ancestor('.list-entry').attr(STR_DATA_KEY);

						selectedEntries = AArray.filter(
							selectedEntries,
							function(item) {
								return item !== removedItem;
							}
						);

						instance.set(STR_SELECTED_ENTRIES, selectedEntries);
					},

					_setEntries: function(val) {
						var instance = this;

						var entriesMap = {};

						AArray.each(
							val,
							function(item) {
								entriesMap[item.key] = item;
							}
						);

						instance._entriesMap = entriesMap;
					},

					_showSelectDialog: function() {
						var instance = this;

						instance._syncUI();
						instance._getSelectDialog().show();
					},

					_syncUI: function() {
						var instance = this;

						var entries = instance.get(STR_ENTRIES);

						var selectedEntries = instance.get(STR_SELECTED_ENTRIES);

						var selectedEntriesNode = instance.one('.selected-entries');

						selectedEntriesNode.empty();

						instance._selectDialogContent.all(STR_INPUT).attr(STR_CHECKED, false);

						AArray.each(
							selectedEntries,
							function(item) {
								selectedEntriesNode.append(
									Lang.sub(TPL_SUMMARY_ENTRY, instance._entriesMap[item])
								);

								instance._selectDialogContent.one('input[data-key="' + item + '"]').attr(STR_CHECKED, true);
							}
						);

						instance.one(STR_INPUT).val(selectedEntries.join(','));
					},

					_updateSelectedEntries: function() {
						var instance = this;

						var dialog = instance._getSelectDialog();

						var selectedEntries = [];

						dialog.bodyNode.all('input:checked').each(
							function(item) {
								selectedEntries.push(item.attr(STR_DATA_KEY));
							}
						);

						instance.set(STR_SELECTED_ENTRIES, selectedEntries);

						instance._hideSelectDialog();
					}
				}
			}
		);

		Liferay.AssetAddonEntrySelector = AssetAddonEntrySelector;
	},
	'',
	{
		requires: ['aui-component', 'liferay-portlet-base', 'liferay-util-window']
	}
);