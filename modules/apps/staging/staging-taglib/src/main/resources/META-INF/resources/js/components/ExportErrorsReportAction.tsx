/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import {useModal} from '@clayui/modal';
import React from 'react';

import {ExportErrorsReportModal} from './ExportErrorsReportModal';

type ExportErrorsReportProps = {
	filename?: string;
};

export function ExportErrorsReportAction({
	filename = 'Extranet_backup_Errors_Report.csv',
}: ExportErrorsReportProps) {
	const {observer, onOpenChange, open} = useModal();

	return (
		<>
			<ClayButton
				className="dropdown-item"
				displayType="unstyled"
				onClick={() => onOpenChange(true)}
			>
				{Liferay.Language.get('export-errors-report')}
			</ClayButton>

			{open && (
				<ExportErrorsReportModal
					filename={filename}
					observer={observer}
					onOpenChange={onOpenChange}
				/>
			)}
		</>
	);
}
