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
import {StatusTag} from '../../../../../../common/components';
import useRouterPath from '../../../../../../common/hooks/useRouterPath';
import {
	FORMAT_DATE_TYPES,
	SLA_STATUS_TYPES,
} from '../../../../../../common/utils/constants';
import getDateCustomFormat from '../../../../../../common/utils/getDateCustomFormat';
import getKebabCase from '../../../../../../common/utils/getKebabCase';
import redirect from './utils/redirect';

const statusReport = {
	[SLA_STATUS_TYPES.active]: i18n.translate('ends-on'),
	[SLA_STATUS_TYPES.future]: i18n.translate('starts-on'),
	[SLA_STATUS_TYPES.expired]: i18n.translate('ended-on'),
};

const ProjectCard = ({compressed, ...koroneikiAccount}) => {
	const pageRoutes = useRouterPath();

	return (
		<ClayCard
			className={classNames(
				'border border-brand-primary-lighten-4 card-interactive shadow-none',
				{
					'card-horizontal mb-3': compressed,
					'cp-project-card-lg mr-5 mb-4': !compressed,
				}
			)}
			onClick={() =>
				redirect(pageRoutes.project(koroneikiAccount.accountKey))
			}
		>
			<ClayCard.Body
				className={classNames({
					'mx-2 py-4 my-3': !compressed,
					'py-4': compressed,
				})}
			>
				<ClayCard.Row
					className={classNames({
						'flex-column': !compressed,
					})}
				>
					<div
						className={classNames('text-truncate-inline', {
							'autofit-col autofit-col-expand': compressed,
						})}
					>
						<div
							className={classNames(
								'mb-1 text-neutral-7 text-truncate',
								{
									h3: !compressed,
									h4: compressed,
								}
							)}
						>
							{koroneikiAccount.name}
						</div>

						{compressed && (
							<div className="text-neutral-5 text-paragraph text-truncate text-uppercase">
								{koroneikiAccount.code}
							</div>
						)}
					</div>

					<div
						className={classNames({
							'autofit-col d-block text-right': compressed,
							'mt-6 pt-3': !compressed,
						})}
					>
						<StatusTag currentStatus={koroneikiAccount.status} />

						<div className="text-neutral-5 text-paragraph-sm">
							{statusReport[koroneikiAccount.status]}

							<span className="font-weight-bold ml-1 text-paragraph">
								{getDateCustomFormat(
									koroneikiAccount.slaCurrentEndDate,
									FORMAT_DATE_TYPES.day2DMonthSYearN
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
					</div>
				</ClayCard.Row>
			</ClayCard.Body>
		</ClayCard>
	);
};

export default memo(ProjectCard);
