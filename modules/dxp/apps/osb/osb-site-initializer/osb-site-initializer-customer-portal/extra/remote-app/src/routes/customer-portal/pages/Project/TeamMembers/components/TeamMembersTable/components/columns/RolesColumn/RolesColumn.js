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

import RolesDropdown from './components/RolesDropdown';

const RolesColumn = ({
	accountRoles,
	availableSupportSeatsCount,
	currentRoleBriefNames,
	edit,
	hasAccountSupportSeatRole,
	onClick,
	supportSeatsCount,
}) => {
	return edit ? (
		<RolesDropdown
			accountRoles={accountRoles}
			availableSupportSeatsCount={availableSupportSeatsCount}
			currentRoleBriefNames={currentRoleBriefNames}
			hasAccountSupportSeatRole={hasAccountSupportSeatRole}
			onClick={onClick}
			supportSeatsCount={supportSeatsCount}
		/>
	) : (
		<p className="m-0 text-truncate">{currentRoleBriefNames.join(', ')}</p>
	);
};

export default RolesColumn;
