/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import i18n from '../../../../../../../../../../../common/I18n';

const SLACardMessage = () => (
	<div className="bg-neutral-1 cp-sla-card-message rounded-lg">
		<p className="m-0 px-3 py-2 text-neutral-7 text-paragraph-sm">
			{i18n.translate(
				"the-project's-support-level-is-displayed-here-for-projects-with-ticketing-support"
			)}
		</p>
	</div>
);

export default SLACardMessage;
