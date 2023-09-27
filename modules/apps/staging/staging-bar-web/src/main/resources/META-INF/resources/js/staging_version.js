/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

AUI.add(
	'liferay-staging-version',
	(A) => {
		const StagingBar = Liferay.StagingBar;

		const MAP_CMD_REVISION = {
			redo: 'redo_layout_revision',
			undo: 'undo_layout_revision',
		};

		const MAP_TEXT_REVISION = {
			redo: Liferay.Language.get(
				'are-you-sure-you-want-to-redo-your-last-changes'
			),
			undo: Liferay.Language.get(
				'are-you-sure-you-want-to-undo-your-last-changes'
			),
		};

		A.mix(StagingBar, {
			_cleanup() {
				const instance = this;

				if (instance._eventHandles) {
					A.Array.invoke(instance._eventHandles, 'detach');
				}
			},

			_onInit() {
				const instance = this;

				instance._cleanup();

				const namespace = instance._namespace;

				const eventHandles = [
					Liferay.on(
						namespace + 'redo',
						instance._onRevisionChange,
						instance,
						'redo'
					),
					Liferay.on(
						namespace + 'submit',
						instance._onSubmit,
						instance
					),
					Liferay.on(
						namespace + 'undo',
						instance._onRevisionChange,
						instance,
						'undo'
					),
					Liferay.on(
						namespace + 'viewHistory',
						instance._onViewHistory,
						instance
					),
				];

				const layoutRevisionDetails = A.one(
					'#' + namespace + 'layoutRevisionDetails'
				);
				const layoutRevisionStatus = A.one(
					'#' + namespace + 'layoutRevisionStatus'
				);

				if (layoutRevisionDetails) {
					eventHandles.push(
						Liferay.after('updatedLayout', () => {
							Liferay.Util.fetch(
								instance.markAsReadyForPublicationURL
							)
								.then((response) => response.text())
								.then((response) => {
									layoutRevisionDetails.plug(
										A.Plugin.ParseContent
									);

									layoutRevisionDetails.setContent(response);

									Liferay.fire('updatedStatus');
								})
								.catch(() => {
									layoutRevisionDetails.setContent(
										Liferay.Language.get(
											'there-was-an-unexpected-error.-please-refresh-the-current-page'
										)
									);
								});
						})
					);
				}

				if (layoutRevisionStatus) {
					Liferay.after('updatedStatus', () => {
						Liferay.Util.fetch(instance.layoutRevisionStatusURL)
							.then((response) => {
								return response.text();
							})
							.then((response) => {
								layoutRevisionStatus.plug(
									A.Plugin.ParseContent
								);

								layoutRevisionStatus.setContent(response);
							})
							.catch(() => {
								layoutRevisionStatus.setContent(
									Liferay.Language.get(
										'there-was-an-unexpected-error.-please-refresh-the-current-page'
									)
								);
							});
					});
				}

				instance._eventHandles = eventHandles;
			},

			_onRevisionChange(event, type) {
				const instance = this;

				const cmd = MAP_CMD_REVISION[type];
				const confirmText = MAP_TEXT_REVISION[type];

				Liferay.Util.openConfirmModal({
					message: confirmText,
					onConfirm: (isConfirmed) => {
						if (isConfirmed) {
							instance._updateRevision(
								cmd,
								event.layoutRevisionId,
								event.layoutSetBranchId
							);
						}
					},
				});
			},

			_onSubmit(event) {
				const instance = this;

				const namespace = instance._namespace;

				const layoutRevisionDetails = A.one(
					'#' + namespace + 'layoutRevisionDetails'
				);
				const layoutRevisionInfo = layoutRevisionDetails.one(
					'.layout-revision-info'
				);

				if (layoutRevisionInfo) {
					layoutRevisionInfo.addClass('loading');
				}

				const submitLink = A.one('#' + namespace + 'submitLink');

				if (submitLink) {
					submitLink.html(Liferay.Language.get('loading') + '...');
				}

				Liferay.Util.fetch(event.publishURL)
					.then(() => {
						if (event.incomplete) {
							location.href = event.currentURL;
						}
						else {
							Liferay.fire('updatedLayout');
						}
					})
					.catch(() => {
						layoutRevisionDetails.addClass('alert alert-danger');

						layoutRevisionDetails.setContent(
							Liferay.Language.get(
								'there-was-an-unexpected-error.-please-refresh-the-current-page'
							)
						);
					});
			},

			_onViewHistory() {
				Liferay.Util.openWindow({
					dialog: {
						after: {
							destroy() {
								window.location.reload();
							},
						},
						destroyOnHide: true,
					},
					title: Liferay.Language.get('history'),
					uri: StagingBar.viewHistoryURL,
				});
			},

			_updateRevision(cmd, layoutRevisionId, layoutSetBranchId) {
				const updateLayoutData = {
					cmd,
					layoutRevisionId,
					layoutSetBranchId,
					p_auth: Liferay.authToken,
					p_l_id: themeDisplay.getPlid(),
					p_v_l_s_g_id: themeDisplay.getSiteGroupId(),
				};

				Liferay.Util.fetch(
					themeDisplay.getPathMain() + '/portal/update_layout',
					{
						body: Liferay.Util.objectToFormData(updateLayoutData),
						method: 'POST',
					}
				)
					.then(() => {
						window.location.reload();
					})
					.catch(() => {
						Liferay.Util.openToast({
							message: Liferay.Language.get(
								'there-was-an-unexpected-error.-please-refresh-the-current-page'
							),
							toastProps: {
								autoClose: 10000,
							},
							type: 'warning',
						});
					});
			},

			destructor() {
				const instance = this;

				instance._cleanup();
			},
		});

		Liferay.on('initStagingBar', StagingBar._onInit, StagingBar);
	},
	'',
	{
		requires: ['aui-button', 'liferay-staging'],
	}
);
