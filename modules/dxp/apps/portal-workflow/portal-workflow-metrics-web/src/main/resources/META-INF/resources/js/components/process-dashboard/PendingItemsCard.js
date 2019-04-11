import React, { Fragment } from 'react';
import { AppContext } from '../AppContext';
import { getPercentage } from '../../shared/util/util';
import Icon from '../../shared/components/Icon';
import LoadingState from '../../shared/components/loading/LoadingState';
import { openErrorToast } from '../../shared/util/toast';
import Panel from '../../shared/components/Panel';
import PANELS from './Panels';
import SummaryCard from './SummaryCard';
import Tooltip from '../../shared/components/Tooltip';

class PendingItemsCard extends React.Component {
	constructor(props) {
		super(props);

		this.state = {
			loading: false,
			process: {
				dueAfterInstanceCount: 0,
				dueInInstanceCount: 0,
				instanceCount: 0,
				onTimeInstanceCount: 0,
				overdueInstanceCount: 0
			}
		};
	}

	componentWillMount() {
		this.requestData()
			.then(data => {
				this.context.setTitle(data.title);

				this.setState({
					loading: false,
					process: data
				});
			})
			.catch(error => {
				this.setState({
					loading: false
				});

				openErrorToast(error);
			});
	}

	requestData() {
		const { client } = this.context;
		const { processId } = this.props;
		const urlRequest = `/processes/${processId}`;

		this.setState({
			loading: true
		});

		return client.get(urlRequest).then(({ data }) => data);
	}

	render() {
		const { loading, process } = this.state;

		if (loading) {
			return <LoadingState />;
		}

		return (
			<Panel>
				<Panel.Header elementClasses={'dashboard-panel-header'}>
					<div>
						<span className={'mr-3'}>
							{Liferay.Language.get('pending-items')}
						</span>
						<Tooltip
							message={[
								<Fragment key="tooltip">
									{Liferay.Language.get(
										'pending-items-shows-the-status-from-all-pending-items-according-to-sla-duration-time-targets'
									)}
								</Fragment>
							]}
							position={'right'}
							width={'288'}
						>
							<Icon iconName={'question-circle-full'} />
						</Tooltip>
					</div>
				</Panel.Header>
				<Panel.Body>
					<div className={'pt-1 pb-4 d-flex'}>
						{PANELS.map(
							(
								{ addressedToField, iconColor, iconName, title, totalField },
								index
							) => (
								<SummaryCard
									iconColor={iconColor}
									iconName={iconName}
									key={`${index}_${addressedToField}`}
									percentage={
										totalField
											? `${getPercentage(
												process[addressedToField],
												process[totalField]
											  )}%`
											: false
									}
									title={title}
									total={process[addressedToField]}
								/>
							)
						)}
					</div>
				</Panel.Body>
			</Panel>
		);
	}
}

PendingItemsCard.contextType = AppContext;
export default PendingItemsCard;