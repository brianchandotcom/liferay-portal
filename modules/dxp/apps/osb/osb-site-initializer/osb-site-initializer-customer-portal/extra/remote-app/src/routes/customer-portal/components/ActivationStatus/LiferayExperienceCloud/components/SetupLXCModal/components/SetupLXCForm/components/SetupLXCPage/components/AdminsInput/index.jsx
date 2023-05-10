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

			<Input
				groupStyle="mb-0"
				label={i18n.translate('project-admin-s-github-username')}
				name={`lxc.admins[${id}].github`}
				required
				type="text"
			/>
		</ClayForm>
	);
};

export default AdminInputs;
