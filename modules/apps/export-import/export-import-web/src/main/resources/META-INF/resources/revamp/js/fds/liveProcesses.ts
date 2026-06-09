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

const listenersByProcessId = new Map<number, Set<() => void>>();
const processesById = new Map<number, Process>();

export function getLiveProcess(processId: number): Process | undefined {
	return processesById.get(processId);
}

export function publishLiveProcess(process: Process) {
	processesById.set(process.id, process);

	listenersByProcessId
		.get(process.id)
		?.forEach((listener: () => void) => listener());
}

export function useLiveProcess(processId?: number): Process | undefined {
	const [process, setProcess] = useState(
		processId ? processesById.get(processId) : undefined
	);

	useEffect(() => {
		if (!processId) {
			return;
		}

		setProcess(processesById.get(processId));

		const listeners =
			listenersByProcessId.get(processId) ?? new Set<() => void>();

		listenersByProcessId.set(processId, listeners);

		const listener = () => setProcess(processesById.get(processId));

		listeners.add(listener);

		return () => {
			listeners.delete(listener);
		};
	}, [processId]);

	return process;
}
