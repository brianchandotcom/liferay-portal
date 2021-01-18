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
import className from 'classnames';
import {ALIGN_POSITIONS} from 'frontend-js-web';
import PropTypes from 'prop-types';
import React, {useMemo, useState} from 'react';

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

const SOCIAL_MEDIA_COLORS = {
	facebook: '#4B9BFF',
	instagram: '#FFB46E',
	linkedin: '#7785FF',
	others: '#6B6C7E',
	pinterest: '#50D2A0',
	snapchat: '#FFD76E',
	tiktok: '#FF73C3',
	twitter: '#5FC8FF',
	youtube: '#FF5F5F',
};

export default function SocialDetail({
	currentPage,
	languageTag,
	timeSpanOptions,
	trafficShareDataProvider,
	trafficVolumeDataProvider,
}) {
	const {referringSocialMedia} = currentPage.data;

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

	const keyToHexColor = (name) => {
		return SOCIAL_MEDIA_COLORS[name] ?? '#666666';
	};

	const keyToWidth = (index) => {
		if (index === 0) {
			return '100%';
		}

		return `${
			(referringSocialMedia[index].trafficAmount * 100) /
			referringSocialMedia[0].trafficAmount
		}%`;
	};

	const [highlighted, setHighlighted] = useState(null);

	function handleLegendMouseEnter(name) {
		setHighlighted(name);
	}

	function handleLegendMouseLeave() {
		setHighlighted(null);
	}

	return (
		<div className="c-p-3 traffic-source-detail">
			<div className="c-mb-3 c-mt-2">
				<TimeSpanSelector
					disabledNextTimeSpan={chartState.timeSpanOffset === 0}
					disabledPreviousPeriodButton={
						isPreviousPeriodButtonDisabled
					}
					onNextTimeSpanClick={nextTimeSpan}
					onPreviousTimeSpanClick={previousTimeSpan}
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
				popoverAlign={ALIGN_POSITIONS.Bottom}
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
				{referringSocialMedia.map(
					({name, title, trafficAmount}, index) => {
						const listItemClasses = className({
							dim: highlighted && name !== highlighted,
						});

						return (
							<ClayList.Item
								className={listItemClasses}
								flex
								key={title}
								onMouseOut={handleLegendMouseLeave}
								onMouseOver={() => handleLegendMouseEnter(name)}
							>
								<ClayList.ItemField style={{width: '70px'}}>
									<ClayList.ItemText>
										<span className="c-mr-2 text-secondary">
											{title}
										</span>
									</ClayList.ItemText>
								</ClayList.ItemField>
								<ClayList.ItemField
									className="align-self-center"
									expand
								>
									<div
										style={{
											backgroundColor: keyToHexColor(
												name
											),
											height: '16px',
											width: keyToWidth(index),
										}}
									/>
								</ClayList.ItemField>
								<ClayList.ItemField className="align-self-center">
									<span className="align-self-end c-ml-2 font-weight-semi-bold text-dark">
										{numberFormat(
											languageTag,
											trafficAmount
										)}
									</span>
								</ClayList.ItemField>
							</ClayList.Item>
						);
					}
				)}
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
