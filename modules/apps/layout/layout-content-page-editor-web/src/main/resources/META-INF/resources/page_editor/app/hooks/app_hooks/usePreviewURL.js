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

import {useEffect} from 'react';

import {LAYOUT_TYPES} from '../../config/constants/layoutTypes';
import {config} from '../../config/index';
import {useDisplayPagePreviewItem} from '../../contexts/DisplayPagePreviewItemContext';
import {useSelector} from '../../contexts/StoreContext';
import selectLanguageId from '../../selectors/selectLanguageId';
import selectSegmentsExperienceId from '../../selectors/selectSegmentsExperienceId';

export default function usePreviewURL() {
	const displayPagePreviewItem = useDisplayPagePreviewItem();
	const languageId = useSelector(selectLanguageId);
	const segmentsExperienceId = useSelector(selectSegmentsExperienceId);

	useEffect(() => {
		const previewElement = document.querySelector(
			'[data-page-editor-layout-preview-base-url]'
		);

		if (!previewElement?.dataset.pageEditorLayoutPreviewBaseUrl) {
			return;
		}

		const url = new URL(
			previewElement?.dataset.pageEditorLayoutPreviewBaseUrl
		);

		const setParameters = (parameters) => {
			Object.entries(parameters).forEach(([key, value]) => {
				url.searchParams.set(`${config.portletNamespace}${key}`, value);
			});
		};

		setParameters({
			languageId,
			segmentsExperienceId,
		});

		if (
			config.layoutType === LAYOUT_TYPES.display &&
			displayPagePreviewItem
		) {
			setParameters(displayPagePreviewItem.data);
		}

		previewElement.setAttribute('href', url.toString());
	}, [displayPagePreviewItem, languageId, segmentsExperienceId]);
}
