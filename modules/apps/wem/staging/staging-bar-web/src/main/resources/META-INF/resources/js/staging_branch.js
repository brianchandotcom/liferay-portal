AUI.add(
	'liferay-staging-branch',
	function(A) {
		var Lang = A.Lang;

		var StagingBar = Liferay.StagingBar;

		var MAP_TEXT_REVISION = {
			redo: Liferay.Language.get('are-you-sure-you-want-to-redo-your-last-changes'),
			undo: Liferay.Language.get('are-you-sure-you-want-to-undo-your-last-changes')
		};

		A.mix(
			StagingBar,
			{
				addBranch: function(dialogTitle) {
					var instance = this;

					var branchDialog = instance._getBranchDialog();

					if (Lang.isValue(dialogTitle)) {
						branchDialog.set('title', dialogTitle);
					}

					branchDialog.show();
				},

				_getBranchDialog: function() {
					var instance = this;

					var branchDialog = instance._branchDialog;

					if (!branchDialog) {
						var namespace = instance._namespace;

						branchDialog = Liferay.Util.Window.getWindow(
							{
								dialog: {
									bodyContent: A.one('#' + namespace + 'addBranch').show()
								},
								title: Liferay.Language.get('branch')
							}
						);

						instance._branchDialog = branchDialog;
					}

					return branchDialog;
				}
			}
		);
	},
	'',
	{
		requires: ['liferay-staging']
	}
);