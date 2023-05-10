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
import {memo} from 'react';
import i18n from '../../../../../../../../../../../common/I18n';
import getKebabCase from '../../../../../../../../../../../common/utils/getKebabCase';
import getStyleFromTitle from './utils/getStyleFromTitle';

const SLACard = ({active, endDate, label, last, startDate, title, unique}) => {
	const displayDate = `${startDate} - ${endDate}`;
	const currentStyle = getStyleFromTitle(title);

	return (
		<div
			className={classNames(
				'align-items-center d-flex',
				!unique && {
					active,
					'cp-sla-card mt-3': true,
					last,
				}
			)}
		>
			<ClayCard
				className={classNames(
					'm-0 p-3 rounded-lg border',
					currentStyle.cardStyle
				)}
			>
				<ClayCard.Row className="align-items-center d-flex justify-content-between">
					<h5 className={classNames('mb-0', currentStyle.titleStyle)}>
						{i18n.translate(getKebabCase(title))}
					</h5>

					<div>
						<ClayCard.Caption>
							<ClayLabel
								className={classNames(
									'mr-0 p-0 text-small-caps',
									currentStyle.labelStyle
								)}
								displayType="secundary"
							>
								{i18n
									.translate(getKebabCase(label))
									.toUpperCase()}
							</ClayLabel>
						</ClayCard.Caption>
					</div>
				</ClayCard.Row>

				<ClayCard.Description
					className={classNames(currentStyle.dateStyle)}
					displayType="text"
					truncate={false}
				>
					{displayDate}
				</ClayCard.Description>
			</ClayCard>
		</div>
	);
};

export default memo(SLACard);
