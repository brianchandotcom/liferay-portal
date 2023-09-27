/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

/// <reference types="react" />

declare const PreviewActionsDescriptionSection: ({
	description,
	downloadURL,
	fetchSharingButtonURL,
	handleError,
	preview,
	title,
}: IProps) => JSX.Element;
interface IPreview {
	imageURL: string;
	url: string;
}
interface IProps {
	description?: string;
	downloadURL?: string;
	fetchSharingButtonURL?: string;
	handleError?: () => void;
	preview?: IPreview;
	title: string;
}
export default PreviewActionsDescriptionSection;
