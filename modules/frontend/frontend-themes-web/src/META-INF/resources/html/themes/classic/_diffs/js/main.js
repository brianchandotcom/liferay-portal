AUI().ready(
	'liferay-navigation-interaction',
	'liferay-sign-in-modal',
	function(A) {
		var navigation = A.one('#navigation');

		if (navigation) {
			navigation.plug(Liferay.NavigationInteraction);
		}

		var signIn = A.one('li.sign-in a');

		if (signIn && signIn.getData('redirect') !== 'true') {
			signIn.plug(Liferay.SignInModal);
		}

		if (themeDisplay.isSignedIn()) {
			AUI.$('#sidenavContainerId').sideNavigation(
				{
					gutter: '0',
					toggler: '#sidenavToggleId',
					type: 'fixed-push',
					typeMobile: 'fixed',
					width: '320px'
				}
			);
		}
	}
);