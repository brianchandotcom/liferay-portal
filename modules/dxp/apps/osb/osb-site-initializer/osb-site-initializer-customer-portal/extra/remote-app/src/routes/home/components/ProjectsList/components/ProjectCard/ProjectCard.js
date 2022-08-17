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
import {Skeleton, StatusTag} from '../../../../../../common/components';
import useRouterPath from '../../../../../../common/hooks/useRouterPath';
import getKebabCase from '../../../../../../common/utils/getKebabCase';
import CardTitleDescription from './components/CardTitleDescription/CardTitleDescription';
import StatusDescription from './components/StatusDescription';
import redirect from './utils/redirect';

const ProjectCard = ({compressed, loading, ...koroneikiAccount}) => {
	const pageRoutes = useRouterPath();

	return (
		<ClayCard
			className={classNames('m-0', {
				'cp-project-card': !compressed,
				'cp-project-card-sm': compressed,
			})}
			onClick={() =>
				redirect(pageRoutes.project(koroneikiAccount.accountKey))
			}
		>
			<ClayCard.Body
				className={classNames('d-flex h-100 justify-content-between', {
					'flex-column': !compressed,
					'flex-row': compressed,
				})}
			>
				{loading ? (
					<Skeleton height={32} width={460.5} />
				) : (
					<CardTitleDescription
						compressed={compressed}
						{...koroneikiAccount}
					/>
				)}

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
						{loading ? (
							<Skeleton height={20} width={54} />
						) : (
							<StatusTag
								currentStatus={koroneikiAccount.status}
							/>
						)}

						{loading ? (
							<Skeleton
								className="mt-1"
								height={24}
								width={137}
							/>
						) : (
							<StatusDescription
								compressed={compressed}
								{...koroneikiAccount}
							/>
						)}

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
