import React from 'react';

export default class SummaryCard extends React.Component {
	render() {
		const {description, total} = this.props;

		return (
			<div className="summary-card border col-2" style={{marginLeft: '16px'}}>
				<span className="regular-text semi-bold text-secondary">
					{description}
				</span>
				<div className="">
					<span className="font-weight-normal" style={{fontSize: '2.5rem'}}>
						{total}
					</span>
					<span className="regular-text text-secondary"> {'items'}</span>
				</div>
			</div>
		);
	}
}