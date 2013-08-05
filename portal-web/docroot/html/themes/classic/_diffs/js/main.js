AUI().ready(
	'aui-io-request', 'aui-modal', 'aui-parse-content', 'liferay-hudcrumbs', 'liferay-navigation-interaction', 'liferay-portlet-url',
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

		var signInPortletBody;

		var signInPortlet = A.one('#p_p_id_58_');

		if (signInPortlet) {
			signInPortletBody = signInPortlet.one('.portlet-body');
		}

		var modal;

		var getModal = function() {
			if (!modal) {
				modal = new A.Modal(
					{
						after: {
							visibleChange: function(event) {
								if (!event.newVal && signInPortletBody) {
									var oldParent = signInPortletBody.getData('oldParent');

									if (oldParent) {
										oldParent.append(signInPortletBody);
									}
								}
							}
						},
						centered: true,
						constrain: true,
						headerContent: '<h3>' + Liferay.Language.get('sign-in') + '</h3>',
						modal: true,
						zIndex: 400
					}
				).render();

				var bodyNode = modal.bodyNode;

				bodyNode.plug(A.Plugin.ParseContent);
			}

			return modal;
		};

		var setModalContent = function(content) {
			var modal = getModal();

			modal.show();

			var bodyNode = modal.bodyNode;

			bodyNode.setContent(content);

			modal.align();
		};

		if (signIn) {
			signIn.on(
				'click',
				function(event) {
					event.preventDefault();

					var redirectPage = function() {
						A.config.win.location.href = signInURL;
					};

					if (signInPortletBody) {
						signInPortletBody.setData('oldParent', signInPortletBody.ancestor());

						setModalContent(signInPortletBody);
					}
					else {
						var signInURL = event.currentTarget.attr('href');

						var modalSignInURL = Liferay.PortletURL.createURL(signInURL);

						modalSignInURL.setWindowState('exclusive');

						A.io.request(
							modalSignInURL.toString(),
							{
								on: {
									failure: redirectPage,
									success: function(event, id, obj) {
										var responseData = this.get('responseData');

										if (responseData) {
											setModalContent(responseData);
										}
										else {
											redirectPage();
										}
									}
								}
							}
						);
					}
				}
			);
		}
	}
);