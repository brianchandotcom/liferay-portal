/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Button as ClayButton} from '@clayui/core';
import i18n from '~/common/I18n';

const IncidentContactsButton = ({onClick}) => {
	return (
		<ClayButton
			className="btn btn-secondary btn-sm incident-contact-button px-2 py-2"
			displayType="secondary"
			onClick={onClick}
		>
			{i18n.translate('select-team-members')}
		</ClayButton>
	);
};

export default IncidentContactsButton;
