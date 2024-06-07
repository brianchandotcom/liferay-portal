/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import getValueFromItem from '../../utils/getValueFromItem';

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
): React.ImgHTMLAttributes<HTMLImageElement> | undefined {
	let imageProps: React.ImgHTMLAttributes<HTMLImageElement> = {
		alt: '',
		src: undefined,
	};

	if (!imageData || !Object.keys(imageData).length) {
		return undefined;
	}

	if (typeof imageData === 'string') {
		imageProps = {...imageProps, src: imageData};
	}
	else if ('value' in imageData && typeof imageData.value === 'string') {
		imageProps = {
			...imageProps,
			alt:
				imageData.options?.label ||
				(imageData.options?.labelKey
					? getValueFromItem(
							imageData.itemData,
							imageData.options.labelKey
						)
					: ''),
			src: imageData.value,
		};
	}
	else if ('value' in imageData && typeof imageData.value === 'object') {
		imageProps =
			'link' in imageData.value
				? {
						...imageProps,
						alt: imageData.value.link.label,
						src: imageData.value.link.href,
					}
				: {
						...imageProps,
						alt: imageData.value.alt,
						src: imageData.value.src,
					};
	}
	else if ('link' in imageData) {
		imageProps = {
			...imageProps,
			alt: imageData.link.label,
			src: imageData.link.href,
		};
	}

	return imageProps;
}
