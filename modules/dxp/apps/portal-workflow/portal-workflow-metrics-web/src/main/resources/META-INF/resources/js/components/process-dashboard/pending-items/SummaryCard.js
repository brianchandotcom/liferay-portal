import { AppContext } from '../../AppContext';
import autobind from 'autobind-decorator';
import { ChildLink } from '../../../shared/components/router/routerWrapper';
import getCN from 'classnames';
import Icon from '../../../shared/components/Icon';
import React from 'react';

const CLASS_NAME = 'process-dashboard-summary-card';

class SummaryCard extends React.Component {
	constructor(props) {
		super(props);

		this.state = {
			inOver: false
		};
	}

	@autobind
	handleMouseOver() {
		this.setState({ inOver: !this.state.inOver });
	}

	@autobind
	handleMouseOut() {
		this.setState({ inOver: !this.state.inOver });
	}

	render() {
		const {
			elementClasses,
			iconColor,
			iconName,
			percentage,
			processId,
			title,
			total
		} = this.props;

		const className = getCN(CLASS_NAME, elementClasses);

		const { defaultDelta } = this.context;

		const dashboardItemsPath = `/instances/${processId}/${defaultDelta}/1`;

		const { inOver } = this.state;

		return (
			<ChildLink
				className={className}
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

					<div className={'body'}>{total}</div>

					<div className={'footer'}>
						{!inOver && percentage}

						{inOver && (
							<span className={'highlight-hover'}>
								{Liferay.Language.get('see-items')}
							</span>
						)}
					</div>
				</div>
			</ChildLink>
		);
	}
}

SummaryCard.contextType = AppContext;
export default SummaryCard;