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
	'document-library-checkin',
	A => {
		var DocumentLibraryCheckin = {
			showDialog(namespace, callback) {
				var contentId = namespace + 'versionDetails';
				var versionDetailsDialog = Liferay.Util.Window.getWindow({
					dialog: {
						bodyContent: A.one('#' + contentId).html(),
						destroyOnHide: true,
						height: 400,
						'toolbars.footer': [
							{
								cssClass: 'btn-link',
								label: Liferay.Language.get('cancel'),
								on: {
									click() {
										Liferay.Util.getWindow(
											contentId + 'Dialog'
										).destroy();
									}
								}
							},
							{
								cssClass: 'btn-primary',
								label: Liferay.Language.get('save'),
								on: {
									click() {
										var versionIncrease = false;
										var versionIncreaseElement = document.querySelector(
											"input[name='" +
												namespace +
												"versionDetailsVersionIncrease']:checked"
										);

										if (versionIncreaseElement) {
											versionIncrease =
												versionIncreaseElement.value;
										}

										var changeLog = '';
										var changeLogElement = document.getElementById(
											namespace +
												'versionDetailsChangeLog'
										);

										if (changeLogElement) {
											changeLog = changeLogElement.value;
										}

										callback(versionIncrease, changeLog);
									}
								}
							}
						],
						width: 700
					},
					dialogIframe: {
						bodyCssClass: 'dialog-with-footer'
					},
					id: contentId + 'Dialog',
					title: Liferay.Language.get('describe-your-changes')
				});

				versionDetailsDialog.render();
			}
		};

		Liferay.DocumentLibraryCheckin = DocumentLibraryCheckin;
	},
	'',
	{
		requires: ['liferay-util-window']
	}
);
