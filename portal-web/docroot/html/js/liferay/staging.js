AUI().add(
	'liferay-staging',
	function(A) {
		var Lang = A.Lang;

		var Staging = {};

		var MAP_TEXT_REVISION = {
			redo: Liferay.Language.get('are-you-sure-you-want-to-redo-your-last-changes'),
			undo: Liferay.Language.get('are-you-sure-you-want-to-undo-your-last-changes')
		};

		var MAP_CMD_REVISION = {
			redo: 'redo_layout_revision',
			undo: 'undo_layout_revision'
		};

		var Branching = {
			init: function(options) {
				var instance = this;

				instance._namespace = options.namespace;
			},

			addBranch: function() {
				var instance = this;

				var branchDialog = instance._getBranchDialog();

				branchDialog.show();
			},

			addRootLayoutRevision: function() {
				var instance = this;

				var variationDialog = instance._getVariationDialog();

				variationDialog.show();
			},

			mergeBranch: function(options) {
				var instance = this;

				var mergeDialog = instance._getMergeDialog();
				var mergeDialogIO = mergeDialog.io;

				mergeDialogIO.set('uri', options.uri);
				mergeDialogIO.start();

				mergeDialog.show();
			},

			_getBranchDialog: function() {
				var instance = this;

				var branchDialog = instance._branchDialog;

				if (!branchDialog) {
					var namespace = instance._namespace;

					branchDialog = new A.Dialog(
						{
							align: {
								points: ['tc', 'tc']
							},
							bodyContent: A.one('#' + namespace + 'addBranch').show(),
							modal: true,
							title: Liferay.Language.get('add-backstage'),
							width: 530
						}
					).render();

					branchDialog.move(branchDialog.get('x'), branchDialog.get('y') + 10);

					instance._branchDialog = branchDialog;
				}

				return branchDialog;
			},

			_getVariationDialog: function() {
				var instance = this;

				var variationDialog = instance._variationDialog;

				if (!variationDialog) {
					var namespace = instance._namespace;

					variationDialog = new A.Dialog(
						{
							align: {
								points: ['tc', 'tc']
							},
							bodyContent: A.one('#' + namespace + 'addRootLayoutRevision').show(),
							title: Liferay.Language.get('new-page-variation'),
							modal: true,
							width: 530
						}
					).render();

					variationDialog.move(variationDialog.get('x'), variationDialog.get('y') + 100);

					instance._variationDialog = variationDialog;
				}

				return variationDialog;
			},

			_getMergeDialog: function() {
				var instance = this;

				var mergeDialog = instance._mergeDialog;

				if (!mergeDialog) {
					mergeDialog = new A.Dialog(
						{
							align: {
								points: ['tc', 'tc']
							},
							draggable: true,
							modal: true,
							title: Liferay.Language.get('merge-changes-from-branch'),
							width: 530
						}
					).plug(
						A.Plugin.IO,
						{
							autoLoad: false,
							data: {
								doAsUserId: themeDisplay.getDoAsUserIdEncoded(),
								p_l_id: themeDisplay.getPlid(),
								redirect: Liferay.currentURL
							}
						}
					).render();

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

			_onMergeBranch: function(node) {
				var instance = this;

				var namespace = instance._namespace;

				var addBranch = A.one('#' + namespace + 'addBranch');

				var mergeLayoutSetBranchId = node.attr('data-layoutSetBranchId');
				var mergeLayoutSetBranchName = node.attr('data-layoutSetBranchName');

				if (confirm(Liferay.Language.get('are-you-sure-you-want-to-merge-changes-from-backstage') + ' ' + mergeLayoutSetBranchName)) {
					var form = A.one('#' + namespace + 'fm4');

					form.one('#' + namespace + 'mergeLayoutSetBranchId').val(mergeLayoutSetBranchId);

					submitForm('#' + namespace + 'fm4');
				}
			}
		};

		var Dockbar = {
			init: function(options) {
				var instance = this;

				var namespace = options.namespace;

				instance._namespace = namespace;

				var backstageToolbar = new A.Toolbar(
					{
						activeState: false,
						boundingBox: '#' + namespace + 'backstageToolbar',
						children: [
							{
							type: 'ToolbarSpacer'
							},
							{
								handler: A.bind(instance._onViewHistory, instance),
								icon: 'clock',
								label: Liferay.Language.get('history')
							}
						]
					}
				).render();

				Dockbar.backstageToolbar = backstageToolbar;

				var redoText = Liferay.Language.get('redo');
				var undoText = Liferay.Language.get('undo');

				Dockbar.redoButton = new A.ButtonItem(
					{
						handler: A.bind(instance._onRevisionChange, instance, 'redo'),
						icon: 'arrowreturnthick-1-r',
						label: redoText,
						title: redoText
					}
				);

				Dockbar.undoButton = new A.ButtonItem(
					{
						handler: A.bind(instance._onRevisionChange, instance, 'undo'),
						icon: 'arrowreturnthick-1-b',
						label: undoText,
						title: undoText
					}
				);

				var layoutRevisionDetails = A.one('#' + namespace + 'layoutRevisionDetails');

				if (layoutRevisionDetails) {
					Liferay.publish(
						'updatedLayout',
						{
							defaultFn: function(event) {
								A.io.request(
									themeDisplay.getPathMain() + '/staging_bar/view_layout_revision_details',
									{
										data: {
											p_l_id: themeDisplay.getPlid()
										},
										method: 'GET',
										on: {
											success: function(event, id, obj) {
												var response = this.get('responseData');

												layoutRevisionDetails.plug(A.Plugin.ParseContent);

												layoutRevisionDetails.setContent(response);
											}
										}
									}
								);
							}
						}
					);
				}
			},

			_getGraphDialog: function() {
				var instance = this;

				var graphDialog = instance._graphDialog;

				if (!graphDialog) {
					graphDialog = new A.Dialog(
						{
							align: {
								points: ['tc', 'tc']
							},
							draggable: true,
							modal: true,
							title: Liferay.Language.get('history'),
							width: 600
						}
					).plug(
						A.Plugin.IO,
						{
							autoLoad: false,
							data: {
								doAsUserId: themeDisplay.getDoAsUserIdEncoded(),
								p_l_id: themeDisplay.getPlid(),
								p_p_isolated: true,
								redirect: Liferay.currentURL
							},
							uri: themeDisplay.getPathMain() + '/staging_bar/view_layout_revisions'
						}
					).render();

					graphDialog.move(graphDialog.get('x'), graphDialog.get('y') + 100);

					graphDialog.bodyNode.delegate(
						'click',
						function(event) {
							instance._selectRevision(event.currentTarget);
						},
						'a.layout-revision.selection-handle'
					);

					instance._graphDialog = graphDialog;
				}

				return graphDialog;
			},

			_onRevisionChange: function(type, event) {
				var instance = this;

				var confirmText = MAP_TEXT_REVISION[type];
				var cmd = MAP_CMD_REVISION[type];

				if (confirm(confirmText)) {
					var button = event.currentTarget.get('contentBox');

					instance._updateRevision(
						cmd,
						button.attr('data-layoutRevisionId'),
						button.attr('data-layoutSetBranchId')
					);
				}
			},

			_onViewHistory: function(event) {
				var instance = this;

				var namespace = instance._namespace;

				var form = A.one('#' + namespace + 'fm');

				var layoutRevisionId = form.one('#' + namespace + 'layoutRevisionId').val();
				var layoutSetBranchId = form.one('#' + namespace + 'layoutSetBranchId').val();

				var graphDialog = instance._getGraphDialog();

				var graphDialogIO = graphDialog.io;

				var data = graphDialogIO.get('data');

				data.layoutRevisionId = layoutRevisionId;
				data.layoutSetBranchId = layoutSetBranchId;

				graphDialogIO.set('data', data);
				graphDialogIO.start();

				graphDialog.show();
			},

			_selectRevision: function(node) {
				var instance = this;

				instance._updateRevision(
					node,
					node.attr('data-layoutRevisionId'),
					node.attr('data-layoutSetBranchId')
				);
			},

			_updateRevision: function(cmd, layoutRevisionId, layoutSetBranchId) {
				A.io.request(
					themeDisplay.getPathMain() + '/portal/update_layout',
					{
						data: {
							cmd: cmd,
							doAsUserId: themeDisplay.getDoAsUserIdEncoded(),
							layoutRevisionId: layoutRevisionId,
							layoutSetBranchId: layoutSetBranchId,
							p_l_id: themeDisplay.getPlid()
						},
						on: {
							success: function(event, id, obj) {
								window.location.reload();
							}
						}
					}
				);
			}
		};

		Staging.Branching = Branching;
		Staging.Dockbar = Dockbar;
		Liferay.Staging = Staging;
	},
	'',
	{
		requires: ['aui-button-item', 'aui-dialog', 'aui-io-plugin', 'aui-toolbar', 'liferay-portlet-url']
	}
);