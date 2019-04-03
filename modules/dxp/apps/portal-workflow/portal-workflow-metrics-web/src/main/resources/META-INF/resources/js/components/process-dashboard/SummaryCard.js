import autobind from 'autobind-decorator';
import getCN from 'classnames';
import Icon from '../../shared/components/Icon';
import React from 'react';

const CLASSNAME = 'process-dashboard-summary-card';

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

		const classes = getCN(CLASSNAME, elementClasses);

		const { inOver } = this.state;

		return (
			<a
				className={classes}
				href={'#'}
				onMouseOut={this.handleMouseOut}
				onMouseOver={this.handleMouseOver}
			>
				<div>
					<div className={'header'}>
						{iconName && (
							<Icon
								elementClasses={['iconBadge', `icon-${iconColor}`, 'mr-3']}
								iconName={iconName}
							/>
						)}
						{title}
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