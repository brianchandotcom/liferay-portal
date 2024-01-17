import Label from 'shared/components/Label';
import React from 'react';
import {PropTypes} from 'prop-types';
class SubscriptionTitle extends React.Component {
	static propTypes = {
		labelText: PropTypes.string,
		name: PropTypes.node
	};

	render() {
		const {labelText, name} = this.props;

		return (
			<div
				className={`plan-title${
					this.props.className ? ` ${this.props.className}` : ''
				}`}
			>
				<div className='plan-name'>{name}</div>

				{!!labelText && (
					<Label display='primary' size='lg' uppercase>
						{labelText}
					</Label>
				)}
			</div>
		);
	}
}

export default SubscriptionTitle;
