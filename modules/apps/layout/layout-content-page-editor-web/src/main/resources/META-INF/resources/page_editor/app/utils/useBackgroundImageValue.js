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

import {useIsMounted} from '@liferay/frontend-js-react-web';
import {useEffect, useState} from 'react';

import ImageService from '../services/ImageService';
import resolveEditableValue from './editable-value/resolveEditableValue';

export default function useBackgroundImageValue(
	elementId,
	backgroundImage,
	getFieldValue
) {
	const isMounted = useIsMounted();
	const [backgroundImageValue, setBackgroundImageValue] = useState({
		mediaQueries: '',
		url: backgroundImage?.url || '',
	});

	useEffect(() => {
		if (!backgroundImage) {
			return;
		}

		loadBackgroundImage(backgroundImage, getFieldValue)
			.then(({fileEntryId, url}) =>
				loadBackgroundImageMediaQueries(elementId, {
					...backgroundImage,
					fileEntryId: fileEntryId || backgroundImage.fileEntryId,
				}).then((mediaQueries) => ({
					mediaQueries,
					url,
				}))
			)
			.then((nextBackgroundImageValue) => {
				if (isMounted()) {
					setBackgroundImageValue(nextBackgroundImageValue);
				}
			});
	}, [elementId, backgroundImage, isMounted, getFieldValue]);

	return backgroundImageValue;
}

function loadBackgroundImage(backgroundImage, getFieldValue) {
	return resolveEditableValue(
		{...(backgroundImage || {}), defaultValue: backgroundImage?.url || ''},
		null,
		getFieldValue
	).then((editableValue) => ({
		fileEntryId: editableValue.fileEntryId || '',
		url:
			editableValue.fieldValue?.url ||
			editableValue.url ||
			editableValue ||
			'',
	}));
}

function loadBackgroundImageMediaQueries(elementId, backgroundImage) {
	if (!elementId || !backgroundImage?.fileEntryId) {
		return Promise.resolve('');
	}

	return ImageService.getAvailableImageConfigurations({
		fileEntryId: backgroundImage.fileEntryId,
		onNetworkStatus: () => {},
	}).then((imageSizes) => {
		if (!imageSizes?.length) {
			return '';
		}

		return imageSizes
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
			.join('\n');
	});
}
