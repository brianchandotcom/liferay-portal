Liferay.Util.portletTitleEdit = function() {
};

if (!themeDisplay.isStatePopUp()) {
	AUI().use(
		'liferay-control-panel',
		function(A) {
			new Liferay.ControlPanel();
		}
	);
}