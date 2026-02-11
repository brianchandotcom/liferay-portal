/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {
	EConfigInURLBehavior,
	IFrontendDataSetProps,
} from '@liferay/frontend-data-set-web';

import CarouselView from './CarouselView';

export default function propsTransformer({
	itemsActions,
	...otherProps
}: IFrontendDataSetProps): IFrontendDataSetProps {
	return {
		...otherProps,
		configInURLBehavior: EConfigInURLBehavior.REPLACE,
		itemsActions: itemsActions?.map((action) => {
			const key = action?.data?.id as string;

			if (!key || key !== 'turnGreen') {
				return action;
			}

			return {
				...action,
				isVisible: (item: any) => item?.color !== 'Green',
			};
		}),
		views: [
			{
				component: CarouselView,
				default: true,
				label: 'My Carousel View',
				name: 'carouselViewRenderer',
				schema: {
					description: 'description',
					image: 'imageURL',
					link: '',
					sticker: '',
					symbol: '',
					title: 'title',
				},
				thumbnail: 'rotate',
			},
			{
				contentRenderer: 'table',
				label: 'My Table View',
				name: 'table',
				schema: {
					fields: [
						{
							fieldName: 'title',
							label: 'Title',
						},
						{
							fieldName: 'color',
							label: 'Color',
						},
					],
				},
				thumbnail: 'table',
			},
		],
	};
}
