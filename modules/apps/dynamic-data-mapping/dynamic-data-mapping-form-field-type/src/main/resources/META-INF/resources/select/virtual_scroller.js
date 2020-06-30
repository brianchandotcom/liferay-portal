AUI.add(
	'liferay-ddm-form-field-select-virtual-scroller',
	function(A) {
        var DEFAULT_ITEM_HEIGHT = 43;

		var VirtualScroller = A.Component.create(
			{
				ATTRS: {
                    element: {
                        value: null
                    },

					items: {
						value: []
                    },

                    itemSelector: {
                        value: 'li'
                    },

                    paddingItemsCount: {
                        value: 8
                    },

                    scrollTop: {
                        value: 0
                    },
                    
                    viewportItemsCount: {
                        value: 10
                    }
				},

				EXTENDS: A.Base,

				NAME: 'liferay-ddm-form-field-select-virtual-scroller',

				prototype: {
					initializer: function() {
                        var instance = this;

                        instance.bindScrollEvent();
                        
                        instance.after('elementChange', A.bind(instance._afterElementChange, instance));

                        instance.lastUpdatedStartIndex = 0;
					},

					destructor: function() {
                        var instance = this;

                        instance.unbindScrollEvent();
                    },

                    bindScrollEvent: function() {
                        var instance = this;

                        instance.unbindScrollEvent();

                        var element = instance.get('element');

                        if (element && element.inDoc()) {
                            instance._scrollEventHandler = element.on('scroll', A.bind(instance._onScroll, instance));    
                        }
                    },

                    getItemHeight: function() {
                        var instance = this;

                        var element = instance.get('element');

                        if (!element) {
                            return DEFAULT_ITEM_HEIGHT;
                        }

                        if (!element.inDoc()) {
							return DEFAULT_ITEM_HEIGHT;
                        }

                        var itemSelector = instance.get('itemSelector');
                        var firstItem = element.one(itemSelector);

                        if (!firstItem) {
                            return DEFAULT_ITEM_HEIGHT;
                        }

                        return firstItem.get('offsetHeight') || DEFAULT_ITEM_HEIGHT;
                    },

                    getPaddingBottom: function() {
                        var instance = this;

                        var paddingTop = instance.getPaddingTop();

                        var itemHeight = instance.getItemHeight();
                        var items = instance.get('items');
                        var visibleItemsCount = instance.getVisibleItemsCount();

                        return items.length * itemHeight - (visibleItemsCount * itemHeight + paddingTop);
                    },

                    getPaddingTop: function() {
                        var instance = this;

                        return instance.getStartIndex() * instance.getItemHeight();
                    },

                    getStartIndex: function() {
                        var instance = this;

                        var visibleIndex = instance.getVisibleStartIndex();

						return Math.max(0, visibleIndex - instance.get('paddingItemsCount'));
                    },

                    getViewportItemsCount: function() {
                        var instance = this;
                        
                        var element = instance.get('element');

                        var defaultViewportItemsCount = instance.get('viewportItemsCount');

                        if (!element) {
                            return defaultViewportItemsCount;
                        }

                        if (!element.inDoc()) {
                            return defaultViewportItemsCount;
                        }

                        return Math.ceil(element.get('offsetHeight') / instance.getItemHeight());
                    },

                    getVisibleItems: function() {
                        var instance = this;

						var items = instance.get('items');
						var length = items.length;

						if (length <= instance.getViewportItemsCount()) {
							return items;
						}

                        var startIndex = instance.getStartIndex();
                        var visibleItemsCount = instance.getVisibleItemsCount();

                        return items.slice(startIndex, startIndex + visibleItemsCount);
                    },

                    getVisibleItemsCount: function() {
                        var instance = this;

                        var viewportItemsCount = instance.getViewportItemsCount();
                        var paddingItemsCount = instance.get('paddingItemsCount');

                        return viewportItemsCount + 2 * paddingItemsCount;
                    },

                    getVisibleStartIndex: function() {
                        var instance = this;

                        var element = instance.get('element');

                        if (!element) {
                            return 0;
                        }

						if (!element.inDoc()) {
							return 0;
						}

                        var scrollTop = instance.get('scrollTop');

                        return Math.floor(scrollTop / instance.getItemHeight());
                    },

                    unbindScrollEvent: function() {
                        var instance = this;

                        if (instance._scrollEventHandler) {
                            instance._scrollEventHandler.detach();
                        }
                    },

                    _afterElementChange: function(event) {
                        var instance = this;

                        instance.bindScrollEvent();

                        var element = event.newVal;

                        instance.set('scrollTop', element.get('scrollTop'));
                    },

                    _onScroll: function() {
                        var instance = this;

                        var element = instance.get('element');

                        instance.set('scrollTop', element.get('scrollTop'));

                        var updateThreshold = instance.get('paddingItemsCount') / 2;
                        var viewportItemsCount = instance.getViewportItemsCount();

                        var visibleStartIndex = instance.getVisibleStartIndex();
                        var visibleEndIndex = visibleStartIndex + viewportItemsCount;

                        if (
                            visibleStartIndex === 0 ||
                            visibleStartIndex < instance.lastVisibleStartIndex - updateThreshold ||
                            visibleEndIndex > instance.lastVisibleStartIndex + viewportItemsCount + updateThreshold
                        ) {
                            instance.lastVisibleStartIndex = visibleStartIndex;

                            instance.fire('update');
                        }
                    }
				}
			}
		);

		Liferay.namespace('DDM').VirtualScroller = VirtualScroller;
	},
	'',
	{
		requires: ['aui-base']
	}
);