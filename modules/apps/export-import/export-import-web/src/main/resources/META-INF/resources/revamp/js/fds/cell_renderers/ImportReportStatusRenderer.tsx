/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React from 'react';

export default function ImportReportStatusRenderer({
	value,
}: {
	value?: {
		code: 1 | 2;
		label: string;
		label_i18n?: string;
	};
}) {
	const getLabelType = (code: number): string => {
		switch (code) {
			case 1:
				return 'label-success';
			case 2:
				return 'label-danger';
			default:
				return 'label-secondary';
		}
	};

	return value ? (
		<span className="taglib-workflow-status">
			<span className="workflow-status">
				<strong className={`label ${getLabelType(value.code)}`}>
					{value.label_i18n ?? value.label}
				</strong>
			</span>
		</span>
	) : null;
}
