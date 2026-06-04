/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import {openToast} from 'frontend-js-components-web';
import React, {useMemo, useState} from 'react';

import {buildInitialConfig} from '../constants';
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
	domainId: number | null;
	scanConfig: string | null;
	schedule: Partial<ScheduleConfig> | null;
	timeZones: TimeZoneOption[];
}

export default function HealthScanConfiguration({
	defaultTimeZoneId,
	domainId,
	scanConfig,
	schedule,
	timeZones,
}: Props) {
	const [savedConfig, setSavedConfig] = useState<HealthScanConfig>(() =>
		buildInitialConfig(defaultTimeZoneId, scanConfig, schedule)
	);
	const [config, setConfig] = useState<HealthScanConfig>(savedConfig);
	const [saving, setSaving] = useState(false);

	const valid = useMemo(() => isConfigValid(config), [config]);

	function handleEngineChange(key: EngineKey, engineConfig: EngineConfig) {
		setConfig((current) => ({
			...current,
			engines: {...current.engines, [key]: engineConfig},
		}));
	}

	function handleScheduleChange(nextSchedule: ScheduleConfig) {
		setConfig((current) => ({...current, schedule: nextSchedule}));
	}

	function handleCancel() {
		setConfig(savedConfig);
	}

	async function handleSave() {
		if (!domainId) {
			return;
		}

		setSaving(true);

		try {
			const response = await Liferay.Util.fetch(
				`/o/seo-studio/domains/${domainId}`,
				{
					body: JSON.stringify({
						autoScanEnabled: config.schedule.autoScanEnabled,
						scanConfig: JSON.stringify({engines: config.engines}),
						scanDayOfMonth: config.schedule.scanDayOfMonth,
						scanDayOfWeek: config.schedule.scanDayOfWeek,
						scanFrequency: config.schedule.scanFrequency,
						scanTime: config.schedule.scanTime,
						scanTimeZone: config.schedule.scanTimeZone,
					}),
					headers: {'Content-Type': 'application/json'},
					method: 'PATCH',
				}
			);

			if (response.ok) {
				setSavedConfig(config);

				openToast({
					message: Liferay.Language.get(
						'your-request-completed-successfully'
					),
					type: 'success',
				});
			}
			else {
				openToast({
					message: Liferay.Language.get(
						'an-unexpected-error-occurred'
					),
					type: 'danger',
				});
			}
		}
		catch {
			openToast({
				message: Liferay.Language.get('an-unexpected-error-occurred'),
				type: 'danger',
			});
		}

		setSaving(false);
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
						<ClayButton
							disabled={!valid || !domainId || saving}
							displayType="primary"
							onClick={handleSave}
						>
							{Liferay.Language.get('save')}
						</ClayButton>

						<ClayButton
							disabled={saving}
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
