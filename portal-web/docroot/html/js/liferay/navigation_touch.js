AUI.add(
	'liferay-navigation-touch',
	function(A) {
		var Util = Liferay.Util;

		var SELECTOR_DRAG_HANDLE = '.drag-handle';

		var SELECTOR_LFR_NAV_SORTABLE = '.lfr-nav-sortable';

		var TPL_DRAG_HANDLE = '<span class="drag-handle"><i class="icon-reorder"></i></span>';

		var afterMakeSortable = Liferay.Navigation.prototype._afterMakeSortable;

		A.mix(
			Liferay.Navigation.prototype,
			{
				_afterMakeSortable: function(sortable) {
					var instance = this;

					var navItems = instance.get('navBlock').all(instance._navItemSelector);

					var sortableDD = sortable.delegate.dd;

					instance._sortableDD = sortableDD;

					instance._createDragHandles(navItems);

					afterMakeSortable.call(instance, sortable);

					sortableDD.plug(A.Plugin.DDConstrained);

					sortableDD.on(
						['drag:drophit', 'drag:dropmiss'],
						function(event) {
							event.halt();
						}
					);

					instance._toggleDragConfig(sortableDD);

					A.on('windowresize', A.bind('_onWindowResize', instance));
				},

				_createDragHandles: function(items) {
					var instance = this;

					var layoutIds = instance.get('layoutIds');

					items.each(
						function(item, index) {
							var layoutConfig = layoutIds[index];

							if (layoutConfig && layoutConfig.sortable && layoutConfig.id == item.getData('layoutId')) {
								item.append(TPL_DRAG_HANDLE);
							}
						}
					);
				},

				_onWindowResize: function() {
					var instance = this;

					instance._toggleDragConfig(instance._sortableDD);
				},

				_toggleDragConfig: function(dd) {
					var instance = this;

					var tablet = Util.isTablet();

					dd.con.set('stickY', tablet);

					var addHandleString = SELECTOR_DRAG_HANDLE;
					var removeHandleString = SELECTOR_LFR_NAV_SORTABLE;

					if (!tablet) {
						addHandleString = SELECTOR_LFR_NAV_SORTABLE;
						removeHandleString = SELECTOR_DRAG_HANDLE;
					}

					dd.addHandle(addHandleString);
					dd.removeHandle(removeHandleString);
				}
			},
			true
		);
	},
	'',
	{
		requires: ['dd-constrain', 'event-resize', 'event-touch', 'liferay-navigation']
	}
);