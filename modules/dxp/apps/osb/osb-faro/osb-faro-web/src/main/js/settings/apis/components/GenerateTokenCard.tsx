import * as API from 'shared/api';
import Button from 'shared/components/Button';
import Card from 'shared/components/Card';
import Form from '@clayui/form';
import React, {useState} from 'react';
import Select from 'shared/components/Select';
import {EXPIRATION_DATE_LABELS, ExpirationPeriod} from 'shared/util/constants';

interface IGenerateTokenCard {
	groupId: string;
	handleError: () => void;
	handleSuccess: (message: string) => void;
	loading: boolean;
	setLoading?: (value: boolean) => void;
	token: string;
}

const expirationDates = [
	{
		key: ExpirationPeriod.In30Days,
		label: EXPIRATION_DATE_LABELS[ExpirationPeriod.In30Days]
	},
	{
		key: ExpirationPeriod.In6Months,
		label: EXPIRATION_DATE_LABELS[ExpirationPeriod.In6Months]
	},
	{
		key: ExpirationPeriod.In1Year,
		label: EXPIRATION_DATE_LABELS[ExpirationPeriod.In1Year]
	},
	{
		key: ExpirationPeriod.Indefinite,
		label: EXPIRATION_DATE_LABELS[ExpirationPeriod.Indefinite]
	}
];

const GenerateTokenCard: React.FC<IGenerateTokenCard> = ({
	groupId,
	handleError,
	handleSuccess,
	loading,
	setLoading,
	token
}) => {
	const [expiresIn, setExpiresIn] = useState(expirationDates[0].key);

	return (
		<Card>
			<Card.Body>
				<h4>{Liferay.Language.get('create-new-access-token')}</h4>
				<div className='col-md-5 pl-0 mt-2'>
					<Form.Group>
						<label htmlFor='picker' id='picker-label'>
							{Liferay.Language.get('expiration-date')}
						</label>
						<Select
							onChange={({target: {value}}) => {
								setExpiresIn(value);
							}}
							value={expiresIn}
						>
							{expirationDates.map(({key, label}) => (
								<Select.Item key={key} value={key}>
									{label}
								</Select.Item>
							))}
						</Select>
					</Form.Group>
				</div>

				{
					<Button
						className='col-md-3'
						data-testid='generate-token-button'
						display='primary'
						loading={loading}
						onClick={() => {
							setLoading(true);

							if (token) {
								API.apiTokens.revoke({
									groupId,
									token
								});
							}

							API.apiTokens
								.generate({expiresIn, groupId})
								.then(() => {
									analytics.track('Created API Token');

									handleSuccess(
										Liferay.Language.get(
											'new-token-was-generated'
										)
									);
								})
								.catch(handleError);
						}}
					>
						{Liferay.Language.get('generate-token')}
					</Button>
				}
			</Card.Body>
		</Card>
	);
};

export default GenerateTokenCard;
