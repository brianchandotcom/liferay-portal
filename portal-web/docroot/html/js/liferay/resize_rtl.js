AUI.add(
	'liferay-resize-rtl',
	function(A) {
		var ARules = A.Resize.RULES;

		var TEMP_L_RULE = ARules.l;

		ARules.l = ARules.r;
		ARules.r = TEMP_L_RULE;
	},
	'',
	{
		requires: 'resize-base'
	}
);