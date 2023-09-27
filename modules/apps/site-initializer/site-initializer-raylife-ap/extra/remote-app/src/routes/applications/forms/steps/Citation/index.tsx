/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {getWebDavUrl} from '../../../../../../../../extra/remote-app/src/common/utils/webdav';

const DrivingUnderTheInfluence = () => {
	return (
		<div className="align-items-center citation d-flex justify-content-center text-center">
			<div>
				<img
					className="mr-2"
					src={`${getWebDavUrl()}/${'exclamation_icon.svg'}`}
				/>

				<h2 className="mb-4">
					Sorry, we cannot proceed with a quote at this time.
				</h2>

				<p className="text-neutral-7 text-paragraph">
					This application has been <b>Rejected</b> due to not meeting
					minimum requirements. Please follow up with applicant
					accordingly.
				</p>

				<p className="text-neutral-7 text-paragraph">
					<b>Rejection Reason:</b> Applicants who have a DUI on their
					record are not eligible to be insured.
				</p>
			</div>
		</div>
	);
};
export default DrivingUnderTheInfluence;
