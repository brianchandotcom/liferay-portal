AUI.add(
	'liferay-staging-version',
	function(A) {
		var Lang = A.Lang;

		var StagingBar = Liferay.StagingBar;

		var MAP_CMD_REVISION = {
			redo: 'redo_layout_revision',
			undo: 'undo_layout_revision'
		};

		var MAP_TEXT_REVISION = {
			redo: Liferay.Language.get('are-you-sure-you-want-to-redo-your-last-changes'),
			undo: Liferay.Language.get('are-you-sure-you-want-to-undo-your-last-changes')
		};

		A.mix(
			StagingBar,
			{
				destructor: function() {
					var instance = this;

					instance._destroyToolbarContent();

					A.Array.invoke(instance._eventHandles, 'detach');
				},

				_onInit: function(event) {
					var instance = this;

					var namespace = instance._namespace;

					instance._destroyToolbarContent();

					var layoutRevisionToolbar = new A.Toolbar(
						{
							boundingBox: A.byIdNS(namespace, 'layoutRevisionToolbar')
						}
					).render();

					if (!event.hideHistory) {
						layoutRevisionToolbar.add(
							{
								icon: 'icon-time',
								label: Liferay.Language.get('history'),
								on: {
									click: A.bind('_onViewHistory', instance)
								}
							}
						);
					}

					StagingBar.layoutRevisionToolbar = layoutRevisionToolbar;

					var redoText = Liferay.Language.get('redo');
					var undoText = Liferay.Language.get('undo');

					StagingBar.redoButton = new A.Button(
						{
							icon: 'icon-forward',
							label: redoText,
							on: {
								click: A.bind('_onRevisionChange', instance, 'redo')
							},
							title: redoText
						}
					);

					StagingBar.undoButton = new A.Button(
						{
							icon: 'icon-backward',
							label: undoText,
							on: {
								click: A.bind('_onRevisionChange', instance, 'undo')
							},
							title: undoText
						}
					);

					var eventHandles = [];

					var layoutRevisionDetails = A.byIdNS(namespace, 'layoutRevisionDetails');

					if (layoutRevisionDetails) {
						eventHandles.push(
							Liferay.onceAfter(
								'updatedLayout',
								function(event) {
									A.io.request(
										themeDisplay.getPathMain() + '/staging_bar/view_layout_revision_details',
										{
											data: {
												p_l_id: themeDisplay.getPlid()
											},
											on: {
												failure: function(event, id, obj) {
													layoutRevisionDetails.setContent(Liferay.Language.get('there-was-an-unexpected-error.-please-refresh-the-current-page'));
												},
												success: function(event, id, obj) {
													instance._destroyToolbarContent();

													var response = this.get('responseData');

													layoutRevisionDetails.plug(A.Plugin.ParseContent);

													layoutRevisionDetails.setContent(response);
												}
											}
										}
									);
								}
							)
						);
					}

					eventHandles.push(Liferay.on(event.portletId + ':portletRefreshed', A.bind('destructor', instance)));

					instance._eventHandles = eventHandles;
				},

				_destroyToolbarContent: function() {
					if (StagingBar.layoutRevisionToolbar) {
						StagingBar.layoutRevisionToolbar.destroy();

						StagingBar.layoutRevisionToolbar = null;
					}

					if (StagingBar.redoButton) {
						StagingBar.redoButton.destroy();

						StagingBar.redoButton = null;
					}

					if (StagingBar.undoButton) {
						StagingBar.undoButton.destroy();

						StagingBar.undoButton = null;
					}
				},

				_getGraphDialog: function() {
					var instance = this;

					var graphDialog = instance._graphDialog;

					if (!graphDialog) {
						graphDialog = Liferay.Util.Window.getWindow(
						    {
								title: Liferay.Language.get('history')
						    }
						);

						graphDialog.plug(
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
						);

						graphDialog.bodyNode.delegate(
							'click',
							function(event) {
								instance._selectRevision(event.target);
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

					var form = A.byIdNS(namespace, 'fm');

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
						'select_layout_revision',
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
								p_auth: Liferay.authToken,
								p_l_id: themeDisplay.getPlid(),
								p_v_l_s_g_id: themeDisplay.getSiteGroupId()
							},
							on: {
								success: function(event, id, obj) {
									window.location.reload();
								}
							}
						}
					);
				}
			}
		);

		Liferay.on('initStagingBar', StagingBar._onInit, StagingBar);
	},
	'',
	{
		requires: ['aui-button', 'liferay-node', 'liferay-staging']
	}
);