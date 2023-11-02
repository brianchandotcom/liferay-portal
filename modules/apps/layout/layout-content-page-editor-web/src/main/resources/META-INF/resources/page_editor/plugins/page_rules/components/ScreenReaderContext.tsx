/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React, {ReactNode, useCallback, useRef} from 'react';

import ScreenReaderAnnouncer from '../../../common/components/ScreenReaderAnnouncer';

export type ScreenReaderAnnouncerContextType = {
	sendMessage: (message: string) => void;
};

const ScreenReaderAnnouncerContext = React.createContext<
	ScreenReaderAnnouncerContextType
>({
	sendMessage: () => {},
});

export default function ScreenReaderAnnouncerContextProvider({
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
			<ScreenReaderAnnouncer
				aria-atomic={false}
				ref={screenReaderAnnouncerRef}
			/>

			{children}
		</ScreenReaderAnnouncerContext.Provider>
	);
}

export {ScreenReaderAnnouncerContext};
