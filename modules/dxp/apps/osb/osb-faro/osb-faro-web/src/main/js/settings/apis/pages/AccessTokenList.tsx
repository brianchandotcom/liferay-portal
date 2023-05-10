import * as API from 'shared/api';
import Alerts, {AlertTypes} from 'shared/components/Alert';
import BasePage from 'settings/components/BasePage';
import Card from 'shared/components/Card';
import ClayButton from '@clayui/button';
import CopyButton from 'shared/components/CopyButton';
import GenerateTokenCard from '../components/GenerateTokenCard';
import moment from 'moment';
import React, {useState} from 'react';
import Table from 'shared/components/table';
import TokenCell from '../components/TokenCell';
import URLConstants from 'shared/util/url-constants';
import {AccessToken} from '../types';
import {addAlert} from 'shared/actions/alerts';
import {Alert} from 'shared/types';
import {ApisPath} from 'shared/util/url-constants';
import {close, modalTypes, open} from 'shared/actions/modals';
import {compose} from 'redux';
import {connect, ConnectedProps} from 'react-redux';
import {ExpirationPeriod} from 'shared/util/constants';
import {formatDateToTimeZone, getDateNow} from 'shared/util/date';
import {RootState} from 'shared/store';
import {sub} from 'shared/util/lang';
import {
	withAdminPermission,
	withError,
	withLoading,
	withQuery
} from 'shared/hoc';

export const isExpired = (expirationDate: string) =>
	moment.utc(expirationDate).isSameOrBefore(getDateNow());

const DATE_FORMAT = 'MMM DD, YYYY';

const connector = connect(
	(store: RootState, {groupId}: {groupId: string}) => ({
		timeZoneId: store.getIn([
			'projects',
			groupId,
			'data',
			'timeZone',
			'timeZoneId'
		])
	}),
	{addAlert, close, open}
);

type PropsFromRedux = ConnectedProps<typeof connector>;

const TokenList: React.FC<
	{
		groupId: string;
		refetch: () => Promise<any>;
		tokens: AccessToken[];
	} & PropsFromRedux
