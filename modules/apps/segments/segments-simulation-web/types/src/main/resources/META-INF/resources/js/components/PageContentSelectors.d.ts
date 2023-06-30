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

/// <reference types="react" />

import SegmentEntry from '../../types/SegmentEntry';
import SegmentExperience from '../../types/SegmentExperience';
interface Props {
	deactivateSimulationURL: string;
	namespace: string;
	portletNamespace: string;
	segmentationEnabled: boolean;
	segmentsCompanyConfigurationURL: string;
	segmentsEntries: SegmentEntry[];
	segmentsExperiences: SegmentExperience[];
	selectSegmentsEntryURL: string;
	selectSegmentsExperienceURL: string;
	simulateSegmentsEntriesURL: string;
}
declare function PageContentSelectors({
	deactivateSimulationURL,
	namespace,
	portletNamespace,
	segmentationEnabled,
	segmentsCompanyConfigurationURL,
	segmentsEntries,
	segmentsExperiences,
	selectSegmentsEntryURL,
	selectSegmentsExperienceURL,
	simulateSegmentsEntriesURL,
}: Props): JSX.Element;
export default PageContentSelectors;
