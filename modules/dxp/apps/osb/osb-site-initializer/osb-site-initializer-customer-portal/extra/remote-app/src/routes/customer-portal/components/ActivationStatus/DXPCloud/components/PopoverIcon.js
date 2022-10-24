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
import i18n from '../../../../../../common/I18n';

const PopoverIcon = ({alignPosition = 'right'}) => {
	return (
		<ClayPopover
			alignPosition={alignPosition}
			className="cp-team-members-popover"
			closeOnClickOutside
			trigger={
				<ClayButton className="px-1" displayType="unstyled">
					<ClayIcon
						className="cp-team-members-support-seat-icon ml-1 mr-1 py-0"
						symbol="info-circle"
					/>
				</ClayButton>
			}
		>
			<p className="m-0 text-neutral-10">
				{i18n.translate(
					'lxc-sm-is-the-abbreviation-of-liferay-experience-cloud-self-managed'
				)}
			</p>
		</ClayPopover>
	);
};

export default PopoverIcon;
