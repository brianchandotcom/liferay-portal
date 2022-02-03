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
import ClayLabel from '@clayui/label';
import classNames from 'classnames';
import React from 'react';

const SlaCardLayout = ({
	slaDateEnd,
	slaDateStart,
	slaLabel,
	slaSelected,
	slaTitle,
}) => {
	const slaDate = `${slaDateStart} - ${slaDateEnd}`;
	const SLA_TITLE = {
		gold: 'Gold',
		limited: 'Limited',
		platinum: 'Platinum',
	};

	return (
		<div
			className={classNames('align-items-center d-flex', {
				'sla-card': slaLabel !== slaSelected,
				'sla-card-active': slaLabel === slaSelected,
			})}
		>
			<ClayCard
				className={classNames('m-0 p-3 rounded-lg', {
					'bg-brand-secondary-lighten-6 sla-gold':
						slaTitle === SLA_TITLE.gold,
					'bg-neutral-0 sla-limited': slaTitle === SLA_TITLE.limited,
					'sla-platinum': slaTitle === SLA_TITLE.platinum,
				})}
			>
				<ClayCard.Row className="align-items-center d-flex justify-content-between">
					<div
						className={classNames('h5 mb-0', {
							'text-brand-primary-darken-2':
								slaTitle === SLA_TITLE.limited,
							'text-brand-secondary-darken-3':
								slaTitle === SLA_TITLE.gold,
							'text-neutral-7': slaTitle === SLA_TITLE.platinum,
						})}
					>
						{slaTitle}
					</div>

					<div>
						<ClayCard.Caption>
							<ClayLabel
								className={classNames(
									'mr-0 p-0 text-small-caps sla-label',
									{
										'label-borderless-dark text-neutral-7':
											slaTitle === SLA_TITLE.platinum,
										'label-borderless-primary text-brand-primary-darken-2':
											slaTitle === SLA_TITLE.limited,
										'label-borderless-secondary text-brand-secondary-darken-3':
											slaTitle === SLA_TITLE.gold,
									}
								)}
								displayType="secundary"
							>
								{slaLabel}
							</ClayLabel>
						</ClayCard.Caption>
					</div>
				</ClayCard.Row>

				<ClayCard.Description
					className={classNames('', {
						'text-brand-primary-darken-2':
							slaTitle === SLA_TITLE.limited,
						'text-brand-secondary-darken-3':
							slaTitle === SLA_TITLE.gold,
						'text-neutral-6': slaTitle === SLA_TITLE.platinum,
					})}
					displayType="text"
					truncate={false}
				>
					{slaDate}
				</ClayCard.Description>
			</ClayCard>
		</div>
	);
};

export default SlaCardLayout;
