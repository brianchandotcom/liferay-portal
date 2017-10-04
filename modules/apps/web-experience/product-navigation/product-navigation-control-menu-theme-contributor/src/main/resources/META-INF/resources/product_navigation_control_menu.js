;(function() {
	var adjustScrollTop = function() {
		if (Liferay.ControlMenu) {
			var controlMenuId = Liferay.ControlMenu._namespace + 'ControlMenu';
			var controlMenu = document.getElementById(controlMenuId);

			if (controlMenu) {
				var scroll = (controlMenu.offsetHeight || 0);

				window.scrollBy(0, -scroll);
			}
		}
	};

	var handleFormRegistered = function(event) {
		if (event.form && event.form.formValidator) {
			AUI().Do.after(
				adjustScrollTop,
				event.form.formValidator,
				'focusInvalidField'
			);
		}
	};

	Liferay.on('form:registered', handleFormRegistered);
}());