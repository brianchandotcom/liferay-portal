(function() {
	var _ALLOY = YUI();

	window.AUI = function() {
		return _ALLOY;
	};

	_ALLOY.mix(AUI, YUI);

	window.Liferay = {
		AUI: {
			getCombine: sinon.stub().returns(false),
			getFilterConfig: sinon.stub().returns(null)
		},

		Loader: Loader,

		ThemeDisplay: {
			getDefaultLanguageId: sinon.stub().returns('en_US'),
			getLanguageId: sinon.stub().returns('en_US')
		},

		Util: {
			getLexiconIconTpl: sinon.stub().returns('')
		}
	};

	Liferay.namespace = YUI.namespace;

	Liferay.Language = {};

	Liferay.Language.get = function(key) {
		return key;
	};

	Liferay.Language.available = sinon.stub().returns({});
	Liferay.Language.direction = sinon.stub().returns({});

	window.themeDisplay = Liferay.ThemeDisplay;
})();