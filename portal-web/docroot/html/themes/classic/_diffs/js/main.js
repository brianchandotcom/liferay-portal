AUI().ready(
	'aui-modal', 'liferay-hudcrumbs', 'liferay-navigation-interaction', 'node-load',
	function(A) {
		var navigation = A.one('#navigation');

		if (navigation) {
			navigation.plug(Liferay.NavigationInteraction);
		}

		var siteBreadcrumbs = A.one('#breadcrumbs');

		if (siteBreadcrumbs) {
			siteBreadcrumbs.plug(A.Hudcrumbs);
		}

		var signIn = A.one('li.sign-in a');

		if (signIn) {
			signIn.on(
				'click',
				function(event) {
					event.preventDefault();

					var signInURL = event.currentTarget.attr('href');

					var signInDialog = new A.Modal(
						{
							bodyContent: '<div class="loading-animation" />',
							centered: true,
							constrain: true,
							headerContent: '<h3>' + Liferay.Language.get('sign-in') + '</h3>',
							modal: true,
							zIndex: 400
						}
					).render();

					signInDialog.bodyNode.load(
						signInURL,
						'#portlet_58 .portlet-body',
						function() {
							signInDialog.align();
						}
					);
				}
			);
		}
	}
);