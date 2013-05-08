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

				mergeBranch: function(options) {
					var instance = this;

					var mergeDialog = instance._getMergeDialog();

					var mergeDialogIO = mergeDialog.io;

					mergeDialogIO.set('uri', options.uri);

					mergeDialogIO.start();

					var dialogTitle = options.dialogTitle;

					if (Lang.isValue(dialogTitle)) {
						mergeDialog.set('title', dialogTitle);
					}

					mergeDialog.show();
				},

				updateBranch: function(options) {
					var instance = this;

					var updateBranchDialog = instance._getUpdateBranchDialog();

					var updateBranchDialogIO = updateBranchDialog.io;

					updateBranchDialogIO.set('uri', options.uri);

					updateBranchDialogIO.start();

					var dialogTitle = options.dialogTitle;

					if (Lang.isValue(dialogTitle)) {
						updateBranchDialog.set('title', dialogTitle);
					}

					updateBranchDialog.show();
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

						branchDialog.move(branchDialog.get('x'), branchDialog.get('y') + 10);

						instance._branchDialog = branchDialog;
					}

					return branchDialog;
				},

				_getMergeDialog: function() {
					var instance = this;

					var mergeDialog = instance._mergeDialog;

					if (!mergeDialog) {
						mergeDialog = Liferay.Util.Window.getWindow(
						    {
								title: Liferay.Language.get('merge')
						    }
						);

						mergeDialog.plug(
							A.Plugin.IO,
							{
								autoLoad: false,
								data: {
									doAsUserId: themeDisplay.getDoAsUserIdEncoded(),
									p_l_id: themeDisplay.getPlid(),
									redirect: Liferay.currentURL
								}
							}
						);

						mergeDialog.move(mergeDialog.get('x'), mergeDialog.get('y') + 100);

						mergeDialog.bodyNode.delegate(
							'click',
							function(event) {
								var node = event.currentTarget;

								instance._onMergeBranch(node);
							},
							'a.layout-set-branch'
						);

						instance._mergeDialog = mergeDialog;
					}

					return mergeDialog;
				},

				_getUpdateBranchDialog: function() {
					var instance = this;

					var	updateBranchDialog = Liferay.Util.Window.getWindow(
						{
							title: Liferay.Language.get('branch')
						}
					);

					updateBranchDialog.plug(
						A.Plugin.IO,
						{
							autoLoad: false,
							data: {
								doAsUserId: themeDisplay.getDoAsUserIdEncoded(),
								p_l_id: themeDisplay.getPlid()
							}
						}
					);

					return updateBranchDialog;
				},

				_onMergeBranch: function(node) {
					var instance = this;

					var namespace = instance._namespace;

					var addBranch = A.one('#' + namespace + 'addBranch');

					var mergeLayoutSetBranchId = node.attr('data-layoutSetBranchId');
					var mergeLayoutSetBranchName = node.attr('data-layoutSetBranchName');
					var mergeLayoutSetBranchMessage = node.attr('data-layoutSetBranchMessage');

					if (confirm(mergeLayoutSetBranchMessage)) {
						var form = A.one('#' + namespace + 'fm4');

						form.one('#' + namespace + 'mergeLayoutSetBranchId').val(mergeLayoutSetBranchId);

						submitForm('#' + namespace + 'fm4');
					}
				}
			}
		);
	},
	'',
	{
		requires: ['liferay-staging']
	}
);