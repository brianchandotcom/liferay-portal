/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ClayTooltipProvider} from '@clayui/tooltip';
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
	const roleProductNames = currentRoleBriefName
		.map((roleBriefName) => {
			return i18n.translate(getKebabCase(roleBriefName));
		})
		.join(', ');

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
		<div className="d-flex">
			<ClayTooltipProvider delay={100}>
				<p className="m-0 pt-1 text-truncate" title={roleProductNames}>
					{roleProductNames}
				</p>
			</ClayTooltipProvider>
		</div>
	);
};

export default RolesColumn;
