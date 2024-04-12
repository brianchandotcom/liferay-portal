/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

/// <reference types="react" />

interface IDocsAndMediaImageProps {
	id: number;
	link: {
		href: string;
		label: string;
	};
	name: string;
}
interface IImageProps {
	itemData?: any;
	name?: string;
	options?: {
		label?: string;
		labelKey?: string;
		shape?: 'circle' | 'user-icon';
		size?: 'lg' | 'sm' | 'xl';
	};
	value?:
		| string
		| IDocsAndMediaImageProps
		| React.ImgHTMLAttributes<HTMLImageElement>;
}
export default function imagePropsTransformer(
	imageData: IDocsAndMediaImageProps | IImageProps | string | undefined
): React.ImgHTMLAttributes<HTMLImageElement> | undefined;
export {};
