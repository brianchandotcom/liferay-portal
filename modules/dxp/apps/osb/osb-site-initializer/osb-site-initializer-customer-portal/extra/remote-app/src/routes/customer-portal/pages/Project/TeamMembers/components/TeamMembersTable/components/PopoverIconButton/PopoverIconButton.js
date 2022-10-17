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

import {ClayButtonWithIcon} from '@clayui/button';
import ClayPopover from '@clayui/popover';
import i18n from '../../../../../../../../../common/I18n';

const PopoverIconButton = ({
	alignPosition = 'bottom',
	articleAccountSupportURL,
}) => (
	<ClayPopover
		alignPosition={alignPosition}
		closeOnClickOutside
		size="lg"
		trigger={
			<ClayButtonWithIcon
				className="text-brand-primary-darken-2"
				displayType={null}
				size="sm"
				symbol="info-circle"
			/>
		}
	>
		<p className="font-weight-bold m-0">
			{i18n.translate(
				'the-limit-of-support-seats-available-counts-the-total-of-administrators-requesters-roles-assigned-due-to-both-have-role-permissions-to-open-support-tickets'
			)}
			&nbsp;
			<a
				href={articleAccountSupportURL}
				rel="noopener noreferrer"
				target="_blank"
			>
				{i18n.translate('learn-more-about-customer-portal-roles')}
			</a>
		</p>
	</ClayPopover>
);

export default PopoverIconButton;
