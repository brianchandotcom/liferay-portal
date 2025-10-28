/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {FrontendDataSet} from '@liferay/frontend-data-set-web';
import React from 'react';

const ContentRenderersFrontendDataSet = () => {
	const items = [
		{
			numberActionLink: 0,
			numberDefault: 0,
			title: 'Item with 0',
		},
	];

	return (
		<FrontendDataSet
			id="ContentRenderersFrontendDataSet"
			items={items}
			itemsActions={[
				{
					data: {
						id: 'edit',
					},
					icon: 'pencil',
					label: Liferay.Language.get('edit'),
					onClick: () => {},
				},
			]}
			views={[
				{
					contentRenderer: 'table',
					name: 'table',
					schema: {
						fields: [
							{
								fieldName: 'title',
								label: 'Title',
							},
							{
								actionId: 'edit',
								contentRenderer: 'actionLink',
								fieldName: 'numberActionLink',
								label: 'Number (actionLink)',
							},
							{
								fieldName: 'numberDefault',
								label: 'Number (default)',
							},
						],
					},
				},
			]}
		/>
	);
};

export default ContentRenderersFrontendDataSet;
