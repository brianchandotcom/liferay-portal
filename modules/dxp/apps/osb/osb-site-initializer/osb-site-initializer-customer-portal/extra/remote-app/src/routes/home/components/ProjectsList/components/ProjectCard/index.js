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

import ClayCard from '@clayui/card';
import classNames from 'classnames';
import {memo} from 'react';
import i18n from '../../../../../../common/I18n';
import {PAGE_ROUTER_TYPES} from '../../../../../../common/utils/constants';
import StatusTag from '../../../../../customer-portal/components/StatusTag';
import {STATUS_TAG_TYPES} from '../../../../../customer-portal/utils/constants';
import getDateCustomFormat from '../../../../../customer-portal/utils/getDateCustomFormat';
import getKebabCase from '../../../../../customer-portal/utils/getKebabCase';
import redirect from './utils/redirect';

const statusReport = {
	[STATUS_TAG_TYPES.active]: i18n.translate('ends-on'),
	[STATUS_TAG_TYPES.future]: i18n.translate('starts-on'),
	[STATUS_TAG_TYPES.expired]: i18n.translate('ended-on'),
};

const ProjectCard = ({compressed, ...koroneikiAccount}) => {
	return (
		<ClayCard
			className={classNames('m-0', {
				'cp-project-card': !compressed,
				'cp-project-card-sm': compressed,
			})}
			onClick={() =>
				redirect(PAGE_ROUTER_TYPES.project(koroneikiAccount.accountKey))
			}
		>
			<ClayCard.Body
				className={classNames('d-flex h-100 justify-content-between', {
					'flex-column': !compressed,
					'flex-row': compressed,
				})}
			>
				<ClayCard.Description
					className="text-neutral-7"
					displayType="title"
					tag={compressed ? 'h4' : 'h3'}
					title={koroneikiAccount.name}
				>
					{koroneikiAccount.name}

					{compressed && (
						<div className="font-weight-lighter subtitle text-neutral-5 text-paragraph text-uppercase">
							{koroneikiAccount.code}
						</div>
					)}
				</ClayCard.Description>

				<div
					className={classNames('d-flex justify-content-between', {
						'align-items-end': compressed,
					})}
				>
					<ClayCard.Description
						displayType="text"
						tag="div"
						title={null}
						truncate={false}
					>
						<StatusTag currentStatus={koroneikiAccount.status} />

						<div
							className={classNames(
								'text-paragraph-sm',
								'text-neutral-5',
								{
									'my-1': !compressed,
									'sm-mb': compressed,
								}
							)}
						>
							{statusReport[koroneikiAccount.status]}

							<span className="font-weight-bold ml-1 text-paragraph">
								{getDateCustomFormat(
									koroneikiAccount.slaCurrentEndDate,
									{
										day: '2-digit',
										month: 'short',
										year: 'numeric',
									}
								)}
							</span>
						</div>

						{compressed && (
							<div className="text-align-end text-neutral-5 text-paragraph-sm">
								{i18n.translate('support-region')}

								<span className="font-weight-bold ml-1">
									{i18n.translate(
										getKebabCase(koroneikiAccount.region)
									)}
								</span>
							</div>
						)}
					</ClayCard.Description>
				</div>
			</ClayCard.Body>
		</ClayCard>
	);
};

export default memo(ProjectCard);
