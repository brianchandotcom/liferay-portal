Liferay.Util.portletTitleEdit = function() {
};

if (themeDisplay.isSignedIn()) {
	AUI.$(
		function() {
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
	);
}