/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React from 'react';
import SegmentEntry from '../../types/SegmentEntry';
interface Props {
	namespace: string;
	onSelectSegmentEntry: (key: React.Key) => void;
	segmentsEntries: SegmentEntry[];
	selectedSegmentEntry: SegmentEntry;
}
declare function SegmentSelector({
	namespace,
	onSelectSegmentEntry,
	segmentsEntries,
	selectedSegmentEntry,
}: Props): JSX.Element;
export default SegmentSelector;
