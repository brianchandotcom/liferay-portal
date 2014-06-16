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

					instance._cleanup();
				},

				_cleanup: function() {
					var instance = this;

					if (instance._eventHandles) {
						A.Array.invoke(instance._eventHandles, 'detach');
					}
				},

				_getNotification: function() {
					var instance = this;

					var notification = instance._notification;

					if (!notification) {
						notification = new Liferay.Notice(
							{
								closeText: false,
								content: Liferay.Language.get('there-was-an-unexpected-error.-please-refresh-the-current-page'),
								noticeClass: 'hide',
								timeout: 10000,
								toggleText: false,
								type: 'warning',
								useAnimation: true
							}
						);

						instance._notification = notification;
					}

					return notification;
				},

				_onInit: function(event) {
					var instance = this;

					instance._cleanup();

					var namespace = instance._namespace;

					var eventHandles = [
						Liferay.on(namespace + 'redo', instance._onRevisionChange, instance, 'redo'),
						Liferay.on(namespace + 'submit', instance._onSubmit, instance),
						Liferay.on(namespace + 'undo', instance._onRevisionChange, instance, 'undo'),
						Liferay.on(namespace + 'viewHistory', instance._onViewHistory, instance)
					];

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

					instance._eventHandles = eventHandles;
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

				_onRevisionChange: function(event, type) {
					var instance = this;

					var confirmText = MAP_TEXT_REVISION[type];
					var cmd = MAP_CMD_REVISION[type];

					if (confirm(confirmText)) {
						instance._updateRevision(cmd, event.layoutRevisionId, event.layoutSetBranchId);
					}
				},

				_onSubmit: function(event) {
					var instance = this;

					var namespace = instance._namespace;

					var layoutRevisionDetails = A.byIdNS(namespace, 'layoutRevisionDetails');

					var layoutRevisionInfo = layoutRevisionDetails.one('.layout-revision-info');

					if (layoutRevisionInfo) {
						layoutRevisionInfo.addClass('loading');
					}

					var submitLink = A.byIdNS(namespace, 'submitLink');

					if (submitLink) {
						submitLink.html(Liferay.Language.get('loading') + '...');
					}

					A.io.request(
						event.publishURL,
						{
							after: {
								failure: function() {
									layoutRevisionDetails.addClass('alert alert-danger');

									layoutRevisionDetails.setContent(Liferay.Language.get('there-was-an-unexpected-error.-please-refresh-the-current-page'));
								},
								success: function() {
									if (event.incomplete) {
										location.href = event.currentURL;
									}
									else {
										Liferay.fire('updatedLayout');
									}
								}
							}
						}
					);
				},

				_onViewHistory: function(event) {
					var instance = this;

					var graphDialog = instance._getGraphDialog();

					var graphDialogIO = graphDialog.io;

					var data = graphDialogIO.get('data');

					data.layoutRevisionId = event.layoutRevisionId;
					data.layoutSetBranchId = event.layoutSetBranchId;

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
					var instance = this;

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
								failure: function() {
									instance._getNotification().show();
								},
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
		requires: ['aui-button', 'liferay-staging']
	}
);