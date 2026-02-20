import Card from 'shared/components/Card';
import classNames from 'classnames';
import React from 'react';
import Sticker from '@clayui/sticker';
import {Icon, Text} from '@clayui/core';

const ContextualInfoConfig = [
	{
		columnClass: 'col-6',
		items: [
			{className: 'col-6', icon: 'mobile-devices', key: 'deviceType'},
			{className: 'col-6', key: 'screenHeight'},
			{className: 'col-6', key: 'platformName'},
			{className: 'col-6', key: 'screenWidth'},
			{className: 'col-6', key: 'browserName'},
			{className: 'col-6', key: 'devicePixelRatio'},
			{className: 'col-12', icon: 'tabs', key: 'userAgent'}
		],
		title: Liferay.Language.get('last-session-device')
	},
	{
		columnClass: 'col-3',
		items: [
			{className: 'col-12', icon: 'globe-pin', key: 'country'},
			{className: 'col-12', key: 'city'},
			{className: 'col-12', key: 'languageId'},
			{className: 'col-12', key: 'timeZoneOffset'}
		],
		title: Liferay.Language.get('last-session-location')
	},
	{
		columnClass: 'col-3',
		items: [
			{className: 'col-12', icon: 'lock', key: 'email'},
			{className: 'col-12', key: 'uuid'},
			{className: 'col-12', key: 'userId'},
			{className: 'col-12', key: 'contactId'}
		],
		title: Liferay.Language.get('individual-unique-identifiers')
	}
];

const mockedData: ContextualInfoData = {
	// contactId ?
	// userId ?
	browserName: 'Chrome',
	city: 'Sydney',
	country: 'Australia',
	devicePixelRatio: '2',
	deviceType: 'Desktop',
	languageId: 'en-Au',
	platformName: 'MacOS',
	region: 'AEST',
	screenHeight: '1920',
	screenWidth: '1080',
	timeZoneOffset: '-03:00',
	userAgent:
		'Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/'
};

type ContextualInfoData = {
	browserName?: string;
	city?: string;
	country?: string;
	contactId?: string;
	devicePixelRatio?: string;
	deviceType?: string;
	languageId?: string;
	platformName?: string;
	region?: string;
	screenHeight?: string;
	screenWidth?: string;
	timeZoneOffset?: string;
	userAgent?: string;
	userId?: string;
};

interface IContextualInfoProps {
	contextualInfo: ContextualInfoData;
	email?: string;
	uuid?: string;
}

const INFO_LANGUAGE_MAP: Record<string, string> = {
	browserName: Liferay.Language.get('browser'),
	city: Liferay.Language.get('city'),
	contactId: Liferay.Language.get('contact-id'),
	country: Liferay.Language.get('country'),
	devicePixelRatio: Liferay.Language.get('device-pixel-ratio'),
	deviceType: Liferay.Language.get('device-type'),
	email: Liferay.Language.get('email'),
	languageId: Liferay.Language.get('language'),
	platformName: Liferay.Language.get('operating-system'),
	screenHeight: Liferay.Language.get('screen-height'),
	screenWidth: Liferay.Language.get('screen-width'),
	timeZoneOffset: Liferay.Language.get('time-zone'),
	userAgent: Liferay.Language.get('user-agent'),
	userId: Liferay.Language.get('user-id'),
	uuid: 'UUID'
};

function formatTimeZoneOffset(timeZoneOffset?: string, region?: string) {
	return `${timeZoneOffset ? `UTC ${timeZoneOffset}` : ''} ${
		region ? `(${region})` : ''
	}`.trim();
}

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
	contextualInfo = mockedData,
	email = 'john.doe@liferay.com',
	uuid = '6ba7b810-9dad-11d1-80b4-00c04fd430c8'
}) => {
	const getValue = (key: string): string | undefined => {
		if (key === 'email') return email;
		if (key === 'uuid') return uuid;

		if (key === 'timeZoneOffset') {
			const {region, timeZoneOffset} = contextualInfo;
			if (!timeZoneOffset && !region) return undefined;
			return formatTimeZoneOffset(timeZoneOffset, region);
		}

		return contextualInfo[key as keyof ContextualInfoData];
	};

	return (
		<div className='contextual-info mb-4'>
			<div className='text-secondary mr-3 my-3'>
				<Icon className='mr-2' symbol='sites' />
				<span className='text-uppercase text-weight-semi-bold'>
					{Liferay.Language.get('contextual-info')}
				</span>
			</div>

			<div className='row g-3'>
				{ContextualInfoConfig.map(section => {
					const visibleItems = section.items.filter(item =>
						getValue(item.key)
					);

					if (visibleItems.length === 0) return null;

					return (
						<div
							className={section.columnClass}
							key={section.title}
						>
							<Card className='h-100 p-2 '>
								<Card.Title className='pt-2 px-2 text-uppercase'>
									<Text size={4}>{section.title}</Text>
								</Card.Title>
								<Card.Body className='pb-0 px-2'>
									<div className='g-2 row'>
										{visibleItems.map(item => (
											<InfoItem
												className={item.className}
												icon={item.icon}
												key={item.key}
												label={
													INFO_LANGUAGE_MAP[item.key]
												}
												value={
													getValue(item.key) as string
												}
											/>
										))}
									</div>
								</Card.Body>
							</Card>
						</div>
					);
				})}
			</div>
		</div>
	);
};

export default ContextualInfo;
