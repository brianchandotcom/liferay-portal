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

import ClayIcon from '@clayui/icon';
import React, {useContext, useState} from 'react';

import filterConstants from '../../../shared/components/filter/util/filterConstants.es';
import ChildLink from '../../../shared/components/router/ChildLink.es';
import {formatNumber} from '../../../shared/util/numeral.es';
import {getPercentage} from '../../../shared/util/util.es';
import {AppContext} from '../../AppContext.es';
import {processStatusConstants} from '../../filter/ProcessStatusFilter.es';

const SummaryCard = ({
	completed,
	getTitle,
	iconColor,
	iconName,
	processId,
	slaStatusFilter,
	timeRange,
	total,
	totalValue,
	value,
}) => {
	const [hovered, setHovered] = useState(false);
	const {defaultDelta} = useContext(AppContext);
	const disabled = !total && value === undefined;
	const formattedPercentage = !total
		? formatNumber(getPercentage(value, totalValue), '0[.]00%')
		: null;
	const formattedValue = formatNumber(value, '0[,0][.]0a');

	const filterParams = {
		[filterConstants.processStatus.key]: [
			completed
				? processStatusConstants.completed
				: processStatusConstants.pending,
		],
		[filterConstants.slaStatus.key]: [slaStatusFilter],
	};

	if (timeRange) {
		const {dateEnd, dateStart, key} = timeRange;

		filterParams.dateEnd = dateEnd;
		filterParams.dateStart = dateStart;
		filterParams.timeRange = [key];
	}

	return (
		<ChildLink
			className={`${
				disabled ? 'disabled' : ''
			} process-dashboard-summary-card`}
			onMouseOut={() => setHovered(false)}
			onMouseOver={() => setHovered(true)}
			query={{filters: filterParams}}
			to={`/instance/${processId}/${defaultDelta}/1/dateCreated:asc`}
		>
			<div>
				<div className="header">
					{iconName && (
						<span
							className={`bg-${iconColor}-light mr-3 sticker sticker-circle`}
						>
							<span className="inline-item">
								<ClayIcon
									className={`text-${iconColor}`}
									symbol={iconName}
								/>
							</span>
						</span>
					)}

					<span>{getTitle(completed)}</span>
				</div>

				{!disabled && (
					<>
						<div className="body" title={value}>
							{formattedValue}
						</div>

						<div className="footer">
							<span
								className={`${
									hovered ? 'highlight-hover' : ''
								} xsmall`}
							>
								{hovered
									? Liferay.Language.get('see-items')
									: formattedPercentage}
							</span>
						</div>
					</>
				)}
			</div>
		</ChildLink>
	);
};

export default SummaryCard;
