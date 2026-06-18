/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useEffect, useState} from 'react';

export interface Process {
	dateCompleted?: string;
	errorMessage?: string;
	id: number;
	name?: string;
	status: {
		code: number;
		label: string;
		label_i18n?: string;
	};
}

const listeners = new Map<number, Set<() => void>>();
const processes = new Map<number, Process>();

export function getLiveProcess(processId: number): Process | undefined {
	return processes.get(processId);
}

export function publishLiveProcess(process: Process) {
	processes.set(process.id, process);

	listeners.get(process.id)?.forEach((listener: () => void) => listener());
}

export function useLiveProcess(processId?: number): Process | undefined {
	const [process, setProcess] = useState(
		processId ? processes.get(processId) : undefined
	);

	useEffect(() => {
		if (!processId) {
			return;
		}

		setProcess(processes.get(processId));

		const processListeners =
			listeners.get(processId) ?? new Set<() => void>();

		listeners.set(processId, processListeners);

		const listener = () => setProcess(processes.get(processId));

		processListeners.add(listener);

		return () => {
			processListeners.delete(listener);
		};
	}, [processId]);

	return process;
}
