;(function() {
	var adjustScrollTop = function() {
		var controlMenu;
		var controlMenuId;
		var controlMenuScroll;
		var errorFieldLabel;
		var labelScroll;

		errorFieldLabel = document.querySelector('.form-group.has-error .control-label');

		if (errorFieldLabel) {
			labelScroll = (errorFieldLabel.clientHeight || 0);

			window.scrollBy(0, -labelScroll);
		}

		if (Liferay.ControlMenu) {
			controlMenuId = Liferay.ControlMenu._namespace + 'ControlMenu';
			controlMenu = document.getElementById(controlMenuId);

			if (controlMenu) {
				controlMenuScroll = (controlMenu.offsetHeight || 0);

				window.scrollBy(0, -controlMenuScroll);
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