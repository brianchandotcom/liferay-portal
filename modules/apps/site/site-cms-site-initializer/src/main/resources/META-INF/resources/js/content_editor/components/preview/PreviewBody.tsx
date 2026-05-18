/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayAlert from '@clayui/alert';
import ClayEmptyState from '@clayui/empty-state';
import {preventIframeNavigation} from '@liferay/layout-js-components-web';
import React from 'react';

import PreviewSelectors from './PreviewSelectors';
import usePreviewState from './usePreviewState';

export default function PreviewBody({
	getPreviewDataURL,
	isContentEdited,
	languageId,
}: {
	getPreviewDataURL: string;
	isContentEdited: boolean;
	languageId: Liferay.Language.Locale;
}) {
	const {
		displayPageTemplates,
		isDisplayPageTemplatesListEmpty,
		previewURL,
		setExternalURL,
		...selectorProps
	} = usePreviewState(getPreviewDataURL, languageId);

	return (
		<>
			<div className="border-bottom c-gap-3 d-flex flex-wrap mb-0 p-3">
				<PreviewSelectors
					{...selectorProps}
					displayPageTemplates={displayPageTemplates}
					onBlurExternalURLInput={setExternalURL}
					previewURL={previewURL}
					showPreviewInNewTabLink
				/>
			</div>

			{isContentEdited && previewURL ? (
				<ClayAlert
					displayType="info"
					title={Liferay.Language.get('info')}
					variant="stripe"
				>
					{Liferay.Language.get(
						'you-have-unsaved-changes.-save-as-draft-or-publish-to-update-this-preview'
					)}
				</ClayAlert>
			) : null}

			{isDisplayPageTemplatesListEmpty ? (
				<ClayAlert
					className="m-3"
					displayType="info"
					title={Liferay.Language.get('info')}
				>
					{Liferay.Language.get(
						'no-display-page-templates-are-available-for-preview-in-this-channel'
					)}
				</ClayAlert>
			) : (
				<div className="align-items-center content-editor__preview__content d-flex position-relative">
					{previewURL ? (
						<iframe
							className="border-0 d-block h-100 w-100"
							onLoad={preventIframeNavigation}
							src={previewURL}
							title={Liferay.Language.get('preview')}
						/>
					) : (
						<ClayEmptyState
							className="mt-0"
							description={Liferay.Language.get(
								'select-a-channel-and-save-as-draft-or-publish-to-see-your-changes-here'
							)}
							imgSrc={`${Liferay.ThemeDisplay.getPathContext()}/o/fragment-collection-contributor-inputs/drag_drop_illustration.svg`}
							small
							title={Liferay.Language.get('nothing-to-show-yet')}
						/>
					)}
				</div>
			)}
		</>
	);
}
