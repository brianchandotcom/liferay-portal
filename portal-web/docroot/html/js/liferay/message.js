AUI.add(
	'liferay-message',
	function(A) {
		var Lang = A.Lang;

		var EVENT_DATA_DISMISS_ALL = {
			categoryVisible: false
		};

		var EVENT_HOVER = ['mouseenter', 'mouseleave'];

		var	NAME = 'liferaymessage';

		var REGEX_CSS_TYPE = A.DOM._getRegExp('\\blfr-message-(alert|error|help|info|success)\\b', 'g');

		var TPL_HIDE_NOTICES = '<div class="lfr-message-controls"><span class="aui-icon aui-icon-closethick lfr-message-close lfr-message-control" title="{0}"></span></div>';

		var TPL_HIDE_NOTICES_ALL = '<span class="lfr-message-close-all lfr-message-control">{0}</span>';

		var Message = A.Component.create(
			{
				ATTRS: {
					dismissible: {
						value: true
					},

					persistenceCategory: {
						value: ''
					},

					persistent: {
						value: true
					},

					trigger: {
						setter: A.one
					},

					type: {
						value: 'info'
					}
				},

				CSS_PREFIX: 'lfr-message',

				NAME: NAME,

				UI_ATTRS: ['dismissible', 'persistent', 'type'],

				prototype: {
					initializer: function() {
						var instance = this;

						instance._boundingBox = instance.get('boundingBox');
						instance._contentBox = instance.get('contentBox');

						instance._cssDismissible = instance.getClassName('dismissible');
						instance._cssPersistent = instance.getClassName('persistent');
					},

					renderUI: function() {
						var instance = this;

						var dismissible = instance.get('dismissible');

						if (dismissible) {
							var trigger = instance.get('trigger');

							trigger.addClass('lfr-message-trigger');

							instance._trigger = trigger;

							var tplHideNotices = Lang.sub(TPL_HIDE_NOTICES, [Liferay.Language.get('hide-this-message')]);

							var hideNoticesControl = A.Node.create(tplHideNotices);

							if (instance.get('persistenceCategory')) {
								var dismissAllText = instance.get('strings.dismissAll') || '<a href="javascript:;">' + Liferay.Language.get('hide-all-messages-like-this') + '</a>';

								var tplHideNoticesAll = Lang.sub(TPL_HIDE_NOTICES_ALL, [dismissAllText]);

								var hideAllNotices = A.Node.create(tplHideNoticesAll);

								instance._hideAllNoticesControl = hideAllNotices.one('a');

								hideNoticesControl.append(hideAllNotices);

								instance._hideAllNotices = hideAllNotices;
							}

							instance._closeButton = hideNoticesControl.one('.lfr-message-close');

							instance._contentBox.append(hideNoticesControl);

							instance._hideNoticesControl = hideNoticesControl;
						}

						instance._dismissible = dismissible;
					},

					bindUI: function() {
						var instance = this;

						if (instance._dismissible) {
							instance.on(EVENT_HOVER, instance._onBoxHover);

							instance.after('visibleChange', instance._afterVisibleChange);

							var closeButton = instance._closeButton;

							if (closeButton) {
								closeButton.on('click', instance._onCloseButtonClick, instance);
							}

							var trigger = instance._trigger;

							if (trigger) {
								trigger.on('click', instance._onTriggerClick, instance);
							}

							if (instance._hideNoticesControl) {
								instance._hideNoticesControl.on(EVENT_HOVER, instance._onCloseButtonHover, instance);
							}

							if (instance._hideAllNoticesControl) {
								instance._hideAllNoticesControl.on('click', instance._onHideAllClick, instance);
							}
						}
					},

					_afterVisibleChange: function(event) {
						var instance = this;

						var messageVisible = event.newVal;

						instance._contentBox.toggle(messageVisible);

						instance.get('trigger').toggle(!messageVisible);

						if (instance.get('persistent')) {
							var sessionData = {};

							if (themeDisplay.isImpersonated()) {
								sessionData.doAsUserId = themeDisplay.getDoAsUserIdEncoded();
							}

							if (event.categoryVisible === false) {
								sessionData[instance.get('persistenceCategory')] = false;
							}

							sessionData[instance.get('id')] = messageVisible;

							Liferay.Store(sessionData);
						}
					},

					_onBoxHover: function(event) {
						var instance = this;

						instance._boundingBox.toggleClass('lfr-message-hover', (event.type.indexOf('mouseenter') > -1));
					},

					_onHideAllClick: function(event) {
						var instance = this;

						instance.set('visible', false, EVENT_DATA_DISMISS_ALL);
					},

					_onCloseButtonClick: function(event) {
						var instance = this;

						instance.hide();
					},

					_onCloseButtonHover: function(event) {
						var instance = this;

						instance._hideNoticesControl.toggleClass('lfr-message-controls-hover', (event.type == 'mouseenter'));
					},

					_onTriggerClick: function(event) {
						var instance = this;

						instance.show();
					},

					_uiSetDismissible: function(value) {
						var instance = this;

						instance._boundingBox.toggleClass(instance._cssDismissible, value);
					},

					_uiSetPersistent: function(value) {
						var instance = this;

						instance._boundingBox.toggleClass(instance._cssPersistent, value);
					},

					_uiSetType: function(value) {
						var instance = this;

						var contentBox = instance._contentBox;

						var cssClass = contentBox.attr('class').replace(REGEX_CSS_TYPE, '');

						cssClass += ' ' + instance.getClassName(value);

						contentBox.attr('class', cssClass);
					}
				}
			}
		);

		Liferay.Message = Message;
	},
	'',
	{
		requires: ['aui-base', 'liferay-store']
	}
);