import Card from 'shared/components/Card';
import classNames from 'classnames';
import React from 'react';
import Sticker from '@clayui/sticker';
import {Icon, Text} from '@clayui/core';

const mockedData: ContextualInfoData = {
	browser: 'Chrome',
	city: 'Sydney',
	contactId: '522456661',
	country: 'Australia',
	devicePixelRatio: '2',
	deviceType: 'Desktop',
	email: 'test@jorge.com',
	language: 'en-Au',
	operatingSystem: 'MacOS',
	screenHeight: '1920',
	screenWidth: '1080',
	timeZone: 'UTC+10:00 (AEST)',
	userAgent:
		'Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/',
	userID: '522456660',
	UUID: '6ba7b810-9dad-11d1-80b4-00c04fd430c8'
};

type ContextualInfoData = {
	browser?: string;
	city?: string;
	contactId?: string;
	country?: string;
	devicePixelRatio?: string;
	deviceType?: string;
	email?: string;
	language?: string;
	operatingSystem?: string;
	screenHeight?: string;
	screenWidth?: string;
	timeZone?: string;
	userAgent?: string;
	userID?: string;
	UUID?: string;
};

interface IContextualInfoProps {
	contextualInfo: ContextualInfoData;
}

const INFO_LANGUAGE_MAP: Record<string, string> = {
	browser: Liferay.Language.get('browser'),
	city: Liferay.Language.get('city'),
	contactId: Liferay.Language.get('contact-id'),
	country: Liferay.Language.get('country'),
	devicePixelRatio: Liferay.Language.get('device-pixel-ratio'),
	deviceType: Liferay.Language.get('device-type'),
	email: Liferay.Language.get('email'),
	language: Liferay.Language.get('language'),
	operatingSystem: Liferay.Language.get('operating-system'),
	screenHeight: Liferay.Language.get('screen-height'),
	screenWidth: Liferay.Language.get('screen-width'),
	timeZone: Liferay.Language.get('time-zone'),
	userAgent: Liferay.Language.get('user-agent'),
	userID: Liferay.Language.get('user-id'),
	UUID: 'UUID'
};

function InfoItem({
	className,
	icon,
	label,
	value
}: {
	className?: string;
	icon?: string;
	label: string;
	value: string;
}) {
	return (
		<div className={classNames(className, 'align-items-start d-flex mb-2')}>
			<Sticker
				className='flex-shrink-0 mr-3'
				displayType='unstyled'
				size='lg'
			>
				{icon && <Icon className='text-secondary' symbol={icon} />}
			</Sticker>

			<div className='d-flex flex-column mt-1'>
				<Text color='secondary' size={3}>
					{label}
				</Text>
				<span
					className='font-weight-semi-bold text-break text-dark'
					style={{wordBreak: 'break-all'}}
				>
					{value}
				</span>
			</div>
		</div>
	);
}

const ContextualInfo: React.FC<IContextualInfoProps> = ({
	contextualInfo = mockedData
}) => {
	const {
		browser,
		city,
		contactId,
		country,
		devicePixelRatio,
		deviceType,
		email,
		language,
		operatingSystem,
		screenHeight,
		screenWidth,
		timeZone,
		userAgent,
		userID,
		UUID
	} = contextualInfo;

	return (
		<>
			<div className='text-secondary mr-3 my-3'>
				<Icon className='mr-2' symbol='sites' />
				<span className='text-uppercase text-weight-semi-bold'>
					{Liferay.Language.get('contextual-info')}
				</span>
			</div>
			<div className='row g-3'>
				<div className='col-6'>
					<Card className='p-2 h-100'>
						<Card.Title className='text-uppercase px-2 pt-2'>
							<Text size={4}>
								{Liferay.Language.get('last-session-device')}
							</Text>
						</Card.Title>
						<Card.Body className='px-2 pb-0'>
							<div className='row g-2'>
								{deviceType && (
									<InfoItem
										className='col-6'
										icon='mobile-devices'
										label={INFO_LANGUAGE_MAP.deviceType}
										value={deviceType}
									/>
								)}
								{screenHeight && (
									<InfoItem
										className='col-6'
										label={INFO_LANGUAGE_MAP.screenHeight}
										value={screenHeight}
									/>
								)}

								{operatingSystem && (
									<InfoItem
										className='col-6'
										label={
											INFO_LANGUAGE_MAP.operatingSystem
										}
										value={operatingSystem}
									/>
								)}
								{screenWidth && (
									<InfoItem
										className='col-6'
										label={INFO_LANGUAGE_MAP.screenWidth}
										value={screenWidth}
									/>
								)}
								{browser && (
									<InfoItem
										className='col-6'
										label={INFO_LANGUAGE_MAP.browser}
										value={browser}
									/>
								)}
								{devicePixelRatio && (
									<InfoItem
										className='col-6'
										label={
											INFO_LANGUAGE_MAP.devicePixelRatio
										}
										value={devicePixelRatio}
									/>
								)}
								{userAgent && (
									<InfoItem
										className='col-12'
										icon='tabs'
										label={INFO_LANGUAGE_MAP.userAgent}
										value={userAgent}
									/>
								)}
							</div>
						</Card.Body>
					</Card>
				</div>

				<div className='col-3'>
					<Card className='p-2 h-100'>
						<Card.Title className='text-uppercase px-2 pt-2'>
							<Text size={4}>
								{Liferay.Language.get('last-session-location')}
							</Text>
						</Card.Title>
						<Card.Body className='px-2 pb-0'>
							<div className='row g-2'>
								{country && (
									<InfoItem
										className='col-12'
										icon='globe-pin'
										label={INFO_LANGUAGE_MAP.country}
										value={country}
									/>
								)}
								{city && (
									<InfoItem
										className='col-12'
										label={INFO_LANGUAGE_MAP.city}
										value={city}
									/>
								)}
								{language && (
									<InfoItem
										className='col-12'
										label={INFO_LANGUAGE_MAP.language}
										value={language}
									/>
								)}
								{timeZone && (
									<InfoItem
										className='col-12'
										label={INFO_LANGUAGE_MAP.timeZone}
										value={timeZone}
									/>
								)}
							</div>
						</Card.Body>
					</Card>
				</div>

				<div className='col-3'>
					<Card className='p-2 h-100'>
						<Card.Title className='text-uppercase px-2 pt-2'>
							<Text size={4}>
								{Liferay.Language.get(
									'individual-unique-identifiers'
								)}
							</Text>
						</Card.Title>
						<Card.Body className='px-2 pb-0'>
							<div className='row g-2'>
								{email && (
									<InfoItem
										className='col-12'
										icon='lock'
										label={INFO_LANGUAGE_MAP.email}
										value={email}
									/>
								)}
								{UUID && (
									<InfoItem
										className='col-12'
										label={INFO_LANGUAGE_MAP.UUID}
										value={UUID}
									/>
								)}
								{userID && (
									<InfoItem
										className='col-12'
										label={INFO_LANGUAGE_MAP.userID}
										value={userID}
									/>
								)}
								{contactId && (
									<InfoItem
										className='col-12'
										label={INFO_LANGUAGE_MAP.contactId}
										value={contactId}
									/>
								)}
							</div>
						</Card.Body>
					</Card>
				</div>
			</div>
		</>
	);
};

export default ContextualInfo;
