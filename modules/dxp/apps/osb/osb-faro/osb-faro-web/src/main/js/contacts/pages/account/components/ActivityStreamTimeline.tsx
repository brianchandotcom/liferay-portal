import ClayIcon from '@clayui/icon';
import Loading from 'shared/components/Loading';
import moment from 'moment';
import NoResultsDisplay from 'shared/components/NoResultsDisplay';
import PaginationBar from 'shared/components/PaginationBar';
import React, {useMemo, useState} from 'react';
import {
	AccountUserSession,
	AccountUserSessionEvent
} from 'shared/queries/AccountUserSessionQuery';
import {applyTimeZone, formatDateToTimeZone} from 'shared/util/date';
import {Sizes} from 'shared/util/constants';
import {Text} from '@clayui/core';

interface IActivityStreamTimelineProps {
	delta: number;
	hasQuery: boolean;
	loading: boolean;
	onClearQuery: () => void;
	onDeltaChange: (delta: number) => void;
	onPageChange: (page: number) => void;
	page: number;
	sessions: AccountUserSession[];
	timeZoneId?: string;
	totalEvents: number;
}

interface UserGroup {
	sessions: AccountUserSession[];
	userName: string;
}

interface DateGroup {
	dateKey: string;
	totalEvents: number;
	userGroups: UserGroup[];
}

const DEVICE_ICONS: Record<string, string> = {
	desktop: 'ac_display',
	mobile: 'mobile-portrait',
	smartphone: 'mobile-portrait',
	tablet: 'ac_tablet_landscape'
};

const SESSION_ATTRIBUTE_KEYS: (keyof AccountUserSession)[] = [
	'contentLanguageId',
	'devicePixelRatio',
	'languageId',
	'screenHeight',
	'screenWidth',
	'timezoneOffset',
	'userAgent'
];

const EVENT_ATTRIBUTE_KEYS: (keyof AccountUserSessionEvent)[] = [
	'applicationId',
	'assetTitle',
	'canonicalUrl',
	'pageDescription',
	'pageKeywords',
	'referrer',
	'url'
];

const RAIL_LEFT_REM = 0.8125;
const RAIL_PADDING_LEFT_REM = 2.0625;
const RAIL_WIDTH_PX = 2;

const RAIL_CENTER_FROM_WRAPPER_REM =
	RAIL_LEFT_REM - RAIL_PADDING_LEFT_REM + RAIL_WIDTH_PX / 2 / 16;

const SESSION_DOT_SIZE_REM = 0.875;
const EVENT_DOT_SIZE_REM = 0.5;

const RAIL_STYLE: React.CSSProperties = {
	backgroundColor: '#e7e7ed',
	bottom: 0,
	left: `${RAIL_LEFT_REM}rem`,
	position: 'absolute',
	top: 0,
	width: RAIL_WIDTH_PX
};

const SESSION_DOT_STYLE: React.CSSProperties = {
	backgroundColor: '#1f8be8',
	border: '2px solid #fff',
	borderRadius: '50%',
	height: `${SESSION_DOT_SIZE_REM}rem`,
	left: `${RAIL_CENTER_FROM_WRAPPER_REM - SESSION_DOT_SIZE_REM / 2}rem`,
	position: 'absolute',
	top: '0.625rem',
	width: `${SESSION_DOT_SIZE_REM}rem`,
	zIndex: 1
};

const EVENT_DOT_STYLE: React.CSSProperties = {
	backgroundColor: '#a7a9bc',
	border: '1px solid #fff',
	borderRadius: '50%',
	height: `${EVENT_DOT_SIZE_REM}rem`,
	left: `${RAIL_CENTER_FROM_WRAPPER_REM - EVENT_DOT_SIZE_REM / 2}rem`,
	position: 'absolute',
	top: '0.875rem',
	width: `${EVENT_DOT_SIZE_REM}rem`,
	zIndex: 1
};

const RAIL_INDENT_STYLE: React.CSSProperties = {
	paddingLeft: `${RAIL_PADDING_LEFT_REM}rem`
};

const ANONYMOUS_FALLBACK = (): string => Liferay.Language.get('anonymous');

const formatDay = (dateKey: string, timeZoneId?: string): string =>
	formatDateToTimeZone(dateKey, 'MMM D, YYYY', timeZoneId);

const formatTime = (date: string, timeZoneId?: string): string =>
	formatDateToTimeZone(date, 'h:mma', timeZoneId);

const formatSessionTimeRange = (
	{completeDate, createDate}: AccountUserSession,
	timeZoneId?: string
): string => {
	const start = formatTime(createDate, timeZoneId);

	if (completeDate) {
		const end = formatTime(completeDate, timeZoneId);

		return start === end ? start : `${start} - ${end}`;
	}

	return `${start} - ${Liferay.Language.get('in-progress').toLowerCase()}`;
};

