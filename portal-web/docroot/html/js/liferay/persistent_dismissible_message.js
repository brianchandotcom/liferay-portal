AUI.add(
	'liferay-persistent-dismissible-message',
	function(A) {
		var sessionData = {};

		if (themeDisplay.isImpersonated()) {
			sessionData.doAsUserId = themeDisplay.getDoAsUserIdEncoded();
		}

		var CSS_ICON_CLOSE = 'aui-icon-closethick';

		var CSS_ICON_HELP = 'aui-icon-help';

		var	NAME = 'persistent-dismissible-message';

		var PersistentDismissibleMessage = A.Component.create(
			{
				ATTRS: {
					iconDismissSelector: {
						value: '.aui-icon'
					},

					sessionDismissAllMessage: {
						value: ''
					},

					sessionDismissMessage: {
						value: ''
					},

					trigger: {
						setter: A.one
					}
				},

				NAME: NAME,

				prototype: {
					renderUI: function() {
						var instance = this;

						var contentBox = instance.get('contentBox');

						var iconDismissSelector = instance.get('iconDismissSelector');

						instance._icon = contentBox.one(iconDismissSelector);

						instance._hideNoticesControl = contentBox.one('.hide-notices-control');
						instance._hideAllNotices = instance._hideNoticesControl.one('.hide-all-notices');
						instance._hideAllNoticesControl = instance._hideNoticesControl.one('a');
					},

					bindUI: function() {
						var instance = this;

						instance.on(['mouseenter', 'mouseleave'], instance._onBoxMouseToggle);
						instance.after('visibleChange', instance._afterVisibleChange);

						instance._icon.on('click', instance._onIconClick, instance);

						instance.get('trigger').on('click', instance._onTriggerClick, instance);

						instance._hideNoticesControl.on(['mouseenter', 'mouseleave'], instance._onIconMouseToggle, instance);

						if (instance._hideAllNoticesControl) {
							instance._hideAllNoticesControl.on('click', instance._onHideAllClick, instance);
						}
					},

					_afterVisibleChange: function(event) {
						var instance = this;

						var action = 'show';
						var panelAction = 'hide';

						if (event.newVal) {
							action = 'hide';
							panelAction = 'show';
						}

						var trigger = instance.get('trigger');

						trigger[action]();

						var contentBox = instance.get('contentBox');

						contentBox[panelAction]();

						if (event.sessionDismissAllMessage === false) {
							sessionData[instance.get('sessionDismissAllMessage')] = false;
						}

						sessionData[instance.get('sessionDismissMessage')] = event.newVal;

						A.io.request(
							themeDisplay.getPathMain() + '/portal/session_click',
							{
								data: sessionData
							}
						);
					},

					_onBoxMouseToggle: function(event) {
						var instance = this;

						var from = CSS_ICON_HELP;
						var to = CSS_ICON_CLOSE;

						var mouseenter = event.type.indexOf('mouseenter') > -1;

						if (!mouseenter) {
							from = CSS_ICON_CLOSE;
							to = CSS_ICON_HELP;
						}

						instance._icon.replaceClass(from, to);
					},

					_onHideAllClick: function(event) {
						var instance = this;

						instance.set(
							'visible',
							false,
							{
								sessionDismissAllMessage: false
							}
						);
					},

					_onIconClick: function(event) {
						var instance = this;

						instance.hide();
					},

					_onIconMouseToggle: function(event) {
						var instance = this;

						instance._hideNoticesControl.toggleClass('hide-notices-hover', (event.type == 'mouseenter'));

						if (instance._hideAllNotices) {
							instance._hideAllNotices.toggle();
						}
					},

					_onTriggerClick: function(event) {
						var instance = this;

						instance.show();
					}
				}
			}
		);

		Liferay.PersistentDismissibleMessage = PersistentDismissibleMessage;
	},
	'',
	{
		requires: ['aui-io-request']
	}
);