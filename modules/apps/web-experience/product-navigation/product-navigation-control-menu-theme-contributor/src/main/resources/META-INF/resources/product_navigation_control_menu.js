;(function() {
    var adjustScrollTop = function() {
        window.scrollBy(0, -65);
    };

    Liferay.on(
        'form:registered',
        function(event) {
            AUI().Do.after(adjustScrollTop, event.form.formValidator, 'focusInvalidField', event.form.formValidator);
        }
    );
}());