/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {openToast} from '@liferay/object-js-components-web';
import {useCallback, useState} from 'react';

import {getCredential} from '../services/ConfigurationService';

export function useCredential() {
	const [clientSecret, setClientSecret] = useState<string | null>(null);
	const [revealing, setRevealing] = useState(false);

	const hideClientSecret = useCallback(() => {
		setClientSecret(null);
	}, []);

	const revealClientSecret = useCallback(async () => {
		setRevealing(true);

		try {
			const credential = await getCredential();

			setClientSecret(credential.clientSecret ?? '');
		}
		catch (error) {
			openToast({
				message: Liferay.Language.get('failed-to-load-credentials'),
				type: 'danger',
			});
		}
		finally {
			setRevealing(false);
		}
	}, []);

	return {clientSecret, hideClientSecret, revealClientSecret, revealing};
}
