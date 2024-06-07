/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React, {ReactNode, useCallback, useRef} from 'react';

import ScreenReaderAnnouncer from '../components/screen_reader_announcer/ScreenReaderAnnouncer';

type ScreenReaderAnnouncerContextType = {
	sendMessage: (message: string) => void;
};

const ScreenReaderAnnouncerContext =
	React.createContext<ScreenReaderAnnouncerContextType>({
		sendMessage: () => {},
	});

function ScreenReaderAnnouncerContextProvider({
	children,
}: {
	children: ReactNode;
}) {
	const screenReaderAnnouncerRef = useRef<ScreenReaderAnnouncerContextType>();

	const sendMessage = useCallback((message) => {
		const ref = screenReaderAnnouncerRef;

		if (ref.current) {
			ref.current?.sendMessage(message);
		}
	}, []);

	return (
		<ScreenReaderAnnouncerContext.Provider value={{sendMessage}}>
			<ScreenReaderAnnouncer ref={screenReaderAnnouncerRef} />

			{children}
		</ScreenReaderAnnouncerContext.Provider>
	);
}

export {
	ScreenReaderAnnouncerContext,
	ScreenReaderAnnouncerContextProvider,
	ScreenReaderAnnouncerContextType,
};
