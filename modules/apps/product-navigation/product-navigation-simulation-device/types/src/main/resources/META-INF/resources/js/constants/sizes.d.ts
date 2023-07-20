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

export interface ScreenSize {
	height: number;
	width: number;
}
export declare type Size = {
	cssClass?: string;
	icon: string;
	id: string;
	label: string;
	responsive?: boolean;
	rotatedId?: keyof typeof SIZES;
	screenSize?: ScreenSize;
};
export declare const SIZES: {
	readonly autosize: {
		readonly icon: 'autosize';
		readonly id: 'autosize';
		readonly label: string;
	};
	readonly custom: {
		readonly cssClass: 'custom';
		readonly icon: 'custom-size';
		readonly id: 'custom';
		readonly label: string;
		readonly screenSize: {
			readonly height: 600;
			readonly width: 600;
		};
	};
	readonly desktop: {
		readonly cssClass: 'desktop';
		readonly icon: 'desktop';
		readonly id: 'desktop';
		readonly label: string;
		readonly screenSize: {
			readonly height: 1050;
			readonly width: 1300;
		};
	};
	readonly smartphone: {
		readonly cssClass: 'smartphone';
		readonly icon: 'mobile-portrait';
		readonly id: 'smartphone';
		readonly label: string;
		readonly responsive: true;
		readonly rotatedId: 'smartphoneRotated';
		readonly screenSize: {
			readonly height: 640;
			readonly width: number;
		};
	};
	readonly smartphoneRotated: {
		readonly cssClass: 'smartphone rotated';
		readonly icon: 'mobile-landscape';
		readonly id: 'smartphoneRotated';
		readonly label: string;
		readonly responsive: true;
		readonly rotatedId: 'smartphone';
		readonly screenSize: {
			readonly height: 400;
			readonly width: number;
		};
	};
	readonly tablet: {
		readonly cssClass: 'tablet';
		readonly icon: 'tablet-portrait';
		readonly id: 'tablet';
		readonly label: string;
		readonly rotatedId: 'tabletRotated';
		readonly screenSize: {
			readonly height: 900;
			readonly width: number;
		};
	};
	readonly tabletRotated: {
		readonly cssClass: 'tablet rotated';
		readonly icon: 'tablet-landscape';
		readonly id: 'tabletRotated';
		readonly label: string;
		readonly rotatedId: 'tablet';
		readonly screenSize: {
			readonly height: 760;
			readonly width: number;
		};
	};
};
