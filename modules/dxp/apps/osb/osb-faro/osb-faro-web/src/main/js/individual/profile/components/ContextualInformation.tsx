import React from 'react';
import {
	DataDrivenConfig,
	GeneralInfoSection
} from 'shared/components/GeneralInfoSection';
import {Map} from 'immutable';
import {SectionHeader} from './SectionHeader';

const contextualInfoConfig: DataDrivenConfig = [
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

function formatTimeZoneOffset(timeZoneOffset?: string, region?: string) {
	return `${timeZoneOffset ? `UTC ${timeZoneOffset}` : ''} ${
		region ? `(${region})` : ''
	}`.trim();
}

interface IContextualInfoProps {
	contextData: Map<string, any>;
	email?: string;
	showEmptyState?: boolean;
	uuid?: string;
}

const INFO_LANGUAGE_MAP: Record<string, string> = {
	browserName: Liferay.Language.get('browser'),
	city: Liferay.Language.get('city'),
	contactId: 'contactId',
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
	userId: 'userId',
	uuid: 'UUID'
};

const ContextualInformation: React.FC<IContextualInfoProps> = ({
	children: emptyState,
	contextData,
	email,
	showEmptyState = false,
	uuid
}) => {
	const getValue = (key: string): string | undefined => {
		if (key === 'email') return email;
		if (key === 'uuid') return uuid;

		if (key === 'timeZoneOffset') {
			const region = contextData?.get('region');
			const timeZoneOffset = contextData?.get('timeZoneOffset');

			if (timeZoneOffset || region) {
				return formatTimeZoneOffset(timeZoneOffset, region);
			}
			return undefined;
		}

		return contextData?.get(key) || undefined;
	};

	return (
		<>
			<SectionHeader
				icon='sites'
				title={Liferay.Language.get('contextual-information')}
			/>

			{showEmptyState ? (
				emptyState
			) : (
				<GeneralInfoSection
					config={contextualInfoConfig}
					getValue={getValue}
					languageMap={INFO_LANGUAGE_MAP}
				/>
			)}
		</>
	);
};

export default ContextualInformation;
