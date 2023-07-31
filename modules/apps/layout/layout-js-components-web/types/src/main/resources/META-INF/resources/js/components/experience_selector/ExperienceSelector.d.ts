/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

/// <reference types="react" />

import SegmentExperience from '../../types/SegmentExperience';
interface Props {
	disabled?: boolean;
	displayType?: 'light' | 'dark';
	segmentsExperiences: SegmentExperience[];
	selectedSegmentsExperience: SegmentExperience;
}
export default function ExperienceSelector({
	disabled,
	displayType,
	segmentsExperiences,
	selectedSegmentsExperience,
	...otherProps
}: Props): JSX.Element;
export {};
