/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
				aria-label={i18n.translate(title)}
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
