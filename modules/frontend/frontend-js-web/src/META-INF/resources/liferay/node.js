AUI.add(
	'liferay-node',
	function(A) {
		var getRegExp = A.DOM._getRegExp;

		var prefix = A.Lang.String.prefix;

		var formatSelectorNS = function(ns, selector) {
			return selector.replace(getRegExp('(#|\\[id=(\\\"|\\\'))(?!' + ns + ')', 'g'), '$1' + ns);
		};

		A.mix(
			A.Node.prototype,
			{
				allNS: function(ns, selector) {
					var instance = this;

					return instance.all(formatSelectorNS(ns, selector));
				},

				oneNS: function(ns, selector) {
					var instance = this;

					return instance.one(formatSelectorNS(ns, selector));
				}
			}
		);

		A.Node.formatSelectorNS = formatSelectorNS;

		A.queryNS = function(ns, selector, methodName) {
			return A[methodName || 'one'](formatSelectorNS(ns, selector));
		};

		A.oneNS = A.queryNS;

		A.allNS = function(ns, selector) {
			return A.queryNS(ns, selector, 'all');
		};

		A.byIdNS = function(ns, id) {
			return A.one('#' + prefix(ns, id));
		};
	},
	'',
	{
		requires: ['aui-node-base']
	}
);