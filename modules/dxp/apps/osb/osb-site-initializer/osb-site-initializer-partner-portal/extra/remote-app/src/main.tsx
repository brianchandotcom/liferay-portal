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
import React from 'react';
import {Root, createRoot} from 'react-dom/client';
import {SWRConfig} from 'swr';

import getIconSpriteMap from './common/utils/getIconSpriteMap';
import handleError from './common/utils/handleError';
import MDFRequestForm from './routes/MDFRequestForm';

class PartnerPortalRemoteAppComponent extends HTMLElement {
	private root: Root | undefined;

	connectedCallback() {
		if (!this.root) {
			this.root = createRoot(this);

			this.root.render(
				<SWRConfig
					value={{
						onError: (error) => handleError(error),
						revalidateOnFocus: false,
						revalidateOnReconnect: false,
						shouldRetryOnError: false,
					}}
				>
					<ClayIconSpriteContext.Provider value={getIconSpriteMap()}>
						<MDFRequestForm />
					</ClayIconSpriteContext.Provider>
				</SWRConfig>
			);
		}
	}
}

const ELEMENT_NAME = 'liferay-remote-app-partner-portal';

if (!customElements.get(ELEMENT_NAME)) {
	customElements.define(ELEMENT_NAME, PartnerPortalRemoteAppComponent);
}