const formatAttributeValue = (value: unknown): string => {
	if (value === null || value === undefined || value === '') {
		return '""';
	}

	if (typeof value === 'string') {
		return /^[\w-]+$/.test(value) ? value : `"${value}"`;
	}

	return String(value);
};

const groupByDate = (
	sessions: AccountUserSession[],
	timeZoneId?: string
): DateGroup[] => {
	const byDate = new Map<string, AccountUserSession[]>();

	sessions.forEach(session => {
		const dateKey = applyTimeZone(session.createDate, timeZoneId)
			.startOf('day')
			.format('YYYY-MM-DD');

		const bucket = byDate.get(dateKey) ?? [];

		bucket.push(session);

		byDate.set(dateKey, bucket);
	});

	return Array.from(byDate.entries())
		.sort(([a], [b]) => (a < b ? 1 : a > b ? -1 : 0))
		.map(([dateKey, daySessions]) => {
			const byUser = new Map<string, AccountUserSession[]>();

			daySessions.forEach(session => {
				const key = session.userName ?? ANONYMOUS_FALLBACK();

				const bucket = byUser.get(key) ?? [];

				bucket.push(session);

				byUser.set(key, bucket);
			});

			const userGroups: UserGroup[] = Array.from(byUser.entries()).map(
				([userName, userSessions]) => ({
					sessions: userSessions.sort(
						(a, b) =>
							moment(b.createDate).valueOf() -
							moment(a.createDate).valueOf()
					),
					userName
				})
			);

			const totalEvents = daySessions.reduce(
				(acc, {events}) => acc + (events?.length ?? 0),
				0
			);

			return {
				dateKey,
				totalEvents,
				userGroups
			};
		});
};

const ActivityStreamTimeline: React.FC<IActivityStreamTimelineProps> = ({
	delta,
	hasQuery,
	loading,
	onClearQuery,
	onDeltaChange,
	onPageChange,
	page,
	sessions,
	timeZoneId,
	totalEvents
}) => {
	const dateGroups = useMemo(
		() => groupByDate(sessions, timeZoneId),
		[sessions, timeZoneId]
	);

	if (loading) {
		return <Loading />;
	}

	if (!sessions.length) {
		if (hasQuery) {
			return (
				<NoResultsDisplay
					description={Liferay.Language.get(
						'review-your-search-and-try-again'
					)}
					icon={{
						border: false,
						size: Sizes.XXXLarge,
						symbol: 'ac_no_results_found'
					}}
					spacer
					title={Liferay.Language.get('there-are-no-results-found')}
				>
					<button
						className='btn btn-secondary'
						onClick={onClearQuery}
						type='button'
					>
						{Liferay.Language.get('clear-search')}
					</button>
				</NoResultsDisplay>
			);
		}

		return (
			<NoResultsDisplay
				description={Liferay.Language.get(
					'check-back-later-to-see-if-data-has-been-received-from-your-data-sources,-or-try-a-different-date-range'
				)}
				spacer
				title={Liferay.Language.get('there-is-no-data-found')}
			/>
		);
	}

	return (
		<div className='account-activity-stream-timeline'>
			{dateGroups.map(({dateKey, totalEvents: dayEvents, userGroups}) => (
				<section className='mb-4' key={dateKey}>
					<header className='d-flex align-items-center py-2 border-bottom mb-3'>
						<Text size={4} weight='semi-bold'>
							{formatDay(dateKey, timeZoneId)}
						</Text>

						<span className='ml-3 text-secondary d-inline-flex align-items-center'>
							<ClayIcon className='mr-1' symbol='click' />

							{dayEvents}
						</span>
					</header>

					{userGroups.map(({sessions: userSessions, userName}) => (
						<div className='mb-4' key={`${dateKey}-${userName}`}>
							<div className='d-flex align-items-center mb-2'>
								<span
									className='d-inline-flex align-items-center justify-content-center bg-secondary text-white rounded-circle mr-2'
									style={{
										height: '1.625rem',
										width: '1.625rem'
									}}
								>
									<ClayIcon symbol='user' />
								</span>

								<Text color='primary' weight='semi-bold'>
									{userName}
								</Text>
							</div>

							<div
								className='position-relative'
								style={RAIL_INDENT_STYLE}
							>
								<div style={RAIL_STYLE} />

								{userSessions.map((session, idx) => (
									<SessionRow
										key={`${session.createDate}-${idx}`}
										session={session}
										timeZoneId={timeZoneId}
									/>
								))}
							</div>
						</div>
					))}
				</section>
			))}

			<PaginationBar
				className='mt-3'
				onDeltaChange={onDeltaChange}
				onPageChange={onPageChange}
				page={page}
				selectedDelta={delta}
				totalItems={totalEvents}
			/>
		</div>
	);
};

