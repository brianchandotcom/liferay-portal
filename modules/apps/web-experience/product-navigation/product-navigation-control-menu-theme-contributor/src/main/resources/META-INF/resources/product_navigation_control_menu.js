;(function() {
    var adjustScrollTop = function() {
        window.scrollBy(0, -65);
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