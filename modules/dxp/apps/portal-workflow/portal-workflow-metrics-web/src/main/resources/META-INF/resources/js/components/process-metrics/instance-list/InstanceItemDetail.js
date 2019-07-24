import {AppContext} from '../../AppContext';
import Icon from '../../../shared/components/Icon';
import moment from '../../../shared/util/moment';
import React from 'react';

class InstanceItemDetail extends React.Component {
	constructor(props) {
		super(props);
		this.state = {};
	}

	componentWillReceiveProps(nextProps) {
		const {instanceId, processId} = nextProps;

		if (instanceId) {
			this.fetchData(instanceId, processId).then(data =>
				this.setState(data)
			);
		}
	}

	fetchData(instanceId, processId) {
		return this.context.client
			.get(`/processes/${processId}/instances/${instanceId}`)
			.then(({data}) => data);
	}

	render() {
		const {instanceId} = this.props;
		const {
			assetTitle,
			assetType,
			dateCompletion,
			dateCreated,
			slaResults = [],
			slaStatus,
			status,
			taskNames = [],
			userName
		} = this.state;

		const slaResolved = slaResults.filter(
			({status}) => status === 'Stopped'
		);
		const slaOpen = slaResults.filter(({status}) =>
			['Running', 'Paused'].includes(status)
		);

		let styleName = 'text-danger';

		const completed = status === 'Completed';
		const empty = slaResults.length === 0;
		const overdue = slaStatus === 'Overdue';

		if (status === 'Pending' && slaStatus === 'OnTime') {
			styleName = 'text-success';
		} else if (status === 'Completed') {
			styleName = 'text-secondary';
		} else if (empty) {
			styleName = 'text-info';
		}

		let iconTitleName = 'check-circle';

		if (empty) {
			iconTitleName = 'hr';
		} else if (overdue) {
			iconTitleName = 'exclamation-circle';
		}

		return (
			<div
				aria-labelledby="instanceDetailModalLabel"
				className="fade modal"
				id="instanceDetailModal"
				role="dialog"
				style={{display: 'none'}}
				tabIndex="-1"
			>
				<div className="modal-dialog modal-lg">
					<div className="modal-content">
						<div className="modal-header">
							<div
								className="font-weight-medium modal-title"
								id="instanceDetailModalLabel"
							>
								<span
									className={`modal-title-indicator ${styleName}`}
								>
									<Icon iconName={iconTitleName} />
								</span>

								{`${Liferay.Language.get(
									'item'
								)} #${instanceId}`}
							</div>
							<button
								aria-labelledby="Close"
								className="close"
								data-dismiss="modal"
								role="button"
								type="button"
							>
								<Icon iconName="times" />
							</button>
						</div>
						<div className="modal-body">
							<InstanceItemDetail.SectionTitle>
								{Liferay.Language.get('due-date-by-sla')}
							</InstanceItemDetail.SectionTitle>

							{slaResults.length === 0 && (
								<p>
									<span className="font-weight-medium text-muted">
										{Liferay.Language.get(
											'no-sla-records-for-this-item'
										)}
									</span>
								</p>
							)}

							{!!slaOpen.length && (
								<InstanceItemDetail.SectionSubTitle>
									{`${Liferay.Language.get(
										'open'
									).toUpperCase()} (${slaOpen.length})`}
								</InstanceItemDetail.SectionSubTitle>
							)}

							{slaOpen.map(item => (
								<InstanceItemDetail.Item
									key={item.id}
									{...item}
								/>
							))}

							{!!slaResolved.length && (
								<InstanceItemDetail.SectionSubTitle>
									{`${Liferay.Language.get(
										'resolved'
									).toUpperCase()} (${slaResolved.length})`}
								</InstanceItemDetail.SectionSubTitle>
							)}

							{slaResolved.map(item => (
								<InstanceItemDetail.Item
									key={item.id}
									{...item}
								/>
							))}

							<InstanceItemDetail.SectionTitle className="mt-5">
								{Liferay.Language.get('process-details')}
							</InstanceItemDetail.SectionTitle>

							<InstanceItemDetail.SectionAttribute
								description={Liferay.Language.get(
									'process-status'
								)}
								detail={status}
							/>

							<InstanceItemDetail.SectionAttribute
								description={Liferay.Language.get('created-by')}
								detail={userName}
							/>

							{!!dateCreated && (
								<InstanceItemDetail.SectionAttribute
									description={Liferay.Language.get(
										'creation-date'
									)}
									detail={moment
										.utc(dateCreated)
										.format(
											Liferay.Language.get('mmm-dd-lt')
										)}
								/>
							)}

							<InstanceItemDetail.SectionAttribute
								description={Liferay.Language.get('asset-type')}
								detail={assetType}
							/>

							<InstanceItemDetail.SectionAttribute
								description={Liferay.Language.get(
									'asset-title'
								)}
								detail={assetTitle}
							/>

							{!completed && (
								<InstanceItemDetail.SectionAttribute
									description={Liferay.Language.get(
										'current-step'
									)}
									detail={taskNames.join(', ')}
								/>
							)}

							{completed && !!dateCompletion && (
								<InstanceItemDetail.SectionAttribute
									description={Liferay.Language.get(
										'end-date'
									)}
									detail={moment
										.utc(dateCompletion)
										.format(
											Liferay.Language.get('mmm-dd-lt')
										)}
								/>
							)}

							<a
								className="btn btn-secondary btn-sm mb-1 font-weight-medium mt-3"
								href={`/group/control_panel/manage/-/workflow_instance/view/${instanceId}`}
								target="_blank"
							>
								{Liferay.Language.get('go-to-submission-page')}

								<span className="inline-item inline-item-after">
									<Icon iconName="shortcut" />
								</span>
							</a>
						</div>
					</div>
				</div>
			</div>
		);
	}
}

