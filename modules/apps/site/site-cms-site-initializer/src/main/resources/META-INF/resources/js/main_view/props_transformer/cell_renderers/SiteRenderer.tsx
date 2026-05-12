/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClaySticker from '@clayui/sticker';
import React from 'react';

import {SITE_TEMPLATE_TYPE} from '../../../common/utils/constants';

const SiteRenderer = ({itemData, value}: {itemData: any; value: string}) => {
	const label =
		itemData.type === SITE_TEMPLATE_TYPE
			? `${value} (${Liferay.Language.get('site-template')})`
			: value;

	return (
		<span className="align-items-center d-flex">
			<ClaySticker
				className="c-mr-2"
				displayType="secondary"
				shape="circle"
				size="lg"
			>
				<ClaySticker.Image alt={label} src={itemData.logo} />
			</ClaySticker>

			{label}
		</span>
	);
};

export default SiteRenderer;
