/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import ClayEmptyState from '@clayui/empty-state';
import React from 'react';

type Props = {
	description: string;
	onClick: () => void;
	title: string;
};

export function NotFound({description, onClick, title}: Props) {
	return (
		<div className="a11y-panel__sidebar--body">
			<ClayEmptyState description={description} title={title}>
				<ClayButton displayType="secondary" onClick={onClick}>
					{Liferay.Language.get('back')}
				</ClayButton>
			</ClayEmptyState>
		</div>
	);
}
