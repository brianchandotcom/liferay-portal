import * as API from 'shared/api';
import Card from 'shared/components/Card';
import ClayButton from '@clayui/button';
import Form from '@clayui/form';
import Loading, {Align} from 'shared/components/Loading';
import React, {useState} from 'react';
import Select from 'shared/components/Select';
import {EXPIRATION_DATE_LABELS, ExpirationPeriod} from 'shared/util/constants';

interface IGenerateTokenCardProps {
	groupId: string;
	onError: () => void;
	onSuccess: (message: string) => void;
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

const GenerateTokenCard: React.FC<IGenerateTokenCardProps> = ({
	groupId,
	onError,
	onSuccess,
	token
}) => {
	const [loading, setLoading] = useState(false);
	const [expiresIn, setExpiresIn] = useState(expirationDates[0].key);

	return (
		<Card>
			<Card.Body>
				<h4>{Liferay.Language.get('create-new-access-token')}</h4>
				<div className='col-md-5 mt-2 pl-0'>
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

				<ClayButton
					className='button-root col-md-3'
					data-testid='generate-token-button'
					disabled={loading}
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

								setLoading(false);
								onSuccess(
									Liferay.Language.get(
										'new-token-was-generated'
									)
								);
							})
							.catch(() => {
								setLoading(false);
								onError();
							});
					}}
				>
					{Liferay.Language.get('generate-token')}

					{loading && <Loading align={Align.Right} />}
				</ClayButton>
			</Card.Body>
		</Card>
	);
};

export default GenerateTokenCard;
