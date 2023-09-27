/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

/*
 * @deprecated As of Athanasius (7.3.x), replaced by Liferay.Util.openToast
 * @module liferay-alert
 */
AUI.add(
	'liferay-alert',
	(A) => {
		const Lang = A.Lang;

		const Alert = A.Component.create({
			ATTRS: {
				animated: {
					validator: Lang.isBoolean,
					value: true,
				},

				closeableNode: {
					valueFn() {
						return A.Node.create(
							'<button aria-label="' +
								Liferay.Language.get('close') +
								'" class="close" type="button">' +
								Liferay.Util.getLexiconIconTpl('times') +
								'</button>'
						);
					},
				},

				icon: {
					validator: Lang.isString,
					value: 'info-circle',
				},

				message: {
					validator: Lang.isString,
					value: '',
				},

				title: {
					validator: Lang.isString,
				},

				type: {
					validator: Lang.isString,
					value: 'info',
				},
			},

			AUGMENTS: [Liferay.PortletBase],

			EXTENDS: A.Alert,

			NAME: 'liferayalert',

			prototype: {
				_afterTypeChange() {
					const instance = this;

					instance._updateCssClass();
				},

				_cancelHide() {
					const instance = this;

					instance._clearHideTimer();

					instance._set('visible', true);
				},

				_getAlertsContainer(targetNode) {
					const instance = this;

					let alertsContainer = instance._alertsContainer;

					if (!alertsContainer) {
						const rootNode =
							targetNode ||
							instance.get('rootNode') ||
							// eslint-disable-next-line @liferay/aui/no-get-body
							A.getBody();

						alertsContainer =
							(targetNode &&
								targetNode.one('.lfr-alert-container')) ||
							rootNode.one('.lfr-alert-container');

						if (!alertsContainer) {
							alertsContainer = A.Node.create(
								instance.TPL_ALERTS_CONTAINER
							);

							if (targetNode) {
								targetNode.prepend(alertsContainer);
							}
							else {
								const navbar = rootNode.one(
									'.portlet-body > .navbar'
								);

								if (navbar) {
									navbar.placeAfter(alertsContainer);
								}
								else {
									const prependTarget =
										rootNode.one('.portlet-body') ||
										rootNode;

									prependTarget.prepend(alertsContainer);
								}
							}
						}

						instance._alertsContainer = alertsContainer;
					}

					return alertsContainer;
				},

				_getParentNode(targetNode) {
					const instance = this;

					let parentNode = instance._parentNode;

					if (!parentNode) {
						parentNode = A.Node.create(instance.TPL_ALERT_NODE);

						const alertsContainer = instance._getAlertsContainer(
							targetNode
						);

						alertsContainer.prepend(parentNode);

						instance._parentNode = parentNode;
					}

					return parentNode;
				},

				_maybeHide() {
					const instance = this;

					if (instance._ignoreHideDelay) {
						instance._prepareTransition(false);
						instance._transition(false);
					}
					else {
						Alert.superclass._maybeHide.call(this);
					}
				},

				_onClickBoundingBox(event) {
					if (
						event.target.ancestor('.close', true, '.liferayalert')
					) {
						this._ignoreHideDelay = true;

						this.hide();
					}
				},

				_onMouseLeave() {
					const instance = this;

					const delay = instance.get('delay');

					if (delay.hide > 0) {
						instance.hide();
					}
				},

				_prepareTransition(visible) {
					const instance = this;

					const parentNode = instance._getParentNode();

					instance._clearHideTimer();

					if (visible && !parentNode.test('.in')) {
						instance._uiSetVisibleHost(true);

						parentNode.setStyle('height', 0);
					}
				},

				_transition(visible) {
					const instance = this;

					const parentNode = instance._getParentNode();

					if (!visible || !parentNode.test('.in')) {
						try {
							parentNode.transition(
								{
									duration: instance.get('duration') / 1000,
									easing: 'ease-out',
									height: visible
										? instance
												.get('boundingBox')
												.outerHeight() + 'px'
										: 0,
								},
								() => {
									parentNode.toggleClass('in', visible);

									instance._uiSetVisibleHost(visible);

									const delay = instance.get('delay');

									if (visible && delay.hide) {
										instance.hide();
									}
									else if (instance.get('destroyOnHide')) {
										A.soon(A.bind('destroy', instance));
									}
								}
							);
						}
						catch (error) {}
					}
				},

				_updateBodyContent() {
					const instance = this;

					const bodyContent = Lang.sub(instance.TPL_CONTENT, {
						icon: instance.get('icon'),
						message: instance.get('message'),
						spritemap: Liferay.Icons.spritemap,
						title: instance.get('title') || '',
					});

					instance.set('bodyContent', bodyContent);
				},

				_updateCssClass() {
					const instance = this;

					instance.set('cssClass', 'alert-' + instance.get('type'));
				},

				TPL_ALERT_NODE:
					'<div class="container-fluid-1280 lfr-alert-wrapper"></div>',

				TPL_ALERTS_CONTAINER: '<div class="lfr-alert-container"></div>',

				TPL_CONTENT:
					'<strong class="lead"><svg class="lexicon-icon" focusable="false"><use href="{spritemap}#{icon}" /><title>{title}</title></svg> {title}</strong>{message}',

				bindUI() {
					const instance = this;

					const boundingBox = instance.get('boundingBox');

					instance._eventHandles = [
						instance.after(
							['iconChange', 'messageChange', 'titleChange'],
							instance._updateBodyContent,
							instance
						),
						instance.after(
							'typeChange',
							instance._afterTypeChange,
							instance
						),
						boundingBox.on(
							'mouseenter',
							instance._cancelHide,
							instance
						),
						boundingBox.on(
							'mouseleave',
							instance._onMouseLeave,
							instance
						),
					];

					boundingBox.attr('role', 'alert');

					Alert.superclass.bindUI.call(this);
				},

				render(parentNode) {
					const instance = this;

					instance._updateBodyContent();
					instance._updateCssClass();

					parentNode = A.one(parentNode);

					return Alert.superclass.render.call(
						this,
						this._getParentNode(parentNode)
					);
				},
			},
		});

		Liferay.Alert = Alert;
	},
	'',
	{
		requires: [
			'aui-alert',
			'aui-component',
			'event-mouseenter',
			'liferay-portlet-base',
			'timers',
		],
	}
);
