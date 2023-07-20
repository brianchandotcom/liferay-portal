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

const ROTATED_SIZE_PADDING = 100;
const NORMAL_SIZE_PADDING = 40;

export interface ScreenSize {
	height: number;
	width: number;
}

export type Size = {
	cssClass?: string;
	icon: string;
	id: string;
	label: string;
	responsive?: boolean;
	rotatedId?: keyof typeof SIZES;
	screenSize?: ScreenSize;
};

export const SIZES = {
	autosize: {
		icon: 'autosize',
		id: 'autosize',
		label: Liferay.Language.get('autosize'),
	},
	custom: {
		cssClass: 'custom',
		icon: 'custom-size',
		id: 'custom',
		label: Liferay.Language.get('custom'),
		screenSize: {
			height: 600,
			width: 600,
		},
	},
	desktop: {
		cssClass: 'desktop',
		icon: 'desktop',
		id: 'desktop',
		label: Liferay.Language.get('desktop'),
		screenSize: {
			height: 1050,
			width: 1300,
		},
	},
	smartphone: {
		cssClass: 'smartphone',
		icon: 'mobile-portrait',
		id: 'smartphone',
		label: Liferay.Language.get('mobile'),
		responsive: true,
		rotatedId: 'smartphoneRotated',
		screenSize: {
			height: 640,
			width: 360 + NORMAL_SIZE_PADDING,
		},
	},
	smartphoneRotated: {
		cssClass: 'smartphone rotated',
		icon: 'mobile-landscape',
		id: 'smartphoneRotated',
		label: Liferay.Language.get('mobile'),
		responsive: true,
		rotatedId: 'smartphone',
		screenSize: {
			height: 400,
			width: 540 + ROTATED_SIZE_PADDING,
		},
	},
	tablet: {
		cssClass: 'tablet',
		icon: 'tablet-portrait',
		id: 'tablet',
		label: Liferay.Language.get('tablet'),
		rotatedId: 'tabletRotated',
		screenSize: {
			height: 900,
			width: 768 + NORMAL_SIZE_PADDING,
		},
	},
	tabletRotated: {
		cssClass: 'tablet rotated',
		icon: 'tablet-landscape',
		id: 'tabletRotated',
		label: Liferay.Language.get('tablet'),
		rotatedId: 'tablet',
		screenSize: {
			height: 760,
			width: 991 + ROTATED_SIZE_PADDING,
		},
	},
} as const;
