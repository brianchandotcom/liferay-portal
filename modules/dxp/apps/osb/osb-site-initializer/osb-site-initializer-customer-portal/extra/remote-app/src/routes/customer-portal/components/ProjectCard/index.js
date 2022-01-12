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
import {status as statusProject} from '../../utils/constants';
import getDateCustomFormat from '../../utils/dateCustomFormat';
import StatusTag from '../StatusTag';
import ProjectCardSkeleton from './Skeleton';

const ProjectCard = ({code, isSmall, onClick, region, sla, status, title}) => {
	const getStatusMessage = (currentStatus) => {
		if (currentStatus === statusProject.active) {
			return 'Ends on ';
		}

		if (currentStatus === statusProject.expired) {
			return 'Ended on ';
		}

		return 'Starts on ';
	};

	return (
		<ClayCard
			className={classNames('m-0', {
				'project-card': !isSmall,
				'project-card-sm': isSmall,
			})}
			onClick={() => onClick()}
		>
			<ClayCard.Body
				className={classNames('d-flex h-100 justify-content-between', {
					'flex-column': !isSmall,
					'flex-row': isSmall,
				})}
			>
				<ClayCard.Description
					className="text-neutral-7"
					displayType="title"
					tag={isSmall ? 'h4' : 'h3'}
					title={title}
				>
					{title}

					{isSmall && (
						<div className="font-weight-lighter subtitle text-neutral-5 text-paragraph text-uppercase">
							{code}
						</div>
					)}
				</ClayCard.Description>

				<div
					className={classNames('d-flex justify-content-between', {
						'align-items-end': isSmall,
					})}
				>
					<ClayCard.Description
						displayType="text"
						tag="div"
						title={null}
						truncate={false}
					>
						<StatusTag currentStatus={status} />

						<div
							className={classNames(
								'text-paragraph-sm',
								'text-neutral-5',
								{
									'my-1': !isSmall,
									'sm-mb': isSmall,
								}
							)}
						>
							{getStatusMessage(status)}

							<span className="font-weight-bold text-paragraph">
								{getDateCustomFormat(sla.currentEndDate, {
									day: '2-digit',
									month: 'short',
									year: 'numeric',
								})}
							</span>
						</div>

						{isSmall && (
							<div className="text-align-end text-neutral-5 text-paragraph-sm">
								{'Support Region '}

								<span className="font-weight-bold">
									{region}
								</span>
							</div>
						)}
					</ClayCard.Description>
				</div>
			</ClayCard.Body>
		</ClayCard>
	);
};

ProjectCard.Skeleton = ProjectCardSkeleton;

export default ProjectCard;
