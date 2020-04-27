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

(function () {
	var adjustScrollTop = function () {
		var controlMenu;
		var controlMenuId;
		var controlMenuScroll;
		var errorFieldLabel;
		var labelScroll;

		errorFieldLabel = document.querySelector(
			'.form-group.has-error .control-label'
		);

		if (errorFieldLabel) {
			labelScroll = errorFieldLabel.clientHeight || 0;

			window.scrollBy(0, -labelScroll);
		}

		if (Liferay.ControlMenu) {
			controlMenuId = Liferay.ControlMenu._namespace + 'ControlMenu';
			controlMenu = document.getElementById(controlMenuId);

			if (controlMenu) {
				controlMenuScroll = controlMenu.offsetHeight || 0;

				window.scrollBy(0, -controlMenuScroll);
			}
		}
	};

	var handleFormRegistered = function (event) {
		if (event.form && event.form.formValidator) {
			AUI().Do.after(
				adjustScrollTop,
				event.form.formValidator,
				'focusInvalidField'
			);
		}
	};

	Liferay.on('form:registered', handleFormRegistered);
})();
