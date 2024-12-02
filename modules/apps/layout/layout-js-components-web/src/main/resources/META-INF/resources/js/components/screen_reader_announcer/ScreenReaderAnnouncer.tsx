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

type ScreenReaderAnnouncerProps = {
	'aria-atomic'?: AriaAttributes['aria-atomic'];
	'aria-live'?: AriaAttributes['aria-live'];
};

const ScreenReaderAnnouncer = React.forwardRef<any, ScreenReaderAnnouncerProps>(
	(
		{'aria-atomic': ariaAtomic = false, 'aria-live': ariaLive = 'polite'},
		ref
	) => {
		const [message, setMessage] = useState<string>('');
		const isMounted = useIsMounted();

		const sendMessage = useCallback(
			(message: string) => {
				setMessage(message);

				setTimeout(() => {
					if (isMounted()) {
						setMessage('');
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
				{message}
			</span>
		);
	}
);

export default ScreenReaderAnnouncer;
