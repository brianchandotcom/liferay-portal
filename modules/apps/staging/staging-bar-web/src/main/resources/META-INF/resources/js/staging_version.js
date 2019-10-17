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
	A => {
		var StagingBar = Liferay.StagingBar;

		var MAP_CMD_REVISION = {
			redo: 'redo_layout_revision',
			undo: 'undo_layout_revision'
		};

		var MAP_TEXT_REVISION = {
			redo: Liferay.Language.get(
				'are-you-sure-you-want-to-redo-your-last-changes'
			),
			undo: Liferay.Language.get(
				'are-you-sure-you-want-to-undo-your-last-changes'
			)
		};

		A.mix(StagingBar, {
			_cleanup() {
				var instance = this;

				if (instance._eventHandles) {
					A.Array.invoke(instance._eventHandles, 'detach');
				}
			},

			_getNotification() {
				var instance = this;

				var notification = instance._notification;

				if (!notification) {
					notification = new Liferay.Notice({
						closeText: false,
						content: Liferay.Language.get(
							'there-was-an-unexpected-error.-please-refresh-the-current-page'
						),
						noticeClass: 'hide',
						timeout: 10000,
						toggleText: false,
						type: 'warning',
						useAnimation: true
					});

					instance._notification = notification;
				}

				return notification;
			},

			_onInit() {
				var instance = this;

				instance._cleanup();

				var namespace = instance._namespace;

				var eventHandles = [
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
					)
				];

				var layoutRevisionDetails = A.one(
					'#' + namespace + 'layoutRevisionDetails'
				);
				var layoutRevisionStatus = A.one(
					'#' + namespace + 'layoutRevisionStatus'
				);

				if (layoutRevisionDetails) {
					eventHandles.push(
						Liferay.after('updatedLayout', () => {
							Liferay.Util.fetch(
								instance.markAsReadyForPublicationURL
							)
								.then(response => response.text())
								.then(response => {
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
							.then(response => {
								return response.text();
							})
							.then(response => {
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
				var instance = this;

				var cmd = MAP_CMD_REVISION[type];
				var confirmText = MAP_TEXT_REVISION[type];

				if (confirm(confirmText)) {
					instance._updateRevision(
						cmd,
						event.layoutRevisionId,
						event.layoutSetBranchId
					);
				}
			},

			_onSubmit(event) {
				var instance = this;

				var namespace = instance._namespace;

				var layoutRevisionDetails = A.one(
					'#' + namespace + 'layoutRevisionDetails'
				);
				var layoutRevisionInfo = layoutRevisionDetails.one(
					'.layout-revision-info'
				);

				if (layoutRevisionInfo) {
					layoutRevisionInfo.addClass('loading');
				}

				var submitLink = A.one('#' + namespace + 'submitLink');

				if (submitLink) {
					submitLink.html(Liferay.Language.get('loading') + '...');
				}

				Liferay.Util.fetch(event.publishURL)
					.then(() => {
						if (event.incomplete) {
							location.href = event.currentURL;
						} else {
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
							}
						},
						destroyOnHide: true
					},
					title: Liferay.Language.get('history'),
					uri: StagingBar.viewHistoryURL
				});
			},

			_updateRevision(cmd, layoutRevisionId, layoutSetBranchId) {
				var instance = this;

				var updateLayoutData = {
					cmd,
					doAsUserId: themeDisplay.getDoAsUserIdEncoded(),
					layoutRevisionId,
					layoutSetBranchId,
					p_auth: Liferay.authToken,
					p_l_id: themeDisplay.getPlid(),
					p_v_l_s_g_id: themeDisplay.getSiteGroupId()
				};

				Liferay.Util.fetch(
					themeDisplay.getPathMain() + '/portal/update_layout',
					{
						body: Liferay.Util.objectToFormData(updateLayoutData),
						method: 'POST'
					}
				)
					.then(() => {
						window.location.reload();
					})
					.catch(() => {
						instance._getNotification().show();
					});
			},

			destructor() {
				var instance = this;

				instance._cleanup();
			}
		});

		Liferay.on('initStagingBar', StagingBar._onInit, StagingBar);
	},
	'',
	{
		requires: ['aui-button', 'liferay-staging']
	}
);
