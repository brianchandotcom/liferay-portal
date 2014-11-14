;(function($) {
	/*!
	 * jQuery fm Plugin
	 * version: 0.1
	 * Copyright (c) 2014 Nate Cavanaugh / Liferay Inc.
	 * Dual licensed under the MIT and GPL licenses.
	 */

	$.fn.fm = function(name, value) {
		var instance = this;

		var retVal = instance;

		if (arguments.length === 1) {
			var nodes = instance.map(
				function(index, item) {
					var formEl = item.form || item;

					if (formEl && $.nodeName(formEl, 'form')) {
						var form = $(formEl);

						var ns = form.data('fm.namespace') || form.data('fm-namespace') || '';

						var inputName = ns + name;

						var inputNode = formEl[inputName] || formEl.ownerDocument.getElementById(inputName);

						if (inputNode && inputNode.length) {
							inputNode = $.makeArray(inputNode);
						}

						return inputNode;
					}
				}
			);

			retVal = nodes;
		}
		else {
			if (typeof name === 'string') {
				instance.data('fm.' + name, value);
			}
		}

		return retVal;
	};
})(AUI.$);