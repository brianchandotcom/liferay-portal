/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
				if (value !== undefined) {
					url.searchParams.set(key, value);
				}
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
