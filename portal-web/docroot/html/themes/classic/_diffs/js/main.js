AUI().ready(
	'aui-io-request', 'aui-modal', 'liferay-hudcrumbs', 'liferay-navigation-interaction',
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

					var redirectPage = function() {
						A.config.win.location = signInURL;
					};

					A.io.request(
						signInURL,
						{
							on: {
								failure: redirectPage,
								success: function(event, id, obj) {
									var responseData = this.get('responseData');

									var modal;

									if (responseData) {
										var renderData = A.Node.create(responseData).one('#portlet_58 .portlet-body');

										if (renderData) {
											modal = new A.Modal(
												{
													bodyContent: renderData,
													centered: true,
													constrain: true,
													headerContent: '<h3>' + Liferay.Language.get('sign-in') + '</h3>',
													modal: true,
													zIndex: 400
												}
											).render();
										}
									}

									if (!modal) {
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