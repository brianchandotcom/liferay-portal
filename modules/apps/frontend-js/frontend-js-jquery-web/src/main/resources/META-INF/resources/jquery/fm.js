/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

(function($) {
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
			var nodes = instance.map(function(index, item) {
				var formEl = item.form || item;

				if (formEl && $.nodeName(formEl, 'form')) {
					var form = $(formEl);

					var ns =
						form.data('fm.namespace') ||
						form.data('fm-namespace') ||
						'';

					var inputName = ns + name;

					var inputNode =
						formEl[inputName] ||
						formEl.ownerDocument.getElementById(inputName);

					if (inputNode && !inputNode.nodeName) {
						inputNode = $.makeArray(inputNode);
					}

					return inputNode;
				}
			});

			retVal = nodes;
		}
		else {
			if (typeof name === 'string') {
				instance.data('fm.' + name, value);
			}
		}

		return retVal;
	};
})(window.$);