> = ({addAlert, close, groupId, open, refetch, timeZoneId, tokens}) => {
	const [loading, setLoading] = useState(false);
	const [onCloseAlert, setOnCloseAlert] = useState(false);

	const tokenExpired =
		isExpired(tokens[0]?.expirationDate) && !!tokens.length;

	const displayAddExpirationDateCard = tokenExpired || !tokens.length;

	const handleError = () => {
		setLoading(false);

		addAlert({
			alertType: Alert.Types.Error,
			message: Liferay.Language.get('error'),
			timeout: false
		});
	};

	const handleSuccess = message => {
		addAlert({
			alertType: Alert.Types.Success,
			message
		});

		setLoading(false);

		refetch();
	};

	return (
		<div className='col-xl-8 pl-0'>
			{tokenExpired && !onCloseAlert && (
				<Alerts
					iconSymbol='warning-full'
					onClose={() => setOnCloseAlert(true)}
					title={Liferay.Language.get('warning')}
					type={AlertTypes.Warning}
				>
					{Liferay.Language.get('expired-token-warning')}
				</Alerts>
			)}

			{displayAddExpirationDateCard && (
				<GenerateTokenCard
					groupId={groupId}
					handleError={handleError}
					handleSuccess={handleSuccess}
					loading={loading}
					setLoading={setLoading}
					token={tokens[0]?.token}
				/>
			)}
			<Card>
				<Card.Body>
					<div className='d-flex flex-column justify-content-between align-items-start'>
						<h4 className='mb-4'>
							{Liferay.Language.get('token-information')}
						</h4>

						<h5>{Liferay.Language.get('root-endpoint')}</h5>

						<span className='text-secondary'>
							{window.location.origin + ApisPath}
						</span>
					</div>
				</Card.Body>

				{!!tokens.length && (
					<Table
						className='mb-0'
						columns={[
							{
								accessor: 'token',
								cellRenderer: TokenCell,
								label: Liferay.Language.get('token'),
								sortable: false
							},
							{
								accessor: 'lastAccessDate',
								dataFormatter: (val: string) =>
									formatDateToTimeZone(
										val,
										DATE_FORMAT,
										timeZoneId
									),
								label: Liferay.Language.get('last-seen'),
								sortable: false
							},
							{
								accessor: 'createDate',
								dataFormatter: (val: string) =>
									formatDateToTimeZone(
										val,
										DATE_FORMAT,
										timeZoneId
									),
								label: Liferay.Language.get('date-created'),
								sortable: false
							},
							{
								accessor: 'expirationDate',
								cellRenderer: ({data}) => {
									const isIndefinite =
										Math.floor(
											new Date(
												data.expirationDate
											).getTime() / 1000
										) -
										Math.floor(
											new Date(
												data.createDate
											).getTime() / 1000
										);

									if (
										isIndefinite ===
										Number(ExpirationPeriod.Indefinite)
									) {
										return (
											<td>
												{Liferay.Language.get(
													'indefinite'
												)}
											</td>
										);
									}
									return (
										<td>
											{formatDateToTimeZone(
												data.expirationDate,
												'll',
												timeZoneId
											)}
										</td>
									);
								},

								label: Liferay.Language.get('expiration'),
								sortable: false
							}
						]}
						items={tokens}
						renderInlineRowActions={({data: {token}}) => {
							if (!tokenExpired) {
								return (
									<>
										<CopyButton text={token} />

										<ClayButton
											className='button-root'
											onClick={() => {
												open(
													modalTypes.CONFIRMATION_MODAL,
													{
														message: (
															<div className='text-secondary'>
																<div>
																	<strong>
																		{Liferay.Language.get(
																			'are-you-sure-you-want-to-revoke-this-token'
																		)}
																	</strong>
																</div>

																{Liferay.Language.get(
																	'you-will-need-to-generate-a-new-token-to-continue-using-this-api'
																)}
															</div>
														),
														modalVariant:
															'modal-warning',
														onClose: close,
														onSubmit: () => {
															setLoading(true);

															API.apiTokens
																.revoke({
																	groupId,
																	token
																})
																.then(() =>
																	handleSuccess(
																		Liferay.Language.get(
																			'token-successfully-revoked'
																		)
																	)
																)
																.catch(
																	handleError
																);
														},
														submitButtonDisplay:
															'warning',
														title: Liferay.Language.get(
															'revoke-token'
														),
														titleIcon:
															'warning-full'
													}
												);
											}}
										>
											{Liferay.Language.get('revoke')}
										</ClayButton>
									</>
								);
							}
						}}
						rowIdentifier='token'
					/>
				)}
			</Card>
		</div>
	);
};

const ListWithData = compose<any>(
	connector,
	withQuery(
		API.apiTokens.search,
		({groupId}: {groupId: string}) => ({groupId}),
		({data, ...otherParams}) => ({
			tokens: data,
			...otherParams
		})
	),
	withLoading({page: false}),
	withError({page: false})
)(TokenList);

interface IAccessTokenListProps {
	groupId: string;
}

export const AccessTokenList: React.FC<IAccessTokenListProps> = ({groupId}) => (
	<BasePage
		className='access-token-list-root'
		groupId={groupId}
		pageDescription={sub(
			Liferay.Language.get(
				'access-this-workspaces-data-via-api-using-an-access-token.-a-full-list-of-endpoints-is-available-in-the-x'
			),
			[
				<a
					href={URLConstants.APIOverviewDocumentationLink}
					key='API_OVERVIEW_DOCUMENTATION'
					target='_blank'
				>
					{Liferay.Language.get('documentation-fragment')}
				</a>
			],
			false
		)}
		pageTitle={Liferay.Language.get('access-tokens')}
	>
		<ListWithData groupId={groupId} />
	</BasePage>
);

export default withAdminPermission(AccessTokenList);
