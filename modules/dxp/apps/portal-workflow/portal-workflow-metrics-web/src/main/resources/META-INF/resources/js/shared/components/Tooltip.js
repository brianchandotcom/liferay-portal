import getCN from 'classnames';
import React from 'react';

const CLASSNAME = 'workflow-tooltip';

export default class Tooltip extends React.Component {
	constructor(props) {
		super(props);

		this.state = {
			displayTooltip: false
		};
	}

	hideTooltip() {
		this.setState({displayTooltip: false});
	}

	showTooltip() {
		this.setState({displayTooltip: true});
	}

	render() {
		const {message, position = 'top', width} = this.props;
		const {displayTooltip} = this.state;

		return (
			<span
				className={CLASSNAME}
				onMouseLeave={this.hideTooltip.bind(this)}
			>
				{displayTooltip && (
					<TooltipBase
						position={position}
						title={message}
						visible={displayTooltip}
						width={width}
					/>
				)}
				<span
					className='tooltip-trigger'
					onMouseOver={this.showTooltip.bind(this)}
				>
					{this.props.children}
				</span>
			</span>
		);
	}
}

export class TooltipBase extends React.Component {
	render() {
		const {position, title, visible, width} = this.props;

		const classes = getCN('tooltip', {
			[`clay-tooltip-${position}`]: position,
			[`workflow-tooltip-${position}`]: position,
			['show']: visible
		});

		const classesArrow = getCN('arrow', 'workflow-tooltip-arrow');

		return (
			<div className={classes} style={{width: `${width}px`}}>
				{position !== 'none' && <div className={classesArrow} />}
				<div className='tooltip-inner'>
					<div>{title}</div>
				</div>
			</div>
		);
	}
}
