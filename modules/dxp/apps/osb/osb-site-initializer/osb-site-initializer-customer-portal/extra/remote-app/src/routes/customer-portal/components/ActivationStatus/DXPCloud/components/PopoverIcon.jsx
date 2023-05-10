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
import {ClayTooltipProvider} from '@clayui/tooltip';
import i18n from '../../../../../../common/I18n';

const PopoverIcon = ({
	symbol = 'info-circle',
	title = 'lxc-sm-is-the-abbreviation-of-liferay-experience-cloud-self-managed',
}) => (
	<ClayTooltipProvider>
		<span>
			<ClayButtonWithIcon
				className="text-brand-primary-darken-2"
				data-tooltip-align="right"
				displayType={null}
				size="sm"
				symbol={symbol}
				title={i18n.translate(title)}
			/>
		</span>
	</ClayTooltipProvider>
);

export default PopoverIcon;
