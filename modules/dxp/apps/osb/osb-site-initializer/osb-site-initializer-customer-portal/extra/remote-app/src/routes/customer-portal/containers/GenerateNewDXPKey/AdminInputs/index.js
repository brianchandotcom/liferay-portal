/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 */

import {Input} from '../../../../../common/components';
import {
	isValidHost,
	isValidIp,
	isValidMac,
} from '../../../../../common/utils/validations.form';

const AdminInputs = ({id}) => {
	return (
		<>
			<div className="mb-2">
				<div className="cp-input-generate-label">
					<Input
						label="Host Name"
						name={`dxp.admins[${id}].hostName`}
						required
						type="text"
						validations={[(value) => isValidHost(value)]}
					/>
				</div>
			</div>

			<div className="mb-2">
				<div className="cp-input-generate-label">
					<Input
						className="cp-input-generate-placeholder m-0 w-100"
						component="textarea"
						label="IP Addresses"
						name={`dxp.admins[${id}].ipAddresses`}
						placeholder="1.1.1.1&#10;2.2.2.2"
						required
						type="text"
						validations={[(value) => isValidIp(value)]}
					/>

					<h6 className="font-weight-normal mt-1 mx-3">
						Add one IP addresses per line. IPv6 addresses are not
						supported.
					</h6>
				</div>

				<div className="cp-input-generate-label">
					<Input
						className="cp-input-generate-placeholder"
						component="textarea"
						label="MAC Addresses"
						name={`dxp.admins[${id}].macAddresses`}
						placeholder="XX-XX-XX-XX-XX-XX&#10;XX-XX-XX-XX-XX-XX"
						required
						type="text"
						validations={[(value) => isValidMac(value)]}
					/>

					<h6 className="font-weight-normal mt-1 mx-3">
						Add one MAC addresses per line
					</h6>
				</div>
			</div>
		</>
	);
};

export default AdminInputs;
