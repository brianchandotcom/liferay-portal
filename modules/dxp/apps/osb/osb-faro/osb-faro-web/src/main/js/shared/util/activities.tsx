import moment from 'moment';
import React from 'react';
import {DEFAULT_ACTIVITY_MAX} from 'shared/api/activities';
import {
	flattenDepth,
	flow,
	groupBy,
	map,
	mapValues,
	orderBy,
	toPairs
} from 'lodash/fp';
import {RangeSelectors} from 'shared/types';
import {sub} from 'shared/util/lang';
import {TimeIntervals} from 'shared/util/constants';
import {UserSession, UserSessionEvent} from 'shared/queries/UserSessionQuery';

export const CHART_ACTIVITY_ID = 'activities';
export const CHART_ID = 'individualActivity';

export const INTERVAL_MAP = {
	D: TimeIntervals.Day,
	M: TimeIntervals.Month,
	W: TimeIntervals.Week
};

type SessionEvent = {
	attributes: SessionEventAttribute;
	description: string;
	subtitle: string;
	time: moment.Moment;
	title: string;
};

type SessionEventAttribute = {
	canonicalUrl: string;
	header: string;
	referrer: string;
	title: string;
	url: string;
};

export type UserSessionAttributes = {
	contentLanguageID: string;
	description: string;
	devicePixelRatioz: number;
	header: string;
	keywords: string;
	languageID: string;
	screenHeight: number;
	screenWidth: number;
	timezoneOffset: string;
	userAgent: string;
};

export type VerticalTimelineHeader = {
	header: boolean;
	title: moment.Moment;
	totalEvents: number;
};

export type VerticalTimelineSession = {
	attributes: UserSessionAttributes;
	device: string;
	endTime: Date;
	nestedItems: SessionEvent[];
	time: moment.Moment;
};

/**
 * Format actvitiy metrics for use in ChangeLegend
 * @param {Object} changeMetrics - History data points.
 * @param {number} changeMetrics.activityChange - The activity count change from
 *                                                previous period.
 * @param {number} changeMetrics.activityCount - The activity count.
 * @return {Array} Activity metrics formatted for use in ChangeLegend.
 */
export const buildLegendItems = ({
	activityChange,
	activityCount
}: {
	activityChange: number;
	activityCount: number;
}): {change: number; id: string; secondaryInfo: string; title: string}[] => [
	{
		change: activityChange,
		id: CHART_ACTIVITY_ID,
		secondaryInfo: sub(Liferay.Language.get('x-day-change'), [
			DEFAULT_ACTIVITY_MAX
		]) as string,
		title: sub(Liferay.Language.get('total-activity-count-x'), [
			activityCount.toLocaleString()
		]) as string
	}
];

/**
 * Formats UserSessions events and maps its attributes to the required to be used in VerticalTimeline component.
 * @param {Array} events Array of UserSessions events.
 * @returns {Array.<Object>} Array of objects for a vertical timeline.
 */
const formatEvents = (events: UserSessionEvent[]): Array<SessionEvent> =>
	events.map(
		({canonicalUrl, createDate, name, pageTitle, referrer, url}) => ({
			attributes: {
				canonicalUrl,
				header: Liferay.Language.get('event-attributes'),
				referrer,
				title: pageTitle,
				url
			},
			description: pageTitle,
			subtitle: canonicalUrl,
			time: moment(createDate),
			title: name
		})
	);

/**
 * Formats datetime to today or the current date.
 * @param {Date|string|number} datetime - Any value accepeted by Moment.
 * @returns {Moment} Date label to be displayed.
 */
export const formatGroupingTime = (
	datetime: Date | string | number
): moment.Moment => {
	const time = moment(datetime);

	return time.isSame(moment(), 'day')
		? Liferay.Language.get('today')
		: time.utc().format('ll');
};

/**
 * Format sessions into a format usable by the VerticalTimeline component while grouping them by day.
 * @param {Array} sessions
 * @returns {Array.<Object>} An array of session objects.
 */
export const formatSessions = (
	sessions: UserSession[]
): (VerticalTimelineHeader | VerticalTimelineSession)[] =>
	flow(
		groupBy(({createDate}) =>
			moment.utc(createDate).startOf('day').format()
		),
		mapValues(items =>
			items.map(
				({
					browserName,
					completeDate,
					contentLanguageID,
					createDate,
					devicePixelRatioz,
					deviceType,
					events,
					languageID,
					screenHeight,
					screenWidth,
					timezoneOffset,
					userAgent
				}) => ({
					attributes: {
						contentLanguageID,
						devicePixelRatioz,
						header: Liferay.Language.get('session-attributes'),
						languageID,
						screenHeight,
						screenWidth,
						timezoneOffset,
						userAgent
					},
					browserName,
					device: deviceType,
					endTime: completeDate,
					nestedItems: formatEvents(events),
					time: createDate
				})
			)
		),
		toPairs,
		orderBy([([time]) => moment(time).unix()], ['desc']),
		map(([time, items]: any[]) => [
			{
				header: true,
				title: formatGroupingTime(time),
				totalEvents: items.reduce(
					(previousValue, currentValue) =>
						previousValue + currentValue.nestedItems.length,
					0
				)
			},
			items
		]),
		flattenDepth(3)
	)(sessions);

/**
 * Helper function get the correct pluralization of count label.
 * @param {Number} totalEvents
 * @returns {Array} Label to be displayed.
 */
export const getActivityLabel = (totalEvents: number): React.ReactNode[] =>
	sub(
		totalEvents === 1
			? Liferay.Language.get('event-x')
			: Liferay.Language.get('events-x'),
		[<b key='ACTIVITIES'>{totalEvents}</b>],
		false
	) as React.ReactNode[];

export const getSafeRangeKey = (
	rangeKey: RangeSelectors['rangeKey']
): RangeSelectors['rangeKey'] | null => {
	if (rangeKey === 'CUSTOM') {
		return null;
	}

	return rangeKey;
};
