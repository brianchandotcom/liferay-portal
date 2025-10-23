/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import ClayLabel from '@clayui/label';
import ClayLink from '@clayui/link';
import Modal from '@clayui/modal';
import {Observer} from '@clayui/modal/lib/types';
import ClayProgressBar from '@clayui/progress-bar';
import classNames from 'classnames';
import React, {useState} from 'react';

type StatusKey = 'completed' | 'failed' | 'inProgress';

type Status = {
	displayType: 'success' | 'info' | 'danger';
	label: string;
	message: string;
};

const STATUS_MAP: Record<StatusKey, Status> = {
	completed: {
		displayType: 'success',
		label: Liferay.Language.get('completed'),
		message: Liferay.Language.get(
			'your-file-has-been-generated-and-is-ready-to-download'
		),
	},
	failed: {
		displayType: 'danger',
		label: Liferay.Language.get('failed'),
		message: Liferay.Language.get(
			'an-unexpected-error-happened-while-creating-the-file'
		),
	},
	inProgress: {
		displayType: 'info',
		label: Liferay.Language.get('running'),
		message: Liferay.Language.get('errors-report-file-is-being-created'),
	},
};

export function ExportErrorsReportModal({
	filename,
	observer,
	onOpenChange,
}: {
	filename: string;
	observer: Observer;
	onOpenChange: (value: boolean) => void;
}) {
	const [progress] = useState<number>(0);
	const [status] = useState<StatusKey>('inProgress');
	const [downloadURL] = useState<string | undefined>();

	return (
		<Modal
			disableAutoClose
			observer={observer}
			status={STATUS_MAP[status].displayType}
		>
			<Modal.Header>
				{Liferay.Language.get('export-errors-report')}
			</Modal.Header>

			<Modal.Body className="text-3 text-weight-semi-bold">
				<p className="mb-0">{STATUS_MAP[status].message}</p>

				<p>{filename}</p>

				<ClayLabel displayType={STATUS_MAP[status].displayType}>
					{STATUS_MAP[status].label}
				</ClayLabel>

				<ClayProgressBar value={progress} />
			</Modal.Body>

			<Modal.Footer
				last={
					<ClayButton.Group spaced>
						<ClayButton
							displayType="secondary"
							onClick={() => onOpenChange(false)}
						>
							{Liferay.Language.get('cancel')}
						</ClayButton>

						<ClayLink
							button
							className={classNames(
								`btn-${STATUS_MAP[status].displayType}`,
								{
									disabled: status !== 'completed',
								}
							)}
							download={filename}
							href={downloadURL ?? '#'}
						>
							{Liferay.Language.get('download')}
						</ClayLink>
					</ClayButton.Group>
				}
			/>
		</Modal>
	);
}
