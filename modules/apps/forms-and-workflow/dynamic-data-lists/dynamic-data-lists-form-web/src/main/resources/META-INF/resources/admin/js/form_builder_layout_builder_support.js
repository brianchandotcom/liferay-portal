AUI.add(
	'liferay-ddl-form-builder-layout-builder-support',
	function(A) {
		var Lang = A.Lang;

		var CSS_FORM_BUILDER_MOVING_MESSAGE = A.getClassName('form', 'builder', 'moving', 'message');

		var CSS_FORM_BUILDER_MOVING_MESSAGE_DISMISS_BUTTOM = A.getClassName('form', 'builder', 'moving', 'message', 'dismiss', 'button');

		var CSS_LAYOUT_BUILDER_MOVE_CUT_COL_BUTTON = A.getClassName('layout', 'builder', 'move', 'cut', 'col', 'button');

		var FormBuilderLayoutBuilderSupport = function() {
		};

		FormBuilderLayoutBuilderSupport.ATTRS = {
		};

		FormBuilderLayoutBuilderSupport.prototype = {
			TPL_MOVE_MESSAGE: '<div class="' + CSS_FORM_BUILDER_MOVING_MESSAGE + '">' +
				'<button class="btn btn-lg btn-default ' + CSS_FORM_BUILDER_MOVING_MESSAGE_DISMISS_BUTTOM + '" type="button">{dismiss-operation}</button>' +
			'</div>',

			initializer: function() {
				var instance = this;

				var boundingBox = instance.get('boundingBox');

				instance._eventHandlers.push(
					instance.after('render', instance._afterMainClassRender, instance),
					boundingBox.delegate('mouseenter', A.bind(instance._showCutRowHelperMessage, instance), '.layout-builder-move-cut-row-button'),
					boundingBox.delegate('mouseleave', A.bind(instance._hideCutRowHelperMessage, instance), '.layout-builder-move-cut-row-button')
				);
			},

			moveField: function(field) {
				var instance = this;

				var fieldNode = field.get('content');

				var col = fieldNode.ancestor('.col').getData('layout-col');

				instance._layoutBuilder.get('chooseColMoveTarget')(fieldNode.one('.form-group'), col);
			},

			_afterMainClassRender: function() {
				var instance = this;

				instance._bindLayoutBuilderEvents();
				instance._createPopoverHelperMessage();
			},

			_bindLayoutBuilderEvents: function() {
				var instance = this;

				instance._eventHandlers.push(
					instance._layoutBuilder.after('layout-builder:moveStart', A.bind(instance._onLayoutMoveStart, instance)),
					instance._layoutBuilder.after('layout-builder:moveEnd', A.bind(instance._onLayoutMoveEnd, instance))
				);
			},

			_createMovingRowMessage: function() {
				var instance = this;

				return A.Node.create(
					Lang.sub(
						instance.TPL_MOVE_MESSAGE,
						{
							'dismiss-operation': Liferay.Language.get('dismiss-operation')
						}
					)
				);
			},

			_createPopoverHelperMessage: function() {
				var instance = this;

				var popover = new A.Popover(
					{
						constrain: true,
						position: 'top',
						visible: false,
						zIndex: 50
					}
				).render();

				instance._popoverHelperMessage = popover;
			},

			_hideCutRowHelperMessage: function() {
				var instance = this;

				instance._popoverHelperMessage.hide();
			},

			_onClickOutsideMoveColTarget: function(event) {
				var instance = this;

				var target = event.target;

				if (!target.hasClass(CSS_LAYOUT_BUILDER_MOVE_CUT_COL_BUTTON)) {
					Liferay.DDL.FormBuilder.superclass._onClickOutsideMoveColTarget.apply(instance, arguments);
				}
			},

			_onClickOutsideMoveRowTarget: function(event) {
				var targetNode = event.target;

				if (targetNode.hasClass(CSS_FORM_BUILDER_MOVING_MESSAGE_DISMISS_BUTTOM)) {
					this._layoutBuilder.cancelMove();
				}
			},

			_onLayoutMoveEnd: function(event) {
				var instance = this;

				var pageManager = instance._pageManager;

				instance._removeMovingMessage();

				pageManager.toggleControlsTriggerDisabled(false);
				pageManager.toggleDescriptionDisabled(false);
				pageManager.toggleTitleDisabled(false);
			},

			_onLayoutMoveStart: function(event) {
				var instance = this;

				var pageManager = instance._pageManager;

				var row = event.moveElement;

				if (A.instanceOf(row, A.LayoutRow)) {
					instance._showMovingRowMessage(row);
				}

				pageManager.toggleControlsTriggerDisabled(true);
				pageManager.toggleDescriptionDisabled(true);
				pageManager.toggleTitleDisabled(true);
			},

			_removeMovingMessage: function() {
				var instance = this;

				var boundingBox = instance.get('boundingBox');

				var movingMessageNode = boundingBox.one('.' + CSS_FORM_BUILDER_MOVING_MESSAGE);

				if (movingMessageNode) {
					movingMessageNode.remove();
				}
			},

			_showCutRowHelperMessage: function(event) {
				var instance = this;

				var alignToNode = {
					node: event.currentTarget,
					points: [A.WidgetPositionAlign.RC, A.WidgetPositionAlign.TC]
				};

				var popoverHelperMessage = instance._popoverHelperMessage;

				popoverHelperMessage.set('bodyContent', Liferay.Language.get('click-to-cut-row'));
				popoverHelperMessage.set('align', alignToNode);

				popoverHelperMessage.show();
			},

			_showMovingRowMessage: function(row) {
				var instance = this;

				var movingMessage = instance._createMovingRowMessage();

				var rowNode = row.get('node');

				movingMessage.appendTo(rowNode);
			}
		};

		Liferay.namespace('DDL').FormBuilderLayoutBuilderSupport = FormBuilderLayoutBuilderSupport;
	},
	'',
	{
		requires: []
	}
);