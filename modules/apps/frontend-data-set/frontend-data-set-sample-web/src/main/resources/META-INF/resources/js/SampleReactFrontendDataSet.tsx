/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {FrontendDataSet} from '@liferay/frontend-data-set-web';
import React from 'react';

const SampleReactFrontendDataSet = (props: any) => {
	const cardsViewWithLabels = {
		_key: 'cards',
		contentRenderer: 'cards',
		default: false,
		label: 'Cards',
		name: 'cards',
		schema: {
			description: 'description',
			href: '',
			image: '',
			labels: [
				{
					displayType: 'info',
					value: 'color',
				},
				{
					displayTypeKey: 'status.label',
					displayTypeValues: {
						approved: 'success',
						expired: 'danger',
					},
					value: 'status.label_i18n',
				},
			],
			sticker: '',
			symbol: '',
			title: 'title',
		},
		thumbnail: 'cards2',
	};

	props.views.push(cardsViewWithLabels);

	return <FrontendDataSet {...props} />;
};

export default SampleReactFrontendDataSet;
