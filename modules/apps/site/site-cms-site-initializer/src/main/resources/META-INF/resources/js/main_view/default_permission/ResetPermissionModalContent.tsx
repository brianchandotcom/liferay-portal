/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import CMSAssetPermissionService from '../../common/services/CMSAssetPermissionService';

export default function openResetAssetPermissionModal({
	className,
	classPK,
	loadData,
}: {
	className?: string;
	classPK?: number;
	loadData: () => void;
}) {
	Liferay.Util.openModal({
		bodyHTML: `<p>${Liferay.Language.get(
			'are-you-sure-you-want-to-reset-the-permissions-to-the-default-values'
		)}</p>`,
		buttons: [
			{
				displayType: 'secondary',
				label: Liferay.Language.get('cancel'),
				type: 'cancel',
			},
			{
				displayType: 'warning',
				label: Liferay.Language.get('confirm'),
				onClick: async ({processClose}: {processClose: () => void}) => {
					try {
						if (className && classPK !== undefined) {
							const response =
								await CMSAssetPermissionService.resetAssetPermission(
									{
										className,
										classPK,
									}
								);

							if (response.error) {
								throw new Error(response.error);
							}

							Liferay.Util.openToast({
								message: Liferay.Language.get(
									'permissions-reset-successfully'
								),
								type: 'success',
							});
						}

						loadData();
					}
					catch (error) {
						console.error('Error resetting permissions:', error);

						Liferay.Util.openToast({
							message: Liferay.Language.get('an-error-occurred'),
							type: 'danger',
						});
					}
					finally {
						processClose();
					}
				},
			},
		],
		containerProps: {
			className: '',
		},
		status: 'warning',
		title: Liferay.Language.get('confirm-reset-to-default-permissions'),
	});
}
