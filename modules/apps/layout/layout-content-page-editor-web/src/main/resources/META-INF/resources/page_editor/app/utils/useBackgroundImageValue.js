/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useIsMounted} from '@liferay/frontend-js-react-web';
import {useEffect, useState} from 'react';

import ImageService from '../services/ImageService';
import resolveEditableValue from './editable_value/resolveEditableValue';

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
