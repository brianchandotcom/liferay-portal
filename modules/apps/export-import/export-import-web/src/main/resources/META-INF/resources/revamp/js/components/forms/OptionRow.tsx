/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayLayout from '@clayui/layout';
import classnames from 'classnames';
import React from 'react';

import '../../../css/utilities.scss';

export function OptionRow({
	bordered = false,
	description,
	descriptionId,
	input,
	label,
	labelId,
}: {
	bordered?: boolean;
	description?: string;
	descriptionId?: string;
	input: React.ReactNode;
	label: string;
	labelId: string;
}) {
	return (
		<label
			className={classnames(
				'cursor-pointer d-block mb-2 text-3',
				bordered && 'border p-3 rounded'
			)}
		>
			<ClayLayout.ContentRow padded>
				<ClayLayout.ContentCol expand={false}>
					<div className="pt-1">{input}</div>
				</ClayLayout.ContentCol>

				<ClayLayout.ContentCol expand>
					<ClayLayout.ContentSection>
						<div
							className="font-weight-semi-bold mb-0 text-dark"
							id={labelId}
						>
							{label}
						</div>

						{description && (
							<div
								className="small text-secondary"
								id={descriptionId}
							>
								{description}
							</div>
						)}
					</ClayLayout.ContentSection>
				</ClayLayout.ContentCol>
			</ClayLayout.ContentRow>
		</label>
	);
}
