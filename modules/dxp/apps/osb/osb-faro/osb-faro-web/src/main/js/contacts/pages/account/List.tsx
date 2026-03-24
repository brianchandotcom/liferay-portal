import * as breadcrumbs from 'shared/util/breadcrumbs';
import BasePage from 'shared/components/base-page';
import Card from 'shared/components/Card';
import React from 'react';
import {useChannelContext} from 'shared/context/channel';
import {useCurrentUser} from 'shared/hooks/useCurrentUser';
import {useFrontendDataSet} from 'shared/hooks/useFrontendDataSet';
import {User} from 'shared/util/records';

interface IListProps {
	channelId: string;
	currentUser: User;
	groupId: string;
}

const List: React.FC<IListProps> = ({channelId, groupId, ...otherProps}) => {
	const currentUser = useCurrentUser();
	const {selectedChannel} = useChannelContext();

	const authorized = currentUser.isAdmin();

	const FrontendDataSet = useFrontendDataSet();

	return (
		<BasePage documentTitle={Liferay.Language.get('accounts')}>
			<BasePage.Header
				breadcrumbs={[
					breadcrumbs.getHome({
						channelId,
						groupId,
						label: selectedChannel && selectedChannel.name
					})
				]}
				groupId={groupId}
			>
				<BasePage.Row>
					<BasePage.Header.TitleSection
						title={Liferay.Language.get('accounts')}
					/>
				</BasePage.Row>
			</BasePage.Header>
			<BasePage.Body>
				<Card>
					{FrontendDataSet && (
						<FrontendDataSet
							// TODO => Use the correct endpoint
							// apiURL={`o/contacts/${groupId}/account/search`}
							apiURL='/o/headless-admin-user/v1.0/user-accounts'
							customDataRenderers={{
								testRenderer: ({value}) => (
									<span>
										<b>{value}</b>
									</span>
								)
							}}
							filters={[
								{
									id: 'lifecycleStatus',
									items: [
										{
											label: Liferay.Language.get(
												'established'
											),
											value: 'established'
										},
										{
											label: Liferay.Language.get(
												'pipeline'
											),
											value: 'pipeline'
										},
										{
											label: Liferay.Language.get(
												'at-risk'
											),
											value: 'at-risk'
										}
									],
									label: Liferay.Language.get('status'),
									name: 'status',
									type: 'selection'
								},
								{
									id: 'company',
									items: [
										{
											label: Liferay.Language.get(
												'liferay'
											),
											value: 'liferay'
										}
									],
									label: Liferay.Language.get('industry'),
									name: 'companyId',
									type: 'selection'
								}
							]}
							pagination={{
								deltas: [{label: 10}, {label: 20}, {label: 50}],
								initialDelta: 20,
								initialPageNumber: 1
							}}
							showPagination
							snapshotsEnabled
							sort={[
								{
									active: true,
									direction: 'asc',
									key: 'alternateName',
									label: Liferay.Language.get(
										'alternate-name'
									)
								},
								{
									active: false,
									direction: 'asc',
									key: 'emailAddress',
									label: Liferay.Language.get('email')
								}
							]}
							views={[
								{
									contentRenderer: 'table',
									default: false,
									label: 'table',
									name: 'table',
									schema: {
										fields: [
											{
												fieldName: 'alternateName',
												label: Liferay.Language.get(
													'alternate-name'
												),
												sortable: true,
												truncate: true
											},
											{
												fieldName: 'familyName',
												label: Liferay.Language.get(
													'family-name'
												),
												sortable: false
											},
											{
												contentRenderer: 'testRenderer',
												fieldName: 'id',
												label: Liferay.Language.get(
													'id'
												),
												sortable: false
											},
											{
												contentRenderer: 'testRenderer',
												fieldName: 'emailAddress',
												label: Liferay.Language.get(
													'email'
												),
												sortable: true
											}
										]
									},
									thumbnail: 'table'
								}
							]}
						/>
					)}
				</Card>
			</BasePage.Body>
		</BasePage>
	);
};

export default List;
