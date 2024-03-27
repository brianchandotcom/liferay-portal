/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

export default function imagePropsTransformer(
	imageData: any | string
): React.ImgHTMLAttributes<HTMLImageElement> | string | undefined {
	if (typeof imageData === 'string') {
		return imageData;
	}

	if (typeof imageData === 'object') {

		// Docs & media object

		return {
			alt: imageData?.link.label,
			id: imageData?.id,
			src: imageData?.link.href,
		};
	}
}
