AUI.add(
	'liferay-util-list-fields',
	function(A) {
		var Util = Liferay.Util;

		Util.listChecked = function(form, name) {
			var buffer = [];

			form = AUI().one(form);

			if (form) {
				var selector = 'input[type=checkbox]';

				if (name) {
					selector += '[name='+ name +']';
				}

				form.all(selector).each(
					function(item, index, collection) {
						var val = item.val();

						if (val && item.get('checked')) {
							buffer.push(val);
						}
					}
				);
			}

			return buffer.join();
		};

		Util.listCheckedExcept = function(form, except, name) {
			var buffer = [];

			form = AUI().one(form);

			if (form) {
				var selector = 'input[type=checkbox]';

				if (name) {
					selector += '[name='+ name +']';
				}

				form.all(selector).each(
					function(item, index, collection) {
						var val = item.val();

						if (val && item.get('name') != except && item.get('checked') && !item.get('disabled')) {
							buffer.push(val);
						}
					}
				);
			}

			return buffer.join();
		};

		Util.listSelect = function(box, delimeter) {
			var buffer = [];

			var selectList = '';

			if (box != null) {
				var select = AUI().one(box);

				if (select) {
					var options = select.all('option');

					options.each(
						function(item, index, collection) {
							var val = item.val();

							if (val) {
								buffer.push(val);
							}
						}
					);
				}

				if (buffer[0] != '.none') {
					selectList = buffer.join(delimeter || ',');
				}
			}

			return selectList;
		};

		Util.listUncheckedExcept = function(form, except, name) {
			var buffer = [];

			form = AUI().one(form);

			if (form) {
				var selector = 'input[type=checkbox]';

				if (name) {
					selector += '[name='+ name +']';
				}

				form.all(selector).each(
					function(item, index, collection) {
						var val = item.val();

						if (val && item.get('name') != except && !item.get('checked') && !item.get('disabled')) {
							buffer.push(val);
						}
					}
				);
			}

			return buffer.join();
		};
	},
	'',
	{
		requires: ['aui-base']
	}
);