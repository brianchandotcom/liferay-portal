AUI.add(
	'liferay-list-view',
	function(A) {
		var Lang = A.Lang;
		var isString = Lang.isString;
		var getClassName = A.getClassName;

		var CONTENT_BOX = 'contentBox';

		var NAME = 'listview';

		var CSS_DATA_CONTAINER = 'lfr-list-view-data-container';

		var STR_BOTTOM = 'bottom';

		var STR_LEFT = 'left';

		var STR_REGION = 'region';

		var STR_RIGHT = 'right';

		var STR_TOP = 'top';

		var TPL_DATA_CONTAINER = '<div class="' + CSS_DATA_CONTAINER + ' hide"></div>';

		var UI_SRC = A.Widget.UI_SRC;

		var ListView = A.Component.create(
			{
				ATTRS: {
					cssClass: {
						value: 'lfr-list-view'
					},
					data: {
						setter: '_setData',
						validator: '_validateData',
						value: null
					},

					direction: {
						validator: '_validateDirection',
						value: STR_LEFT
					},

					item: {
						validator: Lang.isObject,
						value: null
					},

					itemChosenEvent: {
						validator: isString,
						value: 'click'
					},

					itemSelector: {
						validator: isString,
						value: null
					},

					transitionConfig: {
						validator: Lang.isObject,
						value: {
							duration: 0.3,
							easing: 'ease-out',
							left: 0,
							top: 0
						}
					},

					useTransition: {
						validator: Lang.isBoolean,
						value: true
					}
				},

				NAME: NAME,

				prototype: {
					initializer: function() {
						var instance = this;

						instance._transitionCompleteProxy = A.fn(instance.fire, instance, 'transitionComplete');
					},

					renderUI: function() {
						var instance = this;

						var boundingBox = instance.get('boundingBox');

						var dataContainer = A.Node.create(TPL_DATA_CONTAINER);

						boundingBox.append(dataContainer);

						instance._dataContainer = dataContainer;
					},

					bindUI: function() {
						var instance = this;

						var contentBox = instance.get(CONTENT_BOX);

						var itemChosenEvent = instance.get('itemChosenEvent');
						var itemSelector = instance.get('itemSelector');

						instance._itemChosenHandle = contentBox.delegate(itemChosenEvent, instance._onItemChosen, itemSelector, instance);

						instance.after('dataChange', instance._afterDataChange);

						instance.publish(
							'transitionComplete',
							{
								defaultFn: instance._defTransitionCompletedFn
							}
						);
					},

					destructor: function() {
						var instance = this;

						if (instance._itemChosenHandle) {
							instance._itemChosenHandle.detach();
						}

						if (instance._dataContainer) {
							instance._dataContainer.destroy(true);
						}
					},

					_afterDataChange: function(event) {
						var instance = this;

						var useTransition = instance.get('useTransition');

						var newData = event.newVal;

						if (useTransition) {
							var dataContainer = instance._dataContainer;

							dataContainer.plug(A.Plugin.ParseContent);

							dataContainer.setContent(newData);

							instance._moveContainer();
						}
						else {
							var contentBox = instance.get(CONTENT_BOX);

							contentBox.plug(A.Plugin.ParseContent);

							contentBox.setContent(newData);
						}
					},

					_defTransitionCompletedFn: function(event) {
						var instance = this;

						var dataContainer = instance._dataContainer;

						instance.get(CONTENT_BOX).setContent(dataContainer.getDOM().childNodes);

						dataContainer.hide();
						dataContainer.empty();
					},

					_onItemChosen: function(event) {
						var instance = this;

						event.preventDefault();

						instance.set(
							'item',
							event.currentTarget,
							{
								src: UI_SRC
							}
						);
					},

					_moveContainer: function() {
						var instance = this;

						var contentBox = instance.get(CONTENT_BOX);

						var targetRegion = contentBox.get(STR_REGION);

						instance._setDataContainerPosition(targetRegion);

						var dataContainer = instance._dataContainer;

						dataContainer.show();

						var transitionConfig = instance.get('transitionConfig');

						dataContainer.transition(transitionConfig, instance._transitionCompleteProxy);
					},

					_setData: function(value) {
						if (isString(value)) {
							value = A.Node.create(value);
						}

						return value;
					},

					_setDataContainerPosition: function(targetRegion) {
						var instance = this;

						targetRegion = targetRegion || instance.get(CONTENT_BOX).get(STR_REGION);

						var direction = instance.get('direction');

						var dataContainer = instance._dataContainer;

						var styles = {
							left: 0,
							top: 0
						};

						var valid = true;

						if (direction == STR_LEFT) {
							styles.left = targetRegion.width;
						}
						else if (direction == STR_RIGHT) {
							styles.left = -(targetRegion.width);
						}
						else if (direction == STR_TOP) {
							styles.top = -(targetRegion.height);
						}
						else if (direction == STR_BOTTOM) {
							styles.top = targetRegion.height;
						}
						else {
							valid = false;
						}

						dataContainer.setStyles(styles);
					},

					_validateData: function(value) {
						return isString(value) || A.instanceOf(value, A.Node);
					},

					_validateDirection: function(value) {
						return value === STR_BOTTOM || value === STR_LEFT ||
							value === STR_RIGHT || value === STR_TOP;
					}
				}
			}
		);

		Liferay.ListView = ListView;
	},
	'',
	{
		requires: ['aui-base', 'transition']
	}
);