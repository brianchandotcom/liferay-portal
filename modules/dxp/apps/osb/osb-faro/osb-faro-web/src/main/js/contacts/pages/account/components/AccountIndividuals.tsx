import Card from 'shared/components/Card';
import classNames from 'classnames';
import React from 'react';
import {columns, pagination} from 'shared/util/frontend-data-set';
import {Routes} from 'shared/util/router';
import {Text} from '@clayui/core';
import {useFrontendDataSet} from 'shared/hooks/useFrontendDataSet';
import {useParams} from 'react-router-dom';

const FDS_ID = 'account-individuals-dataset';

interface IAccountIndividualsProps {
	className?: string;
}

const AccountIndividuals: React.FC<IAccountIndividualsProps> = ({
	className
}) => {
	const {channelId, groupId, id} = useParams<{
		channelId: string;
		groupId: string;
		id: string;
	}>();
	const FrontendDataSet = useFrontendDataSet();

	if (!FrontendDataSet) {
		return null;
	}

	return (
		<Card className={classNames(className)}>
			<Card.Title className='mt-3 mx-3'>
				<Text size={4} weight='semi-bold'>
					<span className='text-uppercase'>
						{Liferay.Language.get('account-individuals')}
					</span>
				</Text>
			</Card.Title>
			<Card.Body noPadding>
				<div className='mt-1 mx-3'>
					<Text color='secondary' size={3}>
						{Liferay.Language.get(
							'lists-all-individuals-associated-with-this-account'
						)}
					</Text>
				</div>
				<div className='mt-3'>
					<FrontendDataSet
						apiURL={`/o/faro/contacts/${groupId}/account/${id}/individuals`}
						configInURLBehavior='off'
						customDataRenderers={{
							department: ({
								itemData
							}: {
								itemData: {properties?: {department?: string}};
							}) => itemData.properties?.department ?? '',
							individualNameRenderer: ({
								itemData,
								value
							}: {
								itemData: {id: string | number};
								value: string;
							}) =>
								columns.nameAndLinkRenderer({
									channelId,
									groupId,
									itemData,
									route: Routes.CONTACTS_INDIVIDUAL,
									value
								}),
							jobTitle: ({
								itemData
							}: {
								itemData: {properties?: {jobTitle?: string}};
							}) => itemData.properties?.jobTitle ?? '',
							lastActiveRenderer: ({value}: {value: string}) =>
								columns.dateRenderer({itemData: {}, value})
						}}
						id={FDS_ID}
						pagination={pagination}
						showPagination
						views={[
							{
								contentRenderer: 'table',
								default: true,
								label: Liferay.Language.get('default-view'),
								name: 'table',
								schema: {
									fields: [
										{
											_key: 'individualName',
											contentRenderer:
												'individualNameRenderer',
											fieldName: 'name',
											label: Liferay.Language.get(
												'individual-name'
											),
											sortable: true
										},
										{
											_key: 'department',
											contentRenderer: 'department',
											fieldName: 'department',
											label: Liferay.Language.get(
												'department'
											),
											sortable: true
										},
										{
											_key: 'jobTitle',
											contentRenderer: 'jobTitle',
											fieldName: 'jobTitle',
											label: Liferay.Language.get(
												'job-title'
											),
											sortable: true
										},
										{
											_key: 'lastActive',
											contentRenderer:
												'lastActiveRenderer',
											fieldName: 'lastActivityDate',
											label: Liferay.Language.get(
												'last-active'
											),
											sortable: true
										}
									]
								},
								thumbnail: 'table'
							}
						]}
					/>
				</div>
			</Card.Body>
		</Card>
	);
};

export default AccountIndividuals;
