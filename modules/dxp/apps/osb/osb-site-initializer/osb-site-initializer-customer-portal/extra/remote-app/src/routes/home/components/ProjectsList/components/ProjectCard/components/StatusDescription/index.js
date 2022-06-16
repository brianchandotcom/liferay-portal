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

import classNames from 'classnames';
import i18n from '../../../../../../../../common/I18n';
import {SLA_STATUS_TYPES} from '../../../../../../../../common/utils/constants';
import getDateCustomFormat from '../../../../../../../../common/utils/getDateCustomFormat';

const statusReport = {
	[SLA_STATUS_TYPES.active]: i18n.translate('ends-on'),
	[SLA_STATUS_TYPES.future]: i18n.translate('starts-on'),
	[SLA_STATUS_TYPES.expired]: i18n.translate('ended-on'),
};

const StatusDescription = ({compressed, slaCurrentEndDate, status}) => {
	return (
		<div
			className={classNames('text-paragraph-sm', 'text-neutral-5', {
				'my-1': !compressed,
				'sm-mb': compressed,
			})}
		>
			{statusReport[status]}

			<span className="font-weight-bold ml-1 text-paragraph">
				{getDateCustomFormat(slaCurrentEndDate, {
					day: '2-digit',
					month: 'short',
					year: 'numeric',
				})}
			</span>
		</div>
	);
};

export default StatusDescription;
