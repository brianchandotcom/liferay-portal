import {AppContext} from '../../AppContext';
import {ChildLink} from '../../../shared/components/router/routerWrapper';
import {filterConstants} from '../instance-list/store/InstanceListStore';
import {formatNumber} from '../../../shared/util/numeral';
import {formatQueryDate} from '../util/timeRangeUtil';
import {getPercentage} from '../../../shared/util/util';
import Icon from '../../../shared/components/Icon';
import {processStatusConstants} from '../filter/store/ProcessStatusStore';
import React from 'react';

class SummaryCard extends React.Component {
	constructor(props) {
		super(props);

		this.state = {
			hovered: false
		};
	}

	get formattedPercentage() {
		const {total, totalValue, value} = this.props;

		if (!total) {
			const percentage = getPercentage(value, totalValue);

			return formatNumber(percentage, '0[.]00%');
		}

		return null;
	}

	get formattedValue() {
		return formatNumber(this.props.value, '0[,0][.]0a');
	}

	getFiltersQuery() {
		const {completed, slaStatusFilter, timeRange} = this.props;

		const filterParams = {
			[filterConstants.processStatus]: [
				completed
					? processStatusConstants.completed
					: processStatusConstants.pending
			],
			[filterConstants.slaStatus]: [slaStatusFilter]
		};

		if (timeRange) {
			filterParams[filterConstants.timeRange] = [timeRange.key];
			filterParams[filterConstants.timeRangeDateEnd] = formatQueryDate(
				timeRange.dateEnd
			);
			filterParams[filterConstants.timeRangeDateStart] = formatQueryDate(
				timeRange.dateStart
			);
		}

		return filterParams;
	}

	handleMouseOver(evt, callback) {
		this.setState({hovered: true}, callback);
	}

	handleMouseOut(evt, callback) {
		this.setState({hovered: false}, callback);
	}

	render() {
		const {
			completed,
			getTitle,
			iconColor,
			iconName,
			processId,
			slaStatusFilter,
			total,
			value
		} = this.props;
		const {defaultDelta} = this.context;
		const {hovered} = this.state;

		const disabled = !total && value === undefined;

		const disabledClassName = disabled ? 'disabled' : '';
		const disableRender = Component => !disabled && Component;

		const hoveredClassName = hovered ? 'highlight-hover' : '';
		const hoverRender = (Component, HoveredComponent) => (
			<span className={`${hoveredClassName} xsmall`}>
				{(hovered && HoveredComponent) || Component}
			</span>
		);

		const instancesListPath = `/instances/${processId}/${defaultDelta}/1`;
		const title = getTitle(completed);

		return (
			<ChildLink
				className={`${disabledClassName} process-dashboard-summary-card`}
				onMouseOut={this.handleMouseOut.bind(this)}
				onMouseOver={this.handleMouseOver.bind(this)}
				query={{filters: this.getFiltersQuery(slaStatusFilter)}}
				to={instancesListPath}
			>
				<div>
					<div className={'header'}>
						{iconName && (
							<span
								className={`bg-${iconColor}-light mr-3 sticker sticker-circle`}
							>
								<span className="inline-item">
									<Icon
										elementClasses={`text-${iconColor}`}
										iconName={iconName}
									/>
								</span>
							</span>
						)}

						<span>{title}</span>
					</div>

					{disableRender(
						<div className="body" title={value}>
							{this.formattedValue}
						</div>
					)}

					{disableRender(
						<div className="footer">
							{hoverRender(
								this.formattedPercentage,
								Liferay.Language.get('see-items')
							)}
						</div>
					)}
				</div>
			</ChildLink>
		);
	}
}

SummaryCard.contextType = AppContext;
export default SummaryCard;
