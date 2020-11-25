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

import ClayList from '@clayui/list';
import {Align} from 'metal-position';
import PropTypes from 'prop-types';
import React, {useMemo} from 'react';

import {
	useChangeTimeSpanKey,
	useChartState,
	useDateTitle,
	useIsPreviousPeriodButtonDisabled,
	useNextTimeSpan,
	usePreviousTimeSpan,
} from '../../context/ChartStateContext';
import {generateDateFormatters as dateFormat} from '../../utils/dateFormat';
import {numberFormat} from '../../utils/numberFormat';
import TimeSpanSelector from '../TimeSpanSelector';
import TotalCount from '../TotalCount';

export default function SocialDetail({
	currentPage,
	languageTag,
	timeSpanOptions,
	trafficShareDataProvider,
	trafficVolumeDataProvider,
}) {
	const {details} = currentPage.data;
	const {referringSocialMedia} = details;

	const dateFormatters = useMemo(() => dateFormat(languageTag), [
		languageTag,
	]);

	const {firstDate, lastDate} = useDateTitle();

	const title = dateFormatters.formatChartTitle([firstDate, lastDate]);

	const chartState = useChartState();

	const isPreviousPeriodButtonDisabled = useIsPreviousPeriodButtonDisabled();

	const changeTimeSpanKey = useChangeTimeSpanKey();

	const previousTimeSpan = usePreviousTimeSpan();

	const nextTimeSpan = useNextTimeSpan();

	const handleTimeSpanChange = (event) => {
		const {value} = event.target;

		changeTimeSpanKey({key: value});
	};

	const handlePreviousTimeSpanClick = () => {
		previousTimeSpan();
	};

	const handleNextTimeSpanClick = () => {
		nextTimeSpan();
	};

	return (
		<div className="c-p-3 traffic-source-detail">
			<div className="c-mb-3 c-mt-2">
				<TimeSpanSelector
					disabledNextTimeSpan={chartState.timeSpanOffset === 0}
					disabledPreviousPeriodButton={
						isPreviousPeriodButtonDisabled
					}
					onNextTimeSpanClick={handleNextTimeSpanClick}
					onPreviousTimeSpanClick={handlePreviousTimeSpanClick}
					onTimeSpanChange={handleTimeSpanChange}
					timeSpanKey={chartState.timeSpanKey}
					timeSpanOptions={timeSpanOptions}
				/>
			</div>

			{title && <h5 className="c-mb-4">{title}</h5>}

			<TotalCount
				className="c-mb-2"
				dataProvider={trafficVolumeDataProvider}
				label={Liferay.Util.sub(Liferay.Language.get('traffic-volume'))}
				languageTag={languageTag}
				popoverAlign={Align.Bottom}
				popoverHeader={Liferay.Language.get('traffic-volume')}
				popoverMessage={Liferay.Language.get(
					'traffic-volume-is-the-number-of-page-views-coming-from-one-channel'
				)}
				popoverPosition="bottom"
			/>

			<TotalCount
				className="c-mb-3"
				dataProvider={trafficShareDataProvider}
				label={Liferay.Util.sub(Liferay.Language.get('traffic-share'))}
				percentage={true}
				popoverHeader={Liferay.Language.get('traffic-share')}
				popoverMessage={Liferay.Language.get(
					'traffic-share-is-the-percentage-of-traffic-sent-to-your-page-by-one-channel'
				)}
			/>

			<ClayList className="list-group-pages-list">
				<ClayList.Item flex>
					<ClayList.ItemField expand>
						<ClayList.ItemTitle className="text-truncate-inline">
							<span className="text-truncate">
								{Liferay.Language.get(
									'top-referring-social-media'
								)}
							</span>
						</ClayList.ItemTitle>
					</ClayList.ItemField>
					<ClayList.ItemField>
						<ClayList.ItemTitle>
							<span>{Liferay.Language.get('traffic')}</span>
						</ClayList.ItemTitle>
					</ClayList.ItemField>
				</ClayList.Item>
				{referringSocialMedia.map(({title, traffic}) => {
					return (
						<ClayList.Item flex key={title}>
							<ClayList.ItemField expand>
								<ClayList.ItemText>
									<span className="c-mr-2">{title}</span>
								</ClayList.ItemText>
							</ClayList.ItemField>
							<ClayList.ItemField expand>
								<span className="align-self-end">
									{numberFormat(languageTag, traffic)}
								</span>
							</ClayList.ItemField>
						</ClayList.Item>
					);
				})}
			</ClayList>
		</div>
	);
}

SocialDetail.propTypes = {
	currentPage: PropTypes.object.isRequired,
	languageTag: PropTypes.string.isRequired,
	timeSpanOptions: PropTypes.arrayOf(
		PropTypes.shape({
			key: PropTypes.string,
			label: PropTypes.string,
		})
	).isRequired,
	trafficShareDataProvider: PropTypes.func.isRequired,
	trafficVolumeDataProvider: PropTypes.func.isRequired,
};
