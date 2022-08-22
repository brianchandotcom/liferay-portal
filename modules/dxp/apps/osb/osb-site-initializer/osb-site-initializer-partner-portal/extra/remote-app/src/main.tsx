/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 */

import {ClayIconSpriteContext} from '@clayui/icon';
import {Root, createRoot} from 'react-dom/client';
import {SWRConfig} from 'swr';

import {AppRouteType} from './common/enums/appRouteType';
import getIconSpriteMap from './common/utils/getIconSpriteMap';
import handleError from './common/utils/handleError';
import MDFRequestForm from './routes/MDFRequestForm';
import MDFRequestList from './routes/MDFRequestList';

interface IProps {
	route: AppRouteType;
}

type AppRouteComponent = {
	[key in AppRouteType]?: JSX.Element;
};

const appRoutes: AppRouteComponent = {
	[AppRouteType.MDF_REQUEST_FORM]: <MDFRequestForm />,
	[AppRouteType.MDF_REQUEST_LIST]: <MDFRequestList />,
};

const PartnerPortalApp = ({route}: IProps) => {
	return (
		<SWRConfig
			value={{
				onError: (error) => handleError(error),
				revalidateIfStale: false,
				revalidateOnFocus: false,
				revalidateOnReconnect: false,
				shouldRetryOnError: false,
			}}
		>
			<ClayIconSpriteContext.Provider value={getIconSpriteMap()}>
				{appRoutes[route]}
			</ClayIconSpriteContext.Provider>
		</SWRConfig>
	);
};

class PartnerPortalRemoteAppComponent extends HTMLElement {
	private root: Root | undefined;

	connectedCallback() {
		if (!this.root) {
			this.root = createRoot(this);

			this.root.render(
				<PartnerPortalApp
					route={super.getAttribute('route') as AppRouteType}
				/>
			);
		}
	}
}

const ELEMENT_NAME = 'liferay-remote-app-partner-portal';

if (!customElements.get(ELEMENT_NAME)) {
	customElements.define(ELEMENT_NAME, PartnerPortalRemoteAppComponent);
}
