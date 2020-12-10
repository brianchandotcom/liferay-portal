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

import {useIsMounted} from 'frontend-js-react-web';
import {useEffect, useState} from 'react';

import ImageService from '../services/ImageService';

export const useBackgroundImageMediaQueries = (elementId, backgroundImage) => {
	const [
		backgroundImageMediaQueries,
		setBackgroundImageMediaQueries,
	] = useState('');
	const isMounted = useIsMounted();

	useEffect(() => {
		setBackgroundImageMediaQueries('');

		if (!backgroundImage?.fileEntryId) {
			return;
		}

		ImageService.getAvailableImageConfigurations({
			fileEntryId: backgroundImage.fileEntryId,
			onNetworkStatus: () => {},
		}).then((imageSizes) => {
			if (!imageSizes || !imageSizes.length || !isMounted()) {
				return;
			}

			setBackgroundImageMediaQueries(
				imageSizes
					.filter((imageSize) => {
						return imageSize.mediaQuery && imageSize.url;
					})
					.map((imageSize) => {
						return `@media ${imageSize.mediaQuery} {
							#${elementId} {
								background-image: url(${imageSize.url}) !important;
							}
						}`;
					})
					.join('\n')
			);
		});
	}, [backgroundImage, elementId, isMounted]);

	return backgroundImageMediaQueries;
};
