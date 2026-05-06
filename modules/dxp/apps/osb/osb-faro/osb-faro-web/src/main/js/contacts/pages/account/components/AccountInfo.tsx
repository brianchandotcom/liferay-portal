import AccountDetailsModal from './AccountDetailsModal';
import Button from '@clayui/button';
import Card from 'shared/components/Card';
import classNames from 'classnames';
import React, {useState} from 'react';
import {SectionHeader} from 'shared/components/SectionHeader';
import {Text} from '@clayui/core';
import {toThousands} from 'shared/util/numbers';

interface LifecycleStatusProps {
	className?: string;
}

const AccountInfoData: {
	accountName?: string;
	accountType?: string;
	annualRevenue?: number;
	numberOfEmployees?: number;
	id: string;
	industry?: string;
	fields: [
		{
			name: string;
			dataSourceId?: string;
			dataSourceName?: string;
			value?: string;
		}
	];
} = {
	accountName: 'Acme Corp',
	accountType: 'Customer',
	annualRevenue: 5000000,
	fields: [
		{
			dataSourceName: 'Salesforce Production',
			name: 'website',
			value: 'https://acme.com'
		}
	],
	id: 'acc-123',
	industry: 'Technology',
	numberOfEmployees: 250
};

const infoDataLabels = {
	accountName: Liferay.Language.get('account-name'),
	accountType: Liferay.Language.get('account-type'),
	annualRevenue: Liferay.Language.get('annual-revenue'),
	industry: Liferay.Language.get('industry'),
	numberOfEmployees: Liferay.Language.get('company-size'),
	website: Liferay.Language.get('website')
};

const infoItem = (label: string, value?: string, link?: boolean) => (
	<div className='d-flex justify-content-between mb-2'>
		<Text color='secondary' size={3}>
			{label}
		</Text>
		{link ? (
			<a href={value} rel='noopener noreferrer' target='_blank'>
				<Text size={3}>{value}</Text>
			</a>
		) : (
			<Text color='secondary' size={3}>
				{value}
			</Text>
		)}
	</div>
);

const AccountInfo: React.FC<LifecycleStatusProps> = ({className}) => {
	const [isDetailsModalOpen, setIsDetailsModalOpen] = useState(false);

	const getValue = (key: keyof typeof infoDataLabels): string | undefined => {
		if (key === 'annualRevenue') {
			return AccountInfoData.annualRevenue !== undefined
				? toThousands(AccountInfoData.annualRevenue)
				: undefined;
		}
		if (key === 'numberOfEmployees') {
			return AccountInfoData.numberOfEmployees !== undefined
				? toThousands(AccountInfoData.numberOfEmployees)
				: undefined;
		}
		if (key === 'website') {
			return AccountInfoData.fields.find(
				field => field.name === 'website'
			)?.value;
		}
		return AccountInfoData[key];
	};

	return (
		<>
			<SectionHeader
				icon='plus-squares'
				title={Liferay.Language.get('account-details')}
			/>
			<Card className={classNames(className, 'p-3')}>
				<Card.Title>
					<Text size={4} weight='semi-bold'>
						<span className='text-uppercase'>
							{Liferay.Language.get(
								'general-account-information'
							)}
						</span>
					</Text>
				</Card.Title>
				<Card.Body className='px-0 pb-0'>
					{(
						Object.keys(infoDataLabels) as Array<
							keyof typeof infoDataLabels
						>
					).map(key => (
						<React.Fragment key={key}>
							{infoItem(
								infoDataLabels[key],
								getValue(key),
								key === 'website'
							)}
						</React.Fragment>
					))}
					<Button
						borderless
						className='ml-auto rounded-lg'
						onClick={() => setIsDetailsModalOpen(true)}
						size='sm'
					>
						{Liferay.Language.get('view-all')}
					</Button>
				</Card.Body>
			</Card>

			{isDetailsModalOpen && (
				<AccountDetailsModal
					accountId={AccountInfoData.id}
					accountName={AccountInfoData.accountName}
					onClose={() => setIsDetailsModalOpen(false)}
				/>
			)}
		</>
	);
};

export default AccountInfo;
