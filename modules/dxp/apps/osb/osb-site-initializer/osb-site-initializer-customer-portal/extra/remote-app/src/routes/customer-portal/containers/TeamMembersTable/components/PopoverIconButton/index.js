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

import ClayButton from '@clayui/button';
import ClayIcon from '@clayui/icon';
import ClayPopover from '@clayui/popover';
import {useApplicationProvider} from '../../../../../../common/context/AppPropertiesProvider';
const PopoverIconButton = ({alignPosition = 'bottom'}) => {
	const {customerPortalRoles} = useApplicationProvider();

	return (
		<ClayPopover
			alignPosition={alignPosition}
			className="cp-team-members-popover"
			closeOnClickOutside
			trigger={
				<ClayButton className="px-1" displayType="unstyled">
					<ClayIcon
						className="cp-team-members-support-seat-icon py-0"
						symbol="info-circle"
					/>
				</ClayButton>
			}
		>
			<p className="m-0 text-neutral-10">
				The limit of support seats available counts the total of&nbsp;
				<span className="text-weight-bold">
					Administrators & Requesters
				</span>
				&nbsp;roles assigned due to both have role permissions to open
				support tickets.&nbsp;
				<a
					href={customerPortalRoles}
					rel="noopener noreferrer"
					target="_blank"
				>
					Learn more about Customer Portal roles.
				</a>
			</p>
		</ClayPopover>
	);
};

export default PopoverIconButton;
