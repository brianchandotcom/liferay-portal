/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import {useModal} from '@clayui/modal';
import React from 'react';

import {ExportReportEntriesModal} from './ExportReportEntriesModal';

type ExportErrorsReportProps = {
	backgroundTaskId: string;
	filename: string;
};

export function ExportReportEntriesAction({
	backgroundTaskId,
	filename,
}: ExportErrorsReportProps) {
	const {observer, onOpenChange, open} = useModal();

	return (
		<>
			<ClayButton
				className="dropdown-item"
				displayType="unstyled"
				onClick={() => onOpenChange(true)}
			>
				{Liferay.Language.get('export-report-entries')}
			</ClayButton>

			{open && (
				<ExportReportEntriesModal
					filename={filename}
					importProcessId={backgroundTaskId}
					observer={observer}
					onOpenChange={onOpenChange}
				/>
			)}
		</>
	);
}
