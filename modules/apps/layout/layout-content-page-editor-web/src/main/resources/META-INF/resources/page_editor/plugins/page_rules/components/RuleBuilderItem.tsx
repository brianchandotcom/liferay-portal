/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ClayButtonWithIcon} from '@clayui/button';
import React, {ReactNode} from 'react';

interface RuleBuilderItemProps {
	children: ReactNode;
	onDeleteButtonClick: () => void;
	type: 'action' | 'condition';
}

export default function RuleBuilderItem({
	children,
	onDeleteButtonClick,
	type,
}: RuleBuilderItemProps) {
	return (
		<div
			className={`p-2 mb-3 d-flex align-items-center justify-content-between page-editor__rule-builder-item page-editor__rule-builder-item--${type}`}
		>
			<div className="c-gap-2 d-flex flex-grow-1">{children}</div>

			<ClayButtonWithIcon
				aria-label={
					type === 'action'
						? Liferay.Language.get('delete-action')
						: Liferay.Language.get('delete-condition')
				}
				borderless
				className="page-editor__rule-builder-delete-button"
				displayType="secondary"
				onClick={() => onDeleteButtonClick()}
				size="sm"
				symbol="times-circle"
				title={
					type === 'action'
						? Liferay.Language.get('delete-action')
						: Liferay.Language.get('delete-condition')
				}
			/>
		</div>
	);
}
