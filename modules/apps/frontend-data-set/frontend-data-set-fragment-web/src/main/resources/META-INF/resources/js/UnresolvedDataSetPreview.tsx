/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayAlert from '@clayui/alert';
import React, {useState} from 'react';

import './UnresolvedDataSetPreview.scss';

const SKELETON_COLUMNS = [1, 2, 3, 4];

const SKELETON_ROWS = [1, 2, 3, 4, 5];

interface IUnresolvedDataSetPreview {
	apiURL: string;
}

export default function UnresolvedDataSetPreview({
	apiURL,
}: IUnresolvedDataSetPreview) {
	const [showAlert, setShowAlert] = useState(true);

	return (
		<div className="unresolved-data-set-preview">
			{showAlert && (
				<ClayAlert
					displayType="info"
					onClose={() => setShowAlert(false)}
				>
					{Liferay.Language.get('unmapped-url-help')}
				</ClayAlert>
			)}

			<div className="border p-2 pl-3 rounded text-break">
				{(apiURL || '')
					.split(/(\{[^}]*\})/)
					.map((part, index) =>
						index % 2 ? (
							<strong key={index}>{part}</strong>
						) : (
							<React.Fragment key={index}>{part}</React.Fragment>
						)
					)}
			</div>

			<div className="border mt-3 rounded">
				<div className="align-items-center bg-light d-flex data-set-skeleton-management-bar p-3">
					<span className="data-set-skeleton-bar mr-3" />

					<span className="data-set-skeleton-bar flex-grow-1 mr-3" />

					<span className="data-set-skeleton-bar mr-3" />

					<span className="data-set-skeleton-bar" />
				</div>

				<table className="data-set-skeleton-table mb-0 table">
					<tbody>
						{SKELETON_ROWS.map((row) => (
							<tr key={row}>
								<td>
									<span className="data-set-skeleton-bar" />
								</td>

								{SKELETON_COLUMNS.map((column) => (
									<td key={column}>
										<span className="data-set-skeleton-bar" />
									</td>
								))}
							</tr>
						))}
					</tbody>
				</table>
			</div>
		</div>
	);
}
