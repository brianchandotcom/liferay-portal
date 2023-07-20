/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

(function ($) {

	/*!
	 * jQuery fm Plugin
	 * version: 0.1
	 * Copyright (c) 2014 Nate Cavanaugh / Liferay Inc.
	 * Dual licensed under the MIT and GPL licenses.
	 */

	$.fn.fm = function (name, value) {
		var instance = this;

		var retVal = instance;

		if (arguments.length === 1) {
			var nodes = instance.map(function (index, item) {
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
