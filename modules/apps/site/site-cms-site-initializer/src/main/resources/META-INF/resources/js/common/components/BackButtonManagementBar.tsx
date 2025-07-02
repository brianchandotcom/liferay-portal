/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import '../../../css/components/BackButtonManagementBar.scss';

import ClayIcon from '@clayui/icon';
import ClayLink from '@clayui/link';
import {ManagementToolbar} from 'frontend-js-components-web';
import React from 'react';

export default function BackButtonManagementBar({
	backURL,
	headerTitle,
}: {
	backURL: string;
	headerTitle: string;
}) {
	return (
		<ManagementToolbar.Container className="border cms__back-button-management-bar position-fixed">
			<ManagementToolbar.ItemList className="c-gap-3" expand>
				<ManagementToolbar.Item>
					<ClayLink
						aria-label={Liferay.Language.get('back')}
						borderless
						displayType="secondary"
						href={backURL}
						monospaced
						outline
						small
					>
						<ClayIcon symbol="angle-left" />
					</ClayLink>
				</ManagementToolbar.Item>

				<ManagementToolbar.Item className="nav-item-expand">
					<h2 className="font-weight-semi-bold m-0 text-5">
						{headerTitle}
					</h2>
				</ManagementToolbar.Item>
			</ManagementToolbar.ItemList>
		</ManagementToolbar.Container>
	);
}
