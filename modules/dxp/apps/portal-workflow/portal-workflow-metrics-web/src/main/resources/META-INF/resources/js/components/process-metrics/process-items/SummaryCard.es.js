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
import React, {useContext, useMemo, useState} from 'react';

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

	const title = useMemo(() => getTitle(completed), [completed, getTitle]);

	const formattedPercentage = useMemo(() => {
		if (!total) {
			const percentage = getPercentage(value, totalValue);

			return formatNumber(percentage, '0[.]00%');
		}

		return null;
	}, [total, totalValue, value]);

	const formattedValue = useMemo(() => formatNumber(value, '0[,0][.]0a'), [
		value,
	]);

	const filtersQuery = useMemo(() => {
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

		return filterParams;
	}, [completed, slaStatusFilter, timeRange]);

	return (
		<ChildLink
			className={`${
				disabled ? 'disabled' : ''
			} process-dashboard-summary-card`}
			data-testid="childLink"
			onMouseOut={() => setHovered(false)}
			onMouseOver={() => setHovered(true)}
			query={{filters: filtersQuery}}
			to={`/instance/${processId}/${defaultDelta}/1`}
		>
			<div>
				<div className={'header'}>
					{iconName && (
						<span
							className={`bg-${iconColor}-light mr-3 sticker sticker-circle`}
						>
							<span className="inline-item">
								<ClayIcon
									className={`text-${iconColor}`}
									data-testid="instanceIcon"
									symbol={iconName}
								/>
							</span>
						</span>
					)}

					<span data-testid="instanceTitle">{title}</span>
				</div>

				{!disabled && (
					<>
						<div
							className="body"
							data-testid="formattedValue"
							title={value}
						>
							{formattedValue}
						</div>

						<div className="footer" data-testid="footer">
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
