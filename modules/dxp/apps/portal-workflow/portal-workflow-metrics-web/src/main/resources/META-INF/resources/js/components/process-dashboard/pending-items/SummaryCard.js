import { AppContext } from '../../AppContext';
import autobind from 'autobind-decorator';
import { ChildLink } from '../../../shared/components/router/routerWrapper';
import { formatNumber } from '../../../shared/util/numeral';
import { getPercentage } from '../../../shared/util/util';
import Icon from '../../../shared/components/Icon';
import React from 'react';

class SummaryCard extends React.Component {
	constructor(props) {
		super(props);

		this.state = {
			hovered: false
		};
	}

	get formattedPercentage() {
		const { total, totalValue, value } = this.props;

		if (!total) {
			const percentage = getPercentage(value, totalValue);

			return formatNumber(percentage, '0[.]00%');
		}

		return null;
	}

	get formattedValue() {
		const { value } = this.props;
		const formatThreshold = 9999;

		if (value > formatThreshold) {
			return formatNumber(value, '0[,0][.]0a');
		}

		return formatNumber(value, '0[,]0');
	}

	@autobind
	handleMouseOver(evt, callback) {
		this.setState({ hovered: true }, callback);
	}

	@autobind
	handleMouseOut(evt, callback) {
		this.setState({ hovered: false }, callback);
	}

	render() {
		const { defaultDelta } = this.context;
		const { hovered } = this.state;
		const { iconColor, iconName, processId, title, total, value } = this.props;

		const dashboardItemsPath = `/instances/${processId}/${defaultDelta}/1`;

		const disabled = !total && value === undefined;

		const disabledClassName = disabled ? 'disabled' : '';
		const disableRender = Component => !disabled && Component;

		const hoveredClassName = hovered ? 'highlight-hover' : '';
		const hoverRender = (Component, HoveredComponent) => (
			<span className={`${hoveredClassName} xsmall`}>
				{(hovered && HoveredComponent) || Component}
			</span>
		);

		return (
			<ChildLink
				className={`${disabledClassName} process-dashboard-summary-card`}
				onMouseOut={this.handleMouseOut}
				onMouseOver={this.handleMouseOver}
				to={dashboardItemsPath}
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