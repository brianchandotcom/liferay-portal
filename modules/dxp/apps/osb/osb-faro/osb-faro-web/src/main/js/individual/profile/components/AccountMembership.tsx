import React from 'react';
import {
	DataDrivenConfig,
	GeneralInfoSection
} from 'shared/components/GeneralInfoSection';
import {formatUTCDate} from 'shared/util/date';
import {Map} from 'immutable';
import {SectionHeader} from './SectionHeader';

function formatCurrency(
	currencyCode: string,
	locale: string,
	value: string
): string {
	return new Intl.NumberFormat(locale, {
		currency: currencyCode
	}).format(parseFloat(value));
}

const accountMembershipConfig: DataDrivenConfig = [
	{
		columnClass: 'col-12 col-lg-6 mb-3 mb-lg-0',
		items: [
			{
				className: 'col-12 col-md-6',
				icon: 'order-form',
				key: 'accountName'
			},
			{className: 'col-12 col-md-6', key: 'accountId'},
			{className: 'col-12 col-md-6', key: 'industry'},
			{className: 'col-12 col-md-6', key: 'currencyCode'},
			{className: 'col-12 col-md-6', key: 'accountType'},
			{className: 'col-12 col-md-6', key: 'annualRevenue'},
			{className: 'col-12 col-md-6', key: 'numberOfEmployees'}
		],
		title: Liferay.Language.get('general-account-information')
	},
	{
		columnClass: 'col-12 col-md-6 col-lg-3 mb-3 mb-md-0',
		items: [
			{className: 'col-12', icon: 'heart', key: 'customerSince'},
			{className: 'col-12', key: 'lastActivityDate'},
			{className: 'col-12', key: 'createdDate'}
		],
		title: Liferay.Language.get('account-relationship-details')
	},
	{
		columnClass: 'col-12 col-md-6 col-lg-3 mb-3 mb-md-0',
		items: [
			{className: 'col-12', icon: 'globe-pin', key: 'country'},
			{className: 'col-12', key: 'state'}
		],
		title: Liferay.Language.get('account-location')
	}
];

const dateKeys = ['createdDate', 'customerSince', 'lastActivityDate'];

interface IAccountMembershipProps {
	accountData?: Map<string, any>;
	showEmptyState?: boolean;
}

const ACCOUNT_MEMBERSHIP_LABEL_MAP: Record<string, string> = {
	accountId: 'accountId',
	accountName: Liferay.Language.get('account-name'),
	accountType: Liferay.Language.get('account-type'),
	annualRevenue: Liferay.Language.get('annual-revenue'),
	country: Liferay.Language.get('country'),
	createdDate: Liferay.Language.get('created-date'),
	currencyCode: Liferay.Language.get('currency-code'),
	customerSince: Liferay.Language.get('customer-since'),
	industry: Liferay.Language.get('industry'),
	lastActivityDate: Liferay.Language.get('last-activity-date'),
	numberOfEmployees: Liferay.Language.get('number-of-employees'),
	state: Liferay.Language.get('state')
};

const AccountMembership: React.FC<IAccountMembershipProps> = ({
	accountData,
	children: emptyState,
	showEmptyState = false
}) => {
	const getValue = (key: string): string | undefined => {
		if (dateKeys.includes(key)) {
			return accountData?.get(key)
				? formatUTCDate(accountData.get(key), 'YYYY-MM-DD')
				: undefined;
		}

		if (key === 'annualRevenue') {
			return formatCurrency(
				accountData?.get('currencyCode'),
				accountData?.get('locale'),
				accountData?.get(key)
			);
		}

		return accountData?.get(key) || undefined;
	};

	return (
		<>
			<SectionHeader
				icon='briefcase'
				title={Liferay.Language.get('account-membership')}
			/>

			{showEmptyState ? (
				emptyState
			) : (
				<GeneralInfoSection
					config={accountMembershipConfig}
					getValue={getValue}
					languageMap={ACCOUNT_MEMBERSHIP_LABEL_MAP}
				/>
			)}
		</>
	);
};

export default AccountMembership;
