AUI().ready(
	'aui-io-request', 'aui-modal', 'aui-parse-content', 'liferay-hudcrumbs', 'liferay-navigation-interaction', 'liferay-portlet-url',
	function(A) {
		var navigation = A.one('#navigation');
		var signIn = A.one('li.sign-in a');
		var siteBreadcrumbs = A.one('#breadcrumbs');

		if (navigation) {
			navigation.plug(Liferay.NavigationInteraction);
		}

		if (siteBreadcrumbs) {
			siteBreadcrumbs.plug(A.Hudcrumbs);
		}

		if (signIn) {
			signIn.on(
				'click',
				function(event) {
					event.preventDefault();

					var signInURL = event.currentTarget.attr('href');

					var modalSignInURL = Liferay.PortletURL.createURL(signInURL);

					modalSignInURL.setWindowState('exclusive');

					var redirectPage = function() {
						A.config.win.location.href = signInURL;
					};

					A.io.request(
						modalSignInURL.toString(),
						{
							on: {
								failure: redirectPage,
								success: function(event, id, obj) {
									var responseData = this.get('responseData');

									var modal;

									if (responseData) {
										modal = new A.Modal(
											{
												centered: true,
												constrain: true,
												headerContent: '<h3>' + Liferay.Language.get('sign-in') + '</h3>',
												modal: true,
												zIndex: 400
											}
										).render();

										var bodyNode = modal.get('contentBox').one('.modal-body');

										bodyNode.plug(A.Plugin.ParseContent);

										bodyNode.setContent(responseData);

										modal.align();
									}
									else {
										redirectPage();
									}
								}
							}
						}
					);
				}
			);
		}
	}
);