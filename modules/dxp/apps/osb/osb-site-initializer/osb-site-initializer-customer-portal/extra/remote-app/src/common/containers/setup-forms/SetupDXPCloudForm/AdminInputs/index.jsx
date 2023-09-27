/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayForm, {ClayInput} from '@clayui/form';
import i18n from '../../../../I18n';
import {Input} from '../../../../components';
import useBannedDomains from '../../../../hooks/useBannedDomains';
import {isValidEmail} from '../../../../utils/validations.form';

const AdminInputs = ({admin, id}) => {
	const bannedDomains = useBannedDomains(admin?.email, 500);

	return (
		<ClayForm.Group className="mb-0 pb-1">
			<hr className="mb-4 mt-4 mx-3" />

			<Input
				groupStyle="pt-1"
				label={i18n.translate('system-admin-s-email-address')}
				name={`dxp.admins[${id}].email`}
				placeholder="email@example.com"
				required
				type="email"
				validations={[(value) => isValidEmail(value, bannedDomains)]}
			/>

			<ClayInput.Group className="mb-0">
				<ClayInput.GroupItem className="m-0">
					<Input
						label={i18n.translate('system-admin-s-first-name')}
						name={`dxp.admins[${id}].firstName`}
						required
						type="text"
					/>
				</ClayInput.GroupItem>

				<ClayInput.GroupItem className="m-0">
					<Input
						label={i18n.translate('system-admin-s-last-name')}
						name={`dxp.admins[${id}].lastName`}
						required
						type="text"
					/>
				</ClayInput.GroupItem>
			</ClayInput.Group>

			<Input
				groupStyle="mb-0"
				label={i18n.translate('system-admin-s-github-username')}
				name={`dxp.admins[${id}].github`}
				required
				type="text"
			/>
		</ClayForm.Group>
	);
};

export default AdminInputs;
