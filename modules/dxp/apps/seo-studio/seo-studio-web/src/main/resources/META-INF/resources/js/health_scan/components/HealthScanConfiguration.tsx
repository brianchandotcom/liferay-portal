/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import React, {useMemo, useState} from 'react';

import {getDefaultConfig} from '../constants';
import {
	EngineConfig,
	EngineKey,
	HealthScanConfig,
	ScheduleConfig,
	TimeZoneOption,
} from '../types';
import {getPathError} from '../validation';
import ScheduleSection from './ScheduleSection';
import ScopeSection from './ScopeSection';

function isConfigValid(config: HealthScanConfig): boolean {
	return Object.values(config.engines).every((engine) => {
		if (!engine.enabled) {
			return true;
		}

		if (engine.scope === 'excludedPathsOnly') {
			return getPathError(engine.excludedPaths) === undefined;
		}

		if (engine.scope === 'includedPathsOnly') {
			return getPathError(engine.includedPaths) === undefined;
		}

		return true;
	});
}

interface Props {
	defaultTimeZoneId: string;
	timeZones: TimeZoneOption[];
}

export default function HealthScanConfiguration({
	defaultTimeZoneId,
	timeZones,
}: Props) {
	const [savedConfig] = useState<HealthScanConfig>(() =>
		getDefaultConfig(defaultTimeZoneId)
	);
	const [config, setConfig] = useState<HealthScanConfig>(() =>
		getDefaultConfig(defaultTimeZoneId)
	);

	const valid = useMemo(() => isConfigValid(config), [config]);

	function handleEngineChange(key: EngineKey, engineConfig: EngineConfig) {
		setConfig((current) => ({
			...current,
			engines: {...current.engines, [key]: engineConfig},
		}));
	}

	function handleScheduleChange(schedule: ScheduleConfig) {
		setConfig((current) => ({...current, schedule}));
	}

	function handleCancel() {
		setConfig(savedConfig);
	}

	return (
		<div className="p-3 p-md-4">
			<div className="sheet sheet-lg">
				<div className="sheet-header">
					<h2 className="sheet-title">
						{Liferay.Language.get('health-scan')}
					</h2>
				</div>

				<ScheduleSection
					onChange={handleScheduleChange}
					schedule={config.schedule}
					timeZones={timeZones}
				/>

				<ScopeSection
					engines={config.engines}
					onChange={handleEngineChange}
				/>

				<div className="sheet-footer">
					<ClayButton.Group spaced>
						<ClayButton disabled={!valid} displayType="primary">
							{Liferay.Language.get('save')}
						</ClayButton>

						<ClayButton
							displayType="secondary"
							onClick={handleCancel}
						>
							{Liferay.Language.get('cancel')}
						</ClayButton>
					</ClayButton.Group>
				</div>
			</div>
		</div>
	);
}
