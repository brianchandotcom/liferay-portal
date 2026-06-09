/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import ClayProgressBar from '@clayui/progress-bar';
import {LabelRenderer} from '@liferay/frontend-data-set-web';
import {openModal} from 'frontend-js-components-web';
import {escapeHTML, fetch} from 'frontend-js-web';
import React, {useEffect, useState} from 'react';

import {publishLiveProcess, useLiveProcess} from '../liveProcesses';

const POLL_INTERVAL = 1000;

const STATUS_COMPLETED_WITH_ERRORS = 6;
const STATUS_FAILED = 2;
const STATUS_SUCCESSFUL = 3;

const STATUS_DISPLAY_TYPES: Record<number, string> = {
	[STATUS_COMPLETED_WITH_ERRORS]: 'warning',
	[STATUS_FAILED]: 'danger',
	[STATUS_SUCCESSFUL]: 'success',
};

function openErrorModal(title: string, errorMessage: string) {
	openModal({
		bodyHTML: `<div class="alert alert-danger mb-0" role="alert">${escapeHTML(
			errorMessage
		)}</div>`,
		buttons: [
			{
				displayType: 'primary',
				label: Liferay.Language.get('close'),
				onClick: ({processClose}: {processClose: Function}) =>
					processClose(),
			},
		],
		title: title || Liferay.Language.get('error'),
	});
}

export default function ProcessStatusRenderer({
	itemData,
	progressEndpoint,
	value,
}: {
	itemData?: {
		dateCompleted?: string;
		errorMessage?: string;
		id?: number;
		name?: string;
	};
	progressEndpoint: string;
	value?: {
		code: number;
		label: string;
		label_i18n?: string;
	};
}) {
	const id = itemData?.id;

	const liveProcess = useLiveProcess(id);

	const dateCompleted = liveProcess?.dateCompleted ?? itemData?.dateCompleted;
	const status = liveProcess?.status ?? value;

	const [progress, setProgress] = useState<{percentage?: number}>();

	useEffect(() => {
		if (!id || dateCompleted) {
			return;
		}

		let active = true;

		const updateProgress = () =>
			fetch(`${progressEndpoint}/${id}/progress`)
				.then((response) => response.json())
				.then((progress: {percentage?: number}) => {
					if (!active) {
						return;
					}

					if (progress.percentage !== undefined) {
						setProgress(progress);

						return;
					}

					fetch(`${progressEndpoint}/${id}`)
						.then((response) => response.json())
						.then((process) => {
							if (active && process.dateCompleted) {
								publishLiveProcess(process);
							}
						})
						.catch(() => {});
				})
				.catch(() => {});

		updateProgress();

		const interval = window.setInterval(updateProgress, POLL_INTERVAL);

		return () => {
			active = false;

			window.clearInterval(interval);
		};
	}, [dateCompleted, id, progressEndpoint]);

	if (!status) {
		return null;
	}

	const errorMessage = liveProcess?.errorMessage ?? itemData?.errorMessage;
	const name = liveProcess?.name ?? itemData?.name;

	return (
		<>
			<LabelRenderer
				value={{
					displayStyle: STATUS_DISPLAY_TYPES[status.code] || 'info',
					label: status.label_i18n ?? status.label,
				}}
			/>

			{!dateCompleted && progress && (
				<ClayProgressBar value={progress.percentage ?? 0} />
			)}

			{errorMessage && (
				<ClayButton
					className="p-0 text-left"
					displayType="link"
					onClick={() => openErrorModal(name as string, errorMessage)}
					small
				>
					{Liferay.Language.get('see-more-details')}
				</ClayButton>
			)}
		</>
	);
}
