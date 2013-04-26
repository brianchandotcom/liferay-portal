AUI.add(
	'liferay-dockbar-add-content-preview',
	function(A) {
		var Dockbar = Liferay.Dockbar;
		var Lang = A.Lang;

		var BODY_CONTENT = 'bodyContent';

		var CSS_OVER = 'over';

		var STR_BOUNDING_BOX = 'boundingBox';

		var STR_CURRENT_NODE = 'currentNode';

		var TPL_MESSAGE_ERROR = '<div class="portlet-msg-error">{0}</div>';

		var TPL_LOADING = '<div class="loading-animation" />';

		var AddContentPreview = A.Component.create(
			{
				AUGMENTS: [Liferay.PortletBase],

				EXTENDS: A.Base,

				NAME: 'addcontentpreview',

				prototype: {
					initializer: function(config) {
						var instance = this;

						instance._config = config;

						instance._addContentForm = instance.byId('addContentForm');

						instance._loadPreviewTask = A.debounce('_loadPreviewFn', 200, instance);

						instance._createToolTip();
					},

					_addApplication: function(event) {
						var instance = this;

						Liferay.fire(
							'AddContent:addPortlet',
							{
								node: event.currentTarget
							}
						);
					},

					_afterPreviewFailure: function(event) {
						var instance = this;

						var errorMsg = Lang.sub(
							TPL_MESSAGE_ERROR,
							[
								Liferay.Language.get('unable-to-load-content')
							]
						);

						instance._tooltip.set(BODY_CONTENT, errorMsg);
					},

					_afterPreviewSuccess: function(event) {
						var instance = this;

						var tooltip = instance._tooltip;

						tooltip.set(BODY_CONTENT, event.currentTarget.get('responseData'));

						tooltip.get(STR_BOUNDING_BOX).one('.add-button-preview input').on('click', instance._addApplication, instance);
					},

					_createToolTip: function() {
						var instance = this;

						if (instance._tooltip) {
							instance._tooltip.destroy();
						}

						instance._tooltip = new AddContentTooltip(
							{
								align: {
									points: ['lc', 'rc']
								},
								cssClass: 'lfr-content-preview-popup',
								constrain: true,
								hideOn: 'mouseleave',
								on: {
									show: A.bind('_onTooltipShow', instance),
									hide: function() {
										var currentNode = this.get(STR_CURRENT_NODE);

										currentNode.removeClass(CSS_OVER);
									}
								},
								showArrow: false,
								showOn: 'mouseenter',
								trigger: '.has-preview'
							}
						).render();
					},

					_getIOPreview: function() {
						var instance = this;

						var ioPreview = instance._ioPreview;

						if (!ioPreview) {
							ioPreview = A.io.request(
								instance._addContentForm.getAttribute('action'),
								{
									after: {
										failure: A.bind('_afterPreviewFailure', instance),
										success: A.bind('_afterPreviewSuccess', instance)
									},
									autoLoad: false,
									data: {
										viewEntries: false,
										viewPreview: true
									}
								}
							);

							instance._ioPreview = ioPreview;
						}

						return ioPreview;
					},

					_loadPreviewFn: function(className, classPK) {
						var instance = this;

						var ioPreview = instance._getIOPreview();

						ioPreview.stop();

						ioPreview.set('data.classPK', classPK);
						ioPreview.set('data.className', className);

						ioPreview.start();
					},

					_onTooltipShow: function(event) {
						var instance = this;

						var tooltip = instance._tooltip;

						tooltip.set(BODY_CONTENT, TPL_LOADING);

						var currentNode = tooltip.get(STR_CURRENT_NODE);

						if (instance._previousNode && (instance._previousNode != currentNode)) {
							currentNode.addClass(CSS_OVER);

							instance._previousNode.removeClass(CSS_OVER);
						}

						instance._previousNode = currentNode;

						instance._loadPreviewTask(currentNode.attr('data-class-name'), currentNode.attr('data-class-pk'));

						tooltip.get(STR_BOUNDING_BOX).show();
					}
				}
			}
		);

		var AddContentTooltip = A.Component.create(
			{
				EXTENDS: A.Tooltip,
				NAME: 'addcontenttooltip',
				prototype: {
					_setShowOn: function(eventType) {
						var instance = this;

						AddContentTooltip.superclass._setShowOn.call(instance, eventType);

						var trigger = instance.get('trigger');

						trigger.detach('mousedown', instance._stopTriggerEventPropagation);

						instance.get(STR_BOUNDING_BOX).hide();
					}
				}
			}
		);

		Dockbar.AddContentPreview = AddContentPreview;
		Dockbar.AddContentTooltip = AddContentTooltip;
	},
	'',
	{
		requires: ['aui-tooltip', 'event-mouseenter', 'liferay-dockbar']
	}
);