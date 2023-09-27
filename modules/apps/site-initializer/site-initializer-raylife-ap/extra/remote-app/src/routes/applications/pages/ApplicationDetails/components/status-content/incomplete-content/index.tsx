/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';

type IncompleteContentProps = {
	onClick: () => void;
};

const IncompleteContent = ({onClick}: IncompleteContentProps) => (
	<>
		<div className="d-flex flex-column">
			<div className="action-detail-title pt-3 px-5">
				<h5 className="m-0">Complete Application</h5>
			</div>

			<hr />

			<div className="action-detail-content px-5">
				<p>
					This application is incomplete and missing information to be
					properly quoted.
				</p>

				<p>
					Edit the application to finish the quote process for this
					customer.
				</p>
			</div>
		</div>

		<div className="action-detail-footer d-flex justify-content-end pb-5 pt-3 px-5">
			<ClayButton
				className="text-small-caps"
				displayType="primary"
				onClick={() => onClick()}
			>
				Edit Application
			</ClayButton>
		</div>
	</>
);

export default IncompleteContent;
