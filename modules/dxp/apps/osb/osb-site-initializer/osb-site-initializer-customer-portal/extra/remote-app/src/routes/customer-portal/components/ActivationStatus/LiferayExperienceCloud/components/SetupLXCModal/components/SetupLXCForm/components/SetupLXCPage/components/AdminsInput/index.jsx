/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayForm from '@clayui/form';
import i18n from '../../../../../../../../../../../../../common/I18n';
import {Input} from '../../../../../../../../../../../../../common/components';
import useBannedDomains from '../../../../../../../../../../../../../common/hooks/useBannedDomains';
import {isValidEmail} from '../../../../../../../../../../../../../common/utils/validations.form';

const AdminInputs = ({admin, id}) => {
	const bannedDomains = useBannedDomains(admin?.email, 500);

	return (
		<ClayForm className="mb-0 pb-1">
			<hr className="mb-4 mt-4 mx-3" />

			<Input
				label={i18n.translate('project-admin-s-first-and-last-name')}
				name={`lxc.admins[${id}].fullName`}
				required
				type="text"
			/>

			<Input
				groupStyle="pt-1"
				label={i18n.translate('project-admin-s-email-address')}
				name={`lxc.admins[${id}].email`}
				placeholder="email@example.com"
				required
				type="email"
				validations={[(value) => isValidEmail(value, bannedDomains)]}
			/>
		</ClayForm>
	);
};

export default AdminInputs;
