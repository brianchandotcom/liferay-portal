;(function(A, Liferay) {
	var Tabs = Liferay.namespace('Portal.Tabs');
	var ToolTip = Liferay.namespace('Portal.ToolTip');

	var arrayIndexOf = A.Array.indexOf;

	var toCharCode = Liferay.Util.toCharCode;

	var BODY_CONTENT = 'bodyContent';

	var REGION = 'region';

	var TRIGGER = 'trigger';

	Liferay.Portal.Tabs._show = function(event) {
		var id = event.id;
		var names = event.names;
		var namespace = event.namespace;

		var selectedIndex = event.selectedIndex;

		var tabItem = event.tabItem;
		var tabSection = event.tabSection;

		if (tabItem) {
			tabItem.radioClass('active');
		}

		if (tabSection) {
			tabSection.show();
		}

		names.splice(selectedIndex, 1);

		var el;

		for (var i = 0; i < names.length; i++) {
			el = A.one('#' + namespace + toCharCode(names[i]) + 'TabsSection');

			if (el) {
				el.hide();
			}
		}
	};

	Liferay.provide(
		Tabs,
		'show',
		function(namespace, names, id, callback) {
			var namespacedId = namespace + toCharCode(id);

			var tab = A.one('#' + namespacedId + 'TabsId');
			var tabSection = A.one('#' + namespacedId + 'TabsSection');

			var details = {
				id: id,
				names: names,
				namespace: namespace,
				selectedIndex: arrayIndexOf(names, id),
				tabItem: tab,
				tabSection: tabSection
			};

			if (callback && A.Lang.isFunction(callback)) {
				callback.call(this, namespace, names, id, details);
			}

			Liferay.fire('showTab', details);
		},
		['aui-base']
	);

	Liferay.publish(
		'showTab',
		{
			defaultFn: Liferay.Portal.Tabs._show
		}
	);

	ToolTip._getText = A.cached(
		function(id) {
			var node = A.one('#' + id);

			var text = '';

			if (node) {
				var toolTipTextNode = node.next('.tooltip-text');

				if (toolTipTextNode) {
					text = toolTipTextNode.html();
				}
			}

			return text;
		}
	);

	ToolTip.hide = function() {
		var instance = this;

		var cached = instance._cached;

		if (cached) {
			cached.hide();
		}
	};

	Liferay.provide(
		ToolTip,
		'show',
		function(obj, text) {
			var instance = this;

			var cached = instance._cached;

			if (!cached) {
				cached = new A.Tooltip(
					{
						zIndex: Liferay.zIndex.TOOLTIP,
						visible: false
					}
				).render();

				instance._cached = cached;
			}

			if (text == null) {
				obj = A.one(obj);

				text = instance._getText(obj.guid());
			}

			cached.set(BODY_CONTENT, text);

			cached.set(TRIGGER, obj).show();
		},
		['aui-tooltip-deprecated']
	);
})(AUI(), Liferay);