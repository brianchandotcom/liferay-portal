AUI.add(
	'liferay-sign-in-modal',
	function(A) {
		var	NAME = 'signinmodal';

		var WIN = A.config.win;

		var SignInModal = A.Component.create(
			{
				ATTRS: {
					resetFormValidator: {
						value: true
					},

					signInPortlet: {
						setter: A.one,
						value: '#p_p_id_58_'
					}
				},

				EXTENDS: A.Plugin.Base,

				NAME: NAME,

				NS: NAME,

				prototype: {
					initializer: function(config) {
						var instance = this;

						var signInPortlet = instance.get('signInPortlet');

						if (signInPortlet) {
							instance._signInPortletBody = signInPortlet.one('.portlet-body');
						}

						var host = instance.get('host');

						instance._host = host;
						instance._signInPortlet = signInPortlet;

						instance._signInURL = host.attr('href');

						if (signInPortlet) {
							var formNode = signInPortlet.one('form');

							if (formNode) {
								var form = Liferay.Form.get(formNode.attr('id'));

								instance._formValidator = form.formValidator;

								instance._hasSignInForm = formNode.hasClass('sign-in-form');
							}
						}

						instance._bindUI();
					},

					_bindUI: function() {
						var instance = this;

						instance._host.on('click', A.bind('_load', instance));
					},

					_load: function(event) {
						var instance = this;

						event.preventDefault();

						if (instance._signInPortletBody && instance._hasSignInForm) {
							instance._loadDOM();
						}
						else {
							instance._loadIO();
						}
					},

					_loadDOM: function() {
						var instance = this;

						var signInPortletBody = instance._signInPortletBody;

						if (!instance._originalParentNode) {
							instance._originalParentNode = signInPortletBody.ancestor();
						}

						instance._setModalContent(signInPortletBody);

						Liferay.Util.focusFormField('input:text');
					},

					_loadIO: function() {
						var instance = this;

						var modalSignInURL = Liferay.Util.addParams('windowState=exclusive', instance._signInURL);

						A.io.request(
							modalSignInURL,
							{
								on: {
									failure: A.bind('_redirectPage', instance),
									success: function(event, id, obj) {
										var responseData = this.get('responseData');

										if (responseData) {
											instance._setModalContent(responseData);
										}
										else {
											instance._redirectPage();
										}
									}
								}
							}
						);
					},

					_redirectPage: function() {
						var instance = this;

						WIN.location.href = instance._signInURL;
					},

					_setModalContent: function(content) {
						var instance = this;

						var dialog = Liferay.Util.getWindow(NAME);

						if (!dialog) {
							Liferay.Util.openWindow(
								{
									dialog: {
										after: {
											visibleChange: function(event) {
												var signInPortletBody = instance._signInPortletBody;

												var formValidator = instance._formValidator;

												if (formValidator && instance.get('resetFormValidator')) {
													formValidator.resetAllFields();
												}

												if (!event.newVal && signInPortletBody) {
													var originalParentNode = instance._originalParentNode;

													if (originalParentNode) {
														originalParentNode.append(signInPortletBody);
													}
												}
											}
										},
										height: 430,
										width: 560
									},
									id: NAME,
									title: Liferay.Language.get('sign-in')
								},
								function(dialogWindow) {
									var bodyNode = dialogWindow.bodyNode;

									bodyNode.plug(A.Plugin.ParseContent);

									bodyNode.setContent(content);
								}
							);
						}
						else {
							dialog.bodyNode.setContent(content);

							dialog.show();
						}
					}
				}
			}
		);

		Liferay.SignInModal = SignInModal;
	},
	'',
	{
		requires: ['aui-base', 'aui-component', 'aui-io-request', 'aui-parse-content', 'liferay-form', 'liferay-portlet-url', 'liferay-util-window', 'plugin']
	}
);