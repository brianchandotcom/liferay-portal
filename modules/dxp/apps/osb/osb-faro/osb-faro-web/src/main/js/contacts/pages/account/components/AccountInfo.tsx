import AccountDetailsModal from './AccountDetailsModal';
import Button from '@clayui/button';
import Card from 'shared/components/Card';
import classNames from 'classnames';
import ClayLink from '@clayui/link';
import React, {useState} from 'react';
import {SectionHeader} from 'shared/components/SectionHeader';
import {Text} from '@clayui/core';
import {toThousands} from 'shared/util/numbers';
import {useParams} from 'react-router-dom';

export interface IAccount {
	accountName?: string;
	accountType?: string;
	annualRevenue?: number;
	fields?: Array<{
		dataSourceId?: string;
		dataSourceName?: string;
		name: string;
		value?: string;
	}>;
	industry?: string;
	numberOfEmployees?: number;
}

interface IAccountInfoProps {
	account?: IAccount;
	className?: string;
}

const infoDataLabels = {
	accountName: Liferay.Language.get('account-name'),
	accountType: Liferay.Language.get('account-type'),
	annualRevenue: Liferay.Language.get('annual-revenue'),
	industry: Liferay.Language.get('industry'),
	numberOfEmployees: Liferay.Language.get('company-size'),
	website: Liferay.Language.get('website')
};

const normalizeHref = (value: string) =>
	/^https?:\/\//i.test(value) ? value : `https://${value}`;

const infoItem = (label: string, value?: string, link?: boolean) => (
	<div className='d-flex justify-content-between mb-2'>
		<Text color='secondary' size={3}>
			{label}
		</Text>
		{link && value ? (
			<ClayLink href={normalizeHref(value)} target='_blank'>
				<Text size={3}>{value}</Text>
			</ClayLink>
		) : (
			<Text color='secondary' size={3}>
				{value}
			</Text>
		)}
	</div>
);

const AccountInfo: React.FC<IAccountInfoProps> = ({account, className}) => {
	const [isDetailsModalOpen, setIsDetailsModalOpen] = useState(false);
	const {id: accountId} = useParams<{id: string}>();

	const getValue = (key: keyof typeof infoDataLabels): string | undefined => {
		if (!account) {
			return undefined;
		}
		if (key === 'annualRevenue') {
			return account.annualRevenue !== undefined
				? toThousands(account.annualRevenue)
				: undefined;
		}
		if (key === 'numberOfEmployees') {
			return account.numberOfEmployees !== undefined
				? toThousands(account.numberOfEmployees)
				: undefined;
		}
		if (key === 'website') {
			return account.fields?.find(field => field.name === 'website')
				?.value;
		}
		return account[key];
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

			{isDetailsModalOpen && accountId && (
				<AccountDetailsModal
					accountId={accountId}
					accountName={account?.accountName}
					onClose={() => setIsDetailsModalOpen(false)}
				/>
			)}
		</>
	);
};

export default AccountInfo;
