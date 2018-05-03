AUI.add(
	'liferay-calendar-date-picker-sanitizer',
	function(A) {
		var AArray = A.Array;

		var DateMath = A.DataType.DateMath;

		var DatePickerSanitizer = A.Component.create(
			{
				ATTRS: {
					datePickers: {
					},

					defaultDate: {
					},

					maximumDate: {
					},

					minimumDate: {
					}
				},

				EXTENDS: A.Base,

				NAME: 'date-picker-sanitizer',

				prototype: {
					initializer: function(config) {
						var instance = this;

						instance.eventHandlers = [];

						instance.bindUI();
					},

					bindUI: function() {
						var instance = this;

						var datePickers = instance.get('datePickers');

						instance.eventHandlers = A.map(
							datePickers,
							function(item) {
								return item.on('selectionChange', A.bind(instance._onDatePickerSelectionChange, instance));
							}
						);
					},

					destructor: function() {
						var instance = this;

						instance.unlink();

						instance.eventHandlers = null;
					},

					unlink: function() {
						var instance = this;

						AArray.invoke(instance.eventHandlers, 'detach');
					},

					_onDatePickerSelectionChange: function _onDatePickerSelectionChange(event) {
						var instance = this;

						var date = event.newSelection[0];

						var datePicker = event.currentTarget;

						var defaultDate = instance.get('defaultDate');

						var maximumDate = instance.get('maximumDate');

						var minimumDate = instance.get('minimumDate');

						if (date && !DateMath.between(date, minimumDate, maximumDate)) {
							event.halt();
							event.newSelection.pop();

							datePicker.deselectDates();
							datePicker.selectDates([defaultDate]);
						}
					}
				}
			}
		);

		Liferay.DatePickerSanitizer = DatePickerSanitizer;
	},
	'',
	{
		requires: ['aui-base', 'aui-datatype']
	}
);