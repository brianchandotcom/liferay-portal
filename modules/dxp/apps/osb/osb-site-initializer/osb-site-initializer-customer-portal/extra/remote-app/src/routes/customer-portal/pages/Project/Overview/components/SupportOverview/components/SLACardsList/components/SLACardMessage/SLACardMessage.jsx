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
