/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React, {ReactNode} from 'react';
declare type ScreenReaderAnnouncerContextType = {
	sendMessage: (message: string) => void;
};
declare const ScreenReaderAnnouncerContext: React.Context<ScreenReaderAnnouncerContextType>;
declare function ScreenReaderAnnouncerContextProvider({
	children,
}: {
	children: ReactNode;
}): JSX.Element;
export {
	ScreenReaderAnnouncerContext,
	ScreenReaderAnnouncerContextProvider,
	ScreenReaderAnnouncerContextType,
};
