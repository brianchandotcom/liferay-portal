/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import i18n from '../../../../../../../../../../common/I18n';
import getKebabCase from '../../../../../../../../../../common/utils/getKebabCase';
import RolesDropdown from './components/RolesDropdown';

const RolesColumn = ({
	accountRoles,
	availableSupportSeatsCount,
	currentRoleBriefName,
	edit,
	hasAccountSupportSeatRole,
	onClick,
	supportSeatsCount,
}) => {
	return edit ? (
		<RolesDropdown
			accountRoles={accountRoles}
			availableSupportSeatsCount={availableSupportSeatsCount}
			currentRoleBriefName={currentRoleBriefName}
			hasAccountSupportSeatRole={hasAccountSupportSeatRole}
			onClick={onClick}
			supportSeatsCount={supportSeatsCount}
		/>
	) : (
		<p className="m-0 text-truncate">
			{i18n.translate(getKebabCase(currentRoleBriefName))}
		</p>
	);
};

export default RolesColumn;
