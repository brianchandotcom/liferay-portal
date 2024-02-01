/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {liferayConfig} from '../liferay.config';
import {loginTest} from './loginTest';

interface HeadlessClientConfig {
	BASE: string;
	PASSWORD: string;
	USERNAME: string;
}

type ClassInstanceMap<T extends Record<string, new (...args: any[]) => any>> = {
	[K in keyof T]: InstanceType<T[K]>;
};

function headlessClientsTest<
	CLIENT extends new (config: HeadlessClientConfig) => InstanceType<CLIENT>,
	CLIENTS extends {[K: string]: CLIENT}
>(clients: CLIENTS) {
	return loginTest.extend<{headlessClients: ClassInstanceMap<CLIENTS>}>({
		headlessClients: async ({login}, use) => {
			const instances: Partial<ClassInstanceMap<CLIENTS>> = {};

			for (const key in clients) {
				instances[key] = new clients[key]({
					BASE: liferayConfig.environment.baseUrl + '/o',
					PASSWORD: login.password,
					USERNAME: login.user,
				});
			}

			await use(instances as ClassInstanceMap<CLIENTS>);
		},
	});
}

export {headlessClientsTest};
