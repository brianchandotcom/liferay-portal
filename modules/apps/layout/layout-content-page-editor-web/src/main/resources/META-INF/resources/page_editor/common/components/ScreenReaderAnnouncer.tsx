/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useIsMounted} from '@liferay/frontend-js-react-web';
import React, {
	AriaAttributes,
	useCallback,
	useImperativeHandle,
	useState,
} from 'react';

// @ts-ignore

import {v4 as uuidv4} from 'uuid';

type ScreenReaderAnnouncerProps = {
	'aria-atomic'?: AriaAttributes['aria-atomic'];
	'aria-live'?: AriaAttributes['aria-live'];
};

const ScreenReaderAnnouncer = React.forwardRef<any, ScreenReaderAnnouncerProps>(
	(
		{'aria-atomic': ariaAtomic = false, 'aria-live': ariaLive = 'polite'},
		ref
	) => {
		const [textMap, setTextMap] = useState<Record<string, string>>({});
		const isMounted = useIsMounted();

		const sendMessage = useCallback(
			(message: string) => {
				const messageId = uuidv4();

				setTextMap((previousTextMap) => {
					const nextTextMap = {...previousTextMap};

					nextTextMap[messageId] = message;

					return nextTextMap;
				});

				setTimeout(() => {
					if (isMounted()) {
						setTextMap((previousTextMap) => {
							const nextTextMap = {...previousTextMap};

							delete nextTextMap[messageId];

							return nextTextMap;
						});
					}
				}, 10000);
			},
			[isMounted]
		);

		useImperativeHandle(ref, () => ({sendMessage}));

		return (
			<span
				aria-atomic={ariaAtomic}
				aria-live={ariaLive}
				className="sr-only"
			>
				{Object.entries(textMap).map(([messageId, message]) => (
					<p key={messageId}>{message}</p>
				))}
			</span>
		);
	}
);

export default ScreenReaderAnnouncer;
