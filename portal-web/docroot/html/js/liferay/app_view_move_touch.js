AUI.add(
	'liferay-app-view-move-touch',
	function(A) {
		if (Liferay.Util.isTablet()) {
			Liferay.AppViewMove.prototype._registerDragDrop = A.Lang.emptyFn;
		}
	},
	'',
	{
		requires: ['liferay-app-view-move']
	}
);