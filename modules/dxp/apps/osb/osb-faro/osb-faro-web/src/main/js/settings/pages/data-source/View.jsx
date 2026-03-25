import CSV from './CSV';
import DemandbaseOverview from 'settings/components/demandbase/DemandbaseOverview';
import LiferayOverview from 'settings/components/liferay/LiferayOverview';
import omitDefinedProps from 'shared/util/omitDefinedProps';
import React from 'react';
import SalesforceOverview from 'settings/components/salesforce/SalesforceOverview';
import {compose, withDataSource} from 'shared/hoc';
import {DataSource} from 'shared/util/records';
import {DataSourceTypes} from 'shared/util/constants';
import {PropTypes} from 'prop-types';
import {withRouter} from 'react-router-dom';

const PAGE_MAP = {
	[DataSourceTypes.Csv]: CSV,
	[DataSourceTypes.Demandbase]: DemandbaseOverview,
	[DataSourceTypes.Liferay]: LiferayOverview,
	[DataSourceTypes.Salesforce]: SalesforceOverview
};

const getPageComponent = dataSource => PAGE_MAP[dataSource.providerType];

export class View extends React.Component {
	static propTypes = {
		dataSource: PropTypes.instanceOf(DataSource).isRequired
	};

	render() {
		const {dataSource, ...otherProps} = this.props;

		const Page = getPageComponent(dataSource);

		if (Page) {
			return (
				<Page
					{...omitDefinedProps(otherProps, View.propTypes)}
					dataSource={dataSource}
				/>
			);
		}
	}
}

export default compose(withRouter, withDataSource)(View);
