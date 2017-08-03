AUI.add(
	'liferay-alert',
	function(A) {
		var Lang = A.Lang;

		var Alert = A.Component.create(
			{
				ATTRS: {
					animated: {
						validator: Lang.isBoolean,
						value: true
					},

					closeableNode: {
						valueFn: function() {
							return A.Node.create('<button class="close" type="button">' + Liferay.Util.getLexiconIconTpl('times', 'icon-monospaced') + '</button>');
						}
					},

					icon: {
						validator: Lang.isString,
						value: 'info-circle'
					},

					message: {
						validator: Lang.isString,
						value: ''
					},

					title: {
						validator: Lang.isString
					},

					type: {
						validator: Lang.isString,
						value: 'info'
					}
				},

				AUGMENTS: [Liferay.PortletBase],

				EXTENDS: A.Alert,

				NAME: 'liferayalert',

				prototype: {
					TPL_ALERT_NODE: '<div class="container-fluid-1280 lfr-alert-wrapper"></div>',

					TPL_ALERTS_CONTAINER: '<div class="lfr-alert-container"></div>',

					TPL_CONTENT: '<strong class="lead"><svg class="lexicon-icon" focusable="false"><use data-href="{pathThemeImages}/lexicon/icons.svg#{icon}" /></svg> {title}</strong>{message}',

					bindUI: function() {
						var instance = this;

						var boundingBox = instance.get('boundingBox');

						instance._eventHandles = [
							instance.after(['iconChange', 'messageChange', 'titleChange'], instance._updateBodyContent, instance),
							instance.after('typeChange', instance._afterTypeChange, instance),
							boundingBox.on('mouseenter', instance._cancelHide, instance),
							boundingBox.on('mouseleave', instance._onMouseLeave, instance)
						];

						Alert.superclass.bindUI.call(this);
					},

					render: function(parentNode) {
						var instance = this;

						instance._updateBodyContent();
						instance._updateCssClass();

						parentNode = A.one(parentNode);

						return Alert.superclass.render.call(this, this._getParentNode(parentNode));
					},

					_afterTypeChange: function(event) {
						var instance = this;

						instance._updateCssClass();
					},

					_cancelHide: function() {
						var instance = this;

						instance._clearHideTimer();

						instance._set('visible', true);
					},

					_getAlertsContainer: function(targetNode) {
						var instance = this;

						var alertsContainer = instance._alertsContainer;

						if (!alertsContainer) {
							var rootNode = targetNode || instance.get('rootNode') || A.getBody();

							alertsContainer = (targetNode && targetNode.one('.lfr-alert-container')) || rootNode.one('.lfr-alert-container');

							if (!alertsContainer) {
								alertsContainer = A.Node.create(instance.TPL_ALERTS_CONTAINER);

								if (targetNode) {
									targetNode.prepend(alertsContainer);
								}
								else {
									var navbar = rootNode.one('.portlet-body > .navbar');

									if (navbar) {
										navbar.placeAfter(alertsContainer);
									}
									else {
										var prependTarget = rootNode.one('.portlet-body') || rootNode;

										prependTarget.prepend(alertsContainer);
									}
								}
							}

							instance._alertsContainer = alertsContainer;
						}

						return alertsContainer;
					},

					_getParentNode: function(targetNode) {
						var instance = this;

						var parentNode = instance._parentNode;

						if (!parentNode) {
							parentNode = A.Node.create(instance.TPL_ALERT_NODE);

							var alertsContainer = instance._getAlertsContainer(targetNode);

							alertsContainer.prepend(parentNode);

							instance._parentNode = parentNode;
						}

						return parentNode;
					},

					_maybeHide: function() {
						var instance = this;

						if (instance._ignoreHideDelay) {
							instance._prepareTransition(false);
							instance._transition(false);
						}
						else {
							Alert.superclass._maybeHide.call(this);
						}
					},

					_onClickBoundingBox: function(event) {
						if (event.target.ancestor('.close', true, '.liferayalert')) {
							this._ignoreHideDelay = true;

							this.hide();
						}
					},

					_onMouseLeave: function(event) {
						var instance = this;

						var delay = instance.get('delay');

						if (delay.hide > 0) {
							instance.hide();
						}
					},

					_prepareTransition: function(visible) {
						var instance = this;

						var parentNode = instance._getParentNode();

						instance._clearHideTimer();

						if (visible && !parentNode.test('.in')) {
							instance._uiSetVisibleHost(true);

							parentNode.setStyle('height', 0);
						}
					},

					_transition: function(visible) {
						var instance = this;

						var parentNode = instance._getParentNode();

						if (!visible || !parentNode.test('.in')) {
							try {
								parentNode.transition(
									{
										duration: instance.get('duration') / 1000,
										easing: 'ease-out',
										height: visible ? instance.get('boundingBox').outerHeight() + 'px' : 0
									},
									function() {
										parentNode.toggleClass('in', visible);

										instance._uiSetVisibleHost(visible);

										var delay = instance.get('delay');

										if (visible && delay.hide) {
											instance.hide();
										}
										else if (instance.get('destroyOnHide')) {
											A.soon(A.bind('destroy', instance));
										}
									}
								);
							}
							catch (error) {
							}
						}
					},

					_updateBodyContent: function() {
						var instance = this;

						var bodyContent = Lang.sub(
							instance.TPL_CONTENT,
							{
								icon: instance.get('icon'),
								message: instance.get('message'),
								pathThemeImages: themeDisplay.getPathThemeImages(),
								title: instance.get('title') || ''
							}
						);

						instance.set('bodyContent', bodyContent);
					},

					_updateCssClass: function() {
						var instance = this;

						instance.set('cssClass', 'alert-' + instance.get('type'));
					}
				}
			}
		);

		Liferay.Alert = Alert;
	},
	'',
	{
		requires: ['aui-alert', 'aui-component', 'event-mouseenter', 'liferay-portlet-base', 'timers']
	}
);