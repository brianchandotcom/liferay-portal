/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

const NoActionRequired = () => (
	<div className="d-flex flex-column">
		<div className="action-detail-title pt-3 px-5">
			<h5 className="m-0">No Action Required</h5>
		</div>

		<hr />

		<div className="action-detail-content px-5">
			<p>
				There is currently no action required of you. You will be
				notified when your attention is needed.
			</p>
		</div>
	</div>
);

export default NoActionRequired;
