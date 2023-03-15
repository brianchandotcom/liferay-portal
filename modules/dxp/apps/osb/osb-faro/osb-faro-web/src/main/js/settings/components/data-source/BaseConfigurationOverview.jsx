import BaseConfigurationItem, {
	getStatusMessage
} from 'settings/components/data-source/BaseConfigurationItem';
import getCN from 'classnames';
import omitDefinedProps from 'shared/util/omitDefinedProps';
import React from 'react';
import Sheet from 'shared/components/Sheet';
import {
	DataSourceProgressStatuses,
	DataSourceStatuses
} from 'shared/util/constants';
import {get} from 'lodash';
import {PropTypes} from 'prop-types';

const ConfigurationItem = ({
	buttonParams,
	configuration,
	description,
	disabled = false,
	href,
	progress,
	status,
	title
}) => {
	const current = get(progress, 'processedOperations', 0);
	const total = get(progress, 'totalOperations', 0);

	const configured = Boolean(
		configuration && status === DataSourceStatuses.Active
	);

	const inProgress = [
		DataSourceProgressStatuses.InProgress,
		DataSourceProgressStatuses.Started
	].includes(get(progress, 'status'));

	return (
		<BaseConfigurationItem
			buttonParams={{
				disabled,
				href,
				...buttonParams
			}}
			completion={current / total}
			description={description}
			showBar={inProgress}
			statusMessage={getStatusMessage({
				configured,
				current,
				dateRecorded: get(progress, 'dateRecorded'),
				total
			})}
			title={title}
		/>
	);
};

export default class BaseConfigurationOverview extends React.Component {
	static defaultProps = {
		configurationItems: []
	};

	static propTypes = {
		configurationItems: PropTypes.array
	};

	render() {
		const {className, configurationItems, ...otherProps} = this.props;

		return (
			<div className={getCN('configuration-overview-root', className)}>
				<Sheet.Subtitle>
					{Liferay.Language.get('data-configuration')}
				</Sheet.Subtitle>

				{configurationItems.map((item, i) => (
					<ConfigurationItem
						{...omitDefinedProps(
							otherProps,
							BaseConfigurationOverview.propTypes
						)}
						{...item}
						key={i}
					/>
				))}
			</div>
		);
	}
}