InstanceItemDetail.Item = ({
	dateOverdue,
	name,
	onTime,
	remainingTime,
	status
}) => {
	let bgColor = 'danger';
	let iconClassName = 'text-danger';
	let iconName = 'exclamation-circle';
	let statusText = `(${Liferay.Language.get('resolved-overdue')})`;

	if (onTime) {
		bgColor = 'success';
		iconClassName = 'text-success';
		iconName = 'check-circle';
	}

	if (status === 'Stopped' && onTime) {
		statusText = `(${Liferay.Language.get('resolved-on-time')})`;
	} else if (status === 'Running') {
		const remainingTimePositive = onTime
			? remainingTime
			: remainingTime * -1;

		const remainingTimeUTC = moment.utc(remainingTimePositive);

		const durationText =
			remainingTimeUTC.format('D') -
			1 +
			remainingTimeUTC.format('[d] HH[h] mm[min]');

		let onTimeText = Liferay.Language.get('overdue');

		if (onTime) {
			onTimeText = Liferay.Language.get('left');
		}
		statusText = `${moment
			.utc(dateOverdue)
			.format(
				Liferay.Language.get('mmm-dd-lt')
			)} (${durationText} ${onTimeText})`;
	} else if (status === 'Paused') {
		statusText = `(${Liferay.Language.get('sla-paused')})`;
	}

	return (
		<p>
			<span
				className={`bg-${bgColor}-light sticker`}
				style={{height: '1.5rem', width: '1.5rem'}}
			>
				<span className="inline-item" style={{fontSize: '10px'}}>
					<Icon elementClasses={iconClassName} iconName={iconName} />
				</span>
			</span>

			<span className="font-weight-medium small text-secondary">{` ${name} `}</span>

			<span className="small">{statusText}</span>
		</p>
	);
};

InstanceItemDetail.SectionTitle = ({children, className = ''}) => {
	const classNames = `${className} font-weight-medium mb-4`;

	return <h4 className={classNames}>{children}</h4>;
};

InstanceItemDetail.SectionSubTitle = ({children}) => {
	return (
		<h5 className="font-weight-medium mb-4 mt-4 text-secondary">
			{children}
		</h5>
	);
};

InstanceItemDetail.SectionAttribute = ({description, detail}) => {
	return (
		<p className="row">
			<span className="col-2 font-weight-medium small text-secondary">{`${description} `}</span>

			<span className="col small">{detail}</span>
		</p>
	);
};

InstanceItemDetail.contextType = AppContext;
export default InstanceItemDetail;
