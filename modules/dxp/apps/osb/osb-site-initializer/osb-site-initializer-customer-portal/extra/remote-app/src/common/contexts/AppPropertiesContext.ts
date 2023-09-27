/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ApolloClient} from '@apollo/client';
import {createContext, useContext} from 'react';

export const AppPropertiesContext = createContext({
	articleAccountSupportURL: '',
	articleDeployingActivationKeysURL: '',
	articleGettingStartedWithLiferayEnterpriseSearchURL: '',
	articleWhatIsMyInstanceSizingValueURL: '',
	client: null,
	featureFlags: [],
	gravatarAPI: '',
	importDate: null,
	oktaSessionAPI: '',
	provisioningServerAPI: '',
	submitSupportTicketURL: '',
	theOverviewPageURL: '',
});

export function useAppPropertiesContext() {
	const context = useContext(AppPropertiesContext);

	type ContextType = Omit<typeof context, 'client'> & {
		client: ApolloClient<any>;
	};

	return (context as unknown) as ContextType;
}
