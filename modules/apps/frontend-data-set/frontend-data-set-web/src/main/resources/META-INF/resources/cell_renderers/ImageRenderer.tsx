/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClaySticker from '@clayui/sticker';
import React from 'react';

import imagePropsTransformer from '../views/utils/imagePropsTransformer';

function ImageRenderer(sourceImageProps: any) {
	const imageProps = imagePropsTransformer(sourceImageProps);

	return (
		<ClaySticker
			shape={sourceImageProps.options?.shape}
			size={sourceImageProps.options?.size || 'xl'}
		>
			<img className="sticker-img" {...imageProps} />
		</ClaySticker>
	);
}

export default ImageRenderer;
