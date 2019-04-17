import autobind from 'autobind-decorator';
import getCN from 'classnames';
import Icon from '../../../shared/components/Icon';
import React from 'react';

const CLASS_NAME = 'process-dashboard-summary-card';

export default class SummaryCard extends React.Component {
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
			title,
			total
		} = this.props;

		const className = getCN(CLASS_NAME, elementClasses);

		const { inOver } = this.state;

		return (
			<a
				href={'#'}
				className={className}
				onMouseOut={this.handleMouseOut}
				onMouseOver={this.handleMouseOver}
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
						{percentage &&
							(!inOver ? (
								percentage
							) : (
								<span className={'highlight-hover'}>
									{Liferay.Language.get('see-items')}
								</span>
							))}
					</div>
				</div>
			</a>
		);
	}
}