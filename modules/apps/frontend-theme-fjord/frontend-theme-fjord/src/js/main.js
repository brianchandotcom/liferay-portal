AUI().ready('liferay-sign-in-modal', function(A) {
	var BODY = A.getBody();

	var signIn = A.one('#sign-in');

	if (signIn && signIn.getData('redirect') !== 'true') {
		signIn.plug(Liferay.SignInModal);
	}

	var fullScreenToggleIcon = A.one(
		'.fjord-header-fullscreen .navbar-toggler'
	);

	if (fullScreenToggleIcon) {
		fullScreenToggleIcon.on('click', function(event) {
			BODY.toggleClass(
				'overflow-hidden',
				event.currentTarget.hasClass('collapsed')
			);
		});
	}
});
