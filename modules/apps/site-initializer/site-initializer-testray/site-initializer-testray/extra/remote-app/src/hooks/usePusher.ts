/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import Pusher from 'pusher-js';
import {useContext, useEffect, useState} from 'react';
import {ApplicationPropertiesContext} from '~/context/ApplicationPropertiesContext';

const getPusherClient = (
	pusherKey: string,
	pusherRegion: string
): Pusher | undefined => {
	if (pusherKey && pusherRegion) {
		return new Pusher(pusherKey, {
			cluster: pusherRegion,
			forceTLS: true,
		});
	}
};

/**
 * @description This hook is used to keep a connection with pusher in browserSide
 * @returns {Pusher} Pusher
 */

const usePusher = (): Pusher | null => {
	const [pusherClient, setPusherClient] = useState<null | Pusher>(null);

	const {pusherKey, pusherRegion} = useContext(ApplicationPropertiesContext);

	useEffect(() => {
		const _pusherClient = getPusherClient(pusherKey, pusherRegion);

		if (_pusherClient) {
			setPusherClient(_pusherClient);
		}

		return () => _pusherClient?.disconnect();
	}, [pusherKey, pusherRegion]);

	return pusherClient;
};

export default usePusher;
