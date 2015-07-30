AUI().ready(

	/*
	This function gets loaded when all the HTML, not including the portlets, is
	loaded.
	*/

	function() {
	}
);

Liferay.Portlet.ready(

	/*
	This function gets loaded after each and every portlet on the page.

	portletId: the current portlet's id
	node: the Alloy Node object of the current portlet
	*/

	function(portletId, node) {
	}
);

Liferay.on(
	'allPortletsReady',

	/*
	This function gets loaded when everything, including the portlets, is on
	the page.
	*/

	function() {
	}
);

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