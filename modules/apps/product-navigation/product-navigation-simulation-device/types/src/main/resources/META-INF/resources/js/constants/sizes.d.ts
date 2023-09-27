/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
