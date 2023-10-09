/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

/// <reference types="react" />

interface Props {
	createTemplateURL: string;
	getCollectionsURL: string;
	hasMultipleSegmentsExperienceIds: boolean;
	layoutId: string;
	namespace?: string;
	onClose: () => {};
	segmentsExperienceId: string;
}
export default function PageTemplateModal({
	createTemplateURL,
	getCollectionsURL,
	hasMultipleSegmentsExperienceIds,
	layoutId,
	namespace,
	onClose,
	segmentsExperienceId,
}: Props): JSX.Element;
export {};
