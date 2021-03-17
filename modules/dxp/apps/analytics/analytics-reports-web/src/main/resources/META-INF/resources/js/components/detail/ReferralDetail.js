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

import ClayButton from '@clayui/button';
import ClayList from '@clayui/list';
import PropTypes from 'prop-types';
import React, {useContext, useMemo, useState} from 'react';

import {
	ChartDispatchContext,
	ChartStateContext,
	useDateTitle,
	useIsPreviousPeriodButtonDisabled,
} from '../../context/ChartStateContext';
import {StoreStateContext} from '../../context/StoreContext';
import {generateDateFormatters as dateFormat} from '../../utils/dateFormat';
import {numberFormat} from '../../utils/numberFormat';
import Hint from '../Hint';
import TimeSpanSelector from '../TimeSpanSelector';
import TotalCount from '../TotalCount';

const ITEMS_TO_SHOW = 5;

export default function ReferralDetail({
	currentPage,
	showTimeSpanSelector = false,
	timeSpanOptions,
	trafficShareDataProvider,
	trafficVolumeDataProvider,
}) {
	const {languageTag} = useContext(StoreStateContext);

	const [isReferringPagesExpanded, setIsReferringPagesExpanded] = useState(
		false
	);

	const [
		isReferringDomainsExpanded,
		setIsReferringDomainsExpanded,
	] = useState(false);

	const {referringDomains, referringPages} = currentPage.data;

	const dateFormatters = useMemo(() => dateFormat(languageTag), [
		languageTag,
	]);

	const {firstDate, lastDate} = useDateTitle();

	const title = useMemo(() => {
		return dateFormatters.formatChartTitle([firstDate, lastDate]);
	}, [dateFormatters, firstDate, lastDate]);

	const dispatch = useContext(ChartDispatchContext);

	const {timeSpanKey, timeSpanOffset} = useContext(ChartStateContext);

	const isPreviousPeriodButtonDisabled = useIsPreviousPeriodButtonDisabled();

	const handleTimeSpanChange = (event) => {
		const {value} = event.target;

		dispatch({payload: {key: value}, type: 'CHANGE_TIME_SPAN_KEY'});
	};

	return (
		<div className="c-p-3 traffic-source-detail">
			{showTimeSpanSelector && (
				<>
					<div className="c-mb-3 c-mt-2">
						<TimeSpanSelector
							disabledNextTimeSpan={timeSpanOffset === 0}
							disabledPreviousPeriodButton={
								isPreviousPeriodButtonDisabled
							}
							onNextTimeSpanClick={() =>
								dispatch({type: 'NEXT_TIME_SPAN'})
							}
							onPreviousTimeSpanClick={() =>
								dispatch({type: 'PREV_TIME_SPAN'})
							}
							onTimeSpanChange={handleTimeSpanChange}
							timeSpanKey={timeSpanKey}
							timeSpanOptions={timeSpanOptions}
						/>
					</div>

					{title && <h5 className="c-mb-4">{title}</h5>}
				</>
			)}

			<TotalCount
				className="c-mb-2"
				dataProvider={trafficVolumeDataProvider}
				label={Liferay.Util.sub(Liferay.Language.get('traffic-volume'))}
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
								{Liferay.Language.get('top-referring-pages')}
								<span className="text-secondary">
									<Hint
										message={Liferay.Language.get(
											'top-referring-pages-help'
										)}
										title={Liferay.Language.get(
											'top-referring-pages'
										)}
									/>
								</span>
							</span>
						</ClayList.ItemTitle>
					</ClayList.ItemField>
					<ClayList.ItemField>
						<ClayList.ItemTitle>
							<span>{Liferay.Language.get('traffic')}</span>
						</ClayList.ItemTitle>
					</ClayList.ItemField>
				</ClayList.Item>
				{referringPages
					.slice(0, isReferringPagesExpanded ? 10 : ITEMS_TO_SHOW)
					.map(({trafficAmount, url}) => {
						return (
							<ClayList.Item flex key={url}>
								<ClayList.ItemField expand>
									<ClayList.ItemText>
										<span
											className="text-truncate-inline"
											data-tooltip-align="top"
											title={url}
										>
											<a
												className="c-mr-2 text-primary text-truncate text-truncate-reverse"
												href={url}
												target="_blank"
											>
												{url}
											</a>
										</span>
									</ClayList.ItemText>
								</ClayList.ItemField>
								<ClayList.ItemField expand>
									<span className="align-self-end font-weight-semi-bold text-dark">
										{numberFormat(
											languageTag,
											trafficAmount
										)}
									</span>
								</ClayList.ItemField>
							</ClayList.Item>
						);
					})}
			</ClayList>

			{referringPages.length > 5 && (
				<ClayButton
					borderless
					className="c-mb-4"
					displayType="secondary"
					onClick={() =>
						setIsReferringPagesExpanded(!isReferringPagesExpanded)
					}
					small
				>
					{isReferringPagesExpanded ? (
						<span>{Liferay.Language.get('view-less')}</span>
					) : (
						<span>{Liferay.Language.get('view-more')}</span>
					)}
				</ClayButton>
			)}

			<ClayList className="list-group-pages-list">
				<ClayList.Item flex>
					<ClayList.ItemField expand>
						<ClayList.ItemTitle className="text-truncate-inline">
							<span className="text-truncate">
								{Liferay.Language.get('top-referring-domains')}
								<span className="text-secondary">
									<Hint
										message={Liferay.Language.get(
											'top-referring-domains-help'
										)}
										title={Liferay.Language.get(
											'top-referring-domains'
										)}
									/>
								</span>
							</span>
						</ClayList.ItemTitle>
					</ClayList.ItemField>
					<ClayList.ItemField>
						<ClayList.ItemTitle>
							<span>{Liferay.Language.get('traffic')}</span>
						</ClayList.ItemTitle>
					</ClayList.ItemField>
				</ClayList.Item>
				{referringDomains
					.slice(0, isReferringDomainsExpanded ? 10 : ITEMS_TO_SHOW)
					.map(({trafficAmount, url}) => {
						return (
							<ClayList.Item flex key={url}>
								<ClayList.ItemField expand>
									<ClayList.ItemText>
										<span
											className="text-truncate-inline"
											data-tooltip-align="top"
											title={url}
										>
											<a
												className="c-mr-2 text-primary text-truncate"
												href={url}
												target="_blank"
											>
												{url}
											</a>
										</span>
									</ClayList.ItemText>
								</ClayList.ItemField>
								<ClayList.ItemField expand>
									<span className="align-self-end font-weight-semi-bold text-dark">
										{numberFormat(
											languageTag,
											trafficAmount
										)}
									</span>
								</ClayList.ItemField>
							</ClayList.Item>
						);
					})}
			</ClayList>

			{referringDomains.length > 5 && (
				<ClayButton
					borderless
					className="c-mb-4"
					displayType="secondary"
					onClick={() =>
						setIsReferringDomainsExpanded(
							!isReferringDomainsExpanded
						)
					}
					small
				>
					{isReferringDomainsExpanded ? (
						<span>{Liferay.Language.get('view-less')}</span>
					) : (
						<span>{Liferay.Language.get('view-more')}</span>
					)}
				</ClayButton>
			)}
		</div>
	);
}

ReferralDetail.propTypes = {
	currentPage: PropTypes.object.isRequired,
	showTimeSpanSelector: PropTypes.bool,
	timeSpanOptions: PropTypes.arrayOf(
		PropTypes.shape({
			key: PropTypes.string,
			label: PropTypes.string,
		})
	).isRequired,
	trafficShareDataProvider: PropTypes.func.isRequired,
	trafficVolumeDataProvider: PropTypes.func.isRequired,
};