interface ISessionRowProps {
	session: AccountUserSession;
	timeZoneId?: string;
}

const SessionRow: React.FC<ISessionRowProps> = ({session, timeZoneId}) => {
	const [expanded, setExpanded] = useState(false);

	const deviceIcon = session.deviceType
		? DEVICE_ICONS[session.deviceType.toLowerCase()] ?? 'devices'
		: null;

	return (
		<div className='mb-2'>
			<div className='position-relative'>
				<span style={SESSION_DOT_STYLE} />

				<button
					aria-expanded={expanded}
					className={`btn btn-unstyled d-flex align-items-center w-100 py-2 px-3 text-left rounded ${
						expanded ? 'bg-light' : ''
					}`}
					onClick={() => setExpanded(prev => !prev)}
					type='button'
				>
					<Text weight='semi-bold'>
						{formatSessionTimeRange(session, timeZoneId)}
					</Text>

					<span className='ml-auto d-inline-flex align-items-center text-secondary'>
						<span className='d-inline-flex align-items-center mr-3'>
							<ClayIcon className='mr-1' symbol='click' />

							{session.events?.length ?? 0}
						</span>

						{deviceIcon && (
							<ClayIcon className='mr-3' symbol={deviceIcon} />
						)}

						<ClayIcon
							symbol={expanded ? 'caret-top' : 'caret-bottom'}
						/>
					</span>
				</button>
			</div>

			{expanded && <SessionAttributes session={session} />}

			<ul className='list-unstyled mb-0 mt-2'>
				{session.events?.map((event, idx) => (
					<EventRow
						event={event}
						key={`${event.createDate}-${idx}`}
						timeZoneId={timeZoneId}
					/>
				))}
			</ul>
		</div>
	);
};

interface IEventRowProps {
	event: AccountUserSessionEvent;
	timeZoneId?: string;
}

const EventRow: React.FC<IEventRowProps> = ({event, timeZoneId}) => {
	const [expanded, setExpanded] = useState(false);

	return (
		<li className='mb-1'>
			<div className='position-relative'>
				<span style={EVENT_DOT_STYLE} />

				<button
					aria-expanded={expanded}
					className='btn btn-unstyled d-flex align-items-baseline w-100 py-2 px-3 text-left rounded'
					onClick={() => setExpanded(prev => !prev)}
					type='button'
				>
					<span className='text-secondary' style={{minWidth: '5rem'}}>
						{formatTime(event.createDate, timeZoneId)}
					</span>

					<span className='text-secondary mr-3'>{event.name}</span>

					<div className='flex-grow-1 min-w-0'>
						<div className='font-weight-semi-bold text-truncate'>
							{event.pageTitle || event.assetTitle}
						</div>

						{event.url && (
							<div className='text-secondary text-truncate'>
								{event.url}
							</div>
						)}
					</div>

					<ClayIcon
						className='ml-3 text-secondary'
						symbol={expanded ? 'caret-top' : 'caret-bottom'}
					/>
				</button>
			</div>

			{expanded && <EventAttributes event={event} />}
		</li>
	);
};

interface ISessionAttributesProps {
	session: AccountUserSession;
}

const SessionAttributes: React.FC<ISessionAttributesProps> = ({session}) => (
	<div className='px-3 py-3 bg-light rounded'>
		<div className='font-weight-semi-bold mb-2'>
			{Liferay.Language.get('session-attributes')}
		</div>

		<AttributesList
			items={SESSION_ATTRIBUTE_KEYS.map(key => ({
				key,
				value: session[key]
			}))}
		/>
	</div>
);

interface IEventAttributesProps {
	event: AccountUserSessionEvent;
}

const EventAttributes: React.FC<IEventAttributesProps> = ({event}) => (
	<div className='px-3 py-3 bg-light rounded'>
		<div className='font-weight-semi-bold mb-2'>
			{Liferay.Language.get('event-attributes')}
		</div>

		<AttributesList
			items={EVENT_ATTRIBUTE_KEYS.map(key => ({
				key,
				value: event[key]
			}))}
		/>
	</div>
);

interface IAttributesListProps {
	items: {key: string; value: unknown}[];
}

const AttributesList: React.FC<IAttributesListProps> = ({items}) => (
	<dl className='mb-0'>
		{items.map(({key, value}) => (
			<div className='d-flex py-1' key={key}>
				<dt
					className='font-weight-normal text-secondary mb-0'
					style={{minWidth: '11rem'}}
				>
					{key}
				</dt>

				<dd className='mb-0 text-break'>
					{formatAttributeValue(value)}
				</dd>
			</div>
		))}
	</dl>
);

export default ActivityStreamTimeline;
