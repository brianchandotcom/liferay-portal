import BaseDataSourcePage from './BasePage';
import ClayBadge from '@clayui/badge';
import Nav from 'shared/components/Nav';
import omitDefinedProps from 'shared/util/omitDefinedProps';
import React from 'react';
import Sheet from 'shared/components/Sheet';
import {DataSource} from 'shared/util/records';
import {DataSourceTypes} from 'shared/util/constants';
import {isDataSourceValid} from 'shared/util/data-sources';
import {Map} from 'immutable';
import {PropTypes} from 'prop-types';
import {Routes, toRoute} from 'shared/util/router';

export const tabIds = {
	AUTHORIZATION: 'authorization',
	CONFIGURE_DATA_SOURCE: 'configureDataSource'
};

const CONFIGURATIONS_MAP = {
	[DataSourceTypes.Liferay]: [
		'analyticsConfiguration',
		'contactsConfiguration'
	],
	[DataSourceTypes.Salesforce]: [
		'accountsConfiguration',
		'contactsConfiguration'
	]
};

const getUnconfiguredCount = configurations =>
	configurations.filter(config => !config).length;

const getPendingConfigurationSticker = provider => {
	const type = provider.get('type');

	const unconfiguredCount = getUnconfiguredCount(
		CONFIGURATIONS_MAP[type].map(val => provider.get(val))
	);

	return unconfiguredCount ? (
		<ClayBadge className='ml-2' label={unconfiguredCount} />
	) : null;
};

export default class BaseTabsPage extends React.Component {
	static defaultProps = {
		activeTabId: tabIds.AUTHORIZATION,
		addRoute: Routes.SETTINGS_DATA_SOURCE,
		configurationRoute: Routes.SETTINGS_DATA_SOURCE
	};

	static propTypes = {
		activeTabId: PropTypes.oneOf([
			tabIds.AUTHORIZATION,
			tabIds.CONFIGURE_DATA_SOURCE
		]),
		addRoute: PropTypes.string,
		configurationRoute: PropTypes.string,
		dataSource: PropTypes.instanceOf(DataSource),
		groupId: PropTypes.string.isRequired,
		id: PropTypes.string
	};

	render() {
		const {
			activeTabId,
			addRoute,
			children,
			configurationRoute,
			dataSource,
			groupId,
			id,
			...otherProps
		} = this.props;

		const routeOptions = id ? {groupId, id} : {groupId};

		return (
			<BaseDataSourcePage
				{...omitDefinedProps(otherProps, BaseTabsPage.propTypes)}
				dataSource={dataSource}
				groupId={groupId}
				id={id}
			>
				<Sheet className='data-source-base-tabs-root' pageDisplay>
					<Sheet.Body>
						<Nav display='underline'>
							<Nav.Item
								active={activeTabId === tabIds.AUTHORIZATION}
								href={
									id
										? toRoute(
												Routes.SETTINGS_DATA_SOURCE,
												routeOptions
										  )
										: toRoute(addRoute, routeOptions)
								}
							>
								<h4>{Liferay.Language.get('authorization')}</h4>
							</Nav.Item>

							<Nav.Item
								active={
									activeTabId === tabIds.CONFIGURE_DATA_SOURCE
								}
								disabled={
									!id || !isDataSourceValid(dataSource.state)
								}
								href={
									id
										? toRoute(
												configurationRoute,
												routeOptions
										  )
										: '#'
								}
							>
								<h4>
									{Liferay.Language.get(
										'configure-data-source'
									)}
								</h4>

								{!!id &&
									getPendingConfigurationSticker(
										dataSource.provider || new Map()
									)}
							</Nav.Item>
						</Nav>
					</Sheet.Body>

					{children}
				</Sheet>
			</BaseDataSourcePage>
		);
	}
}
