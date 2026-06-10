/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React, {useState} from 'react';

import {Launch} from './api/launches';
import LaunchDetails from './components/LaunchDetails';
import LaunchesLanding from './components/LaunchesLanding';
import NewLaunchForm from './components/NewLaunchForm';

type View =
	| {type: 'landing'}
	| {type: 'new'}
	| {launchId: number; type: 'details'};

export function Launches() {
	const [view, setView] = useState<View>({type: 'landing'});

	switch (view.type) {
		case 'new':
			return (
				<NewLaunchForm
					onCancel={() => setView({type: 'landing'})}
					onCreated={(launch: Launch) =>
						setView({launchId: launch.id, type: 'details'})
					}
				/>
			);
		case 'details':
			return (
				<LaunchDetails
					launchId={view.launchId}
					onBack={() => setView({type: 'landing'})}
				/>
			);
		case 'landing':
		default:
			return (
				<LaunchesLanding
					onNew={() => setView({type: 'new'})}
					onSelect={(launch: Launch) =>
						setView({launchId: launch.id, type: 'details'})
					}
				/>
			);
	}
}
