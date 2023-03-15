import * as breadcrumbs from 'shared/util/breadcrumbs';
import BaseFieldMappingView from 'settings/components/data-source/BaseFieldMappingView';
import omitDefinedProps from 'shared/util/omitDefinedProps';
import React from 'react';
import {compose, withCurrentUser, withDataSource} from 'shared/hoc';
import {DataSource, User} from 'shared/util/records';
import {FieldContexts} from 'shared/util/constants';
import {PropTypes} from 'prop-types';

export class AccountFieldMapping extends React.Component {
	static propTypes = {
		currentUser: PropTypes.instanceOf(User).isRequired,
		dataSource: PropTypes.instanceOf(DataSource).isRequired,
		groupId: PropTypes.string.isRequired,
		id: PropTypes.string.isRequired
	};

	render() {
		const {
			currentUser,
			dataSource,
			groupId,
			id,
			...otherProps
		} = this.props;

		return (
			<BaseFieldMappingView
				{...omitDefinedProps(otherProps, AccountFieldMapping.propTypes)}
				breadcrumbItems={[
					breadcrumbs.getDataSources({groupId}),
					breadcrumbs.getDataSourceName({
						groupId,
						id,
						label: dataSource.name
					}),
					{
						active: true,
						label: Liferay.Language.get('account-field-mapping')
					}
				]}
				context={FieldContexts.Organization}
				currentUser={currentUser}
				dataSource={dataSource}
				details={[
					Liferay.Language.get(
						'all-fields-have-been-mapped-automatically-to-the-best-possible-match'
					),
					Liferay.Language.get(
						'please-review-the-data-mapping-below-to-see-how-your-salesforce-object-has-been-mapped-to-analytics-cloud'
					)
				].join(' ')}
				groupId={groupId}
				id={id}
				pageTitle={Liferay.Language.get('configure-salesforce')}
				title={Liferay.Language.get('account-field-mapping')}
			/>
		);
	}
}

export default compose(withCurrentUser, withDataSource)(AccountFieldMapping);
