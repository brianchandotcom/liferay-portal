/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React, {AriaAttributes} from 'react';
declare type ScreenReaderAnnouncerProps = {
	'aria-atomic'?: AriaAttributes['aria-atomic'];
	'aria-live'?: AriaAttributes['aria-live'];
};
declare const ScreenReaderAnnouncer: React.ForwardRefExoticComponent<
	ScreenReaderAnnouncerProps & React.RefAttributes<any>
>;
export default ScreenReaderAnnouncer;
