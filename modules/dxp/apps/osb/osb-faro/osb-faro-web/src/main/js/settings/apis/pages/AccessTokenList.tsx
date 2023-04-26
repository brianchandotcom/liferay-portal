import * as API from 'shared/api';
import BasePage from 'settings/components/BasePage';
import Card from 'shared/components/Card';
import ClayButton from '@clayui/button';
import ClayLoadingIndicator from '@clayui/loading-indicator';
import CopyButton from 'shared/components/CopyButton';
import moment from 'moment';
import Promise from 'metal-promise';
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
		<Card>
			<Card.Body>
				<div className='d-flex justify-content-between align-items-center'>
					<div className='text-secondary'>
						<strong className='font-weight-bold'>
							{Liferay.Language.get('root-endpoint')}
						</strong>

						<span className='ml-1'>
							{window.location.origin + ApisPath}
						</span>
					</div>

					{!tokens.length && (
						<ClayButton
							className='button-root'
							data-testid='generate-token-button'
							displayType='primary'
							onClick={() => {
								setLoading(true);

								API.apiTokens
									.generate({groupId})
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
							{loading && (
								<ClayLoadingIndicator
									className='d-inline-block mr-2'
									displayType='secondary'
									size='sm'
								/>
							)}

							{Liferay.Language.get('generate-token')}
						</ClayButton>
					)}
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
							dataFormatter: (val: string) =>
								formatDateToTimeZone(val, 'll', timeZoneId),
							label: Liferay.Language.get('expiration'),
							sortable: false
						}
					]}
					items={tokens}
					renderInlineRowActions={({
						data: {expirationDate, token}
					}) => {
						const expired = isExpired(expirationDate);

						return expired ? (
							<ClayButton
								className='button-root'
								onClick={() => {
									setLoading(true);

									API.apiTokens
										.revoke({groupId, token})
										.then(() =>
											API.apiTokens.generate({groupId})
										)
										.then(() =>
											handleSuccess(
												Liferay.Language.get(
													'new-token-was-generated'
												)
											)
										)
										.catch(handleError);
								}}
							>
								{Liferay.Language.get('generate-token')}
							</ClayButton>
						) : (
							<>
								<CopyButton
									displayType='secondary'
									text={token}
								/>

								<ClayButton
									className='button-root'
									displayType='secondary'
									onClick={() => {
										open(modalTypes.CONFIRMATION_MODAL, {
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
											modalVariant: 'modal-warning',
											onClose: close,
											onSubmit: () => {
												setLoading(true);

												API.apiTokens
													.revoke({groupId, token})
													.then(() =>
														handleSuccess(
															Liferay.Language.get(
																'token-successfully-revoked'
															)
														)
													)
													.catch(handleError);
											},
											submitButtonDisplay: 'warning',
											title: Liferay.Language.get(
												'revoke-token'
											),
											titleIcon: 'warning-full'
										});
									}}
								>
									{Liferay.Language.get('revoke')}
								</ClayButton>
							</>
						);
					}}
					rowIdentifier='token'
				/>
			)}
		</Card>
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
