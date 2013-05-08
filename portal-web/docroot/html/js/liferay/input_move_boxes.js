AUI.add(
	'liferay-input-move-boxes',
	function(A) {
		var Lang = A.Lang;
		var Util = Liferay.Util;

		var getClassName = A.ClassNameManager.getClassName;

		var	NAME = 'inputmoveboxes';

		var CONFIG_REORDER = {
			children: [
				[
					{
						cssClass: 'reorder-up',
						icon: 'icon-circle-arrow-up'
					},
					{
						cssClass: 'reorder-down',
						icon: 'icon-circle-arrow-down'
					}
				]
			]
		};

		var	CSS_INPUTMOVEBOXES = getClassName(NAME);

		var CSS_LEFT_REORDER = 'left-reorder';

		var CSS_RIGHT_REORDER = 'right-reorder';

		var InputMoveBoxes = A.Component.create(
			{
				ATTRS: {
					leftReorder: {
					},

					rightReorder: {
					},

					strings: {
						LEFT_MOVE_DOWN: '',
						LEFT_MOVE_UP: '',
						MOVE_LEFT: '',
						MOVE_RIGHT: '',
						RIGHT_MOVE_DOWN: '',
						RIGHT_MOVE_UP: ''
					}
				},

				HTML_PARSER: {
					leftReorder: function(contentBox) {
						return contentBox.hasClass(CSS_LEFT_REORDER);
					},

					rightReorder: function(contentBox) {
						return contentBox.hasClass(CSS_RIGHT_REORDER);
					}
				},

				NAME: NAME,

				prototype: {
					renderUI: function() {
						var instance = this;

						instance._renderBoxes();
						instance._renderButtons();
					},

					bindUI: function() {
						var instance = this;

						var leftReorderToolbar = instance._leftReorderToolbar;

						if (leftReorderToolbar) {
							leftReorderToolbar.after('btn:click', A.rbind('_afterOrderClick', instance, instance._leftBox));
						}

						var rightReorderToolbar = instance._rightReorderToolbar;

						if (rightReorderToolbar) {
							rightReorderToolbar.after('btn:click', A.rbind('_afterOrderClick', instance, instance._rightBox));
						}

						instance._moveToolbar.on('btn:click', instance._afterMoveClick, instance);

						instance._leftBox.on('focus', A.rbind('_onSelectFocus', instance, instance._rightBox));
						instance._rightBox.on('focus', A.rbind('_onSelectFocus', instance, instance._leftBox));
					},

					_afterMoveClick: function(event) {
						var instance = this;

						var cssClass = event.target.get('cssClass');

						var from = instance._leftBox;
						var to = instance._rightBox;
						var sort;

						if (cssClass.indexOf('move-right') != -1) {
							from = instance._rightBox;
							to = instance._leftBox;
							sort = !instance.get('leftReorder');
						}
						else {
							sort = !instance.get('rightReorder');
						}

						Util.moveItem(from, to, sort);

						Liferay.fire(
							NAME + ':moveItem',
							{
								fromBox: from,
								toBox: to
							}
						);
					},

					_afterOrderClick: function(event, box) {
						var instance = this;

						var cssClass = event.target.get('cssClass');

						var direction = 1;

						if (cssClass.indexOf('reorder-up') != -1) {
							direction = 0;
						}

						Util.reorder(box, direction);
					},

					_onSelectFocus: function(event, box) {
						var instance = this;

						box.set('selectedIndex', '-1');
					},

					_renderBoxes: function() {
						var instance = this;

						var contentBox = instance.get('contentBox');

						instance._leftBox = contentBox.one('.left-selector select');
						instance._rightBox = contentBox.one('.right-selector select');
					},

					_renderButtons: function() {
						var instance = this;

						var contentBox = instance.get('contentBox');

						var moveButtonsColumn = contentBox.one('.move-arrow-buttons-content');

						var strings = instance.get('strings');

						if (moveButtonsColumn) {
							instance._moveToolbar = new A.Toolbar(
								{
									children: [
										[
											'normal',
											'vertical',
											{
												cssClass: 'move-left',
												icon: 'icon-circle-arrow-left',
												title: strings.MOVE_LEFT
											},
											{
												cssClass: 'move-right',
												icon: 'icon-circle-arrow-right',
												title: strings.MOVE_RIGHT
											}
										]
									]
								}
							).render(moveButtonsColumn);
						}

						if (instance.get('leftReorder')) {
							var leftColumn = contentBox.one('.left-selector-column-content');

							CONFIG_REORDER.children[0][0].title = strings.LEFT_MOVE_UP;
							CONFIG_REORDER.children[0][1].title = strings.LEFT_MOVE_DOWN;

							instance._leftReorderToolbar = new A.Toolbar(CONFIG_REORDER).render(leftColumn);
						}

						if (instance.get('rightReorder')) {
							var rightColumn = contentBox.one('.right-selector-column-content');

							CONFIG_REORDER.children[0][0].title = strings.RIGHT_MOVE_UP;
							CONFIG_REORDER.children[0][1].title = strings.RIGHT_MOVE_DOWN;

							instance._rightReorderToolbar = new A.Toolbar(CONFIG_REORDER).render(rightColumn);
						}
					}
				}
			}
		);

		Liferay.InputMoveBoxes = InputMoveBoxes;
	},
	'',
	{
		requires: ['aui-base', 'aui-toolbar']
	}
);