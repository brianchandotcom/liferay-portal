/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import i18n from '../../../../../common/I18n';
import {Input} from '../../../../../common/components';
import {
	isValidHost,
	isValidIp,
	isValidMac,
} from '../../../../../common/utils/validations.form';

const KeyInputs = ({id}) => {
	return (
		<>
			<div className="cp-input-generate-label">
				<Input
					label={i18n.translate('host-name')}
					name={`keys[${id}].hostName`}
					type="text"
					validations={[(value) => isValidHost(value)]}
				/>
			</div>

			<h6 className="font-weight-normal mb-3 mx-3">
				{i18n.translate('input-one-host-name-per-instance')}
			</h6>

			<div className="cp-input-generate-label">
				<Input
					className="cp-input-generate-placeholder w-100"
					component="textarea"
					label={i18n.translate('ip-address')}
					name={`keys[${id}].ipAddresses`}
					placeholder="1.1.1.1&#10;2.2.2.2"
					type="text"
					validations={[(value) => isValidIp(value)]}
				/>

				<h6 className="font-weight-normal mb-3 mx-3">
					{i18n.translate(
						'add-one-ip-addresses-per-line-ipv6-addresses-are-not-supported'
					)}
				</h6>

				<div className="cp-input-generate-label">
					<Input
						className="cp-input-generate-placeholder"
						component="textarea"
						label={i18n.translate('mac-address')}
						name={`keys[${id}].macAddresses`}
						placeholder="XX-XX-XX-XX-XX-XX&#10;XX-XX-XX-XX-XX-XX"
						type="text"
						validations={[(value) => isValidMac(value)]}
					/>

					<h6 className="font-weight-normal mb-3 mx-3">
						{i18n.translate(
							'add-one-mac-addresses-per-line-if-available-as-a-static-value'
						)}
					</h6>
				</div>
			</div>
		</>
	);
};

export default KeyInputs;
