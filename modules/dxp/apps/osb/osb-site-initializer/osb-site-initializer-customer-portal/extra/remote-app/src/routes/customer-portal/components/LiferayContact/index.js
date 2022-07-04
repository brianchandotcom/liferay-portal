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

import i18n from '../../../../common/I18n';
import getKebabCase from '../../../../common/utils/getKebabCase';

const LiferayContact = ({koroneikiAccount}) => (
	<div>
		<h5 className="mb-4 rounded-sm text-neutral-10">
			{i18n.translate('liferay-contact')}
		</h5>

		{koroneikiAccount.liferayContactName && (
			<div className="font-weight-bold rounded-sm text-neutral-8 text-paragraph">
				{koroneikiAccount.liferayContactName}
			</div>
		)}

		{koroneikiAccount.liferayContactRole && (
			<div className="rounded-sm text-neutral-10 text-paragraph">
				{i18n.translate(
					getKebabCase(koroneikiAccount.liferayContactRole)
				)}
			</div>
		)}

		{koroneikiAccount.liferayContactEmailAddress && (
			<div className="rounded-sm text-neutral-10 text-paragraph-sm">
				{koroneikiAccount.liferayContactEmailAddress}
			</div>
		)}
	</div>
);

export default LiferayContact;
