/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

import ClayIcon from '@clayui/icon';

import ActionButtons from '../../../../../../common/components/action-detail/action-content/buttons';

const RequestAdditionalInformation = () => (
	<div className="d-flex flex-column">
		<div className="action-detail-title pt-3 px-5">
			<h5 className="m-0">Request Additional Information</h5>
		</div>

		<hr />

		<div className="action-detail-content mb-10 px-5">
			<p className="mb-5">
				The underwriter has requested the following information from the
				customer regarding this claim:
			</p>

			<div className="text-neutral-7">
				<ClayIcon symbol="simple-circle"></ClayIcon>

				<p className="d-inline-block px-2">
					Additional images of damage to driver side rear panel
				</p>
			</div>

			<div className="mb-5 text-neutral-7">
				<ClayIcon symbol="simple-circle"></ClayIcon>

				<p className="d-inline-block px-2">
					Verification of whether this vehicle is primarily used for
					work commute or pleasure
				</p>
			</div>

			<b>
				Please contact the customer for the information described above.
			</b>
		</div>

		<ActionButtons buttonText="Contact Customer" linkText="+ Add Notes" />
	</div>
);

export default RequestAdditionalInformation;
