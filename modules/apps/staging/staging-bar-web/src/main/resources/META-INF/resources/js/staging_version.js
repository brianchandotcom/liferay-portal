/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
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

				instance._openConfirm({
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

			_openConfirm({message, onConfirm}) {
				if (Liferay.FeatureFlags['LPS-148659']) {
					Liferay.Util.openConfirmModal({message, onConfirm});
				}
				else if (confirm(message)) {
					onConfirm(true);
				}
			},

			_updateRevision(cmd, layoutRevisionId, layoutSetBranchId) {
				const updateLayoutData = {
					cmd,
					doAsUserId: themeDisplay.getDoAsUserIdEncoded(),
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
